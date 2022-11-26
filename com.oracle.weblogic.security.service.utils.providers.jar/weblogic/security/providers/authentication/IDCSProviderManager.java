package weblogic.security.providers.authentication;

public interface IDCSProviderManager {
   IDCSMetadataHolder getProviderMetadata(String var1);

   IDCSKeyManager getKeyManager(String var1);

   String getUsersEndpointURI(String var1);

   String getUserPasswordEndpointURI(String var1);

   String getAsserterEndpointURI(String var1);

   boolean isMetadataAvailable(String var1);

   boolean isKeyManagerAvailable(String var1);

   void refresh(IDCSConfiguration var1);

   void clearTenantMetadata(String var1);

   void clearTenantKeyManager(String var1);

   void clearTenantData();

   void cancelFlushTimer();

   void cancelEmptyMetadataCleanTimer();

   void handleTenantDataReload(String var1);

   boolean isClientAppRoleAsserterSupported(String var1);

   IDCSBackoffRetryCounter getBackoffRetryCounter();
}
