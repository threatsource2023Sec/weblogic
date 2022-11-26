package org.apache.taglibs.standard.tag.common.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import javax.el.ValueExpression;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTagSupport;
import org.apache.taglibs.standard.resources.Resources;

public abstract class ForEachSupport extends LoopTagSupport {
   protected ForEachIterator items;
   protected Object rawItems;

   protected boolean hasNext() throws JspTagException {
      return this.items.hasNext();
   }

   protected Object next() throws JspTagException {
      return this.items.next();
   }

   protected void prepare() throws JspTagException {
      if (this.rawItems != null) {
         if (this.rawItems instanceof ValueExpression) {
            this.deferredExpression = (ValueExpression)this.rawItems;
            this.rawItems = this.deferredExpression.getValue(this.pageContext.getELContext());
            if (this.rawItems == null) {
               this.rawItems = new ArrayList();
            }
         }

         this.items = this.supportedTypeForEachIterator(this.rawItems);
      } else {
         this.items = this.beginEndForEachIterator();
      }

   }

   public void release() {
      super.release();
      this.items = null;
      this.rawItems = null;
      this.deferredExpression = null;
   }

   protected ForEachIterator supportedTypeForEachIterator(Object o) throws JspTagException {
      ForEachIterator items;
      if (o instanceof Object[]) {
         items = this.toForEachIterator((Object[])((Object[])o));
      } else if (o instanceof boolean[]) {
         items = this.toForEachIterator((boolean[])((boolean[])o));
      } else if (o instanceof byte[]) {
         items = this.toForEachIterator((byte[])((byte[])o));
      } else if (o instanceof char[]) {
         items = this.toForEachIterator((char[])((char[])o));
      } else if (o instanceof short[]) {
         items = this.toForEachIterator((short[])((short[])o));
      } else if (o instanceof int[]) {
         items = this.toForEachIterator((int[])((int[])o));
      } else if (o instanceof long[]) {
         items = this.toForEachIterator((long[])((long[])o));
      } else if (o instanceof float[]) {
         items = this.toForEachIterator((float[])((float[])o));
      } else if (o instanceof double[]) {
         items = this.toForEachIterator((double[])((double[])o));
      } else if (o instanceof Collection) {
         items = this.toForEachIterator((Collection)o);
      } else if (o instanceof Iterator) {
         items = this.toForEachIterator((Iterator)o);
      } else if (o instanceof Enumeration) {
         items = this.toForEachIterator((Enumeration)o);
      } else if (o instanceof Map) {
         items = this.toForEachIterator((Map)o);
      } else if (o instanceof String) {
         items = this.toForEachIterator((String)o);
      } else {
         items = this.toForEachIterator(o);
      }

      return items;
   }

   private ForEachIterator beginEndForEachIterator() {
      Integer[] ia = new Integer[this.end + 1];

      for(int i = 0; i <= this.end; ++i) {
         ia[i] = i;
      }

      return new SimpleForEachIterator(Arrays.asList(ia).iterator());
   }

   protected ForEachIterator toForEachIterator(Object o) throws JspTagException {
      throw new JspTagException(Resources.getMessage("FOREACH_BAD_ITEMS"));
   }

   protected ForEachIterator toForEachIterator(Object[] a) {
      return new SimpleForEachIterator(Arrays.asList(a).iterator());
   }

   protected ForEachIterator toForEachIterator(boolean[] a) {
      Boolean[] wrapped = new Boolean[a.length];

      for(int i = 0; i < a.length; ++i) {
         wrapped[i] = a[i];
      }

      return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
   }

   protected ForEachIterator toForEachIterator(byte[] a) {
      Byte[] wrapped = new Byte[a.length];

      for(int i = 0; i < a.length; ++i) {
         wrapped[i] = a[i];
      }

      return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
   }

   protected ForEachIterator toForEachIterator(char[] a) {
      Character[] wrapped = new Character[a.length];

      for(int i = 0; i < a.length; ++i) {
         wrapped[i] = a[i];
      }

      return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
   }

   protected ForEachIterator toForEachIterator(short[] a) {
      Short[] wrapped = new Short[a.length];

      for(int i = 0; i < a.length; ++i) {
         wrapped[i] = a[i];
      }

      return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
   }

   protected ForEachIterator toForEachIterator(int[] a) {
      Integer[] wrapped = new Integer[a.length];

      for(int i = 0; i < a.length; ++i) {
         wrapped[i] = a[i];
      }

      return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
   }

   protected ForEachIterator toForEachIterator(long[] a) {
      Long[] wrapped = new Long[a.length];

      for(int i = 0; i < a.length; ++i) {
         wrapped[i] = a[i];
      }

      return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
   }

   protected ForEachIterator toForEachIterator(float[] a) {
      Float[] wrapped = new Float[a.length];

      for(int i = 0; i < a.length; ++i) {
         wrapped[i] = new Float(a[i]);
      }

      return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
   }

   protected ForEachIterator toForEachIterator(double[] a) {
      Double[] wrapped = new Double[a.length];

      for(int i = 0; i < a.length; ++i) {
         wrapped[i] = new Double(a[i]);
      }

      return new SimpleForEachIterator(Arrays.asList(wrapped).iterator());
   }

   protected ForEachIterator toForEachIterator(Collection c) {
      return new SimpleForEachIterator(c.iterator());
   }

   protected ForEachIterator toForEachIterator(Iterator i) {
      return new SimpleForEachIterator(i);
   }

   protected ForEachIterator toForEachIterator(Enumeration e) {
      class EnumerationAdapter implements ForEachIterator {
         private Enumeration e;

         public EnumerationAdapter(Enumeration e) {
            this.e = e;
         }

         public boolean hasNext() {
            return this.e.hasMoreElements();
         }

         public Object next() {
            return this.e.nextElement();
         }
      }

      return new EnumerationAdapter(e);
   }

   protected ForEachIterator toForEachIterator(Map m) {
      return new SimpleForEachIterator(m.entrySet().iterator());
   }

   protected ForEachIterator toForEachIterator(String s) {
      StringTokenizer st = new StringTokenizer(s, ",");
      return this.toForEachIterator((Enumeration)st);
   }

   protected class SimpleForEachIterator implements ForEachIterator {
      private Iterator i;

      public SimpleForEachIterator(Iterator i) {
         this.i = i;
      }

      public boolean hasNext() {
         return this.i.hasNext();
      }

      public Object next() {
         return this.i.next();
      }
   }

   protected interface ForEachIterator {
      boolean hasNext() throws JspTagException;

      Object next() throws JspTagException;
   }
}
