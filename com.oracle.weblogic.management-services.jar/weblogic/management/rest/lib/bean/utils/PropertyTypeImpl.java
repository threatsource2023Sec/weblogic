package weblogic.management.rest.lib.bean.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.debug.DebugLogger;

class PropertyTypeImpl extends AttributeTypeImpl implements PropertyType {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(PropertyTypeImpl.class);
   private boolean ignore;
   private boolean identity;
   private boolean encrypted;
   private boolean sensitive;
   private boolean isDerivedDefault;
   private boolean hasDefaultValue;
   private Object defaultValue;
   private boolean hasProductionModeDefaultValue;
   private Object productionModeDefaultValue;
   private boolean hasSecureValue;
   private Object secureValue;
   private boolean secureValueDocOnly;
   private boolean isCustomMarshaller;
   private boolean isCustomUnmarshaller;
   private PropertyMarshaller marshaller;
   private PropertyUnmarshaller unmarshaller;
   private Number minValue;
   private Number maxValue;
   private Object[] legalValues;
   private boolean isNullable;
   private boolean isWritable;
   private boolean isUnharvestable;
   private static final String[] RESERVED_PROPERTY_NAMES = new String[]{"links", "messages"};
   private static final String[] RESERVED_TASK_PROPERTY_NAMES = new String[]{"startTime", "endTime", "completed", "status", "requestStatus", "intervalToPoll"};
   private static final String[] RESERVED_DPO_PROPERTY_NAMES = new String[]{"progress"};

   PropertyTypeImpl(HttpServletRequest request, BeanType beanType, PropertyDescriptor pd) throws Exception {
      super(beanType, pd);
      this.identity = DescriptorUtils.getBooleanField(pd, "key");
      this.sensitive = DescriptorUtils.getBooleanField(pd, "sensitive");
      this.encrypted = DescriptorUtils.getBooleanField(pd, "encrypted");
      this.isNullable = DescriptorUtils.getBooleanField(pd, "legalNull");
      this.isUnharvestable = DescriptorUtils.getBooleanField(pd, "unharvestable");
      BeanResourceRegistry registry = BeanResourceRegistry.instance();
      boolean restReadOnlySpecified = pd.getValue("restReadOnly") != null;
      boolean restReadOnly = DescriptorUtils.getBooleanField(pd, "restReadOnly");
      if (pd.getWriteMethod() != null) {
         if (restReadOnlySpecified) {
            if (restReadOnly) {
               this.isWritable = false;
            } else {
               this.isWritable = true;
            }
         } else if (this.isIdentity()) {
            this.isWritable = false;
         } else {
            this.isWritable = true;
         }
      } else {
         this.isWritable = false;
         if (restReadOnlySpecified && !restReadOnly && DEBUG.isDebugEnabled()) {
            DEBUG.debug("warning - readonly property has restReadOnly explicitly set to false : " + pd.getPropertyType().getName() + " " + this.getBeanType().getName() + " " + this.getName());
         }
      }

      if (this.isReservedPropertyName()) {
         this.ignore = true;
         this.getBeanType().addPropertyProblem(pd, "This property name is reserved by the REST mapping.");
      } else {
         if (this.encrypted) {
            if ("[B".equals(pd.getPropertyType().getName())) {
               this.ignore = true;
               return;
            }

            this.marshaller = EncryptedPropertyMarshaller.instance();
            this.unmarshaller = EncryptedPropertyUnmarshaller.instance();
         }

         this.isCustomMarshaller = registry.getCustomPropertyMarshaller(this.getBeanType().getName(), this.getName()) != null;
         if (this.marshaller == null && !this.isCustomMarshaller) {
            if (String.class == pd.getPropertyType() && !DescriptorUtils.getBooleanField(pd, "legalNull")) {
               this.marshaller = new DelegatingPropertyMarshaller(NonNullStringMarshaller.instance());
            } else {
               this.marshaller = DefaultPropertyMarshallers.instance().findMarshaller(request, pd.getPropertyType());
            }

            if (this.marshaller == null) {
               this.ignore = true;
            }
         }

         if (this.isWritable) {
            this.isCustomUnmarshaller = registry.getCustomPropertyUnmarshaller(this.getBeanType().getName(), this.getName()) != null;
            if (this.unmarshaller == null && !this.isCustomUnmarshaller) {
               if (String.class == pd.getPropertyType() && !DescriptorUtils.getBooleanField(pd, "legalNull")) {
                  this.unmarshaller = new DelegatingPropertyUnmarshaller(NonNullStringMarshaller.instance());
               } else {
                  this.unmarshaller = DefaultPropertyUnmarshallers.instance().findUnmarshaller(request, pd.getPropertyType());
               }

               if (this.unmarshaller == null) {
                  this.ignore = true;
               }
            }
         }

         if (this.ignore) {
            String access = this.isWritable ? "writeable" : "readonly";
            this.getBeanType().addPropertyProblem(pd, "cannot map " + access + " property type: " + pd.getPropertyType().getName());
         }

         this.isDerivedDefault = DescriptorUtils.getBooleanField(pd, "restDerivedDefault");
         Enumeration e;
         String field;
         if (DescriptorUtils.getBooleanField(pd, "defaultValueNull")) {
            this.hasDefaultValue = true;
            this.defaultValue = null;
         } else {
            e = pd.attributeNames();

            while(e.hasMoreElements()) {
               field = (String)e.nextElement();
               if ("default".equals(field)) {
                  this.hasDefaultValue = true;
                  this.defaultValue = pd.getValue("default");
               }
            }
         }

         e = pd.attributeNames();

         while(e.hasMoreElements()) {
            field = (String)e.nextElement();
            if ("restProductionModeDefault".equals(field)) {
               this.hasProductionModeDefaultValue = true;
               this.productionModeDefaultValue = pd.getValue("restProductionModeDefault");
            }
         }

         if (DescriptorUtils.getBooleanField(pd, "secureValueNull")) {
            this.hasSecureValue = true;
            this.secureValue = null;
         } else {
            e = pd.attributeNames();

            while(e.hasMoreElements()) {
               field = (String)e.nextElement();
               if ("secureValue".equals(field)) {
                  this.hasSecureValue = true;
                  this.secureValue = pd.getValue("secureValue");
               }
            }
         }

         this.secureValueDocOnly = DescriptorUtils.getBooleanField(pd, "secureValueDocOnly");
         this.minValue = DescriptorUtils.getNumberField(pd, "legalMin");
         this.maxValue = DescriptorUtils.getNumberField(pd, "legalMax");
         this.legalValues = (Object[])((Object[])pd.getValue("legalValues"));
         RequestBodyHelper helper = registry.getRequestBodyHelper();
         if (this.encrypted) {
            if (!helper.isConfidentialProperty(this.getName())) {
               this.getBeanType().addPropertyProblem(pd, "illegal encrypted property name: " + this.getName() + ".  Valid encrypted property names must include one of the following, case insensitive: " + helper.getConfidentialPropertyNamePatterns());
            }
         } else if (pd.getPropertyType().equals(String.class)) {
         }

      }
   }

