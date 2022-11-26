package netscape.ldap;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

public class LDAPUrl implements Serializable {
   static final long serialVersionUID = -3245440798565713641L;
   public static String defaultFilter = "(objectClass=*)";
   private String m_hostName;
   private int m_portNumber;
   private String m_DN;
   private Vector m_attributes;
   private int m_scope;
   private String m_filter;
   private String m_URL;
   private boolean m_secure;
   private static LDAPSocketFactory m_factory;
   public static final int DEFAULT_SECURE_PORT = 636;

   public LDAPUrl(String var1) throws MalformedURLException {
      this.m_attributes = null;
      this.m_scope = 0;
      this.m_filter = defaultFilter;
      this.m_URL = var1;
      this.parseUrl(var1);
   }

   private void parseUrl(String var1) throws MalformedURLException {
      StringTokenizer var2 = new StringTokenizer(var1, ":/?", true);
      String var4 = null;

      String var3;
      try {
         var3 = var2.nextToken();
         if (var3.equalsIgnoreCase("LDAPS")) {
            this.m_secure = true;
         } else if (!var3.equalsIgnoreCase("LDAP")) {
            throw new MalformedURLException();
         }

         var3 = var2.nextToken();
         if (!var3.equals(":")) {
            throw new MalformedURLException();
         }

         var3 = var2.nextToken();
         if (!var3.equals("/")) {
            throw new MalformedURLException();
         }

         var3 = var2.nextToken();
         if (!var3.equals("/")) {
            throw new MalformedURLException();
         }

         var3 = var2.nextToken();
      } catch (NoSuchElementException var12) {
         throw new MalformedURLException();
      }

      boolean var5 = true;
      int var13;
      if ((var13 = var1.indexOf("[")) != -1) {
         int var6 = var1.indexOf("]");
         if (var6 == -1) {
            throw new MalformedURLException(" IPV6 address in wrong format");
         }

         this.m_hostName = var1.substring(var13, var6 + 1);
         var2 = new StringTokenizer(var1.substring(var6 + 1, var1.length()), ":/?", true);
         if (var2.countTokens() == 0) {
            this.m_portNumber = this.m_secure ? 636 : 389;
            return;
         }

         var3 = var2.nextToken();
         if (var3.equals(":")) {
            try {
               this.m_portNumber = Integer.parseInt(var2.nextToken());
            } catch (NumberFormatException var10) {
               throw new MalformedURLException("Port not a number");
            } catch (NoSuchElementException var11) {
               throw new MalformedURLException("No port number");
            }

            if (var2.countTokens() == 0) {
               return;
            }

            if (!var2.nextToken().equals("/")) {
               throw new MalformedURLException();
            }
         } else {
            if (!var3.equals("/")) {
               throw new MalformedURLException();
            }

            this.m_portNumber = this.m_secure ? 636 : 389;
         }
      } else if (var3.equals("/")) {
         this.m_hostName = null;
         this.m_portNumber = this.m_secure ? 636 : 389;
      } else {
         if (var3.equals(":")) {
            throw new MalformedURLException("No hostname");
         }

         if (var3.equals("?")) {
            throw new MalformedURLException("No host[:port]");
         }

         this.m_hostName = var3;
         if (var2.countTokens() == 0) {
            this.m_portNumber = this.m_secure ? 636 : 389;
            return;
         }

         var3 = var2.nextToken();
         if (var3.equals(":")) {
            try {
               this.m_portNumber = Integer.parseInt(var2.nextToken());
            } catch (NumberFormatException var8) {
               throw new MalformedURLException("Port not a number");
            } catch (NoSuchElementException var9) {
               throw new MalformedURLException("No port number");
            }

            if (var2.countTokens() == 0) {
               return;
            }

            if (!var2.nextToken().equals("/")) {
               throw new MalformedURLException();
            }
         } else {
            if (!var3.equals("/")) {
               throw new MalformedURLException();
            }

            this.m_portNumber = this.m_secure ? 636 : 389;
         }
      }

      if (var2.hasMoreTokens()) {
         this.m_DN = decode(this.readNextConstruct(var2));
         if (this.m_DN.equals("?")) {
            this.m_DN = "";
         } else if (this.m_DN.equals("/")) {
            throw new MalformedURLException();
         }

         if (var2.hasMoreTokens()) {
            var4 = this.readNextConstruct(var2);
            if (!var4.equals("?")) {
               StringTokenizer var14 = new StringTokenizer(decode(var4), ", ");
               this.m_attributes = new Vector();

               while(var14.hasMoreTokens()) {
                  this.m_attributes.addElement(var14.nextToken());
               }
            }

            if (var2.hasMoreTokens()) {
               var4 = this.readNextConstruct(var2);
               if (!var4.equals("?")) {
                  this.m_scope = this.getScope(var4);
                  if (this.m_scope < 0) {
                     throw new MalformedURLException("Bad scope:" + var4);
                  }
               }

               if (var2.hasMoreTokens()) {
                  var4 = this.readNextConstruct(var2);
                  this.m_filter = decode(var4);
                  this.checkBalancedParentheses(this.m_filter);
                  if (!this.m_filter.startsWith("(") && !this.m_filter.endsWith(")")) {
                     this.m_filter = "(" + this.m_filter + ")";
                  }

                  if (var2.hasMoreTokens()) {
                     throw new MalformedURLException();
                  }
               }
            }
         }
      }
   }

