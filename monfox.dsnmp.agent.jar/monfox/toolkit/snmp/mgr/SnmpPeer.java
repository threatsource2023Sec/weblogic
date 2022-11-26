package monfox.toolkit.snmp.mgr;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpRuntimeException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpOidMap;
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.util.Lock;

public class SnmpPeer implements Serializable {
   public static final int DEFAULT_PORT = 161;
   private Lock a;
   private boolean b;
   private SnmpEngineID c;
   private TransportEntity d;
   private int e;
   private SnmpOid f;
   private InetAddress g;
   private int h;
   private int i;
   private List j;
   private long k;
   private long l;
   private long m;
   private int n;
   private static int o = -1;
   private int p;
   private int q;
   private int r;
   private SnmpEngineID s;
   private SnmpContext t;
   private boolean u;
   private SnmpStats v;
   private boolean w;
   public static int _DefaultMaxRetries = 3;
   public static long _DefaultTimeout = 5000L;
   private SnmpOidMap x;
   private SnmpParameters y;
   private static final String z = "$Id: SnmpPeer.java,v 1.34 2011/01/26 21:26:39 sking Exp $";
   private static Logger A = Logger.getInstance(a("V@\u0006>|"), a("_T\u001a"), a("A}%\u0003|wv:"));

   public SnmpPeer(String var1, int var2, String var3, int var4) throws UnknownHostException, SnmpRuntimeException {
      this((InetAddress)InetAddress.getByName(var1), var2, 1);
      this.setParameters(var4, var3);
   }

   public SnmpPeer(String var1) throws UnknownHostException, SnmpRuntimeException {
      this((InetAddress)InetAddress.getByName(var1), 161, 1);
   }

   public SnmpPeer(String var1, int var2) throws UnknownHostException, SnmpRuntimeException {
      this((InetAddress)InetAddress.getByName(var1), var2, 1);
   }

   public SnmpPeer(InetAddress var1, int var2) throws SnmpRuntimeException {
      this((InetAddress)var1, var2, 1);
   }

   public SnmpPeer(InetAddress var1, int var2, int var3) throws SnmpRuntimeException {
      this.a = new Lock();
      this.b = false;
      this.c = null;
      this.d = null;
      this.e = 1;
      this.f = Snmp.snmpUDPDomain;
      this.g = null;
      this.h = 161;
      this.i = -1;
      this.j = null;
      this.k = _DefaultTimeout;
      this.l = -1L;
      this.m = -1L;
      this.n = -1;
      this.p = _DefaultMaxRetries;
      this.q = -1;
      this.r = -1;
      this.s = null;
      this.t = null;
      this.u = false;
      this.v = new SnmpStats();
      this.w = false;
      this.x = null;
      this.y = new SnmpParameters();
      this.g = var1;
      this.h = var2;
      this.d = TransportEntity.newInstance(var3, this.getAddress(), this.getPort());
      this.e = var3;
      this.f = this.d.getTransportDomain();
   }

   public SnmpPeer(InetAddress var1, int var2, SnmpOid var3) throws SnmpRuntimeException {
      this.a = new Lock();
      this.b = false;
      this.c = null;
      this.d = null;
      this.e = 1;
      this.f = Snmp.snmpUDPDomain;
      this.g = null;
      this.h = 161;
      this.i = -1;
      this.j = null;
      this.k = _DefaultTimeout;
      this.l = -1L;
      this.m = -1L;
      this.n = -1;
      this.p = _DefaultMaxRetries;
      this.q = -1;
      this.r = -1;
      this.s = null;
      this.t = null;
      this.u = false;
      this.v = new SnmpStats();
      this.w = false;
      this.x = null;
      this.y = new SnmpParameters();
      this.g = var1;
      this.h = var2;
      this.d = TransportEntity.newInstance(var3, this.getAddress(), this.getPort());
      this.e = this.d.getTransportType();
      this.f = var3;
   }

   public SnmpPeer(String var1, int var2, int var3) throws SnmpRuntimeException, UnknownHostException {
      this(InetAddress.getByName(var1), var2, var3);
   }

   public SnmpPeer(String var1, int var2, SnmpOid var3) throws SnmpRuntimeException, UnknownHostException {
      this(InetAddress.getByName(var1), var2, var3);
   }

   public boolean equals(Object var1) {
      return this == var1;
   }

   public final InetAddress getAddress() {
      return this.g;
   }

   public final int getPort() {
      return this.h;
   }

   public final String getHostName() {
      return this.getAddress().getHostName();
   }

   public final int getMaxRetries() {
      return this.p;
   }

   public final SnmpParameters getParameters() {
      return this.y;
   }

