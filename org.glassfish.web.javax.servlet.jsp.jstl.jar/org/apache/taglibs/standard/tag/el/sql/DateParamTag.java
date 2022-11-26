package org.apache.taglibs.standard.tag.el.sql;

import java.util.Date;
import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.sql.DateParamTagSupport;

public class DateParamTag extends DateParamTagSupport {
   private String valueEL;
   private String typeEL;

   public void setValue(String valueEL) {
      this.valueEL = valueEL;
   }

   public void setType(String typeEL) {
      this.typeEL = typeEL;
   }

   public int doStartTag() throws JspException {
      this.evaluateExpressions();
      return super.doStartTag();
   }

   private void evaluateExpressions() throws JspException {
      if (this.valueEL != null) {
         this.value = (Date)ExpressionEvaluatorManager.evaluate("value", this.valueEL, Date.class, this, this.pageContext);
      }

      if (this.typeEL != null) {
         this.type = (String)ExpressionEvaluatorManager.evaluate("type", this.typeEL, String.class, this, this.pageContext);
      }

   }
}
