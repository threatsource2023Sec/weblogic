package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.IterationStatus;
import javax.el.ELContext;
import javax.el.ValueExpression;

public final class IterationStatusExpression extends ValueExpression {
   private static final long serialVersionUID = 1L;
   private final IterationStatus status;

   public IterationStatusExpression(IterationStatus status) {
      this.status = status;
   }

   public Object getValue(ELContext context) {
      return this.status;
   }

   public void setValue(ELContext context, Object value) {
      throw new UnsupportedOperationException("Cannot set IterationStatus");
   }

   public boolean isReadOnly(ELContext context) {
      return true;
   }

   public Class getType(ELContext context) {
      return IterationStatus.class;
   }

   public Class getExpectedType() {
      return IterationStatus.class;
   }

   public String getExpressionString() {
      return this.toString();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         IterationStatusExpression other = (IterationStatusExpression)obj;
         return this.status == other.status || this.status != null && this.status.equals(other.status);
      }
   }

   public int hashCode() {
      return this.status.hashCode();
   }

   public boolean isLiteralText() {
      return true;
   }

   public String toString() {
      return this.status.toString();
   }
}
