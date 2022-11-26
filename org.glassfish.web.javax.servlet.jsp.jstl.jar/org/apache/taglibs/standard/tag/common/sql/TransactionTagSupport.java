package org.apache.taglibs.standard.tag.common.sql;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.sql.DataSource;
import org.apache.taglibs.standard.resources.Resources;

public abstract class TransactionTagSupport extends TagSupport implements TryCatchFinally {
   private static final String TRANSACTION_READ_COMMITTED = "read_committed";
   private static final String TRANSACTION_READ_UNCOMMITTED = "read_uncommitted";
   private static final String TRANSACTION_REPEATABLE_READ = "repeatable_read";
   private static final String TRANSACTION_SERIALIZABLE = "serializable";
   protected Object rawDataSource;
   protected boolean dataSourceSpecified;
   private Connection conn;
   private int isolation;
   private int origIsolation;

   public TransactionTagSupport() {
      this.init();
   }

   private void init() {
      this.conn = null;
      this.dataSourceSpecified = false;
      this.rawDataSource = null;
      this.isolation = 0;
   }

   public int doStartTag() throws JspException {
      if (this.rawDataSource == null && this.dataSourceSpecified) {
         throw new JspException(Resources.getMessage("SQL_DATASOURCE_NULL"));
      } else {
         DataSource dataSource = DataSourceUtil.getDataSource(this.rawDataSource, this.pageContext);

         try {
            this.conn = dataSource.getConnection();
            this.origIsolation = this.conn.getTransactionIsolation();
            if (this.origIsolation == 0) {
               throw new JspTagException(Resources.getMessage("TRANSACTION_NO_SUPPORT"));
            } else {
               if (this.isolation != 0 && this.isolation != this.origIsolation) {
                  this.conn.setTransactionIsolation(this.isolation);
               }

               this.conn.setAutoCommit(false);
               return 1;
            }
         } catch (SQLException var3) {
            throw new JspTagException(Resources.getMessage("ERROR_GET_CONNECTION", (Object)var3.toString()), var3);
         }
      }
   }

   public int doEndTag() throws JspException {
      try {
         this.conn.commit();
         return 6;
      } catch (SQLException var2) {
         throw new JspTagException(Resources.getMessage("TRANSACTION_COMMIT_ERROR", (Object)var2.toString()), var2);
      }
   }

   public void doCatch(Throwable t) throws Throwable {
      if (this.conn != null) {
         try {
            this.conn.rollback();
         } catch (SQLException var3) {
         }
      }

      throw t;
   }

   public void doFinally() {
      if (this.conn != null) {
         try {
            if (this.isolation != 0 && this.isolation != this.origIsolation) {
               this.conn.setTransactionIsolation(this.origIsolation);
            }

            this.conn.setAutoCommit(true);
            this.conn.close();
         } catch (SQLException var2) {
         }
      }

      this.conn = null;
   }

   public void release() {
      this.init();
   }

   public void setIsolation(String iso) throws JspTagException {
      if ("read_committed".equals(iso)) {
         this.isolation = 2;
      } else if ("read_uncommitted".equals(iso)) {
         this.isolation = 1;
      } else if ("repeatable_read".equals(iso)) {
         this.isolation = 4;
      } else {
         if (!"serializable".equals(iso)) {
            throw new JspTagException(Resources.getMessage("TRANSACTION_INVALID_ISOLATION"));
         }

         this.isolation = 8;
      }

   }

   public Connection getSharedConnection() {
      return this.conn;
   }
}
