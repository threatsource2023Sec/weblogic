package org.jboss.classfilewriter.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public final class Signatures {
   static final char WILDCARD_UPPER_BOUND = '+';
   static final char WILDCARD_LOWER_BOUND = '-';
   static final char WILDCARD_NO_BOUND = '*';
   static final char TYPE_PARAM_DEL_START = '<';
   static final char TYPE_PARAM_DEL_END = '>';
   static final char SEMICOLON = ';';
   static final char COLON = ':';

   private Signatures() {
   }

   public static String methodSignature(Method method) {
      StringBuilder builder = new StringBuilder();
      TypeVariable[] typeParams = method.getTypeParameters();
      int var5;
      if (typeParams.length > 0) {
         builder.append('<');
         TypeVariable[] var3 = typeParams;
         int var4 = typeParams.length;

         for(var5 = 0; var5 < var4; ++var5) {
            TypeVariable typeParam = var3[var5];
            typeParameter(typeParam, builder);
         }

         builder.append('>');
      }

      Type[] params = method.getGenericParameterTypes();
      builder.append('(');
      Type[] exceptions;
      int var12;
      if (params.length > 0) {
         exceptions = params;
         var5 = params.length;

         for(var12 = 0; var12 < var5; ++var12) {
            Type paramType = exceptions[var12];
            javaType(paramType, builder);
         }
      }

      builder.append(')');
      javaType(method.getGenericReturnType(), builder);
      exceptions = method.getGenericExceptionTypes();
      if (exceptions.length > 0) {
         Type[] var11 = exceptions;
         var12 = exceptions.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            Type exceptionType = var11[var13];
            builder.append('^');
            javaType(exceptionType, builder);
         }
      }

      return builder.toString();
   }

   private static void typeParameter(TypeVariable typeVariable, StringBuilder builder) {
      builder.append(typeVariable.getName());
      Type[] bounds = typeVariable.getBounds();
      if (bounds.length > 0) {
         for(int i = 0; i < bounds.length; ++i) {
            if (i == 0 && getTypeParamBoundRawType(bounds[i]).isInterface()) {
               builder.append(':');
            }

            builder.append(':');
            javaType(bounds[i], builder);
         }
      } else {
         builder.append(':');
         javaType(Object.class, builder);
      }

   }

   private static void javaType(Type type, StringBuilder builder) {
      if (type instanceof Class) {
         nonGenericType((Class)type, builder);
      } else if (type instanceof ParameterizedType) {
         parameterizedType((ParameterizedType)type, builder);
      } else if (type instanceof GenericArrayType) {
         GenericArrayType genericArrayType = (GenericArrayType)type;
         builder.append('[');
         javaType(genericArrayType.getGenericComponentType(), builder);
      } else if (type instanceof WildcardType) {
         wildcardType((WildcardType)type, builder);
      } else {
         if (!(type instanceof TypeVariable)) {
            throw new IllegalArgumentException("Signature encoding error - unsupported type: " + type);
         }

         typeVariable((TypeVariable)type, builder);
      }

   }

   private static void wildcardType(WildcardType wildcard, StringBuilder builder) {
      Type[] var2;
      int var3;
      int var4;
      Type upperBound;
      if (wildcard.getLowerBounds().length > 0) {
         var2 = wildcard.getLowerBounds();
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            upperBound = var2[var4];
            builder.append('-');
            javaType(upperBound, builder);
         }
      } else if (wildcard.getUpperBounds().length == 0 || wildcard.getUpperBounds().length == 1 && Object.class.equals(wildcard.getUpperBounds()[0])) {
         builder.append('*');
      } else {
         var2 = wildcard.getUpperBounds();
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            upperBound = var2[var4];
            builder.append('+');
            javaType(upperBound, builder);
         }
      }

   }

   private static void typeVariable(TypeVariable typeVariable, StringBuilder builder) {
      builder.append('T');
      builder.append(typeVariable.getName());
      builder.append(';');
   }

   private static void parameterizedType(ParameterizedType parameterizedType, StringBuilder builder) {
      Type rawType = parameterizedType.getRawType();
      if (!(rawType instanceof Class)) {
         throw new IllegalStateException(String.format("Signature encoding error - unsupported raw type: %s of parameterized type: %s", parameterizedType, rawType));
      } else {
         builder.append(classTypeBase(((Class)rawType).getName()));
         builder.append('<');
         Type[] var3 = parameterizedType.getActualTypeArguments();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Type actualTypeArgument = var3[var5];
            javaType(actualTypeArgument, builder);
         }

         builder.append('>');
         builder.append(';');
      }
   }

   private static void nonGenericType(Class clazz, StringBuilder builder) {
      if (Void.TYPE.equals(clazz)) {
         builder.append("V");
      } else if (Byte.TYPE.equals(clazz)) {
         builder.append("B");
      } else if (Character.TYPE.equals(clazz)) {
         builder.append("C");
      } else if (Double.TYPE.equals(clazz)) {
         builder.append("D");
      } else if (Float.TYPE.equals(clazz)) {
         builder.append("F");
      } else if (Integer.TYPE.equals(clazz)) {
         builder.append("I");
      } else if (Long.TYPE.equals(clazz)) {
         builder.append("J");
      } else if (Short.TYPE.equals(clazz)) {
         builder.append("S");
      } else if (Boolean.TYPE.equals(clazz)) {
         builder.append("Z");
      } else if (clazz.isArray()) {
         builder.append(encodeClassName(clazz.getName()));
      } else {
         builder.append(classTypeBase(clazz.getName()) + ';');
      }

   }

   private static String classTypeBase(String className) {
      return 'L' + encodeClassName(className);
   }

   private static String encodeClassName(String className) {
      return className.replace('.', '/');
   }

   private static Class getTypeParamBoundRawType(Type type) {
      if (type instanceof Class) {
         return (Class)type;
      } else if (type instanceof ParameterizedType && ((ParameterizedType)type).getRawType() instanceof Class) {
         return (Class)((ParameterizedType)type).getRawType();
      } else if (type instanceof TypeVariable) {
         TypeVariable variable = (TypeVariable)type;
         Type[] bounds = variable.getBounds();
         return getBound(bounds);
      } else {
         throw new IllegalStateException("Signature encoding error - unexpected type parameter bound type: " + type);
      }
   }

   private static Class getBound(Type[] bounds) {
      return bounds.length == 0 ? Object.class : getTypeParamBoundRawType(bounds[0]);
   }
}
