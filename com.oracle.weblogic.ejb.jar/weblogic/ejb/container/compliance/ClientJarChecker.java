package weblogic.ejb.container.compliance;

import weblogic.ejb.container.interfaces.DeploymentInfo;

public final class ClientJarChecker extends BaseComplianceChecker {
   private final DeploymentInfo deploymentInfo;

   public ClientJarChecker(DeploymentInfo di) {
      this.deploymentInfo = di;
   }

   public void checkForEmptyClientJar() {
      if ("".equals(this.deploymentInfo.getClientJarFileName())) {
         Log.getInstance().logWarning(this.getFmt().EMPTY_CLIENT_JAR_TAG());
      }

   }
}
