package netscape.ldap.controls;

import java.io.IOException;
import netscape.ldap.LDAPControl;

abstract class LDAPStringControl extends LDAPControl {
   protected String m_msg = null;

   LDAPStringControl() {
   }

   public LDAPStringControl(String var1, boolean var2, byte[] var3) {
      super(var1, var2, var3);
      if (var3 != null) {
         try {
            this.m_msg = new String(var3, "UTF8");
         } catch (IOException var5) {
         }
      }

   }

   public static String parseResponse(LDAPControl[] var0, String var1) {
      String var2 = null;
      LDAPControl var3 = null;

      for(int var4 = 0; var0 != null && var4 < var0.length; ++var4) {
         if (var0[var4].getID().equals(var1)) {
            var3 = var0[var4];
            break;
         }
      }

      if (var3 != null) {
         try {
            var2 = new String(var3.getValue(), "UTF8");
         } catch (Exception var5) {
         }
      }

      return var2;
   }
}
