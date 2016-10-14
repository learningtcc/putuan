package com.advanpro.putuan.model.type;

/**
 * 作者： Vance
 * 时间： 2016/9/29
 * 描述： 设备类型.
 */
public enum DeviceType {
    PT {
        @Override
        public String desc() {
            return "智能蒲团";
        }
    },

    KD {
        @Override
        public String desc() {
            return "智能磕垫";
        }
    },

    ST {
        @Override
        public String desc() {
            return "智能手套";
        }
    };

    public abstract String desc();
}