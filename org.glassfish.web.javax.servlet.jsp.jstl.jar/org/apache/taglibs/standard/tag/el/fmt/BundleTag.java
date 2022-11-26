package org.apache.taglibs.standard.tag.el.fmt;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;

public class BundleTag extends BundleSupport {
   private String basename_;
   private String prefix_;

   public BundleTag() {
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

   public void setPrefix(String prefix_) {
      this.prefix_ = prefix_;
   }

   private void init() {
      this.basename_ = this.prefix_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.basename = (String)ExpressionEvaluatorManager.evaluate("basename", this.basename_, String.class, this, this.pageContext);
      if (this.prefix_ != null) {
         this.prefix = (String)ExpressionEvaluatorManager.evaluate("prefix", this.prefix_, String.class, this, this.pageContext);
      }

   }
}
