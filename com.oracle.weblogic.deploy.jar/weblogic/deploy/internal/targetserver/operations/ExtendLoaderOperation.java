package weblogic.deploy.internal.targetserver.operations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.internal.InternalDeploymentData;
import weblogic.deploy.internal.targetserver.DeployHelper;
import weblogic.deploy.internal.targetserver.datamanagement.DataUpdateRequestInfo;
import weblogic.deploy.internal.targetserver.datamanagement.ExtendLoaderData;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.classloaders.ClassLoaderManager;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;

public class ExtendLoaderOperation extends AbstractOperation {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   ExtendLoaderData localData = null;

   public ExtendLoaderOperation(long requestId, String taskId, InternalDeploymentData internalDeploymentData, BasicDeploymentMBean basicDeploymentMBean, DomainMBean proposedDomain, AuthenticatedSubject initiator, boolean restartRequired) throws DeploymentException {
      super(requestId, taskId, internalDeploymentData, basicDeploymentMBean, proposedDomain, initiator, restartRequired);
      this.operation = 14;
   }

   protected boolean isRequireBasicDeploymentMBean() {
      return false;
   }

   public void prepare() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("Preparing to extend loader.");
      }

      try {
         this.commitDataUpdate();
         ClassLoader loader = ClassLoaderManager.getWebLogicExtensionLoader();
         File loc = new File(this.localData.getLocation());
         File target = new File(this.localData.getDomainLibDir(), loc.getName());
         if ("nostage".equals(this.localData.getStagingMode())) {
            Files.copy(loc.toPath(), target.toPath(), StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
         }

         if (loader instanceof GenericClassLoader) {
            int maxTries = 3;
            int count = 0;

            JarClassFinder finder;
            while(true) {
               if (this.isDebugEnabled()) {
                  if (!target.exists()) {
                     this.debug(String.format(" target %s does not exist", target));
                  } else if (!target.isFile()) {
                     this.debug(String.format(" target %s is not a file", target));
                  } else if (!target.canRead()) {
                     this.debug(String.format(" target %s cannot be read", target));
                  } else {
                     this.debug(String.format(" target %s len(%d)", target, target.length()));
                  }
               }

               try {
                  finder = new JarClassFinder(target);
                  break;
               } catch (IOException var8) {
                  ++count;
                  if (count == maxTries) {
                     throw var8;
                  }

                  this.debug(String.format(" Exception(%s) retry(%d)", var8.getClass().getName(), count));
                  Thread.sleep(1000L);
               }
            }

            ((GenericClassLoader)loader).addClassFinder(finder);
         }

      } catch (Throwable var9) {
         this.debug(String.format(" Throwable(%s)", var9.getClass().getName()));
         DeploymentException reportExpn = DeployHelper.convertThrowable(var9);
         this.complete(2, reportExpn);
         throw reportExpn;
      }
   }

   protected void doCommit() throws IOException, DeploymentException {
      this.complete(3, (Exception)null);
   }

   protected boolean isCancelNecessary() {
      return false;
   }

   protected ExtendLoaderData createLocalData(String staging) {
      String[] files = this.internalDeploymentData.getExternalDeploymentData().getFiles();
      String location = files != null && files.length > 0 ? files[0] : null;
      File libDir = new File(new File(DomainDir.getRootDir()), "lib");
      return new ExtendLoaderData(location, libDir, (String)null, staging);
   }

   public void initDataUpdate() throws DeploymentException {
      if (this.isDebugEnabled()) {
         this.debug("Initializing data update for extending loader");
      }

      final boolean isStaging = false;
      boolean useDefaultStaging = true;
      DeploymentData extDeplData = this.internalDeploymentData.getExternalDeploymentData();
      if (extDeplData != null) {
         DeploymentOptions opts = extDeplData.getDeploymentOptions();
         if (opts != null) {
            String optsStageMode = opts.getStageMode();
            if (optsStageMode != null) {
               useDefaultStaging = false;
               isStaging = "stage".equals(optsStageMode);
            }
         }
      }

      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      if (useDefaultStaging) {
         isStaging = !runtimeAccess.isAdminServer();
      }

      this.localData = this.createLocalData(isStaging ? "stage" : "nostage");
      this.localData.initDataUpdate(new DataUpdateRequestInfo() {
         public List getDeltaFiles() {
            List deltaFiles = new ArrayList();
            if (ExtendLoaderOperation.this.localData.getLocation() != null) {
               deltaFiles.add(ExtendLoaderOperation.this.localData.getLocation());
            }

            return deltaFiles;
         }

         public List getTargetFiles() {
            List targetFiles = new ArrayList();
            if (ExtendLoaderOperation.this.localData.getDomainLibDir() != null) {
               targetFiles.add(ExtendLoaderOperation.this.localData.getDomainLibDir().getAbsolutePath());
            }

            return targetFiles;
         }

         public long getRequestId() {
            return ExtendLoaderOperation.this.requestId;
         }

         public boolean isStatic() {
            return false;
         }

         public boolean isDelete() {
            return false;
         }

         public boolean isPlanUpdate() {
            return false;
         }

         public boolean isStaging() {
            return isStaging;
         }

         public boolean isPlanStaging() {
            return false;
         }
      });
   }

   protected void prepareDataUpdate(String handlerType) throws DeploymentException {
      this.initDataUpdate();
      this.localData.prepareDataUpdate(handlerType);
   }

   protected void commitDataUpdate() throws DeploymentException {
      this.localData.commitDataUpdate();
   }

   protected void cancelDataUpdate() {
      this.localData.cancelDataUpdate(this.getRequestId());
   }

   protected void closeDataUpdate(boolean success) {
      this.localData.closeDataUpdate(this.getRequestId(), success);
   }
}
