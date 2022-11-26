package weblogic.deploy.api.spi.factories;

import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.factories.DeploymentFactory;

public interface WebLogicDeploymentFactory extends DeploymentFactory {
   int LOCAL_URI_INDEX = 0;
   int REMOTE_URI_INDEX = 1;
   int AUTH_URI_INDEX = 2;
   String LOCAL_DM_URI = "deployer:WebLogic";
   String REMOTE_DM_URI = "remote:deployer:WebLogic";
   String AUTH_DM_URI = "authenticated:deployer:WebLogic";
   String DEFAULT_PROTOCOL = "t3";
   int DEFAULT_PORT = 7001;
   String DEFAULT_PORT_STRING = "7001";
   String DEFAULT_HOST = "localhost";
   String DEFAULT_URL = "t3://localhost:7001";

   DeploymentManager getDeploymentManager(String var1, String var2, String var3) throws DeploymentManagerCreationException;

   DeploymentManager getDisconnectedDeploymentManager(String var1) throws DeploymentManagerCreationException;

   String[] getUris();

   String createUri(String var1, String var2, String var3);

   String createUri(String var1, String var2, String var3, String var4);

   String createUri(String var1, String var2, String var3, String var4, String var5);
}
