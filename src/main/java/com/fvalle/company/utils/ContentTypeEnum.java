package com.fvalle.company.utils;

import lombok.Getter;

@Getter
public enum ContentTypeEnum {

    PNG("image/png", ".png"),
    JPG("image/jpg", ".jpg"),
    JPEG("image/jpeg", ".jpeg");

    private final String type;
    private final String fileExtension;

    ContentTypeEnum(String type, String fileExtension) {
        this.type = type;
        this.fileExtension = fileExtension;

    }
}
