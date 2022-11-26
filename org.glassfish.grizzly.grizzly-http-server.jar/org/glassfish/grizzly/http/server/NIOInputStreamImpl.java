package org.glassfish.grizzly.http.server;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.http.io.InputBuffer;
import org.glassfish.grizzly.http.io.NIOInputStream;

final class NIOInputStreamImpl extends NIOInputStream implements Cacheable {
   private InputBuffer inputBuffer;

   public int read() throws IOException {
      return this.inputBuffer.readByte();
   }

   public int read(byte[] b) throws IOException {
      return this.inputBuffer.read(b, 0, b.length);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      return this.inputBuffer.read(b, off, len);
   }

   public long skip(long n) throws IOException {
      return this.inputBuffer.skip(n);
   }

   public int available() throws IOException {
      return this.inputBuffer.available();
   }

   public void close() throws IOException {
      this.inputBuffer.close();
   }

   public void mark(int readlimit) {
      this.inputBuffer.mark(readlimit);
   }

   public void reset() throws IOException {
      this.inputBuffer.reset();
   }

   public boolean markSupported() {
      return this.inputBuffer.markSupported();
   }

   public void notifyAvailable(ReadHandler handler) {
      this.inputBuffer.notifyAvailable(handler);
   }

   public void notifyAvailable(ReadHandler handler, int size) {
      this.inputBuffer.notifyAvailable(handler, size);
   }

   public boolean isFinished() {
      return this.inputBuffer.isFinished();
   }

   public int readyData() {
      return this.inputBuffer.available();
   }

   public boolean isReady() {
      return this.inputBuffer.available() > 0;
   }

   public Buffer getBuffer() {
      return this.inputBuffer.getBuffer();
   }

   public Buffer readBuffer() {
      return this.inputBuffer.readBuffer();
   }

   public Buffer readBuffer(int size) {
      return this.inputBuffer.readBuffer(size);
   }

   public void recycle() {
      this.inputBuffer = null;
   }

   public void setInputBuffer(InputBuffer inputBuffer) {
      this.inputBuffer = inputBuffer;
   }
}
