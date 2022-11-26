package com.bea.core.repackaged.springframework.core.convert;

import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TypeDescriptor implements Serializable {
   private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];
   private static final Map commonTypesCache = new HashMap(32);
   private static final Class[] CACHED_COMMON_TYPES;
   private final Class type;
   private final ResolvableType resolvableType;
   private final AnnotatedElementAdapter annotatedElement;

   public TypeDescriptor(MethodParameter methodParameter) {
      this.resolvableType = ResolvableType.forMethodParameter(methodParameter);
      this.type = this.resolvableType.resolve(methodParameter.getNestedParameterType());
      this.annotatedElement = new AnnotatedElementAdapter(methodParameter.getParameterIndex() == -1 ? methodParameter.getMethodAnnotations() : methodParameter.getParameterAnnotations());
   }

   public TypeDescriptor(Field field) {
      this.resolvableType = ResolvableType.forField(field);
      this.type = this.resolvableType.resolve(field.getType());
      this.annotatedElement = new AnnotatedElementAdapter(field.getAnnotations());
   }

   public TypeDescriptor(Property property) {
      Assert.notNull(property, (String)"Property must not be null");
      this.resolvableType = ResolvableType.forMethodParameter(property.getMethodParameter());
      this.type = this.resolvableType.resolve(property.getType());
      this.annotatedElement = new AnnotatedElementAdapter(property.getAnnotations());
   }

   public TypeDescriptor(ResolvableType resolvableType, @Nullable Class type, @Nullable Annotation[] annotations) {
      this.resolvableType = resolvableType;
      this.type = type != null ? type : resolvableType.toClass();
      this.annotatedElement = new AnnotatedElementAdapter(annotations);
   }

   public Class getObjectType() {
      return ClassUtils.resolvePrimitiveIfNecessary(this.getType());
   }

   public Class getType() {
      return this.type;
   }

   public ResolvableType getResolvableType() {
      return this.resolvableType;
   }

   public Object getSource() {
      return this.resolvableType.getSource();
   }

   public TypeDescriptor narrow(@Nullable Object value) {
      if (value == null) {
         return this;
      } else {
         ResolvableType narrowed = ResolvableType.forType(value.getClass(), (ResolvableType)this.getResolvableType());
         return new TypeDescriptor(narrowed, value.getClass(), this.getAnnotations());
      }
   }

   @Nullable
   public TypeDescriptor upcast(@Nullable Class superType) {
      if (superType == null) {
         return null;
      } else {
         Assert.isAssignable(superType, this.getType());
         return new TypeDescriptor(this.getResolvableType().as(superType), superType, this.getAnnotations());
      }
   }

   public String getName() {
      return ClassUtils.getQualifiedName(this.getType());
   }

   public boolean isPrimitive() {
      return this.getType().isPrimitive();
   }

   public Annotation[] getAnnotations() {
      return this.annotatedElement.getAnnotations();
   }

   public boolean hasAnnotation(Class annotationType) {
      return this.annotatedElement.isEmpty() ? false : AnnotatedElementUtils.isAnnotated(this.annotatedElement, (Class)annotationType);
   }

   @Nullable
   public Annotation getAnnotation(Class annotationType) {
      return this.annotatedElement.isEmpty() ? null : AnnotatedElementUtils.getMergedAnnotation(this.annotatedElement, annotationType);
   }

   public boolean isAssignableTo(TypeDescriptor typeDescriptor) {
      boolean typesAssignable = typeDescriptor.getObjectType().isAssignableFrom(this.getObjectType());
      if (!typesAssignable) {
         return false;
      } else if (this.isArray() && typeDescriptor.isArray()) {
         return this.isNestedAssignable(this.getElementTypeDescriptor(), typeDescriptor.getElementTypeDescriptor());
      } else if (this.isCollection() && typeDescriptor.isCollection()) {
         return this.isNestedAssignable(this.getElementTypeDescriptor(), typeDescriptor.getElementTypeDescriptor());
      } else if (this.isMap() && typeDescriptor.isMap()) {
         return this.isNestedAssignable(this.getMapKeyTypeDescriptor(), typeDescriptor.getMapKeyTypeDescriptor()) && this.isNestedAssignable(this.getMapValueTypeDescriptor(), typeDescriptor.getMapValueTypeDescriptor());
      } else {
         return true;
      }
   }

   private boolean isNestedAssignable(@Nullable TypeDescriptor nestedTypeDescriptor, @Nullable TypeDescriptor otherNestedTypeDescriptor) {
      return nestedTypeDescriptor == null || otherNestedTypeDescriptor == null || nestedTypeDescriptor.isAssignableTo(otherNestedTypeDescriptor);
   }

   public boolean isCollection() {
      return Collection.class.isAssignableFrom(this.getType());
   }

   public boolean isArray() {
      return this.getType().isArray();
   }

   @Nullable
   public TypeDescriptor getElementTypeDescriptor() {
      if (this.getResolvableType().isArray()) {
         return new TypeDescriptor(this.getResolvableType().getComponentType(), (Class)null, this.getAnnotations());
      } else {
         return Stream.class.isAssignableFrom(this.getType()) ? getRelatedIfResolvable(this, this.getResolvableType().as(Stream.class).getGeneric(0)) : getRelatedIfResolvable(this, this.getResolvableType().asCollection().getGeneric(0));
      }
   }

   @Nullable
   public TypeDescriptor elementTypeDescriptor(Object element) {
      return this.narrow(element, this.getElementTypeDescriptor());
   }

   public boolean isMap() {
      return Map.class.isAssignableFrom(this.getType());
   }

   @Nullable
   public TypeDescriptor getMapKeyTypeDescriptor() {
      Assert.state(this.isMap(), "Not a [java.util.Map]");
      return getRelatedIfResolvable(this, this.getResolvableType().asMap().getGeneric(0));
   }

   @Nullable
   public TypeDescriptor getMapKeyTypeDescriptor(Object mapKey) {
      return this.narrow(mapKey, this.getMapKeyTypeDescriptor());
   }

   @Nullable
   public TypeDescriptor getMapValueTypeDescriptor() {
      Assert.state(this.isMap(), "Not a [java.util.Map]");
      return getRelatedIfResolvable(this, this.getResolvableType().asMap().getGeneric(1));
   }

   @Nullable
   public TypeDescriptor getMapValueTypeDescriptor(Object mapValue) {
      return this.narrow(mapValue, this.getMapValueTypeDescriptor());
   }

   @Nullable
   private TypeDescriptor narrow(@Nullable Object value, @Nullable TypeDescriptor typeDescriptor) {
      if (typeDescriptor != null) {
         return typeDescriptor.narrow(value);
      } else {
         return value != null ? this.narrow(value) : null;
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof TypeDescriptor)) {
         return false;
      } else {
         TypeDescriptor otherDesc = (TypeDescriptor)other;
         if (this.getType() != otherDesc.getType()) {
            return false;
         } else if (!this.annotationsMatch(otherDesc)) {
            return false;
         } else if (!this.isCollection() && !this.isArray()) {
            if (!this.isMap()) {
               return true;
            } else {
               return ObjectUtils.nullSafeEquals(this.getMapKeyTypeDescriptor(), otherDesc.getMapKeyTypeDescriptor()) && ObjectUtils.nullSafeEquals(this.getMapValueTypeDescriptor(), otherDesc.getMapValueTypeDescriptor());
            }
         } else {
            return ObjectUtils.nullSafeEquals(this.getElementTypeDescriptor(), otherDesc.getElementTypeDescriptor());
         }
      }
   }

   private boolean annotationsMatch(TypeDescriptor otherDesc) {
      Annotation[] anns = this.getAnnotations();
      Annotation[] otherAnns = otherDesc.getAnnotations();
      if (anns == otherAnns) {
         return true;
      } else if (anns.length != otherAnns.length) {
         return false;
      } else {
         if (anns.length > 0) {
            for(int i = 0; i < anns.length; ++i) {
               if (!this.annotationEquals(anns[i], otherAnns[i])) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private boolean annotationEquals(Annotation ann, Annotation otherAnn) {
      return ann == otherAnn || ann.getClass() == otherAnn.getClass() && ann.equals(otherAnn);
   }

   public int hashCode() {
      return this.getType().hashCode();
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      Annotation[] var2 = this.getAnnotations();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation ann = var2[var4];
         builder.append("@").append(ann.annotationType().getName()).append(' ');
      }

      builder.append(this.getResolvableType().toString());
      return builder.toString();
   }

   @Nullable
   public static TypeDescriptor forObject(@Nullable Object source) {
      return source != null ? valueOf(source.getClass()) : null;
   }

   public static TypeDescriptor valueOf(@Nullable Class type) {
      if (type == null) {
         type = Object.class;
      }

      TypeDescriptor desc = (TypeDescriptor)commonTypesCache.get(type);
      return desc != null ? desc : new TypeDescriptor(ResolvableType.forClass(type), (Class)null, (Annotation[])null);
   }

   public static TypeDescriptor collection(Class collectionType, @Nullable TypeDescriptor elementTypeDescriptor) {
      Assert.notNull(collectionType, (String)"Collection type must not be null");
      if (!Collection.class.isAssignableFrom(collectionType)) {
         throw new IllegalArgumentException("Collection type must be a [java.util.Collection]");
      } else {
         ResolvableType element = elementTypeDescriptor != null ? elementTypeDescriptor.resolvableType : null;
         return new TypeDescriptor(ResolvableType.forClassWithGenerics(collectionType, element), (Class)null, (Annotation[])null);
      }
   }

   public static TypeDescriptor map(Class mapType, @Nullable TypeDescriptor keyTypeDescriptor, @Nullable TypeDescriptor valueTypeDescriptor) {
      Assert.notNull(mapType, (String)"Map type must not be null");
      if (!Map.class.isAssignableFrom(mapType)) {
         throw new IllegalArgumentException("Map type must be a [java.util.Map]");
      } else {
         ResolvableType key = keyTypeDescriptor != null ? keyTypeDescriptor.resolvableType : null;
         ResolvableType value = valueTypeDescriptor != null ? valueTypeDescriptor.resolvableType : null;
         return new TypeDescriptor(ResolvableType.forClassWithGenerics(mapType, key, value), (Class)null, (Annotation[])null);
      }
   }

   @Nullable
   public static TypeDescriptor array(@Nullable TypeDescriptor elementTypeDescriptor) {
      return elementTypeDescriptor == null ? null : new TypeDescriptor(ResolvableType.forArrayComponent(elementTypeDescriptor.resolvableType), (Class)null, elementTypeDescriptor.getAnnotations());
   }

   @Nullable
   public static TypeDescriptor nested(MethodParameter methodParameter, int nestingLevel) {
      if (methodParameter.getNestingLevel() != 1) {
         throw new IllegalArgumentException("MethodParameter nesting level must be 1: use the nestingLevel parameter to specify the desired nestingLevel for nested type traversal");
      } else {
         return nested(new TypeDescriptor(methodParameter), nestingLevel);
      }
   }

   @Nullable
   public static TypeDescriptor nested(Field field, int nestingLevel) {
      return nested(new TypeDescriptor(field), nestingLevel);
   }

   @Nullable
   public static TypeDescriptor nested(Property property, int nestingLevel) {
      return nested(new TypeDescriptor(property), nestingLevel);
   }

   @Nullable
   private static TypeDescriptor nested(TypeDescriptor typeDescriptor, int nestingLevel) {
      ResolvableType nested = typeDescriptor.resolvableType;

      for(int i = 0; i < nestingLevel; ++i) {
         if (Object.class != nested.getType()) {
            nested = nested.getNested(2);
         }
      }

      if (nested == ResolvableType.NONE) {
         return null;
      } else {
         return getRelatedIfResolvable(typeDescriptor, nested);
      }
   }

   @Nullable
   private static TypeDescriptor getRelatedIfResolvable(TypeDescriptor source, ResolvableType type) {
      return type.resolve() == null ? null : new TypeDescriptor(type, (Class)null, source.getAnnotations());
   }

   static {
      CACHED_COMMON_TYPES = new Class[]{Boolean.TYPE, Boolean.class, Byte.TYPE, Byte.class, Character.TYPE, Character.class, Double.TYPE, Double.class, Float.TYPE, Float.class, Integer.TYPE, Integer.class, Long.TYPE, Long.class, Short.TYPE, Short.class, String.class, Object.class};
      Class[] var0 = CACHED_COMMON_TYPES;
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Class preCachedClass = var0[var2];
         commonTypesCache.put(preCachedClass, valueOf(preCachedClass));
      }

   }

   private class AnnotatedElementAdapter implements AnnotatedElement, Serializable {
      @Nullable
      private final Annotation[] annotations;

      public AnnotatedElementAdapter(@Nullable Annotation[] annotations) {
         this.annotations = annotations;
      }

      public boolean isAnnotationPresent(Class annotationClass) {
         Annotation[] var2 = this.getAnnotations();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Annotation annotation = var2[var4];
            if (annotation.annotationType() == annotationClass) {
               return true;
            }
         }

         return false;
      }

      @Nullable
      public Annotation getAnnotation(Class annotationClass) {
         Annotation[] var2 = this.getAnnotations();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Annotation annotation = var2[var4];
            if (annotation.annotationType() == annotationClass) {
               return annotation;
            }
         }

         return null;
      }

      public Annotation[] getAnnotations() {
         return this.annotations != null ? this.annotations : TypeDescriptor.EMPTY_ANNOTATION_ARRAY;
      }

      public Annotation[] getDeclaredAnnotations() {
         return this.getAnnotations();
      }

      public boolean isEmpty() {
         return ObjectUtils.isEmpty((Object[])this.annotations);
      }

      public boolean equals(Object other) {
         return this == other || other instanceof AnnotatedElementAdapter && Arrays.equals(this.annotations, ((AnnotatedElementAdapter)other).annotations);
      }

      public int hashCode() {
         return Arrays.hashCode(this.annotations);
      }

      public String toString() {
         return TypeDescriptor.this.toString();
      }
   }
}
