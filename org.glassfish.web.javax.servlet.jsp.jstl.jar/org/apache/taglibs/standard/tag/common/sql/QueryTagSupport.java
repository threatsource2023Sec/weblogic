package org.apache.taglibs.standard.tag.common.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.SQLExecutionTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.sql.DataSource;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class QueryTagSupport extends BodyTagSupport implements TryCatchFinally, SQLExecutionTag {
   private String var;
   private int scope;
   protected Object rawDataSource;
   protected boolean dataSourceSpecified;
   protected String sql;
   protected int maxRows;
   protected boolean maxRowsSpecified;
   protected int startRow;
   private Connection conn;
   private List parameters;
   private boolean isPartOfTransaction;

   public QueryTagSupport() {
      this.init();
   }

   private void init() {
      this.startRow = 0;
      this.maxRows = -1;
      this.maxRowsSpecified = this.dataSourceSpecified = false;
      this.isPartOfTransaction = false;
      this.conn = null;
      this.rawDataSource = null;
      this.parameters = null;
      this.sql = null;
      this.var = null;
      this.scope = 1;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scopeName) {
      this.scope = Util.getScope(scopeName);
   }

   public void addSQLParameter(Object o) {
      if (this.parameters == null) {
         this.parameters = new ArrayList();
      }

      this.parameters.add(o);
   }

   public int doStartTag() throws JspException {
      if (!this.maxRowsSpecified) {
         Object obj = Config.find(this.pageContext, "javax.servlet.jsp.jstl.sql.maxRows");
         if (obj != null) {
            if (obj instanceof Integer) {
               this.maxRows = (Integer)obj;
            } else {
               if (!(obj instanceof String)) {
                  throw new JspException(Resources.getMessage("SQL_MAXROWS_INVALID"));
               }

               try {
                  this.maxRows = Integer.parseInt((String)obj);
               } catch (NumberFormatException var4) {
                  throw new JspException(Resources.getMessage("SQL_MAXROWS_PARSE_ERROR", (Object)((String)obj)), var4);
               }
            }
         }
      }

      try {
         this.conn = this.getConnection();
         return 2;
      } catch (SQLException var3) {
         throw new JspException(this.sql + ": " + var3.getMessage(), var3);
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
         if (this.startRow >= 0 && this.maxRows >= -1) {
            Result result = null;

            try {
               PreparedStatement ps = this.conn.prepareStatement(sqlStatement);
               this.setParameters(ps, this.parameters);
               ResultSet rs = ps.executeQuery();
               result = new ResultImpl(rs, this.startRow, this.maxRows);
               ps.close();
            } catch (Throwable var5) {
               throw new JspException(sqlStatement + ": " + var5.getMessage(), var5);
            }

            this.pageContext.setAttribute(this.var, result, this.scope);
            return 6;
         } else {
            throw new JspException(Resources.getMessage("PARAM_BAD_VALUE"));
         }
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

      this.conn = null;
      this.parameters = null;
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
