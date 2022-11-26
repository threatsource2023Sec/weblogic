package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MemberTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;
import java.util.List;

public class MethodDeclaration extends AbstractMethodDeclaration {
   public TypeReference returnType;
   public TypeParameter[] typeParameters;

   public MethodDeclaration(CompilationResult compilationResult) {
      super(compilationResult);
      this.bits |= 256;
   }

   public void analyseCode(ClassScope classScope, FlowContext flowContext, FlowInfo flowInfo) {
      if (!this.ignoreFurtherInvestigation) {
         try {
            if (this.binding == null) {
               return;
            }

            if (!this.binding.isUsed() && !this.binding.isAbstract() && (this.binding.isPrivate() || (this.binding.modifiers & 805306368) == 0 && this.binding.isOrEnclosedByPrivateType()) && !classScope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
               this.scope.problemReporter().unusedPrivateMethod(this);
            }

            if (this.binding.declaringClass.isEnum() && (this.selector == TypeConstants.VALUES || this.selector == TypeConstants.VALUEOF)) {
               return;
            }

            if (this.binding.isAbstract() || this.binding.isNative()) {
               return;
            }

            if (this.typeParameters != null && !this.scope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
               int i = 0;

               for(int length = this.typeParameters.length; i < length; ++i) {
                  TypeParameter typeParameter = this.typeParameters[i];
                  if ((typeParameter.binding.modifiers & 134217728) == 0) {
                     this.scope.problemReporter().unusedTypeParameter(typeParameter);
                  }
               }
            }

            ExceptionHandlingFlowContext methodContext = new ExceptionHandlingFlowContext(flowContext, this, this.binding.thrownExceptions, (FlowContext)null, this.scope, FlowInfo.DEAD_END);
            analyseArguments(classScope.environment(), flowInfo, this.arguments, this.binding);
            if (this.binding.declaringClass instanceof MemberTypeBinding && !this.binding.declaringClass.isStatic()) {
               this.bits &= -257;
            }

            if (this.statements != null) {
               boolean enableSyntacticNullAnalysisForFields = this.scope.compilerOptions().enableSyntacticNullAnalysisForFields;
               int complaintLevel = (flowInfo.reachMode() & 3) == 0 ? 0 : 1;
               int i = 0;

               for(int count = this.statements.length; i < count; ++i) {
                  Statement stat = this.statements[i];
                  if ((complaintLevel = stat.complainIfUnreachable(flowInfo, this.scope, complaintLevel, true)) < 2) {
                     flowInfo = stat.analyseCode(this.scope, methodContext, flowInfo);
                  }

                  if (enableSyntacticNullAnalysisForFields) {
                     methodContext.expireNullCheckedFieldInfo();
                  }
               }
            } else {
               this.bits &= -257;
            }

            TypeBinding returnTypeBinding = this.binding.returnType;
            if (returnTypeBinding != TypeBinding.VOID && !this.isAbstract()) {
               if (flowInfo != FlowInfo.DEAD_END) {
                  this.scope.problemReporter().shouldReturn(returnTypeBinding, this);
               }
            } else if ((flowInfo.tagBits & 1) == 0) {
               this.bits |= 64;
            }

            methodContext.complainIfUnusedExceptionHandlers(this);
            this.scope.checkUnusedParameters(this.binding);
            if (!this.binding.isStatic() && (this.bits & 256) != 0 && !this.isDefaultMethod() && !this.binding.isOverriding() && !this.binding.isImplementing()) {
               if (!this.binding.isPrivate() && !this.binding.isFinal() && !this.binding.declaringClass.isFinal()) {
                  this.scope.problemReporter().methodCanBePotentiallyDeclaredStatic(this);
               } else {
                  this.scope.problemReporter().methodCanBeDeclaredStatic(this);
               }
            }

            this.scope.checkUnclosedCloseables(flowInfo, (FlowContext)null, (ASTNode)null, (BlockScope)null);
         } catch (AbortMethod var10) {
            this.ignoreFurtherInvestigation = true;
         }

      }
   }

   public void getAllAnnotationContexts(int targetType, List allAnnotationContexts) {
      TypeReference.AnnotationCollector collector = new TypeReference.AnnotationCollector(this.returnType, targetType, allAnnotationContexts);
      int i = 0;

      for(int max = this.annotations.length; i < max; ++i) {
         Annotation annotation = this.annotations[i];
         annotation.traverse(collector, (BlockScope)null);
      }

   }

   public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition position) {
      return TypeReference.containsNullAnnotation(this.annotations) || this.returnType != null && this.returnType.hasNullTypeAnnotation(position);
   }

   public boolean isDefaultMethod() {
      return (this.modifiers & 65536) != 0;
   }

   public boolean isMethod() {
      return true;
   }

   public void parseStatements(Parser parser, CompilationUnitDeclaration unit) {
      parser.parse(this, unit);
   }

   public StringBuffer printReturnType(int indent, StringBuffer output) {
      return this.returnType == null ? output : this.returnType.printExpression(0, output).append(' ');
   }

   public void resolveStatements() {
      // $FF: Couldn't be decompiled
   }

   public void traverse(ASTVisitor visitor, ClassScope classScope) {
      if (visitor.visit(this, classScope)) {
         if (this.javadoc != null) {
            this.javadoc.traverse(visitor, (BlockScope)this.scope);
         }

         int statementsLength;
         int i;
         if (this.annotations != null) {
            statementsLength = this.annotations.length;

            for(i = 0; i < statementsLength; ++i) {
               this.annotations[i].traverse(visitor, (BlockScope)this.scope);
            }
         }

         if (this.typeParameters != null) {
            statementsLength = this.typeParameters.length;

            for(i = 0; i < statementsLength; ++i) {
               this.typeParameters[i].traverse(visitor, (BlockScope)this.scope);
            }
         }

         if (this.returnType != null) {
            this.returnType.traverse(visitor, (BlockScope)this.scope);
         }

         if (this.arguments != null) {
            statementsLength = this.arguments.length;

            for(i = 0; i < statementsLength; ++i) {
               this.arguments[i].traverse(visitor, (BlockScope)this.scope);
            }
         }

         if (this.thrownExceptions != null) {
            statementsLength = this.thrownExceptions.length;

            for(i = 0; i < statementsLength; ++i) {
               this.thrownExceptions[i].traverse(visitor, (BlockScope)this.scope);
            }
         }

         if (this.statements != null) {
            statementsLength = this.statements.length;

            for(i = 0; i < statementsLength; ++i) {
               this.statements[i].traverse(visitor, this.scope);
            }
         }
      }

      visitor.endVisit(this, classScope);
   }

   public TypeParameter[] typeParameters() {
      return this.typeParameters;
   }
}
