package org.opensaml.saml.common.profile.logic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultNameIDPolicyPredicate extends AbstractNameIDPolicyPredicate {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(DefaultNameIDPolicyPredicate.class);

   protected boolean doApply(@Nullable String requesterId, @Nullable String responderId, @Nullable String format, @Nullable String nameQualifier, @Nullable String spNameQualifier) {
      if (spNameQualifier == null || requesterId != null && spNameQualifier.equals(requesterId)) {
         if (nameQualifier == null || responderId != null && nameQualifier.equals(responderId)) {
            return true;
         } else {
            this.log.debug("Requested NameQualifier {} did not match responder ID", nameQualifier);
            return false;
         }
      } else {
         this.log.debug("Requested SPNameQualifier {} did not match requester ID", spNameQualifier);
         return false;
      }
   }
}
