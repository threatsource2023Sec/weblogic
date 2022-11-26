package antlr;

public class StringUtils {
   public static String stripBack(String var0, char var1) {
      while(var0.length() > 0 && var0.charAt(var0.length() - 1) == var1) {
         var0 = var0.substring(0, var0.length() - 1);
      }

      return var0;
   }

   public static String stripBack(String var0, String var1) {
      boolean var2;
      do {
         var2 = false;

         for(int var3 = 0; var3 < var1.length(); ++var3) {
            for(char var4 = var1.charAt(var3); var0.length() > 0 && var0.charAt(var0.length() - 1) == var4; var0 = var0.substring(0, var0.length() - 1)) {
               var2 = true;
            }
         }
      } while(var2);

      return var0;
   }

   public static String stripFront(String var0, char var1) {
      while(var0.length() > 0 && var0.charAt(0) == var1) {
         var0 = var0.substring(1);
      }

      return var0;
   }

   public static String stripFront(String var0, String var1) {
      boolean var2;
      do {
         var2 = false;

         for(int var3 = 0; var3 < var1.length(); ++var3) {
            for(char var4 = var1.charAt(var3); var0.length() > 0 && var0.charAt(0) == var4; var0 = var0.substring(1)) {
               var2 = true;
            }
         }
      } while(var2);

      return var0;
   }

   public static String stripFrontBack(String var0, String var1, String var2) {
      int var3 = var0.indexOf(var1);
      int var4 = var0.lastIndexOf(var2);
      return var3 != -1 && var4 != -1 ? var0.substring(var3 + 1, var4) : var0;
   }
}
