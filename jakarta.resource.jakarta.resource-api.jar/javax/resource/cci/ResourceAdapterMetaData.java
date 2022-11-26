package javax.resource.cci;

public interface ResourceAdapterMetaData {
   String getAdapterVersion();

   String getAdapterVendorName();

   String getAdapterName();

   String getAdapterShortDescription();

   String getSpecVersion();

   String[] getInteractionSpecsSupported();

   boolean supportsExecuteWithInputAndOutputRecord();

   boolean supportsExecuteWithInputRecordOnly();

   boolean supportsLocalTransactionDemarcation();
}
