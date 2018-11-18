package com.qatestlab.utils;

public class Properties {
    private static final String DEFAULT_BASE_ADMIN_URL = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";
    private static final String DEFAULT_BASE_URL = "http://prestashop-automation.qatestlab.com.ua/";

    public static String getDefaultBaseAdminUrl() {
        return DEFAULT_BASE_ADMIN_URL;
    }

    public static String getDefaultBaseUrl() {
        return DEFAULT_BASE_URL;
    }
}
