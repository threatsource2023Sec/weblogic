package org.apache.taglibs.standard.tag.el.core;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.tag.common.core.RedirectSupport;

public class RedirectTag extends RedirectSupport {
   private String url_;
   private String context_;

   public RedirectTag() {
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

   public void setUrl(String url_) {
      this.url_ = url_;
   }

   public void setContext(String context_) {
      this.context_ = context_;
   }

   private void init() {
      this.url_ = this.context_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.url = (String)ExpressionUtil.evalNotNull("redirect", "url", this.url_, String.class, this, this.pageContext);
      this.context = (String)ExpressionUtil.evalNotNull("redirect", "context", this.context_, String.class, this, this.pageContext);
   }
}
