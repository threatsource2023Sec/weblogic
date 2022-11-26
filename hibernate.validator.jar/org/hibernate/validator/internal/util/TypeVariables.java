package org.hibernate.validator.internal.util;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.hibernate.validator.internal.engine.valueextraction.AnnotatedObject;
import org.hibernate.validator.internal.engine.valueextraction.ArrayElement;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class TypeVariables {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   private TypeVariables() {
   }

   public static Class getContainerClass(TypeVariable typeParameter) {
      if (isAnnotatedObject(typeParameter)) {
         return null;
      } else {
         return isArrayElement(typeParameter) ? ((ArrayElement)typeParameter).getContainerClass() : getDeclaringClass(typeParameter);
      }
   }

   public static TypeVariable getActualTypeParameter(TypeVariable typeParameter) {
      return isInternal(typeParameter) ? null : typeParameter;
   }

   public static boolean isInternal(TypeVariable typeParameter) {
      return isAnnotatedObject(typeParameter) || isArrayElement(typeParameter);
   }

   public static boolean isAnnotatedObject(TypeVariable typeParameter) {
      return typeParameter == AnnotatedObject.INSTANCE;
   }

   public static boolean isArrayElement(TypeVariable typeParameter) {
      return typeParameter instanceof ArrayElement;
   }

   public static String getTypeParameterName(Class clazz, int typeParameterIndex) {
      if (typeParameterIndex >= clazz.getTypeParameters().length) {
         throw LOG.getUnableToFindTypeParameterInClass(clazz, typeParameterIndex);
      } else {
         return clazz.getTypeParameters()[typeParameterIndex].getName();
      }
   }

   public static Integer getTypeParameterIndex(TypeVariable typeParameter) {
      if (typeParameter != null && !isArrayElement(typeParameter)) {
         TypeVariable[] typeParameters = typeParameter.getGenericDeclaration().getTypeParameters();

         for(int i = 0; i < typeParameters.length; ++i) {
            if (typeParameter.getName().equals(typeParameters[i].getName())) {
               return i;
            }
         }

         throw LOG.getUnableToFindTypeParameterInClass((Class)typeParameter.getGenericDeclaration(), typeParameter.getName());
      } else {
         return null;
      }
   }

   public static Type getContainerElementType(Type type, TypeVariable typeParameter) {
      if (type instanceof ParameterizedType) {
         Type[] typeArguments = ((ParameterizedType)type).getActualTypeArguments();
         return typeArguments[getTypeParameterIndex(typeParameter)];
      } else {
         return type instanceof GenericArrayType ? ((GenericArrayType)type).getGenericComponentType() : null;
      }
   }

   private static Class getDeclaringClass(TypeVariable typeParameter) {
      return (Class)typeParameter.getGenericDeclaration();
   }
}
