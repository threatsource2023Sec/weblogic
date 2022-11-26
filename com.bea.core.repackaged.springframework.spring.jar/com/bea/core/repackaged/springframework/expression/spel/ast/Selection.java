package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Selection extends SpelNodeImpl {
   public static final int ALL = 0;
   public static final int FIRST = 1;
   public static final int LAST = 2;
   private final int variant;
   private final boolean nullSafe;

   public Selection(boolean nullSafe, int variant, int pos, SpelNodeImpl expression) {
      super(pos, expression);
      this.nullSafe = nullSafe;
      this.variant = variant;
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      return this.getValueRef(state).getValue();
   }

   protected ValueRef getValueRef(ExpressionState state) throws EvaluationException {
      TypedValue op = state.getActiveContextObject();
      Object operand = op.getValue();
      SpelNodeImpl selectionCriteria = this.children[0];
      Iterator var8;
      Object element;
      if (operand instanceof Map) {
         Map mapdata = (Map)operand;
         Map result = new HashMap();
         Object lastKey = null;
         var8 = mapdata.entrySet().iterator();

         while(true) {
            if (var8.hasNext()) {
               Map.Entry entry = (Map.Entry)var8.next();

               ValueRef.TypedValueHolderValueRef var12;
               try {
                  TypedValue kvPair = new TypedValue(entry);
                  state.pushActiveContextObject(kvPair);
                  state.enterScope();
                  Object val = selectionCriteria.getValueInternal(state).getValue();
                  if (!(val instanceof Boolean)) {
                     throw new SpelEvaluationException(selectionCriteria.getStartPosition(), SpelMessage.RESULT_OF_SELECTION_CRITERIA_IS_NOT_BOOLEAN, new Object[0]);
                  }

                  if (!(Boolean)val) {
                     continue;
                  }

                  if (this.variant != 1) {
                     result.put(entry.getKey(), entry.getValue());
                     lastKey = entry.getKey();
                     continue;
                  }

                  result.put(entry.getKey(), entry.getValue());
                  var12 = new ValueRef.TypedValueHolderValueRef(new TypedValue(result), this);
               } finally {
                  state.popActiveContextObject();
                  state.exitScope();
               }

               return var12;
            }

            if ((this.variant == 1 || this.variant == 2) && result.isEmpty()) {
               return new ValueRef.TypedValueHolderValueRef(new TypedValue((Object)null), this);
            }

            if (this.variant == 2) {
               Map resultMap = new HashMap();
               element = result.get(lastKey);
               resultMap.put(lastKey, element);
               return new ValueRef.TypedValueHolderValueRef(new TypedValue(resultMap), this);
            }

            return new ValueRef.TypedValueHolderValueRef(new TypedValue(result), this);
         }
      } else if (!(operand instanceof Iterable) && !ObjectUtils.isArray(operand)) {
         if (operand == null) {
            if (this.nullSafe) {
               return ValueRef.NullValueRef.INSTANCE;
            } else {
               throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.INVALID_TYPE_FOR_SELECTION, new Object[]{"null"});
            }
         } else {
            throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.INVALID_TYPE_FOR_SELECTION, new Object[]{operand.getClass().getName()});
         }
      } else {
         Iterable data = operand instanceof Iterable ? (Iterable)operand : Arrays.asList(ObjectUtils.toObjectArray(operand));
         List result = new ArrayList();
         int index = 0;
         var8 = ((Iterable)data).iterator();

         Object resultArray;
         while(var8.hasNext()) {
            element = var8.next();

            try {
               state.pushActiveContextObject(new TypedValue(element));
               state.enterScope("index", index);
               resultArray = selectionCriteria.getValueInternal(state).getValue();
               if (!(resultArray instanceof Boolean)) {
                  throw new SpelEvaluationException(selectionCriteria.getStartPosition(), SpelMessage.RESULT_OF_SELECTION_CRITERIA_IS_NOT_BOOLEAN, new Object[0]);
               }

               if ((Boolean)resultArray) {
                  if (this.variant == 1) {
                     ValueRef.TypedValueHolderValueRef var11 = new ValueRef.TypedValueHolderValueRef(new TypedValue(element), this);
                     return var11;
                  }

                  result.add(element);
               }

               ++index;
            } finally {
               state.exitScope();
               state.popActiveContextObject();
            }
         }

         if ((this.variant == 1 || this.variant == 2) && result.isEmpty()) {
            return ValueRef.NullValueRef.INSTANCE;
         } else if (this.variant == 2) {
            return new ValueRef.TypedValueHolderValueRef(new TypedValue(CollectionUtils.lastElement((List)result)), this);
         } else if (operand instanceof Iterable) {
            return new ValueRef.TypedValueHolderValueRef(new TypedValue(result), this);
         } else {
            Class elementType = null;
            TypeDescriptor typeDesc = op.getTypeDescriptor();
            if (typeDesc != null) {
               TypeDescriptor elementTypeDesc = typeDesc.getElementTypeDescriptor();
               if (elementTypeDesc != null) {
                  elementType = ClassUtils.resolvePrimitiveIfNecessary(elementTypeDesc.getType());
               }
            }

            Assert.state(elementType != null, "Unresolvable element type");
            resultArray = Array.newInstance(elementType, result.size());
            System.arraycopy(result.toArray(), 0, resultArray, 0, result.size());
            return new ValueRef.TypedValueHolderValueRef(new TypedValue(resultArray), this);
         }
      }
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder();
      switch (this.variant) {
         case 0:
            sb.append("?[");
            break;
         case 1:
            sb.append("^[");
            break;
         case 2:
            sb.append("$[");
      }

      return sb.append(this.getChild(0).toStringAST()).append("]").toString();
   }
}
