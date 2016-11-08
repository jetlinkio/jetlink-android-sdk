package com.veslabs.jetlinklibrary.messaging;

import android.content.Context;

import com.veslabs.jetlinklibrary.R;

import java.text.SimpleDateFormat;

/**
 * Created by Burhan Aras on 10/31/2016.
 */
public class DateUtil {
    private static final String TAG = DateUtil.class.getSimpleName();
    private static final long ONE_SECOND =  1000;
    private static final long ONE_MINUTE = 60 * ONE_SECOND;
    private static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final long ONE_WEEK = 7 * ONE_DAY;

    public static String convertDateToVisibleStringOnChatScreen(Context context, long date) {

        long difference = System.currentTimeMillis() - date;

        if (difference < ONE_MINUTE) {
            return context.getString(R.string.duration_just_now);
        } else if (difference < ONE_HOUR) {
            difference = difference / ONE_MINUTE;
            return String.format(context.getString(R.string.duration_minute_ago), difference);
        } else if (difference < ONE_DAY) {
            difference = difference / ONE_HOUR;
            return String.format(context.getString(R.string.duration_hours_ago), difference);
        } else if (difference < ONE_WEEK) {
            difference = difference / ONE_DAY;
            return String.format(context.getString(R.string.duration_days_ago), difference);
        } else {
            String pattern = "dd/MM/yyyy HH:mm";
            return new SimpleDateFormat(pattern).format(date);
        }


    }
}
