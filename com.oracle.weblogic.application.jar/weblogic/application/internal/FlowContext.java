package weblogic.application.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.security.jacc.PolicyConfiguration;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AppDeploymentExtension;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.ApplicationFileManager;
import weblogic.application.ApplicationLifecycleListener;
import weblogic.application.ClassLoadingConfiguration;
import weblogic.application.Module;
import weblogic.application.ModuleManager;
import weblogic.application.PermissionsDescriptorLoader;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.UpdateListener;
import weblogic.application.internal.library.LibraryManagerAggregate;
import weblogic.application.io.Ear;
import weblogic.application.naming.Environment;
import weblogic.application.utils.annotation.AnnotationMappingsImpl;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.management.DeploymentException;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;

public interface FlowContext extends ApplicationContextInternal, UpdateListener.Registration, ClassLoadingConfiguration {
   boolean isAdminState();

   void setAdminState(boolean var1);

   boolean isAdminModeSpecified();

   void setAdminModeSpecified(boolean var1);

   boolean isActive();

   void setIsActive(boolean var1);

   void setSplitDir();

   boolean isSplitDir();

   AppDDHolder getProposedPartialRedeployDDs();

   void setProposedPartialRedeployDDs(AppDDHolder var1);

   void setAdditionalModuleUris(Map var1);

   boolean isRedeployOperation();

   boolean isStopOperation();

   boolean isInternalApp();

   LibraryManagerAggregate getLibraryManagerAggregate();

   List getUpdateListeners();

   void setRootContext(Context var1);

   void setApplicationModules(Module[] var1);

   void mergeStartingModules();

   Module[] getStartingModules();

   void setStartingModules(Module[] var1);

   Module[] getStoppingModules();

   void setStoppingModules(Module[] var1);

   String[] getStoppedModules();

   Map getCustomModuleFactories();

   ModuleManager getModuleManager();

   void setAppLevelRoleMappings(Map var1);

   void setDescriptorCacheDir(File var1);

   void setApplicationDescriptor(ApplicationDescriptor var1) throws IOException, XMLStreamException;

   PolicyConfiguration[] getJACCPolicyConfigurations();

   void setCustomModuleFactories(Map var1);

   void setSplitDirectoryInfo(SplitDirectoryInfo var1);

   void setApplicationFileManager(ApplicationFileManager var1);

   void setRuntime(ApplicationRuntimeMBeanImpl var1);

   void setApplicationSecurityRealmName(String var1);

   void setApplicationPaths(File[] var1);

   void setEar(Ear var1);

   ApplicationBean getApplicationDD();

   WeblogicExtensionBean getWLExtensionDD();

   void setEnvContext(Context var1);

   ApplicationLifecycleListener[] getApplicationListeners();

   void setApplicationListeners(ApplicationLifecycleListener[] var1);

   void setPartialRedeployURIs(String[] var1);

   String[] getPartialRedeployURIs();

   void setAppListenerIdentityMappings(Map var1);

   String getAppListenerIdentity(ApplicationLifecycleListener var1);

   ApplicationVersionLifecycleNotifier getApplicationVersionNotifier();

   void setApplicationVersionNotifier(ApplicationVersionLifecycleNotifier var1);

   void addAppDeploymentExtension(AppDeploymentExtension var1, ExtensionType var2);

   Set getAppDeploymentExtensions(ExtensionType var1);

   void clearAppDeploymentExtensions();

   void resetAppClassLoader(GenericClassLoader var1);

   ModuleAttributes getModuleAttributes(String var1);

   boolean hasApplicationArchive();

   void setupApplicationFileManager(File var1) throws IOException;

   boolean needsAppEnvContextCopy();

   void setAppEnvContextCopy();

   void setAnnotationMappings(AnnotationMappingsImpl var1);

   ComponentInvocationContext getInvocationContext();

   void markShareability();

   boolean checkShareability();

   void createdSharedAppClassLoader();

   boolean wasSharedAppClassLoaderCreated();

   MultiClassFinder getAllAppFindersFromLibraries();

   MultiClassFinder getInstanceAppClassFindersFromLibraries();

   MultiClassFinder getSharedAppClassFindersFromLibraries();

   Environment getEnvironment();

   void setEnvironment(Environment var1);

   void setPojoEnvironmentBean(PojoEnvironmentBean var1);

   void setPermissionsDescriptor(PermissionsDescriptorLoader var1);

   List getLibraryClassInfoFinders();

   void addAppAnnotationScanningClassPathFirst(String var1);

   String getAppAnnotationScanningClassPath();

   File getGeneratedOutputDir();

   File getCacheDir();

   boolean isDebugEnabled();

   void debug(String var1);

   void debug(String var1, Throwable var2);

   void possiblyFixAppRuntimeState(Module[] var1) throws DeploymentException;

   public static enum ExtensionType {
      PRE,
      POST;
   }
}
