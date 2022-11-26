package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BEROctetString extends BERElement {
   private byte[] m_value = null;

   public BEROctetString(String var1) {
      if (var1 != null) {
         try {
            this.m_value = var1.getBytes("UTF8");
         } catch (Throwable var3) {
         }

      }
   }

   public BEROctetString(byte[] var1) {
      this.m_value = var1;
   }

   public BEROctetString(byte[] var1, int var2, int var3) {
      this.m_value = new byte[var3 - var2];

      for(int var4 = 0; var4 < var3 - var2; ++var4) {
         this.m_value[var4] = var1[var2 + var4];
      }

   }

   public BEROctetString(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      int var5 = BERElement.readLengthOctets(var2, var3);
      int[] var6 = new int[1];
      BERElement var7 = null;
      if (var5 == -1) {
         do {
            var6[0] = 0;
            var7 = getElement(var1, var2, var6);
            if (var7 != null) {
               BEROctetString var8 = (BEROctetString)var7;
               byte[] var9 = var8.getValue();
               if (this.m_value == null) {
                  this.m_value = new byte[var9.length];
                  System.arraycopy(var9, 0, this.m_value, 0, var9.length);
               } else {
                  byte[] var10 = new byte[this.m_value.length + var9.length];
                  System.arraycopy(this.m_value, 0, var10, 0, this.m_value.length);
                  System.arraycopy(var9, 0, var10, this.m_value.length, var9.length);
                  this.m_value = var10;
               }
            }
         } while(var7 != null);
      } else {
         var3[0] += var5;
         this.m_value = new byte[var5];
         boolean var11 = false;

         int var12;
         for(int var13 = 0; var13 < var5; var13 += var12) {
            var12 = var2.read(this.m_value, var13, var5 - var13);
         }
      }

   }

   public BEROctetString(InputStream var1, int[] var2) throws IOException {
      int var3 = BERElement.readLengthOctets(var1, var2);
      if (var3 > 0) {
         this.m_value = new byte[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            this.m_value[var4] = (byte)var1.read();
         }

         var2[0] += var3;
      }

   }

   public void write(OutputStream var1) throws IOException {
      var1.write(4);
      if (this.m_value == null) {
         sendDefiniteLength(var1, 0);
      } else {
         sendDefiniteLength(var1, this.m_value.length);
         var1.write(this.m_value, 0, this.m_value.length);
      }

   }

   public byte[] getValue() {
      return this.m_value;
   }

   public int getType() {
      return 4;
   }

   public String toString() {
      if (this.m_value == null) {
         return "OctetString (null)";
      } else {
         StringBuffer var1 = new StringBuffer("OctetString {");

         for(int var2 = 0; var2 < this.m_value.length; ++var2) {
            if (var2 != 0) {
               var1.append(' ');
            }

            var1.append(this.byteToHexString(this.m_value[var2]));
         }

         var1.append('}');
         return var1.toString();
      }
   }
}
