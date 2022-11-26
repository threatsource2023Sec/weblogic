package org.apache.taglibs.standard.tag.el.sql;

import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.sql.SetDataSourceTagSupport;

public class SetDataSourceTag extends SetDataSourceTagSupport {
   private String dataSourceEL;
   private String driverClassNameEL;
   private String jdbcURLEL;
   private String userNameEL;
   private String passwordEL;

   public void setDataSource(String dataSourceEL) {
      this.dataSourceEL = dataSourceEL;
      this.dataSourceSpecified = true;
   }

   public void setDriver(String driverClassNameEL) {
      this.driverClassNameEL = driverClassNameEL;
   }

   public void setUrl(String jdbcURLEL) {
      this.jdbcURLEL = jdbcURLEL;
   }

   public void setUser(String userNameEL) {
      this.userNameEL = userNameEL;
   }

   public void setPassword(String passwordEL) {
      this.passwordEL = passwordEL;
   }

   public int doStartTag() throws JspException {
      this.evaluateExpressions();
      return super.doStartTag();
   }

   private void evaluateExpressions() throws JspException {
      if (this.dataSourceEL != null) {
         this.dataSource = ExpressionEvaluatorManager.evaluate("dataSource", this.dataSourceEL, Object.class, this, this.pageContext);
      }

      if (this.driverClassNameEL != null) {
         this.driverClassName = (String)ExpressionEvaluatorManager.evaluate("driver", this.driverClassNameEL, String.class, this, this.pageContext);
      }

      if (this.jdbcURLEL != null) {
         this.jdbcURL = (String)ExpressionEvaluatorManager.evaluate("url", this.jdbcURLEL, String.class, this, this.pageContext);
      }

      if (this.userNameEL != null) {
         this.userName = (String)ExpressionEvaluatorManager.evaluate("user", this.userNameEL, String.class, this, this.pageContext);
      }

      if (this.passwordEL != null) {
         this.password = (String)ExpressionEvaluatorManager.evaluate("password", this.passwordEL, String.class, this, this.pageContext);
      }

   }
}
