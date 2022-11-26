package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class InformixJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getDbmsPort())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
      } else if (!this.isValid(info.getUserName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbUsernameReqd());
      } else if (!this.isValid(info.getPassword())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPasswordReqd());
      } else if (!this.isValid(info.getDbmsName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
      } else if (!this.isValid(this.getOtherAttribute("informixserver", info))) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().informixSvrNameReqd());
      } else {
         return "jdbc:informix-sqli://" + info.getDbmsHost() + ":" + info.getDbmsPort() + "/" + info.getDbmsName() + ":informixServer=" + this.getOtherAttribute("informixserver", info);
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo jdbcdriverinfo = this.getJDBCInfo();
      Properties properties = new Properties();
      properties.put("user", jdbcdriverinfo.getUserName());
      properties.put("url", this.getURL());
      properties.put("portNumber", jdbcdriverinfo.getDbmsPort());
      properties.put("databaseName", jdbcdriverinfo.getDbmsName());
      properties.put("ifxIFXHOST", jdbcdriverinfo.getDbmsHost());
      properties.put("serverName", this.getOtherAttribute("informixserver", jdbcdriverinfo));
      return properties;
   }
}
