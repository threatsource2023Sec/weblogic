package org.apache.taglibs.standard.tag.el.core;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.tag.common.core.UrlSupport;

public class UrlTag extends UrlSupport {
   private String value_;
   private String context_;

   public UrlTag() {
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

   public void setContext(String context_) {
      this.context_ = context_;
   }

   private void init() {
      this.value_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.value = (String)ExpressionUtil.evalNotNull("url", "value", this.value_, String.class, this, this.pageContext);
      this.context = (String)ExpressionUtil.evalNotNull("url", "context", this.context_, String.class, this, this.pageContext);
   }
}
