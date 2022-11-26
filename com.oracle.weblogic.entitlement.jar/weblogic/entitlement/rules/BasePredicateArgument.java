package weblogic.entitlement.rules;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;

public abstract class BasePredicateArgument implements PredicateArgument {
   protected String displayNameId;
   private String descriptionId;
   private Class type;
   private Object defaultValue;
   private boolean optional;
   public static final int CALENDAR_YEAR = 2006;
   public static final int CALENDAR_MONTH = 3;
   public static final int CALENDAR_DAY_OF_MONTH = 25;
   public static final int CALENDAR_HOUR = 20;
   public static final int CALENDAR_MINUTE = 45;
   public static final int CALENDAR_SECOND = 35;
   public static final int EST_HOUR_OFFSET = 5;

   public BasePredicateArgument(String displayNameId, String descriptionId, Class type, Object defaultValue) {
      this(displayNameId, descriptionId, type, defaultValue, false);
   }

   public BasePredicateArgument(String displayNameId, String descriptionId, Class type, Object defaultValue, boolean optional) {
      this.displayNameId = displayNameId;
      this.descriptionId = descriptionId;
      this.type = type;
      this.defaultValue = defaultValue;
      this.optional = optional;
   }

   public String getType() {
      return this.type.getName();
   }

   public String getDisplayName(Locale locale) {
      return Localizer.getText(this.displayNameId, locale);
   }

   public String getDescription(Locale locale) {
      return Localizer.getText(this.descriptionId, locale);
   }

   public Object getDefaultValue() {
      return this.defaultValue;
   }

   public boolean isOptional() {
      return this.optional;
   }

   public void validateValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      if (value == null) {
         throw new IllegalPredicateArgumentException(Localizer.getText("NullArgumentValue", locale));
      } else if (!this.type.isAssignableFrom(value.getClass())) {
         String errorMsg = (new PredicateTextFormatter(locale)).getInvalidArgumentTypeMessage(this.getType());
         throw new IllegalPredicateArgumentException(errorMsg);
      }
   }

   public Object parseValue(String value, Locale locale) throws IllegalPredicateArgumentException {
      return value;
   }

   public String formatValue(Object value, Locale locale) throws IllegalPredicateArgumentException {
      return value.toString();
   }

   public Object parseExprValue(String value) throws IllegalPredicateArgumentException {
      return this.parseValue(value, Locale.US);
   }

   public String formatExprValue(Object value) throws IllegalPredicateArgumentException {
      return this.formatValue(value, Locale.US);
   }

   protected static void test(PredicateArgument arg, String valueStr) throws Exception {
      Locale l = Locale.getDefault();
      System.out.println("Name: " + arg.getDisplayName(l));
      System.out.println("Description: " + arg.getDescription(l));
      System.out.println("Original string: " + valueStr);
      Object value = arg.parseValue(valueStr, l);
      String exprValueStr = arg.formatExprValue(value);
      System.out.println("Formatted expression value: " + exprValueStr);
      Object exprValue = arg.parseExprValue(exprValueStr);
      if (!value.equals(exprValue)) {
         System.out.println("Values 1/expr are different: " + value + ", " + exprValue);
      }

      valueStr = arg.formatValue(exprValue, l);
      System.out.println("Formatted localized value: " + valueStr);
      Object value2 = arg.parseValue(valueStr, l);
      if (!value.equals(value2)) {
         System.out.println("Values 1/2 are different: " + value + ", " + value2);
      }

   }

   public String getDatePattern(Locale locale) {
      DateFormat fmt = DateFormat.getDateInstance(3, locale);
      if (fmt instanceof SimpleDateFormat) {
         SimpleDateFormat sdf1 = (SimpleDateFormat)fmt;
         return sdf1.toPattern();
      } else {
         return new String(" ");
      }
   }

   public String getTimePattern(Locale locale) {
      return this.getTimePattern(locale, 2);
   }

   public String getTimePattern(Locale locale, int style) {
      DateFormat fmt = DateFormat.getTimeInstance(style, locale);
      if (fmt instanceof SimpleDateFormat) {
         SimpleDateFormat sdf1 = (SimpleDateFormat)fmt;
         return sdf1.toPattern();
      } else {
         return new String(" ");
      }
   }

   public String getDateTimePattern(Locale locale) {
      DateFormat fmt = DateFormat.getDateTimeInstance(3, 2, locale);
      if (fmt instanceof SimpleDateFormat) {
         SimpleDateFormat sdf1 = (SimpleDateFormat)fmt;
         return sdf1.toPattern();
      } else {
         return new String(" ");
      }
   }

   public String getFormatedDateTimePattern(Locale locale) {
      DateFormat fmt = DateFormat.getDateTimeInstance(3, 2, locale);
      SimpleDateFormat sdf = (SimpleDateFormat)fmt;
      StringBuffer sb = new StringBuffer(sdf.toPattern());
      int ichar = sb.indexOf("a");
      if (ichar >= 0) {
         sb = sb.replace(ichar, ichar + 1, Localizer.getText("AM_PM", locale));
      }

      return sb.toString();
   }

   public String getFormatedTimePattern(Locale locale) {
      return this.getFormatedTimePattern(locale, 3);
   }

   public String getFormatedTimePattern(Locale locale, int style) {
      DateFormat fmt = DateFormat.getTimeInstance(style, locale);
      SimpleDateFormat sdf = (SimpleDateFormat)fmt;
      String pattern = sdf.toPattern();
      StringBuffer sb = new StringBuffer(sdf.toPattern());
      int ichar = sb.indexOf("a");
      if (ichar >= 0) {
         sb = sb.replace(ichar, ichar + 1, Localizer.getText("AM_PM", locale));
      }

      return sb.toString();
   }

   public String getFormatedDateString(String pattern, Locale locale, Date date) {
      SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
      StringBuffer dateTimeBuffer = sdf.format(date, new StringBuffer(), new FieldPosition(0));
      return dateTimeBuffer.toString();
   }

   public String parseMessage(StringBuffer message, ArrayList arrayList) {
      String begin = "{";
      String end = "}";

      for(int i = 0; i < arrayList.size(); ++i) {
         int ibegin = message.indexOf(begin);
         int iend = message.indexOf(end);
         String index = message.substring(ibegin, iend + 1);
         String indexValue = message.substring(ibegin + 1, iend);
         if (index.length() >= 0) {
            message = message.replace(ibegin, iend + 1, (String)arrayList.get(Integer.parseInt(indexValue)));
         }
      }

      return message.toString();
   }
}
