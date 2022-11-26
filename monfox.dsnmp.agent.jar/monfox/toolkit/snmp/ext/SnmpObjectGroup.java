package monfox.toolkit.snmp.ext;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectGroupInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;
import monfox.toolkit.snmp.mgr.SnmpSession;

public class SnmpObjectGroup extends SnmpObjectSet implements SnmpPollable {
   private static final int NOTIFY_NONE = 1;
   private static final int NOTIFY_UPDATES = 2;
   private static final int NOTIFY_CHANGES = 4;
   public static final int DATA_VALID = 0;
   public static final int DATA_INVALID = 1;
   private int d;
   private SnmpObjectGroupStatusChangeListener b;
   private static final SnmpNull c = new SnmpNull();
   private SnmpObjectInfo[] e;
   private int[] f;
   private SnmpVarBindList g;
   private SnmpPeer h;
   private b i;
   private SnmpPendingRequest j;
   private boolean k;
   private boolean l;

   public SnmpObjectGroup() {
      this.d = 1;
      this.b = null;
      this.e = new SnmpObjectInfo[0];
      this.f = null;
      this.g = null;
      this.h = null;
      this.i = null;
      this.j = null;
      this.k = true;
      this.l = false;
      this.i = new b(this);
   }

   public SnmpObjectGroup(SnmpMetadata var1, SnmpPeer var2, String var3) throws SnmpValueException {
      this();
      this.setMetadata(var1);
      this.setPeer(var2);
      this.add(var3);
   }

   public SnmpObjectGroup(SnmpMetadata var1, SnmpPeer var2, String[] var3) throws SnmpValueException {
      this();
      this.setMetadata(var1);
      this.setPeer(var2);
      this.add(var3);
   }

   public SnmpObjectGroup(SnmpPeer var1) {
      this();
      this.setPeer(var1);
   }

   public SnmpObjectGroup(SnmpPeer var1, String var2) throws SnmpValueException {
      this();
      this.setPeer(var1);
      this.add(var2);
   }

   public SnmpObjectGroup(SnmpPeer var1, String[] var2) throws SnmpValueException {
      this();
      this.setPeer(var1);
      this.add(var2);
   }

   public SnmpObjectGroup(SnmpPeer var1, SnmpVarBindList var2) {
      this();
      this.setPeer(var1);
      this.setObjects(var2);
   }

   public void setPeer(SnmpPeer var1) {
      this.h = var1;
   }

   public SnmpPeer getPeer() {
      return this.h;
   }

   public SnmpObjectGroup add(String var1) throws SnmpValueException {
      return this.add(var1, true);
   }

   public SnmpObjectGroup add(String var1, boolean var2) throws SnmpValueException {
      SnmpOid var3 = new SnmpOid(this.getMetadata(), var1);
      return this.add(var3, var2);
   }

   public SnmpObjectGroup add(SnmpOid var1) throws SnmpValueException {
      return this.a(var1, true, 1);
   }

   public SnmpObjectGroup add(SnmpOid var1, boolean var2) throws SnmpValueException {
      return this.a(var1, var2, 1);
   }

   public SnmpObjectGroup remove(String var1) throws SnmpValueException {
      SnmpOid var2 = new SnmpOid(this.getMetadata(), var1);
      return this.remove(var2);
   }

   public SnmpObjectGroup remove(SnmpOid var1) throws SnmpValueException {
      return this.a(var1, true, 2);
   }

   public void monitor(SnmpOid var1) throws SnmpException {
      this.a(var1, true, 4);
   }

   public void ignore(SnmpOid var1) throws SnmpException {
      this.a(var1, false, 4);
   }

   public void monitor(String[] var1) throws SnmpException {
      int var2 = 0;

      while(var2 < var1.length) {
         this.monitor(var1[var2]);
         ++var2;
         if (SnmpObjectSet.q != 0) {
            break;
         }
      }

   }

   public void monitor(String var1) throws SnmpException {
      SnmpOid var2 = new SnmpOid(this.getMetadata(), var1);
      this.a(var2, true, 4);
   }

   public void ignore(String var1) throws SnmpException {
      SnmpOid var2 = new SnmpOid(this.getMetadata(), var1);
      this.a(var2, false, 4);
   }

