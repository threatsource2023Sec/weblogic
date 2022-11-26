package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class QualifiedSuperReference extends QualifiedThisReference {
   public QualifiedSuperReference(TypeReference name, int pos, int sourceEnd) {
      super(name, pos, sourceEnd);
   }

   public boolean isSuper() {
      return true;
   }

   public boolean isQualifiedSuper() {
      return true;
   }

   public boolean isThis() {
      return false;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      return this.qualification.print(0, output).append(".super");
   }

   public TypeBinding resolveType(BlockScope scope) {
      if ((this.bits & 534773760) != 0) {
         scope.problemReporter().invalidParenthesizedExpression(this);
         return null;
      } else {
         super.resolveType(scope);
         if (this.resolvedType != null && !this.resolvedType.isValidBinding()) {
            scope.problemReporter().illegalSuperAccess(this.qualification.resolvedType, this.resolvedType, this);
            return null;
         } else if (this.currentCompatibleType == null) {
            return null;
         } else if (this.currentCompatibleType.id == 1) {
            scope.problemReporter().cannotUseSuperInJavaLangObject(this);
            return null;
         } else {
            return this.resolvedType = this.currentCompatibleType.isInterface() ? this.currentCompatibleType : this.currentCompatibleType.superclass();
         }
      }
   }

   int findCompatibleEnclosing(ReferenceBinding enclosingType, TypeBinding type, BlockScope scope) {
      if (!type.isInterface()) {
         return super.findCompatibleEnclosing(enclosingType, type, scope);
      } else {
         CompilerOptions compilerOptions = scope.compilerOptions();
         ReferenceBinding[] supers = enclosingType.superInterfaces();
         int length = supers.length;
         boolean isJava8 = compilerOptions.complianceLevel >= 3407872L;
         boolean isLegal = true;
         char[][] compoundName = null;
         ReferenceBinding closestMatch = null;

         for(int i = 0; i < length; ++i) {
            if (TypeBinding.equalsEquals(supers[i].erasure(), type)) {
               this.currentCompatibleType = closestMatch = supers[i];
            } else if (supers[i].erasure().isCompatibleWith(type)) {
               isLegal = false;
               compoundName = supers[i].compoundName;
               if (closestMatch == null) {
                  closestMatch = supers[i];
               }
            }
         }

         if (!isLegal || !isJava8) {
            this.currentCompatibleType = null;
            this.resolvedType = new ProblemReferenceBinding(compoundName, closestMatch, isJava8 ? 21 : 29);
         }

         return 0;
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.qualification.traverse(visitor, blockScope);
      }

      visitor.endVisit(this, blockScope);
   }

   public void traverse(ASTVisitor visitor, ClassScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.qualification.traverse(visitor, blockScope);
      }

      visitor.endVisit(this, blockScope);
   }
}
