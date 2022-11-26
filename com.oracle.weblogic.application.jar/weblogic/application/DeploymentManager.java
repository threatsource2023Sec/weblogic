package weblogic.application;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.utils.LocatorUtilities;

@Contract
public abstract class DeploymentManager implements DeploymentFinder {
   /** @deprecated */
   @Deprecated
   public static DeploymentManager getDeploymentManager() {
      return DeploymentManager.DeploymentManagerInstance.INSTANCE;
   }

   public abstract String confirmApplicationName(boolean var1, File var2, File var3, String var4, String var5, DomainMBean var6) throws DeploymentException;

   public abstract String confirmApplicationName(boolean var1, File var2, File var3, String var4, String var5, DomainMBean var6, String var7, String var8, String var9) throws DeploymentException;

   public abstract DeploymentCreator getDeploymentCreator(BasicDeploymentMBean var1, File var2) throws DeploymentException;

   public abstract WorkDeployment findDeployment(BasicDeploymentMBean var1);

   public abstract Deployment removeDeployment(BasicDeploymentMBean var1);

   public abstract Deployment removeDeployment(String var1);

   public abstract Iterator getDeployments();

   public abstract Map getAllVersionsOfLibraries(List var1);

   public abstract MBeanFactory getMBeanFactory();

   public abstract void addModuleListener(ModuleListener var1);

   public abstract void removeModuleListener(ModuleListener var1);

   public abstract Iterator getModuleListeners();

   private static final class DeploymentManagerInstance {
      private static final DeploymentManager INSTANCE = (DeploymentManager)LocatorUtilities.getService(DeploymentManager.class);
   }

   public interface DeploymentCreator {
      Deployment createDeployment(BasicDeploymentMBean var1, File var2) throws DeploymentException;
   }
}
