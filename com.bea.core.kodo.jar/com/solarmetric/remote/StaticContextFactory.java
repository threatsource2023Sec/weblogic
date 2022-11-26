package com.solarmetric.remote;

public class StaticContextFactory implements ContextFactory {
   private final Object _context;

   public StaticContextFactory(Object context) {
      this._context = context;
   }

   public Object getContext(Command cmd) {
      return this._context;
   }

   public String toString() {
      return this._context == null ? super.toString() : this._context.toString();
   }
}
