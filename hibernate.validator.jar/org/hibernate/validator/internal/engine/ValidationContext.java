package org.hibernate.validator.internal.engine;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.time.Duration;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public class ValidationContext {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final ValidationOperation validationOperation;
   private final ConstraintValidatorManager constraintValidatorManager;
   private final Object rootBean;
   private final Class rootBeanClass;
   private final BeanMetaData rootBeanMetaData;
   private final Executable executable;
   private final Object[] executableParameters;
   private final Object executableReturnValue;
   private final Optional executableMetaData;
   private final Set processedPathUnits;
   private final Set processedGroupUnits;
   private final Map processedPathsPerBean;
   private final Set failingConstraintViolations;
   private final ConstraintValidatorFactory constraintValidatorFactory;
   private final ValidatorScopedContext validatorScopedContext;
   private final TraversableResolver traversableResolver;
   private final HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext;
   private final boolean disableAlreadyValidatedBeanTracking;
   private String validatedProperty;

   private ValidationContext(ValidationOperation validationOperation, ConstraintValidatorManager constraintValidatorManager, ConstraintValidatorFactory constraintValidatorFactory, ValidatorScopedContext validatorScopedContext, TraversableResolver traversableResolver, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext, Object rootBean, Class rootBeanClass, BeanMetaData rootBeanMetaData, Executable executable, Object[] executableParameters, Object executableReturnValue, Optional executableMetaData) {
      this.validationOperation = validationOperation;
      this.constraintValidatorManager = constraintValidatorManager;
      this.validatorScopedContext = validatorScopedContext;
      this.constraintValidatorFactory = constraintValidatorFactory;
      this.traversableResolver = traversableResolver;
      this.constraintValidatorInitializationContext = constraintValidatorInitializationContext;
      this.rootBean = rootBean;
      this.rootBeanClass = rootBeanClass;
      this.rootBeanMetaData = rootBeanMetaData;
      this.executable = executable;
      this.executableParameters = executableParameters;
      this.executableReturnValue = executableReturnValue;
      this.processedGroupUnits = new HashSet();
      this.processedPathUnits = new HashSet();
      this.processedPathsPerBean = new IdentityHashMap();
      this.failingConstraintViolations = CollectionHelper.newHashSet();
      this.executableMetaData = executableMetaData;
      this.disableAlreadyValidatedBeanTracking = buildDisableAlreadyValidatedBeanTracking(validationOperation, rootBeanMetaData, executableMetaData);
   }

   public static ValidationContextBuilder getValidationContextBuilder(ConstraintValidatorManager constraintValidatorManager, ConstraintValidatorFactory constraintValidatorFactory, ValidatorScopedContext validatorScopedContext, TraversableResolver traversableResolver, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext) {
      return new ValidationContextBuilder(constraintValidatorManager, constraintValidatorFactory, validatorScopedContext, traversableResolver, constraintValidatorInitializationContext);
   }

   public Object getRootBean() {
      return this.rootBean;
   }

   public Class getRootBeanClass() {
      return this.rootBeanClass;
   }

   public BeanMetaData getRootBeanMetaData() {
      return this.rootBeanMetaData;
   }

   public Executable getExecutable() {
      return this.executable;
   }

   public Optional getExecutableMetaData() {
      return this.executableMetaData;
   }

   public TraversableResolver getTraversableResolver() {
      return this.traversableResolver;
   }

   public boolean isFailFastModeEnabled() {
      return this.validatorScopedContext.isFailFast();
   }

   public ConstraintValidatorManager getConstraintValidatorManager() {
      return this.constraintValidatorManager;
   }

   public List getParameterNames() {
      return !ValidationContext.ValidationOperation.PARAMETER_VALIDATION.equals(this.validationOperation) ? null : this.validatorScopedContext.getParameterNameProvider().getParameterNames(this.executable);
   }

   public ClockProvider getClockProvider() {
      return this.validatorScopedContext.getClockProvider();
   }

   public Object getConstraintValidatorPayload() {
      return this.validatorScopedContext.getConstraintValidatorPayload();
   }

   public HibernateConstraintValidatorInitializationContext getConstraintValidatorInitializationContext() {
      return this.constraintValidatorInitializationContext;
   }

   public Set createConstraintViolations(ValueContext localContext, ConstraintValidatorContextImpl constraintValidatorContext) {
      return (Set)constraintValidatorContext.getConstraintViolationCreationContexts().stream().map((c) -> {
         return this.createConstraintViolation(localContext, c, constraintValidatorContext.getConstraintDescriptor());
      }).collect(Collectors.toSet());
   }

   public ConstraintValidatorFactory getConstraintValidatorFactory() {
      return this.constraintValidatorFactory;
   }

   public boolean isBeanAlreadyValidated(Object value, Class group, PathImpl path) {
      if (this.disableAlreadyValidatedBeanTracking) {
         return false;
      } else {
         boolean alreadyValidated = this.isAlreadyValidatedForCurrentGroup(value, group);
         if (alreadyValidated) {
            alreadyValidated = this.isAlreadyValidatedForPath(value, path);
         }

         return alreadyValidated;
      }
   }

   public void markCurrentBeanAsProcessed(ValueContext valueContext) {
      if (!this.disableAlreadyValidatedBeanTracking) {
         this.markCurrentBeanAsProcessedForCurrentGroup(valueContext.getCurrentBean(), valueContext.getCurrentGroup());
         this.markCurrentBeanAsProcessedForCurrentPath(valueContext.getCurrentBean(), valueContext.getPropertyPath());
      }
   }

   public void addConstraintFailures(Set failingConstraintViolations) {
      this.failingConstraintViolations.addAll(failingConstraintViolations);
   }

   public Set getFailingConstraints() {
      return this.failingConstraintViolations;
   }

   public ConstraintViolation createConstraintViolation(ValueContext localContext, ConstraintViolationCreationContext constraintViolationCreationContext, ConstraintDescriptor descriptor) {
      String messageTemplate = constraintViolationCreationContext.getMessage();
      String interpolatedMessage = this.interpolate(messageTemplate, localContext.getCurrentValidatedValue(), descriptor, constraintViolationCreationContext.getMessageParameters(), constraintViolationCreationContext.getExpressionVariables());
      Path path = PathImpl.createCopy(constraintViolationCreationContext.getPath());
      Object dynamicPayload = constraintViolationCreationContext.getDynamicPayload();
      switch (this.validationOperation) {
         case PARAMETER_VALIDATION:
            return ConstraintViolationImpl.forParameterValidation(messageTemplate, constraintViolationCreationContext.getMessageParameters(), constraintViolationCreationContext.getExpressionVariables(), interpolatedMessage, this.getRootBeanClass(), this.getRootBean(), localContext.getCurrentBean(), localContext.getCurrentValidatedValue(), path, descriptor, localContext.getElementType(), this.executableParameters, dynamicPayload);
         case RETURN_VALUE_VALIDATION:
            return ConstraintViolationImpl.forReturnValueValidation(messageTemplate, constraintViolationCreationContext.getMessageParameters(), constraintViolationCreationContext.getExpressionVariables(), interpolatedMessage, this.getRootBeanClass(), this.getRootBean(), localContext.getCurrentBean(), localContext.getCurrentValidatedValue(), path, descriptor, localContext.getElementType(), this.executableReturnValue, dynamicPayload);
         default:
            return ConstraintViolationImpl.forBeanValidation(messageTemplate, constraintViolationCreationContext.getMessageParameters(), constraintViolationCreationContext.getExpressionVariables(), interpolatedMessage, this.getRootBeanClass(), this.getRootBean(), localContext.getCurrentBean(), localContext.getCurrentValidatedValue(), path, descriptor, localContext.getElementType(), dynamicPayload);
      }
   }

   public boolean hasMetaConstraintBeenProcessed(Object bean, Path path, MetaConstraint metaConstraint) {
      return metaConstraint.isDefinedForOneGroupOnly() ? false : this.processedPathUnits.contains(new BeanPathMetaConstraintProcessedUnit(bean, path, metaConstraint));
   }

   public void markConstraintProcessed(Object bean, Path path, MetaConstraint metaConstraint) {
      if (!metaConstraint.isDefinedForOneGroupOnly()) {
         this.processedPathUnits.add(new BeanPathMetaConstraintProcessedUnit(bean, path, metaConstraint));
      }
   }

   public String getValidatedProperty() {
      return this.validatedProperty;
   }

   public void setValidatedProperty(String validatedProperty) {
      this.validatedProperty = validatedProperty;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ValidationContext");
      sb.append("{rootBean=").append(this.rootBean);
      sb.append('}');
      return sb.toString();
   }

   private static boolean buildDisableAlreadyValidatedBeanTracking(ValidationOperation validationOperation, BeanMetaData rootBeanMetaData, Optional executableMetaData) {
      Object validatable;
      switch (validationOperation) {
         case PARAMETER_VALIDATION:
            if (!executableMetaData.isPresent()) {
               return false;
            }

            validatable = ((ExecutableMetaData)executableMetaData.get()).getValidatableParametersMetaData();
            break;
         case RETURN_VALUE_VALIDATION:
            if (!executableMetaData.isPresent()) {
               return false;
            }

            validatable = ((ExecutableMetaData)executableMetaData.get()).getReturnValueMetaData();
            break;
         case BEAN_VALIDATION:
         case PROPERTY_VALIDATION:
         case VALUE_VALIDATION:
            validatable = rootBeanMetaData;
            break;
         default:
            return false;
      }

      return !((Validatable)validatable).hasCascadables();
   }

   private String interpolate(String messageTemplate, Object validatedValue, ConstraintDescriptor descriptor, Map messageParameters, Map expressionVariables) {
      MessageInterpolatorContext context = new MessageInterpolatorContext(descriptor, validatedValue, this.getRootBeanClass(), messageParameters, expressionVariables);

      try {
         return this.validatorScopedContext.getMessageInterpolator().interpolate(messageTemplate, context);
      } catch (ValidationException var8) {
         throw var8;
      } catch (Exception var9) {
         throw LOG.getExceptionOccurredDuringMessageInterpolationException(var9);
      }
   }

   private boolean isAlreadyValidatedForPath(Object value, PathImpl path) {
      Set pathSet = (Set)this.processedPathsPerBean.get(value);
      if (pathSet == null) {
         return false;
      } else {
         Iterator var4 = pathSet.iterator();

         PathImpl p;
         do {
            if (!var4.hasNext()) {
               return false;
            }

            p = (PathImpl)var4.next();
         } while(!path.isRootPath() && !p.isRootPath() && !this.isSubPathOf(path, p) && !this.isSubPathOf(p, path));

         return true;
      }
   }

   private boolean isSubPathOf(Path p1, Path p2) {
      Iterator p1Iter = p1.iterator();
      Iterator p2Iter = p2.iterator();

      Path.Node p1Node;
      Path.Node p2Node;
      do {
         if (!p1Iter.hasNext()) {
            return true;
         }

         p1Node = (Path.Node)p1Iter.next();
         if (!p2Iter.hasNext()) {
            return false;
         }

         p2Node = (Path.Node)p2Iter.next();
      } while(p1Node.equals(p2Node));

      return false;
   }

   private boolean isAlreadyValidatedForCurrentGroup(Object value, Class group) {
      return this.processedGroupUnits.contains(new BeanGroupProcessedUnit(value, group));
   }

   private void markCurrentBeanAsProcessedForCurrentPath(Object bean, PathImpl path) {
      ((Set)this.processedPathsPerBean.computeIfAbsent(bean, (b) -> {
         return new HashSet();
      })).add(PathImpl.createCopy(path));
   }

   private void markCurrentBeanAsProcessedForCurrentGroup(Object bean, Class group) {
      this.processedGroupUnits.add(new BeanGroupProcessedUnit(bean, group));
   }

   // $FF: synthetic method
   ValidationContext(ValidationOperation x0, ConstraintValidatorManager x1, ConstraintValidatorFactory x2, ValidatorScopedContext x3, TraversableResolver x4, HibernateConstraintValidatorInitializationContext x5, Object x6, Class x7, BeanMetaData x8, Executable x9, Object[] x10, Object x11, Optional x12, Object x13) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12);
   }

   private static enum ValidationOperation {
      BEAN_VALIDATION,
      PROPERTY_VALIDATION,
      VALUE_VALIDATION,
      PARAMETER_VALIDATION,
      RETURN_VALUE_VALIDATION;
   }

   static class ValidatorScopedContext {
      private final MessageInterpolator messageInterpolator;
      private final ExecutableParameterNameProvider parameterNameProvider;
      private final ClockProvider clockProvider;
      private final Duration temporalValidationTolerance;
      private final ScriptEvaluatorFactory scriptEvaluatorFactory;
      private final boolean failFast;
      private final boolean traversableResolverResultCacheEnabled;
      private final Object constraintValidatorPayload;

      ValidatorScopedContext(ValidatorFactoryImpl.ValidatorFactoryScopedContext validatorFactoryScopedContext) {
         this.messageInterpolator = validatorFactoryScopedContext.getMessageInterpolator();
         this.parameterNameProvider = validatorFactoryScopedContext.getParameterNameProvider();
         this.clockProvider = validatorFactoryScopedContext.getClockProvider();
         this.temporalValidationTolerance = validatorFactoryScopedContext.getTemporalValidationTolerance();
         this.scriptEvaluatorFactory = validatorFactoryScopedContext.getScriptEvaluatorFactory();
         this.failFast = validatorFactoryScopedContext.isFailFast();
         this.traversableResolverResultCacheEnabled = validatorFactoryScopedContext.isTraversableResolverResultCacheEnabled();
         this.constraintValidatorPayload = validatorFactoryScopedContext.getConstraintValidatorPayload();
      }

      public MessageInterpolator getMessageInterpolator() {
         return this.messageInterpolator;
      }

      public ExecutableParameterNameProvider getParameterNameProvider() {
         return this.parameterNameProvider;
      }

      public ClockProvider getClockProvider() {
         return this.clockProvider;
      }

      public Duration getTemporalValidationTolerance() {
         return this.temporalValidationTolerance;
      }

      public ScriptEvaluatorFactory getScriptEvaluatorFactory() {
         return this.scriptEvaluatorFactory;
      }

      public boolean isFailFast() {
         return this.failFast;
      }

      public boolean isTraversableResolverResultCacheEnabled() {
         return this.traversableResolverResultCacheEnabled;
      }

      public Object getConstraintValidatorPayload() {
         return this.constraintValidatorPayload;
      }
   }

   private static final class BeanPathMetaConstraintProcessedUnit {
      private Object bean;
      private Path path;
      private MetaConstraint metaConstraint;
      private int hashCode;

      private BeanPathMetaConstraintProcessedUnit(Object bean, Path path, MetaConstraint metaConstraint) {
         this.bean = bean;
         this.path = path;
         this.metaConstraint = metaConstraint;
         this.hashCode = this.createHashCode();
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else {
            BeanPathMetaConstraintProcessedUnit that = (BeanPathMetaConstraintProcessedUnit)o;
            if (this.bean != that.bean) {
               return false;
            } else if (this.metaConstraint != that.metaConstraint) {
               return false;
            } else {
               return this.path.equals(that.path);
            }
         }
      }

      public int hashCode() {
         return this.hashCode;
      }

      private int createHashCode() {
         int result = System.identityHashCode(this.bean);
         result = 31 * result + this.path.hashCode();
         result = 31 * result + System.identityHashCode(this.metaConstraint);
         return result;
      }

      // $FF: synthetic method
      BeanPathMetaConstraintProcessedUnit(Object x0, Path x1, MetaConstraint x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static final class BeanGroupProcessedUnit {
      private Object bean;
      private Class group;
      private int hashCode;

      private BeanGroupProcessedUnit(Object bean, Class group) {
         this.bean = bean;
         this.group = group;
         this.hashCode = this.createHashCode();
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else {
            BeanGroupProcessedUnit that = (BeanGroupProcessedUnit)o;
            if (this.bean != that.bean) {
               return false;
            } else {
               return this.group.equals(that.group);
            }
         }
      }

      public int hashCode() {
         return this.hashCode;
      }

      private int createHashCode() {
         int result = System.identityHashCode(this.bean);
         result = 31 * result + this.group.hashCode();
         return result;
      }

      // $FF: synthetic method
      BeanGroupProcessedUnit(Object x0, Class x1, Object x2) {
         this(x0, x1);
      }
   }

   public static class ValidationContextBuilder {
      private final ConstraintValidatorManager constraintValidatorManager;
      private final ConstraintValidatorFactory constraintValidatorFactory;
      private final TraversableResolver traversableResolver;
      private final HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext;
      private final ValidatorScopedContext validatorScopedContext;

      private ValidationContextBuilder(ConstraintValidatorManager constraintValidatorManager, ConstraintValidatorFactory constraintValidatorFactory, ValidatorScopedContext validatorScopedContext, TraversableResolver traversableResolver, HibernateConstraintValidatorInitializationContext constraintValidatorInitializationContext) {
         this.constraintValidatorManager = constraintValidatorManager;
         this.constraintValidatorFactory = constraintValidatorFactory;
         this.traversableResolver = traversableResolver;
         this.constraintValidatorInitializationContext = constraintValidatorInitializationContext;
         this.validatorScopedContext = validatorScopedContext;
      }

      public ValidationContext forValidate(BeanMetaData rootBeanMetaData, Object rootBean) {
         return new ValidationContext(ValidationContext.ValidationOperation.BEAN_VALIDATION, this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, rootBean, rootBeanMetaData.getBeanClass(), rootBeanMetaData, (Executable)null, (Object[])null, (Object)null, (Optional)null);
      }

      public ValidationContext forValidateProperty(BeanMetaData rootBeanMetaData, Object rootBean) {
         return new ValidationContext(ValidationContext.ValidationOperation.PROPERTY_VALIDATION, this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, rootBean, rootBeanMetaData.getBeanClass(), rootBeanMetaData, (Executable)null, (Object[])null, (Object)null, (Optional)null);
      }

      public ValidationContext forValidateValue(BeanMetaData rootBeanMetaData) {
         return new ValidationContext(ValidationContext.ValidationOperation.VALUE_VALIDATION, this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, (Object)null, rootBeanMetaData.getBeanClass(), rootBeanMetaData, (Executable)null, (Object[])null, (Object)null, (Optional)null);
      }

      public ValidationContext forValidateParameters(ExecutableParameterNameProvider parameterNameProvider, BeanMetaData rootBeanMetaData, Object rootBean, Executable executable, Object[] executableParameters) {
         return new ValidationContext(ValidationContext.ValidationOperation.PARAMETER_VALIDATION, this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, rootBean, rootBeanMetaData.getBeanClass(), rootBeanMetaData, executable, executableParameters, (Object)null, rootBeanMetaData.getMetaDataFor(executable));
      }

      public ValidationContext forValidateReturnValue(BeanMetaData rootBeanMetaData, Object rootBean, Executable executable, Object executableReturnValue) {
         return new ValidationContext(ValidationContext.ValidationOperation.RETURN_VALUE_VALIDATION, this.constraintValidatorManager, this.constraintValidatorFactory, this.validatorScopedContext, this.traversableResolver, this.constraintValidatorInitializationContext, rootBean, rootBeanMetaData.getBeanClass(), rootBeanMetaData, executable, (Object[])null, executableReturnValue, rootBeanMetaData.getMetaDataFor(executable));
      }

      // $FF: synthetic method
      ValidationContextBuilder(ConstraintValidatorManager x0, ConstraintValidatorFactory x1, ValidatorScopedContext x2, TraversableResolver x3, HibernateConstraintValidatorInitializationContext x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
