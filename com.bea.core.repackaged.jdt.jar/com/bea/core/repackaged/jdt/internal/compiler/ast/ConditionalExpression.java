package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BaseTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.PolyTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class ConditionalExpression extends OperatorExpression implements IPolyExpression {
   public Expression condition;
   public Expression valueIfTrue;
   public Expression valueIfFalse;
   public Constant optimizedBooleanConstant;
   public Constant optimizedIfTrueConstant;
   public Constant optimizedIfFalseConstant;
   int trueInitStateIndex = -1;
   int falseInitStateIndex = -1;
   int mergedInitStateIndex = -1;
   private int nullStatus = 1;
   int ifFalseNullStatus;
   int ifTrueNullStatus;
   private TypeBinding expectedType;
   private ExpressionContext expressionContext;
   private boolean isPolyExpression;
   private TypeBinding originalValueIfTrueType;
   private TypeBinding originalValueIfFalseType;
   private boolean use18specifics;

   public ConditionalExpression(Expression condition, Expression valueIfTrue, Expression valueIfFalse) {
      this.expressionContext = ExpressionContext.VANILLA_CONTEXT;
      this.isPolyExpression = false;
      this.condition = condition;
      this.valueIfTrue = valueIfTrue;
      this.valueIfFalse = valueIfFalse;
      this.sourceStart = condition.sourceStart;
      this.sourceEnd = valueIfFalse.sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      int initialComplaintLevel = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
      Constant cst = this.condition.optimizedBooleanConstant();
      boolean isConditionOptimizedTrue = cst != Constant.NotAConstant && cst.booleanValue();
      boolean isConditionOptimizedFalse = cst != Constant.NotAConstant && !cst.booleanValue();
      int mode = flowInfo.reachMode();
      flowInfo = this.condition.analyseCode(currentScope, flowContext, flowInfo, cst == Constant.NotAConstant);
      ++flowContext.conditionalLevel;
      FlowInfo trueFlowInfo = flowInfo.initsWhenTrue().copy();
      CompilerOptions compilerOptions = currentScope.compilerOptions();
      if (isConditionOptimizedFalse) {
         if ((mode & 3) == 0) {
            trueFlowInfo.setReachMode(1);
         }

         if (!isKnowDeadCodePattern(this.condition) || compilerOptions.reportDeadCodeInTrivialIfStatement) {
            this.valueIfTrue.complainIfUnreachable(trueFlowInfo, currentScope, initialComplaintLevel, false);
         }
      }

      this.trueInitStateIndex = currentScope.methodScope().recordInitializationStates(trueFlowInfo);
      trueFlowInfo = this.valueIfTrue.analyseCode(currentScope, flowContext, trueFlowInfo);
      this.valueIfTrue.checkNPEbyUnboxing(currentScope, flowContext, trueFlowInfo);
      this.ifTrueNullStatus = -1;
      if (compilerOptions.enableSyntacticNullAnalysisForFields) {
         this.ifTrueNullStatus = this.valueIfTrue.nullStatus(trueFlowInfo, flowContext);
         flowContext.expireNullCheckedFieldInfo();
      }

      FlowInfo falseFlowInfo = flowInfo.initsWhenFalse().copy();
      if (isConditionOptimizedTrue) {
         if ((mode & 3) == 0) {
            falseFlowInfo.setReachMode(1);
         }

         if (!isKnowDeadCodePattern(this.condition) || compilerOptions.reportDeadCodeInTrivialIfStatement) {
            this.valueIfFalse.complainIfUnreachable(falseFlowInfo, currentScope, initialComplaintLevel, true);
         }
      }

      this.falseInitStateIndex = currentScope.methodScope().recordInitializationStates(falseFlowInfo);
      falseFlowInfo = this.valueIfFalse.analyseCode(currentScope, flowContext, falseFlowInfo);
      this.valueIfFalse.checkNPEbyUnboxing(currentScope, flowContext, falseFlowInfo);
      --flowContext.conditionalLevel;
      FlowInfo mergedInfo;
      if (isConditionOptimizedTrue) {
         mergedInfo = trueFlowInfo.addPotentialInitializationsFrom(falseFlowInfo);
         if (this.ifTrueNullStatus != -1) {
            this.nullStatus = this.ifTrueNullStatus;
         } else {
            this.nullStatus = this.valueIfTrue.nullStatus(trueFlowInfo, flowContext);
         }
      } else if (isConditionOptimizedFalse) {
         mergedInfo = falseFlowInfo.addPotentialInitializationsFrom(trueFlowInfo);
         this.nullStatus = this.valueIfFalse.nullStatus(falseFlowInfo, flowContext);
      } else {
         this.computeNullStatus(trueFlowInfo, falseFlowInfo, flowContext);
         cst = this.optimizedIfTrueConstant;
         boolean isValueIfTrueOptimizedTrue = cst != null && cst != Constant.NotAConstant && cst.booleanValue();
         boolean isValueIfTrueOptimizedFalse = cst != null && cst != Constant.NotAConstant && !cst.booleanValue();
         cst = this.optimizedIfFalseConstant;
         boolean isValueIfFalseOptimizedTrue = cst != null && cst != Constant.NotAConstant && cst.booleanValue();
         boolean isValueIfFalseOptimizedFalse = cst != null && cst != Constant.NotAConstant && !cst.booleanValue();
         UnconditionalFlowInfo trueFlowTowardsTrue = trueFlowInfo.initsWhenTrue().unconditionalCopy();
         UnconditionalFlowInfo falseFlowTowardsTrue = falseFlowInfo.initsWhenTrue().unconditionalCopy();
         UnconditionalFlowInfo trueFlowTowardsFalse = trueFlowInfo.initsWhenFalse().unconditionalInits();
         UnconditionalFlowInfo falseFlowTowardsFalse = falseFlowInfo.initsWhenFalse().unconditionalInits();
         if (isValueIfTrueOptimizedFalse) {
            trueFlowTowardsTrue.setReachMode(1);
         }

         if (isValueIfFalseOptimizedFalse) {
            falseFlowTowardsTrue.setReachMode(1);
         }

         if (isValueIfTrueOptimizedTrue) {
            trueFlowTowardsFalse.setReachMode(1);
         }

         if (isValueIfFalseOptimizedTrue) {
            falseFlowTowardsFalse.setReachMode(1);
         }

         mergedInfo = FlowInfo.conditional(trueFlowTowardsTrue.mergedWith(falseFlowTowardsTrue), trueFlowTowardsFalse.mergedWith(falseFlowTowardsFalse));
      }

      this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(mergedInfo);
      mergedInfo.setReachMode(mode);
      return mergedInfo;
   }

   public boolean checkNPE(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, int ttlForFieldCheck) {
      if ((this.nullStatus & 2) != 0) {
         scope.problemReporter().expressionNullReference(this);
      } else if ((this.nullStatus & 16) != 0) {
         scope.problemReporter().expressionPotentialNullReference(this);
      }

      return true;
   }

   private void computeNullStatus(FlowInfo trueBranchInfo, FlowInfo falseBranchInfo, FlowContext flowContext) {
      if (this.ifTrueNullStatus == -1) {
         this.ifTrueNullStatus = this.valueIfTrue.nullStatus(trueBranchInfo, flowContext);
      }

      this.ifFalseNullStatus = this.valueIfFalse.nullStatus(falseBranchInfo, flowContext);
      if (this.ifTrueNullStatus == this.ifFalseNullStatus) {
         this.nullStatus = this.ifTrueNullStatus;
      } else if (trueBranchInfo.reachMode() != 0) {
         this.nullStatus = this.ifFalseNullStatus;
      } else if (falseBranchInfo.reachMode() != 0) {
         this.nullStatus = this.ifTrueNullStatus;
      } else {
         int combinedStatus = this.ifTrueNullStatus | this.ifFalseNullStatus;
         int status = Expression.computeNullStatus(0, combinedStatus);
         if (status > 0) {
            this.nullStatus = status;
         }

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
         Constant cst = this.condition.optimizedBooleanConstant();
         boolean needTruePart = cst == Constant.NotAConstant || cst.booleanValue();
         boolean needFalsePart = cst == Constant.NotAConstant || !cst.booleanValue();
         BranchLabel endifLabel = new BranchLabel(codeStream);
         BranchLabel falseLabel = new BranchLabel(codeStream);
         falseLabel.tagBits |= 2;
         this.condition.generateOptimizedBoolean(currentScope, codeStream, (BranchLabel)null, falseLabel, cst == Constant.NotAConstant);
         if (this.trueInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.trueInitStateIndex);
            codeStream.addDefinitelyAssignedVariables(currentScope, this.trueInitStateIndex);
         }

         if (needTruePart) {
            this.valueIfTrue.generateCode(currentScope, codeStream, valueRequired);
            if (needFalsePart) {
               int position = codeStream.position;
               codeStream.goto_(endifLabel);
               codeStream.recordPositionsFrom(position, this.valueIfTrue.sourceEnd);
               if (valueRequired) {
                  switch (this.resolvedType.id) {
                     case 7:
                     case 8:
                        codeStream.decrStackSize(2);
                        break;
                     default:
                        codeStream.decrStackSize(1);
                  }
               }
            }
         }

         if (needFalsePart) {
            if (this.falseInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.falseInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.falseInitStateIndex);
            }

            if (falseLabel.forwardReferenceCount() > 0) {
               falseLabel.place();
            }

            this.valueIfFalse.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               codeStream.recordExpressionType(this.resolvedType);
            }

            if (needTruePart) {
               endifLabel.place();
            }
         }

         if (this.mergedInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
         }

         if (valueRequired) {
            codeStream.generateImplicitConversion(this.implicitConversion);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public void generateOptimizedBoolean(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      int pc = codeStream.position;
      if ((this.constant == Constant.NotAConstant || this.constant.typeID() != 5) && (this.valueIfTrue.implicitConversion & 255) >> 4 == 5 && (this.valueIfFalse.implicitConversion & 255) >> 4 == 5) {
         Constant cst = this.condition.constant;
         Constant condCst = this.condition.optimizedBooleanConstant();
         boolean needTruePart = (cst == Constant.NotAConstant || cst.booleanValue()) && (condCst == Constant.NotAConstant || condCst.booleanValue());
         boolean needFalsePart = (cst == Constant.NotAConstant || !cst.booleanValue()) && (condCst == Constant.NotAConstant || !condCst.booleanValue());
         BranchLabel endifLabel = new BranchLabel(codeStream);
         boolean needConditionValue = cst == Constant.NotAConstant && condCst == Constant.NotAConstant;
         BranchLabel internalFalseLabel;
         this.condition.generateOptimizedBoolean(currentScope, codeStream, (BranchLabel)null, internalFalseLabel = new BranchLabel(codeStream), needConditionValue);
         if (this.trueInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.trueInitStateIndex);
            codeStream.addDefinitelyAssignedVariables(currentScope, this.trueInitStateIndex);
         }

         if (needTruePart) {
            this.valueIfTrue.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
            if (needFalsePart) {
               label79: {
                  boolean isValueIfTrueOptimizedTrue;
                  if (falseLabel == null) {
                     if (trueLabel != null) {
                        cst = this.optimizedIfTrueConstant;
                        isValueIfTrueOptimizedTrue = cst != null && cst != Constant.NotAConstant && cst.booleanValue();
                        if (isValueIfTrueOptimizedTrue) {
                           break label79;
                        }
                     }
                  } else if (trueLabel == null) {
                     cst = this.optimizedIfTrueConstant;
                     isValueIfTrueOptimizedTrue = cst != null && cst != Constant.NotAConstant && !cst.booleanValue();
                     if (isValueIfTrueOptimizedTrue) {
                        break label79;
                     }
                  }

                  int position = codeStream.position;
                  codeStream.goto_(endifLabel);
                  codeStream.recordPositionsFrom(position, this.valueIfTrue.sourceEnd);
               }
            }
         }

         if (needFalsePart) {
            internalFalseLabel.place();
            if (this.falseInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.falseInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.falseInitStateIndex);
            }

            this.valueIfFalse.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
            endifLabel.place();
         }

         if (this.mergedInitStateIndex != -1) {
            codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
         }

         codeStream.recordPositionsFrom(pc, this.sourceEnd);
      } else {
         super.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
      }
   }

   public int nullStatus(FlowInfo flowInfo, FlowContext flowContext) {
      return (this.implicitConversion & 512) != 0 ? 4 : this.nullStatus;
   }

   public Constant optimizedBooleanConstant() {
      return this.optimizedBooleanConstant == null ? this.constant : this.optimizedBooleanConstant;
   }

   public StringBuffer printExpressionNoParenthesis(int indent, StringBuffer output) {
      this.condition.printExpression(indent, output).append(" ? ");
      this.valueIfTrue.printExpression(0, output).append(" : ");
      return this.valueIfFalse.printExpression(0, output);
   }

   public TypeBinding resolveType(BlockScope scope) {
      LookupEnvironment env = scope.environment();
      long sourceLevel = scope.compilerOptions().sourceLevel;
      boolean use15specifics = sourceLevel >= 3211264L;
      this.use18specifics = sourceLevel >= 3407872L;
      if (this.use18specifics && (this.expressionContext == ExpressionContext.ASSIGNMENT_CONTEXT || this.expressionContext == ExpressionContext.INVOCATION_CONTEXT)) {
         this.valueIfTrue.setExpressionContext(this.expressionContext);
         this.valueIfTrue.setExpectedType(this.expectedType);
         this.valueIfFalse.setExpressionContext(this.expressionContext);
         this.valueIfFalse.setExpectedType(this.expectedType);
      }

      TypeBinding valueIfTrueType;
      if (this.constant != Constant.NotAConstant) {
         this.constant = Constant.NotAConstant;
         valueIfTrueType = this.condition.resolveTypeExpecting(scope, TypeBinding.BOOLEAN);
         this.condition.computeConversion(scope, TypeBinding.BOOLEAN, valueIfTrueType);
         Expression var10000;
         if (this.valueIfTrue instanceof CastExpression) {
            var10000 = this.valueIfTrue;
            var10000.bits |= 32;
         }

         this.originalValueIfTrueType = this.valueIfTrue.resolveType(scope);
         if (this.valueIfFalse instanceof CastExpression) {
            var10000 = this.valueIfFalse;
            var10000.bits |= 32;
         }

         this.originalValueIfFalseType = this.valueIfFalse.resolveType(scope);
         if (valueIfTrueType == null || this.originalValueIfTrueType == null || this.originalValueIfFalseType == null) {
            return null;
         }
      } else {
         if (this.originalValueIfTrueType.kind() == 65540) {
            this.originalValueIfTrueType = this.valueIfTrue.resolveType(scope);
         }

         if (this.originalValueIfFalseType.kind() == 65540) {
            this.originalValueIfFalseType = this.valueIfFalse.resolveType(scope);
         }

         if (this.originalValueIfTrueType == null || !this.originalValueIfTrueType.isValidBinding()) {
            return this.resolvedType = null;
         }

         if (this.originalValueIfFalseType == null || !this.originalValueIfFalseType.isValidBinding()) {
            return this.resolvedType = null;
         }
      }

      if (this.isPolyExpression()) {
         if (this.expectedType != null && this.expectedType.isProperType(true)) {
            return this.resolvedType = this.computeConversions(scope, this.expectedType) ? this.expectedType : null;
         } else {
            return new PolyTypeBinding(this);
         }
      } else {
         valueIfTrueType = this.originalValueIfTrueType;
         TypeBinding valueIfFalseType = this.originalValueIfFalseType;
         if (use15specifics && TypeBinding.notEquals(valueIfTrueType, valueIfFalseType)) {
            TypeBinding unboxedIfFalseType;
            if (valueIfTrueType.isBaseType()) {
               if (valueIfFalseType.isBaseType()) {
                  if (valueIfTrueType == TypeBinding.NULL) {
                     valueIfFalseType = env.computeBoxingType(valueIfFalseType);
                  } else if (valueIfFalseType == TypeBinding.NULL) {
                     valueIfTrueType = env.computeBoxingType(valueIfTrueType);
                  }
               } else {
                  unboxedIfFalseType = valueIfFalseType.isBaseType() ? valueIfFalseType : env.computeBoxingType(valueIfFalseType);
                  if (valueIfTrueType.isNumericType() && unboxedIfFalseType.isNumericType()) {
                     valueIfFalseType = unboxedIfFalseType;
                  } else if (valueIfTrueType != TypeBinding.NULL) {
                     valueIfFalseType = env.computeBoxingType(valueIfFalseType);
                  }
               }
            } else if (valueIfFalseType.isBaseType()) {
               unboxedIfFalseType = valueIfTrueType.isBaseType() ? valueIfTrueType : env.computeBoxingType(valueIfTrueType);
               if (unboxedIfFalseType.isNumericType() && valueIfFalseType.isNumericType()) {
                  valueIfTrueType = unboxedIfFalseType;
               } else if (valueIfFalseType != TypeBinding.NULL) {
                  valueIfTrueType = env.computeBoxingType(valueIfTrueType);
               }
            } else {
               unboxedIfFalseType = env.computeBoxingType(valueIfTrueType);
               TypeBinding unboxedIfFalseType = env.computeBoxingType(valueIfFalseType);
               if (unboxedIfFalseType.isNumericType() && unboxedIfFalseType.isNumericType()) {
                  valueIfTrueType = unboxedIfFalseType;
                  valueIfFalseType = unboxedIfFalseType;
               }
            }
         }

         Constant falseConstant;
         Constant condConstant;
         Constant trueConstant;
         if ((condConstant = this.condition.constant) != Constant.NotAConstant && (trueConstant = this.valueIfTrue.constant) != Constant.NotAConstant && (falseConstant = this.valueIfFalse.constant) != Constant.NotAConstant) {
            this.constant = condConstant.booleanValue() ? trueConstant : falseConstant;
         }

         if (TypeBinding.equalsEquals(valueIfTrueType, valueIfFalseType)) {
            this.valueIfTrue.computeConversion(scope, valueIfTrueType, this.originalValueIfTrueType);
            this.valueIfFalse.computeConversion(scope, valueIfFalseType, this.originalValueIfFalseType);
            if (TypeBinding.equalsEquals(valueIfTrueType, TypeBinding.BOOLEAN)) {
               this.optimizedIfTrueConstant = this.valueIfTrue.optimizedBooleanConstant();
               this.optimizedIfFalseConstant = this.valueIfFalse.optimizedBooleanConstant();
               if (this.optimizedIfTrueConstant != Constant.NotAConstant && this.optimizedIfFalseConstant != Constant.NotAConstant && this.optimizedIfTrueConstant.booleanValue() == this.optimizedIfFalseConstant.booleanValue()) {
                  this.optimizedBooleanConstant = this.optimizedIfTrueConstant;
               } else if ((condConstant = this.condition.optimizedBooleanConstant()) != Constant.NotAConstant) {
                  this.optimizedBooleanConstant = condConstant.booleanValue() ? this.optimizedIfTrueConstant : this.optimizedIfFalseConstant;
               }
            }

            return this.resolvedType = NullAnnotationMatching.moreDangerousType(valueIfTrueType, valueIfFalseType);
         } else if (valueIfTrueType.isNumericType() && valueIfFalseType.isNumericType()) {
            if (TypeBinding.equalsEquals(valueIfTrueType, TypeBinding.BYTE) && TypeBinding.equalsEquals(valueIfFalseType, TypeBinding.SHORT) || TypeBinding.equalsEquals(valueIfTrueType, TypeBinding.SHORT) && TypeBinding.equalsEquals(valueIfFalseType, TypeBinding.BYTE)) {
               this.valueIfTrue.computeConversion(scope, TypeBinding.SHORT, this.originalValueIfTrueType);
               this.valueIfFalse.computeConversion(scope, TypeBinding.SHORT, this.originalValueIfFalseType);
               return this.resolvedType = TypeBinding.SHORT;
            } else if ((TypeBinding.equalsEquals(valueIfTrueType, TypeBinding.BYTE) || TypeBinding.equalsEquals(valueIfTrueType, TypeBinding.SHORT) || TypeBinding.equalsEquals(valueIfTrueType, TypeBinding.CHAR)) && TypeBinding.equalsEquals(valueIfFalseType, TypeBinding.INT) && this.valueIfFalse.isConstantValueOfTypeAssignableToType(valueIfFalseType, valueIfTrueType)) {
               this.valueIfTrue.computeConversion(scope, valueIfTrueType, this.originalValueIfTrueType);
               this.valueIfFalse.computeConversion(scope, valueIfTrueType, this.originalValueIfFalseType);
               return this.resolvedType = valueIfTrueType;
            } else if ((TypeBinding.equalsEquals(valueIfFalseType, TypeBinding.BYTE) || TypeBinding.equalsEquals(valueIfFalseType, TypeBinding.SHORT) || TypeBinding.equalsEquals(valueIfFalseType, TypeBinding.CHAR)) && TypeBinding.equalsEquals(valueIfTrueType, TypeBinding.INT) && this.valueIfTrue.isConstantValueOfTypeAssignableToType(valueIfTrueType, valueIfFalseType)) {
               this.valueIfTrue.computeConversion(scope, valueIfFalseType, this.originalValueIfTrueType);
               this.valueIfFalse.computeConversion(scope, valueIfFalseType, this.originalValueIfFalseType);
               return this.resolvedType = valueIfFalseType;
            } else if (BaseTypeBinding.isNarrowing(valueIfTrueType.id, 10) && BaseTypeBinding.isNarrowing(valueIfFalseType.id, 10)) {
               this.valueIfTrue.computeConversion(scope, TypeBinding.INT, this.originalValueIfTrueType);
               this.valueIfFalse.computeConversion(scope, TypeBinding.INT, this.originalValueIfFalseType);
               return this.resolvedType = TypeBinding.INT;
            } else if (BaseTypeBinding.isNarrowing(valueIfTrueType.id, 7) && BaseTypeBinding.isNarrowing(valueIfFalseType.id, 7)) {
               this.valueIfTrue.computeConversion(scope, TypeBinding.LONG, this.originalValueIfTrueType);
               this.valueIfFalse.computeConversion(scope, TypeBinding.LONG, this.originalValueIfFalseType);
               return this.resolvedType = TypeBinding.LONG;
            } else if (BaseTypeBinding.isNarrowing(valueIfTrueType.id, 9) && BaseTypeBinding.isNarrowing(valueIfFalseType.id, 9)) {
               this.valueIfTrue.computeConversion(scope, TypeBinding.FLOAT, this.originalValueIfTrueType);
               this.valueIfFalse.computeConversion(scope, TypeBinding.FLOAT, this.originalValueIfFalseType);
               return this.resolvedType = TypeBinding.FLOAT;
            } else {
               this.valueIfTrue.computeConversion(scope, TypeBinding.DOUBLE, this.originalValueIfTrueType);
               this.valueIfFalse.computeConversion(scope, TypeBinding.DOUBLE, this.originalValueIfFalseType);
               return this.resolvedType = TypeBinding.DOUBLE;
            }
         } else {
            if (valueIfTrueType.isBaseType() && valueIfTrueType != TypeBinding.NULL) {
               if (!use15specifics) {
                  scope.problemReporter().conditionalArgumentsIncompatibleTypes(this, valueIfTrueType, valueIfFalseType);
                  return null;
               }

               valueIfTrueType = env.computeBoxingType(valueIfTrueType);
            }

            if (valueIfFalseType.isBaseType() && valueIfFalseType != TypeBinding.NULL) {
               if (!use15specifics) {
                  scope.problemReporter().conditionalArgumentsIncompatibleTypes(this, valueIfTrueType, valueIfFalseType);
                  return null;
               }

               valueIfFalseType = env.computeBoxingType(valueIfFalseType);
            }

            if (use15specifics) {
               TypeBinding commonType = null;
               if (valueIfTrueType == TypeBinding.NULL) {
                  commonType = valueIfFalseType;
               } else if (valueIfFalseType == TypeBinding.NULL) {
                  commonType = valueIfTrueType;
               } else {
                  commonType = scope.lowerUpperBound(new TypeBinding[]{valueIfTrueType, valueIfFalseType});
               }

               if (commonType != null) {
                  this.valueIfTrue.computeConversion(scope, commonType, this.originalValueIfTrueType);
                  this.valueIfFalse.computeConversion(scope, commonType, this.originalValueIfFalseType);
                  return this.resolvedType = commonType.capture(scope, this.sourceStart, this.sourceEnd);
               }
            } else {
               if (valueIfFalseType.isCompatibleWith(valueIfTrueType)) {
                  this.valueIfTrue.computeConversion(scope, valueIfTrueType, this.originalValueIfTrueType);
                  this.valueIfFalse.computeConversion(scope, valueIfTrueType, this.originalValueIfFalseType);
                  return this.resolvedType = valueIfTrueType;
               }

               if (valueIfTrueType.isCompatibleWith(valueIfFalseType)) {
                  this.valueIfTrue.computeConversion(scope, valueIfFalseType, this.originalValueIfTrueType);
                  this.valueIfFalse.computeConversion(scope, valueIfFalseType, this.originalValueIfFalseType);
                  return this.resolvedType = valueIfFalseType;
               }
            }

            scope.problemReporter().conditionalArgumentsIncompatibleTypes(this, valueIfTrueType, valueIfFalseType);
            return null;
         }
      }
   }

   protected boolean computeConversions(BlockScope scope, TypeBinding targetType) {
      boolean ok = true;
      if (this.originalValueIfTrueType != null && this.originalValueIfTrueType.isValidBinding()) {
         if (!this.valueIfTrue.isConstantValueOfTypeAssignableToType(this.originalValueIfTrueType, targetType) && !this.originalValueIfTrueType.isCompatibleWith(targetType)) {
            if (this.isBoxingCompatible(this.originalValueIfTrueType, targetType, this.valueIfTrue, scope)) {
               this.valueIfTrue.computeConversion(scope, targetType, this.originalValueIfTrueType);
               if (this.valueIfTrue instanceof CastExpression && (this.valueIfTrue.bits & 16416) == 0) {
                  CastExpression.checkNeedForAssignedCast(scope, targetType, (CastExpression)this.valueIfTrue);
               }
            } else {
               scope.problemReporter().typeMismatchError(this.originalValueIfTrueType, (TypeBinding)targetType, (ASTNode)this.valueIfTrue, (ASTNode)null);
               ok = false;
            }
         } else {
            this.valueIfTrue.computeConversion(scope, targetType, this.originalValueIfTrueType);
            if (this.originalValueIfTrueType.needsUncheckedConversion(targetType)) {
               scope.problemReporter().unsafeTypeConversion(this.valueIfTrue, this.originalValueIfTrueType, targetType);
            }

            if (this.valueIfTrue instanceof CastExpression && (this.valueIfTrue.bits & 16416) == 0) {
               CastExpression.checkNeedForAssignedCast(scope, targetType, (CastExpression)this.valueIfTrue);
            }
         }
      }

      if (this.originalValueIfFalseType != null && this.originalValueIfFalseType.isValidBinding()) {
         if (!this.valueIfFalse.isConstantValueOfTypeAssignableToType(this.originalValueIfFalseType, targetType) && !this.originalValueIfFalseType.isCompatibleWith(targetType)) {
            if (this.isBoxingCompatible(this.originalValueIfFalseType, targetType, this.valueIfFalse, scope)) {
               this.valueIfFalse.computeConversion(scope, targetType, this.originalValueIfFalseType);
               if (this.valueIfFalse instanceof CastExpression && (this.valueIfFalse.bits & 16416) == 0) {
                  CastExpression.checkNeedForAssignedCast(scope, targetType, (CastExpression)this.valueIfFalse);
               }
            } else {
               scope.problemReporter().typeMismatchError(this.originalValueIfFalseType, (TypeBinding)targetType, (ASTNode)this.valueIfFalse, (ASTNode)null);
               ok = false;
            }
         } else {
            this.valueIfFalse.computeConversion(scope, targetType, this.originalValueIfFalseType);
            if (this.originalValueIfFalseType.needsUncheckedConversion(targetType)) {
               scope.problemReporter().unsafeTypeConversion(this.valueIfFalse, this.originalValueIfFalseType, targetType);
            }

            if (this.valueIfFalse instanceof CastExpression && (this.valueIfFalse.bits & 16416) == 0) {
               CastExpression.checkNeedForAssignedCast(scope, targetType, (CastExpression)this.valueIfFalse);
            }
         }
      }

      return ok;
   }

   public void setExpectedType(TypeBinding expectedType) {
      this.expectedType = expectedType;
   }

   public void setExpressionContext(ExpressionContext context) {
      this.expressionContext = context;
   }

   public ExpressionContext getExpressionContext() {
      return this.expressionContext;
   }

   public Expression[] getPolyExpressions() {
      Expression[] truePolys = this.valueIfTrue.getPolyExpressions();
      Expression[] falsePolys = this.valueIfFalse.getPolyExpressions();
      if (truePolys.length == 0) {
         return falsePolys;
      } else if (falsePolys.length == 0) {
         return truePolys;
      } else {
         Expression[] allPolys = new Expression[truePolys.length + falsePolys.length];
         System.arraycopy(truePolys, 0, allPolys, 0, truePolys.length);
         System.arraycopy(falsePolys, 0, allPolys, truePolys.length, falsePolys.length);
         return allPolys;
      }
   }

   public boolean isPertinentToApplicability(TypeBinding targetType, MethodBinding method) {
      return this.valueIfTrue.isPertinentToApplicability(targetType, method) && this.valueIfFalse.isPertinentToApplicability(targetType, method);
   }

   public boolean isPotentiallyCompatibleWith(TypeBinding targetType, Scope scope) {
      return this.valueIfTrue.isPotentiallyCompatibleWith(targetType, scope) && this.valueIfFalse.isPotentiallyCompatibleWith(targetType, scope);
   }

   public boolean isFunctionalType() {
      return this.valueIfTrue.isFunctionalType() || this.valueIfFalse.isFunctionalType();
   }

   public boolean isPolyExpression() throws UnsupportedOperationException {
      if (!this.use18specifics) {
         return false;
      } else if (this.isPolyExpression) {
         return true;
      } else if (this.expressionContext != ExpressionContext.ASSIGNMENT_CONTEXT && this.expressionContext != ExpressionContext.INVOCATION_CONTEXT) {
         return false;
      } else if (this.originalValueIfTrueType != null && this.originalValueIfFalseType != null) {
         if (!this.valueIfTrue.isPolyExpression() && !this.valueIfFalse.isPolyExpression()) {
            return !this.originalValueIfTrueType.isBaseType() && (this.originalValueIfTrueType.id < 26 || this.originalValueIfTrueType.id > 33) || !this.originalValueIfFalseType.isBaseType() && (this.originalValueIfFalseType.id < 26 || this.originalValueIfFalseType.id > 33) ? (this.isPolyExpression = true) : false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean isCompatibleWith(TypeBinding left, Scope scope) {
      return this.isPolyExpression() ? this.valueIfTrue.isCompatibleWith(left, scope) && this.valueIfFalse.isCompatibleWith(left, scope) : super.isCompatibleWith(left, scope);
   }

   public boolean isBoxingCompatibleWith(TypeBinding targetType, Scope scope) {
      return this.isPolyExpression() ? (this.valueIfTrue.isCompatibleWith(targetType, scope) || this.valueIfTrue.isBoxingCompatibleWith(targetType, scope)) && (this.valueIfFalse.isCompatibleWith(targetType, scope) || this.valueIfFalse.isBoxingCompatibleWith(targetType, scope)) : super.isBoxingCompatibleWith(targetType, scope);
   }

   public boolean sIsMoreSpecific(TypeBinding s, TypeBinding t, Scope scope) {
      if (super.sIsMoreSpecific(s, t, scope)) {
         return true;
      } else {
         return this.isPolyExpression() ? this.valueIfTrue.sIsMoreSpecific(s, t, scope) && this.valueIfFalse.sIsMoreSpecific(s, t, scope) : false;
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.condition.traverse(visitor, scope);
         this.valueIfTrue.traverse(visitor, scope);
         this.valueIfFalse.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}
