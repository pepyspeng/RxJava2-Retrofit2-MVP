package com.demo.data.Net;

import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.demo.utils.NetUtils;
import com.orhanobut.logger.Logger;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.demo.base.BaseApplication.mEnvType;
import static com.demo.data.Net.NetConstants.TYPE_COUNT;


/**
 * Created by zhupp on 2017/12/8.
 */

public class RetrofitInstance {

    private static Retrofit retrofit;

    private static SparseArray<Retrofit> sRetrofitManager = new SparseArray<>(TYPE_COUNT);

    public static Retrofit getRetrofit() {
        Retrofit retrofit = sRetrofitManager.get(mEnvType);
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(NetConstants.getBaseUrl(mEnvType))
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            sRetrofitManager.put(mEnvType, retrofit);
        }
        return retrofit;
    }

    public static OkHttpClient getOkHttpClient() {

        Cache cache = new Cache(new File(NetConstants.CACHE_DIR, NetConstants.CACHE_FILE_NAME), NetConstants.HTTP_CACHE_SIZE);

        return new OkHttpClient.Builder().cache(cache)
                .connectTimeout(NetConstants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(NetConstants.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(NetConstants.WRITE_TIME_OUT, TimeUnit.SECONDS)
//                .addInterceptor(mRewriteCacheControlInterceptor)  //关于缓存的配置
                .addNetworkInterceptor(HttpCacheInterceptor)
                .addInterceptor(mLoggingInterceptor)
                .build();
    }


    private final static Interceptor HttpCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isNetworkAvailable()) {  //没网强制从缓存读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Logger.i("Okhttp", "no network");
            }
            Response originalResponse = chain.proceed(request);
            if (originalResponse.code() == 504 && NetUtils.isNetworkAvailable()) {
                //如果采用读取缓存的方式请求,当读取不到缓存时,并且有活动网络,那么重新发起网络请求
                Logger.e("504,无法读取到缓存,重新请求最新数据");
                //缓存不存在,先尝试读取缓存
                request = request.newBuilder().cacheControl(new CacheControl.Builder().maxStale(
                        (int) NetConstants.CACHE_STALE_SEC, TimeUnit.SECONDS).build()).build();
                originalResponse = chain.proceed(request);
            }

            //这里需要注意,按照正常逻辑来说,缓存时间是由服务器配置的header控制,但是我们后台没有做处理
            //所以统一假定缓存的stale为配置里的值，在这里进行统一的设置
            if (NetUtils.isNetworkAvailable()) {
                String cacheControl = request.cacheControl().toString();
                if (!TextUtils.isEmpty(cacheControl)) {
                    originalResponse = originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                }
                return originalResponse;
            } else {
                Logger.e("no network");
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + NetConstants.CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 日志
     */
    private final static Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //the request url
            String url = request.url().toString();
            //the request method
            String method = request.method();
            long t1 = System.nanoTime();

            Logger.i(String.format("Sending %s request [url = %s] %n [connection=%s] %n " +
                    "[headerInfo= %s]", method, request.url(), chain.connection(), request.headers()));
            //the request body
            RequestBody requestBody = request.body();
            StringBuilder RequestBodysb = new StringBuilder("Request Body [");
            if (requestBody != null) {
                okio.Buffer buffer = new okio.Buffer();
                requestBody.writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                if (isPlaintext(buffer)) {
                    RequestBodysb.append(buffer.readString(charset));
                    RequestBodysb.append(" (Content-Type = ").append((contentType == null) ? "" :
                            contentType.toString()).append(",").append(requestBody.contentLength())
                            .append("-byte body)");
                } else {
                    RequestBodysb.append(" (Content-Type = ").append((contentType == null) ? "" :
                            contentType.toString()).append(",binary ").append(requestBody.contentLength())
                            .append("-byte body omitted)");
                }
            }
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            //the response time
            Logger.i(String.format(Locale.getDefault(), "Received response for [url = %s] in %.1f ms %n [headers = %s]"
                    , url, (t2 - t1) / 1e6d, response.headers().toString()));

            Logger.i(String.format(Locale.getDefault(), "Received response is %s ,message[%s],code[%d]", response
                    .isSuccessful() ? "success" : "fail", response.message(), response.code()));

            RequestBodysb.append("]");
            Logger.w(String.format("%s %s", method, RequestBodysb.toString()));
            //the response data
            ResponseBody body = response.body();

            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String bodyString = buffer.clone().readString(charset);
            Logger.i(String.format("Received response json string [%s]", bodyString));

            return response;
        }
    };
    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
