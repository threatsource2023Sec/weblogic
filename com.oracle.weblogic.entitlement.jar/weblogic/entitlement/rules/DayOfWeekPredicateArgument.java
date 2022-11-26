package weblogic.entitlement.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import weblogic.security.providers.authorization.EnumeratedPredicateArgument;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;

public class DayOfWeekPredicateArgument extends BasePredicateArgument implements EnumeratedPredicateArgument {
   private static final List DAYS;

   public DayOfWeekPredicateArgument() {
      this("DayOfWeekPredicateArgumentName", "DayOfWeekPredicateArgumentDescription", DayOfWeek.MONDAY);
   }

   public DayOfWeekPredicateArgument(String displayNameId, String descriptionId, DayOfWeek defaultValue) {
      super(displayNameId, descriptionId, DayOfWeek.class, defaultValue);
   }

   public List getAllowedValues() {
      return DAYS;
   }

   public Object parseValue(String value, Locale locale) throws IllegalPredicateArgumentException {
      for(int i = 0; i < DAYS.size(); ++i) {
         DayOfWeek day = (DayOfWeek)DAYS.get(i);
         if (day.getLocalizedName(locale).equals(value)) {
            return day;
         }
      }

      String errorMsg = (new PredicateTextFormatter(locale)).getInvalidDayOfWeekMessage(value);
      throw new IllegalPredicateArgumentException(errorMsg);
   }

   public String formatValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      this.validateValue(value, locale);
      return ((DayOfWeek)value).getLocalizedName(locale);
   }

   public Object parseExprValue(String value) throws IllegalPredicateArgumentException {
      for(int i = 0; i < DAYS.size(); ++i) {
         DayOfWeek day = (DayOfWeek)DAYS.get(i);
         if (day.getName().equals(value)) {
            return day;
         }
      }

      throw new IllegalPredicateArgumentException("Unknown day of the week " + value);
   }

   public String formatExprValue(Object value) throws IllegalPredicateArgumentException {
      this.validateValue(value, (Locale)null);
      return ((DayOfWeek)value).getName();
   }

   public static void main(String[] args) throws Exception {
      test(new DayOfWeekPredicateArgument(), args[0]);
   }

   static {
      List days = new ArrayList(7);
      days.add(DayOfWeek.SUNDAY);
      days.add(DayOfWeek.MONDAY);
      days.add(DayOfWeek.TUESDAY);
      days.add(DayOfWeek.WEDNESDAY);
      days.add(DayOfWeek.THURSDAY);
      days.add(DayOfWeek.FRIDAY);
      days.add(DayOfWeek.SATURDAY);
      DAYS = Collections.unmodifiableList(days);
   }
}
