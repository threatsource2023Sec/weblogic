package com.sun.faces.taglib.jsf_core;

import com.sun.faces.el.ELUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.servlet.jsp.JspException;

public class ConvertDateTimeTag extends AbstractConverterTag {
   private static final long serialVersionUID = -5815655767093677438L;
   private static ValueExpression CONVERTER_ID_EXPR = null;
   private static final Logger LOGGER;
   private ValueExpression dateStyleExpression;
   private ValueExpression localeExpression;
   private ValueExpression patternExpression;
   private ValueExpression timeStyleExpression;
   private ValueExpression timeZoneExpression;
   private ValueExpression typeExpression;
   private String dateStyle;
   private Locale locale;
   private String pattern;
   private String timeStyle;
   private TimeZone timeZone;
   private String type;

   public ConvertDateTimeTag() {
      this.init();
   }

   public void release() {
      super.release();
      this.init();
   }

   private void init() {
      this.dateStyle = "default";
      this.dateStyleExpression = null;
      this.locale = null;
      this.localeExpression = null;
      this.pattern = null;
      this.patternExpression = null;
      this.timeStyle = "default";
      this.timeStyleExpression = null;
      this.timeZone = null;
      this.timeZoneExpression = null;
      this.type = "date";
      this.typeExpression = null;
      if (CONVERTER_ID_EXPR == null) {
         FacesContext context = FacesContext.getCurrentInstance();
         ExpressionFactory factory = context.getApplication().getExpressionFactory();
         CONVERTER_ID_EXPR = factory.createValueExpression(context.getELContext(), "javax.faces.DateTime", String.class);
      }

   }

   public void setDateStyle(ValueExpression dateStyle) {
      this.dateStyleExpression = dateStyle;
   }

   public void setLocale(ValueExpression locale) {
      this.localeExpression = locale;
   }

   public void setPattern(ValueExpression pattern) {
      this.patternExpression = pattern;
   }

   public void setTimeStyle(ValueExpression timeStyle) {
      this.timeStyleExpression = timeStyle;
   }

   public void setTimeZone(ValueExpression timeZone) {
      this.timeZoneExpression = timeZone;
   }

   public void setType(ValueExpression type) {
      this.typeExpression = type;
   }

   public int doStartTag() throws JspException {
      super.setConverterId(CONVERTER_ID_EXPR);
      return super.doStartTag();
   }

   protected Converter createConverter() throws JspException {
      DateTimeConverter result = (DateTimeConverter)super.createConverter();

      assert null != result;

      this.evaluateExpressions();
      result.setDateStyle(this.dateStyle);
      result.setLocale(this.locale);
      result.setPattern(this.pattern);
      result.setTimeStyle(this.timeStyle);
      result.setTimeZone(this.timeZone);
      result.setType(this.type);
      return result;
   }

   private void evaluateExpressions() {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      ELContext elContext = facesContext.getELContext();
      if (this.dateStyleExpression != null) {
         this.dateStyle = (String)ELUtils.evaluateValueExpression(this.dateStyleExpression, elContext);
      }

      if (this.patternExpression != null) {
         this.pattern = (String)ELUtils.evaluateValueExpression(this.patternExpression, elContext);
      }

      if (this.timeStyleExpression != null) {
         this.timeStyle = (String)ELUtils.evaluateValueExpression(this.timeStyleExpression, elContext);
      }

      if (this.typeExpression != null) {
         this.type = (String)ELUtils.evaluateValueExpression(this.typeExpression, elContext);
      } else if (this.timeStyleExpression != null) {
         if (this.dateStyleExpression != null) {
            this.type = "both";
         } else {
            this.type = "time";
         }
      } else {
         this.type = "date";
      }

      Object tz;
      Object[] params;
      if (this.localeExpression != null) {
         if (this.localeExpression.isLiteralText()) {
            this.locale = getLocale(this.localeExpression.getExpressionString());
         } else {
            tz = ELUtils.evaluateValueExpression(this.localeExpression, elContext);
            if (tz != null) {
               if (tz instanceof String) {
                  this.locale = getLocale((String)tz);
               } else {
                  if (!(tz instanceof Locale)) {
                     params = new Object[]{"locale", "java.lang.String or java.util.Locale", tz.getClass().getName()};
                     if (LOGGER.isLoggable(Level.SEVERE)) {
                        LOGGER.log(Level.SEVERE, "jsf.core.tags.eval_result_not_expected_type", params);
                     }

                     throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.EVAL_ATTR_UNEXPECTED_TYPE", params));
                  }

                  this.locale = (Locale)tz;
               }
            } else {
               this.locale = facesContext.getViewRoot().getLocale();
            }
         }
      }

      if (this.timeZoneExpression != null) {
         if (this.timeZoneExpression.isLiteralText()) {
            this.timeZone = TimeZone.getTimeZone(this.timeZoneExpression.getExpressionString());
         } else {
            tz = ELUtils.evaluateValueExpression(this.timeZoneExpression, elContext);
            if (tz != null) {
               if (tz instanceof String) {
                  this.timeZone = TimeZone.getTimeZone((String)tz);
               } else {
                  if (!(tz instanceof TimeZone)) {
                     params = new Object[]{"timeZone", "java.lang.String or java.util.TimeZone", tz.getClass().getName()};
                     if (LOGGER.isLoggable(Level.SEVERE)) {
                        LOGGER.log(Level.SEVERE, "jsf.core.tags.eval_result_not_expected_type", params);
                     }

                     throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.EVAL_ATTR_UNEXPECTED_TYPE", params));
                  }

                  this.timeZone = (TimeZone)tz;
               }
            }
         }
      }

   }

   protected static Locale getLocale(String string) {
      if (string == null) {
         return Locale.getDefault();
      } else {
         if (string.length() > 2) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "jsf.core.taglib.invalid_locale_value", string);
            }
         } else {
            String[] langs = Locale.getISOLanguages();
            Arrays.sort(langs);
            if (Arrays.binarySearch(langs, string) < 0 && LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "jsf.core.taglib.invalid_language", string);
            }
         }

         return new Locale(string, "");
      }
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
   }
}
