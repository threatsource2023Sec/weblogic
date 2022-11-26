package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.asm.MethodVisitor;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.CodeFlow;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Modifier;

public class VariableReference extends SpelNodeImpl {
   private static final String THIS = "this";
   private static final String ROOT = "root";
   private final String name;

   public VariableReference(String variableName, int pos) {
      super(pos);
      this.name = variableName;
   }

   public ValueRef getValueRef(ExpressionState state) throws SpelEvaluationException {
      if (this.name.equals("this")) {
         return new ValueRef.TypedValueHolderValueRef(state.getActiveContextObject(), this);
      } else if (this.name.equals("root")) {
         return new ValueRef.TypedValueHolderValueRef(state.getRootContextObject(), this);
      } else {
         TypedValue result = state.lookupVariable(this.name);
         return new VariableRef(this.name, result, state.getEvaluationContext());
      }
   }

   public TypedValue getValueInternal(ExpressionState state) throws SpelEvaluationException {
      if (this.name.equals("this")) {
         return state.getActiveContextObject();
      } else {
         TypedValue result;
         if (this.name.equals("root")) {
            result = state.getRootContextObject();
            this.exitTypeDescriptor = CodeFlow.toDescriptorFromObject(result.getValue());
            return result;
         } else {
            result = state.lookupVariable(this.name);
            Object value = result.getValue();
            if (value != null && Modifier.isPublic(value.getClass().getModifiers())) {
               this.exitTypeDescriptor = CodeFlow.toDescriptorFromObject(value);
            } else {
               this.exitTypeDescriptor = "Ljava/lang/Object";
            }

            return result;
         }
      }
   }

   public void setValue(ExpressionState state, @Nullable Object value) throws SpelEvaluationException {
      state.setVariable(this.name, value);
   }

   public String toStringAST() {
      return "#" + this.name;
   }

   public boolean isWritable(ExpressionState expressionState) throws SpelEvaluationException {
      return !this.name.equals("this") && !this.name.equals("root");
   }

   public boolean isCompilable() {
      return this.exitTypeDescriptor != null;
   }

   public void generateCode(MethodVisitor mv, CodeFlow cf) {
      if (this.name.equals("root")) {
         mv.visitVarInsn(25, 1);
      } else {
         mv.visitVarInsn(25, 2);
         mv.visitLdcInsn(this.name);
         mv.visitMethodInsn(185, "com/bea/core/repackaged/springframework/expression/EvaluationContext", "lookupVariable", "(Ljava/lang/String;)Ljava/lang/Object;", true);
      }

      CodeFlow.insertCheckCast(mv, this.exitTypeDescriptor);
      cf.pushDescriptor(this.exitTypeDescriptor);
   }

   private static class VariableRef implements ValueRef {
      private final String name;
      private final TypedValue value;
      private final EvaluationContext evaluationContext;

      public VariableRef(String name, TypedValue value, EvaluationContext evaluationContext) {
         this.name = name;
         this.value = value;
         this.evaluationContext = evaluationContext;
      }

      public TypedValue getValue() {
         return this.value;
      }

      public void setValue(@Nullable Object newValue) {
         this.evaluationContext.setVariable(this.name, newValue);
      }

      public boolean isWritable() {
         return true;
      }
   }
}
