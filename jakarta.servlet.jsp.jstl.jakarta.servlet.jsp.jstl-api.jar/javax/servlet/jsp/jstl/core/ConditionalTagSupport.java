package javax.servlet.jsp.jstl.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class ConditionalTagSupport extends TagSupport {
   private boolean result;
   private String var;
   private int scope;

   protected abstract boolean condition() throws JspTagException;

   public ConditionalTagSupport() {
      this.init();
   }

   public int doStartTag() throws JspException {
      this.result = this.condition();
      this.exposeVariables();
      return this.result ? 1 : 0;
   }

   public void release() {
      super.release();
      this.init();
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      if (scope.equalsIgnoreCase("page")) {
         this.scope = 1;
      } else if (scope.equalsIgnoreCase("request")) {
         this.scope = 2;
      } else if (scope.equalsIgnoreCase("session")) {
         this.scope = 3;
      } else if (scope.equalsIgnoreCase("application")) {
         this.scope = 4;
      }

   }

   private void exposeVariables() {
      if (this.var != null) {
         this.pageContext.setAttribute(this.var, this.result, this.scope);
      }

   }

   private void init() {
      this.result = false;
      this.var = null;
      this.scope = 1;
   }
}
