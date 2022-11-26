package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class CompoundExpression extends SpelNodeImpl {
   public CompoundExpression(int pos, SpelNodeImpl... expressionComponents) {
      super(pos, expressionComponents);
      if (expressionComponents.length < 2) {
         throw new IllegalStateException("Do not build compound expressions with less than two entries: " + expressionComponents.length);
      }
   }

   protected ValueRef getValueRef(ExpressionState state) throws EvaluationException {
      if (this.getChildCount() == 1) {
         return this.children[0].getValueRef(state);
      } else {
         SpelNodeImpl nextNode = this.children[0];

         try {
            TypedValue result = nextNode.getValueInternal(state);
            int cc = this.getChildCount();

            for(int i = 1; i < cc - 1; ++i) {
               try {
                  state.pushActiveContextObject(result);
                  nextNode = this.children[i];
                  result = nextNode.getValueInternal(state);
               } finally {
                  state.popActiveContextObject();
               }
            }

            ValueRef var17;
            try {
               state.pushActiveContextObject(result);
               nextNode = this.children[cc - 1];
               var17 = nextNode.getValueRef(state);
            } finally {
               state.popActiveContextObject();
            }

            return var17;
         } catch (SpelEvaluationException var16) {
            var16.setPosition(nextNode.getStartPosition());
            throw var16;
         }
      }
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      ValueRef ref = this.getValueRef(state);
      TypedValue result = ref.getValue();
      this.exitTypeDescriptor = this.children[this.children.length - 1].exitTypeDescriptor;
      return result;
   }

   public void setValue(ExpressionState state, @Nullable Object value) throws EvaluationException {
      this.getValueRef(state).setValue(value);
   }

   public boolean isWritable(ExpressionState state) throws EvaluationException {
      return this.getValueRef(state).isWritable();
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < this.getChildCount(); ++i) {
         if (i > 0) {
            sb.append(".");
         }

         sb.append(this.getChild(i).toStringAST());
      }

      return sb.toString();
   }

   public boolean isCompilable() {
      SpelNodeImpl[] var1 = this.children;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         SpelNodeImpl child = var1[var3];
         if (!child.isCompilable()) {
            return false;
         }
      }

      return true;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      SpelNodeImpl[] var3 = this.children;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SpelNodeImpl child = var3[var5];
         child.generateCode(mv, cf);
      }

      cf.pushDescriptor(this.exitTypeDescriptor);
   }
}
