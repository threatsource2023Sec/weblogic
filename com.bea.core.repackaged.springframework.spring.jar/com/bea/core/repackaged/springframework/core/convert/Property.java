package com.bea.core.repackaged.springframework.core.convert;

import com.bea.core.repackaged.springframework.core.GenericTypeResolver;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Property {
   private static Map annotationCache = new ConcurrentReferenceHashMap();
   private final Class objectType;
   @Nullable
   private final Method readMethod;
   @Nullable
   private final Method writeMethod;
   private final String name;
   private final MethodParameter methodParameter;
   @Nullable
   private Annotation[] annotations;

   public Property(Class objectType, @Nullable Method readMethod, @Nullable Method writeMethod) {
      this(objectType, readMethod, writeMethod, (String)null);
   }

   public Property(Class objectType, @Nullable Method readMethod, @Nullable Method writeMethod, @Nullable String name) {
      this.objectType = objectType;
      this.readMethod = readMethod;
      this.writeMethod = writeMethod;
      this.methodParameter = this.resolveMethodParameter();
      this.name = name != null ? name : this.resolveName();
   }

   public Class getObjectType() {
      return this.objectType;
   }

   public String getName() {
      return this.name;
   }

   public Class getType() {
      return this.methodParameter.getParameterType();
   }

   @Nullable
   public Method getReadMethod() {
      return this.readMethod;
   }

   @Nullable
   public Method getWriteMethod() {
      return this.writeMethod;
   }

   MethodParameter getMethodParameter() {
      return this.methodParameter;
   }

   Annotation[] getAnnotations() {
      if (this.annotations == null) {
         this.annotations = this.resolveAnnotations();
      }

      return this.annotations;
   }

   private String resolveName() {
      int index;
      if (this.readMethod != null) {
         index = this.readMethod.getName().indexOf("get");
         if (index != -1) {
            index += 3;
         } else {
            index = this.readMethod.getName().indexOf("is");
            if (index == -1) {
               throw new IllegalArgumentException("Not a getter method");
            }

            index += 2;
         }

         return StringUtils.uncapitalize(this.readMethod.getName().substring(index));
      } else if (this.writeMethod != null) {
         index = this.writeMethod.getName().indexOf("set");
         if (index == -1) {
            throw new IllegalArgumentException("Not a setter method");
         } else {
            index += 3;
            return StringUtils.uncapitalize(this.writeMethod.getName().substring(index));
         }
      } else {
         throw new IllegalStateException("Property is neither readable nor writeable");
      }
   }

   private MethodParameter resolveMethodParameter() {
      MethodParameter read = this.resolveReadMethodParameter();
      MethodParameter write = this.resolveWriteMethodParameter();
      if (write == null) {
         if (read == null) {
            throw new IllegalStateException("Property is neither readable nor writeable");
         } else {
            return read;
         }
      } else {
         if (read != null) {
            Class readType = read.getParameterType();
            Class writeType = write.getParameterType();
            if (!writeType.equals(readType) && writeType.isAssignableFrom(readType)) {
               return read;
            }
         }

         return write;
      }
   }

   @Nullable
   private MethodParameter resolveReadMethodParameter() {
      return this.getReadMethod() == null ? null : this.resolveParameterType(new MethodParameter(this.getReadMethod(), -1));
   }

   @Nullable
   private MethodParameter resolveWriteMethodParameter() {
      return this.getWriteMethod() == null ? null : this.resolveParameterType(new MethodParameter(this.getWriteMethod(), 0));
   }

   private MethodParameter resolveParameterType(MethodParameter parameter) {
      GenericTypeResolver.resolveParameterType(parameter, this.getObjectType());
      return parameter;
   }

   private Annotation[] resolveAnnotations() {
      Annotation[] annotations = (Annotation[])annotationCache.get(this);
      if (annotations == null) {
         Map annotationMap = new LinkedHashMap();
         this.addAnnotationsToMap(annotationMap, this.getReadMethod());
         this.addAnnotationsToMap(annotationMap, this.getWriteMethod());
         this.addAnnotationsToMap(annotationMap, this.getField());
         annotations = (Annotation[])annotationMap.values().toArray(new Annotation[0]);
         annotationCache.put(this, annotations);
      }

      return annotations;
   }

   private void addAnnotationsToMap(Map annotationMap, @Nullable AnnotatedElement object) {
      if (object != null) {
         Annotation[] var3 = object.getAnnotations();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annotation = var3[var5];
            annotationMap.put(annotation.annotationType(), annotation);
         }
      }

   }

   @Nullable
   private Field getField() {
      String name = this.getName();
      if (!StringUtils.hasLength(name)) {
         return null;
      } else {
         Field field = null;
         Class declaringClass = this.declaringClass();
         if (declaringClass != null) {
            field = ReflectionUtils.findField(declaringClass, name);
            if (field == null) {
               field = ReflectionUtils.findField(declaringClass, StringUtils.uncapitalize(name));
               if (field == null) {
                  field = ReflectionUtils.findField(declaringClass, StringUtils.capitalize(name));
               }
            }
         }

         return field;
      }
   }

   @Nullable
   private Class declaringClass() {
      if (this.getReadMethod() != null) {
         return this.getReadMethod().getDeclaringClass();
      } else {
         return this.getWriteMethod() != null ? this.getWriteMethod().getDeclaringClass() : null;
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Property)) {
         return false;
      } else {
         Property otherProperty = (Property)other;
         return ObjectUtils.nullSafeEquals(this.objectType, otherProperty.objectType) && ObjectUtils.nullSafeEquals(this.name, otherProperty.name) && ObjectUtils.nullSafeEquals(this.readMethod, otherProperty.readMethod) && ObjectUtils.nullSafeEquals(this.writeMethod, otherProperty.writeMethod);
      }
   }

   public int hashCode() {
      return ObjectUtils.nullSafeHashCode((Object)this.objectType) * 31 + ObjectUtils.nullSafeHashCode((Object)this.name);
   }
}
