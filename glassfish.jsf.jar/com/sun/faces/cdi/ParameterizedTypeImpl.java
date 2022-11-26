package com.sun.faces.cdi;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public class ParameterizedTypeImpl implements ParameterizedType {
   private final Type ownerType;
   private final Class rawType;
   private final Type[] actualTypeArguments;

   public ParameterizedTypeImpl(Class rawType, Type[] actualTypeArguments) {
      this((Type)null, rawType, actualTypeArguments);
   }

   public ParameterizedTypeImpl(Type ownerType, Class rawType, Type[] actualTypeArguments) {
      this.ownerType = ownerType;
      this.rawType = rawType;
      this.actualTypeArguments = actualTypeArguments;
   }

   public Type getOwnerType() {
      return this.ownerType;
   }

   public Type getRawType() {
      return this.rawType;
   }

   public Type[] getActualTypeArguments() {
      return this.actualTypeArguments;
   }

   public boolean equals(Object other) {
      return other instanceof ParameterizedType ? this.equals((ParameterizedType)other) : false;
   }

   public boolean equals(ParameterizedType other) {
      return this == other ? true : Objects.equals(this.getOwnerType(), other.getOwnerType()) && Objects.equals(this.getRawType(), other.getRawType()) && Arrays.equals(this.getActualTypeArguments(), other.getActualTypeArguments());
   }

   public int hashCode() {
      return Objects.hashCode(this.getOwnerType()) ^ Objects.hashCode(this.getRawType()) ^ Arrays.hashCode(this.getActualTypeArguments());
   }
}
