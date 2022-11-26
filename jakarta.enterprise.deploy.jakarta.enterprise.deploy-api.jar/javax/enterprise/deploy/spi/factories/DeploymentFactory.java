package javax.enterprise.deploy.spi.factories;

import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;

public interface DeploymentFactory {
   boolean handlesURI(String var1);

   DeploymentManager getDeploymentManager(String var1, String var2, String var3) throws DeploymentManagerCreationException;

   DeploymentManager getDisconnectedDeploymentManager(String var1) throws DeploymentManagerCreationException;

   String getDisplayName();

   String getProductVersion();
}
