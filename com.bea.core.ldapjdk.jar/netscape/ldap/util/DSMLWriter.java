package netscape.ldap.util;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSchema;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPObjectClassSchema;
import netscape.ldap.LDAPSchema;

public class DSMLWriter extends LDAPWriter {
   public DSMLWriter(PrintWriter var1) {
      super(var1);
   }

   public void printSchema(LDAPEntry var1) {
      LDAPSchema var2 = new LDAPSchema(var1);
      this.printString("  <dsml:directory-schema>");
      this.printObjectClassSchema(var2);
      this.printAttributeSchema(var2);
      this.printString("  </dsml:directory-schema>");
   }

   protected void printObjectClassSchema(LDAPSchema var1) {
      Enumeration var2 = var1.getObjectClasses();

      while(var2.hasMoreElements()) {
         LDAPObjectClassSchema var3 = (LDAPObjectClassSchema)var2.nextElement();
         this.printString("    <dsml:class");
         this.printString("      id=\"" + var3.getName() + "\"");
         this.printString("      oid=\"" + var3.getID() + "\"");
         String[] var4 = var3.getSuperiors();
         if (var4 != null) {
            for(int var5 = 0; var5 < var4.length; ++var5) {
               this.printString("      superior=\"#" + var4[var5] + "\"");
            }
         }

         String var7 = "structural";
         switch (var3.getType()) {
            case 1:
               var7 = "abstract";
               break;
            case 2:
               var7 = "auxiliary";
         }

         this.printString("      type=\"" + var7 + "\">");
         if (var3.isObsolete()) {
            this.printString("      obsolete=true");
         }

         this.printString("      <dsml:name>" + var3.getName() + "</dsml:name>");
         this.printString("      <dsml:description>" + var3.getDescription() + "</dsml:description>");
         Enumeration var6 = var3.getRequiredAttributes();

         while(var6.hasMoreElements()) {
            this.printString("      <dsml:attribute ref=\"#" + (String)var6.nextElement() + "\" required=\"true\"/>");
         }

         var6 = var3.getOptionalAttributes();

         while(var6.hasMoreElements()) {
            this.printString("      <dsml:attribute ref=\"#" + (String)var6.nextElement() + "\" required=\"false\"/>");
         }

         this.printString("    </dsml:class>");
      }

   }

   protected void printAttributeSchema(LDAPSchema var1) {
      Enumeration var2 = var1.getAttributes();

      while(var2.hasMoreElements()) {
         LDAPAttributeSchema var3 = (LDAPAttributeSchema)var2.nextElement();
         this.printString("    <dsml:attribute-type");
         this.printString("      id=\"" + var3.getName() + "\"");
         this.printString("      oid=\"" + var3.getID() + "\"");
         String var4 = var3.getSuperior();
         if (var4 != null) {
            this.printString("      superior=\"#" + var4 + "\"");
         }

         if (var3.isSingleValued()) {
            this.printString("      single-value=true");
         }

         if (var3.isObsolete()) {
            this.printString("      obsolete=true");
         }

         if (var3.getQualifier("NO-USER-MODIFICATION") != null) {
            this.printString("      user-modification=false");
         }

         String[] var5 = var3.getQualifier("EQUALITY");
         if (var5 != null && var5.length > 0) {
            this.printString("      equality=" + var5[0]);
         }

         var5 = var3.getQualifier("ORDERING");
         if (var5 != null && var5.length > 0) {
            this.printString("      ordering=" + var5[0]);
         }

         var5 = var3.getQualifier("SUBSTR");
         if (var5 != null && var5.length > 0) {
            this.printString("      substring=" + var5[0]);
         }

         this.printString("      <dsml:name>" + var3.getName() + "</dsml:name>");
         this.printString("      <dsml:description>" + var3.getDescription() + "</dsml:description>");
         this.printString("      <dsml:syntax>" + var3.getSyntaxString() + "</dsml:syntax>");
         this.printString("    </dsml:attribute-type>");
      }

   }

   protected void printAttribute(LDAPAttribute var1) {
      String var2 = var1.getName();
      Enumeration var3;
      if (var2.equalsIgnoreCase("objectclass")) {
         var3 = var1.getStringValues();
         if (var3 != null) {
            while(var3.hasMoreElements()) {
               String var8 = (String)var3.nextElement();
               this.printString("    <dsml:objectclass>" + var8 + "</dsml:objectclass>");
            }
         }

      } else {
         this.printString("    <dsml:attr name=\"" + var2 + "\">");
         var3 = var1.getByteValues();
         if (var3 != null) {
            while(var3.hasMoreElements()) {
               byte[] var4 = (byte[])((byte[])var3.nextElement());
               String var5;
               if (LDIF.isPrintable(var4)) {
                  try {
                     var5 = new String(var4, "UTF8");
                  } catch (UnsupportedEncodingException var7) {
                     var5 = "";
                  }

                  this.printEscapedValue("      <dsml:value>", var5, "</dsml:value>");
               } else {
                  var5 = this.getPrintableValue(var4);
                  if (var5.length() > 0) {
                     this.printString("      <dsml:value encoding=\"base64\">");
                     this.printString("       " + var5);
                     this.printString("      </dsml:value>");
                  }
               }
            }
         }

         this.printString("    </dsml:attr>");
      }
   }

   protected void printEntryStart(String var1) {
      if (var1 == null) {
         var1 = "";
      }

      this.printString("  <dsml:entry dn=\"" + var1 + "\">");
   }

   protected void printEntryEnd(String var1) {
      this.printString("  </dsml:entry>");
   }

   protected void printEscapedValue(String var1, String var2, String var3) {
      this.m_pw.print(var1);
      int var4 = var2.length();
      char[] var5 = new char[var4];
      var2.getChars(0, var4, var5, 0);

      for(int var6 = 0; var6 < var4; ++var6) {
         char var7 = var5[var6];
         switch (var7) {
            case '&':
               this.m_pw.print("&amp;");
               break;
            case '<':
               this.m_pw.print("&lt;");
               break;
            default:
               this.m_pw.print(var7);
         }
      }

      this.m_pw.print(var3);
      this.m_pw.print('\n');
   }

   protected void printString(String var1) {
      this.m_pw.print(var1);
      this.m_pw.print('\n');
   }
}
