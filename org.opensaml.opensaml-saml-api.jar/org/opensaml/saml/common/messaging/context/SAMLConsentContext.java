package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.BaseContext;

public class SAMLConsentContext extends BaseContext {
   @Nullable
   @NotEmpty
   private String value;

   @Nullable
   @NotEmpty
   public String getConsent() {
      return this.value;
   }

   public void setConsent(@Nullable String consent) {
      this.value = StringSupport.trimOrNull(consent);
   }
}
