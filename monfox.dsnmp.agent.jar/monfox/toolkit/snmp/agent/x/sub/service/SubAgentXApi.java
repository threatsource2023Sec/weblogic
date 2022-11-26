package monfox.toolkit.snmp.agent.x.sub.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.x.common.AgentXCommunicationsException;
import monfox.toolkit.snmp.agent.x.common.AgentXErrorException;
import monfox.toolkit.snmp.agent.x.common.AgentXException;
import monfox.toolkit.snmp.agent.x.common.AgentXTimeoutException;
import monfox.toolkit.snmp.agent.x.connection.AgentXConnection;
import monfox.toolkit.snmp.agent.x.connection.SubAgentXConnection;
import monfox.toolkit.snmp.agent.x.pdu.AgentXPDU;
import monfox.toolkit.snmp.agent.x.pdu.ClosePDU;
import monfox.toolkit.snmp.agent.x.pdu.IndexAllocatePDU;
import monfox.toolkit.snmp.agent.x.pdu.IndexDeallocatePDU;
import monfox.toolkit.snmp.agent.x.pdu.NotifyPDU;
import monfox.toolkit.snmp.agent.x.pdu.OpenPDU;
import monfox.toolkit.snmp.agent.x.pdu.PingPDU;
import monfox.toolkit.snmp.agent.x.pdu.RegisterPDU;
import monfox.toolkit.snmp.agent.x.pdu.ResponsePDU;
import monfox.toolkit.snmp.agent.x.pdu.UnregisterPDU;

public class SubAgentXApi {
   public static Config DEFAULT_CONFIG = null;
   public static final int ALLOCATE_SPECIFIC_INDEX = 0;
   public static final int ALLOCATE_NEW_INDEX = 1;
   public static final int ALLOCATE_ANY_INDEX = 2;
   private StatusListener a;
   private List b;
   private int c;
   private int d;
   private SnmpAgent e;
   private SnmpAccessPolicy f;
   private a g;
   private AgentXNotifier h;
   private SubStatusListener i;
   private SubRequestListener j;
   private ConnectionMonitor k;
   private Thread l;
   private boolean m;
   private Config n;
   private AgentXConnection o;
   private Map p;
   private Logger q;
   public static boolean r;

   public SubAgentXApi(SnmpAgent var1, Config var2, SnmpAccessPolicy var3) {
      boolean var4 = r;
      super();
      this.a = null;
      this.b = new Vector();
      this.c = 3;
      this.d = 5000;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = null;
      this.m = false;
      this.n = null;
      this.o = null;
      this.p = new Hashtable();
      this.q = Logger.getInstance(a("\rJPY\r"), a("\b^[Z\tdA"), a("\u001al|U:,wjL\u001c9p"));
      this.e = var1;
      this.n = var2;
      this.f = var3;
      this.g = new a(this.e, this.f);
      this.h = new AgentXNotifier(this, this.e);
      this.i = new SubStatusListener();
      this.j = new SubRequestListener();
      this.k = new ConnectionMonitor();
      if (var4) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public SubAgentXApi(SnmpAgent var1, Config var2, boolean var3) {
      this(var1, var2, new AgentXAccessPolicy(var3));
   }

   public synchronized void startup() {
      if (this.l == null) {
         this.m = true;
         this.l = new Thread(this.k, a("\u001al|U:,wjL\u001c9p0Y2'pj{/"));
         this.l.setDaemon(true);
         this.l.start();
      }
   }

   public synchronized void shutdown() {
      this.m = false;

      try {
         this.l.interrupt();
      } catch (Exception var3) {
      }

      this.l = null;

      try {
         if (this.o != null) {
            this.o.shutdown();
            this.o = null;
         }
      } catch (Exception var2) {
      }

      if (this.h != null) {
         this.h.shutdown();
      }

   }

   private void a(AgentXConnection.PendingIndication var1, AgentXPDU var2) {
      ResponsePDU var3 = new ResponsePDU();
      var3.setSessionId(var2.getSessionId());
      var1.sendResponse(var3);
   }

   SubAgentXConnection a() throws AgentXCommunicationsException {
      this.q.comms(a(".|jB<%pzW2'w{w) vp<t"));
      if (this.o != null && this.o.getStatus() == 0) {
         this.o = null;
      }

      if (this.o == null) {
         this.o = new SubAgentXConnection(this.n.getMasterAgentAddr(), this.n.getMasterAgentPort(), this.i, this.j);
      }

      this.q.comms(a(".|jB<%pzW2'w{w) vp<ts9") + this.o);
      return (SubAgentXConnection)this.o;
   }

   public Session performOpenSession() throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
      this.q.debug(a("&i{z\u000e,jm}2'17"));
      OpenPDU var1 = new OpenPDU();
      var1.setTimeout(this.n.getSubAgentTimeoutSecs());
      var1.setId(this.n.getSubAgentId());
      var1.setDescr(this.n.getSubAgentDescr());
      var1.setFlags(this.n.isNetworkByteOrder() ? 16 : 0);
      SubAgentXConnection var2 = this.a();
      AgentXConnection.PendingRequest var3 = var2.send(var1);
      ResponsePDU var4 = (ResponsePDU)var3.getResponse();
      if (var4.getError() == 0) {
         Session var5 = new Session(var4.getSessionId(), this.n.isNetworkByteOrder(), var2);
         this.a(var5);
         return var5;
      } else {
         this.q.error(a("*xpz2=9}f8(m{4.,jm}2'#\u0014") + var4);
         throw new AgentXErrorException(var4.getError(), 0);
      }
   }

