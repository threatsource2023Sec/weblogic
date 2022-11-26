package netscape.ldap.controls;

import netscape.ldap.LDAPControl;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;

public class LDAPProxiedAuthControl extends LDAPControl {
   public static final String PROXIEDAUTHREQUEST = "2.16.840.1.113730.3.4.12";
   private String m_dn;

   public LDAPProxiedAuthControl(String var1, boolean var2) {
      super("2.16.840.1.113730.3.4.12", var2, (byte[])null);
      this.m_value = this.createSpecification(this.m_dn = var1);
   }

   private byte[] createSpecification(String var1) {
      BERSequence var2 = new BERSequence();
      var2.addElement(new BEROctetString(var1));
      return this.flattenBER(var2);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{ProxiedAuthCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      var1.append(" dn=");
      var1.append(this.m_dn);
      var1.append("}");
      return var1.toString();
   }
}
