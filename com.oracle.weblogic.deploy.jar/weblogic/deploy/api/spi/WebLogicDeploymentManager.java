package weblogic.deploy.api.spi;

import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.shared.DConfigBeanVersionType;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DeploymentConfiguration;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.DConfigBeanVersionUnsupportedException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.deploy.api.internal.utils.JMXDeployerHelper;
import weblogic.deploy.api.spi.deploy.ServerConnection;
import weblogic.management.configuration.ConfigurationMBean;

public interface WebLogicDeploymentManager extends DeploymentManager {
   Target getTarget(String var1) throws IllegalStateException;

   Target getTarget(String var1, DeploymentOptions var2) throws IllegalStateException;

   WebLogicTargetModuleID createTargetModuleID(String var1, ModuleType var2, Target var3);

   WebLogicTargetModuleID createTargetModuleID(TargetModuleID var1, String var2, ModuleType var3);

   boolean isLocal();

   String getDomain();

   void setDomain(String var1);

   boolean isConnected();

   /** @deprecated */
   @Deprecated
   ProgressObject deploy(Target[] var1, File var2, File var3, DeploymentOptions var4) throws TargetException, IllegalStateException;

   ProgressObject deploy(TargetModuleID[] var1, File var2, File var3, DeploymentOptions var4) throws TargetException, IllegalStateException;

   ProgressObject update(TargetModuleID[] var1, File var2, DeploymentOptions var3) throws IllegalStateException;

   ProgressObject update(TargetModuleID[] var1, File var2, String[] var3, DeploymentOptions var4) throws IllegalStateException;

   void enableFileUploads() throws IllegalStateException;

   ProgressObject redeploy(TargetModuleID[] var1, File var2, String[] var3, DeploymentOptions var4) throws IllegalStateException;

   ProgressObject undeploy(TargetModuleID[] var1, File var2, String[] var3, DeploymentOptions var4) throws IllegalStateException;

   void setTaskId(String var1) throws IllegalStateException;

   TargetModuleID[] filter(TargetModuleID[] var1, String var2, String var3, String var4);

   TargetModuleID[] getModules(ConfigurationMBean var1) throws IllegalStateException;

   ProgressObject start(TargetModuleID[] var1, DeploymentOptions var2) throws IllegalStateException;

   ProgressObject stop(TargetModuleID[] var1, DeploymentOptions var2) throws IllegalStateException;

   ProgressObject undeploy(TargetModuleID[] var1, DeploymentOptions var2) throws IllegalStateException;

   ProgressObject distribute(Target[] var1, File var2, File var3, DeploymentOptions var4) throws IllegalStateException;

   ProgressObject distribute(TargetModuleID[] var1, File var2, File var3, DeploymentOptions var4) throws IllegalStateException, TargetException;

   ProgressObject distribute(Target[] var1, InputStream var2, InputStream var3, DeploymentOptions var4) throws IllegalStateException;

   ProgressObject appendToExtensionLoader(TargetModuleID[] var1, File var2, DeploymentOptions var3) throws IllegalStateException, TargetException;

   ProgressObject redeploy(TargetModuleID[] var1, File var2, File var3, DeploymentOptions var4) throws UnsupportedOperationException, IllegalStateException;

   ProgressObject redeploy(TargetModuleID[] var1, InputStream var2, InputStream var3, DeploymentOptions var4) throws UnsupportedOperationException, IllegalStateException;

   Target[] getTargets() throws IllegalStateException;

   Target[] getTargets(DeploymentOptions var1) throws IllegalStateException;

   Target[] getTargets(DeploymentOptions var1, boolean var2) throws IllegalStateException;

   TargetModuleID[] getRunningModules(ModuleType var1, Target[] var2) throws TargetException, IllegalStateException;

   TargetModuleID[] getRunningModules(ModuleType var1, Target[] var2, DeploymentOptions var3) throws TargetException, IllegalStateException;

