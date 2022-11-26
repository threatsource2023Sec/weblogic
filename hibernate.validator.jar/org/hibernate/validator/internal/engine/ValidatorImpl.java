package org.hibernate.validator.internal.engine;

import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.groups.Group;
import org.hibernate.validator.internal.engine.groups.GroupWithInheritance;
import org.hibernate.validator.internal.engine.groups.Sequence;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.resolver.TraversableResolvers;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorHelper;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ContainerCascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ParameterMetaData;
import org.hibernate.validator.internal.metadata.aggregated.PropertyMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ReturnValueMetaData;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.location.FieldConstraintLocation;
import org.hibernate.validator.internal.metadata.location.GetterConstraintLocation;
import org.hibernate.validator.internal.metadata.location.TypeArgumentConstraintLocation;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;

public class ValidatorImpl implements Validator, ExecutableValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final Collection DEFAULT_GROUPS = Collections.singletonList(Default.class);
   private final transient ValidationOrderGenerator validationOrderGenerator;
   private final ConstraintValidatorFactory constraintValidatorFactory;
   private final TraversableResolver traversableResolver;
   private final BeanMetaDataManager beanMetaDataManager;
   private final ConstraintValidatorManager constraintValidatorManager;
   private final ValueExtractorManager valueExtractorManager;
   private final ValidationContext.ValidatorScopedContext validatorScopedContext;
   private final HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext;

   public ValidatorImpl(ConstraintValidatorFactory constraintValidatorFactory, BeanMetaDataManager beanMetaDataManager, ValueExtractorManager valueExtractorManager, ConstraintValidatorManager constraintValidatorManager, ValidationOrderGenerator validationOrderGenerator, ValidatorFactoryImpl.ValidatorFactoryScopedContext validatorFactoryScopedContext) {
      this.constraintValidatorFactory = constraintValidatorFactory;
      this.beanMetaDataManager = beanMetaDataManager;
      this.valueExtractorManager = valueExtractorManager;
      this.constraintValidatorManager = constraintValidatorManager;
      this.validationOrderGenerator = validationOrderGenerator;
      this.validatorScopedContext = new ValidationContext.ValidatorScopedContext(validatorFactoryScopedContext);
      this.traversableResolver = validatorFactoryScopedContext.getTraversableResolver();
      this.constraintValidatorInitializationContext = validatorFactoryScopedContext.getConstraintValidatorInitializationContext();
   }

   public final Set validate(Object object, Class... groups) {
      Contracts.assertNotNull(object, Messages.MESSAGES.validatedObjectMustNotBeNull());
      this.sanityCheckGroups(groups);
      Class rootBeanClass = object.getClass();
      BeanMetaData rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(rootBeanClass);
      if (!rootBeanMetaData.hasConstraints()) {
         return Collections.emptySet();
      } else {
         ValidationContext validationContext = this.getValidationContextBuilder().forValidate(rootBeanMetaData, object);
         ValidationOrder validationOrder = this.determineGroupValidationOrder(groups);
         ValueContext valueContext = ValueContext.getLocalExecutionContext(this.validatorScopedContext.getParameterNameProvider(), object, validationContext.getRootBeanMetaData(), PathImpl.createRootPath());
         return this.validateInContext(validationContext, valueContext, validationOrder);
      }
   }

   public final Set validateProperty(Object object, String propertyName, Class... groups) {
      Contracts.assertNotNull(object, Messages.MESSAGES.validatedObjectMustNotBeNull());
      this.sanityCheckPropertyPath(propertyName);
      this.sanityCheckGroups(groups);
      Class rootBeanClass = object.getClass();
      BeanMetaData rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(rootBeanClass);
      if (!rootBeanMetaData.hasConstraints()) {
         return Collections.emptySet();
      } else {
         PathImpl propertyPath = PathImpl.createPathFromString(propertyName);
         ValidationContext validationContext = this.getValidationContextBuilder().forValidateProperty(rootBeanMetaData, object);
         ValueContext valueContext = this.getValueContextForPropertyValidation(validationContext, propertyPath);
         if (valueContext.getCurrentBean() == null) {
            throw LOG.getUnableToReachPropertyToValidateException(validationContext.getRootBean(), propertyPath);
         } else {
            ValidationOrder validationOrder = this.determineGroupValidationOrder(groups);
            return this.validateInContext(validationContext, valueContext, validationOrder);
         }
      }
   }

   public final Set validateValue(Class beanType, String propertyName, Object value, Class... groups) {
      Contracts.assertNotNull(beanType, Messages.MESSAGES.beanTypeCannotBeNull());
      this.sanityCheckPropertyPath(propertyName);
      this.sanityCheckGroups(groups);
      BeanMetaData rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(beanType);
      if (!rootBeanMetaData.hasConstraints()) {
         return Collections.emptySet();
      } else {
         ValidationContext validationContext = this.getValidationContextBuilder().forValidateValue(rootBeanMetaData);
         ValidationOrder validationOrder = this.determineGroupValidationOrder(groups);
         return this.validateValueInContext(validationContext, value, PathImpl.createPathFromString(propertyName), validationOrder);
      }
   }

   public Set validateParameters(Object object, Method method, Object[] parameterValues, Class... groups) {
      Contracts.assertNotNull(object, Messages.MESSAGES.validatedObjectMustNotBeNull());
      Contracts.assertNotNull(method, Messages.MESSAGES.validatedMethodMustNotBeNull());
      Contracts.assertNotNull(parameterValues, Messages.MESSAGES.validatedParameterArrayMustNotBeNull());
      return this.validateParameters(object, (Executable)method, parameterValues, groups);
   }

   public Set validateConstructorParameters(Constructor constructor, Object[] parameterValues, Class... groups) {
      Contracts.assertNotNull(constructor, Messages.MESSAGES.validatedConstructorMustNotBeNull());
      Contracts.assertNotNull(parameterValues, Messages.MESSAGES.validatedParameterArrayMustNotBeNull());
      return this.validateParameters((Object)null, (Executable)constructor, parameterValues, groups);
   }

   public Set validateConstructorReturnValue(Constructor constructor, Object createdObject, Class... groups) {
      Contracts.assertNotNull(constructor, Messages.MESSAGES.validatedConstructorMustNotBeNull());
      Contracts.assertNotNull(createdObject, Messages.MESSAGES.validatedConstructorCreatedInstanceMustNotBeNull());
      return this.validateReturnValue((Object)null, (Executable)constructor, createdObject, groups);
   }

   public Set validateReturnValue(Object object, Method method, Object returnValue, Class... groups) {
      Contracts.assertNotNull(object, Messages.MESSAGES.validatedObjectMustNotBeNull());
      Contracts.assertNotNull(method, Messages.MESSAGES.validatedMethodMustNotBeNull());
      return this.validateReturnValue(object, (Executable)method, returnValue, groups);
   }

   private Set validateParameters(Object object, Executable executable, Object[] parameterValues, Class... groups) {
      this.sanityCheckGroups(groups);
      Class rootBeanClass = object != null ? object.getClass() : executable.getDeclaringClass();
      BeanMetaData rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(rootBeanClass);
      if (!rootBeanMetaData.hasConstraints()) {
         return Collections.emptySet();
      } else {
         ValidationContext validationContext = this.getValidationContextBuilder().forValidateParameters(this.validatorScopedContext.getParameterNameProvider(), rootBeanMetaData, object, executable, parameterValues);
         ValidationOrder validationOrder = this.determineGroupValidationOrder(groups);
         this.validateParametersInContext(validationContext, parameterValues, validationOrder);
         return validationContext.getFailingConstraints();
      }
   }

   private Set validateReturnValue(Object object, Executable executable, Object returnValue, Class... groups) {
      this.sanityCheckGroups(groups);
      Class rootBeanClass = object != null ? object.getClass() : executable.getDeclaringClass();
      BeanMetaData rootBeanMetaData = this.beanMetaDataManager.getBeanMetaData(rootBeanClass);
      if (!rootBeanMetaData.hasConstraints()) {
         return Collections.emptySet();
      } else {
         ValidationContext validationContext = this.getValidationContextBuilder().forValidateReturnValue(rootBeanMetaData, object, executable, returnValue);
         ValidationOrder validationOrder = this.determineGroupValidationOrder(groups);
         this.validateReturnValueInContext(validationContext, object, returnValue, validationOrder);
         return validationContext.getFailingConstraints();
      }
   }

   public final BeanDescriptor getConstraintsForClass(Class clazz) {
      return this.beanMetaDataManager.getBeanMetaData(clazz).getBeanDescriptor();
   }

   public final Object unwrap(Class type) {
      if (type.isAssignableFrom(Validator.class)) {
         return type.cast(this);
      } else {
         throw LOG.getTypeNotSupportedForUnwrappingException(type);
      }
   }

   public ExecutableValidator forExecutables() {
      return this;
   }

   private ValidationContext.ValidationContextBuilder getValidationContextBuilder() {
      return ValidationContext.getValidationContextBuilder(this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, TraversableResolvers.wrapWithCachingForSingleValidation(this.traversableResolver, this.validatorScopedContext.isTraversableResolverResultCacheEnabled()), this.constraintValidatorInitializationContext);
   }

   private void sanityCheckPropertyPath(String propertyName) {
      if (propertyName == null || propertyName.length() == 0) {
         throw LOG.getInvalidPropertyPathException();
      }
   }

   private void sanityCheckGroups(Class[] groups) {
      Contracts.assertNotNull(groups, Messages.MESSAGES.groupMustNotBeNull());
      Class[] var2 = groups;
      int var3 = groups.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class clazz = var2[var4];
         if (clazz == null) {
            throw new IllegalArgumentException(Messages.MESSAGES.groupMustNotBeNull());
         }
      }

   }

   private ValidationOrder determineGroupValidationOrder(Class[] groups) {
      Object resultGroups;
      if (groups.length == 0) {
         resultGroups = DEFAULT_GROUPS;
      } else {
         resultGroups = Arrays.asList(groups);
      }

      return this.validationOrderGenerator.getValidationOrder((Collection)resultGroups);
   }

   private Set validateInContext(ValidationContext validationContext, ValueContext valueContext, ValidationOrder validationOrder) {
      if (valueContext.getCurrentBean() == null) {
         return Collections.emptySet();
      } else {
         BeanMetaData beanMetaData = valueContext.getCurrentBeanMetaData();
         if (beanMetaData.defaultGroupSequenceIsRedefined()) {
            validationOrder.assertDefaultGroupSequenceIsExpandable(beanMetaData.getDefaultGroupSequence(valueContext.getCurrentBean()));
         }

         Iterator groupIterator = validationOrder.getGroupIterator();

         Group group;
         while(groupIterator.hasNext()) {
            group = (Group)groupIterator.next();
            valueContext.setCurrentGroup(group.getDefiningClass());
            this.validateConstraintsForCurrentGroup(validationContext, valueContext);
            if (this.shouldFailFast(validationContext)) {
               return validationContext.getFailingConstraints();
            }
         }

         groupIterator = validationOrder.getGroupIterator();

         while(groupIterator.hasNext()) {
            group = (Group)groupIterator.next();
            valueContext.setCurrentGroup(group.getDefiningClass());
            this.validateCascadedConstraints(validationContext, valueContext);
            if (this.shouldFailFast(validationContext)) {
               return validationContext.getFailingConstraints();
            }
         }

         Iterator sequenceIterator = validationOrder.getSequenceIterator();

         while(sequenceIterator.hasNext()) {
            Sequence sequence = (Sequence)sequenceIterator.next();
            Iterator var8 = sequence.iterator();

            while(var8.hasNext()) {
               GroupWithInheritance groupOfGroups = (GroupWithInheritance)var8.next();
               int numberOfViolations = validationContext.getFailingConstraints().size();
               Iterator var11 = groupOfGroups.iterator();

               while(var11.hasNext()) {
                  Group group = (Group)var11.next();
                  valueContext.setCurrentGroup(group.getDefiningClass());
                  this.validateConstraintsForCurrentGroup(validationContext, valueContext);
                  if (this.shouldFailFast(validationContext)) {
                     return validationContext.getFailingConstraints();
                  }

                  this.validateCascadedConstraints(validationContext, valueContext);
                  if (this.shouldFailFast(validationContext)) {
                     return validationContext.getFailingConstraints();
                  }
               }

               if (validationContext.getFailingConstraints().size() > numberOfViolations) {
                  break;
               }
            }
         }

         return validationContext.getFailingConstraints();
      }
   }

   private void validateConstraintsForCurrentGroup(ValidationContext validationContext, ValueContext valueContext) {
      if (!valueContext.validatingDefault()) {
         this.validateConstraintsForNonDefaultGroup(validationContext, valueContext);
      } else {
         this.validateConstraintsForDefaultGroup(validationContext, valueContext);
      }

   }

   private void validateConstraintsForDefaultGroup(ValidationContext validationContext, ValueContext valueContext) {
      BeanMetaData beanMetaData = valueContext.getCurrentBeanMetaData();
      Map validatedInterfaces = new HashMap();
      Iterator var5 = beanMetaData.getClassHierarchy().iterator();

      while(var5.hasNext()) {
         Class clazz = (Class)var5.next();
         BeanMetaData hostingBeanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
         boolean defaultGroupSequenceIsRedefined = hostingBeanMetaData.defaultGroupSequenceIsRedefined();
         if (defaultGroupSequenceIsRedefined) {
            Iterator defaultGroupSequence = hostingBeanMetaData.getDefaultValidationSequence(valueContext.getCurrentBean());
            Set metaConstraints = hostingBeanMetaData.getMetaConstraints();

            while(defaultGroupSequence.hasNext()) {
               Iterator var11 = ((Sequence)defaultGroupSequence.next()).iterator();

               while(var11.hasNext()) {
                  GroupWithInheritance groupOfGroups = (GroupWithInheritance)var11.next();
                  boolean validationSuccessful = true;

                  Group defaultSequenceMember;
                  for(Iterator var14 = groupOfGroups.iterator(); var14.hasNext(); validationSuccessful = this.validateConstraintsForSingleDefaultGroupElement(validationContext, valueContext, validatedInterfaces, clazz, metaConstraints, defaultSequenceMember)) {
                     defaultSequenceMember = (Group)var14.next();
                  }

                  validationContext.markCurrentBeanAsProcessed(valueContext);
                  if (!validationSuccessful) {
                     break;
                  }
               }
            }
         } else {
            Set metaConstraints = hostingBeanMetaData.getDirectMetaConstraints();
            this.validateConstraintsForSingleDefaultGroupElement(validationContext, valueContext, validatedInterfaces, clazz, metaConstraints, Group.DEFAULT_GROUP);
            validationContext.markCurrentBeanAsProcessed(valueContext);
         }

         if (defaultGroupSequenceIsRedefined) {
            break;
         }
      }

   }

   private boolean validateConstraintsForSingleDefaultGroupElement(ValidationContext validationContext, ValueContext valueContext, Map validatedInterfaces, Class clazz, Set metaConstraints, Group defaultSequenceMember) {
      boolean validationSuccessful = true;
      valueContext.setCurrentGroup(defaultSequenceMember.getDefiningClass());
      Iterator var8 = metaConstraints.iterator();

      while(true) {
         MetaConstraint metaConstraint;
         while(true) {
            if (!var8.hasNext()) {
               return validationSuccessful;
            }

            metaConstraint = (MetaConstraint)var8.next();
            Class declaringClass = metaConstraint.getLocation().getDeclaringClass();
            if (!declaringClass.isInterface()) {
               break;
            }

            Class validatedForClass = (Class)validatedInterfaces.get(declaringClass);
            if (validatedForClass == null || validatedForClass.equals(clazz)) {
               validatedInterfaces.put(declaringClass, clazz);
               break;
            }
         }

         boolean tmp = this.validateMetaConstraint(validationContext, valueContext, valueContext.getCurrentBean(), metaConstraint);
         if (this.shouldFailFast(validationContext)) {
            return false;
         }

         validationSuccessful = validationSuccessful && tmp;
      }
   }

   private void validateConstraintsForNonDefaultGroup(ValidationContext validationContext, ValueContext valueContext) {
      this.validateMetaConstraints(validationContext, valueContext, valueContext.getCurrentBean(), valueContext.getCurrentBeanMetaData().getMetaConstraints());
      validationContext.markCurrentBeanAsProcessed(valueContext);
   }

   private void validateMetaConstraints(ValidationContext validationContext, ValueContext valueContext, Object parent, Iterable constraints) {
      Iterator var5 = constraints.iterator();

      while(var5.hasNext()) {
         MetaConstraint metaConstraint = (MetaConstraint)var5.next();
         this.validateMetaConstraint(validationContext, valueContext, parent, metaConstraint);
         if (this.shouldFailFast(validationContext)) {
            break;
         }
      }

   }

   private boolean validateMetaConstraint(ValidationContext validationContext, ValueContext valueContext, Object parent, MetaConstraint metaConstraint) {
      ValueContext.ValueState originalValueState = valueContext.getCurrentValueState();
      valueContext.appendNode(metaConstraint.getLocation());
      boolean success = true;
      if (this.isValidationRequired(validationContext, valueContext, metaConstraint)) {
         if (parent != null) {
            valueContext.setCurrentValidatedValue(valueContext.getValue(parent, metaConstraint.getLocation()));
         }

         success = metaConstraint.validateConstraint(validationContext, valueContext);
         validationContext.markConstraintProcessed(valueContext.getCurrentBean(), valueContext.getPropertyPath(), metaConstraint);
      }

      valueContext.resetValueState(originalValueState);
      return success;
   }

   private void validateCascadedConstraints(ValidationContext validationContext, ValueContext valueContext) {
      Validatable validatable = valueContext.getCurrentValidatable();
      ValueContext.ValueState originalValueState = valueContext.getCurrentValueState();

      for(Iterator var5 = validatable.getCascadables().iterator(); var5.hasNext(); valueContext.resetValueState(originalValueState)) {
         Cascadable cascadable = (Cascadable)var5.next();
         valueContext.appendNode(cascadable);
         ElementType elementType = cascadable.getElementType();
         if (this.isCascadeRequired(validationContext, valueContext.getCurrentBean(), valueContext.getPropertyPath(), elementType)) {
            Object value = this.getCascadableValue(validationContext, valueContext.getCurrentBean(), cascadable);
            CascadingMetaData cascadingMetaData = cascadable.getCascadingMetaData();
            if (value != null) {
               CascadingMetaData effectiveCascadingMetaData = cascadingMetaData.addRuntimeContainerSupport(this.valueExtractorManager, value.getClass());
               if (effectiveCascadingMetaData.isCascading()) {
                  this.validateCascadedAnnotatedObjectForCurrentGroup(value, validationContext, valueContext, effectiveCascadingMetaData);
               }

               if (effectiveCascadingMetaData.isContainer()) {
                  ContainerCascadingMetaData containerCascadingMetaData = (ContainerCascadingMetaData)effectiveCascadingMetaData.as(ContainerCascadingMetaData.class);
                  if (containerCascadingMetaData.hasContainerElementsMarkedForCascading()) {
                     this.validateCascadedContainerElementsForCurrentGroup(value, validationContext, valueContext, containerCascadingMetaData.getContainerElementTypesCascadingMetaData());
                  }
               }
            }
         }
      }

   }

   private void validateCascadedAnnotatedObjectForCurrentGroup(Object value, ValidationContext validationContext, ValueContext valueContext, CascadingMetaData cascadingMetaData) {
      if (!validationContext.isBeanAlreadyValidated(value, valueContext.getCurrentGroup(), valueContext.getPropertyPath()) && !this.shouldFailFast(validationContext)) {
         Class originalGroup = valueContext.getCurrentGroup();
         Class currentGroup = cascadingMetaData.convertGroup(originalGroup);
         ValidationOrder validationOrder = this.validationOrderGenerator.getValidationOrder(currentGroup, currentGroup != originalGroup);
         ValueContext cascadedValueContext = this.buildNewLocalExecutionContext(valueContext, value);
         this.validateInContext(validationContext, cascadedValueContext, validationOrder);
      }
   }

   private void validateCascadedContainerElementsForCurrentGroup(Object value, ValidationContext validationContext, ValueContext valueContext, List containerElementTypesCascadingMetaData) {
      Iterator var5 = containerElementTypesCascadingMetaData.iterator();

      while(var5.hasNext()) {
         ContainerCascadingMetaData cascadingMetaData = (ContainerCascadingMetaData)var5.next();
         if (cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements()) {
            ValueExtractorDescriptor extractor = this.valueExtractorManager.getMaximallySpecificAndRuntimeContainerElementCompliantValueExtractor(cascadingMetaData.getEnclosingType(), cascadingMetaData.getTypeParameter(), value.getClass(), cascadingMetaData.getValueExtractorCandidates());
            if (extractor == null) {
               throw LOG.getNoValueExtractorFoundForTypeException(cascadingMetaData.getEnclosingType(), cascadingMetaData.getTypeParameter(), value.getClass());
            }

            CascadingValueReceiver receiver = new CascadingValueReceiver(validationContext, valueContext, cascadingMetaData);
            ValueExtractorHelper.extractValues(extractor, value, receiver);
         }
      }

   }

   private void validateCascadedContainerElementsInContext(Object value, ValidationContext validationContext, ValueContext valueContext, ContainerCascadingMetaData cascadingMetaData, ValidationOrder validationOrder) {
      Iterator groupIterator = validationOrder.getGroupIterator();

      while(groupIterator.hasNext()) {
         Group group = (Group)groupIterator.next();
         valueContext.setCurrentGroup(group.getDefiningClass());
         this.validateCascadedContainerElementsForCurrentGroup(value, validationContext, valueContext, cascadingMetaData.getContainerElementTypesCascadingMetaData());
         if (this.shouldFailFast(validationContext)) {
            return;
         }
      }

      Iterator sequenceIterator = validationOrder.getSequenceIterator();

      while(sequenceIterator.hasNext()) {
         Sequence sequence = (Sequence)sequenceIterator.next();
         Iterator var9 = sequence.iterator();

         while(var9.hasNext()) {
            GroupWithInheritance groupOfGroups = (GroupWithInheritance)var9.next();
            int numberOfViolations = validationContext.getFailingConstraints().size();
            Iterator var12 = groupOfGroups.iterator();

            while(var12.hasNext()) {
               Group group = (Group)var12.next();
               valueContext.setCurrentGroup(group.getDefiningClass());
               this.validateCascadedContainerElementsForCurrentGroup(value, validationContext, valueContext, cascadingMetaData.getContainerElementTypesCascadingMetaData());
               if (this.shouldFailFast(validationContext)) {
                  return;
               }
            }

            if (validationContext.getFailingConstraints().size() > numberOfViolations) {
               break;
            }
         }
      }

   }

   private ValueContext buildNewLocalExecutionContext(ValueContext valueContext, Object value) {
      ValueContext newValueContext;
      if (value != null) {
         newValueContext = ValueContext.getLocalExecutionContext(this.validatorScopedContext.getParameterNameProvider(), value, this.beanMetaDataManager.getBeanMetaData(value.getClass()), valueContext.getPropertyPath());
         newValueContext.setCurrentValidatedValue(value);
      } else {
         newValueContext = ValueContext.getLocalExecutionContext(this.validatorScopedContext.getParameterNameProvider(), valueContext.getCurrentBeanType(), valueContext.getCurrentBeanMetaData(), valueContext.getPropertyPath());
      }

      return newValueContext;
   }

   private Set validateValueInContext(ValidationContext validationContext, Object value, PathImpl propertyPath, ValidationOrder validationOrder) {
      ValueContext valueContext = this.getValueContextForValueValidation(validationContext, propertyPath);
      valueContext.setCurrentValidatedValue(value);
      BeanMetaData beanMetaData = valueContext.getCurrentBeanMetaData();
      if (beanMetaData.defaultGroupSequenceIsRedefined()) {
         validationOrder.assertDefaultGroupSequenceIsExpandable(beanMetaData.getDefaultGroupSequence((Object)null));
      }

      Iterator groupIterator = validationOrder.getGroupIterator();

      while(groupIterator.hasNext()) {
         Group group = (Group)groupIterator.next();
         valueContext.setCurrentGroup(group.getDefiningClass());
         this.validateConstraintsForCurrentGroup(validationContext, valueContext);
         if (this.shouldFailFast(validationContext)) {
            return validationContext.getFailingConstraints();
         }
      }

      Iterator sequenceIterator = validationOrder.getSequenceIterator();

      while(sequenceIterator.hasNext()) {
         Sequence sequence = (Sequence)sequenceIterator.next();
         Iterator var10 = sequence.iterator();

         while(var10.hasNext()) {
            GroupWithInheritance groupOfGroups = (GroupWithInheritance)var10.next();
            int numberOfConstraintViolationsBefore = validationContext.getFailingConstraints().size();
            Iterator var13 = groupOfGroups.iterator();

            while(var13.hasNext()) {
               Group group = (Group)var13.next();
               valueContext.setCurrentGroup(group.getDefiningClass());
               this.validateConstraintsForCurrentGroup(validationContext, valueContext);
               if (this.shouldFailFast(validationContext)) {
                  return validationContext.getFailingConstraints();
               }
            }

            if (validationContext.getFailingConstraints().size() > numberOfConstraintViolationsBefore) {
               break;
            }
         }
      }

      return validationContext.getFailingConstraints();
   }

   private void validateParametersInContext(ValidationContext validationContext, Object[] parameterValues, ValidationOrder validationOrder) {
      BeanMetaData beanMetaData = validationContext.getRootBeanMetaData();
      Optional executableMetaDataOptional = validationContext.getExecutableMetaData();
      if (executableMetaDataOptional.isPresent()) {
         ExecutableMetaData executableMetaData = (ExecutableMetaData)executableMetaDataOptional.get();
         if (parameterValues.length != executableMetaData.getParameterTypes().length) {
            throw LOG.getInvalidParameterCountForExecutableException(ExecutableHelper.getExecutableAsString(executableMetaData.getType().toString() + "#" + executableMetaData.getName(), executableMetaData.getParameterTypes()), executableMetaData.getParameterTypes().length, parameterValues.length);
         } else {
            if (beanMetaData.defaultGroupSequenceIsRedefined()) {
               validationOrder.assertDefaultGroupSequenceIsExpandable(beanMetaData.getDefaultGroupSequence(validationContext.getRootBean()));
            }

            Iterator groupIterator = validationOrder.getGroupIterator();

            while(groupIterator.hasNext()) {
               this.validateParametersForGroup(validationContext, executableMetaData, parameterValues, (Group)groupIterator.next());
               if (this.shouldFailFast(validationContext)) {
                  return;
               }
            }

            ValueContext cascadingValueContext = ValueContext.getLocalExecutionContext(this.beanMetaDataManager, this.validatorScopedContext.getParameterNameProvider(), (Object)parameterValues, executableMetaData.getValidatableParametersMetaData(), PathImpl.createPathForExecutable(executableMetaData));
            groupIterator = validationOrder.getGroupIterator();

            while(groupIterator.hasNext()) {
               Group group = (Group)groupIterator.next();
               cascadingValueContext.setCurrentGroup(group.getDefiningClass());
               this.validateCascadedConstraints(validationContext, cascadingValueContext);
               if (this.shouldFailFast(validationContext)) {
                  return;
               }
            }

            Iterator sequenceIterator = validationOrder.getSequenceIterator();

            while(sequenceIterator.hasNext()) {
               Sequence sequence = (Sequence)sequenceIterator.next();
               Iterator var11 = sequence.iterator();

               while(var11.hasNext()) {
                  GroupWithInheritance groupOfGroups = (GroupWithInheritance)var11.next();
                  int numberOfViolations = validationContext.getFailingConstraints().size();
                  Iterator var14 = groupOfGroups.iterator();

                  while(var14.hasNext()) {
                     Group group = (Group)var14.next();
                     this.validateParametersForGroup(validationContext, executableMetaData, parameterValues, group);
                     if (this.shouldFailFast(validationContext)) {
                        return;
                     }

                     cascadingValueContext.setCurrentGroup(group.getDefiningClass());
                     this.validateCascadedConstraints(validationContext, cascadingValueContext);
                     if (this.shouldFailFast(validationContext)) {
                        return;
                     }
                  }

                  if (validationContext.getFailingConstraints().size() > numberOfViolations) {
                     break;
                  }
               }
            }

         }
      }
   }

   private void validateParametersForGroup(ValidationContext validationContext, ExecutableMetaData executableMetaData, Object[] parameterValues, Group group) {
      Contracts.assertNotNull(executableMetaData, "executableMetaData may not be null");
      if (group.isDefaultGroup()) {
         Iterator defaultGroupSequence = validationContext.getRootBeanMetaData().getDefaultValidationSequence(validationContext.getRootBean());

         while(defaultGroupSequence.hasNext()) {
            Sequence sequence = (Sequence)defaultGroupSequence.next();
            int numberOfViolations = validationContext.getFailingConstraints().size();
            Iterator var8 = sequence.iterator();

            while(var8.hasNext()) {
               GroupWithInheritance expandedGroup = (GroupWithInheritance)var8.next();
               Iterator var10 = expandedGroup.iterator();

               while(var10.hasNext()) {
                  Group defaultGroupSequenceElement = (Group)var10.next();
                  this.validateParametersForSingleGroup(validationContext, parameterValues, executableMetaData, defaultGroupSequenceElement.getDefiningClass());
                  if (this.shouldFailFast(validationContext)) {
                     return;
                  }
               }

               if (validationContext.getFailingConstraints().size() > numberOfViolations) {
                  return;
               }
            }
         }
      } else {
         this.validateParametersForSingleGroup(validationContext, parameterValues, executableMetaData, group.getDefiningClass());
      }

   }

   private void validateParametersForSingleGroup(ValidationContext validationContext, Object[] parameterValues, ExecutableMetaData executableMetaData, Class currentValidatedGroup) {
      ValueContext valueContext;
      if (!executableMetaData.getCrossParameterConstraints().isEmpty()) {
         valueContext = this.getExecutableValueContext(validationContext.getRootBean(), executableMetaData, executableMetaData.getValidatableParametersMetaData(), currentValidatedGroup);
         this.validateMetaConstraints(validationContext, valueContext, parameterValues, executableMetaData.getCrossParameterConstraints());
         if (this.shouldFailFast(validationContext)) {
            return;
         }
      }

      valueContext = this.getExecutableValueContext(validationContext.getRootBean(), executableMetaData, executableMetaData.getValidatableParametersMetaData(), currentValidatedGroup);

      for(int i = 0; i < parameterValues.length; ++i) {
         ParameterMetaData parameterMetaData = executableMetaData.getParameterMetaData(i);
         Object value = parameterValues[i];
         if (value != null) {
            Class valueType = value.getClass();
            if (parameterMetaData.getType() instanceof Class && ((Class)parameterMetaData.getType()).isPrimitive()) {
               valueType = ReflectionHelper.unBoxedType(valueType);
            }

            if (!TypeHelper.isAssignable(TypeHelper.getErasedType(parameterMetaData.getType()), valueType)) {
               throw LOG.getParameterTypesDoNotMatchException(valueType, parameterMetaData.getType(), i, validationContext.getExecutable());
            }
         }

         this.validateMetaConstraints(validationContext, valueContext, parameterValues, parameterMetaData);
         if (this.shouldFailFast(validationContext)) {
            return;
         }
      }

   }

   private ValueContext getExecutableValueContext(Object object, ExecutableMetaData executableMetaData, Validatable validatable, Class group) {
      ValueContext valueContext;
      if (object != null) {
         valueContext = ValueContext.getLocalExecutionContext(this.beanMetaDataManager, this.validatorScopedContext.getParameterNameProvider(), object, validatable, PathImpl.createPathForExecutable(executableMetaData));
      } else {
         valueContext = ValueContext.getLocalExecutionContext(this.beanMetaDataManager, this.validatorScopedContext.getParameterNameProvider(), (Class)null, validatable, PathImpl.createPathForExecutable(executableMetaData));
      }

      valueContext.setCurrentGroup(group);
      return valueContext;
   }

   private void validateReturnValueInContext(ValidationContext validationContext, Object bean, Object value, ValidationOrder validationOrder) {
      BeanMetaData beanMetaData = validationContext.getRootBeanMetaData();
      Optional executableMetaDataOptional = validationContext.getExecutableMetaData();
      if (executableMetaDataOptional.isPresent()) {
         ExecutableMetaData executableMetaData = (ExecutableMetaData)executableMetaDataOptional.get();
         if (beanMetaData.defaultGroupSequenceIsRedefined()) {
            validationOrder.assertDefaultGroupSequenceIsExpandable(beanMetaData.getDefaultGroupSequence(bean));
         }

         Iterator groupIterator = validationOrder.getGroupIterator();

         while(groupIterator.hasNext()) {
            this.validateReturnValueForGroup(validationContext, executableMetaData, bean, value, (Group)groupIterator.next());
            if (this.shouldFailFast(validationContext)) {
               return;
            }
         }

         ValueContext cascadingValueContext = null;
         if (value != null) {
            cascadingValueContext = ValueContext.getLocalExecutionContext(this.beanMetaDataManager, this.validatorScopedContext.getParameterNameProvider(), (Object)value, executableMetaData.getReturnValueMetaData(), PathImpl.createPathForExecutable(executableMetaData));
            groupIterator = validationOrder.getGroupIterator();

            while(groupIterator.hasNext()) {
               Group group = (Group)groupIterator.next();
               cascadingValueContext.setCurrentGroup(group.getDefiningClass());
               this.validateCascadedConstraints(validationContext, cascadingValueContext);
               if (this.shouldFailFast(validationContext)) {
                  return;
               }
            }
         }

         Iterator sequenceIterator = validationOrder.getSequenceIterator();

         while(sequenceIterator.hasNext()) {
            Sequence sequence = (Sequence)sequenceIterator.next();
            Iterator var12 = sequence.iterator();

            while(var12.hasNext()) {
               GroupWithInheritance groupOfGroups = (GroupWithInheritance)var12.next();
               int numberOfFailingConstraintsBeforeGroup = validationContext.getFailingConstraints().size();
               Iterator var15 = groupOfGroups.iterator();

               while(var15.hasNext()) {
                  Group group = (Group)var15.next();
                  this.validateReturnValueForGroup(validationContext, executableMetaData, bean, value, group);
                  if (this.shouldFailFast(validationContext)) {
                     return;
                  }

                  if (value != null) {
                     cascadingValueContext.setCurrentGroup(group.getDefiningClass());
                     this.validateCascadedConstraints(validationContext, cascadingValueContext);
                     if (this.shouldFailFast(validationContext)) {
                        return;
                     }
                  }
               }

               if (validationContext.getFailingConstraints().size() > numberOfFailingConstraintsBeforeGroup) {
                  break;
               }
            }
         }

      }
   }

   private void validateReturnValueForGroup(ValidationContext validationContext, ExecutableMetaData executableMetaData, Object bean, Object value, Group group) {
      Contracts.assertNotNull(executableMetaData, "executableMetaData may not be null");
      if (group.isDefaultGroup()) {
         Iterator defaultGroupSequence = validationContext.getRootBeanMetaData().getDefaultValidationSequence(bean);

         while(defaultGroupSequence.hasNext()) {
            Sequence sequence = (Sequence)defaultGroupSequence.next();
            int numberOfViolations = validationContext.getFailingConstraints().size();
            Iterator var9 = sequence.iterator();

            while(var9.hasNext()) {
               GroupWithInheritance expandedGroup = (GroupWithInheritance)var9.next();
               Iterator var11 = expandedGroup.iterator();

               while(var11.hasNext()) {
                  Group defaultGroupSequenceElement = (Group)var11.next();
                  this.validateReturnValueForSingleGroup(validationContext, executableMetaData, bean, value, defaultGroupSequenceElement.getDefiningClass());
                  if (this.shouldFailFast(validationContext)) {
                     return;
                  }
               }

               if (validationContext.getFailingConstraints().size() > numberOfViolations) {
                  return;
               }
            }
         }
      } else {
         this.validateReturnValueForSingleGroup(validationContext, executableMetaData, bean, value, group.getDefiningClass());
      }

   }

   private void validateReturnValueForSingleGroup(ValidationContext validationContext, ExecutableMetaData executableMetaData, Object bean, Object value, Class oneGroup) {
      ValueContext valueContext = this.getExecutableValueContext(executableMetaData.getKind() == ElementKind.CONSTRUCTOR ? value : bean, executableMetaData, executableMetaData.getReturnValueMetaData(), oneGroup);
      ReturnValueMetaData returnValueMetaData = executableMetaData.getReturnValueMetaData();
      this.validateMetaConstraints(validationContext, valueContext, value, returnValueMetaData);
   }

   private ValueContext getValueContextForPropertyValidation(ValidationContext validationContext, PathImpl propertyPath) {
      Class clazz = validationContext.getRootBeanClass();
      BeanMetaData beanMetaData = validationContext.getRootBeanMetaData();
      Object value = validationContext.getRootBean();
      PropertyMetaData propertyMetaData = null;
      Iterator propertyPathIter = propertyPath.iterator();

      while(propertyPathIter.hasNext()) {
         NodeImpl propertyPathNode = (NodeImpl)propertyPathIter.next();
         propertyMetaData = this.getBeanPropertyMetaData(beanMetaData, propertyPathNode);
         if (propertyPathIter.hasNext()) {
            if (!propertyMetaData.isCascading()) {
               throw LOG.getInvalidPropertyPathException(validationContext.getRootBeanClass(), propertyPath.asString());
            }

            value = this.getCascadableValue(validationContext, value, (Cascadable)propertyMetaData.getCascadables().iterator().next());
            if (value == null) {
               throw LOG.getUnableToReachPropertyToValidateException(validationContext.getRootBean(), propertyPath);
            }

            clazz = value.getClass();
            if (propertyPathNode.isIterable()) {
               propertyPathNode = (NodeImpl)propertyPathIter.next();
               if (propertyPathNode.getIndex() != null) {
                  value = ReflectionHelper.getIndexedValue(value, propertyPathNode.getIndex());
               } else {
                  if (propertyPathNode.getKey() == null) {
                     throw LOG.getPropertyPathMustProvideIndexOrMapKeyException();
                  }

                  value = ReflectionHelper.getMappedValue(value, propertyPathNode.getKey());
               }

               if (value == null) {
                  throw LOG.getUnableToReachPropertyToValidateException(validationContext.getRootBean(), propertyPath);
               }

               clazz = value.getClass();
               beanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
               propertyMetaData = this.getBeanPropertyMetaData(beanMetaData, propertyPathNode);
            } else {
               beanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
            }
         }
      }

      if (propertyMetaData == null) {
         throw LOG.getInvalidPropertyPathException(clazz, propertyPath.asString());
      } else {
         validationContext.setValidatedProperty(propertyMetaData.getName());
         propertyPath.removeLeafNode();
         return ValueContext.getLocalExecutionContext(this.validatorScopedContext.getParameterNameProvider(), value, beanMetaData, propertyPath);
      }
   }

   private ValueContext getValueContextForValueValidation(ValidationContext validationContext, PathImpl propertyPath) {
      Class clazz = validationContext.getRootBeanClass();
      BeanMetaData beanMetaData = null;
      PropertyMetaData propertyMetaData = null;
      Iterator propertyPathIter = propertyPath.iterator();

      while(propertyPathIter.hasNext()) {
         NodeImpl propertyPathNode = (NodeImpl)propertyPathIter.next();
         beanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
         propertyMetaData = this.getBeanPropertyMetaData(beanMetaData, propertyPathNode);
         if (propertyPathIter.hasNext()) {
            if (propertyPathNode.isIterable()) {
               propertyPathNode = (NodeImpl)propertyPathIter.next();
               clazz = ReflectionHelper.getClassFromType(ReflectionHelper.getCollectionElementType(propertyMetaData.getType()));
               beanMetaData = this.beanMetaDataManager.getBeanMetaData(clazz);
               propertyMetaData = this.getBeanPropertyMetaData(beanMetaData, propertyPathNode);
            } else {
               clazz = ReflectionHelper.getClassFromType(propertyMetaData.getType());
            }
         }
      }

      if (propertyMetaData == null) {
         throw LOG.getInvalidPropertyPathException(clazz, propertyPath.asString());
      } else {
         validationContext.setValidatedProperty(propertyMetaData.getName());
         propertyPath.removeLeafNode();
         return ValueContext.getLocalExecutionContext(this.validatorScopedContext.getParameterNameProvider(), clazz, beanMetaData, propertyPath);
      }
   }

   private boolean isValidationRequired(ValidationContext validationContext, ValueContext valueContext, MetaConstraint metaConstraint) {
      if (validationContext.getValidatedProperty() != null && !Objects.equals(validationContext.getValidatedProperty(), this.getPropertyName(metaConstraint.getLocation()))) {
         return false;
      } else if (validationContext.hasMetaConstraintBeenProcessed(valueContext.getCurrentBean(), valueContext.getPropertyPath(), metaConstraint)) {
         return false;
      } else {
         return !metaConstraint.getGroupList().contains(valueContext.getCurrentGroup()) ? false : this.isReachable(validationContext, valueContext.getCurrentBean(), valueContext.getPropertyPath(), metaConstraint.getElementType());
      }
   }

   private boolean isReachable(ValidationContext validationContext, Object traversableObject, PathImpl path, ElementType type) {
      if (this.needToCallTraversableResolver(path, type)) {
         return true;
      } else {
         Path pathToObject = path.getPathWithoutLeafNode();

         try {
            return validationContext.getTraversableResolver().isReachable(traversableObject, path.getLeafNode(), validationContext.getRootBeanClass(), pathToObject, type);
         } catch (RuntimeException var7) {
            throw LOG.getErrorDuringCallOfTraversableResolverIsReachableException(var7);
         }
      }
   }

   private boolean needToCallTraversableResolver(PathImpl path, ElementType type) {
      return this.isClassLevelConstraint(type) || this.isCrossParameterValidation(path) || this.isParameterValidation(path) || this.isReturnValueValidation(path);
   }

   private boolean isCascadeRequired(ValidationContext validationContext, Object traversableObject, PathImpl path, ElementType type) {
      if (this.needToCallTraversableResolver(path, type)) {
         return true;
      } else {
         boolean isReachable = this.isReachable(validationContext, traversableObject, path, type);
         if (!isReachable) {
            return false;
         } else {
            Path pathToObject = path.getPathWithoutLeafNode();

            try {
               return validationContext.getTraversableResolver().isCascadable(traversableObject, path.getLeafNode(), validationContext.getRootBeanClass(), pathToObject, type);
            } catch (RuntimeException var8) {
               throw LOG.getErrorDuringCallOfTraversableResolverIsCascadableException(var8);
            }
         }
      }
   }

   private boolean isClassLevelConstraint(ElementType type) {
      return ElementType.TYPE.equals(type);
   }

   private boolean isCrossParameterValidation(PathImpl path) {
      return path.getLeafNode().getKind() == ElementKind.CROSS_PARAMETER;
   }

   private boolean isParameterValidation(PathImpl path) {
      return path.getLeafNode().getKind() == ElementKind.PARAMETER;
   }

   private boolean isReturnValueValidation(PathImpl path) {
      return path.getLeafNode().getKind() == ElementKind.RETURN_VALUE;
   }

   private boolean shouldFailFast(ValidationContext validationContext) {
      return validationContext.isFailFastModeEnabled() && !validationContext.getFailingConstraints().isEmpty();
   }

   private PropertyMetaData getBeanPropertyMetaData(BeanMetaData beanMetaData, Path.Node propertyNode) {
      if (!ElementKind.PROPERTY.equals(propertyNode.getKind())) {
         throw LOG.getInvalidPropertyPathException(beanMetaData.getBeanClass(), propertyNode.getName());
      } else {
         return beanMetaData.getMetaDataFor(propertyNode.getName());
      }
   }

   private Object getCascadableValue(ValidationContext validationContext, Object object, Cascadable cascadable) {
      return cascadable.getValue(object);
   }

   private String getPropertyName(ConstraintLocation location) {
      if (location instanceof TypeArgumentConstraintLocation) {
         location = ((TypeArgumentConstraintLocation)location).getOuterDelegate();
      }

      if (location instanceof FieldConstraintLocation) {
         return ((FieldConstraintLocation)location).getPropertyName();
      } else {
         return location instanceof GetterConstraintLocation ? ((GetterConstraintLocation)location).getPropertyName() : null;
      }
   }

   private class CascadingValueReceiver implements ValueExtractor.ValueReceiver {
      private final ValidationContext validationContext;
      private final ValueContext valueContext;
      private final ContainerCascadingMetaData cascadingMetaData;

      public CascadingValueReceiver(ValidationContext validationContext, ValueContext valueContext, ContainerCascadingMetaData cascadingMetaData) {
         this.validationContext = validationContext;
         this.valueContext = valueContext;
         this.cascadingMetaData = cascadingMetaData;
      }

      public void value(String nodeName, Object value) {
         this.doValidate(value, nodeName);
      }

      public void iterableValue(String nodeName, Object value) {
         this.valueContext.markCurrentPropertyAsIterable();
         this.doValidate(value, nodeName);
      }

      public void indexedValue(String nodeName, int index, Object value) {
         this.valueContext.markCurrentPropertyAsIterableAndSetIndex(index);
         this.doValidate(value, nodeName);
      }

      public void keyedValue(String nodeName, Object key, Object value) {
         this.valueContext.markCurrentPropertyAsIterableAndSetKey(key);
         this.doValidate(value, nodeName);
      }

      private void doValidate(Object value, String nodeName) {
         if (value != null && !this.validationContext.isBeanAlreadyValidated(value, this.valueContext.getCurrentGroup(), this.valueContext.getPropertyPath()) && !ValidatorImpl.this.shouldFailFast(this.validationContext)) {
            Class originalGroup = this.valueContext.getCurrentGroup();
            Class currentGroup = this.cascadingMetaData.convertGroup(originalGroup);
            ValidationOrder validationOrder = ValidatorImpl.this.validationOrderGenerator.getValidationOrder(currentGroup, currentGroup != originalGroup);
            ValueContext cascadedValueContext = ValidatorImpl.this.buildNewLocalExecutionContext(this.valueContext, value);
            if (this.cascadingMetaData.getDeclaredContainerClass() != null) {
               cascadedValueContext.setTypeParameter(this.cascadingMetaData.getDeclaredContainerClass(), this.cascadingMetaData.getDeclaredTypeParameterIndex());
            }

            if (this.cascadingMetaData.isCascading()) {
               ValidatorImpl.this.validateInContext(this.validationContext, cascadedValueContext, validationOrder);
            }

            if (this.cascadingMetaData.hasContainerElementsMarkedForCascading()) {
               ValueContext cascadedTypeArgumentValueContext = ValidatorImpl.this.buildNewLocalExecutionContext(this.valueContext, value);
               if (this.cascadingMetaData.getTypeParameter() != null) {
                  cascadedValueContext.setTypeParameter(this.cascadingMetaData.getDeclaredContainerClass(), this.cascadingMetaData.getDeclaredTypeParameterIndex());
               }

               if (nodeName != null) {
                  cascadedTypeArgumentValueContext.appendTypeParameterNode(nodeName);
               }

               ValidatorImpl.this.validateCascadedContainerElementsInContext(value, this.validationContext, cascadedTypeArgumentValueContext, this.cascadingMetaData, validationOrder);
            }

         }
      }
   }
}
