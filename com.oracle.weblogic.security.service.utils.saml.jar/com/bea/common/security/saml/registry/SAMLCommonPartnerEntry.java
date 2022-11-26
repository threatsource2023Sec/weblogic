package com.bea.common.security.saml.registry;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml.utils.SAMLProfile;
import com.bea.common.security.saml.utils.SAMLUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.management.utils.InvalidParameterException;
import weblogic.security.providers.saml.registry.SAMLCommonPartner;

public abstract class SAMLCommonPartnerEntry extends SAMLPartnerEntry implements SAMLCommonPartner {
   private static final long serialVersionUID = -7621988038720443842L;
   private static final String SERIALIZED_AUTH_PASSWORD = "SerAuthPass";
   private static final String SERIALIZED_PASSWORD_SET = "SerPassSet";
   private static final String SERIALIZED_PASSWORD_ENCRYPTED = "SerPassEncrypt";
   private static final String[] COMMON_OBJECT_CLASSES = new String[0];
   private static final String COMMON_ATTR_PROFILE = "beaSAMLProfile";
   private static final String COMMON_ATTR_TARGET_URL = "beaSAMLTargetURL";
   private static final String COMMON_ATTR_AUTH_USERNAME = "beaSAMLAuthUsername";
   private static final String COMMON_ATTR_AUTH_PASSWORD = "beaSAMLAuthPassword";
   private static final String COMMON_ATTR_AUDIENCE_URI = "beaSAMLAudienceURI";
   private static final String COMMON_ATTR_SIGNED_ASSERTIONS = "beaSAMLSignedAssertions";
   private static final String COMMON_ATTR_NAME_MAPPER = "beaSAMLNameMapperClass";
   private static final String COMMON_ATTR_GROUPS_ENABLED = "beaSAMLGroupsAttributeEnabled";
   private static final String[] COMMON_ATTRIBUTES = new String[]{"beaSAMLProfile", "beaSAMLTargetURL", "beaSAMLAuthUsername", "beaSAMLAuthPassword", "beaSAMLAudienceURI", "beaSAMLSignedAssertions", "beaSAMLNameMapperClass", "beaSAMLGroupsAttributeEnabled"};
   private static final Pattern paramsPattern = Pattern.compile("^[\\S&&[^=]]+=[\\S&&[^=]]+$");
   private transient boolean isARSPasswordSet = false;
   private transient boolean isARSPasswordEncrypted = false;
   private SAMLProfile profile = null;
   private boolean isWildcardTarget = false;
   private boolean isDefaultTarget = false;

   protected SAMLCommonPartnerEntry(LoggerSpi logger, LegacyEncryptorSpi encryptionService) {
      super(logger, encryptionService);
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      if (this.containsAttribute("SerPassSet")) {
         this.isARSPasswordSet = this.getBooleanAttribute("SerPassSet");
      }

      if (this.containsAttribute("SerPassEncrypt")) {
         this.isARSPasswordEncrypted = this.getBooleanAttribute("SerPassEncrypt");
      }

      if (!this.containsAttribute("SerAuthPass") && this.containsAttribute("beaSAMLAuthPassword") || this.containsAttribute("SerAuthPass") && this.containsAttribute("beaSAMLAuthPassword") && !this.isPasswordEqual(this.getAttribute("SerAuthPass"), this.getAttribute("beaSAMLAuthPassword"))) {
         String value = this.getAttribute("beaSAMLAuthPassword");
         if (value != null && value.length() != 0) {
            if (!this.isValueEncrypted(value)) {
               this.isARSPasswordSet = true;
               this.isARSPasswordEncrypted = false;
            } else {
               this.isARSPasswordSet = true;
               this.isARSPasswordEncrypted = true;
            }
         } else {
            value = null;
            this.isARSPasswordSet = false;
            this.isARSPasswordEncrypted = false;
            this.setAttribute("beaSAMLAuthPassword", (String)null);
         }

         this.setAttribute("SerAuthPass", value);
         this.setBooleanAttribute("SerPassSet", this.isARSPasswordSet);
         this.setBooleanAttribute("SerPassEncrypt", this.isARSPasswordEncrypted);
      }

   }

