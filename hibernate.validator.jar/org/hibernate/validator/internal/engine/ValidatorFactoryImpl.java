package org.hibernate.validator.internal.engine;

import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.spi.ConfigurationState;
import org.hibernate.validator.HibernateValidatorContext;
import org.hibernate.validator.HibernateValidatorFactory;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.hibernate.validator.internal.engine.constraintdefinition.ConstraintDefinitionContribution;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorManager;
import org.hibernate.validator.internal.engine.constraintvalidation.HibernateConstraintValidatorInitializationContextImpl;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.engine.scripting.DefaultScriptEvaluatorFactory;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.BeanMetaDataManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.provider.ProgrammaticMetaDataProvider;
import org.hibernate.validator.internal.metadata.provider.XmlMetaDataProvider;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.hibernate.validator.spi.cfg.ConstraintMappingContributor;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorFactory;

public class ValidatorFactoryImpl implements HibernateValidatorFactory {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final ValidatorFactoryScopedContext validatorFactoryScopedContext;
   private final ConstraintValidatorManager constraintValidatorManager;
   private final Set constraintMappings;
   private final ConstraintHelper constraintHelper;
   private final TypeResolutionHelper typeResolutionHelper;
   private final ExecutableHelper executableHelper;
   private final MethodValidationConfiguration methodValidationConfiguration;
   private final XmlMetaDataProvider xmlMetaDataProvider;
   private final ConcurrentMap beanMetaDataManagers;
   private final ValueExtractorManager valueExtractorManager;
   private final ValidationOrderGenerator validationOrderGenerator;

   public ValidatorFactoryImpl(ConfigurationState configurationState) {
      ClassLoader externalClassLoader = getExternalClassLoader(configurationState);
      this.valueExtractorManager = new ValueExtractorManager(configurationState.getValueExtractors());
      this.beanMetaDataManagers = new ConcurrentHashMap();
      this.constraintHelper = new ConstraintHelper();
      this.typeResolutionHelper = new TypeResolutionHelper();
      this.executableHelper = new ExecutableHelper(this.typeResolutionHelper);
      ConfigurationImpl hibernateSpecificConfig = null;
      if (configurationState instanceof ConfigurationImpl) {
         hibernateSpecificConfig = (ConfigurationImpl)configurationState;
      }

      if (configurationState.getMappingStreams().isEmpty()) {
         this.xmlMetaDataProvider = null;
      } else {
         this.xmlMetaDataProvider = new XmlMetaDataProvider(this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, configurationState.getMappingStreams(), externalClassLoader);
      }

      this.constraintMappings = Collections.unmodifiableSet(getConstraintMappings(this.typeResolutionHelper, configurationState, externalClassLoader));
      registerCustomConstraintValidators(this.constraintMappings, this.constraintHelper);
      Map properties = configurationState.getProperties();
      this.methodValidationConfiguration = (new MethodValidationConfiguration.Builder()).allowOverridingMethodAlterParameterConstraint(getAllowOverridingMethodAlterParameterConstraint(hibernateSpecificConfig, properties)).allowMultipleCascadedValidationOnReturnValues(getAllowMultipleCascadedValidationOnReturnValues(hibernateSpecificConfig, properties)).allowParallelMethodsDefineParameterConstraints(getAllowParallelMethodsDefineParameterConstraints(hibernateSpecificConfig, properties)).build();
      this.validatorFactoryScopedContext = new ValidatorFactoryScopedContext(configurationState.getMessageInterpolator(), configurationState.getTraversableResolver(), new ExecutableParameterNameProvider(configurationState.getParameterNameProvider()), configurationState.getClockProvider(), this.getTemporalValidationTolerance(configurationState, properties), getScriptEvaluatorFactory(configurationState, properties, externalClassLoader), getFailFast(hibernateSpecificConfig, properties), getTraversableResolverResultCacheEnabled(hibernateSpecificConfig, properties), this.getConstraintValidatorPayload(hibernateSpecificConfig));
      this.constraintValidatorManager = new ConstraintValidatorManager(configurationState.getConstraintValidatorFactory(), this.validatorFactoryScopedContext.getConstraintValidatorInitializationContext());
      this.validationOrderGenerator = new ValidationOrderGenerator();
      if (LOG.isDebugEnabled()) {
         logValidatorFactoryScopedConfiguration(this.validatorFactoryScopedContext);
      }

   }

