package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ElementKind;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.descriptor.BeanDescriptorImpl;
import org.hibernate.validator.internal.metadata.descriptor.ExecutableDescriptorImpl;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.classhierarchy.ClassHierarchyHelper;
import org.hibernate.validator.internal.util.classhierarchy.Filters;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public final class BeanMetaDataImpl implements BeanMetaData {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final List DEFAULT_GROUP_SEQUENCE = Collections.singletonList(Default.class);
   private final boolean hasConstraints;
   private final ValidationOrderGenerator validationOrderGenerator;
   private final Class beanClass;
   private final Set allMetaConstraints;
   private final Set directMetaConstraints;
   private final Map executableMetaDataMap;
   private final Set unconstrainedExecutables;
   private final Map propertyMetaDataMap;
   private final Set cascadedProperties;
   private final BeanDescriptor beanDescriptor;
   private final List defaultGroupSequence;
   private final DefaultGroupSequenceProvider defaultGroupSequenceProvider;
   private final ValidationOrder validationOrder;
   private final List classHierarchyWithoutInterfaces;

   public BeanMetaDataImpl(Class beanClass, List defaultGroupSequence, DefaultGroupSequenceProvider defaultGroupSequenceProvider, Set constraintMetaDataSet, ValidationOrderGenerator validationOrderGenerator) {
      this.validationOrderGenerator = validationOrderGenerator;
      this.beanClass = beanClass;
      this.propertyMetaDataMap = CollectionHelper.newHashMap();
      Set propertyMetaDataSet = CollectionHelper.newHashSet();
      Set executableMetaDataSet = CollectionHelper.newHashSet();
      Set tmpUnconstrainedExecutables = CollectionHelper.newHashSet();
      boolean hasConstraints = false;
      Iterator var10 = constraintMetaDataSet.iterator();

      while(var10.hasNext()) {
         ConstraintMetaData constraintMetaData = (ConstraintMetaData)var10.next();
         boolean elementHasConstraints = constraintMetaData.isCascading() || constraintMetaData.isConstrained();
         hasConstraints |= elementHasConstraints;
         if (constraintMetaData.getKind() == ElementKind.PROPERTY) {
            propertyMetaDataSet.add((PropertyMetaData)constraintMetaData);
         } else {
            ExecutableMetaData executableMetaData = (ExecutableMetaData)constraintMetaData;
            if (elementHasConstraints) {
               executableMetaDataSet.add(executableMetaData);
            } else {
               tmpUnconstrainedExecutables.addAll(executableMetaData.getSignatures());
            }
         }
      }

      Set cascadedProperties = CollectionHelper.newHashSet();
      Set allMetaConstraints = CollectionHelper.newHashSet();
      Iterator var20 = propertyMetaDataSet.iterator();

      while(var20.hasNext()) {
         PropertyMetaData propertyMetaData = (PropertyMetaData)var20.next();
         this.propertyMetaDataMap.put(propertyMetaData.getName(), propertyMetaData);
         cascadedProperties.addAll(propertyMetaData.getCascadables());
         allMetaConstraints.addAll(propertyMetaData.getAllConstraints());
      }

      this.hasConstraints = hasConstraints;
      this.cascadedProperties = CollectionHelper.toImmutableSet(cascadedProperties);
      this.allMetaConstraints = CollectionHelper.toImmutableSet(allMetaConstraints);
      this.classHierarchyWithoutInterfaces = CollectionHelper.toImmutableList(ClassHierarchyHelper.getHierarchy(beanClass, Filters.excludeInterfaces()));
      DefaultGroupSequenceContext defaultGroupContext = getDefaultGroupSequenceData(beanClass, defaultGroupSequence, defaultGroupSequenceProvider, validationOrderGenerator);
      this.defaultGroupSequenceProvider = defaultGroupContext.defaultGroupSequenceProvider;
      this.defaultGroupSequence = CollectionHelper.toImmutableList(defaultGroupContext.defaultGroupSequence);
      this.validationOrder = defaultGroupContext.validationOrder;
      this.directMetaConstraints = this.getDirectConstraints();
      this.executableMetaDataMap = CollectionHelper.toImmutableMap(this.bySignature(executableMetaDataSet));
      this.unconstrainedExecutables = CollectionHelper.toImmutableSet(tmpUnconstrainedExecutables);
      boolean defaultGroupSequenceIsRedefined = this.defaultGroupSequenceIsRedefined();
      List resolvedDefaultGroupSequence = this.getDefaultGroupSequence((Object)null);
      Map propertyDescriptors = getConstrainedPropertiesAsDescriptors(this.propertyMetaDataMap, defaultGroupSequenceIsRedefined, resolvedDefaultGroupSequence);
      Map methodsDescriptors = getConstrainedMethodsAsDescriptors(this.executableMetaDataMap, defaultGroupSequenceIsRedefined, resolvedDefaultGroupSequence);
      Map constructorsDescriptors = getConstrainedConstructorsAsDescriptors(this.executableMetaDataMap, defaultGroupSequenceIsRedefined, resolvedDefaultGroupSequence);
      this.beanDescriptor = new BeanDescriptorImpl(beanClass, getClassLevelConstraintsAsDescriptors(allMetaConstraints), propertyDescriptors, methodsDescriptors, constructorsDescriptors, defaultGroupSequenceIsRedefined, resolvedDefaultGroupSequence);
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public boolean hasConstraints() {
      return this.hasConstraints;
   }

   public BeanDescriptor getBeanDescriptor() {
      return this.beanDescriptor;
   }

   public Set getCascadables() {
      return this.cascadedProperties;
   }

   public boolean hasCascadables() {
      return !this.cascadedProperties.isEmpty();
   }

   public PropertyMetaData getMetaDataFor(String propertyName) {
      PropertyMetaData propertyMetaData = (PropertyMetaData)this.propertyMetaDataMap.get(propertyName);
      if (propertyMetaData == null) {
         throw LOG.getPropertyNotDefinedByValidatedTypeException(this.beanClass, propertyName);
      } else {
         return propertyMetaData;
      }
   }

   public Set getMetaConstraints() {
      return this.allMetaConstraints;
   }

   public Set getDirectMetaConstraints() {
      return this.directMetaConstraints;
   }

   public Optional getMetaDataFor(Executable executable) {
      String signature = ExecutableHelper.getSignature(executable);
      if (this.unconstrainedExecutables.contains(signature)) {
         return Optional.empty();
      } else {
         ExecutableMetaData executableMetaData = (ExecutableMetaData)this.executableMetaDataMap.get(ExecutableHelper.getSignature(executable));
         if (executableMetaData == null) {
            throw LOG.getMethodOrConstructorNotDefinedByValidatedTypeException(this.beanClass, executable);
         } else {
            return Optional.of(executableMetaData);
         }
      }
   }

   public List getDefaultGroupSequence(Object beanState) {
      if (this.hasDefaultGroupSequenceProvider()) {
         List providerDefaultGroupSequence = this.defaultGroupSequenceProvider.getValidationGroups(beanState);
         return getValidDefaultGroupSequence(this.beanClass, providerDefaultGroupSequence);
      } else {
         return this.defaultGroupSequence;
      }
   }

   public Iterator getDefaultValidationSequence(Object beanState) {
      if (this.hasDefaultGroupSequenceProvider()) {
         List providerDefaultGroupSequence = this.defaultGroupSequenceProvider.getValidationGroups(beanState);
         return this.validationOrderGenerator.getDefaultValidationOrder(this.beanClass, getValidDefaultGroupSequence(this.beanClass, providerDefaultGroupSequence)).getSequenceIterator();
      } else {
         return this.validationOrder.getSequenceIterator();
      }
   }

   public boolean defaultGroupSequenceIsRedefined() {
      return this.defaultGroupSequence.size() > 1 || this.hasDefaultGroupSequenceProvider();
   }

   public List getClassHierarchy() {
      return this.classHierarchyWithoutInterfaces;
   }

   private static Set getClassLevelConstraintsAsDescriptors(Set constraints) {
      return (Set)constraints.stream().filter((c) -> {
         return c.getElementType() == ElementType.TYPE;
      }).map(MetaConstraint::getDescriptor).collect(Collectors.toSet());
   }

   private static Map getConstrainedPropertiesAsDescriptors(Map propertyMetaDataMap, boolean defaultGroupSequenceIsRedefined, List resolvedDefaultGroupSequence) {
      Map theValue = CollectionHelper.newHashMap();
      Iterator var4 = propertyMetaDataMap.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         if (((PropertyMetaData)entry.getValue()).isConstrained() && ((PropertyMetaData)entry.getValue()).getName() != null) {
            theValue.put(entry.getKey(), ((PropertyMetaData)entry.getValue()).asDescriptor(defaultGroupSequenceIsRedefined, resolvedDefaultGroupSequence));
         }
      }

      return theValue;
   }

   private static Map getConstrainedMethodsAsDescriptors(Map executableMetaDataMap, boolean defaultGroupSequenceIsRedefined, List resolvedDefaultGroupSequence) {
      Map constrainedMethodDescriptors = CollectionHelper.newHashMap();
      Iterator var4 = executableMetaDataMap.values().iterator();

      while(true) {
         ExecutableMetaData executableMetaData;
         do {
            do {
               if (!var4.hasNext()) {
                  return constrainedMethodDescriptors;
               }

               executableMetaData = (ExecutableMetaData)var4.next();
            } while(executableMetaData.getKind() != ElementKind.METHOD);
         } while(!executableMetaData.isConstrained());

         ExecutableDescriptorImpl descriptor = executableMetaData.asDescriptor(defaultGroupSequenceIsRedefined, resolvedDefaultGroupSequence);
         Iterator var7 = executableMetaData.getSignatures().iterator();

         while(var7.hasNext()) {
            String signature = (String)var7.next();
            constrainedMethodDescriptors.put(signature, descriptor);
         }
      }
   }

   private static Map getConstrainedConstructorsAsDescriptors(Map executableMetaDataMap, boolean defaultGroupSequenceIsRedefined, List resolvedDefaultGroupSequence) {
      Map constrainedMethodDescriptors = CollectionHelper.newHashMap();
      Iterator var4 = executableMetaDataMap.values().iterator();

      while(var4.hasNext()) {
         ExecutableMetaData executableMetaData = (ExecutableMetaData)var4.next();
         if (executableMetaData.getKind() == ElementKind.CONSTRUCTOR && executableMetaData.isConstrained()) {
            constrainedMethodDescriptors.put(executableMetaData.getSignatures().iterator().next(), executableMetaData.asDescriptor(defaultGroupSequenceIsRedefined, resolvedDefaultGroupSequence));
         }
      }

      return constrainedMethodDescriptors;
   }

   private static DefaultGroupSequenceContext getDefaultGroupSequenceData(Class beanClass, List defaultGroupSequence, DefaultGroupSequenceProvider defaultGroupSequenceProvider, ValidationOrderGenerator validationOrderGenerator) {
      if (defaultGroupSequence != null && defaultGroupSequenceProvider != null) {
         throw LOG.getInvalidDefaultGroupSequenceDefinitionException();
      } else {
         DefaultGroupSequenceContext context = new DefaultGroupSequenceContext();
         if (defaultGroupSequenceProvider != null) {
            context.defaultGroupSequenceProvider = defaultGroupSequenceProvider;
            context.defaultGroupSequence = Collections.emptyList();
            context.validationOrder = null;
         } else if (defaultGroupSequence != null && !defaultGroupSequence.isEmpty()) {
            context.defaultGroupSequence = getValidDefaultGroupSequence(beanClass, defaultGroupSequence);
            context.validationOrder = validationOrderGenerator.getDefaultValidationOrder(beanClass, context.defaultGroupSequence);
         } else {
            context.defaultGroupSequence = DEFAULT_GROUP_SEQUENCE;
            context.validationOrder = ValidationOrder.DEFAULT_SEQUENCE;
         }

         return context;
      }
   }

   private Set getDirectConstraints() {
      Set constraints = CollectionHelper.newHashSet();
      Set classAndInterfaces = CollectionHelper.newHashSet();
      classAndInterfaces.add(this.beanClass);
      classAndInterfaces.addAll(ClassHierarchyHelper.getDirectlyImplementedInterfaces(this.beanClass));
      Iterator var3 = classAndInterfaces.iterator();

      while(var3.hasNext()) {
         Class clazz = (Class)var3.next();
         Iterator var5 = this.allMetaConstraints.iterator();

         while(var5.hasNext()) {
            MetaConstraint metaConstraint = (MetaConstraint)var5.next();
            if (metaConstraint.getLocation().getDeclaringClass().equals(clazz)) {
               constraints.add(metaConstraint);
            }
         }
      }

      return CollectionHelper.toImmutableSet(constraints);
   }

   private Map bySignature(Set executables) {
      Map theValue = CollectionHelper.newHashMap();
      Iterator var3 = executables.iterator();

      while(var3.hasNext()) {
         ExecutableMetaData executableMetaData = (ExecutableMetaData)var3.next();
         Iterator var5 = executableMetaData.getSignatures().iterator();

         while(var5.hasNext()) {
            String signature = (String)var5.next();
            theValue.put(signature, executableMetaData);
         }
      }

      return theValue;
   }

   private static List getValidDefaultGroupSequence(Class beanClass, List groupSequence) {
      List validDefaultGroupSequence = new ArrayList();
      boolean groupSequenceContainsDefault = false;
      if (groupSequence != null) {
         Iterator var4 = groupSequence.iterator();

         while(var4.hasNext()) {
            Class group = (Class)var4.next();
            if (group.getName().equals(beanClass.getName())) {
               validDefaultGroupSequence.add(Default.class);
               groupSequenceContainsDefault = true;
            } else {
               if (group.getName().equals(Default.class.getName())) {
                  throw LOG.getNoDefaultGroupInGroupSequenceException();
               }

               validDefaultGroupSequence.add(group);
            }
         }
      }

      if (!groupSequenceContainsDefault) {
         throw LOG.getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException(beanClass);
      } else {
         if (LOG.isTraceEnabled()) {
            LOG.tracef("Members of the default group sequence for bean %s are: %s.", beanClass.getName(), validDefaultGroupSequence);
         }

         return validDefaultGroupSequence;
      }
   }

   private boolean hasDefaultGroupSequenceProvider() {
      return this.defaultGroupSequenceProvider != null;
   }

   public String toString() {
      return "BeanMetaDataImpl{beanClass=" + this.beanClass.getSimpleName() + ", constraintCount=" + this.getMetaConstraints().size() + ", cascadedPropertiesCount=" + this.cascadedProperties.size() + ", defaultGroupSequence=" + this.getDefaultGroupSequence((Object)null) + '}';
   }

   private static class DefaultGroupSequenceContext {
      List defaultGroupSequence;
      DefaultGroupSequenceProvider defaultGroupSequenceProvider;
      ValidationOrder validationOrder;

      private DefaultGroupSequenceContext() {
      }

      // $FF: synthetic method
      DefaultGroupSequenceContext(Object x0) {
         this();
      }
   }

   private static class BuilderDelegate {
      private final Class beanClass;
      private final ConstrainedElement constrainedElement;
      private final ConstraintHelper constraintHelper;
      private final ExecutableHelper executableHelper;
      private final TypeResolutionHelper typeResolutionHelper;
      private final ValueExtractorManager valueExtractorManager;
      private final ExecutableParameterNameProvider parameterNameProvider;
      private MetaDataBuilder propertyBuilder;
      private ExecutableMetaData.Builder methodBuilder;
      private final MethodValidationConfiguration methodValidationConfiguration;
      private final int hashCode;

      public BuilderDelegate(Class beanClass, ConstrainedElement constrainedElement, ConstraintHelper constraintHelper, ExecutableHelper executableHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, ExecutableParameterNameProvider parameterNameProvider, MethodValidationConfiguration methodValidationConfiguration) {
         this.beanClass = beanClass;
         this.constrainedElement = constrainedElement;
         this.constraintHelper = constraintHelper;
         this.executableHelper = executableHelper;
         this.typeResolutionHelper = typeResolutionHelper;
         this.valueExtractorManager = valueExtractorManager;
         this.parameterNameProvider = parameterNameProvider;
         this.methodValidationConfiguration = methodValidationConfiguration;
         switch (constrainedElement.getKind()) {
            case FIELD:
               ConstrainedField constrainedField = (ConstrainedField)constrainedElement;
               this.propertyBuilder = new PropertyMetaData.Builder(beanClass, constrainedField, constraintHelper, typeResolutionHelper, valueExtractorManager);
               break;
            case CONSTRUCTOR:
            case METHOD:
               ConstrainedExecutable constrainedExecutable = (ConstrainedExecutable)constrainedElement;
               Member member = constrainedExecutable.getExecutable();
               if (!Modifier.isPrivate(member.getModifiers()) || beanClass == member.getDeclaringClass()) {
                  this.methodBuilder = new ExecutableMetaData.Builder(beanClass, constrainedExecutable, constraintHelper, executableHelper, typeResolutionHelper, valueExtractorManager, parameterNameProvider, methodValidationConfiguration);
               }

               if (constrainedExecutable.isGetterMethod()) {
                  this.propertyBuilder = new PropertyMetaData.Builder(beanClass, constrainedExecutable, constraintHelper, typeResolutionHelper, valueExtractorManager);
               }
               break;
            case TYPE:
               ConstrainedType constrainedType = (ConstrainedType)constrainedElement;
               this.propertyBuilder = new PropertyMetaData.Builder(beanClass, constrainedType, constraintHelper, typeResolutionHelper, valueExtractorManager);
         }

         this.hashCode = this.buildHashCode();
      }

      public boolean add(ConstrainedElement constrainedElement) {
         boolean added = false;
         if (this.methodBuilder != null && this.methodBuilder.accepts(constrainedElement)) {
            this.methodBuilder.add(constrainedElement);
            added = true;
         }

         if (this.propertyBuilder != null && this.propertyBuilder.accepts(constrainedElement)) {
            this.propertyBuilder.add(constrainedElement);
            if (!added && constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.METHOD && this.methodBuilder == null) {
               ConstrainedExecutable constrainedMethod = (ConstrainedExecutable)constrainedElement;
               this.methodBuilder = new ExecutableMetaData.Builder(this.beanClass, constrainedMethod, this.constraintHelper, this.executableHelper, this.typeResolutionHelper, this.valueExtractorManager, this.parameterNameProvider, this.methodValidationConfiguration);
            }

            added = true;
         }

         return added;
      }

      public Set build() {
         Set metaDataSet = CollectionHelper.newHashSet();
         if (this.propertyBuilder != null) {
            metaDataSet.add(this.propertyBuilder.build());
         }

         if (this.methodBuilder != null) {
            metaDataSet.add(this.methodBuilder.build());
         }

         return metaDataSet;
      }

      public int hashCode() {
         return this.hashCode;
      }

      private int buildHashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + this.beanClass.hashCode();
         result = 31 * result + this.constrainedElement.hashCode();
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!super.equals(obj)) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            BuilderDelegate other = (BuilderDelegate)obj;
            if (!this.beanClass.equals(other.beanClass)) {
               return false;
            } else {
               return this.constrainedElement.equals(other.constrainedElement);
            }
         }
      }
   }

   public static class BeanMetaDataBuilder {
      private final ConstraintHelper constraintHelper;
      private final ValidationOrderGenerator validationOrderGenerator;
      private final Class beanClass;
      private final Set builders = CollectionHelper.newHashSet();
      private final ExecutableHelper executableHelper;
      private final TypeResolutionHelper typeResolutionHelper;
      private final ValueExtractorManager valueExtractorManager;
      private final ExecutableParameterNameProvider parameterNameProvider;
      private final MethodValidationConfiguration methodValidationConfiguration;
      private ConfigurationSource sequenceSource;
      private ConfigurationSource providerSource;
      private List defaultGroupSequence;
      private DefaultGroupSequenceProvider defaultGroupSequenceProvider;

      private BeanMetaDataBuilder(ConstraintHelper constraintHelper, ExecutableHelper executableHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, ExecutableParameterNameProvider parameterNameProvider, ValidationOrderGenerator validationOrderGenerator, Class beanClass, MethodValidationConfiguration methodValidationConfiguration) {
         this.beanClass = beanClass;
         this.constraintHelper = constraintHelper;
         this.validationOrderGenerator = validationOrderGenerator;
         this.executableHelper = executableHelper;
         this.typeResolutionHelper = typeResolutionHelper;
         this.valueExtractorManager = valueExtractorManager;
         this.parameterNameProvider = parameterNameProvider;
         this.methodValidationConfiguration = methodValidationConfiguration;
      }

      public static BeanMetaDataBuilder getInstance(ConstraintHelper constraintHelper, ExecutableHelper executableHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, ExecutableParameterNameProvider parameterNameProvider, ValidationOrderGenerator validationOrderGenerator, Class beanClass, MethodValidationConfiguration methodValidationConfiguration) {
         return new BeanMetaDataBuilder(constraintHelper, executableHelper, typeResolutionHelper, valueExtractorManager, parameterNameProvider, validationOrderGenerator, beanClass, methodValidationConfiguration);
      }

      public void add(BeanConfiguration configuration) {
         if (configuration.getBeanClass().equals(this.beanClass)) {
            if (configuration.getDefaultGroupSequence() != null && (this.sequenceSource == null || configuration.getSource().getPriority() >= this.sequenceSource.getPriority())) {
               this.sequenceSource = configuration.getSource();
               this.defaultGroupSequence = configuration.getDefaultGroupSequence();
            }

            if (configuration.getDefaultGroupSequenceProvider() != null && (this.providerSource == null || configuration.getSource().getPriority() >= this.providerSource.getPriority())) {
               this.providerSource = configuration.getSource();
               this.defaultGroupSequenceProvider = configuration.getDefaultGroupSequenceProvider();
            }
         }

         Iterator var2 = configuration.getConstrainedElements().iterator();

         while(var2.hasNext()) {
            ConstrainedElement constrainedElement = (ConstrainedElement)var2.next();
            this.addMetaDataToBuilder(constrainedElement, this.builders);
         }

      }

      private void addMetaDataToBuilder(ConstrainedElement constrainableElement, Set builders) {
         Iterator var3 = builders.iterator();

         boolean foundBuilder;
         do {
            if (!var3.hasNext()) {
               builders.add(new BuilderDelegate(this.beanClass, constrainableElement, this.constraintHelper, this.executableHelper, this.typeResolutionHelper, this.valueExtractorManager, this.parameterNameProvider, this.methodValidationConfiguration));
               return;
            }

            BuilderDelegate builder = (BuilderDelegate)var3.next();
            foundBuilder = builder.add(constrainableElement);
         } while(!foundBuilder);

      }

      public BeanMetaDataImpl build() {
         Set aggregatedElements = CollectionHelper.newHashSet();
         Iterator var2 = this.builders.iterator();

         while(var2.hasNext()) {
            BuilderDelegate builder = (BuilderDelegate)var2.next();
            aggregatedElements.addAll(builder.build());
         }

         return new BeanMetaDataImpl(this.beanClass, this.defaultGroupSequence, this.defaultGroupSequenceProvider, aggregatedElements, this.validationOrderGenerator);
      }
   }
}
