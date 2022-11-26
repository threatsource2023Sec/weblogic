package org.apache.taglibs.standard.tag.el.sql;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.sql.ParamTagSupport;

public class ParamTag extends ParamTagSupport {
   private String valueEL;

   public void setValue(String valueEL) {
      this.valueEL = valueEL;
   }

   public int doStartTag() throws JspException {
      if (this.valueEL != null) {
         this.value = ExpressionEvaluatorManager.evaluate("value", this.valueEL, Object.class, this, this.pageContext);
      }

      return super.doStartTag();
   }
}
