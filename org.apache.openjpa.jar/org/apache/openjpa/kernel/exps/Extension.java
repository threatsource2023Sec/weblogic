package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;

class Extension extends Val {
   private final FilterListener _listener;
   private final Val _target;
   private final Val _arg;

   public Extension(FilterListener listener, Val target, Val arg) {
      this._listener = listener;
      this._target = target;
      this._arg = arg;
   }

   public Class getType() {
      Class targetClass = this._target == null ? null : this._target.getType();
      return this._listener.getType(targetClass, this.getArgTypes());
   }

   public void setImplicitType(Class type) {
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      Object target = null;
      Class targetClass = null;
      if (this._target != null) {
         target = this._target.eval(candidate, orig, ctx, params);
         targetClass = this._target.getType();
      }

      Object arg = null;
      if (this._arg != null) {
         arg = this._arg.eval(candidate, orig, ctx, params);
      }

      return this._listener.evaluate(target, targetClass, this.getArgs(arg), this.getArgTypes(), candidate, ctx);
   }

   private Class[] getArgTypes() {
      if (this._arg == null) {
         return null;
      } else {
         return this._arg instanceof Args ? ((Args)this._arg).getTypes() : new Class[]{this._arg.getType()};
      }
   }

   private Object[] getArgs(Object arg) {
      if (arg == null) {
         return null;
      } else {
         return this._arg instanceof Args ? (Object[])((Object[])arg) : new Object[]{arg};
      }
   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Value)this);
      if (this._target != null) {
         this._target.acceptVisit(visitor);
      }

      if (this._arg != null) {
         this._arg.acceptVisit(visitor);
      }

      visitor.exit((Value)this);
   }
}
