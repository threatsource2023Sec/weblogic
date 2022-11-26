package monfox.toolkit.snmp.engine;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpCounter;
import monfox.toolkit.snmp.SnmpCounter64;
import monfox.toolkit.snmp.SnmpGauge;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpOpaque;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpTimeTicks;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERCoder;
import monfox.toolkit.snmp.ber.BERException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class SnmpPDUCoder {
   private SnmpMetadata a;
   private static boolean b = false;
   private static char[] c = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static Logger d = null;
   private static SnmpNull e = new SnmpNull();
   private static final String f = "$Id: SnmpPDUCoder.java,v 1.17 2008/12/04 17:21:22 sking Exp $";

   public SnmpPDUCoder() {
      this((SnmpMetadata)null);
   }

   public SnmpPDUCoder(SnmpMetadata var1) {
      this.a = null;
      this.a = var1;
      if (d == null) {
         d = Logger.getInstance(a("\u0016_\r)\u0010\u0001d#6$ C"));
      }

   }

   public SnmpPDU decodePDU(BERBuffer var1) throws SnmpCoderException {
      try {
         int var2 = BERCoder.getTag(var1);
         int var3 = BERCoder.getLength(var1);
         if (var2 == 164) {
            return this.c(var2, var3, var1);
         } else {
            return (SnmpPDU)(var2 == 165 ? this.b(var2, var3, var1) : this.a(var2, var3, var1));
         }
      } catch (BERException var4) {
         d.debug(a("!T\u00036$ a$\f"), var4);
         throw new SnmpCoderException(var4.getMessage());
      }
   }

   private SnmpRequestPDU a(int var1, int var2, BERBuffer var3) throws BERException {
      SnmpRequestPDU var4 = new SnmpRequestPDU();
      var4.setType(var1);
      var4.setRequestId((int)BERCoder.decodeInteger(var3, 2));
      var4.setErrorStatus((int)BERCoder.decodeInteger(var3, 2));
      var4.setErrorIndex((int)BERCoder.decodeInteger(var3, 2));
      var4.setVarBindList(this.a(var3));
      return var4;
   }

   private SnmpBulkPDU b(int var1, int var2, BERBuffer var3) throws BERException {
      SnmpBulkPDU var4 = new SnmpBulkPDU();
      var4.setType(var1);
      var4.setRequestId((int)BERCoder.decodeInteger(var3, 2));
      var4.setNonRepeaters((int)BERCoder.decodeInteger(var3, 2));
      var4.setMaxRepetitions((int)BERCoder.decodeInteger(var3, 2));
      var4.setVarBindList(this.a(var3));
      return var4;
   }

   private SnmpTrapPDU c(int var1, int var2, BERBuffer var3) throws BERException {
      SnmpTrapPDU var4 = new SnmpTrapPDU();
      var4.setType(var1);
      SnmpOid var5 = BERCoder.decodeSnmpOid(var3, 6);
      var5.setMetadata(this.a);
      var4.setEnterprise(var5);
      byte[] var6 = BERCoder.decodeString(var3, 64);
      var4.setAgentAddr(new SnmpIpAddress(var6));
      var4.setGenericTrap((int)BERCoder.decodeInteger(var3, 2));
      var4.setSpecificTrap((int)BERCoder.decodeInteger(var3, 2));
      var4.setTimestamp((int)BERCoder.decodeUnsignedInteger(var3, 67));
      var4.setVarBindList(this.a(var3));
      return var4;
   }

   SnmpVarBindList a(BERBuffer var1) throws BERException {
      boolean var5 = SnmpPDU.i;
      BERCoder.expectTag(var1, 48);
      BERCoder.getLength(var1);
      boolean var2 = false;
      SnmpVarBindList var3 = new SnmpVarBindList(this.a);

      SnmpVarBindList var10000;
      while(true) {
         if (var1.hasMoreData()) {
            SnmpVarBind var4 = this.b(var1);
            var10000 = var3;
            if (var5) {
               break;
            }

            var3.add(var4);
            if (!var5) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   SnmpVarBind b(BERBuffer var1) throws BERException {
      BERCoder.expectTag(var1, 48);
      BERCoder.getLength(var1);
      SnmpOid var2 = BERCoder.decodeSnmpOid(var1, 6);
      var2.setMetadata(this.a);
      int var3 = BERCoder.getTag(var1);
      Object var4 = null;
      switch (var3) {
         case 2:
            var4 = new SnmpInt(BERCoder.decodeInteger(var1));
            break;
         case 4:
            var4 = new SnmpString(BERCoder.decodeString(var1));
            break;
         case 5:
            var4 = e;
            BERCoder.decodeNULL(var1);
            break;
         case 6:
            var4 = BERCoder.decodeSnmpOid(var1);
            ((SnmpOid)var4).setMetadata(this.a);
            break;
         case 64:
            var4 = new SnmpIpAddress(BERCoder.decodeString(var1));
            break;
         case 65:
            var4 = new SnmpCounter(BERCoder.decodeUnsignedInteger(var1));
            break;
         case 66:
            var4 = new SnmpGauge(BERCoder.decodeUnsignedInteger(var1));
            break;
         case 67:
            var4 = new SnmpTimeTicks(BERCoder.decodeUnsignedInteger(var1));
            break;
         case 68:
            var4 = new SnmpOpaque(BERCoder.decodeString(var1));
            break;
         case 70:
            var4 = new SnmpCounter64(BERCoder.decodeBigInteger(var1));
            break;
         case 128:
            var4 = SnmpNull.noSuchObject;
            BERCoder.decodeNULL(var1);
            break;
         case 129:
            var4 = SnmpNull.noSuchInstance;
            BERCoder.decodeNULL(var1);
            break;
         case 130:
            var4 = SnmpNull.endOfMibView;
            BERCoder.decodeNULL(var1);
      }

      return new SnmpVarBind(var2, (SnmpValue)var4, false);
   }

   private static String a(byte[] var0, int var1, int var2) {
      boolean var6 = SnmpPDU.i;
      if (var0 == null) {
         return a("+D\f5");
      } else {
         StringBuffer var3 = new StringBuffer();
         int var4 = var1 + var2;
         if (var4 > var0.length) {
            var4 = var0.length;
         }

         int var5 = var1;

         StringBuffer var10000;
         while(true) {
            if (var5 < var4) {
               var3.append(c[var0[var5] >> 4 & 15]);
               var3.append(c[var0[var5] & 15]);
               var10000 = var3.append(" ");
               if (var6) {
                  break;
               }

               ++var5;
               if (!var6) {
                  continue;
               }
            }

            var10000 = var3;
            break;
         }

         return var10000.toString();
      }
   }

   public int encodePDU(BERBuffer var1, SnmpPDU var2, int var3) throws SnmpCoderException {
      boolean var5 = SnmpPDU.i;

      try {
         int var4 = 0;
         switch (var2.getType()) {
            case 160:
               var4 += this.a(var1, (SnmpRequestPDU)var2, b, var3);
               if (!var5) {
                  break;
               }
            case 161:
               var4 += this.a(var1, (SnmpRequestPDU)var2, b, var3);
               if (!var5) {
                  break;
               }
            case 164:
               var4 += this.a(var1, (SnmpTrapPDU)var2, var3);
               if (!var5) {
                  break;
               }
            case 165:
               var4 += this.a(var1, (SnmpBulkPDU)var2, var3);
               if (!var5) {
                  break;
               }
            case 162:
            case 163:
            default:
               var4 += this.a(var1, (SnmpRequestPDU)var2, true, var3);
         }

         return var4;
      } catch (BERException var6) {
         d.debug(a(" _\u00036$ a$\f"), var6);
         throw new SnmpCoderException(var6.getMessage());
      }
   }

   private int a(BERBuffer var1, SnmpTrapPDU var2, int var3) throws BERException {
      int var4 = this.a(var1, var2.getVarBindList(), true);
      var4 += BERCoder.encodeInteger(var1, (long)((int)var2.getTimestamp()), 67);
      var4 += BERCoder.encodeInteger(var1, (long)var2.getSpecificTrap(), 2);
      var4 += BERCoder.encodeInteger(var1, (long)var2.getGenericTrap(), 2);
      var4 += BERCoder.encodeString(var1, var2.getAgentAddr().toByteArray(), 64);
      var4 += BERCoder.encodeOID(var1, var2.getEnterprise().getLength(), var2.getEnterprise().getLongArray(), 6);
      var4 += BERCoder.encodeLength(var1, var4);
      var4 += BERCoder.encodeTag(var1, var2.getType());
      return var4;
   }

   private int a(BERBuffer var1, SnmpBulkPDU var2, int var3) throws BERException {
      int var4 = this.a(var1, var2.getVarBindList(), false);
      var4 += BERCoder.encodeInteger(var1, (long)var2.getMaxRepetitions(), 2);
      var4 += BERCoder.encodeInteger(var1, (long)var2.getNonRepeaters(), 2);
      var4 += BERCoder.encodeInteger(var1, (long)var2.getRequestId(), 2);
      var4 += BERCoder.encodeLength(var1, var4);
      var4 += BERCoder.encodeTag(var1, var2.getType());
      return var4;
   }

   private int a(BERBuffer var1, SnmpRequestPDU var2, boolean var3, int var4) throws BERException {
      int var5 = this.a(var1, var2.getVarBindList(), var3);
      var5 += BERCoder.encodeInteger(var1, (long)var2.getErrorIndex(), 2);
      var5 += BERCoder.encodeInteger(var1, (long)var2.getErrorStatus(), 2);
      var5 += BERCoder.encodeInteger(var1, (long)var2.getRequestId(), 2);
      var5 += BERCoder.encodeLength(var1, var5);
      var5 += BERCoder.encodeTag(var1, var2.getType());
      return var5;
   }

   private int a(BERBuffer var1, SnmpPDU var2, int var3) throws SnmpCoderException {
      try {
         int var4 = this.encodePDU(var1, var2, var3);
         var4 += BERCoder.encodeString(var1, var2.getCommunity(), 4);
         var4 += BERCoder.encodeInteger(var1, (long)var2.getVersion(), 2);
         var4 += BERCoder.encodeLength(var1, var4);
         var4 += BERCoder.encodeTag(var1, 48);
         return var4;
      } catch (BERException var5) {
         d.debug(a(" _\u00036$ |\u0005*3$V\u0005"), var5);
         throw new SnmpCoderException(var5.getMessage());
      }
   }

   int a(BERBuffer var1, SnmpVarBindList var2, boolean var3) throws BERException {
      boolean var6 = SnmpPDU.i;
      if (var2 != null && var2.hasCachedEncoding()) {
         SnmpBuffer var7 = var2.getCachedEncoding();
         var1.add(var7.data, var7.offset, var7.length);
         return var7.length;
      } else {
         int var10000;
         int var4;
         label41: {
            var4 = 0;
            if (var2 != null) {
               int var5 = var2.size() - 1;

               while(var5 >= 0) {
                  var10000 = var4;
                  if (var6) {
                     break label41;
                  }

                  var4 += this.a(var1, var2.get(var5), var3 || var2.getEncodeGetValues());
                  --var5;
                  if (var6) {
                     break;
                  }
               }
            }

            var4 += BERCoder.encodeLength(var1, var4);
            var10000 = var4 + BERCoder.encodeTag(var1, 48);
         }

         var4 = var10000;
         if (var2 != null && var2.cacheEncoding()) {
            SnmpBuffer var8 = new SnmpBuffer(var1.getRawData(), var1.getOffset(), var4);
            var2.setCachedEncoding(var8);
         }

         return var4;
      }
   }

   int a(BERBuffer var1, SnmpVarBind var2, boolean var3) throws BERException {
      boolean var10 = SnmpPDU.i;
      SnmpOid var4 = var2.getOid();
      SnmpValue var5 = var3 ? var2.getValue() : null;
      int var6 = 0;
      int var7 = 5;
      int var8 = 4;
      if (var5 != null) {
         var7 = var5.getTag();
         var8 = var5.getCoder();
      }

      label65: {
         if (var5 == null) {
            var6 += BERCoder.encodeNULL(var1, 5);
            if (!var10) {
               break label65;
            }
         }

         if (var8 == 1) {
            var6 += BERCoder.encodeInteger(var1, var5.getLongValue(), var7);
            if (!var10) {
               break label65;
            }
         }

         if (var8 == 5) {
            var6 += BERCoder.encodeInteger(var1, var5.getLongValue(), 4, false, var7);
            if (!var10) {
               break label65;
            }
         }

         if (var8 == 6) {
            var6 += BERCoder.encodeInteger(var1, var5.getLongValue(), 8, true, var7);
            if (!var10) {
               break label65;
            }
         }

         if (var8 == 7) {
            var6 += BERCoder.encodeBigInteger(var1, var5.getBigInteger(), 8, false, var7);
            if (!var10) {
               break label65;
            }
         }

         if (var8 == 3) {
            var6 += BERCoder.encodeString(var1, var5.getByteArray(), var7);
            if (!var10) {
               break label65;
            }
         }

         if (var8 == 2) {
            SnmpOid var9 = (SnmpOid)var5;
            var6 += BERCoder.encodeOID(var1, var9.getLength(), var5.getLongArray(), var7);
            if (!var10) {
               break label65;
            }
         }

         if (var8 == 4) {
            var6 += BERCoder.encodeNULL(var1, var7);
            if (!var10) {
               break label65;
            }
         }

         var6 += BERCoder.encodeNULL(var1, 5);
      }

      var6 += BERCoder.encodeOID(var1, var4.getLength(), var4.toLongArray(false), 6);
      var6 += BERCoder.encodeLength(var1, var6);
      var6 += BERCoder.encodeTag(var1, 48);
      return var6;
   }

   public static void setEncodeValues(boolean var0) {
      b = var0;
   }

   public static boolean getEncodeValues() {
      return b;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 69;
               break;
            case 1:
               var10003 = 49;
               break;
            case 2:
               var10003 = 96;
               break;
            case 3:
               var10003 = 89;
               break;
            default:
               var10003 = 64;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
