package com.bea.core.repackaged.springframework.expression.spel;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface SpelNode {
   @Nullable
   Object getValue(ExpressionState var1) throws EvaluationException;

   TypedValue getTypedValue(ExpressionState var1) throws EvaluationException;

   boolean isWritable(ExpressionState var1) throws EvaluationException;

   void setValue(ExpressionState var1, @Nullable Object var2) throws EvaluationException;

   String toStringAST();

   int getChildCount();

   SpelNode getChild(int var1);

   @Nullable
   Class getObjectClass(@Nullable Object var1);

   int getStartPosition();

   int getEndPosition();
}
