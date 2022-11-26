package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.MultiDataStream;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.utils.Debug;
import weblogic.utils.NestedThrowable;

public abstract class Data {
   private static final String INDENT = "  ";
   private final String location;
   private String lockPath;
   private boolean stagingEnabled;
   private String stagingMode;
   private String planStagingMode;
   private Map temporaryFiles;
   private Map dataUpdates;
   private DataUpdate currentDataUpdate;

   protected Data(String location, String lockPath) {
      this(location, lockPath, false);
   }

   protected Data(String location, String lockPath, boolean stagingEnabled) {
      this.stagingMode = null;
      this.planStagingMode = null;
      this.temporaryFiles = new HashMap();
      this.dataUpdates = new HashMap();
      this.currentDataUpdate = null;
      this.location = location;
      this.lockPath = lockPath;
      this.stagingEnabled = stagingEnabled;
   }

   protected Data(String location, String lockPath, String stagingMode) {
      this(location, lockPath, stagingMode, stagingMode);
   }

   protected Data(String location, String lockPath, String stagingMode, String planStagingMode) {
      this.stagingMode = null;
      this.planStagingMode = null;
      this.temporaryFiles = new HashMap();
      this.dataUpdates = new HashMap();
      this.currentDataUpdate = null;
      this.location = location;
      this.lockPath = lockPath;
      this.stagingMode = stagingMode;
      this.planStagingMode = planStagingMode;
      this.stagingEnabled = "stage".equals(this.stagingMode) || "stage".equals(this.planStagingMode);
   }

   public Map getTemporaryFiles() {
      return this.temporaryFiles;
   }

   public final String getLocation() {
      return this.location;
   }

   public final String getLockPath() {
      return this.lockPath;
   }

   public final boolean isStagingEnabled() {
      return this.stagingEnabled;
   }

   public final boolean isAppStagingEnabled() {
      return "stage".equals(this.stagingMode);
   }

   public final boolean isPlanStagingEnabled() {
      return this.planStagingMode == null ? this.isAppStagingEnabled() : "stage".equals(this.planStagingMode);
   }

   public String getStagingMode() {
      return this.stagingMode;
   }

   public String getPlanStagingMode() {
      return this.planStagingMode;
   }

   protected final DataUpdate getCurrentDataUpate() {
      return this.currentDataUpdate;
   }

   public void initDataUpdate(DataUpdateRequestInfo requestInfo) {
      DataUpdate update = this.createDataUpdate(requestInfo);
      if (update != null) {
         this.setCurrentDataUpdate(update);
      }
   }

   public final void prepareDataUpdate(String handlerType) throws DeploymentException {
      try {
         DataUpdate currentUpdate = this.getCurrentDataUpate();
         if (currentUpdate == null) {
            return;
         }

         this.prePrepareDataUpdate();
         currentUpdate.download(handlerType);
         MultiDataStream downloadedStream = currentUpdate.getDownloadedStream();
         if (downloadedStream != null) {
            Iterator dataStreams = downloadedStream.getDataStreams();

            while(dataStreams.hasNext()) {
               FileDataStream next = (FileDataStream)dataStreams.next();
               this.temporaryFiles.put(next.getName(), next.getFile().getAbsolutePath());
            }
         }

         this.postPrepareDataUpdate();
      } catch (Throwable var6) {
         this.resetOnFailure(var6);
      }

   }

   public final void commitDataUpdate() throws DeploymentException {
      try {
         DataUpdate currentUpdate = this.getCurrentDataUpate();
         if (currentUpdate != null) {
            this.preCommitDataUpdate();
            currentUpdate.update();
            this.postCommitDataUpdate();
            return;
         }
      } catch (Throwable var5) {
         this.resetOnFailure(var5);
         return;
      } finally {
         this.setCurrentDataUpdate((DataUpdate)null);
      }

   }

   public final void cancelDataUpdate(long reqId) {
      DataUpdate update = this.getDataUpdate(reqId);
      if (update != null) {
         update.cancel();
         this.closeDataUpdate(reqId, false);
      }
   }

   public void closeDataUpdate(long requestId, boolean success) {
      DataUpdate update = this.getDataUpdate(requestId);
      if (update != null) {
         update.close(success);
         this.removeDataUpdate(requestId);
      }
   }

   public abstract void releaseLock(long var1);