   private static ClassLoader getExternalClassLoader(ConfigurationState configurationState) {
      return configurationState instanceof ConfigurationImpl ? ((ConfigurationImpl)configurationState).getExternalClassLoader() : null;
   }

   private static Set getConstraintMappings(TypeResolutionHelper typeResolutionHelper, ConfigurationState configurationState, ClassLoader externalClassLoader) {
      Set constraintMappings = CollectionHelper.newHashSet();
      if (configurationState instanceof ConfigurationImpl) {
         ConfigurationImpl hibernateConfiguration = (ConfigurationImpl)configurationState;
         constraintMappings.addAll(hibernateConfiguration.getProgrammaticMappings());
         ConstraintMappingContributor serviceLoaderBasedContributor = new ServiceLoaderBasedConstraintMappingContributor(typeResolutionHelper, externalClassLoader != null ? externalClassLoader : (ClassLoader)run(GetClassLoader.fromContext()));
         DefaultConstraintMappingBuilder builder = new DefaultConstraintMappingBuilder(constraintMappings);
         serviceLoaderBasedContributor.createConstraintMappings(builder);
      }

      List contributors = getPropertyConfiguredConstraintMappingContributors(configurationState.getProperties(), externalClassLoader);
      Iterator var9 = contributors.iterator();

      while(var9.hasNext()) {
         ConstraintMappingContributor contributor = (ConstraintMappingContributor)var9.next();
         DefaultConstraintMappingBuilder builder = new DefaultConstraintMappingBuilder(constraintMappings);
         contributor.createConstraintMappings(builder);
      }

      return constraintMappings;
   }

   public Validator getValidator() {
      return this.createValidator(this.constraintValidatorManager.getDefaultConstraintValidatorFactory(), this.valueExtractorManager, this.validatorFactoryScopedContext, this.methodValidationConfiguration);
   }

   public MessageInterpolator getMessageInterpolator() {
      return this.validatorFactoryScopedContext.getMessageInterpolator();
   }

   public TraversableResolver getTraversableResolver() {
      return this.validatorFactoryScopedContext.getTraversableResolver();
   }

   public ConstraintValidatorFactory getConstraintValidatorFactory() {
      return this.constraintValidatorManager.getDefaultConstraintValidatorFactory();
   }

   public ParameterNameProvider getParameterNameProvider() {
      return this.validatorFactoryScopedContext.getParameterNameProvider().getDelegate();
   }

   public ExecutableParameterNameProvider getExecutableParameterNameProvider() {
      return this.validatorFactoryScopedContext.getParameterNameProvider();
   }

   public ClockProvider getClockProvider() {
      return this.validatorFactoryScopedContext.getClockProvider();
   }

   public ScriptEvaluatorFactory getScriptEvaluatorFactory() {
      return this.validatorFactoryScopedContext.getScriptEvaluatorFactory();
   }

   public Duration getTemporalValidationTolerance() {
      return this.validatorFactoryScopedContext.getTemporalValidationTolerance();
   }

   public boolean isFailFast() {
      return this.validatorFactoryScopedContext.isFailFast();
   }

   MethodValidationConfiguration getMethodValidationConfiguration() {
      return this.methodValidationConfiguration;
   }

   public boolean isTraversableResolverResultCacheEnabled() {
      return this.validatorFactoryScopedContext.isTraversableResolverResultCacheEnabled();
   }

   ValueExtractorManager getValueExtractorManager() {
      return this.valueExtractorManager;
   }

   public Object unwrap(Class type) {
      if (type.isAssignableFrom(HibernateValidatorFactory.class)) {
         return type.cast(this);
      } else {
         throw LOG.getTypeNotSupportedForUnwrappingException(type);
      }
   }

   public HibernateValidatorContext usingContext() {
      return new ValidatorContextImpl(this);
   }

   public void close() {
      this.constraintValidatorManager.clear();
      this.constraintHelper.clear();
      Iterator var1 = this.beanMetaDataManagers.values().iterator();

      while(var1.hasNext()) {
         BeanMetaDataManager beanMetaDataManager = (BeanMetaDataManager)var1.next();
         beanMetaDataManager.clear();
      }

      this.validatorFactoryScopedContext.getScriptEvaluatorFactory().clear();
      this.valueExtractorManager.clear();
   }

