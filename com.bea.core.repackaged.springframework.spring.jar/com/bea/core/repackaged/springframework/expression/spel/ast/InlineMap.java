package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelNode;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class InlineMap extends SpelNodeImpl {
   @Nullable
   private TypedValue constant;

   public InlineMap(int pos, SpelNodeImpl... args) {
      super(pos, args);
      this.checkIfConstant();
   }

   private void checkIfConstant() {
      boolean isConstant = true;
      int c = 0;

      int childCount;
      for(childCount = this.getChildCount(); c < childCount; ++c) {
         SpelNode child = this.getChild(c);
         if (!(child instanceof Literal)) {
            if (child instanceof InlineList) {
               InlineList inlineList = (InlineList)child;
               if (!inlineList.isConstant()) {
                  isConstant = false;
                  break;
               }
            } else if (child instanceof InlineMap) {
               InlineMap inlineMap = (InlineMap)child;
               if (!inlineMap.isConstant()) {
                  isConstant = false;
                  break;
               }
            } else if (c % 2 != 0 || !(child instanceof PropertyOrFieldReference)) {
               isConstant = false;
               break;
            }
         }
      }

      if (isConstant) {
         Map constantMap = new LinkedHashMap();
         childCount = this.getChildCount();

         for(int c = 0; c < childCount; ++c) {
            SpelNode keyChild = this.getChild(c++);
            SpelNode valueChild = this.getChild(c);
            Object key = null;
            Object value = null;
            if (keyChild instanceof Literal) {
               key = ((Literal)keyChild).getLiteralValue().getValue();
            } else {
               if (!(keyChild instanceof PropertyOrFieldReference)) {
                  return;
               }

               key = ((PropertyOrFieldReference)keyChild).getName();
            }

            if (valueChild instanceof Literal) {
               value = ((Literal)valueChild).getLiteralValue().getValue();
            } else if (valueChild instanceof InlineList) {
               value = ((InlineList)valueChild).getConstantValue();
            } else if (valueChild instanceof InlineMap) {
               value = ((InlineMap)valueChild).getConstantValue();
            }

            constantMap.put(key, value);
         }

         this.constant = new TypedValue(Collections.unmodifiableMap(constantMap));
      }

   }

   public TypedValue getValueInternal(ExpressionState expressionState) throws EvaluationException {
      if (this.constant != null) {
         return this.constant;
      } else {
         Map returnValue = new LinkedHashMap();
         int childcount = this.getChildCount();

         for(int c = 0; c < childcount; ++c) {
            SpelNode keyChild = this.getChild(c++);
            Object key = null;
            if (keyChild instanceof PropertyOrFieldReference) {
               PropertyOrFieldReference reference = (PropertyOrFieldReference)keyChild;
               key = reference.getName();
            } else {
               key = keyChild.getValue(expressionState);
            }

            Object value = this.getChild(c).getValue(expressionState);
            returnValue.put(key, value);
         }

         return new TypedValue(returnValue);
      }
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder("{");
      int count = this.getChildCount();

      for(int c = 0; c < count; ++c) {
         if (c > 0) {
            sb.append(",");
         }

         sb.append(this.getChild(c++).toStringAST());
         sb.append(":");
         sb.append(this.getChild(c).toStringAST());
      }

      sb.append("}");
      return sb.toString();
   }

   public boolean isConstant() {
      return this.constant != null;
   }

   @Nullable
   public Map getConstantValue() {
      Assert.state(this.constant != null, "No constant");
      return (Map)this.constant.getValue();
   }
}
