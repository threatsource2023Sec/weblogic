package org.hibernate.validator.internal.metadata.provider;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.core.MetaConstraints;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredConstructors;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredFields;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethods;
import org.hibernate.validator.internal.util.privilegedactions.GetMethods;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class AnnotationMetaDataProvider implements MetaDataProvider {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final Annotation[] EMPTY_PARAMETER_ANNOTATIONS = new Annotation[0];
   private final ConstraintHelper constraintHelper;
   private final TypeResolutionHelper typeResolutionHelper;
   private final AnnotationProcessingOptions annotationProcessingOptions;
   private final ValueExtractorManager valueExtractorManager;
   private final BeanConfiguration objectBeanConfiguration;

   public AnnotationMetaDataProvider(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, AnnotationProcessingOptions annotationProcessingOptions) {
      this.constraintHelper = constraintHelper;
      this.typeResolutionHelper = typeResolutionHelper;
      this.valueExtractorManager = valueExtractorManager;
      this.annotationProcessingOptions = annotationProcessingOptions;
      this.objectBeanConfiguration = this.retrieveBeanConfiguration(Object.class);
   }

   public AnnotationProcessingOptions getAnnotationProcessingOptions() {
      return new AnnotationProcessingOptionsImpl();
   }

   public BeanConfiguration getBeanConfiguration(Class beanClass) {
      return Object.class.equals(beanClass) ? this.objectBeanConfiguration : this.retrieveBeanConfiguration(beanClass);
   }

   private BeanConfiguration retrieveBeanConfiguration(Class beanClass) {
      Set constrainedElements = this.getFieldMetaData(beanClass);
      constrainedElements.addAll(this.getMethodMetaData(beanClass));
      constrainedElements.addAll(this.getConstructorMetaData(beanClass));
      Set classLevelConstraints = this.getClassLevelConstraints(beanClass);
      if (!classLevelConstraints.isEmpty()) {
         ConstrainedType classLevelMetaData = new ConstrainedType(ConfigurationSource.ANNOTATION, beanClass, classLevelConstraints);
         constrainedElements.add(classLevelMetaData);
      }

      return new BeanConfiguration(ConfigurationSource.ANNOTATION, beanClass, constrainedElements, this.getDefaultGroupSequence(beanClass), this.getDefaultGroupSequenceProvider(beanClass));
   }

   private List getDefaultGroupSequence(Class beanClass) {
      GroupSequence groupSequenceAnnotation = (GroupSequence)beanClass.getAnnotation(GroupSequence.class);
      return groupSequenceAnnotation != null ? Arrays.asList(groupSequenceAnnotation.value()) : null;
   }

   private DefaultGroupSequenceProvider getDefaultGroupSequenceProvider(Class beanClass) {
      GroupSequenceProvider groupSequenceProviderAnnotation = (GroupSequenceProvider)beanClass.getAnnotation(GroupSequenceProvider.class);
      if (groupSequenceProviderAnnotation != null) {
         Class providerClass = groupSequenceProviderAnnotation.value();
         return this.newGroupSequenceProviderClassInstance(beanClass, providerClass);
      } else {
         return null;
      }
   }

   private DefaultGroupSequenceProvider newGroupSequenceProviderClassInstance(Class beanClass, Class providerClass) {
      Method[] providerMethods = (Method[])this.run(GetMethods.action(providerClass));
      Method[] var4 = providerMethods;
      int var5 = providerMethods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         Class[] paramTypes = method.getParameterTypes();
         if ("getValidationGroups".equals(method.getName()) && !method.isBridge() && paramTypes.length == 1 && paramTypes[0].isAssignableFrom(beanClass)) {
            return (DefaultGroupSequenceProvider)this.run(NewInstance.action(providerClass, "the default group sequence provider"));
         }
      }

      throw LOG.getWrongDefaultGroupSequenceProviderTypeException(beanClass);
   }

   private Set getClassLevelConstraints(Class clazz) {
      if (this.annotationProcessingOptions.areClassLevelConstraintsIgnoredFor(clazz)) {
         return Collections.emptySet();
      } else {
         Set classLevelConstraints = CollectionHelper.newHashSet();
         List classMetaData = this.findClassLevelConstraints(clazz);
         ConstraintLocation location = ConstraintLocation.forClass(clazz);
         Iterator var5 = classMetaData.iterator();

         while(var5.hasNext()) {
            ConstraintDescriptorImpl constraintDescription = (ConstraintDescriptorImpl)var5.next();
            classLevelConstraints.add(MetaConstraints.create(this.typeResolutionHelper, this.valueExtractorManager, constraintDescription, location));
         }

         return classLevelConstraints;
      }
   }

   private Set getFieldMetaData(Class beanClass) {
      Set propertyMetaData = CollectionHelper.newHashSet();
      Field[] var3 = (Field[])this.run(GetDeclaredFields.action(beanClass));
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field field = var3[var5];
         if (!Modifier.isStatic(field.getModifiers()) && !this.annotationProcessingOptions.areMemberConstraintsIgnoredFor(field) && !field.isSynthetic()) {
            propertyMetaData.add(this.findPropertyMetaData(field));
         }
      }

      return propertyMetaData;
   }

   private ConstrainedField findPropertyMetaData(Field field) {
      Set constraints = this.convertToMetaConstraints(this.findConstraints(field, ElementType.FIELD), field);
      CascadingMetaDataBuilder cascadingMetaDataBuilder = this.findCascadingMetaData(field);
      Set typeArgumentsConstraints = this.findTypeAnnotationConstraints(field);
      return new ConstrainedField(ConfigurationSource.ANNOTATION, field, constraints, typeArgumentsConstraints, cascadingMetaDataBuilder);
   }

   private Set convertToMetaConstraints(List constraintDescriptors, Field field) {
      if (constraintDescriptors.isEmpty()) {
         return Collections.emptySet();
      } else {
         Set constraints = CollectionHelper.newHashSet();
         ConstraintLocation location = ConstraintLocation.forField(field);
         Iterator var5 = constraintDescriptors.iterator();

         while(var5.hasNext()) {
            ConstraintDescriptorImpl constraintDescription = (ConstraintDescriptorImpl)var5.next();
            constraints.add(MetaConstraints.create(this.typeResolutionHelper, this.valueExtractorManager, constraintDescription, location));
         }

         return constraints;
      }
   }

   private Set getConstructorMetaData(Class clazz) {
      Executable[] declaredConstructors = (Executable[])this.run(GetDeclaredConstructors.action(clazz));
      return this.getMetaData(declaredConstructors);
   }

   private Set getMethodMetaData(Class clazz) {
      Executable[] declaredMethods = (Executable[])this.run(GetDeclaredMethods.action(clazz));
      return this.getMetaData(declaredMethods);
   }

   private Set getMetaData(Executable[] executableElements) {
      Set executableMetaData = CollectionHelper.newHashSet();
      Executable[] var3 = executableElements;
      int var4 = executableElements.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Executable executable = var3[var5];
         if (!Modifier.isStatic(executable.getModifiers()) && !executable.isSynthetic()) {
            executableMetaData.add(this.findExecutableMetaData(executable));
         }
      }

      return executableMetaData;
   }

   private ConstrainedExecutable findExecutableMetaData(Executable executable) {
      List parameterConstraints = this.getParameterMetaData(executable);
      Map executableConstraints = (Map)this.findConstraints(executable, ExecutableHelper.getElementType(executable)).stream().collect(Collectors.groupingBy(ConstraintDescriptorImpl::getConstraintType));
      Set crossParameterConstraints;
      if (this.annotationProcessingOptions.areCrossParameterConstraintsIgnoredFor(executable)) {
         crossParameterConstraints = Collections.emptySet();
      } else {
         crossParameterConstraints = this.convertToMetaConstraints((List)executableConstraints.get(ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER), executable);
      }

      Set returnValueConstraints;
      Set typeArgumentsConstraints;
      CascadingMetaDataBuilder cascadingMetaDataBuilder;
      if (this.annotationProcessingOptions.areReturnValueConstraintsIgnoredFor(executable)) {
         returnValueConstraints = Collections.emptySet();
         typeArgumentsConstraints = Collections.emptySet();
         cascadingMetaDataBuilder = CascadingMetaDataBuilder.nonCascading();
      } else {
         AnnotatedType annotatedReturnType = executable.getAnnotatedReturnType();
         typeArgumentsConstraints = this.findTypeAnnotationConstraints(executable, annotatedReturnType);
         returnValueConstraints = this.convertToMetaConstraints((List)executableConstraints.get(ConstraintDescriptorImpl.ConstraintType.GENERIC), executable);
         cascadingMetaDataBuilder = this.findCascadingMetaData(executable, annotatedReturnType);
      }

      return new ConstrainedExecutable(ConfigurationSource.ANNOTATION, executable, parameterConstraints, crossParameterConstraints, returnValueConstraints, typeArgumentsConstraints, cascadingMetaDataBuilder);
   }

   private Set convertToMetaConstraints(List constraintsDescriptors, Executable executable) {
      if (constraintsDescriptors == null) {
         return Collections.emptySet();
      } else {
         Set constraints = CollectionHelper.newHashSet();
         ConstraintLocation returnValueLocation = ConstraintLocation.forReturnValue(executable);
         ConstraintLocation crossParameterLocation = ConstraintLocation.forCrossParameter(executable);
         Iterator var6 = constraintsDescriptors.iterator();

         while(var6.hasNext()) {
            ConstraintDescriptorImpl constraintDescriptor = (ConstraintDescriptorImpl)var6.next();
            ConstraintLocation location = constraintDescriptor.getConstraintType() == ConstraintDescriptorImpl.ConstraintType.GENERIC ? returnValueLocation : crossParameterLocation;
            constraints.add(MetaConstraints.create(this.typeResolutionHelper, this.valueExtractorManager, constraintDescriptor, location));
         }

         return constraints;
      }
   }

   private List getParameterMetaData(Executable executable) {
      if (executable.getParameterCount() == 0) {
         return Collections.emptyList();
      } else {
         Parameter[] parameters = executable.getParameters();
         List metaData = new ArrayList(parameters.length);
         int i = 0;
         Parameter[] var5 = parameters;
         int var6 = parameters.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Parameter parameter = var5[var7];

            Annotation[] parameterAnnotations;
            try {
               parameterAnnotations = parameter.getAnnotations();
            } catch (ArrayIndexOutOfBoundsException var19) {
               LOG.warn(Messages.MESSAGES.constraintOnConstructorOfNonStaticInnerClass(), var19);
               parameterAnnotations = EMPTY_PARAMETER_ANNOTATIONS;
            }

            Set parameterConstraints = CollectionHelper.newHashSet();
            if (this.annotationProcessingOptions.areParameterConstraintsIgnoredFor(executable, i)) {
               Type type = ReflectionHelper.typeOf(executable, i);
               metaData.add(new ConstrainedParameter(ConfigurationSource.ANNOTATION, executable, type, i, parameterConstraints, Collections.emptySet(), CascadingMetaDataBuilder.nonCascading()));
               ++i;
            } else {
               ConstraintLocation location = ConstraintLocation.forParameter(executable, i);
               Annotation[] var12 = parameterAnnotations;
               int var13 = parameterAnnotations.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Annotation parameterAnnotation = var12[var14];
                  List constraints = this.findConstraintAnnotations(executable, parameterAnnotation, ElementType.PARAMETER);
                  Iterator var17 = constraints.iterator();

                  while(var17.hasNext()) {
                     ConstraintDescriptorImpl constraintDescriptorImpl = (ConstraintDescriptorImpl)var17.next();
                     parameterConstraints.add(MetaConstraints.create(this.typeResolutionHelper, this.valueExtractorManager, constraintDescriptorImpl, location));
                  }
               }

               AnnotatedType parameterAnnotatedType = parameter.getAnnotatedType();
               Set typeArgumentsConstraints = this.findTypeAnnotationConstraintsForExecutableParameter(executable, i, parameterAnnotatedType);
               CascadingMetaDataBuilder cascadingMetaData = this.findCascadingMetaData(executable, parameters, i, parameterAnnotatedType);
               metaData.add(new ConstrainedParameter(ConfigurationSource.ANNOTATION, executable, ReflectionHelper.typeOf(executable, i), i, parameterConstraints, typeArgumentsConstraints, cascadingMetaData));
               ++i;
            }
         }

         return metaData;
      }
   }

   private List findConstraints(Member member, ElementType type) {
      List metaData = CollectionHelper.newArrayList();
      Annotation[] var4 = ((AccessibleObject)member).getDeclaredAnnotations();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Annotation annotation = var4[var6];
         metaData.addAll(this.findConstraintAnnotations(member, annotation, type));
      }

      return metaData;
   }

   private List findClassLevelConstraints(Class beanClass) {
      List metaData = CollectionHelper.newArrayList();
      Annotation[] var3 = beanClass.getDeclaredAnnotations();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var3[var5];
         metaData.addAll(this.findConstraintAnnotations((Member)null, annotation, ElementType.TYPE));
      }

      return metaData;
   }

   protected List findConstraintAnnotations(Member member, Annotation annotation, ElementType type) {
      if (this.constraintHelper.isJdkAnnotation(annotation.annotationType())) {
         return Collections.emptyList();
      } else {
         List constraints = CollectionHelper.newArrayList();
         Class annotationType = annotation.annotationType();
         if (this.constraintHelper.isConstraintAnnotation(annotationType)) {
            constraints.add(annotation);
         } else if (this.constraintHelper.isMultiValueConstraint(annotationType)) {
            constraints.addAll(this.constraintHelper.getConstraintsFromMultiValueConstraint(annotation));
         }

         return (List)constraints.stream().map((c) -> {
            return this.buildConstraintDescriptor(member, c, type);
         }).collect(Collectors.toList());
      }
   }

   private Map getGroupConversions(AnnotatedElement annotatedElement) {
      return this.getGroupConversions((ConvertGroup)annotatedElement.getAnnotation(ConvertGroup.class), (ConvertGroup.List)annotatedElement.getAnnotation(ConvertGroup.List.class));
   }

   private Map getGroupConversions(ConvertGroup groupConversion, ConvertGroup.List groupConversionList) {
      if (groupConversion != null || groupConversionList != null && groupConversionList.value().length != 0) {
         Map groupConversions = CollectionHelper.newHashMap();
         if (groupConversion != null) {
            groupConversions.put(groupConversion.from(), groupConversion.to());
         }

         if (groupConversionList != null) {
            ConvertGroup[] var4 = groupConversionList.value();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ConvertGroup conversion = var4[var6];
               if (groupConversions.containsKey(conversion.from())) {
                  throw LOG.getMultipleGroupConversionsForSameSourceException(conversion.from(), CollectionHelper.asSet((Class)groupConversions.get(conversion.from()), conversion.to()));
               }

               groupConversions.put(conversion.from(), conversion.to());
            }
         }

         return groupConversions;
      } else {
         return Collections.emptyMap();
      }
   }

   private ConstraintDescriptorImpl buildConstraintDescriptor(Member member, Annotation annotation, ElementType type) {
      return new ConstraintDescriptorImpl(this.constraintHelper, member, new ConstraintAnnotationDescriptor(annotation), type);
   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   protected Set findTypeAnnotationConstraints(Field field) {
      return this.findTypeArgumentsConstraints(field, new TypeArgumentFieldLocation(field), field.getAnnotatedType());
   }

   protected Set findTypeAnnotationConstraints(Executable executable, AnnotatedType annotatedReturnType) {
      return this.findTypeArgumentsConstraints(executable, new TypeArgumentReturnValueLocation(executable), annotatedReturnType);
   }

   private CascadingMetaDataBuilder findCascadingMetaData(Executable executable, Parameter[] parameters, int i, AnnotatedType parameterAnnotatedType) {
      Parameter parameter = parameters[i];
      TypeVariable[] typeParameters = parameter.getType().getTypeParameters();
      Map containerElementTypesCascadingMetaData = this.getTypeParametersCascadingMetadata(parameterAnnotatedType, typeParameters);

      try {
         return this.getCascadingMetaData(ReflectionHelper.typeOf(parameter.getDeclaringExecutable(), i), parameter, containerElementTypesCascadingMetaData);
      } catch (ArrayIndexOutOfBoundsException var9) {
         LOG.warn(Messages.MESSAGES.constraintOnConstructorOfNonStaticInnerClass(), var9);
         return CascadingMetaDataBuilder.nonCascading();
      }
   }

   private CascadingMetaDataBuilder findCascadingMetaData(Field field) {
      TypeVariable[] typeParameters = field.getType().getTypeParameters();
      AnnotatedType annotatedType = field.getAnnotatedType();
      Map containerElementTypesCascadingMetaData = this.getTypeParametersCascadingMetadata(annotatedType, typeParameters);
      return this.getCascadingMetaData(ReflectionHelper.typeOf(field), field, containerElementTypesCascadingMetaData);
   }

   private CascadingMetaDataBuilder findCascadingMetaData(Executable executable, AnnotatedType annotatedReturnType) {
      TypeVariable[] typeParameters;
      if (executable instanceof Method) {
         typeParameters = ((Method)executable).getReturnType().getTypeParameters();
      } else {
         typeParameters = ((Constructor)executable).getDeclaringClass().getTypeParameters();
      }

      Map containerElementTypesCascadingMetaData = this.getTypeParametersCascadingMetadata(annotatedReturnType, typeParameters);
      return this.getCascadingMetaData(ReflectionHelper.typeOf(executable), executable, containerElementTypesCascadingMetaData);
   }

   private Map getTypeParametersCascadingMetadata(AnnotatedType annotatedType, TypeVariable[] typeParameters) {
      if (annotatedType instanceof AnnotatedArrayType) {
         return this.getTypeParametersCascadingMetaDataForArrayType((AnnotatedArrayType)annotatedType);
      } else {
         return annotatedType instanceof AnnotatedParameterizedType ? this.getTypeParametersCascadingMetaDataForParameterizedType((AnnotatedParameterizedType)annotatedType, typeParameters) : Collections.emptyMap();
      }
   }

   private Map getTypeParametersCascadingMetaDataForParameterizedType(AnnotatedParameterizedType annotatedParameterizedType, TypeVariable[] typeParameters) {
      Map typeParametersCascadingMetadata = CollectionHelper.newHashMap(typeParameters.length);
      AnnotatedType[] annotatedTypeArguments = annotatedParameterizedType.getAnnotatedActualTypeArguments();
      int i = 0;
      AnnotatedType[] var6 = annotatedTypeArguments;
      int var7 = annotatedTypeArguments.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         AnnotatedType annotatedTypeArgument = var6[var8];
         Map nestedTypeParametersCascadingMetadata = this.getTypeParametersCascadingMetaDataForAnnotatedType(annotatedTypeArgument);
         typeParametersCascadingMetadata.put(typeParameters[i], new CascadingMetaDataBuilder(annotatedParameterizedType.getType(), typeParameters[i], annotatedTypeArgument.isAnnotationPresent(Valid.class), nestedTypeParametersCascadingMetadata, this.getGroupConversions(annotatedTypeArgument)));
         ++i;
      }

      return typeParametersCascadingMetadata;
   }

   private Map getTypeParametersCascadingMetaDataForArrayType(AnnotatedArrayType annotatedArrayType) {
      return Collections.emptyMap();
   }

   private Map getTypeParametersCascadingMetaDataForAnnotatedType(AnnotatedType annotatedType) {
      if (annotatedType instanceof AnnotatedArrayType) {
         return this.getTypeParametersCascadingMetaDataForArrayType((AnnotatedArrayType)annotatedType);
      } else {
         return annotatedType instanceof AnnotatedParameterizedType ? this.getTypeParametersCascadingMetaDataForParameterizedType((AnnotatedParameterizedType)annotatedType, ReflectionHelper.getClassFromType(annotatedType.getType()).getTypeParameters()) : Collections.emptyMap();
      }
   }

   protected Set findTypeAnnotationConstraintsForExecutableParameter(Executable executable, int i, AnnotatedType parameterAnnotatedType) {
      try {
         return this.findTypeArgumentsConstraints(executable, new TypeArgumentExecutableParameterLocation(executable, i), parameterAnnotatedType);
      } catch (ArrayIndexOutOfBoundsException var5) {
         LOG.warn(Messages.MESSAGES.constraintOnConstructorOfNonStaticInnerClass(), var5);
         return Collections.emptySet();
      }
   }

   private Set findTypeArgumentsConstraints(Member member, TypeArgumentLocation location, AnnotatedType annotatedType) {
      if (!(annotatedType instanceof AnnotatedParameterizedType)) {
         return Collections.emptySet();
      } else {
         Set typeArgumentConstraints = new HashSet();
         if (annotatedType instanceof AnnotatedArrayType) {
            AnnotatedArrayType annotatedArrayType = (AnnotatedArrayType)annotatedType;
            Type validatedType = annotatedArrayType.getAnnotatedGenericComponentType().getType();
            TypeVariable arrayElementTypeArgument = new ArrayElement(annotatedArrayType);
            typeArgumentConstraints.addAll(this.findTypeUseConstraints(member, annotatedArrayType, arrayElementTypeArgument, location, validatedType));
            typeArgumentConstraints.addAll(this.findTypeArgumentsConstraints(member, new NestedTypeArgumentLocation(location, arrayElementTypeArgument, validatedType), annotatedArrayType.getAnnotatedGenericComponentType()));
         } else if (annotatedType instanceof AnnotatedParameterizedType) {
            AnnotatedParameterizedType annotatedParameterizedType = (AnnotatedParameterizedType)annotatedType;
            int i = 0;
            TypeVariable[] var15 = ReflectionHelper.getClassFromType(annotatedType.getType()).getTypeParameters();
            int var8 = var15.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               TypeVariable typeVariable = var15[var9];
               AnnotatedType annotatedTypeParameter = annotatedParameterizedType.getAnnotatedActualTypeArguments()[i];
               Type validatedType = annotatedTypeParameter.getType();
               typeArgumentConstraints.addAll(this.findTypeUseConstraints(member, annotatedTypeParameter, typeVariable, location, validatedType));
               if (validatedType instanceof ParameterizedType) {
                  typeArgumentConstraints.addAll(this.findTypeArgumentsConstraints(member, new NestedTypeArgumentLocation(location, typeVariable, validatedType), annotatedTypeParameter));
               }

               ++i;
            }
         }

         return (Set)(typeArgumentConstraints.isEmpty() ? Collections.emptySet() : typeArgumentConstraints);
      }
   }

   private Set findTypeUseConstraints(Member member, AnnotatedType typeArgument, TypeVariable typeVariable, TypeArgumentLocation location, Type type) {
      Set constraints = (Set)Arrays.stream(typeArgument.getAnnotations()).flatMap((a) -> {
         return this.findConstraintAnnotations(member, a, ElementType.TYPE_USE).stream();
      }).map((d) -> {
         return this.createTypeArgumentMetaConstraint(d, location, typeVariable, type);
      }).collect(Collectors.toSet());
      return constraints;
   }

   private MetaConstraint createTypeArgumentMetaConstraint(ConstraintDescriptorImpl descriptor, TypeArgumentLocation location, TypeVariable typeVariable, Type type) {
      ConstraintLocation constraintLocation = ConstraintLocation.forTypeArgument(location.toConstraintLocation(), typeVariable, type);
      return MetaConstraints.create(this.typeResolutionHelper, this.valueExtractorManager, descriptor, constraintLocation);
   }

   private CascadingMetaDataBuilder getCascadingMetaData(Type type, AnnotatedElement annotatedElement, Map containerElementTypesCascadingMetaData) {
      return CascadingMetaDataBuilder.annotatedObject(type, annotatedElement.isAnnotationPresent(Valid.class), containerElementTypesCascadingMetaData, this.getGroupConversions(annotatedElement));
   }

   private static class NestedTypeArgumentLocation implements TypeArgumentLocation {
      private final TypeArgumentLocation parentLocation;
      private final TypeVariable typeParameter;
      private final Type typeOfAnnotatedElement;

      private NestedTypeArgumentLocation(TypeArgumentLocation parentLocation, TypeVariable typeParameter, Type typeOfAnnotatedElement) {
         this.parentLocation = parentLocation;
         this.typeParameter = typeParameter;
         this.typeOfAnnotatedElement = typeOfAnnotatedElement;
      }

      public ConstraintLocation toConstraintLocation() {
         return ConstraintLocation.forTypeArgument(this.parentLocation.toConstraintLocation(), this.typeParameter, this.typeOfAnnotatedElement);
      }

      // $FF: synthetic method
      NestedTypeArgumentLocation(TypeArgumentLocation x0, TypeVariable x1, Type x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static class TypeArgumentReturnValueLocation implements TypeArgumentLocation {
      private final Executable executable;

      private TypeArgumentReturnValueLocation(Executable executable) {
         this.executable = executable;
      }

      public ConstraintLocation toConstraintLocation() {
         return ConstraintLocation.forReturnValue(this.executable);
      }

      // $FF: synthetic method
      TypeArgumentReturnValueLocation(Executable x0, Object x1) {
         this(x0);
      }
   }

   private static class TypeArgumentFieldLocation implements TypeArgumentLocation {
      private final Field field;

      private TypeArgumentFieldLocation(Field field) {
         this.field = field;
      }

      public ConstraintLocation toConstraintLocation() {
         return ConstraintLocation.forField(this.field);
      }

      // $FF: synthetic method
      TypeArgumentFieldLocation(Field x0, Object x1) {
         this(x0);
      }
   }

   private static class TypeArgumentExecutableParameterLocation implements TypeArgumentLocation {
      private final Executable executable;
      private final int index;

      private TypeArgumentExecutableParameterLocation(Executable executable, int index) {
         this.executable = executable;
         this.index = index;
      }

      public ConstraintLocation toConstraintLocation() {
         return ConstraintLocation.forParameter(this.executable, this.index);
      }

      // $FF: synthetic method
      TypeArgumentExecutableParameterLocation(Executable x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   private interface TypeArgumentLocation {
      ConstraintLocation toConstraintLocation();
   }
}
