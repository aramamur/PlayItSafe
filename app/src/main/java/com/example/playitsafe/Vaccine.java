package com.example.playitsafe;

public class Vaccine {
    public String vacname;
    public String gender;
    public String race;
    public String age_range;
    public String side_effects;
    public String covid_after;
    public String comments;

    public Vaccine(String vacname, String gender, String race, String age_range, String side_effects, String covid_after, String comments) {
        this.vacname = vacname;
        this.gender = gender;
        this.race = race;
        this.age_range = age_range;
        this.side_effects = side_effects;
        this.covid_after = covid_after;
        this.comments = comments;
    }

}
