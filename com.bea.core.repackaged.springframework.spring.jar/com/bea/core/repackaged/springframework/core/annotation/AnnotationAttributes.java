package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnnotationAttributes extends LinkedHashMap {
   private static final String UNKNOWN = "unknown";
   @Nullable
   private final Class annotationType;
   final String displayName;
   boolean validated = false;

   public AnnotationAttributes() {
      this.annotationType = null;
      this.displayName = "unknown";
   }

   public AnnotationAttributes(int initialCapacity) {
      super(initialCapacity);
      this.annotationType = null;
      this.displayName = "unknown";
   }

   public AnnotationAttributes(Map map) {
      super(map);
      this.annotationType = null;
      this.displayName = "unknown";
   }

   public AnnotationAttributes(AnnotationAttributes other) {
      super(other);
      this.annotationType = other.annotationType;
      this.displayName = other.displayName;
      this.validated = other.validated;
   }

   public AnnotationAttributes(Class annotationType) {
      Assert.notNull(annotationType, (String)"'annotationType' must not be null");
      this.annotationType = annotationType;
      this.displayName = annotationType.getName();
   }

   public AnnotationAttributes(String annotationType, @Nullable ClassLoader classLoader) {
      Assert.notNull(annotationType, (String)"'annotationType' must not be null");
      this.annotationType = getAnnotationType(annotationType, classLoader);
      this.displayName = annotationType;
   }

   @Nullable
   private static Class getAnnotationType(String annotationType, @Nullable ClassLoader classLoader) {
      if (classLoader != null) {
         try {
            return classLoader.loadClass(annotationType);
         } catch (ClassNotFoundException var3) {
         }
      }

      return null;
   }

   @Nullable
   public Class annotationType() {
      return this.annotationType;
   }

   public String getString(String attributeName) {
      return (String)this.getRequiredAttribute(attributeName, String.class);
   }

   public String[] getStringArray(String attributeName) {
      return (String[])this.getRequiredAttribute(attributeName, String[].class);
   }

   public boolean getBoolean(String attributeName) {
      return (Boolean)this.getRequiredAttribute(attributeName, Boolean.class);
   }

   public Number getNumber(String attributeName) {
      return (Number)this.getRequiredAttribute(attributeName, Number.class);
   }

   public Enum getEnum(String attributeName) {
      return (Enum)this.getRequiredAttribute(attributeName, Enum.class);
   }

   public Class getClass(String attributeName) {
      return (Class)this.getRequiredAttribute(attributeName, Class.class);
   }

   public Class[] getClassArray(String attributeName) {
      return (Class[])this.getRequiredAttribute(attributeName, Class[].class);
   }

   public AnnotationAttributes getAnnotation(String attributeName) {
      return (AnnotationAttributes)this.getRequiredAttribute(attributeName, AnnotationAttributes.class);
   }

   public Annotation getAnnotation(String attributeName, Class annotationType) {
      return (Annotation)this.getRequiredAttribute(attributeName, annotationType);
   }

   public AnnotationAttributes[] getAnnotationArray(String attributeName) {
      return (AnnotationAttributes[])this.getRequiredAttribute(attributeName, AnnotationAttributes[].class);
   }

   public Annotation[] getAnnotationArray(String attributeName, Class annotationType) {
      Object array = Array.newInstance(annotationType, 0);
      return (Annotation[])((Annotation[])this.getRequiredAttribute(attributeName, array.getClass()));
   }

   private Object getRequiredAttribute(String attributeName, Class expectedType) {
      Assert.hasText(attributeName, "'attributeName' must not be null or empty");
      Object value = this.get(attributeName);
      this.assertAttributePresence(attributeName, value);
      this.assertNotException(attributeName, value);
      if (!expectedType.isInstance(value) && expectedType.isArray() && expectedType.getComponentType().isInstance(value)) {
         Object array = Array.newInstance(expectedType.getComponentType(), 1);
         Array.set(array, 0, value);
         value = array;
      }

      this.assertAttributeType(attributeName, value, expectedType);
      return value;
   }

   private void assertAttributePresence(String attributeName, Object attributeValue) {
      Assert.notNull(attributeValue, () -> {
         return String.format("Attribute '%s' not found in attributes for annotation [%s]", attributeName, this.displayName);
      });
   }

   private void assertNotException(String attributeName, Object attributeValue) {
      if (attributeValue instanceof Throwable) {
         throw new IllegalArgumentException(String.format("Attribute '%s' for annotation [%s] was not resolvable due to exception [%s]", attributeName, this.displayName, attributeValue), (Throwable)attributeValue);
      }
   }

   private void assertAttributeType(String attributeName, Object attributeValue, Class expectedType) {
      if (!expectedType.isInstance(attributeValue)) {
         throw new IllegalArgumentException(String.format("Attribute '%s' is of type %s, but %s was expected in attributes for annotation [%s]", attributeName, attributeValue.getClass().getSimpleName(), expectedType.getSimpleName(), this.displayName));
      }
   }

   public String toString() {
      Iterator entries = this.entrySet().iterator();
      StringBuilder sb = new StringBuilder("{");

      while(entries.hasNext()) {
         Map.Entry entry = (Map.Entry)entries.next();
         sb.append((String)entry.getKey());
         sb.append('=');
         sb.append(this.valueToString(entry.getValue()));
         sb.append(entries.hasNext() ? ", " : "");
      }

      sb.append("}");
      return sb.toString();
   }

   private String valueToString(Object value) {
      if (value == this) {
         return "(this Map)";
      } else {
         return value instanceof Object[] ? "[" + StringUtils.arrayToDelimitedString((Object[])((Object[])value), ", ") + "]" : String.valueOf(value);
      }
   }

   @Nullable
   public static AnnotationAttributes fromMap(@Nullable Map map) {
      if (map == null) {
         return null;
      } else {
         return map instanceof AnnotationAttributes ? (AnnotationAttributes)map : new AnnotationAttributes(map);
      }
   }
}
