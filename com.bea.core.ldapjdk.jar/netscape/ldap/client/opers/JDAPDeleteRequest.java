package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERTag;

public class JDAPDeleteRequest extends JDAPBaseDNRequest implements JDAPProtocolOp {
   protected String m_dn = null;

   public JDAPDeleteRequest(String var1) {
      this.m_dn = var1;
   }

   public int getType() {
      return 10;
   }

   public void setBaseDN(String var1) {
      this.m_dn = var1;
   }

   public String getBaseDN() {
      return this.m_dn;
   }

   public BERElement getBERElement() {
      BEROctetString var1 = new BEROctetString(this.m_dn);
      BERTag var2 = new BERTag(74, var1, true);
      return var2;
   }

   public String toString() {
      return "DeleteRequest {entry=" + this.m_dn + "}";
   }
}
