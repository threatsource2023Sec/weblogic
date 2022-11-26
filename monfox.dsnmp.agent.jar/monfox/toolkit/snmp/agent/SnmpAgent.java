package monfox.toolkit.snmp.agent;

import java.net.InetAddress;
import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import monfox.log.Logger;
import monfox.log.SimpleLogger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.ext.audit.SnmpAuditTrailLogger;
import monfox.toolkit.snmp.agent.notify.SnmpNotifier;
import monfox.toolkit.snmp.agent.proxy.SnmpProxyForwarder;
import monfox.toolkit.snmp.agent.target.SnmpTarget;
import monfox.toolkit.snmp.agent.usm.Usm;
import monfox.toolkit.snmp.agent.usm.UsmDH;
import monfox.toolkit.snmp.agent.vacm.Vacm;
import monfox.toolkit.snmp.agent.vacm.VacmContextTable;
import monfox.toolkit.snmp.agent.xml.XmlSnmpAgentLoader;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpTransportException;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.engine.UdpTransportProvider;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpSessionConfig;
import monfox.toolkit.snmp.util.Commandline;
import monfox.toolkit.snmp.util.ExecManager;
import monfox.toolkit.snmp.v3.V3SnmpMessageModule;
import monfox.toolkit.snmp.v3.usm.USMSecurityCoder;
import monfox.toolkit.snmp.v3.usm.ext.UsmUserSecurityExtension;

public class SnmpAgent {
   private SnmpMib a;
   private SnmpEngine b;
   private SnmpSession c;
   private SnmpSecurityModel d;
   private SnmpAccessControlModel e;
   private SnmpAuditTrailLogger f;
   private boolean g;
   private boolean h;
   private Map i;
   private boolean j;
   private boolean k;
   private long l;
   private long m;
   private SnmpForwarder n;
   private SnmpResponder o;
   private SnmpTarget p;
   private SnmpProxyForwarder q;
   private SnmpNotifier r;
   private List s;
   private HashMap t;
   private SnmpIpAddress u;
   private SnmpOid v;
   private Logger w;

   public SnmpAgent(int var1, SnmpMib var2) throws SnmpException {
      this(var1, var2, (SnmpEngineID)null);
   }

