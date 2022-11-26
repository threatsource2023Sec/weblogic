package org.apache.taglibs.standard.tag.common.fmt;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class FormatDateSupport extends TagSupport {
   private static final String DATE = "date";
   private static final String TIME = "time";
   private static final String DATETIME = "both";
   protected Date value;
   protected String type;
   protected String pattern;
   protected Object timeZone;
   protected String dateStyle;
   protected String timeStyle;
   private String var;
   private int scope;

   public FormatDateSupport() {
      this.init();
   }

   private void init() {
      this.type = this.dateStyle = this.timeStyle = null;
      this.pattern = this.var = null;
      this.value = null;
      this.timeZone = null;
      this.scope = 1;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public int doEndTag() throws JspException {
      String formatted = null;
      if (this.value == null) {
         if (this.var != null) {
            this.pageContext.removeAttribute(this.var, this.scope);
         }

         return 6;
      } else {
         Locale locale = SetLocaleSupport.getFormattingLocale(this.pageContext, this, true, true);
         if (locale != null) {
            DateFormat formatter = this.createFormatter(locale);
            if (this.pattern != null) {
               try {
                  ((SimpleDateFormat)formatter).applyPattern(this.pattern);
               } catch (ClassCastException var6) {
                  formatter = new SimpleDateFormat(this.pattern, locale);
               }
            }

            TimeZone tz = null;
            if (this.timeZone instanceof String && ((String)this.timeZone).equals("")) {
               this.timeZone = null;
            }

            if (this.timeZone != null) {
               if (this.timeZone instanceof String) {
                  tz = TimeZone.getTimeZone((String)this.timeZone);
               } else {
                  if (!(this.timeZone instanceof TimeZone)) {
                     throw new JspTagException(Resources.getMessage("FORMAT_DATE_BAD_TIMEZONE"));
                  }

                  tz = (TimeZone)this.timeZone;
               }
            } else {
               tz = TimeZoneSupport.getTimeZone(this.pageContext, this);
            }

            if (tz != null) {
               ((DateFormat)formatter).setTimeZone(tz);
            }

            formatted = ((DateFormat)formatter).format(this.value);
         } else {
            formatted = this.value.toString();
         }

         if (this.var != null) {
            this.pageContext.setAttribute(this.var, formatted, this.scope);
         } else {
            try {
               this.pageContext.getOut().print(formatted);
            } catch (IOException var5) {
               throw new JspTagException(var5.toString(), var5);
            }
         }

         return 6;
      }
   }

   public void release() {
      this.init();
   }

   private DateFormat createFormatter(Locale loc) throws JspException {
      DateFormat formatter = null;
      if (this.type != null && !"date".equalsIgnoreCase(this.type)) {
         if ("time".equalsIgnoreCase(this.type)) {
            formatter = DateFormat.getTimeInstance(Util.getStyle(this.timeStyle, "FORMAT_DATE_INVALID_TIME_STYLE"), loc);
         } else {
            if (!"both".equalsIgnoreCase(this.type)) {
               throw new JspException(Resources.getMessage("FORMAT_DATE_INVALID_TYPE", (Object)this.type));
            }

            formatter = DateFormat.getDateTimeInstance(Util.getStyle(this.dateStyle, "FORMAT_DATE_INVALID_DATE_STYLE"), Util.getStyle(this.timeStyle, "FORMAT_DATE_INVALID_TIME_STYLE"), loc);
         }
      } else {
         formatter = DateFormat.getDateInstance(Util.getStyle(this.dateStyle, "FORMAT_DATE_INVALID_DATE_STYLE"), loc);
      }

      return formatter;
   }
}
