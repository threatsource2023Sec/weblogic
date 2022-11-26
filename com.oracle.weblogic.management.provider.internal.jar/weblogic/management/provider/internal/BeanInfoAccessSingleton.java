package weblogic.management.provider.internal;

import weblogic.descriptor.DescriptorClassLoader;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.provider.beaninfo.BeanInfoAccessFactory;
import weblogic.management.provider.beaninfo.BeanInfoRegistration;

class BeanInfoAccessSingleton {
   static BeanInfoAccess getInstance() {
      return BeanInfoAccessSingleton.SINGLETON.instance;
   }

   private static class SINGLETON {
      static BeanInfoAccess instance = BeanInfoAccessFactory.getBeanInfoAccess();

      static {
         BeanInfoRegistration beanInfoReg = BeanInfoAccessFactory.getBeanInfoRegistration();
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.management.runtime.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.management.runtime.VBeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.management.configuration.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.management.configuration.VBeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.j2ee.descriptor.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.management.security.WLMANAGEMENTBeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.diagnostics.descriptor.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.management.mbeanservers.internal.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("kodo.conf.descriptor.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.diagnostics.accessor.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("weblogic.diagnostics.accessor.ExtraBeanInfoFactory", (ClassLoader)null);
         beanInfoReg.registerBeanInfoFactoryClass("com.bea.wls.redef.runtime.BeanInfoFactory", (ClassLoader)null);
         beanInfoReg.discoverBeanInfoFactories(DescriptorClassLoader.getClassLoader());
      }
   }
}
