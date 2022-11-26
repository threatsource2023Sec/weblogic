package weblogic.application.internal.library;

import java.security.AccessController;
import weblogic.application.AdminModeCallback;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextFactory;
import weblogic.application.Deployment;
import weblogic.application.DeploymentContext;
import weblogic.application.internal.FlowContext;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryDeploymentException;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.LibraryRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class LibraryDeployment implements Deployment {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final LibraryRegistry libraryRegistry = LibraryRegistry.getRegistry();
   private final LibraryDefinition def;
   private final FlowContext appCtx;
   private final LibraryMBean mbean;
   private final String partitionName;

   public LibraryDeployment(LibraryDefinition def, LibraryMBean mbean) {
      this.def = def;
      this.mbean = mbean;
      this.appCtx = (FlowContext)ApplicationContextFactory.getApplicationContextFactory().newApplicationContext(mbean.getName());
      this.partitionName = this.appCtx.getPartitionName();
   }

   public LibraryMBean getLibraryMBean() {
      return this.mbean;
   }

   public void prepare(DeploymentContext deploymentContext) throws LibraryDeploymentException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var3 = null;

      try {
         try {
            LibraryLoggingUtils.initLibraryDefinition(this.def);
            this.registerLibrary();
         } catch (LoggableLibraryProcessingException var13) {
            throw new LibraryDeploymentException(var13.getLoggable().getMessage());
         }

         this.initRuntime();
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void initRuntime() throws LibraryDeploymentException {
      LibraryRuntimeMBeanImpl runtime = null;

      try {
         ComponentMBean[] components = null;
         if (this.mbean.getAppMBean() != null) {
            components = this.mbean.getAppMBean().getComponents();
         }

         runtime = new LibraryRuntimeMBeanImpl(this.def.getLibData(), this.mbean.getApplicationIdentifier(), components);
      } catch (ManagementException var3) {
         throw new LibraryDeploymentException(var3);
      }

      this.def.setRuntime(runtime);
   }

   private void registerLibrary() throws LoggableLibraryProcessingException {
      if (LibraryUtils.isDebugOn()) {
         LibraryUtils.debug("Registering: " + this.def + "(" + this.def.getType() + ")");
      }

      LibraryLoggingUtils.registerLibrary(this.def, this.partitionName, true);
      if (LibraryUtils.isDebugOn()) {
         LibraryUtils.debug("Registry has: " + libraryRegistry.toString());
      }

   }

   public void unprepare(DeploymentContext deploymentContext) throws LibraryDeploymentException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var3 = null;

      try {
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("unprepare");
         }

         this.unregisterLibrary();
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void remove(DeploymentContext deploymentContext) throws LibraryDeploymentException {
      try {
         ManagedInvocationContext mic = this.setInvocationContext();
         Throwable var3 = null;

         try {
            LibraryLoggingUtils.errorRemoveLibrary(this.def);
         } catch (Throwable var13) {
            var3 = var13;
            throw var13;
         } finally {
            if (mic != null) {
               if (var3 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  mic.close();
               }
            }

         }

      } catch (LoggableLibraryProcessingException var15) {
         throw new LibraryDeploymentException(var15.getLoggable().getMessage());
      }
   }

   private void unregisterLibrary() throws LibraryDeploymentException {
      try {
         this.def.getRuntimeImpl().unregister();
      } catch (ManagementException var2) {
         throw new LibraryDeploymentException(var2);
      }

      if (LibraryUtils.isDebugOn()) {
         LibraryUtils.debug("Removing from registry...: " + this.def);
      }

      libraryRegistry.remove(this.def, this.partitionName);
      if (LibraryUtils.isDebugOn()) {
         LibraryUtils.debug("...Now registry has: " + libraryRegistry.toString());
      }

   }

   public void gracefulProductionToAdmin(DeploymentContext deploymentContext) {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var3 = null;

      try {
         AdminModeCallback callback = null;
         if (deploymentContext != null) {
            callback = deploymentContext.getAdminModeCallback();
         }

         if (callback != null) {
            callback.completed();
         }
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void forceProductionToAdmin(DeploymentContext deploymentContext) {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var3 = null;

      try {
         AdminModeCallback callback = null;
         if (deploymentContext != null) {
            callback = deploymentContext.getAdminModeCallback();
         }

         if (callback != null) {
            callback.completed();
         }
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void assertUndeployable() throws DeploymentException {
      ManagedInvocationContext mic = this.setInvocationContext();
      Throwable var2 = null;

      try {
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("Got Library related undeployment operation: " + this.mbean.getName());
         }

         verifyLibrary(this.mbean);
      } catch (Throwable var11) {
         var2 = var11;
         throw var11;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var10) {
                  var2.addSuppressed(var10);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void activate(DeploymentContext deploymentContext) {
   }

   public void deactivate(DeploymentContext deploymentContext) {
   }

   public void prepareUpdate(DeploymentContext deploymentContext) {
   }

   public void activateUpdate(DeploymentContext deploymentContext) {
   }

   public void rollbackUpdate(DeploymentContext deploymentContext) {
   }

   public void adminToProduction(DeploymentContext deploymentContext) {
   }

   public void stop(DeploymentContext deploymentContext) {
   }

   public void start(DeploymentContext deploymentContext) {
   }

   public boolean deregisterCallback(int tag) {
      return false;
   }

   public ApplicationContext getApplicationContext() {
      return this.appCtx;
   }

   private static void verifyLibrary(LibraryMBean lib) throws DeploymentException {
      String libName = lib.getName();
      if (LibraryUtils.isDebugOn()) {
         LibraryUtils.debug("Trying to get libruntimembean for lib " + libName);
      }

      LibraryRuntimeMBean l = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupLibraryRuntime(libName);
      if (l != null) {
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("is " + l.getName() + " used? " + l.isReferenced());
         }

         if (l.isReferenced()) {
            throw new DeploymentException(formatErrorMessage(l));
         }
      }
   }

   private static String formatErrorMessage(LibraryRuntimeMBean l) {
      String serverName = ManagementUtils.getServerName();
      StringBuffer sb = new StringBuffer();
      sb.append("Cannot undeploy library ").append(LibraryUtils.toString(l.getLibraryName(), l.getSpecificationVersion(), l.getImplementationVersion())).append(" from server ").append(serverName).append(", because the following deployed applications reference it: ");
      String[] referencingAppNames = l.getReferencingNames();

      for(int i = 0; i < referencingAppNames.length; ++i) {
         sb.append(referencingAppNames[i]);
         if (i < referencingAppNames.length - 1) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   private ManagedInvocationContext setInvocationContext() {
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
      ComponentInvocationContext cic = this.appCtx.getInvocationContext();
      return cicm.setCurrentComponentInvocationContext(cic);
   }
}
