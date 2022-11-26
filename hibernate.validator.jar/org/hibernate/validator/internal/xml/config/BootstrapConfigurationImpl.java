package org.hibernate.validator.internal.xml.config;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.validation.BootstrapConfiguration;
import javax.validation.executable.ExecutableType;
import org.hibernate.validator.internal.util.CollectionHelper;

class BootstrapConfigurationImpl implements BootstrapConfiguration {
   private static final Set DEFAULT_VALIDATED_EXECUTABLE_TYPES;
   private static final Set ALL_VALIDATED_EXECUTABLE_TYPES;
   private static final BootstrapConfiguration DEFAULT_BOOTSTRAP_CONFIGURATION;
   private final String defaultProviderClassName;
   private final String constraintValidatorFactoryClassName;
   private final String messageInterpolatorClassName;
   private final String traversableResolverClassName;
   private final String parameterNameProviderClassName;
   private final String clockProviderClassName;
   private final Set valueExtractorClassNames;
   private final Set constraintMappingResourcePaths;
   private final Map properties;
   private final Set validatedExecutableTypes;
   private final boolean isExecutableValidationEnabled;

   private BootstrapConfigurationImpl() {
      this.defaultProviderClassName = null;
      this.constraintValidatorFactoryClassName = null;
      this.messageInterpolatorClassName = null;
      this.traversableResolverClassName = null;
      this.parameterNameProviderClassName = null;
      this.clockProviderClassName = null;
      this.valueExtractorClassNames = new HashSet();
      this.validatedExecutableTypes = DEFAULT_VALIDATED_EXECUTABLE_TYPES;
      this.isExecutableValidationEnabled = true;
      this.constraintMappingResourcePaths = new HashSet();
      this.properties = new HashMap();
   }

   public BootstrapConfigurationImpl(String defaultProviderClassName, String constraintValidatorFactoryClassName, String messageInterpolatorClassName, String traversableResolverClassName, String parameterNameProviderClassName, String clockProviderClassName, Set valueExtractorClassNames, EnumSet validatedExecutableTypes, boolean isExecutableValidationEnabled, Set constraintMappingResourcePaths, Map properties) {
      this.defaultProviderClassName = defaultProviderClassName;
      this.constraintValidatorFactoryClassName = constraintValidatorFactoryClassName;
      this.messageInterpolatorClassName = messageInterpolatorClassName;
      this.traversableResolverClassName = traversableResolverClassName;
      this.parameterNameProviderClassName = parameterNameProviderClassName;
      this.clockProviderClassName = clockProviderClassName;
      this.valueExtractorClassNames = valueExtractorClassNames;
      this.validatedExecutableTypes = this.prepareValidatedExecutableTypes(validatedExecutableTypes);
      this.isExecutableValidationEnabled = isExecutableValidationEnabled;
      this.constraintMappingResourcePaths = constraintMappingResourcePaths;
      this.properties = properties;
   }

   public static BootstrapConfiguration getDefaultBootstrapConfiguration() {
      return DEFAULT_BOOTSTRAP_CONFIGURATION;
   }

   private Set prepareValidatedExecutableTypes(EnumSet validatedExecutableTypes) {
      if (validatedExecutableTypes == null) {
         return DEFAULT_VALIDATED_EXECUTABLE_TYPES;
      } else if (validatedExecutableTypes.contains(ExecutableType.ALL)) {
         return ALL_VALIDATED_EXECUTABLE_TYPES;
      } else if (validatedExecutableTypes.contains(ExecutableType.NONE)) {
         if (validatedExecutableTypes.size() == 1) {
            return Collections.emptySet();
         } else {
            EnumSet preparedValidatedExecutableTypes = EnumSet.copyOf(validatedExecutableTypes);
            preparedValidatedExecutableTypes.remove(ExecutableType.NONE);
            return CollectionHelper.toImmutableSet(preparedValidatedExecutableTypes);
         }
      } else {
         return CollectionHelper.toImmutableSet(validatedExecutableTypes);
      }
   }

   public String getDefaultProviderClassName() {
      return this.defaultProviderClassName;
   }

   public String getConstraintValidatorFactoryClassName() {
      return this.constraintValidatorFactoryClassName;
   }

   public String getMessageInterpolatorClassName() {
      return this.messageInterpolatorClassName;
   }

   public String getTraversableResolverClassName() {
      return this.traversableResolverClassName;
   }

   public String getParameterNameProviderClassName() {
      return this.parameterNameProviderClassName;
   }

   public String getClockProviderClassName() {
      return this.clockProviderClassName;
   }

   public Set getValueExtractorClassNames() {
      return new HashSet(this.valueExtractorClassNames);
   }

   public Set getConstraintMappingResourcePaths() {
      return new HashSet(this.constraintMappingResourcePaths);
   }

   public boolean isExecutableValidationEnabled() {
      return this.isExecutableValidationEnabled;
   }

   public Set getDefaultValidatedExecutableTypes() {
      return new HashSet(this.validatedExecutableTypes);
   }

   public Map getProperties() {
      return new HashMap(this.properties);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("BootstrapConfigurationImpl");
      sb.append("{defaultProviderClassName='").append(this.defaultProviderClassName).append('\'');
      sb.append(", constraintValidatorFactoryClassName='").append(this.constraintValidatorFactoryClassName).append('\'');
      sb.append(", messageInterpolatorClassName='").append(this.messageInterpolatorClassName).append('\'');
      sb.append(", traversableResolverClassName='").append(this.traversableResolverClassName).append('\'');
      sb.append(", parameterNameProviderClassName='").append(this.parameterNameProviderClassName).append('\'');
      sb.append(", clockProviderClassName='").append(this.clockProviderClassName).append('\'');
      sb.append(", validatedExecutableTypes='").append(this.validatedExecutableTypes).append('\'');
      sb.append(", constraintMappingResourcePaths=").append(this.constraintMappingResourcePaths).append('\'');
      sb.append(", properties=").append(this.properties);
      sb.append('}');
      return sb.toString();
   }

   static {
      DEFAULT_VALIDATED_EXECUTABLE_TYPES = Collections.unmodifiableSet(EnumSet.of(ExecutableType.CONSTRUCTORS, ExecutableType.NON_GETTER_METHODS));
      ALL_VALIDATED_EXECUTABLE_TYPES = Collections.unmodifiableSet(EnumSet.complementOf(EnumSet.of(ExecutableType.ALL, ExecutableType.NONE, ExecutableType.IMPLICIT)));
      DEFAULT_BOOTSTRAP_CONFIGURATION = new BootstrapConfigurationImpl();
   }
}
