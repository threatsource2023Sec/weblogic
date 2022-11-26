package weblogic.entitlement.rules;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;

public class NumberPredicateArgument extends BasePredicateArgument {
   private boolean parseIntegerOnly;

   public NumberPredicateArgument() {
      this("NumberPredicateArgumentName", "NumberPredicateArgumentDescription", (Number)null);
   }

   public NumberPredicateArgument(String displayNameId, String descriptionId, Number defaultValue) {
      super(displayNameId, descriptionId, Number.class, defaultValue);
      this.parseIntegerOnly = false;
   }

   protected NumberPredicateArgument(String displayNameId, String descriptionId, Class type, Number defaultValue, boolean parseIntegerOnly) {
      super(displayNameId, descriptionId, type, defaultValue);
      this.parseIntegerOnly = false;
      this.parseIntegerOnly = parseIntegerOnly;
   }

   public Object parseValue(String value, Locale locale) throws IllegalPredicateArgumentException {
      ParsePosition pp = new ParsePosition(0);
      NumberFormat nf = NumberFormat.getNumberInstance(locale);
      nf.setParseIntegerOnly(this.parseIntegerOnly);
      Number num = nf.parse(value, pp);
      if (num != null && pp.getIndex() >= value.length()) {
         return num;
      } else {
         String msg = Localizer.getText(this.parseIntegerOnly ? "NotIntegerValue" : "NotNumericValue", locale);
         throw new IllegalPredicateArgumentException(msg);
      }
   }

   public String formatValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      super.validateValue(value, locale);
      Number numValue = (Number)value;
      NumberFormat nf = NumberFormat.getInstance(locale);
      return this.parseIntegerOnly ? nf.format(numValue.longValue()) : nf.format(numValue.doubleValue());
   }
}
