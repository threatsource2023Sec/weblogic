package monfox.toolkit.snmp.agent.x.master;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpTimeTicks;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.x.common.AgentXCommunicationsException;
import monfox.toolkit.snmp.agent.x.common.AgentXErrorException;
import monfox.toolkit.snmp.agent.x.common.AgentXServerSocketFactory;
import monfox.toolkit.snmp.agent.x.common.AgentXTimeoutException;
import monfox.toolkit.snmp.agent.x.connection.AgentXConnection;
import monfox.toolkit.snmp.agent.x.connection.MasterAgentXConnection;
import monfox.toolkit.snmp.agent.x.connection.MasterAgentXServer;
import monfox.toolkit.snmp.agent.x.pdu.AgentXPDU;
import monfox.toolkit.snmp.agent.x.pdu.CleanupSetPDU;
import monfox.toolkit.snmp.agent.x.pdu.ClosePDU;
import monfox.toolkit.snmp.agent.x.pdu.CoderException;
import monfox.toolkit.snmp.agent.x.pdu.CommitSetPDU;
import monfox.toolkit.snmp.agent.x.pdu.GetNextPDU;
import monfox.toolkit.snmp.agent.x.pdu.GetPDU;
import monfox.toolkit.snmp.agent.x.pdu.IndexAllocatePDU;
import monfox.toolkit.snmp.agent.x.pdu.IndexDeallocatePDU;
import monfox.toolkit.snmp.agent.x.pdu.NotifyPDU;
import monfox.toolkit.snmp.agent.x.pdu.OpenPDU;
import monfox.toolkit.snmp.agent.x.pdu.PingPDU;
import monfox.toolkit.snmp.agent.x.pdu.RegisterPDU;
import monfox.toolkit.snmp.agent.x.pdu.ResponsePDU;
import monfox.toolkit.snmp.agent.x.pdu.SearchRange;
import monfox.toolkit.snmp.agent.x.pdu.TestSetPDU;
import monfox.toolkit.snmp.agent.x.pdu.UndoSetPDU;
import monfox.toolkit.snmp.agent.x.pdu.UnregisterPDU;

