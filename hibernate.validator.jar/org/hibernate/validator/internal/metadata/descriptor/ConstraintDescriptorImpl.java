package org.hibernate.validator.internal.metadata.descriptor;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.OverridesAttribute;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ValidateUnwrappedValue;
import javax.validation.valueextraction.Unwrapping;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorDescriptor;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.core.ConstraintOrigin;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttributes;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethods;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;

public class ConstraintDescriptorImpl implements ConstraintDescriptor, Serializable {
   private static final long serialVersionUID = -2563102960314069246L;
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final int OVERRIDES_PARAMETER_DEFAULT_INDEX = -1;
   private static final List NON_COMPOSING_CONSTRAINT_ANNOTATIONS = Arrays.asList(Documented.class.getName(), Retention.class.getName(), Target.class.getName(), Constraint.class.getName(), ReportAsSingleViolation.class.getName(), Repeatable.class.getName(), Deprecated.class.getName());
   private final ConstraintAnnotationDescriptor annotationDescriptor;
   private final List constraintValidatorClasses;
   private final transient List matchingConstraintValidatorDescriptors;
   private final Set groups;
   private final Set payloads;
   private final Set composingConstraints;
   private final boolean isReportAsSingleInvalidConstraint;
   private final ElementType elementType;
   private final ConstraintOrigin definedOn;
   private final ConstraintType constraintType;
   private final ValidateUnwrappedValue valueUnwrapping;
   private final ConstraintTarget validationAppliesTo;
   private final CompositionType compositionType;
   private final int hashCode;

   public ConstraintDescriptorImpl(ConstraintHelper constraintHelper, Member member, ConstraintAnnotationDescriptor annotationDescriptor, ElementType type, Class implicitGroup, ConstraintOrigin definedOn, ConstraintType externalConstraintType) {
      this.annotationDescriptor = annotationDescriptor;
      this.elementType = type;
      this.definedOn = definedOn;
      this.isReportAsSingleInvalidConstraint = annotationDescriptor.getType().isAnnotationPresent(ReportAsSingleViolation.class);
      this.groups = buildGroupSet(annotationDescriptor, implicitGroup);
      this.payloads = buildPayloadSet(annotationDescriptor);
      this.valueUnwrapping = determineValueUnwrapping(this.payloads, member, annotationDescriptor.getType());
      this.validationAppliesTo = determineValidationAppliesTo(annotationDescriptor);
      this.constraintValidatorClasses = (List)constraintHelper.getAllValidatorDescriptors(annotationDescriptor.getType()).stream().map(ConstraintValidatorDescriptor::getValidatorClass).collect(Collectors.collectingAndThen(Collectors.toList(), CollectionHelper::toImmutableList));
      List crossParameterValidatorDescriptors = CollectionHelper.toImmutableList(constraintHelper.findValidatorDescriptors(annotationDescriptor.getType(), ValidationTarget.PARAMETERS));
      List genericValidatorDescriptors = CollectionHelper.toImmutableList(constraintHelper.findValidatorDescriptors(annotationDescriptor.getType(), ValidationTarget.ANNOTATED_ELEMENT));
      if (crossParameterValidatorDescriptors.size() > 1) {
         throw LOG.getMultipleCrossParameterValidatorClassesException(annotationDescriptor.getType());
      } else {
         this.constraintType = this.determineConstraintType(annotationDescriptor.getType(), member, type, !genericValidatorDescriptors.isEmpty(), !crossParameterValidatorDescriptors.isEmpty(), externalConstraintType);
         this.composingConstraints = this.parseComposingConstraints(constraintHelper, member, this.constraintType);
         this.compositionType = this.parseCompositionType(constraintHelper);
         this.validateComposingConstraintTypes();
         if (this.constraintType == ConstraintDescriptorImpl.ConstraintType.GENERIC) {
            this.matchingConstraintValidatorDescriptors = CollectionHelper.toImmutableList(genericValidatorDescriptors);
         } else {
            this.matchingConstraintValidatorDescriptors = CollectionHelper.toImmutableList(crossParameterValidatorDescriptors);
         }

         this.hashCode = annotationDescriptor.hashCode();
      }
   }

   public ConstraintDescriptorImpl(ConstraintHelper constraintHelper, Member member, ConstraintAnnotationDescriptor annotationDescriptor, ElementType type) {
      this(constraintHelper, member, annotationDescriptor, type, (Class)null, ConstraintOrigin.DEFINED_LOCALLY, (ConstraintType)null);
   }

