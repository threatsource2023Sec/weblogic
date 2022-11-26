package netscape.ldap;

class LDAPSyntaxSchemaElement extends LDAPSchemaElement {
   static final long serialVersionUID = 6086340702503710702L;
   int syntax = 0;
   String syntaxString = null;

   int getSyntax() {
      return this.syntax;
   }

   String getSyntaxString() {
      return this.syntaxString;
   }

   static String internalSyntaxToString(int var0) {
      String var1;
      if (var0 == 1) {
         var1 = "1.3.6.1.4.1.1466.115.121.1.15";
      } else if (var0 == 2) {
         var1 = "1.3.6.1.4.1.1466.115.121.1.5";
      } else if (var0 == 4) {
         var1 = "1.3.6.1.4.1.1466.115.121.1.26";
      } else if (var0 == 3) {
         var1 = "1.3.6.1.4.1.1466.115.121.1.50";
      } else if (var0 == 5) {
         var1 = "1.3.6.1.4.1.1466.115.121.1.12";
      } else if (var0 == 6) {
         var1 = "1.3.6.1.4.1.1466.115.121.1.27";
      } else {
         var1 = null;
      }

      return var1;
   }

   String syntaxToString() {
      String var1;
      if (this.syntax == 1) {
         var1 = "case-insensitive string";
      } else if (this.syntax == 2) {
         var1 = "binary";
      } else if (this.syntax == 6) {
         var1 = "integer";
      } else if (this.syntax == 4) {
         var1 = "case-exact string";
      } else if (this.syntax == 3) {
         var1 = "telephone";
      } else if (this.syntax == 5) {
         var1 = "distinguished name";
      } else {
         var1 = this.syntaxString;
      }

      return var1;
   }

   int syntaxCheck(String var1) {
      byte var2 = 0;
      if (var1 != null) {
         if (var1.equals("1.3.6.1.4.1.1466.115.121.1.15")) {
            var2 = 1;
         } else if (var1.equals("1.3.6.1.4.1.1466.115.121.1.5")) {
            var2 = 2;
         } else if (var1.equals("1.3.6.1.4.1.1466.115.121.1.26")) {
            var2 = 4;
         } else if (var1.equals("1.3.6.1.4.1.1466.115.121.1.27")) {
            var2 = 6;
         } else if (var1.equals("1.3.6.1.4.1.1466.115.121.1.50")) {
            var2 = 3;
         } else if (var1.equals("1.3.6.1.4.1.1466.115.121.1.12")) {
            var2 = 5;
         }
      }

      return var2;
   }
}
