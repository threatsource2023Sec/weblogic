package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class DerbyJDBCDriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else {
         String ret = "jdbc:derby://" + info.getDbmsHost();
         if (this.isValid(info.getDbmsPort())) {
            ret = ret + ":" + info.getDbmsPort();
         }

         if (!this.isValid(info.getDbmsName())) {
            throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
         } else {
            ret = ret + "/" + info.getDbmsName();
            return ret + ";ServerName=" + info.getDbmsHost() + ";databaseName=" + info.getDbmsName() + ";create=true";
         }
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      Properties props = new Properties();
      if (this.isValid(info.getUserName())) {
         props.put("user", info.getUserName());
      }

      if (this.isValid(info.getDbmsPort())) {
         props.put("portNumber", info.getDbmsPort());
      }

      props.put("databaseName", info.getDbmsName() + ";create=true");
      props.put("serverName", info.getDbmsHost());
      return props;
   }
}
