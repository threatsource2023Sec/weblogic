package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface JDBCPropertyOverrideMBean extends ConfigurationMBean {
   String getValue();

   void setValue(String var1);

   String getSysPropValue();

   void setSysPropValue(String var1);

   String getEncryptedValue();

   void setEncryptedValue(String var1) throws InvalidAttributeValueException;

   byte[] getEncryptedValueEncrypted();

   void setEncryptedValueEncrypted(byte[] var1) throws InvalidAttributeValueException;
}
