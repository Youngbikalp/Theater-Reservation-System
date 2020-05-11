package com.company;

import java.util.ArrayList;

public class Theatre {
    private String name;
    private ArrayList<Show> shows ;

    public Theatre(String name, ArrayList<Show> shows) {
        this.name = name;
        this.shows = shows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Show> getShows() {
        return shows;
    }

    public void setShows(ArrayList<Show> shows) {
        this.shows = shows;
    }
}
