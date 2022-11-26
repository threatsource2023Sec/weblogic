package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import weblogic.application.archive.utils.ArchiveUtils;
import weblogic.deploy.common.DeploymentConstants;
import weblogic.deploy.service.DataStream;
import weblogic.deploy.service.DataTransferHandler;
import weblogic.deploy.service.DataTransferRequest;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.datatransferhandlers.DataHandlerManager;
import weblogic.management.DeploymentException;

public class AppDataUpdate extends DataUpdate implements DeploymentConstants {
   private boolean isFullDataUpdate = false;
   private boolean isPlanUpdate = false;
   private boolean isStaging = false;
   private boolean isPlanStaging = false;

   public AppDataUpdate(Data localData, DataUpdateRequestInfo requestInfo) {
      super(localData, requestInfo);
      List theDeltaFiles = requestInfo.getDeltaFiles();
      this.isFullDataUpdate = theDeltaFiles == null || theDeltaFiles.isEmpty();
      if (isDebugEnabled()) {
         debug(" +++ isFileDataUpdate : " + this.isFullDataUpdate);
      }

      this.isPlanUpdate = requestInfo.isPlanUpdate();
      if (isDebugEnabled()) {
         debug(" +++ isPlanUpdate : " + this.isPlanUpdate);
      }

      this.isStaging = requestInfo.isStaging();
      this.isPlanStaging = requestInfo.isPlanStaging();
   }

   protected final void doDownload(String handlerType) throws DeploymentException {
      DataTransferRequest request = this.createDataTransferRequest();

      try {
         DataTransferHandler handler = DataHandlerManager.getHandler(handlerType);
         this.setDownloadedStream(handler.getDataAsStream(request));
      } catch (IOException var4) {
         var4.printStackTrace();
         throw new DeploymentException("Exception occured while downloading files", var4);
      }
   }

   protected void doCancel() {
      this.restore();
   }

   protected void doClose(boolean success) {
      if (!success) {
         this.restore();
      }

      this.deleteBackup();
   }

   protected DataTransferRequest createDataTransferRequest() {
      AppData appData = this.getLocalAppData();
      return new AppDataTransferRequestImpl(appData.getAppName(), appData.getAppVersionIdentifier(), this.getRequestId(), this.getRequestedFiles(), appData.getLockPath(), this.isPlanUpdate, appData.getPartition());
   }

   protected void deleteFile(String targetURI) {
   }

   protected File getFileFor(String targetPath) {
      return null;
   }

   protected final boolean isFullUpdate() {
      if (isDebugEnabled()) {
         debug(" +++ isFullUpdate() : " + this.isFullDataUpdate);
      }

      return this.isFullDataUpdate;
   }

   protected void end() {
   }

   protected AppData getLocalAppData() {
      return (AppData)this.getLocalData();
   }

   protected void updateLocalData(DataStream stream) throws IOException {
      String streamName = stream.getName();
      boolean isSpecial = this.isFullUpdate() || this.getLocalAppData().isSystemResource() || streamName.endsWith("wl_app_desc.jar") || streamName.endsWith("wl_app_src.jar");
      if (isDebugEnabled()) {
         debug("updateLocalData(): isSpecial : " + isSpecial);
      }

      if (!isSpecial) {
         super.updateLocalData(stream);
      } else {
         try {
            String destFileName = null;
            if (this.getLocalAppData().isSystemResource()) {
               destFileName = this.getLocalAppData().getRootLocation();
            } else if (streamName.endsWith("wl_app_desc.jar")) {
               destFileName = this.getLocalAppData().getRootLocation();
            } else if (streamName.endsWith("wl_app_src.jar")) {
               destFileName = this.getLocalAppData().getLocation();
            } else {
               destFileName = this.getLocalAppData().getRootLocation() + File.separator + streamName;
            }

            File destFile = new File(destFileName);
            if (isDebugEnabled()) {
               debug(" +++ destFile : " + destFile.getAbsolutePath());
            }

            if (!isValidJ2EEArchive(stream) && stream.isZip()) {
               if (this.getLocalAppData().isPlanStagingEnabled() || this.isPlanStaging) {
                  if (stream instanceof FileDataStream) {
                     this.extract(((FileDataStream)stream).getFile(), destFile);
                  } else {
                     this.extract(stream.getInputStream(), destFile);
                  }
               }
            } else {
               if (isDebugEnabled()) {
                  debug(" +++ isValidJ2EEArchive or stream is not zip.....");
               }

               if (this.getLocalAppData().isAppStagingEnabled() || this.isStaging) {
                  this.copy(stream.getInputStream(), destFile);
               }
            }
         } finally {
            stream.close();
         }

      }
   }

   private static boolean isValidJ2EEArchive(DataStream stream) {
      String streamName = stream.getName();
      if (!streamName.endsWith("wl_app_src.jar") && !streamName.endsWith("wl_app_desc.jar")) {
         return ArchiveUtils.isValidArchiveName(streamName) && stream.isZip();
      } else {
         return false;
      }
   }
}
