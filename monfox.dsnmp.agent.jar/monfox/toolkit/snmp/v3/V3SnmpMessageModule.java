package monfox.toolkit.snmp.v3;

import java.util.Hashtable;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.ber.BERBuffer;
import monfox.toolkit.snmp.ber.BERCoder;
import monfox.toolkit.snmp.ber.BERException;
import monfox.toolkit.snmp.engine.SnmpCoderException;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageModule;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpPDUCoder;
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.util.ByteFormatter;
import monfox.toolkit.snmp.v3.usm.USMSecurityCoder;

public class V3SnmpMessageModule implements SnmpMessageModule {
   private static final byte[] a = new byte[0];
   private static final byte[] b = new byte[0];
   private int c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;
   private SnmpSecurityCoder i;
   private SnmpPDUCoder j;
   private SnmpMetadata k;
   private Hashtable l;
   private static final String m = "$Id: V3SnmpMessageModule.java,v 1.21 2006/10/27 01:46:46 sking Exp $";
   private static Logger n = null;

   public V3SnmpMessageModule() {
      this((SnmpMetadata)null);
   }

   public V3SnmpMessageModule(SnmpMetadata var1) {
      this(var1, (SnmpEngine)null);
   }

   public V3SnmpMessageModule(SnmpMetadata var1, SnmpEngine var2) {
      this.c = 0;
      this.d = 0;
      this.e = 0;
      this.f = 0;
      this.g = 0;
      this.h = 0;
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = new Hashtable();
      if (n == null) {
         n = Logger.getInstance(a("\u0013\u001fa4o5aW)q$KW\u0017m!Y^?"));
      }

      this.k = var1;
      this.j = new SnmpPDUCoder(var1);
      this.i = new USMSecurityCoder(var2);
   }

   public SnmpMessage decodeMessage(int var1, BERBuffer var2) throws SnmpCoderException {
      int var15 = SnmpSecurityCoderException.g;
      int var3 = 0;
      SnmpSecurityCoder var4 = null;

      try {
         SnmpMessage var5 = new SnmpMessage();
         V3SnmpMessageParameters var6 = new V3SnmpMessageParameters();
         var5.setMessageParameters(var6);
         int var7 = this.a(var2, var5);
         var3 = var5.getMsgID();
         switch (var7) {
            case 1:
               if (var15 == 0) {
                  break;
               }

               SnmpException.b = !SnmpException.b;
            case 3:
               var4 = this.i;
               break;
            default:
               var4 = (SnmpSecurityCoder)this.l.get(new Integer(var7));
         }

         SnmpSecurityParameters var8 = null;
         if (var4 != null) {
            var8 = var4.decodeSecurityParameters(var2, var5);
            int var9 = BERCoder.getTag(var2);
            var5.setVersion(var1);
            var5.setMessageParameters(var6);
            var5.setSecurityParameters(var8);
            SnmpPDU var10 = null;
            if (var9 == 48) {
               BERCoder.getLength(var2);
               var10 = this.a(var2, var6);
            } else if (var9 == 4) {
               byte[] var11 = BERCoder.decodeString(var2);
               BERBuffer var12 = new BERBuffer(var11);
               BERBuffer var13 = var4.decryptScopedPDU(var12, var5);

               try {
                  int var14 = BERCoder.getTag(var13);
                  n.debug(a("\u0016o}\nG\u0001\u0001b\u001eWhxs\u001d8e") + var14);
                  BERCoder.getLength(var13);
                  var10 = this.a(var13, var6);
               } catch (SnmpCoderException var16) {
                  throw new SnmpSecurityCoderException(7, var16.getMessage());
               } catch (BERException var17) {
                  throw new SnmpSecurityCoderException(7, var17.getMessage());
               }
            }

            if (var10 != null) {
               var10.setVersion(var1);
            }

            SnmpContext var20 = new SnmpContext(var6.getContextEngineID(), var6.getContextName());
            var5.setContext(var20);
            var5.setData(var10);
            return var5;
         } else {
            throw new SnmpSecurityCoderException(1, a("\u0010BA/r5C@.g!\fa?a0^[.{ea]>g)\u0016\u0012") + var7);
         }
      } catch (BERException var18) {
         if (n.isDebugEnabled()) {
            n.debug(a("!IQ5f aW)q$KW"), var18);
         }

         throw new SnmpCoderException(var18.getMessage());
      } catch (SnmpSecurityCoderException var19) {
         var19.setMsgId(var3);
         this.updateCounters(var19.getSpecificError());
         throw var19;
      }
   }

