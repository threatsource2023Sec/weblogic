package org.opensaml.saml.saml2.binding.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.ActorBearing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractChannelBindingsHeadersHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ExtractChannelBindingsHeadersHandler.class);
   private boolean finalDestination;
   private boolean nextDestination = true;

   public void setFinalDestination(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.finalDestination = flag;
   }

   public void setNextDestination(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.nextDestination = flag;
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      Collection channelBindings = new ArrayList();
      List headers = SOAPMessagingSupport.getHeaderBlock(messageContext, ChannelBindings.DEFAULT_ELEMENT_NAME, (Set)null, this.finalDestination);
      Iterator var4 = headers.iterator();

      while(true) {
         XMLObject header;
         do {
            do {
               if (!var4.hasNext()) {
                  if (channelBindings.isEmpty()) {
                     this.log.debug("{} No ChannelBindings header blocks found", this.getLogPrefix());
                  } else {
                     this.log.debug("{} {} ChannelBindings header block(s) found", this.getLogPrefix(), channelBindings.size());
                     ((ChannelBindingsContext)((SOAP11Context)messageContext.getSubcontext(SOAP11Context.class)).getSubcontext(ChannelBindingsContext.class, true)).getChannelBindings().addAll(channelBindings);
                  }

                  return;
               }

               header = (XMLObject)var4.next();
            } while(!(header instanceof ChannelBindings));
         } while(null != ((ActorBearing)header).getSOAP11Actor() && !this.nextDestination);

         channelBindings.add((ChannelBindings)header);
      }
   }
}
