package org.apache.openjpa.kernel.exps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;

public abstract class Val implements Value {
   private ClassMetaData _meta = null;

   public final Object evaluate(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      try {
         return this.eval(candidate, candidate, ctx, params);
      } catch (NullPointerException var6) {
         return null;
      } catch (ClassCastException var7) {
         return null;
      }
   }

   public final Object evaluate(Collection candidates, Object orig, StoreContext ctx, Object[] params) {
      try {
         Collection c = this.eval(candidates, orig, ctx, params);
         return c.isEmpty() ? null : c.iterator().next();
      } catch (NullPointerException var6) {
         return null;
      } catch (ClassCastException var7) {
         return null;
      }
   }

   protected abstract Object eval(Object var1, Object var2, StoreContext var3, Object[] var4);

   protected Collection eval(Collection candidates, Object orig, StoreContext ctx, Object[] params) {
      Collection ret = new ArrayList(candidates.size());
      Iterator itr = candidates.iterator();

      while(itr.hasNext()) {
         Object candidate = itr.next();
         ret.add(this.evaluate(candidate, orig == null ? candidate : orig, ctx, params));
      }

      return ret;
   }

   public ClassMetaData getMetaData() {
      return this._meta;
   }

   public void setMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public boolean isVariable() {
      return false;
   }

   public boolean isAggregate() {
      return false;
   }

   public boolean isXPath() {
      return false;
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      visitor.exit((Value)this);
   }
}
