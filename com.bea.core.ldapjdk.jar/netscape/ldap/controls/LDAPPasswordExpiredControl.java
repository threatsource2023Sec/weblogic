package netscape.ldap.controls;

import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPException;

public class LDAPPasswordExpiredControl extends LDAPStringControl {
   public static final String EXPIRED = "2.16.840.1.113730.3.4.4";

   public LDAPPasswordExpiredControl(String var1, boolean var2, byte[] var3) throws LDAPException {
      super("2.16.840.1.113730.3.4.4", var2, var3);
      if (!var1.equals("2.16.840.1.113730.3.4.4")) {
         throw new LDAPException("oid must be LDAPPasswordExpiredControl.PWEXPIRED", 89);
      }
   }

   /** @deprecated */
   public static String parseResponse(LDAPControl[] var0) {
      return LDAPStringControl.parseResponse(var0, "2.16.840.1.113730.3.4.4");
   }

   public String getMessage() {
      return this.m_msg;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{PasswordExpiredCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      var1.append(" msg=");
      var1.append(this.m_msg);
      var1.append("}");
      return var1.toString();
   }
}
