package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class MSSQLJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getDbmsPort())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
      } else {
         return "jdbc:microsoft:sqlserver://" + info.getDbmsHost() + ":" + info.getDbmsPort();
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getUserName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbUsernameReqd());
      } else {
         Properties props = new Properties();
         props.put("serverName", info.getDbmsHost());
         props.put("dataSourceName", "SQL2000JDBC");
         props.put("user", info.getUserName());
         props.put("userName", info.getUserName());
         if (this.isValid(info.getDbmsName())) {
            props.put("databaseName", info.getDbmsName());
         }

         props.put("selectMethod", "cursor");
         props.put("url", this.getURL());
         return props;
      }
   }
}
