package monfox.toolkit.snmp.mgr;

import java.util.Properties;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.util.Lock;

public final class SnmpPendingRequest {
   private Lock a;
   private int b;
   private int c;
   private int d;
   private SnmpPendingRequest e;
   private Properties f;
   private int g;
   private int h;
   private SnmpVarBindList i;
   private SnmpVarBindList j;
   private SnmpVarBindList k;
   private long l;
   private long m;
   private int n;
   private SnmpPeer o;
   private SnmpParameters p;
   private SnmpResponseListener q;
   private SnmpSession r;
   private int s;
   private int t;
   private int u;
   private int v;
   private int w;
   private long x;
   private boolean y;
   private boolean z;
   private boolean A;
   private boolean B;
   private boolean C;
   private boolean D;
   private boolean E;
   private boolean F;
   private boolean G;
   private Object H;
   private Object I;
   private Object J;
   private SnmpOid K;
   private SnmpMessage L;
   private SnmpContext M;
   private SnmpPDU N;
   private SnmpVarBindList O;
   private Vector P;
   private Object Q;
   private TransportEntity R;
   private static int S = 1;
   private int T;
   private Object U;
   private static String[] V = new String[]{a(";\u0015%{\u0004:\b"), a("!\u0015\u000fK\u001f2"), a(";\u00153|\u0015=4\u0001d\u0013"), a("7\u001b\u0004_\u00179\u000f\u0005"), a("'\u001f\u0001m9;\u0016\u0019"), a("2\u001f\u000eL\u0004'"), a(";\u0015!j\u00150\t\u0013"), a("\"\b\u000fg\u0011\u0001\u0003\u0010l"), a("\"\b\u000fg\u0011\u0019\u001f\u000en\u0002="), a("\"\b\u000fg\u0011\u0010\u0014\u0003f\u0012<\u0014\u0007"), a("\"\b\u000fg\u0011\u0003\u001b\f|\u0013"), a(";\u0015#{\u00134\u000e\tf\u0018"), a("<\u0014\u0003f\u0018&\u0013\u0013}\u0013;\u000e6h\u001a \u001f"), a("'\u001f\u0013f\u0003'\u0019\u0005\\\u00184\f\u0001`\u001a4\u0018\fl"), a("6\u0015\rd\u001f!<\u0001`\u001a0\u001e"), a(" \u0014\u0004f04\u0013\fl\u0012"), a("4\u000f\u0014a\u0019'\u0013\u001ah\u0002<\u0015\u000eL\u0004'\u0015\u0012"), a(";\u0015\u0014^\u0004<\u000e\u0001k\u001a0"), a("<\u0014\u0003f\u0018&\u0013\u0013}\u0013;\u000e.h\u001b0")};
   private static final String W = "$Id: SnmpPendingRequest.java,v 1.37 2011/01/07 23:20:32 sking Exp $";
   private static Logger X = Logger.getInstance(a("\u0011).D&"), a("\u0018=2"), a("\u0006\u0014\ry&0\u0014\u0004`\u00182(\u0005x\u0013&\u000e"));

   SnmpPendingRequest(SnmpSession var1, SnmpPeer var2, SnmpResponseListener var3, int var4, SnmpVarBindList var5) throws SnmpException {
      boolean var6 = SnmpSession.B;
      super();
      this.a = null;
      this.b = 0;
      this.c = -1;
      this.d = -1;
      this.e = null;
      this.f = null;
      this.g = 0;
      this.h = 0;
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = -1L;
      this.m = -1L;
      this.n = -1;
      this.o = null;
      this.p = null;
      this.q = null;
      this.r = null;
      this.s = 0;
      this.t = 0;
      this.u = 0;
      this.v = 0;
      this.w = 0;
      this.x = 0L;
      this.y = false;
      this.z = false;
      this.A = false;
      this.B = false;
      this.C = false;
      this.D = false;
      this.E = false;
      this.F = false;
      this.G = false;
      this.H = null;
      this.I = null;
      this.J = null;
      this.K = null;
      this.L = null;
      this.M = null;
      this.N = null;
      this.O = null;
      this.P = null;
      this.Q = null;
      this.R = null;
      this.T = -1;
      this.U = new Object();
      this.r = var1;
      this.o = var2;
      this.p = var2.getParameters();
      this.q = var3;
      this.u = var4;
      this.i = var5;
      this.c = var2.getMaxPDUVarBinds() > 0 ? var2.getMaxPDUVarBinds() : var1.getMaxPDUVarBinds();
      this.T = S++;
      if (SnmpException.b) {
         SnmpSession.B = !var6;
      }

   }

