package org.opensaml.saml.saml2.binding.encoding.impl;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.HTMLEncoder;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.artifact.AbstractSAMLArtifact;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.saml.common.messaging.context.SAMLArtifactContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.binding.artifact.AbstractSAML2Artifact;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilder;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactType0004;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPArtifactEncoder extends BaseSAML2MessageEncoder {
   @Nonnull
   @NotEmpty
   public static final String DEFAULT_TEMPLATE_ID = "/templates/saml2-post-artifact-binding.vm";
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(HTTPArtifactEncoder.class);
   private boolean postEncoding;
   @Nullable
   private VelocityEngine velocityEngine;
   @Nonnull
   @NotEmpty
   private String velocityTemplateId;
   @NonnullAfterInit
   private SAMLArtifactMap artifactMap;
   @Nonnull
   @NotEmpty
   private byte[] defaultArtifactType;

   public HTTPArtifactEncoder() {
      this.defaultArtifactType = SAML2ArtifactType0004.TYPE_CODE;
      this.setVelocityTemplateId("/templates/saml2-post-artifact-binding.vm");
   }

   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact";
   }

   public boolean isPostEncoding() {
      return this.postEncoding;
   }

   public void setPostEncoding(boolean post) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.postEncoding = post;
   }

   @Nullable
   public VelocityEngine getVelocityEngine() {
      return this.velocityEngine;
   }

   public void setVelocityEngine(@Nullable VelocityEngine newVelocityEngine) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.velocityEngine = newVelocityEngine;
   }

   @Nonnull
   @NotEmpty
   public String getVelocityTemplateId() {
      return this.velocityTemplateId;
   }

   public void setVelocityTemplateId(@Nonnull @NotEmpty String newVelocityTemplateId) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.velocityTemplateId = (String)Constraint.isNotNull(StringSupport.trimOrNull(newVelocityTemplateId), "Velocity template ID cannot be null or empty");
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
      } else if (this.isPostEncoding() && this.velocityEngine == null) {
         throw new ComponentInitializationException("VelocityEngine cannot be null when POST is used");
      }
   }

   protected void doDestroy() {
      this.velocityEngine = null;
      this.velocityTemplateId = null;
      this.artifactMap = null;
      super.doDestroy();
   }

   protected void doEncode() throws MessageEncodingException {
      HttpServletResponse response = this.getHttpServletResponse();
      if (response == null) {
         throw new MessageEncodingException("HttpServletResponse was null");
      } else {
         response.setCharacterEncoding("UTF-8");
         if (this.postEncoding) {
            this.postEncode();
         } else {
            this.getEncode();
         }

      }
   }

   protected void postEncode() throws MessageEncodingException {
      this.log.debug("Performing HTTP POST SAML 2 artifact encoding");
      MessageContext messageContext = this.getMessageContext();
      this.log.debug("Creating velocity context");
      VelocityContext context = new VelocityContext();
      String endpointURL = this.getEndpointURL(messageContext).toString();
      String encodedEndpointURL = HTMLEncoder.encodeForHTMLAttribute(endpointURL);
      this.log.debug("Setting action parameter to: '{}', encoded as '{}'", endpointURL, encodedEndpointURL);
      context.put("action", encodedEndpointURL);
      context.put("SAMLArt", this.buildArtifact(messageContext).base64Encode());
      context.put("binding", this.getBindingURI());
      String relayState = SAMLBindingSupport.getRelayState(messageContext);
      if (SAMLBindingSupport.checkRelayState(relayState)) {
         String encodedRelayState = HTMLEncoder.encodeForHTMLAttribute(relayState);
         this.log.debug("Setting RelayState parameter to: '{}', encoded as '{}'", relayState, encodedRelayState);
         context.put("RelayState", encodedRelayState);
      }

      try {
         this.log.debug("Invoking velocity template");
         HttpServletResponse response = this.getHttpServletResponse();
         OutputStreamWriter outWriter = new OutputStreamWriter(response.getOutputStream());
         this.velocityEngine.mergeTemplate(this.velocityTemplateId, "UTF-8", context, outWriter);
         outWriter.flush();
      } catch (Exception var8) {
         this.log.error("Error invoking velocity template to create POST form", var8);
         throw new MessageEncodingException("Error creating output document", var8);
      }
   }

   protected void getEncode() throws MessageEncodingException {
      this.log.debug("Performing HTTP GET SAML 2 artifact encoding");
      MessageContext messageContext = this.getMessageContext();
      String endpointUrl = this.getEndpointURL(messageContext).toString();

      URLBuilder urlBuilder;
      try {
         urlBuilder = new URLBuilder(endpointUrl);
      } catch (MalformedURLException var10) {
         throw new MessageEncodingException("Endpoint URL " + endpointUrl + " is not a valid URL", var10);
      }

      List queryParams = urlBuilder.getQueryParams();
      queryParams.clear();
      AbstractSAMLArtifact artifact = this.buildArtifact(messageContext);
      if (artifact == null) {
         this.log.error("Unable to build artifact for message to relying party");
         throw new MessageEncodingException("Unable to build artifact for message to relying party");
      } else {
         queryParams.add(new Pair("SAMLart", artifact.base64Encode()));
         String relayState = SAMLBindingSupport.getRelayState(messageContext);
         if (SAMLBindingSupport.checkRelayState(relayState)) {
            queryParams.add(new Pair("RelayState", relayState));
         }

         HttpServletResponse response = this.getHttpServletResponse();

         try {
            response.sendRedirect(urlBuilder.buildURL());
         } catch (IOException var9) {
            throw new MessageEncodingException("Problem sending HTTP redirect", var9);
         }
      }
   }

   @Nonnull
   protected AbstractSAML2Artifact buildArtifact(@Nonnull MessageContext messageContext) throws MessageEncodingException {
      String requester = this.getInboundMessageIssuer(messageContext);
      String issuer = this.getOutboundMessageIssuer(messageContext);
      if (requester != null && issuer != null) {
         byte[] artifactType = this.getSAMLArtifactType(messageContext);
         SAML2ArtifactBuilder artifactBuilder;
         if (artifactType != null) {
            artifactBuilder = SAMLConfigurationSupport.getSAML2ArtifactBuilderFactory().getArtifactBuilder(artifactType);
         } else {
            artifactBuilder = SAMLConfigurationSupport.getSAML2ArtifactBuilderFactory().getArtifactBuilder(this.defaultArtifactType);
            this.storeSAMLArtifactType(messageContext, this.defaultArtifactType);
         }

         AbstractSAML2Artifact artifact = artifactBuilder.buildArtifact(messageContext);
         if (artifact == null) {
            this.log.error("Unable to build artifact for message to relying party");
            throw new MessageEncodingException("Unable to build artifact for message to relying party");
         } else {
            String encodedArtifact = artifact.base64Encode();

            try {
               this.artifactMap.put(encodedArtifact, requester, issuer, (SAMLObject)messageContext.getMessage());
               return artifact;
            } catch (IOException var9) {
               this.log.error("Unable to store message mapping for artifact", var9);
               throw new MessageEncodingException("Unable to store message mapping for artifact", var9);
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
