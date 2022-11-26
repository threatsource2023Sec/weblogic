package antlr;

public class DefaultFileLineFormatter extends FileLineFormatter {
   public String getFormatString(String var1, int var2, int var3) {
      StringBuffer var4 = new StringBuffer();
      if (var1 != null) {
         var4.append(var1 + ":");
      }

      if (var2 != -1) {
         if (var1 == null) {
            var4.append("line ");
         }

         var4.append(var2);
         if (var3 != -1) {
            var4.append(":" + var3);
         }

         var4.append(":");
      }

      var4.append(" ");
      return var4.toString();
   }
}
