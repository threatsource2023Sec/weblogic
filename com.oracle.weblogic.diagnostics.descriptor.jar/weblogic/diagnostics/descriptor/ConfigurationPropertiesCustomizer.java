package weblogic.diagnostics.descriptor;

public class ConfigurationPropertiesCustomizer {
   private WLDFConfigurationPropertiesBean propsBean;

   public ConfigurationPropertiesCustomizer(WLDFConfigurationPropertiesBean bean) {
      this.propsBean = bean;
   }

   public WLDFConfigurationPropertyBean[] getConfigurationProperties() {
      WLDFPropertyBean[] properties = this.propsBean.getProperties();
      WLDFEncryptedPropertyBean[] encryptedProperties = this.propsBean.getEncryptedProperties();
      WLDFConfigurationPropertyBean[] allProperties = new WLDFConfigurationPropertyBean[properties.length + encryptedProperties.length];
      System.arraycopy(properties, 0, allProperties, 0, properties.length);
      System.arraycopy(encryptedProperties, 0, allProperties, properties.length, encryptedProperties.length);
      return allProperties;
   }
}
