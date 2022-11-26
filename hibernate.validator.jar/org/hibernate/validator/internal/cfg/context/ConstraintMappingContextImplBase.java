package org.hibernate.validator.internal.cfg.context;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.core.MetaConstraints;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;

abstract class ConstraintMappingContextImplBase extends ConstraintContextImplBase {
   private final Set constraints = CollectionHelper.newHashSet();

   ConstraintMappingContextImplBase(DefaultConstraintMapping mapping) {
      super(mapping);
   }

   protected abstract ConstraintDescriptorImpl.ConstraintType getConstraintType();

   protected DefaultConstraintMapping getConstraintMapping() {
      return this.mapping;
   }

   protected void addConstraint(ConfiguredConstraint constraint) {
      this.constraints.add(constraint);
   }

   protected Set getConstraints(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      if (this.constraints == null) {
         return Collections.emptySet();
      } else {
         Set metaConstraints = CollectionHelper.newHashSet();
         Iterator var5 = this.constraints.iterator();

         while(var5.hasNext()) {
            ConfiguredConstraint configuredConstraint = (ConfiguredConstraint)var5.next();
            metaConstraints.add(this.asMetaConstraint(configuredConstraint, constraintHelper, typeResolutionHelper, valueExtractorManager));
         }

         return metaConstraints;
      }
   }

   private MetaConstraint asMetaConstraint(ConfiguredConstraint config, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      ConstraintDescriptorImpl constraintDescriptor = new ConstraintDescriptorImpl(constraintHelper, config.getLocation().getMember(), config.createAnnotationDescriptor(), config.getElementType(), this.getConstraintType());
      return MetaConstraints.create(typeResolutionHelper, valueExtractorManager, constraintDescriptor, config.getLocation());
   }
}
