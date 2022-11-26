package org.hibernate.validator.cdi;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.WithAnnotations;
import javax.enterprise.util.AnnotationLiteral;
import javax.validation.BootstrapConfiguration;
import javax.validation.Configuration;
import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import org.hibernate.validator.cdi.internal.InheritedMethodsHelper;
import org.hibernate.validator.cdi.internal.ValidationProviderHelper;
import org.hibernate.validator.cdi.internal.ValidatorBean;
import org.hibernate.validator.cdi.internal.ValidatorFactoryBean;
import org.hibernate.validator.cdi.internal.interceptor.ValidationEnabledAnnotatedType;
import org.hibernate.validator.cdi.internal.interceptor.ValidationInterceptor;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ValidationExtension implements Extension {
   private static final Log log = LoggerFactory.make(MethodHandles.lookup());
   private static final EnumSet ALL_EXECUTABLE_TYPES;
   private static final EnumSet DEFAULT_EXECUTABLE_TYPES;
   private final Annotation defaultQualifier = new AnnotationLiteral() {
   };
   private final Annotation hibernateValidatorQualifier = new AnnotationLiteral() {
   };
   private final ExecutableHelper executableHelper;
   private final Validator validator;
   private final ValidatorFactory validatorFactory;
   private final Set globalExecutableTypes;
   private final boolean isExecutableValidationEnabled;
   private Bean defaultValidatorFactoryBean;
   private Bean hibernateValidatorFactoryBean;
   private Bean defaultValidatorBean;
   private Bean hibernateValidatorBean;

   public ValidationExtension() {
      Configuration config = Validation.byDefaultProvider().configure();
      config.parameterNameProvider(config.getDefaultParameterNameProvider());
      BootstrapConfiguration bootstrap = config.getBootstrapConfiguration();
      this.globalExecutableTypes = bootstrap.getDefaultValidatedExecutableTypes();
      this.isExecutableValidationEnabled = bootstrap.isExecutableValidationEnabled();
      this.validatorFactory = config.buildValidatorFactory();
      this.validator = this.validatorFactory.getValidator();
      this.executableHelper = new ExecutableHelper(new TypeResolutionHelper());
   }

   public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery beforeBeanDiscoveryEvent, BeanManager beanManager) {
      Contracts.assertNotNull(beforeBeanDiscoveryEvent, "The BeforeBeanDiscovery event cannot be null");
      Contracts.assertNotNull(beanManager, "The BeanManager cannot be null");
      AnnotatedType annotatedType = beanManager.createAnnotatedType(ValidationInterceptor.class);
      beforeBeanDiscoveryEvent.addAnnotatedType(annotatedType, ValidationInterceptor.class.getName());
   }

   public void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBeanDiscoveryEvent, BeanManager beanManager) {
      Contracts.assertNotNull(afterBeanDiscoveryEvent, "The AfterBeanDiscovery event cannot be null");
      Contracts.assertNotNull(beanManager, "The BeanManager cannot be null");
      ValidationProviderHelper defaultProviderHelper = ValidationProviderHelper.forDefaultProvider(this.validatorFactory);
      ValidationProviderHelper hvProviderHelper = ValidationProviderHelper.forHibernateValidator();
      if (this.defaultValidatorFactoryBean == null) {
         this.defaultValidatorFactoryBean = new ValidatorFactoryBean(beanManager, defaultProviderHelper);
         if (this.hibernateValidatorFactoryBean == null && defaultProviderHelper.isHibernateValidator()) {
            this.hibernateValidatorFactoryBean = this.defaultValidatorFactoryBean;
         }

         afterBeanDiscoveryEvent.addBean(this.defaultValidatorFactoryBean);
      }

      if (this.hibernateValidatorFactoryBean == null) {
         this.hibernateValidatorFactoryBean = new ValidatorFactoryBean(beanManager, hvProviderHelper);
         afterBeanDiscoveryEvent.addBean(this.hibernateValidatorFactoryBean);
      }

      if (this.defaultValidatorBean == null) {
         this.defaultValidatorBean = new ValidatorBean(beanManager, this.defaultValidatorFactoryBean, defaultProviderHelper);
         if (this.hibernateValidatorBean == null && defaultProviderHelper.isHibernateValidator()) {
            this.hibernateValidatorBean = this.defaultValidatorBean;
         }

         afterBeanDiscoveryEvent.addBean(this.defaultValidatorBean);
      }

      if (this.hibernateValidatorBean == null) {
         this.hibernateValidatorBean = new ValidatorBean(beanManager, this.hibernateValidatorFactoryBean, hvProviderHelper);
         afterBeanDiscoveryEvent.addBean(this.hibernateValidatorBean);
      }

   }

   public void processBean(@Observes ProcessBean processBeanEvent) {
      Contracts.assertNotNull(processBeanEvent, "The ProcessBean event cannot be null");
      Bean bean = processBeanEvent.getBean();
      if (!bean.getTypes().contains(ValidatorFactory.class) && !(bean instanceof ValidatorFactoryBean)) {
         if (bean.getTypes().contains(Validator.class) || bean instanceof ValidatorBean) {
            if (bean.getQualifiers().contains(this.defaultQualifier)) {
               this.defaultValidatorBean = bean;
            }

            if (bean.getQualifiers().contains(this.hibernateValidatorQualifier)) {
               this.hibernateValidatorBean = bean;
            }
         }
      } else {
         if (bean.getQualifiers().contains(this.defaultQualifier)) {
            this.defaultValidatorFactoryBean = bean;
         }

         if (bean.getQualifiers().contains(this.hibernateValidatorQualifier)) {
            this.hibernateValidatorFactoryBean = bean;
         }
      }

   }

   public void processAnnotatedType(@Observes @WithAnnotations({Constraint.class, Valid.class, ValidateOnExecution.class}) ProcessAnnotatedType processAnnotatedTypeEvent) {
      Contracts.assertNotNull(processAnnotatedTypeEvent, "The ProcessAnnotatedType event cannot be null");
      if (this.isExecutableValidationEnabled) {
         AnnotatedType type = processAnnotatedTypeEvent.getAnnotatedType();
         Set constrainedCallables = this.determineConstrainedCallables(type);
         if (!constrainedCallables.isEmpty()) {
            ValidationEnabledAnnotatedType wrappedType = new ValidationEnabledAnnotatedType(type, constrainedCallables);
            processAnnotatedTypeEvent.setAnnotatedType(wrappedType);
         }

      }
   }

   private Set determineConstrainedCallables(AnnotatedType type) {
      Set callables = new HashSet();
      BeanDescriptor beanDescriptor = this.validator.getConstraintsForClass(type.getJavaClass());
      this.determineConstrainedConstructors(type, beanDescriptor, callables);
      this.determineConstrainedMethods(type, beanDescriptor, callables);
      return callables;
   }

   private void determineConstrainedMethods(AnnotatedType type, BeanDescriptor beanDescriptor, Set callables) {
      List overriddenAndImplementedMethods = InheritedMethodsHelper.getAllMethods(type.getJavaClass());
      Iterator var5 = type.getMethods().iterator();

      while(var5.hasNext()) {
         AnnotatedMethod annotatedMethod = (AnnotatedMethod)var5.next();
         Method method = annotatedMethod.getJavaMember();
         boolean isGetter = ReflectionHelper.isGetterMethod(method);
         Method methodForExecutableTypeRetrieval = this.replaceWithOverriddenOrInterfaceMethod(method, overriddenAndImplementedMethods);
         EnumSet classLevelExecutableTypes = this.executableTypesDefinedOnType(methodForExecutableTypeRetrieval.getDeclaringClass());
         EnumSet memberLevelExecutableType = this.executableTypesDefinedOnMethod(methodForExecutableTypeRetrieval, isGetter);
         ExecutableType currentExecutableType = isGetter ? ExecutableType.GETTER_METHODS : ExecutableType.NON_GETTER_METHODS;
         if (!this.veto(classLevelExecutableTypes, memberLevelExecutableType, currentExecutableType)) {
            boolean needsValidation;
            if (isGetter) {
               needsValidation = this.isGetterConstrained(method, beanDescriptor);
            } else {
               needsValidation = this.isNonGetterConstrained(method, beanDescriptor);
            }

            if (needsValidation) {
               callables.add(annotatedMethod);
            }
         }
      }

   }

   private void determineConstrainedConstructors(AnnotatedType type, BeanDescriptor beanDescriptor, Set callables) {
      Class clazz = type.getJavaClass();
      EnumSet classLevelExecutableTypes = this.executableTypesDefinedOnType(clazz);
      Iterator var6 = type.getConstructors().iterator();

      while(var6.hasNext()) {
         AnnotatedConstructor annotatedConstructor = (AnnotatedConstructor)var6.next();
         Constructor constructor = annotatedConstructor.getJavaMember();
         EnumSet memberLevelExecutableType = this.executableTypesDefinedOnConstructor(constructor);
         if (!this.veto(classLevelExecutableTypes, memberLevelExecutableType, ExecutableType.CONSTRUCTORS) && beanDescriptor.getConstraintsForConstructor(constructor.getParameterTypes()) != null) {
            callables.add(annotatedConstructor);
         }
      }

   }

   private boolean isNonGetterConstrained(Method method, BeanDescriptor beanDescriptor) {
      return beanDescriptor.getConstraintsForMethod(method.getName(), method.getParameterTypes()) != null;
   }

   private boolean isGetterConstrained(Method method, BeanDescriptor beanDescriptor) {
      String propertyName = ReflectionHelper.getPropertyName(method);
      PropertyDescriptor propertyDescriptor = beanDescriptor.getConstraintsForProperty(propertyName);
      return propertyDescriptor != null && propertyDescriptor.findConstraints().declaredOn(new ElementType[]{ElementType.METHOD}).hasConstraints();
   }

   private boolean veto(EnumSet classLevelExecutableTypes, EnumSet memberLevelExecutableType, ExecutableType currentExecutableType) {
      if (!memberLevelExecutableType.isEmpty()) {
         return !memberLevelExecutableType.contains(currentExecutableType) && !memberLevelExecutableType.contains(ExecutableType.IMPLICIT);
      } else if (classLevelExecutableTypes.isEmpty()) {
         return !this.globalExecutableTypes.contains(currentExecutableType);
      } else {
         return !classLevelExecutableTypes.contains(currentExecutableType) && !classLevelExecutableTypes.contains(ExecutableType.IMPLICIT);
      }
   }

   private EnumSet executableTypesDefinedOnType(Class clazz) {
      ValidateOnExecution validateOnExecutionAnnotation = (ValidateOnExecution)clazz.getAnnotation(ValidateOnExecution.class);
      EnumSet executableTypes = this.commonExecutableTypeChecks(validateOnExecutionAnnotation);
      return executableTypes.contains(ExecutableType.IMPLICIT) ? DEFAULT_EXECUTABLE_TYPES : executableTypes;
   }

   private EnumSet executableTypesDefinedOnMethod(Method method, boolean isGetter) {
      ValidateOnExecution validateOnExecutionAnnotation = (ValidateOnExecution)method.getAnnotation(ValidateOnExecution.class);
      EnumSet executableTypes = this.commonExecutableTypeChecks(validateOnExecutionAnnotation);
      if (executableTypes.contains(ExecutableType.IMPLICIT)) {
         if (isGetter) {
            executableTypes.add(ExecutableType.GETTER_METHODS);
         } else {
            executableTypes.add(ExecutableType.NON_GETTER_METHODS);
         }
      }

      return executableTypes;
   }

   private EnumSet executableTypesDefinedOnConstructor(Constructor constructor) {
      ValidateOnExecution validateOnExecutionAnnotation = (ValidateOnExecution)constructor.getAnnotation(ValidateOnExecution.class);
      EnumSet executableTypes = this.commonExecutableTypeChecks(validateOnExecutionAnnotation);
      if (executableTypes.contains(ExecutableType.IMPLICIT)) {
         executableTypes.add(ExecutableType.CONSTRUCTORS);
      }

      return executableTypes;
   }

   private EnumSet commonExecutableTypeChecks(ValidateOnExecution validateOnExecutionAnnotation) {
      if (validateOnExecutionAnnotation == null) {
         return EnumSet.noneOf(ExecutableType.class);
      } else {
         EnumSet executableTypes = EnumSet.noneOf(ExecutableType.class);
         if (validateOnExecutionAnnotation.type().length == 0) {
            executableTypes.add(ExecutableType.NONE);
         } else {
            Collections.addAll(executableTypes, validateOnExecutionAnnotation.type());
         }

         if (executableTypes.contains(ExecutableType.IMPLICIT) && executableTypes.size() > 1) {
            throw log.getMixingImplicitWithOtherExecutableTypesException();
         } else {
            if (executableTypes.contains(ExecutableType.NONE) && executableTypes.size() > 1) {
               executableTypes.remove(ExecutableType.NONE);
            }

            if (executableTypes.contains(ExecutableType.ALL)) {
               executableTypes = ALL_EXECUTABLE_TYPES;
            }

            return executableTypes;
         }
      }
   }

   private Method replaceWithOverriddenOrInterfaceMethod(Method method, List allMethodsOfType) {
      LinkedList list = new LinkedList(allMethodsOfType);
      Iterator iterator = list.descendingIterator();

      Method overriddenOrInterfaceMethod;
      do {
         if (!iterator.hasNext()) {
            return method;
         }

         overriddenOrInterfaceMethod = (Method)iterator.next();
      } while(!this.executableHelper.overrides(method, overriddenOrInterfaceMethod));

      if (method.getAnnotation(ValidateOnExecution.class) != null) {
         throw log.getValidateOnExecutionOnOverriddenOrInterfaceMethodException(method);
      } else {
         return overriddenOrInterfaceMethod;
      }
   }

   static {
      ALL_EXECUTABLE_TYPES = EnumSet.of(ExecutableType.CONSTRUCTORS, ExecutableType.NON_GETTER_METHODS, ExecutableType.GETTER_METHODS);
      DEFAULT_EXECUTABLE_TYPES = EnumSet.of(ExecutableType.CONSTRUCTORS, ExecutableType.NON_GETTER_METHODS);
   }
}
