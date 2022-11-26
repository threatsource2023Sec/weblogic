package org.apache.taglibs.standard.tag.el.xml;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.tag.common.xml.ExprSupport;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

public class ExprTag extends ExprSupport {
   private String escapeXml_;

   public ExprTag() {
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

   public void setEscapeXml(String escapeXml_) {
      this.escapeXml_ = escapeXml_;
   }

   private void init() {
      this.escapeXml_ = null;
   }

   private void evaluateExpressions() throws JspException {
      if (this.escapeXml_ != null) {
         Boolean b = (Boolean)ExpressionUtil.evalNotNull("out", "escapeXml", this.escapeXml_, Boolean.class, this, this.pageContext);
         if (b == null) {
            this.escapeXml = false;
         } else {
            this.escapeXml = b;
         }
      }

   }
}
