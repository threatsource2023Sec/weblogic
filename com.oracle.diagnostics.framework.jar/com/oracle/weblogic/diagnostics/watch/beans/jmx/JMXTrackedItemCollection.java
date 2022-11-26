package com.oracle.weblogic.diagnostics.watch.beans.jmx;

import com.oracle.weblogic.diagnostics.expressions.AbstractTrackedItemCollection;
import com.oracle.weblogic.diagnostics.expressions.DiagnosticsELContext;
import com.oracle.weblogic.diagnostics.expressions.EvaluationContext;
import com.oracle.weblogic.diagnostics.expressions.EvaluationContextHelper;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBeanRuntimeException;
import com.oracle.weblogic.diagnostics.expressions.MBeanELResolver;
import com.oracle.weblogic.diagnostics.expressions.Traceable;
import com.oracle.weblogic.diagnostics.expressions.TrackedValue;
import com.oracle.weblogic.diagnostics.expressions.TrackedValueSource;
import com.oracle.weblogic.diagnostics.expressions.UnknownValueType;
import com.oracle.weblogic.diagnostics.expressions.Utils;
import com.oracle.weblogic.diagnostics.utils.DiagnosticsUtils;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.el.ExpressionFactory;
import javax.el.LambdaExpression;
import javax.el.ValueExpression;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.GlobalServiceLocator;

public class JMXTrackedItemCollection extends AbstractTrackedItemCollection implements TrackedValueSource {
   private static final String FORMAL_LAMBDA_PARAM = "x";
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionMetrics");
   private static ServiceLocator locator = GlobalServiceLocator.getServiceLocator();
   private static ExpressionFactory exprFactory = ExpressionFactory.newInstance();
   private JMXMBeanServerSource source;
   private String namePattern;
   private String attributePattern;
   private String key;
   private QueryExp queryFilter;
   private String attributeExpression;

   public JMXTrackedItemCollection(JMXMBeanServerSource source, String namePattern, String attributePattern, String key) {
      this(source, namePattern, attributePattern, key, (QueryExp)null);
   }

   public JMXTrackedItemCollection(JMXMBeanServerSource source, String namePattern, String attributePattern, String key, QueryExp filter) {
      this(source, namePattern, attributePattern);
      this.key = key;
      this.queryFilter = filter;
   }

   public JMXTrackedItemCollection(JMXMBeanServerSource source, String namePattern, String attributePattern) {
      this.source = source;
      this.namePattern = namePattern;
      this.attributePattern = attributePattern;
      this.attributeExpression = "${x." + attributePattern + "}";
   }

   public String getInstanceName() {
      return this.getKey();
   }

