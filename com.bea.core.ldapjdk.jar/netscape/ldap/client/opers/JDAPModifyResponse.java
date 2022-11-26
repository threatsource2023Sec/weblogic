package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERTag;

public class JDAPModifyResponse extends JDAPResult implements JDAPProtocolOp {
   public JDAPModifyResponse(BERElement var1) throws IOException {
      super(((BERTag)var1).getValue());
   }

   public int getType() {
      return 7;
   }

   public String toString() {
      return "ModifyResponse " + super.getParamString();
   }
}
