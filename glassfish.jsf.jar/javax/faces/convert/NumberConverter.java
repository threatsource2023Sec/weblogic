package javax.faces.convert;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.el.ValueExpression;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class NumberConverter implements Converter, PartialStateHolder {
   public static final String CONVERTER_ID = "javax.faces.Number";
   public static final String CURRENCY_ID = "javax.faces.converter.NumberConverter.CURRENCY";
   public static final String NUMBER_ID = "javax.faces.converter.NumberConverter.NUMBER";
   public static final String PATTERN_ID = "javax.faces.converter.NumberConverter.PATTERN";
   public static final String PERCENT_ID = "javax.faces.converter.NumberConverter.PERCENT";
   public static final String STRING_ID = "javax.faces.converter.STRING";
   private static final String NBSP = " ";
   private String currencyCode = null;
   private String currencySymbol = null;
   private Boolean groupingUsed = true;
   private Boolean integerOnly = false;
   private Integer maxFractionDigits;
   private Integer maxIntegerDigits;
   private Integer minFractionDigits;
   private Integer minIntegerDigits;
   private Locale locale = null;
   private String pattern = null;
   private String type = "number";
   private static Class currencyClass;
   private static final Class[] GET_INSTANCE_PARAM_TYPES;
   private boolean transientFlag;
   private boolean initialState;

   public String getCurrencyCode() {
      return this.currencyCode;
   }

   public void setCurrencyCode(String currencyCode) {
      this.clearInitialState();
      this.currencyCode = currencyCode;
   }

   public String getCurrencySymbol() {
      return this.currencySymbol;
   }

   public void setCurrencySymbol(String currencySymbol) {
      this.clearInitialState();
      this.currencySymbol = currencySymbol;
   }

   public boolean isGroupingUsed() {
      return this.groupingUsed != null ? this.groupingUsed : true;
   }

   public void setGroupingUsed(boolean groupingUsed) {
      this.clearInitialState();
      this.groupingUsed = groupingUsed;
   }

   public boolean isIntegerOnly() {
      return this.integerOnly != null ? this.integerOnly : false;
   }

   public void setIntegerOnly(boolean integerOnly) {
      this.clearInitialState();
      this.integerOnly = integerOnly;
   }

   public int getMaxFractionDigits() {
      return this.maxFractionDigits != null ? this.maxFractionDigits : 0;
   }

   public void setMaxFractionDigits(int maxFractionDigits) {
      this.clearInitialState();
      this.maxFractionDigits = maxFractionDigits;
   }

   public int getMaxIntegerDigits() {
      return this.maxIntegerDigits != null ? this.maxIntegerDigits : 0;
   }

   public void setMaxIntegerDigits(int maxIntegerDigits) {
      this.clearInitialState();
      this.maxIntegerDigits = maxIntegerDigits;
   }

   public int getMinFractionDigits() {
      return this.minFractionDigits != null ? this.minFractionDigits : 0;
   }

   public void setMinFractionDigits(int minFractionDigits) {
      this.clearInitialState();
      this.minFractionDigits = minFractionDigits;
   }

   public int getMinIntegerDigits() {
      return this.minIntegerDigits != null ? this.minIntegerDigits : 0;
   }

   public void setMinIntegerDigits(int minIntegerDigits) {
      this.clearInitialState();
      this.minIntegerDigits = minIntegerDigits;
   }

   public Locale getLocale() {
      if (this.locale == null) {
         this.locale = this.getLocale(FacesContext.getCurrentInstance());
      }

      return this.locale;
   }

   public void setLocale(Locale locale) {
      this.clearInitialState();
      this.locale = locale;
   }

   public String getPattern() {
      return this.pattern;
   }

   public void setPattern(String pattern) {
      this.clearInitialState();
      this.pattern = pattern;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.clearInitialState();
      this.type = type;
   }

   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (context != null && component != null) {
         Object returnValue = null;
         NumberFormat parser = null;

         try {
            if (value == null) {
               return null;
            }

            value = value.trim();
            if (value.length() < 1) {
               return null;
            }

            Locale locale = this.getLocale(context);
            parser = this.getNumberFormat(locale);
            if (this.pattern != null && this.pattern.length() != 0 || "currency".equals(this.type)) {
               this.configureCurrency(parser);
            }

            parser.setParseIntegerOnly(this.isIntegerOnly());
            boolean groupSepChanged = false;
            if (parser instanceof DecimalFormat) {
               DecimalFormat dParser = (DecimalFormat)parser;
               ValueExpression ve = component.getValueExpression("value");
               if (ve != null) {
                  Class expectedType = ve.getType(context.getELContext());
                  if (expectedType != null && expectedType.isAssignableFrom(BigDecimal.class)) {
                     dParser.setParseBigDecimal(true);
                  }
               }

               DecimalFormatSymbols symbols = dParser.getDecimalFormatSymbols();
               if (symbols.getGroupingSeparator() == 160) {
                  groupSepChanged = true;
                  String tValue;
                  if (value.contains(" ")) {
                     tValue = value.replace(' ', ' ');
                  } else {
                     tValue = value;
                  }

                  symbols.setGroupingSeparator(' ');
                  dParser.setDecimalFormatSymbols(symbols);

                  try {
                     return dParser.parse(tValue);
                  } catch (ParseException var13) {
                     if (groupSepChanged) {
                        symbols.setGroupingSeparator(' ');
                        dParser.setDecimalFormatSymbols(symbols);
                     }
                  }
               }
            }

            returnValue = parser.parse(value);
         } catch (ParseException var14) {
            if (this.pattern != null) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.NumberConverter.PATTERN", value, "#,##0.0#", MessageFactory.getLabel(context, component)), var14);
            }

            if (this.type.equals("currency")) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.NumberConverter.CURRENCY", value, parser.format(99.99), MessageFactory.getLabel(context, component)), var14);
            }

            if (this.type.equals("number")) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.NumberConverter.NUMBER", value, parser.format(99L), MessageFactory.getLabel(context, component)), var14);
            }

            if (this.type.equals("percent")) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.NumberConverter.PERCENT", value, parser.format(0.75), MessageFactory.getLabel(context, component)), var14);
            }
         } catch (Exception var15) {
            throw new ConverterException(var15);
         }

         return returnValue;
      } else {
         throw new NullPointerException();
      }
   }

   public String getAsString(FacesContext context, UIComponent component, Object value) {
      if (context != null && component != null) {
         try {
            if (value == null) {
               return "";
            } else if (value instanceof String) {
               return (String)value;
            } else {
               Locale locale = this.getLocale(context);
               NumberFormat formatter = this.getNumberFormat(locale);
               if (this.pattern != null && this.pattern.length() != 0 || "currency".equals(this.type)) {
                  this.configureCurrency(formatter);
               }

               this.configureFormatter(formatter);
               return formatter.format(value);
            }
         } catch (ConverterException var6) {
            throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.STRING", value, MessageFactory.getLabel(context, component)), var6);
         } catch (Exception var7) {
            throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.STRING", value, MessageFactory.getLabel(context, component)), var7);
         }
      } else {
         throw new NullPointerException();
      }
   }

   private void configureCurrency(NumberFormat formatter) throws Exception {
      String code = null;
      String symbol = null;
      if (this.currencyCode != null || this.currencySymbol != null) {
         if (this.currencyCode != null && this.currencySymbol != null) {
            if (currencyClass != null) {
               code = this.currencyCode;
            } else {
               symbol = this.currencySymbol;
            }
         } else if (this.currencyCode == null) {
            symbol = this.currencySymbol;
         } else if (currencyClass != null) {
            code = this.currencyCode;
         } else {
            symbol = this.currencyCode;
         }

         if (code != null) {
            Object[] methodArgs = new Object[1];
            Method m = currencyClass.getMethod("getInstance", GET_INSTANCE_PARAM_TYPES);
            methodArgs[0] = code;
            Object currency = m.invoke((Object)null, methodArgs);
            Class[] paramTypes = new Class[]{currencyClass};
            Class numberFormatClass = Class.forName("java.text.NumberFormat");
            m = numberFormatClass.getMethod("setCurrency", paramTypes);
            methodArgs[0] = currency;
            m.invoke(formatter, methodArgs);
         } else {
            DecimalFormat df = (DecimalFormat)formatter;
            DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
            dfs.setCurrencySymbol(symbol);
            df.setDecimalFormatSymbols(dfs);
         }

      }
   }

   private void configureFormatter(NumberFormat formatter) {
      formatter.setGroupingUsed(this.groupingUsed);
      if (this.isMaxIntegerDigitsSet()) {
         formatter.setMaximumIntegerDigits(this.maxIntegerDigits);
      }

      if (this.isMinIntegerDigitsSet()) {
         formatter.setMinimumIntegerDigits(this.minIntegerDigits);
      }

      if (this.isMaxFractionDigitsSet()) {
         formatter.setMaximumFractionDigits(this.maxFractionDigits);
      }

      if (this.isMinFractionDigitsSet()) {
         formatter.setMinimumFractionDigits(this.minFractionDigits);
      }

   }

   private boolean isMaxIntegerDigitsSet() {
      return this.maxIntegerDigits != null;
   }

   private boolean isMinIntegerDigitsSet() {
      return this.minIntegerDigits != null;
   }

   private boolean isMaxFractionDigitsSet() {
      return this.maxFractionDigits != null;
   }

   private boolean isMinFractionDigitsSet() {
      return this.minFractionDigits != null;
   }

   private Locale getLocale(FacesContext context) {
      Locale locale = this.locale;
      if (locale == null) {
         locale = context.getViewRoot().getLocale();
      }

      return locale;
   }

   private NumberFormat getNumberFormat(Locale locale) {
      if (this.pattern == null && this.type == null) {
         throw new IllegalArgumentException("Either pattern or type must be specified.");
      } else if (this.pattern != null) {
         DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
         return new DecimalFormat(this.pattern, symbols);
      } else if (this.type.equals("currency")) {
         return NumberFormat.getCurrencyInstance(locale);
      } else if (this.type.equals("number")) {
         return NumberFormat.getNumberInstance(locale);
      } else if (this.type.equals("percent")) {
         return NumberFormat.getPercentInstance(locale);
      } else {
         throw new ConverterException(new IllegalArgumentException(this.type));
      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (!this.initialStateMarked()) {
         Object[] values = new Object[]{this.currencyCode, this.currencySymbol, this.groupingUsed, this.integerOnly, this.maxFractionDigits, this.maxIntegerDigits, this.minFractionDigits, this.minIntegerDigits, this.locale, this.pattern, this.type};
         return values;
      } else {
         return null;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (state != null) {
            Object[] values = (Object[])((Object[])state);
            this.currencyCode = (String)values[0];
            this.currencySymbol = (String)values[1];
            this.groupingUsed = (Boolean)values[2];
            this.integerOnly = (Boolean)values[3];
            this.maxFractionDigits = (Integer)values[4];
            this.maxIntegerDigits = (Integer)values[5];
            this.minFractionDigits = (Integer)values[6];
            this.minIntegerDigits = (Integer)values[7];
            this.locale = (Locale)values[8];
            this.pattern = (String)values[9];
            this.type = (String)values[10];
         }

      }
   }

   public boolean isTransient() {
      return this.transientFlag;
   }

   public void setTransient(boolean transientFlag) {
      this.transientFlag = transientFlag;
   }

   public void markInitialState() {
      this.initialState = true;
   }

   public boolean initialStateMarked() {
      return this.initialState;
   }

   public void clearInitialState() {
      this.initialState = false;
   }

   static {
      try {
         currencyClass = Class.forName("java.util.Currency");
      } catch (Exception var1) {
      }

      GET_INSTANCE_PARAM_TYPES = new Class[]{String.class};
   }
}
