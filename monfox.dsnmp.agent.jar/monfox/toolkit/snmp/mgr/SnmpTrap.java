package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpTimeTicks;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.SnmpTrapPDU;
import monfox.toolkit.snmp.engine.TransportEntity;

public class SnmpTrap {
   private static SnmpOid a = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 1L, 3L, 0L});
   private static SnmpOid b = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 4L, 1L, 0L});
   private static SnmpOid c = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 4L, 3L, 0L});
   private static SnmpOid d = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 5L});
   private String e;
   private String f;
   private SnmpMessage g;
   private SnmpPDU h;
   private SnmpSession i;
   private SnmpOid j;
   private SnmpOid k;
   private SnmpIpAddress l;
   private long m;
   private int n;
   private int o;
   private TransportEntity p;
   private SnmpVarBindList q;
   private SnmpVarBindList r;

   SnmpTrap(SnmpSession var1, TransportEntity var2, SnmpMessage var3) {
      boolean var9 = SnmpSession.B;
      super();
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = null;
      this.m = 0L;
      this.n = 0;
      this.o = 0;
      this.p = null;
      this.q = null;
      this.r = null;
      this.g = var3;
      this.h = var3.getData();
      this.p = var2;
      this.i = var1;
      this.q = this.h.getVarBindList();
      if (var3.getContext() != null) {
         this.e = var3.getContext().getContextName();
      }

      if (this.h instanceof SnmpTrapPDU) {
         label52: {
            SnmpTrapPDU var4 = (SnmpTrapPDU)this.h;
            this.n = var4.getGenericTrap();
            this.o = var4.getSpecificTrap();
            this.k = var4.getEnterprise();
            this.m = var4.getTimestamp();
            this.l = var4.getAgentAddr();
            if (this.isGenericTrap()) {
               this.j = new SnmpOid(var1.getMetadata(), d);
               this.j.append((long)(this.n + 1));
               if (!var9) {
                  break label52;
               }
            }

            this.j = new SnmpOid(var1.getMetadata(), this.k);
            this.j.append(0L);
            this.j.append((long)var4.getSpecificTrap());
         }

         this.r = this.h.getVarBindList();
         if (!var9) {
            return;
         }
      }

      if (this.h instanceof SnmpRequestPDU) {
         SnmpRequestPDU var10 = (SnmpRequestPDU)this.h;
         SnmpVarBindList var5 = new SnmpVarBindList(var10.getVarBindList());
         if (var5.size() >= 2) {
            SnmpValue var6 = var5.get(0).getValue();
            if (var6 instanceof SnmpTimeTicks) {
               this.m = (long)var6.intValue();
            }

            label42: {
               SnmpValue var7 = var5.get(1).getValue();
               if (var7 instanceof SnmpOid) {
                  this.j = (SnmpOid)var7;
                  if (d.contains(this.j)) {
                     this.n = (int)(this.j.getLast() - 1L);
                     this.k = d;
                     this.o = 0;
                     if (!var9) {
                        break label42;
                     }
                  }

                  this.n = 6;
                  this.o = (int)this.j.getLast();
                  this.k = this.j.getParent(2);
                  if (!var9) {
                     break label42;
                  }
               }

               this.j = new SnmpOid(0L);
            }

            var5.remove(0);
            var5.remove(0);
            if (var5.size() > 0) {
               SnmpVarBind var8 = var5.get(var5.size() - 1);
               if (var8.getOid().equals(c) && var8.getValue() != null && var8.getValue() instanceof SnmpOid) {
                  this.k = (SnmpOid)var8.getValue();
                  var5.remove(var8);
               }
            }
         }

         this.r = var5;
      }

   }

   public SnmpIpAddress getAgentAddr() {
      return this.l;
   }

   public SnmpOid getEnterprise() {
      return this.k;
   }

   public long getSysUpTime() {
      return this.m;
   }

   public int getVersion() {
      return this.h.getVersion();
   }

   public byte[] getCommunity() {
      return this.h.getCommunity();
   }

   public SnmpVarBindList getVarBindList() {
      return this.q;
   }

   public SnmpVarBindList getObjectValues() {
      return this.r;
   }

   public TransportEntity getSource() {
      return this.p;
   }

   public SnmpSession getSession() {
      return this.i;
   }

   public SnmpMessage getMessage() {
      return this.g;
   }

   public SnmpOid getTrapOid() {
      return this.j;
   }

   public int getSpecificTrap() {
      return this.o;
   }

   public boolean isGenericTrap() {
      return this.n < 6;
   }

   public String getContext() {
      return this.e;
   }

   public int getGenericTrap() {
      return this.n;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("F\u0014@+$g\u001b]s"));
      var1.append(a("\u001fZ\r{\u0004l\nHf")).append(this.getTrapOid());
      var1.append(a("\u001fZ\r{\u0006p\b^2\u001f{G")).append(Snmp.versionToString(this.getVersion()));
      if (this.getCommunity() != null) {
         var1.append(a("\u001fZ\r{\u0013z\u0017@.\u001e|\u000eTf")).append(SnmpFramework.byteArrayToString(this.getCommunity(), (SnmpOid)null));
      }

      if (this.e != null) {
         var1.append(a("\u001fZ\r{\u0013z\u0014Y>\baG")).append(this.e);
      }

      var1.append(a("\u001fZ\r{\u0015{\u000eH)\u0000g\u0013^>M")).append(this.getEnterprise());
      var1.append(a("\u001fZ\r{\u0017p\u0014H)\u0019vG")).append(this.getGenericTrap());
      var1.append(a("\u001fZ\r{\u0003e\u001fN2\u0016|\u0019\u0010")).append(this.getSpecificTrap());
      var1.append(a("\u001fZ\r{\u0003z\u000f_8\u0015(")).append(this.getSource());
      var1.append(a("\u001fZ\r{\u0003l\tx+$|\u0017Hf")).append(this.getSysUpTime());
      if (this.l != null) {
         var1.append(a("\u001fZ\r{\u0011r\u001fC/1q\u001e_f")).append(this.getAgentAddr());
      }

      var1.append(a("\u001fZ\r{\u0006t\bo2\u001eq\t\u0010Q")).append(this.getVarBindList());
      var1.append(")");
      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 21;
               break;
            case 1:
               var10003 = 122;
               break;
            case 2:
               var10003 = 45;
               break;
            case 3:
               var10003 = 91;
               break;
            default:
               var10003 = 112;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
