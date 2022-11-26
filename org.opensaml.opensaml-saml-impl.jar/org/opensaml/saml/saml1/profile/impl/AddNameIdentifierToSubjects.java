package org.opensaml.saml.saml1.profile.impl;

import com.google.common.base.Function;
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
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.profile.logic.MetadataNameIdentifierFormatStrategy;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectStatement;
import org.opensaml.saml.saml1.profile.SAML1NameIdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNameIdentifierToSubjects extends AbstractProfileAction {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AddNameIdentifierToSubjects.class);
   @Nonnull
   private final SAMLObjectBuilder subjectBuilder;
   @Nonnull
   private final SAMLObjectBuilder nameIdentifierBuilder;
   private boolean overwriteExisting;
   @Nonnull
   private Function assertionsLookupStrategy;
   @Nonnull
   private Function formatLookupStrategy;
   @NonnullAfterInit
   private SAML1NameIdentifierGenerator generator;
   @Nonnull
   @NonnullElements
   private List formats;
   @Nonnull
   @NonnullElements
   private List assertions;

   public AddNameIdentifierToSubjects() {
      this.subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
      this.nameIdentifierBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameIdentifier.DEFAULT_ELEMENT_NAME);
      this.overwriteExisting = true;
      this.assertionsLookupStrategy = new AssertionStrategy();
      this.formatLookupStrategy = new MetadataNameIdentifierFormatStrategy();
      this.formats = Collections.emptyList();
   }

   public void setOverwriteExisting(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.overwriteExisting = flag;
   }

   public void setAssertionsLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.assertionsLookupStrategy = (Function)Constraint.isNotNull(strategy, "Assertions lookup strategy cannot be null");
   }

   public void setFormatLookupStrategy(@Nonnull Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.formatLookupStrategy = (Function)Constraint.isNotNull(strategy, "Format lookup strategy cannot be null");
   }

   public void setNameIdentifierGenerator(@Nonnull SAML1NameIdentifierGenerator theGenerator) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.generator = (SAML1NameIdentifierGenerator)Constraint.isNotNull(theGenerator, "SAML1NameIdentifierGenerator cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.generator == null) {
         throw new ComponentInitializationException("SAML1NameIdentifierGenerator cannot be null");
      }
   }

   protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      if (!super.doPreExecute(profileRequestContext)) {
         return false;
      } else {
         this.log.debug("{} Attempting to add NameIdentifier to statements in outgoing Assertions", this.getLogPrefix());
         this.assertions = (List)this.assertionsLookupStrategy.apply(profileRequestContext);
         if (this.assertions != null && !this.assertions.isEmpty()) {
            this.formats = (List)this.formatLookupStrategy.apply(profileRequestContext);
            if (this.formats != null && !this.formats.isEmpty()) {
               this.log.debug("{} Candidate NameIdentifier formats: {}", this.getLogPrefix(), this.formats);
               return true;
            } else {
               this.log.debug("{} No candidate NameIdentifier formats, nothing to do", this.getLogPrefix());
               return false;
            }
         } else {
            this.log.debug("{} No assertions returned, nothing to do", this.getLogPrefix());
            return false;
         }
      }
   }

   protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
      NameIdentifier nameIdentifier = this.generateNameIdentifier(profileRequestContext);
      if (nameIdentifier == null) {
         this.log.debug("{} Unable to generate a NameIdentifier, leaving empty", this.getLogPrefix());
      } else {
         int count = 0;
         Iterator var4 = this.assertions.iterator();

         label45:
         while(var4.hasNext()) {
            Assertion assertion = (Assertion)var4.next();
            Iterator var6 = assertion.getStatements().iterator();

            while(true) {
               Subject subject;
               NameIdentifier existing;
               do {
                  Statement statement;
                  do {
                     if (!var6.hasNext()) {
                        continue label45;
                     }

                     statement = (Statement)var6.next();
                  } while(!(statement instanceof SubjectStatement));

                  subject = this.getStatementSubject((SubjectStatement)statement);
                  existing = subject.getNameIdentifier();
               } while(existing != null && !this.overwriteExisting);

               subject.setNameIdentifier(count > 0 ? this.cloneNameIdentifier(nameIdentifier) : nameIdentifier);
               ++count;
            }
         }

         if (count > 0) {
            this.log.debug("{} Added NameIdentifier to {} statement subject(s)", this.getLogPrefix(), count);
         }

      }
   }

   @Nullable
   private NameIdentifier generateNameIdentifier(@Nonnull ProfileRequestContext profileRequestContext) {
      Iterator var2 = this.formats.iterator();

      while(var2.hasNext()) {
         String format = (String)var2.next();
         this.log.debug("{} Trying to generate NameIdentifier with Format {}", this.getLogPrefix(), format);

         try {
            NameIdentifier nameIdentifier = this.generator.generate(profileRequestContext, format);
            if (nameIdentifier != null) {
               this.log.debug("{} Successfully generated NameIdentifier with Format {}", this.getLogPrefix(), format);
               return nameIdentifier;
            }
         } catch (SAMLException var5) {
            this.log.error("{} Error while generating NameIdentifier", this.getLogPrefix(), var5);
         }
      }

      return null;
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
   private NameIdentifier cloneNameIdentifier(@Nonnull NameIdentifier nameIdentifier) {
      NameIdentifier clone = (NameIdentifier)this.nameIdentifierBuilder.buildObject();
      clone.setFormat(nameIdentifier.getFormat());
      clone.setNameQualifier(nameIdentifier.getNameQualifier());
      clone.setValue(nameIdentifier.getValue());
      return clone;
   }

   private class AssertionStrategy implements Function {
      private AssertionStrategy() {
      }

      @Nullable
      public List apply(@Nullable ProfileRequestContext input) {
         if (input != null && input.getOutboundMessageContext() != null) {
            Object outboundMessage = input.getOutboundMessageContext().getMessage();
            if (outboundMessage == null) {
               return null;
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
