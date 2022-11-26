package weblogic.messaging.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.Filter;
import weblogic.messaging.kernel.InvalidExpressionException;
import weblogic.messaging.kernel.Kernel;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Sink;
import weblogic.utils.concurrent.locks.NonRecursiveReadWriteLock;
import weblogic.utils.expressions.ExpressionEvaluationException;
import weblogic.utils.expressions.ExpressionParser;
import weblogic.utils.expressions.ExpressionParserException;
import weblogic.utils.expressions.VariableBinder;

public class SQLFilter implements Filter {
   protected final Map subscribers = new LinkedHashMap();
   protected final NonRecursiveReadWriteLock lock = new NonRecursiveReadWriteLock();
   protected Kernel kernel;
   protected VariableBinder variableBinder;

   public SQLFilter(Kernel kernel, VariableBinder binder) {
      this.kernel = kernel;
      this.variableBinder = binder;
   }

   public Expression createExpression(Object exp) throws KernelException {
      try {
         SQLExpression expression = (SQLExpression)exp;
         return expression.isNull() ? null : new Exp(this.variableBinder, expression);
      } catch (ClassCastException var3) {
         throw new InvalidExpressionException("Invalid expression class: " + exp.getClass().getName());
      }
   }

   public void subscribe(Sink sink, Expression e) {
      CompiledSQLExpression exp = (CompiledSQLExpression)e;
      this.lock.lockWrite();
      this.subscribers.put(sink, exp);
      this.lock.unlockWrite();
   }

   public void unsubscribe(Sink sink) {
      this.lock.lockWrite();
      this.subscribers.remove(sink);
      this.lock.unlockWrite();
   }

   public synchronized void resubscribe(Sink sink, Expression expression) {
      this.unsubscribe(sink);
      this.subscribe(sink, expression);
   }

   public Collection match(MessageElement message) {
      ArrayList ret = new ArrayList();
      this.lock.lockRead();

      try {
         Iterator i = this.subscribers.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            CompiledSQLExpression exp = (CompiledSQLExpression)entry.getValue();
            if (exp == null || exp.evaluate(message)) {
               ret.add(entry.getKey());
            }
         }

         ArrayList var9 = ret;
         return var9;
      } finally {
         this.lock.unlockRead();
      }
   }

   public boolean match(MessageElement message, Expression expression) {
      if (expression == null) {
         return true;
      } else {
         CompiledSQLExpression jmsExp = (CompiledSQLExpression)expression;

         try {
            return jmsExp.evaluate(message);
         } catch (ClassCastException var5) {
            return false;
         }
      }
   }

   private final class Exp implements CompiledSQLExpression {
      private weblogic.utils.expressions.Expression wlExp;

      Exp(VariableBinder binder, SQLExpression exp) throws KernelException {
         String selectorString = exp.getSelector();
         if (selectorString != null) {
            try {
               ExpressionParser parser = new ExpressionParser();
               this.wlExp = parser.parse(selectorString, binder);
            } catch (ExpressionParserException var6) {
               throw new InvalidExpressionException(selectorString);
            }
         }

      }

      public Filter getFilter() {
         return SQLFilter.this;
      }

      public boolean evaluate(MessageElement message) {
         if (this.wlExp != null) {
            try {
               return this.wlExp.evaluate(message);
            } catch (ExpressionEvaluationException var3) {
               return false;
            }
         } else {
            return true;
         }
      }

      public String getSelectorID() {
         return this.wlExp == null ? null : this.wlExp.getSelectorID();
      }
   }
}
