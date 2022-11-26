package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import net.shibboleth.utilities.java.support.security.SecureRandomIdentifierGenerationStrategy;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.profile.logic.DefaultNameIDPolicyPredicate;
import org.opensaml.saml.common.profile.logic.MetadataNameIdentifierFormatStrategy;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.opensaml.saml.saml2.profile.SAML2NameIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNameIDToSubjects extends AbstractProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddNameIDToSubjects.class);
   @Nonnull
   private SAMLObjectBuilder subjectBuilder;
   @Nonnull
   private SAMLObjectBuilder nameIdBuilder;
   private boolean overwriteExisting;
   @Nonnull
   private Function requestLookupStrategy;
   @Nonnull
   private Function assertionsLookupStrategy;
   @Nonnull
   private Function idGeneratorLookupStrategy;
   @Nullable
   private Function issuerLookupStrategy;
   @Nonnull
   private Predicate nameIDPolicyPredicate;
   @Nonnull
   private Function formatLookupStrategy;
   @NonnullAfterInit
   private SAML2NameIDGenerator generator;
   @Nonnull
   @NonnullElements
   private List formats;
   @Nullable
   private String requiredFormat;
   @Nullable
   private AuthnRequest request;
   @Nullable
   private List assertions;
   @Nullable
   private IdentifierGenerationStrategy idGenerator;
   @Nullable
   private String issuerId;

   public AddNameIDToSubjects() throws ComponentInitializationException {
      this.subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
      this.nameIdBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameID.DEFAULT_ELEMENT_NAME);
      this.overwriteExisting = true;
      this.requestLookupStrategy = Functions.compose(new MessageLookup(AuthnRequest.class), new InboundMessageContextLookup());
      this.assertionsLookupStrategy = new AssertionStrategy();
      this.idGeneratorLookupStrategy = new Function() {
         public IdentifierGenerationStrategy apply(ProfileRequestContext input) {
            return new SecureRandomIdentifierGenerationStrategy();
         }
      };
      this.nameIDPolicyPredicate = new DefaultNameIDPolicyPredicate();
      ((DefaultNameIDPolicyPredicate)this.nameIDPolicyPredicate).setRequesterIdLookupStrategy(new RequesterIdFromIssuerFunction());
      ((DefaultNameIDPolicyPredicate)this.nameIDPolicyPredicate).setObjectLookupStrategy(new NameIDPolicyLookupFunction());
      ((DefaultNameIDPolicyPredicate)this.nameIDPolicyPredicate).initialize();
      this.formatLookupStrategy = new MetadataNameIdentifierFormatStrategy();
      this.formats = Collections.emptyList();
   }

   public void setOverwriteExisting(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.overwriteExisting = flag;
   }

   public void setRequestLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requestLookupStrategy = (Function)Constraint.isNotNull(strategy, "AuthnRequest lookup strategy cannot be null");
   }

   public void setAssertionsLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.assertionsLookupStrategy = (Function)Constraint.isNotNull(strategy, "Assertions lookup strategy cannot be null");
   }

   public void setIdentifierGeneratorLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.idGeneratorLookupStrategy = (Function)Constraint.isNotNull(strategy, "IdentifierGenerationStrategy lookup strategy cannot be null");
   }

   public void setIssuerLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.issuerLookupStrategy = strategy;
   }

   public void setNameIDPolicyPredicate(@Nonnull Predicate predicate) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.nameIDPolicyPredicate = (Predicate)Constraint.isNotNull(predicate, "NameIDPolicy predicate cannot be null");
   }

   public void setFormatLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.formatLookupStrategy = (Function)Constraint.isNotNull(strategy, "Format lookup strategy cannot be null");
   }

   public void setNameIDGenerator(@Nullable SAML2NameIDGenerator theGenerator) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.generator = (SAML2NameIDGenerator)Constraint.isNotNull(theGenerator, "SAML2NameIDGenerator cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.generator == null) {
         throw new ComponentInitializationException("SAML2NameIDGenerator cannot be null");
      }
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      if (!super.doPreExecute(profileRequestContext)) {
         return false;
      } else {
         this.log.debug("{} Attempting to add NameID to outgoing Assertion Subjects", this.getLogPrefix());
         this.idGenerator = (IdentifierGenerationStrategy)this.idGeneratorLookupStrategy.apply(profileRequestContext);
         if (this.idGenerator == null) {
            this.log.debug("{} No identifier generation strategy", this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidProfileContext");
            return false;
         } else {
            if (this.issuerLookupStrategy != null) {
               this.issuerId = (String)this.issuerLookupStrategy.apply(profileRequestContext);
            }

            this.assertions = (List)this.assertionsLookupStrategy.apply(profileRequestContext);
            if (this.assertions != null && !this.assertions.isEmpty()) {
               if (!this.nameIDPolicyPredicate.apply(profileRequestContext)) {
                  this.log.debug("{} NameIDPolicy was unacceptable", this.getLogPrefix());
                  ActionSupport.buildEvent(profileRequestContext, "InvalidNameIDPolicy");
                  return false;
               } else {
                  this.request = (AuthnRequest)this.requestLookupStrategy.apply(profileRequestContext);
                  this.requiredFormat = this.getRequiredFormat(profileRequestContext);
                  if (this.requiredFormat != null) {
                     this.formats = Collections.singletonList(this.requiredFormat);
                     this.log.debug("{} Request specified NameID format: {}", this.getLogPrefix(), this.requiredFormat);
                  } else {
                     this.formats = (List)this.formatLookupStrategy.apply(profileRequestContext);
                     if (this.formats == null || this.formats.isEmpty()) {
                        this.log.debug("{} No candidate NameID formats, nothing to do", this.getLogPrefix());
                        return false;
                     }

                     this.log.debug("{} Candidate NameID formats: {}", this.getLogPrefix(), this.formats);
                  }

                  return true;
               }
            } else {
               this.log.debug("{} No assertions returned, nothing to do", this.getLogPrefix());
               return false;
            }
         }
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      NameID nameId = this.generateNameID(profileRequestContext);
      if (nameId == null) {
         if (this.requiredFormat != null) {
            this.log.warn("{} Request specified use of an unsupportable identifier format: {}", this.getLogPrefix(), this.requiredFormat);
            ActionSupport.buildEvent(profileRequestContext, "InvalidNameIDPolicy");
         } else {
            this.log.debug("{} Unable to generate a NameID, leaving empty", this.getLogPrefix());
         }

      } else {
         int count = 0;

         for(Iterator var4 = this.assertions.iterator(); var4.hasNext(); ++count) {
            Assertion assertion = (Assertion)var4.next();
            Subject subject = this.getAssertionSubject(assertion);
            NameID existing = subject.getNameID();
            if (existing == null || this.overwriteExisting) {
               subject.setNameID(count > 0 ? this.cloneNameID(nameId) : nameId);
            }
         }

         if (count > 0) {
            this.log.debug("{} Added NameID to {} assertion subject(s)", this.getLogPrefix(), count);
         }

      }
   }

   @Nullable
   private String getRequiredFormat(@Nonnull ProfileRequestContext profileRequestContext) {
      if (this.request != null) {
         NameIDPolicy policy = this.request.getNameIDPolicy();
         if (policy != null) {
            String format = policy.getFormat();
            if (!Strings.isNullOrEmpty(format) && !"urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified".equals(format) && !"urn:oasis:names:tc:SAML:2.0:nameid-format:encrypted".equals(format)) {
               return format;
            }
         }
      }

      return null;
   }

   @Nullable
   private NameID generateNameID(@Nonnull ProfileRequestContext profileRequestContext) {
      Iterator var2 = this.formats.iterator();

      while(var2.hasNext()) {
         String format = (String)var2.next();
         this.log.debug("{} Trying to generate NameID with Format {}", this.getLogPrefix(), format);

         try {
            NameID nameId = this.generator.generate(profileRequestContext, format);
            if (nameId != null) {
               this.log.debug("{} Successfully generated NameID with Format {}", this.getLogPrefix(), format);
               return nameId;
            }
         } catch (SAMLException var5) {
            this.log.error("{} Error while generating NameID", this.getLogPrefix(), var5);
         }
      }

      return null;
   }

   @Nonnull
   private Subject getAssertionSubject(@Nonnull Assertion assertion) {
      if (assertion.getSubject() != null) {
         return assertion.getSubject();
      } else {
         Subject subject = (Subject)this.subjectBuilder.buildObject();
         assertion.setSubject(subject);
         return subject;
      }
   }

   @Nonnull
   private NameID cloneNameID(@Nonnull NameID nameId) {
      NameID clone = (NameID)this.nameIdBuilder.buildObject();
      clone.setFormat(nameId.getFormat());
      clone.setNameQualifier(nameId.getNameQualifier());
      clone.setSPNameQualifier(nameId.getSPNameQualifier());
      clone.setSPProvidedID(nameId.getSPProvidedID());
      clone.setValue(nameId.getValue());
      return clone;
   }

   public static class RequesterIdFromIssuerFunction implements Function {
      @Nonnull
      private Function requestLookupStrategy = Functions.compose(new MessageLookup(RequestAbstractType.class), new InboundMessageContextLookup());

      public void setRequestLookupStrategy(@Nonnull Function strategy) {
         this.requestLookupStrategy = (Function)Constraint.isNotNull(strategy, "Request lookup strategy cannot be null");
      }

      @Nullable
      public String apply(@Nullable ProfileRequestContext profileRequestContext) {
         RequestAbstractType request = (RequestAbstractType)this.requestLookupStrategy.apply(profileRequestContext);
         if (request != null && request.getIssuer() != null) {
            Issuer issuer = request.getIssuer();
            if (issuer.getFormat() == null || "urn:oasis:names:tc:SAML:2.0:nameid-format:entity".equals(issuer.getFormat())) {
               return issuer.getValue();
            }
         }

         return null;
      }
   }

   public static class NameIDPolicyLookupFunction implements Function {
      @Nonnull
      private Function requestLookupStrategy = Functions.compose(new MessageLookup(AuthnRequest.class), new InboundMessageContextLookup());

      public void setRequestLookupStrategy(@Nonnull Function strategy) {
         this.requestLookupStrategy = (Function)Constraint.isNotNull(strategy, "AuthnRequest lookup strategy cannot be null");
      }

      @Nullable
      public SAMLObject apply(@Nullable ProfileRequestContext profileRequestContext) {
         AuthnRequest request = (AuthnRequest)this.requestLookupStrategy.apply(profileRequestContext);
         return request != null ? request.getNameIDPolicy() : null;
      }
   }

   private class AssertionStrategy implements Function {
      private AssertionStrategy() {
      }

      @Nullable
      public List apply(@Nullable ProfileRequestContext input) {
         if (input != null && input.getOutboundMessageContext() != null) {
            Object outboundMessage = input.getOutboundMessageContext().getMessage();
            if (outboundMessage == null) {
               Assertion ret = SAML2ActionSupport.buildAssertion(AddNameIDToSubjects.this, AddNameIDToSubjects.this.idGenerator, AddNameIDToSubjects.this.issuerId);
               input.getOutboundMessageContext().setMessage(ret);
               return Collections.singletonList(ret);
            }

            if (outboundMessage instanceof Assertion) {
               return Collections.singletonList((Assertion)outboundMessage);
            }

            if (outboundMessage instanceof Response) {
               return ((Response)outboundMessage).getAssertions();
            }
         }

         return null;
      }

      // $FF: synthetic method
      AssertionStrategy(Object x1) {
         this();
      }
   }
}
