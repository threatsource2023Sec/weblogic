package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class JTOpenDB2JDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getDbmsPort())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
      } else {
         String ret = "jdbc:as400://" + info.getDbmsHost();
         if (info.getDbmsPort() != null) {
            ret = ret + ":" + info.getDbmsPort();
         }

         return ret;
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getUserName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbUsernameReqd());
      } else if (!this.isValid(info.getPassword())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPasswordReqd());
      } else {
         Properties props = new Properties();
         props.put("user", info.getUserName());
         return props;
      }
   }
}
