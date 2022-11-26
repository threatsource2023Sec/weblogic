package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPExtendedResponse extends JDAPResult implements JDAPProtocolOp {
   protected String m_oid = null;
   protected byte[] m_value = null;

   public JDAPExtendedResponse(BERElement var1) throws IOException {
      super(((BERTag)var1).getValue());
      BERSequence var2 = (BERSequence)((BERTag)var1).getValue();

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         try {
            BERElement var4 = var2.elementAt(var3);
            if (var4.getType() == -1) {
               BERTag var5 = (BERTag)var4;
               switch (var5.getTag() & 15) {
                  case 10:
                     BEROctetString var6 = (BEROctetString)var5.getValue();

                     try {
                        this.m_oid = new String(var6.getValue(), "UTF8");
                     } catch (Throwable var8) {
                     }
                     break;
                  case 11:
                     BEROctetString var7 = (BEROctetString)var5.getValue();
                     this.m_value = var7.getValue();
               }
            }
         } catch (ClassCastException var9) {
         }
      }

   }

   public int getType() {
      return 24;
   }

   public byte[] getValue() {
      return this.m_value;
   }

   public String getID() {
      return this.m_oid;
   }

   public String toString() {
      return "ExtendedResponse " + super.getParamString();
   }
}
