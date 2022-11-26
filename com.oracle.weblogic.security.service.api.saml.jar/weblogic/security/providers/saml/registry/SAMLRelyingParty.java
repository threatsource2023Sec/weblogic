package weblogic.security.providers.saml.registry;

public interface SAMLRelyingParty extends SAMLCommonPartner {
   String getAssertionConsumerURL();

   void setAssertionConsumerURL(String var1);

   String[] getAssertionConsumerParams();

   void setAssertionConsumerParams(String[] var1);

   String getPostForm();

   void setPostForm(String var1);

   String getSSLClientCertAlias();

   void setSSLClientCertAlias(String var1);

   int getTimeToLive();

   void setTimeToLive(int var1);

   int getTimeToLiveOffset();

   void setTimeToLiveOffset(int var1);

   boolean isDoNotCacheCondition();

   void setDoNotCacheCondition(boolean var1);

   boolean isKeyinfoIncluded();

   void setKeyinfoIncluded(boolean var1);

   boolean isCredentialCacheEnabled();

   void setCredentialCacheEnabled(boolean var1);
}
