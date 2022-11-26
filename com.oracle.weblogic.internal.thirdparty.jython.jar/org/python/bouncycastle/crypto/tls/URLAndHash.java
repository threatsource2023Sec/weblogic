package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.python.bouncycastle.util.Strings;

public class URLAndHash {
   protected String url;
   protected byte[] sha1Hash;

   public URLAndHash(String var1, byte[] var2) {
      if (var1 != null && var1.length() >= 1 && var1.length() < 65536) {
         if (var2 != null && var2.length != 20) {
            throw new IllegalArgumentException("'sha1Hash' must have length == 20, if present");
         } else {
            this.url = var1;
            this.sha1Hash = var2;
         }
      } else {
         throw new IllegalArgumentException("'url' must have length from 1 to (2^16 - 1)");
      }
   }

   public String getURL() {
      return this.url;
   }

   public byte[] getSHA1Hash() {
      return this.sha1Hash;
   }

   public void encode(OutputStream var1) throws IOException {
      byte[] var2 = Strings.toByteArray(this.url);
      TlsUtils.writeOpaque16(var2, var1);
      if (this.sha1Hash == null) {
         TlsUtils.writeUint8((int)0, var1);
      } else {
         TlsUtils.writeUint8((int)1, var1);
         var1.write(this.sha1Hash);
      }

   }

   public static URLAndHash parse(TlsContext var0, InputStream var1) throws IOException {
      byte[] var2 = TlsUtils.readOpaque16(var1);
      if (var2.length < 1) {
         throw new TlsFatalAlert((short)47);
      } else {
         String var3 = Strings.fromByteArray(var2);
         byte[] var4 = null;
         short var5 = TlsUtils.readUint8(var1);
         switch (var5) {
            case 0:
               if (TlsUtils.isTLSv12(var0)) {
                  throw new TlsFatalAlert((short)47);
               }
               break;
            case 1:
               var4 = TlsUtils.readFully(20, var1);
               break;
            default:
               throw new TlsFatalAlert((short)47);
         }

         return new URLAndHash(var3, var4);
      }
   }
}
