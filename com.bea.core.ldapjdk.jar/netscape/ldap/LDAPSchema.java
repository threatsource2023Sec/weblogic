package netscape.ldap;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

public class LDAPSchema implements Serializable {
   static final long serialVersionUID = -3911737419783579398L;
   private Hashtable objectClasses = new Hashtable();
   private Hashtable attributes = new Hashtable();
   private Hashtable matchingRules = new Hashtable();
   private Hashtable syntaxes = new Hashtable();
   private Hashtable structureRulesByName = new Hashtable();
   private Hashtable structureRulesById = new Hashtable();
   private Hashtable contentRules = new Hashtable();
   private Hashtable nameForms = new Hashtable();

   public LDAPSchema() {
   }

   public LDAPSchema(LDAPEntry var1) {
      this.initialize(var1);
   }

   public void addObjectClass(LDAPObjectClassSchema var1) {
      this.objectClasses.put(var1.getName().toLowerCase(), var1);
   }

   public void addAttribute(LDAPAttributeSchema var1) {
      this.attributes.put(var1.getName().toLowerCase(), var1);
   }

   public void addMatchingRule(LDAPMatchingRuleSchema var1) {
      this.matchingRules.put(var1.getName().toLowerCase(), var1);
   }

   public void addSyntax(LDAPSyntaxSchema var1) {
      String var2 = var1.getName().toLowerCase();
      if (var2.length() < 1) {
         var2 = var1.getOID();
      }

      this.syntaxes.put(var2, var1);
   }

   public void addDITStructureRule(LDAPDITStructureRuleSchema var1) {
      String var2 = var1.getName().toLowerCase();
      this.structureRulesByName.put(var2, var1);
      this.structureRulesById.put(new Integer(var1.getRuleID()), var1);
   }

   public void addDITContentRule(LDAPDITContentRuleSchema var1) {
      String var2 = var1.getName().toLowerCase();
      this.contentRules.put(var2, var1);
   }

   public void addNameForm(LDAPNameFormSchema var1) {
      String var2 = var1.getName().toLowerCase();
      this.nameForms.put(var2, var1);
   }

   public Enumeration getObjectClasses() {
      return this.objectClasses.elements();
   }

   public Enumeration getAttributes() {
      return this.attributes.elements();
   }

   public Enumeration getMatchingRules() {
      return this.matchingRules.elements();
   }

   public Enumeration getSyntaxes() {
      return this.syntaxes.elements();
   }

   public Enumeration getDITStructureRules() {
      return this.structureRulesByName.elements();
   }

   public Enumeration getDITContentRules() {
      return this.contentRules.elements();
   }

   public Enumeration getNameForms() {
      return this.nameForms.elements();
   }

   public LDAPObjectClassSchema getObjectClass(String var1) {
      return (LDAPObjectClassSchema)this.objectClasses.get(var1.toLowerCase());
   }

   public LDAPAttributeSchema getAttribute(String var1) {
      return (LDAPAttributeSchema)this.attributes.get(var1.toLowerCase());
   }

   public LDAPMatchingRuleSchema getMatchingRule(String var1) {
      return (LDAPMatchingRuleSchema)this.matchingRules.get(var1.toLowerCase());
   }

   public LDAPSyntaxSchema getSyntax(String var1) {
      return (LDAPSyntaxSchema)this.syntaxes.get(var1.toLowerCase());
   }

   public LDAPDITStructureRuleSchema getDITStructureRule(String var1) {
      return (LDAPDITStructureRuleSchema)this.structureRulesByName.get(var1.toLowerCase());
   }

   public LDAPDITStructureRuleSchema getDITStructureRule(int var1) {
      return (LDAPDITStructureRuleSchema)this.structureRulesById.get(new Integer(var1));
   }

   public LDAPDITContentRuleSchema getDITContentRule(String var1) {
      return (LDAPDITContentRuleSchema)this.contentRules.get(var1.toLowerCase());
   }

   public LDAPNameFormSchema getNameForm(String var1) {
      return (LDAPNameFormSchema)this.nameForms.get(var1.toLowerCase());
   }

   public Enumeration getObjectClassNames() {
      return this.objectClasses.keys();
   }

   public Enumeration getAttributeNames() {
      return this.attributes.keys();
   }

