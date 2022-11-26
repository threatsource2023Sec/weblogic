package monfox.toolkit.snmp.ber;

import java.math.BigInteger;

final class g {
   private static final String a = "$Id: UNSIGNED_BERCoder.java,v 1.1 2008/12/04 17:31:29 sking Exp $";

   int a(BERBuffer var1, long var2, int var4, boolean var5) throws BERException {
      int var6 = b.longToCompactByteArray(var1, var2, var4, var5);
      var6 += b.encodeLength(var1, var6);
      return var6;
   }

   long a(BERBuffer var1) throws BERException {
      int var2 = monfox.toolkit.snmp.ber.a.getLength(var1);
      return monfox.toolkit.snmp.ber.a.b(var1, var2);
   }

   int a(BERBuffer var1, BigInteger var2, int var3, boolean var4) throws BERException {
      byte[] var5 = var2.toByteArray();
      var1.add(var5);
      int var6 = var5.length;
      var6 += b.encodeLength(var1, var6);
      return var6;
   }

   BigInteger b(BERBuffer var1) throws BERException {
      int var2 = monfox.toolkit.snmp.ber.a.getLength(var1);
      if (var2 == 0) {
         return BigInteger.ZERO;
      } else {
         try {
            byte[] var3 = new byte[var2];
            var1.nextBytes(var3, var2);
            return new BigInteger(var3);
         } catch (ArrayIndexOutOfBoundsException var4) {
            throw new BERException(a("k?;g7L0833\tv") + var2 + a("\u0000~6){M;<(?@08g4O~\u001d.<`0+\"<L,q"));
         }
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
               var10003 = 41;
               break;
            case 1:
               var10003 = 94;
               break;
            case 2:
               var10003 = 95;
               break;
            case 3:
               var10003 = 71;
               break;
            default:
               var10003 = 91;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