   public ValidatorFactoryScopedContext getValidatorFactoryScopedContext() {
      return this.validatorFactoryScopedContext;
   }

   Validator createValidator(ConstraintValidatorFactory constraintValidatorFactory, ValueExtractorManager valueExtractorManager, ValidatorFactoryScopedContext validatorFactoryScopedContext, MethodValidationConfiguration methodValidationConfiguration) {
      BeanMetaDataManager beanMetaDataManager = (BeanMetaDataManager)this.beanMetaDataManagers.computeIfAbsent(new BeanMetaDataManagerKey(validatorFactoryScopedContext.getParameterNameProvider(), valueExtractorManager, methodValidationConfiguration), (key) -> {
         return new BeanMetaDataManager(this.constraintHelper, this.executableHelper, this.typeResolutionHelper, validatorFactoryScopedContext.getParameterNameProvider(), valueExtractorManager, this.validationOrderGenerator, this.buildDataProviders(), methodValidationConfiguration);
      });
      return new ValidatorImpl(constraintValidatorFactory, beanMetaDataManager, valueExtractorManager, this.constraintValidatorManager, this.validationOrderGenerator, validatorFactoryScopedContext);
   }

   private List buildDataProviders() {
      List metaDataProviders = CollectionHelper.newArrayList();
      if (this.xmlMetaDataProvider != null) {
         metaDataProviders.add(this.xmlMetaDataProvider);
      }

      if (!this.constraintMappings.isEmpty()) {
         metaDataProviders.add(new ProgrammaticMetaDataProvider(this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.constraintMappings));
      }

      return metaDataProviders;
   }

   private static boolean checkPropertiesForBoolean(Map properties, String propertyKey, boolean programmaticValue) {
      boolean value = programmaticValue;
      String propertyStringValue = (String)properties.get(propertyKey);
      if (propertyStringValue != null) {
         value = Boolean.valueOf(propertyStringValue);
      }

      return value;
   }

