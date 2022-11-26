package weblogic.management.configuration;

import java.util.Arrays;
import javax.management.Attribute;
import javax.management.InvalidAttributeValueException;
import weblogic.utils.ArrayUtils;

public class LegalChecks {
   private static final ManagementConfigValidatorsTextFormatter textFormatter = ManagementConfigValidatorsTextFormatter.getInstance();

   public static String checkLegalStringSet(Attribute attribute, String[] set) throws InvalidAttributeValueException {
      String value = (String)attribute.getValue();

      for(int i = 0; i < set.length; ++i) {
         if (value.equalsIgnoreCase(set[i])) {
            return set[i];
         }
      }

      throw new InvalidAttributeValueException(textFormatter.getcheckLegalStringSetString(value, attribute.getName().toString(), Arrays.asList((Object[])set).toString()));
   }

   public static void checkLegalIntSet(Attribute attribute, int[] set) throws InvalidAttributeValueException {
      int value = ((Number)attribute.getValue()).intValue();

      for(int i = 0; i < set.length; ++i) {
         if (value == set[i]) {
            return;
         }
      }

      throw new InvalidAttributeValueException(textFormatter.getcheckLegalStringSetString(Integer.toString(value), attribute.getName().toString(), ArrayUtils.toString(set)));
   }

   public static void checkLegalRange(Attribute attribute, long min, long max) throws InvalidAttributeValueException {
      long value = ((Number)attribute.getValue()).longValue();
      if (value < min || value > max) {
         throw new InvalidAttributeValueException(textFormatter.getVoidcheckLegalRangeString(Long.toString(value), attribute.getName().toString(), Long.toString(min), Long.toString(max)));
      }
   }

   public static void checkNonNull(Attribute attribute) throws InvalidAttributeValueException {
      if (attribute.getValue() == null) {
         throw new InvalidAttributeValueException(textFormatter.getVoidCheckNonNullString(attribute.getName().toString()));
      }
   }

   public static void checkNonEmptyString(Attribute attribute) throws InvalidAttributeValueException {
      Object value = attribute.getValue();
      if (!(value instanceof String)) {
         throw new InvalidAttributeValueException(textFormatter.getVoidCheckNonEmptyStringString(attribute.getName().toString(), value.getClass().getName().toString()));
      }
   }
}
