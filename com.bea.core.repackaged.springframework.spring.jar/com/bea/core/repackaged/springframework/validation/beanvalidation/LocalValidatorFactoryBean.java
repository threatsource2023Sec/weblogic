package com.bea.core.repackaged.springframework.validation.beanvalidation;

import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.ApplicationContextAware;
import com.bea.core.repackaged.springframework.context.MessageSource;
import com.bea.core.repackaged.springframework.core.DefaultParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.core.ParameterNameDiscoverer;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.validation.Configuration;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidationProviderResolver;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;
import javax.validation.bootstrap.GenericBootstrap;
import javax.validation.bootstrap.ProviderSpecificBootstrap;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

public class LocalValidatorFactoryBean extends SpringValidatorAdapter implements ValidatorFactory, ApplicationContextAware, InitializingBean, DisposableBean {
   @Nullable
   private Class providerClass;
   @Nullable
   private ValidationProviderResolver validationProviderResolver;
   @Nullable
   private MessageInterpolator messageInterpolator;
   @Nullable
   private TraversableResolver traversableResolver;
   @Nullable
   private ConstraintValidatorFactory constraintValidatorFactory;
   @Nullable
   private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
   @Nullable
   private Resource[] mappingLocations;
   private final Map validationPropertyMap = new HashMap();
   @Nullable
   private ApplicationContext applicationContext;
   @Nullable
   private ValidatorFactory validatorFactory;

   public void setProviderClass(Class providerClass) {
      this.providerClass = providerClass;
   }

   public void setValidationProviderResolver(ValidationProviderResolver validationProviderResolver) {
      this.validationProviderResolver = validationProviderResolver;
   }

   public void setMessageInterpolator(MessageInterpolator messageInterpolator) {
      this.messageInterpolator = messageInterpolator;
   }

   public void setValidationMessageSource(MessageSource messageSource) {
      this.messageInterpolator = LocalValidatorFactoryBean.HibernateValidatorDelegate.buildMessageInterpolator(messageSource);
   }

   public void setTraversableResolver(TraversableResolver traversableResolver) {
      this.traversableResolver = traversableResolver;
   }

