package org.glassfish.grizzly.http.server;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.http.io.OutputBuffer;

class NIOOutputStreamImpl extends NIOOutputStream implements Cacheable {
   private OutputBuffer outputBuffer;

   public void write(int b) throws IOException {
      this.outputBuffer.writeByte(b);
   }

   public void write(byte[] b) throws IOException {
      this.outputBuffer.write(b);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.outputBuffer.write(b, off, len);
   }

   public void flush() throws IOException {
      this.outputBuffer.flush();
   }

   public void close() throws IOException {
      this.outputBuffer.close();
   }

   /** @deprecated */
   public boolean canWrite(int length) {
      return this.outputBuffer.canWrite();
   }

   public boolean canWrite() {
      return this.outputBuffer.canWrite();
   }

   /** @deprecated */
   public void notifyCanWrite(WriteHandler handler, int length) {
      this.outputBuffer.notifyCanWrite(handler);
   }

   public void notifyCanWrite(WriteHandler handler) {
      this.outputBuffer.notifyCanWrite(handler);
   }

   public void write(Buffer buffer) throws IOException {
      this.outputBuffer.writeBuffer(buffer);
   }

   public void recycle() {
      this.outputBuffer = null;
   }

   public void setOutputBuffer(OutputBuffer outputBuffer) {
      this.outputBuffer = outputBuffer;
   }
}
