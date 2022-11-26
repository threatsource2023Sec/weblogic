package weblogic.jms.bridge;

public interface AdapterMetaData {
   String INTERACTION_SYNC_SEND = "SYNC_SEND";
   String INTERACTION_SYNC_RECEIVE = "SYNC_RECEIVE";
   String INTERACTION_SYNC_SEND_RECEIVE = "SYNC_SEND_RECEIVE";
   String INTERACTION_ASYNC_SEND = "SYNC_SEND";
   String INTERACTION_ASYNC_RECEIVE = "SYNC_RECEIVE";
   String INTERACTION_ASYNC_SEND_RECEIVE = "SYNC_SEND_RECEIVE";

   String getAdapterName();

   String getAdapterShortDescription();

   String getAdapterVendorName();

   String getAdapterVersion();

   String getSpecVersion();

   String getNativeMessageFormat();

   String[] getMessageFormatsUnderstands();

   boolean supportsLocalTransactionDemarcation();

   boolean supportsXAResource();

   boolean supportsAcknowledgement();

   boolean supportsAsynchronousMode();

   String[] getInteractionModeSupport();

   boolean supportsDurability();

   boolean supportsMDBTransaction();

   boolean understands(String var1);
}
