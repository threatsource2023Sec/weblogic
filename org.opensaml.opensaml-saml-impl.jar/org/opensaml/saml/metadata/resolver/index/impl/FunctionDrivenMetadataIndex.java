package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.Function;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class FunctionDrivenMetadataIndex implements MetadataIndex {
   @Nonnull
   private Function criteriaStrategy;
   @Nonnull
   private Function descriptorStrategy;

   public FunctionDrivenMetadataIndex(@Nonnull Function descriptorFunction, @Nonnull Function criteriaFunction) {
      this.descriptorStrategy = (Function)Constraint.isNotNull(descriptorFunction, "EntityDescriptor strategy function was null");
      this.criteriaStrategy = (Function)Constraint.isNotNull(criteriaFunction, "CriteriaSet strategy function was null");
   }

   @Nullable
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set generateKeys(@Nonnull CriteriaSet criteriaSet) {
      Constraint.isNotNull(criteriaSet, "CriteriaSet was null");
      return (Set)this.criteriaStrategy.apply(criteriaSet);
   }

   @Nullable
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set generateKeys(@Nonnull EntityDescriptor descriptor) {
      Constraint.isNotNull(descriptor, "EntityDescriptor was null");
      return (Set)this.descriptorStrategy.apply(descriptor);
   }
}
