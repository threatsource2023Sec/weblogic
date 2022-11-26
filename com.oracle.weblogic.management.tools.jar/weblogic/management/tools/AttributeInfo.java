package weblogic.management.tools;

import java.lang.reflect.Array;
import javax.management.MBeanAttributeInfo;
import weblogic.management.info.ExtendedAttributeInfo;

public class AttributeInfo extends MBeanAttributeInfo implements ExtendedAttributeInfo {
   static final long serialVersionUID = 1876948055092590334L;
   private static String[] primitiveTypes = new String[]{"byte", "short", "int", "long", "float", "double", "boolean"};
   transient Class typeClass;
   transient Class collectionTypeClass;
   private String collectionType = null;
   private Object clientDefault = null;
   private boolean configurable = true;
   private boolean isIs = false;
   private String oldProp = null;
   private boolean dynamic = false;
   private Object[] legalValues = null;
   private boolean isLegalValuesExtensible = false;
   private Long legalMax = null;
   private Long legalMin = null;
   private boolean legalNull = true;
   private Object defaultValue = null;
   private Object productionModeDefaultValue = null;
   private boolean isExcluded = false;
   private boolean isEncrypted = false;
   private String units = null;
   private String[] legalChecks = null;
   private String[] legalResponses = null;
   private Integer protectionLevel = null;
   private boolean overrideDynamic = false;
   private boolean deploymentDescriptor = false;
   private boolean isContained = false;
   private transient Boolean productionModeEnabled;
   private transient boolean initializedClasses = false;

   public AttributeInfo(String name, String type, String description, Object defaultValue, Object productionModeDefaultValue, Object clientDefault, String collectionType, boolean readable, boolean writable, boolean configurable, boolean isIs, String oldProp, boolean dynamic, String[] legalChecks, Object[] legalValues, boolean isLegalValuesExtensible, Long legalMax, Long legalMin, boolean legalNull, boolean isExcluded, boolean isEncrypted, String units, String[] legalResponses, Integer protectionLevel, boolean overrideDynamic, boolean deploymentDescriptor, boolean isContained) {
      super(name, type, description, readable, writable, isIs);
      this.defaultValue = defaultValue;
      this.productionModeDefaultValue = productionModeDefaultValue;
      this.clientDefault = clientDefault;
      this.configurable = configurable;
      this.collectionType = collectionType;
      this.oldProp = oldProp;
      this.isIs = isIs;
      this.dynamic = dynamic;
      this.legalChecks = legalChecks;
      this.legalValues = legalValues;
      this.isLegalValuesExtensible = isLegalValuesExtensible;
      this.legalMax = legalMax;
      this.legalMin = legalMin;
      this.legalNull = legalNull;
      this.isExcluded = isExcluded;
      this.isEncrypted = isEncrypted;
      this.units = units;
      this.legalResponses = legalResponses;
      this.protectionLevel = protectionLevel;
      this.overrideDynamic = overrideDynamic;
      this.deploymentDescriptor = deploymentDescriptor;
      this.isContained = isContained;
      if (isPrimitive(type)) {
         this.legalNull = false;
      }

      this.initializeClasses();
   }

