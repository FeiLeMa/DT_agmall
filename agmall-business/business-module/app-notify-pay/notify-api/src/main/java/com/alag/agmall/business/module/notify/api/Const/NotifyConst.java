package com.alag.agmall.business.module.notify.api.Const;

public class NotifyConst {

    public interface Notify {
        String QUEUE_NAME = "MAX_NOTIFY";
    }


    public enum NotifyStatusEnum {
        CREATED(900, "通知记录已创建"),
        SUCCESS(100, "通知成功"),
        FAILED(444, "通知失败"),
        HTTP_REQUEST_SUCCESS(200, "http请求响应成功"),
        HTTP_REQUEST_FALIED(400, "http请求失败");

        private String value;
        private int code;

        NotifyStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static NotifyStatusEnum codeOf(int code) {
            for (NotifyStatusEnum notifyStatusEnum : values()) {
                if (notifyStatusEnum.getCode() == code) {
                    return notifyStatusEnum;
                }
            }
            throw new RuntimeException("没有有找到对应的枚举");
        }
    }

    public enum NotifyTypeEnum {
        /**
         * 商户通知
         */
        MERCHANT(20, "商户通知"),

        /**
         * 微信刷卡支付轮询
         */
        WEPAY_SEARCH(10, "微信刷卡支付轮询");

        private int code;
        private String value;

        NotifyTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static NotifyTypeEnum codeOf(int code) {
            for (NotifyTypeEnum notifyTypeEnum : values()) {
                if (notifyTypeEnum.getCode() == code) {
                    return notifyTypeEnum;
                }
            }
            throw new RuntimeException("没有有找到对应的枚举");
        }

    }

    public interface DestinationName {

        /**
         * 会计队列
         */
        String ACCOUNTING_NOTIFY = "ACCOUNTING_NOTIFY";

        /**
         * 银行队列
         */
        String BANK_NOTIFY = "BANK_NOTIFY";

        /**
         * 商户通知
         */
        String MERCHANT_NOTIFY = "MERCHANT_NOTIFY";

    }
}
