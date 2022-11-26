package monfox.toolkit.snmp.ber;

import java.math.BigInteger;
import monfox.toolkit.snmp.SnmpOid;

public final class BERCoder {
   private static g a = new g();
   private static c b = new c();
   private static f c = new f();
   private static e d = new e();
   private static d e = new d();
   private static final String f = "$Id: BERCoder.java,v 1.8 2008/12/04 17:21:22 sking Exp $";

   public static int encodeLength(BERBuffer var0) throws BERException {
      return monfox.toolkit.snmp.ber.b.encodeLength(var0, var0.getEncLength());
   }

   public static int encodeLength(BERBuffer var0, int var1) throws BERException {
      return monfox.toolkit.snmp.ber.b.encodeLength(var0, var1);
   }

   public static int encodeTag(BERBuffer var0, int var1) throws BERException {
      var0.a((byte)var1);
      return 1;
   }

   public static int getLength(BERBuffer var0) throws BERException {
      return monfox.toolkit.snmp.ber.a.getLength(var0);
   }

   public static int getTag(BERBuffer var0) throws BERException {
      return monfox.toolkit.snmp.ber.a.getEntireTagValue(var0);
   }

   public static void expectTag(BERBuffer var0, int var1) throws BERException {
      int var2 = getTag(var0);
      if (var2 != var1) {
         throw new BERException(a(")0\f\u001e=\n63") + var2 + a("Jl") + var1 + a("6\u007f"));
      }
   }

   public static void expectTag(BERBuffer var0, int var1, String var2) throws BERException {
      int var3 = getTag(var0);
      if (var3 != var1) {
         throw new BERException(a(")0\f\u001e=\n63") + var3 + a("Jl") + var1 + a("6k") + var2);
      }
   }

   public static int encodeInteger(BERBuffer var0, long var1) throws BERException {
      return encodeInteger(var0, var1, 4, true);
   }

   public static int encodeInteger(BERBuffer var0, long var1, int var3, boolean var4) throws BERException {
      return b.a(var0, var1, var3, var4);
   }

   public static int encodeInteger(BERBuffer var0, long var1, int var3) throws BERException {
      return encodeInteger(var0, var1, 4, true, var3);
   }

   public static int encodeInteger(BERBuffer var0, long var1, int var3, boolean var4, int var5) throws BERException {
      int var6 = b.a(var0, var1, var3, var4);
      var0.a((byte)var5);
      return var6 + 1;
   }

   public static long decodeInteger(BERBuffer var0) throws BERException {
      return b.a(var0);
   }

   public static long decodeInteger(BERBuffer var0, int var1) throws BERException {
      expectTag(var0, var1);
      return b.a(var0);
   }

   public static long decodeUnsignedInteger(BERBuffer var0) throws BERException {
      return a.a(var0);
   }

   public static long decodeUnsignedInteger(BERBuffer var0, int var1) throws BERException {
      expectTag(var0, var1);
      return a.a(var0);
   }

   public static int encodeUnsignedInteger(BERBuffer var0, long var1) throws BERException {
      return encodeUnsignedInteger(var0, var1, 4, true);
   }

   public static int encodeUnsignedInteger(BERBuffer var0, long var1, int var3, boolean var4) throws BERException {
      return a.a(var0, var1, var3, var4);
   }

   public static int encodeUnsignedInteger(BERBuffer var0, long var1, int var3) throws BERException {
      return encodeUnsignedInteger(var0, var1, 4, true, var3);
   }

   public static int encodeUnsignedInteger(BERBuffer var0, long var1, int var3, boolean var4, int var5) throws BERException {
      int var6 = a.a(var0, var1, var3, var4);
      var0.a((byte)var5);
      return var6 + 1;
   }

   public static int encodeBigInteger(BERBuffer var0, BigInteger var1) throws BERException {
      return encodeBigInteger(var0, var1, 4, true);
   }

   public static int encodeBigInteger(BERBuffer var0, BigInteger var1, int var2, boolean var3) throws BERException {
      return b.a(var0, var1, var2, var3);
   }

   public static int encodeBigInteger(BERBuffer var0, BigInteger var1, int var2) throws BERException {
      return encodeBigInteger(var0, var1, 4, true, var2);
   }

   public static int encodeBigInteger(BERBuffer var0, BigInteger var1, int var2, boolean var3, int var4) throws BERException {
      int var5 = b.a(var0, var1, var2, var3);
      var0.a((byte)var4);
      return var5 + 1;
   }

   public static BigInteger decodeBigInteger(BERBuffer var0) throws BERException {
      return b.b(var0);
   }

   public static BigInteger decodeBigInteger(BERBuffer var0, int var1) throws BERException {
      expectTag(var0, var1);
      return b.b(var0);
   }

   public static int encodeString(BERBuffer var0, byte[] var1, int var2) throws BERException {
      int var3 = c.a(var0, var1);
      var0.a((byte)var2);
      return var3 + 1;
   }

   public static int encodeString(BERBuffer var0, byte[] var1) throws BERException {
      return c.a(var0, var1);
   }

   public static byte[] decodeString(BERBuffer var0) throws BERException {
      return c.a(var0);
   }

   public static byte[] decodeString(BERBuffer var0, int var1) throws BERException {
      expectTag(var0, var1);
      return c.a(var0);
   }

   public static int encodeOID(BERBuffer var0, int var1, long[] var2) throws BERException {
      return d.a(var0, var2, var1);
   }

   public static int encodeOID(BERBuffer var0, long[] var1) throws BERException {
      return d.a(var0, var1);
   }

   public static int encodeOID(BERBuffer var0, int var1, long[] var2, int var3) throws BERException {
      int var4 = d.a(var0, var2, var1);
      var0.a((byte)var3);
      return var4 + 1;
   }

   public static int encodeOID(BERBuffer var0, long[] var1, int var2) throws BERException {
      int var3 = d.a(var0, var1);
      var0.a((byte)var2);
      return var3 + 1;
   }

   public static SnmpOid decodeSnmpOid(BERBuffer var0) throws BERException {
      return d.b(var0);
   }

   public static SnmpOid decodeSnmpOid(BERBuffer var0, int var1) throws BERException {
      expectTag(var0, var1);
      return d.b(var0);
   }

   public static long[] decodeOID(BERBuffer var0) throws BERException {
      return d.a(var0);
   }

   public static long[] decodeOID(BERBuffer var0, int var1) throws BERException {
      expectTag(var0, var1);
      return d.a(var0);
   }

   public static int encodeNULL(BERBuffer var0) throws BERException {
      return e.encode(var0);
   }

   public static int encodeNULL(BERBuffer var0, int var1) throws BERException {
      int var2 = e.encode(var0);
      var0.a((byte)var1);
      return var2 + 1;
   }

   public static void decodeNULL(BERBuffer var0) throws BERException {
      e.decode(var0);
   }

   public static void decodeNULL(BERBuffer var0, int var1) throws BERException {
      expectTag(var0, var1);
      e.decode(var0);
   }

   public static void bypassValue(BERBuffer var0) throws BERException {
      int var1 = monfox.toolkit.snmp.ber.a.getLength(var0);
      var0.setRelativeIndex(var1);
   }

   public static void bypassEntireValue(BERBuffer var0) throws BERException {
      monfox.toolkit.snmp.ber.a.getEntireEncoding(var0);
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
               var10003 = 81;
               break;
            case 2:
               var10003 = 104;
               break;
            case 3:
               var10003 = 62;
               break;
            default:
               var10003 = 105;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
