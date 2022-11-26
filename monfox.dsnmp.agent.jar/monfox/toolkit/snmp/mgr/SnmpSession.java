package monfox.toolkit.snmp.mgr;

import java.net.InetAddress;
import java.util.Properties;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpBulkPDU;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.mgr.usm.Usm;
import monfox.toolkit.snmp.util.Lock;

public class SnmpSession {
   private SnmpInformListener a;
   private SnmpTrapListener b;
   private String c;
   private static int d = 0;
   private Object e;
   private boolean f;
   private boolean g;
   private Lock h;
   private SnmpResponseListener i;
   private SnmpResponseListener j;
   private Properties k;
   private SnmpPeer l;
   l m;
   SnmpMetadata n;
   boolean o;
   int p;
   boolean q;
   boolean r;
   private long s;
   int t;
   private boolean u;
   private SnmpStats v;
   private static final SnmpVarBindList w = new SnmpVarBindList();
   private static Logger x = null;
   private boolean y;
   private static SnmpSessionConfig z = null;
   private static final String A = "$Id: SnmpSession.java,v 1.59 2013/09/06 22:17:00 sking Exp $";
   public static boolean B;

   public SnmpSession(SnmpMetadata var1) throws SnmpException {
      this(var1, (SnmpPeer)null, getDefaultConfig().getLocalPort(), getDefaultConfig().getLocalAddr(), getDefaultConfig().getReceiveBufferSize());
   }

   public SnmpSession(SnmpMetadata var1, SnmpPeer var2) throws SnmpException {
      this(var1, var2, getDefaultConfig().getLocalPort(), getDefaultConfig().getLocalAddr(), getDefaultConfig().getReceiveBufferSize());
   }

   public SnmpSession(SnmpPeer var1, int var2, InetAddress var3) throws SnmpException {
      this(getDefaultConfig().getMetadata(), var1, var2, var3, getDefaultConfig().getReceiveBufferSize());
   }

   public SnmpSession(int var1) throws SnmpException {
      this(getDefaultConfig().getMetadata(), (SnmpPeer)null, var1, getDefaultConfig().getLocalAddr(), getDefaultConfig().getReceiveBufferSize());
   }

   public SnmpSession(SnmpMetadata var1, int var2) throws SnmpException {
      this(var1, (SnmpPeer)null, var2, getDefaultConfig().getLocalAddr(), getDefaultConfig().getReceiveBufferSize());
   }

   public SnmpSession(SnmpMetadata var1, SnmpPeer var2, int var3, InetAddress var4) throws SnmpException {
      this(var1, var2, var3, var4, getDefaultConfig().getReceiveBufferSize());
   }

   public SnmpSession(SnmpPeer var1, int var2, InetAddress var3, int var4) throws SnmpException {
      this(getDefaultConfig().getMetadata(), var1, var2, var3, var4, getDefaultConfig().getBuffered());
   }

   public SnmpSession(SnmpPeer var1, int var2, InetAddress var3, int var4, boolean var5) throws SnmpException {
      this(getDefaultConfig().getMetadata(), var1, var2, var3, var4, var5);
   }

   public SnmpSession(SnmpMetadata var1, SnmpPeer var2, int var3, InetAddress var4, int var5) throws SnmpException {
      this(var1, var2, var3, var4, var5, getDefaultConfig().getBuffered());
   }

   public SnmpSession(SnmpMetadata var1, SnmpPeer var2, int var3, InetAddress var4, int var5, boolean var6) throws SnmpException {
      this.a = null;
      this.b = null;
      this.c = a("\u0005\f<\u0004\u0013\u0019\u0007B") + ++d;
      this.e = new Object();
      this.f = false;
      this.g = false;
      this.h = new Lock();
      this.i = new GetAllListener();
      this.j = new BulkGetAllListener();
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = null;
      this.o = false;
      this.p = 1;
      this.q = false;
      this.r = true;
      this.s = -1L;
      this.t = -1;
      this.u = false;
      this.v = new SnmpStats();
      this.y = false;
      this.a();
      if (var1 == null) {
         var1 = SnmpFramework.getMetadata();
      }

      this.n = var1;
      this.l = var2;
      SnmpSessionConfig var7 = (SnmpSessionConfig)getDefaultConfig().clone();
      var7.setLocalPort(var3);
      var7.setLocalAddr(var4);
      var7.setReceiveBufferSize(var5);
      var7.setBuffered(var6);
      var7.setMetadata(var1);
      this.o = var7.isViewFromFirstVarBind();
      this.p = var7.getTimeoutAlgorithm();
      this.t = var7.getMaxPDUVarBinds();
      this.q = var7.isShowSteps();
      this.r = var7.isShutdownPendingRequests();
      this.m = new SnmpSessionImpl(this, var7);
      if (x.isDebugEnabled()) {
      }

   }

   public SnmpSession(SnmpMetadata var1, SnmpEngineID var2, int var3) throws SnmpException {
      this(var1, var2, var3, getDefaultConfig().getLocalAddr(), getDefaultConfig().getReceiveBufferSize(), getDefaultConfig().getBuffered());
   }