   public boolean removeStagedFiles() {
      return true;
   }

   public abstract File getSourceFile();

   protected abstract DataUpdate createDataUpdate(DataUpdateRequestInfo var1);

   protected abstract String getRootLocation();

   protected void prePrepareDataUpdate() {
      if (isDebugEnabled()) {
         debug(" Preparing DataUpdate for : " + this);
      }

   }

   protected void postPrepareDataUpdate() {
      if (isDebugEnabled()) {
         debug(" Prepared DataUpdate for : " + this);
      }

   }

   protected void preCommitDataUpdate() throws DeploymentException {
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
      this.closeCurrentDataUpdate();
      this.setCurrentDataUpdate((DataUpdate)null);
   }

   protected final DataUpdate getDataUpdate(long requestId) {
      if (isDebugEnabled()) {
         debug(" dataUpdates.get(" + requestId + ")");
      }

      synchronized(this.dataUpdates) {
         return (DataUpdate)this.dataUpdates.get(requestId);
      }
   }

   protected final void putDataUpdate(long requestId, DataUpdate update) {
      if (isDebugEnabled()) {
         debug(" dataUpdates.put(" + requestId + " = " + update);
      }

      synchronized(this.dataUpdates) {
         this.dataUpdates.put(requestId, update);
      }
   }

   protected final void removeDataUpdate(long requestId) {
      if (isDebugEnabled()) {
         debug(" dataUpdates.remove(" + requestId + ")");
      }

      synchronized(this.dataUpdates) {
         DataUpdate update = (DataUpdate)this.dataUpdates.remove(requestId);
         if (update != null && update == this.currentDataUpdate) {
            this.currentDataUpdate = null;
         }

      }
   }

   protected void dumpFiles(File theFile, String indent) {
      if (isDebugEnabled()) {
         debug(indent + theFile);
         if (theFile.isDirectory()) {
            File[] allFiles = theFile.listFiles();
            if (allFiles != null) {
               for(int i = 0; i < allFiles.length; ++i) {
                  String theIndent = indent + "  ";
                  this.dumpFiles(allFiles[i], theIndent);
               }
            }
         }
      }

   }

   public abstract void deleteFile(String var1, long var2);

   public abstract File getFileFor(long var1, String var3);

   private void resetOnFailure(Throwable t) throws DeploymentException {
      Debug.assertion(t != null);
      this.onFailure(t);
      throw this.convertThrowable(t);
   }

   private DeploymentException convertThrowable(Throwable thrown) {
      if (weblogic.deploy.common.Debug.isDeploymentDebugEnabled()) {
         thrown.printStackTrace();
      }

      return this.handleException(thrown, thrown.getMessage());
   }

   private DeploymentException handleException(Throwable expn, String msg) {
      if (expn instanceof DeploymentException) {
         return (DeploymentException)expn;
      } else if (expn instanceof ManagementException) {
         ManagementException managementExpn = (ManagementException)expn;
         Throwable cause = managementExpn.getNested();
         DeploymentException de = cause != null ? new DeploymentException(managementExpn.getMessage(), cause) : new DeploymentException(managementExpn.getMessage());
         de.setStackTrace(managementExpn.getStackTrace());
         return de;
      } else {
         return new DeploymentException(msg, this.getWrappedException(expn));
      }
   }

   private Throwable getWrappedException(Throwable t) {
      Throwable cause;
      if (t instanceof NestedThrowable) {
         cause = ((NestedThrowable)t).getNested();
      } else {
         cause = t.getCause();
      }

      return cause == null ? t : this.getWrappedException(cause);
   }

   protected static void debug(String msg) {
      weblogic.deploy.common.Debug.deploymentDebug(msg);
   }

   protected static boolean isDebugEnabled() {
      return weblogic.deploy.common.Debug.isDeploymentDebugEnabled();
   }

   private void closeCurrentDataUpdate() {
      DataUpdate update = this.getCurrentDataUpate();
      if (update != null) {
         this.cancelDataUpdate(update.getRequestId());
         this.setCurrentDataUpdate((DataUpdate)null);
      }

   }

   private void setCurrentDataUpdate(DataUpdate update) {
      this.currentDataUpdate = update;
      if (this.currentDataUpdate != null) {
         this.putDataUpdate(this.currentDataUpdate.getRequestId(), update);
      }

   }
}
