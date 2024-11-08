package com.whu.servicedataclient.util;

import java.util.UUID;

public class IDUtil {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
