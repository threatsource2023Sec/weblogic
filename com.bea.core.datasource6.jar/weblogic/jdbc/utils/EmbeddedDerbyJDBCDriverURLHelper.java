package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class EmbeddedDerbyJDBCDriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
      } else {
         return "jdbc:derby:memory:" + info.getDbmsName();
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      Properties props = new Properties();
      if (this.isValid(info.getUserName())) {
         props.put("user", info.getUserName());
      }

      props.put("databaseName", info.getDbmsName());
      props.put("create", "true");
      return props;
   }
}