   private static boolean isPrimitive(String type) {
      if (type != null && type.length() != 0) {
         for(int i = 0; i < primitiveTypes.length; ++i) {
            if (type.equals(primitiveTypes[i])) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public Object getDefaultValue() {
      return this.defaultValue;
   }

   public Object getProductionModeDefaultValue() {
      return this.productionModeDefaultValue != null ? this.productionModeDefaultValue : this.defaultValue;
   }

   public Class getTypeClass() {
      if (!this.initializedClasses) {
         this.initializeClasses();
      }

      return this.typeClass;
   }

   public Class getCollectionTypeClass() {
      if (!this.initializedClasses) {
         this.initializeClasses();
      }

      return this.collectionTypeClass;
   }

   public boolean isDynamic() {
      return this.dynamic;
   }

   public String getLegalCheck() {
      return this.legalChecks != null && this.legalChecks.length > 0 ? this.legalChecks[0] : null;
   }

   public Object[] getLegalValues() {
      return this.legalValues;
   }

   public Long getLegalMax() {
      return this.legalMax;
   }

   public Long getLegalMin() {
      return this.legalMin;
   }

   public boolean getLegalNull() {
      return this.legalNull;
   }

   public Object getClientDefault() {
      return this.clientDefault;
   }

   public boolean isConfigurable() {
      return this.configurable;
   }

   public String getOldProp() {
      return this.oldProp;
   }

   public boolean isIs() {
      return this.isIs;
   }

   public boolean isContained() {
      return this.isContained;
   }

   public boolean isExcluded() {
      return this.isExcluded;
   }

   public boolean isEncrypted() {
      return this.isEncrypted;
   }

   public String getUnits() {
      return this.units;
   }

   public boolean isLegalValuesExtensible() {
      return this.isLegalValuesExtensible;
   }

   public String[] getLegalChecks() {
      return this.legalChecks;
   }

   public String[] getLegalResponses() {
      return this.legalResponses;
   }

   public String getLegalResponse() {
      return this.legalResponses != null && this.legalResponses.length > 0 ? this.legalResponses[0] : null;
   }

   public Integer getProtectionLevel() {
      return this.protectionLevel;
   }

   public boolean isOverrideDynamic() {
      return this.overrideDynamic;
   }

   public boolean isDeploymentDescriptor() {
      return this.deploymentDescriptor;
   }

   public String toString() {
      StringBuffer result = new StringBuffer("name=" + this.getName());
      result.append(", type=" + this.getType());
      result.append(", readable=" + this.isReadable());
      result.append(", writable=" + this.isWritable());
      result.append(", dynamic=" + this.isDynamic());
      result.append(", configurable=" + this.isConfigurable());
      result.append(", encrypted=" + this.isEncrypted());
      result.append(", protection level=" + this.getProtectionLevel());
      result.append(", overrideDynamic=" + this.isOverrideDynamic());
      result.append(", deploymentDescriptor=" + this.isDeploymentDescriptor());
      result.append(", oldprop=" + this.oldProp);
      String printableDefault = "<null>";
      if (this.defaultValue != null) {
         printableDefault = this.defaultValue.toString();
      }

      result.append(", defaultValue=[" + printableDefault + "]");
      result.append(", production-mode-default=[" + this.productionModeDefaultValue + "]");
      result.append(", legalValuesExtensible=" + this.isLegalValuesExtensible());
      int i;
      if (this.legalChecks != null && this.legalChecks.length > 0) {
         for(i = 0; i < this.legalChecks.length; ++i) {
            result.append(", @legal-" + (new Integer(i)).toString() + "=" + this.legalChecks[i]);
         }
      } else {
         result.append(", @legal=" + printableDefault);
      }

      if (this.legalResponses != null && this.legalResponses.length > 0) {
         for(i = 0; i < this.legalResponses.length; ++i) {
            result.append(", @legalResponse-" + (new Integer(i)).toString() + "=" + this.legalResponses[i]);
         }
      } else {
         result.append(", @legalResponse=" + printableDefault);
      }

      if (this.defaultValue != null) {
         printableDefault = this.defaultValue.toString();
      }

      result.append(", max: " + this.legalMax);
      result.append(", min: " + this.legalMin);
      result.append(", null ok: " + this.legalNull);
      if (this.legalValues != null) {
         result.append("legalValues: ");

         for(i = 0; i < this.legalValues.length; ++i) {
            result.append("" + this.legalValues[i]);
            if (i != this.legalValues.length - 1) {
               result.append(",");
            }
         }
      }

      return result.toString();
   }

   private void initializeClasses() {
      if (!this.initializedClasses) {
         try {
            this.typeClass = AttributeInfo.Helper.findClass(this.getType());
            if (this.collectionType != null) {
               this.collectionTypeClass = AttributeInfo.Helper.findClass(this.collectionType);
            }

            this.initializedClasses = true;
         } catch (ClassNotFoundException var2) {
            throw new AssertionError(var2);
         }
      }
   }

   public static class Helper {
      public static Class findClass(String name) throws ClassNotFoundException {
         if (name.equals(Long.class.getName())) {
            return Long.TYPE;
         } else if (name.equals(Double.class.getName())) {
            return Double.TYPE;
         } else if (name.equals(Float.class.getName())) {
            return Float.TYPE;
         } else if (name.equals(Integer.class.getName())) {
            return Integer.TYPE;
         } else if (name.equals(Character.class.getName())) {
            return Character.TYPE;
         } else if (name.equals(Short.class.getName())) {
            return Short.TYPE;
         } else if (name.equals(Byte.class.getName())) {
            return Byte.TYPE;
         } else if (name.equals(Boolean.class.getName())) {
            return Boolean.TYPE;
         } else if (name.equals(Void.class.getName())) {
            return Void.TYPE;
         } else if (name.equals("long")) {
            return Long.TYPE;
         } else if (name.equals("double")) {
            return Double.TYPE;
         } else if (name.equals("float")) {
            return Float.TYPE;
         } else if (name.equals("int")) {
            return Integer.TYPE;
         } else if (name.equals("char")) {
            return Character.TYPE;
         } else if (name.equals("short")) {
            return Short.TYPE;
         } else if (name.equals("byte")) {
            return Byte.TYPE;
         } else if (name.equals("boolean")) {
            return Boolean.TYPE;
         } else if (name.equals("void")) {
            return Void.TYPE;
         } else if (name.endsWith("[]")) {
            Class componentClass = findClass(name.substring(0, name.length() - 2));
            return Array.newInstance(componentClass, 0).getClass();
         } else {
            return Class.forName(name);
         }
      }

      public static Class wrapClass(Class type) {
         if (type == Long.TYPE) {
            return Long.class;
         } else if (type == Double.TYPE) {
            return Double.class;
         } else if (type == Float.TYPE) {
            return Float.class;
         } else if (type == Integer.TYPE) {
            return Integer.class;
         } else if (type == Character.TYPE) {
            return Character.class;
         } else if (type == Short.TYPE) {
            return Short.class;
         } else if (type == Byte.TYPE) {
            return Byte.class;
         } else {
            return type == Boolean.TYPE ? Boolean.class : type;
         }
      }

      public static String trimPackage(String className) {
         int index = className.lastIndexOf(46);
         int len = className.length();
         if (index != -1) {
            className = className.substring(index + 1, len);
         }

         return className;
      }
   }
}
