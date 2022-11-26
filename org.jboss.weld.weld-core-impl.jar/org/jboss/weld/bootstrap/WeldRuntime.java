package org.jboss.weld.bootstrap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import javax.enterprise.context.BeforeDestroyed.Literal;
import org.jboss.weld.Container;
import org.jboss.weld.ContainerState;
import org.jboss.weld.bootstrap.events.BeforeShutdownImpl;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.context.ApplicationContext;
import org.jboss.weld.context.SingletonContext;
import org.jboss.weld.event.ContextEvent;
import org.jboss.weld.manager.BeanManagerImpl;

public class WeldRuntime {
   private BeanManagerImpl deploymentManager;
   private ConcurrentMap bdaToBeanManagerMap;
   private String contextId;

   public WeldRuntime(String contextId, BeanManagerImpl deploymentManager, ConcurrentMap bdaToBeanManagerMap) {
      this.contextId = contextId;
      this.deploymentManager = deploymentManager;
      this.bdaToBeanManagerMap = bdaToBeanManagerMap;
   }

   public BeanManagerImpl getManager(BeanDeploymentArchive beanDeploymentArchive) {
      return (BeanManagerImpl)this.bdaToBeanManagerMap.get(beanDeploymentArchive);
   }

   public void shutdown() {
      boolean var19 = false;

      try {
         var19 = true;
         this.fireEventForNonWebModules(Object.class, ContextEvent.APPLICATION_BEFORE_DESTROYED, Literal.APPLICATION);
         ((ApplicationContext)this.deploymentManager.instance().select(ApplicationContext.class, new Annotation[0]).get()).invalidate();
         ((SingletonContext)this.deploymentManager.instance().select(SingletonContext.class, new Annotation[0]).get()).invalidate();
         var19 = false;
      } finally {
         if (var19) {
            this.fireEventForNonWebModules(Object.class, ContextEvent.APPLICATION_DESTROYED, javax.enterprise.context.Destroyed.Literal.APPLICATION);
            boolean var15 = false;

            try {
               var15 = true;
               BeforeShutdownImpl.fire(this.deploymentManager);
               var15 = false;
            } finally {
               if (var15) {
                  Container container = Container.instance(this.contextId);
                  container.setState(ContainerState.SHUTDOWN);
                  container.cleanup();
               }
            }

            Container container = Container.instance(this.contextId);
            container.setState(ContainerState.SHUTDOWN);
            container.cleanup();
         }
      }

      this.fireEventForNonWebModules(Object.class, ContextEvent.APPLICATION_DESTROYED, javax.enterprise.context.Destroyed.Literal.APPLICATION);
      boolean var11 = false;

      try {
         var11 = true;
         BeforeShutdownImpl.fire(this.deploymentManager);
         var11 = false;
      } finally {
         if (var11) {
            Container container = Container.instance(this.contextId);
            container.setState(ContainerState.SHUTDOWN);
            container.cleanup();
         }
      }

      Container container = Container.instance(this.contextId);
      container.setState(ContainerState.SHUTDOWN);
      container.cleanup();
   }

   private void fireEventForNonWebModules(Type eventType, Object event, Annotation... qualifiers) {
      try {
         BeanDeploymentModules modules = (BeanDeploymentModules)this.deploymentManager.getServices().get(BeanDeploymentModules.class);
         if (modules != null) {
            Iterator var5 = modules.iterator();

            while(var5.hasNext()) {
               BeanDeploymentModule module = (BeanDeploymentModule)var5.next();
               if (!module.isWebModule()) {
                  module.fireEvent(eventType, event, qualifiers);
               }
            }
         }
      } catch (Exception var7) {
      }

   }
}
