package weblogic.diagnostics.accessor.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class IncrementalDataReader {
   private static final int DEFAULT_CHUNK_SIZE = 4096;
   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
   private InputStream inputStream;
   private ReadableByteChannel inputChannel;
   private ByteBuffer inputBuffer;
   private long timestamp = System.currentTimeMillis();
   private CloseAction closeAction;

   IncrementalDataReader(InputStream input) {
      this.inputStream = input;
      this.inputChannel = Channels.newChannel(this.inputStream);
      this.inputBuffer = ByteBuffer.allocate(4096);
   }

   public byte[] getNextDataChunk() throws IOException {
      if (!this.isOpen()) {
         return EMPTY_BYTE_ARRAY;
      } else {
         this.inputBuffer.clear();
         int bytesRead = this.inputChannel.read(this.inputBuffer);
         byte[] result = EMPTY_BYTE_ARRAY;
         if (bytesRead > 0) {
            result = new byte[bytesRead];
            this.inputBuffer.flip();
            this.inputBuffer.get(result);
         }

         this.timestamp = System.currentTimeMillis();
         return result;
      }
   }

   public long getLastAccessedTimestamp() {
      return this.timestamp;
   }

   public boolean isOpen() {
      return this.inputChannel.isOpen();
   }

   public void close() throws IOException {
      this.inputBuffer.clear();
      this.inputChannel.close();
      this.inputStream.close();
      if (this.closeAction != null) {
         this.closeAction.run();
      }

   }

   public CloseAction getCloseAction() {
      return this.closeAction;
   }

   public void setCloseAction(CloseAction closeAction) {
      this.closeAction = closeAction;
   }

   public interface CloseAction extends Runnable {
   }
}
