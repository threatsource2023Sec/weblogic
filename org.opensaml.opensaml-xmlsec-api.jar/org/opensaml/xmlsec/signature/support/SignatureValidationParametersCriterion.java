package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.xmlsec.SignatureValidationParameters;

public class SignatureValidationParametersCriterion implements Criterion {
   private SignatureValidationParameters params;

   public SignatureValidationParametersCriterion(@Nonnull SignatureValidationParameters validationParams) {
      this.params = (SignatureValidationParameters)Constraint.isNotNull(validationParams, "SignatureValidationParameters instance was null");
   }

   @Nonnull
   public SignatureValidationParameters getSignatureValidationParameters() {
      return this.params;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("SignatureValidationParametersCriterion [params=");
      builder.append(this.params);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.params.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof SignatureValidationParametersCriterion ? this.params.equals(((SignatureValidationParametersCriterion)obj).getSignatureValidationParameters()) : false;
      }
   }
}
