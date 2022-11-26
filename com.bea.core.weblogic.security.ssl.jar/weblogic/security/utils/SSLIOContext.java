package weblogic.security.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.SSLFilterImpl;
import weblogic.utils.io.ChunkedInputStream;

public final class SSLIOContext {
   private static final boolean DEBUG = SSLSetupLogging.isDebugEnabled(3);
   private SSLSocket sslSocket;
   private SSLFilter muxerFilter;
   private ChunkedInputStream muxerIS;
   private OutputStream outputStream;
   private InputStream rawInputStream;

   public SSLIOContext(InputStream is, OutputStream os, SSLSocket sock) throws IOException {
      this.rawInputStream = is;
      this.outputStream = os;
      this.sslSocket = sock;
      this.muxerFilter = new SSLFilterImpl(is, sock);
      this.muxerIS = this.muxerFilter.getInputStream();
   }

   public SSLIOContext(InputStream is, OutputStream os, SSLSocket sock, SSLFilter filter) throws IOException {
      this.rawInputStream = is;
      this.outputStream = os;
      this.sslSocket = sock;
      this.muxerFilter = filter;
      this.muxerIS = this.muxerFilter.getInputStream();
   }

   public boolean isMuxerActivated() {
      if (DEBUG) {
         SSLSetupLogging.info("isMuxerActivated: " + this.muxerFilter.isActivated());
      }

      return this.muxerFilter.isActivated();
   }

   public synchronized boolean hasSSLRecord() {
      if (DEBUG) {
         SSLSetupLogging.info("hasSSLRecord()");
      }

      int hibite = this.muxerIS.peek(3);
      int lobite = this.muxerIS.peek(4);
      if (hibite != -1 && lobite != -1) {
         int recordLen = (hibite & 255) << 8 | lobite & 255;
         boolean hasRecord = recordLen + 5 <= this.muxerIS.available();
         if (DEBUG) {
            SSLSetupLogging.info("hasSSLRecord returns " + hasRecord);
         }

         return hasRecord;
      } else {
         if (DEBUG) {
            SSLSetupLogging.info("hasSSLRecord returns false 1");
         }

         return false;
      }
   }

   public InputStream getMuxerIS() {
      return this.muxerIS;
   }

   public SSLSocket getSSLSocket() {
      return this.sslSocket;
   }

   public Object getFilter() {
      return this.muxerFilter;
   }

   public InputStream getRawInputStream() {
      return this.rawInputStream;
   }
}
