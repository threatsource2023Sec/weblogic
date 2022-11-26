package weblogic.store.io.file.checksum;

import java.nio.ByteBuffer;
import java.util.zip.Checksum;

public interface Checksummer extends Checksum {
   void update(ByteBuffer var1, int var2, int var3);

   long calculateChecksum(ByteBuffer var1);
}
