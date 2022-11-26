package weblogic.entitlement.rules;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;

public class TimeZonePredicateArgument extends BasePredicateArgument {
   public TimeZonePredicateArgument() {
      this("TimeZonePredicateArgumentName", "TimeZonePredicateArgumentDescription", (String)null, true);
   }

   public TimeZonePredicateArgument(String displayNameId, String descriptionId, String defaultValue, boolean optional) {
      super(displayNameId, descriptionId, TimeZone.class, defaultValue, optional);
   }

   public Object parseValue(String value, Locale locale) throws IllegalPredicateArgumentException {
      TimeZone tz = TimeZone.getTimeZone(value);
      if (tz.getRawOffset() == 0) {
         if (value == null || !value.startsWith("GMT") && !value.startsWith("UTC") && !value.startsWith("WET")) {
            String errorMsg = Localizer.getText("InvalidTimeZoneFormat", locale);
            throw new IllegalPredicateArgumentException(errorMsg);
         }

         if (value.length() > 3) {
            char sign = value.charAt(3);
            if (sign != '-' && sign != '+') {
               String errorMsg = Localizer.getText("InvalidTimeZoneFormat", locale);
               throw new IllegalPredicateArgumentException(errorMsg);
            }

            int count = 0;

            for(int i = 4; i < value.length(); ++i) {
               char c = value.charAt(i);
               String errorMsg;
               if (c != '0' && c != ':') {
                  errorMsg = Localizer.getText("InvalidTimeZoneFormat", locale);
                  throw new IllegalPredicateArgumentException(errorMsg);
               }

               if (c == ':') {
                  ++count;
                  if (count > 1) {
                     errorMsg = Localizer.getText("InvalidTimeZoneFormat", locale);
                     throw new IllegalPredicateArgumentException(errorMsg);
                  }
               }
            }
         }
      }

      return tz;
   }

   public String formatValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      super.validateValue(value, locale);
      TimeZone tz = (TimeZone)value;
      return tz.getDisplayName(false, 0, locale);
   }

   public static void main(String[] args) throws Exception {
      test(new TimeZonePredicateArgument(), args[0]);
   }

   public String getDescription(Locale locale) {
      return this.getDescription(Localizer.getText("TimeZonePredicateArgumentDescription", locale), Locale.US);
   }

   public String getDescription(String message, Locale locale) {
      ArrayList msgArrayList = new ArrayList();
      String timePattern = this.getFormatedTimePattern(locale);
      msgArrayList.add(timePattern);
      msgArrayList.add(timePattern);
      GregorianCalendar gc1 = new GregorianCalendar(locale);
      gc1.set(10, 5);
      gc1.set(12, 0);
      Date myDate1 = new Date(gc1.getTimeInMillis());
      msgArrayList.add(this.getFormatedDateString(timePattern, locale, myDate1));
      return this.parseMessage(new StringBuffer(message), msgArrayList);
   }

   public String getFormatedTimePattern(Locale locale) {
      DateFormat fmt = DateFormat.getTimeInstance(3, locale);
      SimpleDateFormat sdf = (SimpleDateFormat)fmt;
      String pattern = sdf.toPattern();
      StringBuffer sb = new StringBuffer(sdf.toPattern());
      int ichar = sb.indexOf("a");
      if (ichar > 0) {
         sb = sb.delete(ichar - 1, ichar + 1);
      }

      return sb.toString();
   }
}
