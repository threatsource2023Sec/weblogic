package org.apache.taglibs.standard.tag.el.fmt;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.RequestEncodingSupport;

public class RequestEncodingTag extends RequestEncodingSupport {
   private String value_;

   public RequestEncodingTag() {
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
   }

   private void init() {
      this.value_ = null;
   }

   private void evaluateExpressions() throws JspException {
      if (this.value_ != null) {
         this.value = (String)ExpressionEvaluatorManager.evaluate("value", this.value_, String.class, this, this.pageContext);
      }

   }
}
