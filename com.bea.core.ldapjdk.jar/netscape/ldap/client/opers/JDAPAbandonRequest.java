package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BERTag;

public class JDAPAbandonRequest implements JDAPProtocolOp {
   protected int m_msgid;

   public JDAPAbandonRequest(int var1) {
      this.m_msgid = var1;
   }

   public int getType() {
      return 16;
   }

   public BERElement getBERElement() {
      BERInteger var1 = new BERInteger(this.m_msgid);
      BERTag var2 = new BERTag(80, var1, true);
      return var2;
   }

   public String toString() {
      return "AbandonRequest {msgid=" + this.m_msgid + "}";
   }
}