   public ConstraintDescriptorImpl(ConstraintHelper constraintHelper, Member member, ConstraintAnnotationDescriptor annotationDescriptor, ElementType type, ConstraintType constraintType) {
      this(constraintHelper, member, annotationDescriptor, type, (Class)null, ConstraintOrigin.DEFINED_LOCALLY, constraintType);
   }

   public ConstraintAnnotationDescriptor getAnnotationDescriptor() {
      return this.annotationDescriptor;
   }

   public Annotation getAnnotation() {
      return this.annotationDescriptor.getAnnotation();
   }

   public Class getAnnotationType() {
      return this.annotationDescriptor.getType();
   }

   public String getMessageTemplate() {
      return this.annotationDescriptor.getMessage();
   }

   public Set getGroups() {
      return this.groups;
   }

   public Set getPayload() {
      return this.payloads;
   }

   public ConstraintTarget getValidationAppliesTo() {
      return this.validationAppliesTo;
   }

   public ValidateUnwrappedValue getValueUnwrapping() {
      return this.valueUnwrapping;
   }

   public List getConstraintValidatorClasses() {
      return this.constraintValidatorClasses;
   }

   public List getMatchingConstraintValidatorDescriptors() {
      return this.matchingConstraintValidatorDescriptors;
   }

   public Map getAttributes() {
      return this.annotationDescriptor.getAttributes();
   }

   public Set getComposingConstraints() {
      return (Set)this.composingConstraints;
   }

   public Set getComposingConstraintImpls() {
      return this.composingConstraints;
   }

   public boolean isReportAsSingleViolation() {
      return this.isReportAsSingleInvalidConstraint;
   }

   public ElementType getElementType() {
      return this.elementType;
   }

   public ConstraintOrigin getDefinedOn() {
      return this.definedOn;
   }

   public ConstraintType getConstraintType() {
      return this.constraintType;
   }

