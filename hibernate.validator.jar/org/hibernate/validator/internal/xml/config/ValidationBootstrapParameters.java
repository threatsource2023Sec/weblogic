package org.hibernate.validator.internal.xml.config;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.validation.BootstrapConfiguration;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.spi.ValidationProvider;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;

public class ValidationBootstrapParameters {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private ConstraintValidatorFactory constraintValidatorFactory;
   private MessageInterpolator messageInterpolator;
   private TraversableResolver traversableResolver;
   private ParameterNameProvider parameterNameProvider;
   private ClockProvider clockProvider;
   private ValidationProvider provider;
   private Class providerClass = null;
   private final Map configProperties = new HashMap();
   private final Set mappings = new HashSet();
   private final Map valueExtractorDescriptors = new HashMap();

   public ValidationBootstrapParameters() {
   }

   public ValidationBootstrapParameters(BootstrapConfiguration bootstrapConfiguration, ClassLoader externalClassLoader) {
      this.setProviderClass(bootstrapConfiguration.getDefaultProviderClassName(), externalClassLoader);
      this.setMessageInterpolator(bootstrapConfiguration.getMessageInterpolatorClassName(), externalClassLoader);
      this.setTraversableResolver(bootstrapConfiguration.getTraversableResolverClassName(), externalClassLoader);
      this.setConstraintValidatorFactory(bootstrapConfiguration.getConstraintValidatorFactoryClassName(), externalClassLoader);
      this.setParameterNameProvider(bootstrapConfiguration.getParameterNameProviderClassName(), externalClassLoader);
      this.setClockProvider(bootstrapConfiguration.getClockProviderClassName(), externalClassLoader);
      this.setValueExtractors(bootstrapConfiguration.getValueExtractorClassNames(), externalClassLoader);
      this.setMappingStreams(bootstrapConfiguration.getConstraintMappingResourcePaths(), externalClassLoader);
      this.setConfigProperties(bootstrapConfiguration.getProperties());
   }

   public final ConstraintValidatorFactory getConstraintValidatorFactory() {
      return this.constraintValidatorFactory;
   }

