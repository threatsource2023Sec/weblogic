package org.hibernate.validator.internal.cfg.context;

import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.Set;
import javax.validation.Constraint;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;
import org.hibernate.validator.cfg.context.TypeConstraintMappingContext;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;

public class DefaultConstraintMapping implements ConstraintMapping {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final AnnotationProcessingOptionsImpl annotationProcessingOptions = new AnnotationProcessingOptionsImpl();
   private final Set configuredTypes = CollectionHelper.newHashSet();
   private final Set typeContexts = CollectionHelper.newHashSet();
   private final Set definedConstraints = CollectionHelper.newHashSet();
   private final Set constraintContexts = CollectionHelper.newHashSet();

   public final TypeConstraintMappingContext type(Class type) {
      Contracts.assertNotNull(type, Messages.MESSAGES.beanTypeMustNotBeNull());
      if (this.configuredTypes.contains(type)) {
         throw LOG.getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException(type);
      } else {
         TypeConstraintMappingContextImpl typeContext = new TypeConstraintMappingContextImpl(this, type);
         this.typeContexts.add(typeContext);
         this.configuredTypes.add(type);
         return typeContext;
      }
   }

   public final AnnotationProcessingOptionsImpl getAnnotationProcessingOptions() {
      return this.annotationProcessingOptions;
   }

   public Set getConfiguredTypes() {
      return this.configuredTypes;
   }

   public Set getBeanConfigurations(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      Set configurations = CollectionHelper.newHashSet();
      Iterator var5 = this.typeContexts.iterator();

      while(var5.hasNext()) {
         TypeConstraintMappingContextImpl typeContext = (TypeConstraintMappingContextImpl)var5.next();
         configurations.add(typeContext.build(constraintHelper, typeResolutionHelper, valueExtractorManager));
      }

      return configurations;
   }

   public ConstraintDefinitionContext constraintDefinition(Class annotationClass) {
      Contracts.assertNotNull(annotationClass, Messages.MESSAGES.annotationTypeMustNotBeNull());
      Contracts.assertTrue(annotationClass.isAnnotationPresent(Constraint.class), Messages.MESSAGES.annotationTypeMustBeAnnotatedWithConstraint());
      if (this.definedConstraints.contains(annotationClass)) {
         throw LOG.getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException(annotationClass);
      } else {
         ConstraintDefinitionContextImpl constraintContext = new ConstraintDefinitionContextImpl(this, annotationClass);
         this.constraintContexts.add(constraintContext);
         this.definedConstraints.add(annotationClass);
         return constraintContext;
      }
   }

   public Set getConstraintDefinitionContributions() {
      Set contributions = CollectionHelper.newHashSet();
      Iterator var2 = this.constraintContexts.iterator();

      while(var2.hasNext()) {
         ConstraintDefinitionContextImpl constraintContext = (ConstraintDefinitionContextImpl)var2.next();
         contributions.add(constraintContext.build());
      }

      return contributions;
   }
}
