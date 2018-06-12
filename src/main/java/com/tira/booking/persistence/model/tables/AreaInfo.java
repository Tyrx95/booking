package com.tira.booking.persistence.model.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tira.booking.persistence.model.BaseModel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "area_info")
public class AreaInfo extends BaseModel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;


    @OneToMany(mappedBy = "landmarksAreaInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InfoObject> landmarks;

    @OneToMany(mappedBy = "marketsAreaInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InfoObject> markets;

    @OneToMany(mappedBy = "airportsAreaInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InfoObject> airports;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lodging_id")
    private Lodging lodging;


    public AreaInfo() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<InfoObject> getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(Set<InfoObject> landmarks) {
        this.landmarks = landmarks;
    }

    public Set<InfoObject> getMarkets() {
        return markets;
    }

    public void setMarkets(Set<InfoObject> markets) {
        this.markets = markets;
    }

    public Set<InfoObject> getAirports() {
        return airports;
    }

    public void setAirports(Set<InfoObject> airports) {
        this.airports = airports;
    }

    public Lodging getLodging() {
        return lodging;
    }

    public void setLodging(Lodging lodging) {
        this.lodging = lodging;
    }
}
