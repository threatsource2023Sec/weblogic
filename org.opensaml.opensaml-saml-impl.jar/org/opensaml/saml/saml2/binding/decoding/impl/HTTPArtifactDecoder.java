package org.opensaml.saml.saml2.binding.decoding.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import net.shibboleth.utilities.java.support.security.SecureRandomIdentifierGenerationStrategy;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.decoder.servlet.BaseHttpServletRequestXMLMessageDecoder;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.BindingDescriptor;
import org.opensaml.saml.common.binding.EndpointResolver;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.artifact.SAMLSourceLocationArtifact;
import org.opensaml.saml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.saml.common.binding.impl.DefaultEndpointResolver;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.criterion.ArtifactCriterion;
import org.opensaml.saml.criterion.EndpointCriterion;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.criterion.RoleDescriptorCriterion;
import org.opensaml.saml.metadata.resolver.RoleDescriptorResolver;
import org.opensaml.saml.saml2.binding.artifact.SAML2Artifact;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilderFactory;
import org.opensaml.saml.saml2.core.Artifact;
import org.opensaml.saml.saml2.core.ArtifactResolve;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.SecurityException;
import org.opensaml.soap.client.SOAPClient;
import org.opensaml.soap.common.SOAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPArtifactDecoder extends BaseHttpServletRequestXMLMessageDecoder implements SAMLMessageDecoder {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(HTTPArtifactDecoder.class);
   @Nullable
   private BindingDescriptor bindingDescriptor;
   @NonnullAfterInit
   private SAML2ArtifactBuilderFactory artifactBuilderFactory;
   @NonnullAfterInit
   private EndpointResolver artifactEndpointResolver;
   @NonnullAfterInit
   private RoleDescriptorResolver roleDescriptorResolver;
   @NonnullAfterInit
   private QName peerEntityRole;
   private SOAPClient soapClient;
   private IdentifierGenerationStrategy idStrategy;

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.roleDescriptorResolver == null) {
         throw new ComponentInitializationException("RoleDescriptorResolver cannot be null");
      } else if (this.peerEntityRole == null) {
         throw new ComponentInitializationException("Peer entity role cannot be null");
      } else if (this.soapClient == null) {
         throw new ComponentInitializationException("SOAPClient cannot be null");
      } else {
         if (this.idStrategy == null) {
            this.idStrategy = new SecureRandomIdentifierGenerationStrategy();
         }

         if (this.artifactBuilderFactory == null) {
            this.artifactBuilderFactory = SAMLConfigurationSupport.getSAML2ArtifactBuilderFactory();
            if (this.artifactBuilderFactory == null) {
               throw new ComponentInitializationException("Could not obtain a required instance of SAML2ArtifactBuilderFactory");
            }
         }

         if (this.artifactEndpointResolver == null) {
            this.artifactEndpointResolver = new DefaultEndpointResolver();
         }

      }
   }

   protected void doDestroy() {
      super.doDestroy();
      this.bindingDescriptor = null;
      this.artifactBuilderFactory = null;
      this.artifactEndpointResolver = null;
      this.roleDescriptorResolver = null;
      this.peerEntityRole = null;
      this.soapClient = null;
      this.idStrategy = null;
   }

   @NonnullAfterInit
   public IdentifierGenerationStrategy getIdentifierGenerationStrategy() {
      return this.idStrategy;
   }

   public void setIdentifierGenerationStrategy(@Nullable IdentifierGenerationStrategy strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.idStrategy = strategy;
   }

   @NonnullAfterInit
   public QName getPeerEntityRole() {
      return this.peerEntityRole;
   }

   public void setPeerEntityRole(@Nonnull QName role) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.peerEntityRole = role;
   }

   @NonnullAfterInit
   public EndpointResolver getArtifactEndpointResolver() {
      return this.artifactEndpointResolver;
   }

   public void setArtifactEndpointResolver(@Nullable EndpointResolver resolver) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.artifactEndpointResolver = resolver;
   }

   @NonnullAfterInit
   public RoleDescriptorResolver getRoleDescriptorResolver() {
      return this.roleDescriptorResolver;
   }

   public void setRoleDescriptorResolver(@Nullable RoleDescriptorResolver resolver) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.roleDescriptorResolver = resolver;
   }

   @NonnullAfterInit
   public SAML2ArtifactBuilderFactory getArtifactBuilderFactory() {
      return this.artifactBuilderFactory;
   }

   public void setArtifactBuilderFactory(@Nullable SAML2ArtifactBuilderFactory factory) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.artifactBuilderFactory = factory;
   }

   @NonnullAfterInit
   public SOAPClient getSOAPClient() {
      return this.soapClient;
   }

   public void setSOAPClient(@Nonnull SOAPClient client) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.soapClient = client;
   }

   @Nonnull
   @NotEmpty
   public String getBindingURI() {
      return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact";
   }

   @Nullable
   public BindingDescriptor getBindingDescriptor() {
      return this.bindingDescriptor;
   }

   public void setBindingDescriptor(@Nullable BindingDescriptor descriptor) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.bindingDescriptor = descriptor;
   }

   protected void doDecode() throws MessageDecodingException {
      MessageContext messageContext = new MessageContext();
      HttpServletRequest request = this.getHttpServletRequest();
      String relayState = StringSupport.trim(request.getParameter("RelayState"));
      this.log.debug("Decoded SAML relay state of: {}", relayState);
      SAMLBindingSupport.setRelayState(messageContext, relayState);
      this.processArtifact(messageContext, request);
      this.populateBindingContext(messageContext);
      this.setMessageContext(messageContext);
   }

   private void processArtifact(MessageContext messageContext, HttpServletRequest request) throws MessageDecodingException {
      String encodedArtifact = StringSupport.trimOrNull(request.getParameter("SAMLart"));
      if (encodedArtifact == null) {
         this.log.error("URL SAMLart parameter was missing or did not contain a value.");
         throw new MessageDecodingException("URL SAMLart parameter was missing or did not contain a value.");
      } else {
         try {
            SAML2Artifact artifact = this.parseArtifact(encodedArtifact);
            RoleDescriptor peerRoleDescriptor = this.resolvePeerRoleDescriptor(artifact);
            if (peerRoleDescriptor == null) {
               throw new MessageDecodingException("Failed to resolve peer RoleDescriptor based on inbound artifact");
            } else {
               ArtifactResolutionService ars = this.resolveArtifactEndpoint(artifact, peerRoleDescriptor);
               SAMLObject inboundMessage = this.dereferenceArtifact(artifact, peerRoleDescriptor, ars);
               messageContext.setMessage(inboundMessage);
            }
         } catch (MessageDecodingException var8) {
            throw var8;
         } catch (Exception var9) {
            throw new MessageDecodingException("Fatal error decoding or resolving inbound artifact", var9);
         }
      }
   }

   private SAMLObject dereferenceArtifact(SAML2Artifact artifact, RoleDescriptor peerRoleDescriptor, ArtifactResolutionService ars) throws MessageDecodingException {
      MessageContext outbound = new MessageContext();
      outbound.setMessage(this.buildArtifactResolveRequestMessage(artifact, ars.getLocation(), peerRoleDescriptor));
      InOutOperationContext opContext = new InOutOperationContext((MessageContext)null, outbound);

      try {
         this.log.trace("Executing ArtifactResolve over SOAP 1.1 binding to endpoint: {}", ars.getLocation());
         this.soapClient.send(ars.getLocation(), opContext);
         return (SAMLObject)opContext.getInboundMessageContext().getMessage();
      } catch (SecurityException | SOAPException var7) {
         throw new MessageDecodingException("Error dereferencing artifact", var7);
      }
   }

   private ArtifactResolve buildArtifactResolveRequestMessage(SAML2Artifact artifact, String endpoint, RoleDescriptor peerRoleDescriptor) {
      ArtifactResolve request = (ArtifactResolve)XMLObjectSupport.buildXMLObject(ArtifactResolve.DEFAULT_ELEMENT_NAME);
      Artifact requestArtifact = (Artifact)XMLObjectSupport.buildXMLObject(Artifact.DEFAULT_ELEMENT_NAME);
      requestArtifact.setArtifact(Base64Support.encode(artifact.getArtifactBytes(), false));
      request.setArtifact(requestArtifact);
      request.setID(this.idStrategy.generateIdentifier(true));
      request.setDestination(endpoint);
      request.setIssueInstant(new DateTime(ISOChronology.getInstanceUTC()));
      request.setIssuer(this.buildIssuer(peerRoleDescriptor));
      return request;
   }

   private Issuer buildIssuer(RoleDescriptor peerRoleDescriptor) {
      Issuer issuer = (Issuer)XMLObjectSupport.buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
      return issuer;
   }

   private ArtifactResolutionService resolveArtifactEndpoint(SAML2Artifact artifact, RoleDescriptor peerRoleDescriptor) throws MessageDecodingException {
      RoleDescriptorCriterion roleDescriptorCriterion = new RoleDescriptorCriterion(peerRoleDescriptor);
      ArtifactResolutionService arsTemplate = (ArtifactResolutionService)XMLObjectSupport.buildXMLObject(ArtifactResolutionService.DEFAULT_ELEMENT_NAME);
      arsTemplate.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:SOAP");
      if (artifact instanceof SAMLSourceLocationArtifact) {
         arsTemplate.setLocation(((SAMLSourceLocationArtifact)artifact).getSourceLocation());
      }

      Integer endpointIndex = SAMLBindingSupport.convertSAML2ArtifactEndpointIndex(artifact.getEndpointIndex());
      arsTemplate.setIndex(endpointIndex);
      EndpointCriterion endpointCriterion = new EndpointCriterion(arsTemplate, false);
      CriteriaSet criteriaSet = new CriteriaSet(new Criterion[]{roleDescriptorCriterion, endpointCriterion});

      try {
         ArtifactResolutionService ars = (ArtifactResolutionService)this.artifactEndpointResolver.resolveSingle(criteriaSet);
         if (ars != null) {
            return ars;
         } else {
            throw new MessageDecodingException("Unable to resolve ArtifactResolutionService endpoint");
         }
      } catch (ResolverException var9) {
         throw new MessageDecodingException("Unable to resolve ArtifactResolutionService endpoint");
      }
   }

   private RoleDescriptor resolvePeerRoleDescriptor(SAML2Artifact artifact) throws MessageDecodingException {
      CriteriaSet criteriaSet = new CriteriaSet(new Criterion[]{new ArtifactCriterion(artifact), new ProtocolCriterion("urn:oasis:names:tc:SAML:2.0:protocol"), new EntityRoleCriterion(this.getPeerEntityRole())});

      try {
         return (RoleDescriptor)this.roleDescriptorResolver.resolveSingle(criteriaSet);
      } catch (ResolverException var4) {
         throw new MessageDecodingException("Error resolving peer entity RoleDescriptor", var4);
      }
   }

   private SAML2Artifact parseArtifact(String encodedArtifact) throws MessageDecodingException {
      return this.artifactBuilderFactory.buildArtifact(encodedArtifact);
   }

   protected void populateBindingContext(MessageContext messageContext) {
      SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true);
      bindingContext.setBindingUri(this.getBindingURI());
      bindingContext.setBindingDescriptor(this.bindingDescriptor);
      bindingContext.setHasBindingSignature(false);
      bindingContext.setIntendedDestinationEndpointURIRequired(false);
   }
}
