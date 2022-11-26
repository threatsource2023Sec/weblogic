package org.hibernate.validator.internal.util;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class TypeHelper {
   private static final Map SUBTYPES_BY_PRIMITIVE;
   private static final int CONSTRAINT_TYPE_INDEX = 0;
   private static final int VALIDATOR_TYPE_INDEX = 1;
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   private TypeHelper() {
      throw new AssertionError();
   }

   public static boolean isAssignable(Type supertype, Type type) {
      Contracts.assertNotNull(supertype, "supertype");
      Contracts.assertNotNull(type, "type");
      if (supertype.equals(type)) {
         return true;
      } else if (supertype instanceof Class) {
         if (type instanceof Class) {
            return isClassAssignable((Class)supertype, (Class)type);
         } else if (type instanceof ParameterizedType) {
            return isAssignable(supertype, ((ParameterizedType)type).getRawType());
         } else if (type instanceof TypeVariable) {
            return isTypeVariableAssignable(supertype, (TypeVariable)type);
         } else if (type instanceof GenericArrayType) {
            return ((Class)supertype).isArray() ? isAssignable(getComponentType(supertype), getComponentType(type)) : isArraySupertype((Class)supertype);
         } else {
            return type instanceof WildcardType ? isClassAssignableToWildcardType((Class)supertype, (WildcardType)type) : false;
         }
      } else if (supertype instanceof ParameterizedType) {
         if (type instanceof Class) {
            return isSuperAssignable(supertype, type);
         } else {
            return type instanceof ParameterizedType ? isParameterizedTypeAssignable((ParameterizedType)supertype, (ParameterizedType)type) : false;
         }
      } else if (type instanceof TypeVariable) {
         return isTypeVariableAssignable(supertype, (TypeVariable)type);
      } else if (supertype instanceof GenericArrayType) {
         return isArray(type) ? isAssignable(getComponentType(supertype), getComponentType(type)) : false;
      } else {
         return supertype instanceof WildcardType ? isWildcardTypeAssignable((WildcardType)supertype, type) : false;
      }
   }

   public static Type getErasedType(Type type) {
      Type componentType;
      if (type instanceof ParameterizedType) {
         componentType = ((ParameterizedType)type).getRawType();
         return getErasedType(componentType);
      } else if (isArray(type)) {
         componentType = getComponentType(type);
         Type erasedComponentType = getErasedType(componentType);
         return getArrayType(erasedComponentType);
      } else {
         Type[] upperBounds;
         if (type instanceof TypeVariable) {
            upperBounds = ((TypeVariable)type).getBounds();
            return getErasedType(upperBounds[0]);
         } else if (type instanceof WildcardType) {
            upperBounds = ((WildcardType)type).getUpperBounds();
            return getErasedType(upperBounds[0]);
         } else {
            return type;
         }
      }
   }

   public static Class getErasedReferenceType(Type type) {
      Contracts.assertTrue(isReferenceType(type), "type is not a reference type: %s", type);
      return (Class)getErasedType(type);
   }

   public static boolean isArray(Type type) {
      return type instanceof Class && ((Class)type).isArray() || type instanceof GenericArrayType;
   }

   public static Type getComponentType(Type type) {
      if (type instanceof Class) {
         Class klass = (Class)type;
         return klass.isArray() ? klass.getComponentType() : null;
      } else {
         return type instanceof GenericArrayType ? ((GenericArrayType)type).getGenericComponentType() : null;
      }
   }

   private static Type getArrayType(Type componentType) {
      Contracts.assertNotNull(componentType, "componentType");
      return (Type)(componentType instanceof Class ? Array.newInstance((Class)componentType, 0).getClass() : genericArrayType(componentType));
   }

   public static GenericArrayType genericArrayType(final Type componentType) {
      return new GenericArrayType() {
         public Type getGenericComponentType() {
            return componentType;
         }
      };
   }

   public static boolean isInstance(Type type, Object object) {
      return getErasedReferenceType(type).isInstance(object);
   }

   public static ParameterizedType parameterizedType(final Class rawType, final Type... actualTypeArguments) {
      return new ParameterizedType() {
         public Type[] getActualTypeArguments() {
            return actualTypeArguments;
         }

         public Type getRawType() {
            return rawType;
         }

         public Type getOwnerType() {
            return null;
         }
      };
   }

   private static Type getResolvedSuperclass(Type type) {
      Contracts.assertNotNull(type, "type");
      Class rawType = getErasedReferenceType(type);
      Type supertype = rawType.getGenericSuperclass();
      return supertype == null ? null : resolveTypeVariables(supertype, type);
   }

   private static Type[] getResolvedInterfaces(Type type) {
      Contracts.assertNotNull(type, "type");
      Class rawType = getErasedReferenceType(type);
      Type[] interfaces = rawType.getGenericInterfaces();
      Type[] resolvedInterfaces = new Type[interfaces.length];

      for(int i = 0; i < interfaces.length; ++i) {
         resolvedInterfaces[i] = resolveTypeVariables(interfaces[i], type);
      }

      return resolvedInterfaces;
   }

   public static Map getValidatorTypes(Class annotationType, List validators) {
      Map validatorsTypes = CollectionHelper.newHashMap();
      Iterator var3 = validators.iterator();

      ConstraintValidatorDescriptor validator;
      Type type;
      ConstraintValidatorDescriptor previous;
      do {
         if (!var3.hasNext()) {
            return validatorsTypes;
         }

         validator = (ConstraintValidatorDescriptor)var3.next();
         type = validator.getValidatedType();
         previous = (ConstraintValidatorDescriptor)validatorsTypes.put(type, validator);
      } while(previous == null);

      throw LOG.getMultipleValidatorsForSameTypeException(annotationType, type, previous.getValidatorClass(), validator.getValidatorClass());
   }

   public static Type extractValidatedType(Class validator) {
      return extractConstraintValidatorTypeArgumentType(validator, 1);
   }

   public static Type extractConstraintType(Class validator) {
      return extractConstraintValidatorTypeArgumentType(validator, 0);
   }

   public static Type extractConstraintValidatorTypeArgumentType(Class validator, int typeArgumentIndex) {
      Map resolvedTypes = new HashMap();
      Type constraintValidatorType = resolveTypes(resolvedTypes, validator);
      Type type = ((ParameterizedType)constraintValidatorType).getActualTypeArguments()[typeArgumentIndex];
      if (type == null) {
         throw LOG.getNullIsAnInvalidTypeForAConstraintValidatorException();
      } else {
         if (type instanceof GenericArrayType) {
            type = getArrayType(getComponentType(type));
         }

         while(resolvedTypes.containsKey(type)) {
            type = (Type)resolvedTypes.get(type);
         }

         return type;
      }
   }

   public static boolean isUnboundWildcard(Type type) {
      if (!(type instanceof WildcardType)) {
         return false;
      } else {
         WildcardType wildcardType = (WildcardType)type;
         return isEmptyBounds(wildcardType.getUpperBounds()) && isEmptyBounds(wildcardType.getLowerBounds());
      }
   }

   private static Type resolveTypes(Map resolvedTypes, Type type) {
      if (type == null) {
         return null;
      } else {
         if (type instanceof Class) {
            Class clazz = (Class)type;
            Type returnedType = resolveTypeForClassAndHierarchy(resolvedTypes, clazz);
            if (returnedType != null) {
               return returnedType;
            }
         } else if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType)type;
            if (!(paramType.getRawType() instanceof Class)) {
               return null;
            }

            Class rawType = (Class)paramType.getRawType();
            TypeVariable[] originalTypes = rawType.getTypeParameters();
            Type[] partiallyResolvedTypes = paramType.getActualTypeArguments();
            int nbrOfParams = originalTypes.length;

            for(int i = 0; i < nbrOfParams; ++i) {
               resolvedTypes.put(originalTypes[i], partiallyResolvedTypes[i]);
            }

            if (rawType.equals(ConstraintValidator.class)) {
               return type;
            }

            Type returnedType = resolveTypeForClassAndHierarchy(resolvedTypes, rawType);
            if (returnedType != null) {
               return returnedType;
            }
         }

         return null;
      }
   }

   private static Type resolveTypeForClassAndHierarchy(Map resolvedTypes, Class clazz) {
      Type returnedType = resolveTypes(resolvedTypes, clazz.getGenericSuperclass());
      if (returnedType != null) {
         return returnedType;
      } else {
         Type[] var3 = clazz.getGenericInterfaces();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Type genericInterface = var3[var5];
            returnedType = resolveTypes(resolvedTypes, genericInterface);
            if (returnedType != null) {
               return returnedType;
            }
         }

         return null;
      }
   }

   private static void putPrimitiveSubtypes(Map subtypesByPrimitive, Class primitiveType, Class... directSubtypes) {
      Set subtypes = CollectionHelper.newHashSet();
      Class[] var4 = directSubtypes;
      int var5 = directSubtypes.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class directSubtype = var4[var6];
         subtypes.add(directSubtype);
         subtypes.addAll((Collection)subtypesByPrimitive.get(directSubtype));
      }

      subtypesByPrimitive.put(primitiveType, Collections.unmodifiableSet(subtypes));
   }

   private static boolean isClassAssignable(Class supertype, Class type) {
      return supertype.isPrimitive() && type.isPrimitive() ? ((Set)SUBTYPES_BY_PRIMITIVE.get(supertype)).contains(type) : supertype.isAssignableFrom(type);
   }

   private static boolean isClassAssignableToWildcardType(Class supertype, WildcardType type) {
      Type[] var2 = type.getUpperBounds();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type upperBound = var2[var4];
         if (!isAssignable(supertype, upperBound)) {
            return false;
         }
      }

      return true;
   }

   private static boolean isParameterizedTypeAssignable(ParameterizedType supertype, ParameterizedType type) {
      Type rawSupertype = supertype.getRawType();
      Type rawType = type.getRawType();
      if (!rawSupertype.equals(rawType)) {
         return rawSupertype instanceof Class && rawType instanceof Class && !((Class)rawSupertype).isAssignableFrom((Class)rawType) ? false : isSuperAssignable(supertype, type);
      } else {
         Type[] supertypeArgs = supertype.getActualTypeArguments();
         Type[] typeArgs = type.getActualTypeArguments();
         if (supertypeArgs.length != typeArgs.length) {
            return false;
         } else {
            for(int i = 0; i < supertypeArgs.length; ++i) {
               Type supertypeArg = supertypeArgs[i];
               Type typeArg = typeArgs[i];
               if (supertypeArg instanceof WildcardType) {
                  if (!isWildcardTypeAssignable((WildcardType)supertypeArg, typeArg)) {
                     return false;
                  }
               } else if (!supertypeArg.equals(typeArg)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean isTypeVariableAssignable(Type supertype, TypeVariable type) {
      Type[] var2 = type.getBounds();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type bound = var2[var4];
         if (isAssignable(supertype, bound)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isWildcardTypeAssignable(WildcardType supertype, Type type) {
      Type[] var2 = supertype.getUpperBounds();
      int var3 = var2.length;

      int var4;
      Type lowerBound;
      for(var4 = 0; var4 < var3; ++var4) {
         lowerBound = var2[var4];
         if (!isAssignable(lowerBound, type)) {
            return false;
         }
      }

      var2 = supertype.getLowerBounds();
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         lowerBound = var2[var4];
         if (!isAssignable(type, lowerBound)) {
            return false;
         }
      }

      return true;
   }

   private static boolean isSuperAssignable(Type supertype, Type type) {
      Type superclass = getResolvedSuperclass(type);
      if (superclass != null && isAssignable(supertype, superclass)) {
         return true;
      } else {
         Type[] var3 = getResolvedInterfaces(type);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Type interphace = var3[var5];
            if (isAssignable(supertype, interphace)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isReferenceType(Type type) {
      return type == null || type instanceof Class || type instanceof ParameterizedType || type instanceof TypeVariable || type instanceof GenericArrayType;
   }

   private static boolean isArraySupertype(Class type) {
      return Object.class.equals(type) || Cloneable.class.equals(type) || Serializable.class.equals(type);
   }

   private static Type resolveTypeVariables(Type type, Type subtype) {
      if (!(type instanceof ParameterizedType)) {
         return type;
      } else {
         Map actualTypeArgumentsByParameter = getActualTypeArgumentsByParameter(type, subtype);
         Class rawType = getErasedReferenceType(type);
         return parameterizeClass(rawType, actualTypeArgumentsByParameter);
      }
   }

   private static Map getActualTypeArgumentsByParameter(Type... types) {
      Map actualTypeArgumentsByParameter = new LinkedHashMap();
      Type[] var2 = types;
      int var3 = types.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type type = var2[var4];
         actualTypeArgumentsByParameter.putAll(getActualTypeArgumentsByParameterInternal(type));
      }

      return normalize(actualTypeArgumentsByParameter);
   }

   private static Map getActualTypeArgumentsByParameterInternal(Type type) {
      if (!(type instanceof ParameterizedType)) {
         return Collections.emptyMap();
      } else {
         TypeVariable[] typeParameters = getErasedReferenceType(type).getTypeParameters();
         Type[] typeArguments = ((ParameterizedType)type).getActualTypeArguments();
         if (typeParameters.length != typeArguments.length) {
            throw new MalformedParameterizedTypeException();
         } else {
            Map actualTypeArgumentsByParameter = new LinkedHashMap();

            for(int i = 0; i < typeParameters.length; ++i) {
               if (!typeParameters[i].equals(typeArguments[i])) {
                  actualTypeArgumentsByParameter.put(typeParameters[i], typeArguments[i]);
               }
            }

            return actualTypeArgumentsByParameter;
         }
      }
   }

   private static ParameterizedType parameterizeClass(Class type, Map actualTypeArgumentsByParameter) {
      return parameterizeClassCapture(type, actualTypeArgumentsByParameter);
   }

   private static ParameterizedType parameterizeClassCapture(Class type, Map actualTypeArgumentsByParameter) {
      TypeVariable[] typeParameters = type.getTypeParameters();
      Type[] actualTypeArguments = new Type[typeParameters.length];

      for(int i = 0; i < typeParameters.length; ++i) {
         TypeVariable typeParameter = typeParameters[i];
         Type actualTypeArgument = (Type)actualTypeArgumentsByParameter.get(typeParameter);
         if (actualTypeArgument == null) {
            throw LOG.getMissingActualTypeArgumentForTypeParameterException(typeParameter);
         }

         actualTypeArguments[i] = actualTypeArgument;
      }

      return parameterizedType(getErasedReferenceType(type), actualTypeArguments);
   }

   private static Map normalize(Map map) {
      Iterator var1 = map.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         Object key = entry.getKey();

         Object value;
         for(value = entry.getValue(); map.containsKey(value); value = map.get(value)) {
         }

         map.put(key, value);
      }

      return map;
   }

   private static boolean isEmptyBounds(Type[] bounds) {
      return bounds == null || bounds.length == 0 || bounds.length == 1 && Object.class.equals(bounds[0]);
   }

   static {
      Map subtypesByPrimitive = CollectionHelper.newHashMap();
      putPrimitiveSubtypes(subtypesByPrimitive, Void.TYPE);
      putPrimitiveSubtypes(subtypesByPrimitive, Boolean.TYPE);
      putPrimitiveSubtypes(subtypesByPrimitive, Byte.TYPE);
      putPrimitiveSubtypes(subtypesByPrimitive, Character.TYPE);
      putPrimitiveSubtypes(subtypesByPrimitive, Short.TYPE, Byte.TYPE);
      putPrimitiveSubtypes(subtypesByPrimitive, Integer.TYPE, Character.TYPE, Short.TYPE);
      putPrimitiveSubtypes(subtypesByPrimitive, Long.TYPE, Integer.TYPE);
      putPrimitiveSubtypes(subtypesByPrimitive, Float.TYPE, Long.TYPE);
      putPrimitiveSubtypes(subtypesByPrimitive, Double.TYPE, Float.TYPE);
      SUBTYPES_BY_PRIMITIVE = Collections.unmodifiableMap(subtypesByPrimitive);
   }
}
