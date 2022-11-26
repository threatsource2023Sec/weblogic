package org.opensaml.saml.common.messaging.context.navigate;

import javax.annotation.Nullable;
import org.joda.time.DateTime;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.saml.common.messaging.context.SAMLMessageInfoContext;

public class SAMLMessageInfoContextIssueInstantFunction implements ContextDataLookupFunction {
   @Nullable
   public DateTime apply(@Nullable SAMLMessageInfoContext input) {
      return input != null ? input.getMessageIssueInstant() : null;
   }
}
