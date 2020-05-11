package com.company;


import java.util.ArrayList;

public class Booking {

    private int booking_id;
    private ArrayList<Reservation> reservations;

    public Booking(int booking_id) {
        this.booking_id = booking_id;
        reservations=new ArrayList<>();
    }

    public Booking(int booking_id, ArrayList<Reservation> reservations) {
        this.booking_id = booking_id;
        this.reservations = reservations;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public boolean addReservations(Reservation reserve) {
        this.reservations.add(reserve);
        return true;
    }

    public void removeReservations(Reservation reservations) {
        this.reservations.remove(reservations);
    }

    public String getUserID(){
        if(reservations.isEmpty())return null;
        return reservations.get(0).getUserid();
    }

    void printReservation() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
        }
    }

    void deleteReservationBySeatNumber(String seatNumber) {

        for (Reservation reservation : reservations) {
            if(reservation.getSeatNumber().equalsIgnoreCase(seatNumber)){
                reservations.remove(reservation);
                break;
            }
        }
    }

    void printReservationReceipt() {
        if(reservations.get(0).isDiscountedNight()){
            double total=0;
            for (Reservation reservation : reservations) {
                System.out.println(calculateAmount(reservation,-1));
                total+=20 ;

            }
            System.out.println("Total Price: " + total);
            return;
        }

        if(reservations.size()<5){
            double total=0;
            for (Reservation reservation : reservations) {
                System.out.println(calculateAmount(reservation,0));
                total+= getAmount(reservation.getSeatNumber(), 0);

            }
            System.out.println("Total Price: " + total);
        }else if (reservations.size()>=5 && reservations.size()<=10){
            double total=0;
            for (Reservation reservation : reservations) {
                System.out.println(calculateAmount(reservation,2));
                total+= getAmount(reservation.getSeatNumber(), 2);

            }
            System.out.println("Total Price: " + total);
        }else if (reservations.size()>=11 && reservations.size()<=20){
            double total=0;
            for (Reservation reservation : reservations) {
                System.out.println(calculateAmount(reservation,5));
                total+= getAmount(reservation.getSeatNumber(), 5);

            }
            System.out.println("Total Price: " + total);
        }


    }

    private double getAmount(String seatNumber, int discount){
        double amt=new Show("").getSeatPrice(seatNumber)-discount;
        return amt;
    }
    private String calculateAmount(Reservation reservation, int discount) {

        double amt=new Show("").getSeatPrice(reservation.getSeatNumber())-discount;
        if(discount==-1) amt=20;

        String text="Reservation{ showDate=" + reservation.getStringDate() + ", showTime=" + reservation.getStringTime()
                + ", seatNumber=" + reservation.getSeatNumber() + ", price=" + amt  ;
        text+= '}';
        return text;
    }
    private String calculateDiscountedAmount(Reservation reservation) {
        String text="Reservation{ showDate=" + reservation.getStringDate() + ", showTime=" + reservation.getStringTime()
                + ", seatNumber=" + reservation.getSeatNumber() + ", price=20"  ;
        text+= '}';
        return text;
    }

    public boolean reservationExists(Reservation reserve) {
        for (Reservation r : reservations) {
            if (r.getSeatNumber().equalsIgnoreCase(reserve.getSeatNumber()) && r.getStringDate().equals(reserve.getStringDate()) && r.getStringTime().equals(reserve.getStringTime())) {
                return true;
            }
        }
        return false;
    }
}
