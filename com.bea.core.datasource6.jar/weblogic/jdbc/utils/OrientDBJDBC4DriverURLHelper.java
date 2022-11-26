package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class OrientDBJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getDbmsName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
      } else {
         return "jdbc:orient:remote:" + info.getDbmsHost() + "/" + info.getDbmsName();
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      Properties props = new Properties();
      JDBCDriverInfo info = this.getJDBCInfo();
      if (this.isValid(info.getUserName())) {
         props.put("user", info.getUserName());
      }

      return props;
   }
}
