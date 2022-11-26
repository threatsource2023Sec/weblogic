package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERTag;

public class JDAPDeleteResponse extends JDAPResult implements JDAPProtocolOp {
   public JDAPDeleteResponse(BERElement var1) throws IOException {
      super(((BERTag)var1).getValue());
   }

   public int getType() {
      return 11;
   }

   public String toString() {
      return "DeleteResponse " + super.getParamString();
   }
}
