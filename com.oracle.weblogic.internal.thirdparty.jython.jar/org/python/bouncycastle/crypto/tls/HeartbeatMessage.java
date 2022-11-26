package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.io.Streams;

public class HeartbeatMessage {
   protected short type;
   protected byte[] payload;
   protected int paddingLength;

   public HeartbeatMessage(short var1, byte[] var2, int var3) {
      if (!HeartbeatMessageType.isValid(var1)) {
         throw new IllegalArgumentException("'type' is not a valid HeartbeatMessageType value");
      } else if (var2 != null && var2.length < 65536) {
         if (var3 < 16) {
            throw new IllegalArgumentException("'paddingLength' must be at least 16");
         } else {
            this.type = var1;
            this.payload = var2;
            this.paddingLength = var3;
         }
      } else {
         throw new IllegalArgumentException("'payload' must have length < 2^16");
      }
   }

   public void encode(TlsContext var1, OutputStream var2) throws IOException {
      TlsUtils.writeUint8(this.type, var2);
      TlsUtils.checkUint16(this.payload.length);
      TlsUtils.writeUint16(this.payload.length, var2);
      var2.write(this.payload);
      byte[] var3 = new byte[this.paddingLength];
      var1.getNonceRandomGenerator().nextBytes(var3);
      var2.write(var3);
   }

   public static HeartbeatMessage parse(InputStream var0) throws IOException {
      short var1 = TlsUtils.readUint8(var0);
      if (!HeartbeatMessageType.isValid(var1)) {
         throw new TlsFatalAlert((short)47);
      } else {
         int var2 = TlsUtils.readUint16(var0);
         PayloadBuffer var3 = new PayloadBuffer();
         Streams.pipeAll(var0, var3);
         byte[] var4 = var3.toTruncatedByteArray(var2);
         if (var4 == null) {
            return null;
         } else {
            int var5 = var3.size() - var4.length;
            return new HeartbeatMessage(var1, var4, var5);
         }
      }
   }

   static class PayloadBuffer extends ByteArrayOutputStream {
      byte[] toTruncatedByteArray(int var1) {
         int var2 = var1 + 16;
         return this.count < var2 ? null : Arrays.copyOf(this.buf, var1);
      }
   }
}
