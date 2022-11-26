package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemFieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class JavadocFieldReference extends FieldReference {
   public int tagSourceStart;
   public int tagSourceEnd;
   public int tagValue;
   public MethodBinding methodBinding;

   public JavadocFieldReference(char[] source, long pos) {
      super(source, pos);
      this.bits |= 32768;
   }

   protected TypeBinding internalResolveType(Scope scope) {
      this.constant = Constant.NotAConstant;
      if (this.receiver == null) {
         this.actualReceiverType = scope.enclosingReceiverType();
      } else if (scope.kind == 3) {
         this.actualReceiverType = this.receiver.resolveType((ClassScope)scope);
      } else {
         this.actualReceiverType = this.receiver.resolveType((BlockScope)scope);
      }

      if (this.actualReceiverType == null) {
         return null;
      } else {
         Binding fieldBinding = this.receiver != null && this.receiver.isThis() ? scope.classScope().getBinding(this.token, this.bits & 7, this, true) : scope.getField(this.actualReceiverType, this.token, this);
         if (!((Binding)fieldBinding).isValidBinding()) {
            switch (((Binding)fieldBinding).problemId()) {
               case 5:
               case 6:
               case 7:
                  FieldBinding closestMatch = ((ProblemFieldBinding)fieldBinding).closestMatch;
                  if (closestMatch != null) {
                     fieldBinding = closestMatch;
                  }
            }
         }

         if (((Binding)fieldBinding).isValidBinding() && fieldBinding instanceof FieldBinding) {
            this.binding = (FieldBinding)fieldBinding;
            if (this.isFieldUseDeprecated(this.binding, scope, this.bits)) {
               scope.problemReporter().javadocDeprecatedField(this.binding, this, scope.getDeclarationModifiers());
            }

            return this.resolvedType = this.binding.type;
         } else if (this.receiver.resolvedType instanceof ProblemReferenceBinding) {
            return null;
         } else {
            if (this.actualReceiverType instanceof ReferenceBinding) {
               ReferenceBinding refBinding = (ReferenceBinding)this.actualReceiverType;
               char[] selector = this.token;
               MethodBinding possibleMethod = null;
               if (CharOperation.equals(this.actualReceiverType.sourceName(), selector)) {
                  possibleMethod = scope.getConstructor(refBinding, Binding.NO_TYPES, this);
               } else {
                  possibleMethod = this.receiver.isThis() ? scope.getImplicitMethod(selector, Binding.NO_TYPES, this) : scope.getMethod(refBinding, selector, Binding.NO_TYPES, this);
               }

               if (possibleMethod.isValidBinding()) {
                  this.methodBinding = possibleMethod;
               } else {
                  ProblemMethodBinding problemMethodBinding = (ProblemMethodBinding)possibleMethod;
                  if (problemMethodBinding.closestMatch == null) {
                     if (((Binding)fieldBinding).isValidBinding()) {
                        fieldBinding = new ProblemFieldBinding(refBinding, ((Binding)fieldBinding).readableName(), 1);
                     }

                     scope.problemReporter().javadocInvalidField(this, (Binding)fieldBinding, this.actualReceiverType, scope.getDeclarationModifiers());
                  } else {
                     this.methodBinding = problemMethodBinding.closestMatch;
                  }
               }
            }

            return null;
         }
      }
   }

   public boolean isSuperAccess() {
      return (this.bits & 16384) != 0;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      if (this.receiver != null) {
         this.receiver.printExpression(0, output);
      }

      output.append('#').append(this.token);
      return output;
   }

   public TypeBinding resolveType(BlockScope scope) {
      return this.internalResolveType(scope);
   }

   public TypeBinding resolveType(ClassScope scope) {
      return this.internalResolveType(scope);
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope) && this.receiver != null) {
         this.receiver.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope) && this.receiver != null) {
         this.receiver.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}