   private synchronized SnmpObjectGroup a(SnmpOid var1, boolean var2, int var3) throws SnmpValueException {
      int var14 = SnmpObjectSet.q;
      this.k = true;
      if (this.g == null) {
         this.g = new SnmpVarBindList(this.getMetadata());
      }

      SnmpOidInfo var5 = var1.getOidInfo();
      if (var5 == null) {
         throw new SnmpValueException(a("V\u0018hZ_v\u0012>tQu\u0013}O\t") + var1 + ".");
      } else {
         label122: {
            SnmpObjectGroup var10000;
            label107: {
               if (var5 instanceof SnmpObjectGroupInfo) {
                  SnmpObjectGroupInfo var6 = (SnmpObjectGroupInfo)var5;
                  SnmpObjectInfo[] var7 = var6.getObjects();
                  SnmpNull var8 = var2 ? null : c;
                  int var9 = 0;

                  while(var9 < var7.length) {
                     SnmpObjectInfo var10 = var7[var9];
                     SnmpOid var11 = (SnmpOid)var10.getOid().clone();
                     SnmpVarBind var12 = new SnmpVarBind(var11, var8, false);
                     var10000 = this;
                     if (var14 != 0) {
                        return var10000;
                     }

                     if (!this.l) {
                        var12.addInstance(0L);
                     }

                     label110: {
                        if (var3 == 1) {
                           this.g.add(var12);
                           if (var14 == 0) {
                              break label110;
                           }
                        }

                        if (var3 == 2) {
                           this.g.remove(var12);
                           if (var14 == 0) {
                              break label110;
                           }
                        }

                        SnmpVarBind var13 = this.g.get(var12.getOid());
                        if (var13 != null) {
                           var13.setValue((SnmpValue)var8);
                           if (var14 == 0) {
                              break label110;
                           }
                        }

                        this.g.add(var12);
                     }

                     ++var9;
                     if (var14 != 0) {
                        break;
                     }
                  }

                  if (var14 == 0) {
                     break label107;
                  }
               }

               if (!(var5 instanceof SnmpObjectInfo)) {
                  break label122;
               }

               SnmpNull var15 = var2 ? null : c;
               SnmpVarBind var16 = new SnmpVarBind(var1, var15, false);
               if (!this.l && var1.getLength() <= var5.getLevel()) {
                  var16.addInstance(0L);
               }

               label111: {
                  if (var3 == 1) {
                     this.g.add(var16);
                     if (var14 == 0) {
                        break label111;
                     }
                  }

                  if (var3 == 2) {
                     this.g.remove(var16);
                     if (var14 == 0) {
                        break label111;
                     }
                  }

                  SnmpVarBind var17 = this.g.get(var16.getOid());
                  if (var17 != null) {
                     var17.setValue((SnmpValue)var15);
                     if (var14 == 0) {
                        break label111;
                     }
                  }

                  this.g.add(var16);
               }

               if (var14 != 0) {
                  break label122;
               }
            }

            var10000 = this;
            return var10000;
         }

         if (var5 instanceof SnmpTableInfo) {
            throw new SnmpValueException(a("V\u0018hZ_v\u0012>tQu\u0013}O\t") + var1 + a("1VKHV?%pVCK\u0017|WV?\u001fpHGz\u0017z\u0015"));
         } else {
            throw new SnmpValueException(a("V\u0018hZ_v\u0012>tQu\u0013}O\t") + var1 + ".");
         }
      }
   }

   public synchronized SnmpObjectGroup add(String[] var1) throws SnmpValueException {
      int var3 = SnmpObjectSet.q;
      int var2 = 0;

      SnmpObjectGroup var10000;
      while(true) {
         if (var2 < var1.length) {
            var10000 = this.add(var1[var2]);
            if (var3 != 0) {
               break;
            }

            ++var2;
            if (var3 == 0) {
               continue;
            }
         }

         var10000 = this;
         break;
      }

      return var10000;
   }

   public synchronized SnmpObjectGroup remove(String[] var1) throws SnmpValueException {
      int var3 = SnmpObjectSet.q;
      int var2 = 0;

      SnmpObjectGroup var10000;
      while(true) {
         if (var2 < var1.length) {
            var10000 = this.remove(var1[var2]);
            if (var3 != 0) {
               break;
            }

            ++var2;
            if (var3 == 0) {
               continue;
            }
         }

         var10000 = this;
         break;
      }

      return var10000;
   }

   public void setObjects(SnmpVarBindList var1) {
      this.g = var1;
   }

   public void perform(SnmpSession var1) {
      try {
         SnmpObjectInfo[] var2;
         label16: {
            var2 = this.infoSet();
            if (this.l) {
               this.j = var1.startGetNext(this.h, this.i, this.g);
               if (SnmpObjectSet.q == 0) {
                  break label16;
               }
            }

            this.j = var1.startGet(this.h, this.i, this.g);
         }

         this.j.setUserData(var2);
      } catch (SnmpException var3) {
         this.notifyError(new SnmpError(3, var3.getMessage(), var3));
      }

   }

