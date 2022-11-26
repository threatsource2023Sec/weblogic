package org.hibernate.validator.internal.util.annotation;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttributes;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethods;

public class AnnotationDescriptor implements Serializable {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Class type;
   private final Map attributes;
   private final int hashCode;
   private final Annotation annotation;

   public AnnotationDescriptor(Annotation annotation) {
      this.type = annotation.annotationType();
      this.attributes = (Map)run(GetAnnotationAttributes.action(annotation));
      this.annotation = annotation;
      this.hashCode = this.buildHashCode();
   }

   public AnnotationDescriptor(AnnotationDescriptor descriptor) {
      this.type = descriptor.type;
      this.attributes = descriptor.attributes;
      this.hashCode = descriptor.hashCode;
      this.annotation = descriptor.annotation;
   }

   private AnnotationDescriptor(Class annotationType, Map attributes) {
      this.type = annotationType;
      this.attributes = CollectionHelper.toImmutableMap(attributes);
      this.hashCode = this.buildHashCode();
      this.annotation = AnnotationFactory.create(this);
   }

   public Class getType() {
      return this.type;
   }

   public Map getAttributes() {
      return this.attributes;
   }

   public Object getMandatoryAttribute(String attributeName, Class attributeType) {
      Object attribute = this.attributes.get(attributeName);
      if (attribute == null) {
         throw LOG.getUnableToFindAnnotationAttributeException(this.type, attributeName, (NoSuchMethodException)null);
      } else if (!attributeType.isAssignableFrom(attribute.getClass())) {
         throw LOG.getWrongAnnotationAttributeTypeException(this.type, attributeName, attributeType, attribute.getClass());
      } else {
         return attribute;
      }
   }

   public Object getAttribute(String attributeName, Class attributeType) {
      Object attribute = this.attributes.get(attributeName);
      if (attribute == null) {
         return null;
      } else if (!attributeType.isAssignableFrom(attribute.getClass())) {
         throw LOG.getWrongAnnotationAttributeTypeException(this.type, attributeName, attributeType, attribute.getClass());
      } else {
         return attribute;
      }
   }

   public Object getAttribute(String attributeName) {
      return this.attributes.get(attributeName);
   }

