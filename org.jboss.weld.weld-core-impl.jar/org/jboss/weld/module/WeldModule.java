package org.jboss.weld.module;

import org.jboss.weld.bean.builtin.AbstractBuiltInBean;
import org.jboss.weld.bootstrap.ContextHolder;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.manager.BeanManagerImpl;

public interface WeldModule {
   String getName();

   default void postServiceRegistration(PostServiceRegistrationContext ctx) {
   }

   default void postContextRegistration(PostContextRegistrationContext ctx) {
   }

   default void postBeanArchiveServiceRegistration(PostBeanArchiveServiceRegistrationContext ctx) {
   }

   default void preBeanRegistration(PreBeanRegistrationContext ctx) {
   }

   public interface PreBeanRegistrationContext {
      Environment getEnvironment();

      BeanDeploymentArchive getBeanDeploymentArchive();

      BeanManagerImpl getBeanManager();

      void registerBean(AbstractBuiltInBean var1);
   }

   public interface PostBeanArchiveServiceRegistrationContext {
      ServiceRegistry getServices();

      BeanManagerImpl getBeanManager();

      BeanDeploymentArchive getBeanDeploymentArchive();
   }

   public interface PostContextRegistrationContext {
      String getContextId();

      ServiceRegistry getServices();

      void addContext(ContextHolder var1);
   }

   public interface PostServiceRegistrationContext {
      String getContextId();

      ServiceRegistry getServices();

      void registerPlugableValidator(PlugableValidator var1);
   }
}
