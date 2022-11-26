package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface MailSessionMBean extends RMCFactoryMBean {
   String getName();

   Properties getProperties();

   void setProperties(Properties var1) throws InvalidAttributeValueException;

   String getSessionUsername();

   void setSessionUsername(String var1);

   String getSessionPassword();

   void setSessionPassword(String var1);

   byte[] getSessionPasswordEncrypted();

   void setSessionPasswordEncrypted(byte[] var1);

   void setJNDIName(String var1) throws InvalidAttributeValueException;

   void setTargets(TargetMBean[] var1) throws InvalidAttributeValueException, DistributedManagementException;
}
