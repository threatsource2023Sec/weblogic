package monfox.toolkit.snmp.mgr;

import java.io.IOException;
import java.net.InetAddress;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageProcessor;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.util.Queue;
import monfox.toolkit.snmp.util.WorkItem;

final class SnmpNotificationDispatcherImpl implements Runnable {
   int a = 0;
   private Thread b = null;
   private TransportProvider c = null;
   private boolean d = true;
   private Queue e = null;
   private Queue f = null;
   private int g;
   private byte[] h;
   private SnmpBuffer i;
   private SnmpEngine j;
   private SnmpMetadata k;
   private SnmpNotificationDispatcher l;
   private SnmpMessageProcessor m;
   private static Logger n = null;

   public SnmpNotificationDispatcherImpl(SnmpNotificationDispatcher var1, SnmpMetadata var2, int var3, InetAddress var4, int var5, int var6) throws SnmpException {
      this.g = Snmp.DEFAULT_RECEIVE_BUFFER_SIZE;
      this.h = new byte[Snmp.DEFAULT_RECEIVE_BUFFER_SIZE];
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = null;
      this.m = null;
      if (n == null) {
         n = Logger.getInstance(a("{c\u001c=JGy\u0018+mKl\u0005$kFI\u0018>tIy\u0012%aZ"));
      }

      this.setReceiveBufferSize(var6);
      this.a = 0;
      this.l = var1;
      this.k = var2;
      this.j = new SnmpEngine(var2);
      this.m = this.j.getMessageProcessor();
      this.c = TransportProvider.newInstance(var5, var4, var3);
      int var7 = Integer.getInteger(a("Fb\u0005$bAn\u00109mGc5$wXl\u0005.lM\u007f!?mG\u007f\u00189}"), new Integer(6));
      this.b = new Thread(this);
      this.b.setDaemon(true);
      this.b.setPriority(var7);
      this.b.start();
      int var8 = Integer.getInteger(a("Fb\u0005$bAn\u00109mGc%%vMl\u0015>"), new Integer(1));
      int var9 = Integer.getInteger(a("Fb\u0005$bAn\u00109mGc!?mG\u007f\u00189}"), new Integer(5));
      this.f = new Queue(a("m{\u0014#px\u007f\u001e.a[~\u001e?"), var8, var9);
      int var10 = Integer.getInteger(a("Jx\u0017+aZY\u0019?aIi\u0002"), new Integer(-1));
      int var11 = Integer.getInteger(a("Jx\u0017+aZ]\u0003$kZd\u00054"), new Integer(5));
      if (var10 > 0) {
         this.e = new Queue(a("jx\u0017+aZ\\\u0004(qM"), var10, var11);
      }

   }

   void a() {
      this.d = false;
      if (this.b != null) {
         this.b.interrupt();
      }

      try {
         if (this.c != null) {
            this.c.shutdown();
         }
      } catch (Exception var2) {
         SnmpFramework.handleException(this, var2);
      }

      if (this.f != null) {
         this.f.shutdown();
      }

      if (this.e != null) {
         this.e.shutdown();
      }

   }

   protected void finalize() throws Throwable {
   }

   public void sendInformResponse(TransportEntity var1, SnmpRequestPDU var2, SnmpVarBindList var3, int var4, int var5) throws SnmpException {
      SnmpRequestPDU var6 = new SnmpRequestPDU();
      var6.setVersion(1);
      var6.setVarBindList(var3);
      var6.setType(162);
      var6.setErrorStatus(var4);
      var6.setErrorIndex(var5);
      SnmpMessage var7 = new SnmpMessage();
      SnmpMessageProfile var8 = new SnmpMessageProfile(1, 2, 0, new String(var2.getCommunity()));
      var7.setMessageProfile(var8);
      int var9 = var2.getRequestId();
      SnmpContext var10 = new SnmpContext();
      var7.setContext(var10);
      var7.setSnmpEngineID(var10.getContextEngineID());
      var7.setVersion(var8.getSnmpVersion());
      var7.setMsgID(var9);
      var6.setRequestId(var9);
      var7.setData(var6);
      var6.setVersion(1);
      this.j.send(var7, var1);
   }

   public void run() {
      while(true) {
         if (this.d) {
            try {
               this.b();
               continue;
            } catch (IOException var2) {
               SnmpFramework.handleException(this, var2);
               continue;
            } catch (SnmpException var3) {
               SnmpFramework.handleException(this, var3);
               if (!SnmpSession.B) {
                  continue;
               }
            }
         }

         return;
      }
   }

   private void b() throws IOException, SnmpException {
      if (this.e != null) {
         byte[] var1 = this.h;
         SnmpBuffer var2 = new SnmpBuffer(var1, 0, var1.length);
         TransportEntity var3 = this.c.receive(var2, true);
         ++this.a;
         byte[] var4 = new byte[var2.length];
         System.arraycopy(var2.data, var2.offset, var4, 0, var2.length);
         var2.data = var4;
         var2.offset = 0;
         var2.length = var4.length;
         var2.timestamp = System.currentTimeMillis();
         this.e.put(new BufferWorkItem(var2, var3));
         if (!SnmpSession.B) {
            return;
         }
      }

      SnmpBuffer var5 = this.i;
      TransportEntity var6 = this.c.receive(var5, true);
      var5.timestamp = System.currentTimeMillis();
      this.a(var5, var6);
   }

   void a(SnmpBuffer var1, TransportEntity var2) throws IOException, SnmpException {
      if (n.isDebugEnabled()) {
         n.debug(a("zH2\bM~H5m>\b%") + var1 + ")");
      }

      SnmpMessage var3 = this.m.decodeMessage(var1);
      SnmpPDU var4 = var3.getData();
      var4.setTimestamp(var1.timestamp);
      if (n.isDebugEnabled()) {
         n.debug(a("zH2\bM~H5mTlXKm,") + var4 + ")");
      }

      this.f.put(new o(this.l, var4, var2));
   }

   public synchronized void setReceiveBufferSize(int var1) {
      this.g = var1;
      this.h = new byte[this.g];
      this.i = new SnmpBuffer(this.h, 0, this.h.length);
   }

   public int getReceiveBufferSize() {
      return this.g;
   }

   public boolean isActive() {
      return this.d;
   }

   public Thread getThread() {
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
               var10003 = 40;
               break;
            case 1:
               var10003 = 13;
               break;
            case 2:
               var10003 = 113;
               break;
            case 3:
               var10003 = 77;
               break;
            default:
               var10003 = 4;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class BufferWorkItem extends WorkItem {
      SnmpBuffer a = null;
      TransportEntity b = null;

      BufferWorkItem(SnmpBuffer var2, TransportEntity var3) {
         this.a = var2;
         this.b = var3;
      }

      public void perform() {
         try {
            SnmpNotificationDispatcherImpl.this.a(this.a, this.b);
         } catch (SnmpException var2) {
            SnmpFramework.handleException(this, var2);
         } catch (IOException var3) {
            SnmpFramework.handleException(this, var3);
         }

      }
   }
}
