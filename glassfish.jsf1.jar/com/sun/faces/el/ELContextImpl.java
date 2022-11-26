package com.sun.faces.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

public class ELContextImpl extends ELContext {
   private static final FunctionMapper functionMapper = new NoopFunctionMapper();
   private VariableMapper variableMapper;
   private ELResolver resolver;

   public ELContextImpl(ELResolver resolver) {
      this.resolver = resolver;
   }

   public FunctionMapper getFunctionMapper() {
      return functionMapper;
   }

   public VariableMapper getVariableMapper() {
      if (this.variableMapper == null) {
         this.variableMapper = new VariableMapperImpl();
      }

      return this.variableMapper;
   }

   public ELResolver getELResolver() {
      return this.resolver;
   }

   private static class NoopFunctionMapper extends FunctionMapper {
      private NoopFunctionMapper() {
      }

      public Method resolveFunction(String s, String s1) {
         return null;
      }

      // $FF: synthetic method
      NoopFunctionMapper(Object x0) {
         this();
      }
   }

   private static class VariableMapperImpl extends VariableMapper {
      private Map variables = new HashMap();

      public VariableMapperImpl() {
      }

      public ValueExpression resolveVariable(String s) {
         return (ValueExpression)this.variables.get(s);
      }

      public ValueExpression setVariable(String s, ValueExpression valueExpression) {
         return (ValueExpression)this.variables.put(s, valueExpression);
      }
   }
}
