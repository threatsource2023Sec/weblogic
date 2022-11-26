package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.SAMLMessageInfoContext;

public class SAMLMessageInfoContextIDFunction implements ContextDataLookupFunction {
   @Nullable
   public String apply(@Nullable SAMLMessageInfoContext input) {
      return input != null ? input.getMessageId() : null;
   }
}
