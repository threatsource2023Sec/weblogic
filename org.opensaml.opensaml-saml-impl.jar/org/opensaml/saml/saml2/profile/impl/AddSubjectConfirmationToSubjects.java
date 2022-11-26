package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.net.URI;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddSubjectConfirmationToSubjects extends AbstractProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddSubjectConfirmationToSubjects.class);
   @Nonnull
   private final SAMLObjectBuilder subjectBuilder;
   @Nonnull
   private final SAMLObjectBuilder confirmationBuilder;
   @Nonnull
   private final SAMLObjectBuilder confirmationDataBuilder;
   private boolean overwriteExisting;
   @Nonnull
   private Function responseLookupStrategy;
   @Nullable
   private Function addressLookupStrategy;
   @Nullable
   private Function inResponseToLookupStrategy;
   @Nullable
   private Function recipientLookupStrategy;
   @Nullable
   private Function lifetimeLookupStrategy;
   @NonnullAfterInit
   private String confirmationMethod;
   @Nullable
   private Response response;

   public AddSubjectConfirmationToSubjects() {
      this.subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
      this.confirmationBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
      this.confirmationDataBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
      this.overwriteExisting = true;
      this.responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
      this.addressLookupStrategy = new Function() {
         public String apply(ProfileRequestContext input) {
            String address = AddSubjectConfirmationToSubjects.this.getHttpServletRequest() != null ? AddSubjectConfirmationToSubjects.this.getHttpServletRequest().getRemoteAddr() : null;
            AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data Address to {}", AddSubjectConfirmationToSubjects.this.getLogPrefix(), address != null ? address : "(none)");
            return address;
         }
      };
      this.inResponseToLookupStrategy = new Function() {
         public String apply(ProfileRequestContext input) {
            if (AddSubjectConfirmationToSubjects.this.response != null && AddSubjectConfirmationToSubjects.this.response.getInResponseTo() != null) {
               AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data InResponseTo to {}", AddSubjectConfirmationToSubjects.this.getLogPrefix(), AddSubjectConfirmationToSubjects.this.response.getInResponseTo());
               return AddSubjectConfirmationToSubjects.this.response.getInResponseTo();
            } else {
               AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data InResponseTo to (none)", AddSubjectConfirmationToSubjects.this.getLogPrefix());
               return null;
            }
         }
      };
      this.recipientLookupStrategy = new Function() {
         public String apply(ProfileRequestContext input) {
            if (input.getOutboundMessageContext() != null) {
               try {
                  URI uri = SAMLBindingSupport.getEndpointURL(input.getOutboundMessageContext());
                  if (uri != null) {
                     String url = uri.toString();
                     AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data Recipient to {}", AddSubjectConfirmationToSubjects.this.getLogPrefix(), url);
                     return url;
                  }
               } catch (BindingException var4) {
                  AddSubjectConfirmationToSubjects.this.log.debug("{} Error getting response endpoint", AddSubjectConfirmationToSubjects.this.getLogPrefix(), var4);
               }
            }

            AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data Recipient to (none)", AddSubjectConfirmationToSubjects.this.getLogPrefix());
            return null;
         }
      };
      this.lifetimeLookupStrategy = new Function() {
         public Long apply(ProfileRequestContext input) {
            AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data NotOnOrAfter to 5 minutes from now", AddSubjectConfirmationToSubjects.this.getLogPrefix());
            return 300000L;
         }
      };
   }

   public void setOverwriteExisting(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.overwriteExisting = flag;
   }

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   public void setAddressLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.addressLookupStrategy = strategy;
   }

   public void setInResponseToLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.inResponseToLookupStrategy = strategy;
   }

   public void setRecipientLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.recipientLookupStrategy = strategy;
   }

   public void setLifetimeLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.lifetimeLookupStrategy = strategy;
   }

   public void setMethod(@Nonnull @NotEmpty String method) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.confirmationMethod = (String)Constraint.isNotNull(StringSupport.trimOrNull(method), "Confirmation method cannot be null or empty");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.confirmationMethod == null) {
         throw new ComponentInitializationException("Confirmation method cannot be null or empty");
      }
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.log.debug("{} Attempting to add SubjectConfirmation to assertions in outgoing Response", this.getLogPrefix());
      this.response = (Response)this.responseLookupStrategy.apply(profileRequestContext);
      if (this.response == null) {
         this.log.debug("{} No SAML response located in current profile request context", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else if (this.response.getAssertions().isEmpty()) {
         this.log.debug("{} No assertions in response message, nothing to do", this.getLogPrefix());
         return false;
      } else {
         return super.doPreExecute(profileRequestContext);
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      SubjectConfirmation confirmation = (SubjectConfirmation)this.confirmationBuilder.buildObject();
      confirmation.setMethod(this.confirmationMethod);
      SubjectConfirmationData confirmationData = null;
      String address = this.addressLookupStrategy != null ? (String)this.addressLookupStrategy.apply(profileRequestContext) : null;
      if (address != null) {
         confirmationData = confirmationData != null ? confirmationData : (SubjectConfirmationData)this.confirmationDataBuilder.buildObject();
         confirmationData.setAddress(address);
      }

      String inResponseTo = this.inResponseToLookupStrategy != null ? (String)this.inResponseToLookupStrategy.apply(profileRequestContext) : null;
      if (inResponseTo != null) {
         confirmationData = confirmationData != null ? confirmationData : (SubjectConfirmationData)this.confirmationDataBuilder.buildObject();
         confirmationData.setInResponseTo(inResponseTo);
      }

      String recipient = this.recipientLookupStrategy != null ? (String)this.recipientLookupStrategy.apply(profileRequestContext) : null;
      if (recipient != null) {
         confirmationData = confirmationData != null ? confirmationData : (SubjectConfirmationData)this.confirmationDataBuilder.buildObject();
         confirmationData.setRecipient(recipient);
      }

      Long lifetime = this.lifetimeLookupStrategy != null ? (Long)this.lifetimeLookupStrategy.apply(profileRequestContext) : null;
      if (lifetime != null) {
         confirmationData = confirmationData != null ? confirmationData : (SubjectConfirmationData)this.confirmationDataBuilder.buildObject();
         confirmationData.setNotOnOrAfter((new DateTime()).plus(lifetime));
      }

      if (confirmationData != null) {
         confirmation.setSubjectConfirmationData(confirmationData);
      }

      int count = 0;

      for(Iterator var9 = this.response.getAssertions().iterator(); var9.hasNext(); ++count) {
         Assertion assertion = (Assertion)var9.next();
         Subject subject = this.getAssertionSubject(assertion);
         if (this.overwriteExisting) {
            subject.getSubjectConfirmations().clear();
         }

         subject.getSubjectConfirmations().add(count > 0 ? this.cloneConfirmation(confirmation) : confirmation);
      }

      if (count > 0) {
         this.log.debug("{} Added SubjectConfirmation with method {} to {} assertion(s)", new Object[]{this.getLogPrefix(), this.confirmationMethod, count});
      }

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
   private SubjectConfirmation cloneConfirmation(@Nonnull SubjectConfirmation confirmation) {
      SubjectConfirmation clone = (SubjectConfirmation)this.confirmationBuilder.buildObject();
      clone.setMethod(confirmation.getMethod());
      SubjectConfirmationData data = confirmation.getSubjectConfirmationData();
      if (data != null) {
         SubjectConfirmationData cloneData = (SubjectConfirmationData)this.confirmationDataBuilder.buildObject();
         cloneData.setAddress(data.getAddress());
         cloneData.setInResponseTo(data.getInResponseTo());
         cloneData.setRecipient(data.getRecipient());
         cloneData.setNotBefore(data.getNotBefore());
         cloneData.setNotOnOrAfter(data.getNotOnOrAfter());
         clone.setSubjectConfirmationData(cloneData);
      }

      return clone;
   }
}
