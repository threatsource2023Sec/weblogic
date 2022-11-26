package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MissingTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemFieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;

public class SingleNameReference extends NameReference implements OperatorIds {
   public static final int READ = 0;
   public static final int WRITE = 1;
   public char[] token;
   public MethodBinding[] syntheticAccessors;
   public TypeBinding genericCast;
   public boolean isLabel;

   public SingleNameReference(char[] source, long pos) {
      this.token = source;
      this.sourceStart = (int)(pos >>> 32);
      this.sourceEnd = (int)pos;
   }

   public FlowInfo analyseAssignment(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo, Assignment assignment, boolean isCompound) {
      boolean isReachable = (((FlowInfo)flowInfo).tagBits & 3) == 0;
      FieldBinding fieldBinding;
      LocalVariableBinding localBinding;
      if (isCompound) {
         switch (this.bits & 7) {
            case 1:
               fieldBinding = (FieldBinding)this.binding;
               if (fieldBinding.isBlankFinal() && currentScope.needBlankFinalFieldInitializationCheck(fieldBinding)) {
                  FlowInfo fieldInits = flowContext.getInitsForFinalBlankInitializationCheck(fieldBinding.declaringClass.original(), (FlowInfo)flowInfo);
                  if (!fieldInits.isDefinitelyAssigned(fieldBinding)) {
                     currentScope.problemReporter().uninitializedBlankFinalField(fieldBinding, this);
                  }
               }

               this.manageSyntheticAccessIfNecessary(currentScope, (FlowInfo)flowInfo, true);
               break;
            case 2:
               if (!((FlowInfo)flowInfo).isDefinitelyAssigned(localBinding = (LocalVariableBinding)this.binding)) {
                  currentScope.problemReporter().uninitializedLocalVariable(localBinding, this, currentScope);
               }

               if (localBinding.useFlag != 1) {
                  if (isReachable && (this.implicitConversion & 1024) != 0) {
                     localBinding.useFlag = 1;
                  } else if (localBinding.useFlag <= 0) {
                     --localBinding.useFlag;
                  }
               }
         }
      }

      if (assignment.expression != null) {
         flowInfo = assignment.expression.analyseCode(currentScope, flowContext, (FlowInfo)flowInfo).unconditionalInits();
      }

      switch (this.bits & 7) {
         case 1:
            this.manageSyntheticAccessIfNecessary(currentScope, (FlowInfo)flowInfo, false);
            fieldBinding = (FieldBinding)this.binding;
            if (fieldBinding.isFinal()) {
               if (!isCompound && fieldBinding.isBlankFinal() && currentScope.allowBlankFinalFieldAssignment(fieldBinding)) {
                  if (((FlowInfo)flowInfo).isPotentiallyAssigned(fieldBinding)) {
                     currentScope.problemReporter().duplicateInitializationOfBlankFinalField(fieldBinding, this);
                  } else {
                     flowContext.recordSettingFinal(fieldBinding, this, (FlowInfo)flowInfo);
                  }

                  ((FlowInfo)flowInfo).markAsDefinitelyAssigned(fieldBinding);
               } else {
                  currentScope.problemReporter().cannotAssignToFinalField(fieldBinding, this);
               }
            } else if (!isCompound && (fieldBinding.isNonNull() || fieldBinding.type.isTypeVariable()) && TypeBinding.equalsEquals(fieldBinding.declaringClass, currentScope.enclosingReceiverType())) {
               ((FlowInfo)flowInfo).markAsDefinitelyAssigned(fieldBinding);
            }
            break;
         case 2:
            localBinding = (LocalVariableBinding)this.binding;
            boolean isFinal = localBinding.isFinal();
            if (!((FlowInfo)flowInfo).isDefinitelyAssigned(localBinding)) {
               this.bits |= 8;
            } else {
               this.bits &= -9;
            }

            if (((FlowInfo)flowInfo).isPotentiallyAssigned(localBinding) || (this.bits & 524288) != 0) {
               localBinding.tagBits &= -2049L;
               if (!isFinal && (this.bits & 524288) != 0) {
                  currentScope.problemReporter().cannotReferToNonEffectivelyFinalOuterLocal(localBinding, this);
               }
            }

            if (!isFinal && (localBinding.tagBits & 2048L) != 0L && (localBinding.tagBits & 1024L) == 0L) {
               flowContext.recordSettingFinal(localBinding, this, (FlowInfo)flowInfo);
            } else if (isFinal) {
               if ((this.bits & 8160) != 0) {
                  currentScope.problemReporter().cannotAssignToFinalOuterLocal(localBinding, this);
               } else if ((!isReachable || !isCompound) && localBinding.isBlankFinal()) {
                  if (((FlowInfo)flowInfo).isPotentiallyAssigned(localBinding)) {
                     currentScope.problemReporter().duplicateInitializationOfFinalLocal(localBinding, this);
                  } else if ((this.bits & 524288) != 0) {
                     currentScope.problemReporter().cannotAssignToFinalOuterLocal(localBinding, this);
                  } else {
                     flowContext.recordSettingFinal(localBinding, this, (FlowInfo)flowInfo);
                  }
               } else {
                  currentScope.problemReporter().cannotAssignToFinalLocal(localBinding, this);
               }
            } else if ((localBinding.tagBits & 1024L) != 0L) {
               currentScope.problemReporter().parameterAssignment(localBinding, this);
            }

            ((FlowInfo)flowInfo).markAsDefinitelyAssigned(localBinding);
      }

      this.manageEnclosingInstanceAccessIfNecessary(currentScope, (FlowInfo)flowInfo);
      return (FlowInfo)flowInfo;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      return this.analyseCode(currentScope, flowContext, flowInfo, true);
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo, boolean valueRequired) {
      switch (this.bits & 7) {
         case 1:
            if (valueRequired || currentScope.compilerOptions().complianceLevel >= 3145728L) {
               this.manageSyntheticAccessIfNecessary(currentScope, flowInfo, true);
            }

            FieldBinding fieldBinding = (FieldBinding)this.binding;
            if (fieldBinding.isBlankFinal() && currentScope.needBlankFinalFieldInitializationCheck(fieldBinding)) {
               FlowInfo fieldInits = flowContext.getInitsForFinalBlankInitializationCheck(fieldBinding.declaringClass.original(), flowInfo);
               if (!fieldInits.isDefinitelyAssigned(fieldBinding)) {
                  currentScope.problemReporter().uninitializedBlankFinalField(fieldBinding, this);
               }
            }
            break;
         case 2:
            LocalVariableBinding localBinding;
            if (!flowInfo.isDefinitelyAssigned(localBinding = (LocalVariableBinding)this.binding)) {
               currentScope.problemReporter().uninitializedLocalVariable(localBinding, this, currentScope);
            }

            if ((flowInfo.tagBits & 3) == 0) {
               localBinding.useFlag = 1;
            } else if (localBinding.useFlag == 0) {
               localBinding.useFlag = 2;
            }
      }

      if (valueRequired) {
         this.manageEnclosingInstanceAccessIfNecessary(currentScope, flowInfo);
      }

      return flowInfo;
   }

