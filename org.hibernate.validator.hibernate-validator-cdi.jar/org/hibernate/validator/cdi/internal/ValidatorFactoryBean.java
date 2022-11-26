package org.hibernate.validator.cdi.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.validation.BootstrapConfiguration;
import javax.validation.ClockProvider;
import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.classhierarchy.ClassHierarchyHelper;
import org.hibernate.validator.internal.util.classhierarchy.Filter;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.GetInstancesFromServiceLoader;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

public class ValidatorFactoryBean implements Bean, PassivationCapable {
   private final BeanManager beanManager;
   private final Set destructibleResources;
   private final ValidationProviderHelper validationProviderHelper;
   private final Set types;

   public ValidatorFactoryBean(BeanManager beanManager, ValidationProviderHelper validationProviderHelper) {
      this.beanManager = beanManager;
      this.destructibleResources = CollectionHelper.newHashSet(5);
      this.validationProviderHelper = validationProviderHelper;
      this.types = Collections.unmodifiableSet(CollectionHelper.newHashSet(ClassHierarchyHelper.getHierarchy(validationProviderHelper.getValidatorFactoryBeanClass(), new Filter[0])));
   }

   public Class getBeanClass() {
      return this.validationProviderHelper.getValidatorFactoryBeanClass();
   }

   public Set getInjectionPoints() {
      return Collections.emptySet();
   }

   public String getName() {
      return null;
   }

   public Set getQualifiers() {
      return this.validationProviderHelper.getQualifiers();
   }

   public Class getScope() {
      return ApplicationScoped.class;
   }

   public Set getStereotypes() {
      return Collections.emptySet();
   }

   public Set getTypes() {
      return this.types;
   }

   public boolean isAlternative() {
      return false;
   }

   public boolean isNullable() {
      return false;
   }

   public ValidatorFactory create(CreationalContext ctx) {
      Configuration config = this.getConfiguration();
      config.constraintValidatorFactory(this.createConstraintValidatorFactory(config));
      config.messageInterpolator(this.createMessageInterpolator(config));
      config.traversableResolver(this.createTraversableResolver(config));
      config.parameterNameProvider(this.createParameterNameProvider(config));
      config.clockProvider(this.createClockProvider(config));
      this.addValueExtractorBeans(config);
      return config.buildValidatorFactory();
   }

   private void addValueExtractorBeans(Configuration config) {
      Map valueExtractorDescriptors = (Map)this.createValidationXmlValueExtractors(config).stream().collect(Collectors.toMap(ValueExtractorDescriptor::getKey, Function.identity()));
      Iterator var3 = this.createServiceLoaderValueExtractors().iterator();

      ValueExtractorDescriptor valueExtractorDescriptor;
      while(var3.hasNext()) {
         valueExtractorDescriptor = (ValueExtractorDescriptor)var3.next();
         valueExtractorDescriptors.putIfAbsent(valueExtractorDescriptor.getKey(), valueExtractorDescriptor);
      }

      var3 = valueExtractorDescriptors.values().iterator();

      while(var3.hasNext()) {
         valueExtractorDescriptor = (ValueExtractorDescriptor)var3.next();
         config.addValueExtractor(valueExtractorDescriptor.getValueExtractor());
      }

   }

   public void destroy(ValidatorFactory instance, CreationalContext ctx) {
      Iterator var3 = this.destructibleResources.iterator();

      while(var3.hasNext()) {
         DestructibleBeanInstance resource = (DestructibleBeanInstance)var3.next();
         resource.destroy();
      }

      instance.close();
   }

   private MessageInterpolator createMessageInterpolator(Configuration config) {
      BootstrapConfiguration bootstrapConfiguration = config.getBootstrapConfiguration();
      String messageInterpolatorFqcn = bootstrapConfiguration.getMessageInterpolatorClassName();
      if (messageInterpolatorFqcn == null) {
         return config.getDefaultMessageInterpolator();
      } else {
         Class messageInterpolatorClass = (Class)this.run(LoadClass.action(messageInterpolatorFqcn, (ClassLoader)null));
         return (MessageInterpolator)this.createInstance(messageInterpolatorClass);
      }
   }

