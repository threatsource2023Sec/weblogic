package netscape.ldap.util;

import netscape.ldap.LDAPAttribute;

public class LDIFAddContent extends LDIFBaseContent {
   private LDAPAttribute[] m_attrs = null;
   static final long serialVersionUID = -665548826721177756L;

   public LDIFAddContent(LDAPAttribute[] var1) {
      this.m_attrs = var1;
   }

   public int getType() {
      return 1;
   }

   public LDAPAttribute[] getAttributes() {
      return this.m_attrs;
   }

   public String toString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_attrs.length; ++var2) {
         var1 = var1 + this.m_attrs[var2].toString();
      }

      if (this.getControls() != null) {
         var1 = var1 + this.getControlString();
      }

      return "LDIFAddContent {" + var1 + "}";
   }
}
