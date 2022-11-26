package netscape.ldap.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public final class RDN implements Serializable {
   static final long serialVersionUID = 7895454691174650321L;
   private String[] m_type = null;
   private String[] m_value = null;
   private boolean m_ismultivalued = false;
   private static Hashtable m_attributehash = new Hashtable();
   public static final String[] _cesAttributes = new String[]{"adminurl", "altserver", "automountinformation", "bootfile", "bootparameter", "cirbindcredentials", "generation", "homedirectory", "internationalisdnnumber", "labeleduri", "membercertificatedescription", "membernisnetgroup", "memberuid", "memberurl", "nismapentry", "nisnetgrouptriple", "nsaddressbooksyncurl", "presentationaddress", "ref", "replicaentryfilter", "searchguide", "subtreeaci", "vlvfilter", "vlvname", "x121address"};
   public static final String CES_SYNTAX = "1.3.6.1.4.1.1466.115.121.1.26";

   public RDN(String var1) {
      String var2 = neutralizeEscapes(var1);
      if (var2 != null) {
         int var3 = var2.indexOf("=");
         if (var3 > 0) {
            Vector var5 = new Vector();
            Vector var6 = new Vector();
            var6.addElement(var1.substring(0, var3).trim());

            for(int var4 = var2.indexOf(43, var3); var4 != -1; var4 = var2.indexOf(43, var3)) {
               this.m_ismultivalued = true;
               var5.addElement(var1.substring(var3 + 1, var4).trim());
               var3 = var2.indexOf("=", var4 + 1);
               if (var3 == -1) {
                  return;
               }

               var6.addElement(var1.substring(var4 + 1, var3).trim());
            }

            var5.addElement(var1.substring(var3 + 1).trim());
            this.m_type = new String[var6.size()];
            this.m_value = new String[var5.size()];

            for(int var7 = 0; var7 < var6.size(); ++var7) {
               this.m_type[var7] = (String)var6.elementAt(var7);
               if (!this.isValidType(this.m_type[var7])) {
                  this.m_type = this.m_value = null;
                  return;
               }

               this.m_value[var7] = (String)var5.elementAt(var7);
               if (!this.isValidValue(this.m_value[var7])) {
                  this.m_type = this.m_value = null;
                  return;
               }
            }

         }
      }
   }

   static String neutralizeEscapes(String var0) {
      if (var0 == null) {
         return null;
      } else {
         StringBuffer var1 = new StringBuffer(var0);
         boolean var2 = false;

         int var3;
         for(var3 = 0; var3 < var1.length(); ++var3) {
            if (var1.charAt(var3) == '\\') {
               var1.setCharAt(var3, 'x');
               if (var3 >= var1.length() - 1) {
                  return null;
               }

               var1.setCharAt(var3 + 1, 'x');
            }
         }

         for(var3 = 0; var3 < var1.length(); ++var3) {
            if (var1.charAt(var3) == '"') {
               var2 = !var2;
            } else if (var2) {
               var1.setCharAt(var3, 'x');
            }
         }

         return var2 ? null : var1.toString();
      }
   }

   private boolean isValidType(String var1) {
      if (var1 != null && var1.length() >= 1) {
         for(int var2 = 0; var2 < var1.length(); ++var2) {
            for(int var3 = 0; var3 < DN.ESCAPED_CHAR.length; ++var3) {
               if (var1.charAt(var2) == DN.ESCAPED_CHAR[var3]) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isValidValue(String var1) {
      if (var1 != null && var1.length() >= 1) {
         int var2 = 0;
         int var3 = 0;

         while(var3 >= 0 && var3 < var1.length()) {
            var3 = var1.indexOf(34, var3);
            if (var3 >= 0) {
               if (var3 == 0 || var1.charAt(var3 - 1) != '\\') {
                  ++var2;
               }

               ++var3;
            }
         }

         if (var2 == 0) {
            return true;
         } else if (var2 != 2) {
            return false;
         } else {
            return var1.charAt(0) == '"' && var1.charAt(var1.length() - 1) == '"';
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public String[] explodeRDN(boolean var1) {
      if (this.m_type == null) {
         return null;
      } else {
         String[] var2 = new String[1];
         if (var1) {
            var2[0] = this.getValue();
         } else {
            var2[0] = this.toString();
         }

         return var2;
      }
   }

   /** @deprecated */
   public String getType() {
      return this.m_type != null && this.m_type.length > 0 ? this.m_type[0] : null;
   }

   public String[] getTypes() {
      return this.m_type;
   }

   /** @deprecated */
   public String getValue() {
      return this.m_value != null && this.m_value.length > 0 ? this.m_value[0] : null;
   }

   public String[] getValues() {
      return this.m_value;
   }

   public boolean isMultivalued() {
      return this.m_ismultivalued;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; this.m_type != null && var2 < this.m_type.length; ++var2) {
         if (var2 != 0) {
            var1.append(" + ");
         }

         var1.append(this.m_type[var2] + "=" + this.m_value[var2]);
      }

      return var1.toString();
   }

   public static boolean isRDN(String var0) {
      RDN var1 = new RDN(var0);
      return var1.getTypes() != null && var1.getValues() != null;
   }

   public boolean equals(RDN var1) {
      String[] var2 = (String[])((String[])this.getTypes().clone());
      String[] var3 = (String[])((String[])this.getValues().clone());
      String[] var4 = (String[])((String[])var1.getTypes().clone());
      String[] var5 = (String[])((String[])var1.getValues().clone());
      if (var2.length != var4.length) {
         return false;
      } else {
         this.sortTypesAndValues(var2, var3);
         this.sortTypesAndValues(var4, var5);

         for(int var6 = 0; var6 < var2.length; ++var6) {
            if (!var2[var6].equalsIgnoreCase(var4[var6])) {
               return false;
            }

            if ("1.3.6.1.4.1.1466.115.121.1.26".equals(getAttributeSyntax(var2[var6]))) {
               if (!var3[var6].equals(var5[var6])) {
                  return false;
               }
            } else if (!var3[var6].equalsIgnoreCase(var5[var6])) {
               return false;
            }
         }

         return true;
      }
   }

   void sortTypesAndValues(String[] var1, String[] var2) {
      do {
         boolean var3 = true;

         for(int var4 = 0; var4 < var1.length - 1; ++var4) {
            if (var1[var4].toLowerCase().compareTo(var1[var4 + 1].toLowerCase()) > 0) {
               String var5 = var1[var4];
               String var6 = var2[var4];
               var1[var4] = var1[var4 + 1];
               var2[var4] = var2[var4 + 1];
               var1[var4 + 1] = var5;
               var2[var4 + 1] = var6;
               var3 = false;
            }
         }

         var3 = false;
      } while(false);

   }

   public static void registerAttributeSyntax(String var0, String var1) {
      m_attributehash.put(var0.toLowerCase(), var1);
   }

   public static void unregisterAttributeSyntax(String var0) {
      m_attributehash.remove(var0.toLowerCase());
   }

   public static String getAttributeSyntax(String var0) {
      return (String)m_attributehash.get(var0.toLowerCase());
   }

   public static String[] getAttributesForSyntax(String var0) {
      Enumeration var1 = m_attributehash.keys();
      Vector var2 = new Vector();
      String var3 = null;

      while(var1.hasMoreElements()) {
         var3 = (String)var1.nextElement();
         if (var0.equals((String)m_attributehash.get(var3))) {
            var2.addElement(var3);
         }
      }

      String[] var4 = new String[var2.size()];

      for(int var5 = 0; var5 < var4.length; ++var5) {
         var4[var5] = new String((String)var2.elementAt(var5));
      }

      return var4;
   }

   static {
      for(int var0 = 0; var0 < _cesAttributes.length; ++var0) {
         registerAttributeSyntax(_cesAttributes[var0], "1.3.6.1.4.1.1466.115.121.1.26");
      }

   }
}
