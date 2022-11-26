package org.python.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.util.encoders.Base64;

public class PEMUtil {
   private final String _header1;
   private final String _header2;
   private final String _footer1;
   private final String _footer2;

   PEMUtil(String var1) {
      this._header1 = "-----BEGIN " + var1 + "-----";
      this._header2 = "-----BEGIN X509 " + var1 + "-----";
      this._footer1 = "-----END " + var1 + "-----";
      this._footer2 = "-----END X509 " + var1 + "-----";
   }

   private String readLine(InputStream var1) throws IOException {
      StringBuffer var2 = new StringBuffer();

      int var3;
      do {
         while((var3 = var1.read()) != 13 && var3 != 10 && var3 >= 0) {
            if (var3 != 13) {
               var2.append((char)var3);
            }
         }
      } while(var3 >= 0 && var2.length() == 0);

      return var3 < 0 ? null : var2.toString();
   }

   ASN1Sequence readPEMObject(InputStream var1) throws IOException {
      StringBuffer var2 = new StringBuffer();

      String var3;
      while((var3 = this.readLine(var1)) != null && !var3.startsWith(this._header1) && !var3.startsWith(this._header2)) {
      }

      while((var3 = this.readLine(var1)) != null && !var3.startsWith(this._footer1) && !var3.startsWith(this._footer2)) {
         var2.append(var3);
      }

      if (var2.length() != 0) {
         ASN1Primitive var4 = (new ASN1InputStream(Base64.decode(var2.toString()))).readObject();
         if (!(var4 instanceof ASN1Sequence)) {
            throw new IOException("malformed PEM data encountered");
         } else {
            return (ASN1Sequence)var4;
         }
      } else {
         return null;
      }
   }
}
