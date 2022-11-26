package org.opensaml.saml.common.binding.impl;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.RequestAbstractType;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckMessageVersionHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(CheckMessageVersionHandler.class);
   private boolean ignoreMissingOrUnrecognized;

   public void setIgnoreMissingOrUnrecognized(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.ignoreMissingOrUnrecognized = flag;
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      Object message = messageContext.getMessage();
      if (message == null) {
         this.log.debug("Message was not found");
         if (!this.ignoreMissingOrUnrecognized) {
            throw new MessageHandlerException("Message was not found");
         }
      } else {
         SAMLVersion version;
         if (message instanceof RequestAbstractType) {
            version = ((RequestAbstractType)message).getVersion();
            if (version.getMajorVersion() != 1) {
               throw new MessageHandlerException("Request major version  was invalid");
            }
         } else if (message instanceof ResponseAbstractType) {
            version = ((ResponseAbstractType)message).getVersion();
            if (version.getMajorVersion() != 1) {
               throw new MessageHandlerException("Request major version  was invalid");
            }
         } else if (message instanceof org.opensaml.saml.saml2.core.RequestAbstractType) {
            version = ((org.opensaml.saml.saml2.core.RequestAbstractType)message).getVersion();
            if (version.getMajorVersion() != 2) {
               throw new MessageHandlerException("Response major version  was invalid");
            }
         } else if (message instanceof StatusResponseType) {
            version = ((StatusResponseType)message).getVersion();
            if (version.getMajorVersion() != 2) {
               throw new MessageHandlerException("Response major version  was invalid");
            }
         } else {
            this.log.debug("Message type was not recognized");
            if (!this.ignoreMissingOrUnrecognized) {
               throw new MessageHandlerException("Message type was not recognized");
            }
         }
      }

   }
}
