package weblogic.management.jmx.modelmbean;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.io.ObjectStreamException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.Descriptor;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.CompositeTypeProperties;
import weblogic.management.jmx.CompositeTypeThrowable;
import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.jmx.PrimitiveMapper;
import weblogic.management.provider.BeanInfoKey;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.provider.core.ManagementCoreService;

public class ModelMBeanInfoWrapper extends ModelMBeanInfoWrapperKey {
   private static final VersionInfo COMPATIBILITY_VERSION = new VersionInfo("9.0.0.0");
   private boolean dynamicMBeanCompatibilityMode = false;
   private static final String INTERFACECLASS_FIELDNAME = "interfaceclassname";
   private static final String OPENTYPE_FIELDNAME = "openType";
   private static final String HARVESTABLE_FIELDNAME = "com.bea.harvestable";
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");
   private volatile DeferredLoadModelMBeanInfo modelMBeanInfo;
   static final BeanInfoAccess beanInfoAccess = ManagementCoreService.getBeanInfoAccess();
   private Map beanPropertiesByName = new ConcurrentHashMap();
   private static final String COLLECTIONROLE_FIELDNAME = "com.bea.collectionRole";
   private Map beanOperationsBySignature = new ConcurrentHashMap();

   private String printStrings(String[] strings) {
      StringBuffer buffer = new StringBuffer();

      for(int i = 0; i < strings.length; ++i) {
         String string = strings[i];
         buffer.append(string + ",");
      }

      return buffer.toString();
   }

   ModelMBeanInfoWrapper(BeanInfoKey beanInfoKey, ObjectNameManager nameManager) throws OperationsException, MBeanVersionMismatchException {
      super(beanInfoKey, nameManager);
      String version = beanInfoKey.getVersion();
      if (version != null && COMPATIBILITY_VERSION.laterThan(new VersionInfo(version))) {
         this.dynamicMBeanCompatibilityMode = true;
      }

      this.modelMBeanInfo = this.createDeferredLoadModelMBeanInfo(beanInfoKey);
   }

   public ModelMBeanInfo getModelMBeanInfo() {
      return this.modelMBeanInfo;
   }

   void releaseInactiveMBeanInfo(long threshold) {
      this.modelMBeanInfo.releaseInactiveDelegate(threshold);
   }

   private void clearCache() {
      this.beanOperationsBySignature = new ConcurrentHashMap();
      this.beanPropertiesByName = new ConcurrentHashMap();
   }

   public PropertyDescriptor getPropertyDescriptor(String property) {
      this.ensureDeferredMBeanInfoLoaded();
      return (PropertyDescriptor)this.beanPropertiesByName.get(property);
   }

   public OperationData getOperationData(String operationName, String[] paramTypes) {
      this.ensureDeferredMBeanInfoLoaded();
      return (OperationData)this.beanOperationsBySignature.get(new Signature(operationName, paramTypes));
   }

   public MethodDescriptor getMethodDescriptor(String operationName, String[] paramTypes) {
      OperationData data = this.getOperationData(operationName, paramTypes);
      return data == null ? null : data.descriptor;
   }

   private ModelMBeanInfo createModelMBeanInfo(BeanInfo beanInfo, boolean readOnly, String desiredVersionStr) throws OperationsException, MBeanVersionMismatchException {
      BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
      VersionInfo desiredVersion = null;
      String description;
      if (desiredVersionStr != null) {
         desiredVersion = new VersionInfo(desiredVersionStr);
         description = (String)beanDescriptor.getValue("since");
         if (description != null) {
            VersionInfo beanStartVersion = new VersionInfo(description);
            if (beanStartVersion.laterThan(desiredVersion)) {
               throw new MBeanVersionMismatchException("Bean Type: " + beanDescriptor.getBeanClass().getName());
            }
         }
      }

      description = (String)beanDescriptor.getValue("description");
      if (description == null) {
         description = "";
      }

      Descriptor modelMBeanDescriptor = this.makeDescriptor(beanDescriptor);
      modelMBeanDescriptor.setField("descriptorType", "MBean");
      modelMBeanDescriptor.removeField("generatedByWLSInfoBinder");
      if (this.isAnnotationTrue(beanDescriptor, "harvestable")) {
         modelMBeanDescriptor.setField("com.bea.harvestable", Boolean.TRUE);
      }

      ModelMBeanAttributeInfo[] modelMBeanAttributes = this.getAttributesFromBean(beanInfo, readOnly, desiredVersion);
      ModelMBeanOperationInfo[] modelMBeanOperations = this.getOperationsFromBean(beanInfo, desiredVersion);
      ModelMBeanNotificationInfo[] modelMBeanNotifications = this.getNotificationsFromBean(beanInfo, desiredVersion);
      ModelMBeanInfoSupport mBeanInfo = new ModelMBeanInfoSupport(beanDescriptor.getBeanClass().getName(), description, modelMBeanAttributes, (ModelMBeanConstructorInfo[])null, modelMBeanOperations, modelMBeanNotifications, modelMBeanDescriptor);
      return mBeanInfo;
   }

