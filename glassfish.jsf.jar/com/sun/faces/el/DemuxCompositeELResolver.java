package com.sun.faces.el;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;

public class DemuxCompositeELResolver extends FacesCompositeELResolver {
   private final FacesCompositeELResolver.ELResolverChainType _chainType;
   private ELResolver[] _rootELResolvers = new ELResolver[2];
   private ELResolver[] _propertyELResolvers = new ELResolver[2];
   private ELResolver[] _allELResolvers = new ELResolver[2];
   private int _rootELResolverCount = 0;
   private int _propertyELResolverCount = 0;
   private int _allELResolverCount = 0;

   public DemuxCompositeELResolver(FacesCompositeELResolver.ELResolverChainType chainType) {
      if (chainType == null) {
         throw new NullPointerException();
      } else {
         this._chainType = chainType;
      }
   }

   public FacesCompositeELResolver.ELResolverChainType getChainType() {
      return this._chainType;
   }

   private void _addAllELResolver(ELResolver elResolver) {
      if (elResolver == null) {
         throw new NullPointerException();
      } else {
         if (this._allELResolverCount == this._allELResolvers.length) {
            ELResolver[] biggerResolvers = new ELResolver[this._allELResolverCount * 2];
            System.arraycopy(this._allELResolvers, 0, biggerResolvers, 0, this._allELResolverCount);
            this._allELResolvers = biggerResolvers;
         }

         this._allELResolvers[this._allELResolverCount] = elResolver;
         ++this._allELResolverCount;
      }
   }

   private void _addRootELResolver(ELResolver elResolver) {
      if (elResolver == null) {
         throw new NullPointerException();
      } else {
         if (this._rootELResolverCount == this._rootELResolvers.length) {
            ELResolver[] biggerResolvers = new ELResolver[this._rootELResolverCount * 2];
            System.arraycopy(this._rootELResolvers, 0, biggerResolvers, 0, this._rootELResolverCount);
            this._rootELResolvers = biggerResolvers;
         }

         this._rootELResolvers[this._rootELResolverCount] = elResolver;
         ++this._rootELResolverCount;
      }
   }

   public void _addPropertyELResolver(ELResolver elResolver) {
      if (elResolver == null) {
         throw new NullPointerException();
      } else {
         if (this._propertyELResolverCount == this._propertyELResolvers.length) {
            ELResolver[] biggerResolvers = new ELResolver[this._propertyELResolverCount * 2];
            System.arraycopy(this._propertyELResolvers, 0, biggerResolvers, 0, this._propertyELResolverCount);
            this._propertyELResolvers = biggerResolvers;
         }

         this._propertyELResolvers[this._propertyELResolverCount] = elResolver;
         ++this._propertyELResolverCount;
      }
   }

   public void addRootELResolver(ELResolver elResolver) {
      super.add(elResolver);
      this._addRootELResolver(elResolver);
      this._addAllELResolver(elResolver);
   }

   public void addPropertyELResolver(ELResolver elResolver) {
      super.add(elResolver);
      this._addPropertyELResolver(elResolver);
      this._addAllELResolver(elResolver);
   }

   public void add(ELResolver elResolver) {
      super.add(elResolver);
      this._addRootELResolver(elResolver);
      this._addPropertyELResolver(elResolver);
      this._addAllELResolver(elResolver);
   }

   private Object _getValue(int resolverCount, ELResolver[] resolvers, ELContext context, Object base, Object property) throws ELException {
      for(int i = 0; i < resolverCount; ++i) {
         Object result = resolvers[i].getValue(context, base, property);
         if (context.isPropertyResolved()) {
            return result;
         }
      }

      return null;
   }

   public Object getValue(ELContext context, Object base, Object property) throws ELException {
      context.setPropertyResolved(false);
      int resolverCount;
      ELResolver[] resolvers;
      if (base == null) {
         resolverCount = this._rootELResolverCount;
         resolvers = this._rootELResolvers;
      } else {
         resolverCount = this._propertyELResolverCount;
         resolvers = this._propertyELResolvers;
      }

      return this._getValue(resolverCount, resolvers, context, base, property);
   }

