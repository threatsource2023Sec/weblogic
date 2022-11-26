package com.oracle.pitchfork.spi;

import com.bea.core.repackaged.springframework.beans.factory.support.InstantiationStrategy;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.interfaces.ComponentFactory;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.server.Bootstrap;

public class WLSBootstrap extends Bootstrap {
   private final ComponentFactory componentFactory;
   private final String springConfigurationResourceName;
   private final boolean usesSpringExtensionModel;

   public WLSBootstrap(ClassLoader loader, String springConfigurationResourceName, String componentFactoryClassName, boolean usesSpringExtensionModel) {
      if (componentFactoryClassName == null) {
         this.componentFactory = null;
      } else {
         try {
            this.componentFactory = (ComponentFactory)Class.forName(componentFactoryClassName).newInstance();
         } catch (Exception var6) {
            throw new IllegalStateException("error loading component factory", var6);
         }
      }

      this.usesSpringExtensionModel = usesSpringExtensionModel;
      this.springConfigurationResourceName = springConfigurationResourceName;
   }

   public GenericApplicationContext deploy(String[] requiredSpringLocations, ResourceLoader resourceLoader, ComponentContributor componentContributor, DeploymentUnitMetadata deploymentUnitMetadata, String... optionalSpringLocations) {
      String[] resources;
      if (this.usesSpringExtensionModel) {
         if (optionalSpringLocations != null && this.springConfigurationResourceName != null) {
            resources = new String[optionalSpringLocations.length + 1];
            System.arraycopy(optionalSpringLocations, 0, resources, 0, optionalSpringLocations.length);
            resources[resources.length - 1] = this.springConfigurationResourceName;
         } else {
            resources = optionalSpringLocations;
         }
      } else {
         resources = new String[0];
      }

      GenericApplicationContext ctx = super.deploy(requiredSpringLocations, resourceLoader, componentContributor, deploymentUnitMetadata, resources);
      if (this.componentFactory != null) {
         InstantiationStrategy istrat = new WLSInstantiationStrategy(this.componentFactory);
         ctx.getDefaultListableBeanFactory().setInstantiationStrategy(istrat);
      }

      return ctx;
   }
}
