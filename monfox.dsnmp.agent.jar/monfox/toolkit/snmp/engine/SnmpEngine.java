package monfox.toolkit.snmp.engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;

public class SnmpEngine implements Serializable {
   static final long serialVersionUID = -507892421245852147L;
   long a;
   int b;
   int c;
   int d;
   int e;
   int f;
   int g;
   private SnmpOidMap.Manager h;
   private static Logger i = null;
   private int j;
   private SnmpMessageListener k;
   private SnmpMessageListener l;
   private SnmpMessageListener m;
   private SnmpMessageListener n;
   private SnmpErrorListener o;
   private SnmpEngineID p;
   private int q;
   private SnmpMessageProcessor r;
   private Vector s;
   Monitor t;

   public SnmpEngine() {
      this((SnmpEngineID)null, (SnmpMetadata)null);
   }

   public SnmpEngine(SnmpMetadata var1) {
      this((SnmpEngineID)null, var1);
   }

   public SnmpEngine(SnmpEngineID var1) {
      this(var1, (SnmpMetadata)null);
   }

   public SnmpEngine(SnmpEngineID var1, SnmpMetadata var2) {
      boolean var3;
      label33: {
         var3 = SnmpPDU.i;
         super();
         this.a = -1L;
         this.b = 0;
         this.c = 0;
         this.d = 0;
         this.e = 0;
         this.f = 0;
         this.g = 0;
         this.h = null;
         this.j = Snmp.DEFAULT_MAX_SIZE;
         this.k = null;
         this.l = null;
         this.m = null;
         this.n = null;
         this.o = null;
         this.p = null;
         this.q = 9999;
         this.r = null;
         this.s = new Vector();
         this.t = null;
         if (var1 == null || var1.getValue().length == 0) {
            this.p = null;
            if (!var3) {
               break label33;
            }
         }

         this.p = var1;
      }

      this.r = new SnmpMessageProcessor(this, var2);
      this.a = System.currentTimeMillis();
      if (i == null) {
         i = Logger.getInstance(a("U\u001b>QRh\u0012:Or"));
      }

      if (SnmpException.b) {
         SnmpPDU.i = !var3;
      }

   }

   public void setOidMapManager(SnmpOidMap.Manager var1) {
      this.h = var1;
   }

   public SnmpOidMap.Manager getOidMapManager() {
      return this.h;
   }

   public SnmpMessageProcessor getMessageProcessor() {
      return this.r;
   }

   public void addEventListener(SnmpMessageListener var1) {
      this.n = monfox.toolkit.snmp.engine.c.add(this.n, var1);
   }

   public void removeEventListener(SnmpMessageListener var1) {
      this.n = monfox.toolkit.snmp.engine.c.remove(this.n, var1);
   }

   public boolean containsEventListener(SnmpMessageListener var1) {
      return monfox.toolkit.snmp.engine.c.contains(this.n, var1);
   }

   public void addReportListener(SnmpMessageListener var1) {
      this.k = monfox.toolkit.snmp.engine.c.add(this.k, var1);
   }

   public void removeReportListener(SnmpMessageListener var1) {
      this.k = monfox.toolkit.snmp.engine.c.remove(this.k, var1);
   }

   public void addRequestListener(SnmpMessageListener var1) {
      this.l = monfox.toolkit.snmp.engine.c.add(this.l, var1);
   }

   public void removeRequestListener(SnmpMessageListener var1) {
      this.l = monfox.toolkit.snmp.engine.c.remove(this.l, var1);
   }

   public boolean containsRequestListener(SnmpMessageListener var1) {
      return monfox.toolkit.snmp.engine.c.contains(this.l, var1);
   }

   public void addResponseListener(SnmpMessageListener var1) {
      this.m = monfox.toolkit.snmp.engine.c.add(this.m, var1);
   }

   public void removeResponseListener(SnmpMessageListener var1) {
      this.m = monfox.toolkit.snmp.engine.c.remove(this.m, var1);
   }

   public boolean containsResponseListener(SnmpMessageListener var1) {
      return monfox.toolkit.snmp.engine.c.contains(this.m, var1);
   }

   public void addErrorListener(SnmpErrorListener var1) {
      this.o = monfox.toolkit.snmp.engine.b.add(this.o, var1);
   }

   public void removeErrorListener(SnmpErrorListener var1) {
      this.o = monfox.toolkit.snmp.engine.b.remove(this.o, var1);
   }

   public boolean containsErrorListener(SnmpErrorListener var1) {
      return monfox.toolkit.snmp.engine.b.contains(this.o, var1);
   }

