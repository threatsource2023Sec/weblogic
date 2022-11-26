package com.bea.core.repackaged.springframework.expression.common;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.Expression;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class LiteralExpression implements Expression {
   private final String literalValue;

   public LiteralExpression(String literalValue) {
      this.literalValue = literalValue;
   }

   public final String getExpressionString() {
      return this.literalValue;
   }

   public Class getValueType(EvaluationContext context) {
      return String.class;
   }

   public String getValue() {
      return this.literalValue;
   }

   @Nullable
   public Object getValue(@Nullable Class expectedResultType) throws EvaluationException {
      Object value = this.getValue();
      return ExpressionUtils.convertTypedValue((EvaluationContext)null, new TypedValue(value), expectedResultType);
   }

   public String getValue(Object rootObject) {
      return this.literalValue;
   }

   @Nullable
   public Object getValue(Object rootObject, @Nullable Class desiredResultType) throws EvaluationException {
      Object value = this.getValue(rootObject);
      return ExpressionUtils.convertTypedValue((EvaluationContext)null, new TypedValue(value), desiredResultType);
   }

   public String getValue(EvaluationContext context) {
      return this.literalValue;
   }

   @Nullable
   public Object getValue(EvaluationContext context, @Nullable Class expectedResultType) throws EvaluationException {
      Object value = this.getValue(context);
      return ExpressionUtils.convertTypedValue(context, new TypedValue(value), expectedResultType);
   }

   public String getValue(EvaluationContext context, Object rootObject) throws EvaluationException {
      return this.literalValue;
   }

   @Nullable
   public Object getValue(EvaluationContext context, Object rootObject, @Nullable Class desiredResultType) throws EvaluationException {
      Object value = this.getValue(context, rootObject);
      return ExpressionUtils.convertTypedValue(context, new TypedValue(value), desiredResultType);
   }

   public Class getValueType() {
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
      throw new EvaluationException(this.literalValue, "Cannot call setValue() on a LiteralExpression");
   }

   public void setValue(EvaluationContext context, @Nullable Object value) throws EvaluationException {
      throw new EvaluationException(this.literalValue, "Cannot call setValue() on a LiteralExpression");
   }

   public void setValue(EvaluationContext context, Object rootObject, @Nullable Object value) throws EvaluationException {
      throw new EvaluationException(this.literalValue, "Cannot call setValue() on a LiteralExpression");
   }
}
