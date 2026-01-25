package com.example.gatherly.configs;

public class TenantContext {
    private static final ThreadLocal<String> LOCATION = new ThreadLocal<>();

    public static void setLocation(String location) {
        LOCATION.set(location);
    }

    public static String getLocation() {
        return LOCATION.get();
    }

    public static void clear() {
        LOCATION.remove();
    }
}
