package weblogic.security.providers.authentication;

public interface IDCSMetadataHolder {
   long getTimestamp();

   long getTimeToExpire();

   String getIssuer();

   String getUsersEndpointURI();

   String getUserPasswordEndpointURI();

   String getAsserterEndpointURI();

   boolean isClientAppRoleAsserterSupported();
}
