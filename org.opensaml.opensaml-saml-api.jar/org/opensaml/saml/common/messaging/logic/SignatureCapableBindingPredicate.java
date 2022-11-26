package org.opensaml.saml.common.messaging.logic;

import com.google.common.base.Predicate;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.binding.SAMLBindingSupport;

public class SignatureCapableBindingPredicate implements Predicate {
   public boolean apply(MessageContext input) {
      return input != null && SAMLBindingSupport.isSigningCapableBinding(input);
   }
}
