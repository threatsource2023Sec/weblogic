package org.apache.taglibs.standard.tag.common.fmt;

import java.util.TimeZone;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class SetTimeZoneSupport extends TagSupport {
   protected Object value;
   private int scope;
   private String var;

   public SetTimeZoneSupport() {
      this.init();
   }

   private void init() {
      this.value = this.var = null;
      this.scope = 1;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public void setVar(String var) {
      this.var = var;
   }

   public int doEndTag() throws JspException {
      TimeZone timeZone = null;
      if (this.value == null) {
         timeZone = TimeZone.getTimeZone("GMT");
      } else if (this.value instanceof String) {
         if (((String)this.value).trim().equals("")) {
            timeZone = TimeZone.getTimeZone("GMT");
         } else {
            timeZone = TimeZone.getTimeZone((String)this.value);
         }
      } else {
         timeZone = (TimeZone)this.value;
      }

      if (this.var != null) {
         this.pageContext.setAttribute(this.var, timeZone, this.scope);
      } else {
         Config.set(this.pageContext, "javax.servlet.jsp.jstl.fmt.timeZone", timeZone, this.scope);
      }

      return 6;
   }

   public void release() {
      this.init();
   }
}
