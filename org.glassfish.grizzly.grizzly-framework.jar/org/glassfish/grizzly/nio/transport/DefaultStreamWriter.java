package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.streams.AbstractStreamWriter;
import org.glassfish.grizzly.streams.BufferedOutput;

public final class DefaultStreamWriter extends AbstractStreamWriter {
   public DefaultStreamWriter(Connection connection) {
      super(connection, new Output(connection));
   }

   public GrizzlyFuture flush(CompletionHandler completionHandler) throws IOException {
      return super.flush(new ResetCounterCompletionHandler((Output)this.output, completionHandler));
   }

   private static final class ResetCounterCompletionHandler implements CompletionHandler {
      private final Output output;
      private final CompletionHandler parentCompletionHandler;

      public ResetCounterCompletionHandler(Output output, CompletionHandler parentCompletionHandler) {
         this.output = output;
         this.parentCompletionHandler = parentCompletionHandler;
      }

      public void cancelled() {
         if (this.parentCompletionHandler != null) {
            this.parentCompletionHandler.cancelled();
         }

      }

      public void failed(Throwable throwable) {
         if (this.parentCompletionHandler != null) {
            this.parentCompletionHandler.failed(throwable);
         }

      }

      public void completed(Integer result) {
         this.output.sentBytesCounter = 0;
         if (this.parentCompletionHandler != null) {
            this.parentCompletionHandler.completed(result);
         }

      }

      public void updated(Integer result) {
         if (this.parentCompletionHandler != null) {
            this.parentCompletionHandler.updated(result);
         }

      }
   }

   private static final class CompletionHandlerAdapter implements CompletionHandler {
      private final Output output;
      private final FutureImpl future;
      private final CompletionHandler completionHandler;

      public CompletionHandlerAdapter(Output output, FutureImpl future, CompletionHandler completionHandler) {
         this.output = output;
         this.future = future;
         this.completionHandler = completionHandler;
      }

      public void cancelled() {
         if (this.completionHandler != null) {
            this.completionHandler.cancelled();
         }

         if (this.future != null) {
            this.future.cancel(false);
         }

      }

      public void failed(Throwable throwable) {
         if (this.completionHandler != null) {
            this.completionHandler.failed(throwable);
         }

         if (this.future != null) {
            this.future.failure(throwable);
         }

      }

      public void completed(WriteResult result) {
         Output var2 = this.output;
         var2.sentBytesCounter = (int)((long)var2.sentBytesCounter + result.getWrittenSize());
         int totalSentBytes = this.output.sentBytesCounter;
         if (this.completionHandler != null) {
            this.completionHandler.completed(totalSentBytes);
         }

         if (this.future != null) {
            this.future.result(totalSentBytes);
         }

      }

      public void updated(WriteResult result) {
         if (this.completionHandler != null) {
            this.completionHandler.updated(this.output.sentBytesCounter + (int)result.getWrittenSize());
         }

      }
   }

   public static final class Output extends BufferedOutput {
      private final Connection connection;
      private int sentBytesCounter;

      public Output(Connection connection) {
         super(connection.getWriteBufferSize());
         this.connection = connection;
      }

      protected GrizzlyFuture flush0(Buffer buffer, CompletionHandler completionHandler) throws IOException {
         FutureImpl future = SafeFutureImpl.create();
         if (buffer == null) {
            buffer = Buffers.EMPTY_BUFFER;
         }

         this.connection.write(buffer, new CompletionHandlerAdapter(this, future, completionHandler));
         return future;
      }

      protected Buffer newBuffer(int size) {
         return this.connection.getMemoryManager().allocate(size);
      }

      protected Buffer reallocateBuffer(Buffer oldBuffer, int size) {
         return this.connection.getMemoryManager().reallocate(oldBuffer, size);
      }

      protected void onClosed() throws IOException {
         this.connection.closeSilently();
      }
   }
}
