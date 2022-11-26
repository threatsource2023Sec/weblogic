package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class DB2JDBC2DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
      } else {
         return "jdbc:db2:" + info.getDbmsName();
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
      } else if (!this.isValid(info.getUserName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbUsernameReqd());
      } else if (!this.isValid(info.getPassword())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPasswordReqd());
      } else {
         Properties props = new Properties();
         props.put("user", info.getUserName());
         props.put("databaseName", info.getDbmsName());
         return props;
      }
   }
}
