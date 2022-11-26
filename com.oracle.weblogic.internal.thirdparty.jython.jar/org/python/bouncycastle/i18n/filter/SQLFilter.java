package org.python.bouncycastle.i18n.filter;

public class SQLFilter implements Filter {
   public String doFilter(String var1) {
      StringBuffer var2 = new StringBuffer(var1);

      for(int var3 = 0; var3 < var2.length(); ++var3) {
         char var4 = var2.charAt(var3);
         switch (var4) {
            case '\n':
               var2.replace(var3, var3 + 1, "\\n");
               ++var3;
               break;
            case '\r':
               var2.replace(var3, var3 + 1, "\\r");
               ++var3;
               break;
            case '"':
               var2.replace(var3, var3 + 1, "\\\"");
               ++var3;
               break;
            case '\'':
               var2.replace(var3, var3 + 1, "\\'");
               ++var3;
               break;
            case '-':
               var2.replace(var3, var3 + 1, "\\-");
               ++var3;
               break;
            case '/':
               var2.replace(var3, var3 + 1, "\\/");
               ++var3;
               break;
            case ';':
               var2.replace(var3, var3 + 1, "\\;");
               ++var3;
               break;
            case '=':
               var2.replace(var3, var3 + 1, "\\=");
               ++var3;
               break;
            case '\\':
               var2.replace(var3, var3 + 1, "\\\\");
               ++var3;
         }
      }

      return var2.toString();
   }

   public String doFilterUrl(String var1) {
      return this.doFilter(var1);
   }
}
