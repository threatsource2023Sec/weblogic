package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CatchParameterBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class Argument extends LocalDeclaration {
   private static final char[] SET = "set".toCharArray();

   public Argument(char[] name, long posNom, TypeReference tr, int modifiers) {
      super(name, (int)(posNom >>> 32), (int)posNom);
      this.declarationSourceEnd = (int)posNom;
      this.modifiers = modifiers;
      this.type = tr;
      if (tr != null) {
         this.bits |= tr.bits & 1048576;
      }

      this.bits |= 1073741828;
   }

   public Argument(char[] name, long posNom, TypeReference tr, int modifiers, boolean typeElided) {
      super(name, (int)(posNom >>> 32), (int)posNom);
      this.declarationSourceEnd = (int)posNom;
      this.modifiers = modifiers;
      this.type = tr;
      if (tr != null) {
         this.bits |= tr.bits & 1048576;
      }

      this.bits |= 1073741830;
   }

   public boolean isRecoveredFromLoneIdentifier() {
      return false;
   }

   public TypeBinding createBinding(MethodScope scope, TypeBinding typeBinding) {
      if (this.binding == null) {
         this.binding = new LocalVariableBinding(this, typeBinding, this.modifiers, scope);
      } else if (!this.binding.type.isValidBinding()) {
         AbstractMethodDeclaration methodDecl = scope.referenceMethod();
         if (methodDecl != null) {
            MethodBinding methodBinding = methodDecl.binding;
            if (methodBinding != null) {
               methodBinding.tagBits |= 512L;
            }
         }
      }

      if ((this.binding.tagBits & 8589934592L) == 0L) {
         resolveAnnotations(scope, this.annotations, this.binding, true);
         if (scope.compilerOptions().sourceLevel >= 3407872L) {
            Annotation.isTypeUseCompatible(this.type, scope, this.annotations);
            scope.validateNullAnnotation(this.binding.tagBits, this.type, this.annotations);
         }
      }

      this.binding.declaration = this;
      return this.binding.type;
   }

   public TypeBinding bind(MethodScope scope, TypeBinding typeBinding, boolean used) {
      TypeBinding newTypeBinding = this.createBinding(scope, typeBinding);
      Binding existingVariable = scope.getBinding(this.name, 3, this, false);
      if (existingVariable != null && existingVariable.isValidBinding()) {
         boolean localExists = existingVariable instanceof LocalVariableBinding;
         if (localExists && this.hiddenVariableDepth == 0) {
            if ((this.bits & 2097152) != 0 && scope.isLambdaSubscope()) {
               scope.problemReporter().lambdaRedeclaresArgument(this);
            } else {
               scope.problemReporter().redefineArgument(this);
            }
         } else {
            boolean isSpecialArgument = false;
            if (existingVariable instanceof FieldBinding) {
               if (scope.isInsideConstructor()) {
                  isSpecialArgument = true;
               } else {
                  AbstractMethodDeclaration methodDecl = scope.referenceMethod();
                  if (methodDecl != null && CharOperation.prefixEquals(SET, methodDecl.selector)) {
                     isSpecialArgument = true;
                  }
               }
            }

            scope.problemReporter().localVariableHiding(this, existingVariable, isSpecialArgument);
         }
      }

      scope.addLocalVariable(this.binding);
      this.binding.useFlag = used ? 1 : 0;
      return newTypeBinding;
   }

   public int getKind() {
      return (this.bits & 4) != 0 ? 5 : 4;
   }

   public boolean isArgument() {
      return true;
   }

   public boolean isVarArgs() {
      return this.type != null && (this.type.bits & 16384) != 0;
   }

   public boolean hasElidedType() {
      return (this.bits & 2) != 0;
   }

   public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition position) {
      return TypeReference.containsNullAnnotation(this.annotations) || this.type != null && this.type.hasNullTypeAnnotation(position);
   }

   public StringBuffer print(int indent, StringBuffer output) {
      printIndent(indent, output);
      printModifiers(this.modifiers, output);
      if (this.annotations != null) {
         printAnnotations(this.annotations, output);
         output.append(' ');
      }

      if (this.type == null) {
         output.append("<no type> ");
      } else {
         this.type.print(0, output).append(' ');
      }

      return output.append(this.name);
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      return this.print(indent, output).append(';');
   }

   public TypeBinding resolveForCatch(BlockScope scope) {
      TypeBinding exceptionType = this.type.resolveType(scope, true);
      boolean hasError;
      if (exceptionType == null) {
         hasError = true;
      } else {
         hasError = false;
         switch (exceptionType.kind()) {
            case 260:
               if (exceptionType.isBoundParameterizedType()) {
                  hasError = true;
                  scope.problemReporter().invalidParameterizedExceptionType(exceptionType, this);
               }
               break;
            case 4100:
               scope.problemReporter().invalidTypeVariableAsException(exceptionType, this);
               hasError = true;
         }

         if (exceptionType.findSuperTypeOriginatingFrom(21, true) == null && exceptionType.isValidBinding()) {
            scope.problemReporter().cannotThrowType(this.type, exceptionType);
            hasError = true;
         }
      }

      Binding existingVariable = scope.getBinding(this.name, 3, this, false);
      if (existingVariable != null && existingVariable.isValidBinding()) {
         if (existingVariable instanceof LocalVariableBinding && this.hiddenVariableDepth == 0) {
            scope.problemReporter().redefineArgument(this);
         } else {
            scope.problemReporter().localVariableHiding(this, existingVariable, false);
         }
      }

      if ((this.type.bits & 536870912) != 0) {
         this.binding = new CatchParameterBinding(this, exceptionType, this.modifiers | 16, false);
         LocalVariableBinding var10000 = this.binding;
         var10000.tagBits |= 4096L;
      } else {
         this.binding = new CatchParameterBinding(this, exceptionType, this.modifiers, false);
      }

      resolveAnnotations(scope, this.annotations, this.binding, true);
      Annotation.isTypeUseCompatible(this.type, scope, this.annotations);
      if (scope.compilerOptions().isAnnotationBasedNullAnalysisEnabled && (this.type.hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY) || TypeReference.containsNullAnnotation(this.annotations))) {
         scope.problemReporter().nullAnnotationUnsupportedLocation(this.type);
      }

      scope.addLocalVariable(this.binding);
      this.binding.setConstant(Constant.NotAConstant);
      return hasError ? null : exceptionType;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.annotations != null) {
            int annotationsLength = this.annotations.length;

            for(int i = 0; i < annotationsLength; ++i) {
               this.annotations[i].traverse(visitor, scope);
            }
         }

         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         if (this.annotations != null) {
            int annotationsLength = this.annotations.length;

            for(int i = 0; i < annotationsLength; ++i) {
               this.annotations[i].traverse(visitor, scope);
            }
         }

         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }
      }

      visitor.endVisit(this, scope);
   }
}
