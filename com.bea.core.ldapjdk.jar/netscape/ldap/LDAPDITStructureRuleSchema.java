package netscape.ldap;

public class LDAPDITStructureRuleSchema extends LDAPSchemaElement {
   static final long serialVersionUID = -2823317246039655811L;
   public static final String FORM = "FORM";
   static final String[] NOVALS = new String[]{"OBSOLETE"};
   static final String[] IGNOREVALS;
   private String nameForm = null;
   private int ruleID = 0;

   protected LDAPDITStructureRuleSchema() {
   }

   public LDAPDITStructureRuleSchema(String var1, int var2, String var3, boolean var4, String var5, String[] var6) {
      super(var1, "", var3, (String[])null);
      this.nameForm = var5;
      this.ruleID = var2;
      if (var4) {
         this.setQualifier("OBSOLETE", "");
      }

      if (var6 != null && var6.length > 0) {
         this.setQualifier("SUP", var6);
      }

   }

   public LDAPDITStructureRuleSchema(String var1) {
      this.attrName = "ditStructureRules";
      this.parseValue(var1);
      Object var2 = this.properties.get("FORM");
      if (var2 != null) {
         this.nameForm = (String)var2;
      }

      try {
         this.ruleID = Integer.parseInt(this.oid);
      } catch (Exception var4) {
      }

   }

   public String[] getSuperiors() {
      return this.getQualifier("SUP");
   }

   public int getRuleID() {
      return this.ruleID;
   }

   public String getNameForm() {
      return this.nameForm;
   }

   public String getValue() {
      String var1 = "( " + this.ruleID + ' ';
      if (this.name != null) {
         var1 = var1 + "NAME '" + this.name + "' ";
      }

      if (this.description != null) {
         var1 = var1 + "DESC '" + this.description + "' ";
      }

      if (this.isObsolete()) {
         var1 = var1 + "OBSOLETE ";
      }

      var1 = var1 + "FORM " + this.nameForm + ' ';
      String var2 = this.getValue("SUP", false);
      if (var2 != null && var2.length() > 1) {
         var1 = var1 + var2 + ' ';
      }

      var2 = this.getCustomValues();
      if (var2.length() > 0) {
         var1 = var1 + var2 + ' ';
      }

      var1 = var1 + ')';
      return var1;
   }

   public String toString() {
      String var1 = "Name: " + this.name + "; ruleID: " + this.ruleID + "; ";
      var1 = var1 + "Description: " + this.description;
      if (this.isObsolete()) {
         var1 = var1 + "; OBSOLETE";
      }

      String[] var2 = this.getSuperiors();
      if (var2 != null) {
         for(int var3 = 0; var3 < var2.length; ++var3) {
            var1 = var1 + var2[var3];
            if (var3 < var2.length - 1) {
               var1 = var1 + ", ";
            }
         }
      }

      var1 = var1 + "Name form: " + this.nameForm + "; ";
      var1 = var1 + this.getQualifierString(IGNOREVALS);
      return var1;
   }

   static {
      for(int var0 = 0; var0 < NOVALS.length; ++var0) {
         novalsTable.put(NOVALS[var0], NOVALS[var0]);
      }

      IGNOREVALS = new String[]{"OBSOLETE", "FORM", "SUP"};
   }
}
