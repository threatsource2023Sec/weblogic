package monfox.toolkit.snmp.agent.x.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.agent.x.common.AgentXServerSocketFactory;
import monfox.toolkit.snmp.agent.x.master.MasterAgentX;
import monfox.toolkit.snmp.agent.x.pdu.AgentXPDU;

public class MasterAgentXServer {
   private int a;
   private InetAddress b;
   private int c;
   private Thread d;
   private boolean e;
   private int f;
   private AgentXConnection.RequestListener g;
   private AgentXConnection.StatusListener h;
   private RequestListener i;
   private StatusListener j;
   private ServerSocket k;
   private List l;
   private Logger m;

   public MasterAgentXServer(int var1, int var2) throws IOException {
      this((InetAddress)null, var1, var2);
   }

   public MasterAgentXServer(InetAddress var1, int var2, int var3) {
      int var4 = AgentXConnection.q;
      super();
      this.a = 10485760;
      this.b = null;
      this.c = -1;
      this.d = null;
      this.e = true;
      this.f = 10;
      this.g = null;
      this.h = null;
      this.i = null;
      this.j = null;
      this.l = new Vector();
      this.m = Logger.getInstance(a("\rI\u001cN4"), a("\b]\u0017M0dB"), a("\u0004{!w\u0001;[5f\n=B\u0001f\u0016?\u007f "));
      this.b = null;
      this.c = var2;
      this.g = new XRequestListener();
      this.h = new XStatusListener();
      this.f = var3;
      if (SnmpException.b) {
         ++var4;
         AgentXConnection.q = var4;
      }

   }

   private void a() throws IOException {
      int var2 = AgentXConnection.q;
      AgentXServerSocketFactory var1 = MasterAgentX.getServerSocketFactory();
      if (var1 != null) {
         this.m.comms(a("a|3`\u0010&h+*D*h7b\u0010 t5#\u0017,h$f\u0016ii=`\u000f,nh#") + this.b + ":" + this.c);
         this.k = var1.newServerSocket(this.b, this.c, 10);
         if (var2 == 0) {
            return;
         }
      }

      if (this.b == null) {
         this.m.comms(a("*h7b\u0010 t5#\u0017,h$f\u0016ii=`\u000f,nh#Tg*|3Jy") + this.c);
         this.k = new ServerSocket(this.c);
         if (var2 == 0) {
            return;
         }
      }

      this.m.comms(a("*h7b\u0010 t5#\u0017,h$f\u0016ii=`\u000f,nh#") + this.b + ":" + this.c);
      this.k = new ServerSocket(this.c, 10, this.b);
   }

   public List getConnections() {
      return this.l;
   }

   public ServerSocket getServerSocket() {
      return this.k;
   }

