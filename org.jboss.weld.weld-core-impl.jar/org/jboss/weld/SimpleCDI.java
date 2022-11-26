package org.jboss.weld;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jboss.weld.bean.builtin.BeanManagerProxy;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;

public class SimpleCDI extends AbstractCDI {
   private final ComputingCache beanManagers;
   private final Container container;

   public SimpleCDI() {
      this(Container.instance());
   }

   public SimpleCDI(Container container) {
      this.container = container;
      this.beanManagers = ComputingCacheBuilder.newBuilder().setWeakValues().build(new ClassNameToBeanManager());
   }

   protected BeanManagerImpl unsatisfiedBeanManager(String callerClassName) {
      throw BeanManagerLogger.LOG.unsatisfiedBeanManager(callerClassName);
   }

   protected BeanManagerImpl ambiguousBeanManager(String callerClassName, Set managers) {
      throw BeanManagerLogger.LOG.ambiguousBeanManager(callerClassName);
   }

   public BeanManagerProxy getBeanManager() {
      ContainerState state = this.container.getState();
      if (!state.equals(ContainerState.STOPPED) && !state.equals(ContainerState.SHUTDOWN)) {
         return (BeanManagerProxy)this.beanManagers.getValue(this.getCallingClassName());
      } else {
         throw BeanManagerLogger.LOG.beanManagerNotAvailable();
      }
   }

   public String toString() {
      return "Weld";
   }

   protected Container getContainer() {
      return this.container;
   }

   public void cleanup() {
      this.beanManagers.clear();
   }

   private class ClassNameToBeanManager implements Function {
      private ClassNameToBeanManager() {
      }

      public BeanManagerProxy apply(String callerClassName) {
         return new BeanManagerProxy(this.findBeanManager(callerClassName));
      }

      public BeanManagerImpl findBeanManager(String callerClassName) {
         if (callerClassName == null) {
            throw BeanManagerLogger.LOG.unableToIdentifyBeanManager();
         } else {
            Set managers = new HashSet();
            Iterator var3 = SimpleCDI.this.container.beanDeploymentArchives().entrySet().iterator();

            while(var3.hasNext()) {
               Map.Entry entry = (Map.Entry)var3.next();
               Iterator var5 = ((BeanDeploymentArchive)entry.getKey()).getKnownClasses().iterator();

               while(var5.hasNext()) {
                  String className = (String)var5.next();
                  if (className.equals(callerClassName)) {
                     managers.add(entry.getValue());
                  }
               }
            }

            if (managers.size() == 1) {
               return (BeanManagerImpl)managers.iterator().next();
            } else if (managers.size() == 0) {
               return SimpleCDI.this.unsatisfiedBeanManager(callerClassName);
            } else {
               return SimpleCDI.this.ambiguousBeanManager(callerClassName, managers);
            }
         }
      }

      // $FF: synthetic method
      ClassNameToBeanManager(Object x1) {
         this();
      }
   }
}
