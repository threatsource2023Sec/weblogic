package netscape.ldap.client;

import netscape.ldap.ber.stream.BERBoolean;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPFilterExtensible extends JDAPFilter {
   private int m_tag = 169;
   private String m_type = null;
   private String m_value = null;

   public JDAPFilterExtensible(String var1, String var2) {
      this.m_type = var1;
      this.m_value = var2;
   }

   public BERElement getBERElement() {
      String var1 = this.m_value;
      String var2 = this.m_type;
      int var3 = var2.lastIndexOf(58);
      if (var3 == -1) {
         return null;
      } else {
         boolean var4 = false;
         String var5 = null;
         if (var2.regionMatches(true, var3 + 1, "dn", 0, 2)) {
            var4 = true;
         } else {
            var5 = var2.substring(var3 + 1);
         }

         var2 = var2.substring(0, var3);
         var3 = var2.lastIndexOf(58);
         if (var3 >= 0) {
            if (var2.regionMatches(true, var3 + 1, "dn", 0, 2)) {
               var4 = true;
            } else {
               var5 = var2.substring(var3 + 1);
            }
         }

         BERSequence var6 = new BERSequence();
         BERTag var7;
         if (var5 != null) {
            var7 = new BERTag(129, new BEROctetString(var5), true);
            var6.addElement(var7);
         }

         if (var2.length() > 0) {
            var7 = new BERTag(130, new BEROctetString(var2), true);
            var6.addElement(var7);
         }

         var7 = new BERTag(131, new BEROctetString(var1), true);
         var6.addElement(var7);
         var7 = new BERTag(132, new BERBoolean(var4), true);
         var6.addElement(var7);
         BERTag var8 = new BERTag(this.m_tag, var6, true);
         return var8;
      }
   }

   public String toString() {
      return "JDAPFilterExtensible {" + this.m_value + "}";
   }
}
