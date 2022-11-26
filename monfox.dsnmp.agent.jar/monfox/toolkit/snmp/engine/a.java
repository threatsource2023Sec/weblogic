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

/** @deprecated */
class a implements SnmpCoder {
   private SnmpMetadata a;
   private static Logger b = null;
   private static char[] c = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static SnmpNull d = new SnmpNull();
   private static final String e = "$Id: BERSnmpCoder.java,v 1.11 2008/12/04 17:21:22 sking Exp $";

   public a() {
      this((SnmpMetadata)null);
   }

   public a(SnmpMetadata var1) {
      this.a = null;
      this.a = var1;
      if (b == null) {
         b = Logger.getInstance(a("\u0007\u0000];9(5L\u00073 7"));
      }

   }

   public SnmpPDU decodeMessage(SnmpBuffer var1) throws SnmpCoderException {
      try {
         BERBuffer var2 = new BERBuffer(var1.data, var1.offset, var1.length);
         BERCoder.expectTag(var2, 48);
         int var3 = BERCoder.getLength(var2);
         int var4 = var2.getIndex();
         int var5 = (int)BERCoder.decodeInteger(var2, 2);
         byte[] var6 = BERCoder.decodeString(var2, 4);
         int var7 = var2.getIndex();
         SnmpPDU var8 = this.a(var2);
         var8.setVersion(var5);
         var8.setCommunity(var6);
         return var8;
      } catch (BERException var9) {
         throw new SnmpCoderException(var9.getMessage());
      }
   }

   private SnmpPDU a(BERBuffer var1) throws SnmpCoderException {
      try {
         int var2 = BERCoder.getTag(var1);
         int var3 = BERCoder.getLength(var1);
         if (var2 == 164) {
            return this.c(var2, var3, var1);
         } else {
            return (SnmpPDU)(var2 == 165 ? this.b(var2, var3, var1) : this.a(var2, var3, var1));
         }
      } catch (BERException var4) {
         b.warn(a("! l\u00073 \u0015K="), var4);
         throw new SnmpCoderException(var4.getMessage());
      }
   }

   private SnmpRequestPDU a(int var1, int var2, BERBuffer var3) throws BERException {
      SnmpRequestPDU var4 = new SnmpRequestPDU();
      var4.setType(var1);
      var4.setRequestId((int)BERCoder.decodeInteger(var3, 2));
      var4.setErrorStatus((int)BERCoder.decodeInteger(var3, 2));
      var4.setErrorIndex((int)BERCoder.decodeInteger(var3, 2));
      if (b.isDebugEnabled()) {
         b.debug(a("ie}\r&0 |\u001c\u001e!x") + var4.getRequestId());
         b.debug(a("iej\u001a%*7\\\u001c610|U") + var4.getErrorStatus());
         b.debug(a("iej\u001a%*7F\u00063 =2") + var4.getErrorIndex());
      }

      var4.setVarBindList(this.b(var3));
      return var4;
   }

   private SnmpBulkPDU b(int var1, int var2, BERBuffer var3) throws BERException {
      SnmpBulkPDU var4 = new SnmpBulkPDU();
      var4.setType(var1);
      var4.setRequestId((int)BERCoder.decodeInteger(var3, 2));
      var4.setNonRepeaters((int)BERCoder.decodeInteger(var3, 2));
      var4.setMaxRepetitions((int)BERCoder.decodeInteger(var3, 2));
      var4.setVarBindList(this.b(var3));
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
      var4.setVarBindList(this.b(var3));
      return var4;
   }

