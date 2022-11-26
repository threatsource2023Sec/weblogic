package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.Label;
import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class Elvis extends SpelNodeImpl {
   public Elvis(int pos, SpelNodeImpl... args) {
      super(pos, args);
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      TypedValue value = this.children[0].getValueInternal(state);
      if (!StringUtils.isEmpty(value.getValue())) {
         return value;
      } else {
         TypedValue result = this.children[1].getValueInternal(state);
         this.computeExitTypeDescriptor();
         return result;
      }
   }

   public String toStringAST() {
      return this.getChild(0).toStringAST() + " ?: " + this.getChild(1).toStringAST();
   }

   public boolean isCompilable() {
      SpelNodeImpl condition = this.children[0];
      SpelNodeImpl ifNullValue = this.children[1];
      return condition.isCompilable() && ifNullValue.isCompilable() && condition.exitTypeDescriptor != null && ifNullValue.exitTypeDescriptor != null;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      this.computeExitTypeDescriptor();
      cf.enterCompilationScope();
      this.children[0].generateCode(mv, cf);
      String lastDesc = cf.lastDescriptor();
      Assert.state(lastDesc != null, "No last descriptor");
      CodeFlow.insertBoxIfNecessary(mv, lastDesc.charAt(0));
      cf.exitCompilationScope();
      Label elseTarget = new Label();
      Label endOfIf = new Label();
      mv.visitInsn(89);
      mv.visitJumpInsn(198, elseTarget);
      mv.visitInsn(89);
      mv.visitLdcInsn("");
      mv.visitInsn(95);
      mv.visitMethodInsn(182, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
      mv.visitJumpInsn(153, endOfIf);
      mv.visitLabel(elseTarget);
      mv.visitInsn(87);
      cf.enterCompilationScope();
      this.children[1].generateCode(mv, cf);
      if (!CodeFlow.isPrimitive(this.exitTypeDescriptor)) {
         lastDesc = cf.lastDescriptor();
         Assert.state(lastDesc != null, "No last descriptor");
         CodeFlow.insertBoxIfNecessary(mv, lastDesc.charAt(0));
      }

      cf.exitCompilationScope();
      mv.visitLabel(endOfIf);
      cf.pushDescriptor(this.exitTypeDescriptor);
   }

   private void computeExitTypeDescriptor() {
      if (this.exitTypeDescriptor == null && this.children[0].exitTypeDescriptor != null && this.children[1].exitTypeDescriptor != null) {
         String conditionDescriptor = this.children[0].exitTypeDescriptor;
         String ifNullValueDescriptor = this.children[1].exitTypeDescriptor;
         if (ObjectUtils.nullSafeEquals(conditionDescriptor, ifNullValueDescriptor)) {
            this.exitTypeDescriptor = conditionDescriptor;
         } else {
            this.exitTypeDescriptor = "Ljava/lang/Object";
         }
      }

   }
}
