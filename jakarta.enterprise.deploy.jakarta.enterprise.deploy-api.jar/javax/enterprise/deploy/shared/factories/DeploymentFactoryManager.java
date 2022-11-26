package javax.enterprise.deploy.shared.factories;

import java.util.Vector;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.factories.DeploymentFactory;

public final class DeploymentFactoryManager {
   private Vector deploymentFactories = null;
   private static DeploymentFactoryManager deploymentFactoryManager = new DeploymentFactoryManager();

   private DeploymentFactoryManager() {
      this.deploymentFactories = new Vector();
   }

   public static DeploymentFactoryManager getInstance() {
      return deploymentFactoryManager;
   }

   public DeploymentFactory[] getDeploymentFactories() {
      Vector deploymentFactoriesSnapShot = null;
      synchronized(this) {
         deploymentFactoriesSnapShot = (Vector)this.deploymentFactories.clone();
      }

      DeploymentFactory[] factoriesArray = new DeploymentFactory[deploymentFactoriesSnapShot.size()];
      deploymentFactoriesSnapShot.copyInto(factoriesArray);
      return factoriesArray;
   }

   public DeploymentManager getDeploymentManager(String uri, String username, String password) throws DeploymentManagerCreationException {
      try {
         DeploymentFactory[] factories = this.getDeploymentFactories();

         for(int factoryIndex = 0; factoryIndex < factories.length; ++factoryIndex) {
            if (factories[factoryIndex].handlesURI(uri)) {
               return factories[factoryIndex].getDeploymentManager(uri, username, password);
            }
         }

         throw new DeploymentManagerCreationException("URL [" + uri + "] not supported by any available factories");
      } catch (Throwable var6) {
         throw new DeploymentManagerCreationException("Could not get DeploymentManager");
      }
   }

   public void registerDeploymentFactory(DeploymentFactory factory) {
      this.deploymentFactories.add(factory);
   }

   public DeploymentManager getDisconnectedDeploymentManager(String uri) throws DeploymentManagerCreationException {
      try {
         DeploymentFactory[] factories = this.getDeploymentFactories();

         for(int factoryIndex = 0; factoryIndex < factories.length; ++factoryIndex) {
            if (factories[factoryIndex].handlesURI(uri)) {
               return factories[factoryIndex].getDisconnectedDeploymentManager(uri);
            }
         }

         throw new DeploymentManagerCreationException("URL [" + uri + "] not supported by any available factories");
      } catch (Throwable var4) {
         throw new DeploymentManagerCreationException("Could not get DeploymentManager");
      }
   }
}
