package org.jboss.weld.resolution;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.reflection.Reflections;

public class BeanTypeAssignabilityRules extends AbstractAssignabilityRules {
   private static final AssignabilityRules INSTANCE = new BeanTypeAssignabilityRules();

   public static AssignabilityRules instance() {
      return INSTANCE;
   }

   protected BeanTypeAssignabilityRules() {
   }

   public boolean matches(Type requiredType, Type beanType) {
      return this.matchesNoBoxing(Types.boxedType(requiredType), Types.boxedType(beanType));
   }

   public boolean matchesNoBoxing(Type requiredType, Type beanType) {
      if (Types.isArray(requiredType) && Types.isArray(beanType)) {
         return this.matchesNoBoxing(Types.getArrayComponentType(requiredType), Types.getArrayComponentType(beanType));
      } else {
         if (requiredType instanceof Class) {
            if (beanType instanceof Class) {
               return this.matches((Class)requiredType, (Class)beanType);
            }

            if (beanType instanceof ParameterizedType) {
               return this.matches((Class)requiredType, (ParameterizedType)beanType);
            }
         } else if (requiredType instanceof ParameterizedType) {
            if (beanType instanceof Class) {
               return this.matches((Class)beanType, (ParameterizedType)requiredType);
            }

            if (beanType instanceof ParameterizedType) {
               return this.matches((ParameterizedType)requiredType, (ParameterizedType)beanType);
            }
         }

         return false;
      }
   }

   private boolean matches(Class requiredType, Class beanType) {
      return requiredType.equals(beanType);
   }

   private boolean matches(Class type1, ParameterizedType type2) {
      return !type1.equals(Reflections.getRawType(type2)) ? false : Types.isArrayOfUnboundedTypeVariablesOrObjects(type2.getActualTypeArguments());
   }

   private boolean matches(ParameterizedType requiredType, ParameterizedType beanType) {
      if (!requiredType.getRawType().equals(beanType.getRawType())) {
         return false;
      } else if (requiredType.getActualTypeArguments().length != beanType.getActualTypeArguments().length) {
         throw new IllegalArgumentException("Invalid argument combination " + requiredType + "; " + beanType);
      } else {
         for(int i = 0; i < requiredType.getActualTypeArguments().length; ++i) {
            if (!this.parametersMatch(requiredType.getActualTypeArguments()[i], beanType.getActualTypeArguments()[i])) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean parametersMatch(Type requiredParameter, Type beanParameter) {
      if (Types.isActualType(requiredParameter) && Types.isActualType(beanParameter)) {
         return this.matches(requiredParameter, beanParameter);
      } else if (requiredParameter instanceof WildcardType && Types.isActualType(beanParameter)) {
         return this.parametersMatch((WildcardType)requiredParameter, beanParameter);
      } else if (requiredParameter instanceof WildcardType && beanParameter instanceof TypeVariable) {
         return this.parametersMatch((WildcardType)requiredParameter, (TypeVariable)beanParameter);
      } else if (Types.isActualType(requiredParameter) && beanParameter instanceof TypeVariable) {
         return this.parametersMatch(requiredParameter, (TypeVariable)beanParameter);
      } else {
         return requiredParameter instanceof TypeVariable && beanParameter instanceof TypeVariable ? this.parametersMatch((TypeVariable)requiredParameter, (TypeVariable)beanParameter) : false;
      }
   }

   protected boolean parametersMatch(WildcardType requiredParameter, Type beanParameter) {
      return this.lowerBoundsOfWildcardMatch(beanParameter, requiredParameter) && this.upperBoundsOfWildcardMatch(requiredParameter, beanParameter);
   }

   protected boolean parametersMatch(WildcardType requiredParameter, TypeVariable beanParameter) {
      Type[] beanParameterBounds = this.getUppermostTypeVariableBounds(beanParameter);
      if (!this.lowerBoundsOfWildcardMatch(beanParameterBounds, requiredParameter)) {
         return false;
      } else {
         Type[] requiredUpperBounds = requiredParameter.getUpperBounds();
         return this.boundsMatch(requiredUpperBounds, beanParameterBounds) || this.boundsMatch(beanParameterBounds, requiredUpperBounds);
      }
   }

   protected boolean parametersMatch(Type requiredParameter, TypeVariable beanParameter) {
      Type[] var3 = this.getUppermostTypeVariableBounds(beanParameter);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type bound = var3[var5];
         if (!CovariantTypes.isAssignableFrom(bound, requiredParameter)) {
            return false;
         }
      }

      return true;
   }

   protected boolean parametersMatch(TypeVariable requiredParameter, TypeVariable beanParameter) {
      return this.boundsMatch(this.getUppermostTypeVariableBounds(beanParameter), this.getUppermostTypeVariableBounds(requiredParameter));
   }
}
