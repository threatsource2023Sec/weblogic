package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import weblogic.socket.MuxableSocket;
import weblogic.utils.http.BytesToString;
import weblogic.utils.io.Chunk;

public class ChunkOutputWrapper {
   public static final String OUTPUT_ATT_NAME = "weblogic.servlet.BodyTagOutput";
   private ChunkOutput currChunkoutput;
   private ChunkOutput realChunkOutput;
   private boolean writeEnabled = true;
   private boolean nativeControlsPipe = false;
   private boolean suspended = false;
   private boolean enforceCL = false;
   private long clen = -1L;
   private static final ChunkOutput readonlyChunkoutput = new NullChunkOutput();
   private static final ChunkOutput nativePipeChunkoutput = new IllegalStateChunkOutput(new IllegalStateException("Can not send data via ServletOutputStream or Writer when native control pipe is used."));

   public static ChunkOutputWrapper getCurrentOutput(ServletRequest rq, ServletResponse rp) throws IOException {
      ChunkOutputWrapper co = null;
      co = (ChunkOutputWrapper)rq.getAttribute("weblogic.servlet.BodyTagOutput");
      if (co != null) {
         return co;
      } else {
         ServletOutputStream os = rp.getOutputStream();
         if (os instanceof ServletOutputStreamImpl) {
            ServletOutputStreamImpl sos = (ServletOutputStreamImpl)os;
            co = sos.getOutput();
         }

         return co;
      }
   }

   public ChunkOutputWrapper(ChunkOutput co) {
      this.currChunkoutput = co;
      this.realChunkOutput = co;
   }

   boolean isSuspended() {
      return this.suspended;
   }

   void setSuspended(boolean suspended) {
      this.suspended = suspended;
   }

   public void changeToCharset(String charset, CharsetMap csm) throws UnsupportedEncodingException {
      if (BytesToString.is8BitUnicodeSubset(charset)) {
         this.realChunkOutput = ChunkOutput.create(this.realChunkOutput, (String)null, csm);
      } else {
         this.realChunkOutput = ChunkOutput.create(this.realChunkOutput, charset, csm);
      }

      if (this.writeEnabled) {
         this.currChunkoutput = this.realChunkOutput;
      }

   }

   public ChunkOutput getOutput() {
      return this.currChunkoutput;
   }

   public void setOutput(ChunkOutput co) {
      this.currChunkoutput = co;
      this.realChunkOutput = co;
   }

   public void reset() {
      this.realChunkOutput.reset();
      this.enforceCL = false;
      this.clen = -1L;
      this.suspended = false;
   }

   public void release() {
      this.realChunkOutput.release();
      this.enforceCL = false;
      this.clen = -1L;
      this.suspended = false;
   }

   public long getTotal() {
      return this.realChunkOutput.getTotal();
   }

   public int getCount() {
      return this.realChunkOutput.getCount();
   }

   public int getBufferSize() {
      return this.realChunkOutput.getBufferSize();
   }

   public void setBufferSize(int bs) {
      this.realChunkOutput.setBufferSize(bs);
   }

   public boolean isAutoFlush() {
      return this.realChunkOutput.isAutoFlush();
   }

   public void setAutoFlush(boolean b) {
      this.realChunkOutput.setAutoFlush(b);
   }

   public boolean isChunking() {
      return this.realChunkOutput.isChunking();
   }

   public void setChunking(boolean b) {
      this.realChunkOutput.setChunking(b);
   }

   void writeByte(int i) throws IOException {
      if (!this.isSuspended()) {
         if (!this.enforceCL) {
            this.currChunkoutput.writeByte(i);
         } else {
            if (this.remainingContent() > 0L) {
               this.currChunkoutput.writeByte(i);
            }

            if (this.remainingContent() == 0L) {
               this.currChunkoutput.commit();
            }
         }

      }
   }

   public void write(int i) throws IOException {
      if (!this.isSuspended()) {
         if (!this.enforceCL) {
            this.currChunkoutput.write(i);
         } else {
            if (this.remainingContent() > 0L) {
               this.currChunkoutput.write(i);
            }

            if (this.remainingContent() == 0L) {
               this.currChunkoutput.commit();
            }
         }

      }
   }

   public void write(char[] c, int off, int len) throws IOException {
      if (!this.isSuspended()) {
         this.checkBoundary(c.length, off, len);
         if (!this.enforceCL) {
            this.currChunkoutput.write(c, off, len);
         } else {
            long rem = this.remainingContent();
            if ((long)len > rem) {
               len = (int)rem;
            }

            this.currChunkoutput.write(c, off, len);
            if (this.remainingContent() == 0L) {
               this.currChunkoutput.commit();
            }
         }

      }
   }

