package monfox.toolkit.snmp;

public class SnmpGauge extends SnmpUnsignedInt {
   static final long serialVersionUID = 1483243462006579189L;
   private static final String a = "$Id: SnmpGauge.java,v 1.11 2002/11/11 17:20:04 sking Exp $";

   public SnmpGauge(SnmpGauge var1) {
      super((SnmpUnsignedInt)var1);
   }

   public SnmpGauge(int var1) {
      super(var1);
   }

   public SnmpGauge(long var1) {
      super(var1);
   }

   public SnmpGauge(Integer var1) {
      super(var1);
   }

   public SnmpGauge(Long var1) {
      super(var1);
   }

   public SnmpGauge(String var1) throws SnmpValueException {
      super(var1);
   }

   public final String getTypeName() {
      return a("\u007f\u000fcU\\");
   }

   public Object clone() {
      return new SnmpGauge(this);
   }

   public int getType() {
      return 66;
   }

   public int getTag() {
      return 66;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 56;
               break;
            case 1:
               var10003 = 110;
               break;
            case 2:
               var10003 = 22;
               break;
            case 3:
               var10003 = 50;
               break;
            default:
               var10003 = 57;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
