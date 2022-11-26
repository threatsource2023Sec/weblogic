package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.support.BooleanTypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class OpOr extends Operator {
   public OpOr(int pos, SpelNodeImpl... operands) {
      super("or", pos, operands);
      this.exitTypeDescriptor = "Z";
   }

   public BooleanTypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      return this.getBooleanValue(state, this.getLeftOperand()) ? BooleanTypedValue.TRUE : BooleanTypedValue.forValue(this.getBooleanValue(state, this.getRightOperand()));
   }

   private boolean getBooleanValue(ExpressionState state, SpelNodeImpl operand) {
      try {
         Boolean value = (Boolean)operand.getValue(state, Boolean.class);
         this.assertValueNotNull(value);
         return value;
      } catch (SpelEvaluationException var4) {
         var4.setPosition(operand.getStartPosition());
         throw var4;
      }
   }

   private void assertValueNotNull(@Nullable Boolean value) {
      if (value == null) {
         throw new SpelEvaluationException(SpelMessage.TYPE_CONVERSION_ERROR, new Object[]{"null", "boolean"});
      }
   }

   public boolean isCompilable() {
      SpelNodeImpl left = this.getLeftOperand();
      SpelNodeImpl right = this.getRightOperand();
      return left.isCompilable() && right.isCompilable() && CodeFlow.isBooleanCompatible(left.exitTypeDescriptor) && CodeFlow.isBooleanCompatible(right.exitTypeDescriptor);
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      Label elseTarget = new Label();
      Label endOfIf = new Label();
      cf.enterCompilationScope();
      this.getLeftOperand().generateCode(mv, cf);
      cf.unboxBooleanIfNecessary(mv);
      cf.exitCompilationScope();
      mv.visitJumpInsn(153, elseTarget);
      mv.visitLdcInsn(1);
      mv.visitJumpInsn(167, endOfIf);
      mv.visitLabel(elseTarget);
      cf.enterCompilationScope();
      this.getRightOperand().generateCode(mv, cf);
      cf.unboxBooleanIfNecessary(mv);
      cf.exitCompilationScope();
      mv.visitLabel(endOfIf);
      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
