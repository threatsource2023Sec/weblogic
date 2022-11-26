package weblogic.store.io.file.checksum;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

public class FaFChecksummer extends Adler32 implements Checksummer {
   private Method updateMethod;

   public FaFChecksummer() throws ChecksummerInstantiationException {
      try {
         this.updateMethod = Class.forName("com.oracle.util.Checksums").getDeclaredMethod("update", Adler32.class, ByteBuffer.class);
      } catch (Exception var2) {
         throw new ChecksummerInstantiationException(var2);
      }
   }

   public void update(Adler32 adler32, ByteBuffer buf) {
      try {
         this.updateMethod.invoke(this, adler32, buf);
      } catch (Exception var4) {
         throw new RuntimeException(var4.getMessage());
      }
   }

   public long calculateChecksum(ByteBuffer buffer) {
      this.reset();
      this.update(buffer, buffer.position(), buffer.remaining());
      return this.getValue();
   }

   public void update(ByteBuffer buffer, int offset, int len) {
      assert offset >= 0 && len >= 0 && offset + len <= buffer.limit();

      int pos = buffer.position();
      int lim = buffer.limit();
      buffer.position(offset);
      buffer.limit(len + offset);

      try {
         this.updateMethod.invoke((Object)null, this, buffer);
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }

      buffer.limit(lim);
      buffer.position(pos);
   }
}
