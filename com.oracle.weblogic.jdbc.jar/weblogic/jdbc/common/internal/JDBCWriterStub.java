package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import weblogic.rmi.extensions.server.StubDelegateInfo;

public class JDBCWriterStub extends Writer implements JDBCWriter, Serializable, StubDelegateInfo {
   JDBCWriter remoteWtr;
   boolean verbose = false;
   char[] chars = null;
   private int currpos = 0;
   boolean closed = false;
   int block_size;

   public JDBCWriterStub(JDBCWriter wtr, boolean verbose, int block_size) {
      this.remoteWtr = wtr;
      this.block_size = block_size;
      this.verbose = verbose;
   }

   public void close() throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else {
         this.remoteWtr.close();
         this.closed = true;
      }
   }

   public void flush() throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else {
         if (this.chars != null && this.currpos > 0) {
            if (this.currpos >= this.block_size) {
               this.writeBlock(this.chars);
               this.remoteWtr.flush();
               this.currpos = 0;
            } else {
               char[] new_chars = new char[this.currpos];
               System.arraycopy(this.chars, 0, new_chars, 0, new_chars.length);
               this.writeBlock(new_chars);
               this.remoteWtr.flush();
               this.currpos = 0;
            }
         } else {
            this.remoteWtr.flush();
         }

      }
   }

   public void writeBlock(char[] c) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else {
         this.remoteWtr.writeBlock(c);
      }
   }

   public void write(int c) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR OutputStream has been closed and cannot be used");
      } else {
         if (this.chars == null) {
            this.chars = new char[this.block_size];
         }

         if (this.currpos >= this.block_size) {
            this.flush();
         }

         this.chars[this.currpos++] = (char)c;
      }
   }

   public void write(String str) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else if (str == null) {
         throw new IOException("ERROR cannot accept null input");
      } else {
         char[] c = str.toCharArray();

         try {
            this.write((char[])c, 0, c.length);
         } catch (IOException var4) {
            throw new IOException(var4.getMessage());
         }
      }
   }

   public void write(String str, int offset, int length) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else if (str == null) {
         throw new IOException("ERROR cannot accept null input");
      } else if (offset >= length) {
         throw new IOException("ERROR offset (" + offset + ") cannot exceed length " + length);
      } else if (length > str.length()) {
         throw new IOException("ERROR offset (" + length + ") cannot exceed input length" + str.length());
      } else {
         char[] c = str.toCharArray();

         try {
            this.write(c, offset, length);
         } catch (IOException var6) {
            throw new IOException(var6.getMessage());
         }
      }
   }

   public void write(char[] c) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else if (c == null) {
         throw new IOException("ERROR cannot accept null input");
      } else {
         try {
            this.write((char[])c, 0, c.length);
         } catch (IOException var3) {
            throw new IOException(var3.getMessage());
         }
      }
   }

   public void write(char[] c, int off, int len) throws IOException {
      if (this.closed) {
         throw new IOException("ERROR Writer has been closed and cannot be used");
      } else if (c != null && off >= 0 && off <= len && len >= 0 && len <= c.length) {
         if (off == 0 && len == c.length) {
            this.writeBlock(c);
         } else if (len != 1 && c.length != 1) {
            if (off > 0 || len < c.length) {
               int new_len = len - off;
               char[] new_c = new char[new_len];
               System.arraycopy(c, off, new_c, 0, new_len);
               this.writeBlock(new_c);
            }
         } else {
            this.write(c[off]);
         }

      } else {
         throw new IOException("ERROR parameters are incorrect - no data or offset or length are incorrct");
      }
   }

   public Object getStubDelegate() {
      return this.remoteWtr;
   }
}
