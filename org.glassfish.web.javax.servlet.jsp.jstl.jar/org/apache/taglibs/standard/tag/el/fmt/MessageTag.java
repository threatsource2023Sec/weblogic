package org.apache.taglibs.standard.tag.el.fmt;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.MessageSupport;

public class MessageTag extends MessageSupport {
   private String key_;
   private String bundle_;

   public MessageTag() {
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

   public void setKey(String key_) {
      this.key_ = key_;
      this.keySpecified = true;
   }

   public void setBundle(String bundle_) {
      this.bundle_ = bundle_;
      this.bundleSpecified = true;
   }

   private void init() {
      this.key_ = this.bundle_ = null;
   }

   private void evaluateExpressions() throws JspException {
      if (this.keySpecified) {
         this.keyAttrValue = (String)ExpressionEvaluatorManager.evaluate("key", this.key_, String.class, this, this.pageContext);
      }

      if (this.bundleSpecified) {
         this.bundleAttrValue = (LocalizationContext)ExpressionEvaluatorManager.evaluate("bundle", this.bundle_, LocalizationContext.class, this, this.pageContext);
      }

   }
}
