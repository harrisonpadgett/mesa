package com.example.mesaapp.calendarparts;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }


    private String name;
    private LocalDate date;
    private String time;
    private String notifyCheck;

    public Event(String name, LocalDate date, String time, String notifyCheck)
    {
        this.name = name;
        this.date = date;
        this.time = time;
        this.notifyCheck = notifyCheck;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public String getNotifyCheck() {return notifyCheck;}

    public void setNotifyCheck(String notif) {this.notifyCheck = notif;}

}
