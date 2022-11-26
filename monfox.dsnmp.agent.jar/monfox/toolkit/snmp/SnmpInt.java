package monfox.toolkit.snmp;

public class SnmpInt extends SnmpValue {
   static final long serialVersionUID = 1589999440299587603L;
   long a;
   private static final String b = "$Id: SnmpInt.java,v 1.16 2010/02/04 23:23:12 sking Exp $";

   SnmpInt() {
      this.a = 0L;
   }

   SnmpInt(SnmpInt var1) {
      this.a = var1.a;
   }

   public SnmpInt(int var1) {
      this.a = (long)var1;
   }

   public SnmpInt(long var1) {
      this.a = var1;
   }

   public SnmpInt(Integer var1) {
      this.a = var1.longValue();
   }

   public SnmpInt(Long var1) {
      this.a = var1;
   }

   public SnmpInt(boolean var1) {
      this.a = var1 ? 1L : 0L;
   }

   public SnmpInt(String var1) throws SnmpValueException {
      this.fromString(var1);
   }

   public synchronized void fromString(String var1) throws SnmpValueException {
      boolean var5 = SnmpValue.b;

      try {
         this.a = Long.parseLong(var1);
      } catch (NumberFormatException var7) {
         String var2;
         if (!var1.startsWith("'") || !var1.endsWith(a("\u0018`")) && !var1.endsWith(a("\u0018@"))) {
            if (var1.startsWith("'") && (var1.endsWith(a("\u0018j")) || var1.endsWith(a("\u0018J")))) {
               var2 = var1.substring(1, var1.length() - 2);

               String var10000;
               while(true) {
                  if (var2.length() % 8 != 0) {
                     var10000 = var2 + "0";
                     if (var5) {
                        break;
                     }

                     var2 = var10000;
                     if (!var5) {
                        continue;
                     }
                  }

                  var10000 = var2;
                  break;
               }

               int var3 = var10000.length();
               this.a = 0L;
               int var4 = 0;

               while(var4 < var3) {
                  label56: {
                     if (var2.charAt(var4) == '1') {
                        this.a |= (long)(1 << var2.length() - var4 - 1);
                        if (!var5) {
                           break label56;
                        }
                     }

                     if (var2.charAt(var4) != '0') {
                        throw new SnmpValueException(a("vF=i\u0012VLkJ\u0017QI9q^{A,a\n\u001f\u000f") + var2 + "'");
                     }
                  }

                  ++var4;
                  if (var5) {
                     break;
                  }
               }

            } else if (SnmpFramework.isBooleanToIntSupported() && var1.equalsIgnoreCase(a("KZ>m"))) {
               this.a = 1L;
            } else if (SnmpFramework.isBooleanToIntSupported() && var1.equalsIgnoreCase(a("YI'{\u001b"))) {
               this.a = 0L;
            } else {
               throw new SnmpValueException(a("vF=i\u0012VLkA\u0010KM,m\f\u001f\u000f") + var1 + "'");
            }
         } else {
            var2 = var1.substring(1, var1.length() - 2);

            try {
               this.a = (long)Integer.decode(a("\u000fP") + var2);
            } catch (NumberFormatException var6) {
               throw new SnmpValueException(a("vF=i\u0012VLk@\u001bG\b\u0018|\fVF,(Y") + var2 + "'");
            }
         }
      }
   }

   public SnmpOid toIndexOid(boolean var1) {
      return new SnmpOid(this.a);
   }

   public void appendIndexOid(SnmpOid var1, boolean var2) {
      var1.append(this.a);
   }

   public int fromIndexOid(SnmpOid var1, int var2, boolean var3, int var4) throws SnmpValueException {
      if (var2 + 1 > var1.getLength()) {
         throw new SnmpValueException(a("vF=i\u0012VLka\u0010[M3(1vlkd\u001bQO?`D\u001f\u0019"));
      } else {
         this.a = var1.get(var2);
         return var2 + 1;
      }
   }

   public synchronized Object clone() {
      return new SnmpInt(this.a);
   }

   public String getTypeName() {
      return a("vF?m\u0019ZZ");
   }

   public boolean booleanValue() {
      return this.a != 0L;
   }

   public byte byteValue() {
      return (byte)((int)this.a);
   }

   public int intValue() {
      return (int)this.a;
   }

   public long longValue() {
      return this.a;
   }

   public String getString() {
      return this.toString();
   }

   public long[] toLongArray() {
      return new long[]{this.a};
   }

   public Integer toInteger() {
      return new Integer((int)this.a);
   }

   public Long toLong() {
      return new Long(this.a);
   }

   public String toString() {
      return Long.toString(this.a);
   }

   public void toString(StringBuffer var1) {
      var1.append(Long.toString(this.a));
   }

   public boolean equals(Object var1) {
      try {
         return this.compareTo(var1) == 0;
      } catch (ClassCastException var3) {
         return false;
      }
   }

   public int compareTo(Object var1) throws ClassCastException {
      if (var1 instanceof SnmpInt) {
         return this.compareTo(((SnmpInt)var1).a);
      } else if (var1 instanceof Number) {
         return this.compareTo((long)((Number)var1).intValue());
      } else {
         throw new ClassCastException(a("qG?(\u001fQ\b\u0018f\u0013Oa%|\u0002vF?m\u0019ZZ"));
      }
   }

   public int compareTo(long var1) {
      if (this.a < var1) {
         return -1;
      } else {
         return this.a > var1 ? 1 : 0;
      }
   }

   public long getLongValue() {
      return this.a;
   }

   public int getType() {
      return 2;
   }

   public int getTag() {
      return 2;
   }

   public int getCoder() {
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
               var10003 = 63;
               break;
            case 1:
               var10003 = 40;
               break;
            case 2:
               var10003 = 75;
               break;
            case 3:
               var10003 = 8;
               break;
            default:
               var10003 = 126;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
