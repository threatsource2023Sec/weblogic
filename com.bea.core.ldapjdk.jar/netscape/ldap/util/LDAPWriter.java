package netscape.ldap.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPEntry;

public abstract class LDAPWriter implements Serializable {
   protected PrintWriter m_pw;
   private static MimeBase64Encoder m_encoder = new MimeBase64Encoder();

   public LDAPWriter(PrintWriter var1) {
      this.m_pw = var1;
   }

   public void printEntry(LDAPEntry var1) throws IOException {
      this.printEntryStart(var1.getDN());
      LDAPAttributeSet var2 = var1.getAttributeSet();
      Enumeration var3 = var2.getAttributes();

      while(var3.hasMoreElements()) {
         LDAPAttribute var4 = (LDAPAttribute)var3.nextElement();
         this.printAttribute(var4);
      }

      this.printEntryEnd(var1.getDN());
   }

   public void printSchema(LDAPEntry var1) throws IOException {
      this.printEntry(var1);
   }

   protected abstract void printAttribute(LDAPAttribute var1);

   protected abstract void printEntryStart(String var1);

   protected abstract void printEntryEnd(String var1);

   protected String getPrintableValue(byte[] var1) {
      String var2 = "";
      ByteBuf var3 = new ByteBuf(var1, 0, var1.length);
      ByteBuf var4 = new ByteBuf();
      m_encoder.translate(var3, var4);
      int var5 = var4.length();
      if (var5 > 0) {
         var2 = new String(var4.toBytes(), 0, var5);
      }

      return var2;
   }
}
