package com.google.android.gms.nearby.messages.samples.nearbydevices;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalendarContentResolver {


    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Events.TITLE               // 3
    };

    public static final String[] CALENDAR_PROJECTION = new String[] {
            CalendarContract.Calendars.ACCOUNT_NAME               // 3
    };

    // The indices for the projection array above.
    private static final int TITLE = 0;

    private static final int OWNER = 0;

    public static final Uri EVENTS_URI = Uri.parse("content://com.android.calendar/events");
    public static final Uri CALENDARS_URI = Uri.parse("content://com.android.calendar/calendars");

    ContentResolver contentResolver;
    List<String> events = new ArrayList<>();

    public CalendarContentResolver(Context ctx) {
        contentResolver = ctx.getContentResolver();
    }

    public List<String> getEvents() {


        Cursor cur;
        ContentResolver cr = contentResolver;
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        long startDay = calendar.getTimeInMillis();
        calendar.set(calendar.get(calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+1, 0, 0, 0);
        long endDay = calendar.getTimeInMillis();

        String selection = CalendarContract.Events.DTSTART + " >= ? AND " + CalendarContract.Events.DTSTART + "<= ?";
        String[] selectionArgs = new String[] { Long.toString(startDay), Long.toString(endDay) };
        cur = cr.query(EVENTS_URI, EVENT_PROJECTION, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            events.add(cur.getString(TITLE));
        }
        return events;
    }


    public String GetCalendarOwner() {
        Cursor cur;
        ContentResolver cr = contentResolver;
        cur = cr.query(CALENDARS_URI, CALENDAR_PROJECTION, null, null, null);

        cur.moveToFirst();
        if (cur.getString(OWNER) != "")  return cur.getString(OWNER);
        else return "Accept";

    }
}
