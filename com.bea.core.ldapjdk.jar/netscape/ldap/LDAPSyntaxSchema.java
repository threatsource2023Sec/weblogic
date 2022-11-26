package netscape.ldap;

public class LDAPSyntaxSchema extends LDAPSchemaElement {
   static final long serialVersionUID = 3590667117475688132L;
   protected LDAPSyntaxSchemaElement syntaxElement = new LDAPSyntaxSchemaElement();

   protected LDAPSyntaxSchema() {
   }

   public LDAPSyntaxSchema(String var1, String var2) {
      super("", var1, var2);
      this.attrName = "ldapSyntaxes";
      this.syntaxElement.syntax = this.syntaxElement.syntaxCheck(var1);
      this.syntaxElement.syntaxString = var1;
   }

   public LDAPSyntaxSchema(String var1) {
      this.attrName = "ldapSyntaxes";
      this.parseValue(var1);
   }

   public int getSyntax() {
      return this.syntaxElement.syntax;
   }

   public String getSyntaxString() {
      return this.syntaxElement.syntaxString;
   }

   public String getValue() {
      String var1 = this.getValuePrefix();
      String var2 = this.getCustomValues();
      if (var2.length() > 0) {
         var1 = var1 + var2 + ' ';
      }

      var1 = var1 + ')';
      return var1;
   }

   public String toString() {
      String var1 = "OID: " + this.oid;
      var1 = var1 + "; Description: " + this.description;
      var1 = var1 + this.getQualifierString((String[])null);
      return var1;
   }
}
