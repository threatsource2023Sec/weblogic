package netscape.ldap;

import java.util.Vector;

public class LDAPMatchingRuleSchema extends LDAPAttributeSchema {
   static final long serialVersionUID = 6466155218986944131L;
   static final String[] EXPLICIT = new String[]{"OBSOLETE", "SYNTAX"};
   private String[] attributes;

   public LDAPMatchingRuleSchema(String var1, String var2, String var3, String[] var4, int var5) {
      this(var1, var2, var3, var4, "1.3.6.1.4.1.1466.115.121.1.15");
      this.syntaxElement.syntax = var5;
      LDAPSyntaxSchemaElement var10000 = this.syntaxElement;
      String var6 = LDAPSyntaxSchemaElement.internalSyntaxToString(var5);
      if (var6 != null) {
         this.syntaxElement.syntaxString = var6;
      }

      this.setQualifier("SYNTAX", this.syntaxElement.syntaxString);
   }

   public LDAPMatchingRuleSchema(String var1, String var2, String var3, String[] var4, String var5) {
      this(var1, var2, var3, var4, var5, (String[])null);
   }

   public LDAPMatchingRuleSchema(String var1, String var2, String var3, String[] var4, String var5, String[] var6) {
      this.attributes = null;
      if (var2 != null && var2.trim().length() >= 1) {
         this.name = var1;
         this.oid = var2;
         this.description = var3;
         this.attrName = "matchingrules";
         this.syntaxElement.syntax = this.syntaxElement.syntaxCheck(var5);
         this.syntaxElement.syntaxString = var5;
         this.setQualifier("SYNTAX", this.syntaxElement.syntaxString);
         this.attributes = new String[var4.length];

         for(int var7 = 0; var7 < var4.length; ++var7) {
            this.attributes[var7] = var4[var7];
         }

         if (var6 != null && var6.length > 0) {
            this.aliases = var6;
         }

      } else {
         throw new IllegalArgumentException("OID required");
      }
   }

   public LDAPMatchingRuleSchema(String var1, String var2) {
      this.attributes = null;
      this.attrName = "matchingrules";
      if (var1 != null) {
         this.parseValue(var1);
      }

      if (var2 != null) {
         this.parseValue(var2);
      }

      Vector var3 = (Vector)this.properties.get("APPLIES");
      if (var3 != null) {
         this.attributes = new String[var3.size()];
         var3.copyInto(this.attributes);
         var3.removeAllElements();
      }

      String var4 = (String)this.properties.get("SYNTAX");
      if (var4 != null) {
         this.syntaxElement.syntaxString = var4;
         this.syntaxElement.syntax = this.syntaxElement.syntaxCheck(var4);
      }

   }

   public String[] getAttributes() {
      return this.attributes;
   }

   String getValue(boolean var1) {
      String var2 = this.getValuePrefix();
      if (this.syntaxElement.syntaxString != null) {
         var2 = var2 + "SYNTAX ";
         if (var1) {
            var2 = var2 + '\'';
         }

         var2 = var2 + this.syntaxElement.syntaxString;
         if (var1) {
            var2 = var2 + '\'';
         }

         var2 = var2 + ' ';
      }

      String var3 = this.getCustomValues();
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      var2 = var2 + ')';
      return var2;
   }

   public String getValue() {
      return this.getValue(false);
   }

   public String getUseValue() {
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

   protected void update(LDAPConnection var1, int var2, String var3, String var4) throws LDAPException {
      LDAPAttribute[] var5 = new LDAPAttribute[]{new LDAPAttribute("matchingRules", this.getValue()), new LDAPAttribute("matchingRuleUse", this.getUseValue())};
      this.update(var1, var2, var5, var4);
   }

   public String toString() {
      String var1 = "Name: " + this.name + "; OID: " + this.oid + "; Type: ";
      var1 = var1 + this.syntaxElement.syntaxToString();
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
      var1 = var1 + this.getAliasString();
      return var1;
   }
}
