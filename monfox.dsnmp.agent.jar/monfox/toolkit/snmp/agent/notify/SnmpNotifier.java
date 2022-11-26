package monfox.toolkit.snmp.agent.notify;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpTimeTicks;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.agent.target.SnmpTarget;
import monfox.toolkit.snmp.agent.target.SnmpTargetAddrTable;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.SnmpTrapPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpNotificationInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;

public class SnmpNotifier {
   private SnmpAgent a;
   private SnmpEngine b;
   private SnmpMib c;
   private SnmpMetadata d;
   private RequestIdGenerator e;
   private SnmpNotifyTable f;
   private SnmpNotifyFilterProfileTable g;
   private SnmpNotifyFilterTable h;
   private SnmpTarget i;
   private SnmpContext j;
   private int k;
   private boolean l;
   private Listener m;
   private Logger n;
   private String o;
   private String p;
   private int q;
   public static final SnmpOid snmpNotifyTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 13L, 1L, 1L});
   public static final SnmpOid snmpNotifyFilterProfileTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 13L, 1L, 2L});
   public static final SnmpOid snmpNotifyFilterTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 13L, 1L, 3L});
   private static final SnmpOid r = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 4L, 1L, 0L});
   private static final SnmpOid s = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 4L, 3L, 0L});
   private static final SnmpOid t = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 5L});
   private static final SnmpOid u = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 1L, 3L, 0L});
   public static boolean v;

   public SnmpNotifier(SnmpAgent var1) throws SnmpException {
      boolean var3 = v;
      super();
      this.k = 1000;
      this.l = true;
      this.m = null;
      this.n = Logger.getInstance(a("f_8a!ZE<w\u0006PC"));
      this.o = a("QT3p\u001aYE");
      this.p = a("QT3p\u001aYE");
      this.q = 0;
      this.a = var1;
      this.b = var1.getEngine();
      this.d = var1.getMib().getMetadata();
      this.i = this.a.getTarget();
      this.j = new SnmpContext(this.b.getEngineID());
      SnmpMib var2 = var1.getMib();
      this.f = new SnmpNotifyTable(var2.getMetadata());
      this.g = new SnmpNotifyFilterProfileTable(var2.getMetadata());
      this.h = new SnmpNotifyFilterTable(var2.getMetadata());
      var2.add(this.f, true);
      var2.add(this.g, true);
      var2.add(this.h, true);
      if (SnmpException.b) {
         v = !var3;
      }

   }

   public SnmpNotifyTable getNotifyTable() {
      return this.f;
   }

   public SnmpNotifyFilterProfileTable getNotifyFilterProfileTable() {
      return this.g;
   }

   public SnmpNotifyFilterTable getNotifyFilterTable() {
      return this.h;
   }

   public SnmpTarget getTarget() {
      return this.i;
   }

   public void addListener(Listener var1) {
      this.m = monfox.toolkit.snmp.agent.notify.a.add(this.m, var1);
   }

   public void removeListener(Listener var1) {
      this.m = monfox.toolkit.snmp.agent.notify.a.remove(this.m, var1);
   }

   /** @deprecated */
   public SnmpPeer addTrapV1Target(String var1, int var2, String var3) throws UnknownHostException {
      String var4 = a("AP'v\nA") + this.q++;

      try {
         this.i.addV1(var4, var1, var2, this.o, var3);
      } catch (SnmpException var6) {
      }

      SnmpPeer var5 = new SnmpPeer(var1, var2);
      var5.setParameters(0, var3);
      return var5;
   }

   /** @deprecated */
   public void addTrapV1Target(SnmpPeer var1) {
      String var2 = a("AP'v\nA") + this.q++;

      try {
         this.i.addV1(var2, var1.getHostName(), var1.getPort(), (int)var1.getTimeout(), var1.getMaxRetries(), this.o, var1.getParameters().getInformProfile().getSecurityName());
      } catch (SnmpException var4) {
      } catch (UnknownHostException var5) {
      }

   }

   /** @deprecated */
   public SnmpPeer addTrapV2Target(String var1, int var2, String var3) throws UnknownHostException {
      String var4 = a("AP'v\nA") + this.q++;

      try {
         this.i.addV2(var4, var1, var2, this.o, var3);
      } catch (SnmpException var6) {
      }

      SnmpPeer var5 = new SnmpPeer(var1, var2);
      var5.setParameters(1, var3);
      return var5;
   }

   /** @deprecated */
   public void addTrapV2Target(SnmpPeer var1) {
      String var2 = a("AP'v\nA") + this.q++;

      try {
         this.i.addV2(var2, var1.getHostName(), var1.getPort(), (int)var1.getTimeout(), var1.getMaxRetries(), this.o, var1.getParameters().getInformProfile().getSecurityName());
      } catch (SnmpException var4) {
      } catch (UnknownHostException var5) {
      }

   }

   /** @deprecated */
   public void sendNotification(String var1) throws SnmpMibException, SnmpValueException {
      this.send(this.p, (String)var1, (SnmpOid)null, (SnmpVarBindList)null);
   }

   /** @deprecated */
   public void sendNotification(String var1, String var2) throws SnmpMibException, SnmpValueException {
      this.send(this.p, var1, new SnmpOid(this.d, var2));
   }

   /** @deprecated */
   public void sendNotification(String var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      this.send(this.p, var1, var2);
   }

   /** @deprecated */
   public void sendNotification(String var1, SnmpOid var2, SnmpVarBindList var3) throws SnmpMibException, SnmpValueException {
      this.send(this.p, var1, var2, var3);
   }

   /** @deprecated */
   public NotifyResult sendRawNotification(String var1, SnmpVarBindList var2) throws SnmpMibException, SnmpValueException {
      return this.send(this.p, var1, var2);
   }

   public NotifyResult send(String var1, String var2) throws SnmpMibException, SnmpValueException {
      return this.send(var1, (String)var2, (SnmpOid)null, (SnmpVarBindList)null);
   }

   public NotifyResult send(String var1, SnmpOid var2) throws SnmpMibException {
      return this.send(var1, var2, (SnmpOid)null, (SnmpVarBindList)null);
   }

   public NotifyResult send(String var1, SnmpOid var2, String var3) throws SnmpMibException {
      return this.send(var1, var2, (SnmpOid)null, (SnmpVarBindList)null, var3);
   }

   public NotifyResult send(String var1, String var2, SnmpOid var3) throws SnmpMibException, SnmpValueException {
      return this.send(var1, var2, var3, (SnmpVarBindList)null);
   }

   public NotifyResult send(String var1, SnmpOid var2, SnmpOid var3, String var4) throws SnmpMibException {
      return this.send(var1, var2, var3, (SnmpVarBindList)null, var4);
   }

   public NotifyResult send(String var1, SnmpOid var2, SnmpOid var3) throws SnmpMibException {
      return this.send(var1, var2, var3, (SnmpVarBindList)null);
   }

   public NotifyResult send(String var1, String var2, SnmpOid var3, SnmpVarBindList var4) throws SnmpMibException, SnmpValueException {
      SnmpNotificationInfo var5 = this.d.getNotification(var2);
      return this.send(var1, var5.getOid(), var3, var4);
   }

   public NotifyResult send(String var1, SnmpOid var2, SnmpOid var3, SnmpVarBindList var4) throws SnmpMibException {
      return this.send(var1, var2, var3, var4, (String)null);
   }

   public NotifyResult send(String var1, SnmpOid var2, SnmpOid var3, SnmpVarBindList var4, String var5) throws SnmpMibException {
      SnmpOidInfo var6 = var2.getOidInfo();
      if (!(var6 instanceof SnmpNotificationInfo)) {
         throw new SnmpMibException(a("ZS?t\fA\u0011!h\u001fP\u0011<bO[^!1\u0001ZE<w\u0006VP!x\u0000[\u000bu") + var2 + "(" + var6.toString() + ")");
      } else {
         SnmpNotificationInfo var7 = (SnmpNotificationInfo)var6;
         SnmpObjectInfo[] var8 = var7.getObjects();
         SnmpVarBindList var9 = null;
         if (var8.length != 0) {
            var9 = this.a(var8, var3);
         }

         if (var4 != null) {
            if (var9 == null) {
               var9 = var4;
               if (!v) {
                  return this.send(var1, var7.getOid(), var9, var5);
               }
            }

            var9.add(var4);
         }

         return this.send(var1, var7.getOid(), var9, var5);
      }
   }

   public NotifyResult send(String var1, String var2, SnmpVarBindList var3) throws SnmpValueException {
      SnmpNotificationInfo var4 = this.d.getNotification(var2);
      return this.send(var1, var4.getOid(), var3);
   }

   public NotifyResult send(String var1, SnmpOid var2, SnmpVarBindList var3) {
      return this.send(var1, (SnmpOid)var2, (SnmpVarBindList)var3, (String)null);
   }

   public NotifyResult send(String var1, SnmpOid var2, SnmpVarBindList var3, String var4) {
      boolean var22 = v;
      if (var1 == null) {
         var1 = this.p;
      }

      if (this.n.isDebugEnabled()) {
         this.n.debug(a("FT;uGRC:d\u001f\b") + var1 + a("\u0019\u0011:x\u000b\b") + var2 + a("\u0019\u00116~\u0001AT-eR") + var4 + a("\u001c\u000b_") + var3);
      }

      Listener var5 = this.m;
      if (var5 != null) {
         try {
            SnmpMessage var6 = this.a(var2, var3, 1, 1, var4);
            var5.handleNotification(var1, var2, var6.getData().getVarBindList(), var4);
         } catch (Throwable var23) {
            this.n.error(a("PI6t\u001fAX:\u007fO\\_uB\u0001XA\u001b~\u001b\\W<t\u001d\u001b}<b\u001bP_0cA]P;u\u0003P\u007f:e\u0006SX6p\u001b\\^;+OEC:r\nFB<\u007f\b"), var23);
         }
      }

      String var27;
      label127: {
         if (this.p.equals(var1)) {
            var27 = this.o;
            if (!var22) {
               break label127;
            }
         }

         var27 = this.f.getTag(var1);
      }

      if (var27 == null) {
         this.n.comms(a("[^ue\u000eR\u00113~\u001a[Uuw\u0000G\u0011;~\u001b\\W,_\u000eXTo1") + var1);
         return new NotifyResult(var1, var2, var3, var4, (String)null, 0, 0, new PeerResult[0]);
      } else {
         int var7 = this.f.getType(var1);
         Vector var8 = null;
         if (this.l) {
            var8 = this.a(var27, var2, var3);
            if (this.n.isDebugEnabled()) {
               this.n.debug(a("\u0018\u001cuw\u0006YE0c\nQ\u0011%t\nGBo") + var8);
            }
         } else {
            var8 = this.a.getTarget().getPeers(var27);
            if (this.n.isDebugEnabled()) {
               this.n.debug(a("\u0018\u001cud\u0001SX9e\nGT11\u001fPT'bU") + var8);
            }
         }

         if (var8 != null && var8.size() != 0) {
            SnmpMessage var9 = null;
            SnmpMessage var10 = null;
            SnmpMessage var11 = null;
            PeerResult[] var12 = new PeerResult[var8.size()];
            int var13 = 0;
            int var14 = 0;
            int var15 = 0;
            Iterator var16 = var8.iterator();

            while(var16.hasNext()) {
               SnmpPeer var17;
               int var19;
               SnmpMessage var20;
               label136: {
                  var17 = (SnmpPeer)var16.next();
                  int var18 = var17.getParameters().getDefaultProfile().getSnmpVersion();
                  var19 = this.a(var17);
                  var12[var15] = new PeerResult(var17, -1, (Exception)null);
                  if (var18 == 0) {
                     if (var7 == 1) {
                        if (var9 == null) {
                           var9 = this.a(var2, var3);
                        }

                        var20 = var9;
                        if (!var22) {
                           break label136;
                        }
                     }

                     var20 = null;
                     if (!var22) {
                        break label136;
                     }
                  }

                  if (var18 == 1) {
                     if (var10 == null) {
                        var10 = this.a(var2, var3, 1, var7, var4);
                     }

                     var20 = var10;
                     var10.getData().setRequestId(var19);
                     if (!var22) {
                        break label136;
                     }
                  }

                  if (var11 == null) {
                     var11 = this.a(var2, var3, 3, var7, var4);
                  }

                  var20 = var11;
                  var11.getData().setRequestId(var19);
               }

               label98: {
                  if (var20 != null) {
                     var20.setMsgID(var19);
                     var20.setMessageProfile(var17.getParameters().getInformProfile());
                     if (this.n.isDebugEnabled()) {
                        this.n.debug(a("FT;u\u0006[Vu\u007f\u0000AX3x\fTE<~\u0001\u0015") + var17.getTransportEntity());
                     }

                     if (var20.getData().getType() == 166) {
                        try {
                           SnmpPendingRequest var21 = this.a.getSession().startInform(var17, var20.getData().getVarBindList());
                           var12[var15].a(var21);
                           ++var13;
                           var12[var15].a(-1);
                           break label98;
                        } catch (SnmpException var25) {
                           this.n.error(a("PC'~\u001d\u0015B0\u007f\u000b\\_21\u0006[W:c\u0002\u0015E:1\u001fPT'1R") + var17, var25);
                           var12[var15].a(0);
                           var12[var15].a((Exception)var25);
                           ++var14;
                           break label98;
                        } catch (Exception var26) {
                           this.n.error(a("[D9}OFT&b\u0006Z_u,") + var17, var26);
                           var12[var15].a(0);
                           var12[var15].a(var26);
                           ++var14;
                           if (!var22) {
                              break label98;
                           }
                        }
                     }

                     if (this.n.isDebugEnabled()) {
                        this.n.debug(a("FT;uBAC4aU\u0015") + var17);
                     }

                     if (var17.getOidMap() != null) {
                        if (this.n.isDebugEnabled()) {
                           this.n.debug(a("FT;uBAC4aU\u0015D&x\u0001R\u0011\u001ax\u000bxP%+O") + var17.getOidMap());
                        }

                        var20.setOidMap(var17.getOidMap());
                     }

                     try {
                        this.b.send(var20, var17.getTransportEntity());
                        ++var13;
                        var12[var15].a(1);
                        break label98;
                     } catch (SnmpException var24) {
                        var12[var15].a(0);
                        var12[var15].a((Exception)var24);
                        ++var14;
                        this.n.error(a("PC'~\u001d\u0015B0\u007f\u000b\\_21\u0001ZE<w\u0006VP!x\u0000[\u00110\u007f\u001b\\E,,") + var17.getTransportEntity());
                        this.n.error(a("PC'~\u001d\u0015B0\u007f\u000b\\_21\u0001ZE<w\u0006VP!x\u0000["), var24);
                        if (!var22) {
                           break label98;
                        }
                     }
                  }

                  var12[var15].a(0);
                  var12[var15].a((Exception)(new SnmpException(a("\\_6~\u0002EP!x\rYTu|\nFB4v\n\u0015E,a\n\u0015W:cOET0cOCT'b\u0006Z_"))));
                  ++var14;
               }

               ++var15;
               if (var22) {
                  break;
               }
            }

            return new NotifyResult(var1, var2, var3, var4, var27, var13, var14, var12);
         } else {
            this.n.comms(a("[^ua\nPC&1\u001cP]0r\u001bPUuw\u0000G\u0011!p\b\u000f\u0011") + var27);
            return new NotifyResult(var1, var2, var3, var4, var27, 0, 0, new PeerResult[0]);
         }
      }
   }

   private SnmpVarBindList a(SnmpObjectInfo[] var1, SnmpOid var2) throws SnmpMibException {
      boolean var9 = v;
      SnmpVarBindList var3 = new SnmpVarBindList();
      int var4 = 0;

      while(var4 < var1.length) {
         SnmpOid var6;
         label30: {
            SnmpObjectInfo var5 = var1[var4];
            var6 = (SnmpOid)var5.getOid().clone();
            if (var5.isColumnar()) {
               if (var2 == null) {
                  throw new SnmpMibException(a("[^ux\u0001QT-1\u001fG^#x\u000bPUuw\u0000G\u00116~\u0003@\\;p\u001d\u0015^7{\nVEu") + var5.getName());
               }

               var6.append(var2);
               if (!var9) {
                  break label30;
               }
            }

            var6.append(0L);
         }

         var3.add(var6);
         ++var4;
         if (var9) {
            break;
         }
      }

      SnmpRequestPDU var10 = new SnmpRequestPDU();
      var10.setRequestId(0);
      var10.setType(160);
      var10.setVarBindList(var3);
      var10.setVersion(1);
      var10.setCommunity(a("{~\u0001X)|t\u0007").getBytes());
      SnmpMessage var11 = new SnmpMessage();
      var11.setVersion(1);
      var11.setMsgID(0);
      var11.setData(var10);
      SnmpPendingIndication var12 = new SnmpPendingIndication(this.b, (TransportEntity)null, var11, this.a);
      SnmpAccessPolicy var7 = new SnmpAccessPolicy(a("{~\u0001X)|t\u0007"), true);
      this.a.getResponder().handleMessage(var12, var7);
      if (var12.getErrorStatus() != 0) {
         SnmpVarBind var8 = var3.get(var12.getErrorIndex() - 1);
         throw new SnmpMibException(a("PC'~\u001d\u0015S x\u0003QX;vOCP'x\u000eW]0<\r\\_1x\u0001RB") + (var8 == null ? "" : a("\u000f\u0011") + var8.getOid()));
      } else {
         return var12.getResponseVBL();
      }
   }

   private SnmpMessage a(SnmpOid var1, SnmpVarBindList var2) {
      SnmpTrapPDU var3;
      label30: {
         boolean var7 = v;
         var3 = new SnmpTrapPDU();
         var3.setAgentAddr(this.a.getAgentAddress());
         var3.setTimestamp(this.b.getSysUpTime());
         var3.setVarBindList(var2);
         if (t.contains(var1)) {
            var3.setEnterprise(this.a.getSysObjectID());
            var3.setGenericTrap((int)var1.getLast() - 1);
            var3.setSpecificTrap(0);
            if (!var7) {
               break label30;
            }
         }

         long var4 = 0L;

         try {
            var4 = var1.get(var1.getLength() - 2);
         } catch (SnmpValueException var8) {
         }

         label22: {
            if (var4 == 0L) {
               var3.setEnterprise(var1.getParent().getParent());
               if (!var7) {
                  break label22;
               }
            }

            var3.setEnterprise(var1.getParent());
         }

         var3.setGenericTrap(6);
         var3.setSpecificTrap((int)var1.getLast());
      }

      SnmpMessage var9 = new SnmpMessage();
      var9.setVersion(0);
      var9.setData(var3);
      return var9;
   }

   private SnmpMessage a(SnmpOid var1, SnmpVarBindList var2, int var3, int var4, String var5) {
      boolean var6;
      SnmpVarBindList var7;
      boolean var11;
      label63: {
         var11 = v;
         var6 = false;
         var7 = new SnmpVarBindList();
         if (var2 == null || var2.size() == 0 || !var2.get(0).getOid().equals(u)) {
            var7.add((SnmpOid)u, (SnmpValue)(new SnmpTimeTicks(this.b.getSysUpTime())));
            if (!var11) {
               break label63;
            }
         }

         var6 = true;
         var2 = (SnmpVarBindList)var2.clone();
         var7.add(var2.get(0));
         var2.remove(0);
      }

      label64: {
         if (var2 == null || var2.size() == 0 || !var2.get(0).getOid().equals(r)) {
            var7.add((SnmpOid)r, (SnmpValue)var1);
            if (!var11) {
               break label64;
            }
         }

         if (!var6) {
            var2 = (SnmpVarBindList)var2.clone();
         }

         var7.add(var2.get(0));
         var2.remove(0);
      }

      if (var2 != null) {
         var7.add(var2);
      }

      SnmpRequestPDU var8;
      label40: {
         var8 = new SnmpRequestPDU();
         if (var4 == 1) {
            var8.setType(167);
            if (!var11) {
               break label40;
            }
         }

         var8.setType(166);
      }

      var8.setVarBindList(var7);
      var8.setVersion(var3);
      SnmpMessage var9 = new SnmpMessage();
      var9.setVersion(var3);
      var9.setData(var8);
      if (var3 == 3) {
         if (var5 != null && var5.length() > 0) {
            SnmpContext var10 = new SnmpContext(this.b.getEngineID(), var5);
            var9.setContext(var10);
            if (!var11) {
               return var9;
            }
         }

         var9.setSnmpEngineID(this.b.getEngineID());
         var9.setContext(this.j);
      }

      return var9;
   }

   public RequestIdGenerator getRequestIdGenerator() {
      return this.e;
   }

   public void setRequestIdGenerator(RequestIdGenerator var1) {
      this.e = var1;
   }

   private synchronized int a(SnmpPeer var1) {
      RequestIdGenerator var2 = this.e;
      if (var2 != null) {
         try {
            return var2.getNextRequestId(var1);
         } catch (Exception var4) {
            this.n.error(a("PI6t\u001fAX:\u007fO\\_uv\n[T'p\u001b\\_21\u001dP@ t\u001cA\u0011<u"), var4);
            return this.k++;
         }
      } else {
         return this.k++;
      }
   }

   private Vector a(String var1, SnmpOid var2, SnmpVarBindList var3) {
      boolean var28 = v;
      if (this.n.isDebugEnabled()) {
         this.n.debug(a("SX9e\nGp1u\u001daP7}\ng^\"bG") + var1 + "," + var2 + "," + var3 + ")");
      }

      SnmpTargetAddrTable var4 = this.a.getTarget().getAddrTable();
      Vector var5 = new Vector();
      Iterator var6 = var4.getRows();

      while(var6.hasNext()) {
         SnmpMibTableRow var7 = (SnmpMibTableRow)var6.next();
         if (var7.isActive()) {
            if (this.n.isDebugEnabled()) {
               this.n.debug(a("\u0018\u001cup\u000bQC\u0001p\rYT\u0007~\u0018\u000f\u0011") + var7.getIndex());
            }

            String var8 = var7.getLeaf(6).getValue().getString();
            StringTokenizer var9 = new StringTokenizer(var8, a("\u00158X\u001bc"), false);
            boolean var10 = false;

            String var11;
            boolean var10000;
            while(true) {
               if (var9.hasMoreTokens()) {
                  label156: {
                     var11 = var9.nextToken();
                     var10 = false;
                     this.n.debug(a("\u0018\u001cu\u007f\nMExe\u000eR\u000bu") + var11);
                     var10000 = var11.equals(var1);
                     if (var28) {
                        break;
                     }

                     if (var10000) {
                        var10 = true;

                        try {
                           String var12 = var7.getLeaf(7).getValue().getString();
                           SnmpMibTableRow var13 = this.g.get(var12);
                           if (var13 != null) {
                              String var14 = var13.getLeaf(1).getValue().getString();
                              boolean var15 = false;
                              boolean var16 = true;
                              SnmpOid var17 = new SnmpOid();
                              SnmpOid var18 = new SnmpOid();
                              if (this.n.isDebugEnabled()) {
                                 this.n.debug(a("\u0018\u001cuw\u0006YE0c?G^3x\u0003P\u007f4|\n\u000f\u0011") + var12);
                              }

                              Iterator var19 = this.h.getRows();

                              label134: {
                                 while(var19.hasNext()) {
                                    SnmpMibTableRow var20 = (SnmpMibTableRow)var19.next();
                                    SnmpValue[] var21 = var20.getIndexValues();
                                    var10000 = this.n.isDebugEnabled();
                                    if (var28) {
                                       break label134;
                                    }

                                    if (var10000) {
                                       this.n.debug(a("\u0018\u001cur\u0007PR>x\u0001R\u00113x\u0003AT'+O") + var21[0]);
                                    }

                                    if (var14.equals(var21[0].getString())) {
                                       if (this.n.isDebugEnabled()) {
                                          this.n.debug(a("\u0018\u001cu}\u0000VP!t\u000b\u0015W<}\u001bPCo1") + var21[1]);
                                       }

                                       SnmpOid var22 = (SnmpOid)var21[1];
                                       byte[] var23 = var20.getLeaf(2).getValue().getByteArray();
                                       boolean var24 = var20.getLeaf(3).getValue().intValue() == 1;
                                       int var25 = var22.getLength();
                                       int var26 = var18.getLength();
                                       int var27 = var17.getLength();
                                       if (monfox.toolkit.snmp.agent.notify.b.a(var22, var2, var23) && (var25 > var27 || var25 == var27 && var22.compareTo(var17) > 0)) {
                                          var17 = var22;
                                          var15 = var24;
                                          if (this.n.isDebugEnabled()) {
                                             this.n.debug(a("[^!x\tL\u00113x\u0003AT'1\u0002TE6yG") + var22 + a("\u0019X;r\u0003@U0uR") + var24 + ")");
                                          }
                                       }

                                       if (monfox.toolkit.snmp.agent.notify.b.a(var22, var3, var23) && (var25 > var26 || var25 == var26 && var22.compareTo(var18) > 0)) {
                                          var18 = var22;
                                          var16 = var24;
                                          if (this.n.isDebugEnabled()) {
                                             this.n.debug(a("ZX11\t\\]!t\u001d\u0015\\4e\f]\u0019") + var22 + a("\u0019X;r\u0003@U0uR") + var24 + ")");
                                          }
                                       }
                                    }

                                    if (var28) {
                                       break;
                                    }
                                 }

                                 var10000 = var15;
                              }

                              label161: {
                                 if (var10000 && var16) {
                                    var10 = true;
                                    this.n.debug(a("\u0018\u001cuw\u0006YE0cOEP&b\nQ\u0011t0"));
                                    if (!var28) {
                                       break label161;
                                    }
                                 }

                                 if (this.n.isDebugEnabled()) {
                                    this.n.debug(a("\u0018\u001cuw\u0006YE0cOSP<}\nQ\u0019;~\u001b\\Wh") + var15 + a("\u0019^<u\u001c\b") + var16 + a("\u001c\u0011t0"));
                                 }

                                 var10 = false;
                              }

                              if (!var28) {
                                 break label156;
                              }
                           }

                           var10 = true;
                           this.n.debug(a("\u0018\u001cuw\u0006YE0cOEP&b\nQ\u000bu\u007f\u0000\u0015W<}\u001bPC<\u007f\b"));
                           break label156;
                        } catch (Exception var29) {
                           this.n.debug(a("\u0018\u001cu\u007f\u0000\u0015W<}\u001bPCua\u001dZW<}\n\u0015W:cOAP21H") + var1 + "'", var29);
                        }
                     }

                     if (!var28) {
                        continue;
                     }
                  }
               }

               var10000 = var10;
               break;
            }

            if (var10000) {
               label162: {
                  if (this.n.isDebugEnabled()) {
                     this.n.debug(a("\u0018\u0011!p\u001dRT!1\u001fTB&t\u000b\u000f\u0011") + var7);
                  }

                  var11 = var7.getLeaf(1).getValue().getString();
                  SnmpPeer var30 = this.i.getPeer(var11);
                  if (var30 != null) {
                     var5.add(var30);
                     if (!var28) {
                        break label162;
                     }
                  }

                  this.n.debug(a("\u0018\u001cur\u000e[_:eOVC0p\u001bP\u0011%t\nG\u00113~\u001d\u0015C:fU\u0015") + var7);
               }
            }

            if (var28) {
               break;
            }
         }
      }

      return var5;
   }

   public void setFilteringMode(boolean var1) {
      this.l = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 53;
               break;
            case 1:
               var10003 = 49;
               break;
            case 2:
               var10003 = 85;
               break;
            case 3:
               var10003 = 17;
               break;
            default:
               var10003 = 111;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public interface Listener {
      void handleNotification(String var1, SnmpOid var2, SnmpVarBindList var3, String var4);
   }

   public class PeerResult {
      public static final int UNKNOWN = -1;
      public static final int FAILURE = 0;
      public static final int SUCCESS = 1;
      private SnmpPeer a;
      private int b;
      private Exception c;
      private SnmpPendingRequest d;

      PeerResult(SnmpPeer var2, int var3, Exception var4) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
      }

      public SnmpPeer getPeer() {
         return this.a;
      }

      public int getResult() {
         return this.b;
      }

      public Exception getException() {
         return this.c;
      }

      public SnmpPendingRequest getPendingRequest() {
         return this.d;
      }

      void a(SnmpPeer var1) {
         this.a = var1;
      }

      void a(int var1) {
         this.b = var1;
      }

      void a(Exception var1) {
         this.c = var1;
      }

      void a(SnmpPendingRequest var1) {
         this.d = var1;
      }

      public String toString() {
         boolean var2 = SnmpNotifier.v;
         String var1;
         switch (this.b) {
            case 1:
               var1 = a("\u0000\"\u001f8*\u0000$");
               if (!var2) {
                  break;
               }
            case 0:
               var1 = a("\u00156\u00157:\u00012");
               if (!var2) {
                  break;
               }
            default:
               var1 = a("\u00069\u00175 \u00049");
         }

         return "(" + var1 + a("sM|") + this.a + a("sM|") + (this.c == null ? a("o\u00193V\n+\u00149\u000b\u001b:\u00182E") : this.c.toString()) + ")";
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 83;
                  break;
               case 1:
                  var10003 = 119;
                  break;
               case 2:
                  var10003 = 92;
                  break;
               case 3:
                  var10003 = 123;
                  break;
               default:
                  var10003 = 111;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public class NotifyResult {
      private String a;
      private SnmpOid b;
      private SnmpVarBindList c;
      private String d;
      private String e;
      private int f = 0;
      private int g = 0;
      private PeerResult[] h;

      NotifyResult(String var2, SnmpOid var3, SnmpVarBindList var4, String var5, String var6, int var7, int var8, PeerResult[] var9) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
         this.d = var5;
         this.e = var6;
         this.f = var7;
         this.g = var8;
         this.h = var9;
      }

      public String getNotifyName() {
         return this.a;
      }

      public SnmpOid getNotifyOid() {
         return this.b;
      }

      public SnmpVarBindList getVarBindList() {
         return this.c;
      }

      public String getContextName() {
         return this.d;
      }

      public String getTag() {
         return this.e;
      }

      public int getSentCount() {
         return this.f;
      }

      public int getErrorCount() {
         return this.g;
      }

      public PeerResult[] getResults() {
         return this.h;
      }

      public synchronized void awaitCompletion() {
         boolean var4 = SnmpNotifier.v;
         int var1 = 0;

         while(var1 < this.h.length) {
            SnmpPendingRequest var2 = this.h[var1].getPendingRequest();
            if (var2 != null) {
               try {
                  label24: {
                     var2.expectCompletion();
                     if (var2.getErrorStatus() == 0) {
                        this.h[var1].a(1);
                        if (!var4) {
                           break label24;
                        }
                     }

                     throw new SnmpErrorException(var2.getErrorStatus(), var2.getErrorIndex(), var2.getResponseVarBindList());
                  }
               } catch (Exception var5) {
                  this.h[var1].a(0);
                  this.h[var1].a(var5);
                  ++this.g;
               }
            }

            ++var1;
            if (var4) {
               break;
            }
         }

      }

      public String toString() {
         boolean var3 = SnmpNotifier.v;
         StringBuffer var1 = new StringBuffer();
         var1.append(a("= y\u0007Ryb 5\u0014N_1/\u0007[yt\u0001R\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_"));
         var1.append("\n");
         var1.append(a("=-t2\u001dCd2%<V`1|R\u0017-t|O\u0017-") + this.a);
         var1.append(a("=-t2\u001dCd25\u0011Vy=3\u001cxd0|O\u0017-") + this.b);
         var1.append(a("=-t?\u001dYy1$\u0006yl99R\u0017-t|O\u0017-") + this.d);
         var1.append(a("=-t2\u001dCd2%&Vjt|R\u0017-t|O\u0017-") + this.e);
         var1.append("\n");
         var1.append(a("=-t,\u0017R\u007f\u00173\u0007Yyt|R\u0017-t|O\u0017-") + this.h.length);
         var1.append(a("=-t/\u0017Yy\u00173\u0007Yyt|R\u0017-t|O\u0017-") + this.f);
         var1.append(a("=-t9\u0000Eb&\u001f\u001dBc |R\u0017-t|O\u0017-") + this.g);
         var1.append("\n");
         int var2 = 0;

         while(true) {
            if (var2 < this.h.length) {
               var1.append(a("=-t|")).append(this.h[var2]);
               ++var2;
               if (var3) {
                  break;
               }

               if (!var3) {
                  continue;
               }

               SnmpException.b = !SnmpException.b;
            }

            var1.append("\n");
            var1.append(a("= yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_\u001a yq_"));
            var1.append("\n");
            break;
         }

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
                  var10003 = 55;
                  break;
               case 1:
                  var10003 = 13;
                  break;
               case 2:
                  var10003 = 84;
                  break;
               case 3:
                  var10003 = 92;
                  break;
               default:
                  var10003 = 114;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class DefaultRequestIdGenerator implements RequestIdGenerator {
      private int a;
      private int b;
      private int c;

      public DefaultRequestIdGenerator() {
         this(100, 100, Integer.MAX_VALUE);
      }

      public DefaultRequestIdGenerator(int var1, int var2, int var3) {
         this.a = 100;
         this.b = Integer.MAX_VALUE;
         this.c = this.a;

         try {
            this.a = var2;
            this.b = var3;
            if (var1 < 0) {
               this.c = (int)(Math.random() * (double)((float)(var3 - var2)) + (double)var2);
               if (!SnmpNotifier.v) {
                  return;
               }
            }

            this.c = var1;
         } catch (Exception var5) {
            var5.printStackTrace();
         }

      }

      public synchronized int getNextRequestId(SnmpPeer var1) {
         int var2 = this.c++;
         if (this.c >= this.b || this.c < 0) {
            this.c = this.a;
         }

         return var2;
      }

      public int getCurrentRequestId() {
         return this.c;
      }

      public void setCurrentRequestId(int var1) {
         this.c = var1;
      }
   }

   public interface RequestIdGenerator {
      int getNextRequestId(SnmpPeer var1);
   }
}
