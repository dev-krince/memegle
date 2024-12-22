package com.krince.memegle.global.constant;

public enum CorsArrowedUrl {

    MEMEGLE_PROD_CLIENT_URL("https://memegle.netlify.app"),
    MEMEGLE_PROD_ADMIN_URL("https://project-memegle.github.io/admin/"),
    MEMEGLE_DEV_CLIENT_URL1("http://Localhost:5173"),
    MEMEGLE_DEV_CLIENT_URL2("http://Localhost:5174"),
    MEMEGLE_DEV_CLIENT_URL3("http://127.0.0.1:5173"),
    MEMEGLE_DEV_CLIENT_URL4("http://Localhost:5174"),;

    String corsArrowedUrl;

    CorsArrowedUrl(String corsArrowedUrl) {
        this.corsArrowedUrl = corsArrowedUrl;
    }

    public String getStringValue() {
        return corsArrowedUrl;
    }
}
