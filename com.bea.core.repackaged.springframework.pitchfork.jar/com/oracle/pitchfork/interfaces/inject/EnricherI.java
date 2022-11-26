package com.oracle.pitchfork.interfaces.inject;

public interface EnricherI {
   void setDeploymentUnitMetadata(DeploymentUnitMetadataI var1);

   DeploymentUnitMetadataI getDeploymentUnitMetadata();

   void attach(Jsr250MetadataI var1);

   void attach(Jsr250MetadataI var1, boolean var2);

   String[] getRegisteredBeanDefinitionNames();
}