   public void write(char[] c) throws IOException {
      if (!this.isSuspended()) {
         if (!this.enforceCL) {
            this.currChunkoutput.write((char[])c, 0, c.length);
         } else {
            long rem = this.remainingContent();
            int len = c.length;
            if ((long)len > rem) {
               len = (int)rem;
            }

            this.currChunkoutput.write((char[])c, 0, len);
            if (this.remainingContent() == 0L) {
               this.currChunkoutput.commit();
            }
         }

      }
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (!this.isSuspended()) {
         this.checkBoundary(b.length, off, len);
         if (!this.enforceCL) {
            this.currChunkoutput.write(b, off, len);
         } else {
            long rem = this.remainingContent();
            if ((long)len > rem) {
               len = (int)rem;
            }

            this.currChunkoutput.write(b, off, len);
            if (this.remainingContent() == 0L) {
               this.currChunkoutput.commit();
            }
         }

      }
   }

   public void write(ByteBuffer buf) throws IOException {
      if (!this.isSuspended()) {
         if (!this.enforceCL) {
            this.currChunkoutput.write(buf);
         } else {
            int len = buf.limit();
            long rem = this.remainingContent();
            if ((long)len > rem) {
               len = (int)rem;
            }

            this.currChunkoutput.write(buf);
            if (this.remainingContent() == 0L) {
               this.currChunkoutput.commit();
            }
         }

      }
   }

   public void print(String s) throws IOException {
      if (!this.isSuspended()) {
         if (!this.enforceCL) {
            this.currChunkoutput.print(s);
         } else {
            long rem = this.remainingContent();
            if (s == null) {
               s = "null";
            }

            int len = s.length();
            if ((long)len > rem) {
               s = s.substring(0, (int)rem);
            }

            this.currChunkoutput.print(s);
            if (this.remainingContent() == 0L) {
               this.currChunkoutput.commit();
            }
         }

      }
   }

   public void clearBuffer() {
      this.realChunkOutput.clearBuffer();
   }

   public void flush() throws IOException {
      this.currChunkoutput.flush();
   }

   public void writeStream(InputStream is, int len) throws IOException {
      if (!this.isSuspended()) {
         this.currChunkoutput.writeStream(is, (long)len, this.clen);
      }
   }

   public String getEncoding() {
      return this.realChunkOutput.getEncoding();
   }

   boolean isWriteEnabled() {
      return this.writeEnabled;
   }

   void setWriteEnabled(boolean b) {
      this.writeEnabled = b;
      if (!this.writeEnabled) {
         this.currChunkoutput = readonlyChunkoutput;
      } else {
         this.currChunkoutput = this.realChunkOutput;
      }

   }

   void setWriteStateContext(WriteListenerStateContext writeStateContext, MuxableSocket ms) {
      this.realChunkOutput.setWriteStateContext(writeStateContext, ms);
   }

   boolean canWrite() {
      return this.realChunkOutput.canWrite();
   }

   public void notifyWritePossible(WriteListenerStateContext writeStateContext) {
      this.realChunkOutput.notifyWritePossible(writeStateContext);
   }

   public void resetChunkOutput() {
      this.realChunkOutput.setWriteStateContext((WriteListenerStateContext)null, (MuxableSocket)null);
      this.realChunkOutput.setBufferFlushStrategy();
   }

   void setNativeControlsPipe(boolean b) {
      this.nativeControlsPipe = b;
      if (this.nativeControlsPipe) {
         this.currChunkoutput = nativePipeChunkoutput;
      } else {
         this.currChunkoutput = this.realChunkOutput;
      }

   }

   void setHttpHeaders(Chunk httpHeaders) {
      this.realChunkOutput.setHttpHeaders(httpHeaders);
   }

   public void commit() throws IOException {
      this.realChunkOutput.commit();
   }

   public void setCL(long cl) {
      this.clen = cl;
      if (this.clen != -1L) {
         this.enforceCL = true;
      }

   }

   private long remainingContent() {
      return this.clen - (this.getTotal() + (long)this.getCount());
   }

   private void checkBoundary(int total, int off, int len) {
      if (off < 0 || off > total || len < 0 || off + len > total || off + len < 0) {
         throw new IndexOutOfBoundsException();
      }
   }
}
