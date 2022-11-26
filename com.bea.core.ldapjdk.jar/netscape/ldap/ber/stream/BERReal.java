package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BERReal extends BERElement {
   public static final float PLUS_INFINITY = Float.POSITIVE_INFINITY;
   public static final float MINUS_INFINITY = Float.NEGATIVE_INFINITY;
   private float m_value = 0.0F;

   public BERReal(float var1) {
      this.m_value = var1;
   }

   public BERReal(InputStream var1, int[] var2) throws IOException {
      int var3 = BERElement.readLengthOctets(var1, var2);
      if (var3 == 0) {
         this.m_value = 0.0F;
      } else {
         int var4 = var1.read();
         int var10002 = var2[0]++;
         if (var4 == 64) {
            this.m_value = Float.POSITIVE_INFINITY;
         } else if (var4 == 65) {
            this.m_value = Float.NEGATIVE_INFINITY;
         } else {
            if ((var4 & 128) <= 0) {
               throw new IOException("real ISO6093 not supported. ");
            }

            byte var5;
            if ((var4 & 64) > 0) {
               var5 = -1;
            } else {
               var5 = 1;
            }

            byte var6;
            if ((var4 & 32) > 0) {
               if ((var4 & 16) > 0) {
                  var6 = 0;
               } else {
                  var6 = 16;
               }
            } else if ((var4 & 16) > 0) {
               var6 = 8;
            } else {
               var6 = 2;
            }

            byte var8;
            if ((var4 & 8) > 0) {
               if ((var4 & 4) > 0) {
                  var8 = 3;
               } else {
                  var8 = 2;
               }
            } else if ((var4 & 4) > 0) {
               var8 = 1;
            } else {
               var8 = 0;
            }

            int var9;
            int var11;
            if ((var4 & 2) > 0) {
               if ((var4 & 1) > 0) {
                  var11 = var1.read();
                  var10002 = var2[0]++;
                  var9 = this.readTwosComplement(var1, var2, var11);
               } else {
                  var11 = 3;
                  var9 = this.readTwosComplement(var1, var2, var11);
               }
            } else if ((var4 & 1) > 0) {
               var11 = 2;
               var9 = this.readTwosComplement(var1, var2, var11);
            } else {
               var11 = 1;
               var9 = this.readTwosComplement(var1, var2, var11);
            }

            int var12 = var3 - 1 - var11;
            int var7 = this.readUnsignedBinary(var1, var2, var12);
            int var10 = (int)((double)(var5 * var7) * Math.pow(2.0, (double)var8));
            this.m_value = (float)var10 * (float)Math.pow((double)var6, (double)var9);
         }
      }

   }

   public void write(OutputStream var1) throws IOException {
      if (this.m_value == 0.0F) {
         var1.write(9);
         var1.write(0);
      } else if (this.m_value == Float.POSITIVE_INFINITY) {
         var1.write(9);
         var1.write(1);
         var1.write(64);
      } else if (this.m_value == Float.NEGATIVE_INFINITY) {
         var1.write(9);
         var1.write(1);
         var1.write(65);
      }

   }

   public int getType() {
      return 9;
   }

   public String toString() {
      return "Real {" + this.m_value + "}";
   }
}
