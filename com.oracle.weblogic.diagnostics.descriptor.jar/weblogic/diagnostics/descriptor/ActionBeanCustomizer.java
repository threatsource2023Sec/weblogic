package weblogic.diagnostics.descriptor;

public class ActionBeanCustomizer {
   private final WLDFActionBean propsBean;

   public ActionBeanCustomizer(WLDFActionBean bean) {
      this.propsBean = bean;
   }

   public WLDFConfigurationPropertyBean[] getConfigurationProperties() {
      WLDFPropertyBean[] properties = this.propsBean.getProperties();
      WLDFEncryptedPropertyBean[] encryptedProperties = this.propsBean.getEncryptedProperties();
      WLDFArrayPropertyBean[] arrayProperties = this.propsBean.getArrayProperties();
      WLDFConfigurationPropertiesBean[] mapProperties = this.propsBean.getMapProperties();
      WLDFConfigurationPropertyBean[] allProperties = new WLDFConfigurationPropertyBean[properties.length + encryptedProperties.length + arrayProperties.length + mapProperties.length];
      System.arraycopy(properties, 0, allProperties, 0, properties.length);
      System.arraycopy(encryptedProperties, 0, allProperties, properties.length, encryptedProperties.length);
      int arrayPropsOffset = properties.length + encryptedProperties.length;
      System.arraycopy(arrayProperties, 0, allProperties, arrayPropsOffset, arrayProperties.length);
      int mapPropsOffset = arrayPropsOffset + arrayProperties.length;
      System.arraycopy(mapProperties, 0, allProperties, mapPropsOffset, mapProperties.length);
      return allProperties;
   }

   public WLDFConfigurationPropertyBean lookupConfigurationProperty(String name) {
      WLDFConfigurationPropertyBean bean = this.propsBean.lookupProperty(name);
      if (bean == null) {
         bean = this.propsBean.lookupEncryptedProperty(name);
         if (bean == null) {
            bean = this.propsBean.lookupArrayProperty(name);
            if (bean == null) {
               bean = this.propsBean.lookupMapProperty(name);
            }
         }
      }

      return (WLDFConfigurationPropertyBean)bean;
   }
}
