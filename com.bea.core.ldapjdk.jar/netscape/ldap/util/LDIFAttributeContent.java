package netscape.ldap.util;

import java.util.Vector;
import netscape.ldap.LDAPAttribute;

public class LDIFAttributeContent extends LDIFBaseContent {
   private Vector m_attrs = new Vector();
   static final long serialVersionUID = -2912294697848028220L;

   public int getType() {
      return 0;
   }

   public void addElement(LDAPAttribute var1) {
      this.m_attrs.addElement(var1);
   }

   public LDAPAttribute[] getAttributes() {
      LDAPAttribute[] var1 = new LDAPAttribute[this.m_attrs.size()];

      for(int var2 = 0; var2 < this.m_attrs.size(); ++var2) {
         var1[var2] = (LDAPAttribute)this.m_attrs.elementAt(var2);
      }

      return var1;
   }

   public String toString() {
      String var1 = "";

      for(int var2 = 0; var2 < this.m_attrs.size(); ++var2) {
         var1 = var1 + ((LDAPAttribute)this.m_attrs.elementAt(var2)).toString();
      }

      if (this.getControls() != null) {
         var1 = var1 + this.getControlString();
      }

      return "LDIFAttributeContent {" + var1 + "}";
   }
}
