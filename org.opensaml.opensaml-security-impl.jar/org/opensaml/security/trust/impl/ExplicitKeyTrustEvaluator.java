package org.opensaml.security.trust.impl;

import java.security.Key;
import java.util.Iterator;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplicitKeyTrustEvaluator {
   private final Logger log = LoggerFactory.getLogger(ExplicitKeyTrustEvaluator.class);

   public boolean validate(@Nonnull Key untrustedKey, @Nonnull Key trustedKey) {
      return untrustedKey.equals(trustedKey);
   }

   public boolean validate(@Nonnull Key untrustedKey, @Nonnull Iterable trustedKeys) {
      Iterator var3 = trustedKeys.iterator();

      Key trustedKey;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         trustedKey = (Key)var3.next();
      } while(!untrustedKey.equals(trustedKey));

      return true;
   }

   public boolean validate(@Nonnull Credential untrustedCredential, @Nonnull Credential trustedCredential) {
      Key untrustedKey = null;
      Key trustedKey = null;
      if (untrustedCredential.getPublicKey() != null) {
         untrustedKey = untrustedCredential.getPublicKey();
         trustedKey = trustedCredential.getPublicKey();
      } else {
         untrustedKey = untrustedCredential.getSecretKey();
         trustedKey = trustedCredential.getSecretKey();
      }

      if (untrustedKey == null) {
         this.log.debug("Untrusted credential contained no key, unable to evaluate");
         return false;
      } else if (trustedKey == null) {
         this.log.debug("Trusted credential contained no key of the appropriate type, unable to evaluate");
         return false;
      } else if (this.validate((Key)untrustedKey, (Key)trustedKey)) {
         this.log.debug("Successfully validated untrusted credential against trusted key");
         return true;
      } else {
         this.log.debug("Failed to validate untrusted credential against trusted key");
         return false;
      }
   }

   public boolean validate(@Nonnull Credential untrustedCredential, @Nonnull Iterable trustedCredentials) {
      Iterator var3 = trustedCredentials.iterator();

      Credential trustedCredential;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         trustedCredential = (Credential)var3.next();
      } while(!this.validate(untrustedCredential, trustedCredential));

      return true;
   }
}
