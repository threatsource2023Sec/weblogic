package weblogic.jndi;

import java.util.Hashtable;
import weblogic.kernel.ThreadLocalStack;

public class ThreadLocalMap {
   private static final ThreadLocalStack threadEnvironment = new ThreadLocalStack(true);

   public static void push(Hashtable env) {
      threadEnvironment.push(env);
   }

   public static Hashtable pop() {
      return (Hashtable)threadEnvironment.popAndPeek();
   }

   public static Hashtable get() {
      return (Hashtable)threadEnvironment.get();
   }
}
