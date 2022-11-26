package netscape.ldap.ber.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import java.util.Vector;

public class BERObjectId extends BERElement {
   private int[] m_value = null;

   public BERObjectId(int[] var1) {
      this.m_value = new int[var1.length];
      System.arraycopy(var1, 0, this.m_value, 0, var1.length);
   }

   public BERObjectId(String var1) {
      StringTokenizer var2 = new StringTokenizer(var1, ".");
      this.m_value = new int[var2.countTokens()];

      for(int var3 = 0; var3 < this.m_value.length; ++var3) {
         this.m_value[var3] = Integer.parseInt(var2.nextToken());
      }

   }

   public BERObjectId(InputStream var1, int[] var2) throws IOException {
      int var3 = BERElement.readLengthOctets(var1, var2);
      var2[0] += var3;
      int[] var4 = new int[1];
      Vector var5 = new Vector(10);
      var4[0] = 0;
      int var6 = this.readSubIdentifier(var1, var4);
      var3 -= var4[0];
      if (var6 < 40) {
         var5.addElement(new Integer(0));
      } else if (var6 < 80) {
         var5.addElement(new Integer(1));
      } else {
         var5.addElement(new Integer(2));
      }

      var5.addElement(new Integer(var6 - (Integer)var5.elementAt(var5.size() - 1) * 40));

      while(var3 > 0) {
         var4[0] = 0;
         var6 = this.readSubIdentifier(var1, var4);
         var3 -= var4[0];
         var5.addElement(new Integer(var6));
      }

      this.m_value = new int[var5.size()];

      for(int var7 = 0; var7 < var5.size(); ++var7) {
         this.m_value[var7] = (Integer)var5.elementAt(var7);
      }

   }

   public void write(OutputStream var1) throws IOException {
      var1.write(6);
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      this.writeSubIdentifier(var2, this.m_value[0] * 40 + this.m_value[1]);

      for(int var3 = 2; var3 < this.m_value.length; ++var3) {
         this.writeSubIdentifier(var2, this.m_value[var3]);
      }

      byte[] var4 = var2.toByteArray();
      sendDefiniteLength(var1, var4.length);
      var1.write(var4);
   }

   private int readSubIdentifier(InputStream var1, int[] var2) throws IOException {
      int var4 = 0;

      int var3;
      do {
         var3 = var1.read();
         int var10002 = var2[0]++;
         var4 = var4 << 7 | var3 & 127;
      } while((var3 & 128) > 0);

      return var4;
   }

   private void writeSubIdentifier(OutputStream var1, int var2) throws IOException {
      ByteArrayOutputStream var3;
      for(var3 = new ByteArrayOutputStream(); var2 > 0; var2 >>= 7) {
         var3.write(var2 & 127);
      }

      byte[] var4 = var3.toByteArray();

      for(int var5 = var4.length - 1; var5 > 0; --var5) {
         var1.write(var4[var5] | 128);
      }

      var1.write(var4[0]);
   }

   public int[] getValue() {
      return this.m_value;
   }

   public int getType() {
      return 6;
   }

   public String toString() {
      if (this.m_value == null) {
         return "ObjectIdentifier (null)";
      } else {
         String var1 = "";

         for(int var2 = 0; var2 < this.m_value.length; ++var2) {
            if (var2 != 0) {
               var1 = var1 + " ";
            }

            var1 = var1 + this.m_value[var2];
         }

         return "ObjectIdentifier {" + var1 + "}";
      }
   }
}
