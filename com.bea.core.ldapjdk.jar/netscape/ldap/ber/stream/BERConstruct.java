package netscape.ldap.ber.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public abstract class BERConstruct extends BERElement {
   private Vector m_elements = new Vector();

   public BERConstruct() {
   }

   public BERConstruct(BERTagDecoder var1, InputStream var2, int[] var3) throws IOException {
      int var4 = BERElement.readLengthOctets(var2, var3);
      int[] var5 = new int[1];
      if (var4 == -1) {
         BERElement var6 = null;
         var5[0] = 0;
         var6 = getElement(var1, var2, var5);
         if (var6 != null) {
            this.addElement(var6);
         }

         while(true) {
            if (var6 != null) {
               continue;
            }
         }
      } else {
         for(var3[0] += var4; var4 > 0; var4 -= var5[0]) {
            var5[0] = 0;
            this.addElement(getElement(var1, var2, var5));
         }
      }

   }

   public void addElement(BERElement var1) {
      this.m_elements.addElement(var1);
   }

   public int size() {
      return this.m_elements.size();
   }

   public BERElement elementAt(int var1) {
      return (BERElement)this.m_elements.elementAt(var1);
   }

   public void write(OutputStream var1) throws IOException {
      var1.write(this.getType());
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();

      for(int var3 = 0; var3 < this.m_elements.size(); ++var3) {
         BERElement var4 = this.elementAt(var3);
         var4.write(var2);
      }

      byte[] var5 = var2.toByteArray();
      sendDefiniteLength(var1, var5.length);
      var1.write(var5);
   }

   public abstract int getType();

   public abstract String toString();
}
