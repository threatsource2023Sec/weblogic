package weblogic.ejb.container.internal;

import javax.ejb.EJBContext;
import weblogic.kernel.ThreadLocalStack;

public final class EJBContextManager {
   private static final ThreadLocalStack THREAD_STORAGE = new ThreadLocalStack(true);

   public static void pushEjbContext(EJBContext ejbContext) {
      THREAD_STORAGE.push(ejbContext);
   }

   public static void popEjbContext() {
      THREAD_STORAGE.pop();
   }

   public static EJBContext getEJBContext() {
      return (EJBContext)THREAD_STORAGE.peek();
   }
}
