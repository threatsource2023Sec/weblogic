package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERTag;

public class JDAPCompareResponse extends JDAPResult implements JDAPProtocolOp {
   public JDAPCompareResponse(BERElement var1) throws IOException {
      super(((BERTag)var1).getValue());
   }

   public int getType() {
      return 15;
   }

   public String toString() {
      return "CompareResponse " + super.getParamString();
   }
}
