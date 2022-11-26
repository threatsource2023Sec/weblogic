package netscape.ldap.util;

import java.util.Vector;
import netscape.ldap.LDAPModification;

public class LDIFModifyContent extends LDIFBaseContent {
   private Vector m_mods = new Vector();
   static final long serialVersionUID = -710573832339780084L;

   public int getType() {
      return 3;
   }

   public void addElement(LDAPModification var1) {
      this.m_mods.addElement(var1);
   }

   public LDAPModification[] getModifications() {
      LDAPModification[] var1 = new LDAPModification[this.m_mods.size()];

      for(int var2 = 0; var2 < this.m_mods.size(); ++var2) {
         var1[var2] = (LDAPModification)this.m_mods.elementAt(var2);
      }

      return var1;
   }

   public String toString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_mods.size(); ++var2) {
         var1 = var1 + ((LDAPModification)this.m_mods.elementAt(var2)).toString();
      }

      if (this.getControls() != null) {
         var1 = var1 + this.getControlString();
      }

      return "LDIFModifyContent {" + var1 + "}";
   }
}
