package monfox.toolkit.snmp.metadata.gen;

public class InfoMessage extends Message {
   public static final int validationStage = 50;
   public static final int validationComplete = 51;
   public static final int errorSummary = 52;
   public static final int debug = 53;
   public static final int savingFile = 54;
   public static final int autoLoading = 55;
   public static final int loadingFile = 56;
   public static final int parsedElement = 57;
   public static final int textMessage = 58;
   private int c;

   public InfoMessage(String var1) {
      this(58, var1);
   }

   public InfoMessage(int var1, String var2) {
      super(var2);
      this.c = var1;
   }

   public int getType() {
      return this.c;
   }

   static String a(int var0) {
      switch (var0) {
         case 50:
            return a(">NAw\u0014");
         case 51:
            return a(";[Ly\u0015\fNI\u007f\u001fMyO}\u0001\u0001_Tu");
         case 52:
            return a("(HR\u007f\u0003MiU}\u001c\fHY");
         case 53:
            return a(")\u007fbE6");
         case 54:
            return a(">[Vy\u001f\n\u001afy\u001d\b");
         case 55:
            return a(",OT\u007fQ!UAt\u0018\u0003]");
         case 56:
            return a("!UAt\u0018\u0003]\u0000V\u0018\u0001_");
         case 57:
            return a("=[Rc\u0014\t\u001ae|\u0014\u0000_Nd");
         case 58:
            return a("9_XdQ _Sc\u0010\n_");
         default:
            return a("$TF\u007f");
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 109;
               break;
            case 1:
               var10003 = 58;
               break;
            case 2:
               var10003 = 32;
               break;
            case 3:
               var10003 = 16;
               break;
            default:
               var10003 = 113;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class ErrorSummary extends InfoMessage {
      private Result a = null;

      public ErrorSummary(Result var1) {
         super(52, a("y.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^\t\u0014wB vT7\u0003\u0001z\u0003zJ") + var1.getErrorCount() + a("SfK(\r\u0001p\u0019|B") + var1.getWarningCount() + a("StX(\f\u001am^)K") + a("y.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^.\u0014wO^"));
         this.a = var1;
      }

      public Result getResult() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 115;
                  break;
               case 1:
                  var10003 = 3;
                  break;
               case 2:
                  var10003 = 57;
                  break;
               case 3:
                  var10003 = 90;
                  break;
               default:
                  var10003 = 98;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ValidationComplete extends InfoMessage {
      private Result a = null;

      ValidationComplete(Result var1) {
         super(51, a("t\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS;\u0017&B(PVb\u0006\u001fESd\f^rUf\u0012\u0012TNnX^\u0019") + var1.getErrorCount() + a("^THy\r\fB\u001a-B") + var1.getWarningCount() + a("^F[y\f\u0017_]xK") + a("t\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS\u001c\u0017&OS"));
         this.a = var1;
      }

      public Result getResult() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 126;
                  break;
               case 1:
                  var10003 = 49;
                  break;
               case 2:
                  var10003 = 58;
                  break;
               case 3:
                  var10003 = 11;
                  break;
               default:
                  var10003 = 98;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ValidationStage extends InfoMessage {
      private int c = 0;
      private String a;

      ValidationStage(int var1, String var2) {
         super(50, a("FR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0Eau\u001c0H\u001f\u000bPz\r\u0017") + var1 + a("\u0011E\u0011") + var2 + a("FR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0EaR\u001c0Ea"));
         this.c = var1;
         this.a = var2;
      }

      public int getStage() {
         return this.c;
      }

      public String getStageName() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 76;
                  break;
               case 1:
                  var10003 = 127;
                  break;
               case 2:
                  var10003 = 49;
                  break;
               case 3:
                  var10003 = 29;
                  break;
               default:
                  var10003 = 104;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class Debug extends InfoMessage {
      Debug(String var1) {
         super(53, var1);
      }

      public String toString() {
         return a("W\u007f!b\u0013Kf^\u0000") + this.getMessage();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 12;
                  break;
               case 1:
                  var10003 = 59;
                  break;
               case 2:
                  var10003 = 100;
                  break;
               case 3:
                  var10003 = 32;
                  break;
               default:
                  var10003 = 70;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class SavingFile extends InfoMessage {
      private String a;

      SavingFile(String var1, String var2) {
         super(54, a("7\u0004u,") + var2 + a("\u0007\t\u007f") + var1 + a("\u001a\u0007"));
         this.a = var1;
      }

      public String getFilename() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 61;
                  break;
               case 1:
                  var10003 = 41;
                  break;
               case 2:
                  var10003 = 88;
                  break;
               case 3:
                  var10003 = 12;
                  break;
               default:
                  var10003 = 101;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class AutoLoading extends InfoMessage {
      AutoLoading() {
         super(55, a("G\u007fC;\u001f8&\u0001w1,6\u0007u9m\u001f\u0007h-$<\t;\u0013\"6\u001bw;>X"));
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 77;
                  break;
               case 1:
                  var10003 = 82;
                  break;
               case 2:
                  var10003 = 110;
                  break;
               case 3:
                  var10003 = 27;
                  break;
               default:
                  var10003 = 94;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class LoadingFile extends InfoMessage {
      private String a;

      LoadingFile(String var1) {
         super(55, a("y\u0005&\u0005;5Lo'3t\b!") + var1 + a("s\u0006"));
         this.a = var1;
      }

      public String getFilename() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 84;
                  break;
               case 1:
                  var10003 = 40;
                  break;
               case 2:
                  var10003 = 6;
                  break;
               case 3:
                  var10003 = 73;
                  break;
               default:
                  var10003 = 84;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class ParsedElement extends InfoMessage {
      private String a;
      private String b;

      ParsedElement(String var1, String var2) {
         super(57, a("3\u0011\u0018}") + var1 + a("N\u000b\u0018") + var2 + "");
         this.a = var1;
         this.b = var2;
      }

      public String getElementType() {
         return this.a;
      }

      public String getElementName() {
         return this.b;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 19;
                  break;
               case 1:
                  var10003 = 49;
                  break;
               case 2:
                  var10003 = 56;
                  break;
               case 3:
                  var10003 = 38;
                  break;
               default:
                  var10003 = 36;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
