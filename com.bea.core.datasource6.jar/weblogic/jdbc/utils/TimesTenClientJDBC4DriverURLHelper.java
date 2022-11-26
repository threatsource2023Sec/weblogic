package weblogic.jdbc.utils;

import java.util.Properties;

public class TimesTenClientJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      String DSN = null;
      String TTC_SERVER = null;
      String TCP_PORT = null;
      String TTC_SERVER_DSN = null;
      if (this.isValid(this.getOtherAttribute("dsn", info))) {
         DSN = this.getOtherAttribute("dsn", info);
      }

      if (this.isValid(this.getOtherAttribute("ttc_server", info))) {
         TTC_SERVER = this.getOtherAttribute("ttc_server", info);
      }

      if (this.isValid(this.getOtherAttribute("tcp_port", info))) {
         TCP_PORT = this.getOtherAttribute("tcp_port", info);
      }

      if (this.isValid(this.getOtherAttribute("ttc_server_dsn", info))) {
         TTC_SERVER_DSN = this.getOtherAttribute("ttc_server_dsn", info);
      }

      if (TTC_SERVER == null && TCP_PORT == null && TTC_SERVER_DSN == null) {
         if (DSN == null) {
            throw new JDBCDriverInfoException("dsn");
         } else {
            return "jdbc:timesten:client:" + DSN;
         }
      } else if (TTC_SERVER == null) {
         throw new JDBCDriverInfoException("ttc_server");
      } else if (TCP_PORT == null) {
         throw new JDBCDriverInfoException("tcp_port");
      } else if (TTC_SERVER_DSN == null) {
         throw new JDBCDriverInfoException("ttc_server_dsn");
      } else if (DSN != null) {
         throw new JDBCDriverInfoException("dsn");
      } else {
         return "jdbc:timesten:client:TTC_SERVER=" + TTC_SERVER + ";TCP_PORT=" + TCP_PORT + ";TTC_SERVER_DSN=" + TTC_SERVER_DSN;
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
