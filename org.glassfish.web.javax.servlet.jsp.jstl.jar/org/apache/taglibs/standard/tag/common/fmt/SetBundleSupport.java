package org.apache.taglibs.standard.tag.common.fmt;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class SetBundleSupport extends TagSupport {
   protected String basename;
   private int scope;
   private String var;

   public SetBundleSupport() {
      this.init();
   }

   private void init() {
      this.basename = null;
      this.scope = 1;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public int doEndTag() throws JspException {
      LocalizationContext locCtxt = BundleSupport.getLocalizationContext(this.pageContext, this.basename);
      if (this.var != null) {
         this.pageContext.setAttribute(this.var, locCtxt, this.scope);
      } else {
         Config.set(this.pageContext, "javax.servlet.jsp.jstl.fmt.localizationContext", locCtxt, this.scope);
      }

      return 6;
   }

   public void release() {
      this.init();
   }
}