   private void checkBalancedParentheses(String var1) throws MalformedURLException {
      int var2 = 0;
      StringTokenizer var3 = new StringTokenizer(var1, "()", true);

      while(var3.hasMoreElements()) {
         String var4 = var3.nextToken();
         if (var4.equals("(")) {
            ++var2;
         } else if (var4.equals(")")) {
            --var2;
            if (var2 < 0) {
               throw new MalformedURLException("Unbalanced filter parentheses");
            }
         }
      }

      if (var2 != 0) {
         throw new MalformedURLException("Unbalanced filter parentheses");
      }
   }

   public LDAPUrl(String var1, int var2, String var3) {
      this.initialize(var1, var2, var3, (Enumeration)null, 0, defaultFilter, false);
   }

   public LDAPUrl(String var1, int var2, String var3, String[] var4, int var5, String var6) {
      this(var1, var2, var3, var4, var5, var6, false);
   }

   public LDAPUrl(String var1, int var2, String var3, Enumeration var4, int var5, String var6) {
      this.initialize(var1, var2, var3, var4, var5, var6, false);
   }

   public LDAPUrl(String var1, int var2, String var3, String[] var4, int var5, String var6, boolean var7) {
      if (var4 != null) {
         Vector var8 = new Vector();

         for(int var9 = 0; var9 < var4.length; ++var9) {
            var8.addElement(var4[var9]);
         }

         this.initialize(var1, var2, var3, var8.elements(), var5, var6, var7);
      } else {
         this.initialize(var1, var2, var3, (Enumeration)null, var5, var6, var7);
      }

   }

   private void initialize(String var1, int var2, String var3, Enumeration var4, int var5, String var6, boolean var7) {
      this.m_hostName = var1;
      this.m_DN = var3;
      this.m_portNumber = var2;
      this.m_filter = var6 != null ? var6 : defaultFilter;
      this.m_scope = var5;
      this.m_secure = var7;
      if (var4 != null) {
         this.m_attributes = new Vector();

         while(var4.hasMoreElements()) {
            this.m_attributes.addElement(var4.nextElement());
         }
      } else {
         this.m_attributes = null;
      }

      StringBuffer var8 = new StringBuffer(var7 ? "ldaps://" : "ldap://");
      if (var1 != null) {
         var8.append(var1);
         var8.append(':');
         var8.append(String.valueOf(var2));
      }

      var8.append('/');
      var8.append(encode(var3));
      if (var4 != null) {
         var8.append('?');
         Enumeration var9 = this.m_attributes.elements();

         for(boolean var10 = true; var9.hasMoreElements(); var8.append((String)var9.nextElement())) {
            if (!var10) {
               var8.append(',');
            } else {
               var10 = false;
            }
         }
      }

      if (var6 != null) {
         if (var4 == null) {
            var8.append('?');
         }

         var8.append('?');
         switch (var5) {
            case 0:
            default:
               var8.append("base");
               break;
            case 1:
               var8.append("one");
               break;
            case 2:
               var8.append("sub");
         }

         var8.append('?');
         var8.append(var6);
      }

      this.m_URL = var8.toString();
   }

   public String getHost() {
      return this.m_hostName;
   }

   public int getPort() {
      return this.m_portNumber;
   }

   public String getDN() {
      return this.m_DN;
   }

   String getServerUrl() {
      return (this.m_secure ? "ldaps://" : "ldap://") + this.m_hostName + ":" + this.m_portNumber;
   }

   public Enumeration getAttributes() {
      return this.m_attributes == null ? null : this.m_attributes.elements();
   }

   public String[] getAttributeArray() {
      if (this.m_attributes == null) {
         return null;
      } else {
         String[] var1 = new String[this.m_attributes.size()];
         Enumeration var2 = this.getAttributes();

         for(int var3 = 0; var2.hasMoreElements(); var1[var3++] = (String)var2.nextElement()) {
         }

         return var1;
      }
   }

   public int getScope() {
      return this.m_scope;
   }