   public SnmpAgent(int var1, SnmpMib var2, SnmpEngineID var3) throws SnmpException {
      this.g = false;
      this.h = false;
      this.i = new HashMap();
      this.j = false;
      this.k = false;
      this.l = -1L;
      this.m = -1L;
      this.s = new Vector();
      this.t = new HashMap();
      this.u = null;
      this.v = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 4L, 1L, 3817L, 1L, 1000L});
      SnmpEngine var4 = new SnmpEngine(var3, var2.getMetadata());
      TransportProvider var5 = TransportProvider.newInstance(1, (InetAddress)null, var1);
      var4.addTransportProvider(var5);
      this.a(var4, var2, (Config)null);
   }

   public SnmpAgent(SnmpMib var1, TransportProvider[] var2) throws SnmpException {
      this(var1, var2, (SnmpEngineID)null);
   }

   public SnmpAgent(SnmpMib var1, TransportProvider[] var2, SnmpEngineID var3) throws SnmpException {
      boolean var6 = SnmpMibNode.b;
      super();
      this.g = false;
      this.h = false;
      this.i = new HashMap();
      this.j = false;
      this.k = false;
      this.l = -1L;
      this.m = -1L;
      this.s = new Vector();
      this.t = new HashMap();
      this.u = null;
      this.v = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 4L, 1L, 3817L, 1L, 1000L});
      SnmpEngine var4 = new SnmpEngine(var3, var1.getMetadata());
      int var5 = 0;

      while(true) {
         if (var5 < var2.length) {
            var4.addTransportProvider(var2[var5]);
            ++var5;
            if (var6) {
               break;
            }

            if (!var6) {
               continue;
            }
         }

         this.a(var4, var1, (Config)null);
         break;
      }

   }

   public SnmpAgent(SnmpEngine var1, SnmpMib var2) throws SnmpException {
      this.g = false;
      this.h = false;
      this.i = new HashMap();
      this.j = false;
      this.k = false;
      this.l = -1L;
      this.m = -1L;
      this.s = new Vector();
      this.t = new HashMap();
      this.u = null;
      this.v = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 4L, 1L, 3817L, 1L, 1000L});
      this.a(var1, var2, (Config)null);
   }

   public SnmpAgent(SnmpMib var1, V3Config var2) throws SnmpException {
      this((SnmpMib)var1, (Config)var2);
   }

   public SnmpAgent(SnmpMib var1, Config var2) throws SnmpException {
      boolean var9 = SnmpMibNode.b;
      super();
      this.g = false;
      this.h = false;
      this.i = new HashMap();
      this.j = false;
      this.k = false;
      this.l = -1L;
      this.m = -1L;
      this.s = new Vector();
      this.t = new HashMap();
      this.u = null;
      this.v = new SnmpOid(new long[]{1L, 3L, 6L, 1L, 4L, 1L, 3817L, 1L, 1000L});
      SnmpEngineID var3 = null;
      V3Config var4;
      if (var2 instanceof V3Config) {
         var4 = (V3Config)var2;
         var3 = var4.getEngineID();
      }

      label63: {
         var4 = null;
         SnmpEngine var10;
         if (var2.getEngine() != null) {
            var10 = var2.getEngine();
         } else {
            int var6;
            if (var2.getTransportProviders() != null) {
               var10 = new SnmpEngine(var3, var1.getMetadata());
               TransportProvider[] var5 = var2.getTransportProviders();
               var6 = 0;

               while(var6 < var5.length) {
                  var10.addTransportProvider(var5[var6]);
                  ++var6;
                  if (var9 && var9) {
                     break label63;
                  }
               }
            } else if (var2.getTransportProviderParams() != null) {
               var10 = new SnmpEngine(var3, var1.getMetadata());
               TransportProvider.Params[] var11 = var2.getTransportProviderParams();
               var6 = 0;

               while(var6 < var11.length) {
                  TransportProvider.Params var7 = var11[var6];
                  TransportProvider var8 = TransportProvider.newInstance(var7);
                  var10.addTransportProvider(var8);
                  ++var6;
                  if (var9) {
                     break label63;
                  }

                  if (var9) {
                     break;
                  }
               }
            } else {
               if (var2.getPort() <= 0) {
                  throw new SnmpException(a("\\IiI\fSH:M\u0011@RiN\u000eWE [\u0017WBg\u001d\u0013GU=\u001d\rBC*T\u0018[C-\u001d\u001f\\\u0006\u001aS\u0013Bc'Z\u0017\\Ce\u001d*@G'N\u000e]T=m\f]P Y\u001b@}\u0014\u001d\u0011@\u00069R\fF\u0006'H\u0013PC;"));
               }

               var10 = new SnmpEngine(var3, var1.getMetadata());
               TransportProvider var12;
               if (var2.getUdpEnabled()) {
                  var12 = TransportProvider.newInstance(1, var2.getInetAddress(), var2.getPort());
                  var10.addTransportProvider(var12);
               }

               if (var2.getTcpEnabled()) {
                  var12 = TransportProvider.newInstance(2, var2.getInetAddress(), var2.getPort());
                  var10.addTransportProvider(var12);
               }
            }
         }

         this.a(var10, var1, var2);
      }

      if (SnmpException.b) {
         SnmpMibNode.b = !var9;
      }

   }

   private void a(SnmpEngine var1, SnmpMib var2, Config var3) throws SnmpException {
      boolean var10 = SnmpMibNode.b;
      V3Config var4 = null;
      if (var3 != null && var3 instanceof V3Config) {
         var4 = (V3Config)var3;
      }

      if (var2 == null | var1 == null) {
         throw new NullPointerException();
      } else {
         SnmpEngineID var5 = var1.getEngineID();
         if (var5 != null && var5.isAuthoritative()) {
            this.g = true;
         }

         this.b = var1;
         this.a = var2;
         this.w = Logger.getInstance(a("sa\fs*"));
         this.w.info(a("ah\u0004m^sA,S\n\u0012O'T\n[G%T\u0004WB"));
         this.w.info(a("AH$M;\\A S\u001b{bt") + this.b.getEngineID());
         if (var3 != null && var3.getAccessControlModel() != null) {
            this.e = var3.getAccessControlModel();
         }

         SnmpSessionConfig var6 = null;
         if (var3 != null) {
            var6 = var3.getSessionConfig();
         }

         if (var6 == null) {
            var6 = new SnmpSessionConfig();
            var6.setResponseThreadCount(1);
            var6.setTimerThreadCount(1);
            var6.setPollingThreadCount(1);
            var6.setEventThreadCount(1);
            var6.isShutdownPendingRequests(true);
         }

         label109: {
            var6.setMetadata(var2.getMetadata());
            var6.setEngine(this.b);
            this.c = new SnmpSession(var6);
            SnmpMetadata var7;
            if (this.g) {
               var7 = var2.getMetadata();

               try {
                  var7.loadModule(a("ah\u0004m\b\u0000\u000b\u0004t<"));
                  var7.loadModule(a("ah\u0004mSfg\u001bz;f\u000b\u0004t<"));
                  var7.loadModule(a("ah\u0004mSqi\u0004p+|o\u001ddS\u007fo\u000b"));
                  var7.loadModule(a("ah\u0004mStt\bp;ei\u001bvS\u007fo\u000b"));
                  var7.loadModule(a("ah\u0004mSbt\u0006e'\u001fk\u0000\u007f"));
                  var7.loadModule(a("ah\u0004mS|i\u001dt8{e\bi7}hdp7p"));
                  var7.loadModule(a("ah\u0004mSgu\foSpg\u001ax:\u001fu\u0004\u00103{d"));
                  var7.loadModule(a("ah\u0004mSdo\fjSpg\u001ax:\u001fg\npS\u007fo\u000b"));
                  var7.loadModule(a("vu\u0007p.\u001fg\u000ex0f\u000b\u0004t<"));
                  if (var4 != null && var4.getUsmDHEnabled()) {
                     var7.loadModule(a("ah\u0004mSgu\u0004\u0010:z\u000b\u0006\u007f4we\u001dnS\u007fo\u000b"));
                  }
               } catch (Exception var13) {
                  this.w.error(a("qG'S\u0011F\u0006%R\u001fV\u0006;X\u000fGO;X\u001a\u0012k\u0000\u007f^_I-H\u0012W\u001ci\u001a"), var13);
               }

               label93: {
                  if (var4 != null && var4.getUsmDHEnabled()) {
                     this.d = new UsmDH(this);
                     if (!var10) {
                        break label93;
                     }
                  }

                  this.d = new Usm(this);
               }

               if (var4 != null && var4.getUsmUserSecurityExtension() != null) {
                  V3SnmpMessageModule var8 = (V3SnmpMessageModule)this.getEngine().getMessageProcessor().getMessageModule(3);
                  USMSecurityCoder var9 = (USMSecurityCoder)var8.getSecurityCoder();
                  var9.setUsmUserSecurityExtension(var4.getUsmUserSecurityExtension());
                  this.c.getUsm().setUserExtension(var4.getUsmUserSecurityExtension());
               }

               if (this.e == null) {
                  this.e = new Vacm(this);
               }

               if (!var10) {
                  break label109;
               }
            }

            var7 = var2.getMetadata();

            try {
               var7.loadModule(a("ah\u0004m\b\u0000\u000b\u0004t<"));
               var7.loadModule(a("ah\u0004mSfg\u001bz;f\u000b\u0004t<"));
               var7.loadModule(a("ah\u0004mSbt\u0006e'\u001fk\u0000\u007f"));
               var7.loadModule(a("ah\u0004mSqi\u0004p+|o\u001ddS\u007fo\u000b"));
               var7.loadModule(a("ah\u0004mStt\bp;ei\u001bvS\u007fo\u000b"));
               var7.loadModule(a("ah\u0004mS|i\u001dt8{e\bi7}hdp7p"));
               var7.loadModule(a("vu\u0007p.\u001fg\u000ex0f\u000b\u0004t<"));
            } catch (Exception var12) {
               this.w.error(a("qG'S\u0011F\u0006%R\u001fV\u0006;X\u000fGO;X\u001a\u0012k\u0000\u007f^_I-H\u0012W\u001ci\u001a"), var12);
            }

            this.d = null;
            if (this.e == null) {
               this.e = new V2cacm(this);
            }
         }

         if (var3 != null && var3.getAuditTrailLogger() != null) {
            this.f = var3.getAuditTrailLogger();

            try {
               V3SnmpMessageModule var14 = (V3SnmpMessageModule)this.getEngine().getMessageProcessor().getMessageModule(3);
               USMSecurityCoder var15 = (USMSecurityCoder)var14.getSecurityCoder();
               var15.setAuditTrailLogger(this.f);
            } catch (Throwable var11) {
               this.w.error(a("QG'S\u0011F\u0006;X\u0019[U=X\f\u0012U,^\u000b@O=D^SS-T\n\u0012R;\\\u0017^"), var11);
            }
         }

         this.o = new SnmpResponder(this);
         this.n = new SnmpForwarder(this);
         this.p = new SnmpTarget(this);
         this.q = new SnmpProxyForwarder(this);
         this.r = new SnmpNotifier(this);
      }
   }

   public void shutdown() throws SnmpTransportException {
      boolean var3 = SnmpMibNode.b;
      this.w.info(a("AN<I\u001a]Q'"));
      this.o.shutdown();
      Enumeration var1 = this.b.getTransportProviders();

      while(true) {
         if (var1.hasMoreElements()) {
            TransportProvider var2 = (TransportProvider)var1.nextElement();
            var2.shutdown();
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         if (this.c != null) {
            this.w.info(a("\u001f\u000biN\u0016GR=T\u0010U\u0006-R\t\\\u0006(Z\u001b\\RiN\u001bAU R\u0010"));
            this.c.shutdown();
            this.w.info(a("\u001f\u000biN\u0016GR=T\u0010U\u0006-R\t\\\u0006*R\u0013BJ,I\u001b"));
            this.c = null;
         }

         this.w.info(a("ah\u0004m^sA,S\n\u0012U!H\nVI>S"));
         break;
      }

   }

   public SnmpEngine getEngine() {
      return this.b;
   }

   public SnmpMib getMib() {
      return this.a;
   }

   public SnmpNotifier getNotifier() {
      return this.r;
   }

   public SnmpTarget getTarget() {
      return this.p;
   }

   public SnmpProxyForwarder getProxyForwarder() {
      return this.q;
   }

   public SnmpAccessControlModel getAccessControlModel() {
      return this.e;
   }

   public SnmpSecurityModel getSecurityModel() {
      return this.d;
   }

   public SnmpResponder getResponder() {
      return this.o;
   }

   SnmpForwarder a() {
      return this.n;
   }

   public boolean isV3() {
      return this.g;
   }

   /** @deprecated */
   public SnmpAccessPolicy addAccessPolicy(String var1, boolean var2) {
      if (!(this.e instanceof V2cacm)) {
         return null;
      } else {
         V2cacm var3 = (V2cacm)this.e;
         return var3.addAccessPolicy(var1, var2);
      }
   }

   /** @deprecated */
   public SnmpAccessPolicy addAccessPolicy(String var1, boolean var2, SnmpMibView var3) {
      if (!(this.e instanceof V2cacm)) {
         return null;
      } else {
         V2cacm var4 = (V2cacm)this.e;
         return var4.addAccessPolicy(var1, var2, var3);
      }
   }

   /** @deprecated */
   public void addAccessPolicy(SnmpAccessPolicy var1) {
      if (this.e instanceof V2cacm) {
         V2cacm var2 = (V2cacm)this.e;
         var2.addAccessPolicy(var1);
      }
   }

   /** @deprecated */
   public SnmpAccessPolicy getAccessPolicy(String var1) {
      if (!(this.e instanceof V2cacm)) {
         return null;
      } else {
         V2cacm var2 = (V2cacm)this.e;
         return var2.getAccessPolicy(var1);
      }
   }

   /** @deprecated */
   public SnmpAccessPolicy removeAccessPolicy(String var1) {
      if (!(this.e instanceof V2cacm)) {
         return null;
      } else {
         V2cacm var2 = (V2cacm)this.e;
         return var2.removeAccessPolicy(var1);
      }
   }

   /** @deprecated */
   public void setDefaultAccessPolicy(SnmpAccessPolicy var1) {
      if (this.e instanceof V2cacm) {
         V2cacm var2 = (V2cacm)this.e;
         var2.setDefaultAccessPolicy(var1);
      }
   }

   /** @deprecated */
   public SnmpAccessPolicy getDefaultAccessPolicy() {
      if (!(this.e instanceof V2cacm)) {
         return null;
      } else {
         V2cacm var1 = (V2cacm)this.e;
         return var1.getDefaultAccessPolicy();
      }
   }

   public void addMibModule(SnmpMibModule var1) {
      this.s.add(var1);
      this.t.put(var1.getModuleName(), var1);
   }

   public List getMibModules() {
      return this.s;
   }

   public SnmpMibModule getMibModule(String var1) {
      return (SnmpMibModule)this.t.get(var1);
   }

   public void removeMibModule(String var1) {
      SnmpMibModule var2 = this.getMibModule(var1);
      if (var2 != null) {
         this.s.remove(var2);
         this.t.remove(var1);
      }

   }

   public SnmpIpAddress getAgentAddress() {
      boolean var5 = SnmpMibNode.b;
      if (this.u != null) {
         return this.u;
      } else {
         Enumeration var1 = this.b.getTransportProviders();

         while(true) {
            if (var1.hasMoreElements()) {
               label34: {
                  TransportProvider var2 = (TransportProvider)var1.nextElement();
                  if (var5) {
                     break;
                  }

                  if (var2 instanceof UdpTransportProvider) {
                     UdpTransportProvider var3 = (UdpTransportProvider)var2;
                     InetAddress var4 = var3.getAddress();
                     if (var4 != null) {
                        this.u = new SnmpIpAddress(var4.getAddress());
                        if (!var5) {
                           break label34;
                        }
                     }
                  }

                  if (!var5) {
                     continue;
                  }
               }
            }

            if (this.u == null) {
               this.u = new SnmpIpAddress(0L, 0L, 0L, 0L);
            }
            break;
         }

         return this.u;
      }
   }

   public void setAgentAddress(SnmpIpAddress var1) {
      this.u = var1;
   }

   public SnmpOid getSysObjectID() {
      return this.v;
   }

   public void setSysObjectID(SnmpOid var1) {
      this.v = var1;
   }

   /** @deprecated */
   public static void startLog() {
   }

   /** @deprecated */
   public static void stopLog() {
   }

   /** @deprecated */
   public static void restartLog() {
   }

   /** @deprecated */
   public static void startLog(String var0) {
   }

   public int getNumThreads() {
      return this.o.b();
   }

   public void setNumThreads(int var1) {
      if (var1 > 1) {
         this.j = true;
      }

      this.o.a(var1);
   }

   public void setRequestExecManager(ExecManager var1) {
      if (var1 != null) {
         this.setUseNodeLocking(true);
      }

      this.o.a(var1);
   }

   public ExecManager getRequestExecManager() {
      return this.o.a();
   }

   public void setUseTableLocking(boolean var1) {
      this.k = var1;
   }

   public boolean getUseTableLocking() {
      return this.k;
   }

   public void setTableLockTimeout(long var1) {
      this.m = var1;
   }

   public long getTableLockTimeout() {
      return this.m;
   }

   public void setUseNodeLocking(boolean var1) {
      this.j = var1;
   }

   public void setNodeLockTimeout(long var1) {
      this.l = var1;
   }

   public long getNodeLockTimeout() {
      return this.l;
   }

   public boolean getUseNodeLocking() {
      return this.j;
   }

   public SnmpAuditTrailLogger getAuditTrailLogger() {
      return this.f;
   }

   public SnmpMib swapMib(SnmpMib var1) {
      if (var1 == null) {
         return null;
      } else {
         SnmpMib var2 = this.a;
         this.a = var1;
         return var2;
      }
   }

   public void addContextMib(String var1, SnmpMib var2) throws SnmpMibException, SnmpValueException {
      this.i.put(var1, var2);
      if (this.e != null && this.e instanceof Vacm) {
         Vacm var3 = (Vacm)this.e;
         VacmContextTable var4 = var3.getContextTable();
         var4.add(var1);
      }

   }

   public void removeContextMib(String var1) throws SnmpMibException, SnmpValueException {
      this.i.remove(var1);
      if (this.e != null && this.e instanceof Vacm) {
         Vacm var2 = (Vacm)this.e;
         VacmContextTable var3 = var2.getContextTable();
         var3.add(var1);
      }

   }

   public SnmpMib getContextMib(String var1) {
      return (SnmpMib)this.i.get(var1);
   }

   public boolean hasContextMibs() {
      return this.i.size() > 0;
   }

   public boolean isCommunityAtContextFormSupported() {
      return this.h;
   }

   public void isCommunityAtContextFormSupported(boolean var1) {
      this.h = var1;
   }

   public SnmpSession getSession() {
      return this.c;
   }

   public static void main(String[] var0) {
      boolean var13 = SnmpMibNode.b;
      Commandline var1 = null;

      String var4;
      try {
         String var2 = a("V\u0019");
         String[] var3 = new String[]{a("GU(Z\u001b"), a("VS$M")};
         var4 = "p";
         String[] var5 = new String[]{a("BI;I")};
         var1 = new Commandline(var0, var2, var4, var3, var5);
      } catch (Throwable var16) {
         System.out.println(a("pG-\u001d\u0011BR R\u0010\b\u0006") + var16.getMessage());
         b();
         System.exit(1);
      }

      String[] var20 = var1.params;
      if (var1.hasFlag("?") || var20.length == 0) {
         b();
         System.exit(1);
      }

      if (var20.length < 1) {
         b();
         System.exit(1);
      }

      XmlSnmpAgentLoader var21;
      try {
         var21 = new XmlSnmpAgentLoader(var20[0]);
      } catch (Exception var15) {
         System.out.println(var15);
         return;
      }

      var4 = var21.getLogFile();
      if (var4 != null) {
         Logger.setProvider(new SimpleLogger.Provider(var4));
         var21.initLog();
      }

      Logger var22 = Logger.getInstance(a("aH$M?UC'ISsA,S\naO$"));

      SnmpMib var6;
      try {
         label85: {
            SnmpMetadata var7 = var21.loadMetadata();
            if (var7 == null) {
               var6 = new SnmpMib();
               if (!var13) {
                  break label85;
               }
            }

            var6 = new SnmpMib(var7);
         }

         var21.loadMib(var6);
      } catch (Exception var19) {
         var19.printStackTrace();
         System.out.println(var19);
         var22.error(a("\u007fo\u000b\u001d2]G-\u001d;JE,M\n[I'\u0007^"), var19);
         return;
      }

      int var23 = var1.getIntOption(a("B\u001c9R\fF"), var21.getPort());

      try {
         SnmpAgent var8;
         label63: {
            SnmpEngineID var9 = var21.getEngineID();
            if (var21.getPort() > 0) {
               var8 = new SnmpAgent(var23, var6, var9);
               if (!var13) {
                  break label63;
               }
            }

            SnmpEngine var10 = new SnmpEngine(var9, var6.getMetadata());
            var8 = new SnmpAgent(var10, var6);
         }

         if (var8.isV3()) {
            String var24 = var21.getSecurityProvider();

            try {
               Class var11 = Class.forName(var24);
               Provider var25 = (Provider)var11.newInstance();
               Security.addProvider(var25);
            } catch (Exception var17) {
               Provider[] var12 = Security.getProviders();
               if (var12 == null || var12.length == 0) {
                  System.out.println(a("aC*H\f[R0\u001d\u000e@I?T\u001aWTi\u001a") + var24 + a("\u0015\u0006,O\f]Tg"));
                  System.exit(1);
               }
            }
         }

         var21.loadAgentConfig(var8);
         var21.loadNetConfig(var8);
         if (System.getProperty(a("CS X\n"), (String)null) == null) {
            label105: {
               if (var21.getPort() > 0) {
                  System.out.println(a("~O:I\u001b\\O'Z^]Him\u0011@Rs\u001d") + var23);
                  if (!var13) {
                     break label105;
                  }
               }

               System.out.println(a("8\u0006i\u001d,wg\rd^\u0013\u0007C"));
            }
         }
      } catch (Exception var18) {
         var18.printStackTrace();
         System.out.println(var18);
         return;
      }

      try {
         while(true) {
            Thread.sleep(3000L);
         }
      } catch (Exception var14) {
         System.out.println(var14);
      }
   }

   private static void b() {
      System.out.println(a("8\u0006\u001cn?ucC7^\u0012\u0006i\u001d\u0014SP(\u001d\u0013]H/R\u0006\u001cR&R\u0012YO=\u0013\r\\K9\u0013\u001fUC'IPaH$M?UC'I^iI9I\u0017]H:`^\u000e^$QSQI'[\u0017U\u000b/T\u0012W\u0018C7^vc\u001a~,{v\u001dt1|,C\u001d^\u0012\u0006ii\u0016W\u0006\u001aS\u0013Bg.X\u0010F\u0006(M\u000e^O*\\\n[I'\u001d\u0017A\u0006(\u001d\u0019WH,O\u0017Q\u0006\u001as3bPx\u0011\b\u0000\u0006(S\u001a\u0012Pz\u001d?UC'It\u0012\u0006i\u001d^SA,S\n\u0012U P\u000b^G=R\f\u001c\u0006\u001dU\u001b\u0012U P\u000b^G=R\f\u0012B(I\u001f\u0012O:\u001d\u000e@I?T\u001aWBi_\u0007\u0012G'\u001d&\u007fjC\u001d^\u0012\u0006i^\u0011\\@ Z\u000b@G=T\u0011\\\u0006/T\u0012W\u0006a\u0001\u0006_Jd^\u0011\\@ ZSTO%X@\u001b\u0006>U\u0017QNiT\r\u0012@&O\u0013SR=X\u001a\u0012O'7^\u0012\u0006i\u001d\u001fQE&O\u001aSH*X^EO=U^FN,\u001d3]H/R\u0006\u0012\u0001:S\u0013Bg.X\u0010F\b-I\u001a\u0015\u0006/T\u0012W\bii\u0016W\u0006*R\u0010TO.\u001d\u0018[J,7^\u0012\u0006i\u001d\u001d]H=\\\u0017\\Ui^\u0011\\@ Z\u000b@G=T\u0011\\\u0006 S\u0018]T$\\\n[I'\u001d\u0017\\E%H\u001a[H.\u0007t8\u0006i\u001d^\u0012\u0006i\u001d^\u001f\u0006\u001aH\u000eBI;I\u001bV\u0006\u0004t<\u0012B,[\u0017\\O=T\u0011\\\u0006C\u001d^\u0012\u0006i\u001d^\u0012\u0006d\u001d-|k\u0019\u001d3{diY\u001fFGi\u00153{diK\u001f@O(_\u0012W\u0006?\\\u0012GC:\u0014t\u0012\u0006i\u001d^\u0012\u0006i\u001dS\u0012s\u001ap^gU,O^QI'[\u0017US;\\\n[I'7^\u0012\u0006i\u001d^\u0012\u0006i\u0010^dg\np^QI'[\u0017US;\\\n[I'7^\u0012\u0006i\u001d^\u0012\u0006i\u0010^|I=T\u0018[E(I\u0017]HiI\u001f@A,I\r8\u0006i\u001d^\u0012\u0006i\u001d^\u001f\u0006\nR\u0013_S'T\nK\u0006*R\u0010TO.H\fSR R\u00108,im?`g\u0004x*wt\u001a7t\u0012\u0006i\u001d^\u000e^$QSQI'[\u0017U\u000b/T\u0012W\u0018i\u001dD\u0012~\u0004q^TO%X^QI'I\u001f[H S\u0019\u0012R!X^SA,S\n\u0012B(I\u001f8,ir.fo\u0006s-8,i\u001d^\u0012\u0006d\u0002\u0002GU(Z\u001b\u0012\u0006i\u001d^\u0012\u0006i\u001d^\u0012\u001ciY\u0017AV%\\\u0007\u0012S:\\\u0019W\u0006 S\u0018]T$\\\n[I'7^\u0012\u0006i\u001dSB}&O\no\u0006uM\u0011@Rw\u001d^\u0012\u0006i\u0007^SA,S\n\u0012J N\nWHiM\u0011@Ri\u0015\u0011DC;O\u0017VC:\u001d\u001d]H/T\u0019\u0012@ Q\u001b\u0012V&O\n\u001b,C"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 50;
               break;
            case 1:
               var10003 = 38;
               break;
            case 2:
               var10003 = 73;
               break;
            case 3:
               var10003 = 61;
               break;
            default:
               var10003 = 126;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class Config {
      private SnmpAuditTrailLogger a = null;
      private SnmpAccessControlModel b = null;
      private SnmpSessionConfig c = null;
      private int d = 161;
      private boolean e = true;
      private boolean f = false;
      private InetAddress g = null;
      private TransportProvider[] h = null;
      private SnmpEngine i = null;
      private int j = 1;
      private TransportProvider.Params[] k = null;

      public Config(int var1) {
         this.d = var1;
      }

      public Config(SnmpEngine var1) {
         this.i = var1;
      }

      public Config(TransportProvider[] var1) {
         this.h = var1;
      }

      public Config(TransportProvider.Params[] var1) {
         this.k = var1;
      }

      public void setSessionConfig(SnmpSessionConfig var1) {
         this.c = var1;
      }

      public SnmpSessionConfig getSessionConfig() {
         return this.c;
      }

      public int getPort() {
         return this.d;
      }

      public TransportProvider[] getTransportProviders() {
         return this.h;
      }

      public SnmpEngine getEngine() {
         return this.i;
      }

      public void setNumThreads(int var1) {
         this.j = var1;
      }

      public int getNumThreads() {
         return this.j;
      }

      public InetAddress getInetAddress() {
         return this.g;
      }

      public void setInetAddress(InetAddress var1) {
         this.g = var1;
      }

      public boolean getUdpEnabled() {
         return this.e;
      }

      public void setUdpEnabled(boolean var1) {
         this.e = var1;
      }

      public boolean getTcpEnabled() {
         return this.f;
      }

      public void setTcpEnabled(boolean var1) {
         this.f = var1;
      }

      public void setAccessControlModel(SnmpAccessControlModel var1) {
         this.b = var1;
      }

      public SnmpAccessControlModel getAccessControlModel() {
         return this.b;
      }

      public void setAuditTrailLogger(SnmpAuditTrailLogger var1) {
         this.a = var1;
      }

      public SnmpAuditTrailLogger getAuditTrailLogger() {
         return this.a;
      }

      public TransportProvider.Params[] getTransportProviderParams() {
         return this.k;
      }
   }

   public static class V3Config extends Config {
      private UsmUserSecurityExtension a;
      private boolean e = false;
      private SnmpEngineID b = null;

      public V3Config(int var1, byte[] var2) {
         super(var1);
         this.setEngineID(new SnmpEngineID(var2, true));
      }

      public V3Config(TransportProvider[] var1, byte[] var2) {
         super(var1);
         this.setEngineID(new SnmpEngineID(var2, true));
      }

      public V3Config(int var1, SnmpEngineID var2) {
         super(var1);
         this.setEngineID(var2);
      }

      public V3Config(TransportProvider[] var1, SnmpEngineID var2) {
         super(var1);
         this.setEngineID(var2);
      }

      public V3Config(TransportProvider.Params[] var1, SnmpEngineID var2) {
         super(var1);
         this.setEngineID(var2);
      }

      public V3Config(SnmpEngine var1) {
         super(var1);
      }

      public void setUsmDHEnabled(boolean var1) {
         this.e = var1;
      }

      public boolean getUsmDHEnabled() {
         return this.e;
      }

      public void setEngineID(SnmpEngineID var1) {
         if (!var1.isAuthoritative()) {
            this.b = new SnmpEngineID(var1.getValue(), true);
            if (!SnmpMibNode.b) {
               return;
            }
         }

         this.b = var1;
      }

      public void setUsmUserSecurityExtension(UsmUserSecurityExtension var1) {
         this.a = var1;
      }

      public UsmUserSecurityExtension getUsmUserSecurityExtension() {
         return this.a;
      }

      public SnmpEngineID getEngineID() {
         return this.b;
      }
   }
}
