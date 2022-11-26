package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.common.messaging.context.SAMLMessageInfoContext;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddInResponseToToResponse extends AbstractConditionalProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddInResponseToToResponse.class);
   @Nonnull
   private Function responseLookupStrategy = Functions.compose(new MessageLookup(SAMLObject.class), new OutboundMessageContextLookup());
   @Nonnull
   private Function requestIdLookupStrategy = new DefaultRequestIdLookupStrategy();
   @Nullable
   private SAMLObject response;
   @Nullable
   private String requestId;

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   public void setRequestIdLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requestIdLookupStrategy = (Function)Constraint.isNotNull(strategy, "Request ID lookup strategy cannot be null");
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.log.debug("{} Attempting to add InResponseTo to outgoing Response", this.getLogPrefix());
      this.response = (SAMLObject)this.responseLookupStrategy.apply(profileRequestContext);
      if (this.response == null) {
         this.log.debug("{} No SAML message located in current profile request context", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else {
         this.requestId = (String)this.requestIdLookupStrategy.apply(profileRequestContext);
         if (this.requestId == null) {
            this.log.debug("{} No request ID, nothing to do", this.getLogPrefix());
            return false;
         } else {
            return super.doPreExecute(profileRequestContext);
         }
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      if (this.response instanceof ResponseAbstractType) {
         ((ResponseAbstractType)this.response).setInResponseTo(this.requestId);
      } else if (this.response instanceof StatusResponseType) {
         ((StatusResponseType)this.response).setInResponseTo(this.requestId);
      } else {
         this.log.debug("{} Message type {} is not supported", this.getLogPrefix(), this.response.getElementQName());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
      }

   }

   public static class DefaultRequestIdLookupStrategy implements Function {
      @Nonnull
      private final Logger log = LoggerFactory.getLogger(AddInResponseToToResponse.class);
      @Nonnull
      @NonnullElements
      private Set suppressForBindings = Collections.emptySet();

      public void setSuppressForBindings(@Nonnull @NonnullElements Collection bindings) {
         Constraint.isNotNull(bindings, "Bindings collection cannot be null");
         this.suppressForBindings = new HashSet();
         Iterator var2 = bindings.iterator();

         while(var2.hasNext()) {
            String b = (String)var2.next();
            String trimmed = StringSupport.trimOrNull(b);
            if (trimmed != null) {
               this.suppressForBindings.add(trimmed);
            }
         }

      }

      @Nullable
      public String apply(@Nullable ProfileRequestContext input) {
         MessageContext inMsgCtx = input.getInboundMessageContext();
         if (inMsgCtx == null) {
            this.log.debug("No inbound message context available");
            return null;
         } else {
            if (!this.suppressForBindings.isEmpty()) {
               SAMLBindingContext bindingCtx = (SAMLBindingContext)inMsgCtx.getSubcontext(SAMLBindingContext.class);
               if (bindingCtx != null && bindingCtx.getBindingUri() != null && this.suppressForBindings.contains(bindingCtx.getBindingUri())) {
                  this.log.debug("Inbound binding {} is suppressed, ignoring request ID", bindingCtx.getBindingUri());
                  return null;
               }
            }

            SAMLMessageInfoContext infoCtx = (SAMLMessageInfoContext)inMsgCtx.getSubcontext(SAMLMessageInfoContext.class, true);
            if (infoCtx == null) {
               this.log.debug("No inbound SAMLMessageInfoContext available");
               return null;
            } else {
               return infoCtx.getMessageId();
            }
         }
      }
   }
}
