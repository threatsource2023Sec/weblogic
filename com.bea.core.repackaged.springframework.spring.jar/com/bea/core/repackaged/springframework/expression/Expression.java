package com.bea.core.repackaged.springframework.expression;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface Expression {
   String getExpressionString();

   @Nullable
   Object getValue() throws EvaluationException;

   @Nullable
   Object getValue(@Nullable Class var1) throws EvaluationException;

   @Nullable
   Object getValue(Object var1) throws EvaluationException;

   @Nullable
   Object getValue(Object var1, @Nullable Class var2) throws EvaluationException;

   @Nullable
   Object getValue(EvaluationContext var1) throws EvaluationException;

   @Nullable
   Object getValue(EvaluationContext var1, Object var2) throws EvaluationException;

   @Nullable
   Object getValue(EvaluationContext var1, @Nullable Class var2) throws EvaluationException;

   @Nullable
   Object getValue(EvaluationContext var1, Object var2, @Nullable Class var3) throws EvaluationException;

   @Nullable
   Class getValueType() throws EvaluationException;

   @Nullable
   Class getValueType(Object var1) throws EvaluationException;

   @Nullable
   Class getValueType(EvaluationContext var1) throws EvaluationException;

   @Nullable
   Class getValueType(EvaluationContext var1, Object var2) throws EvaluationException;

   @Nullable
   TypeDescriptor getValueTypeDescriptor() throws EvaluationException;

   @Nullable
   TypeDescriptor getValueTypeDescriptor(Object var1) throws EvaluationException;

   @Nullable
   TypeDescriptor getValueTypeDescriptor(EvaluationContext var1) throws EvaluationException;

   @Nullable
   TypeDescriptor getValueTypeDescriptor(EvaluationContext var1, Object var2) throws EvaluationException;

   boolean isWritable(Object var1) throws EvaluationException;

   boolean isWritable(EvaluationContext var1) throws EvaluationException;

   boolean isWritable(EvaluationContext var1, Object var2) throws EvaluationException;

   void setValue(Object var1, @Nullable Object var2) throws EvaluationException;

   void setValue(EvaluationContext var1, @Nullable Object var2) throws EvaluationException;

   void setValue(EvaluationContext var1, Object var2, @Nullable Object var3) throws EvaluationException;
}
