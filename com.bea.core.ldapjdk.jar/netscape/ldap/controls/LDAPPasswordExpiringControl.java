package netscape.ldap.controls;

import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPException;

public class LDAPPasswordExpiringControl extends LDAPStringControl {
   public static final String EXPIRING = "2.16.840.1.113730.3.4.5";

   public LDAPPasswordExpiringControl(String var1, boolean var2, byte[] var3) throws LDAPException {
      super("2.16.840.1.113730.3.4.5", var2, var3);
      if (!var1.equals("2.16.840.1.113730.3.4.5")) {
         throw new LDAPException("oid must be LDAPPasswordExpiringControl.EXPIRING", 89);
      }
   }

   public int getSecondsToExpiration() {
      return Integer.parseInt(this.m_msg);
   }

   public String getMessage() {
      return this.m_msg;
   }

   /** @deprecated */
   public static String parseResponse(LDAPControl[] var0) {
      return LDAPStringControl.parseResponse(var0, "2.16.840.1.113730.3.4.5");
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{PasswordExpiringCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      var1.append(" msg=");
      var1.append(this.m_msg);
      var1.append("}");
      return var1.toString();
   }
}
