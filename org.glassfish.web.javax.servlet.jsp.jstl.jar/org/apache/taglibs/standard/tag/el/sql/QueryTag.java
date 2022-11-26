package org.apache.taglibs.standard.tag.el.sql;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.sql.QueryTagSupport;

public class QueryTag extends QueryTagSupport {
   private String dataSourceEL;
   private String sqlEL;
   private String startRowEL;
   private String maxRowsEL;

   public void setDataSource(String dataSourceEL) {
      this.dataSourceEL = dataSourceEL;
      this.dataSourceSpecified = true;
   }

   public void setStartRow(String startRowEL) {
      this.startRowEL = startRowEL;
   }

   public void setMaxRows(String maxRowsEL) {
      this.maxRowsEL = maxRowsEL;
      this.maxRowsSpecified = true;
   }

   public void setSql(String sqlEL) {
      this.sqlEL = sqlEL;
   }

   public int doStartTag() throws JspException {
      this.evaluateExpressions();
      return super.doStartTag();
   }

   private void evaluateExpressions() throws JspException {
      Integer tempInt = null;
      if (this.dataSourceEL != null) {
         this.rawDataSource = ExpressionEvaluatorManager.evaluate("dataSource", this.dataSourceEL, Object.class, this, this.pageContext);
      }

      if (this.sqlEL != null) {
         this.sql = (String)ExpressionEvaluatorManager.evaluate("sql", this.sqlEL, String.class, this, this.pageContext);
      }

      if (this.startRowEL != null) {
         tempInt = (Integer)ExpressionEvaluatorManager.evaluate("startRow", this.startRowEL, Integer.class, this, this.pageContext);
         if (tempInt != null) {
            this.startRow = tempInt;
         }
      }

      if (this.maxRowsEL != null) {
         tempInt = (Integer)ExpressionEvaluatorManager.evaluate("maxRows", this.maxRowsEL, Integer.class, this, this.pageContext);
         if (tempInt != null) {
            this.maxRows = tempInt;
         }
      }

   }
}
