package com.demo.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.R;
import com.demo.utils.DisplayUtil;


/**
 * Created by pepys on 2017/12/14
 * description:
 *
 */
public class PopUpWindowAlertDialog extends Dialog {

    public PopUpWindowAlertDialog(Context context) {
        super(context);
    }

    PopUpWindowAlertDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private int titleSize, messageSize, positiveButtonTextSize, negativeButtonTextSize;

        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        //pop点击取消按钮是否会消失
        private boolean isPopDismiss = true;
        //pop点击返回是否会消失
        private boolean isBackKeyPopDismiss = true;

        /**
         * 自定义content的高度
         */
        private int contentHeight = 0;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message, int messageSize) {
            this.message = message;
            if (messageSize > 0) {
                this.messageSize = messageSize;
            }
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setContentHeight(int contentHeight) {
            this.contentHeight = contentHeight;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * 设置标题文字和文字的大小
         *
         * @param title
         * @param tv_size 单位是dp
         * @return
         */
        public Builder setTitle(String title, int tv_size) {
            this.title = title;
            if (tv_size > 0) titleSize = tv_size;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * 设置点击返回是否会消失
         * @param isBackKeyPopDismiss
         * @return
         */
        public Builder setBackKeyPopDismiss(boolean isBackKeyPopDismiss){
            this.isBackKeyPopDismiss = isBackKeyPopDismiss;
            return this;
        }
        /**
         * 设置点击取消pop是否会消失
         * @param isPopDismiss
         * @return
         */
        public Builder setNegativePopDismiss(boolean isPopDismiss){
            this.isPopDismiss = isPopDismiss;
            return this;
        }
        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public PopUpWindowAlertDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //自定义透明背景的弹出窗
            final PopUpWindowAlertDialog dialog = new PopUpWindowAlertDialog(context, R.style.my_dialog);
            View layout = inflater.inflate(R.layout.view_popup_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // 设置弹出窗标题
            if (title != null) {
                TextView tvTitle = (TextView) layout.findViewById(R.id.tvTitle);
                tvTitle.setText(title);
                if (titleSize > 0) tvTitle.setTextSize(titleSize);
                tvTitle.setVisibility(View.VISIBLE);
            } else {
                layout.findViewById(R.id.tvTitle).setVisibility(View.GONE);
            }
            // 设置确定按钮文字
            if (positiveButtonText != null ) {
                layout.findViewById(R.id.alert_dialog_ll_btn).setVisibility(View.VISIBLE);
                ((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ( layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            dialog.dismiss();
                        }
                    });
                }
            }  else{
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            }
            // 设置取消按钮文字显示
            if (negativeButtonText != null && isPopDismiss) {
                layout.findViewById(R.id.alert_dialog_ll_btn).setVisibility(View.VISIBLE);
                ((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    (layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            dialog.dismiss();
                        }
                    });
                }
            } else if(isPopDismiss == false) {
                layout.findViewById(R.id.alert_dialog_ll_btn).setVisibility(View.VISIBLE);
                ((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    (layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.llNegative).setVisibility(View.GONE);
            }

            if (message != null) {
                TextView tvMessage = (TextView) layout.findViewById(R.id.tvMessage);
                tvMessage.setText(message);
                if (messageSize > 0) tvMessage.setTextSize(messageSize);
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ViewGroup.LayoutParams layoutParams;
                if(contentHeight == 0){
                    layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                }else{
                    layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(contentHeight));
                }
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView,layoutParams);
            } else {
                (layout.findViewById(R.id.content)).setVisibility(View.GONE);
            }

            if(!isBackKeyPopDismiss){
                dialog.setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK){
                            return true;
                        }
                        return false;
                    }
                });
            }
            dialog.show();
            dialog.setContentView(layout, new ViewGroup.LayoutParams(DisplayUtil.getScreenWidth(), DisplayUtil.getScreenHeight()));
            show(dialog);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            return dialog;
        }

        private void show(PopUpWindowAlertDialog dialog) {
            dialog.show();
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DisplayUtil.getScreenWidth();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }
}
