package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERTag;

public class JDAPSearchResult extends JDAPResult implements JDAPProtocolOp {
   public JDAPSearchResult(BERElement var1) throws IOException {
      super(((BERTag)var1).getValue());
   }

   public int getType() {
      return 5;
   }

   public String toString() {
      return "SearchResult " + super.getParamString();
   }
}
