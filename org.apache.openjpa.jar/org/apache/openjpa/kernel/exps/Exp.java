package org.apache.openjpa.kernel.exps;

import java.util.Collection;
import org.apache.openjpa.kernel.StoreContext;

class Exp implements Expression {
   public final boolean evaluate(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      try {
         return this.eval(candidate, candidate, ctx, params);
      } catch (ClassCastException var6) {
         return false;
      } catch (NullPointerException var7) {
         return false;
      }
   }

   public final boolean evaluate(Collection candidates, StoreContext ctx, Object[] params) {
      try {
         return this.eval(candidates, ctx, params);
      } catch (ClassCastException var5) {
         return false;
      } catch (NullPointerException var6) {
         return false;
      }
   }

   protected boolean eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      return true;
   }

   protected boolean eval(Collection candidates, StoreContext ctx, Object[] params) {
      return true;
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      visitor.exit((Expression)this);
   }
}
