package org.jboss.weld.resolution;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.reflection.Reflections;

public class InvariantTypes {
   private InvariantTypes() {
   }

   public static boolean isAssignableFrom(Type type1, Type type2) {
      if (!(type1 instanceof WildcardType) && !(type2 instanceof WildcardType)) {
         if (type1 instanceof Class) {
            if (type2 instanceof Class) {
               return isAssignableFrom((Class)type1, (Class)type2);
            } else if (type2 instanceof ParameterizedType) {
               return isAssignableFrom((Class)type1, (ParameterizedType)type2);
            } else if (type2 instanceof TypeVariable) {
               return isAssignableFrom((Class)type1, (TypeVariable)type2);
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
            } else if (type2 instanceof GenericArrayType) {
               return isAssignableFrom((TypeVariable)type1, (GenericArrayType)type2);
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
            } else if (type2 instanceof GenericArrayType) {
               return isAssignableFrom((GenericArrayType)type1, (GenericArrayType)type2);
            } else {
               throw ReflectionLogger.LOG.unknownType(type2);
            }
         } else {
            throw ReflectionLogger.LOG.unknownType(type1);
         }
      } else {
         return CovariantTypes.isAssignableFrom(type1, type2);
      }
   }

   private static boolean isAssignableFrom(Class type1, Class type2) {
      return Types.boxedClass(type1).equals(Types.boxedClass(type2));
   }

   private static boolean isAssignableFrom(Class type1, ParameterizedType type2) {
      return false;
   }

   private static boolean isAssignableFrom(Class type1, TypeVariable type2) {
      return false;
   }

   private static boolean isAssignableFrom(Class type1, GenericArrayType type2) {
      return false;
   }

   private static boolean isAssignableFrom(ParameterizedType type1, Class type2) {
      return false;
   }

   private static boolean isAssignableFrom(ParameterizedType type1, ParameterizedType type2) {
      if (!Reflections.getRawType(type1).equals(Reflections.getRawType(type2))) {
         return false;
      } else {
         Type[] types1 = type1.getActualTypeArguments();
         Type[] types2 = type2.getActualTypeArguments();
         if (types1.length != types2.length) {
            throw ReflectionLogger.LOG.invalidTypeArgumentCombination(type1, type2);
         } else {
            for(int i = 0; i < types1.length; ++i) {
               if (!isAssignableFrom(types1[i], types2[i])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean isAssignableFrom(ParameterizedType type1, TypeVariable type2) {
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
      return type1.equals(type2);
   }

   private static boolean isAssignableFrom(TypeVariable type1, GenericArrayType type2) {
      return false;
   }

   private static boolean isAssignableFrom(GenericArrayType type1, Class type2) {
      return false;
   }

   private static boolean isAssignableFrom(GenericArrayType type1, ParameterizedType type2) {
      return false;
   }

   private static boolean isAssignableFrom(GenericArrayType type1, TypeVariable type2) {
      return false;
   }

   private static boolean isAssignableFrom(GenericArrayType type1, GenericArrayType type2) {
      return isAssignableFrom(type1.getGenericComponentType(), type2.getGenericComponentType());
   }
}
