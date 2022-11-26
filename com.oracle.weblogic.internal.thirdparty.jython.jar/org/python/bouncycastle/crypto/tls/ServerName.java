package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerName {
   protected short nameType;
   protected Object name;

   public ServerName(short var1, Object var2) {
      if (!isCorrectType(var1, var2)) {
         throw new IllegalArgumentException("'name' is not an instance of the correct type");
      } else {
         this.nameType = var1;
         this.name = var2;
      }
   }

   public short getNameType() {
      return this.nameType;
   }

   public Object getName() {
      return this.name;
   }

   public String getHostName() {
      if (!isCorrectType((short)0, this.name)) {
         throw new IllegalStateException("'name' is not a HostName string");
      } else {
         return (String)this.name;
      }
   }

   public void encode(OutputStream var1) throws IOException {
      TlsUtils.writeUint8(this.nameType, var1);
      switch (this.nameType) {
         case 0:
            byte[] var2 = ((String)this.name).getBytes("ASCII");
            if (var2.length < 1) {
               throw new TlsFatalAlert((short)80);
            }

            TlsUtils.writeOpaque16(var2, var1);
            return;
         default:
            throw new TlsFatalAlert((short)80);
      }
   }

   public static ServerName parse(InputStream var0) throws IOException {
      short var1 = TlsUtils.readUint8(var0);
      switch (var1) {
         case 0:
            byte[] var2 = TlsUtils.readOpaque16(var0);
            if (var2.length < 1) {
               throw new TlsFatalAlert((short)50);
            }

            String var3 = new String(var2, "ASCII");
            return new ServerName(var1, var3);
         default:
            throw new TlsFatalAlert((short)50);
      }
   }

   protected static boolean isCorrectType(short var0, Object var1) {
      switch (var0) {
         case 0:
            return var1 instanceof String;
         default:
            throw new IllegalArgumentException("'name' is an unsupported value");
      }
   }
}
