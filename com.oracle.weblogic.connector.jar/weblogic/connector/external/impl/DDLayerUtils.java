package weblogic.connector.external.impl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import weblogic.connector.external.ConfigPropInfo;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;

public class DDLayerUtils {
   public static Hashtable mergeConfigProperties(ConfigPropertyBean[] raConfigProps, ConfigPropertiesBean[] wlraConfigProps, PropertyNameNormalizer propertyNameNormalizer) {
      Hashtable result = new Hashtable();
      if (raConfigProps != null && propertyNameNormalizer != null) {
         int len = 0;
         if (wlraConfigProps != null) {
            len = wlraConfigProps.length;
         }

         Map[] wlsPropertyOverridesList = new HashMap[len];

         for(int i = 0; i < len; ++i) {
            wlsPropertyOverridesList[i] = getWLRAConfigPropertyOverrides(wlraConfigProps[i], propertyNameNormalizer);
         }

         ConfigPropertyBean[] var16 = raConfigProps;
         int var7 = raConfigProps.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ConfigPropertyBean raPropertyBean = var16[var8];
            if (!raPropertyBean.isConfigPropertyIgnore()) {
               String name = propertyNameNormalizer.normalize(raPropertyBean.getConfigPropertyName());
               String value = raPropertyBean.getConfigPropertyValue();
               HashMap[] var12 = wlsPropertyOverridesList;
               int var13 = wlsPropertyOverridesList.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Map wlsPropertyOverrides = var12[var14];
                  if (wlsPropertyOverrides.containsKey(name)) {
                     value = (String)wlsPropertyOverrides.get(name);
                  }
               }

               ConfigPropInfo configPropInfo = new ConfigPropInfoImpl(raPropertyBean, value);
               result.put(name, configPropInfo);
            }
         }

         return result;
      } else {
         return result;
      }
   }

   private static Map getWLRAConfigPropertyOverrides(ConfigPropertiesBean wlsConfiPropertiesBean, PropertyNameNormalizer propertyNameNormalizer) {
      Map wlraOverrides = new HashMap();
      if (wlsConfiPropertiesBean == null) {
         return wlraOverrides;
      } else {
         weblogic.j2ee.descriptor.wl.ConfigPropertyBean[] var3 = wlsConfiPropertiesBean.getProperties();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            weblogic.j2ee.descriptor.wl.ConfigPropertyBean propertyOverride = var3[var5];
            String name = propertyNameNormalizer.normalize(propertyOverride.getName());
            wlraOverrides.put(name, propertyOverride.getValue());
         }

         return wlraOverrides;
      }
   }
}
