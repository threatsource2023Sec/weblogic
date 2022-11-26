package org.opensaml.saml.common.binding.impl;

import com.google.common.base.Function;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.util.SOAPSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddChannelBindingsHeaderHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddChannelBindingsHeaderHandler.class);
   @Nonnull
   private Function channelBindingsContextLookupStrategy = new ChildContextLookup(ChannelBindingsContext.class);
   @Nullable
   private ChannelBindingsContext channelBindingsContext;

   public void setChannelBindingsContextLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.channelBindingsContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "ChannelBindingsContext lookup strategy cannot be null");
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         this.channelBindingsContext = (ChannelBindingsContext)this.channelBindingsContextLookupStrategy.apply(messageContext);
         if (this.channelBindingsContext != null && !this.channelBindingsContext.getChannelBindings().isEmpty()) {
            return true;
         } else {
            this.log.debug("{} No ChannelBindings to add, nothing to do", this.getLogPrefix());
            return false;
         }
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      SAMLObjectBuilder cbBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(ChannelBindings.DEFAULT_ELEMENT_NAME);
      Iterator var3 = this.channelBindingsContext.getChannelBindings().iterator();

      while(var3.hasNext()) {
         ChannelBindings cb = (ChannelBindings)var3.next();
         ChannelBindings header = (ChannelBindings)cbBuilder.buildObject();
         header.setType(cb.getType());
         SOAPSupport.addSOAP11MustUnderstandAttribute(header, true);
         SOAPSupport.addSOAP11ActorAttribute(header, "http://schemas.xmlsoap.org/soap/actor/next");

         try {
            SOAPMessagingSupport.addHeaderBlock(messageContext, header);
         } catch (Exception var7) {
            throw new MessageHandlerException(var7);
         }
      }

   }
}
