package com.kubilaycakmak.models;

import java.util.ArrayList;

public class User {
    String id;
    String name;
    String lastname;
    String username;
    String created_date;
    ArrayList<Recipe> recipes;

    public User(String id, String name, String lastname, String username) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
    }

    public User(String id, String username, String name, String lastname, String created_date, ArrayList<Recipe> recipes) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.created_date = created_date;
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", created_date='" + created_date + '\'' +
                ", recipes=" + recipes +
                '}';
    }
}
