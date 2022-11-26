package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import java.util.List;

public class TypeParameter extends AbstractVariableDeclaration {
   public TypeVariableBinding binding;
   public TypeReference[] bounds;

   public int getKind() {
      return 6;
   }

   public void checkBounds(Scope scope) {
      if (this.type != null) {
         this.type.checkBounds(scope);
      }

      if (this.bounds != null) {
         int i = 0;

         for(int length = this.bounds.length; i < length; ++i) {
            this.bounds[i].checkBounds(scope);
         }
      }

   }

   public void getAllAnnotationContexts(int targetType, int typeParameterIndex, List allAnnotationContexts) {
      TypeReference.AnnotationCollector collector = new TypeReference.AnnotationCollector(this, targetType, typeParameterIndex, allAnnotationContexts);
      int boundIndex;
      int boundsLength;
      if (this.annotations != null) {
         boundIndex = this.annotations.length;

         for(boundsLength = 0; boundsLength < boundIndex; ++boundsLength) {
            this.annotations[boundsLength].traverse(collector, (BlockScope)null);
         }
      }

      switch (collector.targetType) {
         case 0:
            collector.targetType = 17;
            break;
         case 1:
            collector.targetType = 18;
      }

      boundIndex = 0;
      if (this.type != null) {
         if (this.type.resolvedType.isInterface()) {
            boundIndex = 1;
         }

         if ((this.type.bits & 1048576) != 0) {
            collector.info2 = boundIndex;
            this.type.traverse(collector, (BlockScope)null);
         }
      }

      if (this.bounds != null) {
         boundsLength = this.bounds.length;

         for(int i = 0; i < boundsLength; ++i) {
            TypeReference bound = this.bounds[i];
            if ((bound.bits & 1048576) != 0) {
               ++boundIndex;
               collector.info2 = boundIndex;
               bound.traverse(collector, (BlockScope)null);
            }
         }
      }

   }

   private void internalResolve(Scope scope, boolean staticContext) {
      if (this.binding != null) {
         Binding existingType = scope.parent.getBinding(this.name, 4, this, false);
         if (existingType != null && this.binding != existingType && existingType.isValidBinding() && (existingType.kind() != 4100 || !staticContext)) {
            scope.problemReporter().typeHiding(this, existingType);
         }
      }

      if (this.annotations != null || scope.environment().usesNullTypeAnnotations()) {
         this.resolveAnnotations(scope);
      }

      if (CharOperation.equals(this.name, TypeConstants.VAR)) {
         if (scope.compilerOptions().sourceLevel < 3538944L) {
            scope.problemReporter().varIsReservedTypeNameInFuture(this);
         } else {
            scope.problemReporter().varIsNotAllowedHere(this);
         }
      }

   }

   public void resolve(BlockScope scope) {
      this.internalResolve(scope, scope.methodScope().isStatic);
   }

   public void resolve(ClassScope scope) {
      this.internalResolve(scope, scope.enclosingSourceType().isStatic());
   }

   public void resolveAnnotations(Scope scope) {
      BlockScope resolutionScope = Scope.typeAnnotationsResolutionScope(scope);
      if (resolutionScope != null) {
         AnnotationBinding[] annotationBindings = resolveAnnotations(resolutionScope, this.annotations, this.binding, false);
         LookupEnvironment environment = scope.environment();
         boolean isAnnotationBasedNullAnalysisEnabled = environment.globalOptions.isAnnotationBasedNullAnalysisEnabled;
         if (annotationBindings != null && annotationBindings.length > 0) {
            this.binding.setTypeAnnotations(annotationBindings, isAnnotationBasedNullAnalysisEnabled);
            scope.referenceCompilationUnit().compilationResult.hasAnnotations = true;
         }

         if (isAnnotationBasedNullAnalysisEnabled && this.binding != null && this.binding.isValidBinding()) {
            if (!this.binding.hasNullTypeAnnotations() && scope.hasDefaultNullnessFor(128, this.sourceStart())) {
               AnnotationBinding[] annots = new AnnotationBinding[]{environment.getNonNullAnnotation()};
               TypeVariableBinding previousBinding = this.binding;
               this.binding = (TypeVariableBinding)environment.createAnnotatedType(this.binding, (AnnotationBinding[])annots);
               if (scope instanceof MethodScope) {
                  MethodScope methodScope = (MethodScope)scope;
                  if (methodScope.referenceContext instanceof AbstractMethodDeclaration) {
                     MethodBinding methodBinding = ((AbstractMethodDeclaration)methodScope.referenceContext).binding;
                     if (methodBinding != null) {
                        methodBinding.updateTypeVariableBinding(previousBinding, this.binding);
                     }
                  }
               }
            }

            this.binding.evaluateNullAnnotations(scope, this);
         }
      }

   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      if (this.annotations != null) {
         printAnnotations(this.annotations, output);
         output.append(' ');
      }

      output.append(this.name);
      if (this.type != null) {
         output.append(" extends ");
         this.type.print(0, output);
      }

      if (this.bounds != null) {
         for(int i = 0; i < this.bounds.length; ++i) {
            output.append(" & ");
            this.bounds[i].print(0, output);
         }
      }

      return output;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         int boundsLength;
         int i;
         if (this.annotations != null) {
            boundsLength = this.annotations.length;

            for(i = 0; i < boundsLength; ++i) {
               this.annotations[i].traverse(visitor, scope);
            }
         }

         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }

         if (this.bounds != null) {
            boundsLength = this.bounds.length;

            for(i = 0; i < boundsLength; ++i) {
               this.bounds[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      if (visitor.visit(this, scope)) {
         int boundsLength;
         int i;
         if (this.annotations != null) {
            boundsLength = this.annotations.length;

            for(i = 0; i < boundsLength; ++i) {
               this.annotations[i].traverse(visitor, scope);
            }
         }

         if (this.type != null) {
            this.type.traverse(visitor, scope);
         }

         if (this.bounds != null) {
            boundsLength = this.bounds.length;

            for(i = 0; i < boundsLength; ++i) {
               this.bounds[i].traverse(visitor, scope);
            }
         }
      }

      visitor.endVisit(this, scope);
   }
}
