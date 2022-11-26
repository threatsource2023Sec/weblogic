package weblogic.deploy.api.spi.deploy;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.management.MBeanServerConnection;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.internal.utils.JMXDeployerHelper;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.deploy.mbeans.ModuleCache;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.deploy.api.spi.status.ProgressObjectImpl;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;

public interface ServerConnection {
   JMXDeployerHelper getHelper();

   MBeanServerConnection getMBeanServerConnection();

   MBeanServerConnection getRuntimeServerConnection();

   void init(URI var1, String var2, String var3, WebLogicDeploymentManager var4) throws ServerConnectionException;

   void close(boolean var1);

   List getTargets() throws ServerConnectionException;

   List getTargets(DeploymentOptions var1) throws ServerConnectionException;

   List getTargets(DeploymentOptions var1, boolean var2) throws ServerConnectionException;

   List getServers() throws ServerConnectionException;

   List getServers(DeploymentOptions var1) throws ServerConnectionException;

   List getClusters() throws ServerConnectionException;

   List getClusters(DeploymentOptions var1) throws ServerConnectionException;

   List getHosts() throws ServerConnectionException;

   List getHosts(DeploymentOptions var1) throws ServerConnectionException;

   boolean isRunning(TargetModuleID var1) throws ServerConnectionException;

   boolean isRunning(DeploymentOptions var1, TargetModuleID var2) throws ServerConnectionException;

   void validateTargets(Target[] var1) throws TargetException, ServerConnectionException;

   void validateTargets(Target[] var1, DeploymentOptions var2) throws TargetException, ServerConnectionException;

   List getModules() throws ServerConnectionException;

   List getModules(ConfigurationMBean var1) throws ServerConnectionException;

   List getModules(DeploymentOptions var1) throws ServerConnectionException;

   List getModules(ConfigurationMBean var1, DeploymentOptions var2) throws ServerConnectionException;

   List getModulesForTarget(ModuleType var1, Target var2) throws TargetException, ServerConnectionException;

   List getModulesForTarget(ModuleType var1, Target var2, DeploymentOptions var3) throws TargetException, ServerConnectionException;

   List getModulesForTargets(ModuleType var1, Target[] var2) throws TargetException, ServerConnectionException;

   List getModulesForTargets(ModuleType var1, Target[] var2, DeploymentOptions var3) throws TargetException, ServerConnectionException;

   void populateWarUrlInChildren(TargetModuleID var1);

   void registerListener(ProgressObjectImpl var1) throws ServerConnectionException;

   void deregisterListener(ProgressObjectImpl var1) throws ServerConnectionException;

   List getServersForCluster(TargetImpl var1) throws ServerConnectionException;

   List getServersForCluster(DeploymentOptions var1, TargetImpl var2) throws ServerConnectionException;

   List getServersForJmsServer(TargetImpl var1) throws ServerConnectionException;

   List getServersForJmsServer(DeploymentOptions var1, TargetImpl var2) throws ServerConnectionException;

   List getServersForSafAgent(TargetImpl var1) throws ServerConnectionException;

   List getServersForSafAgent(DeploymentOptions var1, TargetImpl var2) throws ServerConnectionException;

   List getServersForHost(TargetImpl var1) throws ServerConnectionException;

   List getServersForHost(DeploymentOptions var1, TargetImpl var2) throws ServerConnectionException;

   TargetImpl getTarget(String var1) throws ServerConnectionException;

   TargetImpl getTarget(DeploymentOptions var1, String var2) throws ServerConnectionException;

   void setRemote();

   boolean isUploadEnabled();

   InstallDir upload(InstallDir var1, String var2, String[] var3) throws ServerConnectionException, IOException;

   InstallDir upload(InstallDir var1, String var2, String[] var3, DeploymentOptions var4) throws ServerConnectionException, IOException;

   String uploadApp(String var1, String var2, String[] var3) throws ServerConnectionException;

   String uploadApp(String var1, String var2, String[] var3, DeploymentOptions var4) throws ServerConnectionException;

   String uploadConfig(String var1, DeploymentPlanBean var2, String var3) throws ServerConnectionException;

   String uploadConfig(String var1, DeploymentPlanBean var2, String var3, DeploymentOptions var4) throws ServerConnectionException;

   ModuleCache getModuleCache();

   String uploadPlan(String var1, String var2) throws ServerConnectionException;

   String uploadPlan(String var1, String var2, DeploymentOptions var3) throws ServerConnectionException;

   void test() throws Throwable;

   void resetDomain(DomainMBean var1);

   void setLocale(Locale var1) throws IOException;

   AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntimeMBean();

   DomainMBean getDomainMBean();
}