   private SnmpPDU a(BERBuffer var1, V3SnmpMessageParameters var2) throws SnmpCoderException, BERException {
      if (n.isDebugEnabled()) {
         n.debug(a("\u0001iq\u0015F\fbuzQ\u0006cb\u001fFe|v\u000f"));
      }

      byte[] var3 = BERCoder.decodeString(var1, 4);
      byte[] var4 = BERCoder.decodeString(var1, 4);
      SnmpPDU var5 = this.j.decodePDU(var1);
      var2.setContextEngineID(var3);
      var2.setContextName(var4);
      if (n.isDebugEnabled()) {
         n.debug(a("\u0001iq\u0015F\u0000h\u0012\u0019M\u000bxw\u0002V\u007f\f"));
         n.debug(a("h\u0001\u00129m+XW\"v\u0000BU3l ev`\"") + ByteFormatter.toHexString(var3));
         n.debug(a("h\u0001\u00129m+XW\"v\u000bM_?8e\f\u0012z\"") + new String(var4) + ByteFormatter.toHexString(var4));
      }

      return var5;
   }

   private int a(BERBuffer var1, SnmpMessage var2) throws BERException {
      if (n.isDebugEnabled()) {
         n.debug(a("\u0001iq\u0015F\fbuzTv\fz\u001fC\u0001i`"));
      }

      V3SnmpMessageParameters var3 = (V3SnmpMessageParameters)var2.getMessageParameters();
      BERCoder.expectTag(var1, 48);
      BERCoder.getLength(var1);
      int var4 = (int)BERCoder.decodeInteger(var1, 2);
      int var5 = (int)BERCoder.decodeInteger(var1, 2);
      byte[] var6 = BERCoder.decodeString(var1, 4);
      var3.setFlags(var6[0]);
      int var7 = (int)BERCoder.decodeInteger(var1, 2);
      var2.setMsgID(var4);
      var3.setMaxSize(var5);
      if (n.isDebugEnabled()) {
         n.debug(a("\u0001iq\u0015F\u0000h\u0012\tL\b|\u0012\f1edw\u001bF\u0000~\bz"));
         n.debug(a("h\u0001\u00127q\"ev`\"e\f\u0012z\"e") + var4);
         n.debug(a("h\u0001\u00127q\"aS\"Q,VW`\"e") + var5);
         n.debug(a("h\u0001\u00127q\"j^;e6\u0016\u0012z\"eMG.jm") + (var6[0] & 1) + a("l\u0000") + a("5^[,*") + ((var6[0] & 2) >> 1) + a("l\u0000") + a("7IB5p1MP6gm") + ((var6[0] & 4) >> 2) + ")");
         n.debug(a("h\u0001\u00127q\"\u007fW9O*HW68e") + var7);
      }

      return var7;
   }

   public BERBuffer encodeMessage(SnmpMessage var1, int var2) throws SnmpCoderException {
      int var9 = SnmpSecurityCoderException.g;

      BERBuffer var10000;
      try {
         V3SnmpMessageParameters var3 = (V3SnmpMessageParameters)var1.getMessageParameters();
         SnmpMessageProfile var4 = var1.getMessageProfile();
         BERBuffer var5 = new BERBuffer();
         this.a(var5, var1, var2);
         SnmpSecurityCoder var6 = null;
         int var7 = var4.getSecurityModel();
         switch (var7) {
            case 1:
               if (var9 == 0) {
                  break;
               }
            case 3:
               var6 = this.i;
               break;
            default:
               var6 = (SnmpSecurityCoder)this.l.get(new Integer(var7));
         }

         if (var6 == null) {
            throw new SnmpSecurityCoderException(1, a("\u0010BA/r5C@.g!\fa?a0^[.{ea]>g)\u0016\u0012") + var7);
         }

         int var8 = var6.encodeSecurityParameters(var5, var1);
         this.b(var5, var1);
         BERCoder.encodeInteger(var5, (long)var1.getVersion(), 2);
         BERCoder.encodeLength(var5);
         BERCoder.encodeTag(var5, 48);
         var6.authenticateOutgoing(var5, var1, var8);
         var10000 = var5;
      } catch (BERException var10) {
         if (n.isDebugEnabled()) {
            n.debug(a(" BQ5f aW)q$KW"), var10);
         }

         throw new SnmpCoderException(var10.getMessage());
      } catch (SnmpSecurityCoderException var11) {
         var11.setMsgId(var1.getMsgID());
         throw var11;
      }

      if (SnmpException.b) {
         ++var9;
         SnmpSecurityCoderException.g = var9;
      }

      return var10000;
   }

