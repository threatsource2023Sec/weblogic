package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleLookupTable;

public class TypeBindingVisitor {
   private SimpleLookupTable visitedCache;

   public void reset() {
      this.visitedCache = null;
   }

   public boolean visit(BaseTypeBinding baseTypeBinding) {
      return true;
   }

   public boolean visit(ArrayBinding arrayBinding) {
      return true;
   }

   public boolean visit(TypeVariableBinding typeVariable) {
      return true;
   }

   public boolean visit(ReferenceBinding referenceBinding) {
      return true;
   }

   public boolean visit(WildcardBinding wildcardBinding) {
      return true;
   }

   public boolean visit(ParameterizedTypeBinding parameterizedTypeBinding) {
      return true;
   }

   public boolean visit(IntersectionTypeBinding18 intersectionTypeBinding18) {
      return true;
   }

   public boolean visit(RawTypeBinding rawTypeBinding) {
      return true;
   }

   public boolean visit(PolyTypeBinding polyTypeBinding) {
      return true;
   }

   public static void visit(TypeBindingVisitor visitor, ReferenceBinding[] types) {
      int i = 0;

      for(int length = types == null ? 0 : types.length; i < length; ++i) {
         visit(visitor, (TypeBinding)types[i]);
      }

   }

   public static void visit(TypeBindingVisitor visitor, TypeBinding type) {
      if (type != null) {
         SimpleLookupTable visitedCache = visitor.visitedCache;
         if (visitedCache == null) {
            visitor.visitedCache = new SimpleLookupTable(3);
            visitedCache = visitor.visitedCache;
         }

         Object result = visitedCache.get(type);
         if (result != Boolean.TRUE) {
            visitedCache.put(type, Boolean.TRUE);
            switch (type.kind()) {
               case 4:
               case 2052:
                  ReferenceBinding referenceBinding = (ReferenceBinding)type;
                  if (visitor.visit(referenceBinding)) {
                     visit(visitor, (TypeBinding)referenceBinding.enclosingType());
                     visit(visitor, (ReferenceBinding[])referenceBinding.typeVariables());
                  }
                  break;
               case 68:
                  ArrayBinding arrayBinding = (ArrayBinding)type;
                  if (visitor.visit(arrayBinding)) {
                     visit(visitor, arrayBinding.leafComponentType);
                  }
                  break;
               case 132:
                  visitor.visit((BaseTypeBinding)type);
                  break;
               case 260:
                  ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding)type;
                  if (visitor.visit(parameterizedTypeBinding)) {
                     visit(visitor, (TypeBinding)parameterizedTypeBinding.enclosingType());
                     visit(visitor, parameterizedTypeBinding.arguments);
                  }
                  break;
               case 516:
               case 8196:
                  WildcardBinding wildcard = (WildcardBinding)type;
                  if (visitor.visit(wildcard) && wildcard.boundKind != 0) {
                     visit(visitor, wildcard.bound);
                     visit(visitor, wildcard.otherBounds);
                  }
                  break;
               case 1028:
                  visitor.visit((RawTypeBinding)type);
                  break;
               case 4100:
                  TypeVariableBinding typeVariableBinding = (TypeVariableBinding)type;
                  if (visitor.visit(typeVariableBinding)) {
                     visit(visitor, typeVariableBinding.firstBound);
                     visit(visitor, (TypeBinding)typeVariableBinding.superclass);
                     visit(visitor, typeVariableBinding.superInterfaces);
                  }
                  break;
               case 32772:
                  IntersectionTypeBinding18 intersectionTypeBinding18 = (IntersectionTypeBinding18)type;
                  if (visitor.visit(intersectionTypeBinding18)) {
                     visit(visitor, intersectionTypeBinding18.intersectingTypes);
                  }
                  break;
               case 65540:
                  visitor.visit((PolyTypeBinding)type);
                  break;
               default:
                  throw new InternalError("Unexpected binding type");
            }

         }
      }
   }

   public static void visit(TypeBindingVisitor visitor, TypeBinding[] types) {
      int i = 0;

      for(int length = types == null ? 0 : types.length; i < length; ++i) {
         visit(visitor, types[i]);
      }

   }
}
