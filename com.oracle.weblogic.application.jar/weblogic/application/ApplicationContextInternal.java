package weblogic.application;

import java.io.File;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.management.MBeanServer;
import javax.naming.Context;
import javax.security.auth.login.LoginException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.internal.ApplicationRuntimeMBeanImpl;
import weblogic.application.io.Ear;
import weblogic.application.library.LibraryProvider;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.wl.CdiDescriptorBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.api.WorkManagerCollector;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;

public interface ApplicationContextInternal extends ApplicationContext, GenericApplicationContext, DescriptorUpdater, WorkManagerCollector {
   BasicDeploymentMBean getBasicDeploymentMBean();

   SystemResourceMBean getSystemResourceMBean();

   DomainMBean getProposedDomain();

   DomainMBean getEffectiveDomain();

   boolean requiresRestart();

   void setDeploymentInitiator(AuthenticatedSubject var1);

   AuthenticatedSubject getDeploymentInitiator();

   ConcurrentManagedObjectCollection getConcurrentManagedObjectCollection();

   boolean isEar();

   Ear getEar();

   ApplicationBean getApplicationDD();

   WeblogicApplicationBean getWLApplicationDD();

   File[] getApplicationPaths();

   String getApplicationFileName();

   String getStagingPath();

   String getOutputPath();

   ApplicationRuntimeMBeanImpl getRuntime();

   ApplicationFileManager getApplicationFileManager();

   SplitDirectoryInfo getSplitDirectoryInfo();

   Map getApplicationParameters();

   void setApplicationParameters(Map var1);

   Module[] getApplicationModules();

   Module[] findModules(String... var1);

   LibraryProvider getLibraryProvider(String var1);

   DeploymentPlanBean findDeploymentPlan();

   SecurityRole getSecurityRole(String var1);

   File getDescriptorCacheDir();

   boolean isStaticDeploymentOperation();

   int getDeploymentOperation();

   void addApplicationListener(ApplicationLifecycleListener var1);

   Object getSchemaTypeLoader(ClassLoader var1);

   void setSchemaTypeLoader(ClassLoader var1, Object var2);

   void clear();

   SubDeploymentMBean[] getLibrarySubDeployments();

   Object putUserObject(Object var1, Object var2);

   Object getUserObject(Object var1);

   Object removeUserObject(Object var1);

   AppDeploymentExtension getAppDeploymentExtension(String var1);

   ModuleContext getModuleContext(String var1);

   Context getRootContext();

   /** @deprecated */
   @Deprecated
   Set getAnnotatedClasses(Class... var1) throws AnnotationProcessingException;

   ClassInfoFinder getClassInfoFinder();

   /** @deprecated */
   @Deprecated
   ApplicationArchive getApplicationArchive();

   void setupApplicationFileManager(File var1) throws IOException;

   Map getModuleURItoIdMap();

   void setModuleURItoIdMap(Map var1);

   Collection getModuleExtensions(String var1);

   String getPartitionName();

   GenericClassLoader getPartitionClassLoader();

   ResourceGroupMBean getResourceGroupMBean();

   boolean isDeployedThroughResourceGroupTemplate();

   PojoEnvironmentBean getPojoEnvironmentBean();

   PermissionsBean getPermissionsBean() throws IOException, XMLStreamException;

   boolean isPojoAnnotationEnabled();

   void freeDeploymentMemory();

   SecurityProvider getSecurityProvider();

   CdiDescriptorBean getCdiDescriptorBean();

   String getCdiPolicy();

   boolean isSpecifiedTargetsOnly();

   String getAppClassLoaderClassPath();

   void reloadConfiguration() throws ModuleException;

   public interface SecurityProvider {
      boolean isIdentityValid(String var1, String var2);

      boolean isIdentityAdministrator(String var1, String var2);

      boolean isUserAnAdministrator(AuthenticatedSubject var1);

      boolean isUserAnonymous(AuthenticatedSubject var1);

      boolean isAdminPrivilegeEscalation(AuthenticatedSubject var1, String var2, String var3);

      Object invokePrivilegedAction(String var1, String var2, PrivilegedAction var3) throws LoginException;

      Object invokePrivilegedAction(AuthenticatedSubject var1, PrivilegedAction var2);

      Object invokePrivilegedAction(AuthenticatedSubject var1, PrivilegedExceptionAction var2) throws PrivilegedActionException;

      Object invokePrivilegedActionAsAnonymous(PrivilegedExceptionAction var1) throws PrivilegedActionException;

      Object invokePrivilegedActionAsCurrent(PrivilegedAction var1);

      boolean isJACCEnabled();

      boolean isWLSRuntimeAccessInitialized();

      RuntimeAccess getWLSRuntimeAccess();

      MBeanServer getRuntimeMBeanServer();

      MBeanServer getWLSDomainRuntimeMBeanServer();

      MBeanServer getWLSEditMBeanServer();
   }
}
