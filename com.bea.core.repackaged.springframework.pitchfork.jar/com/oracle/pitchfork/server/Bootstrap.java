package com.oracle.pitchfork.server;

import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.support.GenericApplicationContext;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.inject.Enricher;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;

public class Bootstrap {
   private final ContainerBootstrap containerBootstrap;

   public Bootstrap(ContainerBootstrap containerBootstrap) {
      this.containerBootstrap = containerBootstrap;
   }

   public Bootstrap() {
      this(new DefaultContainerBootstrap());
   }

   public GenericApplicationContext deploy(ComponentContributor componentContributor) {
      return this.deploy((ResourceLoader)null, componentContributor);
   }

   public GenericApplicationContext deploy(ResourceLoader resourceLoader, ComponentContributor componentContributor) {
      return this.deploy(new String[0], resourceLoader, componentContributor, (DeploymentUnitMetadata)null);
   }

   public GenericApplicationContext deploy(String[] requiredSpringLocations, ResourceLoader resourceLoader, ComponentContributor componentContributor, DeploymentUnitMetadata deploymentUnitMetadata) {
      return this.deploy(requiredSpringLocations, resourceLoader, componentContributor, deploymentUnitMetadata);
   }

   public GenericApplicationContext deploy(String[] requiredSpringLocations, ResourceLoader resourceLoader, ComponentContributor componentContributor, DeploymentUnitMetadata deploymentUnitMetadata, String... optionalSpringLocations) {
      GenericApplicationContext deploymentContext = this.containerBootstrap.bootstrap((ApplicationContext)null, requiredSpringLocations, optionalSpringLocations, resourceLoader);
      Enricher enricher = new Enricher(deploymentContext);
      if (deploymentUnitMetadata != null) {
         enricher.setDeploymentUnitMetadata(deploymentUnitMetadata);
      }

      componentContributor.contribute(enricher);
      enricher.startup();
      return deploymentContext;
   }
}
