package weblogic.descriptor.beangen;

import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.descriptor.annotation.AnnotationDefinition;
import weblogic.descriptor.annotation.BeanAnnotations;
import weblogic.descriptor.annotation.GlobalAnnotations;
import weblogic.descriptor.annotation.PropertyAnnotations;
import weblogic.descriptor.codegen.Utils;

public class PropertyDeclaration extends BeanElement implements Comparable {
   private String name;
   private String type;
   private JClass jClass;
   private HashMap methods;
   private JClass componentJClass = null;
   private boolean isReadOnly = true;
   private boolean hasCreator = false;
   private boolean hasAddressableComponents = false;
   private boolean isSuperProperty = false;
   protected PropertyMethodDeclaration getter;
   protected PropertyMethodDeclaration setter;
   private PropertyMethodDeclaration adder = null;
   private PropertyMethodDeclaration destroyer = null;
   private PropertyMethodDeclaration remover = null;
   private int index;
   protected PropertyImplementation implementation;
   private PropertyType propertyType = null;
   private ArrayList postGenValidationList = new ArrayList();
   private boolean hasRestrictiveAccess = false;
   private PropertyDeclaration delegatedProperty = null;

   protected PropertyDeclaration(PropertyMethodDeclaration getter, int index) {
      super(getter.getMethod());
      this.name = getter.getPropertyName();
      this.type = getter.getReturnType();
      this.jClass = getter.getReturnJClass();
      this.index = index;
      if (this.jClass.isArrayType()) {
         this.componentJClass = this.jClass.getArrayComponentType();
      }

      this.getter = getter;
      this.methods = new HashMap();
      this.addMethod(getter, false);
   }

   public BeanClass getBean() {
      return this.getter.getBean();
   }

   public String getName() {
      return this.name;
   }

   public String getDecapitalizedName() {
      return Introspector.decapitalize(this.name);
   }

   public String getType() {
      return this.type;
   }

   public int getIndex() {
      return this.index;
   }

   void setIndex(int index) {
      this.index = index;
   }

   public boolean isBean() {
      return Context.get().isBean(this.getComponentJClass());
   }

   /** @deprecated */
   @Deprecated
   public boolean isSuperProperty() {
      return this.isSuperProperty || this.isAnnotationDefined(PropertyAnnotations.ALLOWS_SUBTYPES);
   }

   public boolean isChild() {
      if (this.isBean() && !this.isTransient()) {
         if (this.hasCreator || this.isReadOnly && !this.isAnnotationDefined(PropertyAnnotations.REFERENCE)) {
            return true;
         } else if (this.getGetter() != null && !this.getGetter().isAnnotationDefined(GlobalAnnotations.INTERNAL) && this.getSetter() != null && this.getSetter().isAnnotationDefined(GlobalAnnotations.INTERNAL) && !this.isAnnotationDefined(PropertyAnnotations.REFERENCE)) {
            return true;
         } else {
            return this.getGetter() != null && !this.getGetter().isAnnotationDefined(GlobalAnnotations.INTERNAL) && this.getSetter() == null && this.getAdder() != null && this.getAdder().isAnnotationDefined(GlobalAnnotations.INTERNAL) && !this.isAnnotationDefined(PropertyAnnotations.REFERENCE);
         }
      } else {
         return false;
      }
   }

   public boolean isReferenceable() {
      return BeanAnnotations.REFERENCEABLE.isDefined(this.getComponentJClass());
   }

   public boolean isReadOnly() {
      return this.isReadOnly;
   }

   public boolean isReference() {
      return this.isBean() && !this.isChild() && !this.isTransient();
   }

   public boolean isDeclaredEncrypted() {
      return this.isAnnotationDefined(PropertyAnnotations.ENCRYPTED) && (this.getBean().getCustomizer() == null || !this.getBean().getCustomizer().definesMethod(this.getGetter()));
   }

   public boolean isTransient() {
      return this.isAnnotationDefined(PropertyAnnotations.TRANSIENT) || this.isDeclaredEncrypted();
   }

   public boolean isPreviouslyPersisted() {
      return this.isDeclaredEncrypted();
   }

   public boolean isExcludedFromSchema() {
      return this.isTransient() || this.isPreviouslyPersisted() || this.isObsolete();
   }

   public String getInitializer() {
      String str = this.getAnnotationString(PropertyAnnotations.INITIALIZER);
      return str == null ? "" : str;
   }

   public boolean isArray() {
      return this.jClass.isArrayType();
   }

