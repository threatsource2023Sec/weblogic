package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ExtendLoaderData extends Data {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final File domainLibDir;

   public ExtendLoaderData(String location, File domainLibDir, String lockPath, String stagingMode) {
      super(location, lockPath, stagingMode);
      this.domainLibDir = domainLibDir;
   }

   public void releaseLock(long requestId) {
      DataUpdate update = this.getDataUpdate(requestId);
      if (update != null) {
         ((ExtendLoaderUpdate)update).releaseLock();
      }
   }

   public File getSourceFile() {
      return null;
   }

   public File getDomainLibDir() {
      return this.domainLibDir;
   }

   protected DataUpdate createDataUpdate(DataUpdateRequestInfo reqInfo) {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      if (!runtimeAccess.isAdminServer() && !runtimeAccess.isAdminServerAvailable()) {
         if (isDebugEnabled()) {
            debug(" createDataUpdate - AdminServer unavailable");
         }

         return null;
      } else {
         return this.isStagingEnabled() ? new ExtendLoaderUpdate(this, reqInfo) : null;
      }
   }

   protected String getRootLocation() {
      return null;
   }

   public void deleteFile(String targetURI, long requestId) {
   }

   public File getFileFor(long requestId, String targetPath) {
      return null;
   }
}
