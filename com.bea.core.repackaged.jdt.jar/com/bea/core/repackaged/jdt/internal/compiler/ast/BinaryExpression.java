package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class BinaryExpression extends OperatorExpression {
   public Expression left;
   public Expression right;
   public Constant optimizedBooleanConstant;

   public BinaryExpression(Expression left, Expression right, int operator) {
      this.left = left;
      this.right = right;
      this.bits |= operator << 6;
      this.sourceStart = left.sourceStart;
      this.sourceEnd = right.sourceEnd;
   }

   public BinaryExpression(BinaryExpression expression) {
      this.left = expression.left;
      this.right = expression.right;
      this.bits = expression.bits;
      this.sourceStart = expression.sourceStart;
      this.sourceEnd = expression.sourceEnd;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      UnconditionalFlowInfo var5;
      try {
         if (this.resolvedType.id != 11) {
            FlowInfo flowInfo = this.left.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits();
            this.left.checkNPE(currentScope, flowContext, flowInfo);
            if ((this.bits & 4032) >> 6 != 2) {
               flowContext.expireNullCheckedFieldInfo();
            }

            flowInfo = this.right.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits();
            this.right.checkNPE(currentScope, flowContext, flowInfo);
            if ((this.bits & 4032) >> 6 != 2) {
               flowContext.expireNullCheckedFieldInfo();
            }

            var5 = flowInfo;
            return var5;
         }

         var5 = this.right.analyseCode(currentScope, flowContext, this.left.analyseCode(currentScope, flowContext, flowInfo).unconditionalInits()).unconditionalInits();
      } finally {
         flowContext.recordAbruptExit();
      }

      return var5;
   }

   public void computeConstant(BlockScope scope, int leftId, int rightId) {
      if (this.left.constant != Constant.NotAConstant && this.right.constant != Constant.NotAConstant) {
         try {
            this.constant = Constant.computeConstantOperation(this.left.constant, leftId, (this.bits & 4032) >> 6, this.right.constant, rightId);
         } catch (ArithmeticException var4) {
            this.constant = Constant.NotAConstant;
         }
      } else {
         this.constant = Constant.NotAConstant;
         this.optimizedBooleanConstant(leftId, (this.bits & 4032) >> 6, rightId);
      }

   }

   public Constant optimizedBooleanConstant() {
      return this.optimizedBooleanConstant == null ? this.constant : this.optimizedBooleanConstant;
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      int pc = codeStream.position;
      if (this.constant != Constant.NotAConstant) {
         if (valueRequired) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      } else {
         BranchLabel falseLabel;
         BranchLabel endLabel;
         label314:
         switch ((this.bits & 4032) >> 6) {
            case 2:
               switch (this.bits & 15) {
                  case 5:
                     this.generateLogicalAnd(currentScope, codeStream, valueRequired);
                  case 6:
                  case 8:
                  case 9:
                  default:
                     break label314;
                  case 7:
                     if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 7 && this.left.constant.longValue() == 0L) {
                        this.right.generateCode(currentScope, codeStream, false);
                        if (valueRequired) {
                           codeStream.lconst_0();
                        }
                     } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 7 && this.right.constant.longValue() == 0L) {
                        this.left.generateCode(currentScope, codeStream, false);
                        if (valueRequired) {
                           codeStream.lconst_0();
                        }
                     } else {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                        if (valueRequired) {
                           codeStream.land();
                        }
                     }
                     break label314;
                  case 10:
                     if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 10 && this.left.constant.intValue() == 0) {
                        this.right.generateCode(currentScope, codeStream, false);
                        if (valueRequired) {
                           codeStream.iconst_0();
                        }
                     } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 10 && this.right.constant.intValue() == 0) {
                        this.left.generateCode(currentScope, codeStream, false);
                        if (valueRequired) {
                           codeStream.iconst_0();
                        }
                     } else {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                        if (valueRequired) {
                           codeStream.iand();
                        }
                     }
                     break label314;
               }
            case 3:
               switch (this.bits & 15) {
                  case 5:
                     this.generateLogicalOr(currentScope, codeStream, valueRequired);
                  case 6:
                  case 8:
                  case 9:
                  default:
                     break label314;
                  case 7:
                     if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 7 && this.left.constant.longValue() == 0L) {
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                     } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 7 && this.right.constant.longValue() == 0L) {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                     } else {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                        if (valueRequired) {
                           codeStream.lor();
                        }
                     }
                     break label314;
                  case 10:
                     if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 10 && this.left.constant.intValue() == 0) {
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                     } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 10 && this.right.constant.intValue() == 0) {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                     } else {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                        if (valueRequired) {
                           codeStream.ior();
                        }
                     }
                     break label314;
               }
            case 4:
               this.generateOptimizedLessThan(currentScope, codeStream, (BranchLabel)null, falseLabel = new BranchLabel(codeStream), valueRequired);
               if (valueRequired) {
                  codeStream.iconst_1();
                  if ((this.bits & 16) != 0) {
                     codeStream.generateImplicitConversion(this.implicitConversion);
                     codeStream.generateReturnBytecode(this);
                     falseLabel.place();
                     codeStream.iconst_0();
                  } else {
                     codeStream.goto_(endLabel = new BranchLabel(codeStream));
                     codeStream.decrStackSize(1);
                     falseLabel.place();
                     codeStream.iconst_0();
                     endLabel.place();
                  }
               }
               break;
            case 5:
               this.generateOptimizedLessThanOrEqual(currentScope, codeStream, (BranchLabel)null, falseLabel = new BranchLabel(codeStream), valueRequired);
               if (valueRequired) {
                  codeStream.iconst_1();
                  if ((this.bits & 16) != 0) {
                     codeStream.generateImplicitConversion(this.implicitConversion);
                     codeStream.generateReturnBytecode(this);
                     falseLabel.place();
                     codeStream.iconst_0();
                  } else {
                     codeStream.goto_(endLabel = new BranchLabel(codeStream));
                     codeStream.decrStackSize(1);
                     falseLabel.place();
                     codeStream.iconst_0();
                     endLabel.place();
                  }
               }
               break;
            case 6:
               this.generateOptimizedGreaterThan(currentScope, codeStream, (BranchLabel)null, falseLabel = new BranchLabel(codeStream), valueRequired);
               if (valueRequired) {
                  codeStream.iconst_1();
                  if ((this.bits & 16) != 0) {
                     codeStream.generateImplicitConversion(this.implicitConversion);
                     codeStream.generateReturnBytecode(this);
                     falseLabel.place();
                     codeStream.iconst_0();
                  } else {
                     codeStream.goto_(endLabel = new BranchLabel(codeStream));
                     codeStream.decrStackSize(1);
                     falseLabel.place();
                     codeStream.iconst_0();
                     endLabel.place();
                  }
               }
               break;
            case 7:
               this.generateOptimizedGreaterThanOrEqual(currentScope, codeStream, (BranchLabel)null, falseLabel = new BranchLabel(codeStream), valueRequired);
               if (valueRequired) {
                  codeStream.iconst_1();
                  if ((this.bits & 16) != 0) {
                     codeStream.generateImplicitConversion(this.implicitConversion);
                     codeStream.generateReturnBytecode(this);
                     falseLabel.place();
                     codeStream.iconst_0();
                  } else {
                     codeStream.goto_(endLabel = new BranchLabel(codeStream));
                     codeStream.decrStackSize(1);
                     falseLabel.place();
                     codeStream.iconst_0();
                     endLabel.place();
                  }
               }
               break;
            case 8:
               switch (this.bits & 15) {
                  case 5:
                     this.generateLogicalXor(currentScope, codeStream, valueRequired);
                  case 6:
                  case 8:
                  case 9:
                  default:
                     break label314;
                  case 7:
                     if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 7 && this.left.constant.longValue() == 0L) {
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                     } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 7 && this.right.constant.longValue() == 0L) {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                     } else {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                        if (valueRequired) {
                           codeStream.lxor();
                        }
                     }
                     break label314;
                  case 10:
                     if (this.left.constant != Constant.NotAConstant && this.left.constant.typeID() == 10 && this.left.constant.intValue() == 0) {
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                     } else if (this.right.constant != Constant.NotAConstant && this.right.constant.typeID() == 10 && this.right.constant.intValue() == 0) {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                     } else {
                        this.left.generateCode(currentScope, codeStream, valueRequired);
                        this.right.generateCode(currentScope, codeStream, valueRequired);
                        if (valueRequired) {
                           codeStream.ixor();
                        }
                     }
                     break label314;
               }
            case 9:
               switch (this.bits & 15) {
                  case 7:
                     this.left.generateCode(currentScope, codeStream, true);
                     this.right.generateCode(currentScope, codeStream, true);
                     codeStream.ldiv();
                     if (!valueRequired) {
                        codeStream.pop2();
                     }
                     break label314;
                  case 8:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.ddiv();
                     }
                     break label314;
                  case 9:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.fdiv();
                     }
                     break label314;
                  case 10:
                     this.left.generateCode(currentScope, codeStream, true);
                     this.right.generateCode(currentScope, codeStream, true);
                     codeStream.idiv();
                     if (!valueRequired) {
                        codeStream.pop();
                     }
                  default:
                     break label314;
               }
            case 10:
               switch (this.bits & 15) {
                  case 7:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.lshl();
                     }
                  case 8:
                  case 9:
                  default:
                     break;
                  case 10:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.ishl();
                     }
               }
            case 11:
            case 12:
            case 18:
            default:
               break;
            case 13:
               switch (this.bits & 15) {
                  case 7:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.lsub();
                     }
                     break label314;
                  case 8:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.dsub();
                     }
                     break label314;
                  case 9:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.fsub();
                     }
                     break label314;
                  case 10:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.isub();
                     }
                  default:
                     break label314;
               }
            case 14:
               switch (this.bits & 15) {
                  case 7:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.ladd();
                     }
                     break label314;
                  case 8:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.dadd();
                     }
                     break label314;
                  case 9:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.fadd();
                     }
                     break label314;
                  case 10:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.iadd();
                     }
                     break label314;
                  case 11:
                     codeStream.generateStringConcatenationAppend(currentScope, this.left, this.right);
                     if (!valueRequired) {
                        codeStream.pop();
                     }
                  default:
                     break label314;
               }
            case 15:
               switch (this.bits & 15) {
                  case 7:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.lmul();
                     }
                     break label314;
                  case 8:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.dmul();
                     }
                     break label314;
                  case 9:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.fmul();
                     }
                     break label314;
                  case 10:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.imul();
                     }
                  default:
                     break label314;
               }
            case 16:
               switch (this.bits & 15) {
                  case 7:
                     this.left.generateCode(currentScope, codeStream, true);
                     this.right.generateCode(currentScope, codeStream, true);
                     codeStream.lrem();
                     if (!valueRequired) {
                        codeStream.pop2();
                     }
                     break label314;
                  case 8:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.drem();
                     }
                     break label314;
                  case 9:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.frem();
                     }
                     break label314;
                  case 10:
                     this.left.generateCode(currentScope, codeStream, true);
                     this.right.generateCode(currentScope, codeStream, true);
                     codeStream.irem();
                     if (!valueRequired) {
                        codeStream.pop();
                     }
                  default:
                     break label314;
               }
            case 17:
               switch (this.bits & 15) {
                  case 7:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.lshr();
                     }
                  case 8:
                  case 9:
                  default:
                     break label314;
                  case 10:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.ishr();
                     }
                     break label314;
               }
            case 19:
               switch (this.bits & 15) {
                  case 7:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.lushr();
                     }
                  case 8:
                  case 9:
                  default:
                     break;
                  case 10:
                     this.left.generateCode(currentScope, codeStream, valueRequired);
                     this.right.generateCode(currentScope, codeStream, valueRequired);
                     if (valueRequired) {
                        codeStream.iushr();
                     }
               }
         }

         if (valueRequired) {
            codeStream.generateImplicitConversion(this.implicitConversion);
         }

         codeStream.recordPositionsFrom(pc, this.sourceStart);
      }
   }

   public void generateOptimizedBoolean(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      if (this.constant != Constant.NotAConstant && this.constant.typeID() == 5) {
         super.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
      } else {
         switch ((this.bits & 4032) >> 6) {
            case 2:
               this.generateOptimizedLogicalAnd(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               return;
            case 3:
               this.generateOptimizedLogicalOr(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               return;
            case 4:
               this.generateOptimizedLessThan(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               return;
            case 5:
               this.generateOptimizedLessThanOrEqual(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               return;
            case 6:
               this.generateOptimizedGreaterThan(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               return;
            case 7:
               this.generateOptimizedGreaterThanOrEqual(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               return;
            case 8:
               this.generateOptimizedLogicalXor(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               return;
            default:
               super.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
         }
      }
   }

   public void generateOptimizedGreaterThan(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      int promotedTypeID = (this.left.implicitConversion & 255) >> 4;
      if (promotedTypeID == 10) {
         if (this.left.constant != Constant.NotAConstant && this.left.constant.intValue() == 0) {
            this.right.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.iflt(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.ifge(falseLabel);
               }
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }

         if (this.right.constant != Constant.NotAConstant && this.right.constant.intValue() == 0) {
            this.left.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.ifgt(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.ifle(falseLabel);
               }
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         if (falseLabel == null) {
            if (trueLabel != null) {
               switch (promotedTypeID) {
                  case 7:
                     codeStream.lcmp();
                     codeStream.ifgt(trueLabel);
                     break;
                  case 8:
                     codeStream.dcmpl();
                     codeStream.ifgt(trueLabel);
                     break;
                  case 9:
                     codeStream.fcmpl();
                     codeStream.ifgt(trueLabel);
                     break;
                  case 10:
                     codeStream.if_icmpgt(trueLabel);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
               return;
            }
         } else if (trueLabel == null) {
            switch (promotedTypeID) {
               case 7:
                  codeStream.lcmp();
                  codeStream.ifle(falseLabel);
                  break;
               case 8:
                  codeStream.dcmpl();
                  codeStream.ifle(falseLabel);
                  break;
               case 9:
                  codeStream.fcmpl();
                  codeStream.ifle(falseLabel);
                  break;
               case 10:
                  codeStream.if_icmple(falseLabel);
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }
      }

   }

   public void generateOptimizedGreaterThanOrEqual(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      int promotedTypeID = (this.left.implicitConversion & 255) >> 4;
      if (promotedTypeID == 10) {
         if (this.left.constant != Constant.NotAConstant && this.left.constant.intValue() == 0) {
            this.right.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.ifle(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.ifgt(falseLabel);
               }
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }

         if (this.right.constant != Constant.NotAConstant && this.right.constant.intValue() == 0) {
            this.left.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.ifge(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.iflt(falseLabel);
               }
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         if (falseLabel == null) {
            if (trueLabel != null) {
               switch (promotedTypeID) {
                  case 7:
                     codeStream.lcmp();
                     codeStream.ifge(trueLabel);
                     break;
                  case 8:
                     codeStream.dcmpl();
                     codeStream.ifge(trueLabel);
                     break;
                  case 9:
                     codeStream.fcmpl();
                     codeStream.ifge(trueLabel);
                     break;
                  case 10:
                     codeStream.if_icmpge(trueLabel);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
               return;
            }
         } else if (trueLabel == null) {
            switch (promotedTypeID) {
               case 7:
                  codeStream.lcmp();
                  codeStream.iflt(falseLabel);
                  break;
               case 8:
                  codeStream.dcmpl();
                  codeStream.iflt(falseLabel);
                  break;
               case 9:
                  codeStream.fcmpl();
                  codeStream.iflt(falseLabel);
                  break;
               case 10:
                  codeStream.if_icmplt(falseLabel);
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }
      }

   }

   public void generateOptimizedLessThan(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      int promotedTypeID = (this.left.implicitConversion & 255) >> 4;
      if (promotedTypeID == 10) {
         if (this.left.constant != Constant.NotAConstant && this.left.constant.intValue() == 0) {
            this.right.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.ifgt(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.ifle(falseLabel);
               }
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }

         if (this.right.constant != Constant.NotAConstant && this.right.constant.intValue() == 0) {
            this.left.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.iflt(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.ifge(falseLabel);
               }
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         if (falseLabel == null) {
            if (trueLabel != null) {
               switch (promotedTypeID) {
                  case 7:
                     codeStream.lcmp();
                     codeStream.iflt(trueLabel);
                     break;
                  case 8:
                     codeStream.dcmpg();
                     codeStream.iflt(trueLabel);
                     break;
                  case 9:
                     codeStream.fcmpg();
                     codeStream.iflt(trueLabel);
                     break;
                  case 10:
                     codeStream.if_icmplt(trueLabel);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
               return;
            }
         } else if (trueLabel == null) {
            switch (promotedTypeID) {
               case 7:
                  codeStream.lcmp();
                  codeStream.ifge(falseLabel);
                  break;
               case 8:
                  codeStream.dcmpg();
                  codeStream.ifge(falseLabel);
                  break;
               case 9:
                  codeStream.fcmpg();
                  codeStream.ifge(falseLabel);
                  break;
               case 10:
                  codeStream.if_icmpge(falseLabel);
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }
      }

   }

   public void generateOptimizedLessThanOrEqual(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      int promotedTypeID = (this.left.implicitConversion & 255) >> 4;
      if (promotedTypeID == 10) {
         if (this.left.constant != Constant.NotAConstant && this.left.constant.intValue() == 0) {
            this.right.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.ifge(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.iflt(falseLabel);
               }
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }

         if (this.right.constant != Constant.NotAConstant && this.right.constant.intValue() == 0) {
            this.left.generateCode(currentScope, codeStream, valueRequired);
            if (valueRequired) {
               if (falseLabel == null) {
                  if (trueLabel != null) {
                     codeStream.ifle(trueLabel);
                  }
               } else if (trueLabel == null) {
                  codeStream.ifgt(falseLabel);
               }
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         if (falseLabel == null) {
            if (trueLabel != null) {
               switch (promotedTypeID) {
                  case 7:
                     codeStream.lcmp();
                     codeStream.ifle(trueLabel);
                     break;
                  case 8:
                     codeStream.dcmpg();
                     codeStream.ifle(trueLabel);
                     break;
                  case 9:
                     codeStream.fcmpg();
                     codeStream.ifle(trueLabel);
                     break;
                  case 10:
                     codeStream.if_icmple(trueLabel);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
               return;
            }
         } else if (trueLabel == null) {
            switch (promotedTypeID) {
               case 7:
                  codeStream.lcmp();
                  codeStream.ifgt(falseLabel);
                  break;
               case 8:
                  codeStream.dcmpg();
                  codeStream.ifgt(falseLabel);
                  break;
               case 9:
                  codeStream.fcmpg();
                  codeStream.ifgt(falseLabel);
                  break;
               case 10:
                  codeStream.if_icmpgt(falseLabel);
            }

            codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            return;
         }
      }

   }

   public void generateLogicalAnd(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      if ((this.left.implicitConversion & 15) == 5) {
         Constant condConst;
         if ((condConst = this.left.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateCode(currentScope, codeStream, false);
               this.right.generateCode(currentScope, codeStream, valueRequired);
            } else {
               this.left.generateCode(currentScope, codeStream, false);
               this.right.generateCode(currentScope, codeStream, false);
               if (valueRequired) {
                  codeStream.iconst_0();
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            }

            return;
         }

         if ((condConst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateCode(currentScope, codeStream, valueRequired);
               this.right.generateCode(currentScope, codeStream, false);
            } else {
               this.left.generateCode(currentScope, codeStream, false);
               this.right.generateCode(currentScope, codeStream, false);
               if (valueRequired) {
                  codeStream.iconst_0();
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            }

            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         codeStream.iand();
      }

      codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
   }

   public void generateLogicalOr(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      if ((this.left.implicitConversion & 15) == 5) {
         Constant condConst;
         if ((condConst = this.left.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateCode(currentScope, codeStream, false);
               this.right.generateCode(currentScope, codeStream, false);
               if (valueRequired) {
                  codeStream.iconst_1();
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            } else {
               this.left.generateCode(currentScope, codeStream, false);
               this.right.generateCode(currentScope, codeStream, valueRequired);
            }

            return;
         }

         if ((condConst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateCode(currentScope, codeStream, false);
               this.right.generateCode(currentScope, codeStream, false);
               if (valueRequired) {
                  codeStream.iconst_1();
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            } else {
               this.left.generateCode(currentScope, codeStream, valueRequired);
               this.right.generateCode(currentScope, codeStream, false);
            }

            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         codeStream.ior();
      }

      codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
   }

   public void generateLogicalXor(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      if ((this.left.implicitConversion & 15) == 5) {
         Constant condConst;
         if ((condConst = this.left.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateCode(currentScope, codeStream, false);
               if (valueRequired) {
                  codeStream.iconst_1();
               }

               this.right.generateCode(currentScope, codeStream, valueRequired);
               if (valueRequired) {
                  codeStream.ixor();
                  codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
               }
            } else {
               this.left.generateCode(currentScope, codeStream, false);
               this.right.generateCode(currentScope, codeStream, valueRequired);
            }

            return;
         }

         if ((condConst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateCode(currentScope, codeStream, valueRequired);
               this.right.generateCode(currentScope, codeStream, false);
               if (valueRequired) {
                  codeStream.iconst_1();
                  codeStream.ixor();
                  codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
               }
            } else {
               this.left.generateCode(currentScope, codeStream, valueRequired);
               this.right.generateCode(currentScope, codeStream, false);
            }

            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         codeStream.ixor();
      }

      codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
   }

   public void generateOptimizedLogicalAnd(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      if ((this.left.implicitConversion & 15) == 5) {
         Constant condConst;
         if ((condConst = this.left.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
            } else {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               if (valueRequired && falseLabel != null) {
                  codeStream.goto_(falseLabel);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            }

            return;
         }

         if ((condConst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
            } else {
               BranchLabel internalTrueLabel = new BranchLabel(codeStream);
               this.left.generateOptimizedBoolean(currentScope, codeStream, internalTrueLabel, falseLabel, false);
               internalTrueLabel.place();
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               if (valueRequired && falseLabel != null) {
                  codeStream.goto_(falseLabel);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            }

            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         codeStream.iand();
         if (falseLabel == null) {
            if (trueLabel != null) {
               codeStream.ifne(trueLabel);
            }
         } else if (trueLabel == null) {
            codeStream.ifeq(falseLabel);
         }
      }

      codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
   }

   public void generateOptimizedLogicalOr(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      if ((this.left.implicitConversion & 15) == 5) {
         Constant condConst;
         BranchLabel internalFalseLabel;
         if ((condConst = this.left.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               internalFalseLabel = new BranchLabel(codeStream);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, internalFalseLabel, false);
               internalFalseLabel.place();
               if (valueRequired && trueLabel != null) {
                  codeStream.goto_(trueLabel);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            } else {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
            }

            return;
         }

         if ((condConst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               internalFalseLabel = new BranchLabel(codeStream);
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, internalFalseLabel, false);
               internalFalseLabel.place();
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               if (valueRequired && trueLabel != null) {
                  codeStream.goto_(trueLabel);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
            } else {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
            }

            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         codeStream.ior();
         if (falseLabel == null) {
            if (trueLabel != null) {
               codeStream.ifne(trueLabel);
            }
         } else if (trueLabel == null) {
            codeStream.ifeq(falseLabel);
         }
      }

      codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
   }

   public void generateOptimizedLogicalXor(BlockScope currentScope, CodeStream codeStream, BranchLabel trueLabel, BranchLabel falseLabel, boolean valueRequired) {
      if ((this.left.implicitConversion & 15) == 5) {
         Constant condConst;
         if ((condConst = this.left.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               this.right.generateOptimizedBoolean(currentScope, codeStream, falseLabel, trueLabel, valueRequired);
            } else {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
            }

            return;
         }

         if ((condConst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant) {
            if (condConst.booleanValue()) {
               this.left.generateOptimizedBoolean(currentScope, codeStream, falseLabel, trueLabel, valueRequired);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
            } else {
               this.left.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, valueRequired);
               this.right.generateOptimizedBoolean(currentScope, codeStream, trueLabel, falseLabel, false);
            }

            return;
         }
      }

      this.left.generateCode(currentScope, codeStream, valueRequired);
      this.right.generateCode(currentScope, codeStream, valueRequired);
      if (valueRequired) {
         codeStream.ixor();
         if (falseLabel == null) {
            if (trueLabel != null) {
               codeStream.ifne(trueLabel);
            }
         } else if (trueLabel == null) {
            codeStream.ifeq(falseLabel);
         }
      }

      codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd);
   }

   public void generateOptimizedStringConcatenation(BlockScope blockScope, CodeStream codeStream, int typeID) {
      if ((this.bits & 4032) >> 6 == 14 && (this.bits & 15) == 11) {
         if (this.constant != Constant.NotAConstant) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
            codeStream.invokeStringConcatenationAppendForType(this.implicitConversion & 15);
         } else {
            int pc = codeStream.position;
            this.left.generateOptimizedStringConcatenation(blockScope, codeStream, this.left.implicitConversion & 15);
            codeStream.recordPositionsFrom(pc, this.left.sourceStart);
            pc = codeStream.position;
            this.right.generateOptimizedStringConcatenation(blockScope, codeStream, this.right.implicitConversion & 15);
            codeStream.recordPositionsFrom(pc, this.right.sourceStart);
         }
      } else {
         super.generateOptimizedStringConcatenation(blockScope, codeStream, typeID);
      }

   }

   public void generateOptimizedStringConcatenationCreation(BlockScope blockScope, CodeStream codeStream, int typeID) {
      if ((this.bits & 4032) >> 6 == 14 && (this.bits & 15) == 11) {
         if (this.constant != Constant.NotAConstant) {
            codeStream.newStringContatenation();
            codeStream.dup();
            codeStream.ldc(this.constant.stringValue());
            codeStream.invokeStringConcatenationStringConstructor();
         } else {
            int pc = codeStream.position;
            this.left.generateOptimizedStringConcatenationCreation(blockScope, codeStream, this.left.implicitConversion & 15);
            codeStream.recordPositionsFrom(pc, this.left.sourceStart);
            pc = codeStream.position;
            this.right.generateOptimizedStringConcatenation(blockScope, codeStream, this.right.implicitConversion & 15);
            codeStream.recordPositionsFrom(pc, this.right.sourceStart);
         }
      } else {
         super.generateOptimizedStringConcatenationCreation(blockScope, codeStream, typeID);
      }

   }

   public boolean isCompactableOperation() {
      return true;
   }

   void nonRecursiveResolveTypeUpwards(BlockScope scope) {
      TypeBinding leftType = this.left.resolvedType;
      boolean rightIsCast;
      if (rightIsCast = this.right instanceof CastExpression) {
         Expression var10000 = this.right;
         var10000.bits |= 32;
      }

      TypeBinding rightType = this.right.resolveType(scope);
      if (leftType != null && rightType != null) {
         int leftTypeID = leftType.id;
         int rightTypeID = rightType.id;
         boolean use15specifics = scope.compilerOptions().sourceLevel >= 3211264L;
         if (use15specifics) {
            if (!leftType.isBaseType() && rightTypeID != 11 && rightTypeID != 12) {
               leftTypeID = scope.environment().computeBoxingType(leftType).id;
            }

            if (!rightType.isBaseType() && leftTypeID != 11 && leftTypeID != 12) {
               rightTypeID = scope.environment().computeBoxingType(rightType).id;
            }
         }

         if (leftTypeID > 15 || rightTypeID > 15) {
            if (leftTypeID == 11) {
               rightTypeID = 1;
            } else {
               if (rightTypeID != 11) {
                  this.constant = Constant.NotAConstant;
                  scope.problemReporter().invalidOperator(this, leftType, rightType);
                  return;
               }

               leftTypeID = 1;
            }
         }

         if ((this.bits & 4032) >> 6 == 14) {
            if (leftTypeID == 11) {
               this.left.computeConversion(scope, leftType, leftType);
               if (rightType.isArrayType() && TypeBinding.equalsEquals(((ArrayBinding)rightType).elementsType(), TypeBinding.CHAR)) {
                  scope.problemReporter().signalNoImplicitStringConversionForCharArrayExpression(this.right);
               }
            }

            if (rightTypeID == 11) {
               this.right.computeConversion(scope, rightType, rightType);
               if (leftType.isArrayType() && TypeBinding.equalsEquals(((ArrayBinding)leftType).elementsType(), TypeBinding.CHAR)) {
                  scope.problemReporter().signalNoImplicitStringConversionForCharArrayExpression(this.left);
               }
            }
         }

         int operator = (this.bits & 4032) >> 6;
         int operatorSignature = OperatorExpression.OperatorSignatures[operator][(leftTypeID << 4) + rightTypeID];
         this.left.computeConversion(scope, TypeBinding.wellKnownType(scope, operatorSignature >>> 16 & 15), leftType);
         this.right.computeConversion(scope, TypeBinding.wellKnownType(scope, operatorSignature >>> 8 & 15), rightType);
         this.bits |= operatorSignature & 15;
         switch (operatorSignature & 15) {
            case 2:
               this.resolvedType = TypeBinding.CHAR;
               break;
            case 3:
               this.resolvedType = TypeBinding.BYTE;
               break;
            case 4:
            case 6:
            default:
               this.constant = Constant.NotAConstant;
               scope.problemReporter().invalidOperator(this, leftType, rightType);
               return;
            case 5:
               this.resolvedType = TypeBinding.BOOLEAN;
               break;
            case 7:
               this.resolvedType = TypeBinding.LONG;
               break;
            case 8:
               this.resolvedType = TypeBinding.DOUBLE;
               break;
            case 9:
               this.resolvedType = TypeBinding.FLOAT;
               break;
            case 10:
               this.resolvedType = TypeBinding.INT;
               break;
            case 11:
               this.resolvedType = scope.getJavaLangString();
         }

         boolean leftIsCast;
         if ((leftIsCast = this.left instanceof CastExpression) || rightIsCast) {
            CastExpression.checkNeedForArgumentCasts(scope, operator, operatorSignature, this.left, leftTypeID, leftIsCast, this.right, rightTypeID, rightIsCast);
         }

         this.computeConstant(scope, leftTypeID, rightTypeID);
      } else {
         this.constant = Constant.NotAConstant;
      }
   }

   public void optimizedBooleanConstant(int leftId, int operator, int rightId) {
      Constant cst;
      switch (operator) {
         case 2:
            if (leftId != 5 || rightId != 5) {
               return;
            }
         case 0:
            if ((cst = this.left.optimizedBooleanConstant()) != Constant.NotAConstant) {
               if (!cst.booleanValue()) {
                  this.optimizedBooleanConstant = cst;
                  return;
               }

               if ((cst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant) {
                  this.optimizedBooleanConstant = cst;
               }

               return;
            }

            if ((cst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant && !cst.booleanValue()) {
               this.optimizedBooleanConstant = cst;
            }

            return;
         case 3:
            if (leftId != 5 || rightId != 5) {
               return;
            }
         case 1:
            if ((cst = this.left.optimizedBooleanConstant()) != Constant.NotAConstant) {
               if (cst.booleanValue()) {
                  this.optimizedBooleanConstant = cst;
                  return;
               }

               if ((cst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant) {
                  this.optimizedBooleanConstant = cst;
               }

               return;
            } else if ((cst = this.right.optimizedBooleanConstant()) != Constant.NotAConstant && cst.booleanValue()) {
               this.optimizedBooleanConstant = cst;
            }
         default:
      }
   }

   public StringBuffer printExpressionNoParenthesis(int indent, StringBuffer output) {
      this.left.printExpression(indent, output).append(' ').append(this.operatorToString()).append(' ');
      return this.right.printExpression(0, output);
   }

   public TypeBinding resolveType(BlockScope scope) {
      Expression var10000;
      boolean leftIsCast;
      if (leftIsCast = this.left instanceof CastExpression) {
         var10000 = this.left;
         var10000.bits |= 32;
      }

      TypeBinding leftType = this.left.resolveType(scope);
      boolean rightIsCast;
      if (rightIsCast = this.right instanceof CastExpression) {
         var10000 = this.right;
         var10000.bits |= 32;
      }

      TypeBinding rightType = this.right.resolveType(scope);
      if (leftType != null && rightType != null) {
         int leftTypeID = leftType.id;
         int rightTypeID = rightType.id;
         boolean use15specifics = scope.compilerOptions().sourceLevel >= 3211264L;
         if (use15specifics) {
            if (!leftType.isBaseType() && rightTypeID != 11 && rightTypeID != 12) {
               leftTypeID = scope.environment().computeBoxingType(leftType).id;
            }

            if (!rightType.isBaseType() && leftTypeID != 11 && leftTypeID != 12) {
               rightTypeID = scope.environment().computeBoxingType(rightType).id;
            }
         }

         if (leftTypeID > 15 || rightTypeID > 15) {
            if (leftTypeID == 11) {
               rightTypeID = 1;
            } else {
               if (rightTypeID != 11) {
                  this.constant = Constant.NotAConstant;
                  scope.problemReporter().invalidOperator(this, leftType, rightType);
                  return null;
               }

               leftTypeID = 1;
            }
         }

         if ((this.bits & 4032) >> 6 == 14) {
            if (leftTypeID == 11) {
               this.left.computeConversion(scope, leftType, leftType);
               if (rightType.isArrayType() && TypeBinding.equalsEquals(((ArrayBinding)rightType).elementsType(), TypeBinding.CHAR)) {
                  scope.problemReporter().signalNoImplicitStringConversionForCharArrayExpression(this.right);
               }
            }

            if (rightTypeID == 11) {
               this.right.computeConversion(scope, rightType, rightType);
               if (leftType.isArrayType() && TypeBinding.equalsEquals(((ArrayBinding)leftType).elementsType(), TypeBinding.CHAR)) {
                  scope.problemReporter().signalNoImplicitStringConversionForCharArrayExpression(this.left);
               }
            }
         }

         int operator = (this.bits & 4032) >> 6;
         int operatorSignature = OperatorExpression.OperatorSignatures[operator][(leftTypeID << 4) + rightTypeID];
         this.left.computeConversion(scope, TypeBinding.wellKnownType(scope, operatorSignature >>> 16 & 15), leftType);
         this.right.computeConversion(scope, TypeBinding.wellKnownType(scope, operatorSignature >>> 8 & 15), rightType);
         this.bits |= operatorSignature & 15;
         switch (operatorSignature & 15) {
            case 2:
               this.resolvedType = TypeBinding.CHAR;
               break;
            case 3:
               this.resolvedType = TypeBinding.BYTE;
               break;
            case 4:
            case 6:
            default:
               this.constant = Constant.NotAConstant;
               scope.problemReporter().invalidOperator(this, leftType, rightType);
               return null;
            case 5:
               this.resolvedType = TypeBinding.BOOLEAN;
               break;
            case 7:
               this.resolvedType = TypeBinding.LONG;
               break;
            case 8:
               this.resolvedType = TypeBinding.DOUBLE;
               break;
            case 9:
               this.resolvedType = TypeBinding.FLOAT;
               break;
            case 10:
               this.resolvedType = TypeBinding.INT;
               break;
            case 11:
               this.resolvedType = scope.getJavaLangString();
         }

         if (leftIsCast || rightIsCast) {
            CastExpression.checkNeedForArgumentCasts(scope, operator, operatorSignature, this.left, leftTypeID, leftIsCast, this.right, rightTypeID, rightIsCast);
         }

         this.computeConstant(scope, leftTypeID, rightTypeID);
         return this.resolvedType;
      } else {
         this.constant = Constant.NotAConstant;
         return null;
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
