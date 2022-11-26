package org.opensaml.security.credential.criteria.impl;

import java.security.Key;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.criteria.KeyLengthCriterion;
import org.opensaml.security.crypto.KeySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluableKeyLengthCredentialCriterion extends AbstractTriStatePredicate implements EvaluableCredentialCriterion {
   private final Logger log = LoggerFactory.getLogger(EvaluableKeyLengthCredentialCriterion.class);
   private final Integer keyLength;

   public EvaluableKeyLengthCredentialCriterion(@Nonnull KeyLengthCriterion criteria) {
      this.keyLength = ((KeyLengthCriterion)Constraint.isNotNull(criteria, "Criterion instance cannot be null")).getKeyLength();
   }

   public EvaluableKeyLengthCredentialCriterion(@Nonnull Integer newKeyLength) {
      this.keyLength = (Integer)Constraint.isNotNull(newKeyLength, "Key length cannot be null");
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
            Integer length = KeySupport.getKeyLength(key);
            if (length == null) {
               this.log.info("Could not evaluate criteria, cannot determine length of key");
               return this.isUnevaluableSatisfies();
            } else {
               return this.keyLength.equals(length);
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
      builder.append("EvaluableKeyLengthCredentialCriterion [keyLength=");
      builder.append(this.keyLength);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.keyLength.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof EvaluableKeyLengthCredentialCriterion ? this.keyLength.equals(((EvaluableKeyLengthCredentialCriterion)obj).keyLength) : false;
      }
   }
}
