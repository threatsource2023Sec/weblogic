package org.jboss.weld.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jboss.weld.resolution.CovariantTypes;
import org.jboss.weld.util.collections.Arrays2;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.GenericArrayTypeImpl;
import org.jboss.weld.util.reflection.ParameterizedTypeImpl;
import org.jboss.weld.util.reflection.Reflections;

public class Types {
   public static final Function TYPE_TO_CLASS_FUNCTION = Reflections::getRawType;

   private Types() {
   }

   public static Type boxedType(Type type) {
      return (Type)(type instanceof Class ? boxedClass((Class)type) : type);
   }

   public static Class boxedClass(Class type) {
      if (!type.isPrimitive()) {
         return type;
      } else if (type.equals(Boolean.TYPE)) {
         return Boolean.class;
      } else if (type.equals(Character.TYPE)) {
         return Character.class;
      } else if (type.equals(Byte.TYPE)) {
         return Byte.class;
      } else if (type.equals(Short.TYPE)) {
         return Short.class;
      } else if (type.equals(Integer.TYPE)) {
         return Integer.class;
      } else if (type.equals(Long.TYPE)) {
         return Long.class;
      } else if (type.equals(Float.TYPE)) {
         return Float.class;
      } else if (type.equals(Double.TYPE)) {
         return Double.class;
      } else {
         return type.equals(Void.TYPE) ? Void.class : type;
      }
   }

   public static String getTypeId(Type type) {
      if (type instanceof Class) {
         return ((Class)Reflections.cast(type)).getName();
      } else {
         StringBuilder builder;
         if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)type;
            builder = new StringBuilder(getTypeId(pt.getRawType()));
            builder.append("<");

            for(int i = 0; i < pt.getActualTypeArguments().length; ++i) {
               if (i > 0) {
                  builder.append(",");
               }

               builder.append(getTypeId(pt.getActualTypeArguments()[i]));
            }

            builder.append(">");
            return builder.toString();
         } else if (type instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType)type;
            builder = new StringBuilder(getTypeId(arrayType.getGenericComponentType()));
            builder.append("[]");
            return builder.toString();
         } else {
            throw new IllegalArgumentException("Cannot create type id for " + type.toString());
         }
      }
   }

   public static Type getCanonicalType(Class clazz) {
      if (clazz.isArray()) {
         Class componentType = clazz.getComponentType();
         Type resolvedComponentType = getCanonicalType(componentType);
         if (componentType != resolvedComponentType) {
            return new GenericArrayTypeImpl(resolvedComponentType);
         }
      }

      if (clazz.getTypeParameters().length > 0) {
         Type[] actualTypeParameters = clazz.getTypeParameters();
         return new ParameterizedTypeImpl(clazz, actualTypeParameters, clazz.getDeclaringClass());
      } else {
         return clazz;
      }
   }

   public static Type getCanonicalType(Type type) {
      if (type instanceof Class) {
         Class clazz = (Class)type;
         return getCanonicalType(clazz);
      } else {
         return type;
      }
   }

   public static boolean containsTypeVariable(Type type) {
      type = getCanonicalType(type);
      if (type instanceof TypeVariable) {
         return true;
      } else {
         if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type[] var2 = parameterizedType.getActualTypeArguments();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Type t = var2[var4];
               if (containsTypeVariable(t)) {
                  return true;
               }
            }
         }

         if (type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType)type;
            return containsTypeVariable(genericArrayType.getGenericComponentType());
         } else {
            return false;
         }
      }
   }

   public static Set getRawTypes(Set types) {
      return (Set)types.stream().map(Reflections::getRawType).collect(ImmutableSet.collector());
   }

   public static Class[] getRawTypes(Type[] types) {
      if (types.length == 0) {
         return Arrays2.EMPTY_CLASS_ARRAY;
      } else {
         Class[] result = new Class[types.length];

         for(int i = 0; i < types.length; ++i) {
            result[i] = (Class)TYPE_TO_CLASS_FUNCTION.apply(types[i]);
         }

         return result;
      }
   }

   public static Map buildClassNameMap(Iterable set) {
      Map classNameMap = new HashMap();
      Iterator var2 = set.iterator();

      while(var2.hasNext()) {
         Class javaClass = (Class)var2.next();
         classNameMap.put(javaClass.getName(), javaClass);
      }

      return classNameMap;
   }

   public static boolean isActualType(Type type) {
      return type instanceof Class || type instanceof ParameterizedType || type instanceof GenericArrayType;
   }

   public static boolean isArray(Type type) {
      return type instanceof GenericArrayType || type instanceof Class && ((Class)type).isArray();
   }

   public static Type getArrayComponentType(Type type) {
      if (type instanceof GenericArrayType) {
         return ((GenericArrayType)GenericArrayType.class.cast(type)).getGenericComponentType();
      } else {
         if (type instanceof Class) {
            Class clazz = (Class)type;
            if (clazz.isArray()) {
               return clazz.getComponentType();
            }
         }

         throw new IllegalArgumentException("Not an array type " + type);
      }
   }

   public static boolean isArrayOfUnboundedTypeVariablesOrObjects(Type[] types) {
      Type[] var1 = types;
      int var2 = types.length;
      int var3 = 0;

      while(true) {
         if (var3 >= var2) {
            return true;
         }

         Type type = var1[var3];
         if (!Object.class.equals(type)) {
            if (!(type instanceof TypeVariable)) {
               break;
            }

            Type[] bounds = ((TypeVariable)type).getBounds();
            if (bounds != null && bounds.length != 0 && (bounds.length != 1 || !Object.class.equals(bounds[0]))) {
               break;
            }
         }

         ++var3;
      }

      return false;
   }

   public static boolean isRawGenericType(Type type) {
      if (!(type instanceof Class)) {
         return false;
      } else {
         Class clazz = (Class)type;
         if (clazz.isArray()) {
            Class componentType = clazz.getComponentType();
            return isRawGenericType(componentType);
         } else {
            return clazz.getTypeParameters().length > 0;
         }
      }
   }

   public static boolean isIllegalBeanType(Type beanType) {
      boolean result = false;
      if (beanType instanceof TypeVariable) {
         result = true;
      } else if (beanType instanceof ParameterizedType) {
         ParameterizedType parameterizedType = (ParameterizedType)beanType;
         Type[] var3 = parameterizedType.getActualTypeArguments();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Type typeArgument = var3[var5];
            if (!(typeArgument instanceof TypeVariable) && (typeArgument instanceof WildcardType || isIllegalBeanType(typeArgument))) {
               result = true;
               break;
            }
         }
      } else if (beanType instanceof GenericArrayType) {
         GenericArrayType arrayType = (GenericArrayType)beanType;
         result = isIllegalBeanType(arrayType.getGenericComponentType());
      }

      return result;
   }

   public static boolean isMoreSpecific(Type type1, Type type2) {
      return type1.equals(type2) ? false : CovariantTypes.isAssignableFrom(type2, type1);
   }
}