   public void dispatchMessage(TransportEntity var1, SnmpMessage var2) {
      SnmpOidMap.Manager var3 = this.h;
      if (var3 != null) {
         try {
            var3.processIncoming(var2);
         } catch (Exception var8) {
            i.error(a("c\u0007!Ne&\u0005!Ntc\u0006 HyaU\u0000Ozv::EZg\u0005}lvh\u00144De(\u0005!Ntc\u0006 hye\u001a>Hya"), var8);
         }
      }

      SnmpPDU var4 = var2.getData();
      SnmpMessageListener var5 = null;
      switch (var4.getType()) {
         case 162:
            var5 = this.m;
            break;
         case 163:
         case 165:
         default:
            var5 = this.l;
            break;
         case 164:
         case 166:
         case 167:
            var5 = this.n;
            break;
         case 168:
            var5 = this.k;
      }

      if (var5 != null) {
         try {
            var5.handleMessage(var2, var1);
         } catch (RuntimeException var7) {
            i.debug(a("b\u001c Qvr\u0016;lru\u00062Fr"), var7);
         }
      }

   }

   public void dispatchSecurityError(SnmpBuffer var1, TransportEntity var2, int var3, int var4, SnmpCoderException var5) {
      if (this.o != null) {
         try {
            this.o.handleError(var1, var2, var3, var4, var5);
         } catch (RuntimeException var7) {
            i.debug(a("b\u001c Qvr\u0016;det\u001a!"), var7);
         }
      }

   }

   public void send(SnmpMessage var1, TransportEntity var2) throws SnmpTransportException, SnmpCoderException {
      boolean var8;
      label90: {
         var8 = SnmpPDU.i;
         SnmpOidMap.Manager var3 = this.h;
         if (var3 != null) {
            try {
               var3.processOutgoing(var1);
               break label90;
            } catch (Exception var11) {
               i.error(a("c\u0007!Ne&\u0005!Ntc\u0006 HyaU\u0000Ozv::EZg\u0005}lvh\u00144De(\u0005!Ntc\u0006 nbr\u0012<Hya"), var11);
               if (!var8) {
                  break label90;
               }
            }
         }

         if (var1.getOidMap() != null) {
            if (i.isDebugEnabled()) {
               i.debug(a("s\u0006:Op&\u001a:E7k\u0014#\u001b7") + var1.getOidMap().getMapName());
            }

            try {
               if (var1.getData() != null && var1.getData().getVarBindList() != null) {
                  SnmpVarBindList var4 = var1.getOidMap().translate(var1.getData().getVarBindList(), false);
                  if (var4 != null) {
                     var1.getData().setVarBindList(var4);
                  }
               }
            } catch (Exception var9) {
               i.error(a("c\u0007!Ne&\u0005!Ntc\u0006 HyaU\u0000Ozv::EZg\u0005}lvh\u00144De(\u0005!Ntc\u0006 hye\u001a>Hya"), var9);
            }
         }
      }

      TransportProvider var12;
      int var10000;
      label93: {
         var12 = var2.getProvider();
         if (var12 == null) {
            Enumeration var5 = this.s.elements();

            while(var5.hasMoreElements()) {
               TransportProvider var6 = (TransportProvider)var5.nextElement();

               label77: {
                  try {
                     var10000 = var6.getTransportType();
                     if (var8) {
                        break label93;
                     }

                     if (var10000 != var2.getTransportType() && !var6.getTransportDomain().equals(var2.getTransportDomain())) {
                        break label77;
                     }

                     var12 = var6;
                  } catch (Exception var10) {
                     break label77;
                  }

                  if (!var8) {
                     break;
                  }
               }

               if (var8) {
                  break;
               }
            }
         }

         if (var12 == null) {
            i.error(a("h\u001asUeg\u001b Qxt\u0001sQei\u0003:ErtU5Nbh\u0011sGxtU'Svh\u0006#NerU7Nzg\u001c=\u001b7") + var2.getTransportDomain());
            throw new SnmpTransportException(a("h\u001asUeg\u001b Qxt\u0001sQei\u0003:ErtU5Nbh\u0011sGxtU'Svh\u0006#NerU7Nzg\u001c=\u001b7") + var2.getTransportDomain());
         }

         var10000 = var2.getMaxSize();
      }

      int var13 = var10000;
      if (var13 <= 0) {
         var13 = this.getMaxSize();
      }

      if (var1.getMaxSize() < 484) {
         var1.setMaxSize(this.getMaxSize());
      }

      if (i.isDebugEnabled()) {
         i.debug(a("C;\u0010nSO;\u0014\u0001DH8\u0003\u0001ZU2i") + var1);
      }

      SnmpBuffer var14 = this.r.encodeMessage(var1, var13);
      if (this.t != null) {
         this.t.outgoingMessage(var1, var14, var2);
      }

      var12.send(var14, var2);
   }

   public Thread addTransportProvider(TransportProvider var1) {
      this.s.addElement(var1);
      if (i.isDebugEnabled()) {
         i.debug(a("G\u00117Ds&!!@yu\u0005<ScV\u0007<W~b\u0010!\t") + var1 + ")");
      }

      if (var1.isPushProvider()) {
         var1.a((TransportProvider.PushListener)(new PushHandler()));
         return null;
      } else {
         d var2 = new d(this, var1);
         Thread var3 = new Thread(var2);
         var3.setDaemon(true);
         var3.start();
         return var3;
      }
   }

