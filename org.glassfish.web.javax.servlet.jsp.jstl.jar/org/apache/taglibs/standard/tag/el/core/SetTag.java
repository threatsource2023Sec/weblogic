package org.apache.taglibs.standard.tag.el.core;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.apache.taglibs.standard.tag.common.core.SetSupport;

public class SetTag extends SetSupport {
   private String value_;
   private String target_;
   private String property_;

   public SetTag() {
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
      this.valueSpecified = true;
   }

   public void setTarget(String target_) {
      this.target_ = target_;
   }

   public void setProperty(String property_) {
      this.property_ = property_;
   }

   private void init() {
      this.value_ = this.target_ = this.property_ = null;
   }

   private void evaluateExpressions() throws JspException {
      try {
         this.value = ExpressionUtil.evalNotNull("set", "value", this.value_, Object.class, this, this.pageContext);
      } catch (NullAttributeException var3) {
         this.value = null;
      }

      this.target = ExpressionUtil.evalNotNull("set", "target", this.target_, Object.class, this, this.pageContext);

      try {
         this.property = (String)ExpressionUtil.evalNotNull("set", "property", this.property_, String.class, this, this.pageContext);
      } catch (NullAttributeException var2) {
         this.property = null;
      }

   }
}
