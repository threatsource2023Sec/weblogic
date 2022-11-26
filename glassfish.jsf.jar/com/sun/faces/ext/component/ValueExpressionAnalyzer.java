package com.sun.faces.ext.component;

import java.util.Iterator;
import java.util.Locale;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.el.CompositeComponentExpressionHolder;

class ValueExpressionAnalyzer {
   private ValueExpression expression;

   public ValueExpressionAnalyzer(ValueExpression expression) {
      this.expression = expression;
   }

   public ValueReference getReference(ELContext elContext) {
      InterceptingResolver resolver = new InterceptingResolver(elContext.getELResolver());

      try {
         this.expression.setValue(this.decorateELContext(elContext, resolver), (Object)null);
      } catch (ELException var6) {
         return null;
      }

      ValueReference reference = resolver.getValueReference();
      if (reference != null) {
         Object base = reference.getBase();
         if (base instanceof CompositeComponentExpressionHolder) {
            ValueExpression ve = ((CompositeComponentExpressionHolder)base).getExpression(reference.getProperty());
            if (ve != null) {
               this.expression = ve;
               reference = this.getReference(elContext);
            }
         }
      }

      return reference;
   }

   private ELContext decorateELContext(final ELContext context, final ELResolver resolver) {
      return new ELContext() {
         public ELResolver getELResolver() {
            return resolver;
         }

         public Object getContext(Class key) {
            return context.getContext(key);
         }

         public Locale getLocale() {
            return context.getLocale();
         }

         public boolean isPropertyResolved() {
            return context.isPropertyResolved();
         }

         public void putContext(Class key, Object contextObject) {
            context.putContext(key, contextObject);
         }

         public void setLocale(Locale locale) {
            context.setLocale(locale);
         }

         public void setPropertyResolved(boolean resolved) {
            context.setPropertyResolved(resolved);
         }

         public FunctionMapper getFunctionMapper() {
            return context.getFunctionMapper();
         }

         public VariableMapper getVariableMapper() {
            return context.getVariableMapper();
         }
      };
   }

   private static class InterceptingResolver extends ELResolver {
      private ELResolver delegate;
      private ValueReference valueReference;

      public InterceptingResolver(ELResolver delegate) {
         this.delegate = delegate;
      }

      public ValueReference getValueReference() {
         return this.valueReference;
      }

      public void setValue(ELContext context, Object base, Object property, Object value) {
         if (base != null && property != null) {
            context.setPropertyResolved(true);
            this.valueReference = new ValueReference(base, property.toString());
         }

      }

      public Object getValue(ELContext context, Object base, Object property) {
         return this.delegate.getValue(context, base, property);
      }

      public Class getType(ELContext context, Object base, Object property) {
         return this.delegate.getType(context, base, property);
      }

      public boolean isReadOnly(ELContext context, Object base, Object property) {
         return this.delegate.isReadOnly(context, base, property);
      }

      public Iterator getFeatureDescriptors(ELContext context, Object base) {
         return this.delegate.getFeatureDescriptors(context, base);
      }

      public Class getCommonPropertyType(ELContext context, Object base) {
         return this.delegate.getCommonPropertyType(context, base);
      }
   }
}
