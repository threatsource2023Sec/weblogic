package monfox.toolkit.snmp.engine;

import java.net.InetAddress;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.util.ParamUtil;
import monfox.toolkit.snmp.util.SimpleQueue;

public class BufferedTransportProvider extends TransportProvider {
   public static int DEFAULT_MAX_QUEUE_LENGTH = 100;
   private static int a = 10;
   private int b;
   private SimpleQueue c;
   private int d;
   private Thread e;
   private TransportProvider f;
   private static Logger g = null;
   private boolean h;
   private boolean i;
   private static final String j = "$Id: BufferedTransportProvider.java,v 1.4 2013/09/06 22:17:00 sking Exp $";

   public BufferedTransportProvider(TransportProvider var1) {
      this.b = Snmp.DEFAULT_RECEIVE_BUFFER_SIZE;
      this.c = null;
      this.d = a;
      this.e = null;
      this.f = null;
      this.h = true;
      this.i = true;
      if (g == null) {
         g = Logger.getInstance(b("PaH7["), b("Q|A3EQ"), b("VG`\u001cnfWb.yu\\u\ndfFV\bdb[b\u001fy"));
      }

      this.f = var1;
      this.c = new SimpleQueue(b("VG`\u001cnfWb.yu\\u\ndfFV\bdb[b\u001fy:cs\u001f~q"));
      this.c.setMaxSize(DEFAULT_MAX_QUEUE_LENGTH);
      this.i = true;
   }

   public int getTransportType() {
      return this.f.getTransportType();
   }

   public void setMaxQueueLength(int var1) {
      if (var1 == 0) {
         var1 = DEFAULT_MAX_QUEUE_LENGTH;
      }

      this.c.setMaxSize(var1);
   }

   public void setReceiveBufferSize(int var1) {
      this.b = var1;
   }

   public int getReceiveBufferSize() {
      return this.b;
   }

   public void initialize(InetAddress var1, int var2) throws SnmpTransportException {
      this.f.initialize(var1, var2);
      this.e = new Thread(new Worker());
      this.e.setPriority(this.d);
      this.e.setDaemon(true);
      this.e.start();
   }

   public void setThreadPriority(int var1) {
      this.d = var1;
      if (this.e != null) {
         this.e.setPriority(var1);
      }

   }

   public Thread getThread() {
      return this.e;
   }

   public TransportProvider getProvider() {
      return this.f;
   }

   public boolean isActive() {
      return this.h;
   }

   public void shutdown() throws SnmpTransportException {
      this.h = false;
      if (this.e != null) {
         this.e.interrupt();
      }

      this.f.shutdown();
   }

   public Object send(Object var1, TransportEntity var2) throws SnmpTransportException {
      return this.f.send(var1, var2);
   }

   public TransportEntity receive(SnmpBuffer var1, boolean var2) throws SnmpTransportException {
      if ((!this.i || !this.h) && this.c.size() == 0) {
         return this.f.receive(var1, var2);
      } else {
         try {
            BufferInfo var3 = (BufferInfo)this.c.popFront();
            var1.data = var3.a;
            var1.length = var3.b;
            var1.offset = 0;
            var1.timestamp = var3.c;
            return var3.d;
         } catch (InterruptedException var4) {
            throw new SnmpTransportException(b("]\\r\u001fyfGv\u000enp"));
         }
      }
   }

   public void setParameter(String var1, Object var2) {
      if (!ParamUtil.setParameter(g, this, var1, var2)) {
         if (this.f != null) {
            this.f.setParameter(var1, var2);
         }

      }
   }

   public void setDefaultThreadPriority(int var1) {
      a = var1;
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 20;
               break;
            case 1:
               var10003 = 50;
               break;
            case 2:
               var10003 = 6;
               break;
            case 3:
               var10003 = 122;
               break;
            default:
               var10003 = 11;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class Worker implements Runnable {
      public void run() {
         boolean var4 = SnmpPDU.i;
         BufferedTransportProvider.g.debug(a("-wh\u0003\u0000(8i\u001c\u0004(l\u007f\f"));

         try {
            while(BufferedTransportProvider.this.h) {
               SnmpBuffer var1 = new SnmpBuffer((byte[])null, 0, 0);

               try {
                  byte[] var2 = new byte[BufferedTransportProvider.this.b];
                  var1.data = var2;
                  var1.length = BufferedTransportProvider.this.b;
                  TransportEntity var3 = BufferedTransportProvider.this.f.receive(var1, true);
                  if (var4) {
                     break;
                  }

                  if (BufferedTransportProvider.g.isDebugEnabled()) {
                     BufferedTransportProvider.g.debug(a("8m|\u000e\u0000(qt\u000fE*yy\u0003\u0000.8A\u001b\f }'") + var1.length + a("v8n\u0001\b?%") + var1.timestamp + a("\u00078|\u001a\n7\":") + var3);
                  }

                  BufferedTransportProvider.this.c.pushBack(BufferedTransportProvider.this.new BufferInfo(var1.data, var1.length, var1.timestamp, var3));
                  if (BufferedTransportProvider.g.isDebugEnabled()) {
                     BufferedTransportProvider.g.debug(a("+m\u007f\u001d\u0000wks\u0012\u0000g") + BufferedTransportProvider.this.c.size());
                  }
               } catch (SnmpTransportException var5) {
                  BufferedTransportProvider.g.debug(a("\u001fjh\u0007\u0017zqtH\u0011(yt\u001b\u00155jnH\u0015(wl\u0001\u0001?j"), var5);
               }

               if (var4) {
                  break;
               }
            }
         } catch (Exception var6) {
            BufferedTransportProvider.g.debug(a("\u0018m|\u000e\u0000(}~H1(yt\u001b\u00155jnH5(wl\u0001\u0001?j:-\u001d9}j\u001c\f5v"), var6);
            BufferedTransportProvider.this.h = false;
            BufferedTransportProvider.this.i = false;
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
                  var10003 = 90;
                  break;
               case 1:
                  var10003 = 24;
                  break;
               case 2:
                  var10003 = 26;
                  break;
               case 3:
                  var10003 = 104;
                  break;
               default:
                  var10003 = 101;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class BufferInfo {
      byte[] a = null;
      int b;
      long c;
      TransportEntity d = null;

      BufferInfo(byte[] var2, int var3, long var4, TransportEntity var6) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
         this.d = var6;
      }
   }
}
