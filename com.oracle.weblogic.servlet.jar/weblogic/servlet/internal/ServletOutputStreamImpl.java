package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.SocketException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.WebConnection;
import weblogic.servlet.http2.Stream;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.utils.ChunkedGzipOutputStream;
import weblogic.servlet.utils.HTTPDiagnosticHelper;
import weblogic.servlet.utils.StatInfoCalculator;
import weblogic.servlet.utils.StatOutputStream;
import weblogic.socket.MuxableSocket;
import weblogic.socket.NIOConnection;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedDataOutputStream;

public final class ServletOutputStreamImpl extends ServletOutputStream {
   private static final int DEFAULT_BUFFER_SIZE = 8192;
   private final ServletResponseImpl response;
   private OutputStream out;
   private OutputStream gzipOut;
   private boolean headersSent;
   private boolean inFinish = false;
   private ChunkOutputWrapper co;
   private long clen;
   private boolean nativeControlsPipe = false;
   private boolean enforceCL;
   private boolean flushOK = true;
   private boolean doFinish = true;
   private boolean useGzip = false;
   private boolean upgradeMode = false;
   private WriteListenerStateContext writeStateContext = null;
   private WebConnection webConnection = null;
   private WebAppServletContext context = null;
   private StatInfoCalculator calculator = null;
   private boolean isUpgrade = false;
   private HttpServer httper;
   private boolean commitCalled = false;
   private boolean writeBody;
   private static final boolean allowClosingStream = Boolean.getBoolean("weblogic.http.allowClosingServletOutputStream");
   static final byte[] FINAL_CHUNK = new byte[]{48, 48, 48, 48, 13, 10, 13, 10};
   private static final byte[] ZERO_CHUNK = new byte[]{48, 48, 48, 48, 13, 10};

   public ServletOutputStreamImpl(OutputStream o, ServletResponseImpl res) {
      this.setHttpServer(WebServerRegistry.getInstance().getHttpServerManager().defaultHttpServer());
      this.response = res;
      this.out = o;
      int bufSize = 8192;
      if (o instanceof NIOConnection) {
         NIOConnection conn = (NIOConnection)o;
         if (conn.supportsGatheredWrites()) {
            bufSize = conn.getOptimalNumberOfBuffers() * Chunk.CHUNK_SIZE;
         }
      }

      this.co = new ChunkOutputWrapper(ChunkOutput.create(bufSize, true, this.out, this));
      this.setBufferSize(bufSize);
      this.co.setChunking(false);
      this.clen = -1L;
   }

   public void flush() throws IOException {
      if (DebugHttpConciseLogger.isEnabled()) {
         HTTPDiagnosticHelper.analyzeAndDumpStackTraceForNonWeblogicCaller("flush", -1);
      }

      try {
         if (this.flushOK) {
            if (!this.headersSent) {
               this.sendHeaders();
            }

            this.co.flush();
         }
      } catch (IOException var2) {
         if (!this.handleIOException(var2)) {
            throw var2;
         }
      } catch (RuntimeException var3) {
         if (!this.handleRuntimeException(var3)) {
            throw var3;
         }
      }

   }

   public void write(int b) throws IOException {
      this.checkReadyStatus();
      if (this.canWrite()) {
         this.checkCL(1);

         try {
            this.co.writeByte(b);
         } catch (IOException var3) {
            if (!this.handleIOException(var3)) {
               throw var3;
            }
         } catch (RuntimeException var4) {
            if (!this.handleRuntimeException(var4)) {
               throw var4;
            }
         }
      }

   }

   private boolean canWrite() {
      return this.flushOK && !this.isSuspended();
   }

   public final void write(byte[] b, int off, int len) throws IOException {
      this.checkReadyStatus();
      if (this.canWrite()) {
         this.checkCL(len);

         try {
            this.co.write(b, off, len);
         } catch (IOException var5) {
            if (!this.handleIOException(var5)) {
               throw var5;
            }
         } catch (RuntimeException var6) {
            if (!this.handleRuntimeException(var6)) {
               throw var6;
            }
         }
      }

   }

