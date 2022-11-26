package weblogic.deploy.service;

public interface DataTransferHandlerManager {
   void registerDataTransferHandler(DataTransferHandler var1) throws DataTransferHandlerExistsException;

   DataTransferHandler getDataTransferHandler(String var1);

   String[] getRegisteredDataTransferHandlerTypes();
}
