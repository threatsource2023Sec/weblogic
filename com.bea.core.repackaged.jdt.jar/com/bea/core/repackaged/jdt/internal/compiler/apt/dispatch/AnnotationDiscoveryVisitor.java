package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.ElementImpl;
import com.bea.core.repackaged.jdt.internal.compiler.apt.model.Factory;
import com.bea.core.repackaged.jdt.internal.compiler.apt.util.ManyToMany;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Argument;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ConstructorDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AptSourceLocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CompilationUnitScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import javax.lang.model.element.TypeElement;

public class AnnotationDiscoveryVisitor extends ASTVisitor {
   final BaseProcessingEnvImpl _env;
   final Factory _factory;
   final ManyToMany _annoToElement;

   public AnnotationDiscoveryVisitor(BaseProcessingEnvImpl env) {
      this._env = env;
      this._factory = env.getFactory();
      this._annoToElement = new ManyToMany();
   }

   public boolean visit(Argument argument, BlockScope scope) {
      Annotation[] annotations = argument.annotations;
      ReferenceContext referenceContext = scope.referenceContext();
      if (referenceContext instanceof AbstractMethodDeclaration) {
         MethodBinding binding = ((AbstractMethodDeclaration)referenceContext).binding;
         if (binding != null) {
            TypeDeclaration typeDeclaration = scope.referenceType();
            typeDeclaration.binding.resolveTypesFor(binding);
            if (argument.binding != null) {
               argument.binding = new AptSourceLocalVariableBinding(argument.binding, binding);
            }
         }

         if (annotations != null && argument.binding != null) {
            this.resolveAnnotations(scope, annotations, argument.binding);
         }
      }

      return false;
   }

   public boolean visit(ConstructorDeclaration constructorDeclaration, ClassScope scope) {
      Annotation[] annotations = constructorDeclaration.annotations;
      if (annotations != null) {
         MethodBinding constructorBinding = constructorDeclaration.binding;
         if (constructorBinding == null) {
            return false;
         }

         ((SourceTypeBinding)constructorBinding.declaringClass).resolveTypesFor(constructorBinding);
         this.resolveAnnotations(constructorDeclaration.scope, annotations, constructorBinding);
      }

      TypeParameter[] typeParameters = constructorDeclaration.typeParameters;
      int argumentLength;
      if (typeParameters != null) {
         int typeParametersLength = typeParameters.length;

         for(argumentLength = 0; argumentLength < typeParametersLength; ++argumentLength) {
            typeParameters[argumentLength].traverse(this, (BlockScope)constructorDeclaration.scope);
         }
      }

      Argument[] arguments = constructorDeclaration.arguments;
      if (arguments != null) {
         argumentLength = arguments.length;

         for(int i = 0; i < argumentLength; ++i) {
            arguments[i].traverse(this, (BlockScope)constructorDeclaration.scope);
         }
      }

      return false;
   }

   public boolean visit(FieldDeclaration fieldDeclaration, MethodScope scope) {
      Annotation[] annotations = fieldDeclaration.annotations;
      if (annotations != null) {
         FieldBinding fieldBinding = fieldDeclaration.binding;
         if (fieldBinding == null) {
            return false;
         }

         ((SourceTypeBinding)fieldBinding.declaringClass).resolveTypeFor(fieldBinding);
         if (fieldDeclaration.binding == null) {
            return false;
         }

         this.resolveAnnotations(scope, annotations, fieldBinding);
      }

      return false;
   }

   public boolean visit(TypeParameter typeParameter, ClassScope scope) {
      Annotation[] annotations = typeParameter.annotations;
      if (annotations != null) {
         TypeVariableBinding binding = typeParameter.binding;
         if (binding == null) {
            return false;
         }

         this.resolveAnnotations(scope.referenceContext.initializerScope, annotations, binding);
      }

      return false;
   }

   public boolean visit(TypeParameter typeParameter, BlockScope scope) {
      Annotation[] annotations = typeParameter.annotations;
      if (annotations != null) {
         TypeVariableBinding binding = typeParameter.binding;
         if (binding == null) {
            return false;
         }

         MethodBinding methodBinding = (MethodBinding)binding.declaringElement;
         ((SourceTypeBinding)methodBinding.declaringClass).resolveTypesFor(methodBinding);
         this.resolveAnnotations(scope, annotations, binding);
      }

      return false;
   }

   public boolean visit(MethodDeclaration methodDeclaration, ClassScope scope) {
      Annotation[] annotations = methodDeclaration.annotations;
      if (annotations != null) {
         MethodBinding methodBinding = methodDeclaration.binding;
         if (methodBinding == null) {
            return false;
         }

         ((SourceTypeBinding)methodBinding.declaringClass).resolveTypesFor(methodBinding);
         this.resolveAnnotations(methodDeclaration.scope, annotations, methodBinding);
      }

      TypeParameter[] typeParameters = methodDeclaration.typeParameters;
      int argumentLength;
      if (typeParameters != null) {
         int typeParametersLength = typeParameters.length;

         for(argumentLength = 0; argumentLength < typeParametersLength; ++argumentLength) {
            typeParameters[argumentLength].traverse(this, (BlockScope)methodDeclaration.scope);
         }
      }

      Argument[] arguments = methodDeclaration.arguments;
      if (arguments != null) {
         argumentLength = arguments.length;

         for(int i = 0; i < argumentLength; ++i) {
            arguments[i].traverse(this, (BlockScope)methodDeclaration.scope);
         }
      }

      return false;
   }

   public boolean visit(TypeDeclaration memberTypeDeclaration, ClassScope scope) {
      SourceTypeBinding binding = memberTypeDeclaration.binding;
      if (binding == null) {
         return false;
      } else {
         Annotation[] annotations = memberTypeDeclaration.annotations;
         if (annotations != null) {
            this.resolveAnnotations(memberTypeDeclaration.staticInitializerScope, annotations, binding);
         }

         return true;
      }
   }

   public boolean visit(TypeDeclaration typeDeclaration, CompilationUnitScope scope) {
      SourceTypeBinding binding = typeDeclaration.binding;
      if (binding == null) {
         return false;
      } else {
         Annotation[] annotations = typeDeclaration.annotations;
         if (annotations != null) {
            this.resolveAnnotations(typeDeclaration.staticInitializerScope, annotations, binding);
         }

         return true;
      }
   }

   public boolean visit(ModuleDeclaration module, CompilationUnitScope scope) {
      ModuleBinding binding = module.binding;
      if (binding == null) {
         return false;
      } else {
         module.resolveTypeDirectives(scope);
         return true;
      }
   }

   private void resolveAnnotations(BlockScope scope, Annotation[] annotations, Binding currentBinding) {
      int length = annotations == null ? 0 : annotations.length;
      if (length != 0) {
         boolean old = scope.insideTypeAnnotation;
         scope.insideTypeAnnotation = true;
         currentBinding.getAnnotationTagBits();
         scope.insideTypeAnnotation = old;
         ElementImpl element = (ElementImpl)this._factory.newElement(currentBinding);
         AnnotationBinding[] annotationBindings = element.getPackedAnnotationBindings();
         AnnotationBinding[] var11 = annotationBindings;
         int var10 = annotationBindings.length;

         for(int var9 = 0; var9 < var10; ++var9) {
            AnnotationBinding binding = var11[var9];
            ReferenceBinding annotationType = binding.getAnnotationType();
            if (binding != null && Annotation.isAnnotationTargetAllowed(scope, annotationType, currentBinding)) {
               TypeElement anno = (TypeElement)this._factory.newElement(annotationType);
               this._annoToElement.put(anno, element);
            }
         }

      }
   }
}
