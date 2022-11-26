package org.apache.openjpa.kernel.exps;

import java.util.ArrayList;
import java.util.List;
import org.apache.openjpa.kernel.StoreContext;

class Args extends Val implements Arguments {
   private final List _args = new ArrayList(3);

   public Args(Value val1, Value val2) {
      if (val1 instanceof Args) {
         this._args.addAll(((Args)val1)._args);
      } else {
         this._args.add(val1);
      }

      if (val2 instanceof Args) {
         this._args.addAll(((Args)val2)._args);
      } else {
         this._args.add(val2);
      }

   }

   public Value[] getValues() {
      return (Value[])((Value[])this._args.toArray(new Value[this._args.size()]));
   }

   public Class getType() {
      return Object[].class;
   }

   public Class[] getTypes() {
      Class[] c = new Class[this._args.size()];

      for(int i = 0; i < this._args.size(); ++i) {
         c[i] = ((Val)this._args.get(i)).getType();
      }

      return c;
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object[] vals = new Object[this._args.size()];

      for(int i = 0; i < this._args.size(); ++i) {
         vals[i] = ((Val)this._args.get(i)).eval(candidate, orig, ctx, params);
      }

      return vals;
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);

      for(int i = 0; i < this._args.size(); ++i) {
         ((Val)this._args.get(i)).acceptVisit(visitor);
      }

      visitor.exit((Value)this);
   }
}
