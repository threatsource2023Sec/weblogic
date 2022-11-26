package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class Trim extends Val {
   private final Val _val;
   private final Val _trimChar;
   private final Boolean _where;

   public Trim(Val val, Val trimChar, Boolean where) {
      this._val = val;
      this._trimChar = trimChar;
      this._where = where;
   }

   public Class getType() {
      return String.class;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object eval = this._val.eval(candidate, orig, ctx, params);
      if (eval == null) {
         return null;
      } else {
         String toTrim = this._trimChar.eval(candidate, orig, ctx, params).toString();
         String str = eval.toString();
         if (this._where == null || Boolean.TRUE.equals(this._where)) {
            while(str.startsWith(toTrim)) {
               str = str.substring(toTrim.length());
            }
         }

         if (this._where == null || Boolean.FALSE.equals(this._where)) {
            while(str.endsWith(toTrim)) {
               str = str.substring(0, str.length() - toTrim.length());
            }
         }

         return str;
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      this._trimChar.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}
