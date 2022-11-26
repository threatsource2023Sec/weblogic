package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.weaver.BoundedReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeFactory;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReferenceType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

public class JavaLangTypeToResolvedTypeConverter {
   private Map typeVariablesInProgress = new HashMap();
   private final World world;

   public JavaLangTypeToResolvedTypeConverter(World aWorld) {
      this.world = aWorld;
   }

   private World getWorld() {
      return this.world;
   }

   public ResolvedType fromType(Type aType) {
      if (aType instanceof Class) {
         Class clazz = (Class)aType;
         String name = clazz.getName();
         if (clazz.isArray()) {
            UnresolvedType ut = UnresolvedType.forSignature(name.replace('.', '/'));
            return this.getWorld().resolve(ut);
         } else {
            return this.getWorld().resolve(name);
         }
      } else {
         Type[] upperBounds;
         if (aType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)aType;
            ResolvedType baseType = this.fromType(pt.getRawType());
            upperBounds = pt.getActualTypeArguments();
            ResolvedType[] resolvedArgs = this.fromTypes(upperBounds);
            return TypeFactory.createParameterizedType(baseType, resolvedArgs, this.getWorld());
         } else if (aType instanceof TypeVariable) {
            TypeVariableReferenceType inprogressVar = (TypeVariableReferenceType)this.typeVariablesInProgress.get(aType);
            if (inprogressVar != null) {
               return inprogressVar;
            } else {
               TypeVariable tv = (TypeVariable)aType;
               com.bea.core.repackaged.aspectj.weaver.TypeVariable rt_tv = new com.bea.core.repackaged.aspectj.weaver.TypeVariable(tv.getName());
               TypeVariableReferenceType tvrt = new TypeVariableReferenceType(rt_tv, this.getWorld());
               this.typeVariablesInProgress.put(aType, tvrt);
               Type[] bounds = tv.getBounds();
               ResolvedType[] resBounds = this.fromTypes(bounds);
               ResolvedType upperBound = resBounds[0];
               ResolvedType[] additionalBounds = new ResolvedType[0];
               if (resBounds.length > 1) {
                  additionalBounds = new ResolvedType[resBounds.length - 1];
                  System.arraycopy(resBounds, 1, additionalBounds, 0, additionalBounds.length);
               }

               rt_tv.setUpperBound(upperBound);
               rt_tv.setAdditionalInterfaceBounds(additionalBounds);
               this.typeVariablesInProgress.remove(aType);
               return tvrt;
            }
         } else if (aType instanceof WildcardType) {
            WildcardType wildType = (WildcardType)aType;
            Type[] lowerBounds = wildType.getLowerBounds();
            upperBounds = wildType.getUpperBounds();
            ResolvedType bound = null;
            boolean isExtends = lowerBounds.length == 0;
            if (isExtends) {
               bound = this.fromType(upperBounds[0]);
            } else {
               bound = this.fromType(lowerBounds[0]);
            }

            return new BoundedReferenceType((ReferenceType)bound, isExtends, this.getWorld());
         } else if (aType instanceof GenericArrayType) {
            GenericArrayType gt = (GenericArrayType)aType;
            Type componentType = gt.getGenericComponentType();
            return UnresolvedType.makeArray(this.fromType(componentType), 1).resolve(this.getWorld());
         } else {
            return ResolvedType.MISSING;
         }
      }
   }

   public ResolvedType[] fromTypes(Type[] types) {
      ResolvedType[] ret = new ResolvedType[types.length];

      for(int i = 0; i < ret.length; ++i) {
         ret[i] = this.fromType(types[i]);
      }

      return ret;
   }
}