   void setIdentity(boolean identity) {
      this.identity = identity;
   }

   boolean ignore() {
      return this.ignore;
   }

   public boolean isIdentity() {
      return this.identity;
   }

   public boolean isEncrypted() {
      return this.encrypted;
   }

   public boolean isSensitive() {
      return this.sensitive;
   }

   public boolean isDerivedDefault() {
      return this.isDerivedDefault;
   }

   public boolean hasDefaultValue() {
      return this.hasDefaultValue;
   }

   public Object getDefaultValue() {
      return this.defaultValue;
   }

   public boolean hasProductionModeDefaultValue() {
      return this.hasProductionModeDefaultValue;
   }

   public Object getProductionModeDefaultValue() {
      return this.productionModeDefaultValue;
   }

   public boolean hasSecureValue() {
      return this.hasSecureValue;
   }

   public Object getSecureValue() {
      return this.secureValue;
   }

   public boolean isSecureValueDocOnly() {
      return this.secureValueDocOnly;
   }

   public boolean isWritable() {
      return this.isWritable;
   }

   public Method getWriter() {
      return this.isWritable() ? super.getWriter() : null;
   }

   public Number getMin() {
      return this.minValue;
   }

   public Number getMax() {
      return this.maxValue;
   }

   public Object[] getLegalValues() {
      return this.legalValues;
   }

   public boolean isNullable() {
      return this.isNullable;
   }

   public boolean isUnharvestable() {
      return this.isUnharvestable;
   }

