package org.opensaml.security.credential.criteria.impl;

import java.security.Key;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.criteria.KeyAlgorithmCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableKeyAlgorithmCredentialCriterion extends AbstractTriStatePredicate implements EvaluableCredentialCriterion {
   private final Logger log = LoggerFactory.getLogger(EvaluableKeyAlgorithmCredentialCriterion.class);
   private final String keyAlgorithm;

   public EvaluableKeyAlgorithmCredentialCriterion(@Nonnull KeyAlgorithmCriterion criteria) {
      this.keyAlgorithm = ((KeyAlgorithmCriterion)Constraint.isNotNull(criteria, "Criterion instance cannot be null")).getKeyAlgorithm();
   }

   public EvaluableKeyAlgorithmCredentialCriterion(@Nonnull String newKeyAlgorithm) {
      String trimmed = StringSupport.trimOrNull(newKeyAlgorithm);
      Constraint.isNotNull(trimmed, "Key algorithm cannot be null or empty");
      this.keyAlgorithm = trimmed;
   }

   @Nullable
   public boolean apply(@Nullable Credential target) {
      if (target == null) {
         this.log.error("Credential target was null");
         return this.isNullInputSatisfies();
      } else {
         Key key = this.getKey(target);
         if (key == null) {
            this.log.info("Could not evaluate criteria, credential contained no key");
            return this.isUnevaluableSatisfies();
         } else {
            String algorithm = StringSupport.trimOrNull(key.getAlgorithm());
            if (algorithm == null) {
               this.log.info("Could not evaluate criteria, key does not specify an algorithm via getAlgorithm()");
               return this.isUnevaluableSatisfies();
            } else {
               return this.keyAlgorithm.equals(algorithm);
            }
         }
      }
   }

   @Nullable
   private Key getKey(@Nonnull Credential credential) {
      if (credential.getPublicKey() != null) {
         return credential.getPublicKey();
      } else if (credential.getSecretKey() != null) {
         return credential.getSecretKey();
      } else {
         return credential.getPrivateKey() != null ? credential.getPrivateKey() : null;
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EvaluableKeyAlgorithmCredentialCriterion [keyAlgorithm=");
      builder.append(this.keyAlgorithm);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.keyAlgorithm.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof EvaluableKeyAlgorithmCredentialCriterion ? this.keyAlgorithm.equals(((EvaluableKeyAlgorithmCredentialCriterion)obj).keyAlgorithm) : false;
      }
   }
}
