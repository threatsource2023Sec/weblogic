package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class UnionTypeReference extends TypeReference {
   public TypeReference[] typeReferences;

   public UnionTypeReference(TypeReference[] typeReferences) {
      this.bits |= 536870912;
      this.typeReferences = typeReferences;
      this.sourceStart = typeReferences[0].sourceStart;
      int length = typeReferences.length;
      this.sourceEnd = typeReferences[length - 1].sourceEnd;
   }

   public char[] getLastToken() {
      return null;
   }

   protected TypeBinding getTypeBinding(Scope scope) {
      return null;
   }

   public TypeBinding resolveType(BlockScope scope, boolean checkBounds, int location) {
      int length = this.typeReferences.length;
      TypeBinding[] allExceptionTypes = new TypeBinding[length];
      boolean hasError = false;

      for(int i = 0; i < length; ++i) {
         TypeBinding exceptionType = this.typeReferences[i].resolveType(scope, checkBounds, location);
         if (exceptionType == null) {
            return null;
         }

         switch (exceptionType.kind()) {
            case 260:
               if (exceptionType.isBoundParameterizedType()) {
                  hasError = true;
                  scope.problemReporter().invalidParameterizedExceptionType(exceptionType, this.typeReferences[i]);
               }
               break;
            case 4100:
               scope.problemReporter().invalidTypeVariableAsException(exceptionType, this.typeReferences[i]);
               hasError = true;
         }

         if (exceptionType.findSuperTypeOriginatingFrom(21, true) == null && exceptionType.isValidBinding()) {
            scope.problemReporter().cannotThrowType(this.typeReferences[i], exceptionType);
            hasError = true;
         }

         allExceptionTypes[i] = exceptionType;

         for(int j = 0; j < i; ++j) {
            if (allExceptionTypes[j].isCompatibleWith(exceptionType)) {
               scope.problemReporter().wrongSequenceOfExceptionTypes(this.typeReferences[j], allExceptionTypes[j], exceptionType);
               hasError = true;
            } else if (exceptionType.isCompatibleWith(allExceptionTypes[j])) {
               scope.problemReporter().wrongSequenceOfExceptionTypes(this.typeReferences[i], exceptionType, allExceptionTypes[j]);
               hasError = true;
            }
         }
      }

      if (hasError) {
         return null;
      } else {
         return this.resolvedType = scope.lowerUpperBound(allExceptionTypes);
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
      if (visitor.visit(this, scope)) {
         int length = this.typeReferences == null ? 0 : this.typeReferences.length;

         for(int i = 0; i < length; ++i) {
            this.typeReferences[i].traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      int length = this.typeReferences == null ? 0 : this.typeReferences.length;
      printIndent(indent, output);

      for(int i = 0; i < length; ++i) {
         this.typeReferences[i].printExpression(0, output);
         if (i != length - 1) {
            output.append(" | ");
         }
      }

      return output;
   }

   public boolean isUnionType() {
      return true;
   }

   public TypeReference augmentTypeWithAdditionalDimensions(int additionalDimensions, Annotation[][] additionalAnnotations, boolean isVarargs) {
      return this;
   }
}
