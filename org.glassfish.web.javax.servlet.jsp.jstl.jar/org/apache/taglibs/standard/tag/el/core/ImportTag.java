package org.apache.taglibs.standard.tag.el.core;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.tag.common.core.ImportSupport;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;

public class ImportTag extends ImportSupport {
   private String context_;
   private String charEncoding_;
   private String url_;

   public ImportTag() {
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

   public void setCharEncoding(String charEncoding_) {
      this.charEncoding_ = charEncoding_;
   }

   private void init() {
      this.url_ = this.context_ = this.charEncoding_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.url = (String)ExpressionUtil.evalNotNull("import", "url", this.url_, String.class, this, this.pageContext);
      if (this.url != null && !this.url.equals("")) {
         this.context = (String)ExpressionUtil.evalNotNull("import", "context", this.context_, String.class, this, this.pageContext);
         this.charEncoding = (String)ExpressionUtil.evalNotNull("import", "charEncoding", this.charEncoding_, String.class, this, this.pageContext);
      } else {
         throw new NullAttributeException("import", "url");
      }
   }
}
