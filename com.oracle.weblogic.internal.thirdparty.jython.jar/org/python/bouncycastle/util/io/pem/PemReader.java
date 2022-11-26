package org.python.bouncycastle.util.io.pem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.python.bouncycastle.util.encoders.Base64;

public class PemReader extends BufferedReader {
   private static final String BEGIN = "-----BEGIN ";
   private static final String END = "-----END ";

   public PemReader(Reader var1) {
      super(var1);
   }

   public PemObject readPemObject() throws IOException {
      String var1;
      for(var1 = this.readLine(); var1 != null && !var1.startsWith("-----BEGIN "); var1 = this.readLine()) {
      }

      if (var1 != null) {
         var1 = var1.substring("-----BEGIN ".length());
         int var2 = var1.indexOf(45);
         String var3 = var1.substring(0, var2);
         if (var2 > 0) {
            return this.loadObject(var3);
         }
      }

      return null;
   }

   private PemObject loadObject(String var1) throws IOException {
      String var2 = "-----END " + var1;
      StringBuffer var3 = new StringBuffer();
      ArrayList var4 = new ArrayList();

      String var5;
      while((var5 = this.readLine()) != null) {
         if (var5.indexOf(":") >= 0) {
            int var6 = var5.indexOf(58);
            String var7 = var5.substring(0, var6);
            String var8 = var5.substring(var6 + 1).trim();
            var4.add(new PemHeader(var7, var8));
         } else {
            if (var5.indexOf(var2) != -1) {
               break;
            }

            var3.append(var5.trim());
         }
      }

      if (var5 == null) {
         throw new IOException(var2 + " not found");
      } else {
         return new PemObject(var1, var4, Base64.decode(var3.toString()));
      }
   }
}