   public SnmpSession(SnmpMetadata var1, SnmpEngineID var2, int var3, InetAddress var4, int var5, boolean var6) throws SnmpException {
      this.a = null;
      this.b = null;
      this.c = a("\u0005\f<\u0004\u0013\u0019\u0007B") + ++d;
      this.e = new Object();
      this.f = false;
      this.g = false;
      this.h = new Lock();
      this.i = new GetAllListener();
      this.j = new BulkGetAllListener();
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = null;
      this.o = false;
      this.p = 1;
      this.q = false;
      this.r = true;
      this.s = -1L;
      this.t = -1;
      this.u = false;
      this.v = new SnmpStats();
      this.y = false;
      this.a();
      if (var1 == null) {
         var1 = SnmpFramework.getMetadata();
      }

      this.n = var1;
      SnmpSessionConfig var7 = (SnmpSessionConfig)getDefaultConfig().clone();
      var7.setEngineID(var2);
      var7.setLocalPort(var3);
      var7.setLocalAddr(var4);
      var7.setReceiveBufferSize(var5);
      var7.setBuffered(var6);
      var7.setMetadata(var1);
      this.o = var7.isViewFromFirstVarBind();
      this.p = var7.getTimeoutAlgorithm();
      this.t = var7.getMaxPDUVarBinds();
      this.q = var7.isShowSteps();
      this.r = var7.isShutdownPendingRequests();
      this.m = new SnmpSessionImpl(this, var7);
      if (x.isDebugEnabled()) {
         x.debug(a("\u0015\u001b*\u0016\u000e\u0013\rO\u00044;9<2)% \u00009P") + this);
      }

   }

   protected SnmpSession(l var1) {
      this.a = null;
      this.b = null;
      this.c = a("\u0005\f<\u0004\u0013\u0019\u0007B") + ++d;
      this.e = new Object();
      this.f = false;
      this.g = false;
      this.h = new Lock();
      this.i = new GetAllListener();
      this.j = new BulkGetAllListener();
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = null;
      this.o = false;
      this.p = 1;
      this.q = false;
      this.r = true;
      this.s = -1L;
      this.t = -1;
      this.u = false;
      this.v = new SnmpStats();
      this.y = false;
      this.a();
      this.m = var1;
      if (x.isDebugEnabled()) {
         x.debug(a("\u0015\u001b*\u0016\u000e\u0013\rO\u00044;9<2)% \u00009P") + this);
      }

   }

   public SnmpSession() throws SnmpException {
      this(getDefaultConfig().getMetadata());
   }

   public SnmpSession(SnmpPeer var1) throws SnmpException {
      this(getDefaultConfig().getMetadata(), var1);
   }

   public SnmpSession(SnmpSessionConfig var1) throws SnmpException {
      this.a = null;
      this.b = null;
      this.c = a("\u0005\f<\u0004\u0013\u0019\u0007B") + ++d;
      this.e = new Object();
      this.f = false;
      this.g = false;
      this.h = new Lock();
      this.i = new GetAllListener();
      this.j = new BulkGetAllListener();
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = null;
      this.o = false;
      this.p = 1;
      this.q = false;
      this.r = true;
      this.s = -1L;
      this.t = -1;
      this.u = false;
      this.v = new SnmpStats();
      this.y = false;
      this.a();
      this.n = var1.getMetadata();
      this.o = var1.isViewFromFirstVarBind();
      this.p = var1.getTimeoutAlgorithm();
      this.t = var1.getMaxPDUVarBinds();
      this.q = var1.isShowSteps();
      this.r = var1.isShutdownPendingRequests();
      this.m = new SnmpSessionImpl(this, var1);
      if (x.isDebugEnabled()) {
         x.debug(a("\u0015\u001b*\u0016\u000e\u0013\rO\u00044;9<2)% \u00009P") + this);
      }

   }

   public Usm getUsm() {
      return this.m.getUsm();
   }

   private void a() {
      if (x == null) {
         x = Logger.getInstance(a("\u0005'\u0002'\t3:\u001c>58"));
      }

      if (SnmpFramework.isStackTraceDebugEnabled() && x.isDetailedEnabled()) {
         x.detailed(a("\\\n=\u0012\u001b\u0002\u0000!\u0010z\u0005'\u0002'\t3:\u001c>58iBzz~:\u001b69=i\u001b%;5,Fww{ie]z"), new Exception(""));
      }

   }

   public void setDefaultTimeoutAlgorithm(int var1) {
      this.p = var1;
   }

   public int getDefaultTimeoutAlgorithm() {
      return this.p;
   }

   public final SnmpPeer getDefaultPeer() {
      return this.l;
   }

   public final void setDefaultPeer(SnmpPeer var1) {
      this.l = var1;
   }

   public void setReceiveBufferSize(int var1) {
      this.m.setReceiveBufferSize(var1);
   }

   public int getReceiveBufferSize() {
      return this.m.getReceiveBufferSize();
   }

   public void setMaxSize(int var1) {
      this.m.setMaxSize(var1);
   }

   public int getMaxSize() {
      return this.m.getMaxSize();
   }

   public boolean isShowSteps() {
      return this.q;
   }

   public void isShowSteps(boolean var1) {
      this.q = var1;
   }

   public final SnmpPendingRequest pollGet(SnmpResponseListener var1, SnmpVarBindList var2, int var3) throws SnmpException {
      return this.pollGet(this.l, var1, var2, var3);
   }

