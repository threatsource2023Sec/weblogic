package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;

public class Ternary extends SpelNodeImpl {
   public Ternary(int pos, SpelNodeImpl... args) {
      super(pos, args);
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      Boolean value = (Boolean)this.children[0].getValue(state, Boolean.class);
      if (value == null) {
         throw new SpelEvaluationException(this.getChild(0).getStartPosition(), SpelMessage.TYPE_CONVERSION_ERROR, new Object[]{"null", "boolean"});
      } else {
         TypedValue result = this.children[value ? 1 : 2].getValueInternal(state);
         this.computeExitTypeDescriptor();
         return result;
      }
   }

   public String toStringAST() {
      return this.getChild(0).toStringAST() + " ? " + this.getChild(1).toStringAST() + " : " + this.getChild(2).toStringAST();
   }

   private void computeExitTypeDescriptor() {
      if (this.exitTypeDescriptor == null && this.children[1].exitTypeDescriptor != null && this.children[2].exitTypeDescriptor != null) {
         String leftDescriptor = this.children[1].exitTypeDescriptor;
         String rightDescriptor = this.children[2].exitTypeDescriptor;
         if (ObjectUtils.nullSafeEquals(leftDescriptor, rightDescriptor)) {
            this.exitTypeDescriptor = leftDescriptor;
         } else {
            this.exitTypeDescriptor = "Ljava/lang/Object";
         }
      }

   }

   public boolean isCompilable() {
      SpelNodeImpl condition = this.children[0];
      SpelNodeImpl left = this.children[1];
      SpelNodeImpl right = this.children[2];
      return condition.isCompilable() && left.isCompilable() && right.isCompilable() && CodeFlow.isBooleanCompatible(condition.exitTypeDescriptor) && left.exitTypeDescriptor != null && right.exitTypeDescriptor != null;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      this.computeExitTypeDescriptor();
      cf.enterCompilationScope();
      this.children[0].generateCode(mv, cf);
      String lastDesc = cf.lastDescriptor();
      Assert.state(lastDesc != null, "No last descriptor");
      if (!CodeFlow.isPrimitive(lastDesc)) {
         CodeFlow.insertUnboxInsns(mv, 'Z', lastDesc);
      }

      cf.exitCompilationScope();
      Label elseTarget = new Label();
      Label endOfIf = new Label();
      mv.visitJumpInsn(153, elseTarget);
      cf.enterCompilationScope();
      this.children[1].generateCode(mv, cf);
      if (!CodeFlow.isPrimitive(this.exitTypeDescriptor)) {
         lastDesc = cf.lastDescriptor();
         Assert.state(lastDesc != null, "No last descriptor");
         CodeFlow.insertBoxIfNecessary(mv, lastDesc.charAt(0));
      }

      cf.exitCompilationScope();
      mv.visitJumpInsn(167, endOfIf);
      mv.visitLabel(elseTarget);
      cf.enterCompilationScope();
      this.children[2].generateCode(mv, cf);
      if (!CodeFlow.isPrimitive(this.exitTypeDescriptor)) {
         lastDesc = cf.lastDescriptor();
         Assert.state(lastDesc != null, "No last descriptor");
         CodeFlow.insertBoxIfNecessary(mv, lastDesc.charAt(0));
      }

      cf.exitCompilationScope();
      mv.visitLabel(endOfIf);
      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
