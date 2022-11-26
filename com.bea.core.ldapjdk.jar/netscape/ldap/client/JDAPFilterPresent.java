package netscape.ldap.client;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERTag;

public class JDAPFilterPresent extends JDAPFilter {
   private String m_type = null;

   public JDAPFilterPresent(String var1) {
      this.m_type = var1;
   }

   public BERElement getBERElement() {
      BEROctetString var1 = new BEROctetString(this.m_type);
      BERTag var2 = new BERTag(135, var1, true);
      return var2;
   }

   public String toString() {
      return "JDAPFilterPresent {" + this.m_type + "}";
   }
}
