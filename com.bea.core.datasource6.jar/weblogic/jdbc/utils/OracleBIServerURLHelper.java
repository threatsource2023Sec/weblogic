package weblogic.jdbc.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class OracleBIServerURLHelper extends JDBCURLHelper {
   public String getURL() throws JDBCDriverInfoException {
      String host = null;
      String port = null;
      JDBCDriverInfo info = this.getJDBCInfo();
      Map otherAttributes = info.getUnknownDriverAttributes();
      if (otherAttributes != null) {
         Iterator it = otherAttributes.values().iterator();

         while(it.hasNext()) {
            JDBCDriverAttribute att = (JDBCDriverAttribute)it.next();
            if (att.getName() != null && att.getValue() != null) {
               if (att.getName().equals("ServerHost")) {
                  host = att.getValue();
               } else if (att.getName().equals("ServerPort")) {
                  port = att.getValue();

                  try {
                     Integer.parseInt(port);
                  } catch (NumberFormatException var11) {
                     throw new JDBCDriverInfoException("serverport");
                  }
               }
            }
         }
      }

      String ret = "jdbc:oraclebi:";
      if (host != null) {
         if (port == null) {
            port = "9703";
         }

         ret = ret + "//" + host + ":" + port + "/";
      }

      otherAttributes = info.getUnknownDriverAttributes();
      if (otherAttributes != null) {
         Iterator it = otherAttributes.values().iterator();

         while(true) {
            JDBCDriverAttribute att;
            do {
               while(true) {
                  do {
                     do {
                        if (!it.hasNext()) {
                           return ret;
                        }

                        att = (JDBCDriverAttribute)it.next();
                     } while(att.getName() == null);
                  } while(att.getValue() == null);

                  if (att.getName().equals("Ssl")) {
                     if (!att.getValue().equals("True") && !att.getValue().equals("False")) {
                        throw new JDBCDriverInfoException("ssl");
                     }

                     if (!att.getValue().equals("False")) {
                        break;
                     }
                  } else {
                     if (!att.getName().equals("TrustAnyServer")) {
                        break;
                     }

                     if (!att.getValue().equals("True") && !att.getValue().equals("False")) {
                        throw new JDBCDriverInfoException("trustanyserver");
                     }

                     if (!att.getValue().equals("False")) {
                        break;
                     }
                  }
               }

               if (att.getName().equals("LogLevel")) {
                  if (!att.getValue().equals("SEVERE") && !att.getValue().equals("WARNING") && !att.getValue().equals("INFO") && !att.getValue().equals("CONFIG") && !att.getValue().equals("FINE") && !att.getValue().equals("FINER") && !att.getValue().equals("FINEST")) {
                     throw new JDBCDriverInfoException("loglevel");
                  }
                  break;
               }

               if (att.getName().equals("PrimaryCcsPort")) {
                  try {
                     Integer.parseInt(att.getValue());
                     break;
                  } catch (NumberFormatException var10) {
                     throw new JDBCDriverInfoException("primaryccsport");
                  }
               }

               if (att.getName().equals("SecondaryCcsPort")) {
                  try {
                     Integer.parseInt(att.getValue());
                     break;
                  } catch (NumberFormatException var9) {
                     throw new JDBCDriverInfoException("secondaryccsport");
                  }
               }
            } while(att.getName().equals("ServerHost") || att.getName().equals("ServerPort"));

            ret = ret + att.getName() + "=" + att.getValue() + ";";
         }
      } else {
         return ret;
      }
   }

   public Properties getProperties() throws JDBCDriverInfoException {
      Properties props = new Properties();
      JDBCDriverInfo info = this.getJDBCInfo();
      if (this.isValid(info.getUserName())) {
         props.put("user", info.getUserName());
      }

      return props;
   }
}