   TargetModuleID[] getNonRunningModules(ModuleType var1, Target[] var2) throws TargetException, IllegalStateException;

   TargetModuleID[] getNonRunningModules(ModuleType var1, Target[] var2, DeploymentOptions var3) throws TargetException, IllegalStateException;

   TargetModuleID[] getAvailableModules(ModuleType var1, Target[] var2) throws TargetException, IllegalStateException;

   TargetModuleID[] getAvailableModules(ModuleType var1, Target[] var2, DeploymentOptions var3) throws TargetException, IllegalStateException;

   DeploymentConfiguration createConfiguration(DeployableObject var1) throws InvalidModuleException;

   ProgressObject distribute(Target[] var1, File var2, File var3) throws IllegalStateException;

   ProgressObject distribute(Target[] var1, InputStream var2, InputStream var3) throws IllegalStateException;

   ProgressObject distribute(Target[] var1, ModuleType var2, InputStream var3, InputStream var4) throws IllegalStateException;

   ProgressObject start(TargetModuleID[] var1) throws IllegalStateException;

   ProgressObject stop(TargetModuleID[] var1) throws IllegalStateException;

   ProgressObject undeploy(TargetModuleID[] var1) throws IllegalStateException;

   boolean isRedeploySupported();

   ProgressObject redeploy(TargetModuleID[] var1, File var2, File var3) throws UnsupportedOperationException, IllegalStateException;

   ProgressObject redeploy(TargetModuleID[] var1, InputStream var2, InputStream var3) throws UnsupportedOperationException, IllegalStateException;

   void release();

   Locale getDefaultLocale();

   Locale getCurrentLocale();

   void setLocale(Locale var1) throws UnsupportedOperationException;

   Locale[] getSupportedLocales();

   boolean isLocaleSupported(Locale var1);

   DConfigBeanVersionType getDConfigBeanVersion();

   boolean isDConfigBeanVersionSupported(DConfigBeanVersionType var1);

   void setDConfigBeanVersion(DConfigBeanVersionType var1) throws DConfigBeanVersionUnsupportedException;

   JMXDeployerHelper getHelper();

   ServerConnection getServerConnection();

   String getTaskId();

   boolean isAuthenticated();

   /** @deprecated */
   @Deprecated
   ProgressObject deactivate(TargetModuleID[] var1, DeploymentOptions var2);

   /** @deprecated */
   @Deprecated
   ProgressObject unprepare(TargetModuleID[] var1, DeploymentOptions var2);

   /** @deprecated */
   @Deprecated
   ProgressObject remove(TargetModuleID[] var1, DeploymentOptions var2);

   /** @deprecated */
   @Deprecated
   ProgressObject activate(TargetModuleID[] var1, File var2, File var3, DeploymentOptions var4) throws TargetException, IllegalStateException;

   String confirmApplicationName(boolean var1, String var2, String var3, String var4, String var5) throws RuntimeException;

   /** @deprecated */
   @Deprecated
   String confirmApplicationName(boolean var1, String var2, String var3, String var4) throws RuntimeException;

   String confirmApplicationName(boolean var1, File var2, File var3, String var4, String var5) throws RuntimeException;

   /** @deprecated */
   @Deprecated
   String confirmApplicationName(boolean var1, File var2, String var3, String var4) throws RuntimeException;

   String confirmApplicationName(boolean var1, String var2, String var3, String var4, String var5, DeploymentOptions var6) throws RuntimeException;

   /** @deprecated */
   @Deprecated
   String confirmApplicationName(boolean var1, String var2, String var3, String var4, DeploymentOptions var5) throws RuntimeException;

   String confirmApplicationName(boolean var1, File var2, File var3, String var4, String var5, DeploymentOptions var6) throws RuntimeException;

   /** @deprecated */
   @Deprecated
   String confirmApplicationName(boolean var1, File var2, String var3, String var4, DeploymentOptions var5) throws RuntimeException;
}
