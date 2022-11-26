package weblogic.connector.configuration.validation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.TypeUtils;
import weblogic.j2ee.descriptor.InboundResourceAdapterBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;

public class ValidationUtils {
   private ValidationUtils() {
   }

   private static String switchType(String clz) {
      String returnType = clz;
      if (TypeUtils.isPrimitive(clz)) {
         returnType = TypeUtils.primitiveToObject(clz);
      } else if (TypeUtils.isObjectTypeOfPrimitive(clz)) {
         returnType = TypeUtils.objectToPrimitive(clz);
      }

      return returnType;
   }

   public static Method findSetterProperty(PropertyDescriptor[] pds, String name, String type, PropertyNameNormalizer propertyNameNormalizer) {
      PropertyDescriptor[] var4 = pds;
      int var5 = pds.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         PropertyDescriptor pd = var4[var6];
         if (propertyNameNormalizer.match(pd.getName(), name) && (pd.getPropertyType().getName().equals(type) || pd.getPropertyType().getName().equals(switchType(type)))) {
            Method writeMethod = pd.getWriteMethod();
            if (writeMethod != null) {
               return writeMethod;
            }
         }
      }

      return null;
   }

   public static boolean isInboundAdapter(ResourceAdapterBean resource) {
      if (resource != null) {
         InboundResourceAdapterBean inboundResourceAdapter = resource.getInboundResourceAdapter();
         if (inboundResourceAdapter != null) {
            return inboundResourceAdapter.getMessageAdapter() != null && inboundResourceAdapter.getMessageAdapter().getMessageListeners() != null && inboundResourceAdapter.getMessageAdapter().getMessageListeners().length > 0;
         }
      }

      return false;
   }

   public static boolean hasResourceAdapterBean(ResourceAdapterBean resource) {
      if (resource == null) {
         return false;
      } else {
         String adapterClass = resource.getResourceAdapterClass();
         return adapterClass != null && !"".endsWith(adapterClass);
      }
   }
}
