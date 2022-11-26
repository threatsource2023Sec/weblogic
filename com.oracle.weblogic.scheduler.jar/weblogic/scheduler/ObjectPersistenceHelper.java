package weblogic.scheduler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import weblogic.store.io.jdbc.JDBCHelper;

final class ObjectPersistenceHelper implements Blob, Serializable {
   private byte[] data;
   private static final int DEFAULT_BUF_SIZE = 1000;

   ObjectPersistenceHelper() {
      this.data = new byte[0];
   }

   ObjectPersistenceHelper(Serializable obj) throws IOException {
      this.data = getBytes(obj);
   }

   static byte[] getBytes(Serializable obj) throws IOException {
      ByteArrayOutputStream baos = null;
      ObjectOutputStream oos = null;

      byte[] var3;
      try {
         baos = new ByteArrayOutputStream(1000);
         oos = new ObjectOutputStream(baos);
         oos.writeObject(obj);
         oos.flush();
         var3 = baos.toByteArray();
      } finally {
         if (oos != null) {
            oos.close();
         }

         if (baos != null) {
            baos.close();
         }

      }

      return var3;
   }

   static Blob getBlob(Serializable obj) throws IOException {
      return new ObjectPersistenceHelper(obj);
   }

   static void writeToBlob(Blob blob, Serializable obj) throws SQLException, IOException {
      OutputStream os = null;

      try {
         os = blob.setBinaryStream(1L);
         byte[] b = getBytes(obj);
         os.write(b);
         os.flush();
         os.close();
         os = null;
      } finally {
         try {
            if (os != null) {
               os.close();
            }
         } catch (IOException var9) {
         }

      }

   }

   static Object getObject(Blob blob) throws SQLException, IOException {
      assert blob != null;

      return getObject(blob.getBytes(1L, (int)blob.length()));
   }

   static Object getObject(byte[] bytes) throws SQLException, IOException {
      ObjectInputStream ois = null;
      ByteArrayInputStream bais = null;

      Object var3;
      try {
         bais = new ByteArrayInputStream(bytes);
         ois = new SchedulerObjectInputStream(bais);
         var3 = ois.readObject();
      } catch (ClassNotFoundException var7) {
         throw new IOException(var7.getMessage());
      } finally {
         if (ois != null) {
            ois.close();
         }

         if (bais != null) {
            bais.close();
         }

      }

      return var3;
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

   public OutputStream setBinaryStream(final long lpos) {
      return new OutputStream() {
         int pos = (int)lpos - 1;

         public void write(int b) {
            ObjectPersistenceHelper.this.ensureLength((long)(this.pos + 1));
            ObjectPersistenceHelper.this.data[this.pos++] = (byte)b;
         }

         public void write(byte[] b, int off, int len) {
            this.pos += ObjectPersistenceHelper.this.setBytes((long)this.pos, b, off, len);
         }
      };
   }

   public void free() throws SQLException {
      throw new SQLException("Java SE 6.0 method free is not supported");
   }

   public InputStream getBinaryStream(long pos, long length) throws SQLException {
      throw new SQLException("Java SE 6.0 method getBinaryStream is not supported");
   }

   public void truncate(long len) throws SQLException {
      if (len > (long)this.data.length) {
         throw new SQLException("truncate to length: " + len + " is larger than current size: " + this.data.length);
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
      byte[] oldData = this.data;
      this.data = new byte[(int)newLength];
      System.arraycopy(oldData, 0, this.data, 0, copyLength);
   }

   static boolean isOracleBlobRecord(Connection con, ResultSet results, int columnNumber) throws SQLException {
      return JDBCHelper.isOracleBlobColumn(JDBCHelper.getDBMSType(con.getMetaData(), (String[])null), results, columnNumber);
   }

   static boolean mustSelectForInsert(Connection con) throws SQLException {
      return JDBCHelper.mustSelectForUpdateToInsertBinary(JDBCHelper.getDBMSType(con.getMetaData(), (String[])null), con.getMetaData());
   }

   static Object getObject(Connection con, ResultSet rs, int columnNumber) throws SQLException, IOException {
      return isOracleBlobRecord(con, rs, columnNumber) ? getObject(rs.getBlob(columnNumber)) : getObject(rs.getBytes(columnNumber));
   }

   private static class SchedulerObjectInputStream extends ObjectInputStream {
      private SchedulerObjectInputStream(InputStream is) throws IOException {
         super(is);
      }

      protected Class resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
         ClassLoader loader = Thread.currentThread().getContextClassLoader();
         return Class.forName(v.getName(), false, loader);
      }

      // $FF: synthetic method
      SchedulerObjectInputStream(InputStream x0, Object x1) throws IOException {
         this(x0);
      }
   }
}
