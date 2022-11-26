package org.apache.taglibs.standard.tag.el.fmt;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.TimeZoneSupport;

public class TimeZoneTag extends TimeZoneSupport {
   private String value_;

   public TimeZoneTag() {
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
      this.value = ExpressionEvaluatorManager.evaluate("value", this.value_, Object.class, this, this.pageContext);
   }
}
