package antlr;

class CppCharFormatter implements CharFormatter {
   public String escapeChar(int var1, boolean var2) {
      switch (var1) {
         case 9:
            return "\\t";
         case 10:
            return "\\n";
         case 13:
            return "\\r";
         case 34:
            return "\\\"";
         case 39:
            return "\\'";
         case 92:
            return "\\\\";
         default:
            if (var1 >= 32 && var1 <= 126) {
               return String.valueOf((char)var1);
            } else if (var1 <= 255) {
               return "\\" + Integer.toString(var1, 8);
            } else {
               String var3;
               for(var3 = Integer.toString(var1, 16); var3.length() < 4; var3 = '0' + var3) {
               }

               return "\\u" + var3;
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
      String var2 = "0x" + Integer.toString(var1, 16);
      if (var1 >= 0 && var1 <= 126) {
         var2 = var2 + " /* '" + this.escapeChar(var1, true) + "' */ ";
      }

      return var2;
   }

   public String literalString(String var1) {
      return "\"" + this.escapeString(var1) + "\"";
   }
}
