package monfox.toolkit.snmp.ber;

final class d {
   private static final String a = "$Id: NULL_BERCoder.java,v 1.3 2001/04/11 21:41:24 sking Exp $";

   public void decode(BERBuffer var1) throws BERException {
      if (var1.getRemainingSize() > 0 && var1.getByteAtIndex() == 0) {
         var1.setIndex(var1.getIndex() + 1);
      } else {
         throw new BERException(a("\"9\brV\u00192\bi\u0004\u001d6\u0007hAK1\u0004o\u0004%\u0002'Q"));
      }
   }

   public int encode(BERBuffer var1) throws BERException {
      var1.add((byte)0);
      return 1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 107;
               break;
            case 1:
               var10003 = 87;
               break;
            case 2:
               var10003 = 107;
               break;
            case 3:
               var10003 = 29;
               break;
            default:
               var10003 = 36;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
