package org.glassfish.grizzly.streams;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;

public class StreamOutput implements Output {
   private final StreamWriter streamWriter;

   public StreamOutput(StreamWriter streamWriter) {
      this.streamWriter = streamWriter;
   }

   public void write(byte data) throws IOException {
      this.streamWriter.writeByte(data);
   }

   public void write(Buffer buffer) throws IOException {
      this.streamWriter.writeBuffer(buffer);
   }

   public boolean isBuffered() {
      return false;
   }

   public void ensureBufferCapacity(int size) throws IOException {
   }

   public Buffer getBuffer() {
      throw new UnsupportedOperationException("Buffer is not available in StreamOutput");
   }

   public GrizzlyFuture flush(CompletionHandler completionHandler) throws IOException {
      return this.streamWriter.flush(completionHandler);
   }

   public GrizzlyFuture close(CompletionHandler completionHandler) throws IOException {
      return this.streamWriter.close(completionHandler);
   }
}
