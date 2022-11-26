package weblogic.jdbc.rowset;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import weblogic.utils.StackTraceUtils;

public final class RowSetBlob extends RowSetLob implements Blob, Serializable {
   private static final long serialVersionUID = -2526507456741346804L;
   private byte[] data;

   public RowSetBlob() {
      this(new byte[0]);
   }

   public RowSetBlob(byte[] data) {
      this.data = data;
   }

   public RowSetBlob(Blob b) throws SQLException {
      if (b.length() > 2147483647L) {
         throw new SQLException("RowSets cannot read BLOBs greater than 2147483647 bytes.");
      } else {
         int len = (int)b.length();
         this.data = new byte[len];
         InputStream in = null;

         try {
            in = new BufferedInputStream(b.getBinaryStream());
            int nRead = 0;

            int r;
            for(int r = false; (r = in.read(this.data, nRead, len - nRead)) > 0; nRead += r) {
            }
         } catch (IOException var13) {
            throw new SQLException("Error reading BLOB data: " + StackTraceUtils.throwable2StackTrace(var13));
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (Exception var12) {
               }
            }

         }

      }
   }

   public String toString() {
      return "<BLOB length: " + this.data.length + ">";
   }

   public long length() {
      return (long)this.data.length;
   }

   public byte[] getBytes(long pos, int length) throws SQLException {
      if (pos >= 0L && pos <= (long)this.data.length) {
         if (length < 0) {
            throw new SQLException("length must be positive");
         } else {
            byte[] b = new byte[length];
            System.arraycopy(this.data, (int)pos, b, 0, length);
            return b;
         }
      } else {
         throw new SQLException("Position: " + pos + " must be >=0 and less than Blob.length()");
      }
   }

   public InputStream getBinaryStream() {
      return new ByteArrayInputStream(this.data);
   }

   public long position(byte[] pattern, long start) throws SQLException {
      if (start >= 0L && start < (long)this.data.length) {
         for(int i = (int)start; i < this.data.length; ++i) {
            boolean found = true;

            for(int j = 0; j < pattern.length; ++j) {
               if (this.data[i + j] != pattern[j]) {
                  found = false;
                  break;
               }
            }

            if (found) {
               return (long)(i + 1);
            }
         }

         return -1L;
      } else {
         throw new SQLException("start must be >= 0 and < Blob.length()");
      }
   }

   public long position(Blob pattern, long start) throws SQLException {
      if (pattern.length() > 2147483647L) {
         throw new SQLException("RowSets cannot read BLOBs greater than 2147483647 bytes.");
      } else {
         return this.position(pattern.getBytes(0L, (int)pattern.length()), start);
      }
   }

   public int setBytes(long pos, byte[] b) {
      return this.setBytes(pos, b, 0, b.length);
   }

   public int setBytes(long pos, byte[] b, int offset, int len) {
      this.ensureLength(pos + (long)len);
      System.arraycopy(b, offset, this.data, (int)pos, len);
      return len;
   }

   public OutputStream setBinaryStream(final long lpos) throws SQLException {
      return new OutputStream() {
         int pos = (int)lpos - 1;

         public void write(int b) {
            RowSetBlob.this.ensureLength((long)(this.pos + 1));
            RowSetBlob.this.data[this.pos++] = (byte)b;
         }

         public void write(byte[] b, int off, int len) {
            this.pos += RowSetBlob.this.setBytes((long)this.pos, b, off, len);
         }
      };
   }

   public void truncate(long len) throws SQLException {
      if (len > (long)this.data.length) {
         throw new SQLException("truncate to length: " + len + " is larger than current size: " + this.data.length);
      } else {
         this.changeLength(len, (int)len);
      }
   }

   byte[] getData() {
      return this.data;
   }

   private void ensureLength(long l) {
      if (l > (long)this.data.length) {
         this.changeLength(l, this.data.length);
      }

   }

   private void changeLength(long newLength, int copyLength) {
      byte[] oldData = this.data;
      this.data = new byte[(int)newLength];
      System.arraycopy(oldData, 0, this.data, 0, copyLength);
   }

   protected Object update(Connection con, ResultSet rs, int i, RowSetLob.UpdateHelper helper) throws SQLException {
      return helper.update(con, rs.getBlob(i), this.data);
   }

   public void free() throws SQLException {
      this.data = null;
   }

   public InputStream getBinaryStream(long pos, long length) throws SQLException {
      return new ByteArrayInputStream(this.data, (int)pos, (int)length);
   }
}
