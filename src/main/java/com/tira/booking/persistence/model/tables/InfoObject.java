package com.tira.booking.persistence.model.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tira.booking.persistence.model.BaseModel;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "info_object")
public class InfoObject  extends BaseModel{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "landmarks_info_id", referencedColumnName = "id")
    private AreaInfo landmarksAreaInfo;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "airports_info_id", referencedColumnName = "id")
    private AreaInfo airportsAreaInfo;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "markets_info_id", referencedColumnName = "id")
    private AreaInfo marketsAreaInfo;


    public InfoObject() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public AreaInfo getLandmarksAreaInfo() {
        return landmarksAreaInfo;
    }

    public void setLandmarksAreaInfo(AreaInfo landmarksAreaInfo) {
        this.landmarksAreaInfo = landmarksAreaInfo;
    }

    public AreaInfo getAirportsAreaInfo() {
        return airportsAreaInfo;
    }

    public void setAirportsAreaInfo(AreaInfo airportsAreaInfo) {
        this.airportsAreaInfo = airportsAreaInfo;
    }

    public AreaInfo getMarketsAreaInfo() {
        return marketsAreaInfo;
    }

    public void setMarketsAreaInfo(AreaInfo marketsAreaInfo) {
        this.marketsAreaInfo = marketsAreaInfo;
    }
}
