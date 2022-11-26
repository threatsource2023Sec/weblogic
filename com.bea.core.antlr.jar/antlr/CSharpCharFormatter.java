package antlr;

class CSharpCharFormatter implements CharFormatter {
   public String escapeChar(int var1, boolean var2) {
      switch (var1) {
         case 9:
            return "\\t";
         case 10:
            return "\\n";
         case 13:
            return "\\r";
         case 34:
            return var2 ? "\"" : "\\\"";
         case 39:
            return var2 ? "\\'" : "'";
         case 92:
            return "\\\\";
         default:
            if (var1 >= 32 && var1 <= 126) {
               return String.valueOf((char)var1);
            } else if (0 <= var1 && var1 <= 15) {
               return "\\u000" + Integer.toString(var1, 16);
            } else if (16 <= var1 && var1 <= 255) {
               return "\\u00" + Integer.toString(var1, 16);
            } else {
               return 256 <= var1 && var1 <= 4095 ? "\\u0" + Integer.toString(var1, 16) : "\\u" + Integer.toString(var1, 16);
            }
      }
   }

   public String escapeString(String var1) {
      String var2 = new String();

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         var2 = var2 + this.escapeChar(var1.charAt(var3), false);
      }

      return var2;
   }

   public String literalChar(int var1) {
      return "'" + this.escapeChar(var1, true) + "'";
   }

   public String literalString(String var1) {
      return "@\"\"\"" + this.escapeString(var1) + "\"\"\"";
   }
}
