package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERTag;

public class JDAPAddResponse extends JDAPResult implements JDAPProtocolOp {
   public JDAPAddResponse(BERElement var1) throws IOException {
      super(((BERTag)var1).getValue());
   }

   public int getType() {
      return 9;
   }

   public String toString() {
      return "AddResponse " + super.getParamString();
   }
}
