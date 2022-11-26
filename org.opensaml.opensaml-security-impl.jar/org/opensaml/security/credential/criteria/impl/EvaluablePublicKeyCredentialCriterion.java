package org.opensaml.security.credential.criteria.impl;

import java.security.PublicKey;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.AbstractTriStatePredicate;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.criteria.PublicKeyCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvaluablePublicKeyCredentialCriterion extends AbstractTriStatePredicate implements EvaluableCredentialCriterion {
   private final Logger log = LoggerFactory.getLogger(EvaluablePublicKeyCredentialCriterion.class);
   private final PublicKey publicKey;

   public EvaluablePublicKeyCredentialCriterion(@Nonnull PublicKeyCriterion criteria) {
      this.publicKey = ((PublicKeyCriterion)Constraint.isNotNull(criteria, "Criterion instance cannot be null")).getPublicKey();
   }

   public EvaluablePublicKeyCredentialCriterion(@Nonnull PublicKey newPublicKey) {
      this.publicKey = (PublicKey)Constraint.isNotNull(newPublicKey, "Public key cannot be null");
   }

   @Nullable
   public boolean apply(@Nullable Credential target) {
      if (target == null) {
         this.log.error("Credential target was null");
         return this.isNullInputSatisfies();
      } else {
         PublicKey key = target.getPublicKey();
         if (key == null) {
            this.log.info("Credential contained no public key, does not satisfy public key criteria");
            return false;
         } else {
            return this.publicKey.equals(key);
         }
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EvaluablePublicKeyCredentialCriterion [publicKey=");
      builder.append(this.publicKey);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.publicKey.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof EvaluablePublicKeyCredentialCriterion ? this.publicKey.equals(((EvaluablePublicKeyCredentialCriterion)obj).publicKey) : false;
      }
   }
}
