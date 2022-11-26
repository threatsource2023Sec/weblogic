package weblogic.management.deploy.classdeployment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.common.T3ShutdownDef;
import weblogic.common.T3StartupDef;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.DeploymentType;
import weblogic.deploy.internal.TargetHelper;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.ClassDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ShutdownClassMBean;
import weblogic.management.configuration.StartupClassMBean;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.ServiceFailureException;
import weblogic.t3.srvr.T3Srvr;
import weblogic.utils.AssertionError;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StringUtils;
import weblogic.utils.TypeConversionUtils;
import weblogic.utils.progress.ProgressTrackerRegistrar;
import weblogic.utils.progress.ProgressTrackerService;
import weblogic.utils.progress.ProgressWorkHandle;

@Service
public class ClassDeploymentManager implements DeploymentHandler {
   public static final String PROGRESS_SUBSYSTEM_NAME = "User Startup Classes";
   private Set loadAfterAppAdminState;
   private Set loadBeforeAppActivation;
   private Set loadAfterAppsRunning;
   private Set shutdownClasses;
   @Inject
   private ProgressTrackerRegistrar progressMeterRegistrar;
   private ProgressTrackerService progressMeter;
   private boolean startupComplete;
   @Inject
   private RuntimeAccess runtimeAccess;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final Class[] MAIN_SIGNATURE = new Class[]{String[].class};

   public ClassDeploymentManager() {
      this.loadAfterAppAdminState = Collections.synchronizedSet(new TreeSet(DeploymentType.DEPLOYMENT_HANDLER.getComparator()));
      this.loadBeforeAppActivation = Collections.synchronizedSet(new TreeSet(DeploymentType.DEPLOYMENT_HANDLER.getComparator()));
      this.loadAfterAppsRunning = Collections.synchronizedSet(new TreeSet(DeploymentType.DEPLOYMENT_HANDLER.getComparator()));
      this.shutdownClasses = Collections.synchronizedSet(new TreeSet(DeploymentType.DEPLOYMENT_HANDLER.getComparator()));
      this.startupComplete = false;
   }

   /** @deprecated */
   @Deprecated
   public static ClassDeploymentManager getInstance() {
      return ClassDeploymentManager.Initializer.SINGLETON;
   }

   @PostConstruct
   private void postConstruct() {
      this.progressMeter = this.progressMeterRegistrar.registerProgressTrackerSubsystem("User Startup Classes");
   }

   @PreDestroy
   private void preDestroy() {
      if (this.progressMeter != null) {
         this.progressMeter.close();
         this.progressMeter = null;
      }
   }

   public void prepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      if (deployment instanceof StartupClassMBean) {
         if (this.startupComplete) {
            return;
         }

         StartupClassMBean startup = (StartupClassMBean)deployment;
         if (startup.getLoadBeforeAppDeployments()) {
            return;
         }

         if (startup.getLoadBeforeAppActivation()) {
            this.loadBeforeAppActivation.add(startup);
         } else if (startup.getLoadAfterAppsRunning()) {
            this.loadAfterAppsRunning.add(startup);
         } else {
            this.loadAfterAppAdminState.add(startup);
         }
      }

