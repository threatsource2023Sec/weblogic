package netscape.ldap.util;

import java.io.Serializable;
import netscape.ldap.LDAPControl;

public abstract class LDIFBaseContent implements LDIFContent, Serializable {
   static final long serialVersionUID = -8542611537447295949L;
   private LDAPControl[] m_controls = null;

   public LDAPControl[] getControls() {
      return this.m_controls;
   }

   public void setControls(LDAPControl[] var1) {
      this.m_controls = var1;
   }

   protected String getControlString() {
      String var1 = "";
      if (this.getControls() != null) {
         var1 = var1 + ' ';
         LDAPControl[] var2 = this.getControls();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var1 = var1 + var2[var4].toString();
            if (var4 < var3 - 1) {
               var1 = var1 + ' ';
            }
         }
      }

      return var1;
   }
}