   public TypeBinding checkFieldAccess(BlockScope scope) {
      FieldBinding fieldBinding = (FieldBinding)this.binding;
      this.constant = fieldBinding.constant(scope);
      this.bits &= -8;
      this.bits |= 1;
      MethodScope methodScope = scope.methodScope();
      if (fieldBinding.isStatic()) {
         ReferenceBinding declaringClass = fieldBinding.declaringClass;
         if (declaringClass.isEnum() && !scope.isModuleScope()) {
            SourceTypeBinding sourceType = scope.enclosingSourceType();
            if (this.constant == Constant.NotAConstant && !methodScope.isStatic && (TypeBinding.equalsEquals(sourceType, declaringClass) || TypeBinding.equalsEquals(sourceType.superclass, declaringClass)) && methodScope.isInsideInitializerOrConstructor()) {
               scope.problemReporter().enumStaticFieldUsedDuringInitialization(fieldBinding, this);
            }
         }
      } else {
         if (scope.compilerOptions().getSeverity(4194304) != 256) {
            scope.problemReporter().unqualifiedFieldAccess(this, fieldBinding);
         }

         if (methodScope.isStatic) {
            scope.problemReporter().staticFieldAccessToNonStaticVariable(this, fieldBinding);
            return fieldBinding.type;
         }

         scope.tagAsAccessingEnclosingInstanceStateOf(fieldBinding.declaringClass, false);
      }

      if (this.isFieldUseDeprecated(fieldBinding, scope, this.bits)) {
         scope.problemReporter().deprecatedField(fieldBinding, this);
      }

      if ((this.bits & 8192) == 0 && TypeBinding.equalsEquals(methodScope.enclosingSourceType(), fieldBinding.original().declaringClass) && methodScope.lastVisibleFieldID >= 0 && fieldBinding.id >= methodScope.lastVisibleFieldID && (!fieldBinding.isStatic() || methodScope.isStatic)) {
         scope.problemReporter().forwardReference(this, 0, fieldBinding);
         this.bits |= 536870912;
      }

      return fieldBinding.type;
   }

