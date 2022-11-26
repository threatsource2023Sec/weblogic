package com.sun.faces.facelets.tag.jstl.core;

import java.io.Serializable;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ValueExpression;

public final class MappedValueExpression extends ValueExpression {
   private static final long serialVersionUID = 1L;
   private final Object key;
   private final ValueExpression orig;

   public MappedValueExpression(ValueExpression orig, Map.Entry entry) {
      this.orig = orig;
      this.key = entry.getKey();
   }

   public Object getValue(ELContext context) {
      Object base = this.orig.getValue(context);
      if (base != null) {
         context.setPropertyResolved(true);
         return new Entry((Map)base, this.key);
      } else {
         return null;
      }
   }

   public void setValue(ELContext context, Object value) {
      Object base = this.orig.getValue(context);
      if (base != null) {
         context.setPropertyResolved(false);
         context.getELResolver().setValue(context, base, this.key, value);
      }

   }

   public boolean isReadOnly(ELContext context) {
      Object base = this.orig.getValue(context);
      if (base != null) {
         context.setPropertyResolved(false);
         return context.getELResolver().isReadOnly(context, base, this.key);
      } else {
         return true;
      }
   }

   public Class getType(ELContext context) {
      Object base = this.orig.getValue(context);
      if (base != null) {
         context.setPropertyResolved(false);
         return context.getELResolver().getType(context, base, this.key);
      } else {
         return null;
      }
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
      return 0;
   }

   public boolean isLiteralText() {
      return false;
   }

   private static final class Entry implements Map.Entry, Serializable {
      private static final long serialVersionUID = 4361498560718735987L;
      private final Map src;
      private final Object key;

      public Entry(Map src, Object key) {
         this.src = src;
         this.key = key;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.src.get(this.key);
      }

      public Object setValue(Object value) {
         return this.src.put(this.key, value);
      }
   }
}
