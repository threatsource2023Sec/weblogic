package weblogic.application.internal;

import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ComponentMBean;

final class ComponentMBeanHelper {
   private ComponentMBeanHelper() {
   }

   static ComponentMBean findComponentMBeanByName(ApplicationMBean appMBean, String uri, Class type) {
      return findComponentMBean(appMBean.getComponents(), uri, type, false);
   }

   static ComponentMBean findComponentMBeanByURI(ComponentMBean[] components, String uri, Class type) {
      return findComponentMBean(components, uri, type, true);
   }

   static ComponentMBean findComponentMBeanByURI(ApplicationMBean appMBean, String uri, Class type) {
      return appMBean == null ? null : findComponentMBean(appMBean.getComponents(), uri, type, true);
   }

   private static ComponentMBean findComponentMBean(ComponentMBean[] components, String name, Class type, boolean useURI) {
      if (type == null) {
         throw new IllegalArgumentException("type cannot be null");
      } else {
         if (components != null) {
            for(int i = 0; i < components.length; ++i) {
               String compName = getName(components[i], useURI);
               if (name.equals(compName) && type.isAssignableFrom(components[i].getClass())) {
                  return components[i];
               }
            }
         }

         return null;
      }
   }

   private static String getName(ComponentMBean b, boolean useURI) {
      return useURI ? b.getURI() : b.getName();
   }
}
