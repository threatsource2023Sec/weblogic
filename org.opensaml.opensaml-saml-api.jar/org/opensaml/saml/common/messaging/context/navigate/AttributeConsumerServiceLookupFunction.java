package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.AttributeConsumingServiceContext;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;

public class AttributeConsumerServiceLookupFunction implements ContextDataLookupFunction {
   @Nullable
   public AttributeConsumingService apply(@Nullable AttributeConsumingServiceContext input) {
      return null == input ? null : input.getAttributeConsumingService();
   }
}
