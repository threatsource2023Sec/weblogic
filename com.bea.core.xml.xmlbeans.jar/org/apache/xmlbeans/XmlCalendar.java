package org.apache.xmlbeans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class XmlCalendar extends GregorianCalendar {
   private static int defaultYear = Integer.MIN_VALUE;
   private static final int DEFAULT_DEFAULT_YEAR = 0;
   private static Date _beginningOfTime = new Date(Long.MIN_VALUE);

   public XmlCalendar(String xmlSchemaDateString) {
      this((GDateSpecification)(new GDate(xmlSchemaDateString)));
   }

   public XmlCalendar(GDateSpecification date) {
      this(GDate.timeZoneForGDate(date), date);
   }

   private XmlCalendar(TimeZone tz, GDateSpecification date) {
      super(tz);
      this.setGregorianChange(_beginningOfTime);
      this.clear();
      if (date.hasYear()) {
         int y = date.getYear();
         if (y > 0) {
            this.set(0, 1);
         } else {
            this.set(0, 0);
            y = -y;
         }

         this.set(1, y);
      }

      if (date.hasMonth()) {
         this.set(2, date.getMonth() - 1);
      }

      if (date.hasDay()) {
         this.set(5, date.getDay());
      }

      if (date.hasTime()) {
         this.set(11, date.getHour());
         this.set(12, date.getMinute());
         this.set(13, date.getSecond());
         if (date.getFraction().scale() > 0) {
            this.set(14, date.getMillisecond());
         }
      }

      if (date.hasTimeZone()) {
         this.set(15, date.getTimeZoneSign() * 1000 * 60 * (date.getTimeZoneHour() * 60 + date.getTimeZoneMinute()));
         this.set(16, 0);
      }

   }

   public XmlCalendar(Date date) {
      this(TimeZone.getDefault(), new GDate(date));
      this.complete();
   }

   public XmlCalendar(int year, int month, int day, int hour, int minute, int second, BigDecimal fraction) {
      this(TimeZone.getDefault(), new GDate(year, month, day, hour, minute, second, fraction));
   }

   public XmlCalendar(int year, int month, int day, int hour, int minute, int second, BigDecimal fraction, int tzSign, int tzHour, int tzMinute) {
      this((GDateSpecification)(new GDate(year, month, day, hour, minute, second, fraction, tzSign, tzHour, tzMinute)));
   }

   public int get(int field) {
      return this.isSet(field) && !this.isTimeSet ? this.internalGet(field) : super.get(field);
   }

   public XmlCalendar() {
      this.setGregorianChange(_beginningOfTime);
      this.clear();
   }

   public static int getDefaultYear() {
      if (defaultYear == Integer.MIN_VALUE) {
         try {
            String yearstring = SystemProperties.getProperty("user.defaultyear");
            if (yearstring != null) {
               defaultYear = Integer.parseInt(yearstring);
            } else {
               defaultYear = 0;
            }
         } catch (Throwable var1) {
            defaultYear = 0;
         }
      }

      return defaultYear;
   }

   public static void setDefaultYear(int year) {
      defaultYear = year;
   }

   protected void computeTime() {
      boolean unsetYear = !this.isSet(1);
      if (unsetYear) {
         this.set(1, getDefaultYear());
      }

      try {
         super.computeTime();
      } finally {
         if (unsetYear) {
            this.clear(1);
         }

      }

   }

   public String toString() {
      return (new GDate(this)).toString();
   }
}
