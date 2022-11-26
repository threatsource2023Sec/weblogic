package monfox.toolkit.snmp.ber;

final class f {
   private static final String a = "$Id: String_BERCoder.java,v 1.5 2002/02/01 00:07:00 sking Exp $";

   int a(BERBuffer var1, byte[] var2) throws BERException {
      int var3 = 0;
      if (var2.length == 0) {
         var1.a((byte)0);
         var3 = 1;
         if (BERBuffer.j == 0) {
            return var3;
         }
      }

      var3 += var1.add(var2);
      var3 += b.encodeLength(var1, var3);
      return var3;
   }

   byte[] a(BERBuffer var1) throws BERException {
      int var2 = monfox.toolkit.snmp.ber.a.getLength(var1);
      if (var2 < 0) {
         throw new BERException(a("reW+$InW0vYr@!vZyF%/\u001bgQ*1Oc\u0014l") + var2 + a("\u0007;\u001d"));
      } else if (var2 > var1.getRemainingSize()) {
         throw new BERException(a("Hc[6\"\u001biA\"0^y\u000ed3Uh[ 3_&X!8\\\u007f\\l") + var2 + a("\u0012+\nd4NmR!$\u0016gQ*1Oc\u001c") + var1.getRemainingSize() + ")");
      } else {
         byte[] var3 = new byte[var2];
         var1.nextBytes(var3, var2);
         return var3;
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
               var10003 = 59;
               break;
            case 1:
               var10003 = 11;
               break;
            case 2:
               var10003 = 52;
               break;
            case 3:
               var10003 = 68;
               break;
            default:
               var10003 = 86;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
