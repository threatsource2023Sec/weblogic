package org.jboss.weld.bootstrap;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.Producer;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.executor.IterativeWorkerTaskFactory;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.ExecutorServices;
import org.jboss.weld.util.collections.SetMultimap;

public class ConcurrentValidator extends Validator {
   private final ExecutorServices executor;

   public ConcurrentValidator(Set plugableValidators, ExecutorServices executor, ConcurrentMap resolvedInjectionPoints) {
      super(plugableValidators, resolvedInjectionPoints);
      this.executor = executor;
   }

   public void validateBeans(Collection beans, final BeanManagerImpl manager) {
      final List problems = new CopyOnWriteArrayList();
      final Set specializedBeans = Collections.newSetFromMap(new ConcurrentHashMap());
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(beans) {
         protected void doWork(Bean bean) {
            ConcurrentValidator.this.validateBean(bean, specializedBeans, manager, problems);
         }
      });
      if (!problems.isEmpty()) {
         if (problems.size() == 1) {
            throw (RuntimeException)problems.get(0);
         } else {
            throw new DeploymentException(problems);
         }
      }
   }

   public void validateInterceptors(Collection interceptors, final BeanManagerImpl manager) {
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(interceptors) {
         protected void doWork(Interceptor interceptor) {
            ConcurrentValidator.this.validateInterceptor(interceptor, manager);
         }
      });
   }

   public void validateDecorators(Collection decorators, final BeanManagerImpl manager) {
      final Set specializedBeans = Collections.newSetFromMap(new ConcurrentHashMap());
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(decorators) {
         protected void doWork(Decorator decorator) {
            ConcurrentValidator.this.validateDecorator(decorator, specializedBeans, manager);
         }
      });
   }

   protected void validateObserverMethods(Iterable observers, final BeanManagerImpl beanManager) {
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(observers) {
         protected void doWork(ObserverInitializationContext observerMethod) {
            Iterator var2 = observerMethod.getObserver().getInjectionPoints().iterator();

            while(var2.hasNext()) {
               InjectionPoint ip = (InjectionPoint)var2.next();
               ConcurrentValidator.this.validateInjectionPointForDefinitionErrors(ip, ip.getBean(), beanManager);
               ConcurrentValidator.this.validateMetadataInjectionPoint(ip, (Bean)null, ValidatorLogger.INJECTION_INTO_NON_BEAN);
               ConcurrentValidator.this.validateInjectionPointForDeploymentProblems(ip, ip.getBean(), beanManager);
            }

         }
      });
   }

   public void validateBeanNames(final BeanManagerImpl beanManager) {
      final SetMultimap namedAccessibleBeans = SetMultimap.newConcurrentSetMultimap();
      Iterator var3 = beanManager.getAccessibleBeans().iterator();

      while(var3.hasNext()) {
         Bean bean = (Bean)var3.next();
         if (bean.getName() != null) {
            namedAccessibleBeans.put(bean.getName(), bean);
         }
      }

      final List accessibleNamespaces = beanManager.getAccessibleNamespaces();
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(namedAccessibleBeans.keySet()) {
         protected void doWork(String name) {
            ConcurrentValidator.this.validateBeanName(name, namedAccessibleBeans, accessibleNamespaces, beanManager);
         }
      });
   }

   public void validateProducers(Collection producers, final BeanManagerImpl beanManager) {
      this.executor.invokeAllAndCheckForExceptions(new IterativeWorkerTaskFactory(producers) {
         protected void doWork(Producer producer) {
            ConcurrentValidator.this.validateProducer(producer, beanManager);
         }
      });
   }
}
