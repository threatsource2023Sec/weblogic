package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import org.python.bouncycastle.util.Integers;

public class TlsSRTPUtils {
   public static final Integer EXT_use_srtp = Integers.valueOf(14);

   public static void addUseSRTPExtension(Hashtable var0, UseSRTPData var1) throws IOException {
      var0.put(EXT_use_srtp, createUseSRTPExtension(var1));
   }

   public static UseSRTPData getUseSRTPExtension(Hashtable var0) throws IOException {
      byte[] var1 = TlsUtils.getExtensionData(var0, EXT_use_srtp);
      return var1 == null ? null : readUseSRTPExtension(var1);
   }

   public static byte[] createUseSRTPExtension(UseSRTPData var0) throws IOException {
      if (var0 == null) {
         throw new IllegalArgumentException("'useSRTPData' cannot be null");
      } else {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         TlsUtils.writeUint16ArrayWithUint16Length(var0.getProtectionProfiles(), var1);
         TlsUtils.writeOpaque8(var0.getMki(), var1);
         return var1.toByteArray();
      }
   }

   public static UseSRTPData readUseSRTPExtension(byte[] var0) throws IOException {
      if (var0 == null) {
         throw new IllegalArgumentException("'extensionData' cannot be null");
      } else {
         ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
         int var2 = TlsUtils.readUint16(var1);
         if (var2 >= 2 && (var2 & 1) == 0) {
            int[] var3 = TlsUtils.readUint16Array(var2 / 2, var1);
            byte[] var4 = TlsUtils.readOpaque8(var1);
            TlsProtocol.assertEmpty(var1);
            return new UseSRTPData(var3, var4);
         } else {
            throw new TlsFatalAlert((short)50);
         }
      }
   }
}
