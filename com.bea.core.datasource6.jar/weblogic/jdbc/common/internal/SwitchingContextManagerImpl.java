package weblogic.jdbc.common.internal;

import java.util.Deque;
import java.util.LinkedList;

public class SwitchingContextManagerImpl extends SwitchingContextManager {
   private static ThreadLocal currentSwitchingContext = new ThreadLocal();

   public SwitchingContext get() {
      Deque stack = (Deque)currentSwitchingContext.get();
      return stack == null ? null : (SwitchingContext)stack.peek();
   }

   public void push(SwitchingContext sc) {
      Deque stack = (Deque)currentSwitchingContext.get();
      if (stack == null) {
         stack = new LinkedList();
         currentSwitchingContext.set(stack);
      }

      ((Deque)stack).push(sc);
   }

   public SwitchingContext pop() {
      Deque stack = (Deque)currentSwitchingContext.get();
      if (stack == null) {
         return null;
      } else {
         SwitchingContext sc = (SwitchingContext)stack.pop();
         if (stack.isEmpty()) {
            currentSwitchingContext.set((Object)null);
         }

         return sc;
      }
   }
}
