package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractWriter;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.asyncqueue.AsyncQueue;
import org.glassfish.grizzly.asyncqueue.AsyncQueueWriter;
import org.glassfish.grizzly.asyncqueue.AsyncWriteQueueRecord;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.RecordWriteResult;
import org.glassfish.grizzly.asyncqueue.TaskQueue;
import org.glassfish.grizzly.asyncqueue.WritableMessage;

public abstract class AbstractNIOAsyncQueueWriter extends AbstractWriter implements AsyncQueueWriter {
   private static final Logger LOGGER = Grizzly.logger(AbstractNIOAsyncQueueWriter.class);
   protected final NIOTransport transport;
   protected volatile int maxPendingBytes = -2;
   protected volatile int maxWriteReentrants = 10;
   private volatile boolean isAllowDirectWrite = true;

   public AbstractNIOAsyncQueueWriter(NIOTransport transport) {
      this.transport = transport;
   }

   /** @deprecated */
   @Deprecated
   public boolean canWrite(Connection connection, int size) {
      return this.canWrite(connection);
   }

   public boolean canWrite(Connection connection) {
      NIOConnection nioConnection = (NIOConnection)connection;
      int connectionMaxPendingBytes = nioConnection.getMaxAsyncWriteQueueSize();
      if (connectionMaxPendingBytes < 0) {
         return true;
      } else {
         TaskQueue connectionQueue = nioConnection.getAsyncWriteQueue();
         int size = connectionQueue.spaceInBytes();
         return size == 0 || size < connectionMaxPendingBytes;
      }
   }

   /** @deprecated */
   @Deprecated
   public void notifyWritePossible(Connection connection, WriteHandler writeHandler, int size) {
      this.notifyWritePossible(connection, writeHandler);
   }

   public void notifyWritePossible(Connection connection, WriteHandler writeHandler) {
      ((NIOConnection)connection).getAsyncWriteQueue().notifyWritePossible(writeHandler);
   }

   public void setMaxPendingBytesPerConnection(int maxPendingBytes) {
      this.maxPendingBytes = maxPendingBytes < -2 ? -2 : maxPendingBytes;
   }

   public int getMaxPendingBytesPerConnection() {
      return this.maxPendingBytes;
   }

   public boolean isAllowDirectWrite() {
      return this.isAllowDirectWrite;
   }

   public void setAllowDirectWrite(boolean isAllowDirectWrite) {
      this.isAllowDirectWrite = isAllowDirectWrite;
   }

   public void write(Connection connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler completionHandler, MessageCloner cloner) {
      this.write(connection, (SocketAddress)dstAddress, message, completionHandler, (PushBackHandler)null, cloner);
   }

   /** @deprecated */
   @Deprecated
   public void write(Connection connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
      this.write(connection, (SocketAddress)dstAddress, message, completionHandler, pushBackHandler, (MessageCloner)null);
   }

