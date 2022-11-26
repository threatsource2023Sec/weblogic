package monfox.toolkit.snmp.agent.target;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpCounter;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAccessControlModel;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.vacm.Vacm;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpOidMap;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPeer;

public class SnmpTarget {
   private SnmpAgent a;
   private SnmpTargetAddrTable b;
   private SnmpTargetParamsTable c;
   private Vacm d;
   private Logger e = Logger.getInstance(a("}C\u000f9&}"));
   public static final SnmpOid snmpTargetAddrTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 12L, 1L, 2L});
   public static final SnmpOid snmpTargetParamsTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 12L, 1L, 3L});
   public static final SnmpOid snmpUnavailableContexts = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 12L, 1L, 4L});
   public static final SnmpOid snmpUnknownContexts = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 12L, 1L, 5L});
   public static int f;

   public SnmpTarget(SnmpAgent var1) throws SnmpException {
      this.a = var1;
      SnmpAccessControlModel var2 = this.a.getAccessControlModel();
      if (var2 instanceof Vacm) {
         this.d = (Vacm)var2;
      }

      SnmpMib var3 = var1.getMib();
      this.b = new SnmpTargetAddrTable(this, var3.getMetadata());
      this.c = new SnmpTargetParamsTable(var3.getMetadata());
      var3.add(this.b, true);
      var3.add(this.c, true);
      var3.add(new SnmpUnavailableContexts(), true);
      var3.add(new SnmpUnknownContexts(), true);
   }

   public void addV1(String var1, String var2, int var3, int var4, int var5, String var6, String var7) throws SnmpMibException, SnmpValueException, UnknownHostException {
      this.b.add(var1, var2, var3, var4, var5, var6, var1);
      this.c.addV1(var1, var7);
   }

   public void addV1(String var1, String var2, int var3, String var4, String var5) throws SnmpMibException, SnmpValueException, UnknownHostException {
      this.b.add(var1, var2, var3, var4, var1);
      this.c.addV1(var1, var5);
   }

   public void addV2(String var1, String var2, int var3, int var4, int var5, String var6, String var7) throws SnmpMibException, SnmpValueException, UnknownHostException {
      this.b.add(var1, var2, var3, var4, var5, var6, var1);
      this.c.addV2(var1, var7);
   }

   public void addV2(String var1, String var2, int var3, String var4, String var5) throws SnmpMibException, SnmpValueException, UnknownHostException {
      this.b.add(var1, var2, var3, var4, var1);
      this.c.addV2(var1, var5);
   }

   public void addV3(String var1, String var2, int var3, int var4, int var5, String var6, String var7, int var8) throws SnmpMibException, SnmpValueException, UnknownHostException {
      this.b.add(var1, var2, var3, var4, var5, var6, var1);
      this.c.addV3(var1, var7, var8);
   }

   public void addV3(String var1, String var2, int var3, String var4, String var5, int var6) throws SnmpMibException, SnmpValueException, UnknownHostException {
      this.b.add(var1, var2, var3, var4, var1);
      this.c.addV3(var1, var5, var6);
   }

   public SnmpTargetAddrTable getAddrTable() {
      return this.b;
   }

   public SnmpTargetParamsTable getParamsTable() {
      return this.c;
   }

   public SnmpPeer getPeer(String var1) {
      if (this.e.isDebugEnabled()) {
         this.e.debug(a("Ng).\u0006Lpg^") + var1);
      }

      SnmpMibTableRow var2;
      try {
         var2 = this.b.get(var1);
      } catch (SnmpException var5) {
         this.e.debug(a("Jc3\u0010\f]\":\u001b\u0017\tl<\u0013\u0006\td/\u0011\u000e\tQ3\u0013\u0013}c/\u0019\u0006]C9\u001a\u0011}c?\u0012\u0006\u0013\"") + var1, var5);
         return null;
      }

      if (var2 == null) {
         return null;
      } else if (!var2.isActive()) {
         return null;
      } else {
         try {
            SnmpTargetAddrTable.Row var3 = this.b.b(var2);
            if (this.e.isDebugEnabled()) {
               this.e.debug(var3);
            }

            return var3.getPeer();
         } catch (Exception var4) {
            this.e.debug(a("Jc3\u0010\f]\">\f\u0006Hv8^\u0013Lg/^\u0005Fp}\f\f^8}") + var2, var4);
            return null;
         }
      }
   }

   public Vector getPeers(String var1) {
      int var9 = f;
      Vector var2 = new Vector();
      Iterator var3 = this.b.getRows();

      label43:
      while(var3.hasNext()) {
         SnmpMibTableRow var4 = (SnmpMibTableRow)var3.next();
         if (var4.isActive() || var9 != 0) {
            String var5 = var4.getLeaf(6).getValue().getString();
            StringTokenizer var6 = new StringTokenizer(var5, a("\t\u000bPto"), false);

            while(var6.hasMoreTokens()) {
               if (var6.nextToken().equals(var1)) {
                  SnmpPeer var7;
                  label33: {
                     try {
                        SnmpTargetAddrTable.Row var8 = this.b.b(var4);
                        var7 = var8.getPeer();
                     } catch (Exception var10) {
                        var7 = null;
                        break label33;
                     }

                     if (var9 != 0) {
                        continue label43;
                     }
                  }

                  if (var7 != null) {
                     var2.add(var7);
                     if (var9 != 0) {
                     }
                  }
                  break;
               }
            }

            if (var9 != 0) {
               break;
            }
         }
      }

      return var2;
   }

   SnmpPeer a(SnmpMibTableRow var1) throws UnknownHostException {
      int var18 = f;
      this.b.b(var1);
      String var3 = null;

      try {
         var3 = var1.getLeaf(7).getValue().getString();
      } catch (Exception var19) {
      }

      this.e.debug(a("Ng)\n\nGe}\u000e\u0002[c0\rY\t") + var3);
      SnmpMessageProfile var4 = null;
      boolean var5 = false;
      if (var3 != null && var3.trim().length() != 0) {
         var4 = this.c.getMessageProfile(var3);
      } else {
         var5 = true;
         var4 = new SnmpMessageProfile(a("\rR\u001c-0}J\u000f16nJy"));
      }

      if (var4 == null) {
         return null;
      } else {
         SnmpOid var6 = (SnmpOid)var1.getLeaf(2).getValue();
         byte[] var7 = var1.getLeaf(3).getValue().getByteArray();
         int var8 = var1.getLeaf(4).getValue().intValue();
         int var9 = var1.getLeaf(5).getValue().intValue();
         InetAddress var10 = null;
         int var11 = 0;
         int var12;
         String var16;
         if (var7.length == 6) {
            var12 = var7[0] & 255;
            int var13 = var7[1] & 255;
            int var14 = var7[2] & 255;
            int var15 = var7[3] & 255;
            var16 = var12 + "." + var13 + "." + var14 + "." + var15;
            var10 = InetAddress.getByName(var16);
            var11 |= var7[5] << 8 & '\uff00';
            var11 |= var7[4] & 255;
            this.e.debug(a("`R+JC}c/\u0019\u0006]8}") + var10 + ":" + var11);
         } else if (var7.length == 18) {
            byte[] var21 = new byte[16];
            System.arraycopy(var7, 0, var21, 0, 16);
            var10 = InetAddress.getByAddress(var21);
            var11 |= var7[17] << 8 & '\uff00';
            var11 |= var7[16] & 255;
            this.e.debug(a("`R+HC}c/\u0019\u0006]8}") + var10 + ":" + var11);
         } else {
            var12 = var7.length;
            byte[] var23 = new byte[var12 - 2];
            System.arraycopy(var7, 0, var23, 0, var12 - 2);
            var10 = InetAddress.getByAddress(var23);
            var11 |= var7[var12 - 1] << 8 & '\uff00';
            var11 |= var7[var12] & 255;
            this.e.debug(a("`Ru\u0011\u0017Ag/WC}c/\u0019\u0006]8}") + var10 + ":" + var11);
         }

         SnmpPeer var22 = new SnmpPeer(var10, var11, var6);
         var22.setTimeout((long)(var8 * 10));
         var22.setMaxRetries(var9);
         var22.setParameters(new SnmpParameters(var4));
         var22.isPassThrough(var5);

         try {
            if (this.a.getEngine().getOidMapManager() != null) {
               SnmpOidMap.Manager var24 = this.a.getEngine().getOidMapManager();
               String var25 = var1.getLeaf(6).getValue().getString();
               this.e.debug(a("Em2\u0015\nGe}\u0018\f[\"\u0012\u0017\u0007dc-^\u0005Fp}-\rDr\r\u001b\u0006[8}") + var25);
               StringTokenizer var26 = new StringTokenizer(var25, a("#\u000b}RX"), false);

               while(var26.hasMoreTokens()) {
                  var16 = var26.nextToken();
                  this.e.debug(a("Jj8\u001d\b@l:^\u0017Heg^") + var16);
                  SnmpOidMap var17 = var24.getOidMap(var16);
                  if (var18 != 0) {
                     break;
                  }

                  if (var17 != null) {
                     this.e.debug(a("Om(\u0010\u0007\tM\u0014:CDc-^Y\t") + var17);
                     var22.setOidMap(var17);
                     if (var18 == 0) {
                        break;
                     }
                  }

                  if (var18 != 0) {
                     break;
                  }
               }
            }
         } catch (Exception var20) {
            this.e.error(a("Lp/\u0011\u0011\tk3^\u0007Lv8\f\u000e@l4\u0010\u0004\tM4\u001a.Hr}\u0018\f[\")\u001f\u0011Ng)^\u0002Mf/\u001b\u0010Z"), var20);
         }

         return var22;
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
               var10003 = 41;
               break;
            case 1:
               var10003 = 2;
               break;
            case 2:
               var10003 = 93;
               break;
            case 3:
               var10003 = 126;
               break;
            default:
               var10003 = 99;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class SnmpUnknownContexts extends SnmpMibLeaf {
      public SnmpUnknownContexts() throws SnmpMibException {
         super(SnmpTarget.snmpUnknownContexts);
      }

      public SnmpValue getValue() {
         return SnmpTarget.this.d == null ? new SnmpCounter(0) : new SnmpCounter(SnmpTarget.this.d.getSnmpUnknownContexts());
      }
   }

   class SnmpUnavailableContexts extends SnmpMibLeaf {
      public SnmpUnavailableContexts() throws SnmpMibException {
         super(SnmpTarget.snmpUnavailableContexts);
      }

      public SnmpValue getValue() {
         return SnmpTarget.this.d == null ? new SnmpCounter(0) : new SnmpCounter(SnmpTarget.this.d.getSnmpUnavailableContexts());
      }
   }
}
