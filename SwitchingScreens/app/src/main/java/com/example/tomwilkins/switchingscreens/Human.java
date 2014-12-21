package com.example.tomwilkins.switchingscreens;

import java.io.Serializable;

/**
 * Created by tomwilkins on 21/12/2014.
 */
public class Human implements Serializable {

    private double height, weight;

    public Human(String name, double height, double weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    private String name = "";

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
