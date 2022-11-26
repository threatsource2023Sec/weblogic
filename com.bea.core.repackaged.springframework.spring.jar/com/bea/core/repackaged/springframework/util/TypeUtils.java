package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

public abstract class TypeUtils {
   public static boolean isAssignable(Type lhsType, Type rhsType) {
      Assert.notNull(lhsType, (String)"Left-hand side type must not be null");
      Assert.notNull(rhsType, (String)"Right-hand side type must not be null");
      if (!lhsType.equals(rhsType) && Object.class != lhsType) {
         Type rhsComponent;
         if (lhsType instanceof Class) {
            Class lhsClass = (Class)lhsType;
            if (rhsType instanceof Class) {
               return ClassUtils.isAssignable(lhsClass, (Class)rhsType);
            }

            if (rhsType instanceof ParameterizedType) {
               rhsComponent = ((ParameterizedType)rhsType).getRawType();
               if (rhsComponent instanceof Class) {
                  return ClassUtils.isAssignable(lhsClass, (Class)rhsComponent);
               }
            } else if (lhsClass.isArray() && rhsType instanceof GenericArrayType) {
               rhsComponent = ((GenericArrayType)rhsType).getGenericComponentType();
               return isAssignable((Type)lhsClass.getComponentType(), (Type)rhsComponent);
            }
         }

         Type lhsComponent;
         if (lhsType instanceof ParameterizedType) {
            if (rhsType instanceof Class) {
               lhsComponent = ((ParameterizedType)lhsType).getRawType();
               if (lhsComponent instanceof Class) {
                  return ClassUtils.isAssignable((Class)lhsComponent, (Class)rhsType);
               }
            } else if (rhsType instanceof ParameterizedType) {
               return isAssignable((ParameterizedType)lhsType, (ParameterizedType)rhsType);
            }
         }

         if (lhsType instanceof GenericArrayType) {
            lhsComponent = ((GenericArrayType)lhsType).getGenericComponentType();
            if (rhsType instanceof Class) {
               Class rhsClass = (Class)rhsType;
               if (rhsClass.isArray()) {
                  return isAssignable((Type)lhsComponent, (Type)rhsClass.getComponentType());
               }
            } else if (rhsType instanceof GenericArrayType) {
               rhsComponent = ((GenericArrayType)rhsType).getGenericComponentType();
               return isAssignable(lhsComponent, rhsComponent);
            }
         }

         return lhsType instanceof WildcardType ? isAssignable((WildcardType)lhsType, rhsType) : false;
      } else {
         return true;
      }
   }

   private static boolean isAssignable(ParameterizedType lhsType, ParameterizedType rhsType) {
      if (lhsType.equals(rhsType)) {
         return true;
      } else {
         Type[] lhsTypeArguments = lhsType.getActualTypeArguments();
         Type[] rhsTypeArguments = rhsType.getActualTypeArguments();
         if (lhsTypeArguments.length != rhsTypeArguments.length) {
            return false;
         } else {
            int size = lhsTypeArguments.length;

            for(int i = 0; i < size; ++i) {
               Type lhsArg = lhsTypeArguments[i];
               Type rhsArg = rhsTypeArguments[i];
               if (!lhsArg.equals(rhsArg) && (!(lhsArg instanceof WildcardType) || !isAssignable((WildcardType)lhsArg, rhsArg))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean isAssignable(WildcardType lhsType, Type rhsType) {
      Type[] lUpperBounds = lhsType.getUpperBounds();
      if (lUpperBounds.length == 0) {
         lUpperBounds = new Type[]{Object.class};
      }

      Type[] lLowerBounds = lhsType.getLowerBounds();
      if (lLowerBounds.length == 0) {
         lLowerBounds = new Type[]{null};
      }

      if (rhsType instanceof WildcardType) {
         WildcardType rhsWcType = (WildcardType)rhsType;
         Type[] rUpperBounds = rhsWcType.getUpperBounds();
         if (rUpperBounds.length == 0) {
            rUpperBounds = new Type[]{Object.class};
         }

         Type[] rLowerBounds = rhsWcType.getLowerBounds();
         if (rLowerBounds.length == 0) {
            rLowerBounds = new Type[]{null};
         }

         Type[] var7 = lUpperBounds;
         int var8 = lUpperBounds.length;

         int var9;
         Type lBound;
         Type[] var11;
         int var12;
         int var13;
         Type rBound;
         for(var9 = 0; var9 < var8; ++var9) {
            lBound = var7[var9];
            var11 = rUpperBounds;
            var12 = rUpperBounds.length;

            for(var13 = 0; var13 < var12; ++var13) {
               rBound = var11[var13];
               if (!isAssignableBound(lBound, rBound)) {
                  return false;
               }
            }

            var11 = rLowerBounds;
            var12 = rLowerBounds.length;

            for(var13 = 0; var13 < var12; ++var13) {
               rBound = var11[var13];
               if (!isAssignableBound(lBound, rBound)) {
                  return false;
               }
            }
         }

         var7 = lLowerBounds;
         var8 = lLowerBounds.length;

         for(var9 = 0; var9 < var8; ++var9) {
            lBound = var7[var9];
            var11 = rUpperBounds;
            var12 = rUpperBounds.length;

            for(var13 = 0; var13 < var12; ++var13) {
               rBound = var11[var13];
               if (!isAssignableBound(rBound, lBound)) {
                  return false;
               }
            }

            var11 = rLowerBounds;
            var12 = rLowerBounds.length;

            for(var13 = 0; var13 < var12; ++var13) {
               rBound = var11[var13];
               if (!isAssignableBound(rBound, lBound)) {
                  return false;
               }
            }
         }
      } else {
         Type[] var15 = lUpperBounds;
         int var16 = lUpperBounds.length;

         int var17;
         Type lBound;
         for(var17 = 0; var17 < var16; ++var17) {
            lBound = var15[var17];
            if (!isAssignableBound(lBound, rhsType)) {
               return false;
            }
         }

         var15 = lLowerBounds;
         var16 = lLowerBounds.length;

         for(var17 = 0; var17 < var16; ++var17) {
            lBound = var15[var17];
            if (!isAssignableBound(rhsType, lBound)) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean isAssignableBound(@Nullable Type lhsType, @Nullable Type rhsType) {
      if (rhsType == null) {
         return true;
      } else {
         return lhsType == null ? false : isAssignable(lhsType, rhsType);
      }
   }
}