   public void removeTransportProvider(TransportProvider var1) {
      if (i.isDebugEnabled()) {
         i.debug(a("T\u0010>Nao\u001b4\u0001Ct\u0014=Rgi\u0007'qei\u0003:Ert]") + var1 + ")");
      }

      this.s.removeElement(var1);

      try {
         var1.shutdown();
      } catch (SnmpTransportException var3) {
         i.debug(a("R\u00072Odv\u001a!UGt\u001a%Hsc\u0007}R\u007fs\u00017N`h]z\u0001r~\u00166Qco\u001a="), var3);
      }

   }

   public Enumeration getTransportProviders() {
      return this.s.elements();
   }

   public int getSnmpInPkts() {
      return this.c;
   }

   public int getSnmpInASNParseErrs() {
      return this.d;
   }

   public int getSnmpInBadVersions() {
      return this.e;
   }

   public int getSnmpSilentDrops() {
      return this.f;
   }

   public int getSnmpProxyDrops() {
      return this.g;
   }

   public void incSnmpInBadVersions() {
      ++this.e;
   }

   public int getSysUpTime() {
      return (int)((System.currentTimeMillis() - this.a) / 10L);
   }

   public void setMaxSize(int var1) {
      this.j = var1;
   }

   public int getMaxSize() {
      return this.j;
   }

   public SnmpEngineID getEngineID() {
      return this.p;
   }

   public int getEngineBoots() {
      return this.q;
   }

   public void setEngineBoots(int var1) {
      this.q = var1;
   }

   public void setEngineTime(int var1) {
      this.a = System.currentTimeMillis();
      this.b = var1;
   }

   public int getEngineTime() {
      return this.b + this.getSysUpTime() / 100;
   }

   public void initEngineBoots(String var1) throws IOException, NumberFormatException {
      try {
         FileInputStream var2 = new FileInputStream(var1);
         Properties var3 = new Properties();
         var3.load(var2);
         var2.close();
         int var4 = Integer.parseInt(var3.getProperty(a("c\u001b4Hyc7<Ncu"), "1"));
         this.q = var4 + 1;
         i.debug(a("J:\u0012eRBU\u0016oPO;\u0016\u0001UI:\u0007r-&") + this.q);
      } catch (Exception var5) {
         this.q = 1;
      }

      Properties var6 = new Properties();
      var6.setProperty(a("c\u001b4Hyc7<Ncu"), "" + this.q);
      FileOutputStream var7 = new FileOutputStream(var1);
      var6.save(var7, a("U\u001b>Q7C\u001b4HycU\u001aOqi"));
      var7.close();
   }

   public void setMonitor(Monitor var1) {
      this.t = var1;
   }

   public Monitor getMonitor() {
      return this.t;
   }

   void a(TransportEntity var1, SnmpBuffer var2) {
      long var3 = System.currentTimeMillis();
      ++this.c;

      try {
         SnmpMessage var5 = this.r.decodeMessage(var2);
         var5.setTimestamp(var3);
         if (i.isDebugEnabled()) {
            i.debug(a("B0\u0010nSC1srYK%slDAOs") + var5);
         }

         if (this.t != null) {
            this.t.incomingMessage(var5, var2, var1, (String)null);
         }

         this.dispatchMessage(var1, var5);
      } catch (SnmpBadVersionException var7) {
         if (this.t != null) {
            this.t.incomingMessage((SnmpMessage)null, var2, var1, a("d\u00147\u0001ac\u0007 Hxh"));
         }

         if (i.isDebugEnabled()) {
            i.debug(a("D4\u0017\u0001AC'\u0000hXHU\u001ao7U;\u001eq7K&\u0014"), var7);
         }

         ++this.e;
      } catch (SnmpSecurityCoderException var8) {
         if (this.t != null) {
            this.t.incomingMessage((SnmpMessage)null, var2, var1, a("u\u00100Teo\u0001*\u0001rt\u0007<S"));
         }

         if (i.isDebugEnabled()) {
            i.debug(a("U0\u0010tEO!\n\u0001RT'\u001cs7O;srYK%slDA"), var8);
         }

         this.dispatchSecurityError(var2, var1, var8.getSpecificError(), var8.getMsgId(), var8);
      } catch (SnmpCoderException var9) {
         if (this.t != null) {
            this.t.incomingMessage((SnmpMessage)null, var2, var1, a("d\u00147\u0001rh\u0016<E~h\u0012"));
         }

         if (i.isDebugEnabled()) {
            i.debug(a("E:\u0017hYAU\u0016sEI'shY&&\u001dlG&8\u0000f"), var9);
         }

         ++this.d;
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
               var10003 = 6;
               break;
            case 1:
               var10003 = 117;
               break;
            case 2:
               var10003 = 83;
               break;
            case 3:
               var10003 = 33;
               break;
            default:
               var10003 = 23;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class PushHandler implements TransportProvider.PushListener {
      public void pushMessage(TransportEntity var1, SnmpBuffer var2) {
         SnmpEngine.this.a(var1, var2);
      }
   }

   public interface Monitor {
      void outgoingMessage(SnmpMessage var1, SnmpBuffer var2, TransportEntity var3);

      void incomingMessage(SnmpMessage var1, SnmpBuffer var2, TransportEntity var3, String var4);
   }
}
