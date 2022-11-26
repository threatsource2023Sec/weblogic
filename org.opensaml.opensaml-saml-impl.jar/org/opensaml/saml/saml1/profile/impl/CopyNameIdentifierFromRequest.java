package org.opensaml.saml.saml1.profile.impl;

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
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyNameIdentifierFromRequest extends AbstractProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(CopyNameIdentifierFromRequest.class);
   @Nonnull
   private final SAMLObjectBuilder subjectBuilder;
   @Nonnull
   private final SAMLObjectBuilder nameIdentifierBuilder;
   private boolean overwriteExisting;
   @Nonnull
   private Function nameIdentifierContextLookupStrategy;
   @Nonnull
   private Function responseLookupStrategy;
   @Nullable
   private NameIdentifier nameIdentifier;
   @Nullable
   private Response response;

   public CopyNameIdentifierFromRequest() {
      this.subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
      this.nameIdentifierBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameIdentifier.DEFAULT_ELEMENT_NAME);
      this.overwriteExisting = true;
      this.nameIdentifierContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLSubjectNameIdentifierContext.class, true), new InboundMessageContextLookup());
      this.responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
   }

   public void setOverwriteExisting(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.overwriteExisting = flag;
   }

   public void setNameIdentifierContextLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.nameIdentifierContextLookupStrategy = (Function)Constraint.isNotNull(strategy, "SAMLSubjectNameIdentifierContext lookup strategy cannot be null");
   }

   public void setResponseLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responseLookupStrategy = (Function)Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      this.log.debug("{} Attempting to add NameIdentifier to statements in outgoing Response", this.getLogPrefix());
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
            this.nameIdentifier = idCtx.getSAML1SubjectNameIdentifier();
            if (this.nameIdentifier == null) {
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

      label36:
      while(var3.hasNext()) {
         Assertion assertion = (Assertion)var3.next();
         Iterator var5 = assertion.getStatements().iterator();

         while(true) {
            Subject subject;
            NameIdentifier existing;
            do {
               Statement statement;
               do {
                  if (!var5.hasNext()) {
                     continue label36;
                  }

                  statement = (Statement)var5.next();
               } while(!(statement instanceof SubjectStatement));

               subject = this.getStatementSubject((SubjectStatement)statement);
               existing = subject.getNameIdentifier();
            } while(existing != null && !this.overwriteExisting);

            subject.setNameIdentifier(this.cloneNameIdentifier());
            ++count;
         }
      }

      if (count > 0) {
         this.log.debug("{} Added NameIdentifier to {} statement subject(s)", this.getLogPrefix(), count);
      }

   }

   @Nonnull
   private Subject getStatementSubject(@Nonnull SubjectStatement statement) {
      if (statement.getSubject() != null) {
         return statement.getSubject();
      } else {
         Subject subject = (Subject)this.subjectBuilder.buildObject();
         statement.setSubject(subject);
         return subject;
      }
   }

   @Nonnull
   private NameIdentifier cloneNameIdentifier() {
      NameIdentifier clone = (NameIdentifier)this.nameIdentifierBuilder.buildObject();
      clone.setFormat(this.nameIdentifier.getFormat());
      clone.setNameQualifier(this.nameIdentifier.getNameQualifier());
      clone.setValue(this.nameIdentifier.getValue());
      return clone;
   }
}
