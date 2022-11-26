package org.apache.openjpa.kernel.exps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.StoreContext;

class Cast extends Val {
   private final Val _val;
   private final Class _cast;

   public Cast(Val val, Class cast) {
      this._val = val;
      this._cast = cast;
   }

   public Class getType() {
      return this._cast;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return Filters.convert(this._val.eval(candidate, orig, ctx, params), this._cast);
   }

   protected Collection eval(Collection candidates, Object orig, StoreContext ctx, Object[] params) {
      Collection res = this._val.eval(candidates, orig, ctx, params);
      if (res != null && !res.isEmpty()) {
         Collection casts = new ArrayList(res.size());
         Iterator itr = res.iterator();

         while(itr.hasNext()) {
            casts.add(Filters.convert(itr.next(), this._cast));
         }

         return casts;
      } else {
         return res;
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      this._val.acceptVisit(visitor);
      visitor.exit((Value)this);
   }
}
