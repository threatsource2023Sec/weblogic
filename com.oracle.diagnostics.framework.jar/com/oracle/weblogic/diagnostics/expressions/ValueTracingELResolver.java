package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.utils.DiagnosticsUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELResolver;
import weblogic.diagnostics.debug.DebugLogger;

class ValueTracingELResolver extends ELResolver {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsValueTracingELResolver");
   private ELResolver delegate;
   private Deque chain;
   private List resolvedValues = new ArrayList();

   ValueTracingELResolver(ELResolver delegate) {
      this.delegate = delegate;
   }

   public Class getCommonPropertyType(ELContext arg0, Object arg1) {
      return this.delegate.getCommonPropertyType(arg0, arg1);
   }

   public Iterator getFeatureDescriptors(ELContext arg0, Object arg1) {
      return this.delegate.getFeatureDescriptors(arg0, arg1);
   }

   public Class getType(ELContext context, Object base, Object property) {
      return this.delegate.getType(context, base, property);
   }

   public Object getValue(ELContext context, Object base, Object property) {
      String baseName = base == null ? "" : base.toString();
      String propName = property == null ? "" : property.toString();
      Object value = this.delegate.getValue(context, base, property);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Snooper: resolved=" + context.isPropertyResolved() + ", base=" + baseName + ", value: " + propName);
      }

      if (context.isPropertyResolved() && value != null) {
         if (base == null) {
            this.startTrace();
         }

         this.addToTrace(base, property, value);
         TrackedValue mv = null;
         if (value instanceof TrackedValue) {
            mv = (TrackedValue)value;
            ((TrackedValue)mv).setValuePath((PathBuilder)(new ValuePathBuilderImpl(this.chain)));
         } else if (DiagnosticsUtils.isLeafValueType(value.getClass())) {
            mv = new RawTrackedValue(this.chain, value);
         }

         if (mv != null) {
            this.addResolvedValue((TrackedValue)mv);
            this.startTrace();
         }
      }

      return value;
   }

   private void addToTrace(Object base, Object property, Object value) {
      if (this.chain != null) {
         this.chain.push(new AttributeTerm(base, property, value));
      }

   }

   private void startTrace() {
      this.chain = new ArrayDeque();
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      return this.delegate.isReadOnly(context, base, property);
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
      this.delegate.setValue(context, base, property, value);
   }

   public Object invoke(ELContext context, Object base, Object method, Class[] paramTypes, Object[] params) {
      Object result = this.delegate.invoke(context, base, method, paramTypes, params);
      if (context.isPropertyResolved() && this.chain != null && result instanceof TrackedValue) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Resolved value: " + result);
         }

         this.startTrace();
         this.addResolvedValue((TrackedValue)result);
      }

      return result;
   }

   public Map buildResolvedValuesMap() {
      Map valuesMap = new HashMap();
      Iterator var2 = this.resolvedValues.iterator();

      while(var2.hasNext()) {
         TrackedValue v = (TrackedValue)var2.next();
         valuesMap.put(v.getKey(), v.getValue());
      }

      return valuesMap;
   }

   public void addResolvedValue(TrackedValue tv) {
      this.resolvedValues.add(tv);
   }

   public void resetResolvedValues() {
      this.resolvedValues.clear();
   }

   static class RawTrackedValue extends AbstractTrackedValue {
      private Deque termPath;

      public RawTrackedValue(Deque chain, Object value) {
         super((String)null, (String)null, value);
         this.termPath = chain;
         super.setValuePath((PathBuilder)(new ValuePathBuilderImpl(chain)));
      }

      public Traceable getTraceableParent() {
         if (super.getTraceableParent() == null) {
            super.setTraceableParent(Utils.findParentInChain(this.termPath));
         }

         return super.getTraceableParent();
      }

      public String getInstanceName() {
         if (super.getInstanceName() == null) {
            this.setInstanceName(this.getTraceableParent() != null ? this.getTraceableParent().getInstanceName() : null);
         }

         return super.getInstanceName();
      }
   }
}