   public Annotation getAnnotation() {
      return this.annotation;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && obj instanceof AnnotationDescriptor) {
         AnnotationDescriptor other = (AnnotationDescriptor)obj;
         if (!this.type.equals(other.type)) {
            return false;
         } else if (this.attributes.size() != other.attributes.size()) {
            return false;
         } else {
            Iterator var3 = this.attributes.entrySet().iterator();

            Object value;
            Object otherValue;
            do {
               if (!var3.hasNext()) {
                  return true;
               }

               Map.Entry member = (Map.Entry)var3.next();
               value = member.getValue();
               otherValue = other.attributes.get(member.getKey());
            } while(this.areEqual(value, otherValue));

            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append('@').append(StringHelper.toShortString((Type)this.type)).append('(');
      Iterator var2 = this.getRegisteredAttributesInAlphabeticalOrder().iterator();

      while(var2.hasNext()) {
         String s = (String)var2.next();
         result.append(s).append('=').append(this.attributes.get(s)).append(", ");
      }

      if (this.attributes.size() > 0) {
         result.delete(result.length() - 2, result.length());
         result.append(")");
      } else {
         result.delete(result.length() - 1, result.length());
      }

      return result.toString();
   }

   private SortedSet getRegisteredAttributesInAlphabeticalOrder() {
      return new TreeSet(this.attributes.keySet());
   }

   private int buildHashCode() {
      int hashCode = 0;

      int nameHashCode;
      int valueHashCode;
      for(Iterator var2 = this.attributes.entrySet().iterator(); var2.hasNext(); hashCode += 127 * nameHashCode ^ valueHashCode) {
         Map.Entry member = (Map.Entry)var2.next();
         Object value = member.getValue();
         nameHashCode = ((String)member.getKey()).hashCode();
         valueHashCode = !value.getClass().isArray() ? value.hashCode() : (value.getClass() == boolean[].class ? Arrays.hashCode((boolean[])((boolean[])value)) : (value.getClass() == byte[].class ? Arrays.hashCode((byte[])((byte[])value)) : (value.getClass() == char[].class ? Arrays.hashCode((char[])((char[])value)) : (value.getClass() == double[].class ? Arrays.hashCode((double[])((double[])value)) : (value.getClass() == float[].class ? Arrays.hashCode((float[])((float[])value)) : (value.getClass() == int[].class ? Arrays.hashCode((int[])((int[])value)) : (value.getClass() == long[].class ? Arrays.hashCode((long[])((long[])value)) : (value.getClass() == short[].class ? Arrays.hashCode((short[])((short[])value)) : Arrays.hashCode((Object[])((Object[])value))))))))));
      }

      return hashCode;
   }

   private boolean areEqual(Object o1, Object o2) {
      return !o1.getClass().isArray() ? o1.equals(o2) : (o1.getClass() == boolean[].class ? Arrays.equals((boolean[])((boolean[])o1), (boolean[])((boolean[])o2)) : (o1.getClass() == byte[].class ? Arrays.equals((byte[])((byte[])o1), (byte[])((byte[])o2)) : (o1.getClass() == char[].class ? Arrays.equals((char[])((char[])o1), (char[])((char[])o2)) : (o1.getClass() == double[].class ? Arrays.equals((double[])((double[])o1), (double[])((double[])o2)) : (o1.getClass() == float[].class ? Arrays.equals((float[])((float[])o1), (float[])((float[])o2)) : (o1.getClass() == int[].class ? Arrays.equals((int[])((int[])o1), (int[])((int[])o2)) : (o1.getClass() == long[].class ? Arrays.equals((long[])((long[])o1), (long[])((long[])o2)) : (o1.getClass() == short[].class ? Arrays.equals((short[])((short[])o1), (short[])((short[])o2)) : Arrays.equals((Object[])((Object[])o1), (Object[])((Object[])o2))))))))));
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   // $FF: synthetic method
   AnnotationDescriptor(Class x0, Map x1, Object x2) {
      this(x0, x1);
   }

   public static class Builder {
      private final Class type;
      private final Map attributes;

      public Builder(Class type) {
         this.type = type;
         this.attributes = new HashMap();
      }

      public Builder(Class type, Map attributes) {
         this.type = type;
         this.attributes = new HashMap(attributes);
      }

      public Builder(Annotation annotation) {
         this.type = annotation.annotationType();
         this.attributes = new HashMap((Map)AnnotationDescriptor.run(GetAnnotationAttributes.action(annotation)));
      }

      public void setAttribute(String attributeName, Object value) {
         this.attributes.put(attributeName, value);
      }

      public boolean hasAttribute(String key) {
         return this.attributes.containsKey(key);
      }

      public Class getType() {
         return this.type;
      }

      public AnnotationDescriptor build() {
         return new AnnotationDescriptor(this.type, this.getAnnotationAttributes());
      }

      private Map getAnnotationAttributes() {
         Map result = CollectionHelper.newHashMap(this.attributes.size());
         int processedValuesFromDescriptor = 0;
         Method[] declaredMethods = (Method[])AnnotationDescriptor.run(GetDeclaredMethods.action(this.type));
         Method[] var4 = declaredMethods;
         int var5 = declaredMethods.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method m = var4[var6];
            Object elementValue = this.attributes.get(m.getName());
            if (elementValue != null) {
               result.put(m.getName(), elementValue);
               ++processedValuesFromDescriptor;
            } else {
               if (m.getDefaultValue() == null) {
                  throw AnnotationDescriptor.LOG.getNoValueProvidedForAnnotationAttributeException(m.getName(), this.type);
               }

               result.put(m.getName(), m.getDefaultValue());
            }
         }

         if (processedValuesFromDescriptor != this.attributes.size()) {
            Set unknownAttributes = this.attributes.keySet();
            unknownAttributes.removeAll(result.keySet());
            throw AnnotationDescriptor.LOG.getTryingToInstantiateAnnotationWithUnknownAttributesException(this.type, unknownAttributes);
         } else {
            return result;
         }
      }

      public String toString() {
         StringBuilder result = new StringBuilder();
         result.append('@').append(StringHelper.toShortString((Type)this.type)).append('(');
         Iterator var2 = this.getRegisteredAttributesInAlphabeticalOrder().iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();
            result.append(s).append('=').append(this.attributes.get(s)).append(", ");
         }

         if (this.attributes.size() > 0) {
            result.delete(result.length() - 2, result.length());
            result.append(")");
         } else {
            result.delete(result.length() - 1, result.length());
         }

         return result.toString();
      }

      private SortedSet getRegisteredAttributesInAlphabeticalOrder() {
         return new TreeSet(this.attributes.keySet());
      }
   }
}
