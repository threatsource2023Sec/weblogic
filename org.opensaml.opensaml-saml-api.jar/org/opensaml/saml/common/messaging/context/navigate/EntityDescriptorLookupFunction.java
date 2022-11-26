package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class EntityDescriptorLookupFunction implements ContextDataLookupFunction {
   @Nullable
   public EntityDescriptor apply(@Nullable SAMLMetadataContext input) {
      return null == input ? null : input.getEntityDescriptor();
   }
}
