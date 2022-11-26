package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;

public class SingleTypeReference extends TypeReference {
   public char[] token;

   public SingleTypeReference(char[] source, long pos) {
      this.token = source;
      this.sourceStart = (int)(pos >>> 32);
      this.sourceEnd = (int)(pos & 4294967295L);
   }

   public TypeReference augmentTypeWithAdditionalDimensions(int additionalDimensions, Annotation[][] additionalAnnotations, boolean isVarargs) {
      int totalDimensions = this.dimensions() + additionalDimensions;
      Annotation[][] allAnnotations = this.getMergedAnnotationsOnDimensions(additionalDimensions, additionalAnnotations);
      ArrayTypeReference arrayTypeReference = new ArrayTypeReference(this.token, totalDimensions, allAnnotations, ((long)this.sourceStart << 32) + (long)this.sourceEnd);
      arrayTypeReference.annotations = this.annotations;
      arrayTypeReference.bits |= this.bits & 1048576;
      if (!isVarargs) {
         arrayTypeReference.extendedDimensions = additionalDimensions;
      }

      return arrayTypeReference;
   }

   public char[] getLastToken() {
      return this.token;
   }

   protected TypeBinding getTypeBinding(Scope scope) {
      if (this.resolvedType != null) {
         return this.resolvedType;
      } else {
         this.resolvedType = scope.getType(this.token);
         if (this.resolvedType instanceof TypeVariableBinding) {
            TypeVariableBinding typeVariable = (TypeVariableBinding)this.resolvedType;
            if (typeVariable.declaringElement instanceof SourceTypeBinding) {
               scope.tagAsAccessingEnclosingInstanceStateOf((ReferenceBinding)typeVariable.declaringElement, true);
            }
         } else if (this.resolvedType instanceof LocalTypeBinding) {
            LocalTypeBinding localType = (LocalTypeBinding)this.resolvedType;
            MethodScope methodScope = scope.methodScope();
            if (methodScope != null && !methodScope.isStatic) {
               methodScope.tagAsAccessingEnclosingInstanceStateOf(localType, false);
            }
         }

         return scope.kind == 3 && this.resolvedType.isValidBinding() && ((ClassScope)scope).detectHierarchyCycle(this.resolvedType, this) ? null : this.resolvedType;
      }
   }

   public char[][] getTypeName() {
      return new char[][]{this.token};
   }

   public boolean isBaseTypeReference() {
      return this.token == BYTE || this.token == SHORT || this.token == INT || this.token == LONG || this.token == FLOAT || this.token == DOUBLE || this.token == CHAR || this.token == BOOLEAN || this.token == NULL || this.token == VOID;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      if (this.annotations != null && this.annotations[0] != null) {
         printAnnotations(this.annotations[0], output);
         output.append(' ');
      }

      return output.append(this.token);
   }

   public TypeBinding resolveTypeEnclosing(BlockScope scope, ReferenceBinding enclosingType) {
      this.resolvedType = scope.getMemberType(this.token, enclosingType);
      boolean hasError = false;
      this.resolveAnnotations(scope, 0);
      TypeBinding memberType = this.resolvedType;
      if (!memberType.isValidBinding()) {
         hasError = true;
         scope.problemReporter().invalidEnclosingType(this, memberType, enclosingType);
         memberType = ((ReferenceBinding)memberType).closestMatch();
         if (memberType == null) {
            return null;
         }
      }

      if (this.isTypeUseDeprecated(memberType, scope)) {
         this.reportDeprecatedType(memberType, scope);
      }

      memberType = scope.environment().convertToRawType(memberType, false);
      if (memberType.isRawType() && (this.bits & 1073741824) == 0 && scope.compilerOptions().getSeverity(536936448) != 256) {
         scope.problemReporter().rawTypeReference(this, memberType);
      }

      return hasError ? memberType : (this.resolvedType = memberType);
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope) && this.annotations != null) {
         Annotation[] typeAnnotations = this.annotations[0];
         int i = 0;

         for(int length = typeAnnotations == null ? 0 : typeAnnotations.length; i < length; ++i) {
            typeAnnotations[i].traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope) && this.annotations != null) {
         Annotation[] typeAnnotations = this.annotations[0];
         int i = 0;

         for(int length = typeAnnotations == null ? 0 : typeAnnotations.length; i < length; ++i) {
            typeAnnotations[i].traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }
}
