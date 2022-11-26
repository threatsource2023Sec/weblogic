package netscape.ldap.client;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;

public class JDAPAVA {
   protected String m_type = null;
   protected String m_val = null;

   public JDAPAVA(String var1, String var2) {
      this.m_type = var1;
      this.m_val = var2;
   }

   public String getType() {
      return this.m_type;
   }

   public String getValue() {
      return this.m_val;
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BEROctetString(this.m_type));
      var1.addElement(JDAPFilterOpers.getOctetString(this.m_val));
      return var1;
   }

   public String getParamString() {
      return "{type=" + this.m_type + ", value=" + this.m_val + "}";
   }

   public String toString() {
      return "JDAPAVA " + this.getParamString();
   }
}