   public String getReadName() {
      try {
         return this.y.getReadProfile().getSecurityName();
      } catch (Exception var2) {
         return null;
      }
   }

   public String getWriteName() {
      try {
         return this.y.getWriteProfile().getSecurityName();
      } catch (Exception var2) {
         return null;
      }
   }

   public String getInformName() {
      try {
         return this.y.getInformProfile().getSecurityName();
      } catch (Exception var2) {
         return null;
      }
   }

   public final long getTimeout() {
      return this.k;
   }

   public final long getAdaptiveTimeout() {
      if (this.l < this.k) {
         this.a();
         if (!SnmpSession.B) {
            return this.l;
         }
      }

      if (this.m > 0L && this.l > this.m) {
         this.l = this.m;
      }

      return this.l;
   }

   public void setAdaptiveTimeout(long var1) {
      this.l = var1;
   }

   private void a() {
      boolean var5 = SnmpSession.B;
      if (this.l < this.k) {
         if (this.r == 4) {
            long var1 = this.k;
            if (var1 <= 0L) {
               var1 = _DefaultTimeout;
            }

            long var3 = this.m;
            if (var3 < var1) {
               var3 = var1 * (long)(this.p + 1);
            }

            label24: {
               if (this.l <= 0L) {
                  this.l = (var3 + var1) / 2L;
                  if (!var5) {
                     break label24;
                  }
               }

               this.l = this.k;
            }

            if (!var5) {
               return;
            }
         }

         this.l = this.k;
      }
   }

   public final long getMaxTimeout() {
      return this.m;
   }

   public void setMaxTimeout(long var1) {
      this.m = var1;
   }

   /** @deprecated */
   public final synchronized SnmpPeer setPort(int var1) {
      this.h = var1;
      return this;
   }

   public final synchronized SnmpPeer setMaxRetries(int var1) {
      this.p = var1;
      return this;
   }

   public final synchronized SnmpPeer setParameters(SnmpParameters var1) {
      this.y = var1;
      return this;
   }

   public final synchronized SnmpPeer setParameters(int var1, String var2) {
      this.setParameters(var1, var2, var2, var2);
      return this;
   }

   public final synchronized SnmpPeer setParameters(int var1, String var2, String var3, String var4) {
      this.y = new SnmpParameters(var2, var3, var4);
      this.y.setVersion(var1);
      return this;
   }

   public final synchronized SnmpPeer setTimeout(long var1) {
      this.k = var1;
      return this;
   }

   public void setTimeoutAlgorithm(int var1) {
      this.r = var1;
   }

   public int getTimeoutAlgorithm() {
      return this.r;
   }

   public final synchronized SnmpPeer addView(SnmpPeerView var1) {
      if (this.j == null) {
         this.j = new Vector();
      }

      this.j.add(var1);
      return this;
   }

   public final synchronized void removeView(SnmpPeerView var1) {
      this.j.remove(var1);
   }

   public final synchronized List getViews() {
      return this.j;
   }

   public final synchronized SnmpParameters getParameters(SnmpOid var1, int var2) {
      SnmpPeerView var3 = this.getView(var1, var2);
      return var3 != null ? var3.getParameters() : this.y;
   }

   public final synchronized SnmpParameters getParameters(SnmpVarBindList var1, int var2) {
      SnmpPeerView var3 = this.getView(var1, var2);
      return var3 != null ? var3.getParameters() : this.y;
   }

   public final synchronized SnmpPeerView getView(SnmpVarBindList var1) {
      return this.getView((SnmpVarBindList)var1, 7);
   }

   public final synchronized SnmpPeerView getView(SnmpVarBindList var1, int var2) {
      if (this.j == null) {
         return null;
      } else {
         ListIterator var3 = this.j.listIterator();

         while(var3.hasNext()) {
            SnmpPeerView var4 = (SnmpPeerView)var3.next();
            if (var4.isInView(var1, var2)) {
               return var4;
            }

            if (SnmpSession.B) {
               break;
            }
         }

         return null;
      }
   }

   public final synchronized SnmpPeerView getView(SnmpOid var1) {
      return this.getView((SnmpOid)var1, 7);
   }

