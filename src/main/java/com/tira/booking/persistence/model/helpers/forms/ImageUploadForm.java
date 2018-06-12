package com.tira.booking.persistence.model.helpers.forms;


import com.tira.booking.persistence.model.BaseModel;

import java.util.UUID;

/**
 * The type Image upload form.
 */
public class ImageUploadForm extends BaseModel {

    private UUID lodgingId;
    private String path;
    private String imageType;

    public ImageUploadForm() {}

    public UUID getLodgingId() {
        return lodgingId;
    }

    public void setLodgingId(UUID lodgingId) {
        this.lodgingId = lodgingId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
