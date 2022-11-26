package monfox.toolkit.snmp.engine;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.util.ByteFormatter;

public class SnmpBerUtil {
   private SnmpPDUCoder a;
   private Logger b;

   public SnmpBerUtil() {
      this((SnmpMetadata)null);
   }

   public SnmpBerUtil(SnmpMetadata var1) {
      this.a = null;
      this.b = null;
      this.a = new SnmpPDUCoder(var1);
      this.b = Logger.getInstance(a("KUEm2}I}i\u0019t"));
   }

   public byte[] encodeVarBind(SnmpVarBind var1) throws SnmpValueException {
      try {
         BERBuffer var2 = new BERBuffer();
         this.a.a(var2, var1, true);
         return var2.getBytes();
      } catch (BERException var3) {
         throw new SnmpValueException(var3.toString());
      }
   }

   public byte[] encodeVarBindList(SnmpVarBindList var1) throws SnmpValueException {
      try {
         BERBuffer var2 = new BERBuffer();
         this.a.a(var2, var1, true);
         return var2.getBytes();
      } catch (BERException var3) {
         throw new SnmpValueException(var3.toString());
      }
   }

   public SnmpVarBind decodeVarBind(byte[] var1) throws SnmpValueException {
      try {
         if (this.b.isDebugEnabled()) {
            this.b.debug(a("\\~kR4Quo=&yIjt\u001e|"));
            this.b.debug(ByteFormatter.toString(var1));
         }

         BERBuffer var2 = new BERBuffer(var1);
         SnmpVarBind var3 = this.a.b(var2);
         if (this.b.isDebugEnabled()) {
            this.b.debug(a("J~{H<L\u0001\b") + var3);
         }

         return var3;
      } catch (BERException var4) {
         throw new SnmpValueException(var4.toString());
      }
   }

   public SnmpVarBindList decodeVarBindList(byte[] var1) throws SnmpValueException {
      try {
         if (this.b.isDebugEnabled()) {
            this.b.debug(a("\\~kR4Quo=&yIjt\u001e|wAn\u0004"));
            this.b.debug(ByteFormatter.toString(var1));
         }

         BERBuffer var2 = new BERBuffer(var1);
         SnmpVarBindList var3 = this.a.a(var2);
         if (this.b.isDebugEnabled()) {
            this.b.debug(a("J~{H<L\u0001\b"));
            this.b.debug(var3);
         }

         return var3;
      } catch (BERException var4) {
         if (this.b.isDebugEnabled()) {
            this.b.error(a("Z~z=\u0014}XGy\u0019v\\\bx\b{^Xi\u0019wU"), var4);
         }

         throw new SnmpValueException(var4.toString());
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
               var10003 = 24;
               break;
            case 1:
               var10003 = 59;
               break;
            case 2:
               var10003 = 40;
               break;
            case 3:
               var10003 = 29;
               break;
            default:
               var10003 = 112;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
