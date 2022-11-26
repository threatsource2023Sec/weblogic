package weblogic.jdbc.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class OracleUCPHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      throw new JDBCDriverInfoException("getURL not suported for UCP");
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      Properties props = new Properties();
      JDBCDriverInfo info = this.getJDBCInfo();
      if (this.isValid(info.getUserName())) {
         props.put("user", info.getUserName());
      }

      Map otherAttributes = info.getUnknownDriverAttributes();
      otherAttributes = info.getUnknownDriverAttributes();
      if (otherAttributes != null) {
         Iterator it = otherAttributes.values().iterator();

         while(it.hasNext()) {
            JDBCDriverAttribute att = (JDBCDriverAttribute)it.next();
            if (att.getName() != null && att.getValue() != null) {
               props.put(att.getName(), att.getValue());
            }
         }
      }

      return props;
   }
}
