package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERBoolean;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPModifyRDNRequest extends JDAPBaseDNRequest implements JDAPProtocolOp {
   protected String m_old_dn = null;
   protected String m_new_rdn = null;
   protected boolean m_delete_old_dn;
   protected String m_new_superior;

   public JDAPModifyRDNRequest(String var1, String var2, boolean var3) {
      this.m_old_dn = var1;
      this.m_new_rdn = var2;
      this.m_delete_old_dn = var3;
      this.m_new_superior = null;
   }

   public JDAPModifyRDNRequest(String var1, String var2, boolean var3, String var4) {
      this.m_old_dn = var1;
      this.m_new_rdn = var2;
      this.m_delete_old_dn = var3;
      this.m_new_superior = var4;
   }

   public int getType() {
      return 12;
   }

   public void setBaseDN(String var1) {
      this.m_old_dn = var1;
   }

   public String getBaseDN() {
      return this.m_old_dn;
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BEROctetString(this.m_old_dn));
      var1.addElement(new BEROctetString(this.m_new_rdn));
      var1.addElement(new BERBoolean(this.m_delete_old_dn));
      if (this.m_new_superior != null) {
         var1.addElement(new BERTag(128, new BEROctetString(this.m_new_superior), true));
      }

      BERTag var2 = new BERTag(108, var1, true);
      return var2;
   }

   public String toString() {
      return "ModifyRDNRequest {entry=" + this.m_old_dn + ", newrdn=" + this.m_new_rdn + ", deleteoldrdn=" + this.m_delete_old_dn + "}";
   }
}
