package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.AsyncWriteQueueRecord;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.RecordWriteResult;
import org.glassfish.grizzly.asyncqueue.TaskQueue;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.BufferArray;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.nio.AbstractNIOAsyncQueueWriter;
import org.glassfish.grizzly.nio.DirectByteBufferRecord;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;

public final class TCPNIOAsyncQueueWriter extends AbstractNIOAsyncQueueWriter {
   private static final Logger LOGGER = Grizzly.logger(TCPNIOAsyncQueueWriter.class);
   private static final Attribute COMPOSITE_BUFFER_ATTR;

   public TCPNIOAsyncQueueWriter(NIOTransport transport) {
      super(transport);
   }

   protected RecordWriteResult write0(NIOConnection connection, AsyncWriteQueueRecord queueRecord) throws IOException {
      if (queueRecord instanceof CompositeQueueRecord) {
         return this.writeCompositeRecord(connection, (CompositeQueueRecord)queueRecord);
      } else {
         RecordWriteResult writeResult = queueRecord.getCurrentResult();
         if (queueRecord.remaining() == 0L) {
            return writeResult.lastWriteResult(0L, queueRecord.isUncountable() ? 1L : 0L);
         } else {
            long written = this.write0(connection, queueRecord.getWritableMessage(), writeResult);
            return writeResult.lastWriteResult(written, written);
         }
      }
   }

   protected long write0(NIOConnection connection, WritableMessage message, WriteResult currentResult) throws IOException {
      long written;
      if (message instanceof Buffer) {
         Buffer buffer = (Buffer)message;

         try {
            if (!buffer.hasRemaining()) {
               written = 0L;
            } else if (!buffer.isComposite()) {
               written = (long)TCPNIOUtils.writeSimpleBuffer((TCPNIOConnection)connection, buffer);
            } else {
               written = (long)TCPNIOUtils.writeCompositeBuffer((TCPNIOConnection)connection, (CompositeBuffer)buffer);
            }

            ((TCPNIOConnection)connection).onWrite(buffer, written);
         } catch (IOException var8) {
            ((TCPNIOConnection)connection).terminate0((CompletionHandler)null, new CloseReason(CloseType.REMOTELY, var8));
            throw var8;
         }
      } else {
         if (!(message instanceof FileTransfer)) {
            throw new IllegalStateException("Unhandled message type");
         }

         written = ((FileTransfer)message).writeTo((SocketChannel)connection.getChannel());
         ((TCPNIOConnection)connection).onWrite((Buffer)null, written);
      }

      if (currentResult != null) {
         currentResult.setMessage(message);
         currentResult.setWrittenSize(currentResult.getWrittenSize() + written);
         currentResult.setDstAddressHolder(((TCPNIOConnection)connection).peerSocketAddressHolder);
      }

      return written;
   }

