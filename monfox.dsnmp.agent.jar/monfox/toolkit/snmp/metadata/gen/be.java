package monfox.toolkit.snmp.metadata.gen;

import monfox.jdom.Element;

class be extends bd {
   private static final String a = "$Id: TABLE_ENTRYValidator.java,v 1.10 2003/07/16 23:40:02 sking Exp $";

   boolean f(n var1, Element var2) {
      super.f(var1, var2);
      if (var2.getChild(a("\u0001R+\td\u000eS?")) != null) {
         this.a(var1, var2, var2.getChild(a("\u0001R+\td\u000eS?")), a("\u0014F.\bd\u001fB\"\u0010s\u0019"));
      }

      if (var2.getChild(a("\tI(\u0001y")) != null) {
         this.a(var1, var2, var2.getChild(a("\tI(\u0001y")), (String)null, new String[]{a("\u000fE&\u0001b\u0014")}, new String[]{a("\tI8\u0001f\u0005U"), a("\u000fD8\u0001u`T8\u0016h\u000e@"), a("\u000fE&\u0001b\u0014'%\u0000d\u000eS%\u0002h\u0005U"), a("\u000eb\u00183N2l- E2b\u001f7"), a("\tw- E2b\u001f7")});
      }

      return true;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 64;
               break;
            case 1:
               var10003 = 7;
               break;
            case 2:
               var10003 = 108;
               break;
            case 3:
               var10003 = 68;
               break;
            default:
               var10003 = 33;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
