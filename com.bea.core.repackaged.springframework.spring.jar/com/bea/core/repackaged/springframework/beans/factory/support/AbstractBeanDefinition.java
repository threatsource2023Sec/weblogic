package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeanMetadataAttributeAccessor;
import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.core.io.DescriptiveResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractBeanDefinition extends BeanMetadataAttributeAccessor implements BeanDefinition, Cloneable {
   public static final String SCOPE_DEFAULT = "";
   public static final int AUTOWIRE_NO = 0;
   public static final int AUTOWIRE_BY_NAME = 1;
   public static final int AUTOWIRE_BY_TYPE = 2;
   public static final int AUTOWIRE_CONSTRUCTOR = 3;
   /** @deprecated */
   @Deprecated
   public static final int AUTOWIRE_AUTODETECT = 4;
   public static final int DEPENDENCY_CHECK_NONE = 0;
   public static final int DEPENDENCY_CHECK_OBJECTS = 1;
   public static final int DEPENDENCY_CHECK_SIMPLE = 2;
   public static final int DEPENDENCY_CHECK_ALL = 3;
   public static final String INFER_METHOD = "(inferred)";
   @Nullable
   private volatile Object beanClass;
   @Nullable
   private String scope;
   private boolean abstractFlag;
   private boolean lazyInit;
   private int autowireMode;
   private int dependencyCheck;
   @Nullable
   private String[] dependsOn;
   private boolean autowireCandidate;
   private boolean primary;
   private final Map qualifiers;
   @Nullable
   private Supplier instanceSupplier;
   private boolean nonPublicAccessAllowed;
   private boolean lenientConstructorResolution;
   @Nullable
   private String factoryBeanName;
   @Nullable
   private String factoryMethodName;
   @Nullable
   private ConstructorArgumentValues constructorArgumentValues;
   @Nullable
   private MutablePropertyValues propertyValues;
   private MethodOverrides methodOverrides;
   @Nullable
   private String initMethodName;
   @Nullable
   private String destroyMethodName;
   private boolean enforceInitMethod;
   private boolean enforceDestroyMethod;
   private boolean synthetic;
   private int role;
   @Nullable
   private String description;
   @Nullable
   private Resource resource;

   protected AbstractBeanDefinition() {
      this((ConstructorArgumentValues)null, (MutablePropertyValues)null);
   }

   protected AbstractBeanDefinition(@Nullable ConstructorArgumentValues cargs, @Nullable MutablePropertyValues pvs) {
      this.scope = "";
      this.abstractFlag = false;
      this.lazyInit = false;
      this.autowireMode = 0;
      this.dependencyCheck = 0;
      this.autowireCandidate = true;
      this.primary = false;
      this.qualifiers = new LinkedHashMap();
      this.nonPublicAccessAllowed = true;
      this.lenientConstructorResolution = true;
      this.methodOverrides = new MethodOverrides();
      this.enforceInitMethod = true;
      this.enforceDestroyMethod = true;
      this.synthetic = false;
      this.role = 0;
      this.constructorArgumentValues = cargs;
      this.propertyValues = pvs;
   }

   protected AbstractBeanDefinition(BeanDefinition original) {
      this.scope = "";
      this.abstractFlag = false;
      this.lazyInit = false;
      this.autowireMode = 0;
      this.dependencyCheck = 0;
      this.autowireCandidate = true;
      this.primary = false;
      this.qualifiers = new LinkedHashMap();
      this.nonPublicAccessAllowed = true;
      this.lenientConstructorResolution = true;
      this.methodOverrides = new MethodOverrides();
      this.enforceInitMethod = true;
      this.enforceDestroyMethod = true;
      this.synthetic = false;
      this.role = 0;
      this.setParentName(original.getParentName());
      this.setBeanClassName(original.getBeanClassName());
      this.setScope(original.getScope());
      this.setAbstract(original.isAbstract());
      this.setLazyInit(original.isLazyInit());
      this.setFactoryBeanName(original.getFactoryBeanName());
      this.setFactoryMethodName(original.getFactoryMethodName());
      this.setRole(original.getRole());
      this.setSource(original.getSource());
      this.copyAttributesFrom(original);
      if (original instanceof AbstractBeanDefinition) {
         AbstractBeanDefinition originalAbd = (AbstractBeanDefinition)original;
         if (originalAbd.hasBeanClass()) {
            this.setBeanClass(originalAbd.getBeanClass());
         }

         if (originalAbd.hasConstructorArgumentValues()) {
            this.setConstructorArgumentValues(new ConstructorArgumentValues(original.getConstructorArgumentValues()));
         }

         if (originalAbd.hasPropertyValues()) {
            this.setPropertyValues(new MutablePropertyValues(original.getPropertyValues()));
         }

         if (originalAbd.hasMethodOverrides()) {
            this.setMethodOverrides(new MethodOverrides(originalAbd.getMethodOverrides()));
         }

         this.setAutowireMode(originalAbd.getAutowireMode());
         this.setDependencyCheck(originalAbd.getDependencyCheck());
         this.setDependsOn(originalAbd.getDependsOn());
         this.setAutowireCandidate(originalAbd.isAutowireCandidate());
         this.setPrimary(originalAbd.isPrimary());
         this.copyQualifiersFrom(originalAbd);
         this.setInstanceSupplier(originalAbd.getInstanceSupplier());
         this.setNonPublicAccessAllowed(originalAbd.isNonPublicAccessAllowed());
         this.setLenientConstructorResolution(originalAbd.isLenientConstructorResolution());
         this.setInitMethodName(originalAbd.getInitMethodName());
         this.setEnforceInitMethod(originalAbd.isEnforceInitMethod());
         this.setDestroyMethodName(originalAbd.getDestroyMethodName());
         this.setEnforceDestroyMethod(originalAbd.isEnforceDestroyMethod());
         this.setSynthetic(originalAbd.isSynthetic());
         this.setResource(originalAbd.getResource());
      } else {
         this.setConstructorArgumentValues(new ConstructorArgumentValues(original.getConstructorArgumentValues()));
         this.setPropertyValues(new MutablePropertyValues(original.getPropertyValues()));
         this.setResourceDescription(original.getResourceDescription());
      }

   }

   public void overrideFrom(BeanDefinition other) {
      if (StringUtils.hasLength(other.getBeanClassName())) {
         this.setBeanClassName(other.getBeanClassName());
      }

      if (StringUtils.hasLength(other.getScope())) {
         this.setScope(other.getScope());
      }

      this.setAbstract(other.isAbstract());
      this.setLazyInit(other.isLazyInit());
      if (StringUtils.hasLength(other.getFactoryBeanName())) {
         this.setFactoryBeanName(other.getFactoryBeanName());
      }

      if (StringUtils.hasLength(other.getFactoryMethodName())) {
         this.setFactoryMethodName(other.getFactoryMethodName());
      }

      this.setRole(other.getRole());
      this.setSource(other.getSource());
      this.copyAttributesFrom(other);
      if (other instanceof AbstractBeanDefinition) {
         AbstractBeanDefinition otherAbd = (AbstractBeanDefinition)other;
         if (otherAbd.hasBeanClass()) {
            this.setBeanClass(otherAbd.getBeanClass());
         }

         if (otherAbd.hasConstructorArgumentValues()) {
            this.getConstructorArgumentValues().addArgumentValues(other.getConstructorArgumentValues());
         }

         if (otherAbd.hasPropertyValues()) {
            this.getPropertyValues().addPropertyValues((PropertyValues)other.getPropertyValues());
         }

         if (otherAbd.hasMethodOverrides()) {
            this.getMethodOverrides().addOverrides(otherAbd.getMethodOverrides());
         }

         this.setAutowireMode(otherAbd.getAutowireMode());
         this.setDependencyCheck(otherAbd.getDependencyCheck());
         this.setDependsOn(otherAbd.getDependsOn());
         this.setAutowireCandidate(otherAbd.isAutowireCandidate());
         this.setPrimary(otherAbd.isPrimary());
         this.copyQualifiersFrom(otherAbd);
         this.setInstanceSupplier(otherAbd.getInstanceSupplier());
         this.setNonPublicAccessAllowed(otherAbd.isNonPublicAccessAllowed());
         this.setLenientConstructorResolution(otherAbd.isLenientConstructorResolution());
         if (otherAbd.getInitMethodName() != null) {
            this.setInitMethodName(otherAbd.getInitMethodName());
            this.setEnforceInitMethod(otherAbd.isEnforceInitMethod());
         }

         if (otherAbd.getDestroyMethodName() != null) {
            this.setDestroyMethodName(otherAbd.getDestroyMethodName());
            this.setEnforceDestroyMethod(otherAbd.isEnforceDestroyMethod());
         }

         this.setSynthetic(otherAbd.isSynthetic());
         this.setResource(otherAbd.getResource());
      } else {
         this.getConstructorArgumentValues().addArgumentValues(other.getConstructorArgumentValues());
         this.getPropertyValues().addPropertyValues((PropertyValues)other.getPropertyValues());
         this.setResourceDescription(other.getResourceDescription());
      }

   }

   public void applyDefaults(BeanDefinitionDefaults defaults) {
      this.setLazyInit(defaults.isLazyInit());
      this.setAutowireMode(defaults.getAutowireMode());
      this.setDependencyCheck(defaults.getDependencyCheck());
      this.setInitMethodName(defaults.getInitMethodName());
      this.setEnforceInitMethod(false);
      this.setDestroyMethodName(defaults.getDestroyMethodName());
      this.setEnforceDestroyMethod(false);
   }

   public void setBeanClassName(@Nullable String beanClassName) {
      this.beanClass = beanClassName;
   }

   @Nullable
   public String getBeanClassName() {
      Object beanClassObject = this.beanClass;
      return beanClassObject instanceof Class ? ((Class)beanClassObject).getName() : (String)beanClassObject;
   }

   public void setBeanClass(@Nullable Class beanClass) {
      this.beanClass = beanClass;
   }

   public Class getBeanClass() throws IllegalStateException {
      Object beanClassObject = this.beanClass;
      if (beanClassObject == null) {
         throw new IllegalStateException("No bean class specified on bean definition");
      } else if (!(beanClassObject instanceof Class)) {
         throw new IllegalStateException("Bean class name [" + beanClassObject + "] has not been resolved into an actual Class");
      } else {
         return (Class)beanClassObject;
      }
   }

   public boolean hasBeanClass() {
      return this.beanClass instanceof Class;
   }

   @Nullable
   public Class resolveBeanClass(@Nullable ClassLoader classLoader) throws ClassNotFoundException {
      String className = this.getBeanClassName();
      if (className == null) {
         return null;
      } else {
         Class resolvedClass = ClassUtils.forName(className, classLoader);
         this.beanClass = resolvedClass;
         return resolvedClass;
      }
   }

   public void setScope(@Nullable String scope) {
      this.scope = scope;
   }

   @Nullable
   public String getScope() {
      return this.scope;
   }

   public boolean isSingleton() {
      return "singleton".equals(this.scope) || "".equals(this.scope);
   }

   public boolean isPrototype() {
      return "prototype".equals(this.scope);
   }

   public void setAbstract(boolean abstractFlag) {
      this.abstractFlag = abstractFlag;
   }

   public boolean isAbstract() {
      return this.abstractFlag;
   }

   public void setLazyInit(boolean lazyInit) {
      this.lazyInit = lazyInit;
   }

   public boolean isLazyInit() {
      return this.lazyInit;
   }

   public void setAutowireMode(int autowireMode) {
      this.autowireMode = autowireMode;
   }

   public int getAutowireMode() {
      return this.autowireMode;
   }

   public int getResolvedAutowireMode() {
      if (this.autowireMode == 4) {
         Constructor[] constructors = this.getBeanClass().getConstructors();
         Constructor[] var2 = constructors;
         int var3 = constructors.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Constructor constructor = var2[var4];
            if (constructor.getParameterCount() == 0) {
               return 2;
            }
         }

         return 3;
      } else {
         return this.autowireMode;
      }
   }

   public void setDependencyCheck(int dependencyCheck) {
      this.dependencyCheck = dependencyCheck;
   }

   public int getDependencyCheck() {
      return this.dependencyCheck;
   }

   public void setDependsOn(@Nullable String... dependsOn) {
      this.dependsOn = dependsOn;
   }

   @Nullable
   public String[] getDependsOn() {
      return this.dependsOn;
   }

   public void setAutowireCandidate(boolean autowireCandidate) {
      this.autowireCandidate = autowireCandidate;
   }

   public boolean isAutowireCandidate() {
      return this.autowireCandidate;
   }

   public void setPrimary(boolean primary) {
      this.primary = primary;
   }

   public boolean isPrimary() {
      return this.primary;
   }

   public void addQualifier(AutowireCandidateQualifier qualifier) {
      this.qualifiers.put(qualifier.getTypeName(), qualifier);
   }

   public boolean hasQualifier(String typeName) {
      return this.qualifiers.containsKey(typeName);
   }

   @Nullable
   public AutowireCandidateQualifier getQualifier(String typeName) {
      return (AutowireCandidateQualifier)this.qualifiers.get(typeName);
   }

   public Set getQualifiers() {
      return new LinkedHashSet(this.qualifiers.values());
   }

   public void copyQualifiersFrom(AbstractBeanDefinition source) {
      Assert.notNull(source, (String)"Source must not be null");
      this.qualifiers.putAll(source.qualifiers);
   }

   public void setInstanceSupplier(@Nullable Supplier instanceSupplier) {
      this.instanceSupplier = instanceSupplier;
   }

   @Nullable
   public Supplier getInstanceSupplier() {
      return this.instanceSupplier;
   }

   public void setNonPublicAccessAllowed(boolean nonPublicAccessAllowed) {
      this.nonPublicAccessAllowed = nonPublicAccessAllowed;
   }

   public boolean isNonPublicAccessAllowed() {
      return this.nonPublicAccessAllowed;
   }

   public void setLenientConstructorResolution(boolean lenientConstructorResolution) {
      this.lenientConstructorResolution = lenientConstructorResolution;
   }

   public boolean isLenientConstructorResolution() {
      return this.lenientConstructorResolution;
   }

   public void setFactoryBeanName(@Nullable String factoryBeanName) {
      this.factoryBeanName = factoryBeanName;
   }

   @Nullable
   public String getFactoryBeanName() {
      return this.factoryBeanName;
   }

   public void setFactoryMethodName(@Nullable String factoryMethodName) {
      this.factoryMethodName = factoryMethodName;
   }

   @Nullable
   public String getFactoryMethodName() {
      return this.factoryMethodName;
   }

   public void setConstructorArgumentValues(ConstructorArgumentValues constructorArgumentValues) {
      this.constructorArgumentValues = constructorArgumentValues;
   }

   public ConstructorArgumentValues getConstructorArgumentValues() {
      if (this.constructorArgumentValues == null) {
         this.constructorArgumentValues = new ConstructorArgumentValues();
      }

      return this.constructorArgumentValues;
   }

   public boolean hasConstructorArgumentValues() {
      return this.constructorArgumentValues != null && !this.constructorArgumentValues.isEmpty();
   }

   public void setPropertyValues(MutablePropertyValues propertyValues) {
      this.propertyValues = propertyValues;
   }

   public MutablePropertyValues getPropertyValues() {
      if (this.propertyValues == null) {
         this.propertyValues = new MutablePropertyValues();
      }

      return this.propertyValues;
   }

   public boolean hasPropertyValues() {
      return this.propertyValues != null && !this.propertyValues.isEmpty();
   }

   public void setMethodOverrides(MethodOverrides methodOverrides) {
      this.methodOverrides = methodOverrides;
   }

   public MethodOverrides getMethodOverrides() {
      return this.methodOverrides;
   }

   public boolean hasMethodOverrides() {
      return !this.methodOverrides.isEmpty();
   }

   public void setInitMethodName(@Nullable String initMethodName) {
      this.initMethodName = initMethodName;
   }

   @Nullable
   public String getInitMethodName() {
      return this.initMethodName;
   }

   public void setEnforceInitMethod(boolean enforceInitMethod) {
      this.enforceInitMethod = enforceInitMethod;
   }

   public boolean isEnforceInitMethod() {
      return this.enforceInitMethod;
   }

   public void setDestroyMethodName(@Nullable String destroyMethodName) {
      this.destroyMethodName = destroyMethodName;
   }

   @Nullable
   public String getDestroyMethodName() {
      return this.destroyMethodName;
   }

   public void setEnforceDestroyMethod(boolean enforceDestroyMethod) {
      this.enforceDestroyMethod = enforceDestroyMethod;
   }

   public boolean isEnforceDestroyMethod() {
      return this.enforceDestroyMethod;
   }

   public void setSynthetic(boolean synthetic) {
      this.synthetic = synthetic;
   }

   public boolean isSynthetic() {
      return this.synthetic;
   }

   public void setRole(int role) {
      this.role = role;
   }

   public int getRole() {
      return this.role;
   }

   public void setDescription(@Nullable String description) {
      this.description = description;
   }

   @Nullable
   public String getDescription() {
      return this.description;
   }

   public void setResource(@Nullable Resource resource) {
      this.resource = resource;
   }

   @Nullable
   public Resource getResource() {
      return this.resource;
   }

   public void setResourceDescription(@Nullable String resourceDescription) {
      this.resource = resourceDescription != null ? new DescriptiveResource(resourceDescription) : null;
   }

   @Nullable
   public String getResourceDescription() {
      return this.resource != null ? this.resource.getDescription() : null;
   }

   public void setOriginatingBeanDefinition(BeanDefinition originatingBd) {
      this.resource = new BeanDefinitionResource(originatingBd);
   }

   @Nullable
   public BeanDefinition getOriginatingBeanDefinition() {
      return this.resource instanceof BeanDefinitionResource ? ((BeanDefinitionResource)this.resource).getBeanDefinition() : null;
   }

   public void validate() throws BeanDefinitionValidationException {
      if (this.hasMethodOverrides() && this.getFactoryMethodName() != null) {
         throw new BeanDefinitionValidationException("Cannot combine factory method with container-generated method overrides: the factory method must create the concrete bean instance.");
      } else {
         if (this.hasBeanClass()) {
            this.prepareMethodOverrides();
         }

      }
   }

   public void prepareMethodOverrides() throws BeanDefinitionValidationException {
      if (this.hasMethodOverrides()) {
         this.getMethodOverrides().getOverrides().forEach(this::prepareMethodOverride);
      }

   }

   protected void prepareMethodOverride(MethodOverride mo) throws BeanDefinitionValidationException {
      int count = ClassUtils.getMethodCountForName(this.getBeanClass(), mo.getMethodName());
      if (count == 0) {
         throw new BeanDefinitionValidationException("Invalid method override: no method with name '" + mo.getMethodName() + "' on class [" + this.getBeanClassName() + "]");
      } else {
         if (count == 1) {
            mo.setOverloaded(false);
         }

      }
   }

   public Object clone() {
      return this.cloneBeanDefinition();
   }

   public abstract AbstractBeanDefinition cloneBeanDefinition();

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AbstractBeanDefinition)) {
         return false;
      } else {
         AbstractBeanDefinition that = (AbstractBeanDefinition)other;
         return ObjectUtils.nullSafeEquals(this.getBeanClassName(), that.getBeanClassName()) && ObjectUtils.nullSafeEquals(this.scope, that.scope) && this.abstractFlag == that.abstractFlag && this.lazyInit == that.lazyInit && this.autowireMode == that.autowireMode && this.dependencyCheck == that.dependencyCheck && Arrays.equals(this.dependsOn, that.dependsOn) && this.autowireCandidate == that.autowireCandidate && ObjectUtils.nullSafeEquals(this.qualifiers, that.qualifiers) && this.primary == that.primary && this.nonPublicAccessAllowed == that.nonPublicAccessAllowed && this.lenientConstructorResolution == that.lenientConstructorResolution && ObjectUtils.nullSafeEquals(this.constructorArgumentValues, that.constructorArgumentValues) && ObjectUtils.nullSafeEquals(this.propertyValues, that.propertyValues) && ObjectUtils.nullSafeEquals(this.methodOverrides, that.methodOverrides) && ObjectUtils.nullSafeEquals(this.factoryBeanName, that.factoryBeanName) && ObjectUtils.nullSafeEquals(this.factoryMethodName, that.factoryMethodName) && ObjectUtils.nullSafeEquals(this.initMethodName, that.initMethodName) && this.enforceInitMethod == that.enforceInitMethod && ObjectUtils.nullSafeEquals(this.destroyMethodName, that.destroyMethodName) && this.enforceDestroyMethod == that.enforceDestroyMethod && this.synthetic == that.synthetic && this.role == that.role && super.equals(other);
      }
   }

   public int hashCode() {
      int hashCode = ObjectUtils.nullSafeHashCode((Object)this.getBeanClassName());
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.scope);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.constructorArgumentValues);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.propertyValues);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.factoryBeanName);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.factoryMethodName);
      hashCode = 29 * hashCode + super.hashCode();
      return hashCode;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("class [");
      sb.append(this.getBeanClassName()).append("]");
      sb.append("; scope=").append(this.scope);
      sb.append("; abstract=").append(this.abstractFlag);
      sb.append("; lazyInit=").append(this.lazyInit);
      sb.append("; autowireMode=").append(this.autowireMode);
      sb.append("; dependencyCheck=").append(this.dependencyCheck);
      sb.append("; autowireCandidate=").append(this.autowireCandidate);
      sb.append("; primary=").append(this.primary);
      sb.append("; factoryBeanName=").append(this.factoryBeanName);
      sb.append("; factoryMethodName=").append(this.factoryMethodName);
      sb.append("; initMethodName=").append(this.initMethodName);
      sb.append("; destroyMethodName=").append(this.destroyMethodName);
      if (this.resource != null) {
         sb.append("; defined in ").append(this.resource.getDescription());
      }

      return sb.toString();
   }
}
