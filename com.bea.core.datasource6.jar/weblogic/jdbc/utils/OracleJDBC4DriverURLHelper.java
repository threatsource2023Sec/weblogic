package weblogic.jdbc.utils;

import java.util.Properties;
import weblogic.jdbc.common.internal.JDBCUtil;

public abstract class OracleJDBC4DriverURLHelper extends JDBCURLHelper {
   protected abstract String getSeparator();

   protected abstract String getInitial();

   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      if (!this.isValid(info.getDbmsHost())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbHostReqd());
      } else if (!this.isValid(info.getDbmsPort())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbPortReqd());
      } else if (!this.isValid(info.getDbmsName())) {
         throw new JDBCDriverInfoException(JDBCUtil.getTextFormatter().dbNameReqd());
      } else {
         StringBuffer buff = new StringBuffer("jdbc:oracle:thin");
         buff.append(this.getInitial());
         buff.append(info.getDbmsHost());
         buff.append(":");
         buff.append(info.getDbmsPort());
         buff.append(this.getSeparator());
         buff.append(info.getDbmsName());
         String drcpConnectionClass = this.getOtherAttribute("DRCPConnectionClass", info);
         if (this.isValid(drcpConnectionClass)) {
            buff.append(":POOLED");
         }

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

   public static class ServiceHelper extends OracleJDBC4DriverURLHelper {
      protected String getSeparator() {
         return "/";
      }

      protected String getInitial() {
         return ":@//";
      }
   }

   public static class SIDHelper extends OracleJDBC4DriverURLHelper {
      protected String getSeparator() {
         return ":";
      }

      protected String getInitial() {
         return ":@";
      }
   }
}
