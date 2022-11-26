package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERTag;

public class JDAPModifyRDNResponse extends JDAPResult implements JDAPProtocolOp {
   public JDAPModifyRDNResponse(BERElement var1) throws IOException {
      super(((BERTag)var1).getValue());
   }

   public int getType() {
      return 13;
   }

   public String toString() {
      return "ModifyRDNResponse " + super.getParamString();
   }
}
