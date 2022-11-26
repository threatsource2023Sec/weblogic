package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPExtendedRequest implements JDAPProtocolOp {
   protected String m_oid = null;
   protected byte[] m_value = null;

   public JDAPExtendedRequest(String var1, byte[] var2) {
      this.m_oid = var1;
      this.m_value = var2;
   }

   public int getType() {
      return 23;
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BERTag(128, new BEROctetString(this.m_oid), true));
      if (this.m_value != null) {
         var1.addElement(new BERTag(129, new BEROctetString(this.m_value, 0, this.m_value.length), true));
      }

      BERTag var2 = new BERTag(119, var1, true);
      return var2;
   }

   public String getParamString() {
      String var1 = "";
      if (this.m_value != null) {
         try {
            var1 = new String(this.m_value, "UTF8");
         } catch (Throwable var3) {
         }
      }

      return "{OID='" + this.m_oid + "', value='" + var1 + "'}";
   }

   public String toString() {
      return "ExtendedRequest " + this.getParamString();
   }
}
