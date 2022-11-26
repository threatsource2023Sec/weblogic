package netscape.ldap;

import java.util.Enumeration;
import java.util.Vector;

public class LDAPObjectClassSchema extends LDAPSchemaElement {
   static final long serialVersionUID = -1732784695071118656L;
   public static final int STRUCTURAL = 0;
   public static final int ABSTRACT = 1;
   public static final int AUXILIARY = 2;
   private Vector must;
   private Vector may;
   private int type;
   static final String[] NOVALS = new String[]{"ABSTRACT", "STRUCTURAL", "AUXILIARY", "OBSOLETE"};
   static final String[] IGNOREVALS;
   static final String TYPE = "TYPE";

   public LDAPObjectClassSchema(String var1, String var2, String var3, String var4, String[] var5, String[] var6) {
      this(var1, var2, var3, var4, var5, var6, (String[])null);
   }

   public LDAPObjectClassSchema(String var1, String var2, String[] var3, String var4, String[] var5, String[] var6, int var7, String[] var8) {
      this(var1, var2, var3 != null && var3.length > 0 ? var3[0] : null, var4, var5, var6, var8);
      if (var3 != null && var3.length > 1) {
         this.setQualifier("SUP", var3);
      }

      this.setQualifier("TYPE", this.typeToString(var7));
   }

   protected LDAPObjectClassSchema(String var1, String var2, String var3, String var4, String[] var5, String[] var6, String[] var7) {
      super(var1, var2, var4, var7);
      this.must = new Vector();
      this.may = new Vector();
      this.type = 0;
      this.attrName = "objectclasses";
      this.setQualifier("SUP", var3);
      int var8;
      if (var5 != null) {
         for(var8 = 0; var8 < var5.length; ++var8) {
            this.must.addElement(var5[var8]);
         }
      }

      if (var6 != null) {
         for(var8 = 0; var8 < var6.length; ++var8) {
            this.may.addElement(var6[var8]);
         }
      }

   }

   public LDAPObjectClassSchema(String var1) {
      this.must = new Vector();
      this.may = new Vector();
      this.type = 0;
      this.attrName = "objectclasses";
      this.parseValue(var1);
      this.setQualifier("TYPE", this.typeToString(this.getType()));
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

   }

   public String getSuperior() {
      String[] var1 = this.getSuperiors();
      return var1 != null ? var1[0] : null;
   }

   public String[] getSuperiors() {
      return this.getQualifier("SUP");
   }

   public Enumeration getRequiredAttributes() {
      return this.must.elements();
   }

   public Enumeration getOptionalAttributes() {
      return this.may.elements();
   }

   public int getType() {
      byte var1 = 0;
      if (this.properties.containsKey("AUXILIARY")) {
         var1 = 2;
      } else if (this.properties.containsKey("ABSTRACT")) {
         var1 = 1;
      }

      return var1;
   }

   String getValue(boolean var1) {
      String var2 = this.getValuePrefix();
      String var3 = this.getValue("SUP", var1);
      if (var3 != null && var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

      String[] var4 = this.getQualifier("TYPE");
      if (var4 != null && var4.length > 0) {
         var2 = var2 + var4[0] + ' ';
      }

      var3 = this.getOptionalValues(NOVALS);
      if (var3.length() > 0) {
         var2 = var2 + var3 + ' ';
      }

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
      String var1 = "Name: " + this.name + "; OID: " + this.oid + "; Superior: ";
      String[] var2 = this.getSuperiors();
      int var3;
      if (var2 != null) {
         for(var3 = 0; var3 < var2.length; ++var3) {
            var1 = var1 + var2[var3];
            if (var3 < var2.length - 1) {
               var1 = var1 + ", ";
            }
         }
      }

      var1 = var1 + "; Description: " + this.description + "; Required: ";
      var3 = 0;

      Enumeration var4;
      for(var4 = this.getRequiredAttributes(); var4.hasMoreElements(); var1 = var1 + (String)var4.nextElement()) {
         if (var3 > 0) {
            var1 = var1 + ", ";
         }

         ++var3;
      }

      var1 = var1 + "; Optional: ";
      var4 = this.getOptionalAttributes();

      for(var3 = 0; var4.hasMoreElements(); var1 = var1 + (String)var4.nextElement()) {
         if (var3 > 0) {
            var1 = var1 + ", ";
         }

         ++var3;
      }

      String[] var5 = this.getQualifier("TYPE");
      if (var5 != null && var5.length > 0) {
         var1 = var1 + "; " + var5[0];
      }

      if (this.isObsolete()) {
         var1 = var1 + "; OBSOLETE";
      }

      var1 = var1 + this.getQualifierString(IGNOREVALS);
      var1 = var1 + this.getAliasString();
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

   protected String typeToString(int var1) {
      switch (var1) {
         case 0:
            return "STRUCTURAL";
         case 1:
            return "ABSTRACT";
         case 2:
            return "AUXILIARY";
         default:
            return null;
      }
   }

   static {
      for(int var0 = 0; var0 < NOVALS.length; ++var0) {
         novalsTable.put(NOVALS[var0], NOVALS[var0]);
      }

      IGNOREVALS = new String[]{"ABSTRACT", "STRUCTURAL", "AUXILIARY", "MUST", "MAY", "SUP", "OBSOLETE"};
   }
}
