package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;

public interface ServerStartMBean extends ConfigurationMBean {
   String getJavaVendor();

   void setJavaVendor(String var1) throws InvalidAttributeValueException;

   String getJavaHome();

   void setJavaHome(String var1) throws InvalidAttributeValueException;

   String getClassPath();

   void setClassPath(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getBeaHome();

   /** @deprecated */
   @Deprecated
   void setBeaHome(String var1) throws InvalidAttributeValueException;

   String getMWHome();

   void setMWHome(String var1) throws InvalidAttributeValueException;

   String getRootDirectory();

   void setRootDirectory(String var1) throws InvalidAttributeValueException;

   String getSecurityPolicyFile();

   void setSecurityPolicyFile(String var1) throws InvalidAttributeValueException;

   String getArguments();

   void setArguments(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getMaxRestartCount();

   /** @deprecated */
   @Deprecated
   void setMaxRestartCount(int var1) throws InvalidAttributeValueException;

   String getOutputFile();

   void setOutputFile(String var1);

   String getUsername();

   void setUsername(String var1) throws InvalidAttributeValueException;

   String getPassword();

   void setPassword(String var1) throws InvalidAttributeValueException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;

   Properties getBootProperties();

   Properties getStartupProperties();
}
