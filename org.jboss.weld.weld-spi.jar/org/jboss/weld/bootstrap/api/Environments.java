package org.jboss.weld.bootstrap.api;

import java.util.HashSet;
import java.util.Set;
import org.jboss.weld.injection.spi.EjbInjectionServices;
import org.jboss.weld.injection.spi.InjectionServices;
import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceInjectionServices;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.security.spi.SecurityServices;
import org.jboss.weld.transaction.spi.TransactionServices;

public enum Environments implements Environment {
   EE_INJECT((new EnvironmentBuilder()).addRequiredDeploymentService(TransactionServices.class).addRequiredDeploymentService(SecurityServices.class).addRequiredBeanDeploymentArchiveService(ResourceLoader.class).addRequiredBeanDeploymentArchiveService(JpaInjectionServices.class).addRequiredBeanDeploymentArchiveService(ResourceInjectionServices.class).addRequiredBeanDeploymentArchiveService(EjbInjectionServices.class)),
   EE((new EnvironmentBuilder()).addRequiredDeploymentService(TransactionServices.class).addRequiredDeploymentService(SecurityServices.class).addRequiredBeanDeploymentArchiveService(ResourceLoader.class).addRequiredBeanDeploymentArchiveService(InjectionServices.class)),
   SERVLET((new EnvironmentBuilder()).addRequiredBeanDeploymentArchiveService(ResourceLoader.class)),
   SE((new EnvironmentBuilder()).addRequiredBeanDeploymentArchiveService(ResourceLoader.class).setEEModulesAware(false));

   private final Set requiredDeploymentServices;
   private final Set requiredBeanDeploymentArchiveServices;
   private boolean eeModuleAware = true;

   private Environments(EnvironmentBuilder builder) {
      this.requiredDeploymentServices = builder.getRequiredDeploymentServices();
      this.requiredBeanDeploymentArchiveServices = builder.getRequiredBeanDeploymentArchiveServices();
      this.eeModuleAware = builder.isEEModuleAware();
   }

   public Set getRequiredDeploymentServices() {
      return this.requiredDeploymentServices;
   }

   public Set getRequiredBeanDeploymentArchiveServices() {
      return this.requiredBeanDeploymentArchiveServices;
   }

   public boolean isEEModulesAware() {
      return this.eeModuleAware;
   }

   private static class EnvironmentBuilder {
      private final Set requiredDeploymentServices;
      private final Set requiredBeanDeploymentArchiveServices;
      private boolean eeModuleAware;

      private EnvironmentBuilder() {
         this.eeModuleAware = true;
         this.requiredBeanDeploymentArchiveServices = new HashSet();
         this.requiredDeploymentServices = new HashSet();
      }

      private Set getRequiredBeanDeploymentArchiveServices() {
         return this.requiredBeanDeploymentArchiveServices;
      }

      private Set getRequiredDeploymentServices() {
         return this.requiredDeploymentServices;
      }

      private EnvironmentBuilder addRequiredDeploymentService(Class service) {
         this.requiredDeploymentServices.add(service);
         return this;
      }

      private EnvironmentBuilder addRequiredBeanDeploymentArchiveService(Class service) {
         this.requiredBeanDeploymentArchiveServices.add(service);
         return this;
      }

      private EnvironmentBuilder setEEModulesAware(boolean aware) {
         this.eeModuleAware = aware;
         return this;
      }

      public boolean isEEModuleAware() {
         return this.eeModuleAware;
      }

      // $FF: synthetic method
      EnvironmentBuilder(Object x0) {
         this();
      }
   }
}
