package com.oracle.weblogic.diagnostics.expressions;

import java.lang.annotation.Annotation;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.JFRDebug;
import weblogic.utils.PropertyHelper;

public class ExpressionEvaluatorImpl implements FixedExpressionEvaluator {
   private static final boolean DEBUG_JFR = PropertyHelper.getBoolean("weblogic.diagnostics.evaluator.jfrdebug", false);
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionEvaluators");
   private static ExpressionFactory factory = ExpressionFactory.newInstance();
   private String expression;
   @Inject
   private ServiceLocator locator;
   private DiagnosticsELContext internalContext;
   private Class expectedType;
   private Annotation[] qualifiers;
   private String formattedExpression;

   public ExpressionEvaluatorImpl(String storedExpression, Class expectedResultType, Annotation... scopeList) {
      this.expression = storedExpression;
      this.expectedType = expectedResultType;
      this.qualifiers = scopeList;
   }

   public String getFixedExpression() {
      return this.expression;
   }

   public Object evaluate() {
      return this.evaluateExpression(this.getFormattedExpression(), this.expectedType);
   }

   public Object evaluate(String expr, Class expectedType) {
      return this.evaluateExpression(expr, expectedType);
   }

   public Class getType(String expression) {
      Class type = null;
      if (expression != null) {
         DiagnosticsELContext context = this.getELContext();
         String normalizedExpression = expression.trim();
         if (!normalizedExpression.startsWith("${")) {
            normalizedExpression = "${" + normalizedExpression + "}";
         }

         ValueExpression ve = factory.createValueExpression(context, normalizedExpression, Object.class);
         type = ve.getType(context);
         if (type == null) {
            Object value = ve.getValue(context);
            type = value == null ? null : value.getClass();
         }
      }

      return type;
   }

   public DiagnosticsELContext getELContext() {
      if (this.internalContext == null) {
         Object timingEvent = DEBUG_JFR ? JFRDebug.beginDebugTimedEvent("ExpressionEvaluator", "ELContextInitTimer", (Object)null) : null;

         try {
            this.internalContext = new DiagnosticsELContext(this.qualifiers);
            this.locator.inject(this.internalContext);
            this.locator.postConstruct(this.internalContext);
         } finally {
            if (DEBUG_JFR) {
               JFRDebug.commitDebugTimedEvent(timingEvent);
            }

         }
      }

      return this.internalContext;
   }

   @PreDestroy
   public void preDestroy() {
      if (this.internalContext != null) {
         if (DEBUG_JFR) {
            JFRDebug.generateDebugEvent("Evaluator", "destroying ELContext", (Throwable)null);
         }

         this.locator.preDestroy(this.internalContext);
      }

   }

   public Map getResolvedValues() {
      return this.getELContext().getResolvedValues();
   }

   private String buildExpressionString(String expression) {
      if (expression != null) {
         StringBuffer expressionBuf = new StringBuffer(expression.length() + 3);
         expressionBuf.append("${");
         expressionBuf.append(expression);
         expressionBuf.append("}");
         return expressionBuf.toString();
      } else {
         return expression;
      }
   }

   private Object evaluateExpression(String forExpression, Class forType) {
      EvaluationContext tlsEvalContext = EvaluationContextHelper.getCurrentContext();
      EvaluationContext useEvalContext = tlsEvalContext;
      Object value = null;
      Object timingEvent = DEBUG_JFR ? JFRDebug.beginDebugTimedEvent("ExpressionEvaluator", "ExressionTimer", (Object)null) : null;
      if (tlsEvalContext == null) {
         DiagnosticsELContext elContext = this.getELContext();
         elContext.resetResolvedValues();
         useEvalContext = new EvaluationContext(elContext);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("SETTING NEW EvalContext, instance:" + useEvalContext);
         }

         EvaluationContextHelper.setCurrentContext(useEvalContext);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("USING CACHED EvalContext, instance:" + tlsEvalContext);
      }

      try {
         ValueExpression ve = factory.createValueExpression(useEvalContext.getELContext(), forExpression, forType);
         value = ve.getValue(useEvalContext.getELContext());
      } finally {
         if (DEBUG_JFR) {
            JFRDebug.commitDebugTimedEvent(timingEvent);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("RESETTING EvalContext, instance:" + tlsEvalContext);
         }

         EvaluationContextHelper.setCurrentContext(tlsEvalContext);
      }

      return value;
   }

   private String getFormattedExpression() {
      if (this.formattedExpression == null) {
         this.formattedExpression = this.buildExpressionString(this.expression.trim());
      }

      return this.formattedExpression;
   }
}
