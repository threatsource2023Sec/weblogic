package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.io.IOException;
import weblogic.deploy.service.DataTransferHandler;
import weblogic.deploy.service.DataTransferRequest;
import weblogic.deploy.service.datatransferhandlers.DataHandlerManager;
import weblogic.management.DeploymentException;

public class ExtendLoaderUpdate extends DataUpdate {
   protected ExtendLoaderUpdate(Data localData, DataUpdateRequestInfo requestInfo) {
      super(localData, requestInfo);
   }

   protected void doDownload(String handlerType) throws DeploymentException {
      DataTransferRequest request = this.createDataTransferRequest();

      try {
         DataTransferHandler handler = DataHandlerManager.getHandler(handlerType);
         this.setDownloadedStream(handler.getDataAsStream(request));
      } catch (IOException var4) {
         var4.printStackTrace();
         throw new DeploymentException("Exception occured while downloading files", var4);
      }
   }

   protected DataTransferRequest createDataTransferRequest() {
      ExtendLoaderData data = (ExtendLoaderData)this.getLocalData();
      return new ExtendLoaderDataTransferRequestImpl(this.getRequestId(), this.getRequestedFiles(), this.getTargetFiles(), data.getLockPath());
   }

   protected File selectDestFile(String streamName) {
      ExtendLoaderData data = (ExtendLoaderData)this.getLocalData();
      return new File(data.getDomainLibDir(), this.selectDestFileName(streamName));
   }

   protected String selectDestFileName(String streamName) {
      return (new File(streamName)).getName();
   }

   public final void releaseLock() {
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

   protected void deleteFile(String targetURI) {
   }

   protected File getFileFor(String targetPath) {
      return null;
   }
}