   private DeferredLoadModelMBeanInfo createDeferredLoadModelMBeanInfo(BeanInfoKey beanInfoKey) throws OperationsException, MBeanVersionMismatchException {
      BeanInfo beanInfo = beanInfoAccess.getBeanInfo(beanInfoKey);
      BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
      String desiredVersionStr = beanInfoKey.getVersion();
      VersionInfo desiredVersion = null;
      if (desiredVersionStr != null) {
         desiredVersion = new VersionInfo(desiredVersionStr);
         String beanVersionStr = (String)beanDescriptor.getValue("since");
         if (beanVersionStr != null) {
            VersionInfo beanStartVersion = new VersionInfo(beanVersionStr);
            if (beanStartVersion.laterThan(desiredVersion)) {
               throw new MBeanVersionMismatchException("Bean Type: " + beanDescriptor.getBeanClass().getName());
            }
         }
      }

      return new DeferredLoadModelMBeanInfo(beanInfoKey);
   }

   private ModelMBeanAttributeInfo[] getAttributesFromBean(BeanInfo beanInfo, boolean readOnly, VersionInfo desiredVersion) {
      ModelMBeanAttributeInfo[] attributes = null;
      PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
      attributes = new ModelMBeanAttributeInfo[props.length];
      int insertIndex = 0;

      int index;
      for(index = 0; index < props.length; ++index) {
         PropertyDescriptor prop = props[index];
         String propVersionStr = (String)prop.getValue("since");
         if (propVersionStr != null && desiredVersion != null) {
            VersionInfo propStartVersion = new VersionInfo(propVersionStr);
            if (propStartVersion.laterThan(desiredVersion)) {
               continue;
            }
         }

         String name = prop.getName();
         String description = (String)prop.getValue("description");
         if (description == null) {
            description = "";
         }

         Descriptor attrDescr = this.makeDescriptor(prop);
         attrDescr.setField("descriptorType", "Attribute");
         Method getter = prop.getReadMethod();
         Method setter = null;
         if (!readOnly) {
            setter = prop.getWriteMethod();
         }

         boolean hasGetter = getter != null;
         boolean hasSetter = setter != null;
         boolean isIs = hasGetter && getter.getName().startsWith("is");
         Class attributeClass = getter.getReturnType();
         String type = this.resolveClassReference(attributeClass, attrDescr);
         Enumeration fieldNames = prop.attributeNames();

         while(fieldNames.hasMoreElements()) {
            String fieldName = (String)fieldNames.nextElement();
            String jmxFieldName = DescriptorMapper.beanInfoToJMX(fieldName);
            if (jmxFieldName != null) {
               attrDescr.setField(jmxFieldName, prop.getValue(fieldName));
            }
         }

         ModelMBeanAttributeInfo info = new ModelMBeanAttributeInfo(name, type, description, hasGetter, hasSetter, isIs, attrDescr);
         this.beanPropertiesByName.put(name, prop);
         attributes[insertIndex] = info;
         ++insertIndex;
      }

      if (insertIndex < index) {
         ModelMBeanAttributeInfo[] attributes2 = new ModelMBeanAttributeInfo[insertIndex];
         System.arraycopy(attributes, 0, attributes2, 0, insertIndex);
         attributes = attributes2;
      }

      return attributes;
   }

