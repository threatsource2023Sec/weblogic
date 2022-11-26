package monfox.toolkit.snmp.mgr;

import java.io.Serializable;
import java.net.InetAddress;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.engine.BufferedTransportProvider;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class SnmpSessionConfig implements Serializable, Cloneable {
   private TransportProvider a = null;
   private TransportProvider.Params b = null;
   private SnmpEngine c = null;
   private SnmpMetadata d = null;
   private int e = -1;
   private InetAddress f = null;
   private int g = 1;
   private int h;
   private int i;
   private int j;
   private int k;
   private int l;
   private int m;
   private int n;
   private int o;
   private int p;
   private int q;
   private int r;
   private int s;
   private int t;
   private int u;
   private int v;
   private boolean w;
   private boolean x;
   private boolean y;
   private boolean z;
   private int A;
   private boolean B;
   private SnmpEngineID C;
   private boolean D;
   private boolean E;

   public SnmpSessionConfig() {
      this.h = Snmp.DEFAULT_RECEIVE_BUFFER_SIZE;
      this.i = 1;
      this.j = 5;
      this.k = 5;
      this.l = 5;
      this.m = 5;
      this.n = 5;
      this.o = 1;
      this.p = 5;
      this.q = -1;
      this.r = 1;
      this.s = -1;
      this.t = Integer.MAX_VALUE;
      this.u = 1;
      this.v = BufferedTransportProvider.DEFAULT_MAX_QUEUE_LENGTH;
      this.w = false;
      this.x = false;
      this.y = false;
      this.z = false;
      this.A = -1;
      this.B = true;
      this.C = null;
      this.D = true;
      this.E = false;
      this.i = Integer.getInteger(a("1\u0007Kv -\u0011]R'1\u0007Yb<"), new Integer(1));
      this.j = Integer.getInteger(a("1\u0007Kv -\u0011]V=*\rJo;:"), new Integer(5));
      this.k = Integer.getInteger(a("7\u000bUc=\u0017\nJc.'\u0011"), new Integer(5));
      this.l = Integer.getInteger(a("7\u000bUc=\u0013\u0010Qi=*\u0016A"), new Integer(5));
      this.m = Integer.getInteger(a("3\rTj&-\u0005ln=&\u0003\\u"), new Integer(5));
      this.n = Integer.getInteger(a("3\rTj&-\u0005ht&,\u0010Qr6"), new Integer(5));
      this.o = Integer.getInteger(a("&\u0014]h;\u0017\nJc.'\u0011"), new Integer(1));
      this.p = Integer.getInteger(a("&\u0014]h;\u0013\u0010Qi=*\u0016A"), new Integer(5));
      this.q = Integer.getInteger(a("0\u0007Vb\u001f1\u000bWt&7\u001b"), -1);
      this.A = (int)(Math.random() * 10000.0 + 100.0);
   }

   public SnmpSessionConfig(SnmpSessionConfig var1) {
      this.h = Snmp.DEFAULT_RECEIVE_BUFFER_SIZE;
      this.i = 1;
      this.j = 5;
      this.k = 5;
      this.l = 5;
      this.m = 5;
      this.n = 5;
      this.o = 1;
      this.p = 5;
      this.q = -1;
      this.r = 1;
      this.s = -1;
      this.t = Integer.MAX_VALUE;
      this.u = 1;
      this.v = BufferedTransportProvider.DEFAULT_MAX_QUEUE_LENGTH;
      this.w = false;
      this.x = false;
      this.y = false;
      this.z = false;
      this.A = -1;
      this.B = true;
      this.C = null;
      this.D = true;
      this.E = false;
      this.d = var1.d;
      this.e = var1.e;
      this.f = var1.f;
      this.g = var1.g;
      this.h = var1.h;
      this.i = var1.i;
      this.j = var1.j;
      this.k = var1.k;
      this.l = var1.l;
      this.m = var1.m;
      this.n = var1.n;
      this.o = var1.o;
      this.p = var1.p;
      this.q = var1.q;
      this.w = var1.w;
      this.s = var1.s;
      this.y = var1.y;
      this.z = var1.z;
      this.A = var1.A;
      this.E = var1.E;
   }

   public Object clone() {
      return new SnmpSessionConfig(this);
   }

   public void setEngineID(SnmpEngineID var1) {
      this.C = var1;
   }

   public SnmpEngineID getEngineID() {
      return this.C;
   }

   public SnmpMetadata getMetadata() {
      return this.d;
   }

   public void setMetadata(SnmpMetadata var1) {
      this.d = var1;
   }

   public int getLocalPort() {
      return this.e;
   }

   public void setLocalPort(int var1) {
      this.e = var1;
   }

   public InetAddress getLocalAddr() {
      return this.f;
   }

   public void setLocalAddr(InetAddress var1) {
      this.f = var1;
   }

   public int getTransportType() {
      return this.g;
   }

   public void setTransportType(int var1) {
      this.g = var1;
   }

   public int getReceiveBufferSize() {
      return this.h;
   }

   public void setReceiveBufferSize(int var1) {
      this.h = var1;
   }

   public int getBufferMaxQueueLength() {
      return this.v;
   }

   public void setBufferMaxQueueLength(int var1) {
      this.v = var1;
   }

   public int getResponseThreadCount() {
      return this.i;
   }

   public void setResponseThreadCount(int var1) {
      this.i = var1;
   }

   public int getResponseThreadPriority() {
      return this.j;
   }

   public void setResponseThreadPriority(int var1) {
      this.j = var1;
   }

   public int getTimerThreadCount() {
      return this.k;
   }

   public void setTimerThreadCount(int var1) {
      this.k = var1;
   }

   public int getTimerThreadPriority() {
      return this.l;
   }

   public void setTimerThreadPriority(int var1) {
      this.l = var1;
   }

   public int getPollingThreadCount() {
      return this.m;
   }

   public void setPollingThreadCount(int var1) {
      this.m = var1;
   }

   public int getPollingThreadPriority() {
      return this.n;
   }

   public void setPollingThreadPriority(int var1) {
      this.n = var1;
   }

   public int getEventThreadCount() {
      return this.o;
   }

   public void setEventThreadCount(int var1) {
      this.o = var1;
   }

   public int getEventThreadPriority() {
      return this.p;
   }

   public void setEventThreadPriority(int var1) {
      this.p = var1;
   }

   public int getSendThreadPriority() {
      return this.q;
   }

   public void setSendThreadPriority(int var1) {
      this.q = var1;
   }

   public boolean getBuffered() {
      return this.w;
   }

   public void setBuffered(boolean var1) {
      this.w = var1;
   }

   public boolean isViewFromFirstVarBind() {
      return this.x;
   }

   public void isViewFromFirstVarBind(boolean var1) {
      this.x = var1;
   }

   public void setTimeoutAlgorithm(int var1) {
      this.r = var1;
   }

   public int getTimeoutAlgorithm() {
      return this.r;
   }

   public boolean isAutoDiscovery() {
      return this.B;
   }

   public void isAutoDiscovery(boolean var1) {
      this.B = var1;
   }

   public boolean isShowSteps() {
      return this.y;
   }

   public void isShowSteps(boolean var1) {
      this.y = var1;
   }

   public void setMaxPDUVarBinds(int var1) {
      this.s = var1;
   }

   public int getMaxPDUVarBinds() {
      return this.s;
   }

   public void setMinRequestId(int var1) {
      this.u = var1;
   }

   public int getMinRequestId() {
      return this.u;
   }

   public void setMaxRequestId(int var1) {
      this.t = var1;
   }

   public int getMaxRequestId() {
      return this.t;
   }

   public void setInitialRequestId(int var1) {
      this.A = var1;
   }

   public int getInitialRequestId() {
      return this.A;
   }

   public void setProcessInvalidResponseMessageIDs(boolean var1) {
      this.E = var1;
   }

   public boolean getProcessInvalidResponseMessageIDs() {
      return this.E;
   }

   public void setReuseRequestIds(boolean var1) {
      this.z = var1;
   }

   public boolean getReuseRequestIds() {
      return this.z;
   }

   public void isShutdownPendingRequests(boolean var1) {
      this.D = var1;
   }

   public boolean isShutdownPendingRequests() {
      return this.D;
   }

   public void setTransportProvider(TransportProvider var1) {
      this.a = var1;
   }

   public TransportProvider getTransportProvider() {
      return this.a;
   }

   public void setEngine(SnmpEngine var1) {
      this.c = var1;
   }

   public SnmpEngine getEngine() {
      return this.c;
   }

   public void setTransportProviderParams(TransportProvider.Params var1) {
      this.b = var1;
   }

   public TransportProvider.Params getTransportProviderParams() {
      return this.b;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 67;
               break;
            case 1:
               var10003 = 98;
               break;
            case 2:
               var10003 = 56;
               break;
            case 3:
               var10003 = 6;
               break;
            default:
               var10003 = 79;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
