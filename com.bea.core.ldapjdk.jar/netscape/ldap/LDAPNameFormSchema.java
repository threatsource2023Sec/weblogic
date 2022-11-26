package netscape.ldap;

import java.util.Vector;

public class LDAPNameFormSchema extends LDAPSchemaElement {
   static final long serialVersionUID = 1665316286199590403L;
   private Vector must = new Vector();
   private Vector may = new Vector();
   private String objectClass = null;
   static final String[] NOVALS = new String[]{"OBSOLETE"};
   static final String[] IGNOREVALS;

   public LDAPNameFormSchema(String var1, String var2, String var3, boolean var4, String var5, String[] var6, String[] var7) {
      super(var1, var2, var3, (String[])null);
      this.attrName = "nameforms";
      if (var4) {
         this.setQualifier("OBSOLETE", "");
      }

      this.objectClass = var5;
      int var8;
      if (var6 != null) {
         for(var8 = 0; var8 < var6.length; ++var8) {
            this.must.addElement(var6[var8]);
         }
      }

      if (var7 != null) {
         for(var8 = 0; var8 < var7.length; ++var8) {
            this.may.addElement(var7[var8]);
         }
      }

   }

   public LDAPNameFormSchema(String var1) {
      this.attrName = "objectclasses";
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

      var2 = this.properties.get("OC");
      if (var2 != null) {
         this.objectClass = (String)var2;
      }

   }

   public String[] getRequiredNamingAttributes() {
      String[] var1 = new String[this.must.size()];
      this.must.copyInto(var1);
      return var1;
   }

   public String[] getOptionalNamingAttributes() {
      String[] var1 = new String[this.may.size()];
      this.may.copyInto(var1);
      return var1;
   }

   public String getObjectClass() {
      return this.objectClass;
   }

   String getValue(boolean var1) {
      String var2 = this.getValuePrefix();
      String var3 = this.getOptionalValues(NOVALS);
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      var2 = var2 + "OC " + this.objectClass + ' ';
      if (this.must.size() > 0) {
         var2 = var2 + "MUST " + this.vectorToList(this.must);
         var2 = var2 + ' ';
      }

      if (this.may.size() > 0) {
         var2 = var2 + "MAY " + this.vectorToList(this.may);
         var2 = var2 + ' ';
      }

      var3 = this.getCustomValues();
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      var2 = var2 + ')';
      return var2;
   }

   public String toString() {
      String var1 = "Name: " + this.name + "; OID: " + this.oid;
      var1 = var1 + "; Description: " + this.description + "; Required: ";
      String[] var2 = this.getRequiredNamingAttributes();

      int var3;
      for(var3 = 0; var3 < var2.length; ++var3) {
         if (var3 > 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + var2[var3];
      }

      var1 = var1 + "; Optional: ";
      var2 = this.getOptionalNamingAttributes();

      for(var3 = 0; var3 < var2.length; ++var3) {
         if (var3 > 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + var2[var3];
      }

      if (this.isObsolete()) {
         var1 = var1 + "; OBSOLETE";
      }

      var1 = var1 + this.getQualifierString(IGNOREVALS);
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

   static {
      for(int var0 = 0; var0 < NOVALS.length; ++var0) {
         novalsTable.put(NOVALS[var0], NOVALS[var0]);
      }

      IGNOREVALS = new String[]{"MUST", "MAY", "OBJECTCLASS", "OBSOLETE"};
   }
}
