package org.opensaml.xmlsec.signature.support;

import java.util.Iterator;
import java.util.ServiceLoader;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SignatureValidator {
   private static SignatureValidationProvider validatorInstance;

   protected SignatureValidator() {
   }

   public static void validate(@Nonnull Signature signature, @Nonnull Credential validationCredential) throws SignatureException {
      SignatureValidationProvider validator = getSignatureValidationProvider();
      getLogger().debug("Using a validation provider of implementation: {}", validator.getClass().getName());
      validator.validate(signature, validationCredential);
   }

   @Nonnull
   private static SignatureValidationProvider getSignatureValidationProvider() throws SignatureException {
      if (validatorInstance == null) {
         ServiceLoader loader = ServiceLoader.load(SignatureValidationProvider.class);
         Iterator iterator = loader.iterator();
         if (!iterator.hasNext()) {
            throw new SignatureException("Could not load a signature validation provider implementation via service API");
         }

         validatorInstance = (SignatureValidationProvider)iterator.next();
      }

      return validatorInstance;
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(SignatureValidationProvider.class);
   }
}
