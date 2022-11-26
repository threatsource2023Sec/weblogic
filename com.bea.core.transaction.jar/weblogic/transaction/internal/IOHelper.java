package weblogic.transaction.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class IOHelper {
   public static void writeCompressedInt(ObjectOutput oo, int i) throws IOException {
      if (i >= 0) {
         if (i < 254) {
            oo.writeByte(i);
            return;
         }

         if (i <= 65535) {
            oo.writeByte(254);
            oo.writeByte(i >> 8);
            oo.writeByte(i & 255);
            return;
         }
      }

      oo.writeByte(255);
      oo.writeInt(i);
   }

   public static int readCompressedInt(ObjectInput oi) throws IOException {
      int res = oi.readUnsignedByte();
      if (res < 254) {
         return res;
      } else if (res == 255) {
         return oi.readInt();
      } else {
         res = oi.readUnsignedByte() << 8;
         return res + oi.readUnsignedByte();
      }
   }
}
