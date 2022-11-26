package weblogic.diagnostics.descriptor;

public interface WLDFActionBean extends WLDFNotificationBean {
   String getType();

   void setType(String var1);

   WLDFPropertyBean createProperty(String var1);

   WLDFPropertyBean[] getProperties();

   WLDFPropertyBean lookupProperty(String var1);

   void destroyProperty(WLDFPropertyBean var1);

   WLDFEncryptedPropertyBean createEncryptedProperty(String var1);

   WLDFEncryptedPropertyBean[] getEncryptedProperties();

   WLDFEncryptedPropertyBean lookupEncryptedProperty(String var1);

   void destroyEncryptedProperty(WLDFEncryptedPropertyBean var1);

   WLDFConfigurationPropertiesBean createMapProperty(String var1);

   WLDFConfigurationPropertiesBean lookupMapProperty(String var1);

   WLDFConfigurationPropertiesBean[] getMapProperties();

   void destroyMapProperty(WLDFConfigurationPropertiesBean var1);

   WLDFArrayPropertyBean createArrayProperty(String var1);

   WLDFArrayPropertyBean[] getArrayProperties();

   WLDFArrayPropertyBean lookupArrayProperty(String var1);

   void destroyArrayProperty(WLDFArrayPropertyBean var1);

   WLDFConfigurationPropertyBean[] getConfigurationProperties();

   WLDFConfigurationPropertyBean lookupConfigurationProperty(String var1);
}
