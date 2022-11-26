package org.glassfish.grizzly.http.server;

import java.io.IOException;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.http.io.NIOWriter;
import org.glassfish.grizzly.http.io.OutputBuffer;

final class NIOWriterImpl extends NIOWriter implements Cacheable {
   private OutputBuffer outputBuffer;

   public void write(int c) throws IOException {
      this.outputBuffer.writeChar(c);
   }

   public void write(char[] cbuf) throws IOException {
      this.outputBuffer.write(cbuf);
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.outputBuffer.write(cbuf, off, len);
   }

   public void write(String str) throws IOException {
      this.outputBuffer.write(str);
   }

   public void write(String str, int off, int len) throws IOException {
      this.outputBuffer.write(str, off, len);
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

   public void recycle() {
      this.outputBuffer = null;
   }

   public void setOutputBuffer(OutputBuffer outputBuffer) {
      this.outputBuffer = outputBuffer;
   }
}