   public PropertyMarshaller getMarshaller() {
      if (!this.isCustomMarshaller) {
         return this.marshaller;
      } else {
         BeanResourceRegistry registry = BeanResourceRegistry.instance();
         PropertyMarshaller marshaller = registry.getCustomPropertyMarshallerInstance(this.getBeanType().getName(), this.getName());
         if (marshaller == null) {
            Class clazz = registry.getCustomPropertyMarshaller(this.getBeanType().getName(), this.getName());
            if (clazz == null) {
               throw new AssertionError("No custom property marshaller for " + this.getBeanType().getName() + " " + this.getName() + " in partition " + PartitionUtils.getCicPartitionName());
            }

            try {
               Object m = clazz.newInstance();
               if (m instanceof PropertyMarshaller) {
                  marshaller = (PropertyMarshaller)m;
               } else {
                  if (!(m instanceof Marshaller)) {
                     throw new AssertionError("The class " + clazz.getName() + " does not extend " + PropertyMarshaller.class.getName() + " or " + Marshaller.class.getName() + " in partition " + PartitionUtils.getCicPartitionName());
                  }

                  marshaller = new DelegatingPropertyMarshaller((Marshaller)m);
               }

               registry.registerCustomPropertyMarshallerInstance(this.getBeanType().getName(), this.getName(), (PropertyMarshaller)marshaller);
            } catch (Throwable var5) {
               throw new AssertionError("Problem instantiating custom marshaller for " + this.getBeanType().getName() + " " + this.getName() + " " + clazz.getName() + " in partition " + PartitionUtils.getCicPartitionName());
            }
         }

         return (PropertyMarshaller)marshaller;
      }
   }

   public PropertyUnmarshaller getUnmarshaller() {
      if (!this.isCustomUnmarshaller) {
         return this.unmarshaller;
      } else {
         BeanResourceRegistry registry = BeanResourceRegistry.instance();
         PropertyUnmarshaller unmarshaller = registry.getCustomPropertyUnmarshallerInstance(this.getBeanType().getName(), this.getName());
         if (unmarshaller == null) {
            Class clazz = registry.getCustomPropertyUnmarshaller(this.getBeanType().getName(), this.getName());
            if (clazz == null) {
               throw new AssertionError("No custom property unmarshaller for " + this.getBeanType().getName() + " " + this.getName() + " in partition " + PartitionUtils.getCicPartitionName());
            }

            try {
               Object m = clazz.newInstance();
               if (m instanceof PropertyUnmarshaller) {
                  unmarshaller = (PropertyUnmarshaller)m;
               } else {
                  if (!(m instanceof Unmarshaller)) {
                     throw new AssertionError("The class " + clazz.getName() + " does not extend " + PropertyUnmarshaller.class.getName() + " or " + Unmarshaller.class.getName() + " in partition " + PartitionUtils.getCicPartitionName());
                  }

                  unmarshaller = new DelegatingPropertyUnmarshaller((Unmarshaller)m);
               }

               registry.registerCustomPropertyUnmarshallerInstance(this.getBeanType().getName(), this.getName(), (PropertyUnmarshaller)unmarshaller);
            } catch (Throwable var5) {
               throw new AssertionError("Problem instantiating custom unmarshaller for " + this.getBeanType().getName() + " " + this.getName() + " " + clazz.getName() + " in partition " + PartitionUtils.getCicPartitionName());
            }
         }

         return (PropertyUnmarshaller)unmarshaller;
      }
   }

   private boolean isReservedPropertyName() throws Exception {
      String name = this.getName();
      if (this.isReserved(RESERVED_PROPERTY_NAMES, name)) {
         return true;
      } else if (this.getBeanType().isCustomSecurityProvider()) {
         return false;
      } else {
         Class clz = this.getBeanType().getBeanClass();
         boolean isTask = TaskRuntimeMBeanUtils.isTaskRuntimeMBean(clz);
         boolean isDPO = TaskRuntimeMBeanUtils.isDeploymentProgressObjectMBean(clz);
         if (isTask || isDPO) {
            if (this.isReserved(RESERVED_TASK_PROPERTY_NAMES, name)) {
               return true;
            }

            if (isDPO && this.isReserved(RESERVED_DPO_PROPERTY_NAMES, name)) {
               return true;
            }
         }

         return false;
      }
   }

   boolean isReserved(String[] names, String name) {
      String[] var3 = names;
      int var4 = names.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String n = var3[var5];
         if (n.equals(name)) {
            return true;
         }
      }

      return false;
   }
}
