package monfox.toolkit.snmp.v1;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERCoder;
import monfox.toolkit.snmp.ber.BERException;
import monfox.toolkit.snmp.engine.SnmpCoderException;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageModule;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpPDUCoder;
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class V1SnmpMessageModule implements SnmpMessageModule {
   private SnmpPDUCoder a;
   private SnmpMetadata b;
   private static final String c = "$Id: V1SnmpMessageModule.java,v 1.6 2002/02/05 23:18:05 sking Exp $";
   public static int d;

   public V1SnmpMessageModule() {
      this((SnmpMetadata)null);
   }

   public V1SnmpMessageModule(SnmpMetadata var1) {
      this.a = null;
      this.b = null;
      this.b = var1;
      this.a = new SnmpPDUCoder(var1);
   }

   public SnmpMessage decodeMessage(int var1, BERBuffer var2) throws SnmpCoderException {
      int var8 = d;

      SnmpMessage var10000;
      try {
         byte[] var3 = BERCoder.decodeString(var2, 4);
         int var4 = var2.getIndex();
         SnmpPDU var5 = this.a.decodePDU(var2);
         var5.setCommunity(var3);
         var5.setVersion(var1);
         SnmpMessage var6 = new SnmpMessage();
         var6.setData(var5);
         var6.setVersion(var1);
         var6.setMsgID(var5.getRequestId());
         V1SnmpSecurityParameters var7 = new V1SnmpSecurityParameters(var3);
         var6.setSecurityParameters(var7);
         var10000 = var6;
      } catch (BERException var9) {
         throw new SnmpCoderException(var9.getMessage());
      }

      if (var8 != 0) {
         SnmpException.b = !SnmpException.b;
      }

      return var10000;
   }

   public BERBuffer encodeMessage(SnmpMessage var1, int var2) throws SnmpCoderException {
      int var6 = d;

      BERBuffer var10000;
      try {
         BERBuffer var5;
         label22: {
            SnmpMessageProfile var3 = var1.getMessageProfile();
            SnmpSecurityParameters var4 = var1.getSecurityParameters();
            var5 = new BERBuffer();
            this.a.encodePDU(var5, var1.getData(), var2);
            if (var3 != null) {
               BERCoder.encodeString(var5, var3.getSecurityNameBytes(), 4);
               if (var6 == 0) {
                  break label22;
               }
            }

            if (var4 != null) {
               BERCoder.encodeString(var5, var4.getSecurityName(), 4);
            }
         }

         BERCoder.encodeInteger(var5, (long)var1.getVersion(), 2);
         BERCoder.encodeLength(var5);
         BERCoder.encodeTag(var5, 48);
         var10000 = var5;
      } catch (BERException var7) {
         throw new SnmpCoderException(var7.getMessage());
      }

      if (SnmpException.b) {
         ++var6;
         d = var6;
      }

      return var10000;
   }
}