   private int getScope(String var1) {
      byte var2 = -1;
      if (var1.equalsIgnoreCase("base")) {
         var2 = 0;
      } else if (var1.equalsIgnoreCase("one")) {
         var2 = 1;
      } else if (var1.equalsIgnoreCase("sub")) {
         var2 = 2;
      }

      return var2;
   }

   public String getFilter() {
      return this.m_filter;
   }

   public String getUrl() {
      return this.m_URL;
   }

   public boolean isSecure() {
      return this.m_secure;
   }

   public static LDAPSocketFactory getSocketFactory() {
      if (m_factory == null) {
         Class var0;
         try {
            var0 = Class.forName("netscape.ldap.factory.JSSSocketFactory");
            m_factory = (LDAPSocketFactory)var0.newInstance();
         } catch (Throwable var2) {
         }

         if (m_factory != null) {
            return m_factory;
         }

         try {
            var0 = Class.forName("netscape.ldap.factory.JSSESocketFactory");
            m_factory = (LDAPSocketFactory)var0.newInstance();
         } catch (Throwable var1) {
         }
      }

      return m_factory;
   }

   public static void setSocketFactory(LDAPSocketFactory var0) {
      m_factory = var0;
   }

   private String readNextConstruct(StringTokenizer var1) throws MalformedURLException {
      try {
         if (var1.hasMoreTokens()) {
            String var2 = var1.nextToken();
            if (var2.equals("?")) {
               return var2;
            } else {
               if (var1.hasMoreTokens()) {
                  String var3 = var1.nextToken();
                  if (!var3.equals("?")) {
                     throw new MalformedURLException();
                  }
               }

               return var2;
            }
         } else {
            return null;
         }
      } catch (NoSuchElementException var4) {
         throw new MalformedURLException();
      }
   }

   private static int hexValue(char var0) throws MalformedURLException {
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else if (var0 >= 'A' && var0 <= 'F') {
         return var0 - 65 + 10;
      } else if (var0 >= 'a' && var0 <= 'f') {
         return var0 - 97 + 10;
      } else {
         throw new MalformedURLException();
      }
   }

   private static char hexChar(int var0) {
      if (var0 >= 0 && var0 <= 15) {
         return var0 < 10 ? (char)(var0 + 48) : (char)(var0 - 10 + 97);
      } else {
         return 'x';
      }
   }

   public static String decode(String var0) throws MalformedURLException {
      StringBuffer var1 = new StringBuffer(var0);
      int var2 = 0;
      int var3 = 0;

      try {
         while(var2 < var1.length()) {
            if (var1.charAt(var2) != '%') {
               if (var2 != var3) {
                  var1.setCharAt(var3, var1.charAt(var2));
               }

               ++var2;
               ++var3;
            } else {
               var1.setCharAt(var3, (char)(hexValue(var1.charAt(var2 + 1)) << 4 | hexValue(var1.charAt(var2 + 2))));
               ++var3;
               var2 += 3;
            }
         }
      } catch (StringIndexOutOfBoundsException var5) {
         throw new MalformedURLException();
      }

      var1.setLength(var3);
      return var1.toString();
   }

   public static String encode(String var0) {
      StringBuffer var1 = new StringBuffer(var0.length() + 10);

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         char var3 = var0.charAt(var2);
         if ((var3 < 'a' || var3 > 'z') && (var3 < 'A' || var3 > 'Z') && (var3 < '0' || var3 > '9') && "$-_.+!*'(),".indexOf(var3) <= 0) {
            var1.append("%");
            var1.append(hexChar((var3 & 240) >> 4));
            var1.append(hexChar(var3 & 15));
         } else {
            var1.append(var3);
         }
      }

      return var1.toString();
   }

   public String toString() {
      return this.getUrl();
   }

   public boolean equals(LDAPUrl var1) {
      if (this.getHost() == null) {
         if (var1.getHost() != null) {
            return false;
         }
      } else if (!this.getHost().equals(var1.getHost())) {
         return false;
      }

      if (this.getPort() != var1.getPort()) {
         return false;
      } else {
         if (this.getDN() == null) {
            if (var1.getDN() != null) {
               return false;
            }
         } else if (!this.getDN().equals(var1.getDN())) {
            return false;
         }

         if (this.getFilter() == null) {
            if (var1.getFilter() != null) {
               return false;
            }
         } else if (!this.getFilter().equals(var1.getFilter())) {
            return false;
         }

         if (this.getScope() != var1.getScope()) {
            return false;
         } else {
            if (this.m_attributes == null) {
               if (var1.m_attributes != null) {
                  return false;
               }
            } else {
               if (this.m_attributes.size() != var1.m_attributes.size()) {
                  return false;
               }

               for(int var2 = 0; var2 < this.m_attributes.size(); ++var2) {
                  if (this.m_attributes.elementAt(var2) != var1.m_attributes.elementAt(var2)) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }
}