   /** @deprecated */
   @Deprecated
   public void write(Connection connection, SocketAddress dstAddress, WritableMessage message, CompletionHandler completionHandler, PushBackHandler pushBackHandler, MessageCloner cloner) {
      NIOConnection nioConnection = (NIOConnection)connection;
      AsyncWriteQueueRecord queueRecord = this.createRecord(nioConnection, message, completionHandler, dstAddress, pushBackHandler, !message.hasRemaining() || message.isExternal());
      if (nioConnection == null) {
         queueRecord.notifyFailure(new IOException("Connection is null"));
      } else if (!nioConnection.isOpen()) {
         onWriteFailure(nioConnection, queueRecord, nioConnection.getCloseReason().getCause());
      } else {
         TaskQueue writeTaskQueue = nioConnection.getAsyncWriteQueue();
         int bytesToReserve = (int)queueRecord.getBytesToReserve();
         int pendingBytes = writeTaskQueue.reserveSpace(bytesToReserve);
         boolean isCurrent = pendingBytes == bytesToReserve;
         boolean isLogFine = LOGGER.isLoggable(Level.FINEST);
         if (isLogFine) {
            doFineLog("AsyncQueueWriter.write connection={0}, record={1}, directWrite={2}, size={3}, isUncountable={4}, bytesToReserve={5}, pendingBytes={6}", nioConnection, queueRecord, isCurrent, queueRecord.remaining(), queueRecord.isUncountable(), bytesToReserve, pendingBytes);
         }

         Writer.Reentrant reentrants = Writer.Reentrant.getWriteReentrant();

         try {
            if (!reentrants.inc()) {
               queueRecord.setMessage(cloneRecordIfNeeded(nioConnection, cloner, message));
               if (isCurrent) {
                  writeTaskQueue.setCurrentElement(queueRecord);
                  nioConnection.simulateIOEvent(IOEvent.WRITE);
               } else {
                  writeTaskQueue.offer(queueRecord);
               }

               return;
            }

            if (isCurrent && this.isAllowDirectWrite) {
               RecordWriteResult writeResult = this.write0(nioConnection, queueRecord);
               int bytesToRelease = (int)writeResult.bytesToReleaseAfterLastWrite();
               boolean isFinished = queueRecord.isFinished();
               int pendingBytesAfterRelease = writeTaskQueue.releaseSpaceAndNotify(bytesToRelease);
               boolean isQueueEmpty = pendingBytesAfterRelease == 0;
               if (isLogFine) {
                  doFineLog("AsyncQueueWriter.write directWrite connection={0}, record={1}, isFinished={2}, remaining={3}, isUncountable={4}, bytesToRelease={5}, pendingBytesAfterRelease={6}", nioConnection, queueRecord, isFinished, queueRecord.remaining(), queueRecord.isUncountable(), bytesToRelease, pendingBytesAfterRelease);
               }

               if (isFinished) {
                  queueRecord.notifyCompleteAndRecycle();
                  if (!isQueueEmpty) {
                     nioConnection.simulateIOEvent(IOEvent.WRITE);
                  }

                  return;
               }
            }

            queueRecord.setMessage(cloneRecordIfNeeded(nioConnection, cloner, message));
            if (isLogFine) {
               doFineLog("AsyncQueueWriter.write queuing connection={0}, record={1}, size={2}, isUncountable={3}", nioConnection, queueRecord, queueRecord.remaining(), queueRecord.isUncountable());
            }

            if (isCurrent) {
               writeTaskQueue.setCurrentElement(queueRecord);
               this.onReadyToWrite(nioConnection);
            } else {
               writeTaskQueue.offer(queueRecord);
            }
         } catch (IOException var23) {
            if (isLogFine) {
               LOGGER.log(Level.FINEST, "AsyncQueueWriter.write exception. connection=" + nioConnection + " record=" + queueRecord, var23);
            }

            onWriteFailure(nioConnection, queueRecord, var23);
         } finally {
            reentrants.dec();
         }

      }
   }

