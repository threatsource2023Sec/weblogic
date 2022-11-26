package org.opensaml.saml.common.profile;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObject;

public abstract class AbstractNameIdentifierGenerator extends AbstractIdentifiableInitializableComponent implements FormatSpecificNameIdentifierGenerator, Predicate {
   @Nonnull
   private Predicate activationCondition = Predicates.alwaysTrue();
   @Nullable
   private Function defaultIdPNameQualifierLookupStrategy;
   @Nullable
   private Function defaultSPNameQualifierLookupStrategy;
   private boolean omitQualifiers;
   @NonnullAfterInit
   @NotEmpty
   private String format;
   @Nullable
   private String idpNameQualifier;
   @Nullable
   private String spNameQualifier;
   @Nullable
   private String spProvidedId;

   protected AbstractNameIdentifierGenerator() {
   }

   public void setActivationCondition(@Nonnull Predicate condition) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.activationCondition = (Predicate)Constraint.isNotNull(condition, "Predicate cannot be null");
   }

   @Nullable
   public Function getDefaultIdPNameQualifierLookupStrategy() {
      return this.defaultIdPNameQualifierLookupStrategy;
   }

   public void setDefaultIdPNameQualifierLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.defaultIdPNameQualifierLookupStrategy = strategy;
   }

   @Nullable
   public Function getDefaultSPNameQualifierLookupStrategy() {
      return this.defaultSPNameQualifierLookupStrategy;
   }

   public void setDefaultSPNameQualifierLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.defaultSPNameQualifierLookupStrategy = strategy;
   }

   public boolean isOmitQualifiers() {
      return this.omitQualifiers;
   }

   public void setOmitQualifiers(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.omitQualifiers = flag;
   }

   @NonnullAfterInit
   @NotEmpty
   public String getFormat() {
      return this.format;
   }

   public void setFormat(@Nonnull @NotEmpty String f) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.format = (String)Constraint.isNotNull(StringSupport.trimOrNull(f), "Format cannot be null or empty");
   }

   @Nullable
   public String getIdPNameQualifier() {
      return this.idpNameQualifier;
   }

   public void setIdPNameQualifier(@Nullable String qualifier) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.idpNameQualifier = StringSupport.trimOrNull(qualifier);
   }

   @Nullable
   public String getSPNameQualifier() {
      return this.spNameQualifier;
   }

   public void setSPNameQualifier(@Nullable String qualifier) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.spNameQualifier = StringSupport.trimOrNull(qualifier);
   }

   @Nullable
   public String getSPProvidedID() {
      return this.spProvidedId;
   }

   public void setSPProvidedId(@Nullable String id) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.spProvidedId = id;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.format == null) {
         throw new ComponentInitializationException("Format value cannot be null or empty");
      }
   }

   public boolean apply(@Nullable ProfileRequestContext input) {
      return this.activationCondition.apply(input);
   }

   @Nullable
   public SAMLObject generate(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull @NotEmpty String theFormat) throws SAMLException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      if (!Objects.equals(this.format, theFormat)) {
         throw new SAMLException("The format to generate does not match the value configured");
      } else {
         return !this.apply(profileRequestContext) ? null : this.doGenerate(profileRequestContext);
      }
   }

   @Nullable
   protected abstract SAMLObject doGenerate(@Nonnull ProfileRequestContext var1) throws SAMLException;

   @Nullable
   protected String getIdentifier(@Nonnull ProfileRequestContext profileRequestContext) throws SAMLException {
      return null;
   }

   @Nullable
   protected String getEffectiveIdPNameQualifier(@Nonnull ProfileRequestContext profileRequestContext) {
      if (this.idpNameQualifier != null) {
         if (this.omitQualifiers) {
            return this.defaultIdPNameQualifierLookupStrategy != null && Objects.equals(this.idpNameQualifier, this.defaultIdPNameQualifierLookupStrategy.apply(profileRequestContext)) ? null : this.idpNameQualifier;
         } else {
            return this.idpNameQualifier;
         }
      } else {
         return !this.omitQualifiers && this.defaultIdPNameQualifierLookupStrategy != null ? (String)this.defaultIdPNameQualifierLookupStrategy.apply(profileRequestContext) : null;
      }
   }

   @Nullable
   protected String getEffectiveSPNameQualifier(@Nonnull ProfileRequestContext profileRequestContext) {
      if (this.spNameQualifier != null) {
         if (this.omitQualifiers) {
            return this.defaultSPNameQualifierLookupStrategy != null && Objects.equals(this.spNameQualifier, this.defaultSPNameQualifierLookupStrategy.apply(profileRequestContext)) ? null : this.spNameQualifier;
         } else {
            return this.spNameQualifier;
         }
      } else {
         return !this.omitQualifiers && this.defaultSPNameQualifierLookupStrategy != null ? (String)this.defaultSPNameQualifierLookupStrategy.apply(profileRequestContext) : null;
      }
   }
}
