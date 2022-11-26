package com.sun.faces.facelets.tag.jstl.core;

import javax.el.ELContext;
import javax.el.ValueExpression;

public final class IndexedValueExpression extends ValueExpression {
   private static final long serialVersionUID = 1L;
   private final Integer i;
   private final ValueExpression orig;

   public IndexedValueExpression(ValueExpression orig, int i) {
      this.i = i;
      this.orig = orig;
   }

   public Object getValue(ELContext context) {
      Object base = this.orig.getValue(context);
      if (base != null) {
         context.setPropertyResolved(false);
         return context.getELResolver().getValue(context, base, this.i);
      } else {
         return null;
      }
   }

   public void setValue(ELContext context, Object value) {
      Object base = this.orig.getValue(context);
      if (base != null) {
         context.setPropertyResolved(false);
         context.getELResolver().setValue(context, base, this.i, value);
      }

   }

   public boolean isReadOnly(ELContext context) {
      Object base = this.orig.getValue(context);
      if (base != null) {
         context.setPropertyResolved(false);
         return context.getELResolver().isReadOnly(context, base, this.i);
      } else {
         return true;
      }
   }

   public Class getType(ELContext context) {
      Object base = this.orig.getValue(context);
      if (base != null) {
         context.setPropertyResolved(false);
         return context.getELResolver().getType(context, base, this.i);
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
      return this.orig.hashCode();
   }

   public boolean isLiteralText() {
      return false;
   }
}
