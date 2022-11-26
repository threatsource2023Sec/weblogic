package weblogic.protocol;

import java.util.EmptyStackException;
import weblogic.kernel.ThreadLocalStack;

public final class ProtocolStack {
   private static final boolean DEBUG = false;
   private static final ThreadLocalStack threadEnvironment = new ThreadLocalStack(true);

   public static void push(Protocol protocol) {
      threadEnvironment.push(protocol);
   }

   public static void pop() {
      try {
         Protocol var0 = (Protocol)threadEnvironment.popAndPeek();
      } catch (EmptyStackException var1) {
      }

   }

   public static Protocol get() {
      Protocol protocol = (Protocol)threadEnvironment.get();
      return protocol;
   }

   private static void debug(String str) {
      System.out.println("[PROTOCOLSTACK] " + str);
   }
}
