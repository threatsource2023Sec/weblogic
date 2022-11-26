package com.bea.security.xacml;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CombiningCollection implements Collection {
   private Collection members;

   public CombiningCollection(Collection members) {
      this.members = members;
   }

   public int size() {
      int s = 0;

      Collection e;
      for(Iterator var2 = this.members.iterator(); var2.hasNext(); s += e.size()) {
         e = (Collection)var2.next();
      }

      return s;
   }

   public boolean isEmpty() {
      Iterator var1 = this.members.iterator();

      Collection e;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         e = (Collection)var1.next();
      } while(!e.isEmpty());

      return true;
   }

   public boolean contains(Object arg0) {
      Iterator var2 = this.members.iterator();

      Collection e;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         e = (Collection)var2.next();
      } while(!e.contains(arg0));

      return true;
   }

   public Iterator iterator() {
      Collection its = new ArrayList();
      Iterator var2 = this.members.iterator();

      while(var2.hasNext()) {
         Collection e = (Collection)var2.next();
         its.add(e.iterator());
      }

      return new CombiningIterator(its);
   }

   public Object[] toArray() {
      int size = this.size();
      Object[] result = new Object[size];
      int idx = 0;

      for(Iterator it = this.iterator(); it.hasNext(); result[idx++] = it.next()) {
      }

      return result;
   }

   public Object[] toArray(Object[] arg0) {
      int size = this.size();
      if (arg0.length < size) {
         arg0 = (Object[])((Object[])Array.newInstance(arg0.getClass().getComponentType(), size));
      }

      int idx = 0;

      for(Iterator it = this.iterator(); it.hasNext(); arg0[idx++] = it.next()) {
      }

      if (arg0.length > size) {
         arg0[size] = null;
      }

      return arg0;
   }

   public boolean add(Object arg0) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object arg0) {
      throw new UnsupportedOperationException();
   }

   public boolean containsAll(Collection arg0) {
      if (arg0 == null) {
         return true;
      } else {
         Iterator var2 = arg0.iterator();

         Object o;
         do {
            if (!var2.hasNext()) {
               return true;
            }

            o = var2.next();
         } while(this.contains(o));

         return false;
      }
   }

   public boolean addAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection arg0) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }
}
