package org.apache.taglibs.standard.tag.el.fmt;

import java.util.Date;
import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.FormatDateSupport;

public class FormatDateTag extends FormatDateSupport {
   private String value_;
   private String type_;
   private String dateStyle_;
   private String timeStyle_;
   private String pattern_;
   private String timeZone_;

   public FormatDateTag() {
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

   public void setType(String type_) {
      this.type_ = type_;
   }

   public void setDateStyle(String dateStyle_) {
      this.dateStyle_ = dateStyle_;
   }

   public void setTimeStyle(String timeStyle_) {
      this.timeStyle_ = timeStyle_;
   }

   public void setPattern(String pattern_) {
      this.pattern_ = pattern_;
   }

   public void setTimeZone(String timeZone_) {
      this.timeZone_ = timeZone_;
   }

   private void init() {
      this.value_ = this.type_ = this.dateStyle_ = this.timeStyle_ = this.pattern_ = this.timeZone_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.value = (Date)ExpressionEvaluatorManager.evaluate("value", this.value_, Date.class, this, this.pageContext);
      if (this.type_ != null) {
         this.type = (String)ExpressionEvaluatorManager.evaluate("type", this.type_, String.class, this, this.pageContext);
      }

      if (this.dateStyle_ != null) {
         this.dateStyle = (String)ExpressionEvaluatorManager.evaluate("dateStyle", this.dateStyle_, String.class, this, this.pageContext);
      }

      if (this.timeStyle_ != null) {
         this.timeStyle = (String)ExpressionEvaluatorManager.evaluate("timeStyle", this.timeStyle_, String.class, this, this.pageContext);
      }

      if (this.pattern_ != null) {
         this.pattern = (String)ExpressionEvaluatorManager.evaluate("pattern", this.pattern_, String.class, this, this.pageContext);
      }

      if (this.timeZone_ != null) {
         this.timeZone = ExpressionEvaluatorManager.evaluate("timeZone", this.timeZone_, Object.class, this, this.pageContext);
      }

   }
}
