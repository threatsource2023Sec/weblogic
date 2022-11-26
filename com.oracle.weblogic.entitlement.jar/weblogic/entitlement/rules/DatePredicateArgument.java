package weblogic.entitlement.rules;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;

public class DatePredicateArgument extends BasePredicateArgument {
   private long defaultTime;

   public DatePredicateArgument(long defaultTime) {
      this("DatePredicateArgumentName", "DatePredicateArgumentDescription", new Date(), defaultTime);
   }

   public DatePredicateArgument(String displayNameId, String descriptionId, Date defaultDate, long defaultTime) {
      super(displayNameId, descriptionId, Date.class, defaultDate);
      this.defaultTime = 0L;
      long msPerDay = 86400000L;
      if (defaultTime >= 0L && defaultTime <= msPerDay) {
         this.defaultTime = defaultTime;
      } else {
         throw new IllegalArgumentException("Default time parameter must be > 0 and < " + msPerDay);
      }
   }

   public Object parseValue(String value, Locale locale) throws IllegalPredicateArgumentException {
      try {
         return this.parseValueOrig(value, locale);
      } catch (IllegalPredicateArgumentException var6) {
         try {
            DateFormat df = DateFormat.getDateTimeInstance(3, 2, locale);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            return df.parse(value);
         } catch (ParseException var5) {
            throw var6;
         }
      }
   }

   private Object parseValueOrig(String value, Locale locale) throws IllegalPredicateArgumentException {
      TimeZone tz = TimeZone.getTimeZone("GMT");
      DateFormat df = DateFormat.getDateInstance(3, locale);
      df.setLenient(false);
      df.setTimeZone(tz);
      ParsePosition p = new ParsePosition(0);
      Date date = df.parse(value, p);
      if (date == null) {
         String errorMsg = Localizer.getText("InvalidDateFormat", locale);
         throw new IllegalPredicateArgumentException(errorMsg);
      } else {
         long time;
         if (value.substring(p.getIndex()).trim().length() > 0) {
            DateFormat tf = DateFormat.getTimeInstance(2, locale);
            tf.setLenient(false);
            tf.setTimeZone(tz);
            Date timeDate = tf.parse(value, p);
            if (timeDate == null) {
               String errorMsg = Localizer.getText("InvalidTimeFormat", locale);
               throw new IllegalPredicateArgumentException(errorMsg);
            }

            time = timeDate.getTime();
         } else {
            time = this.defaultTime;
         }

         return new Date(date.getTime() + time);
      }
   }

   public String formatValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      this.validateValue(value, locale);
      DateFormat df = DateFormat.getDateTimeInstance(3, 2, locale);
      df.setTimeZone(TimeZone.getTimeZone("GMT"));
      return df.format((Date)value);
   }

   public Object parseExprValue(String value) throws IllegalPredicateArgumentException {
      SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      df.setTimeZone(TimeZone.getTimeZone("GMT"));
      df.setLenient(false);

      try {
         return df.parse(value);
      } catch (ParseException var4) {
         throw new IllegalPredicateArgumentException(var4.getMessage());
      }
   }

   public String formatExprValue(Object value) throws IllegalPredicateArgumentException {
      this.validateValue(value, (Locale)null);
      SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      df.setTimeZone(TimeZone.getTimeZone("GMT"));
      return df.format((Date)value);
   }

   public static void main(String[] args) throws Exception {
      test(new DatePredicateArgument(0L), args[0]);
   }

   public String getDescription(Locale locale) {
      ArrayList msgArrayList = new ArrayList();
      String datePattern = this.getDatePattern(locale);
      msgArrayList.add(datePattern);
      GregorianCalendar gc = new GregorianCalendar(locale);
      gc.set(1, 2006);
      gc.set(2, 3);
      gc.set(5, 25);
      Date myDate = new Date(gc.getTimeInMillis());
      msgArrayList.add(this.getFormatedDateString(datePattern, locale, myDate));
      msgArrayList.add(this.getFormatedDateTimePattern(locale));
      GregorianCalendar gc1 = new GregorianCalendar(locale);
      gc1.set(1, 2006);
      gc1.set(2, 3);
      gc1.set(5, 25);
      gc1.set(10, 20);
      gc1.set(12, 45);
      gc1.set(13, 35);
      gc1.set(9, 0);
      Date myDate1 = new Date(gc1.getTimeInMillis());
      msgArrayList.add(this.getFormatedDateString(this.getDateTimePattern(locale), locale, myDate1));
      StringBuffer sbDatePredicateArgumentDescription = new StringBuffer(Localizer.getText("DatePredicateArgumentDescription", locale));
      return this.parseMessage(sbDatePredicateArgumentDescription, msgArrayList);
   }
}