   private boolean isPasswordEqual(String pass, String other) {
      if (pass == null && other == null) {
         return true;
      } else {
         return (pass == null || other != null) && (pass != null || other == null) ? pass.equals(other) : false;
      }
   }

   protected static String[] getCommonLDAPObjectClasses() {
      return SAMLUtil.mergeArrays(getBaseLDAPObjectClasses(), COMMON_OBJECT_CLASSES);
   }

   protected static String[] getCommonLDAPAttributes() {
      return SAMLUtil.mergeArrays(getBaseLDAPAttributes(), COMMON_ATTRIBUTES);
   }

   public String getProfile() {
      return this.getAttribute("beaSAMLProfile");
   }

   public void setProfile(String profile) {
      this.setAttribute("beaSAMLProfile", profile);
      if (SAMLProfile.mapProfileNameToId(profile) == 3) {
         this.setBooleanAttribute("beaSAMLSignedAssertions", true);
      }

   }

   public String getTargetURL() {
      return this.getAttribute("beaSAMLTargetURL");
   }

   public void setTargetURL(String targetURL) {
      this.setAttribute("beaSAMLTargetURL", targetURL);
   }

   public String getARSUsername() {
      return this.getAttribute("beaSAMLAuthUsername");
   }

   public void setARSUsername(String arsUsername) {
      this.setAttribute("beaSAMLAuthUsername", arsUsername);
   }

   public String getARSPasswordEncrypted() {
      return this.isARSPasswordSet && this.isARSPasswordEncrypted ? this.getAttribute("beaSAMLAuthPassword") : null;
   }

   public boolean isARSPasswordSet() {
      return this.isARSPasswordSet;
   }

   public void setARSPassword(String arsPassword) {
      if (arsPassword != null && arsPassword.length() == 0) {
         arsPassword = null;
         this.isARSPasswordSet = false;
      } else {
         this.isARSPasswordSet = true;
      }

      this.isARSPasswordEncrypted = false;
      this.setAttribute("beaSAMLAuthPassword", arsPassword);
      this.setAttribute("SerAuthPass", arsPassword);
      this.setBooleanAttribute("SerPassSet", this.isARSPasswordSet);
      this.setBooleanAttribute("SerPassEncrypt", this.isARSPasswordEncrypted);
   }

   public String getAudienceURI() {
      return this.getAttribute("beaSAMLAudienceURI");
   }

   public void setAudienceURI(String audienceURI) {
      this.setAttribute("beaSAMLAudienceURI", audienceURI);
   }

   public boolean isSignedAssertions() {
      return this.getBooleanAttribute("beaSAMLSignedAssertions");
   }

   public void setSignedAssertions(boolean signedAssertions) {
      this.setBooleanAttribute("beaSAMLSignedAssertions", signedAssertions);
   }

   public String getNameMapperClass() {
      return this.getAttribute("beaSAMLNameMapperClass");
   }

   public void setNameMapperClass(String nameMapperClass) {
      this.setAttribute("beaSAMLNameMapperClass", nameMapperClass);
   }

   public boolean isGroupsAttributeEnabled() {
      return this.getBooleanAttribute("beaSAMLGroupsAttributeEnabled");
   }

   public void setGroupsAttributeEnabled(boolean groupsAttributeEnabled) {
      this.setBooleanAttribute("beaSAMLGroupsAttributeEnabled", groupsAttributeEnabled);
   }

   protected int getProfileId() {
      return this.profile.getProfileId();
   }

   protected String getProfileConfMethodName() {
      return this.profile.getProfileConfMethodName();
   }

   protected String getProfileConfMethodURN() {
      return this.profile.getProfileConfMethodURN();
   }

   protected boolean isWildcardTarget() {
      return this.isWildcardTarget;
   }

   protected boolean isDefaultTarget() {
      return this.isDefaultTarget;
   }

   protected String getARSPassword() {
      return this.getEncryptedAttribute("beaSAMLAuthPassword");
   }

   protected String[] getAudienceURIs() {
      return this.getMultiValuedAttribute("beaSAMLAudienceURI");
   }

