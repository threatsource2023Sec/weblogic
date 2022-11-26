package monfox.toolkit.snmp;

public class SnmpNull extends SnmpValue {
   static final long serialVersionUID = 4212385398365431508L;
   public static final SnmpNull noSuchObject = new SnmpNull(128);
   public static final SnmpNull noSuchInstance = new SnmpNull(129);
   public static final SnmpNull endOfMibView = new SnmpNull(130);
   private int a = 5;
   private static final String b = "$Id: SnmpNull.java,v 1.15 2007/03/15 20:36:48 sking Exp $";

   public SnmpNull() {
   }

   public SnmpNull(int var1) {
      this.a = var1;
   }

   public SnmpNull(String var1) {
   }

   public final synchronized Object clone() {
      return new SnmpNull(this.a);
   }

   public final String getTypeName() {
      return a("`Z\u001e0");
   }

   public SnmpOid toIndexOid(boolean var1) {
      throw new RuntimeException(a("cJ\u00064.J\u000f\u001c35\u000e\\\u0007,1A]\u00069%\u000eI\u001d.a`Z\u001e0aXN\u001e)$\u0000"));
   }

   public int fromIndexOid(SnmpOid var1, int var2, boolean var3, int var4) throws SnmpValueException {
      throw new RuntimeException(a("cJ\u00064.J\u000f\u001c35\u000e\\\u0007,1A]\u00069%\u000eI\u001d.a`Z\u001e0aXN\u001e)$\u0000"));
   }

   public void appendIndexOid(SnmpOid var1, boolean var2) {
      throw new RuntimeException(a("cJ\u00064.J\u000f\u001c35\u000e\\\u0007,1A]\u00069%\u000eI\u001d.a`Z\u001e0aXN\u001e)$\u0000"));
   }

   public void toString(StringBuffer var1) {
      boolean var2 = SnmpValue.b;
      switch (this.a) {
         case 128:
            var1.append(a("@@!)\"F`\u00106$M["));
            if (!var2) {
               break;
            }
         case 129:
            var1.append(a("@@!)\"Ff\u001c/5OA\u00119"));
            if (!var2) {
               break;
            }
         case 130:
            var1.append(a("KA\u0016\u0013'cF\u0010\n(KX"));
            if (!var2) {
               break;
            }
         default:
            var1.append(a("`Z\u001e0"));
      }

   }

   public String toString() {
      switch (this.a) {
         case 128:
            return a("@@!)\"F`\u00106$M[");
         case 129:
            return a("@@!)\"Ff\u001c/5OA\u00119");
         case 130:
            return a("KA\u0016\u0013'cF\u0010\n(KX");
         default:
            return a("`Z\u001e0");
      }
   }

   public boolean equals(Object var1) {
      if (var1 instanceof SnmpNull) {
         return ((SnmpNull)var1).getTag() == this.getTag();
      } else {
         return false;
      }
   }

   public void fromString(String var1) throws SnmpValueException {
      if (var1 != null && !var1.equals("") && !var1.equals("0") && !var1.equalsIgnoreCase(a("`z>\u0010"))) {
         throw new SnmpValueException(a("gA\u0004=-GKR\u0012\u0014bcR* BZ\u0017fa") + var1);
      }
   }

   public long longValue() {
      return 0L;
   }

   public int getType() {
      return 5;
   }

   public int getTag() {
      return this.a;
   }

   public int getCoder() {
      return 4;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 46;
               break;
            case 1:
               var10003 = 47;
               break;
            case 2:
               var10003 = 114;
               break;
            case 3:
               var10003 = 92;
               break;
            default:
               var10003 = 65;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