   private String resolveClassReference(Class attributeClass, Descriptor descriptor) {
      String type = attributeClass.getName();
      Object openType;
      if (attributeClass.isArray()) {
         Class componentClass = attributeClass.getComponentType();
         if (this.nameManager != null && this.nameManager.isClassMapped(componentClass)) {
            if (descriptor != null) {
               descriptor.setField("interfaceclassname", type);
            }

            return ObjectName[].class.getName();
         }

         openType = null;

         try {
            openType = ModelMBeanInfoWrapperManager.getOpenTypeForInterface(componentClass.getName(), this.beanInfoKey.getVersion(), this.nameManager);
         } catch (OpenDataException var10) {
            throw new RuntimeException(var10);
         }

         if (openType == null && !this.dynamicMBeanCompatibilityMode) {
            if (Throwable.class.isAssignableFrom(attributeClass)) {
               openType = CompositeTypeThrowable.THROWABLE;
            }

            if (Properties.class.isAssignableFrom(attributeClass)) {
               openType = CompositeTypeProperties.PROPERTIES;
            }
         }

         ArrayType arrayType = null;
         if (openType != null) {
            try {
               arrayType = new ArrayType(1, (OpenType)openType);
            } catch (OpenDataException var8) {
               throw new RuntimeException(var8);
            }

            if (descriptor != null) {
               descriptor.setField("openType", arrayType);
            }

            return ArrayType.class.getName();
         }
      } else {
         if (String.class.equals(attributeClass)) {
            return type;
         }

         if (this.nameManager != null && this.nameManager.isClassMapped(attributeClass)) {
            if (descriptor != null) {
               descriptor.setField("interfaceclassname", type);
            }

            return ObjectName.class.getName();
         }

         String wrappedType = PrimitiveMapper.lookupWrapperClassName(attributeClass);
         if (wrappedType != null) {
            if (descriptor != null) {
               descriptor.setField("interfaceclassname", type);
            }

            return wrappedType;
         }

         openType = null;

         try {
            openType = ModelMBeanInfoWrapperManager.getOpenTypeForInterface(type, this.beanInfoKey.getVersion(), this.nameManager);
         } catch (OpenDataException var9) {
            throw new RuntimeException(var9);
         }

         if (openType == null && !this.dynamicMBeanCompatibilityMode && Throwable.class.isAssignableFrom(attributeClass)) {
            openType = CompositeTypeThrowable.THROWABLE;
         }

         if (openType != null) {
            if (descriptor != null) {
               descriptor.setField("openType", openType);
            }

            return CompositeType.class.getName();
         }
      }

      return type;
   }

