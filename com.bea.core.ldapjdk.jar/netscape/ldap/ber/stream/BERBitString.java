package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;

public class BERBitString extends BERElement {
   private BitSet m_value;
   private int m_value_num_bits;

   public BERBitString(BitSet var1) {
      this.m_value = var1;
   }

   public BERBitString(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      int var5 = BERElement.readLengthOctets(var2, var3);
      int[] var6 = new int[1];
      BERElement var7 = null;
      BERBitString var8;
      BitSet var9;
      int var10;
      if (var5 == -1) {
         var6[0] = 0;
         var7 = getElement(var1, var2, var6);
         if (var7 != null) {
            var8 = (BERBitString)var7;
            var9 = new BitSet(this.m_value_num_bits + var8.getSize());

            for(var10 = 0; var10 < this.m_value_num_bits; ++var10) {
               if (this.m_value.get(var10)) {
                  var9.set(var10);
               }
            }

            for(var10 = 0; var10 < var8.getSize(); ++var10) {
               if (var8.getValue().get(var10)) {
                  var9.set(this.m_value_num_bits + var10);
               }
            }

            this.m_value = var9;
            this.m_value_num_bits += var8.getSize();
         }

         while(true) {
            if (var7 != null) {
               continue;
            }
         }
      } else {
         for(var3[0] += var5; var5 > 0; var5 -= var6[0]) {
            var6[0] = 0;
            var7 = getElement(var1, var2, var6);
            if (var7 != null) {
               var8 = (BERBitString)var7;
               var9 = new BitSet(this.m_value_num_bits + var8.getSize());

               for(var10 = 0; var10 < this.m_value_num_bits; ++var10) {
                  if (this.m_value.get(var10)) {
                     var9.set(var10);
                  }
               }

               for(var10 = 0; var10 < var8.getSize(); ++var10) {
                  if (var8.getValue().get(var10)) {
                     var9.set(this.m_value_num_bits + var10);
                  }
               }

               this.m_value = var9;
               this.m_value_num_bits += var8.getSize();
            }
         }
      }

   }

   public BERBitString(InputStream var1, int[] var2) throws IOException {
      int var4 = BERElement.readLengthOctets(var1, var2);
      int var5 = var1.read();
      int var10002 = var2[0]++;
      --var4;
      this.m_value_num_bits = (var4 - 1) * 8 + (8 - var5);
      this.m_value = new BitSet();
      int var6 = 0;

      int var3;
      int var7;
      int var8;
      for(var7 = 0; var7 < var4 - 1; ++var7) {
         var3 = var1.read();
         var8 = 128;

         for(int var9 = 0; var9 < 8; ++var9) {
            if ((var3 & var8) > 0) {
               this.m_value.set(var6);
            } else {
               this.m_value.clear(var6);
            }

            ++var6;
            var8 /= 2;
         }
      }

      var3 = var1.read();
      var7 = 128;

      for(var8 = 0; var8 < 8 - var5; ++var8) {
         if ((var3 & var7) > 0) {
            this.m_value.set(var6);
         } else {
            this.m_value.clear(var6);
         }

         ++var6;
         var7 /= 2;
      }

      var2[0] += var4;
   }

   public void write(OutputStream var1) throws IOException {
      var1.write(3);
      int var2 = this.m_value_num_bits;
      int var3 = 8 - var2 % 8;
      int var4 = var2 / 8 + 1;
      if (var3 > 0) {
         ++var4;
      }

      var1.write(var4);
      var1.write(var3);

      int var5;
      int var6;
      int var7;
      for(var5 = 0; var5 < var2 / 8; ++var5) {
         var6 = 0;
         var7 = 128;

         for(int var8 = 0; var8 < 8; ++var8) {
            if (this.m_value.get(var5 * 8 + var8)) {
               var6 += var7;
            }

            var7 /= 2;
         }

         var1.write(var6);
      }

      if (var3 > 0) {
         var5 = 0;
         var6 = 128;

         for(var7 = 0; var7 < var3; ++var7) {
            if (this.m_value.get(var2 / 8 * 8 + var7)) {
               var5 += var6;
            }

            var6 /= 2;
         }

         var1.write(var5);
      }

   }

   public BitSet getValue() {
      return this.m_value;
   }

   public int getSize() {
      return this.m_value_num_bits;
   }

   public int getType() {
      return 3;
   }

   public String toString() {
      String var1 = "";
      int var3 = this.m_value_num_bits;

      int var2;
      int var4;
      int var5;
      for(var4 = 0; var4 < var3 / 8; ++var4) {
         var2 = 0;
         var5 = 128;

         for(int var6 = 0; var6 < 8; ++var6) {
            if (this.m_value.get(var4 * 8 + var6)) {
               var2 += var5;
            }

            var5 /= 2;
         }

         var1 = var1 + " " + (byte)var2;
      }

      var4 = 128;
      var2 = 0;

      for(var5 = 0; var5 < var3 - var3 / 8; ++var5) {
         if (this.m_value.get(var3 / 8 * 8 + var5)) {
            var2 += var4;
         }

         var4 /= 2;
      }

      var1 = var1 + " " + (byte)var2;
      return "Bitstring {" + var1 + " }";
   }
}
