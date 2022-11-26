package monfox.toolkit.snmp.mgr;

abstract class a {
   static String a() {
      return b("Z(q8{");
   }

   static String b() {
      return b("z\"q4fW8L\u001bFn");
   }

   static long c() {
      return System.currentTimeMillis();
   }

   static String d() {
      if (a() != null) {
         System.exit(1);
      }

      return b("j>l!+s>l&jY>");
   }

   static String e() {
      return b("4x<v+") + b() + b("\u001e\u001ei4gK:k<dPu?\u0016dN\"m<lV/76\"\u000fb&l&\fk.f'\u001e\u0016p;mQ#3\u0019G}{<v(4");
   }

   static String f() {
      return b("4Q?u+g4j'+[-~9~_/v:e\u001e-z'xW4qudX{8") + b() + b("\u0019{w4x\u001e>g%bL>{{\u0001") + b("\u001e{?\u0005g[:l0+]4q!j]/?rbP=p\u0015fQ5y:s\u00108p8,\u001e/puy[5z\"+G4j'+[-~9~_/v:e4") + b("\u001e{?:y\u001e+j'hV:l0+_{y gR{j&nL{s<h[5l0%4");
   }

   static String g() {
      return b("4Q?u+}:q;dJ{s:h_/zugW8z;x[{y<g[{8") + a() + b("\u00107v6nP(zr+X4mu,") + b() + b("\u0019u\u0015") + b("\u001e{?\u0005g[:l0+]4q!j]/?rbP=p\u0015fQ5y:s\u00108p8,\u001e/pud\\/~<e\u001e/w0+N)p%nL{s<h[5l0%4Q") + b("\u001e{?\u001bdJ>%ugW8z;x[{y<g[{l=dK7{ui[{s:h_/z1+W5?!c[{k:{\u001e7z#nR{o4hU:x0+_5{_") + b("\u001e{?%g_8z1+W5?4+t:mumW7zudL{{<y[8k:yG{v;+J3zuhR:l&{_/w{\u0001");
   }

   static String h() {
      return b("4Q?u+g4j'+R2|0eM>?3bR>?<x\u001e5p!+H:s<o\u001e=p'+\u0019") + b() + b("\u0019u\u0015") + b("\u001e{?\u0005g[:l0+]4q!j]/?rbP=p\u0015fQ5y:s\u00108p8,\u001e/pud\\/~<e\u001e/w0+N)p%nL{s<h[5l0x\u0010Q");
   }

   static String i() {
      return b("4Q?u+g4j'+R2|0eM>?3bR>?3dL{8") + b() + b("\u0019{v&+W5i4gW??:y\u001e2r%yQ+z'gG{y:yS:k!nZu\u0015") + b("\u001e{?\u0005g[:l0+]4q!j]/?rbP=p\u0015fQ5y:s\u00108p8,\u001e/pud\\/~<e\u001e:qu~N?~!nZ{s<h[5l0+U>f_") + b("\u001e{?:y\u001e+j'hV:l0+_{y gR{j&nL{s<h[5l0%4Q");
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
               var10003 = 62;
               break;
            case 1:
               var10003 = 91;
               break;
            case 2:
               var10003 = 31;
               break;
            case 3:
               var10003 = 85;
               break;
            default:
               var10003 = 11;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
