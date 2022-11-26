package netscape.ldap.client;

import java.util.Vector;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPFilterSubString extends JDAPFilter {
   private String m_type = null;
   private Vector m_initial = new Vector();
   private Vector m_any = new Vector();
   private Vector m_final = new Vector();

   public JDAPFilterSubString(String var1) {
      this.m_type = var1;
   }

   public void addInitial(String var1) {
      this.m_initial.addElement(var1);
   }

   public void addAny(String var1) {
      this.m_any.addElement(var1);
   }

   public void addFinal(String var1) {
      this.m_final.addElement(var1);
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BEROctetString(this.m_type));
      BERSequence var2 = new BERSequence();

      int var3;
      String var4;
      BERTag var5;
      for(var3 = 0; var3 < this.m_initial.size(); ++var3) {
         var4 = (String)this.m_initial.elementAt(var3);
         if (var4 != null) {
            var5 = new BERTag(128, JDAPFilterOpers.getOctetString(var4), true);
            var2.addElement(var5);
         }
      }

      for(var3 = 0; var3 < this.m_any.size(); ++var3) {
         var4 = (String)this.m_any.elementAt(var3);
         if (var4 != null) {
            var5 = new BERTag(129, JDAPFilterOpers.getOctetString(var4), true);
            var2.addElement(var5);
         }
      }

      for(var3 = 0; var3 < this.m_final.size(); ++var3) {
         var4 = (String)this.m_final.elementAt(var3);
         if (var4 != null) {
            var5 = new BERTag(130, JDAPFilterOpers.getOctetString(var4), true);
            var2.addElement(var5);
         }
      }

      var1.addElement(var2);
      BERTag var6 = new BERTag(164, var1, true);
      return var6;
   }

   public String toString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_initial.size(); ++var2) {
         if (var2 != 0) {
            var1 = var1 + ",";
         }

         var1 = var1 + (String)this.m_initial.elementAt(var2);
      }

      String var5 = "";

      for(int var3 = 0; var3 < this.m_any.size(); ++var3) {
         if (var3 != 0) {
            var5 = var5 + ",";
         }

         var5 = var5 + (String)this.m_any.elementAt(var3);
      }

      String var6 = "";

      for(int var4 = 0; var4 < this.m_final.size(); ++var4) {
         if (var4 != 0) {
            var6 = var6 + ",";
         }

         var6 = var6 + (String)this.m_final.elementAt(var4);
      }

      return "JDAPFilterSubString {type=" + this.m_type + ", initial=" + var1 + ", any=" + var5 + ", final=" + var6 + "}";
   }
}
