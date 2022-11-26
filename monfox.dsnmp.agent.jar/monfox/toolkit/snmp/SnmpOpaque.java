package monfox.toolkit.snmp;

public class SnmpOpaque extends SnmpString {
   static final long serialVersionUID = -3442649265310144623L;
   private static final String a = "$Id: SnmpOpaque.java,v 1.9 2002/11/11 17:20:04 sking Exp $";

   public SnmpOpaque() {
   }

   public SnmpOpaque(String var1) throws SnmpValueException {
      super(var1);
   }

   public SnmpOpaque(byte[] var1) {
      super(var1);
   }

   public final String getTypeName() {
      return a("9WB!,\u0013");
   }

   public int getTag() {
      return 68;
   }

   public int getCoder() {
      return 3;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 118;
               break;
            case 1:
               var10003 = 39;
               break;
            case 2:
               var10003 = 35;
               break;
            case 3:
               var10003 = 80;
               break;
            default:
               var10003 = 89;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
