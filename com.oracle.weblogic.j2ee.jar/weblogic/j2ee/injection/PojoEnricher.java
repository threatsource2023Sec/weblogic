package weblogic.j2ee.injection;

import com.oracle.pitchfork.inject.DeploymentUnitMetadata;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;

public class PojoEnricher implements EnricherI {
   private DeploymentUnitMetadataI deploymentUnitMetadata;

   public PojoEnricher(DeploymentUnitMetadata deploymentUnitMetadata) {
      this.deploymentUnitMetadata = deploymentUnitMetadata;
   }

   public void setDeploymentUnitMetadata(DeploymentUnitMetadataI deploymentUnitMetadata) {
      this.deploymentUnitMetadata = deploymentUnitMetadata;
   }

   public DeploymentUnitMetadataI getDeploymentUnitMetadata() {
      return this.deploymentUnitMetadata;
   }

   public void attach(Jsr250MetadataI jsr250) {
   }

   public void attach(Jsr250MetadataI jsr250, boolean isSingleton) {
   }

   public String[] getRegisteredBeanDefinitionNames() {
      return new String[0];
   }
}
