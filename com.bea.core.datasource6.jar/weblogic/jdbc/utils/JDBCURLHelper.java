package weblogic.jdbc.utils;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public abstract class JDBCURLHelper {
   private JDBCDriverInfo info;

   public JDBCURLHelper() {
   }

   protected JDBCURLHelper(JDBCDriverInfo info) {
      this.info = info;
   }

   protected void setJDBCDriverInfo(JDBCDriverInfo info) {
      this.info = info;
   }

   public JDBCDriverInfo getJDBCInfo() {
      return this.info;
   }

   public abstract String getURL() throws JDBCDriverInfoException;

   public abstract Properties getProperties() throws JDBCDriverInfoException;

   public boolean isValid(String checkme) {
      return checkme != null && checkme.length() > 0;
   }

   public String getOtherAttribute(String name, JDBCDriverInfo info) {
      Map otherAttributes = info.getUnknownDriverAttributes();
      name = name.toLowerCase(Locale.ENGLISH);
      if (otherAttributes != null) {
         Iterator it = otherAttributes.values().iterator();

         while(it.hasNext()) {
            try {
               JDBCDriverAttribute att = (JDBCDriverAttribute)it.next();
               if (att.getName().toLowerCase(Locale.ENGLISH).startsWith(name)) {
                  if (att.getValue() == null) {
                     if (att.isRequired() && info.isFillRequired()) {
                        return att.getName();
                     }

                     return "";
                  }

                  return att.getValue();
               }
            } catch (Exception var6) {
            }
         }
      }

      return "";
   }
}
