package com.company;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.*;

public class Main {
    private static HashMap<String, String> users = new HashMap<>();
    private static Theatre theatre;
    private static ArrayList<Booking> bookings;
    private static char LAST_ENTRY_POINT;

    public static void loadShows() {
        ArrayList<Show> shows = new ArrayList<Show>();
        shows.add(new Show("Miracle on 34th Street"));
        theatre = new Theatre("SJSU Theatre", shows);
    }

    public static void loadUsers() {

        try {
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(reader);

            FileInputStream input = new FileInputStream("Accounts.txt");
            BufferedReader read = new BufferedReader(new InputStreamReader(input));

            String line = read.readLine();
            while ((line != null)) {
                String[] parts = line.split(",");
                users.put(parts[0], parts[1]);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadBookings() {
        bookings = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(reader);

            FileInputStream input = new FileInputStream("Reservations.txt");
            BufferedReader read = new BufferedReader(new InputStreamReader(input));

            HashMap<Integer, ArrayList<Reservation>> _bookings = new HashMap<>();
            String line = read.readLine();
            while ((line != null)) {
                String[] parts = line.split(",");
                int booking_id = Integer.parseInt(parts[0]);
                if (_bookings.get(booking_id) == null) {
                    ArrayList<Reservation> reservations = new ArrayList<>();
                    Reservation r = new Reservation(parts[1], parts[2], LocalTime.parse(parts[3]), LocalDate.parse(parts[4]), parts[5]);
                    reservations.add(r);
                    _bookings.put(booking_id, reservations);
                } else {
                    Reservation r = new Reservation(parts[1], parts[2], LocalTime.parse(parts[3]), LocalDate.parse(parts[4]), parts[5]);
                    _bookings.get(booking_id).add(r);
                }
                line = read.readLine();
            }

            for (Map.Entry<Integer, ArrayList<Reservation>> e : _bookings.entrySet()) {
                bookings.add(new Booking(e.getKey(), e.getValue()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ArrayList<Show> shows = new ArrayList<Show>();
        HashMap<Date, Theatre[]> theatres = new HashMap<>();

        loadUsers();
        loadShows();
        loadBookings();
        String loggedInUser = menu1();
        String reservation = menu2(loggedInUser);
    }

    public static String menu1() throws IOException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n-------------------");
            System.out.println("THEATER RESERVATION SYSTEM");
            System.out.println("-------------------\n");
            System.out.println("[U] Sign Up");
            System.out.println("[I] Sign In");
            System.out.println("[E] Exit");
            System.out.print("\nEnter your choice : ");
            String str = sc.nextLine();
            char alpha = str.charAt(0);
            switch (alpha) {
                case 'U':
                case 'u':
                    LAST_ENTRY_POINT = 'u';
                    System.out.println("Enter your user name : ");
                    String username = sc.nextLine();
                    while (users.containsKey(username)) {
                        System.out.println("Username already exists. Set again : ");
                        username = sc.nextLine();
                    }
                    System.out.println("Enter password : ");
                    String password = sc.nextLine();

                    users.put(username, password);
                    FileWriter writer = new FileWriter("Accounts.txt"); //
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    for (Map.Entry<String, String> e : users.entrySet()) {
                        bufferedWriter.write(e.getKey() + ",");
                        bufferedWriter.write(e.getValue() + "\n");
                    }
                    bufferedWriter.close();
                    break;
                case 'I':
                case 'i':
                    LAST_ENTRY_POINT = 'i';
                    System.out.println("Please enter your username");
                    String user = sc.nextLine();//strip();
                    if (users.get(user) != null) {
                        System.out.println("Please enter your password");
                        String pass = sc.nextLine();//.strip();
                        if (pass.equals(users.get(user))) {
                            System.out.println("Success...");
                            System.out.println("Welcome " + user);
                            return user;
                        } else {
                            System.out.println("Error...");
                        }
                    } else {
                        System.out.println("Login failed...");
                    }
                    break;
                case 'E':
                case 'e':
                    LAST_ENTRY_POINT = 'e';
//                    FileWriter writer = new FileWriter("Accounts.txt"); //
//                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
//                    for (Map.Entry<String, String> e : users.entrySet()) {
//                        bufferedWriter.write(e.getKey() + ",");
//                        bufferedWriter.write(e.getValue() + "\n");
//                    }
//                    bufferedWriter.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }

    }

    public static String menu2(String userid) throws IOException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n-------------------");
            System.out.println("THEATER RESERVATION SYSTEM");
            System.out.println("Welcome, " + userid);
            System.out.println("-------------------\n");
            System.out.println("[R]eserve");
            System.out.println("[V]iew");
            System.out.println("[C]ancel");
            System.out.println("[O]ut");
            System.out.println("[L]og out");
            System.out.print("\nEnter your choice : ");
            String str = sc.nextLine();
            char alpha;
            try {
                alpha = str.charAt(0);
            } catch (Exception e) {
                alpha = LAST_ENTRY_POINT;
            }
            switch (alpha) {
                case 'R':
                case 'r':
                    LAST_ENTRY_POINT = 'r';
                    System.out.println("Enter the date of the show(yyyy-MM-dd) : ");
                    String date = sc.nextLine();
                    LocalDate reservation_date = LocalDate.parse(date);
                    ;
                    while (!validateDate(reservation_date)) {
                        System.out.println("Invalid date selected. Set again : ");
                        date = sc.nextLine();
                        reservation_date = LocalDate.parse(date);;
                    }
                    System.out.println("Enter the time of the show(HH:MM) : ");
                    String time = sc.nextLine();
                    LocalTime reservation_time = LocalTime.parse(time);
                    ;
                    while (!validateTime(reservation_time)) {
                        System.out.println("Invalid time selected. Set again : ");
                        time = sc.nextLine();
                        reservation_time = LocalTime.parse(time);;
                    }
                    int new_id = 0;
                    try {
                        new_id = bookings.get(bookings.size() - 1).getBooking_id() + 1;
                    } catch (Exception e) {
                    }
                    Reservation reserve = new Reservation("", "", reservation_time, reservation_date, "");
                    ArrayList<Reservation> reservations = new ArrayList<>();
                    System.out.println("How many people will you like to book for : ");
                    int num_of_tickets = sc.nextInt();

                    for (int i = 0; i < num_of_tickets + 1; i++) {
                        if (i == num_of_tickets) {
                            System.out.println("Do you wish to book more or not? ? 1 for yes and any other value for no?");
                            int book_continue = sc.nextInt();
                            if (book_continue == 1) {
                                i = 0;
                                System.out.println("Press enter to continue..... ");
                                sc.nextLine();
                                System.out.println("How many people will you like to book for : ");
                                num_of_tickets = sc.nextInt();

                            } else {
                                System.out.println("*************************");
                                System.out.println("Your Conformation number is " + new_id);
                                sc.nextLine();
                                break;
                            }
                        }
                        String section_id = "";
                        sc.nextLine();
                        while (!printSeatPlan(theatre.getShows().get(0), section_id, reserve, reservations)) {
                            System.out.println("[1] Main Floor - Left Side\n[2] Main Floor - Right Side\n[3] Main Floor\n[4] South Balcony\n[5] West Balconies\n[6] East Balconies");
                            System.out.println("Select Section ID : ");
                            section_id = sc.nextLine();
                        };
                        Show show = theatre.getShows().get(0);
                        Seat reserved_seat = null;
                        String seat_id = "";
                        try {
                            System.out.println("Select Seat : ");
                            seat_id = sc.nextLine();
                        } catch (Exception e) {
                            System.out.println("Invalid seat entered. try again");

                        }

                        //check if reservation for that seat has been made by date and time other wise create reservation
                        Reservation re = new Reservation(userid, show.getShowName(), reservation_time, reservation_date, seat_id);
                        if (!verifyReservation(re)) {
                            if (!isValidSeatId(seat_id)) {
                                System.out.println("Invalid seat number. try again");
                                i--;
                            } else {
                                if (reservationsExist(reservations, re)) {
                                    System.out.println("This seat has already been booked by you.");
                                    i--;
                                } else {
                                    reservations.add(re);
                                    System.out.println("seat " + seat_id + " has been booked");
                                }

                            }

                        } else {
                            System.out.println("This seat has already been booked.");
                            i--;
                        }
                        System.out.println("Press any key to continue...");
                    }
                    Booking bookings_new = new Booking(new_id, reservations);

                    bookings.add(bookings_new);
                    writeout();

                    break;
                case 'V':
                case 'v':
                    LAST_ENTRY_POINT = 'v';
                    System.out.println("[1] View All Booking\n[2] View Bookings by Date\nSelect Section ID : ");
                    String choice = sc.nextLine();
                    if (choice.equalsIgnoreCase("1")) {
                        for (Booking booking_ : bookings) {
                            if (booking_.getUserID().equalsIgnoreCase(userid)) {
                                System.out.println("Conformation number:" + booking_.getBooking_id());
                                booking_.printReservation();
                            }
                        }

                    } else  if (choice.equalsIgnoreCase("2")) {
                        try {
                            System.out.println("Enter the reservation date of the show(yyyy-MM-dd) : ");
                            String date1 = sc.nextLine();
                            LocalDate reservation_date1 = LocalDate.parse(date1);

                            for (Booking booking_ : bookings) {
                                if (booking_.getUserID().equalsIgnoreCase(userid) && booking_.getReservations().get(0).getStringDate().equals(reservation_date1)) {
                                    System.out.println("Conformation number:" + booking_.getBooking_id());
                                    booking_.printReservation();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error occured");
                        }

                    }else{
                        System.out.println("Invalid choice");
                    }

                    break;
                case 'C':
                case 'c':
                    LAST_ENTRY_POINT = ' ';
                    for(Booking booking_ : bookings) {
                        System.out.println("Your conformation number are " + booking_.getBooking_id());
                    }
                    System.out.println("Please enter your conformation id");
                    int id = sc.nextInt();
                    try {
                        Booking booking = findBookingById(id);
                        if (booking == null) {
                            break;
                        }

                        if (booking.getUserID().equals(userid)) {
                            booking.printReservation();
                            sc.nextLine();
                            System.out.println("Please enter seat number to remove");
                            String seatNumber = sc.nextLine();
                            booking.deleteReservationBySeatNumber(seatNumber);

                            bookings.set(getIndex(id), booking);
                        }
                        writeout();
                    } catch (Exception e) {
                    }
                    break;
                case 'O':
                case 'o':
//                    for(Booking booking_ : bookings) {
//                        System.out.println("Your conformation number are " + booking_.getBooking_id());
//                        //booking_.printReservationReceipt();
//                    }
                    System.out.println("Please enter your conformation number");
                    id = sc.nextInt();
                    try {
                        Booking booking = findBookingById(id);
                        if (booking == null) {
                            break;
                        }
                        sc.nextLine();
                        if (booking.getUserID().equals(userid)) {
                            System.out.println("Your conformation number is "+id);
                            booking.printReservationReceipt();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 'L':
                case 'l':
                    menu1();
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    private static boolean validateDate(LocalDate reservation_date) {
        if (reservation_date.isAfter(LocalDate.parse("2020-12-22")) && reservation_date.isBefore(LocalDate.parse("2021-01-03"))) {
            return true;
        }
        return false;
    }

    private static boolean validateTime(LocalTime reservation_time) {
        LocalTime sixThirty = LocalTime.parse("06:30");
        LocalTime eightThirty = LocalTime.parse("08:30");

        if (reservation_time.equals(sixThirty) || reservation_time.equals(eightThirty)) {
            return true;
        }
        return false;
    }

    private static boolean printSeatPlan(Show show, String section_id, Reservation reservation, ArrayList<Reservation> reservations) {
        boolean valid_section_id = false;
        String text = "";
        int c = 0;
        int index = 0;
        for (Seat seat : show.getSeats()) {

            if (c % 7 == 0) {
//                System.out.println("");
                text += "\n";
            }
            if (seat.getSection_id().equalsIgnoreCase(section_id)) {
                valid_section_id = true;
                String seat_id = seat.getSeat_id();
                reservation.setSeatNumber(seat_id);
                if (verifyReservation(reservation, reservations)) {
                    seat_id = "XXX";
                }
//                System.out.print(seat.getSeat_id()+ " | ");
                text += seat_id + " | ";
                c++;
            }
            index++;
        }
        System.out.println(text.trim());
        return valid_section_id;
    }

    static void writeout() {
        try {
            FileWriter writer = new FileWriter("Reservations.txt"); //
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (Booking booking : bookings) {
                if (booking.getReservations().isEmpty()) {
                    continue;
                }
                for (Reservation reserved : booking.getReservations()) {
                    bufferedWriter.write(booking.getBooking_id() + ",");
                    bufferedWriter.write(reserved.getUserid() + ",");
                    bufferedWriter.write(reserved.getShowTitle() + ",");
                    bufferedWriter.write(reserved.getStringTime().toString() + ",");
                    bufferedWriter.write(reserved.getStringDate().toString() + ",");
                    bufferedWriter.write(reserved.getSeatNumber() + "\n");
                }
            }
            bufferedWriter.close();
        } catch (Exception e) {
        }
    }

    private static Booking findBookingById(int id) {
        for (Booking booking : bookings) {
            if (booking.getBooking_id() == id) {
                return booking;
            }
        }
        return null;
    }

    private static int getIndex(int id) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBooking_id() == id) {
                return i;
            }
        }
        return 0;
    }

    private static boolean verifyReservation(Reservation reserve) {

        for (Booking booking : bookings) {
            for (Reservation r : booking.getReservations()) {
                if (r.getSeatNumber().equalsIgnoreCase(reserve.getSeatNumber()) && r.getStringDate().equals(reserve.getStringDate()) && r.getStringTime().equals(reserve.getStringTime())) {
                    //this seat has been booked
                    return true;
                }
            }
        }
        //this seat has not been booked
        return false;
    }

    private static boolean verifyReservation(Reservation reserve, ArrayList<Reservation> reservations) {
        ArrayList<Reservation> temp = new ArrayList<>();
        for (Reservation reservation : reservations) {
            temp.add(reservation);
        }

        for (Booking booking : bookings) {
            for (Reservation r : booking.getReservations()) {
                temp.add(r);

            }
        }

        for (Reservation r : temp) {
            if (r.getSeatNumber().equalsIgnoreCase(reserve.getSeatNumber()) && r.getStringDate().equals(reserve.getStringDate()) && r.getStringTime().equals(reserve.getStringTime())) {
                //this seat has been booked
                return true;
            }
        }
        //this seat has not been booked
        return false;
    }


    private static boolean isValidSeatId(String seatNumber) {
        for (Seat s : theatre.getShows().get(0).getSeats()) {
            if (s.getSeat_id().equalsIgnoreCase(seatNumber)) {
                return true;
            }
        }
        return false;
    }

    private static boolean reservationsExist(ArrayList<Reservation> reservations, Reservation re) {
        for (Reservation reservation : reservations) {
            if (reservation.getSeatNumber().equalsIgnoreCase(re.getSeatNumber()) && reservation.getStringDate().equals(re.getStringDate())
                    && reservation.getStringTime().equals(re.getStringTime())) {
                return true;
            }
        }
        return false;
    }

}
