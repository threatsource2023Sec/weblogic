package weblogic.deploy.internal.targetserver.datamanagement;

import weblogic.deploy.service.DataTransferRequest;

public class ModuleRedeployDataUpdate extends AppDataUpdate {
   public ModuleRedeployDataUpdate(Data localData, DataUpdateRequestInfo reqInfo) {
      super(localData, reqInfo);
   }

   protected DataTransferRequest createDataTransferRequest() {
      AppData appData = this.getLocalAppData();
      return new ModuleRedeployDataTransferRequestImpl(appData.getAppName(), appData.getAppVersionIdentifier(), this.getRequestId(), this.getRequestedFiles(), appData.getLockPath(), false, appData.getPartition());
   }
}