   public void shutdown() {
      this.m.comms(a(":r'w\u0000&m<+M"));
      this.e = false;

      try {
         this.k.close();
         this.k = null;
      } catch (Exception var4) {
      }

      if (this.d != null) {
         this.d.interrupt();
         this.d = null;
      }

      Vector var1 = new Vector();
      var1.addAll(this.l);
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         MasterAgentXConnection var3 = (MasterAgentXConnection)var2.next();
         var3.shutdown();
         if (AgentXConnection.q != 0) {
            break;
         }
      }

   }

   public void startup() throws IOException {
      this.m.comms(a(":n3q\u0010<jz*"));
      if (this.k == null) {
         this.a();
         this.e = true;
         this.d = new Thread(new ConnectionProcessor(), a("\u0004{!w\u0001;[5f\n=B\u0001f\u0016?\u007f 9D") + this.b + ":" + this.c);
         this.d.setDaemon(true);
         this.d.start();
      }
   }

   public void setRequestListener(RequestListener var1) {
      this.i = var1;
   }

   public void setStatusListener(StatusListener var1) {
      this.j = var1;
   }

   void a(MasterAgentXConnection var1) {
      this.m.debug(a("(~6@\u000b't7`\u0010 u<9D") + var1);
      this.l.add(var1);
   }

   void b(MasterAgentXConnection var1) {
      this.m.debug(a(";\u007f?l\u0012,Y=m\n,y&j\u000b' r") + var1);
      this.l.remove(var1);
   }

   public int getMaxPayloadLength() {
      return this.a;
   }

   public void setMaxPayloadLength(int var1) {
      this.a = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 73;
               break;
            case 1:
               var10003 = 26;
               break;
            case 2:
               var10003 = 82;
               break;
            case 3:
               var10003 = 3;
               break;
            default:
               var10003 = 100;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class ConnectionProcessor implements Runnable {
      private ConnectionProcessor() {
      }

      public void run() {
         int var3 = AgentXConnection.q;

         try {
            while(MasterAgentXServer.this.e) {
               Socket var1 = MasterAgentXServer.this.k.accept();
               MasterAgentXServer.this.m.comms(a("IsR=\u001fIhU<\u0014\nzN<\u0017\n") + var1 + a("\nnY0\u001fCjY7"));
               if (var3 != 0) {
                  break;
               }

               label31: {
                  if (MasterAgentXServer.this.l.size() >= MasterAgentXServer.this.f) {
                     MasterAgentXServer.this.m.comms(a("G}Ds\u0019ErR6\u0019^uS=R") + MasterAgentXServer.this.f + a("\u0003<N6\u001bItY7V\n\u007fP<\tCr[s\u0019ErR6\u0019^uS=@\n") + var1);

                     try {
                        var1.close();
                        break label31;
                     } catch (Exception var4) {
                        if (var3 == 0) {
                           break label31;
                        }
                     }
                  }

                  MasterAgentXConnection var2 = new MasterAgentXConnection(MasterAgentXServer.this, var1, MasterAgentXServer.this.h, MasterAgentXServer.this.g);
                  var2.setMaxPayloadLength(MasterAgentXServer.this.getMaxPayloadLength());
               }

               if (var3 != 0) {
                  break;
               }
            }
         } catch (Exception var5) {
            MasterAgentXServer.this.m.debug(a("^yN>\u0013D}H:\u0014M<O6\b\\yNs\tE\u007fW6\u000e\nlN<\u0019OoO:\u0014M"), var5);
         }

      }

      // $FF: synthetic method
      ConnectionProcessor(Object var2) {
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
                  var10003 = 42;
                  break;
               case 1:
                  var10003 = 28;
                  break;
               case 2:
                  var10003 = 60;
                  break;
               case 3:
                  var10003 = 83;
                  break;
               default:
                  var10003 = 122;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class XStatusListener implements AgentXConnection.StatusListener {
      private XStatusListener() {
      }

      public void connectionUp(AgentXConnection var1) {
         MasterAgentXServer.this.a((MasterAgentXConnection)var1);
         if (MasterAgentXServer.this.j != null) {
            MasterAgentXServer.this.j.connectionUp(MasterAgentXServer.this, (MasterAgentXConnection)var1);
            if (AgentXConnection.q == 0) {
               return;
            }
         }

         MasterAgentXServer.this.m.error(a("dq\"r\u001ckjwrHfwqu\rd{p!\u001aoykr\u001colge"));
      }

      public void connectionDown(AgentXConnection var1) {
         MasterAgentXServer.this.b((MasterAgentXConnection)var1);
         if (MasterAgentXServer.this.j != null) {
            MasterAgentXServer.this.j.connectionDown(MasterAgentXServer.this, (MasterAgentXConnection)var1);
            if (AgentXConnection.q == 0) {
               return;
            }
         }

         MasterAgentXServer.this.m.error(a("dq\"r\u001ckjwrHfwqu\rd{p!\u001aoykr\u001colge"));
      }

      // $FF: synthetic method
      XStatusListener(Object var2) {
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
                  var10003 = 10;
                  break;
               case 1:
                  var10003 = 30;
                  break;
               case 2:
                  var10003 = 2;
                  break;
               case 3:
                  var10003 = 1;
                  break;
               default:
                  var10003 = 104;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class XRequestListener implements AgentXConnection.RequestListener {
      private XRequestListener() {
      }

      public void handleRequest(AgentXConnection.PendingIndication var1, AgentXPDU var2) {
         if (MasterAgentXServer.this.i != null) {
            MasterAgentXServer.this.i.handleRequest((MasterAgentXConnection)var1.getConnection(), var1, var2);
            if (AgentXConnection.q == 0) {
               return;
            }
         }

         MasterAgentXServer.this.m.error(a("RLz[\rMV?Z\u001c\u001cO3Z\u001cYM?[HNF=@\u001bHF(L\f"));
      }

      // $FF: synthetic method
      XRequestListener(Object var2) {
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
                  var10003 = 60;
                  break;
               case 1:
                  var10003 = 35;
                  break;
               case 2:
                  var10003 = 90;
                  break;
               case 3:
                  var10003 = 41;
                  break;
               default:
                  var10003 = 104;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public interface StatusListener {
      void connectionUp(MasterAgentXServer var1, MasterAgentXConnection var2);

      void connectionDown(MasterAgentXServer var1, MasterAgentXConnection var2);
   }

   public interface RequestListener {
      void handleRequest(MasterAgentXConnection var1, AgentXConnection.PendingIndication var2, AgentXPDU var3);
   }
}
