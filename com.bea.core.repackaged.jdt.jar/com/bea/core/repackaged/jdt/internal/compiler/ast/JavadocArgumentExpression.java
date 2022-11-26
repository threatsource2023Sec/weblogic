package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class JavadocArgumentExpression extends Expression {
   public char[] token;
   public Argument argument;

   public JavadocArgumentExpression(char[] name, int startPos, int endPos, TypeReference typeRef) {
      this.token = name;
      this.sourceStart = startPos;
      this.sourceEnd = endPos;
      long pos = ((long)startPos << 32) + (long)endPos;
      this.argument = new Argument(name, pos, typeRef, 0);
      this.bits |= 32768;
   }

   private TypeBinding internalResolveType(Scope scope) {
      this.constant = Constant.NotAConstant;
      if (this.resolvedType != null) {
         return this.resolvedType.isValidBinding() ? this.resolvedType : null;
      } else {
         if (this.argument != null) {
            TypeReference typeRef = this.argument.type;
            if (typeRef != null) {
               this.resolvedType = typeRef.getTypeBinding(scope);
               typeRef.resolvedType = this.resolvedType;
               if (this.resolvedType == null) {
                  return null;
               }

               if (typeRef instanceof SingleTypeReference && this.resolvedType.leafComponentType().enclosingType() != null && scope.compilerOptions().complianceLevel <= 3145728L) {
                  scope.problemReporter().javadocInvalidMemberTypeQualification(this.sourceStart, this.sourceEnd, scope.getDeclarationModifiers());
               } else if (typeRef instanceof QualifiedTypeReference) {
                  TypeBinding enclosingType = this.resolvedType.leafComponentType().enclosingType();
                  if (enclosingType != null) {
                     int compoundLength;
                     for(compoundLength = 2; (enclosingType = enclosingType.enclosingType()) != null; ++compoundLength) {
                     }

                     int typeNameLength = typeRef.getTypeName().length;
                     if (typeNameLength != compoundLength && typeNameLength != compoundLength + this.resolvedType.getPackage().compoundName.length) {
                        scope.problemReporter().javadocInvalidMemberTypeQualification(typeRef.sourceStart, typeRef.sourceEnd, scope.getDeclarationModifiers());
                     }
                  }
               }

               if (!this.resolvedType.isValidBinding()) {
                  scope.problemReporter().javadocInvalidType(typeRef, this.resolvedType, scope.getDeclarationModifiers());
                  return null;
               }

               if (this.isTypeUseDeprecated(this.resolvedType, scope)) {
                  scope.problemReporter().javadocDeprecatedType(this.resolvedType, typeRef, scope.getDeclarationModifiers());
               }

               return this.resolvedType = scope.environment().convertToRawType(this.resolvedType, true);
            }
         }

         return null;
      }
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      if (this.argument == null) {
         if (this.token != null) {
            output.append(this.token);
         }
      } else {
         this.argument.print(indent, output);
      }

      return output;
   }

   public void resolve(BlockScope scope) {
      if (this.argument != null) {
         this.argument.resolve(scope);
      }

   }

   public TypeBinding resolveType(BlockScope scope) {
      return this.internalResolveType(scope);
   }

   public TypeBinding resolveType(ClassScope scope) {
      return this.internalResolveType(scope);
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope) && this.argument != null) {
         this.argument.traverse(visitor, blockScope);
      }

      visitor.endVisit(this, blockScope);
   }

   public void traverse(ASTVisitor visitor, ClassScope blockScope) {
      if (visitor.visit(this, blockScope) && this.argument != null) {
         this.argument.traverse(visitor, blockScope);
      }

      visitor.endVisit(this, blockScope);
   }
}
