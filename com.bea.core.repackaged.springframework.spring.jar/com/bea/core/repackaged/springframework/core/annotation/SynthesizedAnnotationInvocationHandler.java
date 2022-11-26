package com.bea.core.repackaged.springframework.core.annotation;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class SynthesizedAnnotationInvocationHandler implements InvocationHandler {
   private final AnnotationAttributeExtractor attributeExtractor;
   private final Map valueCache = new ConcurrentHashMap(8);

   SynthesizedAnnotationInvocationHandler(AnnotationAttributeExtractor attributeExtractor) {
      Assert.notNull(attributeExtractor, (String)"AnnotationAttributeExtractor must not be null");
      this.attributeExtractor = attributeExtractor;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (ReflectionUtils.isEqualsMethod(method)) {
         return this.annotationEquals(args[0]);
      } else if (ReflectionUtils.isHashCodeMethod(method)) {
         return this.annotationHashCode();
      } else if (ReflectionUtils.isToStringMethod(method)) {
         return this.annotationToString();
      } else if (AnnotationUtils.isAnnotationTypeMethod(method)) {
         return this.annotationType();
      } else if (!AnnotationUtils.isAttributeMethod(method)) {
         throw new AnnotationConfigurationException(String.format("Method [%s] is unsupported for synthesized annotation type [%s]", method, this.annotationType()));
      } else {
         return this.getAttributeValue(method);
      }
   }

   private Class annotationType() {
      return this.attributeExtractor.getAnnotationType();
   }

   private Object getAttributeValue(Method attributeMethod) {
      String attributeName = attributeMethod.getName();
      Object value = this.valueCache.get(attributeName);
      if (value == null) {
         value = this.attributeExtractor.getAttributeValue(attributeMethod);
         if (value == null) {
            String msg = String.format("%s returned null for attribute name [%s] from attribute source [%s]", this.attributeExtractor.getClass().getName(), attributeName, this.attributeExtractor.getSource());
            throw new IllegalStateException(msg);
         }

         if (value instanceof Annotation) {
            value = AnnotationUtils.synthesizeAnnotation((Annotation)value, this.attributeExtractor.getAnnotatedElement());
         } else if (value instanceof Annotation[]) {
            value = AnnotationUtils.synthesizeAnnotationArray((Annotation[])((Annotation[])value), this.attributeExtractor.getAnnotatedElement());
         }

         this.valueCache.put(attributeName, value);
      }

      if (value.getClass().isArray()) {
         value = this.cloneArray(value);
      }

      return value;
   }

   private Object cloneArray(Object array) {
      if (array instanceof boolean[]) {
         return ((boolean[])((boolean[])array)).clone();
      } else if (array instanceof byte[]) {
         return ((byte[])((byte[])array)).clone();
      } else if (array instanceof char[]) {
         return ((char[])((char[])array)).clone();
      } else if (array instanceof double[]) {
         return ((double[])((double[])array)).clone();
      } else if (array instanceof float[]) {
         return ((float[])((float[])array)).clone();
      } else if (array instanceof int[]) {
         return ((int[])((int[])array)).clone();
      } else if (array instanceof long[]) {
         return ((long[])((long[])array)).clone();
      } else {
         return array instanceof short[] ? ((short[])((short[])array)).clone() : ((Object[])((Object[])array)).clone();
      }
   }

   private boolean annotationEquals(Object other) {
      if (this == other) {
         return true;
      } else if (!this.annotationType().isInstance(other)) {
         return false;
      } else {
         Iterator var2 = AnnotationUtils.getAttributeMethods(this.annotationType()).iterator();

         Object thisValue;
         Object otherValue;
         do {
            if (!var2.hasNext()) {
               return true;
            }

            Method attributeMethod = (Method)var2.next();
            thisValue = this.getAttributeValue(attributeMethod);
            otherValue = ReflectionUtils.invokeMethod(attributeMethod, other);
         } while(ObjectUtils.nullSafeEquals(thisValue, otherValue));

         return false;
      }
   }

   private int annotationHashCode() {
      int result = 0;

      Method attributeMethod;
      int hashCode;
      for(Iterator var2 = AnnotationUtils.getAttributeMethods(this.annotationType()).iterator(); var2.hasNext(); result += 127 * attributeMethod.getName().hashCode() ^ hashCode) {
         attributeMethod = (Method)var2.next();
         Object value = this.getAttributeValue(attributeMethod);
         if (value.getClass().isArray()) {
            hashCode = this.hashCodeForArray(value);
         } else {
            hashCode = value.hashCode();
         }
      }

      return result;
   }

   private int hashCodeForArray(Object array) {
      if (array instanceof boolean[]) {
         return Arrays.hashCode((boolean[])((boolean[])array));
      } else if (array instanceof byte[]) {
         return Arrays.hashCode((byte[])((byte[])array));
      } else if (array instanceof char[]) {
         return Arrays.hashCode((char[])((char[])array));
      } else if (array instanceof double[]) {
         return Arrays.hashCode((double[])((double[])array));
      } else if (array instanceof float[]) {
         return Arrays.hashCode((float[])((float[])array));
      } else if (array instanceof int[]) {
         return Arrays.hashCode((int[])((int[])array));
      } else if (array instanceof long[]) {
         return Arrays.hashCode((long[])((long[])array));
      } else {
         return array instanceof short[] ? Arrays.hashCode((short[])((short[])array)) : Arrays.hashCode((Object[])((Object[])array));
      }
   }

   private String annotationToString() {
      StringBuilder sb = (new StringBuilder("@")).append(this.annotationType().getName()).append("(");
      Iterator iterator = AnnotationUtils.getAttributeMethods(this.annotationType()).iterator();

      while(iterator.hasNext()) {
         Method attributeMethod = (Method)iterator.next();
         sb.append(attributeMethod.getName());
         sb.append('=');
         sb.append(this.attributeValueToString(this.getAttributeValue(attributeMethod)));
         sb.append(iterator.hasNext() ? ", " : "");
      }

      return sb.append(")").toString();
   }

   private String attributeValueToString(Object value) {
      return value instanceof Object[] ? "[" + StringUtils.arrayToDelimitedString((Object[])((Object[])value), ", ") + "]" : String.valueOf(value);
   }
}
