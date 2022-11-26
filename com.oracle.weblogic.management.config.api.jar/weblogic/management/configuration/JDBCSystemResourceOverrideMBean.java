package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface JDBCSystemResourceOverrideMBean extends ConfigurationMBean {
   String getDataSourceName();

   void setDataSourceName(String var1);

   String getURL();

   void setURL(String var1);

   String getUser();

   void setUser(String var1);

   String getPassword();

   void setPassword(String var1) throws InvalidAttributeValueException;

   byte[] getPasswordEncrypted();

   void setPasswordEncrypted(byte[] var1) throws InvalidAttributeValueException;

   int getInitialCapacity();

   void setInitialCapacity(int var1);

   int getMinCapacity();

   void setMinCapacity(int var1);

   int getMaxCapacity();

   void setMaxCapacity(int var1);

   JDBCPropertyOverrideMBean[] getJDBCPropertyOverrides();

   JDBCPropertyOverrideMBean lookupJDBCPropertyOverride(String var1);

   JDBCPropertyOverrideMBean createJDBCPropertyOverride(String var1);

   void destroyJDBCPropertyOverride(JDBCPropertyOverrideMBean var1);
}
