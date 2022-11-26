package monfox.toolkit.snmp.mgr;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.BufferedTransportProvider;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.engine.SnmpBulkPDU;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageListener;
import monfox.toolkit.snmp.engine.SnmpMessageProcessor;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpOidMap;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.mgr.usm.Usm;
import monfox.toolkit.snmp.util.Queue;
import monfox.toolkit.snmp.util.SimpleQueue;
import monfox.toolkit.snmp.util.TimerItem;
import monfox.toolkit.snmp.util.TimerQueue;
import monfox.toolkit.snmp.util.WorkItem;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;
import monfox.toolkit.snmp.v3.V3SnmpMessageModule;
import monfox.toolkit.snmp.v3.usm.USMEngineInfo;
import monfox.toolkit.snmp.v3.usm.USMEngineMap;
import monfox.toolkit.snmp.v3.usm.USMSecurityCoder;
import monfox.toolkit.snmp.v3.usm.USMUserTable;

final class SnmpSessionImpl implements Runnable, l, SnmpMessageListener {
   private static final SnmpVarBindList a = new SnmpVarBindList();
   private TransportProvider b;
   private boolean c;
   private Thread d;
   private Hashtable e;
   private Object f;
   private int g;
   private int h;
   private int i;
   private boolean j;
   private int k;
   private int l;
   private boolean m;
   private SimpleQueue n;
   private Queue o;
   private Queue p;
   private TimerQueue q;
   private TimerQueue r;
   private byte[] s;
   private e t;
   private SnmpMessageProcessor u;
   private SnmpEngine v;
   private SnmpMetadata w;
   private SnmpSession x;
   private SnmpInformListener y;
   private static Logger z = null;
   private Usm A;
   private boolean B;
   private boolean C;
   static final SnmpOid D = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 2L, 0L});
   static final SnmpOid E = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 4L, 0L});

   SnmpSessionImpl(SnmpSession var1, SnmpSessionConfig var2) throws SnmpException {
      boolean var4 = SnmpSession.B;
      super();
      this.b = null;
      this.c = false;
      this.d = null;
      this.e = new Hashtable();
      this.f = new Object();
      this.g = 100;
      this.h = Integer.MAX_VALUE;
      this.i = 1;
      this.j = false;
      this.k = Snmp.DEFAULT_RECEIVE_BUFFER_SIZE;
      this.l = Snmp.DEFAULT_MAX_SIZE;
      this.m = true;
      this.n = null;
      this.o = null;
      this.p = null;
      this.q = null;
      this.r = null;
      this.s = new byte[Snmp.DEFAULT_RECEIVE_BUFFER_SIZE];
      this.t = null;
      this.u = null;
      this.v = null;
      this.w = null;
      this.x = null;
      this.y = null;
      this.A = null;
      this.B = false;
      this.C = true;
      if (z == null) {
         z = Logger.getInstance(a("\u0013uq\u000f\u001e%ho\u0016\".Rq\u000f!"));
      }

      label67: {
         z.debug(a("~%<,#-kO\u001a>3rs\u0011\u0004-kpWd"));
         this.x = var1;
         this.w = var2.getMetadata();
         this.setReceiveBufferSize(var2.getReceiveBufferSize());
         this.g = var2.getInitialRequestId();
         this.setMaxRequestId(var2.getMaxRequestId());
         this.setMinRequestId(var2.getMinRequestId());
         this.setProcessInvalidResponseMessageIDs(var2.getProcessInvalidResponseMessageIDs());
         if (var2.getEngineID() != null) {
            this.v = new SnmpEngine(var2.getEngineID(), this.w);
            if (!var4) {
               break label67;
            }

            SnmpException.b = !SnmpException.b;
         }

         this.v = new SnmpEngine(this.w);
      }

      label72: {
         this.j = var2.getReuseRequestIds();
         this.A = new Usm(this.v, this.x);
         this.u = this.v.getMessageProcessor();
         this.B = var2.isAutoDiscovery();
         if (var2.getEngine() != null) {
            this.n = new SimpleQueue(a("\u0004~\u007f\u0010)%\u007fQ\u001a>3z{\u001a\u001c5~i\u001a"));
            this.v = var2.getEngine();
            this.v.addResponseListener(this);
            this.v.addReportListener(this);
            this.v.addEventListener(this);
            if (!var4) {
               break label72;
            }
         }

         if (var2.getTransportProvider() != null) {
            this.b = var2.getTransportProvider();
            if (!var4) {
               break label72;
            }
         }

         if (var2.getTransportProviderParams() != null) {
            TransportProvider.Params var3 = var2.getTransportProviderParams();
            if (var3.getLocalAddress() == null && var2.getLocalAddr() != null) {
               var3.setLocalAddress(var2.getLocalAddr());
            }

            if (var3.getLocalPort() <= 0 && var2.getLocalPort() > 0) {
               var3.setLocalPort(var2.getLocalPort());
            }

            this.b = TransportProvider.newInstance(var3);
            if (!var4) {
               break label72;
            }
         }

         this.b = TransportProvider.newInstance(var2.getTransportType(), var2.getLocalAddr(), var2.getLocalPort(), var2.getBuffered());
      }

      if (this.b != null && this.b instanceof BufferedTransportProvider) {
         ((BufferedTransportProvider)this.b).setReceiveBufferSize(var2.getReceiveBufferSize());
         ((BufferedTransportProvider)this.b).setMaxQueueLength(var2.getBufferMaxQueueLength());
      }

      this.d = new Thread(this, a("\u0013uq\u000f\u001e%ho\u0016\".6N\n#.z~\u0013("));
      this.d.setDaemon(true);
      this.d.start();
      if (var2.getEventThreadCount() > 0) {
         this.o = new Queue(a("\u0014i}\u000f\u0004.}s\r \u0010is\u001c(3hs\r"), var2.getEventThreadCount(), var2.getEventThreadPriority());
      }

      this.p = new Queue(a("\u0012~o\u000f\".hy/?/xy\f>/i"), var2.getResponseThreadCount(), var2.getResponseThreadPriority());
      this.q = new TimerQueue(a("\u0014rq\u001a?\u0010is\u001c(3hs\r"), var2.getTimerThreadCount(), var2.getTimerThreadPriority());
      this.r = new TimerQueue(a("\u0010tp\u0013$.|L\r\"#~o\f\"2"), var2.getPollingThreadCount(), var2.getPollingThreadPriority());
      int var5 = var2.getSendThreadPriority();
      if (var5 > 0) {
         this.t = new e(this, var5);
      }

   }

   public void setParameter(String var1, Object var2) {
      if (var1 != null) {
         int var3 = var1.indexOf(46);
         String var4 = null;
         if (var3 > 0) {
            var4 = var1.substring(0, var3);
            var1 = var1.substring(var3 + 1);
         }

         if ((var4 == null || var4.equals(a("\u0013uq\u000f\u001e%ho\u0016\"."))) && a("\u0014sn\u001a,$Kn\u0016\"2rh\u0006").equalsIgnoreCase(var1) && this.d != null) {
            try {
               int var5 = Integer.parseInt("" + var2);
               this.d.setPriority(var5);
               z.debug(a("3~h\u000b$.|<\f(3hu\u0010#`Ot\r(!\u007fL\r$/iu\u000b4z;") + var5);
               return;
            } catch (Exception var6) {
               z.debug(a("%in\u0010?`rr_>%oh\u0016#';o\u001a>3rs\u0011m4sn\u001a,$;l\r$/iu\u000b4z;") + var6);
            }
         }

         this.b.setParameter(var1, var2);
      }
   }

   protected void finalize() throws Throwable {
      this.b.shutdown();
   }

   public void shutdown() {
      boolean var5 = SnmpSession.B;
      this.m = false;
      if (this.n != null) {
         this.n.shutdown();
      }

      if (this.d != null) {
         this.d.interrupt();
      }

      try {
         if (this.b != null) {
            this.b.shutdown();
         }
      } catch (Exception var6) {
         SnmpFramework.handleException(this, var6);
      }

      if (this.p != null) {
         this.p.shutdown();
      }

      if (this.q != null) {
         this.q.shutdown();
      }

      if (this.r != null) {
         this.r.shutdown();
      }

      if (this.o != null) {
         this.o.shutdown();
      }

      if (this.t != null) {
         this.t.shutdown();
      }

      if (this.x.isShutdownPendingRequests()) {
         z.debug(a("m6<\f%5oh\u0016#';x\u0010:.;l\u001a#$rr\u0018m2~m\n(3oo"));
         Vector var1 = new Vector();
         var1.addAll(this.e.values());
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            SnmpPendingRequest var3 = (SnmpPendingRequest)var2.next();
            z.debug(a("3si\u000b9)u{_)/lr_=2!") + var3);
            var3.s();

            try {
               SnmpPendingRequest var4 = var3.v();
               if (var5) {
                  return;
               }

               if (var4 != null) {
                  var4.s();
               }
            } catch (Exception var7) {
               z.debug(a("%in\u0010?`rr_>(nh\u001b\"7u<\u0010+`KN"), var7);
            }

            if (var5) {
               break;
            }
         }

         z.debug(a("m6<\u000f?3;o\u001784\u007fs\b#"));
      }

   }

   public void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3) throws SnmpException {
      this.sendPDU(var1, var2, var3, var1.getPeer().getParameters(), true);
   }

   public void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, boolean var4) throws SnmpException {
      this.sendPDU(var1, var2, var3, var1.getPeer().getParameters(), var4);
   }

   public void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, SnmpParameters var4) throws SnmpException {
      this.sendPDU(var1, var2, var3, var4, true);
   }

   public void sendPollPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, SnmpParameters var4) throws SnmpException {
      synchronized(this.f) {
         Integer var6 = new Integer(var1.getRequestId());
         SnmpPendingRequest var7 = (SnmpPendingRequest)this.e.remove(var6);
         if (var7 != null && z.isDebugEnabled()) {
            z.debug(a("\u000eTH:w`ky\u0011))u{R?%ji\u001a>4;n\u001a*)hh\u001a?%\u007f<\u001e#$;n\u001a /my\u001bw`") + var1);
         }
      }

      this.sendPDU(var1, var2, var3, var4, true);
   }

   public void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, SnmpParameters var4, boolean var5) throws SnmpException {
      boolean var13 = SnmpSession.B;
      if (!var1.o() && var1.u() == null) {
         this.x.a(var1);
      }

      if (z.isDebugEnabled()) {
         z.debug(a("3~r\u001b\u001d\u0004N&_") + var1 + a("l;l\u001b8l;j\u001d!)hhSm0zn\u001e 37<") + var5);
      }

      if (var1.isSplit()) {
         label196: {
            if (var1.q() != null) {
               var3 = var1.q();
               if (!var13) {
                  break label196;
               }
            }

            SnmpVarBindList var6 = new SnmpVarBindList();
            SnmpVarBindList var7 = var1.getRequestVarBindList();
            int var8 = var1.r();
            int var9 = 0;

            label183: {
               while(var9 < var1.getMaxPDUVarBinds()) {
                  if (var13) {
                     break label183;
                  }

                  if (var8 >= var7.size() && !var13) {
                     break;
                  }

                  var6.add(var7.get(var8));
                  ++var8;
                  ++var9;
                  if (var13) {
                     break;
                  }
               }

               var1.d(var8);
            }

            var3 = var6;
         }
      }

      var1.a(var2);
      var1.b(var3);
      var1.a(var4);
      SnmpPeer var15 = var1.getPeer();
      USMEngineInfo var19;
      synchronized(var15) {
         if (var15.c() != null) {
            USMEngineMap var17 = this.A.getEngineMap();
            if (var17 != null) {
               var19 = var17.get(var15.c());
               if (var19 != null) {
                  var19.setEngineBoots(0);
                  var19.setLastEngineTime(0);
               }
            }

            var15.d();
         }
      }

      int var16 = var15.getMaxSize();
      if (var16 <= 0) {
         var16 = this.l;
      }

      SnmpMessage var18 = new SnmpMessage();
      var18.setMaxSize(var16);
      if (var15.getOidMap() != null && z.isDebugEnabled()) {
         z.debug(a("5hu\u0011*`Tu\u001b\u0000!k&_") + var15.getOidMap());
         var18.setOidMap(var15.getOidMap());
      }

      if (var1.m()) {
         var18.isDiscovery(true);
      }

      var19 = null;
      SnmpMessageProfile var20;
      if (var1.m()) {
         var20 = SnmpParameters.DISCOVERY_PROFILE;
      } else if (var1.getType() == 163) {
         var20 = var4.getWriteProfile();
      } else if (var1.getType() == 166) {
         var20 = var4.getInformProfile();
      } else {
         var20 = var4.getReadProfile();
      }

      if (var20 == null) {
         throw new SnmpException(a("\u000et<,(#nn\u001699;L\u001e?!vy\u000b(2h<W$%5<\u001c\"-vi\u0011$4b5_\u001d2tj\u0016)%\u007f<^la"));
      } else if (var20.getSnmpVersion() == 3 && !var1.m() && this.B && var15.getSnmpEngineID() == null) {
         this.e(var1);
      } else {
         if (var20.getSnmpVersion() == 3 && !var1.m() && !var1.n() && !var1.o() && this.C && var15.getSnmpEngineID() != null && var20.getSecurityLevel() != 0) {
            USMEngineMap var10 = this.A.getEngineMap();
            if (var10 != null) {
               USMEngineInfo var11 = var10.get(var15.getSnmpEngineID());
               if (var11 == null || var11.getLastEngineTime() == 0) {
                  this.f(var1);
                  return;
               }
            }
         }

         int var22;
         label139: {
            var18.isTimeSync(var1.n());
            var18.setMessageProfile(var20);
            var18.setUserTable(var4.getUserTable());
            boolean var21 = true;
            if (this.j && var1.getRequestId() > 0) {
               var22 = var1.getRequestId();
               if (!var13) {
                  break label139;
               }
            }

            var22 = this.c();
         }

         SnmpContext var23 = var4.getContext();
         if (var23 == null) {
            var23 = var15.getDefaultContext();
         }

         if (var23 != null && (var23.getContextEngineID() == null || var23.getContextEngineID() == SnmpContext.ANY_ENGINE_ID) && var15.getSnmpEngineID() != null) {
            var23 = new SnmpContext(var15.getSnmpEngineID(), var23.getContextName());
         }

         var18.setContext(var23);
         var18.setSnmpEngineID(var15.getSnmpEngineID());
         var18.setVersion(var20.getSnmpVersion());
         var18.setMsgID(var22);
         var2.setType(var1.getType());
         var2.setRequestId(var22);
         var1.a(var22);
         if (var5) {
            var1.e();
         }

         if (var3 != null) {
            var2.setVarBindList(var3);
         }

         var18.setData(var2);
         var18.setMsgID(var2.getRequestId());
         if (z.isDebugEnabled()) {
            z.debug(a("\u0010IY/\f\u0012RR8w`") + var18);
         }

         label122: {
            if (this.b != null) {
               SnmpBuffer var12 = this.u.encodeMessage(var18, var16);
               var1.a((Object)var12);
               if (!var13) {
                  break label122;
               }
            }

            var1.a(var18);
         }

         Integer var24 = new Integer(var18.getMsgID());
         this.e.put(var24, var1);
         if (var5) {
            this.a(var1);
         }

         if (z.isDebugEnabled()) {
            z.debug(a("\u0013^R;\u0004\u000e\\<,\u0003\rK<2\u001e\u0007!<W") + var18 + ")");
         }

         if (this.t != null) {
            this.t.add(var1);
            if (!var13) {
               return;
            }
         }

         this.g(var1);
      }
   }

   public void pausePolling(SnmpPendingRequest var1) {
      TimerItem var2 = (TimerItem)var1.l();
      if (var2 != null) {
         var2.cancel();
      }

   }

   public void restartPolling(SnmpPendingRequest var1) {
      TimerItem var2 = (TimerItem)var1.l();
      if (var2 != null) {
         var2.cancel();
      }

      if (var1.getPollingInterval() > 0) {
         t var3 = new t(var1, 0L);
         this.r.put(var3);
         var1.c((Object)var3);
         var1.g();
      }

   }

   void a(SnmpPendingRequest var1) {
      TimerItem var2 = (TimerItem)var1.l();
      if (var2 != null) {
         var2.cancel();
      }

      if (var1.getPollingInterval() > 0) {
         t var3 = new t(var1);
         this.r.put(var3);
         var1.c((Object)var3);
         var1.g();
      }

   }

   void b(SnmpPendingRequest var1) {
      boolean var12 = SnmpSession.B;
      if (z.isDebugEnabled()) {
         z.debug(a("\u0001\u007fx\u0016#';H\u0016 %ti\u000bw") + var1);
      }

      SnmpPeer var2 = var1.getPeer();
      long var3 = var2.getAdaptiveTimeout();
      long var5 = var2.getMaxTimeout();
      int var7 = var1.getRetryCount();
      if (z.isDebugEnabled()) {
         z.debug(a("4rq\u001a\"5o1\u000f,2zq\fw`ou\u0012(/nhB") + var3 + a("lv}\u0007`4rq\u001a\"5o!") + var5 + a("l;n\u001a92b1\u001c\"5uhB") + var7);
      }

      if (var7 > 0) {
         int var8 = var2.getTimeoutAlgorithm();
         if (var8 == -1) {
            var8 = this.x.getDefaultTimeoutAlgorithm();
         }

         switch (var8) {
            case 2:
               var3 *= (long)(var7 + 1);
               if (!var12) {
                  break;
               }
            case 3:
               var3 *= (long)(1 << var7);
               if (!var12) {
                  break;
               }
            case 4:
               var3 = var2.getAdaptiveTimeout() + var2.getTimeout();
               if (var5 <= 0L) {
                  var5 = var2.getTimeout() * (long)(var2.getMaxRetries() + 1);
               }

               if (var3 > var5) {
                  var3 = var5;
               }

               var1.getPeer().setAdaptiveTimeout(var3);
         }

         if (z.isDebugEnabled()) {
            z.debug(a(".~kR9)vy\u001084@") + var3 + a("-hAEm") + var1);
         }
      }

      if (var5 > 0L && var3 > var5) {
         var3 = var5;
      }

      if (var3 <= 0L) {
         var3 = var2.getTimeout();
      }

      if (var3 <= 0L) {
         var3 = var2.getMaxTimeout();
      }

      if (var3 <= 0L) {
         var3 = SnmpPeer.getDefaultTimeout();
      }

      if (var3 <= 0L) {
         z.warn(a("#ti\u0013)`us\u000bm$~h\u001a?-rr\u001am4rq\u001a\"5o&_>%oh\u0016#';h\u0010mu+,O 3"));
         var3 = 5000L;
      }

      if (var3 > 0L) {
         synchronized(var1.w()) {
            TimerItem var9 = (TimerItem)var1.k();
            if (var9 != null) {
               var9.cancel();
            }

            if (z.isDebugEnabled()) {
               z.debug(a("!\u007fx\u0016#'6h\u0016 %ti\u000b\u0016") + var3 + a("-hAEm") + var1);
            }

            u var10 = new u(var1, var3);
            this.q.put(var10);
            var1.b((Object)var10);
         }

         if (!var12) {
            return;
         }
      }

      if (z.isDebugEnabled()) {
         z.warn(a("\u000eTHR,$\u007fu\u0011*mou\u0012(/nh$") + var3 + a("-hAEm") + var1);
      }

   }

   void c(SnmpPendingRequest var1) {
      TimerItem var2 = (TimerItem)var1.k();
      if (var2 != null) {
         var2.cancel();
      }

      var1.b((Object)null);
   }

   void d(SnmpPendingRequest var1) {
      TimerItem var2 = (TimerItem)var1.l();
      if (var2 != null) {
         var2.cancel();
      }

      var1.c((Object)null);
   }

   void e(SnmpPendingRequest var1) throws SnmpException {
      if (z.isDebugEnabled()) {
         z.debug(a("\u0001nh\u0010m\u0004ro\u001c\"6~n\u0016#'!<") + var1.getPeer());
      }

      SnmpPendingRequest var2 = new SnmpPendingRequest(this.x, var1.getPeer(), (SnmpResponseListener)null, 160, a);
      var2.a(true);
      var2.a(var1);
      SnmpRequestPDU var3 = new SnmpRequestPDU();
      this.sendPDU(var2, var3, a, var1.getParameters());
   }

   void f(SnmpPendingRequest var1) throws SnmpException {
      if (z.isDebugEnabled()) {
         z.debug(a("\u0010^N9\u0002\u0012VU1\n`OU2\b`HE1\u000ez;") + var1.getPeer());
      }

      var1.c(true);
      SnmpPendingRequest var2 = new SnmpPendingRequest(this.x, var1.getPeer(), (SnmpResponseListener)null, 160, a);
      var2.b(true);
      var2.a(var1);
      SnmpRequestPDU var3 = new SnmpRequestPDU();
      this.sendPDU(var2, var3, a, var1.getParameters());
   }

   void g(SnmpPendingRequest var1) throws SnmpException {
      boolean var6 = SnmpSession.B;

      try {
         this.h(var1);
         SnmpPeer var2 = var1.getPeer();
         Object var3 = var1.j();
         TransportEntity var4;
         if (var3 != null) {
            var1.a(System.currentTimeMillis());
            this.b(var1);
            var4 = var2.getTransportEntity();
            if (var4.getTransportType() != this.b.getTransportType()) {
               z.error(a("4i}\u0011>0tn\u000b\b.ou\u000b4`vu\f !o\u007f\u0017a`xt\u001e#'rr\u0018m%uh\u001699"));

               try {
                  var4 = TransportEntity.newInstance(this.b.getTransportType(), var2.getAddress(), var2.getPort());
                  var2.a(var4);
               } catch (Exception var7) {
                  z.error(a("#zr\u0011\"4;\u007f\r(!oy_#%l<+?!uo\u000f\"2oY\u00119)oe"), var7);
               }
            }

            if (this.v.getMonitor() != null && var3 instanceof SnmpBuffer) {
               this.v.getMonitor().outgoingMessage(var1.getSnmpMessage(), (SnmpBuffer)var3, var4);
            }

            if (var2.isCollectingStats()) {
               var2.getStats().d();
               var2.getStats().f();
            }

            if (this.x.isCollectingStats()) {
               this.x.getStats().d();
               this.x.getStats().f();
            }

            var3 = this.b.send(var3, var4);
            var1.a(var3);
            if (!var6) {
               return;
            }
         }

         if (this.b == null) {
            var1.a(System.currentTimeMillis());
            this.b(var1);
            var4 = var2.getTransportEntity();
            if (var2.isCollectingStats()) {
               var2.getStats().d();
               var2.getStats().f();
            }

            if (this.x.isCollectingStats()) {
               this.x.getStats().d();
               this.x.getStats().f();
            }

            this.v.send(var1.getSnmpMessage(), var4);
            if (!var6) {
               return;
            }
         }

         if (z.isDebugEnabled()) {
            z.debug(a("\u000et<;,4z<\u0019\"2!<") + var1);
         }

         throw new SnmpException(a("\u000et<;,4z<\u0016#`Hr\u0012=\u0010~r\u001b$.|N\u001a<5~o\u000bc"));
      } catch (Exception var8) {
         z.debug(a("3~r\u001b\u001d\u0012"), var8);
         throw new SnmpException(var8.getMessage());
      }
   }

   public void retryPDU(SnmpPendingRequest var1) throws SnmpException {
      this.retryPDU(var1, false);
   }

   public void retryPDU(SnmpPendingRequest var1, boolean var2) throws SnmpException {
      synchronized(this.f) {
         Integer var4 = new Integer(var1.getRequestId());
         SnmpPendingRequest var5 = (SnmpPendingRequest)this.e.remove(var4);
         if (var5 != null || var2) {
            var1.d();
            SnmpPeer var6 = var1.getPeer();

            try {
               if (var6.isCollectingStats()) {
                  var6.getStats().c();
                  var6.getStats().f();
               }

               if (this.x.isCollectingStats()) {
                  this.x.getStats().c();
                  this.x.getStats().f();
               }
            } catch (Exception var9) {
               z.error(a("3o}\u000b$3ou\u001c>`~n\r\"2"), var9);
            }

            this.sendPDU(var1, var1.p(), var1.q(), var1.getParameters(), false);
         }

      }
   }

   public void run() {
      boolean var2 = SnmpSession.B;

      try {
         Thread.sleep(250L);
      } catch (InterruptedException var3) {
      }

      while(this.m) {
         try {
            if (this.n != null) {
               this.a();
               if (!var2) {
                  continue;
               }
            }

            this.b();
         } catch (IOException var4) {
            SnmpFramework.handleException(this, var4);
         } catch (SnmpException var5) {
            SnmpFramework.handleException(this, var5);
         } catch (Exception var6) {
            SnmpFramework.handleException(this, var6);
            if (var2) {
               break;
            }
         }
      }

   }

   public void handleMessage(SnmpMessage var1, TransportEntity var2) {
      QueuedMsg var3 = new QueuedMsg();
      var3.a = var1;
      var3.b = var2;
      var3.c = System.currentTimeMillis();

      try {
         this.n.pushBack(var3);
      } catch (InterruptedException var5) {
      }

   }

   private void a() throws SnmpException, InterruptedException {
      boolean var7 = SnmpSession.B;
      QueuedMsg var1 = (QueuedMsg)this.n.popFront();
      SnmpMessage var2 = var1.a;
      long var3 = var1.c;
      TransportEntity var5 = var1.b;
      SnmpPDU var6 = var2.getData();
      if (var2.isDiscovery() && var6.getType() != 168 && var6.getType() != 162) {
         if (this.A != null) {
            this.A.processDiscovery(var5, var2);
            if (!var7) {
               return;
            }
         }

         SnmpFramework.handleException(this, new SnmpException(a("\u0004ro\u001c\"6~n\u0006m\u0012~m\n(3o<1\"4;]\u0013!/ly\u001bw`") + var2));
         if (!var7) {
            return;
         }
      }

      if (var6.getType() == 162) {
         this.a(var2, (SnmpRequestPDU)var6, var3, var5);
         if (!var7) {
            return;
         }
      }

      if (var6.getType() == 168) {
         this.a(var2, (SnmpRequestPDU)var6, var3);
         if (!var7) {
            return;
         }
      }

      if (var6.getType() == 166) {
         this.a(var2, var5, (SnmpRequestPDU)var6, var3);
         if (!var7) {
            return;
         }
      }

      if (var6.getType() == 164) {
         this.a(var2, var5, var6, var3);
         if (!var7) {
            return;
         }
      }

      if (var6.getType() == 167) {
         this.a(var2, var5, var6, var3);
         if (!var7) {
            return;
         }
      }

      SnmpFramework.handleException(this, new SnmpException(a("\tuj\u001e!)\u007f</\t\u0015!<") + var6));
   }

   private void b() throws IOException, SnmpException {
      boolean var9 = SnmpSession.B;
      SnmpMessage var1 = null;
      SnmpPDU var2 = null;
      long var3 = 0L;
      byte[] var5 = this.s;
      SnmpBuffer var6 = new SnmpBuffer(var5, 0, var5.length);
      TransportEntity var7 = this.b.receive(var6, true);
      var3 = System.currentTimeMillis();

      try {
         var1 = this.u.decodeMessage(var6);
         var1.setTimestamp(var3);
         if (this.v.getMonitor() != null) {
            this.v.getMonitor().incomingMessage(var1, var6, var7, (String)null);
         }
      } catch (SnmpSecurityCoderException var10) {
         if (this.v.getMonitor() != null) {
            this.v.getMonitor().incomingMessage((SnmpMessage)null, var6, var7, a("3~\u007f\n?)oe_(2is\r"));
         }

         SnmpFramework.handleException(this, var10);
         if (z.isDebugEnabled()) {
            z.debug(a("\u0013^_*\u001f\tOE_\b\u0012IS-m\tU<,\u0003\rK<2\u001e\u0007"), var10);
         }

         if (var10.isReportable() || this.x.isReportingAllErrors()) {
            this.v.dispatchSecurityError(var6, var7, var10.getSpecificError(), var10.getMsgId(), var10);
         }

         return;
      }

      var2 = var1.getData();
      if (z.isDebugEnabled()) {
         z.debug(a("\u0012^_:\u0004\u0016^X_\u001e\u000eVL_\u0000\u0013\\&_e") + var1 + ")");
      }

      if (var1.isDiscovery() && var2.getType() != 168 && var2.getType() != 162) {
         if (this.A != null) {
            this.A.processDiscovery(var7, var1);
            if (!var9) {
               return;
            }
         }

         SnmpFramework.handleException(this, new SnmpException(a("\u0004ro\u001c\"6~n\u0006m\u0012~m\n(3o<1\"4;]\u0013!/ly\u001bw`") + var1));
         if (!var9) {
            return;
         }
      }

      if (var2.getType() == 162) {
         this.a(var1, (SnmpRequestPDU)var2, var3, var7);
         if (!var9) {
            return;
         }
      }

      if (var2.getType() == 168) {
         this.a(var1, (SnmpRequestPDU)var2, var3);
         if (!var9) {
            return;
         }
      }

      if (var2.getType() == 166) {
         this.a(var1, var7, (SnmpRequestPDU)var2, var3);
         if (!var9) {
            return;
         }
      }

      if (var2.getType() == 164) {
         this.a(var1, var7, var2, var3);
         if (!var9) {
            return;
         }
      }

      if (var2.getType() == 167) {
         this.a(var1, var7, var2, var3);
         if (!var9) {
            return;
         }
      }

      SnmpFramework.handleException(this, new SnmpException(a("\tuj\u001e!)\u007f</\t\u0015!<") + var2));
   }

   private void a(SnmpMessage var1, TransportEntity var2, SnmpRequestPDU var3, long var4) throws SnmpException {
      SnmpPendingInform var6 = new SnmpPendingInform(this.x, this.v, var2, var1);
      if (this.o != null) {
         this.o.put(new InformWorkItem(var6));
         if (!SnmpSession.B) {
            return;
         }
      }

      this.x.a(var6);
   }

   private void a(SnmpMessage var1, TransportEntity var2, SnmpPDU var3, long var4) throws SnmpException {
      SnmpTrap var6 = new SnmpTrap(this.x, var2, var1);
      if (this.o != null) {
         this.o.put(new TrapWorkItem(var6));
         if (!SnmpSession.B) {
            return;
         }
      }

      this.x.a(var6);
   }

   private void a(SnmpMessage var1, SnmpRequestPDU var2, long var3, TransportEntity var5) throws SnmpException {
      boolean var26 = SnmpSession.B;
      Integer var6 = new Integer(var1.getMsgID());
      SnmpPendingRequest var7 = null;
      boolean var10000;
      synchronized(this.f) {
         label767: {
            var7 = (SnmpPendingRequest)this.e.remove(var6);
            if (var7 == null) {
               if (this.x.isCollectingStats()) {
                  this.x.getStats().b();
                  this.x.getStats().f();
               }

               if (this.getProcessInvalidResponseMessageIDs()) {
                  if (z.isDebugEnabled()) {
                     z.debug(a("!oh\u001a 0ou\u0011*`os_ !o\u007f\u0017m5uq\u001e9#sy\u001bm-~o\f,'~U;w`") + var6);
                  }

                  try {
                     Vector var9 = new Vector();
                     var9.addAll(this.e.values());
                     if (z.isDebugEnabled()) {
                        z.debug(a("2~\u007f\u001a$6~x_(.ou\u000b4z;") + var5.getAddress() + ":" + var5.getPort());
                     }

                     ListIterator var10 = var9.listIterator();

                     while(var10.hasNext()) {
                        SnmpPendingRequest var11 = (SnmpPendingRequest)var10.next();
                        TransportEntity var12 = var11.getPeer().getTransportEntity();
                        var10000 = z.isDebugEnabled();
                        if (var26) {
                           break label767;
                        }

                        if (var10000) {
                           z.debug(a("#sy\u001c&)u{_(.ou\u000b4z;") + var12.getAddress() + ":" + var12.getPort());
                        }

                        if (var12.getAddress().equals(var5.getAddress()) && var12.getPort() == var5.getPort() && var11.getRequestVarBindList().size() == var1.getData().getVarBindList().size()) {
                           var7 = var11;
                           this.e.remove(new Integer(var11.getRequestId()));
                           if (!var26) {
                              break;
                           }
                        }

                        if (var26) {
                           break;
                        }
                     }
                  } catch (Exception var44) {
                     z.debug(a("%in\u0010?`kn\u0010.%ho\u0016#';u\u0011;!wu\u001bm\u0012~m\n(3oU;"), var44);
                  }
               }

               if (var7 == null) {
                  throw new SnmpException(a("\u000et<,#-kN\u001a<5~o\u000bm&tn_\u001f%ji\u001a>4RxEm") + var6);
               }
            }

            var10000 = this.j;
         }

         if (var10000 && (var7.hasTerminationOid() || var7.h() || var7.isSplit())) {
            var7.a(-1);
         }

         this.c(var7);
      }

      var7.a(var5);
      var7.b(var3);
      if (SnmpFramework.isCompatibilityMode() && var7.m()) {
         z.debug(a("\u0003TQ/\f\u0014R^6\u0001\tOE_\u0000\u000f_YEm$ro\u001c\"6~n\u0006m2~l\u0010?4;n\u001a.%rj\u001a)`zo_,`iy\f=/uo\u001aw") + var1);
         SnmpResponseListener var50 = var7.getResponseListener();
         SnmpPeer var79 = var7.getPeer();
         var79.setSnmpEngineID(var1.getSnmpEngineID());
         if (var79.getDefaultContext() == null) {
            var79.setDefaultContext(var1.getContext());
         }

         var7.a(var1);
         var7.setResult(var2.getVarBindList(), var2.getErrorStatus(), var2.getErrorIndex());
         this.c(var7);
         if (var1.getSnmpEngineID() != null) {
            try {
               label779: {
                  V3SnmpMessageModule var55 = (V3SnmpMessageModule)this.u.getMessageModule(3);
                  USMSecurityCoder var62 = (USMSecurityCoder)var55.getSecurityCoder();
                  USMEngineMap var76 = var62.getEngineMap();
                  USMUserTable var68 = (USMUserTable)((USMUserTable)var7.getParameters().getUserTable());
                  USMEngineInfo var87 = var76.get(var1.getSnmpEngineID());
                  if (var87 == null) {
                     var87 = new USMEngineInfo(var1.getSnmpEngineID());
                     var76.add(var87);
                  }

                  var87.addUserData(var68);
                  if (var79.getAutoTimeResyncThreshold() > 0) {
                     var87.setAutoTimeResyncThreshold(var79.getAutoTimeResyncThreshold());
                     if (!var26) {
                        break label779;
                     }
                  }

                  if (SnmpPeer.getDefaultAutoTimeResyncThreshold() > 0) {
                     var87.setAutoTimeResyncThreshold(SnmpPeer.getDefaultAutoTimeResyncThreshold());
                  }
               }
            } catch (Exception var33) {
               z.error(a("\u0005in\u0010?`rr_\u0018\u0013V<*>%i<\u0016#)ou\u001e!)a}\u000b$/u<Em"), var33);
            }
         }

         if (var7.v() != null) {
            label424: {
               SnmpPendingRequest var57 = var7.v();
               if (var57.getPeer().getSnmpEngineID() == null) {
                  SnmpException var63 = new SnmpException(a("\u0005u{\u0016#%;U;m.th_\u001e%o&_") + var57.getPeer());
                  SnmpFramework.handleException(this, var63);
                  var57.t();
                  if (var50 != null) {
                     var50.handleException(var57, var63);
                  }

                  if (var50 != null) {
                     var50.handleTimeout(var57);
                  }

                  var57.c();
                  if (!var26) {
                     break label424;
                  }
               }

               try {
                  this.sendPDU(var57, var57.p(), var57.q(), var57.getParameters(), false);
               } catch (SnmpException var32) {
                  SnmpFramework.handleException(this, var32);
                  var57.t();
                  if (var50 != null) {
                     var50.handleException(var57, var32);
                  }

                  if (var50 != null) {
                     var50.handleTimeout(var57);
                  }

                  var57.c();
               }
            }
         }

         var7.a();
         if (var50 != null) {
            this.p.put(new p(var7, var2));
         }

      } else {
         int var51;
         try {
            SnmpPeer var8 = var7.getPeer();
            long var48;
            if (var8.isCollectingStats()) {
               if (var7.getRetryCount() == 0 || !this.j) {
                  var48 = var7.getResponseTimestamp() - var7.getRequestTimestamp();
                  var8.getStats().a(var48);
               }

               var8.getStats().e();
               var8.getStats().f();
            }

            if (this.x.isCollectingStats()) {
               if (var7.getRetryCount() == 0 || !this.j) {
                  var48 = var7.getResponseTimestamp() - var7.getRequestTimestamp();
                  this.x.getStats().a(var48);
               }

               this.x.getStats().e();
               this.x.getStats().f();
            }

            if (var8.getTimeout() < var8.getAdaptiveTimeout() && var7.getRetryCount() == 0) {
               var51 = var8.getTimeoutAlgorithm();
               if (var51 == -1) {
                  var51 = this.x.getDefaultTimeoutAlgorithm();
               }

               if (var51 == 4) {
                  long var52 = var3 - var7.getRequestTimestamp();
                  if (z.isDebugEnabled()) {
                     z.debug(a("2~o\u000f\".hyR9)vyEm") + var52);
                  }

                  long var60 = var52 * 5L / 4L;
                  if (var8.getAdaptiveTimeout() > var60) {
                     var60 = (var60 + var8.getAdaptiveTimeout()) / 2L;
                  }

                  long var14 = var8.getMaxTimeout();
                  if (var14 <= 0L) {
                     var14 = var8.getTimeout() * (long)(var8.getMaxRetries() + 1);
                  }

                  if (var60 > var14) {
                     var60 = var14;
                  }

                  if (z.isDebugEnabled()) {
                     z.debug(a("3~h\u000b$.|1\u001e)!kh\u0016;%6h\u0016 %ti\u000bw`") + var60);
                  }

                  var8.setAdaptiveTimeout(var60);
               }
            }
         } catch (Exception var43) {
            z.error(a("%in\u0010?`rr_,$zl\u000b$6~<\u000b$-~s\n9`kn\u0010.%ho\u0016#'"), var43);
         }

         if (var7.getPeer() != null && var7.getPeer().getOidMap() != null && var2.getVarBindList() != null) {
            try {
               SnmpOidMap var46 = var7.getPeer().getOidMap();
               var46.translate(var2.getVarBindList(), true);
            } catch (Exception var31) {
            }
         }

         if (var7.n()) {
            this.a(var1, var7, var2, false);
         } else {
            SnmpOid var16;
            boolean var10001;
            SnmpOid var47;
            SnmpVarBindList var53;
            int var67;
            if (var7.getType() == 161 && (var7.hasTerminationOid() || var7.h())) {
               var47 = var7.getTerminationOid();
               if (var7.showSteps() && var7.getResponseListener() != null) {
                  this.p.put(new p(var7, var2));
               }

               boolean var54;
               label702: {
                  var54 = true;
                  var53 = var2.getVarBindList();
                  if (var53.size() > 0) {
                     SnmpVarBind var13;
                     if (var7.hasTerminationOid()) {
                        SnmpVarBind var56 = var53.get(0);
                        SnmpValue var64 = var56.getValue();
                        var13 = var56;
                        SnmpVarBindList var69 = var7.q();
                        if (var69 != null && var69.size() > 0) {
                           var13 = var69.get(0);
                        }

                        label697: {
                           if (var2.getErrorStatus() == 0 && (var56.getOid().compareTo(var47) < 0 || var47.contains(var56.getOid()) && var56.getOid().compareTo(var47) != 0) && var64 != null && var64.getTag() != 130 && var56.getOid().compareTo(var13.getOid()) > 0) {
                              var54 = false;
                              if (!var26) {
                                 break label697;
                              }
                           }

                           var54 = true;
                        }

                        try {
                           if (var7.getPeer().isCheckIncreasingOids()) {
                              SnmpOid var15 = var7.q().get(0).getOid();
                              var16 = var56.getOid();
                              if (var15.compareTo(var16) > 0) {
                                 var54 = true;
                                 if (z.isCommsEnabled()) {
                                    z.comms(a("7zp\u0014m4~n\u0012$.zh\u0016#'7<\r(3ks\u0011>%;S6\t`ro_#/o<\u0018?%zh\u001a?`ot\u001e#`iy\u000e8%hh_\u0002\t_&_") + var16 + a("`:\"_") + var15);
                                 }
                              }
                           }
                        } catch (Exception var30) {
                        }

                        if (!var26) {
                           break label702;
                        }
                     }

                     label838: {
                        SnmpVarBindList var58 = var7.i();
                        if (var58.size() > var53.size()) {
                           var54 = true;
                           if (!var26) {
                              break label838;
                           }
                        }

                        if (var2.getErrorStatus() != 0) {
                           var54 = true;
                           if (!var26) {
                              break label838;
                           }
                        }

                        var54 = true;
                        var67 = 0;

                        while(true) {
                           if (var67 < var58.size()) {
                              label791: {
                                 var13 = var58.get(var67);
                                 SnmpVarBind var72 = var53.get(var67);
                                 var10000 = var13.getOid().contains(var72.getOid());
                                 if (var26) {
                                    break;
                                 }

                                 if (var10000 && !var72.getOid().equals(var13.getOid()) && var72.getValue() != null && !var72.isError()) {
                                    var54 = false;
                                    if (!var26) {
                                       break label791;
                                    }
                                 }

                                 ++var67;
                                 if (!var26) {
                                    continue;
                                 }
                              }
                           }

                           try {
                              var10000 = var7.getPeer().isCheckIncreasingOids();
                              break;
                           } catch (Exception var42) {
                              var10001 = false;
                              break label838;
                           }
                        }

                        try {
                           if (var10000) {
                              SnmpOid var73 = var7.q().get(0).getOid();
                              SnmpOid var65 = var53.get(0).getOid();
                              if (var73.compareTo(var65) > 0) {
                                 var54 = true;
                                 if (z.isCommsEnabled()) {
                                    z.comms(a("7zp\u0014m4~n\u0012$.zh\u0016#'7<\r(3ks\u0011>%;S6\t`ro_#/o<\u0018?%zh\u001a?`ot\u001e#`iy\u000e8%hh_\u0002\t_&_") + var65 + a("`:\"_") + var73);
                                 }
                              }
                           }
                        } catch (Exception var41) {
                           var10001 = false;
                        }
                     }

                     if (!var26) {
                        break label702;
                     }
                  }

                  var54 = true;
               }

               label792: {
                  if (!var54) {
                     label643: {
                        if (var7.getResponseVarBindList() == null) {
                           var7.setResponseVarBindList(var53);
                           if (!var26) {
                              break label643;
                           }
                        }

                        var7.getResponseVarBindList().addAll(var53);
                     }

                     SnmpRequestPDU var59 = new SnmpRequestPDU();
                     this.sendPDU(var7, var59, var53, var7.getParameters(), true);
                     if (!var26) {
                        break label792;
                     }
                  }

                  if (var7.getResponseVarBindList() == null) {
                     var7.setResponseVarBindList(new SnmpVarBindList());
                  }

                  synchronized(var7) {
                     label634: {
                        var7.a(var1);
                        var7.setResult(var7.getResponseVarBindList(), var2.getErrorStatus(), var2.getErrorIndex());
                        if (var7.getResponseListener() != null) {
                           var7.t();
                           this.p.put(new p(var7, (SnmpRequestPDU)null, true));
                           if (!var26) {
                              break label634;
                           }
                        }

                        var7.a();
                     }
                  }
               }

               if (!var26) {
                  return;
               }
            }

            if (var7.getType() == 165 && (var7.hasTerminationOid() || var7.h())) {
               var47 = var7.getTerminationOid();
               if (var7.showSteps() && var7.getResponseListener() != null) {
                  this.p.put(new p(var7, var2));
               }

               label794: {
                  var51 = var7.getRequestVarBindList().size();
                  var53 = var2.getVarBindList();
                  if (var53.size() > 0 && var51 > 0) {
                     label795: {
                        int var61 = var53.size() / var51;
                        var67 = var53.size() % var51;
                        if (var61 > 0) {
                           int var66;
                           boolean var74;
                           int var80;
                           label839: {
                              var66 = (var61 - 1) * var51;
                              var74 = true;
                              SnmpOid var18;
                              if (var7.hasTerminationOid()) {
                                 SnmpVarBind var71 = var53.get(var66);
                                 SnmpValue var78 = var71.getValue();
                                 if (var2.getErrorStatus() == 0 && (var71.getOid().compareTo(var47) < 0 || var47.contains(var71.getOid()) && var71.getOid().compareTo(var47) != 0) && var78 != null && var78.getTag() != 130) {
                                    var74 = false;
                                 }

                                 try {
                                    if (var7.getPeer().isCheckIncreasingOids()) {
                                       var18 = var7.q().get(0).getOid();
                                       SnmpOid var19 = var71.getOid();
                                       if (var18.compareTo(var19) > 0) {
                                          var74 = true;
                                          if (z.isCommsEnabled()) {
                                             z.comms(a("7zp\u0014m4~n\u0012$.zh\u0016#'7<\r(3ks\u0011>%;S6\t`ro_#/o<\u0018?%zh\u001a?`ot\u001e#`iy\u000e8%hh_\u0002\t_&_") + var19 + a("`:\"_") + var18);
                                          }
                                       }
                                    }
                                 } catch (Exception var29) {
                                 }

                                 if (!var26) {
                                    break label839;
                                 }
                              }

                              SnmpVarBindList var75 = var7.i();
                              if (var75.size() > var53.size() - var66) {
                                 var74 = true;
                                 if (!var26) {
                                    break label839;
                                 }
                              }

                              if (var2.getErrorStatus() != 0) {
                                 var74 = true;
                                 if (!var26) {
                                    break label839;
                                 }
                              }

                              var74 = true;
                              var80 = 0;

                              SnmpVarBind var17;
                              while(true) {
                                 if (var80 < var75.size()) {
                                    label800: {
                                       var17 = var75.get(var80);
                                       SnmpVarBind var85 = var53.get(var80 + var66);
                                       var10000 = var17.getOid().contains(var85.getOid());
                                       if (var26) {
                                          break;
                                       }

                                       if (var10000 && !var85.getOid().equals(var17.getOid()) && var85.getValue() != null && !var85.isError()) {
                                          var74 = false;
                                          if (!var26) {
                                             break label800;
                                          }
                                       }

                                       ++var80;
                                       if (!var26) {
                                          continue;
                                       }
                                    }
                                 }

                                 try {
                                    var10000 = var7.getPeer().isCheckIncreasingOids();
                                    break;
                                 } catch (Exception var39) {
                                    var10001 = false;
                                    break label839;
                                 }
                              }

                              try {
                                 if (var10000) {
                                    var16 = var7.q().get(0).getOid();
                                    var17 = var53.get(var66);
                                    var18 = var17.getOid();
                                    if (var16.compareTo(var18) > 0) {
                                       var74 = true;
                                       if (z.isCommsEnabled()) {
                                          z.comms(a("7zp\u0014m4~n\u0012$.zh\u0016#'7<\r(3ks\u0011>%;S6\t`ro_#/o<\u0018?%zh\u001a?`ot\u001e#`iy\u000e8%hh_\u0002\t_&_") + var18 + a("`:\"_") + var16);
                                       }
                                    }
                                 }
                              } catch (Exception var38) {
                                 var10001 = false;
                              }
                           }

                           int var77;
                           SnmpVarBindList var83;
                           int var84;
                           if (!var74) {
                              if (var7.getResponseVarBindList() == null) {
                                 label499: {
                                    if (var67 == 0) {
                                       var7.setResponseVarBindList(var53);
                                       if (!var26) {
                                          break label499;
                                       }
                                    }

                                    var7.setResponseVarBindList(new SnmpVarBindList());
                                    var77 = var61 * var51;
                                    var7.getResponseVarBindList().add(var53, var77);
                                    if (z.isDebugEnabled()) {
                                       z.debug(a("%ch\r,`m}\r/)ux\fm)u<\r(3ks\u0011>%;4") + var67 + a("i5<\u0010#,b<\u001e)$rr\u0018m") + var77 + a("`;j\u001e?\"rr\u001b>n"));
                                    }
                                 }
                              } else {
                                 label803: {
                                    if (var67 == 0) {
                                       var7.getResponseVarBindList().addAll(var53);
                                       if (!var26) {
                                          break label803;
                                       }
                                    }

                                    var77 = var61 * var51;
                                    if (z.isDebugEnabled()) {
                                       z.debug(a("%ch\r,`m}\r/)ux\fm)u<\r(3ks\u0011>%;4") + var67 + a("i5<\u0010#,b<\u001e)$rr\u0018m") + var77 + a("`;j\u001e?\"rr\u001b>n"));
                                    }

                                    var7.getResponseVarBindList().add(var53, var77);
                                 }
                              }

                              SnmpBulkPDU var86 = new SnmpBulkPDU();
                              var86.setNonRepeaters(0);
                              var86.setMaxRepetitions(var7.getMaxRepetitions());
                              var83 = new SnmpVarBindList();
                              var84 = 0;

                              do {
                                 if (var84 >= var51) {
                                    this.sendPDU(var7, var86, var83, var7.getParameters(), true);
                                    if (var26) {
                                    }
                                    break;
                                 }

                                 var83.add(var53.get(var66 + var84));
                                 ++var84;
                              } while(!var26 || !var26);
                           } else {
                              if (var7.getResponseVarBindList() == null) {
                                 var7.setResponseVarBindList(new SnmpVarBindList());
                              }

                              int var90;
                              label564: {
                                 label832: {
                                    var77 = 0;
                                    if (var7.hasTerminationOid()) {
                                       do {
                                          if (var77 > var66) {
                                             break label832;
                                          }

                                          SnmpVarBind var81 = var53.get(var77);
                                          SnmpValue var82 = var81.getValue();
                                          var90 = var81.getOid().compareTo(var47);
                                          if (var26) {
                                             break label564;
                                          }

                                          if (var90 >= 0 && (!var47.contains(var81.getOid()) || var81.getOid().compareTo(var47) == 0) || var82 == null || var82.getTag() == 130) {
                                             break label832;
                                          }

                                          var77 += var51;
                                       } while(!var26);
                                    }

                                    var83 = var7.i();
                                    var84 = 0;

                                    label543:
                                    do {
                                       var90 = var77;

                                       while(true) {
                                          if (var90 > var66) {
                                             break label543;
                                          }

                                          var90 = var84;
                                          if (var26) {
                                             break label564;
                                          }

                                          if (var84 != 0) {
                                             break label543;
                                          }

                                          var84 = 1;
                                          int var88 = 0;

                                          while(true) {
                                             if (var88 >= var83.size()) {
                                                continue label543;
                                             }

                                             SnmpVarBind var89 = var83.get(var88);
                                             SnmpVarBind var20 = var53.get(var77 + var88);
                                             var90 = var89.getOid().contains(var20.getOid());
                                             if (var26) {
                                                break;
                                             }

                                             if (var90 != 0 && !var20.getOid().equals(var89.getOid()) && var20.getValue() != null && !var20.isError()) {
                                                var77 += var51;
                                                var84 = 0;
                                                if (!var26) {
                                                   continue label543;
                                                }
                                             }

                                             ++var88;
                                             if (var26) {
                                                continue label543;
                                             }
                                          }
                                       }
                                    } while(!var26);
                                 }

                                 var90 = 0;
                              }

                              var80 = var90;

                              while(var80 < var77) {
                                 var7.getResponseVarBindList().add(var53.get(var80));
                                 ++var80;
                                 if (var26 && var26) {
                                    break label795;
                                 }
                              }

                              synchronized(var7) {
                                 label505: {
                                    var7.a(var1);
                                    var7.setResult(var7.getResponseVarBindList(), var2.getErrorStatus(), var2.getErrorIndex());
                                    if (var7.getResponseListener() != null) {
                                       var7.t();
                                       this.p.put(new p(var7, (SnmpRequestPDU)null, true));
                                       if (!var26) {
                                          break label505;
                                       }
                                    }

                                    var7.a();
                                 }
                              }
                           }

                           if (!var26) {
                              break label795;
                           }
                        }

                        synchronized(var7) {
                           label481: {
                              var7.a(var1);
                              var7.setResult(var7.getResponseVarBindList(), var2.getErrorStatus(), var2.getErrorIndex());
                              if (var7.getResponseListener() != null) {
                                 var7.t();
                                 this.p.put(new p(var7, (SnmpRequestPDU)null, true));
                                 if (!var26) {
                                    break label481;
                                 }
                              }

                              var7.a();
                           }
                        }
                     }

                     if (!var26) {
                        break label794;
                     }
                  }

                  synchronized(var7) {
                     label470: {
                        var7.a(var1);
                        var7.setResult(var7.getResponseVarBindList(), var2.getErrorStatus(), var2.getErrorIndex());
                        if (var7.getResponseListener() != null) {
                           var7.t();
                           this.p.put(new p(var7, (SnmpRequestPDU)null, true));
                           if (!var26) {
                              break label470;
                           }
                        }

                        var7.a();
                     }
                  }
               }

               if (!var26) {
                  return;
               }
            }

            if (var7.isSplit()) {
               SnmpVarBindList var49 = var2.getVarBindList();
               if (var7.showSteps() && var7.getResponseListener() != null) {
                  this.p.put(new p(var7, var2));
               }

               label458: {
                  if (var7.getResponseVarBindList() == null) {
                     var7.setResponseVarBindList(var49);
                     if (!var26) {
                        break label458;
                     }
                  }

                  var7.getResponseVarBindList().addAll(var49);
               }

               label810: {
                  if (var2.getErrorStatus() == 0 && var7.r() < var7.getRequestVarBindList().size()) {
                     var7.b((SnmpVarBindList)null);
                     SnmpRequestPDU var70 = new SnmpRequestPDU();
                     this.sendPDU(var7, var70, var7.getRequestVarBindList(), var7.getParameters(), true);
                     if (!var26) {
                        break label810;
                     }
                  }

                  var51 = 0;
                  if (var2.getErrorIndex() > 0) {
                     try {
                        var51 = var7.r() - var2.getVarBindList().size() + var2.getErrorIndex();
                     } catch (Exception var28) {
                        var51 = var2.getErrorIndex();
                     }
                  }

                  synchronized(var7) {
                     label447: {
                        var7.a(var1);
                        var7.setResult(var7.getResponseVarBindList(), var2.getErrorStatus(), var51);
                        if (var7.getResponseListener() != null) {
                           var7.t();
                           this.p.put(new p(var7, (SnmpRequestPDU)null, true));
                           if (!var26) {
                              break label447;
                           }
                        }

                        var7.a();
                     }
                  }
               }

               if (!var26) {
                  return;
               }
            }

            synchronized(var7) {
               var7.a(var1);
               var7.setResult(var2.getVarBindList(), var2.getErrorStatus(), var2.getErrorIndex());
               if (var2.getType() == 162) {
                  var7.a();
               }

               if (var7.getResponseListener() != null) {
                  var7.t();
                  this.p.put(new p(var7, var2));
               }
            }

         }
      }
   }

   private void h(SnmpPendingRequest var1) {
      SnmpPeer var2 = var1.getPeer();
      if (var2 != null) {
         if (var2.getAutoTimeResyncThreshold() <= 0 && SnmpPeer.getDefaultAutoTimeResyncThreshold() <= 0) {
            return;
         }

         SnmpEngineID var3 = var2.getSnmpEngineID();
         if (var3 != null) {
            try {
               V3SnmpMessageModule var4 = (V3SnmpMessageModule)this.u.getMessageModule(3);
               USMSecurityCoder var5 = (USMSecurityCoder)var4.getSecurityCoder();
               USMEngineMap var6 = var5.getEngineMap();
               USMUserTable var7 = (USMUserTable)((USMUserTable)var1.getParameters().getUserTable());
               USMEngineInfo var8 = var6.get(var3);
               if (var8 == null) {
                  var8 = new USMEngineInfo(var3);
                  var6.add(var8);
               }

               if (var2.getAutoTimeResyncThreshold() > 0) {
                  var8.setAutoTimeResyncThreshold(var2.getAutoTimeResyncThreshold());
                  if (!SnmpSession.B) {
                     return;
                  }
               }

               if (SnmpPeer.getDefaultAutoTimeResyncThreshold() > 0) {
                  var8.setAutoTimeResyncThreshold(SnmpPeer.getDefaultAutoTimeResyncThreshold());
               }
            } catch (Exception var9) {
               z.error(a("\u0005in\u0010?`rr_\u0018\u0013V<*>%i<\u0016#)ou\u001e!)a}\u000b$/u<Em"), var9);
            }
         }
      }

   }

   private void a(SnmpMessage var1, SnmpPendingRequest var2, SnmpRequestPDU var3, boolean var4) {
      boolean var9 = SnmpSession.B;
      SnmpResponseListener var5 = var2.getResponseListener();
      this.c(var2);
      if (var2.v() != null) {
         SnmpPendingRequest var6 = var2.v();
         SnmpVarBindList var7 = var3.getVarBindList();
         if (var4 && (var7 == null || var7.get(D) == null)) {
            var6.a(var1);
            if (var6.getResponseListener() != null) {
               this.p.put(new p(var6, var3));
            }

            var6.b();
            if (!var9) {
               return;
            }
         }

         if (var6.getPeer().getSnmpEngineID() == null) {
            SnmpException var8 = new SnmpException(a("\u0005u{\u0016#%;U;m.th_\u001e%o&_") + var6.getPeer());
            SnmpFramework.handleException(this, var8);
            var6.c();
            var6.t();
            if (var5 != null) {
               var5.handleException(var6, var8);
            }

            if (var5 != null) {
               var5.handleTimeout(var6);
            }

            if (!var9) {
               return;
            }
         }

         try {
            this.sendPDU(var6, var6.p(), var6.q(), var6.getParameters(), false);
         } catch (SnmpException var10) {
            SnmpFramework.handleException(this, var10);
            var6.t();
            if (var5 != null) {
               var5.handleException(var6, var10);
            }

            if (var5 != null) {
               var5.handleTimeout(var6);
            }

            var6.c();
         }
      }

   }

   private void a(SnmpMessage var1, SnmpRequestPDU var2, long var3) throws SnmpException {
      boolean var14 = SnmpSession.B;
      Integer var5 = new Integer(var1.getMsgID());
      SnmpPendingRequest var6 = (SnmpPendingRequest)this.e.remove(var5);
      if (var6 == null) {
         throw new SnmpException(a("\u000et<,#-kN\u001a<5~o\u000bm&tn_\u001f%ji\u001a>4RxEm") + var5);
      } else {
         SnmpResponseListener var7 = var6.getResponseListener();
         if (var6.m()) {
            SnmpPeer var8 = var6.getPeer();
            var8.setSnmpEngineID(var1.getSnmpEngineID());
            if (var8.getDefaultContext() == null) {
               var8.setDefaultContext(var1.getContext());
            }

            var6.a(var1);
            var6.setResult(var2.getVarBindList(), var2.getErrorStatus(), var2.getErrorIndex());
            this.c(var6);
            if (var1.getSnmpEngineID() != null) {
               try {
                  label113: {
                     V3SnmpMessageModule var9 = (V3SnmpMessageModule)this.u.getMessageModule(3);
                     USMSecurityCoder var10 = (USMSecurityCoder)var9.getSecurityCoder();
                     USMEngineMap var11 = var10.getEngineMap();
                     USMUserTable var12 = (USMUserTable)((USMUserTable)var6.getParameters().getUserTable());
                     USMEngineInfo var13 = var11.get(var1.getSnmpEngineID());
                     if (var13 == null) {
                        var13 = new USMEngineInfo(var1.getSnmpEngineID());
                        var11.add(var13);
                     }

                     var13.addUserData(var12);
                     if (var8.getAutoTimeResyncThreshold() > 0) {
                        var13.setAutoTimeResyncThreshold(var8.getAutoTimeResyncThreshold());
                        if (!var14) {
                           break label113;
                        }
                     }

                     if (SnmpPeer.getDefaultAutoTimeResyncThreshold() > 0) {
                        var13.setAutoTimeResyncThreshold(SnmpPeer.getDefaultAutoTimeResyncThreshold());
                     }
                  }
               } catch (Exception var16) {
                  z.error(a("\u0005in\u0010?`rr_\u0018\u0013V<*>%i<\u0016#)ou\u001e!)a}\u000b$/u<Em"), var16);
               }
            }

            if (var6.v() != null) {
               label118: {
                  SnmpPendingRequest var18 = var6.v();
                  if (var18.getPeer().getSnmpEngineID() == null) {
                     SnmpException var19 = new SnmpException(a("\u0005u{\u0016#%;U;m.th_\u001e%o&_") + var18.getPeer());
                     SnmpFramework.handleException(this, var19);
                     var18.t();
                     if (var7 != null) {
                        var7.handleException(var18, var19);
                     }

                     if (var7 != null) {
                        var7.handleTimeout(var18);
                     }

                     var18.c();
                     if (!var14) {
                        break label118;
                     }
                  }

                  boolean var20 = false;
                  if (var18.getPollingInterval() > 0) {
                     var20 = true;
                  }

                  try {
                     this.sendPDU(var18, var18.p(), var18.q(), var18.getParameters(), var20);
                  } catch (SnmpException var15) {
                     SnmpFramework.handleException(this, var15);
                     var18.t();
                     if (var7 != null) {
                        var7.handleException(var18, var15);
                     }

                     if (var7 != null) {
                        var7.handleTimeout(var18);
                     }

                     var18.c();
                  }
               }
            }

            var6.b();
            if (var7 != null) {
               this.p.put(new p(var6, var2));
            }

            if (!var14) {
               return;
            }
         }

         if (var6.n()) {
            this.a(var1, var6, var2, true);
            if (!var14) {
               return;
            }
         }

         this.c(var6);
         var6.a(var1);
         SnmpVarBindList var17 = var2.getVarBindList();
         z.debug(a("\u0012^L0\u001f\u0014!<") + var17);
         if (var6.getResponseListener() != null) {
            this.p.put(new p(var6, var2));
         }

         if (var6.getRetryCount() >= var6.getPeer().getMaxRetries()) {
            var6.b();
            if (!var14) {
               return;
            }
         }

         if (var17 != null && var17.get(D) == null && var17.get(E) == null) {
            var6.b();
            if (!var14) {
               return;
            }
         }

         var6.getSession().retryPDU(var6, true);
      }
   }

   synchronized int c() {
      if (this.g > this.h) {
         this.g = this.i;
      }

      return this.g++;
   }

   public void cancel(SnmpPendingRequest var1) {
      this.cancel(var1, true);
   }

   public void cancel(SnmpPendingRequest var1, boolean var2) {
      if (z.isDebugEnabled()) {
         z.debug(a("#zr\u001c(,!<") + var1 + a("l;") + var2, new Exception(""));
      }

      this.e.remove(new Integer(var1.getRequestId()));
      synchronized(var1) {
         this.c(var1);
         if (var2) {
            this.d(var1);
         }

         var1.t();
      }
   }

   public synchronized void setReceiveBufferSize(int var1) {
      this.k = var1;
      this.s = new byte[var1];
      if (this.b != null && this.b instanceof BufferedTransportProvider) {
         ((BufferedTransportProvider)this.b).setReceiveBufferSize(var1);
      }

   }

   public int getReceiveBufferSize() {
      return this.k;
   }

   public void setMaxSize(int var1) {
      this.l = var1;
   }

   public Usm getUsm() {
      return this.A;
   }

   public SnmpEngine getSnmpEngine() {
      return this.v;
   }

   public int getMaxSize() {
      return this.l;
   }

   public void setMaxRequestId(int var1) {
      this.h = var1;
      if (this.g > this.h) {
         this.g = this.i;
      }

   }

   public void setMinRequestId(int var1) {
      this.i = var1;
      if (this.g < this.i) {
         this.g = this.i;
      }

   }

   public void setProcessInvalidResponseMessageIDs(boolean var1) {
      this.c = var1;
   }

   public boolean getProcessInvalidResponseMessageIDs() {
      return this.c;
   }

   public boolean isActive() {
      return this.m;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 64;
               break;
            case 1:
               var10003 = 27;
               break;
            case 2:
               var10003 = 28;
               break;
            case 3:
               var10003 = 127;
               break;
            default:
               var10003 = 77;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class TrapWorkItem extends WorkItem {
      SnmpTrap a = null;

      public TrapWorkItem(SnmpTrap var2) {
         this.a = var2;
      }

      public void perform() {
         if (SnmpSessionImpl.z.isDebugEnabled()) {
            SnmpSessionImpl.z.debug(a("8/B4t\u0003/H\rW\t0\u0019dS\t/E+Q\u0001g") + this.a);
         }

         SnmpSessionImpl.this.x.a(this.a);
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 108;
                  break;
               case 1:
                  var10003 = 93;
                  break;
               case 2:
                  var10003 = 35;
                  break;
               case 3:
                  var10003 = 68;
                  break;
               default:
                  var10003 = 35;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class InformWorkItem extends WorkItem {
      SnmpPendingInform a = null;

      public InformWorkItem(SnmpPendingInform var2) {
         this.a = var2;
      }

      public void perform() {
         SnmpSessionImpl.this.x.a(this.a);
      }
   }

   private class QueuedMsg {
      SnmpMessage a;
      TransportEntity b;
      long c;

      private QueuedMsg() {
         this.a = null;
         this.b = null;
         this.c = 0L;
      }

      // $FF: synthetic method
      QueuedMsg(Object var2) {
         this();
      }
   }
}