   private ModelMBeanOperationInfo[] getOperationsFromBean(BeanInfo beanInfo, VersionInfo desiredVersion) {
      ModelMBeanOperationInfo[] operations = null;
      MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
      int operationCount = methodDescriptors.length;
      operations = new ModelMBeanOperationInfo[operationCount];
      int insertIndex = 0;

      for(int index = 0; index < methodDescriptors.length; ++index) {
         MethodDescriptor methodDescriptor = methodDescriptors[index];
         String methodVersionStr = (String)methodDescriptor.getValue("since");
         if (methodVersionStr != null && desiredVersion != null) {
            VersionInfo methodStartVersion = new VersionInfo(methodVersionStr);
            if (methodStartVersion.laterThan(desiredVersion)) {
               continue;
            }
         }

         String name = methodDescriptor.getName();
         Method method = methodDescriptor.getMethod();
         Class cls = method.getReturnType();
         String type = cls.getName();
         String description = (String)methodDescriptor.getValue("description");
         if (description == null) {
            description = "";
         }

         String role = (String)methodDescriptor.getValue("role");
         boolean isGetter = role != null && role.equalsIgnoreCase("getter");
         boolean isSetter = role != null && role.equalsIgnoreCase("setter");
         int impact = 1;
         if (isGetter) {
            impact = 0;
         }

         if (!isGetter && !isSetter) {
            String impactAnno = (String)methodDescriptor.getValue("impact");
            if (impactAnno != null) {
               if ("info".equals(impactAnno)) {
                  impact = 0;
               } else if ("action".equals(impactAnno)) {
                  impact = 1;
               } else if ("action_info".equals(impactAnno)) {
                  impact = 2;
               } else if ("unknown".equals(impactAnno)) {
                  impact = 3;
               }
            }
         }

         Descriptor operDescr = this.makeDescriptor(methodDescriptor);
         operDescr.setField("descriptorType", "operation");
         if (!isGetter && !isSetter) {
            role = "operation";
         }

         operDescr.setField("role", role);
         String _role = (String)methodDescriptor.getValue("role");
         Enumeration fieldNames = methodDescriptor.attributeNames();

         while(fieldNames.hasMoreElements()) {
            String fieldName = (String)fieldNames.nextElement();
            if (fieldName.equals("role")) {
               if (_role.equals("operation") && !_role.equalsIgnoreCase("getter") && !_role.equalsIgnoreCase("setter") && !_role.equalsIgnoreCase("finder") && !_role.equalsIgnoreCase("collection") && !_role.equalsIgnoreCase("factory")) {
                  operDescr.setField("com.bea.collectionRole", role);
               }
            } else {
               String jmxFieldName = DescriptorMapper.beanInfoToJMX(fieldName);
               if (jmxFieldName != null) {
                  operDescr.setField(jmxFieldName, methodDescriptor.getValue(fieldName));
               }
            }
         }

         Class[] parameterTypes = method.getParameterTypes();
         ParameterDescriptor[] parameterDescriptors = methodDescriptor.getParameterDescriptors();
         if (debug.isDebugEnabled() && parameterDescriptors != null && parameterDescriptors.length != parameterTypes.length) {
            debug.debug("Parameter types does not match parameter descriptors");
         }

         MBeanParameterInfo[] signature = new MBeanParameterInfo[parameterTypes.length];
         String[] signatureTypes = new String[parameterTypes.length];

         for(int k = 0; k < parameterTypes.length; ++k) {
            Class parameterClass = parameterTypes[k];
            String paramType = this.resolveClassReference(parameterClass, (Descriptor)null);
            String paramDescription = null;
            String paramName = null;
            if (parameterDescriptors != null && parameterDescriptors.length > k) {
               ParameterDescriptor parameterDescriptor = parameterDescriptors[k];
               paramName = parameterDescriptor.getName();
               paramDescription = parameterDescriptor.getShortDescription();
               if (paramDescription == null) {
                  paramDescription = "";
               }
            } else {
               paramName = parameterClass.getName();
               paramDescription = "";
            }

            signatureTypes[k] = paramType;
            MBeanParameterInfo paramInfo = new MBeanParameterInfo(paramName, paramType, paramDescription);
            signature[k] = paramInfo;
         }

         type = this.resolveClassReference(method.getReturnType(), operDescr);
         if (this.isAnnotationTrue(methodDescriptor, "harvestable")) {
            operDescr.setField("com.bea.harvestable", Boolean.TRUE);
         }

         ModelMBeanOperationInfo methodInfo = new ModelMBeanOperationInfo(name, description, signature, type, impact, operDescr);
         operations[insertIndex] = methodInfo;
         Signature mbeanSignature = new Signature(name, signatureTypes);
         this.beanOperationsBySignature.put(mbeanSignature, new OperationData(methodInfo, methodDescriptor));
         ++insertIndex;
      }

      return operations;
   }

   private ModelMBeanNotificationInfo[] getNotificationsFromBean(BeanInfo beanInfo, VersionInfo desiredVersion) {
      return null;
   }

   private Descriptor makeDescriptor(FeatureDescriptor featureDescriptor) {
      Descriptor descr = new DescriptorSupport();
      Enumeration e = featureDescriptor.attributeNames();

      while(e.hasMoreElements()) {
         String tagName = (String)e.nextElement();
         if (!tagName.equalsIgnoreCase("description")) {
            String jmxTagName = DescriptorMapper.beanInfoToJMX(tagName);
            if (jmxTagName != null) {
               Object tagValue = featureDescriptor.getValue(tagName);
               descr.setField(jmxTagName, tagValue);
            }
         }
      }

      if (descr.getFieldValue("Name") == null || descr.getFieldValue("Name").equals("")) {
         descr.setField("Name", featureDescriptor.getName());
      }

      return descr;
   }

   protected MBeanAttributeInfo[] getAttributes() {
      return this.modelMBeanInfo.getAttributes();
   }

