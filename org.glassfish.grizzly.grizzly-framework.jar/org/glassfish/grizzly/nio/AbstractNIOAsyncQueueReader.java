package org.glassfish.grizzly.nio;

import java.io.EOFException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractReader;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.asyncqueue.AsyncQueue;
import org.glassfish.grizzly.asyncqueue.AsyncQueueReader;
import org.glassfish.grizzly.asyncqueue.AsyncReadQueueRecord;
import org.glassfish.grizzly.asyncqueue.TaskQueue;

public abstract class AbstractNIOAsyncQueueReader extends AbstractReader implements AsyncQueueReader {
   private static final Logger LOGGER = Grizzly.logger(AbstractNIOAsyncQueueReader.class);
   public static final int DEFAULT_BUFFER_SIZE = 8192;
   protected int defaultBufferSize = 8192;
   protected final NIOTransport transport;
   private EOFException cachedEOFException;

   public AbstractNIOAsyncQueueReader(NIOTransport transport) {
      this.transport = transport;
   }

   public void read(Connection connection, Buffer buffer, CompletionHandler completionHandler, Interceptor interceptor) {
      if (connection == null) {
         failure(new IOException("Connection is null"), completionHandler);
      } else if (!connection.isOpen()) {
         failure(new IOException("Connection is closed"), completionHandler);
      } else {
         TaskQueue connectionQueue = ((NIOConnection)connection).getAsyncReadQueue();
         AsyncReadQueueRecord queueRecord = AsyncReadQueueRecord.create(connection, buffer, completionHandler, interceptor);
         ReadResult currentResult = queueRecord.getCurrentResult();
         boolean isCurrent = connectionQueue.reserveSpace(1) == 1;

         try {
            if (isCurrent) {
               this.doRead(connection, queueRecord);
               int interceptInstructions = this.intercept(1, queueRecord, currentResult);
               if ((interceptInstructions & 1) != 0 || interceptor == null && queueRecord.isFinished()) {
                  boolean isQueueEmpty = connectionQueue.releaseSpaceAndNotify(1) == 0;
                  queueRecord.notifyComplete();
                  if (!isQueueEmpty) {
                     this.onReadyToRead(connection);
                  }

                  this.intercept(2, queueRecord, (ReadResult)null);
                  queueRecord.recycle();
               } else {
                  if ((interceptInstructions & 4) != 0) {
                     currentResult.setMessage((Object)null);
                     currentResult.setReadSize(0);
                     queueRecord.setMessage((Object)null);
                  }

                  connectionQueue.setCurrentElement(queueRecord);
                  queueRecord.notifyIncomplete();
                  this.onReadyToRead(connection);
                  this.intercept(3, queueRecord, (ReadResult)null);
               }
            } else {
               connectionQueue.offer(queueRecord);
               if (!connection.isOpen() && connectionQueue.remove(queueRecord)) {
                  this.onReadFailure(connection, queueRecord, new EOFException("Connection is closed"));
               }
            }
         } catch (IOException var11) {
            this.onReadFailure(connection, queueRecord, var11);
         }

      }
   }

   public final boolean isReady(Connection connection) {
      TaskQueue connectionQueue = ((NIOConnection)connection).getAsyncReadQueue();
      return connectionQueue != null && !connectionQueue.isEmpty();
   }

   public AsyncQueue.AsyncResult processAsync(Context context) {
      NIOConnection nioConnection = (NIOConnection)context.getConnection();
      if (!nioConnection.isOpen()) {
         return AsyncQueue.AsyncResult.COMPLETE;
      } else {
         TaskQueue connectionQueue = nioConnection.getAsyncReadQueue();
         boolean done = false;
         AsyncReadQueueRecord queueRecord = null;

         try {
            while((queueRecord = (AsyncReadQueueRecord)connectionQueue.poll()) != null) {
               ReadResult currentResult = queueRecord.getCurrentResult();
               this.doRead(nioConnection, queueRecord);
               Interceptor interceptor = queueRecord.getInterceptor();
               int interceptInstructions = this.intercept(1, queueRecord, currentResult);
               if ((interceptInstructions & 1) != 0 || interceptor == null && queueRecord.isFinished()) {
                  if (!context.isManualIOEventControl() && connectionQueue.spaceInBytes() - 1 <= 0) {
                     context.setManualIOEventControl();
                  }

                  done = connectionQueue.releaseSpaceAndNotify(1) == 0;
                  queueRecord.notifyComplete();
                  this.intercept(2, queueRecord, (ReadResult)null);
                  queueRecord.recycle();
                  if (!done) {
                     continue;
                  }
                  break;
               }

               if ((interceptInstructions & 4) != 0) {
                  currentResult.setMessage((Object)null);
                  currentResult.setReadSize(0);
                  queueRecord.setMessage((Object)null);
               }

               connectionQueue.setCurrentElement(queueRecord);
               queueRecord.notifyIncomplete();
               this.intercept(3, queueRecord, (ReadResult)null);
               return AsyncQueue.AsyncResult.INCOMPLETE;
            }

            if (!done) {
               return AsyncQueue.AsyncResult.EXPECTING_MORE;
            }
         } catch (IOException var9) {
            this.onReadFailure(nioConnection, queueRecord, var9);
         } catch (Exception var10) {
            String message = "Unexpected exception occurred in AsyncQueueReader";
            LOGGER.log(Level.SEVERE, message, var10);
            IOException ioe = new IOException(var10.getClass() + ": " + message);
            this.onReadFailure(nioConnection, queueRecord, ioe);
         }

         return AsyncQueue.AsyncResult.COMPLETE;
      }
   }

   public void onClose(Connection connection) {
      NIOConnection nioConnection = (NIOConnection)connection;
      TaskQueue readQueue = nioConnection.getAsyncReadQueue();
      if (!readQueue.isEmpty()) {
         EOFException error = this.cachedEOFException;
         if (error == null) {
            error = new EOFException("Connection closed");
            this.cachedEOFException = error;
         }

         AsyncReadQueueRecord record;
         while((record = (AsyncReadQueueRecord)readQueue.poll()) != null) {
            record.notifyFailure(error);
         }
      }

   }

   public final void close() {
   }

   protected final int doRead(Connection connection, AsyncReadQueueRecord queueRecord) throws IOException {
      Object message = queueRecord.getMessage();
      Buffer buffer = (Buffer)message;
      ReadResult currentResult = queueRecord.getCurrentResult();
      int readBytes = this.read0(connection, buffer, currentResult);
      if (readBytes == -1) {
         throw new EOFException();
      } else {
         return readBytes;
      }
   }

   protected final void onReadFailure(Connection connection, AsyncReadQueueRecord failedRecord, IOException e) {
      if (failedRecord != null) {
         failedRecord.notifyFailure(e);
      }

      connection.closeSilently();
   }

   private static void failure(Throwable failure, CompletionHandler completionHandler) {
      if (completionHandler != null) {
         completionHandler.failed(failure);
      }

   }

   private int intercept(int event, AsyncReadQueueRecord asyncQueueRecord, ReadResult currentResult) {
      Interceptor interceptor = asyncQueueRecord.getInterceptor();
      return interceptor != null ? interceptor.intercept(event, asyncQueueRecord, currentResult) : 0;
   }

   protected abstract int read0(Connection var1, Buffer var2, ReadResult var3) throws IOException;

   protected abstract void onReadyToRead(Connection var1) throws IOException;
}
