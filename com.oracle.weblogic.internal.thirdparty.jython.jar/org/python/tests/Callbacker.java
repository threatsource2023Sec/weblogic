package org.python.tests;

import java.util.List;
import org.python.util.Generic;

public class Callbacker {
   public static void callNoArg(Callback c) {
      c.call();
   }

   public static void callOneArg(Callback c, long arg) {
      c.call(arg);
   }

   public static class CollectingCallback implements Callback {
      public List calls = Generic.list();

      public void call() {
         this.calls.add("call()");
      }

      public void call(long oneArg) {
         this.calls.add("call(" + oneArg + ")");
      }
   }

   public interface Callback {
      void call();

      void call(long var1);
   }
}
