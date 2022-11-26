package weblogic.entitlement.rules;

import java.util.Locale;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.RangePredicateArgument;

public class DayOfMonthPredicateArgument extends NumberPredicateArgument implements RangePredicateArgument {
   private static final Integer MAX_VALUE = new Integer(31);
   private static final Integer MIN_VALUE = new Integer(-31);

   public DayOfMonthPredicateArgument() {
      this("DayOfMonthPredicateArgumentName", "DayOfMonthPredicateArgumentDescription", 1);
   }

   public DayOfMonthPredicateArgument(String displayNameId, String descriptionId, int defaultValue) {
      super(displayNameId, descriptionId, Integer.class, new Integer(defaultValue), true);
   }

   public Comparable getMinValue() {
      return MIN_VALUE;
   }

   public Comparable getMaxValue() {
      return MAX_VALUE;
   }

   public void validateValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      super.validateValue(value, locale);
      int dayOfMonth = (Integer)value;
      if (dayOfMonth < MIN_VALUE || dayOfMonth > MAX_VALUE) {
         String errorMsg = Localizer.getText("InvalidDayOfMonth", locale);
         throw new IllegalPredicateArgumentException(errorMsg);
      }
   }

   public Object parseValue(String value, Locale locale) throws IllegalPredicateArgumentException {
      Number numValue = (Number)super.parseValue(value, locale);
      Number numValue = new Integer(numValue.intValue());
      this.validateValue(numValue, locale);
      return numValue;
   }

   public String formatValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      this.validateValue(value, locale);
      return super.formatValue(value, locale);
   }

   public static void main(String[] args) throws Exception {
      test(new DayOfMonthPredicateArgument(), args[0]);
   }
}
