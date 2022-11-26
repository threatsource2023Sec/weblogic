package weblogic.j2ee.descriptor;

public interface OutboundResourceAdapterBean {
   ConnectionDefinitionBean[] getConnectionDefinitions();

   ConnectionDefinitionBean createConnectionDefinition();

   void destroyConnectionDefinition(ConnectionDefinitionBean var1);

   String getTransactionSupport();

   void setTransactionSupport(String var1);

   AuthenticationMechanismBean[] getAuthenticationMechanisms();

   AuthenticationMechanismBean createAuthenticationMechanism();

   void destroyAuthenticationMechanism(AuthenticationMechanismBean var1);

   boolean isReauthenticationSupport();

   void setReauthenticationSupport(boolean var1);

   String getId();

   void setId(String var1);
}
