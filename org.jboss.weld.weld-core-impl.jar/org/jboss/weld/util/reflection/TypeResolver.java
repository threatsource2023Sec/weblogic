package org.jboss.weld.util.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

public class TypeResolver {
   private final Map resolvedTypeVariables;

   public TypeResolver(Map resolvedTypeVariables) {
      this.resolvedTypeVariables = resolvedTypeVariables;
   }

   public Type resolveType(TypeVariable variable) {
      Type resolvedType = (Type)this.resolvedTypeVariables.get(variable);
      return (Type)(resolvedType == null ? variable : resolvedType);
   }

   public Type resolveType(ParameterizedType type) {
      Type[] unresolvedTypeArguments = type.getActualTypeArguments();
      boolean modified = false;
      Type[] resolvedTypeArguments = new Type[unresolvedTypeArguments.length];

      for(int i = 0; i < unresolvedTypeArguments.length; ++i) {
         Type resolvedType = unresolvedTypeArguments[i];
         if (resolvedType instanceof TypeVariable) {
            resolvedType = this.resolveType((TypeVariable)resolvedType);
         }

         if (resolvedType instanceof ParameterizedType) {
            resolvedType = this.resolveType((ParameterizedType)resolvedType);
         }

         resolvedTypeArguments[i] = resolvedType;
         if (unresolvedTypeArguments[i] != resolvedType) {
            modified = true;
         }
      }

      if (modified) {
         return new ParameterizedTypeImpl(type.getRawType(), resolvedTypeArguments, type.getOwnerType());
      } else {
         return type;
      }
   }

   public Type resolveType(GenericArrayType type) {
      Type genericComponentType = type.getGenericComponentType();
      Type resolvedType = genericComponentType;
      if (genericComponentType instanceof TypeVariable) {
         resolvedType = this.resolveType((TypeVariable)genericComponentType);
      }

      if (genericComponentType instanceof ParameterizedType) {
         resolvedType = this.resolveType((ParameterizedType)genericComponentType);
      }

      if (genericComponentType instanceof GenericArrayType) {
         resolvedType = this.resolveType((GenericArrayType)genericComponentType);
      }

      if (resolvedType instanceof Class) {
         Class componentClass = (Class)resolvedType;
         return Array.newInstance(componentClass, 0).getClass();
      } else {
         return (Type)(resolvedType == genericComponentType ? type : new GenericArrayTypeImpl(resolvedType));
      }
   }

   public Type resolveType(Type type) {
      if (type instanceof ParameterizedType) {
         return this.resolveType((ParameterizedType)type);
      } else if (type instanceof TypeVariable) {
         return this.resolveType((TypeVariable)type);
      } else {
         return type instanceof GenericArrayType ? this.resolveType((GenericArrayType)type) : type;
      }
   }

   public Map getResolvedTypeVariables() {
      return this.resolvedTypeVariables;
   }
}