public class MasterAgentX {
   private static final SnmpOid a = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 1L, 1L, 4L, 1L, 0L});
   private static final SnmpOid b = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 2L, 1L, 1L, 3L, 0L});
   private static AgentXServerSocketFactory c;
   private Set d;
   private int e;
   private int f;
   private Hashtable g;
   private b h;
   private c i;
   private RequestProcessor j;
   private MasterAgentXServer k;
   private SnmpAgent l;
   private Logger m;
   public static boolean n;

   public MasterAgentX(SnmpAgent var1, InetAddress var2, int var3, int var4) throws IOException {
      boolean var5 = n;
      super();
      this.d = null;
      this.e = 10485760;
      this.f = 1;
      this.g = new Hashtable();
      this.h = null;
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = null;
      this.m = Logger.getInstance(a("T\u0005P\u0004\u0016"), a("Q\u0011[\u0007\u0012=\u000e"), a("]7m=#b\u0017y,(d\u000e"));
      this.l = var1;
      this.k = new MasterAgentXServer(var2, var3, var4);
      this.j = new RequestProcessor();
      this.k.setRequestListener(this.j);
      this.k.setStatusListener(this.j);
      this.h = new b();
      this.i = new c(this);
      this.l.getResponder().addPreProcessor(this.i);
      if (var5) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public void startup() throws IOException {
      this.k.startup();
   }

   public void shutdown() {
      this.k.shutdown();
   }

   private void a(Session var1) {
      boolean var5 = n;
      Iterator var2 = var1.getSubtrees().iterator();

      while(true) {
         if (var2.hasNext()) {
            d var3 = (d)var2.next();

            label21: {
               try {
                  this.c(var3);
               } catch (Exception var6) {
                  this.m.error(a("s7p')dv}%#q8k9fc3m:/\u007f81:3r\"l,#c"), var6);
                  break label21;
               }

               if (var5) {
                  break;
               }
            }

            if (!var5) {
               continue;
            }
         }

         this.h.a(var1);
         break;
      }

   }

   void a(d var1) throws AgentXErrorException {
      boolean var9 = n;
      SnmpMib var2 = this.l.getMib();
      if (var1.getContext() != null && var1.getContext().length() > 0) {
         var2 = this.l.getContextMib(var1.getContext());
         if (var2 == null) {
            this.m.warn(a("e8m<6`9l=#tv}&(d3f=fv$q$fc#|(!u8jsf") + var1.getContext());
            throw new AgentXErrorException(262, 0);
         }
      }

      List var3 = var1.getRegisteredOids();
      Iterator var4 = var3.iterator();

      do {
         boolean var10000 = var4.hasNext();

         SnmpOid var5;
         label66:
         while(true) {
            if (!var10000) {
               return;
            }

            var5 = (SnmpOid)var4.next();
            this.m.debug(a("b3y 5d3l (wvm<$d${,|0") + var5);
            SnmpMibNode var6 = var2.get(var5);
            if (var6 != null) {
               this.m.error(a("~9z,fq:l,'t/>,>y%j:|0") + var6);
               if (var6 instanceof a) {
                  throw new AgentXErrorException(263, 0);
               }

               this.m.error(a("~9pd\u0007w3p=\u001e=8q-#*v") + var6);
               throw new AgentXErrorException(267, 0);
            }

            var6 = var2.getNext(var5, false);

            while(true) {
               if (var6 == null) {
                  break label66;
               }

               var10000 = this.m.isDebugEnabled();
               if (var9) {
                  break;
               }

               if (var10000) {
                  this.m.debug(a("0v3dfs>{*-y8yi(u.ji(\u007f2{sf") + var6);
               }

               if (!var5.contains(var6.getOid())) {
                  break label66;
               }

               if (!(var6 instanceof a)) {
                  this.m.error(a("s7p')dv}&0u$><608q'kQ1{'2H{p&\"ul>") + var6);
                  throw new AgentXErrorException(267, 0);
               }

               SnmpMibNode var7 = var6;
               var6 = var2.getNext(var5, false);
               if (var6.getOid().compareTo(var7.getOid()) <= 0) {
                  this.m.debug(a("w3j\u0007#h\">')dv i*q%j"));
                  if (!var9) {
                     break label66;
                  }
               }

               if (var9) {
                  break label66;
               }
            }
         }

         a var11 = new a(var5, var1.getSession(), var1, var1.isInstance());

         try {
            var2.add(var11);
            var1.addNode(var11);
         } catch (SnmpMibException var10) {
            throw new AgentXErrorException(267, 0);
         }
      } while(!var9);

   }

   void b(d var1) throws AgentXErrorException {
      boolean var11 = n;
      SnmpMib var2 = this.l.getMib();
      if (var1.getContext() != null && var1.getContext().length() > 0) {
         var2 = this.l.getContextMib(var1.getContext());
         if (var2 == null) {
            this.m.warn(a("e8m<6`9l=#tv}&(d3f=fv$q$fc#|(!u8jsf") + var1.getContext());
            throw new AgentXErrorException(262, 0);
         }
      }

      List var3 = var1.getSession().getSubtrees();
      Iterator var4 = var3.iterator();

      label47:
      do {
         boolean var10000 = var4.hasNext();

         while(var10000) {
            d var5 = (d)var4.next();
            List var6 = var5.getNodes();
            Vector var7 = new Vector();
            var7.addAll(var6);
            Iterator var8 = var7.iterator();

            while(true) {
               if (!var8.hasNext()) {
                  continue label47;
               }

               a var9 = (a)var8.next();
               var10000 = var1.getRegisteredOids().contains(var9.getOid());
               if (var11) {
                  break;
               }

               if (var10000) {
                  label55: {
                     SnmpMibNode var10 = var2.get(var9.getOid());
                     if (var10 != var9) {
                        this.m.error(a("~9ji2x3>;#w?m=#b3zi(\u007f2{i%q8p&20${$)f3E") + var9.getOid() + a("Ml>") + var10);
                        if (!var11) {
                           break label55;
                        }
                     }

                     this.m.debug(a("b3s&0y8yi5e4j;#uvp&\"ul>") + var9.getOid());
                     var2.remove((SnmpMibNode)var9);
                     var6.remove(var9);
                  }
               }

               if (var11) {
                  continue label47;
               }
            }
         }

         return;
      } while(!var11);

   }

   void c(d var1) throws AgentXErrorException {
      boolean var7 = n;
      SnmpMib var2 = this.l.getMib();
      if (var1.getContext() != null && var1.getContext().length() > 0) {
         var2 = this.l.getContextMib(var1.getContext());
         if (var2 == null) {
            this.m.warn(a("e8m<6`9l=#tv}&(d3f=fv$q$fc#|(!u8jsf") + var1.getContext());
            throw new AgentXErrorException(262, 0);
         }
      }

      this.m.debug(a("e%w'!0\u001bW\u000b\u001d") + var1.getContext() + a("Ml>") + var2);
      List var3 = var1.getNodes();
      Iterator var4 = var3.iterator();

      while(var4.hasNext()) {
         label25: {
            a var5 = (a)var4.next();
            SnmpMibNode var6 = var2.get(var5.getOid());
            if (var6 != var5) {
               this.m.error(a("~9ji2x3>;#w?m=#b3zi(\u007f2{i%q8p&20${$)f3E") + var5.getOid() + a("Ml>") + var6);
               if (!var7) {
                  break label25;
               }
            }

            var2.remove((SnmpMibNode)var5);
         }

         if (var7) {
            break;
         }
      }

   }

   private void b(Session var1) {
      this.g.put(new Integer(var1.getSessionId()), var1);
   }

   private Session a(int var1) {
      return (Session)this.g.get(new Integer(var1));
   }

   private void b(int var1) {
      this.g.remove(new Integer(var1));
   }

   synchronized int a() {
      return this.f++;
   }

   public void setNotifyName(String var1) {
      boolean var4 = n;
      if (var1 == null) {
         this.d = null;
         if (!var4) {
            return;
         }
      }

      StringTokenizer var2 = new StringTokenizer(var1, a("<v$"), false);
      this.d = new HashSet();

      while(var2.hasMoreTokens()) {
         String var3 = var2.nextToken();
         this.d.add(var3);
         if (var4) {
            break;
         }
      }

   }

   public void addNotifyName(String var1) {
      if (var1 != null) {
         if (this.d == null) {
            this.d = new HashSet();
         }

         StringTokenizer var2 = new StringTokenizer(var1, a("<v$"), false);

         while(var2.hasMoreTokens()) {
            String var3 = var2.nextToken();
            this.d.add(var3);
            if (n) {
               break;
            }
         }

      }
   }

   public void removeNotifyName(String var1) {
      if (var1 != null) {
         if (this.d != null) {
            StringTokenizer var2 = new StringTokenizer(var1, a("<v$"), false);

            while(var2.hasMoreTokens()) {
               String var3 = var2.nextToken();
               this.d.remove(var3);
               if (n) {
                  break;
               }
            }

         }
      }
   }

   public void setNotifyNames(Set var1) {
      this.d = var1;
   }

   public Set getNotifyNames() {
      return this.d;
   }

   public static AgentXServerSocketFactory getServerSocketFactory() {
      return c;
   }

   public static void setServerSocketFactory(AgentXServerSocketFactory var0) {
      c = var0;
   }

   public int getMaxPayloadLength() {
      return this.k.getMaxPayloadLength();
   }

   public void setMaxPayloadLength(int var1) {
      this.k.setMaxPayloadLength(var1);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 16;
               break;
            case 1:
               var10003 = 86;
               break;
            case 2:
               var10003 = 30;
               break;
            case 3:
               var10003 = 73;
               break;
            default:
               var10003 = 70;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class RequestProcessor implements MasterAgentXServer.RequestListener, MasterAgentXServer.StatusListener {
      private RequestProcessor() {
      }

      public void handleRequest(MasterAgentXConnection var1, AgentXConnection.PendingIndication var2, AgentXPDU var3) {
         boolean var6 = MasterAgentX.n;
         Session var4 = MasterAgentX.this.a(var3.getSessionId());
         if (var3.getType() != 1 && var4 == null) {
            MasterAgentX.this.m.comms(a("\u000eQ\\RVQ\u0003@R\u0004\u0018F]NM\u0004M\u000e[K\u0019\u0003GY\u001eK") + var3.getSessionId());
            var2.sendError(257);
         } else {
            if ((var3.getFlags() & 8) != 0) {
               String var5 = var3.getContext();
            }

            switch (var3.getType()) {
               case 1:
                  this.a(var1, var2, var3);
                  if (!var6) {
                     break;
                  }
               case 2:
                  this.a(var4, var1, var2, var3);
                  if (!var6) {
                     break;
                  }
               case 3:
                  this.d(var4, var1, var2, var3);
                  if (!var6) {
                     break;
                  }
               case 4:
                  this.e(var4, var1, var2, var3);
                  if (!var6) {
                     break;
                  }
               case 13:
                  this.b(var4, var1, var2, var3);
                  if (!var6) {
                     break;
                  }
               case 12:
                  this.c(var4, var1, var2, var3);
                  if (!var6) {
                     break;
                  }
               case 14:
                  this.f(var4, var1, var2, var3);
                  if (!var6) {
                     break;
                  }
               case 15:
                  this.g(var4, var1, var2, var3);
                  if (!var6) {
                     break;
                  }
               case 16:
               case 17:
                  MasterAgentX.this.m.error(a("\u001eM]HT\u001bL\\IA\u000f\u0003~yqKQK^A\u0002UKY\u001eK)") + var3);
                  var2.sendError(267);
                  if (!var6) {
                     break;
                  }
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               default:
                  MasterAgentX.this.m.error(a("\u001eM\\XG\u0004D@T^\u000eG\u000em`>\u0003\\XG\u000eJXX@Q\u0003$") + var3);
                  var2.sendError(266);
            }

         }
      }

      void a(MasterAgentXConnection var1, AgentXConnection.PendingIndication var2, AgentXPDU var3) {
         OpenPDU var4 = (OpenPDU)var3;
         Session var5 = MasterAgentX.this.new Session(MasterAgentX.this.a(), var4.getTimeout(), var4.getId(), var4.getDescr(), (var4.getFlags() & 16) != 0, var1);
         MasterAgentX.this.b(var5);
         ResponsePDU var6 = this.a(var5);
         var2.sendResponse(var6);
      }

      void a(Session var1, MasterAgentXConnection var2, AgentXConnection.PendingIndication var3, AgentXPDU var4) {
         ClosePDU var5 = (ClosePDU)var4;
         MasterAgentX.this.a(var1);
         MasterAgentX.this.b(var1.getSessionId());
         ResponsePDU var6 = this.a(var1);
         var3.sendResponse(var6);
      }

      void b(Session var1, MasterAgentXConnection var2, AgentXConnection.PendingIndication var3, AgentXPDU var4) {
         PingPDU var5 = (PingPDU)var4;
         ResponsePDU var6 = this.a(var1);
         var3.sendResponse(var6);
      }

      void c(Session var1, MasterAgentXConnection var2, AgentXConnection.PendingIndication var3, AgentXPDU var4) {
         boolean var13 = MasterAgentX.n;
         NotifyPDU var5 = (NotifyPDU)var4;
         String var6 = var5.getContext();
         SnmpVarBindList var7 = var5.getVarBindList();

         try {
            if (var7.size() == 0) {
               var3.sendError(268);
               return;
            }

            SnmpOid var9;
            label75: {
               SnmpOid var8 = var7.get(0).getOid();
               if (var8.equals(MasterAgentX.b)) {
                  if (var7.size() == 1) {
                     var3.sendError(268);
                     return;
                  }

                  var9 = var7.get(1).getOid();
                  if (!var9.equals(MasterAgentX.a)) {
                     var3.sendError(268);
                     return;
                  }

                  if (!var13) {
                     break label75;
                  }
               }

               if (var8.equals(MasterAgentX.a)) {
                  var7.insert(0, new SnmpVarBind(MasterAgentX.b, new SnmpTimeTicks(MasterAgentX.this.l.getEngine().getSysUpTime())));
                  if (!var13) {
                     break label75;
                  }
               }

               var3.sendError(268);
               return;
            }

            var9 = (SnmpOid)var7.get(1).getValue();
            Set var10 = MasterAgentX.this.getNotifyNames();
            if (var10 == null) {
               MasterAgentX.this.m.debug(a("\u0018F@YM\u0005D\u000eIKKbbq\u0004\u0005LZTB\u0012\u0003IOK\u001eS]"));
               var10 = MasterAgentX.this.l.getNotifier().getNotifyTable().getGroupNames();
            }

            label54: {
               if (var10 != null) {
                  Iterator var11 = var10.iterator();

                  while(var11.hasNext()) {
                     String var12 = (String)var11.next();
                     MasterAgentX.this.l.getNotifier().send(var12, var9, var7, var6);
                     if (var13 && var13) {
                        break label54;
                     }
                  }

                  if (!var13) {
                     break label54;
                  }
               }

               MasterAgentX.this.m.debug(a("\u0005L\u000eSK\u001fJHTG\nWGRJKD\\RQ\u001bP\u000e[K\u001eMJ"));
            }

            ResponsePDU var15 = this.a(var1);
            var3.sendResponse(var15);
         } catch (Exception var14) {
            MasterAgentX.this.m.error(a("\u000e[MXT\u001fJAS\u0004\u0002M\u000eNA\u0005GGSCKMAIM\rJM\\P\u0002L@"), var14);
            var3.sendError(268);
         }

      }

      void d(Session var1, MasterAgentXConnection var2, AgentXConnection.PendingIndication var3, AgentXPDU var4) {
         RegisterPDU var5 = (RegisterPDU)var4;

         ResponsePDU var7;
         try {
            d var6 = new d(var1, var5.getContext(), var5.getSubtree(), var5.getRangeSubid(), var5.getUpperBound(), var5.getTimeout(), var5.getPriority(), var5.isInstance());
            MasterAgentX.this.a(var6);
            var1.addSubtree(var6);
            var7 = this.a(var1);
            var3.sendResponse(var7);
         } catch (AgentXSubtreeException var8) {
            MasterAgentX.this.m.comms(a("\u000eQ\\RVKJ@\u001dW\u001eAZOA\u000e\u0003\\XC\u0002PZOE\u001fJAS"), var8);
            var7 = this.a(var1, var8.getError());
            var3.sendResponse(var7);
         } catch (AgentXErrorException var9) {
            MasterAgentX.this.m.comms(a("\u000eQ\\RVKJ@\u001dW\u001eAZOA\u000e\u0003\\XC\u0002PZOE\u001fJAS"), var9);
            var7 = this.a(var1, var9.getError());
            var3.sendResponse(var7);
         }

      }

      void e(Session var1, MasterAgentXConnection var2, AgentXConnection.PendingIndication var3, AgentXPDU var4) {
         UnregisterPDU var5 = (UnregisterPDU)var4;

         ResponsePDU var7;
         try {
            d var6 = new d(var1, var5.getContext(), var5.getSubtree(), var5.getRangeSubid(), var5.getUpperBound(), 0, var5.getPriority(), var5.isInstance());
            MasterAgentX.this.b(var6);
            var7 = this.a(var1);
            var3.sendResponse(var7);
         } catch (AgentXSubtreeException var8) {
            MasterAgentX.this.m.comms(a("\u000eQ\\RVKJ@\u001dW\u001eAZOA\u000e\u0003\\XC\u0002PZOE\u001fJAS"), var8);
            var7 = this.a(var1, var8.getError());
            var3.sendResponse(var7);
         } catch (AgentXErrorException var9) {
            MasterAgentX.this.m.comms(a("\u000eQ\\RVKJ@\u001dW\u001eAZOA\u000e\u0003\\XC\u0002PZOE\u001fJAS"), var9);
            var7 = this.a(var1, var9.getError());
            var3.sendResponse(var7);
         }

      }

      void f(Session var1, MasterAgentXConnection var2, AgentXConnection.PendingIndication var3, AgentXPDU var4) {
         IndexAllocatePDU var5 = (IndexAllocatePDU)var4;

         ResponsePDU var7;
         try {
            SnmpVarBindList var6 = MasterAgentX.this.h.allocateIndexes(var1, var5.getContext(), var5.getVarBindList(), var5.isSet(2), var5.isSet(4));
            var7 = this.a(var1);
            var7.setVarBindList(var6);
            var3.sendResponse(var7);
         } catch (AgentXErrorException var8) {
            MasterAgentX.this.m.comms(a("\u000eQ\\RVKJ@\u001dM\u0005GKE\u0004\nOBRG\nWGRJ"), var8);
            var7 = this.a(var1, var8.getError(), var8.getIndex());
            var3.sendResponse(var7);
         }

      }

      void g(Session var1, MasterAgentXConnection var2, AgentXConnection.PendingIndication var3, AgentXPDU var4) {
         IndexDeallocatePDU var5 = (IndexDeallocatePDU)var4;

         try {
            MasterAgentX.this.h.deallocateIndexes(var1, var5.getContext(), var5.getVarBindList());
            ResponsePDU var6 = this.a(var1);
            var6.setVarBindList(var5.getVarBindList());
            var3.sendResponse(var6);
         } catch (AgentXErrorException var8) {
            MasterAgentX.this.m.comms(a("\u000eQ\\RVKJ@\u001dM\u0005GKE\u0004\u000fFOQH\u0004@OIM\u0004M"), var8);
            ResponsePDU var7 = this.a(var1, var8.getError(), var8.getIndex());
            var3.sendResponse(var7);
         }

      }

      private ResponsePDU a(Session var1, int var2) {
         ResponsePDU var3 = new ResponsePDU();
         var3.setSessionId(var1.getSessionId());
         var3.setError(var2);
         return var3;
      }

      private ResponsePDU a(Session var1, int var2, int var3) {
         ResponsePDU var4 = new ResponsePDU();
         var4.setSessionId(var1.getSessionId());
         var4.setError(var2);
         var4.setIndex(var3);
         return var4;
      }

      private ResponsePDU a(Session var1) {
         ResponsePDU var2 = new ResponsePDU();
         var2.setSessionId(var1.getSessionId());
         var2.setSysUpTime((long)MasterAgentX.this.l.getEngine().getSysUpTime());
         return var2;
      }

      public void connectionUp(MasterAgentXServer var1, MasterAgentXConnection var2) {
      }

      public void connectionDown(MasterAgentXServer var1, MasterAgentXConnection var2) {
         HashSet var3 = new HashSet();
         var3.addAll(MasterAgentX.this.g.entrySet());
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            Map.Entry var5 = (Map.Entry)var4.next();
            Session var6 = (Session)var5.getValue();
            if (var6.getConnection() == var2) {
               MasterAgentX.this.a(var6);
               MasterAgentX.this.b(var6.getSessionId());
            }

            if (MasterAgentX.n) {
               break;
            }
         }

      }

      // $FF: synthetic method
      RequestProcessor(Object var2) {
         this();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 107;
                  break;
               case 1:
                  var10003 = 35;
                  break;
               case 2:
                  var10003 = 46;
                  break;
               case 3:
                  var10003 = 61;
                  break;
               default:
                  var10003 = 36;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class Transaction {
      private int a = -1;
      private int b = -1;

      Transaction(int var2, int var3) {
         this.a = var2;
         this.b = var3;
      }

      public int getTransactionId() {
         return this.a;
      }

      public int getTimeoutSecs() {
         return this.b;
      }

      public void setTimeoutSecs(int var1) {
         this.b = var1;
      }
   }

   class Session {
      private List a;
      private int b;
      private SnmpOid c;
      private String d;
      private int e;
      private Integer f;
      private boolean g;
      private MasterAgentXConnection h;
      private int i;
      private Logger j;

      Session(int var2, int var3, SnmpOid var4, String var5, boolean var6, MasterAgentXConnection var7) {
         boolean var8 = MasterAgentX.n;
         super();
         this.a = new Vector();
         this.b = 60;
         this.c = null;
         this.d = null;
         this.e = -1;
         this.f = new Integer(-1);
         this.g = true;
         this.h = null;
         this.i = 1;
         this.j = Logger.getInstance(a("\u0016.1TW"), a("\u0013::WS\u007f%"), a("\u001f\u001c\fmb <\u0018|i&%QJb!\u000e\u0016vi"));
         this.j.debug(a("<\u0018\b9t7\u000e\fph<G_") + var2);
         this.b = var3;
         this.c = var4;
         this.d = var5;
         this.e = var2;
         this.f = new Integer(var2);
         this.g = var6;
         this.h = var7;
         if (SnmpException.b) {
            MasterAgentX.n = !var8;
         }

      }

      public MasterAgentXConnection getConnection() {
         return this.h;
      }

      public int getSessionId() {
         return this.e;
      }

      Integer a() {
         return this.f;
      }

      public void performCloseSession() throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException, CoderException {
         this.performCloseSession(ClosePDU.REASON_OTHER);
      }

      public void performCloseSession(int var1) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException, CoderException {
         this.j.debug(a("\"\u0018\r\u007fh \u0010<uh!\u0018,|t!\u0014\u0010w/") + var1 + ")");
         ClosePDU var2 = new ClosePDU();
         var2.setReason(var1);
         var2.setFlags(this.g ? 16 : 0);
         AgentXConnection.PendingRequest var3 = this.h.send(var2);
         ResponsePDU var4 = (ResponsePDU)var3.getResponse();
         if (var4.getError() == 0) {
            MasterAgentX.this.b(this.e);
            if (!MasterAgentX.n) {
               return;
            }
         }

         MasterAgentX.this.m.error(a("1\u001c\u0011wh&]\u001cuh!\u0018_jb!\u000e\u0016vihw") + var4);
         throw new AgentXErrorException(var4.getError(), 0);
      }

      public SnmpVarBindList performGet(String var1, int var2, SnmpOid var3) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         SnmpVarBindList var4 = new SnmpVarBindList();
         var4.add(new SnmpVarBind(var3, (SnmpValue)null, true));
         return this.performGet(var1, var2, var4);
      }

      public SnmpVarBindList performGet(String var1, int var2, SnmpVarBindList var3) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         Vector var4 = new Vector();
         int var5 = 0;

         while(var5 < var3.size()) {
            SnmpVarBind var6 = var3.get(var5);
            SearchRange var7 = new SearchRange(var6.getOid(), false, (SnmpOid)null);
            var4.add(var7);
            ++var5;
            if (MasterAgentX.n) {
               break;
            }
         }

         GetPDU var8 = new GetPDU();
         var8.setSearchRangeList(var4);
         var8.setSessionId(this.e);
         var8.setContext(var1);
         AgentXConnection.PendingRequest var9 = this.h.send(var8);
         ResponsePDU var10 = (ResponsePDU)var9.getResponse();
         if (var10.getError() == 0) {
            return var10.getVarBindList();
         } else {
            MasterAgentX.this.m.error(a("1\u001c\u0011wh&]\u001cuh!\u0018_jb!\u000e\u0016vihw") + var10);
            throw new AgentXErrorException(var10.getError(), 0);
         }
      }

      public SnmpVarBindList performGetNext(String var1, int var2, SnmpOid var3, SnmpOid var4) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         if (MasterAgentX.this.m.isDebugEnabled()) {
            MasterAgentX.this.m.debug(a("\u0001\u0018\fjn=\u0013Qib \u001b\u0010kj\u0015\u0018\u000bWb*\tE9") + var1 + "," + var2 + a("~]\u0010pco") + var3 + a("~\u0010\u001ea:") + var4);
         }

         SearchRange var5 = new SearchRange(var3, false, var4);
         Vector var6 = new Vector();
         var6.add(var5);
         return this.performGetNext(var1, var2, var6);
      }

      public SnmpVarBindList performGetNext(String var1, int var2, List var3) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         if (MasterAgentX.this.m.isDebugEnabled()) {
            MasterAgentX.this.m.debug(a("\u0001\u0018\fjn=\u0013Qib \u001b\u0010kj\u0015\u0018\u000bWb*\tE9") + var1 + "," + var2 + "," + var3);
         }

         GetNextPDU var4 = new GetNextPDU();
         var4.setSearchRangeList(var3);
         var4.setSessionId(this.e);
         var4.setContext(var1);
         AgentXConnection.PendingRequest var5 = this.h.send(var4);
         ResponsePDU var6 = (ResponsePDU)var5.getResponse();
         if (var6.getError() == 0) {
            return var6.getVarBindList();
         } else {
            MasterAgentX.this.m.error(a("1\u001c\u0011wh&]\u001cuh!\u0018_jb!\u000e\u0016vihw") + var6);
            throw new AgentXErrorException(var6.getError(), 0);
         }
      }

      public Transaction newTransaction() {
         int var1 = this.b();
         Transaction var2 = MasterAgentX.this.new Transaction(var1, 0);
         return var2;
      }

      public void performTestSet(Transaction var1, String var2, int var3, SnmpVarBindList var4) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         var1.setTimeoutSecs(var3);
         TestSetPDU var5 = new TestSetPDU();
         var5.setContext(var2);
         var5.setSessionId(this.e);
         var5.setTransactionId(var1.getTransactionId());
         var5.setVarBindList(var4);
         AgentXConnection.PendingRequest var6 = this.h.send(var5);
         ResponsePDU var7 = (ResponsePDU)var6.getResponse();
         if (var7.getError() != 0) {
            MasterAgentX.this.m.error(a("1\u001c\u0011wh&]\u001cuh!\u0018_jb!\u000e\u0016vihw") + var7);
            throw new AgentXErrorException(var7.getError(), 0);
         }
      }

      public void performCommitSet(Transaction var1) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         this.a(var1, 9);
      }

      public void performUndoSet(Transaction var1) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         this.a(var1, 10);
      }

      public void performCleanupSet(Transaction var1) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         this.a(var1, 11);
      }

      private void a(Transaction var1, int var2) throws AgentXErrorException, AgentXTimeoutException, AgentXCommunicationsException {
         Object var3 = null;
         switch (var2) {
            case 9:
               var3 = new CommitSetPDU();
               break;
            case 10:
               var3 = new UndoSetPDU();
               break;
            case 11:
               var3 = new CleanupSetPDU();
               break;
            default:
               throw new AgentXErrorException(5, 0);
         }

         ((AgentXPDU)var3).setSessionId(this.e);
         ((AgentXPDU)var3).setTransactionId(var1.getTransactionId());
         AgentXConnection.PendingRequest var4 = this.h.send((AgentXPDU)var3);
         ResponsePDU var5 = (ResponsePDU)var4.getResponse();
         if (var5.getError() != 0) {
            MasterAgentX.this.m.error(a("!\u0018\u000b9a3\u0014\u0013|chw") + var5);
            throw new AgentXErrorException(var5.getError(), 0);
         }
      }

      public void addSubtree(d var1) {
         this.a.add(var1);
      }

      public List getSubtrees() {
         return this.a;
      }

      private synchronized int b() {
         return this.i++;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 82;
                  break;
               case 1:
                  var10003 = 125;
                  break;
               case 2:
                  var10003 = 127;
                  break;
               case 3:
                  var10003 = 25;
                  break;
               default:
                  var10003 = 7;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
