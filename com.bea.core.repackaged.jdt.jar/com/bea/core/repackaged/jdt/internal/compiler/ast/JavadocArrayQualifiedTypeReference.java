package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class JavadocArrayQualifiedTypeReference extends ArrayQualifiedTypeReference {
   public int tagSourceStart;
   public int tagSourceEnd;

   public JavadocArrayQualifiedTypeReference(JavadocQualifiedTypeReference typeRef, int dim) {
      super(typeRef.tokens, dim, typeRef.sourcePositions);
   }

   protected void reportInvalidType(Scope scope) {
      scope.problemReporter().javadocInvalidType(this, this.resolvedType, scope.getDeclarationModifiers());
   }

   protected void reportDeprecatedType(TypeBinding type, Scope scope) {
      scope.problemReporter().javadocDeprecatedType(type, this, scope.getDeclarationModifiers());
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
