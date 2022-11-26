package com.oracle.injection.provider.weld;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.CDIProvider;
import org.jboss.weld.Container;
import org.jboss.weld.SimpleCDI;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.manager.BeanManagerImpl;

public class WlsWeldProvider implements CDIProvider {
   public CDI getCDI() {
      try {
         return new WlsEnhancedWeld();
      } catch (Throwable var3) {
         Throwable cause = var3.getCause();
         if (cause instanceof IllegalStateException) {
            return null;
         } else {
            throw var3;
         }
      }
   }

   private static class WlsEnhancedWeld extends SimpleCDI {
      private WlsEnhancedWeld() {
      }

      protected BeanManagerImpl unsatisfiedBeanManager(String callerClassName) {
         Map beanDeploymentArchives = Container.instance().beanDeploymentArchives();
         Set entries = beanDeploymentArchives.entrySet();
         if (entries.size() == 1) {
            Map.Entry entry = (Map.Entry)entries.iterator().next();
            if (!(entry.getKey() instanceof ExtensionBeanDeploymentArchive)) {
               return (BeanManagerImpl)entry.getValue();
            }
         }

         Iterator var11 = entries.iterator();

         while(true) {
            Map.Entry entry;
            BeanDeploymentArchive beanDeploymentArchive;
            do {
               if (!var11.hasNext()) {
                  return super.unsatisfiedBeanManager(callerClassName);
               }

               entry = (Map.Entry)var11.next();
               beanDeploymentArchive = (BeanDeploymentArchive)entry.getKey();
            } while(!(beanDeploymentArchive instanceof RootBeanDeploymentArchive));

            RootBeanDeploymentArchive rootBeanDeploymentArchive = (RootBeanDeploymentArchive)beanDeploymentArchive;
            ClassLoader moduleClassLoaderForBDA = rootBeanDeploymentArchive.getModuleClassLoaderForBDA();

            try {
               Class.forName(callerClassName, false, moduleClassLoaderForBDA);
               return (BeanManagerImpl)entry.getValue();
            } catch (Exception var10) {
            }
         }
      }

      // $FF: synthetic method
      WlsEnhancedWeld(Object x0) {
         this();
      }
   }
}
