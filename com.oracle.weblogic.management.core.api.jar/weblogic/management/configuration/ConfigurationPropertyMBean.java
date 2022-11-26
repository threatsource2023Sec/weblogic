package weblogic.management.configuration;

public interface ConfigurationPropertyMBean extends ConfigurationMBean {
   boolean isEncryptValueRequired();

   void setEncryptValueRequired(boolean var1);

   String getValue();

   void setValue(String var1);

   String getEncryptedValue();

   void setEncryptedValue(String var1);

   byte[] getEncryptedValueEncrypted();
}
