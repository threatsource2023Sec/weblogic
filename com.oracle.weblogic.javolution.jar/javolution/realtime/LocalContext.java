package javolution.realtime;

import javolution.lang.Reflection;
import javolution.util.FastMap;

public class LocalContext extends Context {
   private static final Class CLASS = Reflection.getClass("javolution.realtime.LocalContext");
   final FastMap _references = new FastMap();

   public static LocalContext current() {
      return Context.localContext(Thread.currentThread());
   }

   public static void enter() {
      Context.enter(CLASS);
   }

   public static void exit() {
      Context.exit(CLASS);
   }

   public void clear() {
      super.clear();
      this._references.clear();
   }

   protected void enterAction() {
      this.inheritedLocalContext = this;
   }

   protected void exitAction() {
      this._references.clear();
   }
}
