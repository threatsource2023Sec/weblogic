package weblogic.connector.external.impl;

import weblogic.connector.external.RequiredConfigPropInfo;
import weblogic.j2ee.descriptor.RequiredConfigPropertyBean;

public class RequiredConfigPropInfoImpl implements RequiredConfigPropInfo {
   RequiredConfigPropertyBean configPropBean;

   public RequiredConfigPropInfoImpl(RequiredConfigPropertyBean configPropBean) {
      this.configPropBean = configPropBean;
   }

   public String getDescription() {
      String[] descriptions = this.configPropBean.getDescriptions();
      return descriptions != null && descriptions.length > 0 ? descriptions[0] : null;
   }

   public String[] getDescriptions() {
      return this.configPropBean.getDescriptions();
   }

   public String getName() {
      return this.configPropBean.getConfigPropertyName();
   }
}