   public AsyncQueue.AsyncResult processAsync(Context context) {
      boolean isLogFine = LOGGER.isLoggable(Level.FINEST);
      NIOConnection nioConnection = (NIOConnection)context.getConnection();
      if (!nioConnection.isOpen()) {
         return AsyncQueue.AsyncResult.COMPLETE;
      } else {
         TaskQueue writeTaskQueue = nioConnection.getAsyncWriteQueue();
         int bytesReleased = 0;
         boolean done = true;
         AsyncWriteQueueRecord queueRecord = null;

         try {
            while((queueRecord = this.aggregate(writeTaskQueue)) != null) {
               if (isLogFine) {
                  doFineLog("AsyncQueueWriter.processAsync beforeWrite connection={0} record={1}", nioConnection, queueRecord);
               }

               RecordWriteResult writeResult = this.write0(nioConnection, queueRecord);
               int bytesToRelease = (int)writeResult.bytesToReleaseAfterLastWrite();
               done = queueRecord.isFinished();
               bytesReleased += bytesToRelease;
               if (isLogFine) {
                  doFineLog("AsyncQueueWriter.processAsync written connection={0}, written={1}, done={2}, bytesToRelease={3}, bytesReleased={4}", nioConnection, writeResult.lastWrittenBytes(), done, bytesToRelease, bytesReleased);
               }

               if (!done) {
                  queueRecord.notifyIncomplete();
                  writeTaskQueue.setCurrentElement(queueRecord);
                  if (isLogFine) {
                     doFineLog("AsyncQueueWriter.processAsync onReadyToWrite connection={0} peekRecord={1}", nioConnection, queueRecord);
                  }
                  break;
               }

               finishQueueRecord(nioConnection, queueRecord);
            }

            boolean isComplete = false;
            if (bytesReleased > 0) {
               if (done && !context.isManualIOEventControl() && writeTaskQueue.spaceInBytes() - bytesReleased <= 0) {
                  if (isLogFine) {
                     doFineLog("AsyncQueueWriter.processAsync setManualIOEventControl connection={0}", nioConnection);
                  }

                  context.setManualIOEventControl();
               }

               isComplete = writeTaskQueue.releaseSpace(bytesReleased) == 0;
            }

            if (isLogFine) {
               doFineLog("AsyncQueueWriter.processAsync exit connection={0}, done={1}, isComplete={2}, bytesReleased={3}, queueSize={4}", nioConnection, done, isComplete, bytesReleased, writeTaskQueue.size());
            }

            AsyncQueue.AsyncResult result = !done ? AsyncQueue.AsyncResult.INCOMPLETE : (!isComplete ? AsyncQueue.AsyncResult.EXPECTING_MORE : AsyncQueue.AsyncResult.COMPLETE);
            if (bytesReleased > 0) {
               context.complete(result.toProcessorResult());
               writeTaskQueue.doNotify();
               return AsyncQueue.AsyncResult.TERMINATE;
            } else {
               return result;
            }
         } catch (IOException var10) {
            if (isLogFine) {
               LOGGER.log(Level.FINEST, "AsyncQueueWriter.processAsync exception connection=" + nioConnection + " peekRecord=" + queueRecord, var10);
            }

            onWriteFailure(nioConnection, queueRecord, var10);
            return AsyncQueue.AsyncResult.COMPLETE;
         }
      }
   }

   private static void finishQueueRecord(NIOConnection nioConnection, AsyncWriteQueueRecord queueRecord) {
      boolean isLogFine = LOGGER.isLoggable(Level.FINEST);
      if (isLogFine) {
         doFineLog("AsyncQueueWriter.processAsync finished connection={0} record={1}", nioConnection, queueRecord);
      }

      if (queueRecord != null) {
         queueRecord.notifyCompleteAndRecycle();
      }

      if (isLogFine) {
         doFineLog("AsyncQueueWriter.processAsync finishQueueRecord connection={0} queueRecord={1}", nioConnection, queueRecord);
      }

   }

   private static WritableMessage cloneRecordIfNeeded(Connection connection, MessageCloner cloner, WritableMessage message) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "AsyncQueueWriter.write clone. connection={0} cloner={1} size={2}", new Object[]{connection, cloner, message.remaining()});
      }

      return cloner == null ? message : (WritableMessage)cloner.clone(connection, message);
   }

   protected AsyncWriteQueueRecord createRecord(Connection connection, WritableMessage message, CompletionHandler completionHandler, SocketAddress dstAddress, PushBackHandler pushBackHandler, boolean isUncountable) {
      return AsyncWriteQueueRecord.create(connection, message, completionHandler, dstAddress, pushBackHandler, isUncountable);
   }

   public final boolean isReady(Connection connection) {
      TaskQueue connectionQueue = ((NIOConnection)connection).getAsyncWriteQueue();
      return connectionQueue != null && !connectionQueue.isEmpty();
   }

   private static void doFineLog(String msg, Object... params) {
      LOGGER.log(Level.FINEST, msg, params);
   }

   public void onClose(Connection connection) {
      NIOConnection nioConnection = (NIOConnection)connection;
      TaskQueue writeQueue = nioConnection.getAsyncWriteQueue();
      writeQueue.onClose(nioConnection.getCloseReason().getCause());
   }

   public final void close() {
   }

   protected static void onWriteFailure(Connection connection, AsyncWriteQueueRecord failedRecord, Throwable e) {
      failedRecord.notifyFailure(e);
      connection.closeSilently();
   }

   protected abstract RecordWriteResult write0(NIOConnection var1, AsyncWriteQueueRecord var2) throws IOException;

   protected abstract void onReadyToWrite(NIOConnection var1) throws IOException;

   protected AsyncWriteQueueRecord aggregate(TaskQueue connectionQueue) {
      return (AsyncWriteQueueRecord)connectionQueue.poll();
   }
}