   public final SnmpPendingRequest pollGet(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, int var4) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var5 = new SnmpPendingRequest(this, var1, var2, 160, var3);
         var5.showSteps(this.q);
         var5.setPollingInterval(var4);
         SnmpRequestPDU var6 = new SnmpRequestPDU();
         this.sendPDU(var5, var6, var3);
         return var5;
      }
   }

   public final SnmpPendingRequest pollWalk(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, SnmpOid var4, boolean var5, int var6) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var7;
         label16: {
            var7 = new SnmpPendingRequest(this, var1, var2, 161, var3);
            if (var4 != null) {
               var7.setTerminationOid(var4);
               if (!B) {
                  break label16;
               }
            }

            var7.a(var3);
         }

         var7.showSteps(var5);
         var7.setPollingInterval(var6);
         SnmpRequestPDU var8 = new SnmpRequestPDU();
         this.sendPDU(var7, var8, var3);
         return var7;
      }
   }

   public final SnmpPendingRequest pollGetBulk(SnmpResponseListener var1, SnmpVarBindList var2, int var3, int var4, int var5) throws SnmpException {
      return this.pollGetBulk(this.l, var1, var2, var3, var4, var5);
   }

   public final SnmpPendingRequest pollGetBulk(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, int var4, int var5, int var6) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var7 = new SnmpPendingRequest(this, var1, var2, 165, var3);
         var7.showSteps(this.q);
         var7.setPollingInterval(var6);
         var7.b(var4);
         var7.c(var5);
         SnmpBulkPDU var8 = new SnmpBulkPDU();
         var8.setNonRepeaters(var4);
         var8.setMaxRepetitions(var5);
         this.sendPDU(var7, var8, var3);
         return var7;
      }
   }

   public final SnmpPendingRequest pollGetNext(SnmpResponseListener var1, SnmpVarBindList var2, int var3) throws SnmpException {
      return this.pollGetNext(this.l, var1, var2, var3);
   }

   public final SnmpPendingRequest pollGetNext(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, int var4) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var5 = new SnmpPendingRequest(this, var1, var2, 161, var3);
         var5.showSteps(this.q);
         var5.setPollingInterval(var4);
         SnmpRequestPDU var6 = new SnmpRequestPDU();
         this.sendPDU(var5, var6, var3);
         return var5;
      }
   }

   public final SnmpVarBindList performGet(SnmpVarBindList var1) throws SnmpTimeoutException, SnmpErrorException, SnmpException {
      return this.performGet(this.l, (SnmpResponseListener)null, var1);
   }

   public final SnmpVarBindList performGet(SnmpPeer var1, SnmpVarBindList var2) throws SnmpTimeoutException, SnmpErrorException, SnmpException {
      return this.performGet(var1, (SnmpResponseListener)null, var2);
   }

   public final SnmpVarBindList performGet(SnmpPeer var1, String[] var2) throws SnmpTimeoutException, SnmpErrorException, SnmpException {
      return this.performGet(var1, (SnmpResponseListener)null, new SnmpVarBindList(this.n, var2));
   }

   public final SnmpVarBindList performGet(String[] var1) throws SnmpTimeoutException, SnmpErrorException, SnmpException {
      return this.performGet(this.l, (SnmpResponseListener)null, new SnmpVarBindList(this.n, var1));
   }

   public final SnmpVarBindList performGet(SnmpResponseListener var1, SnmpVarBindList var2) throws SnmpTimeoutException, SnmpErrorException, SnmpException {
      return this.performGet(this.l, var1, var2);
   }

   public final SnmpVarBindList performGet(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var4 = this.startGet(var1, var2, var3);
      var4.expectCompletion();
      if (var4.getErrorStatus() == 0) {
         return var4.getResponseVarBindList();
      } else {
         throw new SnmpErrorException(var4.getErrorStatus(), var4.getErrorIndex(), var4.getResponseVarBindList());
      }
   }

   public final void performDiscovery(SnmpPeer var1, SnmpResponseListener var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var3 = this.startDiscovery(var1, var2);

      try {
         var3.expectCompletion();
      } catch (SnmpReportException var5) {
      }

      if (var3.getErrorStatus() != 0) {
         throw new SnmpErrorException(var3.getErrorStatus(), var3.getErrorIndex(), var3.getResponseVarBindList());
      }
   }

   public final SnmpPendingRequest startGet(SnmpResponseListener var1, SnmpVarBindList var2) throws SnmpException {
      return this.startGet(this.l, var1, var2);
   }

   public final SnmpPendingRequest startGet(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var4 = new SnmpPendingRequest(this, var1, var2, 160, var3);
         var4.showSteps(this.q);
         SnmpRequestPDU var5 = new SnmpRequestPDU();
         this.sendPDU(var4, var5, var3);
         return var4;
      }
   }

   public final SnmpPendingRequest startGet(SnmpParameters var1, SnmpPeer var2, SnmpResponseListener var3, SnmpVarBindList var4) throws SnmpException {
      if (var2 == null) {
         var2 = this.l;
      }

      if (var2 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var5 = new SnmpPendingRequest(this, var2, var3, 160, var4);
         var5.showSteps(this.q);
         SnmpRequestPDU var6 = new SnmpRequestPDU();
         this.sendPDU(var5, var6, var4, var1);
         return var5;
      }
   }

   void a(SnmpPendingRequest var1) throws SnmpException {
      Lock var2;
      if (this.g) {
         var2 = this.b();
         if (!var1.a(var2, this.s)) {
            x.debug(a("5(\u000195\"i\u00005.7 \u0001w)3:\u001c>58i\u000389="));
            throw new SnmpException(a("5(\u000195\"i\u00005.7 \u0001w)3:\u001c>58i\u000389="));
         }

         if (!B) {
            return;
         }
      }

      if (this.f) {
         var2 = var1.getPeer().b();
         if (!var1.a(var2, this.s)) {
            x.debug(a("5(\u000195\"i\u00005.7 \u0001w)3:\u001c>58i\u000389="));
            throw new SnmpException(a("5(\u000195\"i\u00005.7 \u0001w)3:\u001c>58i\u000389="));
         }
      }

   }

   public final SnmpPendingRequest startDiscovery(SnmpPeer var1, SnmpResponseListener var2) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var3 = new SnmpPendingRequest(this, var1, var2, 160, w);
         var3.a(true);
         SnmpRequestPDU var4 = new SnmpRequestPDU();
         this.sendPDU(var3, var4, w);
         return var3;
      }
   }

   public final SnmpVarBindList performGetBulk(SnmpPeer var1, String[] var2, int var3, int var4) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performGetBulk(var1, (SnmpResponseListener)null, new SnmpVarBindList(this.n, var2), var3, var4);
   }

   public final SnmpVarBindList performGetBulk(SnmpPeer var1, SnmpVarBindList var2, int var3, int var4) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performGetBulk(var1, (SnmpResponseListener)null, var2, var3, var4);
   }

   public final SnmpVarBindList performGetBulk(SnmpResponseListener var1, SnmpVarBindList var2, int var3, int var4) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performGetBulk(this.l, var1, var2, var3, var4);
   }

   public final SnmpVarBindList performGetBulk(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, int var4, int var5) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var6 = this.startGetBulk(var1, var2, var3, var4, var5);
      var6.expectCompletion();
      if (var6.getErrorStatus() == 0) {
         return var6.getResponseVarBindList();
      } else {
         throw new SnmpErrorException(var6.getErrorStatus(), var6.getErrorIndex(), var6.getResponseVarBindList());
      }
   }

   public final SnmpPendingRequest startGetBulk(SnmpResponseListener var1, SnmpVarBindList var2, int var3, int var4) throws SnmpException {
      return this.startGetBulk(this.l, var1, var2, var3, var4);
   }

   public final SnmpPendingRequest startGetBulk(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, int var4, int var5) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var6 = new SnmpPendingRequest(this, var1, var2, 165, var3);
         var6.showSteps(this.q);
         var6.b(var4);
         var6.c(var5);
         SnmpBulkPDU var7 = new SnmpBulkPDU();
         var7.setNonRepeaters(var4);
         var7.setMaxRepetitions(var5);
         this.sendPDU(var6, var7, var3);
         return var6;
      }
   }

   public final SnmpPendingRequest startGetBulk(SnmpParameters var1, SnmpPeer var2, SnmpResponseListener var3, SnmpVarBindList var4, int var5, int var6) throws SnmpException {
      if (var2 == null) {
         var2 = this.l;
      }

      if (var2 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var7 = new SnmpPendingRequest(this, var2, var3, 165, var4);
         var7.showSteps(this.q);
         var7.b(var5);
         var7.c(var6);
         SnmpBulkPDU var8 = new SnmpBulkPDU();
         var8.setNonRepeaters(var5);
         var8.setMaxRepetitions(var6);
         this.sendPDU(var7, var8, var4, var1);
         return var7;
      }
   }

   public final SnmpVarBindList performGetNext(SnmpResponseListener var1, SnmpVarBindList var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performGetNext(this.l, var1, var2);
   }

   public final SnmpVarBindList performGetNext(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var4 = this.startGetNext(var1, var2, var3);
      var4.expectCompletion();
      if (var4.getErrorStatus() == 0) {
         return var4.getResponseVarBindList();
      } else {
         throw new SnmpErrorException(var4.getErrorStatus(), var4.getErrorIndex(), var4.getResponseVarBindList());
      }
   }

   public final SnmpVarBindList performGetNext(SnmpPeer var1, SnmpVarBindList var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performGetNext(var1, (SnmpResponseListener)null, var2);
   }

   public final SnmpVarBindList performGetNext(SnmpVarBindList var1) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performGetNext(this.l, (SnmpResponseListener)null, var1);
   }

   public final SnmpVarBindList performGetNext(SnmpPeer var1, String[] var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performGetNext(var1, (SnmpResponseListener)null, new SnmpVarBindList(this.n, var2));
   }

   public final SnmpPendingRequest startGetNext(SnmpResponseListener var1, SnmpVarBindList var2) throws SnmpException {
      return this.startGetNext(this.l, var1, var2);
   }

   public final SnmpPendingRequest startGetNext(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var4 = new SnmpPendingRequest(this, var1, var2, 161, var3);
         var4.showSteps(this.q);
         SnmpRequestPDU var5 = new SnmpRequestPDU();
         this.sendPDU(var4, var5, var3);
         return var4;
      }
   }

   public final SnmpPendingRequest startGetNext(SnmpParameters var1, SnmpPeer var2, SnmpResponseListener var3, SnmpVarBindList var4) throws SnmpException {
      if (var2 == null) {
         var2 = this.l;
      }

      if (var2 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var5 = new SnmpPendingRequest(this, var2, var3, 161, var4);
         var5.showSteps(this.q);
         SnmpRequestPDU var6 = new SnmpRequestPDU();
         this.sendPDU(var5, var6, var4, var1);
         return var5;
      }
   }

   public final SnmpVarBindList performInform(SnmpPeer var1, SnmpVarBindList var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var3 = this.startInform(var1, var2);
      var3.expectCompletion();
      if (var3.getErrorStatus() == 0) {
         return var3.getResponseVarBindList();
      } else {
         throw new SnmpErrorException(var3.getErrorStatus(), var3.getErrorIndex(), var3.getResponseVarBindList());
      }
   }

   public final SnmpVarBindList performInform(SnmpVarBindList var1) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var2 = this.startInform(this.l, var1);
      var2.expectCompletion();
      if (var2.getErrorStatus() == 0) {
         return var2.getResponseVarBindList();
      } else {
         throw new SnmpErrorException(var2.getErrorStatus(), var2.getErrorIndex(), var2.getResponseVarBindList());
      }
   }

   public final SnmpPendingRequest startInform(SnmpPeer var1, SnmpVarBindList var2) throws SnmpException {
      return this.startInform(var1, (SnmpResponseListener)null, var2);
   }

   public final SnmpPendingRequest startInform(SnmpVarBindList var1) throws SnmpException {
      return this.startInform(this.l, (SnmpResponseListener)null, var1);
   }

   public final SnmpPendingRequest startInform(SnmpResponseListener var1, SnmpVarBindList var2) throws SnmpException {
      return this.startInform(this.l, var1, var2);
   }

   public final SnmpPendingRequest startInform(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var4 = new SnmpPendingRequest(this, var1, var2, 166, var3);
         var4.showSteps(this.q);
         SnmpRequestPDU var5 = new SnmpRequestPDU();
         this.sendPDU(var4, var5, var3);
         return var4;
      }
   }

   public final SnmpPendingRequest startInform(SnmpParameters var1, SnmpPeer var2, SnmpResponseListener var3, SnmpVarBindList var4) throws SnmpException {
      if (var2 == null) {
         var2 = this.l;
      }

      if (var2 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var5 = new SnmpPendingRequest(this, var2, var3, 166, var4);
         var5.showSteps(this.q);
         SnmpRequestPDU var6 = new SnmpRequestPDU();
         this.sendPDU(var5, var6, var4, var1);
         return var5;
      }
   }

   public final SnmpVarBindList performSet(SnmpResponseListener var1, SnmpVarBindList var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performSet(this.l, var1, var2);
   }

   public final SnmpVarBindList performSet(SnmpVarBindList var1) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performSet(this.l, (SnmpResponseListener)null, var1);
   }

   public final SnmpVarBindList performSet(SnmpPeer var1, SnmpVarBindList var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performSet(var1, (SnmpResponseListener)null, var2);
   }

   public final SnmpVarBindList performSet(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var4 = this.startSet(var1, var2, var3);
      var4.expectCompletion();
      if (var4.getErrorStatus() == 0) {
         return var4.getResponseVarBindList();
      } else {
         throw new SnmpErrorException(var4.getErrorStatus(), var4.getErrorIndex(), var4.getResponseVarBindList());
      }
   }

   public final SnmpPendingRequest startSet(SnmpPeer var1, SnmpVarBindList var2) throws SnmpException {
      return this.startSet(var1, (SnmpResponseListener)null, var2);
   }

   public final SnmpPendingRequest startSet(SnmpResponseListener var1, SnmpVarBindList var2) throws SnmpException {
      return this.startSet(this.l, var1, var2);
   }

   public final SnmpPendingRequest startSet(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var4 = new SnmpPendingRequest(this, var1, var2, 163, var3);
         var4.showSteps(this.q);
         SnmpRequestPDU var5 = new SnmpRequestPDU();
         this.sendPDU(var4, var5, var3);
         return var4;
      }
   }

   public final SnmpPendingRequest startSet(SnmpParameters var1, SnmpPeer var2, SnmpResponseListener var3, SnmpVarBindList var4) throws SnmpException {
      if (var2 == null) {
         var2 = this.l;
      }

      if (var2 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var5 = new SnmpPendingRequest(this, var2, var3, 163, var4);
         var5.showSteps(this.q);
         SnmpRequestPDU var6 = new SnmpRequestPDU();
         this.sendPDU(var5, var6, var4, var1);
         return var5;
      }
   }

   public final SnmpPendingRequest startGetAllInstances(SnmpVarBindList var1) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.startGetAllInstances(this.l, var1);
   }

   public final SnmpPendingRequest startGetAllInstances(SnmpPeer var1, SnmpVarBindList var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      if (var2.size() == 0) {
         throw new SnmpException(a("\u0013$\u001f##v\u001f\u000e%\u0018?'\u000b\u001b3%=O95\"i\u000e;69>\n3t"));
      } else {
         SnmpPendingRequest var3 = this.startWalk(var1, this.i, var2, var2.get(0).getOid(), true);
         return var3;
      }
   }

   public final SnmpVarBindList[] performGetAllInstances(SnmpVarBindList var1) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performGetAllInstances(this.l, var1);
   }

   public final SnmpVarBindList[] performGetAllInstances(SnmpPeer var1, SnmpVarBindList var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var3 = this.startGetAllInstances(var1, var2);
      var3.expectCompletion();
      if (var3.getErrorStatus() != 0 && var3.getErrorStatus() != 2) {
         throw new SnmpErrorException(var3.getErrorStatus(), var3.getErrorIndex(), var3.getResponseVarBindList());
      } else {
         return var3.getAllInstances();
      }
   }

   public final SnmpPendingRequest startBulkGetAllInstances(SnmpVarBindList var1, int var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.startBulkGetAllInstances(this.l, var1, var2);
   }

   public final SnmpPendingRequest startBulkGetAllInstances(SnmpPeer var1, SnmpVarBindList var2, int var3) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      if (var2.size() == 0) {
         throw new SnmpException(a("\u0013$\u001f##v\u001f\u000e%\u0018?'\u000b\u001b3%=O95\"i\u000e;69>\n3t"));
      } else {
         SnmpPendingRequest var4 = this.startBulkWalk(var1, this.j, var2, var2.get(0).getOid(), var3, true);
         return var4;
      }
   }

   public final SnmpVarBindList[] performBulkGetAllInstances(SnmpVarBindList var1, int var2) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performBulkGetAllInstances(this.l, var1, var2);
   }

   public final SnmpVarBindList[] performBulkGetAllInstances(SnmpPeer var1, SnmpVarBindList var2, int var3) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var4 = this.startBulkGetAllInstances(var1, var2, var3);
      var4.expectCompletion();
      if (var4.getErrorStatus() != 0 && var4.getErrorStatus() != 2) {
         throw new SnmpErrorException(var4.getErrorStatus(), var4.getErrorIndex(), var4.getResponseVarBindList());
      } else {
         return var4.getAllInstances();
      }
   }

   public final SnmpVarBindList performWalk(SnmpPeer var1, String var2, SnmpResponseListener var3) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpVarBindList var4 = new SnmpVarBindList(this.n);
      var4.add(var2);
      return this.performWalk(var1, var3, var4, new SnmpOid(this.n, var2), var3 != null);
   }

   public final SnmpVarBindList performWalk(SnmpPeer var1, SnmpOid var2, SnmpResponseListener var3) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpVarBindList var4 = new SnmpVarBindList(this.n);
      var4.add(var2);
      return this.performWalk(var1, var3, var4, var2, var3 != null);
   }

   public final SnmpVarBindList performWalk(SnmpResponseListener var1, SnmpVarBindList var2, SnmpOid var3, boolean var4) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performWalk(this.l, var1, var2, var3, var4);
   }

   public final SnmpVarBindList performWalk(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, SnmpOid var4, boolean var5) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var6 = this.startWalk(var1, var2, var3, var4, var5);
      var6.expectCompletion();
      if (var6.getErrorStatus() != 0 && var6.getErrorStatus() != 2) {
         throw new SnmpErrorException(var6.getErrorStatus(), var6.getErrorIndex(), var6.getResponseVarBindList());
      } else {
         return var6.getResponseVarBindList();
      }
   }

   public final SnmpPendingRequest startWalk(SnmpResponseListener var1, SnmpVarBindList var2, SnmpOid var3, boolean var4) throws SnmpException {
      return this.startWalk(this.l, var1, var2, var3, var4);
   }

   public final SnmpPendingRequest startWalk(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, SnmpOid var4, boolean var5) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var6;
         label16: {
            var6 = new SnmpPendingRequest(this, var1, var2, 161, var3);
            if (var4 != null) {
               var6.setTerminationOid(var4);
               if (!B) {
                  break label16;
               }
            }

            var6.a(var3);
         }

         var6.showSteps(var5);
         SnmpRequestPDU var7 = new SnmpRequestPDU();
         this.sendPDU(var6, var7, var3);
         return var6;
      }
   }

   public final SnmpPendingRequest startWalk(SnmpParameters var1, SnmpPeer var2, SnmpResponseListener var3, SnmpVarBindList var4, SnmpOid var5, boolean var6) throws SnmpException {
      if (var2 == null) {
         var2 = this.l;
      }

      if (var2 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var7;
         label16: {
            var7 = new SnmpPendingRequest(this, var2, var3, 161, var4);
            if (var5 != null) {
               var7.setTerminationOid(var5);
               if (!B) {
                  break label16;
               }
            }

            var7.a(var4);
         }

         var7.showSteps(var6);
         SnmpRequestPDU var8 = new SnmpRequestPDU();
         this.sendPDU(var7, var8, var4, var1);
         return var7;
      }
   }

   protected void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3) throws SnmpException {
      boolean var5 = B;
      boolean var4 = true;
      byte var6;
      switch (var2.getType()) {
         case 163:
            var6 = 2;
            if (!var5) {
               break;
            }
         case 164:
         case 166:
         case 167:
            var6 = 4;
            if (!var5) {
               break;
            }
         case 165:
         default:
            var6 = 1;
      }

      this.sendPDU(var1, var2, var3, var6);
   }

   public final SnmpVarBindList performBulkWalk(SnmpPeer var1, String var2, SnmpResponseListener var3, int var4) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpVarBindList var5 = new SnmpVarBindList(this.n);
      var5.add(var2);
      return this.performBulkWalk(var1, var3, var5, new SnmpOid(this.n, var2), var4, var3 != null);
   }

   public final SnmpVarBindList performBulkWalk(SnmpPeer var1, SnmpOid var2, SnmpResponseListener var3, int var4) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpVarBindList var5 = new SnmpVarBindList(this.n);
      var5.add(var2);
      return this.performBulkWalk(var1, var3, var5, var2, var4, var3 != null);
   }

   public final SnmpVarBindList performBulkWalk(SnmpResponseListener var1, SnmpVarBindList var2, SnmpOid var3, int var4, boolean var5) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      return this.performBulkWalk(this.l, var1, var2, var3, var4, var5);
   }

   public final SnmpVarBindList performBulkWalk(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, SnmpOid var4, int var5, boolean var6) throws SnmpErrorException, SnmpTimeoutException, SnmpException {
      SnmpPendingRequest var7 = this.startBulkWalk(var1, var2, var3, var4, var5, var6);
      var7.expectCompletion();
      if (var7.getErrorStatus() != 0 && var7.getErrorStatus() != 2) {
         throw new SnmpErrorException(var7.getErrorStatus(), var7.getErrorIndex(), var7.getResponseVarBindList());
      } else {
         return var7.getResponseVarBindList();
      }
   }

   public final SnmpPendingRequest startBulkWalk(SnmpResponseListener var1, SnmpVarBindList var2, SnmpOid var3, int var4, boolean var5) throws SnmpException {
      return this.startBulkWalk(this.l, var1, var2, var3, var4, var5);
   }

   public final SnmpPendingRequest startBulkWalk(SnmpPeer var1, SnmpResponseListener var2, SnmpVarBindList var3, SnmpOid var4, int var5, boolean var6) throws SnmpException {
      if (var1 == null) {
         var1 = this.l;
      }

      if (var1 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var7;
         label16: {
            var7 = new SnmpPendingRequest(this, var1, var2, 165, var3);
            if (var4 != null) {
               var7.setTerminationOid(var4);
               if (!B) {
                  break label16;
               }
            }

            var7.a(var3);
         }

         var7.showSteps(var6);
         var7.b(0);
         var7.c(var5);
         SnmpBulkPDU var8 = new SnmpBulkPDU();
         var8.setNonRepeaters(0);
         var8.setMaxRepetitions(var5);
         this.sendPDU(var7, var8, var3);
         return var7;
      }
   }

   public final SnmpPendingRequest startBulkWalk(SnmpParameters var1, SnmpPeer var2, SnmpResponseListener var3, SnmpVarBindList var4, SnmpOid var5, int var6, boolean var7) throws SnmpException {
      if (var2 == null) {
         var2 = this.l;
      }

      if (var2 == null) {
         throw new SnmpException(a("\u0018&O\u0007?3;O\u0004*3*\u0006133-"));
      } else {
         SnmpPendingRequest var8;
         label16: {
            var8 = new SnmpPendingRequest(this, var2, var3, 165, var4);
            if (var5 != null) {
               var8.setTerminationOid(var5);
               if (!B) {
                  break label16;
               }
            }

            var8.a(var4);
         }

         var8.showSteps(var7);
         var8.b(0);
         var8.c(var6);
         SnmpBulkPDU var9 = new SnmpBulkPDU();
         var9.setNonRepeaters(0);
         var9.setMaxRepetitions(var6);
         this.sendPDU(var8, var9, var4, var1);
         return var8;
      }
   }

   protected void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, int var4) throws SnmpException {
      SnmpParameters var5 = null;
      if (this.o && var3.size() > 0) {
         var5 = var1.getPeer().getParameters(var3.get(0).getOid(), var4);
      } else {
         var5 = var1.getPeer().getParameters(var3, var4);
      }

      this.sendPDU(var1, var2, var3, var5);
   }

   protected void sendPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, SnmpParameters var4) throws SnmpException {
      this.m.sendPDU(var1, var2, var3, var4);
   }

   protected void sendPollPDU(SnmpPendingRequest var1, SnmpPDU var2, SnmpVarBindList var3, SnmpParameters var4) throws SnmpException {
      this.m.sendPollPDU(var1, var2, var3, var4);
   }

   protected void retryPDU(SnmpPendingRequest var1) throws SnmpException {
      this.m.retryPDU(var1);
   }

   protected void retryPDU(SnmpPendingRequest var1, boolean var2) throws SnmpException {
      this.m.retryPDU(var1, var2);
   }

   public void cancel(SnmpPendingRequest var1) {
      this.m.cancel(var1);
   }

   void a(SnmpPendingRequest var1, boolean var2) {
      this.m.cancel(var1, var2);
   }

   public SnmpMetadata getMetadata() {
      return this.n;
   }

   public Properties getUserProperties() {
      if (this.k == null) {
         this.k = new Properties();
      }

      return this.k;
   }

   public void shutdown() {
      if (x.isDetailedEnabled()) {
         x.detailed(a("\u0005'\u0002'\t3:\u001c>58i<\u001f\u000f\u0002\r \u0000\u0014li") + this.c);
      }

      this.m.shutdown();
   }

   public void addInformListener(SnmpInformListener var1) {
      this.a = monfox.toolkit.snmp.mgr.i.add(this.a, var1);
   }

   public void removeInformListener(SnmpInformListener var1) {
      this.a = monfox.toolkit.snmp.mgr.i.remove(this.a, var1);
   }

   public boolean containsInformListener(SnmpInformListener var1) {
      return monfox.toolkit.snmp.mgr.i.contains(this.a, var1);
   }

   void a(SnmpPendingInform var1) {
      SnmpInformListener var2 = this.a;
      if (var2 != null) {
         try {
            var2.handleInform(var1);
            return;
         } catch (Exception var4) {
            SnmpFramework.handleException(this, var4);
            if (!B) {
               return;
            }
         }
      }

      x.warn(a("\u0018&O\u001e40&\u001d:z\u001a \u001c#?8,\u001dw\b3.\u0006$.3;\n3"));
   }

   public void addTrapListener(SnmpTrapListener var1) {
      synchronized(this.e) {
         this.b = monfox.toolkit.snmp.mgr.m.add(this.b, var1);
      }
   }

   public void removeTrapListener(SnmpTrapListener var1) {
      synchronized(this.e) {
         this.b = monfox.toolkit.snmp.mgr.m.remove(this.b, var1);
      }
   }

   public boolean containsTrapListener(SnmpTrapListener var1) {
      synchronized(this.e) {
         return monfox.toolkit.snmp.mgr.m.contains(this.b, var1);
      }
   }

   public SnmpEngine getSnmpEngine() {
      return this.m.getSnmpEngine();
   }

   void a(SnmpTrap var1) {
      SnmpTrapListener var2 = this.b;
      if (x.isDebugEnabled()) {
         x.debug(a("0 \u001d2\u000e$(\u001f\u001b3%=\n9?$:Uw") + var1);
      }

      if (var2 != null) {
         try {
            var2.handleTrap(var1);
            return;
         } catch (Exception var4) {
            SnmpFramework.handleException(this, var4);
            if (!B) {
               return;
            }
         }
      }

      x.warn(a("\u0018&O\u0003(79O\u001b3%=\n9?$i=2=?:\u001b2(3-"));
   }

   public static void setDefaultConfig(SnmpSessionConfig var0) {
      z = var0;
   }

   public static SnmpSessionConfig newConfig() {
      return (SnmpSessionConfig)getDefaultConfig().clone();
   }

   public static SnmpSessionConfig getDefaultConfig() {
      if (z == null) {
         z = new SnmpSessionConfig();
      }

      return z;
   }

   public void setMaxPDUVarBinds(int var1) {
      this.t = var1;
   }

   public int getMaxPDUVarBinds() {
      return this.t;
   }

   public SnmpStats getStats() {
      return this.v;
   }

   public void isCollectingStats(boolean var1) {
      this.u = var1;
   }

   public boolean isCollectingStats() {
      return this.u;
   }

   public boolean isSerializeOnSession() {
      return this.g;
   }

   public void isSerializeOnSession(boolean var1) {
      this.g = var1;
   }

   public boolean isSerializeOnPeer() {
      return this.f;
   }

   public void isSerializeOnPeer(boolean var1) {
      this.f = var1;
   }

   public void setSerializeTimeout(long var1) {
      this.s = var1;
   }

   public long getSerializeTimeout() {
      return this.s;
   }

   public void isReportingAllErrors(boolean var1) {
      this.y = var1;
   }

   public boolean isReportingAllErrors() {
      return this.y;
   }

   public void isShutdownPendingRequests(boolean var1) {
      this.r = var1;
   }

   public boolean isShutdownPendingRequests() {
      return this.r;
   }

   void b(SnmpPendingRequest var1) {
      this.m.restartPolling(var1);
   }

   void c(SnmpPendingRequest var1) {
      this.m.pausePolling(var1);
   }

   public void setParameter(String var1, int var2) {
      this.setParameter(var1, new Integer(var2));
   }

   public void setParameter(String var1, Object var2) {
      this.m.setParameter(var1, var2);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\\dBidv\u0012O\u00044;9<2)% \u00009`v")).append(this.c).append(a("v\u0014Bz"));
      var1.append("\n");
      var1.append(a("\\iOwzviO#3;,\u0000\".\u0017%\b8(?=\u0007:zviOwzviOw`v")).append(this.p);
      var1.append(a("\\iOwzviO>)\u0005!\u0000 \t\",\u001f$zviOwzviOwzviOw`v")).append(this.q);
      var1.append(a("\\iOwzviO>)\u0005!\u001a#>9>\u0001\u0007?8-\u00069=\u0004,\u001e\"?%=\u001cw`v")).append(this.r);
      var1.append(a("\\iOwzviO:;.\u0019+\u0002\f7;->42:OwzviOwzviOw`v")).append(this.t);
      var1.append(a("\\iOwzviO>)\u0015&\u0003;?5=\u00069=\u0005=\u000e#)viOwzviOw`v")).append(this.u);
      var1.append(a("\\iOwzviO!33>)%5;\u000f\u0006%)\"\u001f\u000e%\u0018?'\u000bwzviOw`v")).append(this.o);
      var1.append(a("\\iOwzviO>)\u0005,\u001d>;: \u00152\u00158\u0019\n2(viOwzviOw`v")).append(this.f);
      var1.append(a("\\iOwzviO>)\u0005,\u001d>;: \u00152\u00158\u001a\n$)?&\u0001wzviOw`v")).append(this.g);
      var1.append(a("\\dBkfv\u0012O\u00044;9<2)% \u00009`v")).append(this.c).append(a("v\u0014Bz"));
      return var1.toString();
   }

   private Lock b() {
      return this.h;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 86;
               break;
            case 1:
               var10003 = 73;
               break;
            case 2:
               var10003 = 111;
               break;
            case 3:
               var10003 = 87;
               break;
            default:
               var10003 = 90;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class BulkGetAllListener implements SnmpResponseListener {
      private BulkGetAllListener() {
      }

      public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
         boolean var13 = SnmpSession.B;
         if (!var1.isCompleted()) {
            int var5 = var1.getRequestVarBindList().size();
            if (var4.size() > 0 && var5 > 0) {
               int var6 = var4.size() / var5;
               int var7 = 0;

               while(var7 < var6) {
                  if (var4 != null && var4.size() > 0) {
                     try {
                        label61: {
                           SnmpOid var8 = var1.getTerminationOid();
                           SnmpVarBind var9 = var4.get(var7 * var5);
                           SnmpValue var10 = var9.getValue();
                           if (var2 == 0 && (var9.getOid().compareTo(var8) < 0 || var8.contains(var9.getOid()) && var9.getOid().compareTo(var8) != 0) && var10 != null && var10.getTag() != 130) {
                              SnmpVarBindList var11 = new SnmpVarBindList(var4.getMetadata());
                              int var12 = 0;

                              while(true) {
                                 if (var12 < var5) {
                                    var11.add(var4.get(var7 * var5 + var12));
                                    ++var12;
                                    if (!var13 || !var13) {
                                       continue;
                                    }
                                    break;
                                 }

                                 var1.c(var11);
                                 break;
                              }

                              if (!var13) {
                                 break label61;
                              }
                           }

                           var1.c((SnmpVarBindList)null);
                           break;
                        }
                     } catch (Exception var14) {
                        SnmpSession.x.error(a("HC\"Y\\cj?kDjH3j\u001egG8|\\jt3k@`H%}\u0010j^5}@{O9v"), var14);
                     }
                  }

                  ++var7;
                  if (var13) {
                     break;
                  }
               }
            }
         }

      }

      public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      }

      public void handleTimeout(SnmpPendingRequest var1) {
      }

      public void handleException(SnmpPendingRequest var1, Exception var2) {
      }

      // $FF: synthetic method
      BulkGetAllListener(Object var2) {
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
                  var10003 = 15;
                  break;
               case 1:
                  var10003 = 38;
                  break;
               case 2:
                  var10003 = 86;
                  break;
               case 3:
                  var10003 = 24;
                  break;
               default:
                  var10003 = 48;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class GetAllListener implements SnmpResponseListener {
      private GetAllListener() {
      }

      public void handleResponse(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
         if (!var1.isCompleted() && var4 != null && var4.size() > 0) {
            try {
               SnmpOid var5 = var1.getTerminationOid();
               SnmpVarBind var6 = var4.get(0);
               SnmpValue var7 = var6.getValue();
               if (var2 == 0 && (var6.getOid().compareTo(var5) < 0 || var5.contains(var6.getOid()) && var6.getOid().compareTo(var5) != 0) && var7 != null && var7.getTag() != 130) {
                  var1.c(new SnmpVarBindList(var4, true, false));
                  if (!SnmpSession.B) {
                     return;
                  }
               }

               var1.c((SnmpVarBindList)null);
            } catch (Exception var8) {
               SnmpSession.x.error(a("3\u007f2*L\u0018V/\u0018T\u0011t#\u0019\u000e\u001c{(\u000fL\u0011H#\u0018P\u001bt5\u000e\u0000\u0011b%\u000eP\u0000s)\u0005"), var8);
            }
         }

      }

      public void handleReport(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      }

      public void handleTimeout(SnmpPendingRequest var1) {
      }

      public void handleException(SnmpPendingRequest var1, Exception var2) {
      }

      // $FF: synthetic method
      GetAllListener(Object var2) {
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
                  var10003 = 116;
                  break;
               case 1:
                  var10003 = 26;
                  break;
               case 2:
                  var10003 = 70;
                  break;
               case 3:
                  var10003 = 107;
                  break;
               default:
                  var10003 = 32;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