   private Class _getType(int resolverCount, ELResolver[] resolvers, ELContext context, Object base, Object property) throws ELException {
      for(int i = 0; i < resolverCount; ++i) {
         Class type = resolvers[i].getType(context, base, property);
         if (context.isPropertyResolved()) {
            return type;
         }
      }

      return null;
   }

   public Class getType(ELContext context, Object base, Object property) throws ELException {
      context.setPropertyResolved(false);
      int resolverCount;
      ELResolver[] resolvers;
      if (base == null) {
         resolverCount = this._rootELResolverCount;
         resolvers = this._rootELResolvers;
      } else {
         resolverCount = this._propertyELResolverCount;
         resolvers = this._propertyELResolvers;
      }

      return this._getType(resolverCount, resolvers, context, base, property);
   }

   private void _setValue(int resolverCount, ELResolver[] resolvers, ELContext context, Object base, Object property, Object val) throws ELException {
      for(int i = 0; i < resolverCount; ++i) {
         resolvers[i].setValue(context, base, property, val);
         if (context.isPropertyResolved()) {
            return;
         }
      }

   }

   public void setValue(ELContext context, Object base, Object property, Object val) throws ELException {
      context.setPropertyResolved(false);
      int resolverCount;
      ELResolver[] resolvers;
      if (base == null) {
         resolverCount = this._rootELResolverCount;
         resolvers = this._rootELResolvers;
      } else {
         resolverCount = this._propertyELResolverCount;
         resolvers = this._propertyELResolvers;
      }

      this._setValue(resolverCount, resolvers, context, base, property, val);
   }

   private boolean _isReadOnly(int resolverCount, ELResolver[] resolvers, ELContext context, Object base, Object property) throws ELException {
      for(int i = 0; i < resolverCount; ++i) {
         boolean isReadOnly = resolvers[i].isReadOnly(context, base, property);
         if (context.isPropertyResolved()) {
            return isReadOnly;
         }
      }

      return false;
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) throws ELException {
      context.setPropertyResolved(false);
      int resolverCount;
      ELResolver[] resolvers;
      if (base == null) {
         resolverCount = this._rootELResolverCount;
         resolvers = this._rootELResolvers;
      } else {
         resolverCount = this._propertyELResolverCount;
         resolvers = this._propertyELResolvers;
      }

      return this._isReadOnly(resolverCount, resolvers, context, base, property);
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return new DescriptorIterator(context, base, this._allELResolvers, this._allELResolverCount);
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      return null;
   }

   private static final class DescriptorIterator implements Iterator {
      private final ELContext _context;
      private final Object _base;
      private final ELResolver[] _resolvers;
      private final int _resolverCount;
      private int _currResolverIndex;
      private Iterator _currIterator;

      public DescriptorIterator(ELContext context, Object base, ELResolver[] resolvers, int resolverCount) {
         this._context = context;
         this._base = base;
         this._resolvers = resolvers;
         this._resolverCount = resolverCount;
      }

      public boolean hasNext() {
         while(true) {
            Iterator currIterator = this._getCurrIterator();
            if (null != currIterator) {
               if (currIterator.hasNext()) {
                  return true;
               }

               this._currIterator = null;
               ++this._currResolverIndex;
            } else if (this._currResolverIndex >= this._resolverCount) {
               return false;
            }
         }
      }

      private Iterator _getCurrIterator() {
         Iterator currIterator = this._currIterator;
         if (currIterator == null && this._currResolverIndex < this._resolverCount) {
            currIterator = this._resolvers[this._currResolverIndex].getFeatureDescriptors(this._context, this._base);
            ++this._currResolverIndex;
            this._currIterator = currIterator;
         }

         return currIterator;
      }

      public FeatureDescriptor next() {
         if (this.hasNext()) {
            return (FeatureDescriptor)this._getCurrIterator().next();
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
