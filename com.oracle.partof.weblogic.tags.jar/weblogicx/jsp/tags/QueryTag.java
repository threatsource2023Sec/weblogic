package weblogicx.jsp.tags;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.sql.DataSource;

public class QueryTag extends BodyTagSupport {
   private String id = "rs";
   private String sql;
   private String db;
   private Connection conn;
   private Statement stmt;
   private ResultSet rs;

   public void setId(String id) {
      this.id = id;
   }

   public String getId() {
      return this.id;
   }

   public void setSql(String sql) {
      this.sql = sql;
   }

   public String getSql() {
      return this.sql;
   }

   public void setDb(String db) {
      this.db = db;
   }

   public String getDb() {
      return this.db;
   }

   public int doStartTag() throws JspException {
      DataSource ds;
      try {
         ds = (DataSource)(new InitialContext()).lookup("java:comp/env/" + this.db);
      } catch (NamingException var10) {
         throw new JspException("Could not find datasource at " + this.db);
      }

      try {
         this.conn = ds.getConnection();
      } catch (SQLException var9) {
         throw new JspException("Could not get connection from datasource " + this.db + ": " + var9);
      }

      try {
         this.stmt = this.conn.createStatement();
      } catch (SQLException var8) {
         try {
            this.conn.close();
         } catch (SQLException var4) {
         }

         throw new JspException("Could not create statement from connection from datasource " + this.db + ": " + var8);
      }

      try {
         this.rs = this.stmt.executeQuery(this.sql);
         return 2;
      } catch (SQLException var7) {
         try {
            this.stmt.close();
         } catch (SQLException var6) {
         }

         try {
            this.conn.close();
         } catch (SQLException var5) {
         }

         throw new JspException("Could not execute query " + this.sql + " from connection from datasource " + this.db + ": " + var7);
      }
   }

   public void doInitBody() throws JspException {
      this.pageContext.setAttribute(this.id, this.rs);
   }

   public int doAfterBody() throws JspException {
      try {
         this.rs.close();
      } catch (SQLException var5) {
      }

      try {
         this.stmt.close();
      } catch (SQLException var4) {
      }

      try {
         this.conn.close();
      } catch (SQLException var3) {
      }

      this.pageContext.removeAttribute(this.id);

      try {
         this.getBodyContent().writeOut(this.getBodyContent().getEnclosingWriter());
      } catch (IOException var2) {
      }

      return 0;
   }

   public void release() {
      try {
         if (this.rs != null) {
            this.rs.close();
         }
      } catch (SQLException var4) {
      }

      try {
         if (this.stmt != null) {
            this.stmt.close();
         }
      } catch (SQLException var3) {
      }

      try {
         if (this.conn != null) {
            this.conn.close();
         }
      } catch (SQLException var2) {
      }

   }
}
