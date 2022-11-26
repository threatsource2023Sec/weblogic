package org.jboss.weld.util.reflection;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

public class GenericArrayTypeImpl implements GenericArrayType {
   private Type genericComponentType;

   public GenericArrayTypeImpl(Type genericComponentType) {
      this.genericComponentType = genericComponentType;
   }

   public GenericArrayTypeImpl(Class rawType, Type... actualTypeArguments) {
      this.genericComponentType = new ParameterizedTypeImpl(rawType, actualTypeArguments);
   }

   public Type getGenericComponentType() {
      return this.genericComponentType;
   }

   public int hashCode() {
      return this.genericComponentType == null ? 0 : this.genericComponentType.hashCode();
   }

   public boolean equals(Object obj) {
      if (obj instanceof GenericArrayType) {
         GenericArrayType that = (GenericArrayType)obj;
         if (this.genericComponentType == null) {
            return that.getGenericComponentType() == null;
         } else {
            return this.genericComponentType.equals(that.getGenericComponentType());
         }
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (this.genericComponentType instanceof Class) {
         sb.append(((Class)Reflections.cast(this.genericComponentType)).getName());
      } else {
         sb.append(this.genericComponentType.toString());
      }

      sb.append("[]");
      return sb.toString();
   }
}
