package com.malin.order_backend.config;

public class FileProperties {
    private String uploadDir;

    public FileProperties() {
        this.uploadDir = "./Image";
    }

    public String getUploadDir() {
        return uploadDir;
    }
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
