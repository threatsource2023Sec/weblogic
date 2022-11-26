package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;

public interface MailSessionOverrideMBean extends ConfigurationMBean {
   Properties getProperties();

   void setProperties(Properties var1) throws InvalidAttributeValueException;

   String getSessionUsername();

   void setSessionUsername(String var1);

   String getSessionPassword();

   void setSessionPassword(String var1);

   byte[] getSessionPasswordEncrypted();

   void setSessionPasswordEncrypted(byte[] var1);
}
