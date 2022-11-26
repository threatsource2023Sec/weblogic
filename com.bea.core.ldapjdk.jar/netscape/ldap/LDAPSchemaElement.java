package netscape.ldap;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public abstract class LDAPSchemaElement implements Serializable {
   static final long serialVersionUID = -3972153461950418863L;
   public static final int unknown = 0;
   public static final int cis = 1;
   public static final int binary = 2;
   public static final int telephone = 3;
   public static final int ces = 4;
   public static final int dn = 5;
   public static final int integer = 6;
   protected static final String cisString = "1.3.6.1.4.1.1466.115.121.1.15";
   protected static final String binaryString = "1.3.6.1.4.1.1466.115.121.1.5";
   protected static final String telephoneString = "1.3.6.1.4.1.1466.115.121.1.50";
   protected static final String cesString = "1.3.6.1.4.1.1466.115.121.1.26";
   protected static final String intString = "1.3.6.1.4.1.1466.115.121.1.27";
   protected static final String dnString = "1.3.6.1.4.1.1466.115.121.1.12";
   public static final String OBSOLETE = "OBSOLETE";
   public static final String SUPERIOR = "SUP";
   public static final String SYNTAX = "SYNTAX";
   protected String oid;
   protected String name;
   protected String description;
   protected String attrName;
   protected String rawValue;
   protected String[] aliases;
   protected Hashtable properties;
   protected static Hashtable novalsTable = new Hashtable();

   protected LDAPSchemaElement() {
      this.oid = null;
      this.name = "";
      this.description = "";
      this.attrName = null;
      this.rawValue = null;
      this.aliases = null;
      this.properties = null;
   }

   protected LDAPSchemaElement(String var1, String var2, String var3) {
      this(var1, var2, var3, (String[])null);
   }

   protected LDAPSchemaElement(String var1, String var2, String var3, String[] var4) {
      this.oid = null;
      this.name = "";
      this.description = "";
      this.attrName = null;
      this.rawValue = null;
      this.aliases = null;
      this.properties = null;
      if (var2 == null) {
         throw new IllegalArgumentException("OID required");
      } else {
         this.name = var1;
         this.oid = var2;
         this.description = var3;
         if (var4 != null && var4.length > 0) {
            this.aliases = var4;
         }

      }
   }

   public String getName() {
      return this.name;
   }

   public String getID() {
      return this.oid;
   }

   /** @deprecated */
   public String getOID() {
      return this.getID();
   }

   public String getDescription() {
      return this.description;
   }

   protected void update(LDAPConnection var1, int var2, LDAPAttribute var3, String var4) throws LDAPException {
      LDAPAttribute[] var5 = new LDAPAttribute[]{var3};
      this.update(var1, var2, var5, var4);
   }

   protected void update(LDAPConnection var1, int var2, LDAPAttribute[] var3, String var4) throws LDAPException {
      LDAPModificationSet var5 = new LDAPModificationSet();

      for(int var6 = 0; var6 < var3.length; ++var6) {
         var5.add(var2, var3[var6]);
      }

      String var7 = LDAPSchema.getSchemaDN(var1, var4);
      var1.modify(var7, var5);
   }

   protected void update(LDAPConnection var1, int var2, String var3, String var4) throws LDAPException {
      boolean var5 = !LDAPSchema.isAttributeSyntaxStandardsCompliant(var1);
      LDAPAttribute var6 = new LDAPAttribute(var3, this.getValue(var5));
      this.update(var1, var2, var6, var4);
   }

   public void add(LDAPConnection var1, String var2) throws LDAPException {
      this.update(var1, 0, (String)this.attrName, var2);
   }

   public void add(LDAPConnection var1) throws LDAPException {
      this.add(var1, "");
   }

   public void modify(LDAPConnection var1, LDAPSchemaElement var2, String var3) throws LDAPException {
      boolean var4 = !LDAPSchema.isAttributeSyntaxStandardsCompliant(var1);
      LDAPModificationSet var5 = new LDAPModificationSet();
      var5.add(1, new LDAPAttribute(this.attrName, this.getValue(var4)));
      var5.add(0, new LDAPAttribute(this.attrName, var2.getValue(var4)));
      String var6 = LDAPSchema.getSchemaDN(var1, var3);
      var1.modify(var6, var5);
   }

   public void modify(LDAPConnection var1, LDAPSchemaElement var2) throws LDAPException {
      this.modify(var1, var2, "");
   }

   public void remove(LDAPConnection var1, String var2) throws LDAPException {
      this.update(var1, 1, (String)this.attrName, var2);
   }

   public void remove(LDAPConnection var1) throws LDAPException {
      this.remove(var1, "");
   }

   public boolean isObsolete() {
      return this.properties == null ? false : this.properties.containsKey("OBSOLETE");
   }

   protected void parseValue(String var1) {
      if (this.properties == null) {
         this.properties = new Hashtable();
      }

      int var2 = var1.length();
      char[] var3 = new char[var2];
      var1.getChars(0, var2, var3, 0);
      --var2;

      while(var3[var2] == ' ') {
         --var2;
      }

      int var4;
      for(var4 = 0; var3[var4] == ' '; ++var4) {
      }

      var4 += 2;

      int var5;
      for(var5 = var4 + 1; var3[var5] != ' '; ++var5) {
      }

      this.oid = new String(var3, var4, var5 - var4);
      ++var5;

      while(var5 < var2) {
         while(var3[var5] == ' ') {
            ++var5;
         }

         int var8;
         for(var8 = var5 + 1; var8 < var2 && var3[var8] != ' '; ++var8) {
         }

         String var6;
         if (var8 >= var2) {
            var6 = "";
            break;
         }

         var6 = new String(var3, var5, var8 - var5);
         var5 = var8;
         if (novalsTable.containsKey(var6)) {
            this.properties.put(var6, "");
         } else {
            while(var5 < var2 && var3[var5] == ' ') {
               ++var5;
            }

            var8 = var5 + 1;
            if (var5 >= var2) {
               break;
            }

            boolean var9 = false;
            boolean var10 = false;
            if (var3[var5] == '\'') {
               var9 = true;
               ++var5;

               while(var8 < var2 && var3[var8] != '\'') {
                  ++var8;
               }
            } else if (var3[var5] == '(') {
               var10 = true;
               ++var5;

               while(var8 < var2 && var3[var8] != ')') {
                  ++var8;
               }
            } else {
               while(var8 < var2 && var3[var8] != ' ') {
                  ++var8;
               }
            }

            if (var5 < var8 && var8 <= var2) {
               String var7;
               if (var10) {
                  Vector var11 = new Vector();
                  if (var3[var5] == ' ') {
                     ++var5;
                  }

                  var7 = new String(var3, var5, var8 - var5 - 1);
                  String var12 = var7.indexOf(39) >= 0 ? "'" : " ";
                  StringTokenizer var13 = new StringTokenizer(var7, var12);

                  while(var13.hasMoreTokens()) {
                     String var14 = var13.nextToken().trim();
                     if (var14.length() > 0 && !var14.equals("$")) {
                        var11.addElement(var14);
                     }
                  }

                  this.properties.put(var6, var11);
               } else {
                  var7 = new String(var3, var5, var8 - var5);
                  if (var6.equals("NAME")) {
                     this.name = var7;
                  } else if (var6.equals("DESC")) {
                     this.description = var7;
                  } else {
                     this.properties.put(var6, var7);
                  }

                  if (var9) {
                     ++var8;
                  }
               }
            }

            var5 = var8 + 1;
         }
      }

      String[] var15 = this.getQualifier("NAME");
      if (var15 != null && var15.length > 0) {
         this.name = var15[0];
         if (var15.length > 1) {
            this.aliases = new String[var15.length - 1];
            System.arraycopy(var15, 1, this.aliases, 0, this.aliases.length);
         }
      }

   }

   public String getValue() {
      return this.getValue(false);
   }

   String getValue(boolean var1) {
      return null;
   }

   String getValuePrefix() {
      String var1 = "( " + this.oid + ' ';
      if (this.name != null && this.name.length() > 0) {
         var1 = var1 + "NAME ";
         if (this.aliases != null) {
            var1 = var1 + "( '" + this.name + "' ";

            for(int var2 = 0; var2 < this.aliases.length; ++var2) {
               var1 = var1 + '\'' + this.aliases[var2] + "' ";
            }

            var1 = var1 + ") ";
         } else {
            var1 = var1 + '\'' + this.name + "' ";
         }
      }

      if (this.description != null) {
         var1 = var1 + "DESC '" + this.description + "' ";
      }

      if (this.isObsolete()) {
         var1 = var1 + "OBSOLETE ";
      }

      return var1;
   }

   protected String getOptionalValues(String[] var1) {
      String var2 = "";

      for(int var3 = 0; var3 < var1.length; ++var3) {
         String[] var4 = this.getQualifier(var1[var3]);
         if (var4 != null && var4.length > 0) {
            var2 = var2 + var1[var3] + ' ' + var4[0];
         }
      }

      return var2;
   }

   protected String getCustomValues() {
      String var1 = "";
      Enumeration var2 = this.properties.keys();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         if (var3.startsWith("X-")) {
            var1 = var1 + this.getValue(var3, true, false) + ' ';
         }
      }

      if (var1.length() > 0 && var1.charAt(var1.length() - 1) == ' ') {
         var1 = var1.substring(0, var1.length() - 1);
      }

      return var1;
   }

   String getValue(String var1, boolean var2, boolean var3) {
      String var4 = "";
      Object var5 = this.properties.get(var1);
      if (var5 == null) {
         return var4;
      } else {
         if (var5 instanceof String) {
            if (((String)var5).length() > 0) {
               var4 = var4 + var1 + ' ';
               if (var2) {
                  var4 = var4 + '\'';
               }

               var4 = var4 + (String)var5;
               if (var2) {
                  var4 = var4 + '\'';
               }
            }
         } else {
            var4 = var4 + var1 + " ( ";
            Vector var6 = (Vector)var5;

            for(int var7 = 0; var7 < var6.size(); ++var7) {
               if (var2) {
                  var4 = var4 + '\'';
               }

               var4 = var4 + (String)var6.elementAt(var7);
               if (var2) {
                  var4 = var4 + '\'';
               }

               var4 = var4 + ' ';
               if (var3 && var7 < var6.size() - 1) {
                  var4 = var4 + "$ ";
               }
            }

            var4 = var4 + ')';
         }

         return var4;
      }
   }

   String getValue(String var1, boolean var2) {
      return this.getValue(var1, var2, true);
   }

   public void setQualifier(String var1, String var2) {
      if (this.properties == null) {
         this.properties = new Hashtable();
      }

      if (var2 != null) {
         this.properties.put(var1, var2);
      } else {
         this.properties.remove(var1);
      }

   }

   public void setQualifier(String var1, String[] var2) {
      if (var2 != null) {
         if (this.properties == null) {
            this.properties = new Hashtable();
         }

         Vector var3 = new Vector();

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.addElement(var2[var4]);
         }

         this.properties.put(var1, var3);
      }
   }

   public String[] getQualifier(String var1) {
      if (this.properties == null) {
         return null;
      } else {
         Object var2 = this.properties.get(var1);
         if (var2 == null) {
            return null;
         } else if (var2 instanceof Vector) {
            Vector var5 = (Vector)var2;
            String[] var4 = new String[var5.size()];
            var5.copyInto(var4);
            return var4;
         } else {
            String var3 = (String)var2;
            return var3.length() < 1 ? new String[0] : new String[]{var3};
         }
      }
   }

   public Enumeration getQualifierNames() {
      return this.properties.keys();
   }

   public String[] getAliases() {
      return this.aliases;
   }

   String getQualifierString(String[] var1) {
      Hashtable var2 = null;
      if (var1 != null) {
         var2 = new Hashtable();

         for(int var3 = 0; var3 < var1.length; ++var3) {
            var2.put(var1[var3], var1[var3]);
         }
      }

      String var8 = "";
      Enumeration var4 = this.getQualifierNames();

      while(true) {
         while(true) {
            String var5;
            do {
               if (!var4.hasMoreElements()) {
                  if (var8.length() > 0 && var8.charAt(var8.length() - 1) == ' ') {
                     var8 = var8.substring(0, var8.length() - 1);
                  }

                  return var8;
               }

               var5 = (String)var4.nextElement();
            } while(var2 != null && var2.containsKey(var5));

            var8 = var8 + "; " + var5;
            String[] var6 = this.getQualifier(var5);
            if (var6 == null) {
               var8 = var8 + ' ';
            } else {
               var8 = var8 + ": ";

               for(int var7 = 0; var7 < var6.length; ++var7) {
                  var8 = var8 + var6[var7] + ' ';
               }
            }
         }
      }
   }

   String getAliasString() {
      if (this.aliases == null) {
         return "";
      } else {
         String var1 = "; aliases:";

         for(int var2 = 0; var2 < this.aliases.length; ++var2) {
            var1 = var1 + ' ' + this.aliases[var2];
         }

         return var1;
      }
   }
}
