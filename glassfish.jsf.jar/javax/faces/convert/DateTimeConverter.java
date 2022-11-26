package javax.faces.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class DateTimeConverter implements Converter, PartialStateHolder {
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
   private boolean initialState;

   public DateTimeConverter() {
      this.timeZone = DEFAULT_TIME_ZONE;
      this.type = "date";
   }

   public String getDateStyle() {
      return this.dateStyle;
   }

   public void setDateStyle(String dateStyle) {
      this.clearInitialState();
      this.dateStyle = dateStyle;
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

   public String getTimeStyle() {
      return this.timeStyle;
   }

   public void setTimeStyle(String timeStyle) {
      this.clearInitialState();
      this.timeStyle = timeStyle;
   }

   public TimeZone getTimeZone() {
      return this.timeZone;
   }

   public void setTimeZone(TimeZone timeZone) {
      this.clearInitialState();
      this.timeZone = timeZone;
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
         FormatWrapper parser = null;

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
         } catch (DateTimeParseException | ParseException var9) {
            if (null != this.type) {
               switch (this.type) {
                  case "date":
                  case "localDate":
                     throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.DateTimeConverter.DATE", value, parser.formatNow(), MessageFactory.getLabel(context, component)), var9);
                  case "time":
                  case "localTime":
                  case "offsetTime":
                     throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.DateTimeConverter.TIME", value, parser.formatNow(), MessageFactory.getLabel(context, component)), var9);
                  case "both":
                  case "localDateTime":
                  case "offsetDateTime":
                  case "zonedDateTime":
                     throw new ConverterException(MessageFactory.getMessage(context, "javax.faces.converter.DateTimeConverter.DATETIME", value, parser.formatNow(), MessageFactory.getLabel(context, component)), var9);
               }
            }
         } catch (Exception var10) {
            throw new ConverterException(var10);
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
               FormatWrapper formatter = this.getDateFormat(locale);
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

   private FormatWrapper getDateFormat(Locale locale) {
      if (this.pattern == null && this.type == null) {
         throw new IllegalArgumentException("Either pattern or type must be specified.");
      } else {
         DateFormat df = null;
         DateTimeFormatter dtf = null;
         TemporalQuery from = null;
         if (this.pattern != null && !isJavaTimeType(this.type)) {
            df = new SimpleDateFormat(this.pattern, locale);
         } else if (this.type.equals("both")) {
            df = DateFormat.getDateTimeInstance(getStyle(this.dateStyle), getStyle(this.timeStyle), locale);
         } else if (this.type.equals("date")) {
            df = DateFormat.getDateInstance(getStyle(this.dateStyle), locale);
         } else if (this.type.equals("time")) {
            df = DateFormat.getTimeInstance(getStyle(this.timeStyle), locale);
         } else if (this.type.equals("localDate")) {
            if (null != this.pattern) {
               dtf = DateTimeFormatter.ofPattern(this.pattern, locale);
            } else {
               dtf = DateTimeFormatter.ofLocalizedDate(getFormatStyle(this.dateStyle)).withLocale(locale);
            }

            from = LocalDate::from;
         } else if (this.type.equals("localDateTime")) {
            if (null != this.pattern) {
               dtf = DateTimeFormatter.ofPattern(this.pattern, locale);
            } else {
               dtf = DateTimeFormatter.ofLocalizedDateTime(getFormatStyle(this.dateStyle), getFormatStyle(this.timeStyle)).withLocale(locale);
            }

            from = LocalDateTime::from;
         } else if (this.type.equals("localTime")) {
            if (null != this.pattern) {
               dtf = DateTimeFormatter.ofPattern(this.pattern, locale);
            } else {
               dtf = DateTimeFormatter.ofLocalizedTime(getFormatStyle(this.timeStyle)).withLocale(locale);
            }

            from = LocalTime::from;
         } else if (this.type.equals("offsetTime")) {
            if (null != this.pattern) {
               dtf = DateTimeFormatter.ofPattern(this.pattern, locale);
            } else {
               dtf = DateTimeFormatter.ISO_OFFSET_TIME.withLocale(locale);
            }

            from = OffsetTime::from;
         } else if (this.type.equals("offsetDateTime")) {
            if (null != this.pattern) {
               dtf = DateTimeFormatter.ofPattern(this.pattern, locale);
            } else {
               dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(locale);
            }

            from = OffsetDateTime::from;
         } else {
            if (!this.type.equals("zonedDateTime")) {
               throw new IllegalArgumentException("Invalid type: " + this.type);
            }

            if (null != this.pattern) {
               dtf = DateTimeFormatter.ofPattern(this.pattern, locale);
            } else {
               dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME.withLocale(locale);
            }

            from = ZonedDateTime::from;
         }

         if (null != df) {
            ((DateFormat)df).setLenient(false);
            return new FormatWrapper((DateFormat)df);
         } else if (null != dtf) {
            return new FormatWrapper(dtf, from);
         } else {
            throw new IllegalArgumentException("Invalid type: " + this.type);
         }
      }
   }

   private static boolean isJavaTimeType(String type) {
      boolean result = false;
      if (null != type && type.length() > 1) {
         char c = type.charAt(0);
         result = c == 'l' || c == 'o' || c == 'z';
      }

      return result;
   }

   private Locale getLocale(FacesContext context) {
      Locale locale = this.locale;
      if (locale == null) {
         locale = context.getViewRoot().getLocale();
      }

      return locale;
   }

   private static int getStyle(String name) {
      if (null != name) {
         switch (name) {
            case "default":
               return 2;
            case "short":
               return 3;
            case "medium":
               return 2;
            case "long":
               return 1;
            case "full":
               return 0;
         }
      }

      throw new ConverterException("Invalid style '" + name + '\'');
   }

   private static FormatStyle getFormatStyle(String name) {
      if (null != name) {
         switch (name) {
            case "default":
            case "medium":
               return FormatStyle.MEDIUM;
            case "short":
               return FormatStyle.SHORT;
            case "long":
               return FormatStyle.LONG;
            case "full":
               return FormatStyle.FULL;
         }
      }

      throw new ConverterException("Invalid style '" + name + '\'');
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (!this.initialStateMarked()) {
         Object[] values = new Object[]{this.dateStyle, this.locale, this.pattern, this.timeStyle, this.timeZone, this.type};
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
            this.dateStyle = (String)values[0];
            this.locale = (Locale)values[1];
            this.pattern = (String)values[2];
            this.timeStyle = (String)values[3];
            this.timeZone = (TimeZone)values[4];
            this.type = (String)values[5];
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

   private static class FormatWrapper {
      private final DateFormat df;
      private final DateTimeFormatter dtf;
      private final TemporalQuery from;

      private FormatWrapper(DateFormat wrapped) {
         this.df = wrapped;
         this.dtf = null;
         this.from = null;
      }

      private FormatWrapper(DateTimeFormatter dtf, TemporalQuery from) {
         this.df = null;
         this.dtf = dtf;
         this.from = from;
      }

      private Object parse(CharSequence text) throws ParseException {
         Object result = null != this.df ? this.df.parse((String)text) : this.dtf.parse(text, this.from);
         return result;
      }

      private String format(Object obj) {
         return null != this.df ? this.df.format(obj) : this.dtf.format((TemporalAccessor)obj);
      }

      private String formatNow() {
         return null != this.df ? this.df.format(new Date()) : this.dtf.format(ZonedDateTime.now());
      }

      private void setTimeZone(TimeZone zone) {
         if (null != this.df) {
            this.df.setTimeZone(zone);
         }

      }

      // $FF: synthetic method
      FormatWrapper(DateFormat x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      FormatWrapper(DateTimeFormatter x0, TemporalQuery x1, Object x2) {
         this(x0, x1);
      }
   }
}