   private static List getPropertyConfiguredConstraintMappingContributors(Map properties, ClassLoader externalClassLoader) {
      String deprecatedPropertyValue = (String)properties.get("hibernate.validator.constraint_mapping_contributor");
      String propertyValue = (String)properties.get("hibernate.validator.constraint_mapping_contributors");
      if (StringHelper.isNullOrEmptyString(deprecatedPropertyValue) && StringHelper.isNullOrEmptyString(propertyValue)) {
         return Collections.emptyList();
      } else {
         StringBuilder assembledPropertyValue = new StringBuilder();
         if (!StringHelper.isNullOrEmptyString(deprecatedPropertyValue)) {
            assembledPropertyValue.append(deprecatedPropertyValue);
         }

         if (!StringHelper.isNullOrEmptyString(propertyValue)) {
            if (assembledPropertyValue.length() > 0) {
               assembledPropertyValue.append(",");
            }

            assembledPropertyValue.append(propertyValue);
         }

         String[] contributorNames = assembledPropertyValue.toString().split(",");
         List contributors = CollectionHelper.newArrayList(contributorNames.length);
         String[] var7 = contributorNames;
         int var8 = contributorNames.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String contributorName = var7[var9];
            Class contributorType = (Class)run(LoadClass.action(contributorName, externalClassLoader));
            contributors.add(run(NewInstance.action(contributorType, "constraint mapping contributor class")));
         }

         return contributors;
      }
   }

   private static boolean getAllowParallelMethodsDefineParameterConstraints(ConfigurationImpl hibernateSpecificConfig, Map properties) {
      return checkPropertiesForBoolean(properties, "hibernate.validator.allow_parallel_method_parameter_constraint", hibernateSpecificConfig != null ? hibernateSpecificConfig.getMethodValidationConfiguration().isAllowParallelMethodsDefineParameterConstraints() : false);
   }

   private static boolean getAllowMultipleCascadedValidationOnReturnValues(ConfigurationImpl hibernateSpecificConfig, Map properties) {
      return checkPropertiesForBoolean(properties, "hibernate.validator.allow_multiple_cascaded_validation_on_result", hibernateSpecificConfig != null ? hibernateSpecificConfig.getMethodValidationConfiguration().isAllowMultipleCascadedValidationOnReturnValues() : false);
   }

   private static boolean getAllowOverridingMethodAlterParameterConstraint(ConfigurationImpl hibernateSpecificConfig, Map properties) {
      return checkPropertiesForBoolean(properties, "hibernate.validator.allow_parameter_constraint_override", hibernateSpecificConfig != null ? hibernateSpecificConfig.getMethodValidationConfiguration().isAllowOverridingMethodAlterParameterConstraint() : false);
   }

   private static boolean getTraversableResolverResultCacheEnabled(ConfigurationImpl configuration, Map properties) {
      return checkPropertiesForBoolean(properties, "hibernate.validator.enable_traversable_resolver_result_cache", configuration != null ? configuration.isTraversableResolverResultCacheEnabled() : true);
   }

   private static boolean getFailFast(ConfigurationImpl configuration, Map properties) {
      boolean tmpFailFast = configuration != null ? configuration.getFailFast() : false;
      String propertyStringValue = (String)properties.get("hibernate.validator.fail_fast");
      if (propertyStringValue != null) {
         boolean configurationValue = Boolean.valueOf(propertyStringValue);
         if (tmpFailFast && !configurationValue) {
            throw LOG.getInconsistentFailFastConfigurationException();
         }

         tmpFailFast = configurationValue;
      }

      return tmpFailFast;
   }

   private static ScriptEvaluatorFactory getScriptEvaluatorFactory(ConfigurationState configurationState, Map properties, ClassLoader externalClassLoader) {
      if (configurationState instanceof ConfigurationImpl) {
         ConfigurationImpl hibernateSpecificConfig = (ConfigurationImpl)configurationState;
         if (hibernateSpecificConfig.getScriptEvaluatorFactory() != null) {
            LOG.usingScriptEvaluatorFactory(hibernateSpecificConfig.getScriptEvaluatorFactory().getClass());
            return hibernateSpecificConfig.getScriptEvaluatorFactory();
         }
      }

      String scriptEvaluatorFactoryFqcn = (String)properties.get("hibernate.validator.script_evaluator_factory");
      if (scriptEvaluatorFactoryFqcn != null) {
         try {
            Class clazz = (Class)run(LoadClass.action(scriptEvaluatorFactoryFqcn, externalClassLoader));
            ScriptEvaluatorFactory scriptEvaluatorFactory = (ScriptEvaluatorFactory)run(NewInstance.action(clazz, "script evaluator factory class"));
            LOG.usingScriptEvaluatorFactory(clazz);
            return scriptEvaluatorFactory;
         } catch (Exception var6) {
            throw LOG.getUnableToInstantiateScriptEvaluatorFactoryClassException(scriptEvaluatorFactoryFqcn, var6);
         }
      } else {
         return new DefaultScriptEvaluatorFactory(externalClassLoader);
      }
   }

   private Duration getTemporalValidationTolerance(ConfigurationState configurationState, Map properties) {
      if (configurationState instanceof ConfigurationImpl) {
         ConfigurationImpl hibernateSpecificConfig = (ConfigurationImpl)configurationState;
         if (hibernateSpecificConfig.getTemporalValidationTolerance() != null) {
            LOG.logTemporalValidationTolerance(hibernateSpecificConfig.getTemporalValidationTolerance());
            return hibernateSpecificConfig.getTemporalValidationTolerance();
         }
      }

      String temporalValidationToleranceProperty = (String)properties.get("hibernate.validator.temporal_validation_tolerance");
      if (temporalValidationToleranceProperty != null) {
         try {
            Duration tolerance = Duration.ofMillis(Long.parseLong(temporalValidationToleranceProperty)).abs();
            LOG.logTemporalValidationTolerance(tolerance);
            return tolerance;
         } catch (Exception var5) {
            throw LOG.getUnableToParseTemporalValidationToleranceException(temporalValidationToleranceProperty, var5);
         }
      } else {
         return Duration.ZERO;
      }
   }

   private Object getConstraintValidatorPayload(ConfigurationState configurationState) {
      if (configurationState instanceof ConfigurationImpl) {
         ConfigurationImpl hibernateSpecificConfig = (ConfigurationImpl)configurationState;
         if (hibernateSpecificConfig.getConstraintValidatorPayload() != null) {
            LOG.logConstraintValidatorPayload(hibernateSpecificConfig.getConstraintValidatorPayload());
            return hibernateSpecificConfig.getConstraintValidatorPayload();
         }
      }

      return null;
   }

   private static void registerCustomConstraintValidators(Set constraintMappings, ConstraintHelper constraintHelper) {
      Set definedConstraints = CollectionHelper.newHashSet();
      Iterator var3 = constraintMappings.iterator();

      while(var3.hasNext()) {
         DefaultConstraintMapping constraintMapping = (DefaultConstraintMapping)var3.next();
         Iterator var5 = constraintMapping.getConstraintDefinitionContributions().iterator();

         while(var5.hasNext()) {
            ConstraintDefinitionContribution contribution = (ConstraintDefinitionContribution)var5.next();
            processConstraintDefinitionContribution(contribution, constraintHelper, definedConstraints);
         }
      }

   }

   private static void processConstraintDefinitionContribution(ConstraintDefinitionContribution constraintDefinitionContribution, ConstraintHelper constraintHelper, Set definedConstraints) {
      Class constraintType = constraintDefinitionContribution.getConstraintType();
      if (definedConstraints.contains(constraintType)) {
         throw LOG.getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException(constraintType);
      } else {
         definedConstraints.add(constraintType);
         constraintHelper.putValidatorDescriptors(constraintType, constraintDefinitionContribution.getValidatorDescriptors(), constraintDefinitionContribution.includeExisting());
      }
   }

   private static void logValidatorFactoryScopedConfiguration(ValidatorFactoryScopedContext context) {
      LOG.logValidatorFactoryScopedConfiguration(context.getMessageInterpolator().getClass(), "message interpolator");
      LOG.logValidatorFactoryScopedConfiguration(context.getTraversableResolver().getClass(), "traversable resolver");
      LOG.logValidatorFactoryScopedConfiguration(context.getParameterNameProvider().getClass(), "parameter name provider");
      LOG.logValidatorFactoryScopedConfiguration(context.getClockProvider().getClass(), "clock provider");
      LOG.logValidatorFactoryScopedConfiguration(context.getScriptEvaluatorFactory().getClass(), "script evaluator factory");
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   static class ValidatorFactoryScopedContext {
      private final MessageInterpolator messageInterpolator;
      private final TraversableResolver traversableResolver;
      private final ExecutableParameterNameProvider parameterNameProvider;
      private final ClockProvider clockProvider;
      private final Duration temporalValidationTolerance;
      private final ScriptEvaluatorFactory scriptEvaluatorFactory;
      private final boolean failFast;
      private final boolean traversableResolverResultCacheEnabled;
      private final Object constraintValidatorPayload;
      private final HibernateConstraintValidatorInitializationContextImpl constraintValidatorInitializationContext;

      private ValidatorFactoryScopedContext(MessageInterpolator messageInterpolator, TraversableResolver traversableResolver, ExecutableParameterNameProvider parameterNameProvider, ClockProvider clockProvider, Duration temporalValidationTolerance, ScriptEvaluatorFactory scriptEvaluatorFactory, boolean failFast, boolean traversableResolverResultCacheEnabled, Object constraintValidatorPayload) {
         this(messageInterpolator, traversableResolver, parameterNameProvider, clockProvider, temporalValidationTolerance, scriptEvaluatorFactory, failFast, traversableResolverResultCacheEnabled, constraintValidatorPayload, new HibernateConstraintValidatorInitializationContextImpl(scriptEvaluatorFactory, clockProvider, temporalValidationTolerance));
      }

      private ValidatorFactoryScopedContext(MessageInterpolator messageInterpolator, TraversableResolver traversableResolver, ExecutableParameterNameProvider parameterNameProvider, ClockProvider clockProvider, Duration temporalValidationTolerance, ScriptEvaluatorFactory scriptEvaluatorFactory, boolean failFast, boolean traversableResolverResultCacheEnabled, Object constraintValidatorPayload, HibernateConstraintValidatorInitializationContextImpl constraintValidatorInitializationContext) {
         this.messageInterpolator = messageInterpolator;
         this.traversableResolver = traversableResolver;
         this.parameterNameProvider = parameterNameProvider;
         this.clockProvider = clockProvider;
         this.temporalValidationTolerance = temporalValidationTolerance;
         this.scriptEvaluatorFactory = scriptEvaluatorFactory;
         this.failFast = failFast;
         this.traversableResolverResultCacheEnabled = traversableResolverResultCacheEnabled;
         this.constraintValidatorPayload = constraintValidatorPayload;
         this.constraintValidatorInitializationContext = constraintValidatorInitializationContext;
      }

      public MessageInterpolator getMessageInterpolator() {
         return this.messageInterpolator;
      }

      public TraversableResolver getTraversableResolver() {
         return this.traversableResolver;
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

      public HibernateConstraintValidatorInitializationContext getConstraintValidatorInitializationContext() {
         return this.constraintValidatorInitializationContext;
      }

      // $FF: synthetic method
      ValidatorFactoryScopedContext(MessageInterpolator x0, TraversableResolver x1, ExecutableParameterNameProvider x2, ClockProvider x3, Duration x4, ScriptEvaluatorFactory x5, boolean x6, boolean x7, Object x8, Object x9) {
         this(x0, x1, x2, x3, x4, x5, x6, x7, x8);
      }

      // $FF: synthetic method
      ValidatorFactoryScopedContext(MessageInterpolator x0, TraversableResolver x1, ExecutableParameterNameProvider x2, ClockProvider x3, Duration x4, ScriptEvaluatorFactory x5, boolean x6, boolean x7, Object x8, HibernateConstraintValidatorInitializationContextImpl x9, Object x10) {
         this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9);
      }

      static class Builder {
         private final ValidatorFactoryScopedContext defaultContext;
         private MessageInterpolator messageInterpolator;
         private TraversableResolver traversableResolver;
         private ExecutableParameterNameProvider parameterNameProvider;
         private ClockProvider clockProvider;
         private ScriptEvaluatorFactory scriptEvaluatorFactory;
         private Duration temporalValidationTolerance;
         private boolean failFast;
         private boolean traversableResolverResultCacheEnabled;
         private Object constraintValidatorPayload;
         private HibernateConstraintValidatorInitializationContextImpl constraintValidatorInitializationContext;

         Builder(ValidatorFactoryScopedContext defaultContext) {
            Contracts.assertNotNull(defaultContext, "Default context cannot be null.");
            this.defaultContext = defaultContext;
            this.messageInterpolator = defaultContext.messageInterpolator;
            this.traversableResolver = defaultContext.traversableResolver;
            this.parameterNameProvider = defaultContext.parameterNameProvider;
            this.clockProvider = defaultContext.clockProvider;
            this.scriptEvaluatorFactory = defaultContext.scriptEvaluatorFactory;
            this.temporalValidationTolerance = defaultContext.temporalValidationTolerance;
            this.failFast = defaultContext.failFast;
            this.traversableResolverResultCacheEnabled = defaultContext.traversableResolverResultCacheEnabled;
            this.constraintValidatorPayload = defaultContext.constraintValidatorPayload;
            this.constraintValidatorInitializationContext = defaultContext.constraintValidatorInitializationContext;
         }

         public Builder setMessageInterpolator(MessageInterpolator messageInterpolator) {
            if (messageInterpolator == null) {
               this.messageInterpolator = this.defaultContext.messageInterpolator;
            } else {
               this.messageInterpolator = messageInterpolator;
            }

            return this;
         }

         public Builder setTraversableResolver(TraversableResolver traversableResolver) {
            if (traversableResolver == null) {
               this.traversableResolver = this.defaultContext.traversableResolver;
            } else {
               this.traversableResolver = traversableResolver;
            }

            return this;
         }

         public Builder setParameterNameProvider(ParameterNameProvider parameterNameProvider) {
            if (parameterNameProvider == null) {
               this.parameterNameProvider = this.defaultContext.parameterNameProvider;
            } else {
               this.parameterNameProvider = new ExecutableParameterNameProvider(parameterNameProvider);
            }

            return this;
         }

         public Builder setClockProvider(ClockProvider clockProvider) {
            if (clockProvider == null) {
               this.clockProvider = this.defaultContext.clockProvider;
            } else {
               this.clockProvider = clockProvider;
            }

            return this;
         }

         public Builder setTemporalValidationTolerance(Duration temporalValidationTolerance) {
            this.temporalValidationTolerance = temporalValidationTolerance == null ? Duration.ZERO : temporalValidationTolerance.abs();
            return this;
         }

         public Builder setScriptEvaluatorFactory(ScriptEvaluatorFactory scriptEvaluatorFactory) {
            if (scriptEvaluatorFactory == null) {
               this.scriptEvaluatorFactory = this.defaultContext.scriptEvaluatorFactory;
            } else {
               this.scriptEvaluatorFactory = scriptEvaluatorFactory;
            }

            return this;
         }

         public Builder setFailFast(boolean failFast) {
            this.failFast = failFast;
            return this;
         }

         public Builder setTraversableResolverResultCacheEnabled(boolean traversableResolverResultCacheEnabled) {
            this.traversableResolverResultCacheEnabled = traversableResolverResultCacheEnabled;
            return this;
         }

         public Builder setConstraintValidatorPayload(Object constraintValidatorPayload) {
            this.constraintValidatorPayload = constraintValidatorPayload;
            return this;
         }

         public ValidatorFactoryScopedContext build() {
            return new ValidatorFactoryScopedContext(this.messageInterpolator, this.traversableResolver, this.parameterNameProvider, this.clockProvider, this.temporalValidationTolerance, this.scriptEvaluatorFactory, this.failFast, this.traversableResolverResultCacheEnabled, this.constraintValidatorPayload, HibernateConstraintValidatorInitializationContextImpl.of(this.constraintValidatorInitializationContext, this.scriptEvaluatorFactory, this.clockProvider, this.temporalValidationTolerance));
         }
      }
   }

   private static class BeanMetaDataManagerKey {
      private final ExecutableParameterNameProvider parameterNameProvider;
      private final ValueExtractorManager valueExtractorManager;
      private final MethodValidationConfiguration methodValidationConfiguration;
      private final int hashCode;

      public BeanMetaDataManagerKey(ExecutableParameterNameProvider parameterNameProvider, ValueExtractorManager valueExtractorManager, MethodValidationConfiguration methodValidationConfiguration) {
         this.parameterNameProvider = parameterNameProvider;
         this.valueExtractorManager = valueExtractorManager;
         this.methodValidationConfiguration = methodValidationConfiguration;
         this.hashCode = buildHashCode(parameterNameProvider, valueExtractorManager, methodValidationConfiguration);
      }

      private static int buildHashCode(ExecutableParameterNameProvider parameterNameProvider, ValueExtractorManager valueExtractorManager, MethodValidationConfiguration methodValidationConfiguration) {
         int prime = true;
         int result = 1;
         result = 31 * result + (methodValidationConfiguration == null ? 0 : methodValidationConfiguration.hashCode());
         result = 31 * result + (parameterNameProvider == null ? 0 : parameterNameProvider.hashCode());
         result = 31 * result + (valueExtractorManager == null ? 0 : valueExtractorManager.hashCode());
         return result;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            BeanMetaDataManagerKey other = (BeanMetaDataManagerKey)obj;
            return this.methodValidationConfiguration.equals(other.methodValidationConfiguration) && this.parameterNameProvider.equals(other.parameterNameProvider) && this.valueExtractorManager.equals(other.valueExtractorManager);
         }
      }

      public String toString() {
         return "BeanMetaDataManagerKey [parameterNameProvider=" + this.parameterNameProvider + ", valueExtractorManager=" + this.valueExtractorManager + ", methodValidationConfiguration=" + this.methodValidationConfiguration + "]";
      }
   }

   private static class DefaultConstraintMappingBuilder implements ConstraintMappingContributor.ConstraintMappingBuilder {
      private final Set mappings;

      public DefaultConstraintMappingBuilder(Set mappings) {
         this.mappings = mappings;
      }

      public ConstraintMapping addConstraintMapping() {
         DefaultConstraintMapping mapping = new DefaultConstraintMapping();
         this.mappings.add(mapping);
         return mapping;
      }
   }
}
