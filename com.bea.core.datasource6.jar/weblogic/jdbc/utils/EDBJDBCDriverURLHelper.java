package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class EDBJDBCDriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else {
         String ret = "jdbc:edb://" + info.getDbmsHost();
         if (this.isValid(info.getDbmsPort())) {
            ret = ret + ":" + info.getDbmsPort();
         }

         if (!this.isValid(info.getDbmsName())) {
            throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
         } else {
            return ret + "/" + info.getDbmsName();
         }
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      Properties props = new Properties();
      if (this.isValid(info.getUserName())) {
         props.put("user", info.getUserName());
      }

      return props;
   }
}
