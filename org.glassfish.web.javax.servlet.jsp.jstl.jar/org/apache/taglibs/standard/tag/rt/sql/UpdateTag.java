package org.apache.taglibs.standard.tag.rt.sql;

import org.apache.taglibs.standard.tag.common.sql.UpdateTagSupport;

public class UpdateTag extends UpdateTagSupport {
   public void setDataSource(Object dataSource) {
      this.rawDataSource = dataSource;
      this.dataSourceSpecified = true;
   }

   public void setSql(String sql) {
      this.sql = sql;
   }
}
