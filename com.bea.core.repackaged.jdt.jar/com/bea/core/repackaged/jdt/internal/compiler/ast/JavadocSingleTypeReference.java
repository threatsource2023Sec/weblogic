package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PackageBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class JavadocSingleTypeReference extends SingleTypeReference {
   public int tagSourceStart;
   public int tagSourceEnd;
   public PackageBinding packageBinding;

   public JavadocSingleTypeReference(char[] source, long pos, int tagStart, int tagEnd) {
      super(source, pos);
      this.tagSourceStart = tagStart;
      this.tagSourceEnd = tagEnd;
      this.bits |= 32768;
   }

   protected TypeBinding internalResolveType(Scope scope, int location) {
      this.constant = Constant.NotAConstant;
      if (this.resolvedType != null) {
         if (this.resolvedType.isValidBinding()) {
            return this.resolvedType;
         } else {
            switch (this.resolvedType.problemId()) {
               case 1:
               case 2:
               case 5:
                  TypeBinding type = this.resolvedType.closestMatch();
                  return type;
               case 3:
               case 4:
               default:
                  return null;
            }
         }
      } else {
         this.resolvedType = this.getTypeBinding(scope);
         if (this.resolvedType instanceof LocalTypeBinding) {
            LocalTypeBinding localType = (LocalTypeBinding)this.resolvedType;
            if (localType.scope != null && localType.scope.parent == scope) {
               this.resolvedType = new ProblemReferenceBinding(new char[][]{localType.sourceName}, (ReferenceBinding)this.resolvedType, 1);
            }
         }

         if (this.resolvedType == null) {
            return null;
         } else if (!this.resolvedType.isValidBinding()) {
            char[][] tokens = new char[][]{this.token};
            Binding binding = scope.getTypeOrPackage(tokens);
            if (binding instanceof PackageBinding) {
               this.packageBinding = (PackageBinding)binding;
            } else {
               if (this.resolvedType.problemId() == 7) {
                  TypeBinding closestMatch = this.resolvedType.closestMatch();
                  if (closestMatch != null && closestMatch.isTypeVariable()) {
                     this.resolvedType = closestMatch;
                     return this.resolvedType;
                  }
               }

               this.reportInvalidType(scope);
            }

            return null;
         } else {
            if (this.isTypeUseDeprecated(this.resolvedType, scope)) {
               this.reportDeprecatedType(this.resolvedType, scope);
            }

            if (this.resolvedType.isGenericType() || this.resolvedType.isParameterizedType()) {
               this.resolvedType = scope.environment().convertToRawType(this.resolvedType, true);
            }

            return this.resolvedType;
         }
      }
   }

   protected void reportDeprecatedType(TypeBinding type, Scope scope) {
      scope.problemReporter().javadocDeprecatedType(type, this, scope.getDeclarationModifiers());
   }

   protected void reportInvalidType(Scope scope) {
      scope.problemReporter().javadocInvalidType(this, this.resolvedType, scope.getDeclarationModifiers());
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }
}
