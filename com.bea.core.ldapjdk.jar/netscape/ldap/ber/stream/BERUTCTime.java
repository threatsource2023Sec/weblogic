package netscape.ldap.ber.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BERUTCTime extends BERElement {
   private String m_value = null;
   private byte[] byte_buf;

   public BERUTCTime(String var1) {
      this.m_value = var1;
   }

   public BERUTCTime(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      int var5 = BERElement.readLengthOctets(var2, var3);
      int[] var6 = new int[1];
      BERElement var7 = null;
      this.m_value = "";
      BERUTCTime var8;
      if (var5 == -1) {
         var6[0] = 0;
         var7 = getElement(var1, var2, var6);
         if (var7 != null) {
            var8 = (BERUTCTime)var7;
            this.m_value = this.m_value + var8.getValue();
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
               var8 = (BERUTCTime)var7;
               this.m_value = this.m_value + var8.getValue();
            }
         }
      }

   }

   public BERUTCTime(InputStream var1, int[] var2) throws IOException {
      int var3 = BERElement.readLengthOctets(var1, var2);
      if (var3 > 0) {
         byte[] var4 = new byte[var3];

         for(int var5 = 0; var5 < var3; ++var5) {
            var4[var5] = (byte)var1.read();
         }

         var2[0] += var3;

         try {
            this.m_value = new String(var4, "UTF8");
         } catch (Throwable var6) {
         }
      }

   }

   public void write(OutputStream var1) throws IOException {
      var1.write((byte)this.getType());
      if (this.m_value == null) {
         sendDefiniteLength(var1, 0);
      } else {
         try {
            this.byte_buf = this.m_value.getBytes("UTF8");
            sendDefiniteLength(var1, this.byte_buf.length);
         } catch (Throwable var3) {
         }

         var1.write(this.byte_buf, 0, this.byte_buf.length);
      }

   }

   public String getValue() {
      return this.m_value;
   }

   public int getType() {
      return 23;
   }

   public String toString() {
      return this.m_value == null ? "UTCTime (null)" : "UTCTime {" + this.m_value + "}";
   }
}
