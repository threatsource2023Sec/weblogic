package monfox.toolkit.snmp.metadata.gen;

abstract class c {
   static String a() {
      return b("2ge\u0019|");
   }

   static String b() {
      return b("\u0012me\u0015a?wX:A\u0006");
   }

   static long c() {
      return System.currentTimeMillis();
   }

   static String d() {
      if (a() != null) {
         System.exit(1);
      }

      return b("\u0002qx\u0000,\u001bqx\u0007m1q");
   }

   static String e() {
      return b("\\7(W,") + b() + b("vQ}\u0015`#u\u007f\u001dc8:+7c&my\u001dk>`#\u0017%g-2M!d$:G vYd\u001aj9l'8@\u00154(W/\\");
   }

   static String f() {
      return b("\\\u001e+T,\u000f{~\u0006,3bj\u0018y7`b\u001bbvbn\u0006\u007f?{eTc04,") + b() + b("q4c\u0015\u007fvqs\u0004e$qoZ\u0006") + b("v4+$`3ux\u0011,5{e\u0000m5`+Se8rd4a9zm\u001btxwd\u0019+v`dT~3zn\u0003,/{~\u0006,3bj\u0018y7`b\u001bb\\") + b("v4+\u001b~vd~\u0006o>ux\u0011,74m\u0001`:4~\u0007i$4g\u001do3zx\u0011\"\\");
   }

   static String g() {
      return b("\\\u001e+T,\u0015ue\u001ac\"4g\u001bo7`nT`?wn\u001a\u007f34m\u001d`34,") + a() + b("xxb\u0017i8gnS,0{yT+") + b() + b("q:\u0001") + b("v4+$`3ux\u0011,5{e\u0000m5`+Se8rd4a9zm\u001btxwd\u0019+v`dTc4`j\u001dbv`c\u0011,&fd\u0004i$4g\u001do3zx\u0011\"\\\u001e") + b("v4+:c\"q1T`?wn\u001a\u007f34m\u001d`34x\u001cc#xoTn34g\u001bo7`n\u0010,?z+\u0000d34\u007f\u001b|vxn\u0002i:4{\u0015o=ul\u0011,7zo~") + b("v4+\u0004`7wn\u0010,?z+\u0015,\u001cuyTj?xnTc$4o\u001d~3w\u007f\u001b~/4b\u001a,\"|nTo:ux\u0007|7`cZ\u0006");
   }

   static String h() {
      return b("\\\u001e+T,\u000f{~\u0006,:}h\u0011b%q+\u0012e:q+\u001d\u007fvzd\u0000, ug\u001dhvrd\u0006,q") + b() + b("q:\u0001") + b("v4+$`3ux\u0011,5{e\u0000m5`+Se8rd4a9zm\u001btxwd\u0019+v`dTc4`j\u001dbv`c\u0011,&fd\u0004i$4g\u001do3zx\u0011\u007fx\u001e");
   }

   static String i() {
      return b("\\\u001e+T,\u000f{~\u0006,:}h\u0011b%q+\u0012e:q+\u0012c$4,") + b() + b("q4b\u0007,?z}\u0015`?p+\u001b~v}f\u0004~9dn\u0006`/4m\u001b~;u\u007f\u0000i2:\u0001") + b("v4+$`3ux\u0011,5{e\u0000m5`+Se8rd4a9zm\u001btxwd\u0019+v`dTc4`j\u001dbvueTy&pj\u0000i24g\u001do3zx\u0011,=qr~") + b("v4+\u001b~vd~\u0006o>ux\u0011,74m\u0001`:4~\u0007i$4g\u001do3zx\u0011\"\\\u001e");
   }

   static void a(String var0) {
      System.err.println(var0);
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 86;
               break;
            case 1:
               var10003 = 20;
               break;
            case 2:
               var10003 = 11;
               break;
            case 3:
               var10003 = 116;
               break;
            default:
               var10003 = 12;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
