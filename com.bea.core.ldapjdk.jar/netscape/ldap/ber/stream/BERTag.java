package netscape.ldap.ber.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BERTag extends BERElement {
   private int m_tag = 0;
   private BERElement m_element = null;
   private boolean m_implicit = false;

   public BERTag(int var1, BERElement var2, boolean var3) {
      this.m_tag = var1;
      this.m_element = var2;
      this.m_implicit = var3;
   }

   public BERTag(BERTagDecoder var1, int var2, InputStream var3, int[] var4) throws IOException {
      this.m_tag = var2;
      boolean[] var5 = new boolean[1];
      this.m_element = var1.getElement(var1, var2, var3, var4, var5);
      this.m_implicit = var5[0];
   }

   public BERElement getValue() {
      return this.m_element;
   }

   public void setImplicit(boolean var1) {
      this.m_implicit = var1;
   }

   public void write(OutputStream var1) throws IOException {
      var1.write(this.m_tag);
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      this.m_element.write(var2);
      byte[] var3 = var2.toByteArray();
      if (this.m_implicit) {
         var1.write(var3, 1, var3.length - 1);
      } else {
         sendDefiniteLength(var1, var3.length);
         var1.write(var3);
      }

   }

   public int getType() {
      return -1;
   }

   public int getTag() {
      return this.m_tag;
   }

   public String toString() {
      String var1 = "";
      if ((this.m_tag & 192) == 0) {
         var1 = var1 + "UNIVERSAL-";
      } else if ((this.m_tag & 128 & this.m_tag & 64) > 0) {
         var1 = var1 + "PRIVATE-";
      } else if ((this.m_tag & 64) > 0) {
         var1 = var1 + "APPLICATION-";
      } else if ((this.m_tag & 128) > 0) {
         var1 = var1 + "CONTEXT-";
      }

      return "[" + var1 + (this.m_tag & 31) + "] " + this.m_element.toString();
   }
}