   public boolean checkNPE(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, int ttlForFieldCheck) {
      if (!super.checkNPE(scope, flowContext, flowInfo, ttlForFieldCheck)) {
         CompilerOptions compilerOptions = scope.compilerOptions();
         if (compilerOptions.isAnnotationBasedNullAnalysisEnabled && this.binding instanceof FieldBinding) {
            return this.checkNullableFieldDereference(scope, (FieldBinding)this.binding, ((long)this.sourceStart << 32) + (long)this.sourceEnd, flowContext, ttlForFieldCheck);
         }
      }

      return false;
   }

   public void computeConversion(Scope scope, TypeBinding runtimeTimeType, TypeBinding compileTimeType) {
      if (runtimeTimeType != null && compileTimeType != null) {
         if (this.binding != null && this.binding.isValidBinding()) {
            TypeBinding originalType = null;
            if ((this.bits & 1) != 0) {
               FieldBinding field = (FieldBinding)this.binding;
               FieldBinding originalBinding = field.original();
               originalType = originalBinding.type;
            } else if ((this.bits & 2) != 0) {
               LocalVariableBinding local = (LocalVariableBinding)this.binding;
               originalType = local.type;
            }

            if (originalType != null && originalType.leafComponentType().isTypeVariable()) {
               TypeBinding targetType = !compileTimeType.isBaseType() && runtimeTimeType.isBaseType() ? compileTimeType : runtimeTimeType;
               this.genericCast = originalType.genericCast(scope.boxing(targetType));
               if (this.genericCast instanceof ReferenceBinding) {
                  ReferenceBinding referenceCast = (ReferenceBinding)this.genericCast;
                  if (!referenceCast.canBeSeenBy(scope)) {
                     scope.problemReporter().invalidType(this, new ProblemReferenceBinding(CharOperation.splitOn('.', referenceCast.shortReadableName()), referenceCast, 2));
                  }
               }
            }
         }

         super.computeConversion(scope, runtimeTimeType, compileTimeType);
      }
   }

