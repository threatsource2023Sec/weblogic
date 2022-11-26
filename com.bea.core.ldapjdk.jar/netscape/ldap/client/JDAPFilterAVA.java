package netscape.ldap.client;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERTag;

public abstract class JDAPFilterAVA extends JDAPFilter {
   private int m_tag;
   private JDAPAVA m_ava = null;

   public JDAPFilterAVA(int var1, JDAPAVA var2) {
      this.m_tag = var1;
      this.m_ava = var2;
   }

   public JDAPAVA getAVA() {
      return this.m_ava;
   }

   public BERElement getBERElement() {
      BERTag var1 = new BERTag(this.m_tag, this.m_ava.getBERElement(), true);
      return var1;
   }
}
