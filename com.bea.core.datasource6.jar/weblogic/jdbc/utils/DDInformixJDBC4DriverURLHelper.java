package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class DDInformixJDBC4DriverURLHelper extends JDBCURLHelper {
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
         return "jdbc:datadirect:informix://" + info.getDbmsHost() + ":" + info.getDbmsPort() + ";informixServer=" + this.getOtherAttribute("informixserver", info) + ";databaseName=" + info.getDbmsName();
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      Properties props = new Properties();
      props.put("serverName", info.getDbmsHost());
      props.put("portNumber", info.getDbmsPort());
      props.put("user", info.getUserName());
      props.put("databaseName", info.getDbmsName());
      props.put("informixServer", this.getOtherAttribute("informixserver", info));
      return props;
   }
}
