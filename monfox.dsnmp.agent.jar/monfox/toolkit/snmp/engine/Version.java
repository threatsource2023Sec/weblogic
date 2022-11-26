package monfox.toolkit.snmp.engine;

public class Version {
   private static String a = a("|9/\b>?\u0015%d{*\u0003\"]pv\u0000'\u001ehxAe\u0007>j@{\u00001hBd\u0003/xAs\b.`Jx\u0002>+\u001b\"\\yx53B>l/rm)$@s\u001f/h]y\u0002/oP{\n$iAq\u0001.xT");

   public static void main(String[] var0) {
      System.out.println((new Version()).toString());
   }

   public static String getBuildDate() {
      return a("hHf\u0003.uB{\u0003)x@s\b/iJx\u0002");
   }

   public static String getBuildRelease() {
      return a("l/rm)");
   }

   public static String getBuildPlatform() {
      return a("\u0014\u0019%Gfx\u0012*AmxBe\u00040kBf\u0006-i^.^(v\bs\u0004AnDk\u0011/x#\u0006b>\u001e\u0002\"\u0012P7\u0006k\u0000,x@x\b/mJ{\u000b>\r$\b\u0012,hAx\u0012f`F\u0014\u0004*x\bs\u0004AnDkJ&n/}\u0006>\u001f>\u001e\u001dR1\u001e>J");
   }

   public static String getBuildJDKVersion() {
      return a("2\u0011=S>.\u00159Aw7\u001ek\u0013/vGe\u0002AnGj");
   }

   public String toString() {
      return a("\n\u0015'W\u007f+\u0015k\u0012>bP") + getBuildRelease() + "\n" + a("\u001a\u0005\"^zx4*F{bP") + getBuildDate() + "\n" + a("\u001a\u0005\"^zx:\u000fy>bP") + getBuildJDKVersion() + "\n" + a("\b\u001c*Fx7\u0002&\u0012>bP") + getBuildPlatform() + "\n";
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 88;
               break;
            case 1:
               var10003 = 112;
               break;
            case 2:
               var10003 = 75;
               break;
            case 3:
               var10003 = 50;
               break;
            default:
               var10003 = 30;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
