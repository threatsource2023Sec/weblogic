package org.hibernate.validator.internal.cfg.context;

import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext;
import org.hibernate.validator.cfg.context.TypeConstraintMappingContext;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredConstructor;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredField;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethod;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public final class TypeConstraintMappingContextImpl extends ConstraintMappingContextImplBase implements TypeConstraintMappingContext {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Class beanClass;
   private final Set executableContexts = CollectionHelper.newHashSet();
   private final Set propertyContexts = CollectionHelper.newHashSet();
   private final Set configuredMembers = CollectionHelper.newHashSet();
   private List defaultGroupSequence;
   private Class defaultGroupSequenceProviderClass;

   TypeConstraintMappingContextImpl(DefaultConstraintMapping mapping, Class beanClass) {
      super(mapping);
      this.beanClass = beanClass;
      mapping.getAnnotationProcessingOptions().ignoreAnnotationConstraintForClass(beanClass, Boolean.FALSE);
   }

   public TypeConstraintMappingContext constraint(ConstraintDef definition) {
      this.addConstraint(ConfiguredConstraint.forType(definition, this.beanClass));
      return this;
   }

   public TypeConstraintMappingContext ignoreAnnotations() {
      return this.ignoreAnnotations(true);
   }

   public TypeConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
      this.mapping.getAnnotationProcessingOptions().ignoreClassLevelConstraintAnnotations(this.beanClass, ignoreAnnotations);
      return this;
   }

   public TypeConstraintMappingContext ignoreAllAnnotations() {
      this.mapping.getAnnotationProcessingOptions().ignoreAnnotationConstraintForClass(this.beanClass, Boolean.TRUE);
      return this;
   }

   public TypeConstraintMappingContext defaultGroupSequence(Class... defaultGroupSequence) {
      this.defaultGroupSequence = Arrays.asList(defaultGroupSequence);
      return this;
   }

   public TypeConstraintMappingContext defaultGroupSequenceProviderClass(Class defaultGroupSequenceProviderClass) {
      this.defaultGroupSequenceProviderClass = defaultGroupSequenceProviderClass;
      return this;
   }

   public PropertyConstraintMappingContext property(String property, ElementType elementType) {
      Contracts.assertNotNull(property, "The property name must not be null.");
      Contracts.assertNotNull(elementType, "The element type must not be null.");
      Contracts.assertNotEmpty(property, Messages.MESSAGES.propertyNameMustNotBeEmpty());
      Member member = this.getMember(this.beanClass, property, elementType);
      if (member != null && member.getDeclaringClass() == this.beanClass) {
         if (this.configuredMembers.contains(member)) {
            throw LOG.getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException(this.beanClass, property);
         } else {
            PropertyConstraintMappingContextImpl context = new PropertyConstraintMappingContextImpl(this, member);
            this.configuredMembers.add(member);
            this.propertyContexts.add(context);
            return context;
         }
      } else {
         throw LOG.getUnableToFindPropertyWithAccessException(this.beanClass, property, elementType);
      }
   }

   public MethodConstraintMappingContext method(String name, Class... parameterTypes) {
      Contracts.assertNotNull(name, Messages.MESSAGES.methodNameMustNotBeNull());
      Method method = (Method)this.run(GetDeclaredMethod.action(this.beanClass, name, parameterTypes));
      if (method != null && method.getDeclaringClass() == this.beanClass) {
         if (this.configuredMembers.contains(method)) {
            throw LOG.getMethodHasAlreadyBeConfiguredViaProgrammaticApiException(this.beanClass, ExecutableHelper.getExecutableAsString(name, parameterTypes));
         } else {
            MethodConstraintMappingContextImpl context = new MethodConstraintMappingContextImpl(this, method);
            this.configuredMembers.add(method);
            this.executableContexts.add(context);
            return context;
         }
      } else {
         throw LOG.getBeanDoesNotContainMethodException(this.beanClass, name, parameterTypes);
      }
   }

   public ConstructorConstraintMappingContext constructor(Class... parameterTypes) {
      Constructor constructor = (Constructor)this.run(GetDeclaredConstructor.action(this.beanClass, parameterTypes));
      if (constructor != null && constructor.getDeclaringClass() == this.beanClass) {
         if (this.configuredMembers.contains(constructor)) {
            throw LOG.getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException(this.beanClass, ExecutableHelper.getExecutableAsString(this.beanClass.getSimpleName(), parameterTypes));
         } else {
            ConstructorConstraintMappingContextImpl context = new ConstructorConstraintMappingContextImpl(this, constructor);
            this.configuredMembers.add(constructor);
            this.executableContexts.add(context);
            return context;
         }
      } else {
         throw LOG.getBeanDoesNotContainConstructorException(this.beanClass, parameterTypes);
      }
   }

   BeanConfiguration build(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      return new BeanConfiguration(ConfigurationSource.API, this.beanClass, this.buildConstraintElements(constraintHelper, typeResolutionHelper, valueExtractorManager), this.defaultGroupSequence, this.getDefaultGroupSequenceProvider());
   }

   private Set buildConstraintElements(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      Set elements = CollectionHelper.newHashSet();
      elements.add(new ConstrainedType(ConfigurationSource.API, this.beanClass, this.getConstraints(constraintHelper, typeResolutionHelper, valueExtractorManager)));
      Iterator var5 = this.executableContexts.iterator();

      while(var5.hasNext()) {
         ExecutableConstraintMappingContextImpl executableContext = (ExecutableConstraintMappingContextImpl)var5.next();
         elements.add(executableContext.build(constraintHelper, typeResolutionHelper, valueExtractorManager));
      }

      var5 = this.propertyContexts.iterator();

      while(var5.hasNext()) {
         PropertyConstraintMappingContextImpl propertyContext = (PropertyConstraintMappingContextImpl)var5.next();
         elements.add(propertyContext.build(constraintHelper, typeResolutionHelper, valueExtractorManager));
      }

      return elements;
   }

   private DefaultGroupSequenceProvider getDefaultGroupSequenceProvider() {
      return this.defaultGroupSequenceProviderClass != null ? (DefaultGroupSequenceProvider)this.run(NewInstance.action(this.defaultGroupSequenceProviderClass, "default group sequence provider")) : null;
   }

   Class getBeanClass() {
      return this.beanClass;
   }

   protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
      return ConstraintDescriptorImpl.ConstraintType.GENERIC;
   }

   private Member getMember(Class clazz, String property, ElementType elementType) {
      Contracts.assertNotNull(clazz, Messages.MESSAGES.classCannotBeNull());
      if (property != null && property.length() != 0) {
         if (!ElementType.FIELD.equals(elementType) && !ElementType.METHOD.equals(elementType)) {
            throw LOG.getElementTypeHasToBeFieldOrMethodException();
         } else {
            Member member = null;
            if (ElementType.FIELD.equals(elementType)) {
               member = (Member)this.run(GetDeclaredField.action(clazz, property));
            } else {
               String methodName = property.substring(0, 1).toUpperCase(Locale.ROOT) + property.substring(1);
               String[] var6 = ReflectionHelper.PROPERTY_ACCESSOR_PREFIXES;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  String prefix = var6[var8];
                  member = (Member)this.run(GetMethod.action(clazz, prefix + methodName));
                  if (member != null) {
                     break;
                  }
               }
            }

            return member;
         }
      } else {
         throw LOG.getPropertyNameCannotBeNullOrEmptyException();
      }
   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