   public final synchronized SnmpPeerView getView(SnmpOid var1, int var2) {
      if (this.j == null) {
         return null;
      } else {
         ListIterator var3 = this.j.listIterator();

         while(var3.hasNext()) {
            SnmpPeerView var4 = (SnmpPeerView)var3.next();
            if (var4.isInView(var1, var2)) {
               return var4;
            }

            if (SnmpSession.B) {
               break;
            }
         }

         return null;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if (this.g != null) {
         var1.append(a("A}%\u0003|wv:IWsw,\u0001Ia`u"));
      }

      label14: {
         if (this.g != null) {
            var1.append(this.g.getHostAddress());
            if (!SnmpSession.B) {
               break label14;
            }
         }

         var1.append(a(".}'^Mvw:\u0016_a-"));
      }

      var1.append(":").append(this.h);
      var1.append(a(">a-\u0007^{v;N")).append(this.p);
      var1.append(a(">g!\u001eI}f<N")).append(this.getTimeout());
      var1.append(a(">r,\u0012\\fz>\u0016x{~-\u001cYf.")).append(this.getAdaptiveTimeout());
      var1.append(a(">~)\u000bx{~-\u001cYf.")).append(this.getMaxTimeout());
      var1.append(a(">|!\u0017\u0001\u007fr8N")).append(this.getOidMap());
      var1.append("}");
      return var1.toString();
   }

   public final synchronized void useIPAddress(String var1) throws UnknownHostException {
      this.g = InetAddress.getByName(var1);
   }

   public static void setDefaultMaxRetries(int var0) {
      _DefaultMaxRetries = var0;
   }

   public static int getDefaultMaxRetries() {
      return _DefaultMaxRetries;
   }

   public static void setDefaultTimeout(long var0) {
      _DefaultTimeout = var0;
   }

   public static long getDefaultTimeout() {
      return _DefaultTimeout;
   }

   public SnmpEngineID getSnmpEngineID() {
      return this.s;
   }

   public void setSnmpEngineID(SnmpEngineID var1) {
      this.s = var1;
   }

   public SnmpContext getDefaultContext() {
      return this.t;
   }

   public void setDefaultContext(SnmpContext var1) {
      this.t = var1;
   }

   void a(TransportEntity var1) {
      if (var1 != null) {
         this.d = var1;
         this.e = var1.getTransportType();
         this.f = var1.getTransportDomain();
      }

   }

   public SnmpOid getTransportDomain() {
      return this.f;
   }

   public TransportEntity getTransportEntity() {
      return this.d;
   }

   public SnmpPeer setMaxSize(int var1) {
      this.q = var1;
      if (this.d != null) {
         this.d.setMaxSize(var1);
      }

      return this;
   }

   public int getMaxSize() {
      return this.q;
   }

   public void setMaxPDUVarBinds(int var1) {
      this.i = var1;
   }

   public int getMaxPDUVarBinds() {
      return this.i;
   }

   public void reset() {
      this.c = this.s;
      this.s = null;
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

   public void setAutoTimeResyncThreshold(int var1) {
      this.n = var1;
   }

   public int getAutoTimeResyncThreshold() {
      return this.n;
   }

   public static void setDefaultAutoTimeResyncThreshold(int var0) {
      o = var0;
   }

   public static int getDefaultAutoTimeResyncThreshold() {
      return o;
   }

   public boolean isPassThrough() {
      return this.w;
   }

   public void isPassThrough(boolean var1) {
      this.w = var1;
   }

   public SnmpPeer createFromPassThrough(SnmpMessage var1) {
      if (A.isDebugEnabled()) {
         A.debug(a("qa-\u0012XwU:\u001cABr;\u0000xza'\u0006Kz;") + this + ")");
      }

      SnmpPeer var2 = new SnmpPeer(this.getAddress(), this.getPort(), this.getTransportDomain());
      var2.setTimeoutAlgorithm(this.getTimeoutAlgorithm());
      var2.setTimeout(this.getTimeout());
      var2.setMaxRetries(this.getMaxRetries());
      var2.setDefaultContext(this.getDefaultContext());
      SnmpSecurityParameters var3 = var1.getSecurityParameters();
      SnmpParameters var4 = new SnmpParameters(var1.getVersion(), var3.getSecurityModel(), var3.getSecurityLevel(), new String(var3.getSecurityName()));
      var2.setParameters(var4);
      return var2;
   }

   public boolean isCheckIncreasingOids() {
      return this.b;
   }

   public void isCheckIncreasingOids(boolean var1) {
      this.b = var1;
   }

   public void setOidMap(SnmpOidMap var1) {
      this.x = var1;
   }

   public SnmpOidMap getOidMap() {
      return this.x;
   }

   Lock b() {
      return this.a;
   }

   SnmpEngineID c() {
      return this.c;
   }

   void d() {
      this.c = null;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 18;
               break;
            case 1:
               var10003 = 19;
               break;
            case 2:
               var10003 = 72;
               break;
            case 3:
               var10003 = 115;
               break;
            default:
               var10003 = 44;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
