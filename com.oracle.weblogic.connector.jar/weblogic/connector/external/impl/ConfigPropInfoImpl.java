package weblogic.connector.external.impl;

import weblogic.connector.external.ConfigPropInfo;
import weblogic.j2ee.descriptor.ConfigPropertyBean;

public class ConfigPropInfoImpl implements ConfigPropInfo {
   ConfigPropertyBean configPropertyBean;
   String value = null;

   public ConfigPropInfoImpl(ConfigPropertyBean configPropertyBean, String value) {
      this.configPropertyBean = configPropertyBean;
      this.value = value;
   }

   public String getDescription() {
      return this.configPropertyBean.getDescriptions() != null && this.configPropertyBean.getDescriptions().length > 0 ? this.configPropertyBean.getDescriptions()[0] : null;
   }

   public String[] getDescriptions() {
      return this.configPropertyBean.getDescriptions();
   }

   public String getName() {
      return this.configPropertyBean.getConfigPropertyName();
   }

   public String getType() {
      return this.configPropertyBean.getConfigPropertyType();
   }

   public String getValue() {
      return this.value;
   }

   public ConfigPropertyBean getConfigPropertyBean() {
      return this.configPropertyBean;
   }

   public boolean isConfidential() {
      return this.configPropertyBean.isConfigPropertyConfidential();
   }

   public boolean isDynamicUpdatable() {
      return this.configPropertyBean.isConfigPropertySupportsDynamicUpdates();
   }

   public String toString() {
      return "[" + this.getName() + "=[" + (this.isConfidential() ? "******" : this.getValue()) + "]; type: " + this.getType() + "; isConfidential:" + this.isConfidential() + "; isDynamicUpdatable:" + this.isDynamicUpdatable() + "]";
   }
}
