package com.oracle.cmm.lowertier.pressure;

import com.oracle.cmm.lowertier.el.ELContextImpl;
import com.oracle.cmm.lowertier.el.ELFunctionMapperImpl;
import com.oracle.cmm.lowertier.el.ELResolverImpl;
import com.oracle.cmm.lowertier.el.ELVariableMapperImpl;
import com.oracle.cmm.lowertier.gathering.StatisticsGatherer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.CompositeELResolver;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

public class EL2ExpressionEvaluator implements ResourcePressureEvaluator {
   private static final Logger LOGGER = Logger.getLogger(EL2ExpressionEvaluator.class.getPackage().getName());
   private static final String DEFAULT_PREFIX = "com.oracle.cmm.lowertier.pressure.EL2ExpressionEvaluator";
   private ELContextImpl context = null;
   private ELVariableMapperImpl variableMapper = null;
   private ELFunctionMapperImpl functionMapper = null;
   private ELResolverImpl beanResolver = null;
   private CompositeELResolver resolver = null;
   private ExpressionFactory exFactory = null;
   private ValueExpression[] valueExpressions = null;
   private EvaluationManager evaluationManager = null;

   public EL2ExpressionEvaluator() {
      this.resolver = new CompositeELResolver();
      this.beanResolver = new ELResolverImpl();
      this.evaluationManager = EvaluationManager.getEvaluationManager();
      StatisticsGatherer[] gatherers = this.evaluationManager.getStatisticsGatherers();
      if (gatherers != null) {
         for(int i = 0; i < gatherers.length; ++i) {
            if (gatherers[i] != null) {
               this.beanResolver.addBean(gatherers[i].ELBeanName(), gatherers[i]);
            }
         }
      }

      this.resolver.add(this.beanResolver);
      this.variableMapper = new ELVariableMapperImpl();
      this.functionMapper = new ELFunctionMapperImpl();
      this.context = new ELContextImpl(this.resolver, this.variableMapper, this.functionMapper);
   }

   public void initialize(String initialValue) {
      this.exFactory = ExpressionFactory.newInstance();
      if (LOGGER.isLoggable(Level.FINER)) {
         LOGGER.finer("Expression factory: " + (this.exFactory == null ? "null" : this.exFactory.getClass().getName()));
      }

      String prefix = initialValue != null && initialValue.trim().length() != 0 ? initialValue.trim() : "com.oracle.cmm.lowertier.pressure.EL2ExpressionEvaluator";
      Properties props = EvaluationManager.getEvaluationManager().getProperties(prefix);
      this.valueExpressions = new ValueExpression[11];
      if (this.exFactory == null) {
         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("No expression factory, can't create expressions");
         }

      } else {
         this.valueExpressions[0] = this.getExpression(props, prefix, 0);
         this.valueExpressions[1] = this.getExpression(props, prefix, 1);
         this.valueExpressions[2] = this.getExpression(props, prefix, 2);
         this.valueExpressions[3] = this.getExpression(props, prefix, 3);
         this.valueExpressions[4] = this.getExpression(props, prefix, 4);
         this.valueExpressions[5] = this.getExpression(props, prefix, 5);
         this.valueExpressions[6] = this.getExpression(props, prefix, 6);
         this.valueExpressions[7] = this.getExpression(props, prefix, 7);
         this.valueExpressions[8] = this.getExpression(props, prefix, 8);
         this.valueExpressions[9] = this.getExpression(props, prefix, 9);
         this.valueExpressions[10] = this.getExpression(props, prefix, 10);
      }
   }

   private ValueExpression getExpression(Properties props, String prefix, int index) {
      try {
         if (props != null && !props.isEmpty() && index >= 0 && index <= 10) {
            String expressionString = props.getProperty(prefix + "." + index);
            if (expressionString == null) {
               return null;
            } else {
               if (LOGGER.isLoggable(Level.FINER)) {
                  LOGGER.finer("Attempting to create expression for level " + index + ", expression found: " + expressionString);
               }

               return this.exFactory.createValueExpression(this.context, expressionString, Boolean.class);
            }
         } else {
            return null;
         }
      } catch (Throwable var5) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var5.printStackTrace();
         }

         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("No expression created for level " + index);
         }

         return null;
      }
   }

   public int evaluateMemoryPressure() {
      int computedPressure = 0;

      try {
         this.evaluationManager.gatherStatistics();

         for(int i = 10; i >= 0; --i) {
            if (this.valueExpressions[i] != null) {
               Boolean value = (Boolean)this.valueExpressions[i].getValue(this.context);
               if (LOGGER.isLoggable(Level.FINER)) {
                  LOGGER.finer("Expression " + i + "  evaluated to " + value);
               }

               if (value) {
                  return i;
               }
            }
         }
      } catch (Throwable var4) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var4.printStackTrace();
         }
      }

      return computedPressure;
   }
}
