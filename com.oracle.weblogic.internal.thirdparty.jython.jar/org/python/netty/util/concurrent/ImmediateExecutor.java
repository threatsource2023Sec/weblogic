package org.python.netty.util.concurrent;

import java.util.concurrent.Executor;

public final class ImmediateExecutor implements Executor {
   public static final ImmediateExecutor INSTANCE = new ImmediateExecutor();

   private ImmediateExecutor() {
   }

   public void execute(Runnable command) {
      if (command == null) {
         throw new NullPointerException("command");
      } else {
         command.run();
      }
   }
}