   private int b(BERBuffer var1, SnmpMessage var2) throws BERException {
      V3SnmpMessageParameters var3 = (V3SnmpMessageParameters)var2.getMessageParameters();
      SnmpMessageProfile var4 = var2.getMessageProfile();
      byte var5 = 4;
      if (var2.getData() != null) {
         switch (var2.getData().getType()) {
            case 162:
            case 164:
            case 167:
            case 168:
               var5 = 0;
            case 163:
            case 165:
            case 166:
         }
      }

      byte var6 = (byte)(var4.getSecurityLevel() & 3);
      byte[] var7 = new byte[]{(byte)(var6 | var5)};
      int var8 = 0;
      var8 += BERCoder.encodeInteger(var1, (long)var4.getSecurityModel(), 2);
      var8 += BERCoder.encodeString(var1, var7, 4);
      int var9 = var2.getMaxSize();
      if (var9 <= 0 && var3 != null) {
         var9 = var3.getMaxSize();
      }

      var8 += BERCoder.encodeInteger(var1, (long)var2.getMaxSize(), 2);
      var8 += BERCoder.encodeInteger(var1, (long)var2.getMsgID(), 2);
      var8 += BERCoder.encodeLength(var1, var8);
      var8 += BERCoder.encodeTag(var1, 48);
      if (n.isDebugEnabled()) {
         n.debug(a("\u0000bq\u0015F\u0000h\u0012\tL\b|\u0012\f1edw\u001bF\u0000~\bz"));
         n.debug(a("h\u0001\u00127q\"ev`\"e\f\u0012z\"e") + var2.getMsgID());
         n.debug(a("h\u0001\u00127q\"aS\"Q,VW`\"e") + var2.getMaxSize());
         n.debug(a("h\u0001\u00127q\"j^;e6\u0016\u0012z\"eMG.jm") + (var7[0] & 1) + a("l\u0000") + a("5^[,*") + ((var7[0] & 2) >> 1) + a("l\u0000") + a("7IB5p1MP6gm") + ((var7[0] & 4) >> 2) + ")");
         n.debug(a("h\u0001\u00127q\"\u007fW9O*HW68e") + var4.getSecurityModel());
      }

      return var8;
   }

   private int a(BERBuffer var1, SnmpMessage var2, int var3) throws SnmpCoderException, BERException {
      V3SnmpMessageParameters var4 = (V3SnmpMessageParameters)var2.getMessageParameters();
      int var5 = 0;
      SnmpPDU var6 = var2.getData();
      byte[] var7 = null;
      Object var8 = null;
      SnmpContext var9 = var2.getContext();
      byte[] var10;
      if (var9 == null) {
         var7 = a;
         var10 = b;
      } else {
         if (var9.getContextEngineID() == null) {
            if (var2.getSnmpEngineID() != null) {
               var7 = var2.getSnmpEngineID().toByteArray();
            }

            if (var7 == null) {
               var7 = a;
            }
         } else {
            var7 = var9.getContextEngineID().toByteArray();
         }

         var10 = var9.getContextName().getBytes();
      }

      var5 += this.j.encodePDU(var1, var2.getData(), var3);
      var5 += BERCoder.encodeString(var1, var10, 4);
      var5 += BERCoder.encodeString(var1, var7, 4);
      var5 += BERCoder.encodeLength(var1);
      var5 += BERCoder.encodeTag(var1, 48);
      return var5;
   }

   public void updateCounters(int var1) {
      int var2 = SnmpSecurityCoderException.g;
      switch (var1) {
         case 1:
            if (var2 == 0) {
               break;
            }
         case 2:
            ++this.c;
            if (var2 == 0) {
               break;
            }
         case 3:
            ++this.f;
            if (var2 == 0) {
               break;
            }
         case 4:
            ++this.e;
            if (var2 == 0) {
               break;
            }
         case 5:
            ++this.d;
            if (var2 == 0) {
               break;
            }
         case 6:
            ++this.g;
            if (var2 == 0) {
               break;
            }
         case 7:
         case 8:
            ++this.h;
            if (var2 != 0) {
            }
      }

   }

   public int getUnsupportedSecLevels() {
      return this.c;
   }

   public int getNotInTimeWindows() {
      return this.d;
   }

   public int getUnknownUserNames() {
      return this.e;
   }

   public int getUnknownEngineIDs() {
      return this.f;
   }

   public int getWrongDigests() {
      return this.g;
   }

   public int getDecryptionErrors() {
      return this.h;
   }

   public SnmpSecurityCoder getSecurityCoder() {
      return this.i;
   }

   public SnmpPDUCoder getPDUCoder() {
      return this.j;
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
               var10003 = 44;
               break;
            case 2:
               var10003 = 50;
               break;
            case 3:
               var10003 = 90;
               break;
            default:
               var10003 = 2;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
