package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.IntersectionTypeBinding18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.util.HashMap;
import java.util.Map;

public class IntersectionCastTypeReference extends TypeReference {
   public TypeReference[] typeReferences;

   public IntersectionCastTypeReference(TypeReference[] typeReferences) {
      this.typeReferences = typeReferences;
      this.sourceStart = typeReferences[0].sourceStart;
      int length = typeReferences.length;
      this.sourceEnd = typeReferences[length - 1].sourceEnd;
      int i = 0;

      for(int max = typeReferences.length; i < max; ++i) {
         if ((typeReferences[i].bits & 1048576) != 0) {
            this.bits |= 1048576;
            break;
         }
      }

   }

   public TypeReference augmentTypeWithAdditionalDimensions(int additionalDimensions, Annotation[][] additionalAnnotations, boolean isVarargs) {
      throw new UnsupportedOperationException();
   }

   public char[] getLastToken() {
      return null;
   }

   protected TypeBinding getTypeBinding(Scope scope) {
      return null;
   }

   public TypeReference[] getTypeReferences() {
      return this.typeReferences;
   }

   public TypeBinding resolveType(BlockScope scope, boolean checkBounds, int location) {
      int length = this.typeReferences.length;
      ReferenceBinding[] intersectingTypes = new ReferenceBinding[length];
      boolean hasError = false;
      int typeCount = 0;

      label106:
      for(int i = 0; i < length; ++i) {
         TypeReference typeReference = this.typeReferences[i];
         TypeBinding type = typeReference.resolveType(scope, checkBounds, location);
         if (type != null && (type.tagBits & 128L) == 0L) {
            if (i == 0) {
               if (type.isBaseType()) {
                  scope.problemReporter().onlyReferenceTypesInIntersectionCast(typeReference);
                  hasError = true;
                  continue;
               }

               if (type.isArrayType()) {
                  scope.problemReporter().illegalArrayTypeInIntersectionCast(typeReference);
                  hasError = true;
                  continue;
               }
            } else if (!type.isInterface()) {
               scope.problemReporter().boundMustBeAnInterface(typeReference, type);
               hasError = true;
               continue;
            }

            for(int j = 0; j < typeCount; ++j) {
               ReferenceBinding priorType = intersectingTypes[j];
               if (TypeBinding.equalsEquals(priorType, type)) {
                  scope.problemReporter().duplicateBoundInIntersectionCast(typeReference);
                  hasError = true;
               } else if (priorType.isInterface()) {
                  if (TypeBinding.equalsEquals(type.findSuperTypeOriginatingFrom(priorType), priorType)) {
                     intersectingTypes[j] = (ReferenceBinding)type;
                     continue label106;
                  }

                  if (TypeBinding.equalsEquals(priorType.findSuperTypeOriginatingFrom(type), type)) {
                     continue label106;
                  }
               }
            }

            intersectingTypes[typeCount++] = (ReferenceBinding)type;
         } else {
            hasError = true;
         }
      }

      if (hasError) {
         return null;
      } else {
         if (typeCount != length) {
            if (typeCount == 1) {
               return this.resolvedType = intersectingTypes[0];
            }

            System.arraycopy(intersectingTypes, 0, intersectingTypes = new ReferenceBinding[typeCount], 0, typeCount);
         }

         IntersectionTypeBinding18 intersectionType = (IntersectionTypeBinding18)scope.environment().createIntersectionType18(intersectingTypes);
         ReferenceBinding itsSuperclass = null;
         ReferenceBinding[] interfaces = intersectingTypes;
         ReferenceBinding firstType = intersectingTypes[0];
         if (firstType.isClass()) {
            itsSuperclass = firstType.superclass();
            System.arraycopy(intersectingTypes, 1, interfaces = new ReferenceBinding[typeCount - 1], 0, typeCount - 1);
         }

         Map invocations = new HashMap(2);
         int i = 0;

         for(int interfaceCount = interfaces.length; i < interfaceCount; ++i) {
            ReferenceBinding one = interfaces[i];
            if (one != null && (itsSuperclass == null || !scope.hasErasedCandidatesCollisions(itsSuperclass, one, invocations, intersectionType, this))) {
               for(int j = 0; j < i; ++j) {
                  ReferenceBinding two = interfaces[j];
                  if (two != null && scope.hasErasedCandidatesCollisions(one, two, invocations, intersectionType, this)) {
                     break;
                  }
               }
            }
         }

         if ((intersectionType.tagBits & 131072L) != 0L) {
            return null;
         } else {
            return this.resolvedType = intersectionType;
         }
      }
   }

   public char[][] getTypeName() {
      return this.typeReferences[0].getTypeName();
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         int length = this.typeReferences == null ? 0 : this.typeReferences.length;

         for(int i = 0; i < length; ++i) {
            this.typeReferences[i].traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      throw new UnsupportedOperationException("Unexpected traversal request: IntersectionTypeReference in class scope");
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      int length = this.typeReferences == null ? 0 : this.typeReferences.length;
      printIndent(indent, output);

      for(int i = 0; i < length; ++i) {
         this.typeReferences[i].printExpression(0, output);
         if (i != length - 1) {
            output.append(" & ");
         }
      }

      return output;
   }
}
