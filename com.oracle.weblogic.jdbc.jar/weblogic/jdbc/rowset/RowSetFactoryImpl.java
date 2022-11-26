package weblogic.jdbc.rowset;

import javax.sql.DataSource;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.WebRowSet;

public final class RowSetFactoryImpl extends RowSetFactory {
   String dataSourceName = null;
   String username = null;
   String password = null;
   String url = null;
   DataSource dataSource = null;

   public WLCachedRowSet newCachedRowSet() {
      CachedRowSetImpl crs = new CachedRowSetImpl();
      if (this.dataSourceName != null) {
         crs.setDataSourceName(this.dataSourceName);
      }

      if (this.username != null) {
         crs.setUsername(this.username);
      }

      if (this.password != null) {
         crs.setPassword(this.password);
      }

      if (this.url != null) {
         crs.setUrl(this.url);
      }

      if (this.dataSource != null) {
         crs.setDataSource(this.dataSource);
      }

      return crs;
   }

   public JoinRowSet newJoinRowSet() {
      return new JoinRowSetImpl();
   }

   public JdbcRowSet newJdbcRowSet() {
      return new JdbcRowSetImpl();
   }

   public FilteredRowSet newFilteredRowSet() {
      return new FilteredRowSetImpl();
   }

   public WebRowSet newWebRowSet() {
      return new WebRowSetImpl();
   }

   public String getDataSourceName() {
      return this.dataSourceName;
   }

   public void setDataSourceName(String s) {
      this.dataSourceName = s;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String s) {
      this.username = s;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String s) {
      this.password = s;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String s) {
      this.url = s;
   }

   public DataSource getDataSource() {
      return this.dataSource;
   }

   public void setDataSource(DataSource ds) {
      this.dataSource = ds;
   }
}
