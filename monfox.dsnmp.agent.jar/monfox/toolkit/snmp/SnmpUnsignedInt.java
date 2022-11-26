package monfox.toolkit.snmp;

public abstract class SnmpUnsignedInt extends SnmpInt {
   static final long serialVersionUID = 3035571659208607694L;
   private static final String a = "$Id: SnmpUnsignedInt.java,v 1.10 2002/11/11 17:20:04 sking Exp $";

   public SnmpUnsignedInt(SnmpUnsignedInt var1) {
      super((SnmpInt)var1);
   }

   public SnmpUnsignedInt(int var1) {
      super(var1);
   }

   public SnmpUnsignedInt(long var1) {
      super(var1);
   }

   public SnmpUnsignedInt(Integer var1) {
      super(var1);
   }

   public SnmpUnsignedInt(Long var1) {
      super(var1);
   }

   public SnmpUnsignedInt(String var1) throws SnmpValueException {
      super(var1);
   }

   public String getTypeName() {
      return a("\u000b#\u0001^T0(\u0016~]*");
   }

   public int getCoder() {
      return 5;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 94;
               break;
            case 1:
               var10003 = 77;
               break;
            case 2:
               var10003 = 114;
               break;
            case 3:
               var10003 = 55;
               break;
            default:
               var10003 = 51;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
