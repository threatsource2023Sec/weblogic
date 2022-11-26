package org.mozilla.javascript.tools.debugger;

import org.mozilla.javascript.Context;

class ContextHelper {
   Context old;
   int enterCount;
   Context New;

   public void attach(Context var1) {
      this.old = Context.getCurrentContext();
      this.enterCount = 0;
      if (this.old != null) {
         Context.exit();

         while(Context.getCurrentContext() != null) {
            ++this.enterCount;
            Context.exit();
         }
      }

      Context.enter(var1);
      this.New = var1;
   }

   void reset() {
      Context.exit();
      if (this.old != null) {
         if (Context.enter(this.old) != this.old) {
            throw new RuntimeException("debugger error: failed to reset context");
         }

         while(this.enterCount > 0) {
            Context.enter();
            --this.enterCount;
         }
      }

   }
}