   public Enumeration getMatchingRuleNames() {
      return this.matchingRules.keys();
   }

   public Enumeration getSyntaxNames() {
      return this.syntaxes.keys();
   }

   public Enumeration getDITStructureRuleNames() {
      return this.structureRulesByName.keys();
   }

   public Enumeration getDITContentRuleNames() {
      return this.contentRules.keys();
   }

   public Enumeration getNameFormNames() {
      return this.nameForms.keys();
   }

   public void fetchSchema(LDAPConnection var1, String var2) throws LDAPException {
      String var3 = getSchemaDN(var1, var2);
      LDAPEntry var5 = readSchema(var1, var3);
      this.initialize(var5);
   }

   protected void initialize(LDAPEntry var1) {
      LDAPAttribute var2 = var1.getAttribute("objectclasses");
      Enumeration var3;
      if (var2 != null) {
         var3 = var2.getStringValues();

         while(var3.hasMoreElements()) {
            LDAPObjectClassSchema var4 = new LDAPObjectClassSchema((String)var3.nextElement());
            this.addObjectClass(var4);
         }
      }

      var2 = var1.getAttribute("attributetypes");
      if (var2 != null) {
         var3 = var2.getStringValues();

         while(var3.hasMoreElements()) {
            LDAPAttributeSchema var8 = new LDAPAttributeSchema((String)var3.nextElement());
            this.addAttribute(var8);
         }
      }

      var2 = var1.getAttribute("ldapsyntaxes");
      if (var2 != null) {
         var3 = var2.getStringValues();

         while(var3.hasMoreElements()) {
            LDAPSyntaxSchema var9 = new LDAPSyntaxSchema((String)var3.nextElement());
            this.addSyntax(var9);
         }
      }

      var2 = var1.getAttribute("ldapditstructurerules");
      if (var2 != null) {
         var3 = var2.getStringValues();

         while(var3.hasMoreElements()) {
            LDAPDITStructureRuleSchema var10 = new LDAPDITStructureRuleSchema((String)var3.nextElement());
            this.addDITStructureRule(var10);
         }
      }

      var2 = var1.getAttribute("ldapditcontentrules");
      if (var2 != null) {
         var3 = var2.getStringValues();

         while(var3.hasMoreElements()) {
            LDAPDITContentRuleSchema var11 = new LDAPDITContentRuleSchema((String)var3.nextElement());
            this.addDITContentRule(var11);
         }
      }

      var2 = var1.getAttribute("nameforms");
      if (var2 != null) {
         var3 = var2.getStringValues();

         while(var3.hasMoreElements()) {
            LDAPNameFormSchema var12 = new LDAPNameFormSchema((String)var3.nextElement());
            this.addNameForm(var12);
         }
      }

      Hashtable var13 = new Hashtable();
      var2 = var1.getAttribute("matchingruleuse");
      String var5;
      LDAPMatchingRuleSchema var6;
      if (var2 != null) {
         var3 = var2.getStringValues();

         while(var3.hasMoreElements()) {
            var5 = (String)var3.nextElement();
            var6 = new LDAPMatchingRuleSchema((String)null, var5);
            var13.put(var6.getOID(), var5);
         }
      }

      var2 = var1.getAttribute("matchingrules");
      if (var2 != null) {
         for(var3 = var2.getStringValues(); var3.hasMoreElements(); this.addMatchingRule(var6)) {
            var5 = (String)var3.nextElement();
            var6 = new LDAPMatchingRuleSchema(var5, (String)null);
            String var7 = (String)var13.get(var6.getOID());
            if (var7 != null) {
               var6 = new LDAPMatchingRuleSchema(var5, var7);
            }
         }
      }

   }

   public void fetchSchema(LDAPConnection var1) throws LDAPException {
      this.fetchSchema(var1, "");
   }

   static boolean isAttributeSyntaxStandardsCompliant(LDAPConnection var0) throws LDAPException {
      String var1 = (String)var0.getProperty("com.netscape.ldap.schema.quoting");
      if (var1 != null) {
         return var1.equalsIgnoreCase("standard");
      } else {
         boolean var2 = true;
         String var3 = getSchemaDN(var0, "");
         String[] var4 = new String[]{"attributetypes"};
         LDAPEntry var5 = var0.read(var3, var4);
         LDAPAttribute var6 = var5.getAttribute("attributetypes");
         if (var6 != null) {
            Enumeration var7 = var6.getStringValues();
            if (var7.hasMoreElements()) {
               var2 = !isSyntaxQuoted((String)var7.nextElement());
            }
         }

         var0.setProperty("com.netscape.ldap.schema.quoting", var2 ? "standard" : "NetscapeBug");
         return var2;
      }
   }

