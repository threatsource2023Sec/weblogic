package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.support.BooleanTypedValue;

public class OpEQ extends Operator {
   public OpEQ(int pos, SpelNodeImpl... operands) {
      super("==", pos, operands);
      this.exitTypeDescriptor = "Z";
   }

   public BooleanTypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      Object left = this.getLeftOperand().getValueInternal(state).getValue();
      Object right = this.getRightOperand().getValueInternal(state).getValue();
      this.leftActualDescriptor = CodeFlow.toDescriptorFromObject(left);
      this.rightActualDescriptor = CodeFlow.toDescriptorFromObject(right);
      return BooleanTypedValue.forValue(equalityCheck(state.getEvaluationContext(), left, right));
   }

   public boolean isCompilable() {
      SpelNodeImpl left = this.getLeftOperand();
      SpelNodeImpl right = this.getRightOperand();
      if (left.isCompilable() && right.isCompilable()) {
         String leftDesc = left.exitTypeDescriptor;
         String rightDesc = right.exitTypeDescriptor;
         Operator.DescriptorComparison dc = Operator.DescriptorComparison.checkNumericCompatibility(leftDesc, rightDesc, this.leftActualDescriptor, this.rightActualDescriptor);
         return !dc.areNumbers || dc.areCompatible;
      } else {
         return false;
      }
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      cf.loadEvaluationContext(mv);
      String leftDesc = this.getLeftOperand().exitTypeDescriptor;
      String rightDesc = this.getRightOperand().exitTypeDescriptor;
      boolean leftPrim = CodeFlow.isPrimitive(leftDesc);
      boolean rightPrim = CodeFlow.isPrimitive(rightDesc);
      cf.enterCompilationScope();
      this.getLeftOperand().generateCode(mv, cf);
      cf.exitCompilationScope();
      if (leftPrim) {
         CodeFlow.insertBoxIfNecessary(mv, leftDesc.charAt(0));
      }

      cf.enterCompilationScope();
      this.getRightOperand().generateCode(mv, cf);
      cf.exitCompilationScope();
      if (rightPrim) {
         CodeFlow.insertBoxIfNecessary(mv, rightDesc.charAt(0));
      }

      String operatorClassName = Operator.class.getName().replace('.', '/');
      String evaluationContextClassName = EvaluationContext.class.getName().replace('.', '/');
      mv.visitMethodInsn(184, operatorClassName, "equalityCheck", "(L" + evaluationContextClassName + ";Ljava/lang/Object;Ljava/lang/Object;)Z", false);
      cf.pushDescriptor("Z");
   }
}
