package org.opensaml.saml.common.binding.security.impl;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.BasicURLComparator;
import net.shibboleth.utilities.java.support.net.URIComparator;
import net.shibboleth.utilities.java.support.net.URIException;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.MessageException;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivedEndpointSecurityHandler extends AbstractMessageHandler {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(ReceivedEndpointSecurityHandler.class);
   @Nonnull
   private URIComparator uriComparator = new BasicURLComparator();
   @NonnullAfterInit
   private HttpServletRequest httpServletRequest;

   @Nonnull
   public URIComparator getURIComparator() {
      return this.uriComparator;
   }

   public void setURIComparator(@Nonnull URIComparator comparator) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.uriComparator = (URIComparator)Constraint.isNotNull(comparator, "URIComparator cannot be null");
   }

   @NonnullAfterInit
   public HttpServletRequest getHttpServletRequest() {
      return this.httpServletRequest;
   }

   public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.httpServletRequest = (HttpServletRequest)Constraint.isNotNull(request, "HttpServletRequest cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.uriComparator == null) {
         throw new ComponentInitializationException("URIComparator cannot be null");
      } else if (this.httpServletRequest == null) {
         throw new ComponentInitializationException("HttpServletRequest cannot be null");
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      this.checkEndpointURI(messageContext, this.getURIComparator());
   }

   protected boolean compareEndpointURIs(@Nonnull @NotEmpty String messageDestination, @Nonnull @NotEmpty String receiverEndpoint, @Nonnull URIComparator comparator) throws URIException {
      Constraint.isNotNull(messageDestination, "Message destination URI was null");
      Constraint.isNotNull(receiverEndpoint, "Receiver endpoint URI was null");
      Constraint.isNotNull(comparator, "URIComparator was null");
      return comparator.compare(messageDestination, receiverEndpoint);
   }

   protected void checkEndpointURI(@Nonnull MessageContext messageContext, @Nonnull URIComparator comparator) throws MessageHandlerException {
      Constraint.isNotNull(comparator, "URIComparator may not be null");
      this.log.debug("{} Checking SAML message intended destination endpoint against receiver endpoint", this.getLogPrefix());

      String messageDestination;
      try {
         messageDestination = StringSupport.trimOrNull(SAMLBindingSupport.getIntendedDestinationEndpointURI(messageContext));
      } catch (MessageException var10) {
         throw new MessageHandlerException("Error obtaining message intended destination endpoint URI", var10);
      }

      boolean bindingRequires = SAMLBindingSupport.isIntendedDestinationEndpointURIRequired(messageContext);
      if (messageDestination == null) {
         if (bindingRequires) {
            this.log.error("{} SAML message intended destination endpoint URI required by binding was empty", this.getLogPrefix());
            throw new MessageHandlerException("SAML message intended destination (required by binding) was not present");
         } else {
            this.log.debug("{} SAML message intended destination endpoint was empty, not required by binding, skipping", this.getLogPrefix());
         }
      } else {
         String receiverEndpoint;
         try {
            receiverEndpoint = StringSupport.trimOrNull(SAMLBindingSupport.getActualReceiverEndpointURI(messageContext, this.getHttpServletRequest()));
         } catch (MessageException var9) {
            throw new MessageHandlerException("Error obtaining message received endpoint URI", var9);
         }

         this.log.debug("{} Intended message destination endpoint: {}", this.getLogPrefix(), messageDestination);
         this.log.debug("{} Actual message receiver endpoint: {}", this.getLogPrefix(), receiverEndpoint);

         boolean matched;
         try {
            matched = this.compareEndpointURIs(messageDestination, receiverEndpoint, comparator);
         } catch (URIException var8) {
            throw new MessageHandlerException("Error comparing endpoint URI's", var8);
         }

         if (!matched) {
            this.log.error("{} SAML message intended destination endpoint '{}' did not match the recipient endpoint '{}'", new Object[]{this.getLogPrefix(), messageDestination, receiverEndpoint});
            throw new MessageHandlerException("SAML message failed received endpoint check");
         } else {
            this.log.debug("{} SAML message intended destination endpoint matched recipient endpoint", this.getLogPrefix());
         }
      }
   }
}
