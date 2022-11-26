package netscape.ldap.client;

import java.util.StringTokenizer;
import java.util.Vector;
import netscape.ldap.ber.stream.BERElement;

public abstract class JDAPFilter {
   public static JDAPFilter getFilter(String var0) {
      String var1 = new String(var0);
      var1.trim();
      return var1.startsWith("(") && var1.endsWith(")") ? getFilterComp(var1.substring(1, var1.length() - 1)) : getFilterComp(var0);
   }

   public static JDAPFilter getFilterComp(String var0) {
      var0.trim();
      JDAPFilter[] var1;
      int var3;
      if (var0.startsWith("&")) {
         var1 = getFilterList(var0.substring(1, var0.length()));
         if (var1 == null) {
            throw new IllegalArgumentException("Bad search filter");
         } else {
            JDAPFilterAnd var5 = new JDAPFilterAnd();

            for(var3 = 0; var3 < var1.length; ++var3) {
               var5.addElement(var1[var3]);
            }

            return var5;
         }
      } else if (!var0.startsWith("|")) {
         if (var0.startsWith("!")) {
            JDAPFilter var4 = getFilter(var0.substring(1, var0.length()));
            if (var4 == null) {
               throw new IllegalArgumentException("Bad search filter");
            } else {
               return new JDAPFilterNot(var4);
            }
         } else {
            return getFilterItem(var0.substring(0, var0.length()));
         }
      } else {
         var1 = getFilterList(var0.substring(1, var0.length()));
         if (var1 == null) {
            throw new IllegalArgumentException("Bad search filter");
         } else {
            JDAPFilterOr var2 = new JDAPFilterOr();

            for(var3 = 0; var3 < var1.length; ++var3) {
               var2.addElement(var1[var3]);
            }

            return var2;
         }
      }
   }

   public static JDAPFilter[] getFilterList(String var0) {
      var0.trim();
      int var1 = 0;
      int var2 = 0;
      boolean var3 = false;
      Vector var4 = new Vector();

      for(int var5 = 0; var5 < var0.length(); ++var5) {
         if (var0.charAt(var5) == '(') {
            if (var1 == 0) {
               var2 = var5;
            }

            ++var1;
         }

         if (var0.charAt(var5) == ')') {
            --var1;
            if (var1 == 0) {
               var4.addElement(getFilter(var0.substring(var2, var5 + 1)));
            }
         }
      }

      if (var4.size() == 0) {
         return null;
      } else {
         JDAPFilter[] var7 = new JDAPFilter[var4.size()];

         for(int var6 = 0; var6 < var4.size(); ++var6) {
            var7[var6] = (JDAPFilter)var4.elementAt(var6);
         }

         return var7;
      }
   }

   public static JDAPFilter getFilterItem(String var0) {
      var0.trim();
      int var1 = var0.indexOf(61);
      if (var1 == -1) {
         return null;
      } else {
         String var2 = var0.substring(0, var1).trim();
         String var3 = var0.substring(var1 + 1).trim();
         if (var2.indexOf(92) >= 0) {
            throw new IllegalArgumentException("Bad search filter");
         } else {
            var2.trim();
            JDAPAVA var8;
            if (var2.endsWith("~")) {
               var8 = new JDAPAVA(var2.substring(0, var2.length() - 1), var3);
               return new JDAPFilterApproxMatch(var8);
            } else if (var2.endsWith(">")) {
               var8 = new JDAPAVA(var2.substring(0, var2.length() - 1), var3);
               return new JDAPFilterGreaterOrEqual(var8);
            } else if (var2.endsWith("<")) {
               var8 = new JDAPAVA(var2.substring(0, var2.length() - 1), var3);
               return new JDAPFilterLessOrEqual(var8);
            } else if (var2.endsWith(":")) {
               return new JDAPFilterExtensible(var2.substring(0, var2.length() - 1), var3);
            } else if (var3.startsWith("*") && var3.length() == 1) {
               return new JDAPFilterPresent(var2);
            } else if (var3.indexOf(42) == -1) {
               var8 = new JDAPAVA(var2, var3);
               return new JDAPFilterEqualityMatch(var8);
            } else {
               StringTokenizer var4 = new StringTokenizer(var3, "*");
               JDAPFilterSubString var5 = new JDAPFilterSubString(var2);
               String var6 = null;
               if (!var3.startsWith("*")) {
                  var6 = var4.nextToken();
                  var6.trim();
               }

               var5.addInitial(var6);

               while(var4.hasMoreTokens()) {
                  String var7 = var4.nextToken();
                  var7.trim();
                  if (var4.hasMoreTokens()) {
                     var5.addAny(var7);
                  } else if (var3.endsWith("*")) {
                     var5.addAny(var7);
                     var5.addFinal((String)null);
                  } else {
                     var5.addFinal(var7);
                  }
               }

               return var5;
            }
         }
      }
   }

   public abstract BERElement getBERElement();

   public abstract String toString();
}
