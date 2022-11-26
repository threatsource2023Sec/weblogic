package netscape.ldap.client;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERTag;

public class JDAPFilterNot extends JDAPFilter {
   private JDAPFilter m_filter = null;

   public JDAPFilterNot(JDAPFilter var1) {
      this.m_filter = var1;
   }

   public BERElement getBERElement() {
      BERTag var1 = new BERTag(162, this.m_filter.getBERElement(), false);
      return var1;
   }

   public String toString() {
      return "JDAPFilterNot {" + this.m_filter.toString() + "}";
   }
}