   private RecordWriteResult writeCompositeRecord(NIOConnection connection, CompositeQueueRecord queueRecord) throws IOException {
      int written = 0;
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "writeCompositeRecord connection={0}, queueRecord={1}, queueRecord.remaining={2}, queueRecord.queue.size()={3}", new Object[]{connection, queueRecord, queueRecord.remaining(), queueRecord.queue.size()});
      }

      if (queueRecord.size > 0) {
         int bufferSize = Math.min(queueRecord.size, connection.getWriteBufferSize() * 3 / 2);
         DirectByteBufferRecord directByteBufferRecord = DirectByteBufferRecord.get();

         try {
            SocketChannel socketChannel = (SocketChannel)connection.getChannel();
            fill(queueRecord, bufferSize, directByteBufferRecord);
            directByteBufferRecord.finishBufferSlice();
            int arraySize = directByteBufferRecord.getArraySize();
            written = arraySize == 1 ? TCPNIOUtils.flushByteBuffer(socketChannel, directByteBufferRecord.getArray()[0]) : TCPNIOUtils.flushByteBuffers(socketChannel, directByteBufferRecord.getArray(), 0, arraySize);
         } catch (IOException var11) {
            ((TCPNIOConnection)connection).terminate0((CompletionHandler)null, new CloseReason(CloseType.REMOTELY, var11));
            throw var11;
         } finally {
            directByteBufferRecord.release();
         }
      }

      return this.update(queueRecord, written);
   }

   private static void fill(CompositeQueueRecord queueRecord, int totalBufferSize, DirectByteBufferRecord ioRecord) {
      int totalRemaining = totalBufferSize;
      Deque queue = queueRecord.queue;
      ArrayList savedBufferStates = queueRecord.savedBufferStates;
      Iterator it = queue.iterator();

      while(it.hasNext() && totalRemaining > 0) {
         AsyncWriteQueueRecord record = (AsyncWriteQueueRecord)it.next();
         if (!record.isUncountable()) {
            Buffer message = (Buffer)record.getMessage();
            int pos = message.position();
            int messageRemaining = message.remaining();
            BufferArray bufferArray = totalRemaining >= messageRemaining ? message.toBufferArray() : message.toBufferArray(pos, pos + totalRemaining);
            savedBufferStates.add(bufferArray);
            TCPNIOUtils.fill(bufferArray, totalRemaining, ioRecord);
            totalRemaining -= messageRemaining;
         }
      }

   }

   private RecordWriteResult update(CompositeQueueRecord queueRecord, int written) {
      int extraBytesToRelease;
      for(extraBytesToRelease = 0; extraBytesToRelease < queueRecord.savedBufferStates.size(); ++extraBytesToRelease) {
         BufferArray savedState = (BufferArray)queueRecord.savedBufferStates.get(extraBytesToRelease);
         if (savedState != null) {
            savedState.restore();
            savedState.recycle();
         }
      }

      extraBytesToRelease = 0;
      queueRecord.savedBufferStates.clear();
      int remainder = written;
      queueRecord.size = queueRecord.size - written;
      Connection connection = queueRecord.getConnection();
      Deque queue = queueRecord.queue;

      AsyncWriteQueueRecord record;
      while(remainder > 0) {
         record = (AsyncWriteQueueRecord)queue.peekFirst();

         assert record != null;

         if (record.isUncountable()) {
            queue.removeFirst();
            record.notifyCompleteAndRecycle();
            ++extraBytesToRelease;
         } else {
            WriteResult firstResult = record.getCurrentResult();
            Buffer firstMessage = (Buffer)record.getMessage();
            long firstMessageRemaining = record.getInitialMessageSize() - firstResult.getWrittenSize();
            if ((long)remainder < firstMessageRemaining) {
               firstMessage.position(firstMessage.position() + remainder);
               firstResult.setWrittenSize(firstResult.getWrittenSize() + (long)remainder);
               ((TCPNIOConnection)connection).onWrite(firstMessage, (long)remainder);
               return queueRecord.getCurrentResult().lastWriteResult((long)written, (long)(written + extraBytesToRelease));
            }

            remainder = (int)((long)remainder - firstMessageRemaining);
            queue.removeFirst();
            firstResult.setWrittenSize(record.getInitialMessageSize());
            firstMessage.position(firstMessage.limit());
            ((TCPNIOConnection)connection).onWrite(firstMessage, firstMessageRemaining);
            record.notifyCompleteAndRecycle();
         }
      }

      while((record = (AsyncWriteQueueRecord)queue.peekFirst()) != null && record.isUncountable()) {
         queue.removeFirst();
         record.notifyCompleteAndRecycle();
         ++extraBytesToRelease;
      }

      return queueRecord.getCurrentResult().lastWriteResult((long)written, (long)(written + extraBytesToRelease));
   }

   protected final void onReadyToWrite(NIOConnection connection) throws IOException {
      connection.enableIOEvent(IOEvent.WRITE);
   }

   protected AsyncWriteQueueRecord aggregate(TaskQueue writeTaskQueue) {
      int queueSize = writeTaskQueue.size();
      if (queueSize == 0) {
         return null;
      } else {
         AsyncWriteQueueRecord currentRecord = (AsyncWriteQueueRecord)writeTaskQueue.poll();
         if (currentRecord != null && canBeAggregated(currentRecord) && (long)queueSize != currentRecord.remaining()) {
            AsyncWriteQueueRecord nextRecord = checkAndGetNextRecord(writeTaskQueue);
            if (nextRecord == null) {
               return currentRecord;
            } else {
               CompositeQueueRecord compositeQueueRecord = this.createCompositeQueueRecord(currentRecord);

               do {
                  compositeQueueRecord.append(nextRecord);
               } while(compositeQueueRecord.remaining() < (long)queueSize && (nextRecord = checkAndGetNextRecord(writeTaskQueue)) != null);

               return compositeQueueRecord;
            }
         } else {
            return currentRecord;
         }
      }
   }

   private static AsyncWriteQueueRecord checkAndGetNextRecord(TaskQueue writeTaskQueue) {
      AsyncWriteQueueRecord nextRecord = (AsyncWriteQueueRecord)writeTaskQueue.getQueue().poll();
      if (nextRecord == null) {
         return null;
      } else if (!canBeAggregated(nextRecord)) {
         writeTaskQueue.setCurrentElement(nextRecord);
         return null;
      } else {
         return nextRecord;
      }
   }

   private static boolean canBeAggregated(AsyncWriteQueueRecord record) {
      return record.canBeAggregated();
   }

   private CompositeQueueRecord createCompositeQueueRecord(AsyncWriteQueueRecord currentRecord) {
      if (!(currentRecord instanceof CompositeQueueRecord)) {
         Connection connection = currentRecord.getConnection();
         CompositeQueueRecord compositeQueueRecord = (CompositeQueueRecord)COMPOSITE_BUFFER_ATTR.get((AttributeStorage)connection);
         if (compositeQueueRecord == null) {
            compositeQueueRecord = TCPNIOAsyncQueueWriter.CompositeQueueRecord.create(connection);
            COMPOSITE_BUFFER_ATTR.set((AttributeStorage)connection, compositeQueueRecord);
         }

         compositeQueueRecord.append(currentRecord);
         return compositeQueueRecord;
      } else {
         return (CompositeQueueRecord)currentRecord;
      }
   }

   static {
      COMPOSITE_BUFFER_ATTR = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(TCPNIOAsyncQueueWriter.class.getName() + ".compositeBuffer");
   }

   private static final class CompositeQueueRecord extends AsyncWriteQueueRecord {
      private final ArrayList savedBufferStates = new ArrayList(2);
      private final Deque queue = new ArrayDeque(2);
      private int size;

      public static CompositeQueueRecord create(Connection connection) {
         return new CompositeQueueRecord(connection);
      }

      public CompositeQueueRecord(Connection connection) {
         super(connection, (WritableMessage)null, (CompletionHandler)null, (Object)null, (PushBackHandler)null, false);
      }

      public void append(AsyncWriteQueueRecord queueRecord) {
         if (TCPNIOAsyncQueueWriter.LOGGER.isLoggable(Level.FINEST)) {
            TCPNIOAsyncQueueWriter.LOGGER.log(Level.FINEST, "CompositeQueueRecord.append. connection={0}, this={1}, comp-size={2}, elem-count={3}, queueRecord={4}, newrec-size={5}, isEmpty={6}", new Object[]{this.connection, this, this.size, this.queue.size(), queueRecord, queueRecord.remaining(), queueRecord.isUncountable()});
         }

         this.size = (int)((long)this.size + queueRecord.remaining());
         this.queue.add(queueRecord);
      }

      public boolean isUncountable() {
         return false;
      }

      public boolean isFinished() {
         return this.size == 0;
      }

      public boolean canBeAggregated() {
         return true;
      }

      public long remaining() {
         return (long)this.size;
      }

      public void notifyCompleteAndRecycle() {
      }

      public void notifyFailure(Throwable e) {
         AsyncWriteQueueRecord record;
         while((record = (AsyncWriteQueueRecord)this.queue.poll()) != null) {
            record.notifyFailure(e);
         }

      }

      public void recycle() {
      }
   }
}
