package org.opensaml.saml.common.binding.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.AttributeConsumingServiceContext;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLAddAttributeConsumingServiceHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAMLAddAttributeConsumingServiceHandler.class);
   @Nonnull
   private Function metadataContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLMetadataContext.class), new ChildContextLookup(SAMLPeerEntityContext.class));
   @Nullable
   private Function indexLookupStrategy = new AuthnRequestIndexLookup();
   @Nullable
   private Integer index;

   public void setMetadataContextLookupStrategy(@Nonnull Function strategy) {
      this.metadataContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "SAMLMetadataContext lookup strategy cannot be null");
   }

   public void setIndexLookupStrategy(@Nullable Function strategy) {
      this.indexLookupStrategy = (Function)Constraint.isNotNull(strategy, "AttributeConsumingService index lookup strategy cannot be null");
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         if (this.indexLookupStrategy != null) {
            this.index = (Integer)this.indexLookupStrategy.apply(messageContext);
         }

         return true;
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SAMLMetadataContext metadataContext = (SAMLMetadataContext)this.metadataContextLookupStrategy.apply(messageContext);
      if (metadataContext == null) {
         this.log.debug("{} No metadata context found, nothing to do", this.getLogPrefix());
      } else if (!(metadataContext.getRoleDescriptor() instanceof SPSSODescriptor)) {
         this.log.debug("{} Metadata context did not contain an SPSSODescriptor, nothing to do", this.getLogPrefix());
      } else {
         SPSSODescriptor ssoDescriptor = (SPSSODescriptor)metadataContext.getRoleDescriptor();
         AttributeConsumingService acs = null;
         if (null != this.index) {
            this.log.debug("{} Request specified AttributeConsumingService index {}", this.getLogPrefix(), this.index);
            Iterator var5 = ssoDescriptor.getAttributeConsumingServices().iterator();

            while(var5.hasNext()) {
               AttributeConsumingService acsEntry = (AttributeConsumingService)var5.next();
               if (this.index == acsEntry.getIndex()) {
                  acs = acsEntry;
                  break;
               }
            }
         }

         if (null == acs) {
            this.log.debug("{} Selecting default AttributeConsumingService, if any", this.getLogPrefix());
            acs = ssoDescriptor.getDefaultAttributeConsumingService();
         }

         if (null != acs) {
            this.log.debug("{} Selected AttributeConsumingService with index {}", this.getLogPrefix(), acs.getIndex());
            ((AttributeConsumingServiceContext)metadataContext.getSubcontext(AttributeConsumingServiceContext.class, true)).setAttributeConsumingService(acs);
         } else {
            this.log.debug("{} No AttributeConsumingService selected", this.getLogPrefix());
         }

      }
   }

   private class AuthnRequestIndexLookup implements Function {
      private AuthnRequestIndexLookup() {
      }

      public Integer apply(@Nullable MessageContext input) {
         if (input != null) {
            Object message = input.getMessage();
            if (message != null && message instanceof AuthnRequest) {
               return ((AuthnRequest)message).getAttributeConsumingServiceIndex();
            }
         }

         return null;
      }

      // $FF: synthetic method
      AuthnRequestIndexLookup(Object x1) {
         this();
      }
   }
}
