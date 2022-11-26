package org.python.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.util.encoders.Base64;

class PEMUtil {
   private final String _header1;
   private final String _header2;
   private final String _header3;
   private final String _footer1;
   private final String _footer2;
   private final String _footer3;

   PEMUtil(String var1) {
      this._header1 = "-----BEGIN " + var1 + "-----";
      this._header2 = "-----BEGIN X509 " + var1 + "-----";
      this._header3 = "-----BEGIN PKCS7-----";
      this._footer1 = "-----END " + var1 + "-----";
      this._footer2 = "-----END X509 " + var1 + "-----";
      this._footer3 = "-----END PKCS7-----";
   }

   private String readLine(InputStream var1) throws IOException {
      StringBuffer var2 = new StringBuffer();

      int var3;
      do {
         while((var3 = var1.read()) != 13 && var3 != 10 && var3 >= 0) {
            var2.append((char)var3);
         }
      } while(var3 >= 0 && var2.length() == 0);

      if (var3 < 0) {
         return null;
      } else {
         if (var3 == 13) {
            var1.mark(1);
            if ((var3 = var1.read()) == 10) {
               var1.mark(1);
            }

            if (var3 > 0) {
               var1.reset();
            }
         }

         return var2.toString();
      }
   }

   ASN1Sequence readPEMObject(InputStream var1) throws IOException {
      StringBuffer var2 = new StringBuffer();

      String var3;
      while((var3 = this.readLine(var1)) != null && !var3.startsWith(this._header1) && !var3.startsWith(this._header2) && !var3.startsWith(this._header3)) {
      }

      while((var3 = this.readLine(var1)) != null && !var3.startsWith(this._footer1) && !var3.startsWith(this._footer2) && !var3.startsWith(this._footer3)) {
         var2.append(var3);
      }

      if (var2.length() != 0) {
         try {
            return ASN1Sequence.getInstance(Base64.decode(var2.toString()));
         } catch (Exception var5) {
            throw new IOException("malformed PEM data encountered");
         }
      } else {
         return null;
      }
   }
}
