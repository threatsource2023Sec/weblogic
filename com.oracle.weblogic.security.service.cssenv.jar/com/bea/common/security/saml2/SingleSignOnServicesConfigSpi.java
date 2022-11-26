package com.bea.common.security.saml2;

public interface SingleSignOnServicesConfigSpi {
   String getContactPersonGivenName();

   String getContactPersonSurName();

   String getContactPersonType();

   String getContactPersonCompany();

   String getContactPersonTelephoneNumber();

   String getContactPersonEmailAddress();

   String getOrganizationName();

   String getOrganizationURL();

   String getPublishedSiteURL();

   String getEntityID();

   String getErrorPath();

   boolean isServiceProviderEnabled();

   String getDefaultURL();

   boolean isServiceProviderArtifactBindingEnabled();

   boolean isServiceProviderPOSTBindingEnabled();

   String getServiceProviderPreferredBinding();

   boolean isSignAuthnRequests();

   boolean isWantAssertionsSigned();

   String getSSOSigningKeyAlias();

   String getSSOSigningKeyPassPhrase();

   byte[] getSSOSigningKeyPassPhraseEncrypted();

   boolean isForceAuthn();

   boolean isPassive();

   boolean isIdentityProviderEnabled();

   boolean isIdentityProviderArtifactBindingEnabled();

   boolean isIdentityProviderPOSTBindingEnabled();

   boolean isIdentityProviderRedirectBindingEnabled();

   String getIdentityProviderPreferredBinding();

   boolean isWantAuthnRequestsSigned();

   String getLoginURL();

   String getLoginReturnQueryParameter();

   boolean isRecipientCheckEnabled();

   boolean isPOSTOneUseCheckEnabled();

   String getTransportLayerSecurityKeyAlias();

   String getTransportLayerSecurityKeyPassPhrase();

   byte[] getTransportLayerSecurityKeyPassPhraseEncrypted();

   String getBasicAuthUsername();

   String getBasicAuthPassword();

   byte[] getBasicAuthPasswordEncrypted();

   boolean isWantArtifactRequestsSigned();

   boolean isWantTransportLayerSecurityClientAuthentication();

   boolean isWantBasicAuthClientAuthentication();

   int getAuthnRequestMaxCacheSize();

   int getAuthnRequestTimeout();

   int getArtifactMaxCacheSize();

   int getArtifactTimeout();

   boolean isReplicatedCacheEnabled();

   boolean isAssertionEncryptionEnabled();

   String getDataEncryptionAlgorithm();

   String getKeyEncryptionAlgorithm();

   String[] getMetadataEncryptionAlgorithms();

   String getAssertionEncryptionDecryptionKeyAlias();

   String getAssertionEncryptionDecryptionKeyPassPhrase();

   byte[] getAssertionEncryptionDecryptionKeyPassPhraseEncrypted();

   String[] getAllowedTargetHosts();
}
