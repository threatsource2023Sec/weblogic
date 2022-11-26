package weblogic.security.providers.saml.registry;

public interface SAMLAssertingParty extends SAMLCommonPartner {
   String getIssuerURI();

   void setIssuerURI(String var1);

   String getSourceId();

   void setSourceId(String var1);

   String getAssertionRetrievalURL();

   void setAssertionRetrievalURL(String var1);

   String getIntersiteTransferURL();

   void setIntersiteTransferURL(String var1);

   String[] getIntersiteTransferParams();

   void setIntersiteTransferParams(String[] var1);

   String[] getRedirectURIs();

   void setRedirectURIs(String[] var1);

   String getAssertionSigningCertAlias();

   void setAssertionSigningCertAlias(String var1);

   String getProtocolSigningCertAlias();

   void setProtocolSigningCertAlias(String var1);

   boolean isVirtualUserEnabled();

   void setVirtualUserEnabled(boolean var1);
}
