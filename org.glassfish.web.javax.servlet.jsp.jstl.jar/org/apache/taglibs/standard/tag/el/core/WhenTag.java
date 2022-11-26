package org.apache.taglibs.standard.tag.el.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.apache.taglibs.standard.tag.common.core.WhenTagSupport;

public class WhenTag extends WhenTagSupport {
   private String test;

   public WhenTag() {
      this.init();
   }

   public void release() {
      super.release();
      this.init();
   }

   protected boolean condition() throws JspTagException {
      try {
         Object r = ExpressionEvaluatorManager.evaluate("test", this.test, Boolean.class, this, this.pageContext);
         if (r == null) {
            throw new NullAttributeException("when", "test");
         } else {
            return (Boolean)r;
         }
      } catch (JspException var2) {
         throw new JspTagException(var2.toString(), var2);
      }
   }

   public void setTest(String test) {
      this.test = test;
   }

   private void init() {
      this.test = null;
   }
}
