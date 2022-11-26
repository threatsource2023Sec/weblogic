package org.opensaml.saml.saml1.binding.impl;

import java.io.IOException;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml1.core.AssertionArtifact;
import org.opensaml.saml.saml1.core.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAML1ArtifactRequestIssuerHandler extends AbstractMessageHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAML1ArtifactRequestIssuerHandler.class);
   @NonnullAfterInit
   private SAMLArtifactMap artifactMap;

   public void setArtifactMap(@Nonnull SAMLArtifactMap map) {
      this.artifactMap = (SAMLArtifactMap)Constraint.isNotNull(map, "SAMLArtifactMap cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.artifactMap == null) {
         throw new ComponentInitializationException("SAMLArtifactMap cannot be null");
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (messageContext.getMessage() != null && messageContext.getMessage() instanceof Request) {
         Request request = (Request)messageContext.getMessage();
         if (request.getAssertionArtifacts().isEmpty()) {
            this.log.trace("{} Request did not contain any artifacts", this.getLogPrefix());
         } else {
            String artifact = ((AssertionArtifact)request.getAssertionArtifacts().get(0)).getAssertionArtifact();

            try {
               SAMLArtifactMap.SAMLArtifactMapEntry entry = this.artifactMap.get(artifact);
               if (entry == null) {
                  this.log.warn("{} Unable to resolve first artifact in request: {}", this.getLogPrefix(), artifact);
                  return;
               }

               this.log.debug("{} Derived issuer of aritfact resolution request as {}", this.getLogPrefix(), entry.getRelyingPartyId());
               ((SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class, true)).setEntityId(entry.getRelyingPartyId());
            } catch (IOException var5) {
               this.log.error("{} Error resolving first artifact in request: {}", new Object[]{this.getLogPrefix(), artifact, var5});
            }

         }
      } else {
         this.log.trace("{} Request message not set, or not of an applicable type", this.getLogPrefix());
      }
   }
}