   public Object unwrap(Class type) {
      throw LOG.getUnwrappingOfConstraintDescriptorNotSupportedYetException();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ConstraintDescriptorImpl that = (ConstraintDescriptorImpl)o;
         if (this.annotationDescriptor != null) {
            if (!this.annotationDescriptor.equals(that.annotationDescriptor)) {
               return false;
            }
         } else if (that.annotationDescriptor != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ConstraintDescriptorImpl");
      sb.append("{annotation=").append(StringHelper.toShortString((Type)this.annotationDescriptor.getType()));
      sb.append(", payloads=").append(this.payloads);
      sb.append(", hasComposingConstraints=").append(this.composingConstraints.isEmpty());
      sb.append(", isReportAsSingleInvalidConstraint=").append(this.isReportAsSingleInvalidConstraint);
      sb.append(", elementType=").append(this.elementType);
      sb.append(", definedOn=").append(this.definedOn);
      sb.append(", groups=").append(this.groups);
      sb.append(", attributes=").append(this.annotationDescriptor.getAttributes());
      sb.append(", constraintType=").append(this.constraintType);
      sb.append(", valueUnwrapping=").append(this.valueUnwrapping);
      sb.append('}');
      return sb.toString();
   }

   private ConstraintType determineConstraintType(Class constraintAnnotationType, Member member, ElementType elementType, boolean hasGenericValidators, boolean hasCrossParameterValidator, ConstraintType externalConstraintType) {
      ConstraintTarget constraintTarget = this.validationAppliesTo;
      ConstraintType constraintType = null;
      boolean isExecutable = this.isExecutable(elementType);
      if (constraintTarget == ConstraintTarget.RETURN_VALUE) {
         if (!isExecutable) {
            throw LOG.getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException(this.annotationDescriptor.getType(), ConstraintTarget.RETURN_VALUE);
         }

         constraintType = ConstraintDescriptorImpl.ConstraintType.GENERIC;
      } else if (constraintTarget == ConstraintTarget.PARAMETERS) {
         if (!isExecutable) {
            throw LOG.getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException(this.annotationDescriptor.getType(), ConstraintTarget.PARAMETERS);
         }

         constraintType = ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER;
      } else if (externalConstraintType != null) {
         constraintType = externalConstraintType;
      } else if (hasGenericValidators && !hasCrossParameterValidator) {
         constraintType = ConstraintDescriptorImpl.ConstraintType.GENERIC;
      } else if (!hasGenericValidators && hasCrossParameterValidator) {
         constraintType = ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER;
      } else if (!isExecutable) {
         constraintType = ConstraintDescriptorImpl.ConstraintType.GENERIC;
      } else if (constraintAnnotationType.isAnnotationPresent(SupportedValidationTarget.class)) {
         SupportedValidationTarget supportedValidationTarget = (SupportedValidationTarget)constraintAnnotationType.getAnnotation(SupportedValidationTarget.class);
         if (supportedValidationTarget.value().length == 1) {
            constraintType = supportedValidationTarget.value()[0] == ValidationTarget.ANNOTATED_ELEMENT ? ConstraintDescriptorImpl.ConstraintType.GENERIC : ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER;
         }
      } else {
         boolean hasParameters = this.hasParameters(member);
         boolean hasReturnValue = this.hasReturnValue(member);
         if (!hasParameters && hasReturnValue) {
            constraintType = ConstraintDescriptorImpl.ConstraintType.GENERIC;
         } else if (hasParameters && !hasReturnValue) {
            constraintType = ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER;
         }
      }

      if (constraintType == null) {
         throw LOG.getImplicitConstraintTargetInAmbiguousConfigurationException(this.annotationDescriptor.getType());
      } else {
         if (constraintType == ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER) {
            this.validateCrossParameterConstraintType(member, hasCrossParameterValidator);
         }

         return constraintType;
      }
   }

   private static ValidateUnwrappedValue determineValueUnwrapping(Set payloads, Member member, Class annotationType) {
      if (payloads.contains(Unwrapping.Unwrap.class)) {
         if (payloads.contains(Unwrapping.Skip.class)) {
            throw LOG.getInvalidUnwrappingConfigurationForConstraintException(member, annotationType);
         } else {
            return ValidateUnwrappedValue.UNWRAP;
         }
      } else {
         return payloads.contains(Unwrapping.Skip.class) ? ValidateUnwrappedValue.SKIP : ValidateUnwrappedValue.DEFAULT;
      }
   }

   private static ConstraintTarget determineValidationAppliesTo(ConstraintAnnotationDescriptor annotationDescriptor) {
      return annotationDescriptor.getValidationAppliesTo();
   }

   private void validateCrossParameterConstraintType(Member member, boolean hasCrossParameterValidator) {
      if (!hasCrossParameterValidator) {
         throw LOG.getCrossParameterConstraintHasNoValidatorException(this.annotationDescriptor.getType());
      } else if (member == null) {
         throw LOG.getCrossParameterConstraintOnClassException(this.annotationDescriptor.getType());
      } else if (member instanceof Field) {
         throw LOG.getCrossParameterConstraintOnFieldException(this.annotationDescriptor.getType(), member);
      } else if (!this.hasParameters(member)) {
         throw LOG.getCrossParameterConstraintOnMethodWithoutParametersException(this.annotationDescriptor.getType(), (Executable)member);
      }
   }

   private void validateComposingConstraintTypes() {
      Iterator var1 = this.getComposingConstraintImpls().iterator();

      ConstraintDescriptorImpl composingConstraint;
      do {
         if (!var1.hasNext()) {
            return;
         }

         composingConstraint = (ConstraintDescriptorImpl)var1.next();
      } while(composingConstraint.constraintType == this.constraintType);

      throw LOG.getComposedAndComposingConstraintsHaveDifferentTypesException(this.annotationDescriptor.getType(), composingConstraint.annotationDescriptor.getType(), this.constraintType, composingConstraint.constraintType);
   }

   private boolean hasParameters(Member member) {
      boolean hasParameters = false;
      if (member instanceof Constructor) {
         Constructor constructor = (Constructor)member;
         hasParameters = constructor.getParameterTypes().length > 0;
      } else if (member instanceof Method) {
         Method method = (Method)member;
         hasParameters = method.getParameterTypes().length > 0;
      }

      return hasParameters;
   }

   private boolean hasReturnValue(Member member) {
      boolean hasReturnValue;
      if (member instanceof Constructor) {
         hasReturnValue = true;
      } else if (member instanceof Method) {
         Method method = (Method)member;
         hasReturnValue = method.getGenericReturnType() != Void.TYPE;
      } else {
         hasReturnValue = false;
      }

      return hasReturnValue;
   }

   private boolean isExecutable(ElementType elementType) {
      return elementType == ElementType.METHOD || elementType == ElementType.CONSTRUCTOR;
   }

   private static Set buildPayloadSet(ConstraintAnnotationDescriptor annotationDescriptor) {
      Set payloadSet = CollectionHelper.newHashSet();
      Class[] payloadFromAnnotation = annotationDescriptor.getPayload();
      if (payloadFromAnnotation != null) {
         payloadSet.addAll(Arrays.asList(payloadFromAnnotation));
      }

      return CollectionHelper.toImmutableSet(payloadSet);
   }

   private static Set buildGroupSet(ConstraintAnnotationDescriptor annotationDescriptor, Class implicitGroup) {
      Set groupSet = CollectionHelper.newHashSet();
      Class[] groupsFromAnnotation = annotationDescriptor.getGroups();
      if (groupsFromAnnotation.length == 0) {
         groupSet.add(Default.class);
      } else {
         groupSet.addAll(Arrays.asList(groupsFromAnnotation));
      }

      if (implicitGroup != null && groupSet.contains(Default.class)) {
         groupSet.add(implicitGroup);
      }

      return CollectionHelper.toImmutableSet(groupSet);
   }

   private Map parseOverrideParameters() {
      Map overrideParameters = CollectionHelper.newHashMap();
      Method[] methods = (Method[])run(GetDeclaredMethods.action(this.annotationDescriptor.getType()));
      Method[] var3 = methods;
      int var4 = methods.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (m.getAnnotation(OverridesAttribute.class) != null) {
            this.addOverrideAttributes(overrideParameters, m, (OverridesAttribute)m.getAnnotation(OverridesAttribute.class));
         } else if (m.getAnnotation(OverridesAttribute.List.class) != null) {
            this.addOverrideAttributes(overrideParameters, m, ((OverridesAttribute.List)m.getAnnotation(OverridesAttribute.List.class)).value());
         }
      }

      return overrideParameters;
   }

