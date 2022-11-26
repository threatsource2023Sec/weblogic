package weblogic.diagnostics.harvester.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import weblogic.diagnostics.harvester.AttributeNameNormalizer;
import weblogic.management.mbeanservers.Service;
import weblogic.management.runtime.RuntimeMBean;

public class AttributeNormalizerUtil {
   private static final AttributeNameNormalizer ATTR_NORMALIZER = new HarvesterDefaultAttributeNormalizer();

   public static String getNormalizedAttributeName(String typeName, String attributeName) {
      AttributeNameNormalizer normalizer = ATTR_NORMALIZER;
      if (typeName != null) {
         BeanInfo beanInfo = TreeBeanHarvestableDataProviderHelper.getBeanInfo(typeName);
         if (beanInfo != null) {
            PropertyDescriptor[] propDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var5 = propDescriptors;
            int var6 = propDescriptors.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               PropertyDescriptor pd = var5[var7];
               Class propType = pd.getPropertyType();
               if (!propType.isPrimitive() && !RuntimeMBean.class.isAssignableFrom(propType) && !Service.class.isAssignableFrom(propType)) {
                  String propName = pd.getName();
                  if (attributeName.startsWith(propName)) {
                     String normalizerClassName = (String)pd.getValue("harvesterAttributeNormalizerClass");
                     if (normalizerClassName != null) {
                        try {
                           Class normalizerClass = Class.forName(normalizerClassName);
                           normalizer = (AttributeNameNormalizer)normalizerClass.newInstance();
                           break;
                        } catch (Exception var14) {
                           return attributeName;
                        }
                     }
                  }
               }
            }
         }
      }

      return normalizer.getNormalizedAttributeName(attributeName);
   }
}
