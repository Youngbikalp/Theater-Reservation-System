package com.company;

import java.util.ArrayList;

public class Show {

    //Theatre MainBalcony;
    private String showName;
    private ArrayList<Seat> seats;

    public Show(String showName) {
        this.showName = showName;
//        this.showTime = showTime;
//        this.showDate = showDate;
        loadSeats();
    }

    public void loadSeats() {
        seats = new ArrayList<>();
        //Main Floor
        for (int i = 1; i <= 50; i++) {
            Seat seat = new Seat("1","m" + i, "Main Floor - Left Side", 35);
            seats.add(seat);
        }
        for (int i = 51; i <= 100; i++) {
            Seat seat = new Seat("2","m" + i, "Main Floor - Right Side", 35);
            seats.add(seat);
        }
        for (int i = 101; i <= 150; i++) {
            Seat seat = new Seat("3","m" + i, "Main Floor", 35);
            seats.add(seat);
        }
        //South Balcony
        for (int i = 1; i <= 25; i++) {
            Seat seat = new Seat("4","sb" + i, "South Balcony", 50);
            seats.add(seat);
        }
        //West Balconies
        for (int i = 1; i <= 100; i++) {
            Seat seat = new Seat("5","wb" + i, "West Balconies", 40);
            seats.add(seat);
        }
        //East Balconies
        for (int i = 1; i <= 100; i++) {
            Seat seat = new Seat("6","eb" + i, "East Balconies", 40);
            seats.add(seat);
        }

    }


    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }


    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public double getSeatPrice(String seat_id){
        for (Seat seat : seats) {
            if(seat.getSeat_id().equalsIgnoreCase(seat_id)){
                return seat.getPrice();
            }
        }
        return 0;
    }
}
