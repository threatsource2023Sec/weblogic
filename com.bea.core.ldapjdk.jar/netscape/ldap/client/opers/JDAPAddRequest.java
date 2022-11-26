package netscape.ldap.client.opers;

import netscape.ldap.LDAPAttribute;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPAddRequest extends JDAPBaseDNRequest implements JDAPProtocolOp {
   protected String m_dn = null;
   protected LDAPAttribute[] m_attrs = null;

   public JDAPAddRequest(String var1, LDAPAttribute[] var2) {
      this.m_dn = var1;
      this.m_attrs = var2;
   }

   public int getType() {
      return 8;
   }

   public void setBaseDN(String var1) {
      this.m_dn = var1;
   }

   public String getBaseDN() {
      return this.m_dn;
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BEROctetString(this.m_dn));
      BERSequence var2 = new BERSequence();

      for(int var3 = 0; var3 < this.m_attrs.length; ++var3) {
         var2.addElement(this.m_attrs[var3].getBERElement());
      }

      var1.addElement(var2);
      BERTag var4 = new BERTag(104, var1, true);
      return var4;
   }

   public String getParamString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_attrs.length; ++var2) {
         if (var2 != 0) {
            var1 = var1 + " ";
         }

         var1 = var1 + this.m_attrs[var2].toString();
      }

      return "{entry='" + this.m_dn + "', attrs='" + var1 + "'}";
   }

   public String toString() {
      return "AddRequest " + this.getParamString();
   }
}