   public void validate() throws InvalidParameterException {
      super.validate();
      if (this.isEnabled()) {
         String profileName = this.getProfile();
         if (profileName == null) {
            throw new InvalidParameterException("No profile set");
         } else {
            this.profile = new SAMLProfile(profileName);
            if (!this.profile.isValid()) {
               throw new InvalidParameterException("Invalid Profile: " + profileName);
            } else {
               if (this.getTargetURL() == null) {
                  int profileId = this.getProfileId();
                  if (profileId == 3 || profileId == 4 || profileId == 5) {
                     throw new InvalidParameterException("Missing Target Endpoint");
                  }
               }

            }
         }
      }
   }

   protected void handleEncryption(boolean isInbound) {
      super.handleEncryption(isInbound);
      if (isInbound) {
         String value = this.getAttribute("beaSAMLAuthPassword");
         if (value == null || value.length() == 0) {
            this.isARSPasswordSet = false;
            this.isARSPasswordEncrypted = false;
            this.setAttribute("beaSAMLAuthPassword", (String)null);
            this.setAttribute("SerAuthPass", (String)null);
            this.setBooleanAttribute("SerPassSet", this.isARSPasswordSet);
            this.setBooleanAttribute("SerPassEncrypt", this.isARSPasswordEncrypted);
            return;
         }

         if (!this.isValueEncrypted(value)) {
            this.isARSPasswordSet = true;
            this.isARSPasswordEncrypted = false;
            this.setAttribute("SerAuthPass", value);
            this.setBooleanAttribute("SerPassSet", this.isARSPasswordSet);
            this.setBooleanAttribute("SerPassEncrypt", this.isARSPasswordEncrypted);
            return;
         }

         String decryptedValue = this.decrypt(value);
         if (decryptedValue != null && decryptedValue.length() != 0) {
            this.isARSPasswordSet = true;
            this.isARSPasswordEncrypted = true;
            this.setAttribute("SerAuthPass", value);
            this.setBooleanAttribute("SerPassSet", this.isARSPasswordSet);
            this.setBooleanAttribute("SerPassEncrypt", this.isARSPasswordEncrypted);
         } else {
            this.isARSPasswordSet = false;
            this.isARSPasswordEncrypted = false;
            this.setAttribute("beaSAMLAuthPassword", (String)null);
            this.setAttribute("SerAuthPass", (String)null);
            this.setBooleanAttribute("SerPassSet", this.isARSPasswordSet);
            this.setBooleanAttribute("SerPassEncrypt", this.isARSPasswordEncrypted);
         }
      } else if (this.isARSPasswordSet && !this.isARSPasswordEncrypted) {
         this.setEncryptedAttribute("beaSAMLAuthPassword", this.getAttribute("beaSAMLAuthPassword"));
         this.isARSPasswordEncrypted = true;
         this.setAttribute("SerAuthPass", this.getAttribute("beaSAMLAuthPassword"));
         this.setBooleanAttribute("SerPassEncrypt", this.isARSPasswordEncrypted);
      }

   }

   public void construct() throws InvalidParameterException {
      super.construct();
      if (this.isEnabled()) {
         String target = this.getTargetURL();
         if (target != null) {
            if (target.equals("default")) {
               this.isDefaultTarget = true;
            } else if (target.endsWith("*")) {
               this.setTargetURL(target.substring(0, target.length() - 1));
               this.isWildcardTarget = true;
            }
         }

      }
   }

   protected boolean isValidURL(String url) {
      if (url != null) {
         try {
            new URL(url);
            return true;
         } catch (MalformedURLException var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean isValidURI(String uri) {
      if (uri != null) {
         try {
            new URI(uri);
            return true;
         } catch (URISyntaxException var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean isValidContextPath(String path) {
      return this.isValidURI(path) && path.startsWith("/");
   }

   protected boolean isValidCertAlias(String alias) {
      return alias != null && alias.length() > 0;
   }

   protected boolean isValidParameters(String[] params) {
      for(int i = 0; params != null && i < params.length; ++i) {
         if (params[i] != null) {
            Matcher m = paramsPattern.matcher(params[i]);
            if (!m.matches()) {
               return false;
            }
         }
      }

      return true;
   }
}
