package org.apache.taglibs.standard.tag.rt.sql;

import org.apache.taglibs.standard.tag.common.sql.SetDataSourceTagSupport;

public class SetDataSourceTag extends SetDataSourceTagSupport {
   public void setDataSource(Object dataSource) {
      this.dataSource = dataSource;
      this.dataSourceSpecified = true;
   }

   public void setDriver(String driverClassName) {
      this.driverClassName = driverClassName;
   }

   public void setUrl(String jdbcURL) {
      this.jdbcURL = jdbcURL;
   }

   public void setUser(String userName) {
      this.userName = userName;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
