package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class InetMSSQLJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else {
         String ret = "jdbc:inetdae7:" + info.getDbmsHost();
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
      } else {
         Properties props = new Properties();
         props.put("user", info.getUserName());
         if (this.isValid(info.getDbmsName())) {
            props.put("db", info.getDbmsName());
         }

         return props;
      }
   }
}
