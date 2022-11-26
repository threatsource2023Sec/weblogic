package org.apache.taglibs.standard.tag.common.sql;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class DriverTag extends TagSupport {
   private static final String DRIVER_CLASS_NAME = "javax.servlet.jsp.jstl.sql.driver";
   private static final String JDBC_URL = "javax.servlet.jsp.jstl.sql.jdbcURL";
   private static final String USER_NAME = "javax.servlet.jsp.jstl.sql.userName";
   private static final String PASSWORD = "javax.servlet.jsp.jstl.sql.password";
   private String driverClassName;
   private String jdbcURL;
   private int scope = 1;
   private String userName;
   private String var;

   public void setDriver(String driverClassName) {
      this.driverClassName = driverClassName;
   }

   public void setJdbcURL(String jdbcURL) {
      this.jdbcURL = jdbcURL;
   }

   public void setScope(String scopeName) {
      if ("page".equals(scopeName)) {
         this.scope = 1;
      } else if ("request".equals(scopeName)) {
         this.scope = 2;
      } else if ("session".equals(scopeName)) {
         this.scope = 3;
      } else if ("application".equals(scopeName)) {
         this.scope = 4;
      }

   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public int doStartTag() throws JspException {
      DataSourceWrapper ds = new DataSourceWrapper();

      try {
         ds.setDriverClassName(this.getDriverClassName());
      } catch (Exception var3) {
         throw new JspTagException("Invalid driver class name: " + var3.toString(), var3);
      }

      ds.setJdbcURL(this.getJdbcURL());
      ds.setUserName(this.getUserName());
      ds.setPassword(this.getPassword());
      this.pageContext.setAttribute(this.var, ds, this.scope);
      return 0;
   }

   private String getDriverClassName() {
      if (this.driverClassName != null) {
         return this.driverClassName;
      } else {
         ServletContext application = this.pageContext.getServletContext();
         return application.getInitParameter("javax.servlet.jsp.jstl.sql.driver");
      }
   }

   private String getJdbcURL() {
      if (this.jdbcURL != null) {
         return this.jdbcURL;
      } else {
         ServletContext application = this.pageContext.getServletContext();
         return application.getInitParameter("javax.servlet.jsp.jstl.sql.jdbcURL");
      }
   }

   private String getUserName() {
      if (this.userName != null) {
         return this.userName;
      } else {
         ServletContext application = this.pageContext.getServletContext();
         return application.getInitParameter("javax.servlet.jsp.jstl.sql.userName");
      }
   }

   private String getPassword() {
      ServletContext application = this.pageContext.getServletContext();
      return application.getInitParameter("javax.servlet.jsp.jstl.sql.password");
   }
}
