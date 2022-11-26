package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.SAMLProtocolContext;

public class SAMLProtocolContextProtocolFunction implements ContextDataLookupFunction {
   @Nullable
   public String apply(@Nullable SAMLProtocolContext input) {
      return input != null ? input.getProtocol() : null;
   }
}
