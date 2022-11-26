package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERNull;
import netscape.ldap.ber.stream.BERTag;

public class JDAPUnbindRequest implements JDAPProtocolOp {
   public int getType() {
      return 2;
   }

   public BERElement getBERElement() {
      BERNull var1 = new BERNull();
      BERTag var2 = new BERTag(66, var1, true);
      return var2;
   }

   public String toString() {
      return "UnbindRequest {}";
   }
}
