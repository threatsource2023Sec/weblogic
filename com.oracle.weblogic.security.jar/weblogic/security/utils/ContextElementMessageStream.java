package weblogic.security.utils;

import java.io.IOException;
import java.io.InputStream;
import weblogic.security.shared.LoggerWrapper;

public class ContextElementMessageStream extends InputStream implements weblogic.security.service.ContextElementMessageStream {
   private static LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecurityAuditor");
   private boolean resetCalled = false;
   private int readLimit = Integer.MAX_VALUE;
   private InputStream realStream = null;

   private ContextElementMessageStream() {
   }

   public ContextElementMessageStream(InputStream theRealStream, int theReadLimit) {
      this.realStream = theRealStream;
      this.readLimit = theReadLimit;
   }

   public void resetToStart() {
      if (this.resetCalled) {
         try {
            this.realStream.reset();
         } catch (IOException var2) {
            LOGGER.debug("Failure while resetting ContextElementMessageStream", var2);
         }

         this.resetCalled = true;
      }

      this.realStream.mark(this.readLimit);
   }

   public int available() throws IOException {
      return this.realStream.available();
   }

   public void close() throws IOException {
   }

   public void mark(int readlimit) {
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      return this.realStream.read();
   }

   public int read(byte[] b) throws IOException {
      return this.realStream.read(b);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      return this.realStream.read(b, off, len);
   }

   public void reset() throws IOException {
      throw new IOException("Reset not supported on InputStream");
   }

   public long skip(long n) throws IOException {
      return this.realStream.skip(n);
   }
}
