package netscape.ldap.client.opers;

import netscape.ldap.LDAPModification;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPModifyRequest extends JDAPBaseDNRequest implements JDAPProtocolOp {
   protected String m_dn = null;
   protected LDAPModification[] m_mod = null;

   public JDAPModifyRequest(String var1, LDAPModification[] var2) {
      this.m_dn = var1;
      this.m_mod = var2;
   }

   public int getType() {
      return 6;
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

      for(int var3 = 0; var3 < this.m_mod.length; ++var3) {
         var2.addElement(this.m_mod[var3].getBERElement());
      }

      var1.addElement(var2);
      BERTag var4 = new BERTag(102, var1, true);
      return var4;
   }

   public String toString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_mod.length; ++var2) {
         if (var2 != 0) {
            var1 = var1 + "+";
         }

         var1 = var1 + this.m_mod[var2].toString();
      }

      return "ModifyRequest {object=" + this.m_dn + ", modification=" + var1 + "}";
   }
}
