package com.kubilaycakmak.models;

import java.util.Date;

public class Recipe {
    String id;
    String userId;
    String name;
    String short_description;
    String long_description;
    String img;
    double calorie;
    double cookTime;
    Type type;
    String created_time;

    public Recipe() {
    }

    public Recipe(String id, String userId, String name, String short_description, String long_description, String img, double calorie, double cookTime, Type type, String created_time) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.short_description = short_description;
        this.long_description = long_description;
        this.img = img;
        this.calorie = calorie;
        this.cookTime = cookTime;
        this.type = type;
        this.created_time = created_time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getCookTime() {
        return cookTime;
    }

    public void setCookTime(double cookTime) {
        this.cookTime = cookTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", short_description='" + short_description + '\'' +
                ", long_description='" + long_description + '\'' +
                ", img='" + img + '\'' +
                ", calorie=" + calorie +
                ", cookTime=" + cookTime +
                ", type=" + type +
                ", created_time=" + created_time +
                '}';
    }
}
