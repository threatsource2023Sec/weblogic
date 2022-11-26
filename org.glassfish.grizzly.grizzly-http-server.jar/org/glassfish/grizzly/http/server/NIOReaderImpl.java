package org.glassfish.grizzly.http.server;

import java.io.IOException;
import java.nio.CharBuffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.http.io.InputBuffer;
import org.glassfish.grizzly.http.io.NIOReader;

final class NIOReaderImpl extends NIOReader implements Cacheable {
   private InputBuffer inputBuffer;

   public int read(CharBuffer target) throws IOException {
      return this.inputBuffer.read(target);
   }

   public int read() throws IOException {
      return this.inputBuffer.readChar();
   }

   public int read(char[] cbuf) throws IOException {
      return this.inputBuffer.read(cbuf, 0, cbuf.length);
   }

   public long skip(long n) throws IOException {
      return this.inputBuffer.skip(n);
   }

   public boolean ready() throws IOException {
      return this.isReady();
   }

   public boolean markSupported() {
      return true;
   }

   public void mark(int readAheadLimit) throws IOException {
      this.inputBuffer.mark(readAheadLimit);
   }

   public void reset() throws IOException {
      this.inputBuffer.reset();
   }

   public int read(char[] cbuf, int off, int len) throws IOException {
      return this.inputBuffer.read(cbuf, off, len);
   }

   public void close() throws IOException {
      this.inputBuffer.close();
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
      return this.inputBuffer.availableChar();
   }

   public boolean isReady() {
      return this.readyData() > 0;
   }

   public void recycle() {
      this.inputBuffer = null;
   }

   public void setInputBuffer(InputBuffer inputBuffer) {
      this.inputBuffer = inputBuffer;
   }
}
