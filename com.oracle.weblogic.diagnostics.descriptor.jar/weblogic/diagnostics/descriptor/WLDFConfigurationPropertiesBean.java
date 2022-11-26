package weblogic.diagnostics.descriptor;

public interface WLDFConfigurationPropertiesBean extends WLDFConfigurationPropertyBean {
   WLDFPropertyBean createProperty(String var1);

   WLDFPropertyBean[] getProperties();

   WLDFPropertyBean lookupProperty(String var1);

   void destroyProperty(WLDFPropertyBean var1);

   WLDFEncryptedPropertyBean createEncryptedProperty(String var1);

   WLDFEncryptedPropertyBean[] getEncryptedProperties();

   WLDFEncryptedPropertyBean lookupEncryptedProperty(String var1);

   void destroyEncryptedProperty(WLDFEncryptedPropertyBean var1);

   WLDFConfigurationPropertyBean[] getConfigurationProperties();
}
