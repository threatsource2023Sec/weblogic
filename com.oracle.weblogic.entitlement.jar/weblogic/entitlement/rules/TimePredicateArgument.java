package weblogic.entitlement.rules;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;

public class TimePredicateArgument extends BasePredicateArgument {
   public static final String TimePredicateArgumentName = "TimePredicateArgumentName";
   public static final String TimePredicateStartTimeArgumentName = "TimePredicateStartTimeArgumentName";
   public static final String TimePredicateEndTimeArgumentName = "TimePredicateEndTimeArgumentName";

   public TimePredicateArgument() {
      this("TimePredicateArgumentName", "TimePredicateArgumentDescription", (TimeOfDay)null);
   }

   public TimePredicateArgument(String displayNameId, String descriptionId, TimeOfDay defaultValue) {
      super(displayNameId, descriptionId, TimeOfDay.class, defaultValue);
   }

   public Object parseValue(String value, Locale locale) throws IllegalPredicateArgumentException {
      DateFormat df = DateFormat.getTimeInstance(2, locale);
      df.setTimeZone(TimeZone.getTimeZone("GMT"));
      df.setLenient(false);

      try {
         Date time = df.parse(value);
         return new TimeOfDay((int)time.getTime());
      } catch (Exception var5) {
         throw new IllegalPredicateArgumentException(var5.getMessage());
      }
   }

   public String formatValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      this.validateValue(value, locale);
      DateFormat df = DateFormat.getTimeInstance(2);
      df.setTimeZone(TimeZone.getTimeZone("GMT"));
      return df.format(new Date((long)((TimeOfDay)value).getTime()));
   }

   public Object parseExprValue(String value) throws IllegalPredicateArgumentException {
      int hr = 0;
      int min = 0;
      int sec = 0;
      StringTokenizer tokens = new StringTokenizer(value, ":", false);
      if (tokens.hasMoreTokens()) {
         hr = Integer.parseInt(tokens.nextToken().trim());
         if (tokens.hasMoreTokens()) {
            min = Integer.parseInt(tokens.nextToken().trim());
            if (tokens.hasMoreTokens()) {
               sec = Integer.parseInt(tokens.nextToken().trim());
               if (tokens.hasMoreTokens()) {
                  throw new IllegalPredicateArgumentException("Unexpected time format");
               }
            }
         }
      }

      try {
         return new TimeOfDay(hr, min, sec);
      } catch (IllegalArgumentException var7) {
         throw new IllegalPredicateArgumentException(var7.getMessage());
      }
   }

   public String formatExprValue(Object value) throws IllegalPredicateArgumentException {
      this.validateValue(value, (Locale)null);
      TimeOfDay val = (TimeOfDay)value;
      return val.getHours() + ":" + val.getMinutes() + ":" + val.getSeconds();
   }

   public static void main(String[] args) throws Exception {
      test(new TimePredicateArgument(), args[0]);
   }

   public String getDescription(Locale locale) {
      if (this.displayNameId.equals("TimePredicateArgumentName")) {
         return this.getDescription(Localizer.getText("TimePredicateArgumentDescription", locale), locale);
      } else {
         return this.displayNameId.equals("TimePredicateStartTimeArgumentName") ? this.getDescription(Localizer.getText("TimePredicateStartTimeArgumentDescription", locale), locale) : this.getDescription(Localizer.getText("TimePredicateEndTimeArgumentDescription", locale), locale);
      }
   }

   public String getDescription(String message, Locale locale) {
      ArrayList msgArrayList = new ArrayList();
      String timePattern = this.getFormatedTimePattern(locale, 2);
      msgArrayList.add(timePattern);
      GregorianCalendar gc1 = new GregorianCalendar(locale);
      gc1.set(10, 20);
      gc1.set(12, 45);
      gc1.set(13, 35);
      gc1.set(9, 0);
      Date myDate1 = new Date(gc1.getTimeInMillis());
      msgArrayList.add(this.getFormatedDateString(this.getTimePattern(locale), locale, myDate1));
      return this.parseMessage(new StringBuffer(message), msgArrayList);
   }
}
