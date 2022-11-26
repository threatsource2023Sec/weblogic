package org.jboss.weld.resolution;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jboss.weld.util.reflection.Reflections;

public class TypeEqualitySpecializationUtils {
   private TypeEqualitySpecializationUtils() {
   }

   public static boolean areTheSame(Type type1, Type type2) {
      if (type1.equals(type2)) {
         return true;
      } else if (type1 instanceof ParameterizedType && type2 instanceof ParameterizedType) {
         return areTheSame((ParameterizedType)type1, (ParameterizedType)type2);
      } else {
         return type1 instanceof TypeVariable && type2 instanceof TypeVariable ? areTheSame((TypeVariable)type1, (TypeVariable)type2) : false;
      }
   }

   protected static boolean areTheSame(ParameterizedType type1, ParameterizedType type2) {
      return !type1.getRawType().equals(type2.getRawType()) ? false : areTheSame(type1.getActualTypeArguments(), type2.getActualTypeArguments());
   }

   protected static boolean areTheSame(TypeVariable type1, TypeVariable type2) {
      List bounds1 = removeRedundantBounds(type1.getBounds());
      List bounds2 = removeRedundantBounds(type2.getBounds());
      if (bounds1.size() != bounds2.size()) {
         return false;
      } else {
         Iterator var4 = bounds1.iterator();

         Type type;
         do {
            if (!var4.hasNext()) {
               return true;
            }

            type = (Type)var4.next();
         } while(isTheSameAsSomeOf(type, bounds2));

         return false;
      }
   }

   private static boolean isTheSameAsSomeOf(Type bound1, List bounds2) {
      Iterator var2 = bounds2.iterator();

      Type type;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         type = (Type)var2.next();
      } while(!areTheSame(bound1, type));

      return true;
   }

   private static List removeRedundantBounds(Type[] bounds) {
      if (bounds.length == 1) {
         return Collections.singletonList(bounds[0]);
      } else {
         List result = new LinkedList();

         for(int i = 0; i < bounds.length; ++i) {
            boolean isRedundant = false;

            for(int j = 0; j < bounds.length && i != j; ++j) {
               if (Reflections.getRawType(bounds[i]).isAssignableFrom(Reflections.getRawType(bounds[j]))) {
                  isRedundant = true;
                  break;
               }
            }

            if (!isRedundant) {
               result.add(bounds[i]);
            }
         }

         return result;
      }
   }

   protected static boolean areTheSame(Type[] types1, Type[] types2) {
      if (types1.length != types2.length) {
         return false;
      } else {
         for(int i = 0; i < types1.length; ++i) {
            if (!areTheSame(types1[i], types2[i])) {
               return false;
            }
         }

         return true;
      }
   }
}
