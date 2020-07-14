package com.pi.microservice.factory.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Document(collection = "factory")
public class Factory {

    @Id
    private String id;
    @NotEmpty
    private String idOwner;
    private String description;
    private String title;
    private Double latitude;
    private Double longitude;
    private GeoJsonPoint location;
    private Double price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    private String image;
    private String category;


    public Factory(@NotEmpty String idOwner, String description, String title, Double latitude, Double longitude, Double price) {
        this.idOwner = idOwner;
        this.description = description;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.location = new GeoJsonPoint(latitude, longitude);
        this.category = "factory";

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public GeoJsonPoint getPoint() {
        return location;
    }

    public void setPoint(GeoJsonPoint location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
