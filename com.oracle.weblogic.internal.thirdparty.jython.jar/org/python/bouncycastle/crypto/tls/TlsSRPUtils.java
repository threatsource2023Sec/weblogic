package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Hashtable;
import org.python.bouncycastle.util.BigIntegers;
import org.python.bouncycastle.util.Integers;

public class TlsSRPUtils {
   public static final Integer EXT_SRP = Integers.valueOf(12);

   public static void addSRPExtension(Hashtable var0, byte[] var1) throws IOException {
      var0.put(EXT_SRP, createSRPExtension(var1));
   }

   public static byte[] getSRPExtension(Hashtable var0) throws IOException {
      byte[] var1 = TlsUtils.getExtensionData(var0, EXT_SRP);
      return var1 == null ? null : readSRPExtension(var1);
   }

   public static byte[] createSRPExtension(byte[] var0) throws IOException {
      if (var0 == null) {
         throw new TlsFatalAlert((short)80);
      } else {
         return TlsUtils.encodeOpaque8(var0);
      }
   }

   public static byte[] readSRPExtension(byte[] var0) throws IOException {
      if (var0 == null) {
         throw new IllegalArgumentException("'extensionData' cannot be null");
      } else {
         ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
         byte[] var2 = TlsUtils.readOpaque8(var1);
         TlsProtocol.assertEmpty(var1);
         return var2;
      }
   }

   public static BigInteger readSRPParameter(InputStream var0) throws IOException {
      return new BigInteger(1, TlsUtils.readOpaque16(var0));
   }

   public static void writeSRPParameter(BigInteger var0, OutputStream var1) throws IOException {
      TlsUtils.writeOpaque16(BigIntegers.asUnsignedByteArray(var0), var1);
   }

   public static boolean isSRPCipherSuite(int var0) {
      switch (var0) {
         case 49178:
         case 49179:
         case 49180:
         case 49181:
         case 49182:
         case 49183:
         case 49184:
         case 49185:
         case 49186:
            return true;
         default:
            return false;
      }
   }
}
