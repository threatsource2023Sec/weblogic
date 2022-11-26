package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ParameterizedTypeReference {
   private final Type type;

   protected ParameterizedTypeReference() {
      Class parameterizedTypeReferenceSubclass = findParameterizedTypeReferenceSubclass(this.getClass());
      Type type = parameterizedTypeReferenceSubclass.getGenericSuperclass();
      Assert.isInstanceOf(ParameterizedType.class, type, (String)"Type must be a parameterized type");
      ParameterizedType parameterizedType = (ParameterizedType)type;
      Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
      Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
      this.type = actualTypeArguments[0];
   }

   private ParameterizedTypeReference(Type type) {
      this.type = type;
   }

   public Type getType() {
      return this.type;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof ParameterizedTypeReference && this.type.equals(((ParameterizedTypeReference)other).type);
   }

   public int hashCode() {
      return this.type.hashCode();
   }

   public String toString() {
      return "ParameterizedTypeReference<" + this.type + ">";
   }

   public static ParameterizedTypeReference forType(Type type) {
      return new ParameterizedTypeReference(type) {
      };
   }

   private static Class findParameterizedTypeReferenceSubclass(Class child) {
      Class parent = child.getSuperclass();
      if (Object.class == parent) {
         throw new IllegalStateException("Expected ParameterizedTypeReference superclass");
      } else {
         return ParameterizedTypeReference.class == parent ? child : findParameterizedTypeReferenceSubclass(parent);
      }
   }

   // $FF: synthetic method
   ParameterizedTypeReference(Type x0, Object x1) {
      this(x0);
   }
}