   protected MBeanNotificationInfo[] getNotifications() {
      return this.modelMBeanInfo.getNotifications();
   }

   protected MBeanOperationInfo[] getOperations() {
      return this.modelMBeanInfo.getOperations();
   }

   protected MBeanConstructorInfo[] getConstructors() {
      return null;
   }

   protected Descriptor getDescriptor() {
      return this.modelMBeanInfo.getDescriptor();
   }

   private boolean isAnnotationTrue(FeatureDescriptor d, String tag) {
      String value = (String)d.getValue(tag);
      if (value != null && value.length() > 0) {
         char c = value.charAt(0);
         if (c == 't' || c == 'T') {
            return true;
         }
      }

      return false;
   }

   public BeanInfo getBeanInfo() {
      return beanInfoAccess.getBeanInfo(this.beanInfoKey);
   }

   private void ensureDeferredMBeanInfoLoaded() {
      this.modelMBeanInfo.getDescription();
   }

   private class DeferredLoadModelMBeanInfo extends MBeanInfo implements ModelMBeanInfo {
      private BeanInfoKey beanInfoKey;
      private volatile ModelMBeanInfo delegate;
      private AtomicLong lastAccess = new AtomicLong(0L);

      DeferredLoadModelMBeanInfo(BeanInfoKey beanInfoKey) {
         super(ModelMBeanInfoWrapper.beanInfoAccess.getBeanInfo(beanInfoKey).getBeanDescriptor().getBeanClass().getName(), (String)null, (MBeanAttributeInfo[])null, (MBeanConstructorInfo[])null, (MBeanOperationInfo[])null, (MBeanNotificationInfo[])null);
         this.beanInfoKey = beanInfoKey;
         this.lastAccess.set(System.currentTimeMillis());
      }

      public Descriptor[] getDescriptors(String inDescriptorType) throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getDescriptors(inDescriptorType);
      }

