package org.apache.taglibs.standard.tag.rt.fmt;

import java.util.Locale;
import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.ParseNumberSupport;
import org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport;

public class ParseNumberTag extends ParseNumberSupport {
   public void setValue(String value) throws JspTagException {
      this.value = value;
      this.valueSpecified = true;
   }

   public void setType(String type) throws JspTagException {
      this.type = type;
   }

   public void setPattern(String pattern) throws JspTagException {
      this.pattern = pattern;
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

   public void setIntegerOnly(boolean isIntegerOnly) throws JspTagException {
      this.isIntegerOnly = isIntegerOnly;
      this.integerOnlySpecified = true;
   }
}
