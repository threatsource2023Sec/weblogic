package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public abstract class Reference extends Expression {
   public abstract FlowInfo analyseAssignment(BlockScope var1, FlowContext var2, FlowInfo var3, Assignment var4, boolean var5);

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      return flowInfo;
   }

   public boolean checkNPE(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, int ttlForFieldCheck) {
      return flowContext.isNullcheckedFieldAccess(this) ? true : super.checkNPE(scope, flowContext, flowInfo, ttlForFieldCheck);
   }

   protected boolean checkNullableFieldDereference(Scope scope, FieldBinding field, long sourcePosition, FlowContext flowContext, int ttlForFieldCheck) {
      if (field != null) {
         if (ttlForFieldCheck > 0 && scope.compilerOptions().enableSyntacticNullAnalysisForFields) {
            flowContext.recordNullCheckedFieldReference(this, ttlForFieldCheck);
         }

         if ((field.type.tagBits & 36028797018963968L) != 0L) {
            scope.problemReporter().dereferencingNullableExpression(sourcePosition, scope.environment());
            return true;
         }

         if (field.type.isFreeTypeVariable()) {
            scope.problemReporter().fieldFreeTypeVariableReference(field, sourcePosition);
            return true;
         }

         if ((field.tagBits & 36028797018963968L) != 0L) {
            scope.problemReporter().nullableFieldDereference(field, sourcePosition);
            return true;
         }
      }

      return false;
   }

   public FieldBinding fieldBinding() {
      return null;
   }

   public void fieldStore(Scope currentScope, CodeStream codeStream, FieldBinding fieldBinding, MethodBinding syntheticWriteAccessor, TypeBinding receiverType, boolean isImplicitThisReceiver, boolean valueRequired) {
      int pc = codeStream.position;
      TypeBinding constantPoolDeclaringClass;
      if (fieldBinding.isStatic()) {
         if (valueRequired) {
            switch (fieldBinding.type.id) {
               case 7:
               case 8:
                  codeStream.dup2();
                  break;
               default:
                  codeStream.dup();
            }
         }

         if (syntheticWriteAccessor == null) {
            constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(currentScope, fieldBinding, receiverType, isImplicitThisReceiver);
            codeStream.fieldAccess((byte)-77, fieldBinding, constantPoolDeclaringClass);
         } else {
            codeStream.invoke((byte)-72, syntheticWriteAccessor, (TypeBinding)null);
         }
      } else {
         if (valueRequired) {
            switch (fieldBinding.type.id) {
               case 7:
               case 8:
                  codeStream.dup2_x1();
                  break;
               default:
                  codeStream.dup_x1();
            }
         }

         if (syntheticWriteAccessor == null) {
            constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(currentScope, fieldBinding, receiverType, isImplicitThisReceiver);
            codeStream.fieldAccess((byte)-75, fieldBinding, constantPoolDeclaringClass);
         } else {
            codeStream.invoke((byte)-72, syntheticWriteAccessor, (TypeBinding)null);
         }
      }

      codeStream.recordPositionsFrom(pc, this.sourceStart);
   }

   public abstract void generateAssignment(BlockScope var1, CodeStream var2, Assignment var3, boolean var4);

   public abstract void generateCompoundAssignment(BlockScope var1, CodeStream var2, Expression var3, int var4, int var5, boolean var6);

   public abstract void generatePostIncrement(BlockScope var1, CodeStream var2, CompoundAssignment var3, boolean var4);

   public boolean isEquivalent(Reference reference) {
      return false;
   }

   public FieldBinding lastFieldBinding() {
      return null;
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      if ((this.implicitConversion & 512) != 0) {
         return 4;
      } else {
         FieldBinding fieldBinding = this.lastFieldBinding();
         if (fieldBinding != null) {
            if (fieldBinding.isNonNull() || flowContext.isNullcheckedFieldAccess(this)) {
               return 4;
            }

            if (fieldBinding.isNullable()) {
               return 16;
            }

            if (fieldBinding.type.isFreeTypeVariable()) {
               return 48;
            }
         }

         return this.resolvedType != null ? FlowInfo.tagBitsToNullStatus(this.resolvedType.tagBits) : 1;
      }
   }

   void reportOnlyUselesslyReadPrivateField(BlockScope currentScope, FieldBinding fieldBinding, boolean valueRequired) {
      if (valueRequired) {
         fieldBinding.compoundUseFlag = 0;
         fieldBinding.modifiers |= 134217728;
      } else if (fieldBinding.isUsedOnlyInCompound()) {
         --fieldBinding.compoundUseFlag;
         if (fieldBinding.compoundUseFlag == 0 && fieldBinding.isOrEnclosedByPrivateType() && (this.implicitConversion & 1024) == 0) {
            currentScope.problemReporter().unusedPrivateField(fieldBinding.sourceField());
         }
      }

   }

   static void reportOnlyUselesslyReadLocal(BlockScope currentScope, LocalVariableBinding localBinding, boolean valueRequired) {
      if (localBinding.declaration != null) {
         if ((localBinding.declaration.bits & 1073741824) != 0) {
            if (localBinding.useFlag < 1) {
               if (valueRequired) {
                  localBinding.useFlag = 1;
               } else {
                  ++localBinding.useFlag;
                  if (localBinding.useFlag == 0) {
                     if (localBinding.declaration instanceof Argument) {
                        MethodScope methodScope = currentScope.methodScope();
                        if (methodScope != null && !methodScope.isLambdaScope()) {
                           MethodBinding method = ((AbstractMethodDeclaration)methodScope.referenceContext()).binding;
                           boolean shouldReport = !method.isMain();
                           if (method.isImplementing()) {
                              shouldReport &= currentScope.compilerOptions().reportUnusedParameterWhenImplementingAbstract;
                           } else if (method.isOverriding()) {
                              shouldReport &= currentScope.compilerOptions().reportUnusedParameterWhenOverridingConcrete;
                           }

                           if (shouldReport) {
                              currentScope.problemReporter().unusedArgument(localBinding.declaration);
                           }
                        }
                     } else {
                        currentScope.problemReporter().unusedLocalVariable(localBinding.declaration);
                     }

                  }
               }
            }
         }
      }
   }
}
