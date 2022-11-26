package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;

public class ConfigData extends Data {
   private String rootLocation = null;

   public ConfigData(String location, String lockPath, String stagingMode) {
      super(location, lockPath, stagingMode);
      this.rootLocation = DomainDir.getRootDir();
   }

   protected DataUpdate createDataUpdate(DataUpdateRequestInfo reqInfo) {
      return this.isStagingEnabled() ? new ConfigDataUpdate(this, reqInfo) : null;
   }

   public final String getRootLocation() {
      return this.rootLocation;
   }

   public File getSourceFile() {
      return new File(this.getLocation());
   }

   protected final void preCommitDataUpdate() throws DeploymentException {
      if (isDebugEnabled()) {
         debug(" Commiting DataUpdate for : " + this);
      }

   }

   protected void postCommitDataUpdate() {
      if (isDebugEnabled()) {
         debug(" Committed DataUpdate for : " + this);
      }

   }

   protected void onFailure(Throwable failure) {
      if (isDebugEnabled()) {
         debug(" Failure occured : " + this);
      }

      super.onFailure(failure);
   }

   public final void deleteFile(String targetURI, long requestId) {
      DataUpdate update = this.getDataUpdate(requestId);
      if (update != null) {
         update.deleteFile(targetURI);
      }
   }

   public final void releaseLock(long requestId) {
      DataUpdate update = this.getDataUpdate(requestId);
      if (update != null) {
         ((ConfigDataUpdate)update).releaseLock();
      }
   }

   public final File getFileFor(long requestId, String targetPath) {
      DataUpdate update = this.getDataUpdate(requestId);
      return update == null ? new File(this.getRootLocation(), targetPath) : update.getFileFor(targetPath);
   }
}
