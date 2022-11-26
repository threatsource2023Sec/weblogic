package netscape.ldap;

import java.util.Vector;

public class LDAPMatchingRuleUseSchema extends LDAPAttributeSchema {
   static final String[] EXPLICIT = new String[]{"OBSOLETE"};
   private String[] attributes = null;

   public LDAPMatchingRuleUseSchema(String var1, String var2, String var3, String[] var4) {
      if (var2 != null && var2.trim().length() >= 1) {
         this.name = var1;
         this.oid = var2;
         this.description = var3;
         this.attrName = "matchingruleuse";
         this.attributes = new String[var4.length];

         for(int var5 = 0; var5 < var4.length; ++var5) {
            this.attributes[var5] = var4[var5];
         }

      } else {
         throw new IllegalArgumentException("OID required");
      }
   }

   public LDAPMatchingRuleUseSchema(String var1) {
      this.attrName = "matchingruleuse";
      this.parseValue(var1);
      Vector var2 = (Vector)this.properties.get("APPLIES");
      if (var2 != null) {
         this.attributes = new String[var2.size()];
         var2.copyInto(this.attributes);
         var2.removeAllElements();
      }

   }

   public String[] getApplicableAttributes() {
      return this.attributes;
   }

   public String getValue() {
      String var1 = this.getValuePrefix();
      if (this.attributes != null && this.attributes.length > 0) {
         var1 = var1 + "APPLIES ( ";

         for(int var2 = 0; var2 < this.attributes.length; ++var2) {
            if (var2 > 0) {
               var1 = var1 + " $ ";
            }

            var1 = var1 + this.attributes[var2];
         }

         var1 = var1 + " ) ";
      }

      var1 = var1 + ')';
      return var1;
   }

   public String toString() {
      String var1 = "Name: " + this.name + "; OID: " + this.oid;
      var1 = var1 + "; Description: " + this.description;
      if (this.attributes != null) {
         var1 = var1 + "; Applies to: ";

         for(int var2 = 0; var2 < this.attributes.length; ++var2) {
            if (var2 > 0) {
               var1 = var1 + ", ";
            }

            var1 = var1 + this.attributes[var2];
         }
      }

      var1 = var1 + this.getQualifierString(EXPLICIT);
      return var1;
   }
}
