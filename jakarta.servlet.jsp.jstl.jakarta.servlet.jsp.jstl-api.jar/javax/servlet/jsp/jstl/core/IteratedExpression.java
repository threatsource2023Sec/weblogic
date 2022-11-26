package javax.servlet.jsp.jstl.core;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;

public final class IteratedExpression {
   private static final long serialVersionUID = 1L;
   protected final ValueExpression orig;
   protected final String delims;
   private Object base;
   private int index;
   private Iterator iter;

   public IteratedExpression(ValueExpression orig, String delims) {
      this.orig = orig;
      this.delims = delims;
   }

   public Object getItem(ELContext context, int i) {
      if (this.base == null) {
         this.base = this.orig.getValue(context);
         if (this.base == null) {
            return null;
         }

         this.iter = this.toIterator(this.base);
         this.index = 0;
      }

      if (this.index > i) {
         this.iter = this.toIterator(this.base);
         this.index = 0;
      }

      Object item;
      do {
         if (!this.iter.hasNext()) {
            return null;
         }

         item = this.iter.next();
      } while(this.index++ != i);

      return item;
   }

   public ValueExpression getValueExpression() {
      return this.orig;
   }

   private Iterator toIterator(Object obj) {
      Iterator iter;
      if (obj instanceof String) {
         iter = this.toIterator((Enumeration)(new StringTokenizer((String)obj, this.delims)));
      } else if (obj instanceof Iterator) {
         iter = (Iterator)obj;
      } else if (obj instanceof Collection) {
         iter = this.toIterator((Object)((Collection)obj).iterator());
      } else if (obj instanceof Enumeration) {
         iter = this.toIterator((Enumeration)obj);
      } else {
         if (!(obj instanceof Map)) {
            throw new ELException("Don't know how to iterate over supplied items in forEach");
         }

         iter = ((Map)obj).entrySet().iterator();
      }

      return iter;
   }

   private Iterator toIterator(final Enumeration obj) {
      return new Iterator() {
         public boolean hasNext() {
            return obj.hasMoreElements();
         }

         public Object next() {
            return obj.nextElement();
         }

         public void remove() {
         }
      };
   }
}
