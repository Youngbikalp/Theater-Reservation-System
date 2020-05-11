package com.company;
import com.company.*;
import java.util.ArrayList;

public class Seat {
    private String section_id;
    private String seat_id;
    private String section_name;
    private double price;

    public Seat(String section_id, String id, String section_name, double price) {
        this.section_id = section_id;
        this.seat_id = id;
        this.section_name = section_name;
        this.price = price;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(String seat_id) {
        this.seat_id = seat_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
