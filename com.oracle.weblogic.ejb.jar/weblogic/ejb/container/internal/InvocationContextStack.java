package weblogic.ejb.container.internal;

import weblogic.kernel.ThreadLocalStack;

public final class InvocationContextStack {
   private static final ThreadLocalStack THREAD_LOCAL_STACK = new ThreadLocalStack();

   private InvocationContextStack() {
   }

   public static InvocationContext get() {
      return (InvocationContext)THREAD_LOCAL_STACK.peek();
   }

   public static void push(InvocationContext ic) {
      THREAD_LOCAL_STACK.push(ic);
   }

   public static InvocationContext pop() {
      return (InvocationContext)THREAD_LOCAL_STACK.pop();
   }
}
