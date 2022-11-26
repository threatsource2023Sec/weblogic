package weblogic.xml.saaj.mime4j.field.datetime;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import weblogic.xml.saaj.mime4j.field.datetime.parser.DateTimeParser;
import weblogic.xml.saaj.mime4j.field.datetime.parser.ParseException;
import weblogic.xml.saaj.mime4j.field.datetime.parser.TokenMgrError;

public class DateTime {
   private final Date date;
   private final int year;
   private final int month;
   private final int day;
   private final int hour;
   private final int minute;
   private final int second;
   private final int timeZone;

   public DateTime(String yearString, int month, int day, int hour, int minute, int second, int timeZone) {
      this.year = this.convertToYear(yearString);
      this.date = convertToDate(this.year, month, day, hour, minute, second, timeZone);
      this.month = month;
      this.day = day;
      this.hour = hour;
      this.minute = minute;
      this.second = second;
      this.timeZone = timeZone;
   }

   private int convertToYear(String yearString) {
      int year = Integer.parseInt(yearString);
      switch (yearString.length()) {
         case 1:
         case 2:
            if (year >= 0 && year < 50) {
               return 2000 + year;
            }

            return 1900 + year;
         case 3:
            return 1900 + year;
         default:
            return year;
      }
   }

   public static Date convertToDate(int year, int month, int day, int hour, int minute, int second, int timeZone) {
      Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+0"));
      c.set(year, month - 1, day, hour, minute, second);
      c.set(14, 0);
      if (timeZone != Integer.MIN_VALUE) {
         int minutes = timeZone / 100 * 60 + timeZone % 100;
         c.add(12, -1 * minutes);
      }

      return c.getTime();
   }

   public Date getDate() {
      return this.date;
   }

   public int getYear() {
      return this.year;
   }

   public int getMonth() {
      return this.month;
   }

   public int getDay() {
      return this.day;
   }

   public int getHour() {
      return this.hour;
   }

   public int getMinute() {
      return this.minute;
   }

   public int getSecond() {
      return this.second;
   }

   public int getTimeZone() {
      return this.timeZone;
   }

   public void print() {
      System.out.println(this.getYear() + " " + this.getMonth() + " " + this.getDay() + "; " + this.getHour() + " " + this.getMinute() + " " + this.getSecond() + " " + this.getTimeZone());
   }

   public static DateTime parse(String dateString) throws ParseException {
      try {
         return (new DateTimeParser(new StringReader(dateString))).parseAll();
      } catch (TokenMgrError var2) {
         throw new ParseException(var2.getMessage());
      }
   }
}
