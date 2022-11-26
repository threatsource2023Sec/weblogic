package netscape.ldap.client;

import java.io.IOException;
import java.util.Vector;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERSet;
import netscape.ldap.ber.stream.BERTag;

public abstract class JDAPFilterSet extends JDAPFilter {
   private int m_tag;
   private Vector m_set = new Vector();

   public JDAPFilterSet(int var1) {
      this.m_tag = var1;
   }

   public void addElement(JDAPFilter var1) {
      this.m_set.addElement(var1);
   }

   public BERElement getBERElement() {
      try {
         BERSet var1 = new BERSet();

         for(int var2 = 0; var2 < this.m_set.size(); ++var2) {
            JDAPFilter var3 = (JDAPFilter)this.m_set.elementAt(var2);
            var1.addElement(var3.getBERElement());
         }

         BERTag var5 = new BERTag(this.m_tag, var1, true);
         return var5;
      } catch (IOException var4) {
         return null;
      }
   }

   public String getParamString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_set.size(); ++var2) {
         if (var2 != 0) {
            var1 = var1 + ",";
         }

         JDAPFilter var3 = (JDAPFilter)this.m_set.elementAt(var2);
         var1 = var1 + var3.toString();
      }

      return var1;
   }
}
