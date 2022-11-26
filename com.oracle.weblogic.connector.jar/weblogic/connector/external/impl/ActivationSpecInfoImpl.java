package weblogic.connector.external.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weblogic.connector.external.ActivationSpecInfo;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.j2ee.descriptor.ActivationSpecBean;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.RequiredConfigPropertyBean;

public class ActivationSpecInfoImpl implements ActivationSpecInfo {
   private ActivationSpecBean activationSpecBean;
   private PropertyNameNormalizer propertyNameNormalizer;

   public ActivationSpecInfoImpl(PropertyNameNormalizer propertyNameNormalizer, ActivationSpecBean activationSpecBean) {
      this.activationSpecBean = activationSpecBean;
      this.propertyNameNormalizer = propertyNameNormalizer;
   }

   public String getActivationSpecClass() {
      return this.activationSpecBean.getActivationSpecClass();
   }

   public List getRequiredProps() {
      List configPropInfoList = new ArrayList();
      RequiredConfigPropertyBean[] configPropBeans = this.activationSpecBean.getRequiredConfigProperties();
      if (configPropBeans != null) {
         for(int idx = 0; idx < configPropBeans.length; ++idx) {
            RequiredConfigPropInfoImpl configPropInfo = new RequiredConfigPropInfoImpl(configPropBeans[idx]);
            configPropInfoList.add(configPropInfo);
         }
      }

      return configPropInfoList;
   }

   public Map getConfigProps() {
      Map propMap = new HashMap();
      ConfigPropertyBean[] configProperties = this.activationSpecBean.getConfigProperties();
      if (configProperties != null) {
         ConfigPropertyBean[] var3 = configProperties;
         int var4 = configProperties.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ConfigPropertyBean configPropertyBean = var3[var5];
            if (!configPropertyBean.isConfigPropertyIgnore()) {
               ConfigPropInfoImpl configPropInfo = new ConfigPropInfoImpl(configPropertyBean, configPropertyBean.getConfigPropertyValue());
               String name = this.propertyNameNormalizer.normalize(configPropertyBean.getConfigPropertyName());
               propMap.put(name, configPropInfo);
            }
         }
      }

      return Collections.unmodifiableMap(propMap);
   }
}
