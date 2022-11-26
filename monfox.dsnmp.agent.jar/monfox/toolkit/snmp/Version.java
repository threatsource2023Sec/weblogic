package monfox.toolkit.snmp;

public class Version {
   private static String a = a("\u0011kk+]RGaG\u0018GQf~\u0013\u001bRc=\u000b\u0015\u0013!$]\u0007\u0012?#R\u0005\u0010  L\u0015\u00137+M\r\u0018<!]FIf\u007f\u001a\u0015gwa]\u0001}6NJI\u00127<L\u0005\u000f=!L\u0002\u0002?)G\u0004\u00135 K\u0015\u0006");

   public static void main(String[] var0) {
      System.out.println((new Version()).toString());
   }

   public static String getBuildDate() {
      return a("\u0005\u001a\" M\u0018\u0010? J\u0015\u00127+L\u0004\u0018>'");
   }

   public static String getBuildRelease() {
      return a("\u0001}6NJ");
   }

   public static String getBuildPlatform() {
      return a("yKad\u0005\u0015@nb\u000e\u0015\u0010!'S\u0006\u0010\"%N\u0004\fj}K\u001bZ7'\"\u0003\u0016/2L\u0015qBA]sPf13ZT/#O\u0015\u0012<+L\u0000\u0018?(]`vL1O\u0005\u0013<1\u0005\r\u0014P'I\u0015Z7'\"\u0003\u0016/iE\u0003}9%]rlZ>1\\Lzi");
   }

   public static String getBuildJDKVersion() {
      return a("_Cyp]CG}b\u0014ZL/0L\u001b\u0015!!\"\u0003\u0015.");
   }

   public String toString() {
      return a("gGct\u001cFG/1]\u000f\u0002") + getBuildRelease() + "\n" + a("wWf}\u0019\u0015fne\u0018\u000f\u0002") + getBuildDate() + "\n" + a("wWf}\u0019\u0015hKZ]\u000f\u0002") + getBuildJDKVersion() + "\n" + a("eNne\u001bZPb1]\u000f\u0002") + getBuildPlatform() + "\n";
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 53;
               break;
            case 1:
               var10003 = 34;
               break;
            case 2:
               var10003 = 15;
               break;
            case 3:
               var10003 = 17;
               break;
            default:
               var10003 = 125;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