   public final void cancel() {
      this.r.cancel(this);
      this.y = true;
   }

   public final int getType() {
      return this.u;
   }

   public final synchronized int getErrorIndex() {
      return this.t;
   }

   public final String getErrorString() {
      return Snmp.errorStatusToString(this.s);
   }

   public final synchronized int getErrorStatus() {
      return this.s;
   }

   public boolean isError() {
      return this.getErrorStatus() > 0;
   }

   public SnmpVarBind getErrorVarBind() {
      if (this.getErrorIndex() <= 0) {
         return null;
      } else if (this.v() != null) {
         return this.v().getErrorVarBind();
      } else {
         try {
            return this.O.get(this.getErrorIndex() - 1);
         } catch (Exception var2) {
            return null;
         }
      }
   }

   public String getErrorMessage() {
      if (!this.isError()) {
         return null;
      } else {
         SnmpVarBind var1 = this.getErrorVarBind();
         return var1 != null ? "(" + this.getErrorString() + a("oZ\u0005{\u0004:\bM\u007f\u0017'\u0018\tg\u0012\u000e") + this.getErrorIndex() + a("\bG") + var1 + ")" : "(" + this.getErrorString() + a("oZ\u0005{\u0004:\bM`\u00181\u001f\u00184") + this.getErrorIndex() + ")";
      }
   }

   public final SnmpParameters getParameters() {
      return this.p;
   }

   public final SnmpPeer getPeer() {
      return this.o;
   }

   public final synchronized int getRequestId() {
      return this.n;
   }

   public final synchronized SnmpVarBindList getRequestVarBindList() {
      return this.i;
   }

   public final synchronized SnmpVarBindList getResponseVarBindList() {
      return this.j;
   }

   public final synchronized int getRetryCount() {
      return this.v;
   }

   public final SnmpSession getSession() {
      return this.r;
   }

   public final synchronized boolean isCancelled() {
      return this.y;
   }

   public final synchronized boolean isCompleted() {
      return this.z;
   }

   public final synchronized boolean isTimedOut() {
      return this.C;
   }

   public final synchronized boolean isReport() {
      return this.A;
   }

   final synchronized void a() {
      this.t();
      this.z = true;
      this.notify();
   }

   final synchronized void b() {
      this.t();
      this.A = true;
      this.z = true;
      this.notify();
   }

   final synchronized void c() {
      this.t();
      this.C = true;
      this.r.a(this, false);
      this.notify();
   }

   public long getResponseTimestamp() {
      return this.m;
   }

   public long getRequestTimestamp() {
      return this.l;
   }

