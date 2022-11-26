package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import weblogic.rmi.extensions.server.StubDelegateInfo;

public class JDBCOutputStreamStub extends OutputStream implements JDBCOutputStream, Serializable, StubDelegateInfo {
   JDBCOutputStream remoteOs;
   boolean verbose = false;
   byte[] bytes = null;
   private int currpos = 0;
   boolean closed = false;
   int block_size;

   public JDBCOutputStreamStub(JDBCOutputStream os, boolean verbose, int block_size) {
      this.remoteOs = os;
      this.verbose = verbose;
      this.block_size = block_size;
   }

   public void write(int b) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         if (this.bytes == null) {
            this.bytes = new byte[this.block_size];
         }

         if (this.currpos >= this.block_size) {
            this.flush();
         }

         this.bytes[this.currpos++] = (byte)b;
      }
   }

   public void flush() throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         if (this.bytes != null && this.currpos > 0) {
            if (this.currpos >= this.block_size) {
               this.writeBlock(this.bytes);
               this.currpos = 0;
               this.remoteOs.flush();
            } else {
               byte[] new_bytes = new byte[this.currpos];
               System.arraycopy(this.bytes, 0, new_bytes, 0, new_bytes.length);
               this.writeBlock(new_bytes);
               this.currpos = 0;
               this.remoteOs.flush();
            }
         } else {
            this.remoteOs.flush();
         }

      }
   }

   public void write(byte[] b, int offset, int length) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Stream has been closed and cannot be used");
      } else if (b != null && offset >= 0 && offset <= length && length >= 0 && length <= b.length) {
         if (offset == 0 && length == b.length) {
            this.writeBlock(b);
         } else if (length != 1 && b.length != 1) {
            if (offset > 0 || length < b.length) {
               int new_len = length - offset;
               byte[] new_b = new byte[new_len];
               System.arraycopy(b, offset, new_b, 0, new_len);
               this.writeBlock(new_b);
            }
         } else {
            this.write(b[offset]);
         }

      } else {
         throw new IOException("ERROR parameters are incorrect - no data or offset or length are incorrect");
      }
   }

   public void write(byte[] b) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR stream has been closed and cannot be used");
      } else if (b == null) {
         throw new IOException("ERROR cannot accept null input");
      } else {
         try {
            this.write(b, 0, b.length);
         } catch (IOException var3) {
            throw new IOException(var3.getMessage());
         }
      }
   }

   public void close() throws IOException {
      if (!this.closed) {
         this.remoteOs.close();
         this.closed = true;
      }
   }

   public void writeBlock(byte[] b) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         this.remoteOs.writeBlock(b);
      }
   }

   public Object getStubDelegate() {
      return this.remoteOs;
   }
}
