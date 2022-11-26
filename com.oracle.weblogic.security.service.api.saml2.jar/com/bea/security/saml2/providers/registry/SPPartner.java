package com.bea.security.saml2.providers.registry;

public interface SPPartner extends Partner {
   String getServiceProviderNameMapperClassname();

   void setServiceProviderNameMapperClassname(String var1);

   int getTimeToLive();

   void setTimeToLive(int var1);

   int getTimeToLiveOffset();

   void setTimeToLiveOffset(int var1);

   boolean isIncludeOneTimeUseCondition();

   void setIncludeOneTimeUseCondition(boolean var1);

   boolean isGenerateAttributes();

   void setGenerateAttributes(boolean var1);

   boolean isKeyinfoIncluded();

   void setKeyinfoIncluded(boolean var1);

   boolean isWantAssertionsSigned();

   void setWantAssertionsSigned(boolean var1);
}
