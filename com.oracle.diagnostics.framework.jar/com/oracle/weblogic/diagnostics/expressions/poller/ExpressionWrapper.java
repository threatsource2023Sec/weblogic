package com.oracle.weblogic.diagnostics.expressions.poller;

import com.oracle.weblogic.diagnostics.expressions.EvaluatorFactory;
import com.oracle.weblogic.diagnostics.expressions.FixedExpressionEvaluator;
import com.oracle.weblogic.diagnostics.expressions.Traceable;
import com.oracle.weblogic.diagnostics.expressions.TrackedValueSource;
import com.oracle.weblogic.diagnostics.expressions.Utils;
import com.oracle.weblogic.diagnostics.utils.DiagnosticsUtils;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

public class ExpressionWrapper implements TrackedValueSource {
   @Inject
   private EvaluatorFactory evalFactory;
   private String expression;
   private FixedExpressionEvaluator evaluator;
   private Annotation[] qualifiers;

   public ExpressionWrapper(String subExpression, Annotation... qualifiers) {
      this.expression = subExpression;
      this.qualifiers = qualifiers;
   }

   @PostConstruct
   public void postConstruct() {
      this.evaluator = this.evalFactory.createEvaluator(this.expression, Object.class, this.qualifiers);
   }

   @PreDestroy
   public void preDestroy() {
      if (this.evaluator != null) {
         this.evalFactory.destroyEvaluator(this.evaluator);
      }

   }

   public String getKey() {
      return this.expression;
   }

   public Iterator iterator() {
      Object value = this.evaluator != null ? this.evaluator.evaluate() : null;
      return (Iterator)(value != null && value instanceof Iterable ? new IterableWrapper(((Iterable)value).iterator()) : new SingleValueIterator(value));
   }

   public Traceable getTraceableParent() {
      return null;
   }

   public String getInstanceName() {
      return this.expression;
   }

   public class SingleValueIterator implements Iterator {
      private Object value;
      private boolean hasNext;

      public SingleValueIterator(Object value) {
         this.value = value;
         this.hasNext = value != null;
      }

      public boolean hasNext() {
         return this.hasNext;
      }

      public Traceable next() {
         if (this.hasNext) {
            this.hasNext = false;
            return (Traceable)(DiagnosticsUtils.isLeafValueType(this.value.getClass()) ? Utils.convertPrimitiveToMetricValue(this.value, (String)null, ExpressionWrapper.this, ExpressionWrapper.this.expression) : (Traceable)this.value);
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   public class IterableWrapper implements Iterator {
      private Iterator delegate;

      public IterableWrapper(Iterator iterator) {
         this.delegate = iterator;
      }

      public boolean hasNext() {
         return this.delegate.hasNext();
      }

      public Traceable next() {
         Object nextVal = this.delegate.next();
         if (nextVal != null && DiagnosticsUtils.isLeafValueType(nextVal.getClass())) {
            nextVal = Utils.convertPrimitiveToMetricValue(nextVal, (String)null, ExpressionWrapper.this, ExpressionWrapper.this.expression);
         }

         return (Traceable)nextVal;
      }

      public void remove() {
         this.delegate.remove();
      }
   }
}
