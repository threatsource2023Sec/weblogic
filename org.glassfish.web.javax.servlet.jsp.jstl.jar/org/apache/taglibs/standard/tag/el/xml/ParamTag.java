package org.apache.taglibs.standard.tag.el.xml;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.tag.common.xml.ParamSupport;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

public class ParamTag extends ParamSupport {
   private String name_;
   private String value_;

   public ParamTag() {
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

   public void setName(String name_) {
      this.name_ = name_;
   }

   public void setValue(String value_) {
      this.value_ = value_;
   }

   private void init() {
      this.name_ = this.value_ = null;
   }

   private void evaluateExpressions() throws JspException {
      this.name = (String)ExpressionUtil.evalNotNull("param", "name", this.name_, String.class, this, this.pageContext);
      this.value = ExpressionUtil.evalNotNull("param", "value", this.value_, Object.class, this, this.pageContext);
   }
}