   public static String errorToString(int var0) {
      return var0 > 18 ? a(" \u0014\u000bg\u0019\"\u0014%{\u0004:\b") : V[var0];
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u0006\u0014\ry&0\u0014\u0004`\u00182(\u0005x\u00030\t\u0014R") + this.T + a("\bR\u0012l\u0007<\u001e]")).append(this.n);
      var1.append(a("y\n\u0005l\u0004h")).append(this.o);
      var1.append(a("y\u000e\u0019y\u0013h")).append(SnmpPDU.typeToString(this.u));
      var1.append(")");
      return var1.toString();
   }

   public final synchronized boolean awaitCompletion(long var1) {
      if (this.isCompleted()) {
         return true;
      } else if (this.isTimedOut()) {
         return false;
      } else {
         try {
            this.wait(var1);
         } catch (InterruptedException var4) {
         }

         return this.isCompleted();
      }
   }

   public final synchronized void expectCompletion(long var1) throws SnmpTimeoutException, SnmpReportException {
      if (this.isCompleted()) {
         if (this.isReport() && this.L != null) {
            throw new SnmpReportException(this.L);
         }
      } else if (this.isTimedOut()) {
         throw new SnmpTimeoutException(this);
      } else {
         try {
            this.wait(var1);
         } catch (InterruptedException var4) {
         }

         if (this.isReport() && this.L != null) {
            throw new SnmpReportException(this.L);
         } else if (!this.isCompleted()) {
            throw new SnmpTimeoutException(this);
         }
      }
   }

   public final synchronized boolean awaitCompletion() {
      if (this.isCompleted()) {
         return true;
      } else if (this.isTimedOut()) {
         return false;
      } else {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }

         return this.isCompleted();
      }
   }

   public final synchronized void expectCompletion() throws SnmpTimeoutException, SnmpReportException {
      if (this.isCompleted()) {
         if (this.isReport() && this.L != null) {
            throw new SnmpReportException(this.L);
         }
      } else if (this.isTimedOut()) {
         throw new SnmpTimeoutException(this);
      } else {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }

         if (this.isReport() && this.L != null) {
            throw new SnmpReportException(this.L);
         } else if (!this.isCompleted()) {
            throw new SnmpTimeoutException(this);
         }
      }
   }

   public int getPollingInterval() {
      return this.w;
   }

   public void setPollingInterval(int var1) {
      this.w = var1;
   }

   protected void setRequestVarBindList(SnmpVarBindList var1) {
      this.i = var1;
   }

   protected void setResponseVarBindList(SnmpVarBindList var1) {
      this.j = var1;
   }

   protected synchronized void setResult(SnmpVarBindList var1, int var2, int var3) {
      this.j = var1;
      this.s = var2;
      this.t = var3;
   }

   public SnmpResponseListener getResponseListener() {
      return this.q;
   }

   void a(int var1) {
      this.n = var1;
   }

   void d() {
      ++this.v;
   }

   void e() {
      this.v = 0;
      this.C = false;
   }

   void f() {
      this.z = false;
      this.C = false;
   }

   void g() {
      ++this.x;
   }

   public boolean hasTerminationOid() {
      return this.K != null;
   }

   public SnmpOid getTerminationOid() {
      return this.K;
   }

   public void setTerminationOid(SnmpOid var1) {
      this.K = var1;
   }

   boolean h() {
      return this.k != null;
   }

   void a(SnmpVarBindList var1) {
      this.k = var1;
   }

   SnmpVarBindList i() {
      return this.k;
   }

   public boolean showSteps() {
      return this.B;
   }

   public void showSteps(boolean var1) {
      this.B = var1;
   }

   Object j() {
      return this.H;
   }

   void a(Object var1) {
      this.H = var1;
   }

   synchronized Object k() {
      return this.I;
   }

   synchronized void b(Object var1) {
      this.I = var1;
   }

   synchronized Object l() {
      return this.J;
   }

   synchronized void c(Object var1) {
      this.J = var1;
   }

   boolean m() {
      return this.D;
   }

   void a(boolean var1) {
      this.D = var1;
   }

   boolean n() {
      return this.E;
   }

   void b(boolean var1) {
      this.E = var1;
   }

   boolean o() {
      return this.F;
   }

   void c(boolean var1) {
      this.F = var1;
   }

   public int getNonRepeaters() {
      return this.h;
   }

   void b(int var1) {
      this.h = var1;
   }

   public int getMaxRepetitions() {
      return this.g;
   }

   void c(int var1) {
      this.g = var1;
   }

   public SnmpMessage getSnmpMessage() {
      return this.L;
   }

   void a(SnmpMessage var1) {
      this.L = var1;
   }

   void a(long var1) {
      this.l = var1;
   }

   void b(long var1) {
      this.m = var1;
   }

   public SnmpContext getContext() {
      return this.M;
   }

   void a(SnmpContext var1) {
      this.M = var1;
   }

   public Properties getUserProperties() {
      if (this.f == null) {
         this.f = new Properties();
      }

      return this.f;
   }

   void a(SnmpPDU var1) {
      this.N = var1;
   }

   SnmpPDU p() {
      return this.N;
   }

   void b(SnmpVarBindList var1) {
      this.O = var1;
   }

   SnmpVarBindList q() {
      return this.O;
   }

   void a(SnmpParameters var1) {
      this.p = var1;
   }

   void c(SnmpVarBindList var1) {
      if (this.P == null) {
         this.P = new Vector();
      }

      if (var1 != null) {
         this.P.addElement(var1);
      }

   }

   public void pause() {
      if (this.r != null) {
         this.r.c(this);
      }

   }

   public void restart() {
      if (this.r != null) {
         this.r.b(this);
      }

   }

   public synchronized SnmpVarBindList[] getAllInstances() throws SnmpValueException {
      boolean var18 = SnmpSession.B;
      if (this.P == null) {
         return null;
      } else {
         SnmpVarBindList var1 = this.getRequestVarBindList();
         SnmpOid var2 = this.getTerminationOid();
         int var3 = this.P.size();
         int var4 = 0;
         int var5 = var3 - 1;

         int var10000;
         label96:
         while(true) {
            var10000 = var5;

            label93:
            while(var10000 >= 0) {
               SnmpVarBindList var6 = (SnmpVarBindList)this.P.elementAt(var5);
               SnmpOid var7 = var6.get(0).getOid();
               var10000 = var2.contains(var7);
               if (var18) {
                  break label96;
               }

               if (var10000 != 0 || var18) {
                  ++var4;
                  SnmpOid var8 = null;

                  try {
                     var8 = var6.get(0).getOid().suboid(var2.getLength());
                  } catch (SnmpValueException var19) {
                     if (!var18) {
                        break;
                     }
                  }

                  int var9 = 1;

                  while(var9 < var1.size()) {
                     SnmpVarBind var10 = var1.get(var9);
                     SnmpOid var11 = var10.getOid();
                     SnmpOid var12 = new SnmpOid(var11);
                     var12.append(var8);
                     SnmpVarBind var13 = var6.get(var9);
                     var10000 = var13.getOid().equals(var12);
                     if (var18) {
                        continue label93;
                     }

                     if (var10000 == 0) {
                        var13.setOid(var12);
                        byte var14 = 0;
                        int var15 = var5 - 1;

                        while(true) {
                           if (var14 == 0) {
                              var10000 = var15;
                              if (var18) {
                                 break;
                              }

                              if (var15 >= 0) {
                                 SnmpVarBindList var16 = (SnmpVarBindList)this.P.elementAt(var15);
                                 SnmpOid var17 = var16.get(var9).getOid();
                                 if (var17.equals(var12)) {
                                    var13.setValue(var16.get(var9).getValue());
                                    var14 = 1;
                                 }

                                 --var15;
                                 if (!var18) {
                                    continue;
                                 }
                              }
                           }

                           var10000 = var14;
                           break;
                        }

                        if (var10000 == 0) {
                           var13.setValue((SnmpValue)(new SnmpNull()));
                        }
                     }

                     ++var9;
                     if (var18) {
                        break;
                     }
                  }
               }

               --var5;
               if (!var18) {
                  continue label96;
               }
               break;
            }

            var10000 = var4;
            break;
         }

         SnmpVarBindList[] var20 = new SnmpVarBindList[var10000];
         int var21 = 0;

         SnmpVarBindList[] var22;
         while(true) {
            if (var21 < var4) {
               var22 = var20;
               if (var18) {
                  break;
               }

               var20[var21] = (SnmpVarBindList)this.P.elementAt(var21);
               ++var21;
               if (!var18) {
                  continue;
               }
            }

            var22 = var20;
            break;
         }

         return var22;
      }
   }

   public boolean isSplit() {
      if (this.d == -1) {
         this.d = this.c <= 0 || this.i.size() <= this.c || this.u != 160 && this.u != 163 ? 0 : 1;
      }

      return this.d == 1;
   }

   int r() {
      return this.b;
   }

   void d(int var1) {
      this.b = var1;
   }

   public int getMaxPDUVarBinds() {
      return this.c;
   }

   public long getPollingCount() {
      return this.x;
   }

   public void setUserData(Object var1) {
      this.Q = var1;
   }

   public Object getUserData() {
      return this.Q;
   }

   public TransportEntity getResponder() {
      return this.R;
   }

   synchronized void s() {
      this.c();
   }

   void a(TransportEntity var1) {
      this.R = var1;
   }

   boolean a(Lock var1, long var2) {
      if (X.isDebugEnabled()) {
         X.debug(a("\u00195#BLu\u0016\u000fj\u001d\u000e;4]3\u0018*4TLu") + this.toString());
      }

      if (!var1.lock(this, var2)) {
         if (X.isDebugEnabled()) {
            X.debug(a("\u00195#BLu\u0016\u000fj\u001d\u000e<!@:\u0010>=3V") + this.toString());
         }

         return false;
      } else {
         this.a = var1;
         if (X.isDebugEnabled()) {
            X.debug(a("\u00195#BLu\u0016\u000fj\u001d\u000e5\"]7\u001c4%M+oZ") + this.toString());
         }

         return true;
      }
   }

   void t() {
      Lock var1 = this.a;
      this.a = null;
      if (var1 != null) {
         var1.releaseLock(this);
         if (X.isDebugEnabled()) {
            X.debug(a("\u00195#BLu\b\u0005e\u00134\t\u0005E\u00196\u0011Z)") + this.toString());
         }
      }

   }

   Lock u() {
      return this.a;
   }

   void e(int var1) {
      this.c = var1;
   }

   SnmpPendingRequest v() {
      return this.e;
   }

   void a(SnmpPendingRequest var1) {
      this.e = var1;
   }

   Object w() {
      return this.U;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 85;
               break;
            case 1:
               var10003 = 122;
               break;
            case 2:
               var10003 = 96;
               break;
            case 3:
               var10003 = 9;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