   static boolean supportsGatheredWrites(OutputStream os) {
      return os instanceof NIOConnection ? ((NIOConnection)os).supportsGatheredWrites() : false;
   }

   void writeHeader(ChunkedDataOutputStream headerStream) throws IOException {
      if (supportsGatheredWrites(this.out) && this.writeBody) {
         this.co.setHttpHeaders(headerStream.getChunks());
      } else {
         headerStream.writeTo(this.out);
      }

   }

   public void print(String s) throws IOException {
      this.checkReadyStatus();
      if (this.canWrite()) {
         if (s == null) {
            this.checkCL("null".length());
            this.co.print("null");
            return;
         }

         int len = s.length();
         this.checkCL(len);
         this.co.print(s);
      }

   }

   private final void checkCL(int incr) throws IOException {
      if (this.enforceCL) {
         if (this.co.getTotal() + (long)this.co.getCount() + (long)incr > this.clen) {
            throw new ProtocolException("Exceeded stated content-length of: '" + this.clen + "' bytes");
         }
      }
   }

   private boolean handleIOException(IOException e) {
      this.flushOK = false;
      if (this.inFinish) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("IOException occurs while finishing the output", e);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean handleRuntimeException(RuntimeException e) {
      if (this.inFinish && isNestedSocketException(e)) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Socket issue", e);
         }

         this.flushOK = false;
         return true;
      } else {
         return false;
      }
   }

   private static boolean isNestedSocketException(RuntimeException re) {
      Object e;
      for(e = re; e != null && !(e instanceof SocketException); e = ((Throwable)e).getCause()) {
      }

      return e instanceof SocketException;
   }

   void sendHeaders() throws IOException {
      try {
         if (this.flushOK) {
            if (this.headersSent) {
               return;
            }

            if (this.upgradeMode) {
               return;
            }

            if (this.response.getStatus() == 304) {
               this.clearCurrentBuffer();
               this.setWriteEnabled(false);
            } else if (!this.inFinish && this.clen == -1L && !this.isUpgrade()) {
               if (this.response.getRequest().getInputHelper().getRequestParser().isProtocolVersion_1_1() && !this.httper.getMBean().isChunkedTransferDisabled()) {
                  if (this.response.getStatus() != 204) {
                     this.enableChunkedTransferEncoding(false);
                  }
               } else {
                  this.response.disableKeepAlive();
               }
            }

            this.response.writeHeaders();
            this.headersSent = true;
         }
      } catch (IOException var2) {
         if (this.flushOK && !this.handleIOException(var2)) {
            throw var2;
         }
      }

   }

   void enableChunkedTransferEncoding(boolean isSet) {
      this.co.setChunking(true);
      Collection transferEncodingHeaders = this.response.getHeaders("Transfer-Encoding");
      if (transferEncodingHeaders != null && !transferEncodingHeaders.isEmpty()) {
         Iterator var3 = transferEncodingHeaders.iterator();

         String header;
         do {
            if (!var3.hasNext()) {
               this.response.enableChunkedTransferEncodingHeader(isSet);
               return;
            }

            header = (String)var3.next();
         } while(header.indexOf("chunked") <= -1);

      } else {
         this.response.enableChunkedTransferEncodingHeader(isSet);
      }
   }

   boolean headersSent() {
      return this.headersSent;
   }

   void flushBuffer() throws IOException {
      this.flush();
   }

   boolean isCommitted() {
      return this.headersSent;
   }

   boolean isSuspended() {
      return this.co.isSuspended();
   }

   void setSuspended(boolean s) {
      this.co.setSuspended(s);
   }

   void reset() throws IllegalStateException {
      this.clearBuffer();
      this.enforceCL = false;
      this.clen = -1L;
      this.co.setChunking(false);
      this.nativeControlsPipe = false;
   }

   int getBufferSize() {
      return this.co.getBufferSize();
   }

   void setBufferSize(int bufSize) {
      this.co.setBufferSize(bufSize);
   }

   public void setNativeControlsPipe(boolean isNative) throws IOException {
      if (!this.nativeControlsPipe && isNative) {
         this.clearCurrentBuffer();
         this.setEnforceContentLength(false);
         this.response.writeHeaders();
         this.headersSent = true;
         this.co.setNativeControlsPipe(true);
      } else if (this.nativeControlsPipe && !isNative) {
         this.clearCurrentBuffer();
         this.setEnforceContentLength(false);
         this.clen = -1L;
         this.co.setChunking(false);
         this.co.setNativeControlsPipe(false);
      }

      this.nativeControlsPipe = isNative;
   }

   private void setEnforceContentLength(boolean b) {
      if (!this.writeBody) {
         this.enforceCL = false;
      } else {
         this.enforceCL = b;
      }
   }

   public void clearBuffer() {
      if (this.isCommitted()) {
         throw new IllegalStateException("Response already committed");
      } else {
         this.clearCurrentBuffer();
      }
   }

   public void clearCurrentBuffer() {
      this.co.clearBuffer();
   }

   public int getCount() {
      return this.co.getCount();
   }

   long getTotal() {
      return this.co.getTotal();
   }

   void setOutput(ChunkOutputWrapper co) {
      this.co = co;
   }

   public ChunkOutputWrapper getOutput() {
      return this.co;
   }

   void setHttpServer(HttpServer h) {
      this.httper = h;
   }

   public void setContentLength(long len) throws ProtocolException {
      if (this.enforceCL && this.headersSent) {
         throw new ProtocolException("Content-Length already set");
      } else {
         if (this.writeBody) {
            this.enforceCL = true;
         }

         this.clen = len;
         this.co.setCL(this.clen);
         if (this.useKeepAliveHeader()) {
            this.response.setHeaderInternal("Connection", "Keep-Alive");
         }

      }
   }

   private boolean useKeepAliveHeader() {
      if (this.response.getRequest().getInputHelper().getRequestParser().isProtocolVersion_1_1()) {
         return false;
      } else if (this.response.hasKeepAliveHeader()) {
         return false;
      } else {
         return this.response.getUseKeepAlive();
      }
   }

   boolean getDoFinish() {
      return this.doFinish;
   }

   public void setDoFinish(boolean b) {
      this.doFinish = b;
   }

   protected boolean useGzip() {
      return this.useGzip;
   }

   protected void setUseGzip(boolean useGzip) {
      this.useGzip = useGzip;
   }

   void ensureContentLength(long extraBytes) throws IOException {
      if (this.enforceCL && this.flushOK && this.writeBody) {
         long total = this.co.getTotal() + (long)this.co.getCount() + extraBytes;
         if (total != 0L && this.clen != total) {
            if (!this.isCommitted()) {
               try {
                  this.response.sendError(500);
               } catch (Throwable var6) {
               }
            }

            throw new ProtocolException("Didn't meet stated Content-Length, wrote: '" + total + "' bytes instead of stated: '" + this.clen + "' bytes.");
         }
      }
   }

   public boolean isFlushOK() {
      return this.flushOK;
   }

   public void commit() throws IOException {
      if (!this.commitCalled) {
         if (this.doFinish) {
            if (!this.nativeControlsPipe) {
               this.co.setSuspended(false);
               if (!this.headersSent && !this.enforceCL) {
                  if (!this.writeBody) {
                     if (this.response.getStatus() != 204) {
                        this.response.setContentLengthLong(this.clen);
                     }
                  } else if (this.response.getStatus() != 304 && this.response.getStatus() != 204 && !this.co.isChunking()) {
                     this.response.setContentLengthLong(this.co.getTotal() + (long)this.co.getCount());
                  }
               }

               try {
                  this.inFinish = true;
                  boolean hasFinalChunk = this.co.isChunking() && this.flushOK && this.writeBody;
                  if (!hasFinalChunk) {
                     this.commitCalled = true;
                  }

                  this.flush();
                  if (this.response.getRequest().getInputHelper().getRequestParser().isProtocolVersion_2()) {
                     Stream stream = this.response.getRequest().getConnection().getConnectionHandler().getStream();
                     stream.completeDataSend();
                     Map trailerFields = null;
                     if (this.response.getTrailerFields() != null) {
                        trailerFields = (Map)this.response.getTrailerFields().get();
                        stream.sendTrailers(trailerFields);
                     }
                  }

                  if (this.useGzip) {
                     this.closeGZIPOutput();
                  }

                  if (hasFinalChunk || this.useGzip) {
                     try {
                        Map trailerFields = null;
                        if (this.response.getTrailerFields() != null) {
                           trailerFields = (Map)this.response.getTrailerFields().get();
                        }

                        if (trailerFields != null && trailerFields.size() != 0) {
                           this.out.write(ZERO_CHUNK);
                           String trailerPartAndLastCRLF = this.getTrailerPartAndLastCRLF(trailerFields);
                           this.out.write(trailerPartAndLastCRLF.getBytes(this.response.getCharacterEncoding()));
                           this.response.incrementBytesSentCount((long)(ZERO_CHUNK.length + trailerPartAndLastCRLF.length()));
                        } else {
                           this.out.write(FINAL_CHUNK);
                           this.response.incrementBytesSentCount((long)FINAL_CHUNK.length);
                        }

                        this.commitCalled = true;
                        this.out.flush();
                     } catch (IOException var8) {
                        if (!this.handleIOException(var8)) {
                           throw var8;
                        }
                     } catch (RuntimeException var9) {
                        if (!this.handleRuntimeException(var9)) {
                           throw var9;
                        }
                     }
                  }

                  if (this.useGzip && HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("CPU time used for Gzip compressoin is: " + this.calculator.getCpuTimeInMilliSec() + " milliseconds");
                     HTTPDebugLogger.debug("Original content length is: " + this.calculator.getOrigContentLength() + " bytes");
                     HTTPDebugLogger.debug("Gzip compressed content length is: " + (this.calculator.getGzipedContentLength() + (long)FINAL_CHUNK.length) + " bytes");
                     HTTPDebugLogger.debug("Gzip Compression ratio is: " + this.calculator.getCompressionRatio());
                  }
               } finally {
                  this.inFinish = false;
                  this.co.setChunking(false);
                  this.clen = -1L;
                  this.enforceCL = false;
                  this.co.setWriteEnabled(false);
                  this.commitCalled = true;
               }

            }
         }
      }
   }

   private String getTrailerPartAndLastCRLF(Map trailerFields) {
      StringBuilder result = new StringBuilder();
      Iterator var3 = trailerFields.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry trailerEntry = (Map.Entry)var3.next();
         if (!this.response.skipDisallowedTrailerField((String)trailerEntry.getKey())) {
            result.append((String)trailerEntry.getKey());
            result.append(": ");
            result.append((String)trailerEntry.getValue());
            result.append("\r\n");
         }
      }

      result.append("\r\n");
      return result.toString();
   }

   public void finish() throws IOException {
      if (!this.commitCalled) {
         this.commit();
      }

      this.response.resetOutputState();
      this.commitCalled = false;
      this.co.setWriteEnabled(true);
      this.co.setAutoFlush(true);
      if (!this.isUpgrade && (!this.flushOK || !this.response.getUseKeepAlive())) {
         this.co.release();
      } else {
         this.co.reset();
         if (this.co.getEncoding() != null || ChunkOutput.USE_JDK_ENCODER_FOR_ASCII) {
            this.co.changeToCharset((String)null, this.response.getCharsetMap());
         }

         this.headersSent = false;
      }

   }

   public void writeStream(InputStream is) throws IOException {
      this.writeStream(is, -1);
   }

   public void writeStream(InputStream is, int len) throws IOException {
      try {
         this.co.writeStream(is, len);
      } catch (IOException var4) {
         if (!this.handleIOException(var4)) {
            throw var4;
         }
      } catch (RuntimeException var5) {
         if (!this.handleRuntimeException(var5)) {
            throw var5;
         }
      }

   }

   public void writeStreamWithEncoding(InputStream is) throws IOException {
      this.writeStream(is, -1);
   }

   OutputStream getRawOutputStream() {
      return this.out;
   }

   void setWriteEnabled(boolean b) {
      this.writeBody = b;
      this.co.setWriteEnabled(this.writeBody);
   }

   public void close() throws IOException {
      super.close();
      if (allowClosingStream) {
         this.flush();
         this.co.setWriteEnabled(false);
      }

   }

   public boolean isCommitCalled() {
      return this.commitCalled;
   }

   boolean isUpgrade() {
      return this.isUpgrade;
   }

   void setUpgrade(boolean isUpgrade) {
      this.isUpgrade = isUpgrade;
   }

   public void setWriteListener(WriteListener writeListener) {
      if (writeListener == null) {
         if (this.writeStateContext != null) {
            throw new IllegalStateException("Could not switch back to Blocking IO!");
         } else {
            throw new NullPointerException("Listener should not be set to null!");
         }
      } else if (this.writeStateContext != null) {
         throw new IllegalStateException("Already set a WriteListener!");
      } else {
         ServletRequestImpl req = this.response.getRequest();
         if (!req.isAsyncMode() && !this.isUpgrade()) {
            throw new IllegalStateException("Cannot set WriteListener for non-async or non-upgrade request!");
         } else if (req.isAsyncMode() && !req.isAsyncStarted()) {
            throw new IllegalStateException("Cannot set WriteListener for an async request which is not started status!");
         } else {
            if (req.isAsyncMode()) {
               this.context = this.response.getContext();
            }

            this.writeStateContext = new WriteListenerStateContext(writeListener, this.co, this, this.context, req.getConnection().getConnectionHandler());
            if (req.isAsyncMode()) {
               this.writeStateContext.setAsyncContext(req.getAsyncContext());
            }

            this.co.setWriteStateContext(this.writeStateContext, (MuxableSocket)req.getConnection().getConnectionHandler().getRawConnection());
            if (this.writeStateContext.isWriteWait()) {
               this.getOutput().notifyWritePossible(this.writeStateContext);
            } else {
               this.writeStateContext.process();
            }

         }
      }
   }

   public void resetChunkOutput() {
      if (this.writeStateContext != null) {
         this.writeStateContext.setFinishedState();
         this.co.resetChunkOutput();
      }

   }

   private void checkReadyStatus() throws IOException {
      if (this.writeStateContext != null && !this.isReady()) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("OutputStream is trying to write in a not ready status!");
         }

         this.flush();
         this.writeStateContext.setWriteReadyState();
      }

   }

   public boolean isReady() {
      return this.writeStateContext == null ? true : this.writeStateContext.isWriteReady();
   }

   protected WebConnection getWebConnection() {
      return this.webConnection;
   }

   protected void setWebConnection(WebConnection webConnection) {
      this.webConnection = webConnection;
   }

   protected void setContext(WebAppServletContext context) {
      this.context = context;
   }

   protected boolean isUpgradeMode() {
      return this.upgradeMode;
   }

   protected void setUpgradeMode(boolean upgradeMode) {
      this.upgradeMode = upgradeMode;
   }

   protected OutputStream createGzipOutput() throws IOException {
      if (this.gzipOut == null) {
         if (HTTPDebugLogger.isEnabled()) {
            StatOutputStream statOrigOut = new StatOutputStream(this.out);
            this.gzipOut = new StatOutputStream(new ChunkedGzipOutputStream(statOrigOut));
            this.calculator = new StatInfoCalculator(statOrigOut, (StatOutputStream)this.gzipOut);
         } else {
            this.gzipOut = new ChunkedGzipOutputStream(this.out);
         }
      }

      return this.gzipOut;
   }

   protected void resetGzipOutput() throws IllegalStateException {
      this.useGzip = false;
      this.calculator = null;
      this.gzipOut = null;
   }

   protected void closeGZIPOutput() throws IOException {
      if (HTTPDebugLogger.isEnabled()) {
         ((StatOutputStream)this.co.getOutput().getOutput()).finish();
      } else {
         ((ChunkedGzipOutputStream)this.co.getOutput().getOutput()).finish();
      }

   }

   protected StatInfoCalculator getCalculator() {
      return this.calculator;
   }
}
