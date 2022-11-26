package org.apache.taglibs.standard.tag.common.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.sql.SQLExecutionTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.sql.DataSource;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class UpdateTagSupport extends BodyTagSupport implements TryCatchFinally, SQLExecutionTag {
   private String var;
   private int scope;
   protected Object rawDataSource;
   protected boolean dataSourceSpecified;
   protected String sql;
   private Connection conn;
   private List parameters;
   private boolean isPartOfTransaction;

   public UpdateTagSupport() {
      this.init();
   }

   private void init() {
      this.rawDataSource = null;
      this.sql = null;
      this.conn = null;
      this.parameters = null;
      this.isPartOfTransaction = this.dataSourceSpecified = false;
      this.scope = 1;
      this.var = null;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scopeName) {
      this.scope = Util.getScope(scopeName);
   }

   public int doStartTag() throws JspException {
      try {
         this.conn = this.getConnection();
         return 2;
      } catch (SQLException var2) {
         throw new JspException(this.sql + ": " + var2.getMessage(), var2);
      }
   }

   public int doEndTag() throws JspException {
      String sqlStatement = null;
      if (this.sql != null) {
         sqlStatement = this.sql;
      } else if (this.bodyContent != null) {
         sqlStatement = this.bodyContent.getString();
      }

      if (sqlStatement != null && sqlStatement.trim().length() != 0) {
         int result = false;

         int result;
         try {
            PreparedStatement ps = this.conn.prepareStatement(sqlStatement);
            this.setParameters(ps, this.parameters);
            result = ps.executeUpdate();
         } catch (Throwable var4) {
            throw new JspException(sqlStatement + ": " + var4.getMessage(), var4);
         }

         if (this.var != null) {
            this.pageContext.setAttribute(this.var, result, this.scope);
         }

         return 6;
      } else {
         throw new JspTagException(Resources.getMessage("SQL_NO_STATEMENT"));
      }
   }

   public void doCatch(Throwable t) throws Throwable {
      throw t;
   }

   public void doFinally() {
      if (this.conn != null && !this.isPartOfTransaction) {
         try {
            this.conn.close();
         } catch (SQLException var2) {
         }
      }

      this.parameters = null;
      this.conn = null;
   }

   public void addSQLParameter(Object o) {
      if (this.parameters == null) {
         this.parameters = new ArrayList();
      }

      this.parameters.add(o);
   }

   private Connection getConnection() throws JspException, SQLException {
      Connection conn = null;
      this.isPartOfTransaction = false;
      TransactionTagSupport parent = (TransactionTagSupport)findAncestorWithClass(this, TransactionTagSupport.class);
      if (parent != null) {
         if (this.dataSourceSpecified) {
            throw new JspTagException(Resources.getMessage("ERROR_NESTED_DATASOURCE"));
         }

         conn = parent.getSharedConnection();
         this.isPartOfTransaction = true;
      } else {
         if (this.rawDataSource == null && this.dataSourceSpecified) {
            throw new JspException(Resources.getMessage("SQL_DATASOURCE_NULL"));
         }

         DataSource dataSource = DataSourceUtil.getDataSource(this.rawDataSource, this.pageContext);

         try {
            conn = dataSource.getConnection();
         } catch (Exception var5) {
            throw new JspException(Resources.getMessage("DATASOURCE_INVALID", (Object)var5.toString()));
         }
      }

      return conn;
   }

   private void setParameters(PreparedStatement ps, List parameters) throws SQLException {
      if (parameters != null) {
         for(int i = 0; i < parameters.size(); ++i) {
            ps.setObject(i + 1, parameters.get(i));
         }
      }

   }
}
