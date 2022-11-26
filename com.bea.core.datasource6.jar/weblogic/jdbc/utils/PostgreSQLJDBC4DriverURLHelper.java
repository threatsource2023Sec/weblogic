package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class PostgreSQLJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getDbmsPort())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
      } else if (!this.isValid(info.getDbmsName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
      } else {
         return "jdbc:postgresql://" + info.getDbmsHost() + ":" + info.getDbmsPort() + "/" + info.getDbmsName();
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getUserName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbUsernameReqd());
      } else {
         Properties props = new Properties();
         props.put("user", info.getUserName());
         return props;
      }
   }
}