   private SnmpVarBindList b(BERBuffer var1) throws BERException {
      boolean var5 = SnmpPDU.i;
      BERCoder.expectTag(var1, 48);
      BERCoder.getLength(var1);
      boolean var2 = false;
      SnmpVarBindList var3 = new SnmpVarBindList();

      SnmpVarBindList var10000;
      while(true) {
         if (var1.hasMoreData()) {
            SnmpVarBind var4 = this.c(var1);
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

   private SnmpVarBind c(BERBuffer var1) throws BERException {
      BERCoder.expectTag(var1, 48);
      BERCoder.getLength(var1);
      SnmpOid var2 = BERCoder.decodeSnmpOid(var1, 6);
      var2.setMetadata(this.a);
      int var3 = BERCoder.getTag(var1);
      Object var4 = null;
      SnmpNull var5;
      switch (var3) {
         case 2:
            var4 = new SnmpInt(BERCoder.decodeInteger(var1));
            break;
         case 4:
            var4 = new SnmpString(BERCoder.decodeString(var1));
            break;
         case 5:
            var5 = d;
         case 128:
            var5 = SnmpNull.noSuchObject;
         case 129:
            var5 = SnmpNull.noSuchInstance;
         case 130:
            var4 = SnmpNull.endOfMibView;
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
      }

      return new SnmpVarBind(var2, (SnmpValue)var4, false);
   }

   private static String a(byte[] var0, int var1, int var2) {
      boolean var6 = SnmpPDU.i;
      if (var0 == null) {
         return a("+0c\u0004");
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

   public SnmpBuffer encodeMessage(SnmpPDU var1, int var2) throws SnmpCoderException {
      try {
         BERBuffer var3 = new BERBuffer();
         this.a(var3, var1, var2);
         BERCoder.encodeString(var3, var1.getCommunity(), 4);
         BERCoder.encodeInteger(var3, (long)var1.getVersion(), 2);
         BERCoder.encodeLength(var3);
         BERCoder.encodeTag(var3, 48);
         int var4 = var3.getOffset();
         int var5 = var3.getLength();
         byte[] var6 = var3.getRawData();
         if (b.isDebugEnabled()) {
            b.debug(a(" +l\u00073 \bj\u001b$$\"jH,") + var4 + "," + var5 + "," + var6.length + "," + a(var6, var4, var5) + "}");
         }

         if (var2 > 0 && var5 > var2) {
            throw new SnmpCoderException(a("\b$wFw\u0001${\tw\t a\u000f#-eJ\u00104  k\r3m") + var5 + ">" + var2);
         } else {
            return new SnmpBuffer(var6, var4, var5);
         }
      } catch (BERException var7) {
         throw new SnmpCoderException(var7.getMessage());
      }
   }

   private int a(BERBuffer var1, SnmpPDU var2, int var3) throws SnmpCoderException {
      boolean var5 = SnmpPDU.i;

      try {
         int var4 = 0;
         switch (var2.getType()) {
            case 160:
               var4 += this.a(var1, (SnmpRequestPDU)var2, false, var3);
               if (!var5) {
                  break;
               }
            case 161:
               var4 += this.a(var1, (SnmpRequestPDU)var2, false, var3);
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

   private int b(BERBuffer var1, SnmpPDU var2, int var3) throws SnmpCoderException {
      try {
         int var4 = this.a(var1, var2, var3);
         var4 += BERCoder.encodeString(var1, var2.getCommunity(), 4);
         var4 += BERCoder.encodeInteger(var1, (long)var2.getVersion(), 2);
         var4 += BERCoder.encodeLength(var1, var4);
         var4 += BERCoder.encodeTag(var1, 48);
         return var4;
      } catch (BERException var5) {
         throw new SnmpCoderException(var5.getMessage());
      }
   }

   private int a(BERBuffer var1, SnmpVarBindList var2, boolean var3) throws BERException {
      boolean var6 = SnmpPDU.i;
      if (var2.hasCachedEncoding()) {
         SnmpBuffer var8 = var2.getCachedEncoding();
         var1.add(var8.data, var8.offset, var8.length);
         return var8.length;
      } else {
         int var4;
         label28: {
            var4 = 0;
            if (var2 != null) {
               int var5 = var2.size() - 1;

               while(var5 >= 0) {
                  var4 += this.a(var1, var2.get(var5), var3);
                  --var5;
                  if (var6) {
                     break label28;
                  }

                  if (var6) {
                     break;
                  }
               }
            }

            var4 += BERCoder.encodeLength(var1, var4);
            var4 += BERCoder.encodeTag(var1, 48);
         }

         if (var2.cacheEncoding()) {
            SnmpBuffer var7 = new SnmpBuffer(var1.getRawData(), var1.getOffset(), var4);
            var2.setCachedEncoding(var7);
         }

         return var4;
      }
   }

   private int a(BERBuffer var1, SnmpVarBind var2, boolean var3) throws BERException {
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

      label47: {
         if (var5 == null) {
            var6 += BERCoder.encodeNULL(var1, 5);
            if (!var10) {
               break label47;
            }
         }

         if (var8 == 1) {
            var6 += BERCoder.encodeInteger(var1, (long)((int)var5.getLongValue()), var7);
            if (!var10) {
               break label47;
            }
         }

         if (var8 == 3) {
            var6 += BERCoder.encodeString(var1, var5.getByteArray(), var7);
            if (!var10) {
               break label47;
            }
         }

         if (var8 == 2) {
            SnmpOid var9 = (SnmpOid)var5;
            var6 += BERCoder.encodeOID(var1, var9.getLength(), var5.getLongArray(), var7);
            if (!var10) {
               break label47;
            }
         }

         if (var8 == 4) {
            var6 += BERCoder.encodeNULL(var1, var7);
            if (!var10) {
               break label47;
            }
         }

         var6 += BERCoder.encodeNULL(var1, 5);
      }

      var6 += BERCoder.encodeOID(var1, var4.getLength(), var4.toLongArray(false), 6);
      var6 += BERCoder.encodeLength(var1, var6);
      var6 += BERCoder.encodeTag(var1, 48);
      return var6;
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
               var10003 = 69;
               break;
            case 2:
               var10003 = 15;
               break;
            case 3:
               var10003 = 104;
               break;
            default:
               var10003 = 87;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
