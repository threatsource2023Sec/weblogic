package weblogic.jdbc.rowset;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.SQLException;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;

public class RowSetClob extends RowSetLob implements NClob, Serializable {
   private static final long serialVersionUID = -2055191804143683989L;
   protected char[] data;

   public RowSetClob() {
      this(new char[0]);
   }

   public RowSetClob(String s) {
      this(s.toCharArray());
   }

   public RowSetClob(char[] data) {
      this.data = data;
   }

   public RowSetClob(Clob c) throws SQLException {
      if (c.length() > 2147483647L) {
         throw new SQLException("RowSets cannot read CLOBs greater than 2147483647 bytes.");
      } else {
         int len = (int)c.length();
         this.data = new char[len];
         BufferedReader br = null;

         try {
            br = new BufferedReader(c.getCharacterStream());
            int nRead = 0;

            int r;
            for(int r = false; (r = br.read(this.data, nRead, len - nRead)) > 0; nRead += r) {
            }
         } catch (IOException var13) {
            throw new SQLException("Error reading CLOB data: " + StackTraceUtils.throwable2StackTrace(var13));
         } finally {
            if (br != null) {
               try {
                  br.close();
               } catch (Exception var12) {
               }
            }

         }

      }
   }

   public String toString() {
      return "<CLOB length: " + this.data.length + ">";
   }

   private void dumpData() {
      Debug.say("** <CLOB> length:" + this.data.length + " data: " + new String(this.data, 0, this.data.length));
   }

   char[] getData() {
      return this.data;
   }

   private void checkOffset(long offset) throws SQLException {
      if (offset < 0L || offset > (long)this.data.length) {
         throw new SQLException("Offset : " + offset + " is not valid since the data length is: " + this.data.length);
      }
   }

   public long length() {
      return (long)this.data.length;
   }

   public InputStream getAsciiStream() {
      return new StringBufferInputStream(new String(this.data));
   }

   public void truncate(long len) throws SQLException {
      if (len > (long)this.data.length) {
         throw new SQLException("truncate to length:" + len + " is larger than current size: " + this.data.length);
      } else {
         this.changeLength(len, (int)len);
      }
   }

   private void ensureLength(long l) {
      if (l > (long)this.data.length) {
         this.changeLength(l, this.data.length);
      }

   }

   private void changeLength(long newLength, int copyLength) {
      char[] oldData = this.data;
      this.data = new char[(int)newLength];
      System.arraycopy(oldData, 0, this.data, 0, copyLength);
   }

   public String getSubString(long pos, int length) throws SQLException {
      this.checkOffset(pos);
      int off = (int)pos;
      return new String(this.data, off, Math.min(length, this.data.length - off));
   }

   public Reader getCharacterStream() throws SQLException {
      return new CharArrayReader(this.data);
   }

   public int setString(long pos, String str) {
      return this.setString(pos, str, 0, str.length());
   }

   public int setString(long pos, String str, int strOffset, int strLen) {
      this.ensureLength(pos + (long)strLen);
      str.getChars(strOffset, strLen, this.data, (int)pos);
      return strLen;
   }

   public long position(String searchstr, long start) throws SQLException {
      this.checkOffset(start);
      char[] str = searchstr.toCharArray();

      for(int i = (int)start; i < this.data.length; ++i) {
         boolean found = true;

         for(int j = 0; j < str.length; ++j) {
            if (this.data[i + j] != str[j]) {
               found = false;
               break;
            }
         }

         if (found) {
            return (long)(i + 1);
         }
      }

      return -1L;
   }

   public long position(Clob c, long start) throws SQLException {
      return this.position(c.getSubString(0L, (int)c.length()), start);
   }

   public OutputStream setAsciiStream(final long lpos) throws SQLException {
      return new OutputStream() {
         int pos = (int)lpos - 1;

         public void write(int b) {
            RowSetClob.this.ensureLength((long)(this.pos + 1));
            RowSetClob.this.data[this.pos++] = (char)b;
         }

         public void write(byte[] b, int off, int len) {
            RowSetClob.this.ensureLength((long)(this.pos + len));

            while(len > 0) {
               RowSetClob.this.data[this.pos++] = (char)b[off++];
               --len;
            }

         }
      };
   }

   public Writer setCharacterStream(final long lpos) throws SQLException {
      return new Writer() {
         int pos = (int)lpos - 1;

         public void write(char[] cbuf, int off, int len) throws IOException {
            RowSetClob.this.ensureLength((long)(this.pos + len));
            System.arraycopy(cbuf, off, RowSetClob.this.data, this.pos, len);
            this.pos += len;
         }

         public void flush() throws IOException {
         }

         public void close() throws IOException {
         }

         public void write(String str, int off, int len) throws IOException {
            RowSetClob.this.ensureLength((long)(this.pos + len));
            str.getChars(off, len, RowSetClob.this.data, this.pos);
            this.pos += len;
         }
      };
   }

   protected Object update(Connection con, ResultSet rs, int i, RowSetLob.UpdateHelper helper) throws SQLException {
      return helper.update(con, rs.getClob(i), this.data);
   }

   public void free() throws SQLException {
      this.data = null;
   }

   public Reader getCharacterStream(long pos, long length) throws SQLException {
      return new CharArrayReader(this.data, (int)pos, (int)length);
   }
}
