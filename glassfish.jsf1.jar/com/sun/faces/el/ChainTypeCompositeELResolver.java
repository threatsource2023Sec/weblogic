package com.sun.faces.el;

import com.sun.faces.util.RequestStateManager;
import java.util.Iterator;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;

public final class ChainTypeCompositeELResolver extends FacesCompositeELResolver {
   private final FacesCompositeELResolver _wrapped;
   private final FacesCompositeELResolver.ELResolverChainType _chainType;

   public void addRootELResolver(ELResolver elResolver) {
      this._wrapped.addRootELResolver(elResolver);
   }

   public void addPropertyELResolver(ELResolver elResolver) {
      this._wrapped.addPropertyELResolver(elResolver);
   }

   public void add(ELResolver elResolver) {
      this._wrapped.add(elResolver);
   }

   public Object getValue(ELContext context, Object base, Object property) throws ELException {
      FacesContext ctx = this.getFacesContext(context);
      if (ctx == null) {
         return null;
      } else {
         Map stateMap = RequestStateManager.getStateMap(ctx);
         stateMap.put("com.sun.faces.ELResolverChainType", this._chainType);
         Object result = null;

         try {
            result = this._wrapped.getValue(context, base, property);
         } finally {
            stateMap.remove("com.sun.faces.ELResolverChainType");
         }

         return result;
      }
   }

   public Class getType(ELContext context, Object base, Object property) throws ELException {
      FacesContext ctx = this.getFacesContext(context);
      if (ctx == null) {
         return null;
      } else {
         Map stateMap = RequestStateManager.getStateMap(ctx);
         stateMap.put("com.sun.faces.ELResolverChainType", this._chainType);
         Class result = null;

         try {
            result = this._wrapped.getType(context, base, property);
         } finally {
            stateMap.remove("com.sun.faces.ELResolverChainType");
         }

         return result;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object val) throws ELException {
      FacesContext ctx = this.getFacesContext(context);
      if (ctx != null) {
         Map stateMap = RequestStateManager.getStateMap(ctx);
         stateMap.put("com.sun.faces.ELResolverChainType", this._chainType);

         try {
            this._wrapped.setValue(context, base, property, val);
         } finally {
            stateMap.remove("com.sun.faces.ELResolverChainType");
         }

      }
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) throws ELException {
      FacesContext ctx = this.getFacesContext(context);
      if (ctx == null) {
         return false;
      } else {
         Map stateMap = RequestStateManager.getStateMap(ctx);
         stateMap.put("com.sun.faces.ELResolverChainType", this._chainType);
         boolean result = false;

         try {
            result = this._wrapped.isReadOnly(context, base, property);
         } finally {
            stateMap.remove("com.sun.faces.ELResolverChainType");
         }

         return result;
      }
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      FacesContext ctx = this.getFacesContext(context);
      Map stateMap = RequestStateManager.getStateMap(ctx);
      stateMap.put("com.sun.faces.ELResolverChainType", this._chainType);
      Iterator result = null;

      try {
         result = this._wrapped.getFeatureDescriptors(context, base);
      } finally {
         stateMap.remove("com.sun.faces.ELResolverChainType");
      }

      return result;
   }

   public final FacesCompositeELResolver.ELResolverChainType getChainType() {
      return this._chainType;
   }

   public ChainTypeCompositeELResolver(FacesCompositeELResolver.ELResolverChainType chainType) {
      this._wrapped = new DemuxCompositeELResolver(chainType);
      this._chainType = chainType;
   }

   public ChainTypeCompositeELResolver(FacesCompositeELResolver delegate) {
      this._wrapped = delegate;
      this._chainType = delegate.getChainType();
   }

   private FacesContext getFacesContext(ELContext elContext) {
      return (FacesContext)elContext.getContext(FacesContext.class);
   }
}
