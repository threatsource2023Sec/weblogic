package weblogic.security.providers.saml.registry;

public interface SAMLCommonPartner extends SAMLPartner {
   String PROFILE_POST = "Browser/POST";
   String PROFILE_ARTIFACT = "Browser/Artifact";
   String PROFILE_SV = "WSS/Sender-Vouches";
   String PROFILE_HOK = "WSS/Holder-of-Key";
   String PROFILE_BEARER = "WSS/Bearer";

   String getProfile();

   void setProfile(String var1);

   String getTargetURL();

   void setTargetURL(String var1);

   String getARSUsername();

   void setARSUsername(String var1);

   String getARSPasswordEncrypted();

   boolean isARSPasswordSet();

   void setARSPassword(String var1);

   String getAudienceURI();

   void setAudienceURI(String var1);

   boolean isSignedAssertions();

   void setSignedAssertions(boolean var1);

   String getNameMapperClass();

   void setNameMapperClass(String var1);

   boolean isGroupsAttributeEnabled();

   void setGroupsAttributeEnabled(boolean var1);
}
