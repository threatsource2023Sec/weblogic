package monfox.toolkit.snmp;

public class SnmpCounter extends SnmpUnsignedInt {
   static final long serialVersionUID = 1668763304731027959L;
   private static final String a = "$Id: SnmpCounter.java,v 1.11 2002/11/11 17:20:04 sking Exp $";

   public SnmpCounter(SnmpCounter var1) {
      super((SnmpUnsignedInt)var1);
   }

   public SnmpCounter(int var1) throws IllegalArgumentException {
      super(var1);
   }

   public SnmpCounter(long var1) throws IllegalArgumentException {
      super(var1);
   }

   public SnmpCounter(Integer var1) throws IllegalArgumentException {
      super(var1);
   }

   public SnmpCounter(Long var1) throws IllegalArgumentException {
      super(var1);
   }

   public SnmpCounter(String var1) throws SnmpValueException {
      super(var1);
   }

   public final String getTypeName() {
      return a("\u0014\r\u001b9d2\u0010");
   }

   public Object clone() {
      return new SnmpCounter(this);
   }

   public int getType() {
      return 65;
   }

   public int getTag() {
      return 65;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 87;
               break;
            case 1:
               var10003 = 98;
               break;
            case 2:
               var10003 = 110;
               break;
            case 3:
               var10003 = 87;
               break;
            default:
               var10003 = 16;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