      if (deployment instanceof ShutdownClassMBean) {
         this.shutdownClasses.add((ShutdownClassMBean)deployment);
      }

   }

   public void activateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
   }

   public void deactivateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      ServerRuntimeMBean serverRuntime = this.runtimeAccess.getServerRuntime();
      if (serverRuntime.getStateVal() != 4 && serverRuntime.getStateVal() != 7) {
         if (deployment instanceof ShutdownClassMBean) {
            this.shutdownClasses.remove(deployment);
         }

      }
   }

   public void unprepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
   }

   void runStartupsBeforeAppDeployments() throws ServiceFailureException {
      DomainMBean domain = this.runtimeAccess.getDomain();
      StartupClassMBean[] startupClasses = domain.getStartupClasses();
      if (startupClasses != null) {
         Set loadBeforeAppDeployments = Collections.synchronizedSet(new TreeSet(DeploymentType.DEPLOYMENT_HANDLER.getComparator()));
         int i = 0;

         for(int len = startupClasses.length; i < len; ++i) {
            if (startupClasses[i].getLoadBeforeAppDeployments() && TargetHelper.isTargetedLocally(startupClasses[i])) {
               loadBeforeAppDeployments.add(startupClasses[i]);
            }
         }

         this.invokeClassDeployments(loadBeforeAppDeployments);
      }
   }

   public void runStartupsBeforeAppActivation(ConfigurationMBean scopeMBean) throws ServiceFailureException {
      if (scopeMBean instanceof DomainMBean) {
         this.invokeTMDeployment();
         this.invokeClassDeployments(this.loadBeforeAppActivation);
      }

      this.startupComplete = true;
      String startupMode = this.runtimeAccess.getServer().getStartupMode();
      if (this.progressMeter != null && !"RUNNING".equals(startupMode)) {
         this.progressMeter.finished();
      }

   }

   public void runStartupsAfterAppAdminState() throws ServiceFailureException {
      this.invokeClassDeployments(this.loadAfterAppAdminState);
   }

   public void runStartupsAfterAppsRunning() throws ServiceFailureException {
      this.invokeClassDeployments(this.loadAfterAppsRunning);
      if (this.progressMeter != null) {
         this.progressMeter.finished();
      }

   }

   void runShutdownClasses() throws ServiceFailureException {
      this.invokeClassDeployments(this.shutdownClasses);
   }

   public void invokeTMDeployment() throws ServiceFailureException {
      try {
         this.invokeClass("JTAStartupClass", "weblogic.transaction.internal.StartupClass", (String)null);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   private void invokeClassDeployments(Collection deployments) throws ServiceFailureException {
      Iterator i = deployments.iterator();

      while(i.hasNext()) {
         this.invokeClassDeployment((ClassDeploymentMBean)i.next());
      }

      deployments.clear();
   }

   protected void invokeClassDeployment(final ClassDeploymentMBean classDeployment) throws ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("deploying ClassDeploymentMBean: " + classDeployment.getName() + ", classname: " + classDeployment.getClassName() + ", arguments: " + classDeployment.getArguments() + ", kernal id: " + kernelId);
      }

      ServiceFailureException result = (ServiceFailureException)SecurityServiceManager.runAsForUserCode(kernelId, kernelId, new PrivilegedAction() {
         public ServiceFailureException run() {
            long timeToComplete = 0L;
            if (Debug.isDeploymentDebugEnabled()) {
               timeToComplete = System.currentTimeMillis();
            }

            ProgressWorkHandle workHandle = null;

            ServiceFailureException var7;
            try {
               ApplicationVersionUtils.setCurrentAdminMode(true);
               workHandle = ClassDeploymentManager.this.progressMeter.startWork(classDeployment.getClassName());
               ClassDeploymentManager.this.invokeClass(classDeployment.getName(), classDeployment.getClassName(), classDeployment.getArguments());
               workHandle.complete();
               return null;
            } catch (Exception var11) {
               if (workHandle != null) {
                  workHandle.fail();
               }

               Throwable t = var11;
               if (var11 instanceof InvocationTargetException) {
                  t = ((InvocationTargetException)var11).getTargetException();
               }

               if (!(classDeployment instanceof StartupClassMBean)) {
                  if (var11 instanceof InvocationTargetException) {
                     t = ((InvocationTargetException)var11).getTargetException();
                  }

                  T3SrvrLogger.logFailInvokeShutdownClass(classDeployment.getName(), (Throwable)t);
                  return null;
               }

               StartupClassMBean startupClass = (StartupClassMBean)classDeployment;
               if (!startupClass.getFailureIsFatal()) {
                  T3SrvrLogger.logFailInvokeStartupClass(classDeployment.getName(), (Throwable)t);
                  return null;
               }

               var7 = new ServiceFailureException("Can't start server due to classDeployment class failure " + classDeployment.getName(), (Throwable)t);
            } finally {
               ApplicationVersionUtils.unsetCurrentAdminMode();
               if (Debug.isDeploymentDebugEnabled()) {
                  timeToComplete = System.currentTimeMillis() - timeToComplete;
                  Debug.deploymentDebug("deployed ClassDeploymentMBean: " + classDeployment.getName() + ", classname: " + classDeployment.getClassName() + ", took: " + timeToComplete + " ms");
               }

            }

            return var7;
         }
      });
      if (result != null) {
         throw result;
      }
   }

   private void invokeClass(String name, String className, String arguments) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
      Class c = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
      if (T3StartupDef.class.isAssignableFrom(c)) {
         this.invokeStartup(name, c, arguments);
      } else if (T3ShutdownDef.class.isAssignableFrom(c)) {
         invokeShutdown(name, c, arguments);
      } else {
         this.invokeMain(c, arguments);
      }
   }

   private final void invokeStartup(String startupName, Class c, String argString) throws InstantiationException, IllegalAccessException, InvocationTargetException {
      T3StartupDef startup = (T3StartupDef)c.newInstance();
      Hashtable args = new Hashtable();
      TypeConversionUtils.stringToDictionary(argString, args);
      startup.setServices(T3Srvr.getT3Srvr().getT3Services());
      T3SrvrLogger.logInvokingStartup(c.getName(), argString);

      try {
         String result = startup.startup(startupName, args);
         T3SrvrLogger.logStartupClassReports(c.getName(), result);
      } catch (Exception var7) {
         throw new InvocationTargetException(var7);
      }
   }

   private static final void invokeShutdown(String shutdownName, Class c, String argString) throws InstantiationException, IllegalAccessException, InvocationTargetException {
      T3ShutdownDef shutdown = (T3ShutdownDef)c.newInstance();
      Hashtable args = new Hashtable();
      TypeConversionUtils.stringToDictionary(argString, args);
      shutdown.setServices(T3Srvr.getT3Srvr().getT3Services());
      T3SrvrLogger.logInvokingShutdown(c.getName(), argString);

      try {
         String result = shutdown.shutdown(shutdownName, args);
         T3SrvrLogger.logShutdownClassReports(c.getName(), result);
      } catch (Exception var6) {
         throw new InvocationTargetException(var6);
      }
   }

   private void invokeMain(Class c, String argString) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
      Object[] args = null;
      String[] m;
      if (argString != null) {
         m = StringUtils.splitCompletely(argString, " ");
         args = new Object[]{m};
      } else {
         args = new Object[]{new String[0]};
      }

      m = null;
      int classMods = c.getModifiers();
      if (!Modifier.isPublic(classMods)) {
         throw new IllegalAccessException(c.getName() + " is not a public class");
      } else if (Modifier.isInterface(classMods)) {
         throw new IllegalAccessException(c.getName() + " is an interface, not a public class");
      } else {
         Method m;
         try {
            m = c.getMethod("main", MAIN_SIGNATURE);
         } catch (NoSuchMethodException var9) {
            throw new NoSuchMethodException(c.getName() + " does not define 'public static void main(String[])'");
         }

         int mods = m.getModifiers();
         if (Modifier.isStatic(mods) && Modifier.isPublic(mods)) {
            try {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("invoking main on: " + c.getName() + ", with " + argString);
               }

               T3SrvrLogger.logInvokingClass(c.getName(), argString);
               m.invoke((Object)null, args);
            } catch (IllegalArgumentException var8) {
               throw new AssertionError("Should never occur", var8);
            }
         } else {
            throw new IllegalAccessException(c.getName() + ".main(String[]) must must be a public static method");
         }
      }
   }

   private static class Initializer {
      static final ClassDeploymentManager SINGLETON = (ClassDeploymentManager)LocatorUtilities.getService(ClassDeploymentManager.class);
   }
}
