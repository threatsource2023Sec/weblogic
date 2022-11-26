package com.bea.common.security.saml.utils;

public class SAMLProfile {
   public static final String PROFILE_INVALID = "Invalid Profile";
   public static final String PROFILE_POST = "Browser/POST";
   public static final String PROFILE_ARTIFACT = "Browser/Artifact";
   public static final String PROFILE_HOK = "WSS/Holder-of-Key";
   public static final String PROFILE_SV = "WSS/Sender-Vouches";
   public static final String PROFILE_BEARER = "WSS/Bearer";
   public static final String CONF_INVALID = "invalid";
   public static final String CONF_POST = "bearer";
   public static final String CONF_ARTIFACT = "artifact";
   public static final String CONF_HOK = "holder-of-key";
   public static final String CONF_SV = "sender-vouches";
   public static final String CONF_BEARER = "bearer";
   public static final String CONF_URN_BEARER = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
   public static final String CONF_URN_ARTIFACT = "urn:oasis:names:tc:SAML:1.0:cm:artifact";
   public static final String CONF_URN_HOK = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
   public static final String CONF_URN_SV = "urn:oasis:names:tc:SAML:1.0:cm:sender-vouches";
   public static final int PROFILE_ID_INVALID = 0;
   public static final int PROFILE_ID_POST = 1;
   public static final int PROFILE_ID_ARTIFACT = 2;
   public static final int PROFILE_ID_HOK = 3;
   public static final int PROFILE_ID_SV = 4;
   public static final int PROFILE_ID_BEARER = 5;
   public static final String CONF_SSO_ANY = "sso-any";
   private static final int PROFILE_NAME = 0;
   private static final int PROFILE_CONF_NAME = 1;
   private static final int PROFILE_CONF_URN = 2;
   private static final String[][] PROFILE_INFO = new String[][]{{"Invalid Profile", "invalid", null}, {"Browser/POST", "bearer", "urn:oasis:names:tc:SAML:1.0:cm:bearer"}, {"Browser/Artifact", "artifact", "urn:oasis:names:tc:SAML:1.0:cm:artifact"}, {"WSS/Holder-of-Key", "holder-of-key", "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key"}, {"WSS/Sender-Vouches", "sender-vouches", "urn:oasis:names:tc:SAML:1.0:cm:sender-vouches"}, {"WSS/Bearer", "bearer", "urn:oasis:names:tc:SAML:1.0:cm:bearer"}};
   private int profileId = 0;

   private SAMLProfile() {
   }

   public SAMLProfile(String profile) {
      this.setProfile(profile);
   }

   public SAMLProfile(int id) {
      this.setProfile(id);
   }

   private void setProfile(String profile) {
      this.profileId = lookupProfileValue(profile, 0);
   }

   private void setProfile(int id) {
      this.profileId = id;
      if (this.profileId < 0 || this.profileId >= PROFILE_INFO.length) {
         this.profileId = 0;
      }

   }

   public boolean isValid() {
      return this.profileId != 0;
   }

   public int getProfileId() {
      return this.profileId;
   }

   public String getProfileName() {
      return PROFILE_INFO[this.profileId][0];
   }

   public String getProfileConfMethodName() {
      return PROFILE_INFO[this.profileId][1];
   }

   public String getProfileConfMethodURN() {
      return PROFILE_INFO[this.profileId][2];
   }

   public static boolean isValidProfileName(String profile) {
      return 0 != lookupProfileValue(profile, 0);
   }

   public static boolean isValidConfMethodName(String conf) {
      return 0 != lookupProfileValue(conf, 1);
   }

   public static int mapProfileNameToId(String profile) {
      return lookupProfileValue(profile, 0);
   }

   public static int mapConfMethodNameToId(String conf) {
      return lookupProfileValue(conf, 1);
   }

   public static String mapIdToProfileName(int id) {
      return PROFILE_INFO[id][0];
   }

   public static String mapIdToConfMethodName(int id) {
      return PROFILE_INFO[id][1];
   }

   public static String mapIdToConfMethodURN(int id) {
      return PROFILE_INFO[id][2];
   }

   public static String mapConfMethodNameToProfileName(String conf) {
      int profileId = lookupProfileValue(conf, 1);
      return profileId != 0 ? PROFILE_INFO[profileId][0] : null;
   }

   public static String mapConfMethodURNToConfMethodName(String urn) {
      int profileId = lookupProfileValue(urn, 2);
      return profileId != 0 ? PROFILE_INFO[profileId][1] : null;
   }

   private static int lookupProfileValue(String name, int type) {
      if (name != null && name.length() != 0) {
         for(int i = 1; i < PROFILE_INFO.length; ++i) {
            if (name.equals(PROFILE_INFO[i][type])) {
               return i;
            }
         }

         return 0;
      } else {
         return 0;
      }
   }
}
