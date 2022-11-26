package org.apache.taglibs.standard.tag.el.fmt;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.ParamSupport;

public class ParamTag extends ParamSupport {
   private String value_;

   public ParamTag() {
      this.init();
   }

   public int doStartTag() throws JspException {
      this.evaluateExpressions();
      return super.doStartTag();
   }

   public void release() {
      super.release();
      this.init();
   }

   public void setValue(String value_) {
      this.value_ = value_;
      this.valueSpecified = true;
   }

   private void init() {
      this.value_ = null;
   }

   private void evaluateExpressions() throws JspException {
      if (this.value_ != null) {
         this.value = ExpressionEvaluatorManager.evaluate("value", this.value_, Object.class, this, this.pageContext);
      }

   }
}
