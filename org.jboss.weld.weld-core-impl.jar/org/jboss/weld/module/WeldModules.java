package org.jboss.weld.module;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.bootstrap.BeanDeployment;
import org.jboss.weld.bootstrap.ContextHolder;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.ServiceRegistries;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.WeldClassLoaderResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.util.ServiceLoader;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.collections.ImmutableSet;

public class WeldModules implements Service {
   private final List modules;
   private Set validators;

   public WeldModules() {
      this.modules = (List)ServiceLoader.load(WeldModule.class, (ResourceLoader)WeldClassLoaderResourceLoader.INSTANCE).stream().map((metadata) -> {
         return (WeldModule)metadata.getValue();
      }).sorted((m1, m2) -> {
         return m1.getName().compareTo(m2.getName());
      }).collect(ImmutableList.collector());
      this.validators = Collections.emptySet();
      BootstrapLogger.LOG.debugv("Using Weld modules: {0}", this.modules.stream().map((m) -> {
         return m.getName();
      }).collect(Collectors.toList()));
   }

   public void postServiceRegistration(final String contextId, final ServiceRegistry services) {
      final Set validators = new HashSet();
      WeldModule.PostServiceRegistrationContext ctx = new WeldModule.PostServiceRegistrationContext() {
         public ServiceRegistry getServices() {
            return services;
         }

         public String getContextId() {
            return contextId;
         }

         public void registerPlugableValidator(PlugableValidator validator) {
            validators.add(validator);
         }
      };
      Iterator var5 = this.modules.iterator();

      while(var5.hasNext()) {
         WeldModule module = (WeldModule)var5.next();
         module.postServiceRegistration(ctx);
      }

      this.validators = ImmutableSet.copyOf(validators);
   }

   public void postContextRegistration(final String contextId, final ServiceRegistry services, final List contexts) {
      WeldModule.PostContextRegistrationContext ctx = new WeldModule.PostContextRegistrationContext() {
         public String getContextId() {
            return contextId;
         }

         public ServiceRegistry getServices() {
            return ServiceRegistries.unmodifiableServiceRegistry(services);
         }

         public void addContext(ContextHolder context) {
            contexts.add(context);
         }
      };
      Iterator var5 = this.modules.iterator();

      while(var5.hasNext()) {
         WeldModule module = (WeldModule)var5.next();
         module.postContextRegistration(ctx);
      }

   }

   public void postBeanArchiveServiceRegistration(final ServiceRegistry services, final BeanManagerImpl manager, final BeanDeploymentArchive archive) {
      WeldModule.PostBeanArchiveServiceRegistrationContext ctx = new WeldModule.PostBeanArchiveServiceRegistrationContext() {
         public ServiceRegistry getServices() {
            return services;
         }

         public BeanManagerImpl getBeanManager() {
            return manager;
         }

         public BeanDeploymentArchive getBeanDeploymentArchive() {
            return archive;
         }
      };
      Iterator var5 = this.modules.iterator();

      while(var5.hasNext()) {
         WeldModule module = (WeldModule)var5.next();
         module.postBeanArchiveServiceRegistration(ctx);
      }

   }

   public void preBeanRegistration(final BeanDeployment deployment, final Environment environment) {
      WeldModule.PreBeanRegistrationContext ctx = new WeldModule.PreBeanRegistrationContext() {
         public void registerBean(AbstractBuiltInBean bean) {
            deployment.getBeanDeployer().addBuiltInBean(bean);
         }

         public Environment getEnvironment() {
            return environment;
         }

         public BeanManagerImpl getBeanManager() {
            return deployment.getBeanManager();
         }

         public BeanDeploymentArchive getBeanDeploymentArchive() {
            return deployment.getBeanDeploymentArchive();
         }
      };
      Iterator var4 = this.modules.iterator();

      while(var4.hasNext()) {
         WeldModule module = (WeldModule)var4.next();
         module.preBeanRegistration(ctx);
      }

   }

   public void cleanup() {
   }

   public Set getPluggableValidators() {
      return this.validators;
   }
}
