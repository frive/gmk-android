package com.gmk.gmkandroid.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtils {
  static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  public static String nowToISOString() {
    Calendar calendar = GregorianCalendar.getInstance();

    return dateFormatter.format(calendar.getTime());
  }
}
