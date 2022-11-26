package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.SAMLSubjectNameIdentifierContext;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyNameIDFromRequest extends AbstractProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(CopyNameIDFromRequest.class);
   @Nonnull
   private final SAMLObjectBuilder subjectBuilder;
   @Nonnull
   private final SAMLObjectBuilder nameIdBuilder;
   private boolean overwriteExisting;
   @Nonnull
   private Function nameIdentifierContextLookupStrategy;
   @Nonnull
   private Function responseLookupStrategy;
   @Nullable
   private NameID nameId;
   @Nullable
   private Response response;

   public CopyNameIDFromRequest() {
      this.subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
      this.nameIdBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameID.DEFAULT_ELEMENT_NAME);
      this.overwriteExisting = true;
      this.nameIdentifierContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLSubjectNameIdentifierContext.class, true), new InboundMessageContextLookup());
      this.responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
   }

   public void setOverwriteExisting(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.overwriteExisting = flag;
   }

   public void setNameIDContextLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.nameIdentifierContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "SAMLSubjectNameIdentifierContext lookup strategy cannot be null");
   }

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.log.debug("{} Attempting to add NameID to statements in outgoing Response", this.getLogPrefix());
      this.response = (Response)this.responseLookupStrategy.apply(profileRequestContext);
      if (this.response == null) {
         this.log.debug("{} No SAML response located", this.getLogPrefix());
         ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
         return false;
      } else if (this.response.getAssertions().isEmpty()) {
         this.log.debug("{} No assertions in response message, nothing to do", this.getLogPrefix());
         return false;
      } else {
         SAMLSubjectNameIdentifierContext idCtx = (SAMLSubjectNameIdentifierContext)this.nameIdentifierContextLookupStrategy.apply(profileRequestContext);
         if (idCtx == null) {
            this.log.debug("{} No SAMLSubjectNameIdentifierContext located", this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
         } else {
            this.nameId = idCtx.getSAML2SubjectNameID();
            if (this.nameId == null) {
               this.log.debug("{} No SAMLSubjectNameIdentifierContext located", this.getLogPrefix());
               ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
               return false;
            } else {
               return super.doPreExecute(profileRequestContext);
            }
         }
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      int count = 0;
      Iterator var3 = this.response.getAssertions().iterator();

      while(true) {
         Subject subject;
         NameID existing;
         do {
            if (!var3.hasNext()) {
               if (count > 0) {
                  this.log.debug("{} Added NameID to {} assertion(s)", this.getLogPrefix(), count);
               }

               return;
            }

            Assertion assertion = (Assertion)var3.next();
            subject = this.getAssertionSubject(assertion);
            existing = subject.getNameID();
         } while(existing != null && !this.overwriteExisting);

         subject.setNameID(this.cloneNameID());
         ++count;
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
   private NameID cloneNameID() {
      NameID clone = (NameID)this.nameIdBuilder.buildObject();
      clone.setFormat(this.nameId.getFormat());
      clone.setNameQualifier(this.nameId.getNameQualifier());
      clone.setSPNameQualifier(this.nameId.getSPNameQualifier());
      clone.setSPProvidedID(this.nameId.getSPProvidedID());
      clone.setValue(this.nameId.getValue());
      return clone;
   }
}
