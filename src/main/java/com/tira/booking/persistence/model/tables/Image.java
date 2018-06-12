package com.tira.booking.persistence.model.tables;

import com.tira.booking.persistence.model.BaseModel;

import javax.persistence.*;
import java.sql.Blob;
import java.util.UUID;

@Entity
@Table(name = "image")
public class Image extends BaseModel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "media_type")
    private String media_type;

    @Column(name = "file")
    @Lob
    private byte[] file;

    public Image() {
    }

    public Image(String media_type, byte[] file) {
        this.media_type = media_type;
        this.file = file;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
