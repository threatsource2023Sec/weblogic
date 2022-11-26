package org.apache.taglibs.standard.tag.rt.fmt;

import java.util.Locale;
import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.ParseDateSupport;
import org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport;

public class ParseDateTag extends ParseDateSupport {
   public void setValue(String value) throws JspTagException {
      this.value = value;
      this.valueSpecified = true;
   }

   public void setType(String type) throws JspTagException {
      this.type = type;
   }

   public void setDateStyle(String dateStyle) throws JspTagException {
      this.dateStyle = dateStyle;
   }

   public void setTimeStyle(String timeStyle) throws JspTagException {
      this.timeStyle = timeStyle;
   }

   public void setPattern(String pattern) throws JspTagException {
      this.pattern = pattern;
   }

   public void setTimeZone(Object timeZone) throws JspTagException {
      this.timeZone = timeZone;
   }

   public void setParseLocale(Object loc) throws JspTagException {
      if (loc != null) {
         if (loc instanceof Locale) {
            this.parseLocale = (Locale)loc;
         } else if (!"".equals((String)loc)) {
            this.parseLocale = SetLocaleSupport.parseLocale((String)loc);
         }
      }

   }
}
