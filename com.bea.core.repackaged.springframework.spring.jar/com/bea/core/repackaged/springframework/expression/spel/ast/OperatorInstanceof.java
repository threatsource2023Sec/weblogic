package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.expression.spel.support.BooleanTypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class OperatorInstanceof extends Operator {
   @Nullable
   private Class type;

   public OperatorInstanceof(int pos, SpelNodeImpl... operands) {
      super("instanceof", pos, operands);
   }

   public BooleanTypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      SpelNodeImpl rightOperand = this.getRightOperand();
      TypedValue left = this.getLeftOperand().getValueInternal(state);
      TypedValue right = rightOperand.getValueInternal(state);
      Object leftValue = left.getValue();
      Object rightValue = right.getValue();
      if (rightValue != null && rightValue instanceof Class) {
         Class rightClass = (Class)rightValue;
         BooleanTypedValue result;
         if (leftValue == null) {
            result = BooleanTypedValue.FALSE;
         } else {
            result = BooleanTypedValue.forValue(rightClass.isAssignableFrom(leftValue.getClass()));
         }

         this.type = rightClass;
         if (rightOperand instanceof TypeReference) {
            this.exitTypeDescriptor = "Z";
         }

         return result;
      } else {
         throw new SpelEvaluationException(this.getRightOperand().getStartPosition(), SpelMessage.INSTANCEOF_OPERATOR_NEEDS_CLASS_OPERAND, new Object[]{rightValue == null ? "null" : rightValue.getClass().getName()});
      }
   }

   public boolean isCompilable() {
      return this.exitTypeDescriptor != null && this.getLeftOperand().isCompilable();
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      this.getLeftOperand().generateCode(mv, cf);
      CodeFlow.insertBoxIfNecessary(mv, cf.lastDescriptor());
      Assert.state(this.type != null, "No type available");
      if (this.type.isPrimitive()) {
         mv.visitInsn(87);
         mv.visitInsn(3);
      } else {
         mv.visitTypeInsn(193, Type.getInternalName(this.type));
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
