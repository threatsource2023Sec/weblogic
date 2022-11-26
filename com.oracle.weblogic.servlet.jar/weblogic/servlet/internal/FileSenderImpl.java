package weblogic.servlet.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import weblogic.servlet.FileSender;
import weblogic.socket.WeblogicSocket;
import weblogic.utils.io.Chunk;

public abstract class FileSenderImpl implements FileSender {
   protected final long maxMessageSize;
   protected long bytesWritten;
   protected ServletRequestImpl request;
   protected ServletResponseImpl response;

   private FileSenderImpl(ServletRequestImpl request, ServletResponseImpl response) {
      this.response = response;
      this.request = request;
      VirtualConnection connection = request.getConnection();
      this.maxMessageSize = (long)connection.getChannel().getMaxMessageSize();
      response.setFileSender(this);
   }

   public long sendFile(File file) throws IOException {
      if (!file.exists()) {
         throw new FileNotFoundException(file.getName());
      } else if (!file.isFile()) {
         throw new IOException(file.getName() + " is not a file");
      } else {
         FileChannel fc = null;

         long var3;
         try {
            fc = (new FileInputStream(file)).getChannel();
            var3 = this.sendFile(fc, 0L, file.length());
         } finally {
            if (fc != null) {
               fc.close();
            }

         }

         return var3;
      }
   }

   public long getBytesSent() {
      return this.bytesWritten;
   }

   public abstract long sendFile(FileChannel var1, long var2, long var4) throws IOException;

   public abstract boolean usesServletOutputStream();

   public static FileSender getZeroCopyFileSender(HttpServletResponse res) {
      if (res instanceof HttpServletResponseWrapper) {
         return null;
      } else {
         ServletResponseImpl response = (ServletResponseImpl)res;
         ServletRequestImpl request = response.getRequest();
         VirtualConnection vc = request.getConnection();
         Socket s = vc.getSocket();
         if (s instanceof WeblogicSocket) {
            s = ((WeblogicSocket)s).getSocket();
         }

         if (s.getChannel() == null) {
            return null;
         } else if (request.getAttribute("javax.servlet.include.request_uri") != null) {
            return null;
         } else if (response.getContext().getFilterManager().hasFilters()) {
            return null;
         } else {
            ZeroCopyFileSender fs = (ZeroCopyFileSender)response.getFileSender();
            return fs != null ? fs : new ZeroCopyFileSender(request, response);
         }
      }
   }

   public static FileSender getFileSender(HttpServletResponse res) {
      FileSender fs = getZeroCopyFileSender(res);
      if (fs != null) {
         return fs;
      } else {
         ServletResponseImpl response = (ServletResponseImpl)res;
         ServletRequestImpl request = response.getRequest();
         DefaultFileSender dfs = (DefaultFileSender)response.getFileSender();
         return dfs != null ? dfs : new DefaultFileSender(request, response);
      }
   }

   // $FF: synthetic method
   FileSenderImpl(ServletRequestImpl x0, ServletResponseImpl x1, Object x2) {
      this(x0, x1);
   }

   private static final class ChunkHeaderMaker {
      private static final byte[] DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
      private static final int ARR_LEN = 18;

      public static byte[] getChunkHeader(long d) {
         byte[] b = new byte[18];
         long mask = -1152921504606846976L;
         long lsOp = 60L;

         for(int i = 0; i < 18; ++i) {
            b[i] = DIGITS[(int)((d & mask) >> (int)lsOp)];
            mask = mask >> 4 & 1152921504606846975L;
            lsOp -= 4L;
         }

         b[16] = 13;
         b[17] = 10;
         return b;
      }
   }

   private static class DefaultFileSender extends FileSenderImpl {
      public DefaultFileSender(ServletRequestImpl rq, ServletResponseImpl rs) {
         super(rq, rs, null);
      }

      public boolean usesServletOutputStream() {
         return true;
      }

      public long sendFile(FileChannel fc, long start, long count) throws IOException {
         fc.position(start);
         int buffsize = this.response.getBufferSize();
         buffsize = buffsize != -1 && buffsize != 0 ? buffsize : Chunk.CHUNK_SIZE;
         byte[] b = null;
         Chunk c = null;

         long var18;
         try {
            byte[] b;
            if (buffsize == Chunk.CHUNK_SIZE) {
               c = Chunk.getChunk();
               b = c.buf;
            } else {
               b = new byte[buffsize];
            }

            ByteBuffer buffer = ByteBuffer.wrap(b);

            assert buffer.array() != null;

            long sent = 0L;

            while(sent != count) {
               int read = fc.read(buffer);
               long expectedMsgSize = (long)read + this.response.getServletOutputStream().getTotal() + (long)this.response.getServletOutputStream().getCount();
               if (expectedMsgSize > this.maxMessageSize) {
                  throw new IOException("Outgoing message size will exceed the configured maximum message size of " + this.maxMessageSize + " bytes");
               }

               if (read == 0 || read == -1) {
                  break;
               }

               this.response.getOutputStream().write(buffer.array(), 0, read);
               this.response.incrementBytesSentCount((long)read);
               sent += (long)read;
               this.bytesWritten += (long)read;
               buffer.clear();
            }

            var18 = sent;
         } finally {
            if (c != null) {
               Chunk.releaseChunk(c);
            }

         }

         return var18;
      }
   }

   private static class ZeroCopyFileSender extends FileSenderImpl {
      public ZeroCopyFileSender(ServletRequestImpl rq, ServletResponseImpl rs) {
         super(rq, rs, null);
      }

      public boolean usesServletOutputStream() {
         return false;
      }

      public long sendFile(FileChannel fc, long start, long count) throws IOException {
         this.response.flushBuffer();
         VirtualConnection connection = this.request.getConnection();
         long n = this.response.getServletOutputStream().getTotal();
         Socket socket = connection.getSocket();
         if (socket instanceof WeblogicSocket) {
            socket = ((WeblogicSocket)socket).getSocket();
         }

         byte[] cheader = new byte[0];
         if ("HTTP/1.1".equals(this.request.getProtocol()) && this.response.getContentLength() == 0) {
            cheader = FileSenderImpl.ChunkHeaderMaker.getChunkHeader(count);
            OutputStream os = socket.getOutputStream();
            os.write(cheader);
            os.flush();
         }

         assert socket.getChannel() != null;

         long s;
         long sent;
         for(sent = 0L; count > sent; start += s) {
            s = count - sent;
            long toSend = s > 2147483647L ? 2147483647L : s;
            long expectedMsgSize = toSend + this.bytesWritten + n + (long)cheader.length;
            if (expectedMsgSize > this.maxMessageSize) {
               throw new IOException("Outgoing message size will exceed the configured maximum message size of " + this.maxMessageSize + " bytes");
            }

            s = fc.transferTo(start, toSend, socket.getChannel());
            if (s == 0L) {
               break;
            }

            this.response.incrementBytesSentCount(s);
            this.bytesWritten += s;
            sent += s;
         }

         if ("HTTP/1.1".equals(this.request.getProtocol()) && this.response.getContentLength() == 0 && sent != count) {
            throw new IOException("Expected to send " + count + " bytes, but " + sent + " bytes sent");
         } else {
            return sent;
         }
      }
   }
}
