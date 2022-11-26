package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPBindResponse extends JDAPResult implements JDAPProtocolOp {
   protected byte[] m_credentials = null;

   public JDAPBindResponse(BERElement var1) throws IOException {
      super(((BERTag)var1).getValue());
      BERSequence var2 = (BERSequence)((BERTag)var1).getValue();
      if (var2.size() > 3) {
         BERElement var3 = var2.elementAt(3);
         if (var3.getType() == -1) {
            BERElement var4 = ((BERTag)var3).getValue();
            if (var4 instanceof BERSequence) {
               var4 = ((BERSequence)var4).elementAt(0);
            }

            BEROctetString var5 = (BEROctetString)var4;

            try {
               this.m_credentials = var5.getValue();
            } catch (Exception var7) {
            }
         }

      }
   }

   public byte[] getCredentials() {
      return this.m_credentials;
   }

   public int getType() {
      return 1;
   }

   public String toString() {
      return "BindResponse " + super.getParamString();
   }
}
