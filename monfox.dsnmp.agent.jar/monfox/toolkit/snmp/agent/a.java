package monfox.toolkit.snmp.agent;

abstract class a {
   static String a() {
      return b("JyU`\u0019");
   }

   static String b() {
      return b("jsUl\u0004GihC$~'zj\f@~");
   }

   static long c() {
      return System.currentTimeMillis();
   }

   static String d() {
      if (a() != null) {
         System.exit(1);
      }

      return b("zoHyIcoH~\bIo");
   }

   static String e() {
      return b("$)\u0018.I") + b() + b("\u000eOMl\u0005[kOd\u0006@$\u001bN\u0006^sId\u000eF~\u0013n@\u001f3\u00024D\u001c:\n>E\u000eGTc\u000fAr\u0017A%m*\u0018.J$");
   }

   static String f() {
      return b("$\u0000\u001b-IweN\u007fIK|Za\u001cO~Rb\u0007\u000e|^\u007f\u001aGeU-\u0006H*\u001c") + b() + b("\t*Sl\u001a\u000eoC}\u0000\\o_#c") + b("\u000e*\u001b]\u0005KkHhIMeUy\bM~\u001b*\u0000@lTM\u0004Ad]b\u0011\u0000iT`N\u000e~T-\u001bKd^zIWeN\u007fIK|Za\u001cO~Rb\u0007$") + b("\u000e*\u001bb\u001b\u000ezN\u007f\nFkHhIO*]x\u0005B*N~\f\\*Wd\nKdHhG$");
   }

   static String g() {
      return b("$\u0000\u001b-ImkUc\u0006Z*Wb\nO~^-\u0005Gi^c\u001aK*]d\u0005K*\u001c") + a() + b("\u0000fRn\f@y^*IHeI-N") + b() + b("\t$1") + b("\u000e*\u001b]\u0005KkHhIMeUy\bM~\u001b*\u0000@lTM\u0004Ad]b\u0011\u0000iT`N\u000e~T-\u0006L~Zd\u0007\u000e~ShI^xT}\f\\*Wd\nKdHhG$\u0000") + b("\u000e*\u001bC\u0006Zo\u0001-\u0005Gi^c\u001aK*]d\u0005K*He\u0006[f_-\u000bK*Wb\nO~^iIGd\u001by\u0001K*Ob\u0019\u000ef^{\fB*Kl\nEk\\hIOd_\u0007") + b("\u000e*\u001b}\u0005Oi^iIGd\u001blIdkI-\u000fGf^-\u0006\\*_d\u001bKiOb\u001bW*RcIZb^-\nBkH~\u0019O~S#c");
   }

   static String h() {
      return b("$\u0000\u001b-IweN\u007fIBcXh\u0007]o\u001bk\u0000Bo\u001bd\u001a\u000edTyIXkWd\r\u000elT\u007fI\t") + b() + b("\t$1") + b("\u000e*\u001b]\u0005KkHhIMeUy\bM~\u001b*\u0000@lTM\u0004Ad]b\u0011\u0000iT`N\u000e~T-\u0006L~Zd\u0007\u000e~ShI^xT}\f\\*Wd\nKdHh\u001a\u0000\u0000");
   }

   static String i() {
      return b("$\u0000\u001b-IweN\u007fIBcXh\u0007]o\u001bk\u0000Bo\u001bk\u0006\\*\u001c") + b() + b("\t*R~IGdMl\u0005Gn\u001bb\u001b\u000ecV}\u001bAz^\u007f\u0005W*]b\u001bCkOy\fJ$1") + b("\u000e*\u001b]\u0005KkHhIMeUy\bM~\u001b*\u0000@lTM\u0004Ad]b\u0011\u0000iT`N\u000e~T-\u0006L~Zd\u0007\u000ekU-\u001c^nZy\fJ*Wd\nKdHhIEoB\u0007") + b("\u000e*\u001bb\u001b\u000ezN\u007f\nFkHhIO*]x\u0005B*N~\f\\*Wd\nKdHhG$\u0000");
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
               var10003 = 46;
               break;
            case 1:
               var10003 = 10;
               break;
            case 2:
               var10003 = 59;
               break;
            case 3:
               var10003 = 13;
               break;
            default:
               var10003 = 105;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