   public final void setConstraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
      this.constraintValidatorFactory = constraintValidatorFactory;
   }

   public final MessageInterpolator getMessageInterpolator() {
      return this.messageInterpolator;
   }

   public final void setMessageInterpolator(MessageInterpolator messageInterpolator) {
      this.messageInterpolator = messageInterpolator;
   }

   public final ValidationProvider getProvider() {
      return this.provider;
   }

   public final void setProvider(ValidationProvider provider) {
      this.provider = provider;
   }

   public final Class getProviderClass() {
      return this.providerClass;
   }

   public final void setProviderClass(Class providerClass) {
      this.providerClass = providerClass;
   }

   public final TraversableResolver getTraversableResolver() {
      return this.traversableResolver;
   }

   public final void setTraversableResolver(TraversableResolver traversableResolver) {
      this.traversableResolver = traversableResolver;
   }

   public final void addConfigProperty(String key, String value) {
      this.configProperties.put(key, value);
   }

   public final void addMapping(InputStream in) {
      this.mappings.add(in);
   }

   public final void addAllMappings(Set mappings) {
      this.mappings.addAll(mappings);
   }

   public final Set getMappings() {
      return CollectionHelper.toImmutableSet(this.mappings);
   }

   public final Map getConfigProperties() {
      return CollectionHelper.toImmutableMap(this.configProperties);
   }

   public ParameterNameProvider getParameterNameProvider() {
      return this.parameterNameProvider;
   }

   public void setParameterNameProvider(ParameterNameProvider parameterNameProvider) {
      this.parameterNameProvider = parameterNameProvider;
   }

   public ClockProvider getClockProvider() {
      return this.clockProvider;
   }

   public void setClockProvider(ClockProvider clockProvider) {
      this.clockProvider = clockProvider;
   }

   public Map getValueExtractorDescriptors() {
      return this.valueExtractorDescriptors;
   }

   public void addValueExtractorDescriptor(ValueExtractorDescriptor descriptor) {
      this.valueExtractorDescriptors.put(descriptor.getKey(), descriptor);
   }

   private void setProviderClass(String providerFqcn, ClassLoader externalClassLoader) {
      if (providerFqcn != null) {
         try {
            this.providerClass = (Class)this.run(LoadClass.action(providerFqcn, externalClassLoader));
            LOG.usingValidationProvider(this.providerClass);
         } catch (Exception var4) {
            throw LOG.getUnableToInstantiateValidationProviderClassException(providerFqcn, var4);
         }
      }

   }

   private void setMessageInterpolator(String messageInterpolatorFqcn, ClassLoader externalClassLoader) {
      if (messageInterpolatorFqcn != null) {
         try {
            Class messageInterpolatorClass = (Class)this.run(LoadClass.action(messageInterpolatorFqcn, externalClassLoader));
            this.messageInterpolator = (MessageInterpolator)this.run(NewInstance.action(messageInterpolatorClass, "message interpolator"));
            LOG.usingMessageInterpolator(messageInterpolatorClass);
         } catch (ValidationException var4) {
            throw LOG.getUnableToInstantiateMessageInterpolatorClassException(messageInterpolatorFqcn, var4);
         }
      }

   }

   private void setTraversableResolver(String traversableResolverFqcn, ClassLoader externalClassLoader) {
      if (traversableResolverFqcn != null) {
         try {
            Class clazz = (Class)this.run(LoadClass.action(traversableResolverFqcn, externalClassLoader));
            this.traversableResolver = (TraversableResolver)this.run(NewInstance.action(clazz, "traversable resolver"));
            LOG.usingTraversableResolver(clazz);
         } catch (ValidationException var4) {
            throw LOG.getUnableToInstantiateTraversableResolverClassException(traversableResolverFqcn, var4);
         }
      }

   }

   private void setConstraintValidatorFactory(String constraintValidatorFactoryFqcn, ClassLoader externalClassLoader) {
      if (constraintValidatorFactoryFqcn != null) {
         try {
            Class clazz = (Class)this.run(LoadClass.action(constraintValidatorFactoryFqcn, externalClassLoader));
            this.constraintValidatorFactory = (ConstraintValidatorFactory)this.run(NewInstance.action(clazz, "constraint validator factory class"));
            LOG.usingConstraintValidatorFactory(clazz);
         } catch (ValidationException var4) {
            throw LOG.getUnableToInstantiateConstraintValidatorFactoryClassException(constraintValidatorFactoryFqcn, var4);
         }
      }

   }

   private void setParameterNameProvider(String parameterNameProviderFqcn, ClassLoader externalClassLoader) {
      if (parameterNameProviderFqcn != null) {
         try {
            Class clazz = (Class)this.run(LoadClass.action(parameterNameProviderFqcn, externalClassLoader));
            this.parameterNameProvider = (ParameterNameProvider)this.run(NewInstance.action(clazz, "parameter name provider class"));
            LOG.usingParameterNameProvider(clazz);
         } catch (ValidationException var4) {
            throw LOG.getUnableToInstantiateParameterNameProviderClassException(parameterNameProviderFqcn, var4);
         }
      }

   }

   private void setClockProvider(String clockProviderFqcn, ClassLoader externalClassLoader) {
      if (clockProviderFqcn != null) {
         try {
            Class clazz = (Class)this.run(LoadClass.action(clockProviderFqcn, externalClassLoader));
            this.clockProvider = (ClockProvider)this.run(NewInstance.action(clazz, "clock provider class"));
            LOG.usingClockProvider(clazz);
         } catch (ValidationException var4) {
            throw LOG.getUnableToInstantiateClockProviderClassException(clockProviderFqcn, var4);
         }
      }

   }

   private void setValueExtractors(Set valueExtractorFqcns, ClassLoader externalClassLoader) {
      Iterator var3 = valueExtractorFqcns.iterator();

      while(var3.hasNext()) {
         String valueExtractorFqcn = (String)var3.next();

         ValueExtractor valueExtractor;
         try {
            Class clazz = (Class)this.run(LoadClass.action(valueExtractorFqcn, externalClassLoader));
            valueExtractor = (ValueExtractor)this.run(NewInstance.action(clazz, "value extractor class"));
         } catch (ValidationException var8) {
            throw LOG.getUnableToInstantiateValueExtractorClassException(valueExtractorFqcn, var8);
         }

         ValueExtractorDescriptor descriptor = new ValueExtractorDescriptor(valueExtractor);
         ValueExtractorDescriptor previous = (ValueExtractorDescriptor)this.valueExtractorDescriptors.put(descriptor.getKey(), descriptor);
         if (previous != null) {
            throw LOG.getValueExtractorForTypeAndTypeUseAlreadyPresentException(valueExtractor, previous.getValueExtractor());
         }

         LOG.addingValueExtractor(valueExtractor.getClass());
      }

   }

   private void setMappingStreams(Set mappingFileNames, ClassLoader externalClassLoader) {
      Iterator var3 = mappingFileNames.iterator();

      while(var3.hasNext()) {
         String mappingFileName = (String)var3.next();
         LOG.debugf("Trying to open input stream for %s.", mappingFileName);
         InputStream in = ResourceLoaderHelper.getResettableInputStreamForPath(mappingFileName, externalClassLoader);
         if (in == null) {
            throw LOG.getUnableToOpenInputStreamForMappingFileException(mappingFileName);
         }

         this.mappings.add(in);
      }

   }

   private void setConfigProperties(Map properties) {
      Iterator var2 = properties.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.configProperties.put(entry.getKey(), entry.getValue());
      }

   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
