package com.company;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private String userid;
    private String showTitle;
    private LocalTime showTime;
    private LocalDate showDate;
    private String seatNumber;

    public Reservation(String userid, String showTitle, LocalTime showTime, LocalDate showDate, String seatNumber) {
        this.userid = userid;
        this.showTitle = showTitle;
        this.showTime = showTime;
        this.showDate = showDate;
        this.seatNumber = seatNumber;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public LocalTime getStringTime() {
        return showTime;
    }

    public void setStringTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public LocalDate getStringDate() {
        return showDate;
    }

    public void setStringDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "Reservation{" + "showTitle=" + showTitle + ", showTime=" + showTime + ", showDate=" + showDate + ", seatNumber=" + seatNumber + '}';
    }

    boolean isDiscountedNight() {
        if(showDate.equals(LocalDate.parse("2020-12-26")) || showDate.equals(LocalDate.parse("2020-12-27"))){
            return true;
        }
        return false;
    }



}