   private TraversableResolver createTraversableResolver(Configuration config) {
      BootstrapConfiguration bootstrapConfiguration = config.getBootstrapConfiguration();
      String traversableResolverFqcn = bootstrapConfiguration.getTraversableResolverClassName();
      if (traversableResolverFqcn == null) {
         return config.getDefaultTraversableResolver();
      } else {
         Class traversableResolverClass = (Class)this.run(LoadClass.action(traversableResolverFqcn, (ClassLoader)null));
         return (TraversableResolver)this.createInstance(traversableResolverClass);
      }
   }

   private ParameterNameProvider createParameterNameProvider(Configuration config) {
      BootstrapConfiguration bootstrapConfiguration = config.getBootstrapConfiguration();
      String parameterNameProviderFqcn = bootstrapConfiguration.getParameterNameProviderClassName();
      if (parameterNameProviderFqcn == null) {
         return config.getDefaultParameterNameProvider();
      } else {
         Class parameterNameProviderClass = (Class)this.run(LoadClass.action(parameterNameProviderFqcn, (ClassLoader)null));
         return (ParameterNameProvider)this.createInstance(parameterNameProviderClass);
      }
   }

   private ClockProvider createClockProvider(Configuration config) {
      BootstrapConfiguration bootstrapConfiguration = config.getBootstrapConfiguration();
      String clockProviderFqcn = bootstrapConfiguration.getClockProviderClassName();
      if (clockProviderFqcn == null) {
         return config.getDefaultClockProvider();
      } else {
         Class clockProviderClass = (Class)this.run(LoadClass.action(clockProviderFqcn, (ClassLoader)null));
         return (ClockProvider)this.createInstance(clockProviderClass);
      }
   }

   private ConstraintValidatorFactory createConstraintValidatorFactory(Configuration config) {
      BootstrapConfiguration configSource = config.getBootstrapConfiguration();
      String constraintValidatorFactoryFqcn = configSource.getConstraintValidatorFactoryClassName();
      if (constraintValidatorFactoryFqcn == null) {
         return (ConstraintValidatorFactory)this.createInstance(InjectingConstraintValidatorFactory.class);
      } else {
         Class constraintValidatorFactoryClass = (Class)this.run(LoadClass.action(constraintValidatorFactoryFqcn, (ClassLoader)null));
         return (ConstraintValidatorFactory)this.createInstance(constraintValidatorFactoryClass);
      }
   }

   private Set createValidationXmlValueExtractors(Configuration config) {
      BootstrapConfiguration bootstrapConfiguration = config.getBootstrapConfiguration();
      Set valueExtractorFqcns = bootstrapConfiguration.getValueExtractorClassNames();
      Set valueExtractorDescriptors = (Set)valueExtractorFqcns.stream().map((fqcn) -> {
         return (ValueExtractor)this.createInstance((Class)this.run(LoadClass.action(fqcn, (ClassLoader)null)));
      }).map((ve) -> {
         return new ValueExtractorDescriptor(ve);
      }).collect(Collectors.toSet());
      return valueExtractorDescriptors;
   }

   private Set createServiceLoaderValueExtractors() {
      Set valueExtractorDescriptors = new HashSet();
      List valueExtractors = (List)this.run(GetInstancesFromServiceLoader.action((ClassLoader)this.run(GetClassLoader.fromContext()), ValueExtractor.class));
      Iterator var3 = valueExtractors.iterator();

      while(var3.hasNext()) {
         ValueExtractor valueExtractor = (ValueExtractor)var3.next();
         valueExtractorDescriptors.add(new ValueExtractorDescriptor((ValueExtractor)this.injectInstance(valueExtractor)));
      }

      return valueExtractorDescriptors;
   }

   private Object createInstance(Class type) {
      DestructibleBeanInstance destructibleInstance = new DestructibleBeanInstance(this.beanManager, type);
      this.destructibleResources.add(destructibleInstance);
      return destructibleInstance.getInstance();
   }

   private Object injectInstance(Object instance) {
      DestructibleBeanInstance destructibleInstance = new DestructibleBeanInstance(this.beanManager, instance);
      this.destructibleResources.add(destructibleInstance);
      return destructibleInstance.getInstance();
   }

   private Configuration getConfiguration() {
      return this.validationProviderHelper.isDefaultProvider() ? Validation.byDefaultProvider().configure() : Validation.byProvider(HibernateValidator.class).configure();
   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   public String getId() {
      return ValidatorFactoryBean.class.getName() + "_" + (this.validationProviderHelper.isDefaultProvider() ? "default" : "hv");
   }

   public String toString() {
      return "ValidatorFactoryBean [id=" + this.getId() + "]";
   }
}
