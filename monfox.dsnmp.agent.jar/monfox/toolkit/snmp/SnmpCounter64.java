package monfox.toolkit.snmp;

import java.math.BigInteger;

public class SnmpCounter64 extends SnmpValue {
   static final long serialVersionUID = -5998111581982421757L;
   public static final BigInteger MAX_U64 = new BigInteger(new byte[]{0, -1, -1, -1, -1, -1, -1, -1, -1});
   private BigInteger a;
   private static final String b = "$Id: SnmpCounter64.java,v 1.19 2007/03/15 20:36:48 sking Exp $";

   public SnmpCounter64() throws IllegalArgumentException {
      this.a = BigInteger.ZERO;
   }

   public SnmpCounter64(SnmpCounter64 var1) {
      this.a = var1.a;
   }

   public SnmpCounter64(long var1) throws IllegalArgumentException {
      this.a = this.a(var1);
   }

   public SnmpCounter64(Long var1) throws IllegalArgumentException {
      this.a = this.a(var1);
   }

   public SnmpCounter64(BigInteger var1) throws IllegalArgumentException {
      if (var1.compareTo(MAX_U64) > 0) {
         var1 = var1.and(MAX_U64);
      }

      this.a = var1;
   }

   public SnmpCounter64(String var1) throws SnmpValueException {
      this.fromString(var1);
   }

   public synchronized void fromString(String var1) throws SnmpValueException {
      try {
         BigInteger var2 = new BigInteger(var1);
         if (var2.compareTo(MAX_U64) > 0) {
            var2 = var2.and(MAX_U64);
         }

         this.a = var2;
      } catch (Exception var3) {
         throw new SnmpValueException(a("j\u0017Q\u0017AJ\u001d\u00075BV\u0017S\u0013_\u0015M\u0007Q") + var1 + "'");
      }
   }

   public final synchronized Object clone() {
      return new SnmpCounter64(this.a);
   }

   public final String getTypeName() {
      return a("`\u0016R\u0018YF\u000b\u0011B");
   }

   public synchronized byte byteValue() {
      return (byte)this.a.intValue();
   }

   public synchronized int intValue() {
      return this.a.intValue();
   }

   public synchronized long longValue() {
      return this.a.longValue();
   }

   public synchronized boolean booleanValue() {
      return this.a.longValue() != 0L;
   }

   public Integer toInteger() {
      return new Integer(this.a.intValue());
   }

   public Long toLong() {
      return new Long(this.a.longValue());
   }

   public BigInteger toBigInteger() {
      return this.a;
   }

   public SnmpOid toIndexOid(boolean var1) {
      return new SnmpOid(this.a.longValue());
   }

   public void appendIndexOid(SnmpOid var1, boolean var2) {
      var1.append(this.a.longValue());
   }

   public int fromIndexOid(SnmpOid var1, int var2, boolean var3, int var4) throws SnmpValueException {
      if (var2 + 1 > var1.getLength()) {
         throw new SnmpValueException(a("P\u0011H\u0004Y\u0003\u0010I\u0012H[C") + var1);
      } else {
         long var5 = var1.get(var2);
         this.a = this.a(var5);
         return var2 + 1;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      var1.append(this.a.toString());
   }

   public boolean equals(Object var1) {
      try {
         return this.compareTo(var1) == 0;
      } catch (ClassCastException var3) {
         return false;
      }
   }

   public int compareTo(Object var1) throws ClassCastException {
      if (var1 instanceof SnmpCounter64) {
         return this.a.compareTo(((SnmpCounter64)var1).a);
      } else if (var1 instanceof SnmpInt) {
         return this.compareTo(((SnmpInt)var1).longValue());
      } else if (var1 instanceof Number) {
         return this.compareTo(((Number)var1).longValue());
      } else {
         throw new ClassCastException(a("m\u0016SVLMYt\u0018@S:H\u0003CW\u001cU@\u0019_*I\u001b]j\u0017S\ncV\u0014E\u0013_"));
      }
   }

   public int compareTo(long var1) {
      long var3 = this.a.longValue();
      if (var3 < var1) {
         return -1;
      } else {
         return var3 > var1 ? 1 : 0;
      }
   }

   public long getLongValue() {
      return this.a.longValue();
   }

   public BigInteger getUnsignedValue() {
      return this.a;
   }

   public BigInteger getBigInteger() {
      return this.a;
   }

   private BigInteger a(long var1) {
      return var1 >= 0L ? BigInteger.valueOf(var1) : MAX_U64.add(BigInteger.valueOf(var1 + 1L));
   }

   public int getType() {
      return 70;
   }

   public int getTag() {
      return 70;
   }

   public int getCoder() {
      return 7;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 35;
               break;
            case 1:
               var10003 = 121;
               break;
            case 2:
               var10003 = 39;
               break;
            case 3:
               var10003 = 118;
               break;
            default:
               var10003 = 45;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
