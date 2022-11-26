package org.glassfish.grizzly.http.server;

import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.CompletionHandler;

public interface SuspendContext {
   CompletionHandler getCompletionHandler();

   TimeoutHandler getTimeoutHandler();

   long getTimeout(TimeUnit var1);

   void setTimeout(long var1, TimeUnit var3);

   boolean isSuspended();
}
