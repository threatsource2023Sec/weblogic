package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import net.shibboleth.utilities.java.support.security.SecureRandomIdentifierGenerationStrategy;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddStatusResponseShell extends AbstractProfileAction {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(AbstractResponseShellAction.class);
   @NonnullAfterInit
   private QName messageType;
   private boolean overwriteExisting;
   @Nonnull
   private Function idGeneratorLookupStrategy = new Function() {
      public IdentifierGenerationStrategy apply(ProfileRequestContext input) {
         return new SecureRandomIdentifierGenerationStrategy();
      }
   };
   @Nullable
   private Function issuerLookupStrategy;
   @Nullable
   private IdentifierGenerationStrategy idGenerator;
   @Nullable
   private String issuerId;

   public void setMessageType(@Nonnull QName type) {
      this.messageType = (QName)Constraint.isNotNull(type, "Message type cannot be null");
   }

   public void setOverwriteExisting(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.overwriteExisting = flag;
   }

   public void setIdentifierGeneratorLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.idGeneratorLookupStrategy = (Function)Constraint.isNotNull(strategy, "IdentifierGenerationStrategy lookup strategy cannot be null");
   }

   public void setIssuerLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.issuerLookupStrategy = strategy;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.messageType == null) {
         throw new ComponentInitializationException("Message type cannot be null");
      }
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      MessageContext outboundMessageCtx = profileRequestContext.getOutboundMessageContext();
      if (outboundMessageCtx == null) {
         this.log.debug("{} No outbound message context", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else if (!this.overwriteExisting && outboundMessageCtx.getMessage() != null) {
         this.log.debug("{} Outbound message context already contains a response", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else {
         this.idGenerator = (IdentifierGenerationStrategy)this.idGeneratorLookupStrategy.apply(profileRequestContext);
         if (this.idGenerator == null) {
            this.log.debug("{} No identifier generation strategy", this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidProfileContext");
            return false;
         } else {
            if (this.issuerLookupStrategy != null) {
               this.issuerId = (String)this.issuerLookupStrategy.apply(profileRequestContext);
            }

            outboundMessageCtx.setMessage((Object)null);
            return super.doPreExecute(profileRequestContext);
         }
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      XMLObjectBuilderFactory bf = XMLObjectProviderRegistrySupport.getBuilderFactory();
      SAMLObjectBuilder statusCodeBuilder = (SAMLObjectBuilder)bf.getBuilderOrThrow(StatusCode.TYPE_NAME);
      SAMLObjectBuilder statusBuilder = (SAMLObjectBuilder)bf.getBuilderOrThrow(Status.TYPE_NAME);
      SAMLObjectBuilder responseBuilder = (SAMLObjectBuilder)bf.getBuilderOrThrow(this.messageType);
      StatusCode statusCode = (StatusCode)statusCodeBuilder.buildObject();
      statusCode.setValue("urn:oasis:names:tc:SAML:2.0:status:Success");
      Status status = (Status)statusBuilder.buildObject();
      status.setStatusCode(statusCode);
      SAMLObject object = responseBuilder.buildObject();
      if (!(object instanceof StatusResponseType)) {
         this.log.error("{} Message was not derived from StatusResponseType, not compatible with this action", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "MessageProcessingError");
      } else {
         StatusResponseType response = (StatusResponseType)object;
         response.setID(this.idGenerator.generateIdentifier());
         response.setIssueInstant(new DateTime(ISOChronology.getInstanceUTC()));
         response.setStatus(status);
         response.setVersion(SAMLVersion.VERSION_20);
         if (this.issuerId != null) {
            this.log.debug("{} Setting Issuer to {}", this.getLogPrefix(), this.issuerId);
            SAMLObjectBuilder issuerBuilder = (SAMLObjectBuilder)bf.getBuilderOrThrow(Issuer.DEFAULT_ELEMENT_NAME);
            Issuer issuer = (Issuer)issuerBuilder.buildObject();
            issuer.setValue(this.issuerId);
            response.setIssuer(issuer);
         } else {
            this.log.debug("{} No issuer value available, leaving Issuer unset", this.getLogPrefix());
         }

         profileRequestContext.getOutboundMessageContext().setMessage(response);
      }
   }
}
