package antlr;

public abstract class FileLineFormatter {
   private static FileLineFormatter formatter = new DefaultFileLineFormatter();

   public static FileLineFormatter getFormatter() {
      return formatter;
   }

   public static void setFormatter(FileLineFormatter var0) {
      formatter = var0;
   }

   public abstract String getFormatString(String var1, int var2, int var3);
}
