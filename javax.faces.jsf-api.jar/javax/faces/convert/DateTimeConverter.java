package javax.faces.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class DateTimeConverter implements Converter, StateHolder {
   public static final String CONVERTER_ID = "javax.faces.DateTime";
   public static final String DATE_ID = "javax.faces.converter.DateTimeConverter.DATE";
   public static final String TIME_ID = "javax.faces.converter.DateTimeConverter.TIME";
   public static final String DATETIME_ID = "javax.faces.converter.DateTimeConverter.DATETIME";
   public static final String STRING_ID = "javax.faces.converter.STRING";
   private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT");
   private String dateStyle = "default";
   private Locale locale = null;
   private String pattern = null;
   private String timeStyle = "default";
   private TimeZone timeZone;
   private String type;
   private boolean transientFlag;

   public DateTimeConverter() {
      this.timeZone = DEFAULT_TIME_ZONE;
      this.type = "date";
      this.transientFlag = false;
   }

   public String getDateStyle() {
      return this.dateStyle;
   }

   public void setDateStyle(String dateStyle) {
      this.dateStyle = dateStyle;
   }

   public Locale getLocale() {
      if (this.locale == null) {
         this.locale = this.getLocale(FacesContext.getCurrentInstance());
      }

      return this.locale;
   }

   public void setLocale(Locale locale) {
      this.locale = locale;
   }

   public String getPattern() {
      return this.pattern;
   }

   public void setPattern(String pattern) {
      this.pattern = pattern;
   }

   public String getTimeStyle() {
      return this.timeStyle;
   }

   public void setTimeStyle(String timeStyle) {
      this.timeStyle = timeStyle;
   }

   public TimeZone getTimeZone() {
      return this.timeZone;
   }

   public void setTimeZone(TimeZone timeZone) {
      this.timeZone = timeZone;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (context != null && component != null) {
         Object returnValue = null;
         DateFormat parser = null;

         try {
            if (value == null) {
               return null;
            }

            value = value.trim();
            if (value.length() < 1) {
               return null;
            }

            Locale locale = this.getLocale(context);
            parser = this.getDateFormat(locale);
            if (null != this.timeZone) {
               parser.setTimeZone(this.timeZone);
            }

            returnValue = parser.parse(value);
         } catch (ParseException var7) {
            if ("date".equals(this.type)) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.DateTimeConverter.DATE", value, parser.format(new Date(System.currentTimeMillis())), MessageFactory.getLabel(context, component)), var7);
            }

            if ("time".equals(this.type)) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.DateTimeConverter.TIME", value, parser.format(new Date(System.currentTimeMillis())), MessageFactory.getLabel(context, component)), var7);
            }

            if ("both".equals(this.type)) {
               throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.DateTimeConverter.DATETIME", value, parser.format(new Date(System.currentTimeMillis())), MessageFactory.getLabel(context, component)), var7);
            }
         } catch (Exception var8) {
            throw new ConverterException(var8);
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
               DateFormat formatter = this.getDateFormat(locale);
               if (null != this.timeZone) {
                  formatter.setTimeZone(this.timeZone);
               }

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

   private DateFormat getDateFormat(Locale locale) {
      if (this.pattern == null && this.type == null) {
         throw new IllegalArgumentException("Either pattern or type must be specified.");
      } else {
         Object df;
         if (this.pattern != null) {
            df = new SimpleDateFormat(this.pattern, locale);
         } else if (this.type.equals("both")) {
            df = DateFormat.getDateTimeInstance(getStyle(this.dateStyle), getStyle(this.timeStyle), locale);
         } else if (this.type.equals("date")) {
            df = DateFormat.getDateInstance(getStyle(this.dateStyle), locale);
         } else {
            if (!this.type.equals("time")) {
               throw new IllegalArgumentException("Invalid type: " + this.type);
            }

            df = DateFormat.getTimeInstance(getStyle(this.timeStyle), locale);
         }

         ((DateFormat)df).setLenient(false);
         return (DateFormat)df;
      }
   }

   private Locale getLocale(FacesContext context) {
      Locale locale = this.locale;
      if (locale == null) {
         locale = context.getViewRoot().getLocale();
      }

      return locale;
   }

   private static int getStyle(String name) {
      if ("default".equals(name)) {
         return 2;
      } else if ("short".equals(name)) {
         return 3;
      } else if ("medium".equals(name)) {
         return 2;
      } else if ("long".equals(name)) {
         return 1;
      } else if ("full".equals(name)) {
         return 0;
      } else {
         throw new ConverterException("Invalid style '" + name + '\'');
      }
   }

   public Object saveState(FacesContext context) {
      Object[] values = new Object[]{this.dateStyle, this.locale, this.pattern, this.timeStyle, this.timeZone, this.type};
      return values;
   }

   public void restoreState(FacesContext context, Object state) {
      Object[] values = (Object[])((Object[])state);
      this.dateStyle = (String)values[0];
      this.locale = (Locale)values[1];
      this.pattern = (String)values[2];
      this.timeStyle = (String)values[3];
      this.timeZone = (TimeZone)values[4];
      this.type = (String)values[5];
   }

   public boolean isTransient() {
      return this.transientFlag;
   }

   public void setTransient(boolean transientFlag) {
      this.transientFlag = transientFlag;
   }
}
