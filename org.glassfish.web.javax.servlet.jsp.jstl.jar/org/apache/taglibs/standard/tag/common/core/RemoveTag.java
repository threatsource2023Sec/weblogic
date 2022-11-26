package org.apache.taglibs.standard.tag.common.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class RemoveTag extends TagSupport {
   private final String APPLICATION = "application";
   private final String SESSION = "session";
   private final String REQUEST = "request";
   private final String PAGE = "page";
   private int scope;
   private boolean scopeSpecified;
   private String var;

   public RemoveTag() {
      this.init();
   }

   private void init() {
      this.var = null;
      this.scope = 1;
      this.scopeSpecified = false;
   }

   public void release() {
      super.release();
      this.init();
   }

   public int doEndTag() throws JspException {
      if (!this.scopeSpecified) {
         this.pageContext.removeAttribute(this.var);
      } else {
         this.pageContext.removeAttribute(this.var, this.scope);
      }

      return 6;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
      this.scopeSpecified = true;
   }
}
