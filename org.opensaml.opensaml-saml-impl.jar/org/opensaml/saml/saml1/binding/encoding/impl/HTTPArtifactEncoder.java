package org.opensaml.saml.saml1.binding.encoding.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.saml.common.messaging.context.SAMLArtifactContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml1.binding.artifact.AbstractSAML1Artifact;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactBuilder;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactType0001;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPArtifactEncoder extends BaseSAML1MessageEncoder {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(HTTPArtifactEncoder.class);
   @NonnullAfterInit
   private SAMLArtifactMap artifactMap;
   @Nonnull
   @NotEmpty
   private byte[] defaultArtifactType;

   public HTTPArtifactEncoder() {
      this.defaultArtifactType = SAML1ArtifactType0001.TYPE_CODE;
   }

   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:1.0:profiles:artifact-01";
   }

   @NonnullAfterInit
   public SAMLArtifactMap getArtifactMap() {
      return this.artifactMap;
   }

   public void setArtifactMap(@Nonnull SAMLArtifactMap newArtifactMap) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.artifactMap = (SAMLArtifactMap)Constraint.isNotNull(newArtifactMap, "SAMLArtifactMap cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.artifactMap == null) {
         throw new ComponentInitializationException("SAMLArtifactMap cannot be null");
      }
   }

   protected void doDestroy() {
      this.artifactMap = null;
      super.doDestroy();
   }

   protected void doEncode() throws MessageEncodingException {
      MessageContext messageContext = this.getMessageContext();
      String requester = this.getInboundMessageIssuer(messageContext);
      String issuer = this.getOutboundMessageIssuer(messageContext);
      if (requester != null && issuer != null) {
         String endpointUrl = this.getEndpointURL(messageContext).toString();

         URLBuilder urlBuilder;
         try {
            urlBuilder = new URLBuilder(endpointUrl);
         } catch (MalformedURLException var18) {
            throw new MessageEncodingException("Endpoint URL " + endpointUrl + " is not a valid URL", var18);
         }

         List queryParams = urlBuilder.getQueryParams();
         queryParams.clear();
         String relayState = SAMLBindingSupport.getRelayState(messageContext);
         if (SAMLBindingSupport.checkRelayState(relayState)) {
            queryParams.add(new Pair("TARGET", relayState));
         }

         byte[] artifactType = this.getSAMLArtifactType(messageContext);
         SAML1ArtifactBuilder artifactBuilder;
         if (artifactType != null) {
            artifactBuilder = SAMLConfigurationSupport.getSAML1ArtifactBuilderFactory().getArtifactBuilder(artifactType);
         } else {
            artifactBuilder = SAMLConfigurationSupport.getSAML1ArtifactBuilderFactory().getArtifactBuilder(this.defaultArtifactType);
            this.storeSAMLArtifactType(messageContext, this.defaultArtifactType);
         }

         SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
         if (!(outboundMessage instanceof Response)) {
            throw new MessageEncodingException("Outbound message was not a SAML 1 Response");
         } else {
            Response samlResponse = (Response)outboundMessage;
            Iterator var12 = samlResponse.getAssertions().iterator();

            while(var12.hasNext()) {
               Assertion assertion = (Assertion)var12.next();
               AbstractSAML1Artifact artifact = artifactBuilder.buildArtifact(messageContext, assertion);
               if (artifact == null) {
                  this.log.error("Unable to build artifact for message to relying party {}", requester);
                  throw new MessageEncodingException("Unable to build artifact for message to relying party");
               }

               try {
                  this.artifactMap.put(artifact.base64Encode(), requester, issuer, assertion);
               } catch (IOException var17) {
                  this.log.error("Unable to store assertion mapping for artifact", var17);
                  throw new MessageEncodingException("Unable to store assertion mapping for artifact", var17);
               }

               String artifactString = artifact.base64Encode();
               queryParams.add(new Pair("SAMLart", artifactString));
            }

            String encodedEndpoint = urlBuilder.buildURL();
            this.log.debug("Sending redirect to URL {} for relying party {}", encodedEndpoint, requester);
            HttpServletResponse response = this.getHttpServletResponse();
            if (response == null) {
               throw new MessageEncodingException("HttpServletResponse was null");
            } else {
               try {
                  response.sendRedirect(encodedEndpoint);
               } catch (IOException var16) {
                  throw new MessageEncodingException("Problem sending HTTP redirect", var16);
               }
            }
         }
      } else {
         throw new MessageEncodingException("Unable to obtain issuer or relying party for message encoding");
      }
   }

   @Nullable
   private String getOutboundMessageIssuer(@Nonnull MessageContext messageContext) {
      SAMLSelfEntityContext selfCtx = (SAMLSelfEntityContext)messageContext.getSubcontext(SAMLSelfEntityContext.class);
      return selfCtx == null ? null : selfCtx.getEntityId();
   }

   @Nullable
   private String getInboundMessageIssuer(@Nonnull MessageContext messageContext) {
      SAMLPeerEntityContext peerCtx = (SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class);
      return peerCtx == null ? null : peerCtx.getEntityId();
   }

   private void storeSAMLArtifactType(@Nonnull MessageContext messageContext, @Nonnull @NotEmpty byte[] artifactType) {
      ((SAMLArtifactContext)messageContext.getSubcontext(SAMLArtifactContext.class, true)).setArtifactType(artifactType);
   }

   @Nullable
   private byte[] getSAMLArtifactType(@Nonnull MessageContext messageContext) {
      return ((SAMLArtifactContext)messageContext.getSubcontext(SAMLArtifactContext.class, true)).getArtifactType();
   }
}
