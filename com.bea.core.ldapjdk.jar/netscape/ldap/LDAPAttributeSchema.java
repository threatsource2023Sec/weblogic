package netscape.ldap;

public class LDAPAttributeSchema extends LDAPSchemaElement {
   static final long serialVersionUID = 2482595821879862595L;
   public static final String EQUALITY = "EQUALITY";
   public static final String ORDERING = "ORDERING";
   public static final String SUBSTR = "SUBSTR";
   public static final String SINGLE = "SINGLE-VALUE";
   public static final String COLLECTIVE = "COLLECTIVE";
   public static final String NO_USER_MODIFICATION = "NO-USER-MODIFICATION";
   public static final String USAGE = "USAGE";
   static String[] NOVALS = new String[]{"SINGLE-VALUE", "COLLECTIVE", "NO-USER-MODIFICATION"};
   static final String[] MATCHING_RULES;
   static final String[] IGNOREVALS;
   protected LDAPSyntaxSchemaElement syntaxElement;

   protected LDAPAttributeSchema() {
      this.syntaxElement = new LDAPSyntaxSchemaElement();
   }

   public LDAPAttributeSchema(String var1, String var2, String var3, int var4, boolean var5) {
      this(var1, var2, var3, "1.3.6.1.4.1.1466.115.121.1.15", var5);
      this.syntaxElement.syntax = var4;
      LDAPSyntaxSchemaElement var10000 = this.syntaxElement;
      String var6 = LDAPSyntaxSchemaElement.internalSyntaxToString(var4);
      if (var6 != null) {
         this.syntaxElement.syntaxString = var6;
      }

      this.setQualifier("SYNTAX", this.getSyntaxString());
   }

   public LDAPAttributeSchema(String var1, String var2, String var3, String var4, boolean var5) {
      this(var1, var2, var3, var4, var5, (String)null, (String[])null);
   }

   public LDAPAttributeSchema(String var1, String var2, String var3, String var4, boolean var5, String var6, String[] var7) {
      super(var1, var2, var3, var7);
      this.syntaxElement = new LDAPSyntaxSchemaElement();
      this.attrName = "attributetypes";
      this.syntaxElement.syntax = this.syntaxElement.syntaxCheck(var4);
      this.syntaxElement.syntaxString = var4;
      this.setQualifier("SYNTAX", this.syntaxElement.syntaxString);
      if (var5) {
         this.setQualifier("SINGLE-VALUE", "");
      }

      if (var6 != null && var6.length() > 0) {
         this.setQualifier("SUP", var6);
      }

   }

   public LDAPAttributeSchema(String var1) {
      this.syntaxElement = new LDAPSyntaxSchemaElement();
      this.attrName = "attributetypes";
      this.parseValue(var1);
      String var2 = (String)this.properties.get("SYNTAX");
      if (var2 != null) {
         this.syntaxElement.syntaxString = var2;
         this.syntaxElement.syntax = this.syntaxElement.syntaxCheck(var2);
      }

   }

   public boolean isSingleValued() {
      return this.properties != null ? this.properties.containsKey("SINGLE-VALUE") : false;
   }

   public String getSuperior() {
      String[] var1 = this.getQualifier("SUP");
      return var1 != null && var1.length > 0 ? var1[0] : null;
   }

   public int getSyntax() {
      return this.syntaxElement.syntax;
   }

   public String getSyntaxString() {
      return this.syntaxElement.syntaxString;
   }

   String getValue(boolean var1) {
      String var2 = this.getValuePrefix();
      String var3 = this.getValue("SUP", false);
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      var3 = this.getOptionalValues(MATCHING_RULES);
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      var3 = this.getValue("SYNTAX", var1);
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      if (this.isSingleValued()) {
         var2 = var2 + "SINGLE-VALUE ";
      }

      var3 = this.getOptionalValues(NOVALS);
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      var3 = this.getOptionalValues(new String[]{"USAGE"});
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      var3 = this.getCustomValues();
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      var2 = var2 + ')';
      return var2;
   }

   public String toString() {
      String var1 = "Name: " + this.name + "; OID: " + this.oid + "; Type: ";
      var1 = var1 + this.syntaxElement.syntaxToString();
      var1 = var1 + "; Description: " + this.description + "; ";
      if (this.isSingleValued()) {
         var1 = var1 + "single-valued";
      } else {
         var1 = var1 + "multi-valued";
      }

      var1 = var1 + this.getQualifierString(IGNOREVALS);
      var1 = var1 + this.getAliasString();
      return var1;
   }

   static {
      for(int var0 = 0; var0 < NOVALS.length; ++var0) {
         novalsTable.put(NOVALS[var0], NOVALS[var0]);
      }

      MATCHING_RULES = new String[]{"EQUALITY", "ORDERING", "SUBSTR"};
      IGNOREVALS = new String[]{"SINGLE-VALUE", "OBSOLETE", "SUP", "SINGLE-VALUE", "COLLECTIVE", "NO-USER-MODIFICATION", "SYNTAX"};
   }
}
