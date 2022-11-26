package org.apache.taglibs.standard.tag.common.fmt;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class ParseDateSupport extends BodyTagSupport {
   private static final String DATE = "date";
   private static final String TIME = "time";
   private static final String DATETIME = "both";
   protected String value;
   protected boolean valueSpecified;
   protected String type;
   protected String pattern;
   protected Object timeZone;
   protected Locale parseLocale;
   protected String dateStyle;
   protected String timeStyle;
   private String var;
   private int scope;

   public ParseDateSupport() {
      this.init();
   }

   private void init() {
      this.type = this.dateStyle = this.timeStyle = null;
      this.value = this.pattern = this.var = null;
      this.valueSpecified = false;
      this.timeZone = null;
      this.scope = 1;
      this.parseLocale = null;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public int doEndTag() throws JspException {
      String input = null;
      if (this.valueSpecified) {
         input = this.value;
      } else if (this.bodyContent != null && this.bodyContent.getString() != null) {
         input = this.bodyContent.getString().trim();
      }

      if (input != null && !input.equals("")) {
         Locale locale = this.parseLocale;
         if (locale == null) {
            locale = SetLocaleSupport.getFormattingLocale(this.pageContext, this, true, false);
         }

         if (locale == null) {
            throw new JspException(Resources.getMessage("PARSE_DATE_NO_PARSE_LOCALE"));
         } else {
            DateFormat parser = this.createParser(locale);
            if (this.pattern != null) {
               try {
                  ((SimpleDateFormat)parser).applyPattern(this.pattern);
               } catch (ClassCastException var9) {
                  parser = new SimpleDateFormat(this.pattern, locale);
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
                     throw new JspException(Resources.getMessage("PARSE_DATE_BAD_TIMEZONE"));
                  }

                  tz = (TimeZone)this.timeZone;
               }
            } else {
               tz = TimeZoneSupport.getTimeZone(this.pageContext, this);
            }

            if (tz != null) {
               ((DateFormat)parser).setTimeZone(tz);
            }

            Date parsed = null;

            try {
               parsed = ((DateFormat)parser).parse(input);
            } catch (ParseException var8) {
               throw new JspException(Resources.getMessage("PARSE_DATE_PARSE_ERROR", (Object)input), var8);
            }

            if (this.var != null) {
               this.pageContext.setAttribute(this.var, parsed, this.scope);
            } else {
               try {
                  this.pageContext.getOut().print(parsed);
               } catch (IOException var7) {
                  throw new JspTagException(var7.toString(), var7);
               }
            }

            return 6;
         }
      } else {
         if (this.var != null) {
            this.pageContext.removeAttribute(this.var, this.scope);
         }

         return 6;
      }
   }

   public void release() {
      this.init();
   }

   private DateFormat createParser(Locale loc) throws JspException {
      DateFormat parser = null;
      if (this.type != null && !"date".equalsIgnoreCase(this.type)) {
         if ("time".equalsIgnoreCase(this.type)) {
            parser = DateFormat.getTimeInstance(Util.getStyle(this.timeStyle, "PARSE_DATE_INVALID_TIME_STYLE"), loc);
         } else {
            if (!"both".equalsIgnoreCase(this.type)) {
               throw new JspException(Resources.getMessage("PARSE_DATE_INVALID_TYPE", (Object)this.type));
            }

            parser = DateFormat.getDateTimeInstance(Util.getStyle(this.dateStyle, "PARSE_DATE_INVALID_DATE_STYLE"), Util.getStyle(this.timeStyle, "PARSE_DATE_INVALID_TIME_STYLE"), loc);
         }
      } else {
         parser = DateFormat.getDateInstance(Util.getStyle(this.dateStyle, "PARSE_DATE_INVALID_DATE_STYLE"), loc);
      }

      parser.setLenient(false);
      return parser;
   }
}