   public void performUpdate(SnmpSession var1) throws SnmpException {
      try {
         SnmpObjectInfo[] var2 = this.infoSet();
         SnmpVarBindList var3 = null;
         if (this.l) {
            var3 = var1.performGetNext(this.h, this.g);
         } else {
            var3 = var1.performGet(this.h, this.g);
         }

         this.updateValues(var3, 0, System.currentTimeMillis(), var2, a);
      } catch (SnmpException var4) {
         this.notifyError(new SnmpError(3, var4.getMessage(), var4));
         throw var4;
      }
   }

   public void useGetNext(boolean var1) {
      this.l = var1;
   }

   public boolean useGetNext() {
      return this.l;
   }

   protected SnmpObjectInfo[] infoSet() {
      if (!this.k) {
         return this.e;
      } else {
         this.a();
         return this.e;
      }
   }

   protected int[] varMask() {
      if (!this.k) {
         return this.f;
      } else {
         this.a();
         return this.f;
      }
   }

   private synchronized void a() {
      int var5 = SnmpObjectSet.q;
      if (this.g != null) {
         int var1 = this.g.size();
         this.e = new SnmpObjectInfo[var1];
         this.f = null;
         int var2 = 0;

         while(true) {
            if (var2 < var1) {
               try {
                  SnmpVarBind var3 = this.g.get(var2);
                  this.e[var2] = (SnmpObjectInfo)var3.getOid().getOidInfo();
                  SnmpValue var4 = var3.getValue();
                  if (var5 != 0) {
                     break;
                  }

                  if (var4 == c) {
                     if (this.f == null) {
                        this.f = new int[var1];
                     }

                     this.f[var2] = 2;
                  }
               } catch (ClassCastException var6) {
                  this.e[var2] = null;
               }

               ++var2;
               if (var5 == 0) {
                  continue;
               }
            }

            this.k = false;
            break;
         }

      }
   }

   void a(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (this.n.isDebugEnabled()) {
         this.n.debug(a("L\u0018sK|}\u001c{XGX\u0004qNC1\u001e\u007fUWs\u0013L^@o\u0019pHV%V{HG~\u0002#") + var2 + a("3V{RWgK") + var3 + a("3VhY_\"") + var4);
      }

      if (var2 != 0) {
         this.notifyError(new SnmpError(2, a("Z\u0004lTA?${HCp\u0018m^\tD") + Snmp.errorStatusToString(var2) + ":" + var4.get(var3 - 1) + "]", var1));
      } else {
         SnmpObjectInfo[] var5 = (SnmpObjectInfo[])((SnmpObjectInfo[])var1.getUserData());
         this.updateValues(var4, 0, System.currentTimeMillis(), var5, a);
      }
   }

   void b(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (this.n.isDebugEnabled()) {
         this.n.debug(a("L\u0018sK|}\u001c{XGX\u0004qNC1\u001e\u007fUWs\u0013L^Cp\u0004j\u0001\u0013z\u0005jZG\"") + var2 + a("3V{RWgK") + var3 + a("3VhY_\"") + var4);
      }

      if (var2 != 0) {
         this.notifyError(new SnmpError(2, a("Z\u0004lTA?${HCp\u0018m^\tD") + Snmp.errorStatusToString(var2) + ":" + var4.get(var3 - 1) + "]", var1));
      } else {
         SnmpObjectInfo[] var5 = (SnmpObjectInfo[])((SnmpObjectInfo[])var1.getUserData());
         this.updateValues(var4, 0, System.currentTimeMillis(), var5, a);
      }
   }

   void a(SnmpPendingRequest var1) {
      this.notifyError(new SnmpError(1, a("K\u001fs^\\j\u0002")));
   }

   void a(SnmpPendingRequest var1, Exception var2) {
      this.notifyError(new SnmpError(3, var2.toString(), var2));
   }

   protected void notifyError(SnmpError var1) {
      super.notifyError(var1);
      this.a(1);
   }

   protected void updateValues(SnmpVarBindList var1, int var2, long var3, SnmpObjectInfo[] var5, int[] var6) {
      super.updateValues(var1, var2, var3, var5, var6);
      this.a(0);
   }

   public int getStatus() {
      return this.d;
   }

   void a(int var1) {
      synchronized(this) {
         if (this.d == var1) {
            return;
         }

         this.d = var1;
      }

      SnmpObjectGroupStatusChangeListener var2 = this.b;
      if (var2 != null) {
         try {
            var2.handleStatusChange(this);
         } catch (Throwable var4) {
            var4.printStackTrace();
         }
      }

   }

   public void setStatusChangeListener(SnmpObjectGroupStatusChangeListener var1) {
      this.b = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 31;
               break;
            case 1:
               var10003 = 118;
               break;
            case 2:
               var10003 = 30;
               break;
            case 3:
               var10003 = 59;
               break;
            default:
               var10003 = 51;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
