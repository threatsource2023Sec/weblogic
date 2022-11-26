package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.ProcessorResult;

public interface AsyncQueue {
   String EXPECTING_MORE_OPTION = AsyncQueue.class.getName() + ".expectingMore";

   boolean isReady(Connection var1);

   AsyncResult processAsync(Context var1);

   void onClose(Connection var1);

   void close();

   public static enum AsyncResult {
      COMPLETE(ProcessorResult.createLeave()),
      INCOMPLETE(ProcessorResult.createComplete()),
      EXPECTING_MORE(ProcessorResult.createComplete(AsyncQueue.EXPECTING_MORE_OPTION)),
      TERMINATE(ProcessorResult.createTerminate());

      private final ProcessorResult result;

      private AsyncResult(ProcessorResult result) {
         this.result = result;
      }

      public ProcessorResult toProcessorResult() {
         return this.result;
      }
   }
}
