package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class DDMSSQLJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else {
         String ret = "jdbc:datadirect:sqlserver://" + info.getDbmsHost();
         if (this.isValid(info.getDbmsPort())) {
            ret = ret + ":" + info.getDbmsPort() + ";allowPortWithNamedInstance=true";
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
         props.put("serverName", info.getDbmsHost());
         if (this.isValid(info.getDbmsPort())) {
            props.put("portNumber", info.getDbmsPort());
         }

         props.put("user", info.getUserName());
         if (this.isValid(info.getDbmsName())) {
            props.put("databaseName", info.getDbmsName());
         }

         return props;
      }
   }
}
