package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class Projection extends SpelNodeImpl {
   private final boolean nullSafe;

   public Projection(boolean nullSafe, int pos, SpelNodeImpl expression) {
      super(pos, expression);
      this.nullSafe = nullSafe;
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      return this.getValueRef(state).getValue();
   }

   protected ValueRef getValueRef(ExpressionState state) throws EvaluationException {
      TypedValue op = state.getActiveContextObject();
      Object operand = op.getValue();
      boolean operandIsArray = ObjectUtils.isArray(operand);
      ArrayList result;
      if (operand instanceof Map) {
         Map mapData = (Map)operand;
         result = new ArrayList();
         Iterator var20 = mapData.entrySet().iterator();

         while(var20.hasNext()) {
            Map.Entry entry = (Map.Entry)var20.next();

            try {
               state.pushActiveContextObject(new TypedValue(entry));
               state.enterScope();
               result.add(this.children[0].getValueInternal(state).getValue());
            } finally {
               state.popActiveContextObject();
               state.exitScope();
            }
         }

         return new ValueRef.TypedValueHolderValueRef(new TypedValue(result), this);
      } else if (!(operand instanceof Iterable) && !operandIsArray) {
         if (operand == null) {
            if (this.nullSafe) {
               return ValueRef.NullValueRef.INSTANCE;
            } else {
               throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.PROJECTION_NOT_SUPPORTED_ON_TYPE, new Object[]{"null"});
            }
         } else {
            throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.PROJECTION_NOT_SUPPORTED_ON_TYPE, new Object[]{operand.getClass().getName()});
         }
      } else {
         Iterable data = operand instanceof Iterable ? (Iterable)operand : Arrays.asList(ObjectUtils.toObjectArray(operand));
         result = new ArrayList();
         int idx = 0;
         Class arrayElementType = null;

         for(Iterator var9 = ((Iterable)data).iterator(); var9.hasNext(); ++idx) {
            Object element = var9.next();

            try {
               state.pushActiveContextObject(new TypedValue(element));
               state.enterScope("index", idx);
               Object value = this.children[0].getValueInternal(state).getValue();
               if (value != null && operandIsArray) {
                  arrayElementType = this.determineCommonType(arrayElementType, value.getClass());
               }

               result.add(value);
            } finally {
               state.exitScope();
               state.popActiveContextObject();
            }
         }

         if (operandIsArray) {
            if (arrayElementType == null) {
               arrayElementType = Object.class;
            }

            Object resultArray = Array.newInstance(arrayElementType, result.size());
            System.arraycopy(result.toArray(), 0, resultArray, 0, result.size());
            return new ValueRef.TypedValueHolderValueRef(new TypedValue(resultArray), this);
         } else {
            return new ValueRef.TypedValueHolderValueRef(new TypedValue(result), this);
         }
      }
   }

   public String toStringAST() {
      return "![" + this.getChild(0).toStringAST() + "]";
   }

   private Class determineCommonType(@Nullable Class oldType, Class newType) {
      if (oldType == null) {
         return newType;
      } else if (oldType.isAssignableFrom(newType)) {
         return oldType;
      } else {
         for(Class nextType = newType; nextType != Object.class; nextType = nextType.getSuperclass()) {
            if (nextType.isAssignableFrom(oldType)) {
               return nextType;
            }
         }

         Iterator var4 = ClassUtils.getAllInterfacesForClassAsSet(newType).iterator();

         Class nextInterface;
         do {
            if (!var4.hasNext()) {
               return Object.class;
            }

            nextInterface = (Class)var4.next();
         } while(!nextInterface.isAssignableFrom(oldType));

         return nextInterface;
      }
   }
}
