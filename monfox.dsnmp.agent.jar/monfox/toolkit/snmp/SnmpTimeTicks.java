package monfox.toolkit.snmp;

public class SnmpTimeTicks extends SnmpUnsignedInt {
   static final long serialVersionUID = 3953804723745763216L;
   private static final String a = "$Id: SnmpTimeTicks.java,v 1.9 2002/11/11 17:20:04 sking Exp $";

   public SnmpTimeTicks(SnmpTimeTicks var1) {
      super((SnmpUnsignedInt)var1);
   }

   public SnmpTimeTicks(int var1) {
      super(var1);
   }

   public SnmpTimeTicks(long var1) {
      super(var1);
   }

   public SnmpTimeTicks(Integer var1) {
      super(var1);
   }

   public SnmpTimeTicks(Long var1) {
      super(var1);
   }

   public SnmpTimeTicks(String var1) throws SnmpValueException {
      super(var1);
   }

   public final String getTypeName() {
      return a("!9\r9X\u001c3\u000b/");
   }

   public final String toString() {
      return super.toString();
   }

   public Object clone() {
      return new SnmpTimeTicks(this);
   }

   public int getType() {
      return 67;
   }

   public int getTag() {
      return 67;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 117;
               break;
            case 1:
               var10003 = 80;
               break;
            case 2:
               var10003 = 96;
               break;
            case 3:
               var10003 = 92;
               break;
            default:
               var10003 = 12;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
