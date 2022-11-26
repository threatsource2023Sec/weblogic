package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.BooleanConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class EqualExpression extends BinaryExpression {
   public EqualExpression(Expression left, Expression right, int operator) {
      super(left, right, operator);
   }

   private void checkNullComparison(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, FlowInfo initsWhenTrue, FlowInfo initsWhenFalse) {
      int rightStatus = this.right.nullStatus(flowInfo, flowContext);
      int leftStatus = this.left.nullStatus(flowInfo, flowContext);
      boolean leftNonNullChecked = false;
      boolean rightNonNullChecked = false;
      boolean checkEquality = (this.bits & 4032) >> 6 == 18;
      if ((flowContext.tagBits & '\uf000') == 0) {
         if (leftStatus == 4 && rightStatus == 2) {
            leftNonNullChecked = scope.problemReporter().expressionNonNullComparison(this.left, checkEquality);
         } else if (leftStatus == 2 && rightStatus == 4) {
            rightNonNullChecked = scope.problemReporter().expressionNonNullComparison(this.right, checkEquality);
         }
      }

      boolean contextualCheckEquality = checkEquality ^ (flowContext.tagBits & 4) != 0;
      LocalVariableBinding local;
      FieldBinding field;
      if (!leftNonNullChecked) {
         local = this.left.localVariableBinding();
         if (local != null) {
            if ((local.type.tagBits & 2L) == 0L) {
               this.checkVariableComparison(scope, flowContext, flowInfo, initsWhenTrue, initsWhenFalse, local, rightStatus, this.left);
            }
         } else if (this.left instanceof Reference && (!contextualCheckEquality && rightStatus == 2 || contextualCheckEquality && rightStatus == 4) && scope.compilerOptions().enableSyntacticNullAnalysisForFields) {
            field = ((Reference)this.left).lastFieldBinding();
            if (field != null && (field.type.tagBits & 2L) == 0L) {
               flowContext.recordNullCheckedFieldReference((Reference)this.left, 1);
            }
         }
      }

      if (!rightNonNullChecked) {
         local = this.right.localVariableBinding();
         if (local != null) {
            if ((local.type.tagBits & 2L) == 0L) {
               this.checkVariableComparison(scope, flowContext, flowInfo, initsWhenTrue, initsWhenFalse, local, leftStatus, this.right);
            }
         } else if (this.right instanceof Reference && (!contextualCheckEquality && leftStatus == 2 || contextualCheckEquality && leftStatus == 4) && scope.compilerOptions().enableSyntacticNullAnalysisForFields) {
            field = ((Reference)this.right).lastFieldBinding();
            if (field != null && (field.type.tagBits & 2L) == 0L) {
               flowContext.recordNullCheckedFieldReference((Reference)this.right, 1);
            }
         }
      }

      if (leftNonNullChecked || rightNonNullChecked) {
         if (checkEquality) {
            initsWhenTrue.setReachMode(2);
         } else {
            initsWhenFalse.setReachMode(2);
         }
      }

   }

   private void checkVariableComparison(BlockScope scope, FlowContext flowContext, FlowInfo flowInfo, FlowInfo initsWhenTrue, FlowInfo initsWhenFalse, LocalVariableBinding local, int nullStatus, Expression reference) {
      switch (nullStatus) {
         case 2:
            if ((this.bits & 4032) >> 6 == 18) {
               flowContext.recordUsingNullReference(scope, local, reference, 256, flowInfo);
               initsWhenTrue.markAsComparedEqualToNull(local);
               initsWhenFalse.markAsComparedEqualToNonNull(local);
            } else {
               flowContext.recordUsingNullReference(scope, local, reference, 512, flowInfo);
               initsWhenTrue.markAsComparedEqualToNonNull(local);
               initsWhenFalse.markAsComparedEqualToNull(local);
            }
         case 3:
         default:
            break;
         case 4:
            if ((this.bits & 4032) >> 6 == 18) {
               flowContext.recordUsingNullReference(scope, local, reference, 513, flowInfo);
               initsWhenTrue.markAsComparedEqualToNonNull(local);
            } else {
               flowContext.recordUsingNullReference(scope, local, reference, 257, flowInfo);
            }
      }

   }

   private void analyzeLocalVariable(Expression exp, FlowInfo flowInfo) {
      if (exp instanceof SingleNameReference && (exp.bits & 2) != 0) {
         LocalVariableBinding localBinding = (LocalVariableBinding)((SingleNameReference)exp).binding;
         if ((flowInfo.tagBits & 3) == 0) {
            localBinding.useFlag = 1;
         } else if (localBinding.useFlag == 0) {
            localBinding.useFlag = 2;
         }
      }

   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      Object result;
      if ((this.bits & 4032) >> 6 == 18) {
         if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 5) {
            if (this.left.constant.booleanValue()) {
               result = this.right.analyseCode(currentScope, flowContext, flowInfo);
            } else {
               result = this.right.analyseCode(currentScope, flowContext, flowInfo).asNegatedCondition();
               this.analyzeLocalVariable(this.left, flowInfo);
            }
         } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 5) {
            if (this.right.constant.booleanValue()) {
               result = this.left.analyseCode(currentScope, flowContext, flowInfo);
            } else {
               result = this.left.analyseCode(currentScope, flowContext, flowInfo).asNegatedCondition();
               this.analyzeLocalVariable(this.right, flowInfo);
            }
         } else {
            result = this.right.analyseCode(currentScope, flowContext, this.left.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits()).unconditionalInits();
         }
      } else if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 5) {
         if (!this.left.constant.booleanValue()) {
            result = this.right.analyseCode(currentScope, flowContext, flowInfo);
            this.analyzeLocalVariable(this.left, flowInfo);
         } else {
            result = this.right.analyseCode(currentScope, flowContext, flowInfo).asNegatedCondition();
         }
      } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 5) {
         if (!this.right.constant.booleanValue()) {
            result = this.left.analyseCode(currentScope, flowContext, flowInfo);
            this.analyzeLocalVariable(this.right, flowInfo);
         } else {
            result = this.left.analyseCode(currentScope, flowContext, flowInfo).asNegatedCondition();
         }
      } else {
         result = this.right.analyseCode(currentScope, flowContext, this.left.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits()).unconditionalInits();
      }

      if (result instanceof UnconditionalFlowInfo && (((FlowInfo)result).tagBits & 3) == 0) {
         result = FlowInfo.conditional(((FlowInfo)result).copy(), ((FlowInfo)result).copy());
      }

      this.checkNullComparison(currentScope, flowContext, (FlowInfo)result, ((FlowInfo)result).initsWhenTrue(), ((FlowInfo)result).initsWhenFalse());
      return (FlowInfo)result;
   }

   public final void computeConstant(TypeBinding leftType, TypeBinding rightType) {
      if (this.left.constant != Constant.NotAConstant && this.right.constant != Constant.NotAConstant) {
         this.constant = Constant.computeConstantOperationEQUAL_EQUAL(this.left.constant, leftType.id, this.right.constant, rightType.id);
         if ((this.bits & 4032) >> 6 == 29) {
            this.constant = BooleanConstant.fromValue(!this.constant.booleanValue());
         }
      } else {
         this.constant = Constant.NotAConstant;
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
         if ((this.left.implicitConversion & 15) == 5) {
            this.generateBooleanEqual(currentScope, codeStream, valueRequired);
         } else {
            this.generateNonBooleanEqual(currentScope, codeStream, valueRequired);
         }

         if (valueRequired) {
            codeStream.generateImplicitConversion(this.implicitConversion);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public void generateOptimizedBoolean(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      if (this.constant != Constant.NotAConstant) {
         super.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
      } else {
         if ((this.bits & 4032) >> 6 == 18) {
            if ((this.left.implicitConversion & 15) == 5) {
               this.generateOptimizedBooleanEqual(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
            } else {
               this.generateOptimizedNonBooleanEqual(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
            }
         } else if ((this.left.implicitConversion & 15) == 5) {
            this.generateOptimizedBooleanEqual(currentScope, codeStream, falseLabel, trueLabel, valueRequired);
         } else {
            this.generateOptimizedNonBooleanEqual(currentScope, codeStream, falseLabel, trueLabel, valueRequired);
         }

      }
   }

   public void generateBooleanEqual(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      boolean isEqualOperator = (this.bits & 4032) >> 6 == 18;
      Constant cst = this.left.optimizedBooleanConstant();
      BranchLabel endLabel;
      if (cst != Constant.NotAConstant) {
         Constant rightCst = this.right.optimizedBooleanConstant();
         if (rightCst != Constant.NotAConstant) {
            this.left.generateCode(currentScope, codeStream, false);
            this.right.generateCode(currentScope, codeStream, false);
            if (valueRequired) {
               boolean leftBool = cst.booleanValue();
               boolean rightBool = rightCst.booleanValue();
               if (isEqualOperator) {
                  if (leftBool == rightBool) {
                     codeStream.iconst_1();
                  } else {
                     codeStream.iconst_0();
                  }
               } else if (leftBool != rightBool) {
                  codeStream.iconst_1();
               } else {
                  codeStream.iconst_0();
               }
            }
         } else if (cst.booleanValue() == isEqualOperator) {
            this.left.generateCode(currentScope, codeStream, false);
            this.right.generateCode(currentScope, codeStream, valueRequired);
         } else if (valueRequired) {
            endLabel = new BranchLabel(codeStream);
            this.left.generateCode(currentScope, codeStream, false);
            this.right.generateOptimizedBoolean(currentScope, codeStream, (BranchLabel)null, endLabel, valueRequired);
            codeStream.iconst_0();
            if ((this.bits & 16) != 0) {
               codeStream.generateImplicitConversion(this.implicitConversion);
               codeStream.generateReturnBytecode(this);
               endLabel.place();
               codeStream.iconst_1();
            } else {
               BranchLabel endLabel = new BranchLabel(codeStream);
               codeStream.goto_(endLabel);
               codeStream.decrStackSize(1);
               endLabel.place();
               codeStream.iconst_1();
               endLabel.place();
            }
         } else {
            this.left.generateCode(currentScope, codeStream, false);
            this.right.generateCode(currentScope, codeStream, false);
         }

      } else {
         cst = this.right.optimizedBooleanConstant();
         BranchLabel falseLabel;
         if (cst != Constant.NotAConstant) {
            if (cst.booleanValue() == isEqualOperator) {
               this.left.generateCode(currentScope, codeStream, valueRequired);
               this.right.generateCode(currentScope, codeStream, false);
            } else if (valueRequired) {
               falseLabel = new BranchLabel(codeStream);
               this.left.generateOptimizedBoolean(currentScope, codeStream, (BranchLabel)null, falseLabel, valueRequired);
               this.right.generateCode(currentScope, codeStream, false);
               codeStream.iconst_0();
               if ((this.bits & 16) != 0) {
                  codeStream.generateImplicitConversion(this.implicitConversion);
                  codeStream.generateReturnBytecode(this);
                  falseLabel.place();
                  codeStream.iconst_1();
               } else {
                  endLabel = new BranchLabel(codeStream);
                  codeStream.goto_(endLabel);
                  codeStream.decrStackSize(1);
                  falseLabel.place();
                  codeStream.iconst_1();
                  endLabel.place();
               }
            } else {
               this.left.generateCode(currentScope, codeStream, false);
               this.right.generateCode(currentScope, codeStream, false);
            }

         } else {
            this.left.generateCode(currentScope, codeStream, valueRequired);
            this.right.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (isEqualOperator) {
                  codeStream.if_icmpne(falseLabel = new BranchLabel(codeStream));
                  codeStream.iconst_1();
                  if ((this.bits & 16) != 0) {
                     codeStream.generateImplicitConversion(this.implicitConversion);
                     codeStream.generateReturnBytecode(this);
                     falseLabel.place();
                     codeStream.iconst_0();
                  } else {
                     endLabel = new BranchLabel(codeStream);
                     codeStream.goto_(endLabel);
                     codeStream.decrStackSize(1);
                     falseLabel.place();
                     codeStream.iconst_0();
                     endLabel.place();
                  }
               } else {
                  codeStream.ixor();
               }
            }

         }
      }
   }

   public void generateOptimizedBooleanEqual(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      boolean inline;
      if (this.left.constant != Constant.NotAConstant) {
         inline = this.left.constant.booleanValue();
         this.right.generateOptimizedBoolean(currentScope, codeStream, inline ? trueLabel : falseLabel, inline ? falseLabel : trueLabel, valueRequired);
      } else if (this.right.constant != Constant.NotAConstant) {
         inline = this.right.constant.booleanValue();
         this.left.generateOptimizedBoolean(currentScope, codeStream, inline ? trueLabel : falseLabel, inline ? falseLabel : trueLabel, valueRequired);
      } else {
         this.left.generateCode(currentScope, codeStream, valueRequired);
         this.right.generateCode(currentScope, codeStream, valueRequired);
         int pc = codeStream.position;
         if (valueRequired) {
            if (falseLabel == null) {
               if (trueLabel != null) {
                  codeStream.if_icmpeq(trueLabel);
               }
            } else if (trueLabel == null) {
               codeStream.if_icmpne(falseLabel);
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceEnd);
      }
   }

   public void generateNonBooleanEqual(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      boolean isEqualOperator = (this.bits & 4032) >> 6 == 18;
      BranchLabel endLabel;
      if ((this.left.implicitConversion & 255) >> 4 == 10) {
         Constant cst;
         BranchLabel endLabel;
         if ((cst = this.left.constant) != Constant.NotAConstant && cst.intValue() == 0) {
            this.right.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               endLabel = new BranchLabel(codeStream);
               if (isEqualOperator) {
                  codeStream.ifne(endLabel);
               } else {
                  codeStream.ifeq(endLabel);
               }

               codeStream.iconst_1();
               if ((this.bits & 16) != 0) {
                  codeStream.generateImplicitConversion(this.implicitConversion);
                  codeStream.generateReturnBytecode(this);
                  endLabel.place();
                  codeStream.iconst_0();
               } else {
                  endLabel = new BranchLabel(codeStream);
                  codeStream.goto_(endLabel);
                  codeStream.decrStackSize(1);
                  endLabel.place();
                  codeStream.iconst_0();
                  endLabel.place();
               }
            }

            return;
         }

         if ((cst = this.right.constant) != Constant.NotAConstant && cst.intValue() == 0) {
            this.left.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               endLabel = new BranchLabel(codeStream);
               if (isEqualOperator) {
                  codeStream.ifne(endLabel);
               } else {
                  codeStream.ifeq(endLabel);
               }

               codeStream.iconst_1();
               if ((this.bits & 16) != 0) {
                  codeStream.generateImplicitConversion(this.implicitConversion);
                  codeStream.generateReturnBytecode(this);
                  endLabel.place();
                  codeStream.iconst_0();
               } else {
                  endLabel = new BranchLabel(codeStream);
                  codeStream.goto_(endLabel);
                  codeStream.decrStackSize(1);
                  endLabel.place();
                  codeStream.iconst_0();
                  endLabel.place();
               }
            }

            return;
         }
      }

      BranchLabel falseLabel;
      if (this.right instanceof NullLiteral) {
         if (this.left instanceof NullLiteral) {
            if (valueRequired) {
               if (isEqualOperator) {
                  codeStream.iconst_1();
               } else {
                  codeStream.iconst_0();
               }
            }
         } else {
            this.left.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               falseLabel = new BranchLabel(codeStream);
               if (isEqualOperator) {
                  codeStream.ifnonnull(falseLabel);
               } else {
                  codeStream.ifnull(falseLabel);
               }

               codeStream.iconst_1();
               if ((this.bits & 16) != 0) {
                  codeStream.generateImplicitConversion(this.implicitConversion);
                  codeStream.generateReturnBytecode(this);
                  falseLabel.place();
                  codeStream.iconst_0();
               } else {
                  endLabel = new BranchLabel(codeStream);
                  codeStream.goto_(endLabel);
                  codeStream.decrStackSize(1);
                  falseLabel.place();
                  codeStream.iconst_0();
                  endLabel.place();
               }
            }
         }

      } else if (this.left instanceof NullLiteral) {
         this.right.generateCode(currentScope, codeStream, valueRequired);
         if (valueRequired) {
            falseLabel = new BranchLabel(codeStream);
            if (isEqualOperator) {
               codeStream.ifnonnull(falseLabel);
            } else {
               codeStream.ifnull(falseLabel);
            }

            codeStream.iconst_1();
            if ((this.bits & 16) != 0) {
               codeStream.generateImplicitConversion(this.implicitConversion);
               codeStream.generateReturnBytecode(this);
               falseLabel.place();
               codeStream.iconst_0();
            } else {
               endLabel = new BranchLabel(codeStream);
               codeStream.goto_(endLabel);
               codeStream.decrStackSize(1);
               falseLabel.place();
               codeStream.iconst_0();
               endLabel.place();
            }
         }

      } else {
         this.left.generateCode(currentScope, codeStream, valueRequired);
         this.right.generateCode(currentScope, codeStream, valueRequired);
         if (valueRequired) {
            falseLabel = new BranchLabel(codeStream);
            if (isEqualOperator) {
               switch ((this.left.implicitConversion & 255) >> 4) {
                  case 7:
                     codeStream.lcmp();
                     codeStream.ifne(falseLabel);
                     break;
                  case 8:
                     codeStream.dcmpl();
                     codeStream.ifne(falseLabel);
                     break;
                  case 9:
                     codeStream.fcmpl();
                     codeStream.ifne(falseLabel);
                     break;
                  case 10:
                     codeStream.if_icmpne(falseLabel);
                     break;
                  default:
                     codeStream.if_acmpne(falseLabel);
               }
            } else {
               switch ((this.left.implicitConversion & 255) >> 4) {
                  case 7:
                     codeStream.lcmp();
                     codeStream.ifeq(falseLabel);
                     break;
                  case 8:
                     codeStream.dcmpl();
                     codeStream.ifeq(falseLabel);
                     break;
                  case 9:
                     codeStream.fcmpl();
                     codeStream.ifeq(falseLabel);
                     break;
                  case 10:
                     codeStream.if_icmpeq(falseLabel);
                     break;
                  default:
                     codeStream.if_acmpeq(falseLabel);
               }
            }

            codeStream.iconst_1();
            if ((this.bits & 16) != 0) {
               codeStream.generateImplicitConversion(this.implicitConversion);
               codeStream.generateReturnBytecode(this);
               falseLabel.place();
               codeStream.iconst_0();
            } else {
               endLabel = new BranchLabel(codeStream);
               codeStream.goto_(endLabel);
               codeStream.decrStackSize(1);
               falseLabel.place();
               codeStream.iconst_0();
               endLabel.place();
            }
         }

      }
   }

   public void generateOptimizedNonBooleanEqual(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      int pc = codeStream.position;
      Constant inline;
      if ((inline = this.right.constant) != Constant.NotAConstant && (this.left.implicitConversion & 255) >> 4 == 10 && inline.intValue() == 0) {
         this.left.generateCode(currentScope, codeStream, valueRequired);
         if (valueRequired) {
            if (falseLabel == null) {
               if (trueLabel != null) {
                  codeStream.ifeq(trueLabel);
               }
            } else if (trueLabel == null) {
               codeStream.ifne(falseLabel);
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      } else if ((inline = this.left.constant) != Constant.NotAConstant && (this.left.implicitConversion & 255) >> 4 == 10 && inline.intValue() == 0) {
         this.right.generateCode(currentScope, codeStream, valueRequired);
         if (valueRequired) {
            if (falseLabel == null) {
               if (trueLabel != null) {
                  codeStream.ifeq(trueLabel);
               }
            } else if (trueLabel == null) {
               codeStream.ifne(falseLabel);
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      } else if (this.right instanceof NullLiteral) {
         if (this.left instanceof NullLiteral) {
            if (valueRequired && falseLabel == null && trueLabel != null) {
               codeStream.goto_(trueLabel);
            }
         } else {
            this.left.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.ifnull(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.ifnonnull(falseLabel);
               }
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      } else if (this.left instanceof NullLiteral) {
         this.right.generateCode(currentScope, codeStream, valueRequired);
         if (valueRequired) {
            if (falseLabel == null) {
               if (trueLabel != null) {
                  codeStream.ifnull(trueLabel);
               }
            } else if (trueLabel == null) {
               codeStream.ifnonnull(falseLabel);
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      } else {
         this.left.generateCode(currentScope, codeStream, valueRequired);
         this.right.generateCode(currentScope, codeStream, valueRequired);
         if (valueRequired) {
            if (falseLabel == null) {
               if (trueLabel != null) {
                  switch ((this.left.implicitConversion & 255) >> 4) {
                     case 7:
                        codeStream.lcmp();
                        codeStream.ifeq(trueLabel);
                        break;
                     case 8:
                        codeStream.dcmpl();
                        codeStream.ifeq(trueLabel);
                        break;
                     case 9:
                        codeStream.fcmpl();
                        codeStream.ifeq(trueLabel);
                        break;
                     case 10:
                        codeStream.if_icmpeq(trueLabel);
                        break;
                     default:
                        codeStream.if_acmpeq(trueLabel);
                  }
               }
            } else if (trueLabel == null) {
               switch ((this.left.implicitConversion & 255) >> 4) {
                  case 7:
                     codeStream.lcmp();
                     codeStream.ifne(falseLabel);
                     break;
                  case 8:
                     codeStream.dcmpl();
                     codeStream.ifne(falseLabel);
                     break;
                  case 9:
                     codeStream.fcmpl();
                     codeStream.ifne(falseLabel);
                     break;
                  case 10:
                     codeStream.if_icmpne(falseLabel);
                     break;
                  default:
                     codeStream.if_acmpne(falseLabel);
               }
            }
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public boolean isCompactableOperation() {
      return false;
   }

   public TypeBinding resolveType(BlockScope scope) {
      boolean leftIsCast;
      Expression var10000;
      if (leftIsCast = this.left instanceof CastExpression) {
         var10000 = this.left;
         var10000.bits |= 32;
      }

      TypeBinding originalLeftType = this.left.resolveType(scope);
      boolean rightIsCast;
      if (rightIsCast = this.right instanceof CastExpression) {
         var10000 = this.right;
         var10000.bits |= 32;
      }

      TypeBinding originalRightType = this.right.resolveType(scope);
      if (originalLeftType != null && originalRightType != null) {
         CompilerOptions compilerOptions = scope.compilerOptions();
         if (compilerOptions.complainOnUninternedIdentityComparison && originalRightType.hasTypeBit(16) && originalLeftType.hasTypeBit(16)) {
            scope.problemReporter().uninternedIdentityComparison(this, originalLeftType, originalRightType, scope.referenceCompilationUnit());
         }

         boolean use15specifics = compilerOptions.sourceLevel >= 3211264L;
         TypeBinding leftType = originalLeftType;
         TypeBinding rightType = originalRightType;
         if (use15specifics) {
            if (originalLeftType != TypeBinding.NULL && originalLeftType.isBaseType()) {
               if (!originalRightType.isBaseType()) {
                  rightType = scope.environment().computeBoxingType(originalRightType);
               }
            } else if (originalRightType != TypeBinding.NULL && originalRightType.isBaseType()) {
               leftType = scope.environment().computeBoxingType(originalLeftType);
            }
         }

         Binding leftDirect;
         if (leftType.isBaseType() && rightType.isBaseType()) {
            int leftTypeID = leftType.id;
            int rightTypeID = rightType.id;
            int operatorSignature = OperatorSignatures[18][(leftTypeID << 4) + rightTypeID];
            this.left.computeConversion(scope, TypeBinding.wellKnownType(scope, operatorSignature >>> 16 & 15), originalLeftType);
            this.right.computeConversion(scope, TypeBinding.wellKnownType(scope, operatorSignature >>> 8 & 15), originalRightType);
            this.bits |= operatorSignature & 15;
            if ((operatorSignature & 15) == 0) {
               this.constant = Constant.NotAConstant;
               scope.problemReporter().invalidOperator((BinaryExpression)this, leftType, rightType);
               return null;
            } else {
               if (leftIsCast || rightIsCast) {
                  CastExpression.checkNeedForArgumentCasts(scope, 18, operatorSignature, this.left, leftType.id, leftIsCast, this.right, rightType.id, rightIsCast);
               }

               this.computeConstant(leftType, rightType);
               leftDirect = Expression.getDirectBinding(this.left);
               if (leftDirect != null && leftDirect == Expression.getDirectBinding(this.right)) {
                  if (leftTypeID != 8 && leftTypeID != 9 && !(this.right instanceof Assignment)) {
                     scope.problemReporter().comparingIdenticalExpressions(this);
                  }
               } else if (this.constant != Constant.NotAConstant) {
                  int operator = (this.bits & 4032) >> 6;
                  if (operator == 18 && this.constant == BooleanConstant.fromValue(true) || operator == 29 && this.constant == BooleanConstant.fromValue(false)) {
                     scope.problemReporter().comparingIdenticalExpressions(this);
                  }
               }

               return this.resolvedType = TypeBinding.BOOLEAN;
            }
         } else if (leftType.isBaseType() && leftType != TypeBinding.NULL || rightType.isBaseType() && rightType != TypeBinding.NULL || !this.checkCastTypesCompatibility(scope, leftType, rightType, (Expression)null) && !this.checkCastTypesCompatibility(scope, rightType, leftType, (Expression)null)) {
            this.constant = Constant.NotAConstant;
            scope.problemReporter().notCompatibleTypesError(this, leftType, rightType);
            return null;
         } else {
            if (rightType.id == 11 && leftType.id == 11) {
               this.computeConstant(leftType, rightType);
            } else {
               this.constant = Constant.NotAConstant;
            }

            TypeBinding objectType = scope.getJavaLangObject();
            this.left.computeConversion(scope, objectType, leftType);
            this.right.computeConversion(scope, objectType, rightType);
            boolean unnecessaryLeftCast = (this.left.bits & 16384) != 0;
            boolean unnecessaryRightCast = (this.right.bits & 16384) != 0;
            if (unnecessaryLeftCast || unnecessaryRightCast) {
               TypeBinding alternateLeftType = unnecessaryLeftCast ? ((CastExpression)this.left).expression.resolvedType : leftType;
               TypeBinding alternateRightType = unnecessaryRightCast ? ((CastExpression)this.right).expression.resolvedType : rightType;
               if (!this.isCastNeeded(alternateLeftType, alternateRightType) && (this.checkCastTypesCompatibility(scope, alternateLeftType, alternateRightType, (Expression)null) || this.checkCastTypesCompatibility(scope, alternateRightType, alternateLeftType, (Expression)null))) {
                  if (unnecessaryLeftCast) {
                     scope.problemReporter().unnecessaryCast((CastExpression)this.left);
                  }

                  if (unnecessaryRightCast) {
                     scope.problemReporter().unnecessaryCast((CastExpression)this.right);
                  }
               }
            }

            leftDirect = Expression.getDirectBinding(this.left);
            if (leftDirect != null && leftDirect == Expression.getDirectBinding(this.right) && !(this.right instanceof Assignment)) {
               scope.problemReporter().comparingIdenticalExpressions(this);
            }

            return this.resolvedType = TypeBinding.BOOLEAN;
         }
      } else {
         this.constant = Constant.NotAConstant;
         return null;
      }
   }

   private boolean isCastNeeded(TypeBinding leftType, TypeBinding rightType) {
      if (leftType.isParameterizedType()) {
         return rightType.isBaseType();
      } else {
         return rightType.isParameterizedType() ? leftType.isBaseType() : false;
      }
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.left.traverse(visitor, scope);
         this.right.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}
