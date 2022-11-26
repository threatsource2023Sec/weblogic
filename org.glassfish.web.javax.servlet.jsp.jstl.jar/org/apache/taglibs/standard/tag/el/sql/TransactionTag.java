package org.apache.taglibs.standard.tag.el.sql;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.sql.TransactionTagSupport;

public class TransactionTag extends TransactionTagSupport {
   private String dataSourceEL;
   private String isolationEL;

   public void setDataSource(String dataSourceEL) {
      this.dataSourceEL = dataSourceEL;
      this.dataSourceSpecified = true;
   }

   public void setIsolation(String isolationEL) {
      this.isolationEL = isolationEL;
   }

   public int doStartTag() throws JspException {
      if (this.dataSourceEL != null) {
         this.rawDataSource = ExpressionEvaluatorManager.evaluate("dataSource", this.dataSourceEL, Object.class, this, this.pageContext);
      }

      if (this.isolationEL != null) {
         this.isolationEL = (String)ExpressionEvaluatorManager.evaluate("isolation", this.isolationEL, String.class, this, this.pageContext);
         super.setIsolation(this.isolationEL);
      }

      return super.doStartTag();
   }
}
