package org.hibernate.validator.internal.util;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class ReflectionHelper {
   private static final String PROPERTY_ACCESSOR_PREFIX_GET = "get";
   private static final String PROPERTY_ACCESSOR_PREFIX_IS = "is";
   private static final String PROPERTY_ACCESSOR_PREFIX_HAS = "has";
   public static final String[] PROPERTY_ACCESSOR_PREFIXES = new String[]{"get", "is", "has"};
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final Map PRIMITIVE_TO_WRAPPER_TYPES;
   private static final Map WRAPPER_TO_PRIMITIVE_TYPES;

   private ReflectionHelper() {
   }

   public static String getPropertyName(Member member) {
      String name = null;
      if (member instanceof Field) {
         name = member.getName();
      }

      if (member instanceof Method) {
         String methodName = member.getName();
         String[] var3 = PROPERTY_ACCESSOR_PREFIXES;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String prefix = var3[var5];
            if (methodName.startsWith(prefix)) {
               name = StringHelper.decapitalize(methodName.substring(prefix.length()));
            }
         }
      }

      return name;
   }

   public static boolean isGetterMethod(Executable executable) {
      if (!(executable instanceof Method)) {
         return false;
      } else {
         Method method = (Method)executable;
         if (method.getParameterTypes().length != 0) {
            return false;
         } else {
            String methodName = method.getName();
            if (methodName.startsWith("get") && method.getReturnType() != Void.TYPE) {
               return true;
            } else if (methodName.startsWith("is") && method.getReturnType() == Boolean.TYPE) {
               return true;
            } else {
               return methodName.startsWith("has") && method.getReturnType() == Boolean.TYPE;
            }
         }
      }
   }

   public static Type typeOf(Member member) {
      Object type;
      if (member instanceof Field) {
         type = ((Field)member).getGenericType();
      } else if (member instanceof Method) {
         type = ((Method)member).getGenericReturnType();
      } else {
         if (!(member instanceof Constructor)) {
            throw LOG.getMemberIsNeitherAFieldNorAMethodException(member);
         }

         type = member.getDeclaringClass();
      }

      if (type instanceof TypeVariable) {
         type = TypeHelper.getErasedType((Type)type);
      }

      return (Type)type;
   }

   public static Type typeOf(Executable executable, int parameterIndex) {
      Type[] genericParameterTypes = executable.getGenericParameterTypes();
      if (parameterIndex >= ((Object[])genericParameterTypes).length) {
         genericParameterTypes = executable.getParameterTypes();
      }

      Type type = ((Object[])genericParameterTypes)[parameterIndex];
      if (type instanceof TypeVariable) {
         type = TypeHelper.getErasedType((Type)type);
      }

      return (Type)type;
   }

   public static Object getValue(Field field, Object object) {
      try {
         return field.get(object);
      } catch (IllegalAccessException var3) {
         throw LOG.getUnableToAccessMemberException(field.getName(), var3);
      }
   }

   public static Object getValue(Method method, Object object) {
      try {
         return method.invoke(object);
      } catch (InvocationTargetException | IllegalAccessException var3) {
         throw LOG.getUnableToAccessMemberException(method.getName(), var3);
      }
   }

   public static boolean isCollection(Type type) {
      return isIterable(type) || isMap(type) || TypeHelper.isArray(type);
   }

   public static Type getCollectionElementType(Type type) {
      Type indexedType = null;
      ParameterizedType paramType;
      if (isIterable(type) && type instanceof ParameterizedType) {
         paramType = (ParameterizedType)type;
         indexedType = paramType.getActualTypeArguments()[0];
      } else if (isMap(type) && type instanceof ParameterizedType) {
         paramType = (ParameterizedType)type;
         indexedType = paramType.getActualTypeArguments()[1];
      } else if (TypeHelper.isArray(type)) {
         indexedType = TypeHelper.getComponentType(type);
      }

      return indexedType;
   }

   public static boolean isIndexable(Type type) {
      return isList(type) || isMap(type) || TypeHelper.isArray(type);
   }

   public static Class getClassFromType(Type type) {
      if (type instanceof Class) {
         return (Class)type;
      } else if (type instanceof ParameterizedType) {
         return getClassFromType(((ParameterizedType)type).getRawType());
      } else if (type instanceof GenericArrayType) {
         return Object[].class;
      } else {
         throw LOG.getUnableToConvertTypeToClassException(type);
      }
   }

   public static boolean isIterable(Type type) {
      if (type instanceof Class && Iterable.class.isAssignableFrom((Class)type)) {
         return true;
      } else if (type instanceof ParameterizedType) {
         return isIterable(((ParameterizedType)type).getRawType());
      } else if (!(type instanceof WildcardType)) {
         return false;
      } else {
         Type[] upperBounds = ((WildcardType)type).getUpperBounds();
         return upperBounds.length != 0 && isIterable(upperBounds[0]);
      }
   }

   public static boolean isMap(Type type) {
      if (type instanceof Class && Map.class.isAssignableFrom((Class)type)) {
         return true;
      } else if (type instanceof ParameterizedType) {
         return isMap(((ParameterizedType)type).getRawType());
      } else if (!(type instanceof WildcardType)) {
         return false;
      } else {
         Type[] upperBounds = ((WildcardType)type).getUpperBounds();
         return upperBounds.length != 0 && isMap(upperBounds[0]);
      }
   }

   public static boolean isList(Type type) {
      if (type instanceof Class && List.class.isAssignableFrom((Class)type)) {
         return true;
      } else if (type instanceof ParameterizedType) {
         return isList(((ParameterizedType)type).getRawType());
      } else if (!(type instanceof WildcardType)) {
         return false;
      } else {
         Type[] upperBounds = ((WildcardType)type).getUpperBounds();
         return upperBounds.length != 0 && isList(upperBounds[0]);
      }
   }

   public static Object getIndexedValue(Object value, int index) {
      if (value == null) {
         return null;
      } else {
         Type type = value.getClass();
         Iterable iterable;
         if (isIterable(type)) {
            iterable = (Iterable)value;
         } else {
            if (!TypeHelper.isArray(type)) {
               return null;
            }

            iterable = CollectionHelper.iterableFromArray(value);
         }

         int i = 0;

         for(Iterator var5 = iterable.iterator(); var5.hasNext(); ++i) {
            Object o = var5.next();
            if (i == index) {
               return o;
            }
         }

         return null;
      }
   }

   public static Object getMappedValue(Object value, Object key) {
      if (!(value instanceof Map)) {
         return null;
      } else {
         Map map = (Map)value;
         return map.get(key);
      }
   }

   private static Class internalBoxedType(Class primitiveType) {
      Class wrapperType = (Class)PRIMITIVE_TO_WRAPPER_TYPES.get(primitiveType);
      if (wrapperType == null) {
         throw LOG.getHasToBeAPrimitiveTypeException(primitiveType.getClass());
      } else {
         return wrapperType;
      }
   }

   public static Type boxedType(Type type) {
      return (Type)(type instanceof Class && ((Class)type).isPrimitive() ? internalBoxedType((Class)type) : type);
   }

   public static Class boxedType(Class type) {
      return type.isPrimitive() ? internalBoxedType(type) : type;
   }

   public static Class unBoxedType(Class type) {
      Class wrapperType = (Class)WRAPPER_TO_PRIMITIVE_TYPES.get(type);
      if (wrapperType == null) {
         throw LOG.getHasToBeABoxedTypeException(type.getClass());
      } else {
         return wrapperType;
      }
   }

   static {
      Map tmpMap = CollectionHelper.newHashMap(9);
      tmpMap.put(Boolean.TYPE, Boolean.class);
      tmpMap.put(Character.TYPE, Character.class);
      tmpMap.put(Double.TYPE, Double.class);
      tmpMap.put(Float.TYPE, Float.class);
      tmpMap.put(Long.TYPE, Long.class);
      tmpMap.put(Integer.TYPE, Integer.class);
      tmpMap.put(Short.TYPE, Short.class);
      tmpMap.put(Byte.TYPE, Byte.class);
      tmpMap.put(Void.TYPE, Void.TYPE);
      PRIMITIVE_TO_WRAPPER_TYPES = Collections.unmodifiableMap(tmpMap);
      tmpMap = CollectionHelper.newHashMap(9);
      tmpMap.put(Boolean.class, Boolean.TYPE);
      tmpMap.put(Character.class, Character.TYPE);
      tmpMap.put(Double.class, Double.TYPE);
      tmpMap.put(Float.class, Float.TYPE);
      tmpMap.put(Long.class, Long.TYPE);
      tmpMap.put(Integer.class, Integer.TYPE);
      tmpMap.put(Short.class, Short.TYPE);
      tmpMap.put(Byte.class, Byte.TYPE);
      tmpMap.put(Void.TYPE, Void.TYPE);
      WRAPPER_TO_PRIMITIVE_TYPES = Collections.unmodifiableMap(tmpMap);
   }
}