   public void setConstraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
      this.constraintValidatorFactory = constraintValidatorFactory;
   }

   public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
      this.parameterNameDiscoverer = parameterNameDiscoverer;
   }

   public void setMappingLocations(Resource... mappingLocations) {
      this.mappingLocations = mappingLocations;
   }

   public void setValidationProperties(Properties jpaProperties) {
      CollectionUtils.mergePropertiesIntoMap(jpaProperties, this.validationPropertyMap);
   }

   public void setValidationPropertyMap(@Nullable Map validationProperties) {
      if (validationProperties != null) {
         this.validationPropertyMap.putAll(validationProperties);
      }

   }

   public Map getValidationPropertyMap() {
      return this.validationPropertyMap;
   }

   public void setApplicationContext(ApplicationContext applicationContext) {
      this.applicationContext = applicationContext;
   }

   public void afterPropertiesSet() {
      Configuration configuration;
      if (this.providerClass != null) {
         ProviderSpecificBootstrap bootstrap = Validation.byProvider(this.providerClass);
         if (this.validationProviderResolver != null) {
            bootstrap = bootstrap.providerResolver(this.validationProviderResolver);
         }

         configuration = bootstrap.configure();
      } else {
         GenericBootstrap bootstrap = Validation.byDefaultProvider();
         if (this.validationProviderResolver != null) {
            bootstrap = bootstrap.providerResolver(this.validationProviderResolver);
         }

         configuration = bootstrap.configure();
      }

      if (this.applicationContext != null) {
         try {
            Method eclMethod = configuration.getClass().getMethod("externalClassLoader", ClassLoader.class);
            ReflectionUtils.invokeMethod(eclMethod, configuration, this.applicationContext.getClassLoader());
         } catch (NoSuchMethodException var10) {
         }
      }

      MessageInterpolator targetInterpolator = this.messageInterpolator;
      if (targetInterpolator == null) {
         targetInterpolator = configuration.getDefaultMessageInterpolator();
      }

      configuration.messageInterpolator(new LocaleContextMessageInterpolator(targetInterpolator));
      if (this.traversableResolver != null) {
         configuration.traversableResolver(this.traversableResolver);
      }

      ConstraintValidatorFactory targetConstraintValidatorFactory = this.constraintValidatorFactory;
      if (targetConstraintValidatorFactory == null && this.applicationContext != null) {
         targetConstraintValidatorFactory = new SpringConstraintValidatorFactory(this.applicationContext.getAutowireCapableBeanFactory());
      }

      if (targetConstraintValidatorFactory != null) {
         configuration.constraintValidatorFactory((ConstraintValidatorFactory)targetConstraintValidatorFactory);
      }

      if (this.parameterNameDiscoverer != null) {
         this.configureParameterNameProvider(this.parameterNameDiscoverer, configuration);
      }

      if (this.mappingLocations != null) {
         Resource[] var4 = this.mappingLocations;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Resource location = var4[var6];

            try {
               configuration.addMapping(location.getInputStream());
            } catch (IOException var9) {
               throw new IllegalStateException("Cannot read mapping resource: " + location);
            }
         }
      }

      this.validationPropertyMap.forEach(configuration::addProperty);
      this.postProcessConfiguration(configuration);
      this.validatorFactory = configuration.buildValidatorFactory();
      this.setTargetValidator(this.validatorFactory.getValidator());
   }

   private void configureParameterNameProvider(final ParameterNameDiscoverer discoverer, Configuration configuration) {
      final ParameterNameProvider defaultProvider = configuration.getDefaultParameterNameProvider();
      configuration.parameterNameProvider(new ParameterNameProvider() {
         public List getParameterNames(Constructor constructor) {
            String[] paramNames = discoverer.getParameterNames(constructor);
            return paramNames != null ? Arrays.asList(paramNames) : defaultProvider.getParameterNames(constructor);
         }

         public List getParameterNames(Method method) {
            String[] paramNames = discoverer.getParameterNames(method);
            return paramNames != null ? Arrays.asList(paramNames) : defaultProvider.getParameterNames(method);
         }
      });
   }

   protected void postProcessConfiguration(Configuration configuration) {
   }

   public Validator getValidator() {
      Assert.notNull(this.validatorFactory, (String)"No target ValidatorFactory set");
      return this.validatorFactory.getValidator();
   }

   public ValidatorContext usingContext() {
      Assert.notNull(this.validatorFactory, (String)"No target ValidatorFactory set");
      return this.validatorFactory.usingContext();
   }

   public MessageInterpolator getMessageInterpolator() {
      Assert.notNull(this.validatorFactory, (String)"No target ValidatorFactory set");
      return this.validatorFactory.getMessageInterpolator();
   }

   public TraversableResolver getTraversableResolver() {
      Assert.notNull(this.validatorFactory, (String)"No target ValidatorFactory set");
      return this.validatorFactory.getTraversableResolver();
   }

   public ConstraintValidatorFactory getConstraintValidatorFactory() {
      Assert.notNull(this.validatorFactory, (String)"No target ValidatorFactory set");
      return this.validatorFactory.getConstraintValidatorFactory();
   }

   public ParameterNameProvider getParameterNameProvider() {
      Assert.notNull(this.validatorFactory, (String)"No target ValidatorFactory set");
      return this.validatorFactory.getParameterNameProvider();
   }

   public Object unwrap(@Nullable Class type) {
      if (type == null || !ValidatorFactory.class.isAssignableFrom(type)) {
         try {
            return super.unwrap(type);
         } catch (ValidationException var4) {
         }
      }

      if (this.validatorFactory != null) {
         try {
            return this.validatorFactory.unwrap(type);
         } catch (ValidationException var3) {
            if (ValidatorFactory.class == type) {
               return this.validatorFactory;
            } else {
               throw var3;
            }
         }
      } else {
         throw new ValidationException("Cannot unwrap to " + type);
      }
   }

   public void close() {
      if (this.validatorFactory != null) {
         this.validatorFactory.close();
      }

   }

   public void destroy() {
      this.close();
   }

   private static class HibernateValidatorDelegate {
      public static MessageInterpolator buildMessageInterpolator(MessageSource messageSource) {
         return new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(messageSource));
      }
   }
}
