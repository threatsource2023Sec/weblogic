package monfox.toolkit.snmp.v3.usm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.util.ByteFormatter;

class q extends USMPrivProtocolSpec {
   private static Logger b = Logger.getInstance(a("\u001fHD\u0011\u0012"), a("\r(%\t\u0011\u0016"), a("\u000f_O\u000f\u001d\u000eHG\f02mZ.-/ti3.\bko?"));

   public int getKeyLength() {
      return 32;
   }

   public int getPrivProtocol() {
      return 14832;
   }

   public SnmpOid getPrivProtocolOID() {
      return Snmp.TDES_PRIV_PROTOCOL_OID;
   }

   public byte[] extendLocalizedKey(byte[] var1, byte[] var2, int var3) throws NoSuchAlgorithmException {
      MessageDigest var4 = MessageDigest.getInstance(var3 == 0 ? a("\u0016_?") : a("\bSK"));
      if (this.getKeyLength() > var1.length) {
         byte[] var5 = UsmKeyUtil.generateKey(var1, var3);
         var4.reset();
         var4.update(var5);
         var4.update(var2);
         var4.update(var5);
         byte[] var6 = var4.digest();
         byte[] var7 = new byte[var1.length + var6.length];
         System.arraycopy(var1, 0, var7, 0, var1.length);
         System.arraycopy(var6, 0, var7, var1.length, var6.length);
         b.debug(a("\u000fVZ\u0003\u000e\u0014XK\u0010\u001d\u0010^SfbQ") + ByteFormatter.toString(var7));
         var1 = new byte[32];
         System.arraycopy(var7, 0, var1, 0, 32);
         b.debug(a("\u0017TI\u001d\u000e\u0004PO\u0005x{\u0011") + ByteFormatter.toString(var1));
         return var1;
      } else {
         return var1;
      }
   }

   public USMPrivModule newModule() {
      return new l();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 91;
               break;
            case 1:
               var10003 = 27;
               break;
            case 2:
               var10003 = 10;
               break;
            case 3:
               var10003 = 92;
               break;
            default:
               var10003 = 66;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
