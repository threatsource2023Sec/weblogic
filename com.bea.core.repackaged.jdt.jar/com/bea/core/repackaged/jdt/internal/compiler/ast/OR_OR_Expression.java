package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class OR_OR_Expression extends BinaryExpression {
   int rightInitStateIndex = -1;
   int mergedInitStateIndex = -1;

   public OR_OR_Expression(Expression left, Expression right, int operator) {
      super(left, right, operator);
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      Constant cst = this.left.optimizedBooleanConstant();
      boolean isLeftOptimizedTrue = cst != Constant.NotAConstant && cst.booleanValue();
      boolean isLeftOptimizedFalse = cst != Constant.NotAConstant && !cst.booleanValue();
      FlowInfo leftInfo;
      if (isLeftOptimizedFalse) {
         FlowInfo mergedInfo = this.left.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits();
         flowContext.expireNullCheckedFieldInfo();
         leftInfo = this.right.analyseCode(currentScope, flowContext, mergedInfo);
         flowContext.expireNullCheckedFieldInfo();
         this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(leftInfo);
         return leftInfo;
      } else {
         leftInfo = this.left.analyseCode(currentScope, flowContext, flowInfo);
         if ((flowContext.tagBits & 4) == 0) {
            flowContext.expireNullCheckedFieldInfo();
         }

         FlowInfo rightInfo = leftInfo.initsWhenFalse().unconditionalCopy();
         this.rightInitStateIndex = currentScope.methodScope().recordInitializationStates(rightInfo);
         int previousMode = rightInfo.reachMode();
         if (isLeftOptimizedTrue && (rightInfo.reachMode() & 3) == 0) {
            currentScope.problemReporter().fakeReachable(this.right);
            rightInfo.setReachMode(1);
         }

         FlowInfo rightInfo = this.right.analyseCode(currentScope, flowContext, rightInfo);
         if ((flowContext.tagBits & 4) == 0) {
            flowContext.expireNullCheckedFieldInfo();
         }

         this.left.checkNPEbyUnboxing(currentScope, flowContext, flowInfo);
         this.right.checkNPEbyUnboxing(currentScope, flowContext, leftInfo.initsWhenFalse());
         FlowInfo leftInfoWhenTrueForMerging = leftInfo.initsWhenTrue().unconditionalCopy().addPotentialInitializationsFrom(rightInfo.unconditionalInitsWithoutSideEffect());
         FlowInfo mergedInfo = FlowInfo.conditional(leftInfoWhenTrueForMerging.unconditionalInits().mergedWith(rightInfo.safeInitsWhenTrue().setReachMode(previousMode).unconditionalInits()), rightInfo.initsWhenFalse());
         this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(mergedInfo);
         return mergedInfo;
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
         Constant cst = this.right.constant;
         if (cst != Constant.NotAConstant) {
            if (cst.booleanValue()) {
               this.left.generateCode(currentScope, codeStream, false);
               if (valueRequired) {
                  codeStream.iconst_1();
               }
            } else {
               this.left.generateCode(currentScope, codeStream, valueRequired);
            }

            if (this.mergedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            }

            codeStream.generateImplicitConversion(this.implicitConversion);
            codeStream.recordPositionsFrom(pc, this.sourceStart);
         } else {
            BranchLabel trueLabel;
            boolean leftIsConst;
            boolean leftIsTrue;
            boolean rightIsConst;
            boolean rightIsTrue;
            label108: {
               trueLabel = new BranchLabel(codeStream);
               cst = this.left.optimizedBooleanConstant();
               leftIsConst = cst != Constant.NotAConstant;
               leftIsTrue = leftIsConst && cst.booleanValue();
               cst = this.right.optimizedBooleanConstant();
               rightIsConst = cst != Constant.NotAConstant;
               rightIsTrue = rightIsConst && cst.booleanValue();
               if (leftIsConst) {
                  this.left.generateCode(currentScope, codeStream, false);
                  if (leftIsTrue) {
                     break label108;
                  }
               } else {
                  this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, (BranchLabel)null, true);
               }

               if (this.rightInitStateIndex != -1) {
                  codeStream.addDefinitelyAssignedVariables(currentScope, this.rightInitStateIndex);
               }

               if (rightIsConst) {
                  this.right.generateCode(currentScope, codeStream, false);
               } else {
                  this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, (BranchLabel)null, valueRequired);
               }
            }

            if (this.mergedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            }

            if (valueRequired) {
               if (leftIsConst && leftIsTrue) {
                  codeStream.iconst_1();
                  codeStream.recordPositionsFrom(codeStream.position, this.left.sourceEnd);
               } else {
                  if (rightIsConst && rightIsTrue) {
                     codeStream.iconst_1();
                     codeStream.recordPositionsFrom(codeStream.position, this.left.sourceEnd);
                  } else {
                     codeStream.iconst_0();
                  }

                  if (trueLabel.forwardReferenceCount() > 0) {
                     if ((this.bits & 16) != 0) {
                        codeStream.generateImplicitConversion(this.implicitConversion);
                        codeStream.generateReturnBytecode(this);
                        trueLabel.place();
                        codeStream.iconst_1();
                     } else {
                        BranchLabel endLabel;
                        codeStream.goto_(endLabel = new BranchLabel(codeStream));
                        codeStream.decrStackSize(1);
                        trueLabel.place();
                        codeStream.iconst_1();
                        endLabel.place();
                     }
                  } else {
                     trueLabel.place();
                  }
               }

               codeStream.generateImplicitConversion(this.implicitConversion);
               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            } else {
               trueLabel.place();
            }

         }
      }
   }

   public void generateOptimizedBoolean(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      if (this.constant != Constant.NotAConstant) {
         super.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
      } else {
         Constant cst = this.right.constant;
         if (cst != Constant.NotAConstant && !cst.booleanValue()) {
            int pc = codeStream.position;
            this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
            if (this.mergedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            }

            codeStream.recordPositionsFrom(pc, this.sourceStart);
         } else {
            cst = this.left.optimizedBooleanConstant();
            boolean leftIsConst = cst != Constant.NotAConstant;
            boolean leftIsTrue = leftIsConst && cst.booleanValue();
            cst = this.right.optimizedBooleanConstant();
            boolean rightIsConst = cst != Constant.NotAConstant;
            boolean rightIsTrue = rightIsConst && cst.booleanValue();
            if (falseLabel == null) {
               if (trueLabel != null) {
                  this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, (BranchLabel)null, !leftIsConst);
                  if (leftIsTrue) {
                     if (valueRequired) {
                        codeStream.goto_(trueLabel);
                     }

                     codeStream.recordPositionsFrom(codeStream.position, this.left.sourceEnd);
                  } else {
                     if (this.rightInitStateIndex != -1) {
                        codeStream.addDefinitelyAssignedVariables(currentScope, this.rightInitStateIndex);
                     }

                     this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, (BranchLabel)null, valueRequired && !rightIsConst);
                     if (valueRequired && rightIsTrue) {
                        codeStream.goto_(trueLabel);
                        codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
                     }
                  }
               }
            } else if (trueLabel == null) {
               BranchLabel internalTrueLabel = new BranchLabel(codeStream);
               this.left.generateOptimizedBoolean(currentScope, codeStream, internalTrueLabel, (BranchLabel)null, !leftIsConst);
               if (leftIsTrue) {
                  internalTrueLabel.place();
               } else {
                  if (this.rightInitStateIndex != -1) {
                     codeStream.addDefinitelyAssignedVariables(currentScope, this.rightInitStateIndex);
                  }

                  this.right.generateOptimizedBoolean(currentScope, codeStream, (BranchLabel)null, falseLabel, valueRequired && !rightIsConst);
                  int pc = codeStream.position;
                  if (valueRequired && rightIsConst && !rightIsTrue) {
                     codeStream.goto_(falseLabel);
                     codeStream.recordPositionsFrom(pc, this.sourceEnd);
                  }

                  internalTrueLabel.place();
               }
            }

            if (this.mergedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            }

         }
      }
   }

   public boolean isCompactableOperation() {
      return false;
   }

   public TypeBinding resolveType(BlockScope scope) {
      TypeBinding result = super.resolveType(scope);
      Binding leftDirect = Expression.getDirectBinding(this.left);
      if (leftDirect != null && leftDirect == Expression.getDirectBinding(this.right) && !(this.right instanceof Assignment)) {
         scope.problemReporter().comparingIdenticalExpressions(this);
      }

      return result;
   }

   public void traverse(ASTVisitor visitor, BlockScope scope) {
      if (visitor.visit(this, scope)) {
         this.left.traverse(visitor, scope);
         this.right.traverse(visitor, scope);
      }

      visitor.endVisit(this, scope);
   }
}
