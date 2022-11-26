package netscape.ldap;

import java.io.Serializable;

public class LDAPEntry implements Serializable {
   static final long serialVersionUID = -5563306228920012807L;
   private String dn = null;
   private LDAPAttributeSet attrSet = null;

   public LDAPEntry() {
      this.dn = null;
      this.attrSet = new LDAPAttributeSet();
   }

   public LDAPEntry(String var1) {
      this.dn = var1;
      this.attrSet = new LDAPAttributeSet();
   }

   public LDAPEntry(String var1, LDAPAttributeSet var2) {
      this.dn = var1;
      this.attrSet = var2;
   }

   public String getDN() {
      return this.dn;
   }

   void setDN(String var1) {
      this.dn = var1;
   }

   public LDAPAttributeSet getAttributeSet() {
      return this.attrSet;
   }

   public LDAPAttributeSet getAttributeSet(String var1) {
      return this.attrSet.getSubset(var1);
   }

   public LDAPAttribute getAttribute(String var1) {
      return this.attrSet.getAttribute(var1);
   }

   public LDAPAttribute getAttribute(String var1, String var2) {
      return this.attrSet.getAttribute(var1, var2);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("LDAPEntry: ");
      if (this.dn != null) {
         var1.append(this.dn);
         var1.append("; ");
      }

      if (this.attrSet != null) {
         var1.append(this.attrSet.toString());
      }

      return var1.toString();
   }
}