   static boolean isSyntaxQuoted(String var0) {
      int var1 = var0.indexOf(" SYNTAX ");
      if (var1 >= 0) {
         var1 += 8;
         int var2 = var0.length() - var1;
         char[] var3 = new char[var2];
         var0.getChars(var1, var0.length(), var3, 0);

         for(var1 = 0; var1 < var3.length && var3[var1] == ' '; ++var1) {
         }

         if (var1 < var3.length) {
            return var3[var1] == '\'';
         }
      }

      return false;
   }

   public String toString() {
      String var1 = "Object classes:\n";

      Enumeration var2;
      for(var2 = this.getObjectClasses(); var2.hasMoreElements(); var1 = var1 + '\n') {
         var1 = var1 + var2.nextElement().toString();
      }

      var1 = var1 + "Attributes:\n";

      for(var2 = this.getAttributes(); var2.hasMoreElements(); var1 = var1 + '\n') {
         var1 = var1 + var2.nextElement().toString();
      }

      var1 = var1 + "Matching rules:\n";

      for(var2 = this.getMatchingRules(); var2.hasMoreElements(); var1 = var1 + '\n') {
         var1 = var1 + var2.nextElement().toString();
      }

      var1 = var1 + "Syntaxes:\n";

      for(var2 = this.getSyntaxes(); var2.hasMoreElements(); var1 = var1 + '\n') {
         var1 = var1 + var2.nextElement().toString();
      }

      return var1;
   }

   static String getSchemaDN(LDAPConnection var0, String var1) throws LDAPException {
      if (var0 != null && var0.isConnected()) {
         String[] var2 = new String[]{"subschemasubentry"};
         LDAPEntry var3 = var0.read(var1, var2);
         if (var3 == null) {
            throw new LDAPException("", 32);
         } else {
            LDAPAttribute var4 = var3.getAttribute(var2[0]);
            String var5 = "cn=schema";
            if (var4 != null) {
               Enumeration var6 = var4.getStringValues();
               if (var6.hasMoreElements()) {
                  var5 = (String)var6.nextElement();
               }
            }

            return var5;
         }
      } else {
         throw new LDAPException("No connection", 80);
      }
   }

   private static LDAPEntry readSchema(LDAPConnection var0, String var1, String[] var2) throws LDAPException {
      LDAPSearchResults var3 = var0.search(var1, 0, "objectclass=subschema", var2, false);
      if (!var3.hasMoreElements()) {
         throw new LDAPException("Cannot read schema", 50);
      } else {
         return var3.next();
      }
   }

   private static LDAPEntry readSchema(LDAPConnection var0, String var1) throws LDAPException {
      return readSchema(var0, var1, new String[]{"*", "ldapSyntaxes", "matchingRules", "attributeTypes", "objectClasses"});
   }

   private static void printEnum(Enumeration var0) {
      while(var0.hasMoreElements()) {
         LDAPSchemaElement var1 = (LDAPSchemaElement)var0.nextElement();
         System.out.println("  " + var1);
      }

   }

   public static void main(String[] var0) {
      if (var0.length < 2) {
         System.err.println("Usage: netscape.ldap.LDAPSchema HOST PORT");
         System.exit(1);
      }

      int var1 = Integer.parseInt(var0[1]);
      LDAPConnection var2 = new LDAPConnection();

      try {
         var2.connect(var0[0], var1);
         LDAPSchema var3 = new LDAPSchema();
         var3.fetchSchema(var2);
         var2.disconnect();
         System.out.println("Object classes: ");
         printEnum(var3.getObjectClasses());
         System.out.println("\nAttributes: ");
         printEnum(var3.getAttributes());
         System.out.println("\nMatching rules: ");
         printEnum(var3.getMatchingRules());
         System.out.println("\nSyntaxes: ");
         printEnum(var3.getSyntaxes());
         System.exit(0);
      } catch (LDAPException var4) {
         System.err.println(var4);
      }

   }
}
