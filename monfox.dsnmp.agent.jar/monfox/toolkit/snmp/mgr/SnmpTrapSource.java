package monfox.toolkit.snmp.mgr;

import java.net.InetAddress;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpCoderException;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.SnmpTransportException;
import monfox.toolkit.snmp.engine.SnmpTrapPDU;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.mgr.usm.Usm;

public class SnmpTrapSource {
   private RequestIdGenerator a = null;
   private TransportProvider b = null;
   private Usm c = null;
   private SnmpEngine d = null;
   private int e = 100;
   private int f;
   private static Logger g = null;

   public SnmpTrapSource() throws SnmpTransportException {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = new SnmpEngine();
      this.b = TransportProvider.newInstance(1, (InetAddress)null, -1);
      this.d.addTransportProvider(this.b);
      this.a();
   }

   private void a() {
      if (g == null) {
         g = Logger.getInstance(a("\\1\u0019B4}>\u0004a\u000fz-\u0017W"));
      }

      try {
         this.c = new Usm(this.d, (SnmpSession)null);
      } catch (Exception var2) {
         g.error(a("Z\f9\u0012\ta6\u0000[\u0001c6\u000eS\u0014f0\u001a\u0012\u0005}-\u001b@"), var2);
      }

   }

   public SnmpTrapSource(int var1) throws SnmpTransportException {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = new SnmpEngine();
      this.b = TransportProvider.newInstance(1, (InetAddress)null, var1);
      this.d.addTransportProvider(this.b);
      this.a();
   }

   public SnmpTrapSource(InetAddress var1, int var2) throws SnmpTransportException {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = new SnmpEngine();
      this.b = TransportProvider.newInstance(1, var1, var2);
      this.d.addTransportProvider(this.b);
      this.a();
   }

   public SnmpTrapSource(SnmpMetadata var1) throws SnmpTransportException {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = new SnmpEngine(var1);
      this.b = TransportProvider.newInstance(1, (InetAddress)null, -1);
      this.d.addTransportProvider(this.b);
      this.a();
   }

   public SnmpTrapSource(SnmpMetadata var1, int var2) throws SnmpTransportException {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = new SnmpEngine(var1);
      this.b = TransportProvider.newInstance(1, (InetAddress)null, var2);
      this.d.addTransportProvider(this.b);
      this.a();
   }

   public SnmpTrapSource(SnmpMetadata var1, InetAddress var2, int var3) throws SnmpTransportException {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = new SnmpEngine(var1);
      this.b = TransportProvider.newInstance(1, var2, var3);
      this.d.addTransportProvider(this.b);
      this.a();
   }

   public SnmpTrapSource(SnmpMetadata var1, SnmpEngineID var2, InetAddress var3, int var4) throws SnmpTransportException {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = new SnmpEngine(var2, var1);
      this.b = TransportProvider.newInstance(1, var3, var4);
      this.d.addTransportProvider(this.b);
      this.a();
   }

   public SnmpTrapSource(SnmpMetadata var1, SnmpEngineID var2, TransportProvider var3) throws SnmpTransportException {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = new SnmpEngine(var2, var1);
      this.b = var3;
      this.d.addTransportProvider(var3);
      this.a();
   }

   public SnmpTrapSource(SnmpEngine var1) {
      this.f = Snmp.DEFAULT_MAX_SIZE;
      this.d = var1;
      this.a();
   }

   public void sendTrapV2(SnmpVarBindList var1, SnmpPeer var2) throws SnmpException, SnmpTransportException, SnmpCoderException {
      SnmpRequestPDU var3 = new SnmpRequestPDU();
      var3.setVersion(1);
      var3.setVarBindList(var1);
      var3.setType(167);
      this.sendTrapV2(var3, var2);
   }

