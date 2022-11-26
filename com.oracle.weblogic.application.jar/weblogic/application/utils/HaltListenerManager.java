package weblogic.application.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class HaltListenerManager {
   private static final List listeners = new ArrayList(1);

   public static void registerListener(HaltListener l) {
      listeners.add(l);
   }

   public static void invokeListeners() {
      Iterator var0 = listeners.iterator();

      while(var0.hasNext()) {
         HaltListener listener = (HaltListener)var0.next();
         listener.halt();
      }

   }
}