      public void setDescriptors(Descriptor[] inDescriptors) throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         minfo.setDescriptors(inDescriptors);
      }

      public Descriptor getDescriptor(String inDescriptorName, String inDescriptorType) throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getDescriptor(inDescriptorName, inDescriptorType);
      }

      public void setDescriptor(Descriptor inDescriptor, String inDescriptorType) throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         minfo.setDescriptor(inDescriptor, inDescriptorType);
      }

      public Descriptor getMBeanDescriptor() throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getMBeanDescriptor();
      }

      public void setMBeanDescriptor(Descriptor inDescriptor) throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         minfo.setMBeanDescriptor(inDescriptor);
      }

      public ModelMBeanAttributeInfo getAttribute(String inName) throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getAttribute(inName);
      }

      public ModelMBeanOperationInfo getOperation(String inName) throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getOperation(inName);
      }

      public ModelMBeanNotificationInfo getNotification(String inName) throws MBeanException, RuntimeOperationsException {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getNotification(inName);
      }

      public Object clone() {
         ModelMBeanInfo minfo = this.delegate;
         return minfo != null ? minfo.clone() : ModelMBeanInfoWrapper.this.new DeferredLoadModelMBeanInfo(this.beanInfoKey);
      }

      public MBeanAttributeInfo[] getAttributes() {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getAttributes();
      }

      public String getClassName() {
         ModelMBeanInfo minfo = this.delegate;
         return minfo != null ? minfo.getClassName() : ModelMBeanInfoWrapper.beanInfoAccess.getBeanInfo(this.beanInfoKey).getBeanDescriptor().getBeanClass().getName();
      }

      public MBeanConstructorInfo[] getConstructors() {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getConstructors();
      }

      public String getDescription() {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getDescription();
      }

      public MBeanNotificationInfo[] getNotifications() {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getNotifications();
      }

      public MBeanOperationInfo[] getOperations() {
         ModelMBeanInfo minfo = this.initDelegate();
         return minfo.getOperations();
      }

      public Descriptor getDescriptor() {
         ModelMBeanInfo minfo = this.initDelegate();

         try {
            return minfo.getMBeanDescriptor();
         } catch (MBeanException var3) {
            throw new RuntimeException("Error getting model mbean info descriptor ", var3);
         }
      }

      private ModelMBeanInfo initDelegate() {
         this.lastAccess.set(System.currentTimeMillis());
         if (this.delegate != null && ModelMBeanInfoWrapper.this.modelMBeanInfo != null) {
            return this.delegate;
         } else {
            synchronized(ModelMBeanInfoWrapper.this) {
               if (this.delegate != null && ModelMBeanInfoWrapper.this.modelMBeanInfo != null) {
                  return this.delegate;
               } else {
                  ModelMBeanInfo var10000;
                  try {
                     if (ModelMBeanInfoWrapper.debug.isDebugEnabled()) {
                        ModelMBeanInfoWrapper.debug.debug("Deferred load delegate for " + this.beanInfoKey);
                     }

                     this.delegate = ModelMBeanInfoWrapper.this.createModelMBeanInfo(ModelMBeanInfoWrapper.beanInfoAccess.getBeanInfo(this.beanInfoKey), this.beanInfoKey.isReadOnly(), this.beanInfoKey.getVersion());
                     ModelMBeanInfoWrapper.this.modelMBeanInfo.delegate = this.delegate;
                     var10000 = this.delegate;
                  } catch (OperationsException var4) {
                     throw new RuntimeException("Error creating model mbean info", var4);
                  }

                  return var10000;
               }
            }
         }
      }

      public void releaseInactiveDelegate(long threshold) {
         if (this.delegate != null) {
            synchronized(ModelMBeanInfoWrapper.this) {
               if (this.lastAccess.get() <= threshold) {
                  String delegateStr = this.delegate.getClassName();
                  int s1 = ModelMBeanInfoWrapper.this.beanOperationsBySignature.size();
                  int s2 = ModelMBeanInfoWrapper.this.beanPropertiesByName.size();
                  this.delegate = null;
                  ModelMBeanInfoWrapper.this.clearCache();
                  if (ModelMBeanInfoWrapper.debug.isDebugEnabled()) {
                     int s3 = ModelMBeanInfoWrapper.this.beanOperationsBySignature.size();
                     int s4 = ModelMBeanInfoWrapper.this.beanPropertiesByName.size();
                     ModelMBeanInfoWrapper.debug.debug(" release ModelMBeanInfo " + delegateStr + ", oper cache: " + s1 + " to " + s3 + ", prop cache: " + s2 + " to " + s4);
                  }
               }

            }
         }
      }

      private Object writeReplace() throws ObjectStreamException {
         this.initDelegate();
         return this.delegate;
      }

      public String toString() {
         String info = "DeferredLoadModelMBeanInfo: beanInfoKey = " + this.beanInfoKey + " readOnly = " + this.beanInfoKey.isReadOnly() + " version = " + this.beanInfoKey.getVersion();
         return info;
      }
   }

   private class Signature {
      private String operationName;
      private String[] paramTypes;

      public Signature(String operationName, String[] paramTypes) {
         this.operationName = operationName;
         this.paramTypes = paramTypes != null && paramTypes.length == 0 ? null : paramTypes;
      }

      public boolean equals(Object otherObject) {
         if (!(otherObject instanceof Signature)) {
            return false;
         } else {
            Signature other = (Signature)otherObject;
            if (!this.operationName.equals(other.operationName)) {
               return false;
            } else if (other.paramTypes == null && this.paramTypes == null) {
               return true;
            } else if (other.paramTypes != null && this.paramTypes != null) {
               if (other.paramTypes.length != this.paramTypes.length) {
                  return false;
               } else {
                  for(int i = 0; i < this.paramTypes.length; ++i) {
                     if (!this.paramTypes[i].equals(other.paramTypes[i])) {
                        return false;
                     }
                  }

                  return true;
               }
            } else {
               return false;
            }
         }
      }

      public int hashCode() {
         return this.operationName.hashCode();
      }

      public String toString() {
         return this.operationName + "(" + ModelMBeanInfoWrapper.this.printStrings(this.paramTypes) + ")";
      }
   }

   public class OperationData {
      final ModelMBeanOperationInfo info;
      final MethodDescriptor descriptor;

      OperationData(ModelMBeanOperationInfo info, MethodDescriptor descriptor) {
         this.info = info;
         this.descriptor = descriptor;
      }
   }
}
