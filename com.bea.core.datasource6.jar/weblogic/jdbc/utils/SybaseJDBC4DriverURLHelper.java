package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class SybaseJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getDbmsPort())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
      } else {
         String dbmsName = info.getDbmsName();
         if (!this.isValid(dbmsName)) {
            dbmsName = "";
         } else {
            dbmsName = "/" + dbmsName;
         }

         return "jdbc:sybase:Tds:" + info.getDbmsHost() + ":" + info.getDbmsPort() + dbmsName;
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getUserName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbUsernameReqd());
      } else if (!this.isValid(info.getPassword())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPasswordReqd());
      } else if (!this.isValid(info.getDbmsPort())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
      } else {
         Properties props = new Properties();
         props.put("networkProtocol", "Tds");
         props.put("serverName", info.getDbmsHost());
         props.put("portNumber", info.getDbmsPort());
         props.put("user", info.getUserName());
         props.put("userName", info.getUserName());
         if (this.isValid(info.getDbmsName())) {
            props.put("databaseName", info.getDbmsName());
         }

         props.put("url", this.getURL());
         return props;
      }
   }
}
