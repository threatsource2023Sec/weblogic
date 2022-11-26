package org.jboss.weld.resolution;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Iterator;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.jboss.weld.util.reflection.Reflections;

public class CovariantTypes {
   private CovariantTypes() {
   }

   public static boolean isAssignableFromAtLeastOne(Type type1, Type[] types2) {
      Type[] var2 = types2;
      int var3 = types2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type type2 = var2[var4];
         if (isAssignableFrom(type1, type2)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isAssignableFrom(Type type1, Type type2) {
      if (type1 instanceof Class) {
         if (type2 instanceof Class) {
            return isAssignableFrom((Class)type1, (Class)type2);
         } else if (type2 instanceof ParameterizedType) {
            return isAssignableFrom((Class)type1, (ParameterizedType)type2);
         } else if (type2 instanceof TypeVariable) {
            return isAssignableFrom((Class)type1, (TypeVariable)type2);
         } else if (type2 instanceof WildcardType) {
            return isAssignableFrom((Class)type1, (WildcardType)type2);
         } else if (type2 instanceof GenericArrayType) {
            return isAssignableFrom((Class)type1, (GenericArrayType)type2);
         } else {
            throw ReflectionLogger.LOG.unknownType(type2);
         }
      } else if (type1 instanceof ParameterizedType) {
         if (type2 instanceof Class) {
            return isAssignableFrom((ParameterizedType)type1, (Class)type2);
         } else if (type2 instanceof ParameterizedType) {
            return isAssignableFrom((ParameterizedType)type1, (ParameterizedType)type2);
         } else if (type2 instanceof TypeVariable) {
            return isAssignableFrom((ParameterizedType)type1, (TypeVariable)type2);
         } else if (type2 instanceof WildcardType) {
            return isAssignableFrom((ParameterizedType)type1, (WildcardType)type2);
         } else if (type2 instanceof GenericArrayType) {
            return isAssignableFrom((ParameterizedType)type1, (GenericArrayType)type2);
         } else {
            throw ReflectionLogger.LOG.unknownType(type2);
         }
      } else if (type1 instanceof TypeVariable) {
         if (type2 instanceof Class) {
            return isAssignableFrom((TypeVariable)type1, (Class)type2);
         } else if (type2 instanceof ParameterizedType) {
            return isAssignableFrom((TypeVariable)type1, (ParameterizedType)type2);
         } else if (type2 instanceof TypeVariable) {
            return isAssignableFrom((TypeVariable)type1, (TypeVariable)type2);
         } else if (type2 instanceof WildcardType) {
            return isAssignableFrom((TypeVariable)type1, (WildcardType)type2);
         } else if (type2 instanceof GenericArrayType) {
            return isAssignableFrom((TypeVariable)type1, (GenericArrayType)type2);
         } else {
            throw ReflectionLogger.LOG.unknownType(type2);
         }
      } else if (type1 instanceof WildcardType) {
         if (Types.isActualType(type2)) {
            return isAssignableFrom((WildcardType)type1, type2);
         } else if (type2 instanceof TypeVariable) {
            return isAssignableFrom((WildcardType)type1, (TypeVariable)type2);
         } else if (type2 instanceof WildcardType) {
            return isAssignableFrom((WildcardType)type1, (WildcardType)type2);
         } else {
            throw ReflectionLogger.LOG.unknownType(type2);
         }
      } else if (type1 instanceof GenericArrayType) {
         if (type2 instanceof Class) {
            return isAssignableFrom((GenericArrayType)type1, (Class)type2);
         } else if (type2 instanceof ParameterizedType) {
            return isAssignableFrom((GenericArrayType)type1, (ParameterizedType)type2);
         } else if (type2 instanceof TypeVariable) {
            return isAssignableFrom((GenericArrayType)type1, (TypeVariable)type2);
         } else if (type2 instanceof WildcardType) {
            return isAssignableFrom((GenericArrayType)type1, (WildcardType)type2);
         } else if (type2 instanceof GenericArrayType) {
            return isAssignableFrom((GenericArrayType)type1, (GenericArrayType)type2);
         } else {
            throw ReflectionLogger.LOG.unknownType(type2);
         }
      } else {
         throw ReflectionLogger.LOG.unknownType(type1);
      }
   }

   private static boolean isAssignableFrom(Class type1, Class type2) {
      return Types.boxedClass(type1).isAssignableFrom(Types.boxedClass(type2));
   }

   private static boolean isAssignableFrom(Class type1, ParameterizedType type2) {
      return type1.isAssignableFrom(Reflections.getRawType(type2));
   }

   private static boolean isAssignableFrom(Class type1, TypeVariable type2) {
      Type[] var2 = type2.getBounds();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type type = var2[var4];
         if (isAssignableFrom((Type)type1, (Type)type)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isAssignableFrom(Class type1, WildcardType type2) {
      return false;
   }

   private static boolean isAssignableFrom(Class type1, GenericArrayType type2) {
      return type1.equals(Object.class) || type1.isArray() && isAssignableFrom(type1.getComponentType(), Reflections.getRawType(type2.getGenericComponentType()));
   }

   private static boolean isAssignableFrom(ParameterizedType type1, Class type2) {
      Class rawType1 = Reflections.getRawType(type1);
      if (!isAssignableFrom(rawType1, type2)) {
         return false;
      } else {
         return !Types.getCanonicalType(type2).equals(type2) ? true : matches(type1, new HierarchyDiscovery(type2));
      }
   }

   private static boolean isAssignableFrom(ParameterizedType type1, ParameterizedType type2) {
      if (!isAssignableFrom(Reflections.getRawType(type1), Reflections.getRawType(type2))) {
         return false;
      } else {
         return matches(type1, type2) ? true : matches(type1, new HierarchyDiscovery(type2));
      }
   }

   private static boolean matches(ParameterizedType type1, HierarchyDiscovery type2) {
      Iterator var2 = type2.getTypeClosure().iterator();

      Type type;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         type = (Type)var2.next();
      } while(!(type instanceof ParameterizedType) || !matches(type1, (ParameterizedType)type));

      return true;
   }

   private static boolean matches(ParameterizedType type1, ParameterizedType type2) {
      Class rawType1 = Reflections.getRawType(type1);
      Class rawType2 = Reflections.getRawType(type2);
      if (!rawType1.equals(rawType2)) {
         return false;
      } else {
         Type[] types1 = type1.getActualTypeArguments();
         Type[] types2 = type2.getActualTypeArguments();
         if (types1.length != types2.length) {
            throw ReflectionLogger.LOG.invalidTypeArgumentCombination(type1, type2);
         } else {
            for(int i = 0; i < type1.getActualTypeArguments().length; ++i) {
               if (!InvariantTypes.isAssignableFrom(types1[i], types2[i])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean isAssignableFrom(ParameterizedType type1, TypeVariable type2) {
      Type[] var2 = type2.getBounds();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type type = var2[var4];
         if (isAssignableFrom((Type)type1, (Type)type)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isAssignableFrom(ParameterizedType type1, WildcardType type2) {
      return false;
   }

   private static boolean isAssignableFrom(ParameterizedType type1, GenericArrayType type2) {
      return false;
   }

   private static boolean isAssignableFrom(TypeVariable type1, Class type2) {
      return false;
   }

   private static boolean isAssignableFrom(TypeVariable type1, ParameterizedType type2) {
      return false;
   }

   private static boolean isAssignableFrom(TypeVariable type1, TypeVariable type2) {
      if (type1.equals(type2)) {
         return true;
      } else {
         return type2.getBounds()[0] instanceof TypeVariable ? isAssignableFrom(type1, (TypeVariable)type2.getBounds()[0]) : false;
      }
   }

   private static boolean isAssignableFrom(TypeVariable type1, WildcardType type2) {
      return false;
   }

   private static boolean isAssignableFrom(TypeVariable type1, GenericArrayType type2) {
      return false;
   }

   private static boolean isAssignableFrom(WildcardType type1, Type type2) {
      if (!isAssignableFrom(type1.getUpperBounds()[0], type2)) {
         return false;
      } else {
         return type1.getLowerBounds().length <= 0 || isAssignableFrom(type2, type1.getLowerBounds()[0]);
      }
   }

   private static boolean isAssignableFrom(WildcardType type1, TypeVariable type2) {
      return type1.getLowerBounds().length > 0 ? isAssignableFrom((Type)type2, (Type)type1.getLowerBounds()[0]) : isAssignableFrom((Type)type1.getUpperBounds()[0], (Type)type2);
   }

   private static boolean isAssignableFrom(WildcardType type1, WildcardType type2) {
      if (!isAssignableFrom(type1.getUpperBounds()[0], type2.getUpperBounds()[0])) {
         return false;
      } else if (type1.getLowerBounds().length > 0) {
         return type2.getLowerBounds().length > 0 ? isAssignableFrom(type2.getLowerBounds()[0], type1.getLowerBounds()[0]) : false;
      } else {
         return type2.getLowerBounds().length > 0 ? type1.getUpperBounds()[0].equals(Object.class) : true;
      }
   }

   private static boolean isAssignableFrom(GenericArrayType type1, Class type2) {
      return type2.isArray() && isAssignableFrom(Reflections.getRawType(type1.getGenericComponentType()), type2.getComponentType());
   }

   private static boolean isAssignableFrom(GenericArrayType type1, ParameterizedType type2) {
      return false;
   }

   private static boolean isAssignableFrom(GenericArrayType type1, TypeVariable type2) {
      return false;
   }

   private static boolean isAssignableFrom(GenericArrayType type1, WildcardType type2) {
      return false;
   }

   private static boolean isAssignableFrom(GenericArrayType type1, GenericArrayType type2) {
      return isAssignableFrom(type1.getGenericComponentType(), type2.getGenericComponentType());
   }
}
