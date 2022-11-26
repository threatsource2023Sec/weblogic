package org.opensaml.saml.saml2.encryption;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.EncryptedAttribute;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Decrypter extends org.opensaml.xmlsec.encryption.support.Decrypter {
   private final Logger log = LoggerFactory.getLogger(Decrypter.class);

   public Decrypter(DecryptionParameters params) {
      super(params);
   }

   public Decrypter(@Nullable KeyInfoCredentialResolver newResolver, @Nullable KeyInfoCredentialResolver newKEKResolver, @Nullable EncryptedKeyResolver newEncKeyResolver) {
      super(newResolver, newKEKResolver, newEncKeyResolver, (Collection)null, (Collection)null);
   }

   public Decrypter(@Nullable KeyInfoCredentialResolver newResolver, @Nullable KeyInfoCredentialResolver newKEKResolver, @Nullable EncryptedKeyResolver newEncKeyResolver, @Nullable Collection whitelistAlgos, @Nullable Collection blacklistAlgos) {
      super(newResolver, newKEKResolver, newEncKeyResolver, whitelistAlgos, blacklistAlgos);
   }

   public Assertion decrypt(@Nonnull EncryptedAssertion encryptedAssertion) throws DecryptionException {
      SAMLObject samlObject = this.decryptData(encryptedAssertion);
      if (!(samlObject instanceof Assertion)) {
         throw new DecryptionException("Decrypted SAMLObject was not an instance of Assertion");
      } else {
         return (Assertion)samlObject;
      }
   }

   public Attribute decrypt(@Nonnull EncryptedAttribute encryptedAttribute) throws DecryptionException {
      SAMLObject samlObject = this.decryptData(encryptedAttribute);
      if (!(samlObject instanceof Attribute)) {
         throw new DecryptionException("Decrypted SAMLObject was not an instance of Attribute");
      } else {
         return (Attribute)samlObject;
      }
   }

   public SAMLObject decrypt(@Nonnull EncryptedID encryptedID) throws DecryptionException {
      return this.decryptData(encryptedID);
   }

   public NewID decrypt(@Nonnull NewEncryptedID newEncryptedID) throws DecryptionException {
      SAMLObject samlObject = this.decryptData(newEncryptedID);
      if (!(samlObject instanceof NewID)) {
         throw new DecryptionException("Decrypted SAMLObject was not an instance of NewID");
      } else {
         return (NewID)samlObject;
      }
   }

   private SAMLObject decryptData(@Nonnull EncryptedElementType encElement) throws DecryptionException {
      if (encElement.getEncryptedData() == null) {
         throw new DecryptionException("Element had no EncryptedData child");
      } else {
         XMLObject xmlObject = null;

         try {
            xmlObject = this.decryptData(encElement.getEncryptedData(), this.isRootInNewDocument());
         } catch (DecryptionException var4) {
            this.log.error("SAML Decrypter encountered an error decrypting element content", var4);
            throw var4;
         }

         if (!(xmlObject instanceof SAMLObject)) {
            throw new DecryptionException("Decrypted XMLObject was not an instance of SAMLObject");
         } else {
            return (SAMLObject)xmlObject;
         }
      }
   }
}
