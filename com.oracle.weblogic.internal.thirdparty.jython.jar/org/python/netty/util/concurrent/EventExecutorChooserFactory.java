package org.python.netty.util.concurrent;

public interface EventExecutorChooserFactory {
   EventExecutorChooser newChooser(EventExecutor[] var1);

   public interface EventExecutorChooser {
      EventExecutor next();
   }
}
