package netscape.ldap;

import java.util.Enumeration;
import java.util.Vector;

public class LDAPDITContentRuleSchema extends LDAPSchemaElement {
   static final long serialVersionUID = -8588488481097270056L;
   public static final String AUX = "AUX";
   public static final String MUST = "MUST";
   public static final String MAY = "MAY";
   public static final String NOT = "NOT";
   static final String[] NOVALS = new String[]{"OBSOLETE"};
   static final String[] IGNOREVALS;
   private Vector must = new Vector();
   private Vector may = new Vector();
   private Vector aux = new Vector();
   private Vector not = new Vector();

   protected LDAPDITContentRuleSchema() {
   }

   public LDAPDITContentRuleSchema(String var1, String var2, String var3, boolean var4, String[] var5, String[] var6, String[] var7, String[] var8) {
      super(var1, var2, var3, (String[])null);
      int var9;
      if (var6 != null) {
         for(var9 = 0; var9 < var6.length; ++var9) {
            this.must.addElement(var6[var9]);
         }
      }

      if (var7 != null) {
         for(var9 = 0; var9 < var7.length; ++var9) {
            this.may.addElement(var7[var9]);
         }
      }

      if (var5 != null) {
         for(var9 = 0; var9 < var5.length; ++var9) {
            this.aux.addElement(var5[var9]);
         }
      }

      if (var8 != null) {
         for(var9 = 0; var9 < var8.length; ++var9) {
            this.not.addElement(var8[var9]);
         }
      }

      if (var4) {
         this.setQualifier("OBSOLETE", "");
      }

   }

   public LDAPDITContentRuleSchema(String var1) {
      this.attrName = "ditContentRules";
      this.parseValue(var1);
      Object var2 = this.properties.get("MAY");
      if (var2 != null) {
         if (var2 instanceof Vector) {
            this.may = (Vector)var2;
         } else {
            this.may.addElement(var2);
         }
      }

      var2 = this.properties.get("MUST");
      if (var2 != null) {
         if (var2 instanceof Vector) {
            this.must = (Vector)var2;
         } else {
            this.must.addElement(var2);
         }
      }

      var2 = this.properties.get("NOT");
      if (var2 != null) {
         if (var2 instanceof Vector) {
            this.not = (Vector)var2;
         } else {
            this.not.addElement(var2);
         }
      }

      var2 = this.properties.get("AUX");
      if (var2 != null) {
         if (var2 instanceof Vector) {
            this.aux = (Vector)var2;
         } else {
            this.aux.addElement(var2);
         }
      }

   }

   public String[] getRequiredAttributes() {
      String[] var1 = new String[this.must.size()];
      this.must.copyInto(var1);
      return var1;
   }

   public String[] getOptionalAttributes() {
      String[] var1 = new String[this.may.size()];
      this.may.copyInto(var1);
      return var1;
   }

   public String[] getPrecludedAttributes() {
      String[] var1 = new String[this.not.size()];
      this.not.copyInto(var1);
      return var1;
   }

   public String[] getAuxiliaryClasses() {
      String[] var1 = new String[this.aux.size()];
      this.aux.copyInto(var1);
      return var1;
   }

   public String getValue() {
      String var1 = this.getValuePrefix();
      String var2 = this.getOptionalValues(NOVALS);
      if (var2.length() > 0) {
         var1 = var1 + var2 + ' ';
      }

      if (this.aux.size() > 0) {
         var1 = var1 + "AUX " + this.vectorToList(this.aux);
         var1 = var1 + ' ';
      }

      if (this.must.size() > 0) {
         var1 = var1 + "MUST " + this.vectorToList(this.must);
         var1 = var1 + ' ';
      }

      if (this.may.size() > 0) {
         var1 = var1 + "MAY " + this.vectorToList(this.may);
         var1 = var1 + ' ';
      }

      if (this.not.size() > 0) {
         var1 = var1 + "NOT " + this.vectorToList(this.not);
         var1 = var1 + ' ';
      }

      var2 = this.getCustomValues();
      if (var2.length() > 0) {
         var1 = var1 + var2 + ' ';
      }

      var1 = var1 + ')';
      return var1;
   }

   protected String vectorToList(Vector var1) {
      String var2 = "( ";

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         var2 = var2 + (String)var1.elementAt(var3) + ' ';
         if (var3 < var1.size() - 1) {
            var2 = var2 + "$ ";
         }
      }

      var2 = var2 + ')';
      return var2;
   }

   public String toString() {
      String var1 = "Name: " + this.name + "; OID: " + this.oid;
      var1 = var1 + "; Description: " + this.description + "; Required: ";
      int var2 = 0;

      Enumeration var3;
      for(var3 = this.must.elements(); var3.hasMoreElements(); var1 = var1 + (String)var3.nextElement()) {
         if (var2 > 0) {
            var1 = var1 + ", ";
         }

         ++var2;
      }

      var1 = var1 + "; Optional: ";
      var3 = this.may.elements();

      for(var2 = 0; var3.hasMoreElements(); var1 = var1 + (String)var3.nextElement()) {
         if (var2 > 0) {
            var1 = var1 + ", ";
         }

         ++var2;
      }

      var1 = var1 + "; Auxiliary: ";
      var3 = this.aux.elements();

      for(var2 = 0; var3.hasMoreElements(); var1 = var1 + (String)var3.nextElement()) {
         if (var2 > 0) {
            var1 = var1 + ", ";
         }

         ++var2;
      }

      var1 = var1 + "; Precluded: ";
      var3 = this.not.elements();

      for(var2 = 0; var3.hasMoreElements(); var1 = var1 + (String)var3.nextElement()) {
         if (var2 > 0) {
            var1 = var1 + ", ";
         }

         ++var2;
      }

      if (this.isObsolete()) {
         var1 = var1 + "; OBSOLETE";
      }

      var1 = var1 + this.getQualifierString(IGNOREVALS);
      return var1;
   }

   static {
      for(int var0 = 0; var0 < NOVALS.length; ++var0) {
         novalsTable.put(NOVALS[var0], NOVALS[var0]);
      }

      IGNOREVALS = new String[]{"OBSOLETE", "AUX", "MUST", "MAY", "NOT"};
   }
}
