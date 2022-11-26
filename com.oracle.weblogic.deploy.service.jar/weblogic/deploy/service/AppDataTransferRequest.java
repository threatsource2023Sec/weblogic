package weblogic.deploy.service;

public interface AppDataTransferRequest extends DataTransferRequest {
   String getAppName();

   String getAppVersionIdentifier();

   boolean isPlanUpdate();

   String getPartition();
}
