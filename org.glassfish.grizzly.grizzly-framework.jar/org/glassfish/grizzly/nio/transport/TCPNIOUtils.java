package org.glassfish.grizzly.nio.transport;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.BufferArray;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.nio.DirectByteBufferRecord;
import org.glassfish.grizzly.utils.Exceptions;

public class TCPNIOUtils {
   static final Logger LOGGER;

   public static int writeCompositeBuffer(TCPNIOConnection connection, CompositeBuffer buffer) throws IOException {
      int bufferSize = calcWriteBufferSize(connection, buffer.remaining());
      int oldPos = buffer.position();
      int oldLim = buffer.limit();
      buffer.limit(oldPos + bufferSize);
      SocketChannel socketChannel = (SocketChannel)connection.getChannel();
      int written = false;
      BufferArray bufferArray = buffer.toBufferArray();
      DirectByteBufferRecord ioRecord = DirectByteBufferRecord.get();

      int written;
      try {
         fill(bufferArray, bufferSize, ioRecord);
         ioRecord.finishBufferSlice();
         int arraySize = ioRecord.getArraySize();
         written = arraySize != 1 ? flushByteBuffers(socketChannel, ioRecord.getArray(), 0, arraySize) : flushByteBuffer(socketChannel, ioRecord.getArray()[0]);
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "TCPNIOConnection ({0}) (composite) write {1} bytes", new Object[]{connection, written});
         }
      } finally {
         ioRecord.release();
         bufferArray.restore();
         bufferArray.recycle();
      }

      Buffers.setPositionLimit((Buffer)buffer, oldPos + written, oldLim);
      return written;
   }

   public static int writeSimpleBuffer(TCPNIOConnection connection, Buffer buffer) throws IOException {
      SocketChannel socketChannel = (SocketChannel)connection.getChannel();
      int oldPos = buffer.position();
      int oldLim = buffer.limit();
      int written;
      if (buffer.isDirect()) {
         ByteBuffer directByteBuffer = buffer.toByteBuffer();
         int pos = directByteBuffer.position();

         try {
            written = flushByteBuffer(socketChannel, directByteBuffer);
         } finally {
            directByteBuffer.position(pos);
         }
      } else {
         int bufferSize = calcWriteBufferSize(connection, buffer.remaining());
         buffer.limit(oldPos + bufferSize);
         DirectByteBufferRecord ioRecord = DirectByteBufferRecord.get();
         ByteBuffer directByteBuffer = ioRecord.allocate(bufferSize);
         fill(buffer, bufferSize, directByteBuffer);

         try {
            written = flushByteBuffer(socketChannel, directByteBuffer);
         } finally {
            ioRecord.release();
         }
      }

      Buffers.setPositionLimit(buffer, oldPos + written, oldLim);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "TCPNIOConnection ({0}) (plain) write {1} bytes", new Object[]{connection, written});
      }

      return written;
   }

   public static int flushByteBuffer(SocketChannel channel, ByteBuffer byteBuffer) throws IOException {
      return channel.write(byteBuffer);
   }

   public static int flushByteBuffers(SocketChannel channel, ByteBuffer[] byteBuffer, int firstBufferOffest, int numberOfBuffers) throws IOException {
      return (int)channel.write(byteBuffer, firstBufferOffest, numberOfBuffers);
   }

   private static void fill(Buffer src, int size, ByteBuffer dstByteBuffer) {
      dstByteBuffer.limit(size);
      int oldPos = src.position();
      src.get(dstByteBuffer);
      dstByteBuffer.position(0);
      src.position(oldPos);
   }

   static void fill(BufferArray bufferArray, int totalBufferSize, DirectByteBufferRecord ioRecord) {
      Buffer[] buffers = (Buffer[])bufferArray.getArray();
      int size = bufferArray.size();
      int totalRemaining = totalBufferSize;

      for(int i = 0; i < size; ++i) {
         Buffer buffer = buffers[i];

         assert !buffer.isComposite();

         int bufferSize = buffer.remaining();
         if (bufferSize != 0) {
            if (buffer.isDirect()) {
               ioRecord.finishBufferSlice();
               ioRecord.putToArray(buffer.toByteBuffer());
            } else {
               ByteBuffer currentDirectBufferSlice = ioRecord.getDirectBufferSlice();
               if (currentDirectBufferSlice == null) {
                  ByteBuffer directByteBuffer = ioRecord.getDirectBuffer();
                  if (directByteBuffer == null) {
                     ioRecord.allocate(totalRemaining);
                  }

                  currentDirectBufferSlice = ioRecord.sliceBuffer();
               }

               int oldLim = currentDirectBufferSlice.limit();
               currentDirectBufferSlice.limit(currentDirectBufferSlice.position() + bufferSize);
               buffer.get(currentDirectBufferSlice);
               currentDirectBufferSlice.limit(oldLim);
            }

            totalRemaining -= bufferSize;
         }
      }

   }

   private static int calcWriteBufferSize(TCPNIOConnection connection, int bufferSize) {
      return Math.min(TCPNIOTransport.MAX_SEND_BUFFER_SIZE, Math.min(bufferSize, connection.getWriteBufferSize() * 3 / 2));
   }

   public static Buffer allocateAndReadBuffer(TCPNIOConnection connection) throws IOException {
      MemoryManager memoryManager = connection.getMemoryManager();
      Throwable error = null;
      Buffer buffer = null;

      int read;
      try {
         int receiveBufferSize = Math.min(TCPNIOTransport.MAX_RECEIVE_BUFFER_SIZE, connection.getReadBufferSize());
         if (!memoryManager.willAllocateDirect(receiveBufferSize)) {
            DirectByteBufferRecord ioRecord = DirectByteBufferRecord.get();
            ByteBuffer directByteBuffer = ioRecord.allocate(receiveBufferSize);

            try {
               read = readSimpleByteBuffer(connection, directByteBuffer);
               if (read > 0) {
                  directByteBuffer.flip();
                  buffer = memoryManager.allocate(read);
                  buffer.put(directByteBuffer);
               }
            } finally {
               ioRecord.release();
            }
         } else {
            buffer = memoryManager.allocateAtLeast(receiveBufferSize);
            read = readBuffer(connection, buffer);
         }
      } catch (Throwable var12) {
         error = var12;
         read = -1;
      }

      if (read > 0) {
         buffer.position(read);
         buffer.allowBufferDispose(true);
      } else {
         if (buffer != null) {
            buffer.dispose();
         }

         if (read < 0) {
            throw error != null ? Exceptions.makeIOException(error) : new EOFException();
         }

         buffer = Buffers.EMPTY_BUFFER;
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "TCPNIOConnection ({0}) (allocated) read {1} bytes", new Object[]{connection, read});
      }

      return buffer;
   }

   public static int readBuffer(TCPNIOConnection connection, Buffer buffer) throws IOException {
      return buffer.isComposite() ? readCompositeBuffer(connection, (CompositeBuffer)buffer) : readSimpleBuffer(connection, buffer);
   }

   public static int readCompositeBuffer(TCPNIOConnection connection, CompositeBuffer buffer) throws IOException {
      SocketChannel socketChannel = (SocketChannel)connection.getChannel();
      int oldPos = buffer.position();
      ByteBufferArray array = buffer.toByteBufferArray();
      ByteBuffer[] byteBuffers = (ByteBuffer[])array.getArray();
      int size = array.size();
      int read = (int)socketChannel.read(byteBuffers, 0, size);
      array.restore();
      array.recycle();
      if (read > 0) {
         buffer.position(oldPos + read);
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "TCPNIOConnection ({0}) (nonallocated, composite) read {1} bytes", new Object[]{connection, read});
      }

      return read;
   }

   public static int readSimpleBuffer(TCPNIOConnection connection, Buffer buffer) throws IOException {
      SocketChannel socketChannel = (SocketChannel)connection.getChannel();
      int oldPos = buffer.position();
      ByteBuffer byteBuffer = buffer.toByteBuffer();
      int bbOldPos = byteBuffer.position();
      int read = socketChannel.read(byteBuffer);
      if (read > 0) {
         byteBuffer.position(bbOldPos);
         buffer.position(oldPos + read);
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "TCPNIOConnection ({0}) (nonallocated, simple) read {1} bytes", new Object[]{connection, read});
      }

      return read;
   }

   private static int readSimpleByteBuffer(TCPNIOConnection tcpConnection, ByteBuffer byteBuffer) throws IOException {
      SocketChannel socketChannel = (SocketChannel)tcpConnection.getChannel();
      return socketChannel.read(byteBuffer);
   }

   static {
      LOGGER = TCPNIOTransport.LOGGER;
   }
}
