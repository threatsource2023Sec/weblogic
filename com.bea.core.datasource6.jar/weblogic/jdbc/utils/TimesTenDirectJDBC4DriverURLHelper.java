package weblogic.jdbc.utils;

import java.util.Properties;

public class TimesTenDirectJDBC4DriverURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      JDBCDriverInfo info = this.getJDBCInfo();
      String DSN = null;
      if (!this.isValid(this.getOtherAttribute("dsn", info))) {
         throw new JDBCDriverInfoException("dsn");
      } else {
         return "jdbc:timesten:direct:" + this.getOtherAttribute("dsn", info);
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