   public void generateAssignment(BlockScope currentScope, CodeStream codeStream, Assignment assignment, boolean valueRequired) {
      if (assignment.expression.isCompactableOperation()) {
         BinaryExpression operation = (BinaryExpression)assignment.expression;
         int operator = (operation.bits & 4032) >> 6;
         SingleNameReference variableReference;
         if (operation.left instanceof SingleNameReference && (variableReference = (SingleNameReference)operation.left).binding == this.binding) {
            variableReference.generateCompoundAssignment(currentScope, codeStream, this.syntheticAccessors == null ? null : this.syntheticAccessors[1], operation.right, operator, operation.implicitConversion, valueRequired);
            if (valueRequired) {
               codeStream.generateImplicitConversion(assignment.implicitConversion);
            }

            return;
         }

         if (operation.right instanceof SingleNameReference && (operator == 14 || operator == 15) && (variableReference = (SingleNameReference)operation.right).binding == this.binding && operation.left.constant != Constant.NotAConstant && (operation.left.implicitConversion & 255) >> 4 != 11 && (operation.right.implicitConversion & 255) >> 4 != 11) {
            variableReference.generateCompoundAssignment(currentScope, codeStream, this.syntheticAccessors == null ? null : this.syntheticAccessors[1], operation.left, operator, operation.implicitConversion, valueRequired);
            if (valueRequired) {
               codeStream.generateImplicitConversion(assignment.implicitConversion);
            }

            return;
         }
      }

      switch (this.bits & 7) {
         case 1:
            int pc = codeStream.position;
            FieldBinding codegenBinding = ((FieldBinding)this.binding).original();
            if (!codegenBinding.isStatic()) {
               if ((this.bits & 8160) != 0) {
                  ReferenceBinding targetType = currentScope.enclosingSourceType().enclosingTypeAt((this.bits & 8160) >> 5);
                  Object[] emulationPath = currentScope.getEmulationPath(targetType, true, false);
                  codeStream.generateOuterAccess(emulationPath, this, targetType, currentScope);
               } else {
                  this.generateReceiver(codeStream);
               }
            }

            codeStream.recordPositionsFrom(pc, this.sourceStart);
            assignment.expression.generateCode(currentScope, codeStream, true);
            this.fieldStore(currentScope, codeStream, codegenBinding, this.syntheticAccessors == null ? null : this.syntheticAccessors[1], this.actualReceiverType, true, valueRequired);
            if (valueRequired) {
               codeStream.generateImplicitConversion(assignment.implicitConversion);
            }

            return;
         case 2:
            LocalVariableBinding localBinding = (LocalVariableBinding)this.binding;
            if (localBinding.resolvedPosition == -1) {
               if (assignment.expression.constant != Constant.NotAConstant) {
                  if (valueRequired) {
                     codeStream.generateConstant(assignment.expression.constant, assignment.implicitConversion);
                  }
               } else {
                  assignment.expression.generateCode(currentScope, codeStream, true);
                  if (valueRequired) {
                     codeStream.generateImplicitConversion(assignment.implicitConversion);
                  } else {
                     switch (localBinding.type.id) {
                        case 7:
                        case 8:
                           codeStream.pop2();
                           break;
                        default:
                           codeStream.pop();
                     }
                  }
               }

               return;
            } else {
               assignment.expression.generateCode(currentScope, codeStream, true);
               if (localBinding.type.isArrayType() && assignment.expression instanceof CastExpression && ((CastExpression)assignment.expression).innermostCastedExpression().resolvedType == TypeBinding.NULL) {
                  codeStream.checkcast(localBinding.type);
               }

               codeStream.store(localBinding, valueRequired);
               if ((this.bits & 8) != 0) {
                  localBinding.recordInitializationStartPC(codeStream.position);
               }

               if (valueRequired) {
                  codeStream.generateImplicitConversion(assignment.implicitConversion);
               }
            }
         default:
      }
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      if (this.constant != Constant.NotAConstant) {
         if (valueRequired) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      } else {
         switch (this.bits & 7) {
            case 1:
               FieldBinding codegenField = ((FieldBinding)this.binding).original();
               Constant fieldConstant = codegenField.constant();
               if (fieldConstant != Constant.NotAConstant) {
                  if (valueRequired) {
                     codeStream.generateConstant(fieldConstant, this.implicitConversion);
                  }

                  codeStream.recordPositionsFrom(pc, this.sourceStart);
                  return;
               }

               TypeBinding constantPoolDeclaringClass;
               if (codegenField.isStatic()) {
                  if (!valueRequired && TypeBinding.equalsEquals(((FieldBinding)this.binding).original().declaringClass, this.actualReceiverType.erasure()) && (this.implicitConversion & 1024) == 0 && this.genericCast == null) {
                     codeStream.recordPositionsFrom(pc, this.sourceStart);
                     return;
                  }

                  if (this.syntheticAccessors != null && this.syntheticAccessors[0] != null) {
                     codeStream.invoke((byte)-72, this.syntheticAccessors[0], (TypeBinding)null);
                  } else {
                     constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(currentScope, (FieldBinding)codegenField, this.actualReceiverType, true);
                     codeStream.fieldAccess((byte)-78, codegenField, constantPoolDeclaringClass);
                  }
               } else {
                  if (!valueRequired && (this.implicitConversion & 1024) == 0 && this.genericCast == null) {
                     codeStream.recordPositionsFrom(pc, this.sourceStart);
                     return;
                  }

                  if ((this.bits & 8160) != 0) {
                     ReferenceBinding targetType = currentScope.enclosingSourceType().enclosingTypeAt((this.bits & 8160) >> 5);
                     Object[] emulationPath = currentScope.getEmulationPath(targetType, true, false);
                     codeStream.generateOuterAccess(emulationPath, this, targetType, currentScope);
                  } else {
                     this.generateReceiver(codeStream);
                  }

                  if (this.syntheticAccessors != null && this.syntheticAccessors[0] != null) {
                     codeStream.invoke((byte)-72, this.syntheticAccessors[0], (TypeBinding)null);
                  } else {
                     constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(currentScope, (FieldBinding)codegenField, this.actualReceiverType, true);
                     codeStream.fieldAccess((byte)-76, codegenField, constantPoolDeclaringClass);
                  }
               }
               break;
            case 2:
               LocalVariableBinding localBinding = (LocalVariableBinding)this.binding;
               if (localBinding.resolvedPosition == -1) {
                  if (valueRequired) {
                     localBinding.useFlag = 1;
                     throw new AbortMethod(CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE, (CategorizedProblem)null);
                  }

                  codeStream.recordPositionsFrom(pc, this.sourceStart);
                  return;
               }

               if (!valueRequired && (this.implicitConversion & 1024) == 0) {
                  codeStream.recordPositionsFrom(pc, this.sourceStart);
                  return;
               }

               if ((this.bits & 524288) != 0) {
                  this.checkEffectiveFinality(localBinding, currentScope);
                  VariableBinding[] path = currentScope.getEmulationPath(localBinding);
                  codeStream.generateOuterAccess(path, this, localBinding, currentScope);
               } else {
                  codeStream.load(localBinding);
               }
               break;
            default:
               codeStream.recordPositionsFrom(pc, this.sourceStart);
               return;
         }

         if (this.genericCast != null) {
            codeStream.checkcast(this.genericCast);
         }

         if (valueRequired) {
            codeStream.generateImplicitConversion(this.implicitConversion);
         } else {
            boolean isUnboxing = (this.implicitConversion & 1024) != 0;
            if (isUnboxing) {
               codeStream.generateImplicitConversion(this.implicitConversion);
            }

            switch (isUnboxing ? this.postConversionType(currentScope).id : this.resolvedType.id) {
               case 7:
               case 8:
                  codeStream.pop2();
                  break;
               default:
                  codeStream.pop();
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public void generateCompoundAssignment(BlockScope currentScope, CodeStream codeStream, Expression expression, int operator, int assignmentImplicitConversion, boolean valueRequired) {
      switch (this.bits & 7) {
         case 1:
            this.reportOnlyUselesslyReadPrivateField(currentScope, (FieldBinding)this.binding, valueRequired);
            break;
         case 2:
            LocalVariableBinding localBinding = (LocalVariableBinding)this.binding;
            Reference.reportOnlyUselesslyReadLocal(currentScope, localBinding, valueRequired);
      }

      this.generateCompoundAssignment(currentScope, codeStream, this.syntheticAccessors == null ? null : this.syntheticAccessors[1], expression, operator, assignmentImplicitConversion, valueRequired);
   }

   public void generateCompoundAssignment(BlockScope currentScope, CodeStream codeStream, MethodBinding writeAccessor, Expression expression, int operator, int assignmentImplicitConversion, boolean valueRequired) {
      switch (this.bits & 7) {
         case 1:
            FieldBinding codegenField = ((FieldBinding)this.binding).original();
            TypeBinding constantPoolDeclaringClass;
            if (codegenField.isStatic()) {
               if (this.syntheticAccessors != null && this.syntheticAccessors[0] != null) {
                  codeStream.invoke((byte)-72, this.syntheticAccessors[0], (TypeBinding)null);
               } else {
                  constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(currentScope, (FieldBinding)codegenField, this.actualReceiverType, true);
                  codeStream.fieldAccess((byte)-78, codegenField, constantPoolDeclaringClass);
               }
            } else {
               if ((this.bits & 8160) != 0) {
                  ReferenceBinding targetType = currentScope.enclosingSourceType().enclosingTypeAt((this.bits & 8160) >> 5);
                  Object[] emulationPath = currentScope.getEmulationPath(targetType, true, false);
                  codeStream.generateOuterAccess(emulationPath, this, targetType, currentScope);
               } else {
                  codeStream.aload_0();
               }

               codeStream.dup();
               if (this.syntheticAccessors != null && this.syntheticAccessors[0] != null) {
                  codeStream.invoke((byte)-72, this.syntheticAccessors[0], (TypeBinding)null);
               } else {
                  constantPoolDeclaringClass = CodeStream.getConstantPoolDeclaringClass(currentScope, (FieldBinding)codegenField, this.actualReceiverType, true);
                  codeStream.fieldAccess((byte)-76, codegenField, constantPoolDeclaringClass);
               }
            }
            break;
         case 2:
            LocalVariableBinding localBinding = (LocalVariableBinding)this.binding;
            Constant assignConstant;
            switch (localBinding.type.id) {
               case 10:
                  assignConstant = expression.constant;
                  if (localBinding.resolvedPosition == -1) {
                     if (valueRequired) {
                        localBinding.useFlag = 1;
                        throw new AbortMethod(CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE, (CategorizedProblem)null);
                     }

                     if (assignConstant == Constant.NotAConstant) {
                        expression.generateCode(currentScope, codeStream, false);
                     }

                     return;
                  }

                  if (assignConstant != Constant.NotAConstant && assignConstant.typeID() != 9 && assignConstant.typeID() != 8) {
                     int increment;
                     switch (operator) {
                        case 13:
                           increment = -assignConstant.intValue();
                           if (increment == (short)increment) {
                              codeStream.iinc(localBinding.resolvedPosition, increment);
                              if (valueRequired) {
                                 codeStream.load(localBinding);
                              }

                              return;
                           }
                           break;
                        case 14:
                           increment = assignConstant.intValue();
                           if (increment == (short)increment) {
                              codeStream.iinc(localBinding.resolvedPosition, increment);
                              if (valueRequired) {
                                 codeStream.load(localBinding);
                              }

                              return;
                           }
                     }
                  }
               default:
                  if (localBinding.resolvedPosition == -1) {
                     assignConstant = expression.constant;
                     if (valueRequired) {
                        localBinding.useFlag = 1;
                        throw new AbortMethod(CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE, (CategorizedProblem)null);
                     }

                     if (assignConstant == Constant.NotAConstant) {
                        expression.generateCode(currentScope, codeStream, false);
                     }

                     return;
                  }

                  codeStream.load(localBinding);
                  break;
               case 11:
                  codeStream.generateStringConcatenationAppend(currentScope, this, expression);
                  if (valueRequired) {
                     codeStream.dup();
                  }

                  codeStream.store(localBinding, false);
                  return;
            }
      }

      int operationTypeID;
      switch (operationTypeID = (this.implicitConversion & 255) >> 4) {
         case 0:
         case 1:
         case 11:
            codeStream.generateStringConcatenationAppend(currentScope, (Expression)null, expression);
            break;
         default:
            if (this.genericCast != null) {
               codeStream.checkcast(this.genericCast);
            }

            codeStream.generateImplicitConversion(this.implicitConversion);
            if (expression == IntLiteral.One) {
               codeStream.generateConstant(expression.constant, this.implicitConversion);
            } else {
               expression.generateCode(currentScope, codeStream, true);
            }

            codeStream.sendOperator(operator, operationTypeID);
            codeStream.generateImplicitConversion(assignmentImplicitConversion);
      }

      switch (this.bits & 7) {
         case 1:
            FieldBinding codegenField = ((FieldBinding)this.binding).original();
            this.fieldStore(currentScope, codeStream, codegenField, writeAccessor, this.actualReceiverType, true, valueRequired);
            return;
         case 2:
            LocalVariableBinding localBinding = (LocalVariableBinding)this.binding;
            if (valueRequired) {
               switch (localBinding.type.id) {
                  case 7:
                  case 8:
                     codeStream.dup2();
                     break;
                  default:
                     codeStream.dup();
               }
            }

            codeStream.store(localBinding, false);
         default:
      }
   }

   public void generatePostIncrement(BlockScope currentScope, CodeStream codeStream, CompoundAssignment postIncrement, boolean valueRequired) {
      switch (this.bits & 7) {
         case 1:
            FieldBinding fieldBinding = (FieldBinding)this.binding;
            this.reportOnlyUselesslyReadPrivateField(currentScope, fieldBinding, valueRequired);
            FieldBinding codegenField = fieldBinding.original();
            TypeBinding operandType;
            if (codegenField.isStatic()) {
               if (this.syntheticAccessors != null && this.syntheticAccessors[0] != null) {
                  codeStream.invoke((byte)-72, this.syntheticAccessors[0], (TypeBinding)null);
               } else {
                  operandType = CodeStream.getConstantPoolDeclaringClass(currentScope, (FieldBinding)codegenField, this.actualReceiverType, true);
                  codeStream.fieldAccess((byte)-78, codegenField, operandType);
               }
            } else {
               if ((this.bits & 8160) != 0) {
                  ReferenceBinding targetType = currentScope.enclosingSourceType().enclosingTypeAt((this.bits & 8160) >> 5);
                  Object[] emulationPath = currentScope.getEmulationPath(targetType, true, false);
                  codeStream.generateOuterAccess(emulationPath, this, targetType, currentScope);
               } else {
                  codeStream.aload_0();
               }

               codeStream.dup();
               if (this.syntheticAccessors != null && this.syntheticAccessors[0] != null) {
                  codeStream.invoke((byte)-72, this.syntheticAccessors[0], (TypeBinding)null);
               } else {
                  operandType = CodeStream.getConstantPoolDeclaringClass(currentScope, (FieldBinding)codegenField, this.actualReceiverType, true);
                  codeStream.fieldAccess((byte)-76, codegenField, operandType);
               }
            }

            if (this.genericCast != null) {
               codeStream.checkcast(this.genericCast);
               operandType = this.genericCast;
            } else {
               operandType = codegenField.type;
            }

            if (valueRequired) {
               if (codegenField.isStatic()) {
                  switch (operandType.id) {
                     case 7:
                     case 8:
                        codeStream.dup2();
                        break;
                     default:
                        codeStream.dup();
                  }
               } else {
                  switch (operandType.id) {
                     case 7:
                     case 8:
                        codeStream.dup2_x1();
                        break;
                     default:
                        codeStream.dup_x1();
                  }
               }
            }

            codeStream.generateImplicitConversion(this.implicitConversion);
            codeStream.generateConstant(postIncrement.expression.constant, this.implicitConversion);
            codeStream.sendOperator(postIncrement.operator, this.implicitConversion & 15);
            codeStream.generateImplicitConversion(postIncrement.preAssignImplicitConversion);
            this.fieldStore(currentScope, codeStream, codegenField, this.syntheticAccessors == null ? null : this.syntheticAccessors[1], this.actualReceiverType, true, false);
            return;
         case 2:
            LocalVariableBinding localBinding = (LocalVariableBinding)this.binding;
            Reference.reportOnlyUselesslyReadLocal(currentScope, localBinding, valueRequired);
            if (localBinding.resolvedPosition == -1) {
               if (valueRequired) {
                  localBinding.useFlag = 1;
                  throw new AbortMethod(CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE, (CategorizedProblem)null);
               }

               return;
            } else if (TypeBinding.equalsEquals(localBinding.type, TypeBinding.INT)) {
               if (valueRequired) {
                  codeStream.load(localBinding);
               }

               if (postIncrement.operator == 14) {
                  codeStream.iinc(localBinding.resolvedPosition, 1);
               } else {
                  codeStream.iinc(localBinding.resolvedPosition, -1);
               }
            } else {
               codeStream.load(localBinding);
               if (valueRequired) {
                  switch (localBinding.type.id) {
                     case 7:
                     case 8:
                        codeStream.dup2();
                        break;
                     default:
                        codeStream.dup();
                  }
               }

               codeStream.generateImplicitConversion(this.implicitConversion);
               codeStream.generateConstant(postIncrement.expression.constant, this.implicitConversion);
               codeStream.sendOperator(postIncrement.operator, this.implicitConversion & 15);
               codeStream.generateImplicitConversion(postIncrement.preAssignImplicitConversion);
               codeStream.store(localBinding, false);
            }
         default:
      }
   }

   public void generateReceiver(CodeStream codeStream) {
      codeStream.aload_0();
   }

   public TypeBinding[] genericTypeArguments() {
      return null;
   }

   public boolean isEquivalent(Reference reference) {
      char[] otherToken = null;
      if (reference instanceof SingleNameReference) {
         otherToken = ((SingleNameReference)reference).token;
      } else if (reference instanceof FieldReference) {
         FieldReference fr = (FieldReference)reference;
         if (fr.receiver.isThis() && !(fr.receiver instanceof QualifiedThisReference)) {
            otherToken = fr.token;
         }
      }

      return otherToken != null && CharOperation.equals(this.token, otherToken);
   }

   public LocalVariableBinding localVariableBinding() {
      switch (this.bits & 7) {
         case 1:
         default:
            return null;
         case 2:
            return (LocalVariableBinding)this.binding;
      }
   }

   public VariableBinding nullAnnotatedVariableBinding(boolean supportTypeAnnotations) {
      switch (this.bits & 7) {
         case 1:
         case 2:
            if (supportTypeAnnotations || (((VariableBinding)this.binding).tagBits & 108086391056891904L) != 0L) {
               return (VariableBinding)this.binding;
            }
         default:
            return null;
      }
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      if ((this.implicitConversion & 512) != 0) {
         return 4;
      } else {
         LocalVariableBinding local = this.localVariableBinding();
         return local != null ? flowInfo.nullStatus(local) : super.nullStatus(flowInfo, flowContext);
      }
   }

   public void manageEnclosingInstanceAccessIfNecessary(BlockScope currentScope, FlowInfo flowInfo) {
      if (((this.bits & 8160) != 0 || (this.bits & 524288) != 0) && this.constant == Constant.NotAConstant) {
         if ((this.bits & 7) == 2) {
            LocalVariableBinding localVariableBinding = (LocalVariableBinding)this.binding;
            if (localVariableBinding != null) {
               if (localVariableBinding.isUninitializedIn(currentScope)) {
                  return;
               }

               if ((localVariableBinding.tagBits & 2048L) == 0L) {
                  return;
               }

               switch (localVariableBinding.useFlag) {
                  case 1:
                  case 2:
                     currentScope.emulateOuterAccess(localVariableBinding);
               }
            }
         }

      }
   }

   public void manageSyntheticAccessIfNecessary(BlockScope currentScope, FlowInfo flowInfo, boolean isReadAccess) {
      if ((flowInfo.tagBits & 1) == 0) {
         if (this.constant == Constant.NotAConstant) {
            if ((this.bits & 1) != 0) {
               FieldBinding fieldBinding = (FieldBinding)this.binding;
               FieldBinding codegenField = fieldBinding.original();
               if ((this.bits & 8160) != 0 && (codegenField.isPrivate() && !currentScope.enclosingSourceType().isNestmateOf(codegenField.declaringClass) || codegenField.isProtected() && codegenField.declaringClass.getPackage() != currentScope.enclosingSourceType().getPackage())) {
                  if (this.syntheticAccessors == null) {
                     this.syntheticAccessors = new MethodBinding[2];
                  }

                  this.syntheticAccessors[isReadAccess ? 0 : 1] = ((SourceTypeBinding)currentScope.enclosingSourceType().enclosingTypeAt((this.bits & 8160) >> 5)).addSyntheticMethod(codegenField, isReadAccess, false);
                  currentScope.problemReporter().needToEmulateFieldAccess(codegenField, this, isReadAccess);
                  return;
               }
            }

         }
      }
   }

   public TypeBinding postConversionType(Scope scope) {
      TypeBinding convertedType = this.resolvedType;
      if (this.genericCast != null) {
         convertedType = this.genericCast;
      }

      int runtimeType = (this.implicitConversion & 255) >> 4;
      switch (runtimeType) {
         case 2:
            convertedType = TypeBinding.CHAR;
            break;
         case 3:
            convertedType = TypeBinding.BYTE;
            break;
         case 4:
            convertedType = TypeBinding.SHORT;
            break;
         case 5:
            convertedType = TypeBinding.BOOLEAN;
         case 6:
         default:
            break;
         case 7:
            convertedType = TypeBinding.LONG;
            break;
         case 8:
            convertedType = TypeBinding.DOUBLE;
            break;
         case 9:
            convertedType = TypeBinding.FLOAT;
            break;
         case 10:
            convertedType = TypeBinding.INT;
      }

      if ((this.implicitConversion & 512) != 0) {
         convertedType = scope.environment().computeBoxingType((TypeBinding)convertedType);
      }

      return (TypeBinding)convertedType;
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      return output.append(this.token);
   }

   public TypeBinding reportError(BlockScope scope) {
      this.constant = Constant.NotAConstant;
      if (this.binding instanceof ProblemFieldBinding) {
         scope.problemReporter().invalidField((NameReference)this, (FieldBinding)((FieldBinding)this.binding));
      } else if (!(this.binding instanceof ProblemReferenceBinding) && !(this.binding instanceof MissingTypeBinding)) {
         scope.problemReporter().unresolvableReference(this, this.binding);
      } else {
         scope.problemReporter().invalidType(this, (TypeBinding)this.binding);
      }

      return null;
   }

   public TypeBinding resolveType(BlockScope scope) {
      if (this.actualReceiverType != null) {
         this.binding = scope.getField(this.actualReceiverType, this.token, this);
      } else {
         this.actualReceiverType = scope.enclosingSourceType();
         this.binding = scope.getBinding(this.token, this.bits & 7, this, true);
      }

      if (this.binding.isValidBinding()) {
         switch (this.bits & 7) {
            case 3:
            case 7:
               if (this.binding instanceof VariableBinding) {
                  VariableBinding variable = (VariableBinding)this.binding;
                  TypeBinding variableType;
                  if (this.binding instanceof LocalVariableBinding) {
                     this.bits &= -8;
                     this.bits |= 2;
                     ((LocalVariableBinding)this.binding).markReferenced();
                     if (!variable.isFinal() && (this.bits & 524288) != 0 && scope.compilerOptions().sourceLevel < 3407872L) {
                        scope.problemReporter().cannotReferToNonFinalOuterLocal((LocalVariableBinding)variable, this);
                     }

                     variableType = variable.type;
                     this.constant = (this.bits & 8192) == 0 ? variable.constant(scope) : Constant.NotAConstant;
                  } else {
                     variableType = this.checkFieldAccess(scope);
                  }

                  if (variableType != null) {
                     TypeBinding var10001 = (this.bits & 8192) == 0 ? variableType.capture(scope, this.sourceStart, this.sourceEnd) : variableType;
                     variableType = var10001;
                     this.resolvedType = var10001;
                     if ((variableType.tagBits & 128L) != 0L) {
                        if ((this.bits & 2) == 0) {
                           scope.problemReporter().invalidType(this, variableType);
                        }

                        return null;
                     }
                  }

                  return variableType;
               }

               this.bits &= -8;
               this.bits |= 4;
            case 4:
               this.constant = Constant.NotAConstant;
               TypeBinding type = (TypeBinding)this.binding;
               if (this.isTypeUseDeprecated(type, scope)) {
                  scope.problemReporter().deprecatedType(type, this);
               }

               type = scope.environment().convertToRawType(type, false);
               return this.resolvedType = type;
            case 5:
            case 6:
         }
      }

      return this.resolvedType = this.reportError(scope);
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }

   public void traverse(ASTVisitor visitor, ClassScope scope) {
      visitor.visit(this, scope);
      visitor.endVisit(this, scope);
   }

   public String unboundReferenceErrorName() {
      return new String(this.token);
   }

   public char[][] getName() {
      return new char[][]{this.token};
   }
}
