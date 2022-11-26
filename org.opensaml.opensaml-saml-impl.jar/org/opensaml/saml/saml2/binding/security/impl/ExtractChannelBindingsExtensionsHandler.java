package org.opensaml.saml.saml2.binding.security.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractChannelBindingsExtensionsHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ExtractChannelBindingsExtensionsHandler.class);

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (super.doPreInvoke(messageContext) && messageContext.getMessage() != null) {
         if (!SAMLBindingSupport.isMessageSigned(messageContext)) {
            this.log.debug("Message was not signed, cannot extract ChannelBindings from it");
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      Extensions extensions = null;
      if (messageContext.getMessage() instanceof RequestAbstractType) {
         extensions = ((RequestAbstractType)messageContext.getMessage()).getExtensions();
      } else {
         if (!(messageContext.getMessage() instanceof StatusResponseType)) {
            this.log.debug("{} Message was not of a supported type", this.getLogPrefix());
            return;
         }

         extensions = ((StatusResponseType)messageContext.getMessage()).getExtensions();
      }

      List bindings = extensions != null ? extensions.getUnknownXMLObjects(ChannelBindings.DEFAULT_ELEMENT_NAME) : Collections.emptyList();
      if (bindings.isEmpty()) {
         this.log.debug("{} Message did not contain any ChannelBindings extensions", this.getLogPrefix());
      } else {
         Collection channelBindings = ((ChannelBindingsContext)messageContext.getSubcontext(ChannelBindingsContext.class, true)).getChannelBindings();
         Iterator var5 = bindings.iterator();

         while(var5.hasNext()) {
            XMLObject cb = (XMLObject)var5.next();
            if (cb instanceof ChannelBindings) {
               channelBindings.add((ChannelBindings)cb);
            }
         }

         this.log.debug("{} {} ChannelBindings extension(s) found", this.getLogPrefix(), channelBindings.size());
      }
   }
}
