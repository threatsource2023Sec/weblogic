package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;
import netscape.ldap.client.JDAPAVA;

public class JDAPCompareRequest extends JDAPBaseDNRequest implements JDAPProtocolOp {
   protected String m_dn = null;
   protected JDAPAVA m_ava = null;

   public JDAPCompareRequest(String var1, JDAPAVA var2) {
      this.m_dn = var1;
      this.m_ava = var2;
   }

   public int getType() {
      return 14;
   }

   public void setBaseDN(String var1) {
      this.m_dn = var1;
   }

   public String getBaseDN() {
      return this.m_dn;
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BEROctetString(this.m_dn));
      var1.addElement(this.m_ava.getBERElement());
      BERTag var2 = new BERTag(110, var1, true);
      return var2;
   }

   public String toString() {
      return "CompareRequest {entry=" + this.m_dn + ", ava=" + this.m_ava.toString() + "}";
   }
}
