package org.apache.taglibs.standard.tag.el.fmt;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.SetBundleSupport;

public class SetBundleTag extends SetBundleSupport {
   private String basename_;

   public SetBundleTag() {
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

   public void setBasename(String basename_) {
      this.basename_ = basename_;
   }

   private void init() {
      this.basename_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.basename = (String)ExpressionEvaluatorManager.evaluate("basename", this.basename_, String.class, this, this.pageContext);
   }
}