   private void addOverrideAttributes(Map overrideParameters, Method m, OverridesAttribute... attributes) {
      Object value = this.annotationDescriptor.getAttribute(m.getName());
      OverridesAttribute[] var5 = attributes;
      int var6 = attributes.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         OverridesAttribute overridesAttribute = var5[var7];
         String overridesAttributeName = overridesAttribute.name().length() > 0 ? overridesAttribute.name() : m.getName();
         this.ensureAttributeIsOverridable(m, overridesAttribute, overridesAttributeName);
         ClassIndexWrapper wrapper = new ClassIndexWrapper(overridesAttribute.constraint(), overridesAttribute.constraintIndex());
         Map map = (Map)overrideParameters.get(wrapper);
         if (map == null) {
            map = CollectionHelper.newHashMap();
            overrideParameters.put(wrapper, map);
         }

         ((Map)map).put(overridesAttributeName, value);
      }

   }

   private void ensureAttributeIsOverridable(Method m, OverridesAttribute overridesAttribute, String overridesAttributeName) {
      Method method = (Method)run(GetMethod.action(overridesAttribute.constraint(), overridesAttributeName));
      if (method == null) {
         throw LOG.getOverriddenConstraintAttributeNotFoundException(overridesAttributeName);
      } else {
         Class returnTypeOfOverriddenConstraint = method.getReturnType();
         if (!returnTypeOfOverriddenConstraint.equals(m.getReturnType())) {
            throw LOG.getWrongAttributeTypeForOverriddenConstraintException(returnTypeOfOverriddenConstraint, m.getReturnType());
         }
      }
   }

   private Set parseComposingConstraints(ConstraintHelper constraintHelper, Member member, ConstraintType constraintType) {
      Set composingConstraintsSet = CollectionHelper.newHashSet();
      Map overrideParameters = this.parseOverrideParameters();
      Map composingConstraintLocations = new HashMap();
      Annotation[] var7 = this.annotationDescriptor.getType().getDeclaredAnnotations();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Annotation declaredAnnotation = var7[var9];
         Class declaredAnnotationType = declaredAnnotation.annotationType();
         if (!NON_COMPOSING_CONSTRAINT_ANNOTATIONS.contains(declaredAnnotationType.getName())) {
            if (constraintHelper.isConstraintAnnotation(declaredAnnotationType)) {
               if (composingConstraintLocations.containsKey(declaredAnnotationType) && !ConstraintDescriptorImpl.ComposingConstraintAnnotationLocation.DIRECT.equals(composingConstraintLocations.get(declaredAnnotationType))) {
                  throw LOG.getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException(this.annotationDescriptor.getType(), declaredAnnotationType);
               }

               ConstraintDescriptorImpl descriptor = this.createComposingConstraintDescriptor(constraintHelper, member, overrideParameters, -1, declaredAnnotation, constraintType);
               composingConstraintsSet.add(descriptor);
               composingConstraintLocations.put(declaredAnnotationType, ConstraintDescriptorImpl.ComposingConstraintAnnotationLocation.DIRECT);
               LOG.debugf("Adding composing constraint: %s.", descriptor);
            } else if (constraintHelper.isMultiValueConstraint(declaredAnnotationType)) {
               List multiValueConstraints = constraintHelper.getConstraintsFromMultiValueConstraint(declaredAnnotation);
               int index = 0;

               for(Iterator var14 = multiValueConstraints.iterator(); var14.hasNext(); ++index) {
                  Annotation constraintAnnotation = (Annotation)var14.next();
                  if (composingConstraintLocations.containsKey(constraintAnnotation.annotationType()) && !ConstraintDescriptorImpl.ComposingConstraintAnnotationLocation.IN_CONTAINER.equals(composingConstraintLocations.get(constraintAnnotation.annotationType()))) {
                     throw LOG.getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException(this.annotationDescriptor.getType(), constraintAnnotation.annotationType());
                  }

                  ConstraintDescriptorImpl descriptor = this.createComposingConstraintDescriptor(constraintHelper, member, overrideParameters, index, constraintAnnotation, constraintType);
                  composingConstraintsSet.add(descriptor);
                  composingConstraintLocations.put(constraintAnnotation.annotationType(), ConstraintDescriptorImpl.ComposingConstraintAnnotationLocation.IN_CONTAINER);
                  LOG.debugf("Adding composing constraint: %s.", descriptor);
               }
            }
         }
      }

      return CollectionHelper.toImmutableSet(composingConstraintsSet);
   }

   private CompositionType parseCompositionType(ConstraintHelper constraintHelper) {
      Annotation[] var2 = this.annotationDescriptor.getType().getDeclaredAnnotations();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation declaredAnnotation = var2[var4];
         Class declaredAnnotationType = declaredAnnotation.annotationType();
         if (!NON_COMPOSING_CONSTRAINT_ANNOTATIONS.contains(declaredAnnotationType.getName()) && constraintHelper.isConstraintComposition(declaredAnnotationType)) {
            if (LOG.isDebugEnabled()) {
               LOG.debugf("Adding Bool %s.", declaredAnnotationType.getName());
            }

            return ((ConstraintComposition)declaredAnnotation).value();
         }
      }

      return CompositionType.AND;
   }

   private ConstraintDescriptorImpl createComposingConstraintDescriptor(ConstraintHelper constraintHelper, Member member, Map overrideParameters, int index, Annotation constraintAnnotation, ConstraintType constraintType) {
      Class annotationType = constraintAnnotation.annotationType();
      ConstraintAnnotationDescriptor.Builder annotationDescriptorBuilder = new ConstraintAnnotationDescriptor.Builder(annotationType, (Map)run(GetAnnotationAttributes.action(constraintAnnotation)));
      Map overrides = (Map)overrideParameters.get(new ClassIndexWrapper(annotationType, index));
      if (overrides != null) {
         Iterator var10 = overrides.entrySet().iterator();

         while(var10.hasNext()) {
            Map.Entry entry = (Map.Entry)var10.next();
            annotationDescriptorBuilder.setAttribute((String)entry.getKey(), entry.getValue());
         }
      }

      annotationDescriptorBuilder.setGroups((Class[])this.groups.toArray(new Class[this.groups.size()]));
      annotationDescriptorBuilder.setPayload((Class[])this.payloads.toArray(new Class[this.payloads.size()]));
      if (annotationDescriptorBuilder.hasAttribute("validationAppliesTo")) {
         ConstraintTarget validationAppliesTo = this.getValidationAppliesTo();
         if (validationAppliesTo == null) {
            if (constraintType == ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER) {
               validationAppliesTo = ConstraintTarget.PARAMETERS;
            } else {
               validationAppliesTo = ConstraintTarget.IMPLICIT;
            }
         }

         annotationDescriptorBuilder.setAttribute("validationAppliesTo", validationAppliesTo);
      }

      return new ConstraintDescriptorImpl(constraintHelper, member, annotationDescriptorBuilder.build(), this.elementType, (Class)null, this.definedOn, constraintType);
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   public CompositionType getCompositionType() {
      return this.compositionType;
   }

   private static enum ComposingConstraintAnnotationLocation {
      DIRECT,
      IN_CONTAINER;
   }

   public static enum ConstraintType {
      GENERIC,
      CROSS_PARAMETER;
   }

   private static class ClassIndexWrapper {
      final Class clazz;
      final int index;

      ClassIndexWrapper(Class clazz, int index) {
         this.clazz = clazz;
         this.index = index;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            ClassIndexWrapper that = (ClassIndexWrapper)o;
            return this.index != that.index ? false : this.clazz.equals(that.clazz);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.clazz != null ? this.clazz.hashCode() : 0;
         result = 31 * result + this.index;
         return result;
      }

      public String toString() {
         return "ClassIndexWrapper [clazz=" + StringHelper.toShortString((Type)this.clazz) + ", index=" + this.index + "]";
      }
   }
}
