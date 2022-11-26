package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.expressions.poller.CircularValuesBuffer;
import com.oracle.weblogic.diagnostics.expressions.poller.ExpressionWrapperFactory;
import com.oracle.weblogic.diagnostics.expressions.poller.Poller;
import com.oracle.weblogic.diagnostics.expressions.poller.PollerFactory;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.el.CompositeELResolver;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.StandardELContext;
import javax.el.VariableMapper;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;

public class DiagnosticsELContext extends StandardELContext {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsELContext");
   @Inject
   private ExpressionWrapperFactory wrapperFactory;
   @Inject
   private ServiceLocator locator;
   @Inject
   private PollerFactory pollerFactory;
   private FunctionMapper functionMapper;
   private VariableMapper variableMapper;
   private DiagnosticsBeanResolver beanResolver = new DiagnosticsBeanResolver();
   private BeanExtensionResolver beanExtensionResolver;
   private ValueTracingELResolver tracingResolver;
   private CompositeELResolver delegatesResolver;
   private Annotation[] qualifiers;

   public DiagnosticsELContext(Annotation... _qualifiers) {
      super(ExpressionFactory.newInstance());
      this.qualifiers = _qualifiers;
      this.functionMapper = new DiagnosticsFunctionMapper(this.qualifiers);
      this.beanExtensionResolver = new BeanExtensionResolver(this.qualifiers);
   }

   public void insertELResolver(ELResolver resolver) {
      this.delegatesResolver.add(resolver);
   }

   public ELResolver getELResolver() {
      if (this.tracingResolver == null) {
         this.tracingResolver = new ValueTracingELResolver(super.getELResolver());
      }

      return this.tracingResolver;
   }

   public FunctionMapper getFunctionMapper() {
      return this.functionMapper;
   }

   public VariableMapper getVariableMapper() {
      return this.variableMapper;
   }

   @PostConstruct
   private void postConstruct() {
      if (this.locator != null) {
         this.locator.inject(this.functionMapper);
         this.locator.postConstruct(this.functionMapper);
         this.locator.inject(this.beanExtensionResolver);
         this.locator.postConstruct(this.beanExtensionResolver);
      }

      if (this.pollerFactory != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Installing context-local polling factory into context map");
         }

         this.putContext(PollerFactory.class, this.pollerFactory);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("No context-local polling factory available, extract() operation will not work");
      }

      this.delegatesResolver = new CompositeELResolver();
      super.addELResolver(this.delegatesResolver);
      super.addELResolver(this.beanResolver);
      if (this.locator != null) {
         DiagnosticsELResolver diagELResolver = (DiagnosticsELResolver)this.locator.createAndInitialize(DiagnosticsELResolver.class);
         super.addELResolver(diagELResolver);
      }

      if (this.beanExtensionResolver != null) {
         super.addELResolver(this.beanExtensionResolver);
      }

   }

   @PreDestroy
   private void preDestroy() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Shutting down DiagnosticsELContext");
      }

      this.pollerFactory.shutdown();
      this.wrapperFactory.destroy();
   }

   public void setFunctionMapper(FunctionMapper functionMapper) {
      throw new UnsupportedOperationException();
   }

   public void setVariableMapper(VariableMapper variableMapper) {
      this.variableMapper = variableMapper;
   }

   public Object unbind(String name) {
      return this.beanResolver.unbind(name);
   }

   public void bind(String var, Object o) {
      this.beanResolver.bind(var, o);
   }

   public Object forceBind(String var, Object o) {
      Object previous = this.beanResolver.unbind(var);
      this.beanResolver.bind(var, o);
      return previous;
   }

   public void addResolvedValue(TrackedValue tv) {
      this.tracingResolver.addResolvedValue(tv);
   }

   Map getResolvedValues() {
      Map valuesMap = this.tracingResolver.buildResolvedValuesMap();
      Collection pollers = this.pollerFactory.getPollers();
      Iterator var3 = pollers.iterator();

      while(var3.hasNext()) {
         Poller p = (Poller)var3.next();
         Collection pollerData = p.getResolvedValues();
         Iterator valuesIt = pollerData.iterator();

         while(valuesIt.hasNext()) {
            CircularValuesBuffer row = (CircularValuesBuffer)valuesIt.next();
            TrackedValue headValue = (TrackedValue)row.peek();
            Object[] values = row.toArray(new Object[row.size()]);
            valuesMap.put(headValue.getKey(), values);
         }
      }

      return valuesMap;
   }

   void resetResolvedValues() {
      if (this.tracingResolver != null) {
         this.tracingResolver.resetResolvedValues();
      }

   }

   public Object convertToType(Object obj, Class targetType) {
      if (obj instanceof String && targetType == TrackedValueSource.class) {
         String subExpression = (String)obj;
         return this.wrapperFactory.findOrCreateWrapper(subExpression, this.qualifiers);
      } else {
         return super.convertToType(obj, targetType);
      }
   }
}
