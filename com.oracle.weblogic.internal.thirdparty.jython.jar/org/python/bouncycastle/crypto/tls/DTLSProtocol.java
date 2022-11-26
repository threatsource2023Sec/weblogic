package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Vector;
import org.python.bouncycastle.util.Arrays;

public abstract class DTLSProtocol {
   protected final SecureRandom secureRandom;

   protected DTLSProtocol(SecureRandom var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("'secureRandom' cannot be null");
      } else {
         this.secureRandom = var1;
      }
   }

   protected void processFinished(byte[] var1, byte[] var2) throws IOException {
      ByteArrayInputStream var3 = new ByteArrayInputStream(var1);
      byte[] var4 = TlsUtils.readFully(var2.length, var3);
      TlsProtocol.assertEmpty(var3);
      if (!Arrays.constantTimeAreEqual(var2, var4)) {
         throw new TlsFatalAlert((short)40);
      }
   }

   protected static void applyMaxFragmentLengthExtension(DTLSRecordLayer var0, short var1) throws IOException {
      if (var1 >= 0) {
         if (!MaxFragmentLength.isValid(var1)) {
            throw new TlsFatalAlert((short)80);
         }

         int var2 = 1 << 8 + var1;
         var0.setPlaintextLimit(var2);
      }

   }

   protected static short evaluateMaxFragmentLengthExtension(boolean var0, Hashtable var1, Hashtable var2, short var3) throws IOException {
      short var4 = TlsExtensionsUtils.getMaxFragmentLengthExtension(var2);
      if (var4 < 0 || MaxFragmentLength.isValid(var4) && (var0 || var4 == TlsExtensionsUtils.getMaxFragmentLengthExtension(var1))) {
         return var4;
      } else {
         throw new TlsFatalAlert(var3);
      }
   }

   protected static byte[] generateCertificate(Certificate var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      var0.encode(var1);
      return var1.toByteArray();
   }

   protected static byte[] generateSupplementalData(Vector var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      TlsProtocol.writeSupplementalData(var1, var0);
      return var1.toByteArray();
   }

   protected static void validateSelectedCipherSuite(int var0, short var1) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
