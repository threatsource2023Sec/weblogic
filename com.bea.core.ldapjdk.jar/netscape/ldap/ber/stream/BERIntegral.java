package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class BERIntegral extends BERElement {
   private int m_value;

   public BERIntegral(int var1) {
      this.m_value = var1;
   }

   public BERIntegral(InputStream var1, int[] var2) throws IOException {
      int var3 = BERElement.readLengthOctets(var1, var2);
      if (var3 > 0) {
         boolean var4 = false;
         int var5 = var1.read();
         int var10002 = var2[0]++;
         if ((var5 & 128) > 0) {
            var4 = true;
         }

         for(int var6 = 0; var6 < var3; ++var6) {
            if (var6 > 0) {
               var5 = var1.read();
               var10002 = var2[0]++;
            }

            if (var4) {
               this.m_value = (this.m_value << 8) + (var5 ^ 255) & 255;
            } else {
               this.m_value = (this.m_value << 8) + (var5 & 255);
            }
         }

         if (var4) {
            this.m_value = (this.m_value + 1) * -1;
         }
      }

   }

   public void write(OutputStream var1) throws IOException {
      int var2 = this.m_value;
      int var3 = 0;
      byte var5 = 1;
      byte[] var8 = new byte[10];
      byte[] var9 = new byte[10];
      if (this.m_value == 0) {
         var3 = 1;
         var8[var5] = 0;
         var9[var5] = 0;
      } else {
         if (this.m_value < 0) {
            var2 = this.m_value * -1 - 1;
         }

         do {
            if (this.m_value < 0) {
               var8[var3 + var5] = (byte)((var2 ^ 255) & 255);
            } else {
               var8[var3 + var5] = (byte)(var2 & 255);
            }

            var2 >>= 8;
            ++var3;
         } while(var2 > 0);

         for(int var7 = 0; var7 < var3; ++var7) {
            var9[var5 + var3 - 1 - var7] = var8[var5 + var7];
         }

         byte var6 = var9[var5];
         if (this.m_value > 0 && (var6 & 128) > 0) {
            var5 = 0;
            var9[var5] = 0;
            ++var3;
         }
      }

      var1.write(this.getType());
      sendDefiniteLength(var1, var3);
      var1.write(var9, var5, var3);
   }

   public int getValue() {
      return this.m_value;
   }

   public abstract int getType();

   public abstract String toString();
}