   public void sendTrapV2(SnmpRequestPDU var1, SnmpPeer var2) throws SnmpException, SnmpTransportException, SnmpCoderException {
      boolean var8 = SnmpSession.B;
      SnmpMessage var3 = new SnmpMessage();
      SnmpParameters var4 = var2.getParameters();
      SnmpMessageProfile var5 = var4.getInformProfile();
      if (var5 == null) {
         throw new SnmpException(a("A0Ta\u0005l*\u0006[\u0014v\u007f$S\u0012n2\u0011F\u0005},T\u001a\ta9\u001b@\r&\u007f\u0004@\u000fy6\u0010W\u0004"));
      } else {
         int var6;
         label30: {
            var3.setMessageProfile(var5);
            var3.setUserTable(var4.getUserTable());
            var6 = this.a(var2);
            SnmpContext var7;
            if (var2.getParameters() != null && var2.getParameters().getContext() != null) {
               var7 = var2.getParameters().getContext();
               var3.setContext(var7);
               if (!var8) {
                  break label30;
               }
            }

            var7 = var2.getDefaultContext();
            var3.setContext(var7);
         }

         label24: {
            if (this.d.getEngineID() != null && this.d.getEngineID().isAuthoritative()) {
               var3.setSnmpEngineID(this.d.getEngineID());
               if (!var8) {
                  break label24;
               }
            }

            var3.setSnmpEngineID(var2.getSnmpEngineID());
         }

         if (var5.getSnmpVersion() == 0) {
            throw new SnmpException(a("B:\u0007A\u0001h:Tb\u0012`9\u001d^\u0005/,\u0004W\u0003f9\u001dW\u0013/\f\u001a_\u0010YnX\u0012#n1\u001a]\u0014/,\u0011\\\u0004/)F\u0012\u0014}>\u0004\u0012\u0017f+\u001c\u0012\u0016>\u007f\u0004@\u000fi6\u0018WN\u0005\u001c\u001cS\u000eh:Ts\u0007j1\u0000\u00120j:\u0006\u0015\u0013/\t\u0011@\u0013f0\u001a\u0012\u0014`\u007f'\\\r\u007fq\u0007\\\r\u007f\tF"));
         } else {
            var3.setVersion(var5.getSnmpVersion());
            var3.setMsgID(var6);
            var1.setRequestId(var6);
            var3.setData(var1);
            var1.setVersion(1);
            this.d.send(var3, var2.getTransportEntity());
         }
      }
   }

   public void sendTrapV1(SnmpOid var1, SnmpIpAddress var2, int var3, int var4, int var5, SnmpVarBindList var6, SnmpPeer var7) throws SnmpException {
      SnmpTrapPDU var8 = new SnmpTrapPDU();
      var8.setEnterprise(var1);
      var8.setAgentAddr(var2);
      var8.setGenericTrap(var3);
      var8.setSpecificTrap(var4);
      var8.setTimestamp(var5);
      var8.setVarBindList(var6);
      this.sendTrapV1(var8, var7);
   }

   public void sendTrapV1(SnmpTrapPDU var1, SnmpPeer var2) throws SnmpException {
      SnmpMessage var3 = new SnmpMessage();
      SnmpParameters var4 = var2.getParameters();
      SnmpMessageProfile var5 = var4.getInformProfile();
      if (var5 == null) {
         throw new SnmpException(a("A0Ta\u0005l*\u0006[\u0014v\u007f$S\u0012n2\u0011F\u0005},T\u001a\ta9\u001b@\r&\u007f\u0004@\u000fy6\u0010W\u0004"));
      } else {
         var3.setMessageProfile(var5);
         int var6 = this.a(var2);
         SnmpContext var7 = var2.getDefaultContext();
         var3.setContext(var7);
         var3.setSnmpEngineID(var2.getSnmpEngineID());
         var3.setVersion(var5.getSnmpVersion());
         var3.setMsgID(var6);
         var1.setRequestId(var6);
         var3.setData(var1);
         this.d.send(var3, var2.getTransportEntity());
      }
   }

   public SnmpEngine getSnmpEngine() {
      return this.d;
   }

   public Usm getUsm() {
      return this.c;
   }

   public void setMaxSize(int var1) {
      this.f = var1;
   }

   public int getMaxSize() {
      return this.f;
   }

   public void setNextId(int var1) {
      this.e = var1;
   }

   public void shutdown() {
      TransportProvider var1 = this.b;
      if (var1 != null) {
         try {
            var1.shutdown();
         } catch (Exception var3) {
            SnmpFramework.handleException(this, var3);
         }

         this.b = null;
      }

   }

   public RequestIdGenerator getRequestIdGenerator() {
      return this.a;
   }

   public void setRequestIdGenerator(RequestIdGenerator var1) {
      this.a = var1;
   }

   private synchronized int a(SnmpPeer var1) {
      RequestIdGenerator var2 = this.a;
      if (var2 != null) {
         try {
            return var2.getNextRequestId(var1);
         } catch (Exception var4) {
            g.error(a("j'\u0017W\u0010{6\u001b\\@f1TU\u0005a:\u0006S\u0014f1\u0013\u0012\u0012j.\u0001W\u0013{\u007f\u001dV"), var4);
            return this.e++;
         }
      } else {
         return this.e++;
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
               var10003 = 15;
               break;
            case 1:
               var10003 = 95;
               break;
            case 2:
               var10003 = 116;
               break;
            case 3:
               var10003 = 50;
               break;
            default:
               var10003 = 96;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
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
               if (!SnmpSession.B) {
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
