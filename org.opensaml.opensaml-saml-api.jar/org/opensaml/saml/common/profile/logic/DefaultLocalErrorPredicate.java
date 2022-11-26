package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.context.EventContext;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.CurrentOrPreviousEventLookup;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.common.messaging.context.SAMLEndpointContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLocalErrorPredicate implements Predicate {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(DefaultLocalErrorPredicate.class);
   @Nonnull
   private Function bindingContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLBindingContext.class), new OutboundMessageContextLookup());
   @Nonnull
   private Function endpointContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLEndpointContext.class), Functions.compose(new ChildContextLookup(SAMLPeerEntityContext.class), new OutboundMessageContextLookup()));
   @Nonnull
   private Function eventContextLookupStrategy = new CurrentOrPreviousEventLookup();
   @Nonnull
   @NonnullElements
   private Set localEvents = Collections.emptySet();

   public void setBindingContextLookupStrategy(@Nonnull Function strategy) {
      this.bindingContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "SAMLBindingContext lookup strategy cannot be null");
   }

   public void setEndpointContextLookupStrategy(@Nonnull Function strategy) {
      this.endpointContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "SAMLEndpointContext lookup strategy cannot be null");
   }

   public void setEventContextLookupStrategy(@Nonnull Function strategy) {
      this.eventContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "EventContext lookup strategy cannot be null");
   }

   public void setLocalEvents(@Nonnull @NonnullElements Collection events) {
      if (events == null) {
         this.localEvents = Collections.emptySet();
      } else {
         this.localEvents = new HashSet(events.size());
         Iterator var2 = events.iterator();

         while(var2.hasNext()) {
            String e = (String)var2.next();
            String trimmed = StringSupport.trimOrNull(e);
            if (trimmed != null) {
               this.localEvents.add(trimmed);
            }
         }
      }

   }

   public boolean apply(@Nullable ProfileRequestContext input) {
      if (input == null) {
         return true;
      } else {
         SAMLBindingContext bindingCtx = (SAMLBindingContext)this.bindingContextLookupStrategy.apply(input);
         if (bindingCtx != null && bindingCtx.getBindingUri() != null) {
            SAMLEndpointContext endpointCtx = (SAMLEndpointContext)this.endpointContextLookupStrategy.apply(input);
            if (endpointCtx != null && endpointCtx.getEndpoint() != null && (endpointCtx.getEndpoint().getLocation() != null || endpointCtx.getEndpoint().getResponseLocation() != null)) {
               AuthnRequest authnRequest = (AuthnRequest)(new MessageLookup(AuthnRequest.class)).apply((new InboundMessageContextLookup()).apply(input));
               if (authnRequest != null && authnRequest.isPassive()) {
                  this.log.debug("Request was a SAML 2 AuthnRequest with IsPassive set, handling error with response");
                  return false;
               } else {
                  EventContext eventCtx = (EventContext)this.eventContextLookupStrategy.apply(input);
                  if (eventCtx != null && eventCtx.getEvent() != null) {
                     String event = eventCtx.getEvent().toString();
                     if (this.localEvents.contains(event)) {
                        this.log.debug("Error event {} will be handled locally", event);
                        return true;
                     } else {
                        this.log.debug("Error event {} will be handled with response", event);
                        return false;
                     }
                  } else {
                     this.log.debug("No event found, assuming error handled with response");
                     return false;
                  }
               }
            } else {
               this.log.debug("No SAMLEndpointContext or endpoint location available, error must be handled locally");
               return true;
            }
         } else {
            this.log.debug("No SAMLBindingContext or binding URI available, error must be handled locally");
            return true;
         }
      }
   }
}
