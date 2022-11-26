package com.bea.core.repackaged.springframework.expression.common;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.Expression;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class CompositeStringExpression implements Expression {
   private final String expressionString;
   private final Expression[] expressions;

   public CompositeStringExpression(String expressionString, Expression[] expressions) {
      this.expressionString = expressionString;
      this.expressions = expressions;
   }

   public final String getExpressionString() {
      return this.expressionString;
   }

   public final Expression[] getExpressions() {
      return this.expressions;
   }

   public String getValue() throws EvaluationException {
      StringBuilder sb = new StringBuilder();
      Expression[] var2 = this.expressions;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Expression expression = var2[var4];
         String value = (String)expression.getValue(String.class);
         if (value != null) {
            sb.append(value);
         }
      }

      return sb.toString();
   }

   @Nullable
   public Object getValue(@Nullable Class expectedResultType) throws EvaluationException {
      Object value = this.getValue();
      return ExpressionUtils.convertTypedValue((EvaluationContext)null, new TypedValue(value), expectedResultType);
   }

   public String getValue(Object rootObject) throws EvaluationException {
      StringBuilder sb = new StringBuilder();
      Expression[] var3 = this.expressions;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Expression expression = var3[var5];
         String value = (String)expression.getValue(rootObject, String.class);
         if (value != null) {
            sb.append(value);
         }
      }

      return sb.toString();
   }

   @Nullable
   public Object getValue(Object rootObject, @Nullable Class desiredResultType) throws EvaluationException {
      Object value = this.getValue(rootObject);
      return ExpressionUtils.convertTypedValue((EvaluationContext)null, new TypedValue(value), desiredResultType);
   }

   public String getValue(EvaluationContext context) throws EvaluationException {
      StringBuilder sb = new StringBuilder();
      Expression[] var3 = this.expressions;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Expression expression = var3[var5];
         String value = (String)expression.getValue(context, String.class);
         if (value != null) {
            sb.append(value);
         }
      }

      return sb.toString();
   }

   @Nullable
   public Object getValue(EvaluationContext context, @Nullable Class expectedResultType) throws EvaluationException {
      Object value = this.getValue(context);
      return ExpressionUtils.convertTypedValue(context, new TypedValue(value), expectedResultType);
   }

   public String getValue(EvaluationContext context, Object rootObject) throws EvaluationException {
      StringBuilder sb = new StringBuilder();
      Expression[] var4 = this.expressions;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Expression expression = var4[var6];
         String value = (String)expression.getValue(context, rootObject, String.class);
         if (value != null) {
            sb.append(value);
         }
      }

      return sb.toString();
   }

   @Nullable
   public Object getValue(EvaluationContext context, Object rootObject, @Nullable Class desiredResultType) throws EvaluationException {
      Object value = this.getValue(context, rootObject);
      return ExpressionUtils.convertTypedValue(context, new TypedValue(value), desiredResultType);
   }

   public Class getValueType() {
      return String.class;
   }

   public Class getValueType(EvaluationContext context) {
      return String.class;
   }

   public Class getValueType(Object rootObject) throws EvaluationException {
      return String.class;
   }

   public Class getValueType(EvaluationContext context, Object rootObject) throws EvaluationException {
      return String.class;
   }

   public TypeDescriptor getValueTypeDescriptor() {
      return TypeDescriptor.valueOf(String.class);
   }

   public TypeDescriptor getValueTypeDescriptor(Object rootObject) throws EvaluationException {
      return TypeDescriptor.valueOf(String.class);
   }

   public TypeDescriptor getValueTypeDescriptor(EvaluationContext context) {
      return TypeDescriptor.valueOf(String.class);
   }

   public TypeDescriptor getValueTypeDescriptor(EvaluationContext context, Object rootObject) throws EvaluationException {
      return TypeDescriptor.valueOf(String.class);
   }

   public boolean isWritable(Object rootObject) throws EvaluationException {
      return false;
   }

   public boolean isWritable(EvaluationContext context) {
      return false;
   }

   public boolean isWritable(EvaluationContext context, Object rootObject) throws EvaluationException {
      return false;
   }

   public void setValue(Object rootObject, @Nullable Object value) throws EvaluationException {
      throw new EvaluationException(this.expressionString, "Cannot call setValue on a composite expression");
   }

   public void setValue(EvaluationContext context, @Nullable Object value) throws EvaluationException {
      throw new EvaluationException(this.expressionString, "Cannot call setValue on a composite expression");
   }

   public void setValue(EvaluationContext context, Object rootObject, @Nullable Object value) throws EvaluationException {
      throw new EvaluationException(this.expressionString, "Cannot call setValue on a composite expression");
   }
}
