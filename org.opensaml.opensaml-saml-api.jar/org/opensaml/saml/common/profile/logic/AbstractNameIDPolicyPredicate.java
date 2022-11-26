package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNameIDPolicyPredicate extends AbstractInitializableComponent implements Predicate {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractNameIDPolicyPredicate.class);
   @Nullable
   private Function requesterIdLookupStrategy;
   @Nullable
   private Function responderIdLookupStrategy;
   @NonnullAfterInit
   private Function objectLookupStrategy;
   @Nonnull
   @NonnullElements
   private Set formats = new HashSet(Arrays.asList("urn:oasis:names:tc:SAML:2.0:nameid-format:transient", "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent"));

   public void setRequesterIdLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.requesterIdLookupStrategy = strategy;
   }

   public void setResponderIdLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.responderIdLookupStrategy = strategy;
   }

   public void setObjectLookupStrategy(@Nullable Function strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.objectLookupStrategy = (Function)Constraint.isNotNull(strategy, "Object lookup strategy cannot be null");
   }

   public void setFormats(@Nonnull @NonnullElements Collection newFormats) {
      Constraint.isNotNull(this.formats, "Format collection cannot be null");
      Iterator var2 = newFormats.iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         String trimmed = StringSupport.trimOrNull(s);
         if (trimmed != null) {
            this.formats.add(trimmed);
         }
      }

   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.objectLookupStrategy == null) {
         throw new ComponentInitializationException("Object lookup strategy cannot be null");
      }
   }

   public boolean apply(@Nullable ProfileRequestContext input) {
      SAMLObject target = (SAMLObject)this.objectLookupStrategy.apply(input);
      if (target == null) {
         this.log.debug("No object to operate on, returning true");
         return true;
      } else if (target instanceof NameIdentifier) {
         return this.doApply(input, (NameIdentifier)target);
      } else if (target instanceof NameID) {
         return this.doApply(input, (NameID)target);
      } else if (target instanceof NameIDPolicy) {
         return this.doApply(input, (NameIDPolicy)target);
      } else {
         this.log.error("Lookup function returned an object of an unsupported type: {}", target.getElementQName());
         return false;
      }
   }

   private boolean doApply(@Nullable ProfileRequestContext input, @Nonnull NameIdentifier target) {
      String requesterId = this.requesterIdLookupStrategy != null ? (String)this.requesterIdLookupStrategy.apply(input) : null;
      String responderId = this.responderIdLookupStrategy != null ? (String)this.responderIdLookupStrategy.apply(input) : null;
      String format = target.getFormat();
      if (this.formats.contains(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")) {
         this.log.debug("Applying policy to NameIdentifier with Format {}", format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
         return this.doApply(requesterId, responderId, format, target.getNameQualifier(), (String)null);
      } else {
         this.log.debug("Policy checking disabled for NameIdentifier Format {}", format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
         return true;
      }
   }

   private boolean doApply(@Nullable ProfileRequestContext input, @Nonnull NameID target) {
      String requesterId = this.requesterIdLookupStrategy != null ? (String)this.requesterIdLookupStrategy.apply(input) : null;
      String responderId = this.responderIdLookupStrategy != null ? (String)this.responderIdLookupStrategy.apply(input) : null;
      String format = target.getFormat();
      if (this.formats.contains(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")) {
         this.log.debug("Applying policy to NameID with Format {}", format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
         return this.doApply(requesterId, responderId, format, target.getNameQualifier(), target.getSPNameQualifier());
      } else {
         this.log.debug("Policy checking disabled for NameID Format {}", format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
         return true;
      }
   }

   private boolean doApply(@Nullable ProfileRequestContext input, @Nonnull NameIDPolicy target) {
      String requesterId = this.requesterIdLookupStrategy != null ? (String)this.requesterIdLookupStrategy.apply(input) : null;
      String responderId = this.responderIdLookupStrategy != null ? (String)this.responderIdLookupStrategy.apply(input) : null;
      String format = target.getFormat();
      if (this.formats.contains(format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified")) {
         this.log.debug("Applying policy to NameIDPolicy with Format {}", format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
         return this.doApply(requesterId, responderId, format, (String)null, target.getSPNameQualifier());
      } else {
         this.log.debug("Policy checking disabled for NameIDPolicy with Format {}", format != null ? format : "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
         return true;
      }
   }

   protected abstract boolean doApply(@Nullable String var1, @Nullable String var2, @Nullable String var3, @Nullable String var4, @Nullable String var5);
}
