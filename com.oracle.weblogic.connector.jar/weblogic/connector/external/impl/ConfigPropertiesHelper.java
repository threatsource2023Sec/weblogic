package weblogic.connector.external.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;

public class ConfigPropertiesHelper {
   private final Map raConfigPropertyBeans;
   private final List wlsraConfigPropertyBeansList;

   public ConfigPropertiesHelper(Map raConfigPropertyBeans, List wlsraConfigPropertyBeansList) {
      this.raConfigPropertyBeans = raConfigPropertyBeans;
      this.wlsraConfigPropertyBeansList = wlsraConfigPropertyBeansList;
   }

   public String getPropertyValueOverridded(String propertyName) {
      Iterator var2 = this.wlsraConfigPropertyBeansList.iterator();

      ConfigPropertyBean configPropertyBean;
      do {
         if (!var2.hasNext()) {
            weblogic.j2ee.descriptor.ConfigPropertyBean configPropertyBean = (weblogic.j2ee.descriptor.ConfigPropertyBean)this.raConfigPropertyBeans.get(propertyName);
            if (configPropertyBean != null) {
               return configPropertyBean.getConfigPropertyValue();
            }

            return null;
         }

         Map map = (Map)var2.next();
         configPropertyBean = (ConfigPropertyBean)map.get(propertyName);
      } while(configPropertyBean == null || configPropertyBean.getValue() == null);

      return configPropertyBean.getValue();
   }

   public Map getConfigProperties() {
      Map properties = new HashMap();
      Set entrySet = this.raConfigPropertyBeans.entrySet();
      Iterator var3 = entrySet.iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         String key = (String)entry.getKey();
         weblogic.j2ee.descriptor.ConfigPropertyBean bean = (weblogic.j2ee.descriptor.ConfigPropertyBean)entry.getValue();
         String valueOverridded = this.getPropertyValueOverridded(key);
         properties.put(key, new ConfigPropInfoImpl(bean, valueOverridded));
      }

      return properties;
   }
}