   private void a(Session var1) {
      this.p.put(new Integer(var1.getSessionId()), var1);
   }

   private Session a(int var1) {
      return (Session)this.p.get(new Integer(var1));
   }

   private void b(int var1) {
      this.p.remove(new Integer(var1));
   }

   private void a(AgentXConnection var1) {
      Vector var2 = new Vector();
      var2.addAll(this.p.entrySet());
      this.q.debug(a(":|mg4&w>w2<wjOo\u0014#>") + var2.size());
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Map.Entry var4 = (Map.Entry)var3.next();
         Integer var5 = (Integer)var4.getKey();
         Session var6 = (Session)var4.getValue();
         if (var6.getConnection() == var1) {
            this.p.remove(var5);
         }

         if (r) {
            break;
         }
      }

   }

   public void addStatusListener(StatusListener var1) {
      this.a = monfox.toolkit.snmp.agent.x.sub.service.b.add(this.a, var1);
   }

   public void removeStatusListener(StatusListener var1) {
      this.a = monfox.toolkit.snmp.agent.x.sub.service.b.remove(this.a, var1);
   }

   public void addNotifyGroup(String var1) {
      this.b.add(var1);
   }

   public void removeNotifyGroup(String var1) {
      this.b.remove(var1);
   }

   public void sendNotification(String var1, String var2, SnmpVarBindList var3) {
      if (var1 != null && this.b.size() > 0 && !this.b.contains(var1)) {
         this.q.debug(a(".kqa-i>") + var1 + a("n9p{)ipp43&mwr$.kqa-iuwg)e9p{)ij{z9 wy"));
      } else {
         this.q.debug(a(":|mg4&w>w2<wjOl\u0014#>") + this.p.size());
         Vector var4 = new Vector();
         var4.addAll(this.p.entrySet());
         this.q.debug(a(":|mg4&w>w2<wjOo\u0014#>") + var4.size());
         Iterator var5 = var4.iterator();

         while(var5.hasNext()) {
            Map.Entry var6 = (Map.Entry)var5.next();
            Session var7 = (Session)var6.getValue();
            this.q.debug(a(":|pp4'~>z2=px}>(mw{3i\u007fqf}:|mg4&w$4") + var7.getSessionId());

            try {
               var7.performNotify(var2, var3);
            } catch (AgentXException var9) {
               this.q.debug(a(",a}q-=pqz}:|pp4'~>U:,wjL}'vj}; z\u007f`4&w$") + var9, var9);
            }

            if (r) {
               break;
            }
         }

      }
   }

   static {
      try {
         DEFAULT_CONFIG = new Config(InetAddress.getByName(a("%v}u1!vm`")), 705);
      } catch (Exception var2) {
         Logger var1 = Logger.getInstance(a("\rJPY\r"), a("\b^[Z\tdA"), a("\u001al|U:,wjL\u001c9p"));
         var1.error(a("\nXPZ\u0012\u001d9wz4=p\u007fx43|>p8/xkx)iJkv\u001c.|p`\u0005\biw:\u001e&wx}:"), var2);
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
               var10003 = 73;
               break;
            case 1:
               var10003 = 25;
               break;
            case 2:
               var10003 = 30;
               break;
            case 3:
               var10003 = 20;
               break;
            default:
               var10003 = 93;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public class Session {
      private int a = -1;
      private boolean b = true;
      private SubAgentXConnection c = null;
      private Logger d = Logger.getInstance(a("qNL\u001b\u001a"), a("tZG\u0018\u001e\u0018E"), a("fh`\u0017-Psv\u000e\u000bEt,\u0005/Fnk9$"));

      Session(int var2, boolean var3, SubAgentXConnection var4) {
         this.d.debug(a("[xuv9Pnq?%['\"") + var2);
         this.a = var2;
         this.b = var3;
         this.c = var4;
      }

      public int getSessionId() {
         return this.a;
      }

      public SubAgentXConnection getConnection() {
         return this.c;
      }

      public void performCloseSession() throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         this.performCloseSession(ClosePDU.REASON_OTHER);
      }

      public void performCloseSession(int var1) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         this.d.debug(a("Exp0%GpA:%FxQ39Ftm8b") + var1 + ")");
         ClosePDU var2 = new ClosePDU();
         var2.setReason(var1);
         var2.setSessionId(this.a);
         var2.setFlags(this.b ? 16 : 0);
         AgentXConnection.PendingRequest var3 = this.c.send(var2);
         ResponsePDU var4 = (ResponsePDU)var3.getResponse();
         if (var4.getError() == 0) {
            SubAgentXApi.this.b(this.a);
            if (!SubAgentXApi.r) {
               return;
            }
         }

         SubAgentXApi.this.q.error(a("V|l8%A=a:%Fx\"%/Fnk9$\u000f\u0017") + var4);
         throw new AgentXErrorException(var4.getError(), 0);
      }

      public void performPing() throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         this.d.debug(a("Exp0%GpR?$R5+"));
         PingPDU var1 = new PingPDU();
         var1.setSessionId(this.a);
         var1.setFlags(this.b ? 16 : 0);
         AgentXConnection.PendingRequest var2 = this.c.send(var1);
         ResponsePDU var3 = (ResponsePDU)var2.getResponse();
         if (var3.getError() != 0) {
            SubAgentXApi.this.q.error(a("V|l8%A=a:%Fx\"%/Fnk9$\u000f\u0017") + var3);
            throw new AgentXErrorException(var3.getError(), 0);
         }
      }

      public void performRegister(SnmpOid var1, String var2) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         this.performRegister(var1, var2, 0, 0, 0, 127, false);
      }

      public void performRegister(SnmpOid var1, String var2, int var3, int var4, int var5, int var6, boolean var7) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         boolean var11 = SubAgentXApi.r;
         this.d.debug(a("Exp0%GpP3-\\nv38\u001d") + var1 + ")");
         RegisterPDU var8 = new RegisterPDU();
         var8.setSubtree(var1);
         var8.setRangeSubid(var3);
         var8.setUpperBound(var4);
         var8.setTimeout(var5);
         var8.setPriority(var6);
         if (var2 != null && var2.length() > 0) {
            var8.setFlag(8);
            var8.setContext(var2);
         }

         var8.setSessionId(this.a);
         var8.setFlags(this.b ? 16 : 0);
         if (var7) {
            var8.setFlag(1);
         }

         AgentXConnection.PendingRequest var9 = this.c.send(var8);
         ResponsePDU var10 = (ResponsePDU)var9.getResponse();
         if (var10.getError() == 0) {
            if (SnmpException.b) {
               SubAgentXApi.r = !var11;
            }

         } else {
            this.d.error(a("V|l8%A=p3-\\nv38\u0015RK\u0012p?") + var10);
            throw new AgentXErrorException(var10.getError(), 0);
         }
      }

      public void performUnregister(SnmpOid var1, String var2) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         this.performUnregister(var1, var2, 0, 0, 127, false);
      }

      public void performUnregister(SnmpOid var1, String var2, int var3, int var4, int var5, boolean var6) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         this.d.debug(a("Exp0%GpW8/Rtq\"/G5") + var1 + ")");
         UnregisterPDU var7 = new UnregisterPDU();
         var7.setSubtree(var1);
         var7.setRangeSubid(var3);
         var7.setUpperBound(var4);
         var7.setPriority(var5);
         if (var2 != null && var2.length() > 0) {
            var7.setFlag(8);
            var7.setContext(var2);
         }

         var7.setSessionId(this.a);
         var7.setFlags(this.b ? 16 : 0);
         if (var6) {
            var7.setFlag(1);
         }

         AgentXConnection.PendingRequest var8 = this.c.send(var7);
         ResponsePDU var9 = (ResponsePDU)var8.getResponse();
         if (var9.getError() != 0) {
            this.d.error(a("V|l8%A=p3-\\nv38\u0015RK\u0012p?") + var9);
            throw new AgentXErrorException(var9.getError(), 0);
         }
      }

      public void performNotify(String var1, SnmpVarBindList var2) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         this.d.debug(a("Exp0%GpL9>\\{{~") + var2 + ")");
         NotifyPDU var3 = new NotifyPDU();
         var3.setVarBindList(var2);
         if (var1 != null && var1.length() > 0) {
            var3.setFlag(8);
            var3.setContext(var1);
         }

         var3.setSessionId(this.a);
         var3.setFlags(this.b ? 16 : 0);
         AgentXConnection.PendingRequest var4 = this.c.send(var3);
         ResponsePDU var5 = (ResponsePDU)var4.getResponse();
         if (var5.getError() != 0) {
            this.d.error(a("V|l8%A=p3-\\nv38\u0015RK\u0012p?") + var5);
            throw new AgentXErrorException(var5.getError(), 0);
         }
      }

      public SnmpVarBindList performIndexAllocate(String var1, SnmpVarBindList var2) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         return this.performIndexAllocate(var1, var2, 0);
      }

      public SnmpVarBindList performIndexAllocate(String var1, SnmpVarBindList var2, int var3) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         IndexAllocatePDU var4;
         label28: {
            this.d.debug(a("Exp0%GpK8.PeC:&Z~c\"/\u001d") + var2 + ")");
            var4 = new IndexAllocatePDU();
            var4.setVarBindList(var2);
            if (var3 == 1) {
               var4.setFlag(2);
               if (!SubAgentXApi.r) {
                  break label28;
               }
            }

            if (var3 == 2) {
               var4.setFlag(4);
            }
         }

         if (var1 != null && var1.length() > 0) {
            var4.setFlag(8);
            var4.setContext(var1);
         }

         var4.setSessionId(this.a);
         var4.setFlags(this.b ? 16 : 0);
         AgentXConnection.PendingRequest var5 = this.c.send(var4);
         ResponsePDU var6 = (ResponsePDU)var5.getResponse();
         if (var6.getError() == 0) {
            return var6.getVarBindList();
         } else {
            this.d.error(a("V|l8%A=c:&Z~c\"/\u0015tl2/M'\b") + var6);
            throw new AgentXErrorException(var6.getError(), var6.getIndex());
         }
      }

      public void performIndexDeallocate(String var1, SnmpVarBindList var2) throws AgentXCommunicationsException, AgentXTimeoutException, AgentXErrorException {
         this.d.debug(a("Exp0%GpK8.PeF3+Yqm5+Ax*") + var2 + ")");
         IndexDeallocatePDU var3 = new IndexDeallocatePDU();
         var3.setVarBindList(var2);
         if (var1 != null && var1.length() > 0) {
            var3.setFlag(8);
            var3.setContext(var1);
         }

         var3.setSessionId(this.a);
         var3.setFlags(this.b ? 16 : 0);
         AgentXConnection.PendingRequest var4 = this.c.send(var3);
         ResponsePDU var5 = (ResponsePDU)var4.getResponse();
         if (var5.getError() != 0) {
            this.d.error(a("V|l8%A=c:&Z~c\"/\u0015tl2/M'\b") + var5);
            throw new AgentXErrorException(var5.getError(), var5.getIndex());
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
                  var10003 = 53;
                  break;
               case 1:
                  var10003 = 29;
                  break;
               case 2:
                  var10003 = 2;
                  break;
               case 3:
                  var10003 = 86;
                  break;
               default:
                  var10003 = 74;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class ConnectionMonitor implements Runnable {
      private ConnectionMonitor() {
      }

      public void run() {
         boolean var2 = SubAgentXApi.r;

         try {
            while(SubAgentXApi.this.m) {
               label23: {
                  try {
                     SubAgentXApi.this.a();
                  } catch (AgentXCommunicationsException var3) {
                     SubAgentXApi.this.q.comms(a("\u000b\b'\u001f!+\u0013 \u001e*\u0005\b'\u00180'\u0015sQ')\t'\u001e0h\u0004&\u001f*-\u0004=Q0'G$\u00107<\u0002;Q%/\u0002'\u0005~") + SubAgentXApi.this.n.getMasterAgentAddr() + ":" + SubAgentXApi.this.n.getMasterAgentPort(), var3);
                     break label23;
                  }

                  if (var2) {
                     break;
                  }
               }

               Thread.sleep((long)(SubAgentXApi.this.n.getConnectionRetryPeriodSecs() * 1000));
               if (var2) {
                  break;
               }
            }
         } catch (Exception var4) {
            SubAgentXApi.this.q.debug(a("+\b'\u001f!+\u0013 \u001e*h\n&\u001f-<\b;Q!0\u000e=\u0018*/"), var4);
         }

      }

      // $FF: synthetic method
      ConnectionMonitor(Object var2) {
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
                  var10003 = 72;
                  break;
               case 1:
                  var10003 = 103;
                  break;
               case 2:
                  var10003 = 73;
                  break;
               case 3:
                  var10003 = 113;
                  break;
               default:
                  var10003 = 68;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class SubRequestListener implements AgentXConnection.RequestListener {
      private SubRequestListener() {
      }

      public void handleRequest(AgentXConnection.PendingIndication var1, AgentXPDU var2) {
         boolean var3 = SubAgentXApi.r;
         switch (var2.getType()) {
            case 2:
               SubAgentXApi.this.a(var1, var2);
               if (!var3) {
                  break;
               }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
               SubAgentXApi.this.g.handleRequest(var1, var2);
               if (!var3) {
                  break;
               }
            case 1:
            case 3:
            case 4:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
               var1.sendRequestDenied();
         }

      }

      // $FF: synthetic method
      SubRequestListener(Object var2) {
         this();
      }
   }

   private class SubStatusListener implements AgentXConnection.StatusListener {
      private SubStatusListener() {
      }

      public void connectionUp(AgentXConnection var1) {
         SubAgentXApi.this.q.comms(a("c\u0002XW4zE^V.L\u0000^M)M\u000b\u001d\u0005\u0015r[\u0007") + var1);
         SubAgentXApi.this.a.connectionUp(SubAgentXApi.this);
      }

      public void connectionDown(AgentXConnection var1) {
         SubAgentXApi.this.q.comms(a("c\u0002XW4zE^V.L\u0000^M)M\u000b\u001d\u0005\u0004m2s\u0007z") + var1);
         SubAgentXApi.this.o = null;
         SubAgentXApi.this.a.connectionDown(SubAgentXApi.this);
         SubAgentXApi.this.a(var1);
      }

      // $FF: synthetic method
      SubStatusListener(Object var2) {
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
                  var10003 = 34;
                  break;
               case 1:
                  var10003 = 101;
                  break;
               case 2:
                  var10003 = 61;
                  break;
               case 3:
                  var10003 = 57;
                  break;
               default:
                  var10003 = 64;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class Config {
      private int a = 60;
      private int b = 10;
      private String c = "";
      private SnmpOid d = null;
      private boolean e = true;
      private InetAddress f = null;
      private int g = 705;

      public Config(String var1, int var2) throws UnknownHostException {
         this.f = InetAddress.getByName(var1);
         this.g = var2;
      }

      public Config(InetAddress var1, int var2) {
         this.f = var1;
         this.g = var2;
      }

      public SnmpOid getSubAgentId() {
         return this.d;
      }

      public void setSubAgentId(SnmpOid var1) {
         this.d = var1;
      }

      public String getSubAgentDescr() {
         return this.c;
      }

      public void setSubAgentDescr(String var1) {
         this.c = var1;
      }

      public int getConnectionRetryPeriodSecs() {
         return this.b;
      }

      public void setConnectionRetryPeriodSecs(int var1) {
         this.b = var1;
      }

      public int getSubAgentTimeoutSecs() {
         return this.a;
      }

      public void setSubAgentTimeoutSecs(int var1) {
         this.a = var1;
      }

      public InetAddress getMasterAgentAddr() {
         return this.f;
      }

      public int getMasterAgentPort() {
         return this.g;
      }

      public boolean isNetworkByteOrder() {
         return this.e;
      }

      public void isNetworkByteOrder(boolean var1) {
         this.e = var1;
      }
   }

   public interface StatusListener {
      void connectionUp(SubAgentXApi var1);

      void connectionDown(SubAgentXApi var1);

      void sessionClosed(SubAgentXApi var1, Session var2);
   }
}
