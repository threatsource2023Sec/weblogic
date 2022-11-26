package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;

public class ClassLiteralAccess extends Expression {
   public TypeReference type;
   public TypeBinding targetType;
   FieldBinding syntheticField;

   public ClassLiteralAccess(int sourceEnd, TypeReference type) {
      this.type = type;
      type.bits |= 1073741824;
      this.sourceStart = type.sourceStart;
      this.sourceEnd = sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      SourceTypeBinding sourceType = currentScope.outerMostClassScope().enclosingSourceType();
      if (!sourceType.isInterface() && !this.targetType.isBaseType() && currentScope.compilerOptions().targetJDK < 3211264L) {
         this.syntheticField = sourceType.addSyntheticFieldForClassLiteral(this.targetType, currentScope);
      }

      return flowInfo;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      if (valueRequired) {
         codeStream.generateClassLiteralAccessForType(this.type.resolvedType, this.syntheticField);
         codeStream.generateImplicitConversion(this.implicitConversion);
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      return this.type.print(0, output).append(".class");
   }

   public TypeBinding resolveType(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      if ((this.targetType = this.type.resolveType(scope, true)) == null) {
         return null;
      } else {
         LookupEnvironment environment = scope.environment();
         this.targetType = environment.convertToRawType(this.targetType, true);
         TypeBinding leafComponentType;
         if (this.targetType.isArrayType()) {
            ArrayBinding arrayBinding = (ArrayBinding)this.targetType;
            leafComponentType = arrayBinding.leafComponentType;
            if (leafComponentType == TypeBinding.VOID) {
               scope.problemReporter().cannotAllocateVoidArray(this);
               return null;
            }

            if (leafComponentType.isTypeVariable()) {
               scope.problemReporter().illegalClassLiteralForTypeVariable((TypeVariableBinding)leafComponentType, this);
            }
         } else if (this.targetType.isTypeVariable()) {
            scope.problemReporter().illegalClassLiteralForTypeVariable((TypeVariableBinding)this.targetType, this);
         }

         ReferenceBinding classType = scope.getJavaLangClass();
         if (scope.compilerOptions().sourceLevel >= 3211264L) {
            leafComponentType = null;
            Object boxedType;
            if (this.targetType.id == 6) {
               boxedType = environment.getResolvedJavaBaseType(JAVA_LANG_VOID, scope);
            } else {
               boxedType = scope.boxing(this.targetType);
            }

            if (environment.usesNullTypeAnnotations()) {
               boxedType = environment.createAnnotatedType((TypeBinding)boxedType, (AnnotationBinding[])(new AnnotationBinding[]{environment.getNonNullAnnotation()}));
            }

            this.resolvedType = environment.createParameterizedType(classType, new TypeBinding[]{(TypeBinding)boxedType}, (ReferenceBinding)null);
         } else {
            this.resolvedType = classType;
         }

         return this.resolvedType;
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.type.traverse(visitor, blockScope);
      }

      visitor.endVisit(this, blockScope);
   }
}
