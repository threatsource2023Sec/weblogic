package monfox.toolkit.snmp.agent.usm;

import java.math.BigInteger;
import java.security.SecureRandom;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.util.ByteFormatter;
import monfox.toolkit.snmp.util.CryptoUtil;
import monfox.toolkit.snmp.util.PBKDF2;
import monfox.toolkit.snmp.v3.usm.USMAuthProtocolSpec;
import monfox.toolkit.snmp.v3.usm.USMLocalizedUserData;
import monfox.toolkit.snmp.v3.usm.USMPrivProtocolSpec;

public class UsmDHKickstartTable extends SnmpMibTable implements SnmpMibLeafFactory {
   public static byte[] DEFAULT_PRIV_SALT = new byte[]{-47, 49, 11, -90};
   public static byte[] DEFAULT_AUTH_SALT = new byte[]{-104, -33, -75, -84};
   private byte[] a;
   private byte[] b;
   private UsmDH c;
   private SecureRandom d;
   private int e;
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private Logger l;

   UsmDHKickstartTable(UsmDH var1) throws SnmpValueException, SnmpMibException {
      super(UsmDH.usmDHKickstartTable);
      this.a = DEFAULT_AUTH_SALT;
      this.b = DEFAULT_PRIV_SALT;
      this.d = new SecureRandom();
      this.e = 1;
      this.l = Logger.getInstance(a("\u0007$\u001a\u001e\u000f"), a("\u00020\u0011\u001d\u000bn\"\u0007\u001e"), a("\u0016\u00049\u0017\u0017\b\u001e78,7\u0016&'\u000b\"\u001586"));
      this.isSettableWhenActive(true);
      this.c = var1;
   }

   public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpMibException, SnmpValueException {
      return new SnmpMibLeaf(var2, var3);
   }

   public void setAuthSalt(byte[] var1) {
      this.a = var1;
   }

   public byte[] getAuthSalt() {
      return this.a;
   }

   public void setPrivSalt(byte[] var1) {
      this.b = var1;
   }

   public byte[] getPrivSalt() {
      return this.b;
   }

   void a(String var1, BigInteger var2) throws SnmpMibException {
      USMLocalizedUserData var3 = this.c.getUser(var1);
      if (var3 == null) {
         throw new SnmpMibException(a("-\u0018t * \u001ft&,&\u0005tt") + var1 + a("dW1+60\u0003'}"));
      } else {
         this.a(var1, var2, var3);
      }
   }

   void a(String var1, BigInteger var2, int var3, int var4) throws SnmpMibException, SnmpValueException {
      if (this.c.getUser(var1) != null) {
         throw new SnmpMibException(a("6\u00041!\u007fd") + var1 + a("dW5?-&\u00160*\u007f&\u000f= +0Y"));
      } else {
         USMLocalizedUserData var5 = new USMLocalizedUserData(var1, 3, var3, var4, (byte[])null, (byte[])null);
         this.c.addUser(var5);
         this.a(var1, var2, var5);
      }
   }

   private void a(String var1, BigInteger var2, USMLocalizedUserData var3) throws SnmpMibException {
      try {
         BigInteger var4 = UsmDHParameters.DEFAULT_PRIME;
         BigInteger var5 = UsmDHParameters.DEFAULT_BASE;
         int var6 = UsmDHParameters.DEFAULT_LENGTH;
         BigInteger var7 = null;

         while(true) {
            var7 = new BigInteger(var6, this.d);
            int var10000 = var7.compareTo(var4);

            while(var10000 < 0) {
               BigInteger var8 = var5.modPow(var7, var4);
               SnmpMibTableRow var9 = this.addRow(new SnmpValue[]{new SnmpInt(this.e++)});
               SnmpMibLeaf var10 = var9.getLeaf(2);
               SnmpMibLeaf var11 = var9.getLeaf(3);
               SnmpMibLeaf var12 = var9.getLeaf(4);
               var10.setValue((SnmpValue)(new SnmpString(CryptoUtil.encodeUnsigned(var8))));
               var11.setValue((SnmpValue)(new SnmpString(CryptoUtil.encodeUnsigned(var2))));
               var12.setValue((SnmpValue)(new SnmpString(var1)));
               BigInteger var13 = var2.modPow(var7, var4);
               byte[] var14 = CryptoUtil.encodeUnsigned(var13);
               int var15 = var3.getAuthProtocol();
               USMAuthProtocolSpec var16 = USMAuthProtocolSpec.getSpec(var15);
               int var17 = var16.getDigestLength();
               int var18 = var3.getPrivProtocol();
               USMPrivProtocolSpec var19 = USMPrivProtocolSpec.getSpec(var18);
               int var20 = var19.getKeyLength();
               PBKDF2 var21 = new PBKDF2(a("\u0010?\u0015"));
               byte[] var22 = var21.passwd2key(var14, this.getAuthSalt(), 500, var17);
               byte[] var23 = var21.passwd2key(var14, this.getPrivSalt(), 500, var20);
               var10000 = this.l.isConfigEnabled();
               if (!Usm.q) {
                  if (var10000 != 0) {
                     this.l.config(a("\n\u0019='6\"\u001b=)6-\u0010t\u00176%\u0011=6r\u000b\u00128?2\"\u0019t\u00186 \u001c''>1\u0003t\u0003>1\u001696+&\u0005'Y\u007fcW! :1Z:22&Wts\u007fcMt") + var1 + a("IWts>$\u0012:'r1\u0016:70.Wti\u007f") + ByteFormatter.toHexString(CryptoUtil.encodeUnsigned(var7)) + a("IWts>$\u0012:'r3\u00026?6 Wti\u007f") + ByteFormatter.toHexString(CryptoUtil.encodeUnsigned(var8)) + a("IWts2$\u0005y#*!\u001b=0\u007fcWti\u007f") + ByteFormatter.toHexString(CryptoUtil.encodeUnsigned(var2)) + a("IWts,+\u0016&6;n\u000410-&\u0003ti\u007f") + ByteFormatter.toHexString(var14) + a("IWts>6\u0003<~4&\u000ets\u007fcWti\u007f") + ByteFormatter.toHexString(var22) + a("IWts/1\u001e\"~4&\u000ets\u007fcWti\u007f") + ByteFormatter.toHexString(var23));
                  }

                  var3.setLocalizedAuthKey(var22);
                  var3.setLocalizedPrivKey(var23);
                  return;
               }
            }
         }
      } catch (Exception var24) {
         this.l.error(a("&\u0005&<-c\u001e:s6-\u001e :>/\u001e.:1$W:6(c3\u001cs\u0014&\u000e"), var24);
         throw new SnmpMibException(a(" \u0016:=07W7!:\"\u00031s\u001b\u000bW?6&c\u00163!:&\u001a1=+c_") + var24 + ")");
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 67;
               break;
            case 1:
               var10003 = 119;
               break;
            case 2:
               var10003 = 84;
               break;
            case 3:
               var10003 = 83;
               break;
            default:
               var10003 = 95;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