   public boolean hasAddressableComponents() {
      return this.hasAddressableComponents;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasComponents() {
      return this.hasAddressableComponents();
   }

   public String getComponentImplType() {
      Context context = Context.get();
      BeanGenOptions options = context.getOptions();
      JClass componentClass = this.getComponentJClass();
      String implClassName = componentClass.getQualifiedName();
      implClassName = implClassName + options.getSuffix();
      return context.abbreviateClass(implClassName);
   }

   public String getComponentType() {
      return Context.get().abbreviateClass(this.getComponentJClass().getQualifiedName());
   }

   protected JClass getComponentJClass() {
      return this.componentJClass != null ? this.componentJClass : this.jClass;
   }

   public String getAbsoluteType() {
      return this.getComponentType();
   }

   public String getComponentName() {
      String cName = this.getAnnotationString(PropertyAnnotations.COMPONENT_NAME);
      String name = this.getName();
      if (cName != null) {
         return cName;
      } else {
         return name.endsWith("List") ? name.substring(0, name.length() - 4) : Utils.singular(name);
      }
   }

   public JAnnotationValue getAnnotationValue(AnnotationDefinition tag) {
      JAnnotationValue annotation = this.getter.getAnnotationValue(tag);
      if (annotation == null && this.setter != null) {
         annotation = this.setter.getAnnotationValue(tag);
      }

      return annotation;
   }

   public PropertyDeclaration getDelegatedProperty() {
      return this.delegatedProperty;
   }

   public void setDelegatedProperty(PropertyDeclaration p) {
      this.delegatedProperty = p;
   }

   protected String[] getAllAnnotationNames() {
      ArrayList annotations = new ArrayList(Arrays.asList(this.getter.getAllAnnotationNames()));
      if (this.setter != null) {
         annotations.addAll(new ArrayList(Arrays.asList(this.setter.getAllAnnotationNames())));
      }

      return (String[])((String[])annotations.toArray(new String[0]));
   }

   public JClass getJClass() {
      return this.jClass;
   }

   public PropertyMethodDeclaration getGetter() {
      return this.getter;
   }

   public MethodDeclaration getSetter() {
      return this.setter;
   }

   public MethodDeclaration getDestroyer() {
      return this.destroyer;
   }

   public MethodDeclaration getAdder() {
      return this.adder;
   }

   public MethodDeclaration getRemover() {
      return this.remover;
   }

   public PropertyMethodDeclaration[] getMethods() {
      return (PropertyMethodDeclaration[])((PropertyMethodDeclaration[])this.methods.values().toArray(new PropertyMethodDeclaration[0]));
   }

   public PropertyMethodDeclaration findMethod(PropertyMethodType type) {
      Iterator it = this.methods.values().iterator();

      PropertyMethodDeclaration md;
      do {
         if (!it.hasNext()) {
            return null;
         }

         md = (PropertyMethodDeclaration)it.next();
      } while(!md.isType(type));

      return md;
   }

   public PropertyImplementation getImplementation() {
      return this.implementation;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer(this.getName());
      if (this.isChild()) {
         buf.append(" (child)");
      }

      if (this.isReadOnly()) {
         buf.append(" (read-only)");
      }

      return buf.toString();
   }

   public int compareTo(Object o) {
      return !(o instanceof PropertyDeclaration) ? -1 : this.getGetter().compareTo(((PropertyDeclaration)o).getGetter());
   }

   public int hashCode() {
      return this.getGetter().hashCode();
   }

   public boolean equals(Object o) {
      return !(o instanceof PropertyDeclaration) ? false : this.getGetter().equals(((PropertyDeclaration)o).getGetter());
   }

   protected void addMethod(PropertyMethodDeclaration pm, boolean isSynthetic) {
      if (!isSynthetic || !this.methods.containsKey(pm)) {
         if (pm.isType(PropertyMethodType.ADDER)) {
            this.componentJClass = pm.getPropertyJClass();
            this.hasAddressableComponents = true;
         } else if (pm.isType(PropertyMethodType.CREATOR)) {
            this.hasCreator = true;
            if (this.isArray()) {
               this.componentJClass = this.jClass.getArrayComponentType();
               this.hasAddressableComponents = true;
            }

            JClass returnJClass = pm.getReturnJClass();
            if (!returnJClass.equals(this.getComponentJClass()) && this.componentJClass.isAssignableFrom(returnJClass)) {
               this.isSuperProperty = true;
            }
         }

         if (!pm.isType(PropertyMethodType.GETTER) && !pm.isFinder() && !isSynthetic) {
            this.isReadOnly = false;
            if (pm.isType(PropertyMethodType.SETTER)) {
               this.setter = pm;
            } else if (pm.isRemover()) {
               this.remover = pm;
            } else if (pm.isDestroyer()) {
               this.destroyer = pm;
            } else if (pm.isAdder()) {
               this.adder = pm;
            }
         }

         this.methods.put(pm, pm);
         pm.setProperty(this);
      }

   }

   private boolean skipPropertyValidation() {
      return this.isAnnotationDefined(PropertyAnnotations.VALIDATE_PROPERTY_DECLARATION) && !this.isAnnotationTrue(PropertyAnnotations.VALIDATE_PROPERTY_DECLARATION);
   }

   private void validateDeclaration() {
      this.propertyType = PropertyType.getPropertyType(this);
      if (this.getter.isDeclared() && !this.skipPropertyValidation()) {
         this.postGenValidationList.addAll(this.propertyType.validateDeclaration(this));
      }

   }

   public boolean hasTransientOverride() {
      this.propertyType = PropertyType.getPropertyType(this);
      return this.skipPropertyValidation() ? this.propertyType.hasTransientPropertyOverride(this) : false;
   }

   private void validateGeneration() {
      if (!Context.get().getNoSynthetics() && this.getter.isDeclared() && !this.skipPropertyValidation()) {
         if (this.propertyType == null) {
            this.validateDeclaration();
         }

         this.postGenValidationList.addAll(this.propertyType.validateGeneration(this));
      }

   }

   PropertyImplementation implement(BeanCustomizer customizer) {
      this.validateDeclaration();
      this.implementation = new PropertyImplementation(this, customizer);
      this.validateGeneration();
      return this.implementation;
   }

   PropertyImplementation implement(BeanCustomizer customizer, int delegateType) {
      this.validateDeclaration();
      this.implementation = new PropertyImplementation(this, customizer, delegateType);
      this.validateGeneration();
      return this.implementation;
   }

   public String getValueFromString(String arg) {
      String typeName;
      if (!this.isBean()) {
         typeName = this.getJClass().getQualifiedName();
      } else {
         typeName = this.hasAddressableComponents() ? this.getComponentJClass().getQualifiedName() : this.getJClass().getQualifiedName();
      }

      if (typeName.equals(Boolean.TYPE.getName())) {
         return "Boolean.valueOf((String)" + arg + ").booleanValue()";
      } else if (typeName.equals(Byte.TYPE.getName())) {
         return "Byte.valueOf((String)" + arg + ").byteValue()";
      } else if (typeName.equals(Character.TYPE.getName())) {
         return "Character.valueOf((String)" + arg + ").charValue()";
      } else if (typeName.equals(Double.TYPE.getName())) {
         return "Double.valueOf((String)" + arg + ").doubleValue()";
      } else if (typeName.equals(Float.TYPE.getName())) {
         return "Float.valueOf((String)" + arg + ").floatValue()";
      } else if (typeName.equals(Integer.TYPE.getName())) {
         return "Integer.valueOf((String)" + arg + ").intValue()";
      } else if (typeName.equals(Long.TYPE.getName())) {
         return "Long.valueOf((String)" + arg + ").longValue()";
      } else if (typeName.equals(Short.TYPE.getName())) {
         return "Short.valueOf((String)" + arg + ").shortValue()";
      } else if (typeName.equals("java.lang.String[]")) {
         return "parseStringArrayInitializer((String)" + arg + ")";
      } else if (typeName.equals(Integer.class.getName())) {
         return "new Integer((String)" + arg + ")";
      } else if (typeName.equals(Boolean.class.getName())) {
         return "new Boolean((String)" + arg + ")";
      } else if (typeName.equals(Properties.class.getName())) {
         return "parsePropertiesInitializer((String)" + arg + ")";
      } else if (typeName.equals(Hashtable.class.getName())) {
         return "parseHashtableInitializer((String)" + arg + ")";
      } else if (typeName.equals(Map.class.getName())) {
         return "parseMapInitializer((String)" + arg + ")";
      } else if (typeName.equals(Set.class.getName())) {
         return "parseHashSetInitializer((String)" + arg + ")";
      } else {
         return typeName.equals("byte[]") ? "(" + arg + "==null ? null : ((String)" + arg + ").getBytes())" : "((" + typeName + ")" + arg + ")";
      }
   }

   public BeanClass.BindingInfo getBindingInfo() {
      return new BeanClass.BindingInfo(this);
   }

   public String getElementNameAnnotation() {
      return this.getAnnotationString(PropertyAnnotations.XML_ELEMENT_NAME);
   }

   public String getXMLElementName() {
      String eName = this.getElementNameAnnotation();
      return eName != null ? eName : XMLHelper.toElementName(this.isArray() ? this.getComponentName() : this.getName());
   }

   public boolean isDynamic() {
      return this.isAnnotationTrue(GlobalAnnotations.DYNAMIC);
   }

   public boolean isAttribute() {
      return this.isAnnotationDefined(PropertyAnnotations.ATTRIBUTE);
   }

   public String getDefault() {
      return this.getAnnotationString(PropertyAnnotations.DEFAULT);
   }

   public String getLegalValues() {
      return this.getAnnotationString(PropertyAnnotations.ENUMERATION);
   }

   public String getDerivedDefault() {
      return this.getAnnotationString(PropertyAnnotations.DERIVED_DEFAULT);
   }

   public String getRemoveValidator() {
      return this.propertyType == null ? null : this.propertyType.getRemoveValidator(this);
   }

   public boolean isRequired() {
      return this.isAnnotationTrue(PropertyAnnotations.REQUIRED);
   }

   public boolean isConfigurable() {
      return this.isAnnotationDefined(GlobalAnnotations.CONFIGURABLE);
   }

   public boolean isKey() {
      return this.isAnnotationDefined(PropertyAnnotations.KEY);
   }

   public boolean isKeyChoice() {
      return this.isAnnotationDefined(PropertyAnnotations.KEY_CHOICE);
   }

   public boolean isKeyComponent() {
      return this.isAnnotationDefined(PropertyAnnotations.KEY_COMPONENT);
   }

   public boolean arrayOrderSensitive() {
      return this.isArray() && this.isAnnotationDefined(PropertyAnnotations.ARRAYORDERSENSITIVE) ? this.getAnnotationValue(PropertyAnnotations.ARRAYORDERSENSITIVE).asBoolean() : false;
   }

   public boolean isMergeRuleIgnoreSourceDefined() {
      return this.isAnnotationDefined(PropertyAnnotations.MERGERULE) && this.getAnnotationValue(PropertyAnnotations.MERGERULE).asString().equals("ignoreSource");
   }

   public boolean isMergeRuleIgnoreTargetDefined() {
      return this.isAnnotationDefined(PropertyAnnotations.MERGERULE) && this.getAnnotationValue(PropertyAnnotations.MERGERULE).asString().equals("ignoreTarget");
   }

   public boolean isMergeRulePrependDefined() {
      return this.isAnnotationDefined(PropertyAnnotations.MERGERULE) && this.getAnnotationValue(PropertyAnnotations.MERGERULE).asString().equals("prepend");
   }

   public boolean hasRestarts() {
      return this.isAnnotationDefined(GlobalAnnotations.RESTARTS);
   }

   public String getRestartsMethod() {
      return this.getAnnotationString(GlobalAnnotations.RESTARTS);
   }

   public boolean isEmptyRestarts() {
      return this.getAnnotationString(GlobalAnnotations.RESTARTS) == null;
   }

   public boolean hasAnnotationListener() {
      return this.isAnnotationDefined(PropertyAnnotations.META_DATA);
   }

   public boolean needsPostGenValidation() {
      return !this.postGenValidationList.isEmpty();
   }

   public PostGenValidation[] getPostGenValidations() {
      return (PostGenValidation[])((PostGenValidation[])this.postGenValidationList.toArray(new PostGenValidation[0]));
   }

   public PropertyMethodDeclaration getEncryptionDelgateGetterTemplate() {
      return new PropertyMethodDeclaration(this.getBean(), PropertyMethodType.GETTER, new SyntheticJMethod(JClasses.BYTE_ARRAY, "get" + this.name + "Encrypted", PropertyMethodType.NO_ARGS, PropertyMethodType.NO_EXCEPTIONS));
   }

   public PropertyMethodDeclaration getEncryptionDelgateSetterTemplate() {
      return new PropertyMethodDeclaration(this.getBean(), PropertyMethodType.SETTER, new SyntheticJMethod(JClasses.VOID, "set" + this.name + "Encrypted", new JClass[]{JClasses.BYTE_ARRAY}, PropertyMethodType.NO_EXCEPTIONS));
   }

   public void setRestrictiveAccess() {
      this.hasRestrictiveAccess = true;
   }

   public boolean hasRestrictiveAccess() {
      return this.hasRestrictiveAccess || this.isAnnotationDefined(PropertyAnnotations.ENCRYPTED);
   }

   public String getDeprecatedString() {
      String result = this.getAnnotationString("deprecated");
      if (result != null && !"".equals(result)) {
         int idx = result.indexOf("\n");
         if (idx >= 0) {
            result = result.substring(0, idx);
         }
      } else {
         result = "<unknown>";
      }

      return result;
   }

   public String getJClassAnnotationString(String name) {
      BeanClass beanClass = new BeanClass(this.getComponentJClass());
      return beanClass.getAnnotationString(name);
   }

   public boolean isAnnotationDefinedOnPropertyInterface(String name) {
      BeanClass beanClass = new BeanClass(this.getComponentJClass());
      return beanClass.isAnnotationDefined(name);
   }
}
