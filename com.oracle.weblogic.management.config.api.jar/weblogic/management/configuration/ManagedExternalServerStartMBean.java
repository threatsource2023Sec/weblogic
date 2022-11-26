package weblogic.management.configuration;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;

public interface ManagedExternalServerStartMBean extends ConfigurationMBean {
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

   String getArguments();

   void setArguments(String var1) throws InvalidAttributeValueException;

   Properties getBootProperties();

   Properties getStartupProperties();
}
