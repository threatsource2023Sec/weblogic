package javolution.realtime;

import javolution.JavolutionError;
import javolution.lang.Reflection;

public class HeapContext extends Context {
   private static final Class CLASS = Reflection.getClass("javolution.realtime.HeapContext");

   public static HeapContext current() {
      Context var0 = Context.current();
      if (var0.inheritedPoolContext != null) {
         return null;
      } else {
         while(var0 != null) {
            if (var0 instanceof HeapContext) {
               return (HeapContext)var0;
            }

            var0 = var0.getOuter();
         }

         throw new JavolutionError("No heap context or pool context");
      }
   }

   public static void enter() {
      Context.enter(CLASS);
   }

   public static void exit() {
      Context.exit(CLASS);
   }

   protected void enterAction() {
      this.inheritedPoolContext = null;
      PoolContext var1 = this.getOuter().inheritedPoolContext;
      if (var1 != null) {
         var1.setInUsePoolsLocal(false);
      }

   }

   protected void exitAction() {
      PoolContext var1 = this.getOuter().inheritedPoolContext;
      if (var1 != null) {
         var1.setInUsePoolsLocal(true);
      }

   }
}
