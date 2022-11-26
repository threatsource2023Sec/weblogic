package org.opensaml.xmlsec.signature.support.provider;

import java.security.Key;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.impl.SignatureImpl;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignatureValidationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApacheSantuarioSignatureValidationProviderImpl implements SignatureValidationProvider {
   private final Logger log = LoggerFactory.getLogger(ApacheSantuarioSignatureValidationProviderImpl.class);

   public void validate(@Nonnull Signature signature, @Nonnull Credential validationCredential) throws SignatureException {
      this.log.debug("Attempting to validate signature using key from supplied credential");
      Constraint.isNotNull(validationCredential, "Validation credential cannot be null");
      XMLSignature xmlSig = this.getXMLSignature(signature);
      if (xmlSig == null) {
         this.log.debug("No native XMLSignature object associated with Signature XMLObject");
         throw new SignatureException("Native XMLSignature object not available for validation");
      } else {
         Key validationKey = CredentialSupport.extractVerificationKey(validationCredential);
         if (validationKey == null) {
            this.log.debug("Supplied credential contained no key suitable for signature validation");
            throw new SignatureException("No key available to validate signature");
         } else {
            this.log.debug("Validating signature with signature algorithm URI: {}", signature.getSignatureAlgorithm());
            this.log.debug("Validation credential key algorithm '{}', key instance class '{}'", validationKey.getAlgorithm(), validationKey.getClass().getName());

            try {
               if (xmlSig.checkSignatureValue(validationKey)) {
                  this.log.debug("Signature validated with key from supplied credential");
                  return;
               }
            } catch (XMLSignatureException var6) {
               throw new SignatureException("Unable to evaluate key against signature", var6);
            }

            this.log.debug("Signature cryptographic validation not successful");
            throw new SignatureException("Signature cryptographic validation not successful");
         }
      }
   }

   @Nullable
   protected XMLSignature getXMLSignature(@Nonnull Signature signature) {
      Constraint.isNotNull(signature, "Signature cannot be null");
      this.log.debug("Accessing XMLSignature object");
      return ((SignatureImpl)signature).getXMLSignature();
   }
}
