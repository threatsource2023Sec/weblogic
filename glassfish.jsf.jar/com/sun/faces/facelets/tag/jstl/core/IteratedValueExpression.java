package com.sun.faces.facelets.tag.jstl.core;

import java.util.Collection;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;

public final class IteratedValueExpression extends ValueExpression {
   private static final long serialVersionUID = 1L;
   private ValueExpression orig;
   private int start;
   private int index;

   public IteratedValueExpression(ValueExpression orig, int start, int index) {
      this.orig = orig;
      this.start = start;
      this.index = index;
   }

   public Object getValue(ELContext context) {
      Collection collection = (Collection)this.orig.getValue(context);
      Iterator iterator = collection.iterator();
      Object result = null;
      int i = this.start;
      if (i != 0) {
         while(i != 0) {
            result = iterator.next();
            if (!iterator.hasNext()) {
               throw new ELException("Unable to position start");
            }

            --i;
         }
      } else {
         result = iterator.next();
      }

      while(i < this.index) {
         if (!iterator.hasNext()) {
            throw new ELException("Unable to get given value");
         }

         ++i;
         result = iterator.next();
      }

      return result;
   }

   public void setValue(ELContext context, Object value) {
      context.setPropertyResolved(false);
      throw new PropertyNotWritableException();
   }

   public boolean isReadOnly(ELContext context) {
      context.setPropertyResolved(false);
      return true;
   }

   public Class getType(ELContext context) {
      context.setPropertyResolved(false);
      return Object.class;
   }

   public Class getExpectedType() {
      return Object.class;
   }

   public String getExpressionString() {
      return this.orig.getExpressionString();
   }

   public boolean equals(Object obj) {
      return this.orig.equals(obj);
   }

   public int hashCode() {
      return this.orig.hashCode();
   }

   public boolean isLiteralText() {
      return false;
   }
}
