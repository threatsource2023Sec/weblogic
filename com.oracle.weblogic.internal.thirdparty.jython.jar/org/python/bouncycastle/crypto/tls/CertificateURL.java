package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public class CertificateURL {
   protected short type;
   protected Vector urlAndHashList;

   public CertificateURL(short var1, Vector var2) {
      if (!CertChainType.isValid(var1)) {
         throw new IllegalArgumentException("'type' is not a valid CertChainType value");
      } else if (var2 != null && !var2.isEmpty()) {
         this.type = var1;
         this.urlAndHashList = var2;
      } else {
         throw new IllegalArgumentException("'urlAndHashList' must have length > 0");
      }
   }

   public short getType() {
      return this.type;
   }

   public Vector getURLAndHashList() {
      return this.urlAndHashList;
   }

   public void encode(OutputStream var1) throws IOException {
      TlsUtils.writeUint8(this.type, var1);
      ListBuffer16 var2 = new ListBuffer16();

      for(int var3 = 0; var3 < this.urlAndHashList.size(); ++var3) {
         URLAndHash var4 = (URLAndHash)this.urlAndHashList.elementAt(var3);
         var4.encode(var2);
      }

      var2.encodeTo(var1);
   }

   public static CertificateURL parse(TlsContext var0, InputStream var1) throws IOException {
      short var2 = TlsUtils.readUint8(var1);
      if (!CertChainType.isValid(var2)) {
         throw new TlsFatalAlert((short)50);
      } else {
         int var3 = TlsUtils.readUint16(var1);
         if (var3 < 1) {
            throw new TlsFatalAlert((short)50);
         } else {
            byte[] var4 = TlsUtils.readFully(var3, var1);
            ByteArrayInputStream var5 = new ByteArrayInputStream(var4);
            Vector var6 = new Vector();

            while(var5.available() > 0) {
               URLAndHash var7 = URLAndHash.parse(var0, var5);
               var6.addElement(var7);
            }

            return new CertificateURL(var2, var6);
         }
      }
   }

   class ListBuffer16 extends ByteArrayOutputStream {
      ListBuffer16() throws IOException {
         TlsUtils.writeUint16(0, this);
      }

      void encodeTo(OutputStream var1) throws IOException {
         int var2 = this.count - 2;
         TlsUtils.checkUint16(var2);
         TlsUtils.writeUint16(var2, this.buf, 0);
         var1.write(this.buf, 0, this.count);
         this.buf = null;
      }
   }
}
