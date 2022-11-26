package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.BaseContext;

public class SAMLProtocolContext extends BaseContext {
   @Nullable
   @NotEmpty
   private String protocol;

   @Nullable
   @NotEmpty
   public String getProtocol() {
      return this.protocol;
   }

   public void setProtocol(@Nullable String samlProtocol) {
      this.protocol = StringSupport.trimOrNull(samlProtocol);
   }
}
