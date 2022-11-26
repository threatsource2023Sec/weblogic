package monfox.toolkit.snmp.engine;

import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERCoder;
import monfox.toolkit.snmp.ber.BERException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.v1.V1SnmpMessageModule;
import monfox.toolkit.snmp.v2.V2SnmpMessageModule;
import monfox.toolkit.snmp.v3.V3SnmpMessageModule;

public class SnmpMessageProcessor {
   private SnmpEngine a;
   private SnmpMessageModule b;
   private SnmpMessageModule c;
   private SnmpMessageModule d;
   private SnmpMetadata e;
   private static final String f = "$Id: SnmpMessageProcessor.java,v 1.4 2002/04/30 13:58:38 samin Exp $";

   public SnmpMessageProcessor(SnmpEngine var1) {
      this(var1, (SnmpMetadata)null);
   }

   public SnmpMessageProcessor(SnmpEngine var1, SnmpMetadata var2) {
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = null;
      this.e = null;
      this.a = var1;
      this.e = var2;
      this.b = new V1SnmpMessageModule(var2);
      this.c = new V2SnmpMessageModule(var2);
      this.d = new V3SnmpMessageModule(var2, this.a);
   }

   public SnmpMessageModule getMessageModule(int var1) {
      switch (var1) {
         case 0:
            return this.b;
         case 1:
            return this.c;
         case 2:
         default:
            return null;
         case 3:
            return this.d;
      }
   }

   public SnmpMessage decodeMessage(SnmpBuffer var1) throws SnmpCoderException {
      try {
         BERBuffer var2 = new BERBuffer(var1.data, var1.offset, var1.length);
         BERCoder.expectTag(var2, 48);
         int var3 = BERCoder.getLength(var2);
         int var4 = var2.getIndex();
         int var5 = (int)BERCoder.decodeInteger(var2, 2);
         SnmpMessage var6 = null;
         switch (var5) {
            case 0:
               var6 = this.b.decodeMessage(var5, var2);
               break;
            case 1:
               var6 = this.c.decodeMessage(var5, var2);
               break;
            case 2:
            default:
               throw new SnmpBadVersionException(var5);
            case 3:
               var6 = this.d.decodeMessage(var5, var2);
         }

         return var6;
      } catch (BERException var7) {
         throw new SnmpCoderException(var7.getMessage());
      }
   }

   public SnmpBuffer encodeMessage(SnmpMessage var1, int var2) throws SnmpCoderException {
      BERBuffer var3 = null;
      switch (var1.getVersion()) {
         case 0:
            var3 = this.b.encodeMessage(var1, var2);
            break;
         case 1:
            var3 = this.c.encodeMessage(var1, var2);
            break;
         case 2:
         default:
            throw new SnmpBadVersionException(var1.getVersion());
         case 3:
            var3 = this.d.encodeMessage(var1, var2);
      }

      int var4 = var3.getOffset();
      int var5 = var3.getLength();
      byte[] var6 = var3.getRawData();
      if (var2 > 0 && var5 > var2) {
         throw new SnmpCoderException(a("\u0013F\u0010[/\u001aF\u001c\u0014/\u0012B\u0006\u0012{6\u0007-\rl;B\f\u0010kv") + var5 + ">" + var2 + ")");
      } else {
         return new SnmpBuffer(var6, var4, var5);
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
               var10003 = 94;
               break;
            case 1:
               var10003 = 39;
               break;
            case 2:
               var10003 = 104;
               break;
            case 3:
               var10003 = 117;
               break;
            default:
               var10003 = 15;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
