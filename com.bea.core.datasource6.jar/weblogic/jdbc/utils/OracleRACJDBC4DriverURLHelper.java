package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public class OracleRACJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getDbmsPort())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
      } else {
         String serviceName = this.getOtherAttribute("servicename", info);
         String protocol = this.getOtherAttribute("protocol", info);
         if (!this.isValid(protocol)) {
            protocol = "TCP";
         }

         String drcpConnectionClass = this.getOtherAttribute("DRCPConnectionClass", info);
         StringBuffer buff = new StringBuffer("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=" + protocol + ")(HOST=");
         buff.append(info.getDbmsHost());
         buff.append(")(PORT=");
         buff.append(info.getDbmsPort());
         buff.append(")))");
         if (this.isValid(serviceName) || this.isValid(info.getDbmsName())) {
            buff.append("(CONNECT_DATA=");
         }

         if (this.isValid(serviceName)) {
            buff.append("(SERVICE_NAME=");
            buff.append(serviceName);
            buff.append(")");
         }

         if (this.isValid(drcpConnectionClass)) {
            buff.append("(SERVER=POOLED)");
         }

         if (this.isValid(info.getDbmsName())) {
            buff.append("(INSTANCE_NAME=");
            buff.append(info.getDbmsName());
            buff.append(")");
         }

         if (this.isValid(serviceName) || this.isValid(info.getDbmsName())) {
            buff.append(")");
         }

         buff.append(")");
         return buff.toString();
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (info.getDbmsName() == null) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().oracleUserIdReqd());
      } else {
         Properties props = new Properties();
         String userName = info.getUserName();
         if (userName != null) {
            props.put("user", userName);
         }

         String drcpConnectionClass = this.getOtherAttribute("DRCPConnectionClass", info);
         if (this.isValid(drcpConnectionClass)) {
            props.put("oracle.jdbc.DRCPConnectionClass", drcpConnectionClass);
         }

         return props;
      }
   }
}
