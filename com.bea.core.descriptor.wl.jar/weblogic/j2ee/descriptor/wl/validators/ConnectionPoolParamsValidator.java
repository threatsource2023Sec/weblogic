package weblogic.j2ee.descriptor.wl.validators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.j2ee.descriptor.wl.ConnectionPoolParamsBean;

public class ConnectionPoolParamsValidator {
   public static void validateConnectionPoolParamsBean(ConnectionPoolParamsBean bean) throws IllegalArgumentException {
      DescriptorBean parent = ((AbstractDescriptorBean)bean).getParentBean();
      Class jdbcDataSourceBeanClass = null;

      try {
         jdbcDataSourceBeanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBean");
      } catch (ClassNotFoundException var12) {
      }

      if (jdbcDataSourceBeanClass != null && jdbcDataSourceBeanClass.isInstance(parent)) {
         try {
            Method getInternalPropertiesMethod = jdbcDataSourceBeanClass.getMethod("getInternalProperties");
            Object properties = getInternalPropertiesMethod.invoke(parent);
            Class propertiesClass = properties.getClass();
            Method lookupPropertyMethod = propertiesClass.getMethod("lookupProperty", String.class);
            Object result = lookupPropertyMethod.invoke(properties, "LegacyType");
            if (result != null) {
               String resultString = (String)result;
               if (!resultString.equals("0")) {
                  return;
               }
            }
         } catch (NoSuchMethodException var9) {
         } catch (IllegalAccessException var10) {
         } catch (InvocationTargetException var11) {
         }
      }

      if (bean.getInitialCapacity() > bean.getMaxCapacity()) {
         throw new IllegalArgumentException("IntialCapacity (" + bean.getInitialCapacity() + ") > MaxCapacity  (" + bean.getMaxCapacity() + ")");
      }
   }
}
