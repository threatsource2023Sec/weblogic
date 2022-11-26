package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;

public class AttributeConsumingServiceContext extends BaseContext {
   @Nullable
   private AttributeConsumingService attributeConsumingService;

   @Nullable
   public AttributeConsumingService getAttributeConsumingService() {
      return this.attributeConsumingService;
   }

   @Nonnull
   public void setAttributeConsumingService(@Nullable AttributeConsumingService acs) {
      this.attributeConsumingService = acs;
   }
}
