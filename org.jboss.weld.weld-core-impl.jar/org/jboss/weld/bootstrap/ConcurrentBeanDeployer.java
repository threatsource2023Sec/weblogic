package org.jboss.weld.bootstrap;

import java.util.List;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeContext;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.executor.IterativeWorkerTaskFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.ExecutorServices;
import org.jboss.weld.util.collections.SetMultimap;

public class ConcurrentBeanDeployer extends BeanDeployer {
   private final ExecutorServices executor;

   public ConcurrentBeanDeployer(BeanManagerImpl manager, ServiceRegistry services) {
      super(manager, services, BeanDeployerEnvironmentFactory.newConcurrentEnvironment(manager));
      this.executor = (ExecutorServices)services.get(ExecutorServices.class);
   }

   public BeanDeployer addClasses(Iterable c) {
      final AnnotatedTypeLoader loader = this.createAnnotatedTypeLoader();
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(c) {
         protected void doWork(String className) {
            ConcurrentBeanDeployer.this.addClass(className, loader);
         }
      });
      return this;
   }

   public void createClassBeans() {
      final SetMultimap otherWeldClasses = SetMultimap.newConcurrentSetMultimap();
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(this.getEnvironment().getAnnotatedTypes()) {
         protected void doWork(SlimAnnotatedTypeContext ctx) {
            ConcurrentBeanDeployer.this.createClassBean(ctx.getAnnotatedType(), otherWeldClasses);
         }
      });
      this.ejbSupport.createSessionBeans(this.getEnvironment(), otherWeldClasses, this.getManager());
   }

   public void createProducersAndObservers() {
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(this.getEnvironment().getClassBeans()) {
         protected void doWork(AbstractClassBean bean) {
            ConcurrentBeanDeployer.this.createObserversProducersDisposers(bean);
         }
      });
   }

   public void doAfterBeanDiscovery(List beanList) {
      this.executor.invokeAllAndCheckForExceptions(new AfterBeanDiscoveryInitializerFactory(beanList));
   }

   public AbstractBeanDeployer initializeBeans() {
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(this.getEnvironment().getBeans()) {
         protected void doWork(RIBean bean) {
            bean.initialize(ConcurrentBeanDeployer.this.getEnvironment());
         }
      });
      return this;
   }

   private static class AfterBeanDiscoveryInitializerFactory extends IterativeWorkerTaskFactory {
      public AfterBeanDiscoveryInitializerFactory(Iterable iterable) {
         super(iterable);
      }

      protected void doWork(Bean bean) {
         if (bean instanceof RIBean) {
            ((RIBean)bean).initializeAfterBeanDiscovery();
         }

      }
   }
}