   public boolean equals(Object obj) {
      if (obj instanceof JMXTrackedItemCollection) {
         JMXTrackedItemCollection rhs = (JMXTrackedItemCollection)obj;
         if (this.key.equals(rhs.getKey())) {
            return this.queryFilter == rhs.queryFilter;
         }
      }

      return false;
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.source.getName(), this.namePattern, this.attributePattern, this.queryFilter});
   }

   public Iterator iterator() {
      return new MetricsIterator();
   }

   public String getKey() {
      if (this.key == null) {
         this.key = this.source.getName() + "//" + this.namePattern + "//" + this.attributePattern;
      }

      return this.key;
   }

   protected Set getMatchingInstances(String pattern) {
      try {
         return this.source.getMBeanServerConnection().queryNames(new ObjectName(pattern), this.queryFilter);
      } catch (IOException | MalformedObjectNameException var3) {
         throw new ExpressionBeanRuntimeException(var3);
      }
   }

   protected String getNamePattern() {
      return this.namePattern;
   }

   protected String getAttributePattern() {
      return this.attributePattern;
   }

   protected boolean isIgnorable(Throwable t) {
      return DiagnosticsUtils.isIgnorable(t);
   }

   protected boolean isCommunicationsException(Throwable t) {
      return DiagnosticsUtils.isCommunicationsException(t);
   }

   private DiagnosticsELContext getELContext() {
      DiagnosticsELContext elContext = new DiagnosticsELContext(new Annotation[0]);
      locator.inject(elContext);
      locator.postConstruct(elContext);
      elContext.insertELResolver(new MBeanELResolver(this.source.getMBeanServerConnection()));
      return elContext;
   }

   private DiagnosticsELContext getThreadLocalELContext() {
      EvaluationContext currentEvalContext = EvaluationContextHelper.getCurrentContext();
      return currentEvalContext != null ? currentEvalContext.getELContext() : null;
   }

   private class MetricsIterator implements Iterator {
      private Set instances;
      private Iterator metrics;

      private MetricsIterator() {
      }

      public boolean hasNext() {
         if (this.metrics == null) {
            this.identifyMetricsSet();
         }

         return this.metrics.hasNext();
      }

      public TrackedValue next() {
         if (this.metrics == null) {
            this.identifyMetricsSet();
         }

         return (TrackedValue)this.metrics.next();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      private void identifyMetricsSet() {
         List metricSet = new ArrayList();

         try {
            if (this.instances == null) {
               this.instances = JMXTrackedItemCollection.this.getMatchingInstances(JMXTrackedItemCollection.this.namePattern);
            }

            if (this.instances.size() > 0) {
               DiagnosticsELContext elContext = JMXTrackedItemCollection.this.getELContext();
               DiagnosticsELContext parentContext = JMXTrackedItemCollection.this.getThreadLocalELContext();
               ValueExpression lambdaValueExpression = JMXTrackedItemCollection.exprFactory.createValueExpression(elContext, JMXTrackedItemCollection.this.attributeExpression, Object.class);
               LambdaExpression lambda = new LambdaExpression(Arrays.asList("x"), lambdaValueExpression);
               lambda.setELContext(elContext);
               Iterator it = this.instances.iterator();

               while(it.hasNext()) {
                  ObjectName currentInstance = (ObjectName)it.next();
                  Object evalResult = UnknownValueType.UNKNOWN_VALUE;
                  TrackedValue tv = null;

                  try {
                     evalResult = lambda.invoke(new Object[]{currentInstance});
                  } catch (Throwable var11) {
                     if (!JMXTrackedItemCollection.this.isIgnorable(var11)) {
                        throw var11;
                     }

                     if (JMXTrackedItemCollection.debugLogger.isDebugEnabled()) {
                        JMXTrackedItemCollection.debugLogger.debug("Caught unexpected exception for " + currentInstance + ", ignoring", var11);
                     }
                  }

                  if (evalResult != null) {
                     String instanceName = currentInstance.getCanonicalName();
                     if (evalResult instanceof TrackedValue) {
                        tv = (TrackedValue)evalResult;
                     } else {
                        tv = Utils.convertPrimitiveToMetricValue(evalResult, JMXTrackedItemCollection.this.attributePattern, (Traceable)null, instanceName);
                        if (JMXTrackedItemCollection.debugLogger.isDebugEnabled()) {
                           JMXTrackedItemCollection.debugLogger.debug("adding value: " + tv);
                        }
                     }
                  }

                  metricSet.add(tv);
                  if (parentContext != null) {
                     if (JMXTrackedItemCollection.debugLogger.isDebugEnabled()) {
                        JMXTrackedItemCollection.debugLogger.debug("Adding resolved value to parent context: " + tv);
                     }

                     parentContext.addResolvedValue(tv);
                  }
               }
            }

            this.metrics = metricSet.iterator();
         } catch (ExpressionBeanRuntimeException var12) {
            throw var12;
         } catch (Throwable var13) {
            throw new ExpressionBeanRuntimeException(var13);
         }
      }

      // $FF: synthetic method
      MetricsIterator(Object x1) {
         this();
      }
   }
}
