package com.advanpro.putuan.utils.json;

/**
 * 描述： 封装状态码
 * 作者： Joinly
 * 时间： 2015/10/22.
 */
public enum StatusCode {

    //常用错误码 (3000 - 3099)
    OK(3000) {
        public String getMessage() {
            return "成功";
        }
    },
    INTERNAL_ERROR(3001) {
        public String getMessage() {
            return "服务器内部错误";
        }
    },
    INVALID_PARAMETER(3002) {
        public String getMessage() {
            return "参数不合法";
        }
    },
    NO_DATA(3003) {
        public String getMessage() {
            return "数据不存在";
        }
    },
    NOT_ALLOW(3004) {
        public String getMessage() {
            return "操作不允许";
        }
    },
    TOKEN_IS_EMPTY(3005) {
        public String getMessage() {
            return "用户凭证不完整!";
        }
    },
    TOKEN_IS_INVALID(3006) {
        public String getMessage() {
            return "非法的用户凭证!";
        }
    },
    TOKEN_IS_EXPIRE(3007) {
        public String getMessage() {
            return "用户凭证已过期!";
        }
    },
    //业务相关错误码 (3100 - 3299)
    USER_ALREADY_EXISTS(3100) {
        public String getMessage() {
            return "用户已存在!";
        }
    },
    USER_NOT_EXISTS(3101) {
        public String getMessage() {
            return "用户不存在!";
        }
    },
    OLD_PASSWORD_ERROR(3102) {
        public String getMessage() {
            return "原密码错误!";
        }
    },
    PASSWORD_ERROR(3103) {
        public String getMessage() {
            return "密码错误!";
        }
    },
    INPUT_PHONE(3104) {
        public String getMessage() {
            return "请输入正确的电话号码!";
        }
    },
    USER_PHONE_NOT_EXISTS(3105) {
        public String getMessage() {
            return "电话号码不存在!";
        }
    },
    USER_EMAIL_NOT_EXISTS(3106) {
        public String getMessage() {
            return "邮箱不存在!";
        }
    },
    VERIFY_CODE_IS_EXPIRE(3107) {
        public String getMessage() {
            return "验证码已过期!";
        }
    },
    VERIFY_CODE_ERROR(3108) {
        public String getMessage() {
            return "验证码不正确!";
        }
    },
    UPLOAD_FILE_NOT_NULL(3109) {
        public String getMessage() {
            return "上传文件不能为空!";
        }
    },
    USER_FACE_NOT_PICTRUE(3110) {
        public String getMessage() {
            return "请上传图片文件!";
        }
    },
    REPASSWORD_ERROR(3111) {
        public String getMessage() {
            return "两次密码不一样!";
        }
    },
    PHONE_ALREADY_EXISTS(3112) {
        public String getMessage() {
            return "手机号已经被注册了!";
        }
    },
    DEVICE_BIND(3113) {
        public String getMessage() {
            return "此设备已被绑定!";
        }
    },
    WX_BIND(3114) {
        public String getMessage() {
            return "该账号已绑定微信或该微信已被其它账号绑定了!";
        }
    },
    PHONE_ALREADY_BIND(3115) {
        public String getMessage() {
            return "该手机号已经被绑定了!";
        }
    };

    private final int value;

    private StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public abstract String getMessage();
}
