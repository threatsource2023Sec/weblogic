package monfox.toolkit.snmp.agent.usm;

import java.math.BigInteger;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERCoder;

public class UsmDHParameters extends SnmpMibLeaf {
   public static byte[] DEFAULT_PRIME_BYTES = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -55, 15, -38, -94, 33, 104, -62, 52, -60, -58, 98, -117, -128, -36, 28, -47, 41, 2, 78, 8, -118, 103, -52, 116, 2, 11, -66, -90, 59, 19, -101, 34, 81, 74, 8, 121, -114, 52, 4, -35, -17, -107, 25, -77, -51, 58, 67, 27, 48, 43, 10, 109, -14, 95, 20, 55, 79, -31, 53, 109, 109, 81, -62, 69, -28, -123, -75, 118, 98, 94, 126, -58, -12, 76, 66, -23, -90, 55, -19, 107, 11, -1, 92, -74, -12, 6, -73, -19, -18, 56, 107, -5, 90, -119, -97, -91, -82, -97, 36, 17, 124, 75, 31, -26, 73, 40, 102, 81, -20, -26, 83, -127, -1, -1, -1, -1, -1, -1, -1, -1};
   public static BigInteger DEFAULT_PRIME;
   public static BigInteger DEFAULT_BASE;
   public static int DEFAULT_LENGTH;
   private UsmDHUserKeyTable a = null;
   private BigInteger b;
   private BigInteger c;
   private int _access;
   private Logger d;

   public UsmDHParameters(SnmpOid var1, UsmDHUserKeyTable var2) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.b = DEFAULT_PRIME;
      this.c = DEFAULT_BASE;
      this._access = DEFAULT_LENGTH;
      this.d = Logger.getInstance(a("_7ml\u0018"), a("N7n"), a("N\u0017Ne\u0000K\u0005Q@%~\u0010FS;"));
      this.a(DEFAULT_PRIME, DEFAULT_BASE, DEFAULT_LENGTH);
      this.a = var2;
   }

   private void a(BigInteger var1, BigInteger var2, int var3) throws SnmpValueException {
      try {
         BERBuffer var4 = new BERBuffer();
         BERCoder.encodeInteger(var4, (long)DEFAULT_LENGTH, 2);
         BERCoder.encodeBigInteger(var4, DEFAULT_BASE, 2);
         BERCoder.encodeBigInteger(var4, DEFAULT_PRIME, 2);
         BERCoder.encodeLength(var4);
         BERCoder.encodeTag(var4, 48);
         byte[] var5 = var4.getBytes();
         this.setValue(new SnmpString(var5));
      } catch (Exception var6) {
         this.d.error(a("~\n@N,r\nD\u0001-i\u0016LS"), var6);
         throw new SnmpValueException(a("~\n@N,r\nD\u0001-i\u0016LSr") + var6.getMessage());
      }
   }

   public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      int var4 = super.prepareSetRequest(var1, var2, var3);
      if (var4 != 0) {
         return var4;
      } else {
         SnmpValue var5 = var3.getValue();
         if (var5 != null && var5 instanceof SnmpString) {
            byte[] var6 = ((SnmpString)var5).toByteArray();

            try {
               BERBuffer var7 = new BERBuffer(var6);
               BERCoder.expectTag(var7, 48);
               BERCoder.getLength(var7);
               BigInteger var8 = BERCoder.decodeBigInteger(var7, 2);
               BigInteger var9 = BERCoder.decodeBigInteger(var7, 2);
               int var10 = (int)BERCoder.decodeInteger(var7, 2);
               DHP var11 = new DHP(var8, var9, var10);
               var1.setUserObject(var2, var11);
               return 0;
            } catch (Exception var12) {
               this.d.error(a("_,\u0003Q)i\u0005ND<~\u0016\u0003E-x\u000bGDh~\u0016QN:!"), var12);
               return 5;
            }
         } else {
            return 10;
         }
      }
   }

   public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      try {
         DHP var4 = (DHP)var1.getUserObject(var2);
         if (var4 == null) {
            this.d.error(a("v\rPR!u\u0003\u0003@$|\u000bQH<s\t\u0003Q)i\u0005ND<~\u0016P"));
            return false;
         } else {
            this.b = var4.a;
            this.c = var4.b;
            this._access = var4.c;
            this.setValue(var3.getValue());
            this.a.a();
            return true;
         }
      } catch (SnmpValueException var5) {
         this.d.error(a("x\u000bNL!o7FU\u001a~\u0015VD;oDFS:t\u0016"), var5);
         return false;
      }
   }

   public BigInteger getPrime() {
      return this.b;
   }

   public BigInteger getBase() {
      return this.c;
   }

   public int getPrivLength() {
      return this._access;
   }

   static {
      DEFAULT_PRIME = new BigInteger(1, DEFAULT_PRIME_BYTES);
      DEFAULT_BASE = BigInteger.valueOf(2L);
      DEFAULT_LENGTH = 1024;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 27;
               break;
            case 1:
               var10003 = 100;
               break;
            case 2:
               var10003 = 35;
               break;
            case 3:
               var10003 = 33;
               break;
            default:
               var10003 = 72;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class DHP {
      BigInteger a;
      BigInteger b;
      int c;

      DHP(BigInteger var2, BigInteger var3, int var4) {
         this.a = UsmDHParameters.DEFAULT_PRIME;
         this.b = UsmDHParameters.DEFAULT_BASE;
         this.c = UsmDHParameters.DEFAULT_LENGTH;
         this.a = var2;
         this.b = var3;
         this.c = var4;
      }
   }
}
