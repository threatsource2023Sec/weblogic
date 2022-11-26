package monfox.toolkit.snmp.agent.x.connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.agent.x.common.AgentXCommunicationsException;
import monfox.toolkit.snmp.agent.x.common.AgentXTimeoutException;
import monfox.toolkit.snmp.agent.x.pdu.AddAgentCapsPDU;
import monfox.toolkit.snmp.agent.x.pdu.AgentXPDU;
import monfox.toolkit.snmp.agent.x.pdu.CleanupSetPDU;
import monfox.toolkit.snmp.agent.x.pdu.ClosePDU;
import monfox.toolkit.snmp.agent.x.pdu.CoderException;
import monfox.toolkit.snmp.agent.x.pdu.CommitSetPDU;
import monfox.toolkit.snmp.agent.x.pdu.GetBulkPDU;
import monfox.toolkit.snmp.agent.x.pdu.GetNextPDU;
import monfox.toolkit.snmp.agent.x.pdu.GetPDU;
import monfox.toolkit.snmp.agent.x.pdu.IndexAllocatePDU;
import monfox.toolkit.snmp.agent.x.pdu.IndexDeallocatePDU;
import monfox.toolkit.snmp.agent.x.pdu.NotifyPDU;
import monfox.toolkit.snmp.agent.x.pdu.OpenPDU;
import monfox.toolkit.snmp.agent.x.pdu.PingPDU;
import monfox.toolkit.snmp.agent.x.pdu.RegisterPDU;
import monfox.toolkit.snmp.agent.x.pdu.RemoveAgentCapsPDU;
import monfox.toolkit.snmp.agent.x.pdu.ResponsePDU;
import monfox.toolkit.snmp.agent.x.pdu.TestSetPDU;
import monfox.toolkit.snmp.agent.x.pdu.UndoSetPDU;
import monfox.toolkit.snmp.agent.x.pdu.UnregisterPDU;
import monfox.toolkit.snmp.util.ByteFormatter;

public class AgentXConnection {
   public static final int STATUS_DOWN = 0;
   public static final int STATUS_UP = 1;
   private int a = 0;
   private int b = 10485760;
   private Socket c = null;
   private DataInputStream d = null;
   private OutputStream e = null;
   private PDUFactory f = new PDUFactory();
   private Processor g = null;
   private Thread h = null;
   private boolean i = false;
   private RequestListener j = null;
   private StatusListener k = null;
   private Map l = new HashMap();
   private long m;
   private int n;
   private static long o = 10000L;
   private Logger p;
   public static int q;

   AgentXConnection() {
      this.m = o;
      this.n = 100;
      this.p = Logger.getInstance(a("k9\"k\u0019"), a("n-)h\u001d\u00022"), a("n\r\tH=w)\u0003H'J\t\u0018O&A"));
   }

   public void setRequestListener(RequestListener var1) {
      this.j = var1;
   }

   public void setStatusListener(StatusListener var1) {
      this.k = var1;
   }

   public int getStatus() {
      return this.a;
   }

   void a(Socket var1, StatusListener var2, RequestListener var3) throws IOException {
      this.setStatusListener(var2);
      this.setRequestListener(var3);
      if (this.c != null) {
         this.shutdown();
      }

      this.c = var1;
      this.d = new DataInputStream(this.c.getInputStream());
      this.e = this.c.getOutputStream();
      this.i = true;
      this.g = new Processor();
      this.h = new Thread(this.g);
      this.h.setPriority(5);
      this.h.setDaemon(true);
      this.h.start();
   }

   public void setRequestTimeoutMillis(long var1) {
      this.m = var1;
   }

   public long getRequestTimeoutMillis() {
      return this.m;
   }

   void a(ResponsePDU var1) {
      this.p.debug(a("\\\u000f\u0002B\u001bJ\u0019\u001cI'\\\u000fD\bg\u0001C"));

      try {
         if (this.c == null) {
            throw new IOException(a("A\u0005LE&A\u0004\tE=F\u0005\u0002"));
         }

         this.p.debug(a("|/\"b\u0000a-Lt\f|:#h\u001ajPL") + var1);
         byte[] var2 = var1.encode();
         this.p.debug(a("|/\"b\u0000a-Ld\u0010{/?\u001ci%") + ByteFormatter.toString(var2) + "\n");
         synchronized(this.e) {
            this.e.write(var2);
            this.e.flush();
         }
      } catch (Exception var6) {
         this.p.debug(a("L\u000b\u0002H&[J\u001fC'KJ-A,A\u001e4\u0006;J\u0019\u001cI'\\\u000fV,") + var1, var6);
      }

   }

   void a(PendingIndication var1, ResponsePDU var2) {
      this.p.debug(a("\\\u000f\u0002B\u001bJ\u0019\u001cI'\\\u000fD\bg\u0001C"));

      try {
         if (this.c == null) {
            throw new IOException(a("A\u0005LE&A\u0004\tE=F\u0005\u0002"));
         }

         label27: {
            var2.setPacketId(var1.getPacketId());
            var2.setTransactionId(var1.getTransactionId());
            if ((var1.getRequest().getFlags() & 16) != 0) {
               var2.setFlag(16);
               if (q == 0) {
                  break label27;
               }
            }

            var2.resetFlag(16);
         }

         this.p.debug(a("|/\"b\u0000a-Lt\f|:#h\u001ajPL") + var2);
         byte[] var3 = var2.encode();
         this.p.debug(a("|/\"b\u0000a-Ld\u0010{/?\u001ci%") + ByteFormatter.toString(var3) + "\n");
         synchronized(this.e) {
            this.e.write(var3);
            this.e.flush();
         }
      } catch (Exception var7) {
         this.p.debug(a("L\u000b\u0002H&[J\u001fC'KJ-A,A\u001e4\u0006;J\u0019\u001cI'\\\u000fV,") + var2, var7);
      }

   }

   public PendingRequest send(AgentXPDU var1) throws AgentXCommunicationsException {
      int var7 = q;
      this.p.debug(a("\\\u000f\u0002Ba\u0001DB\u000f"));
      PendingRequest var2 = new PendingRequest(var1);
      if (this.c == null) {
         throw new AgentXCommunicationsException(a("A\u0005LE&A\u0004\tE=F\u0005\u0002"));
      } else {
         int var3 = this.a();
         var1.setPacketId(var3);
         var2.a(var3);
         this.p.debug(a("|/\"b\u0000a-Lt\f~?)u\u001d\u0015J") + var1);
         Object var4 = null;

         byte[] var11;
         try {
            var11 = var1.encode();
         } catch (CoderException var10) {
            this.p.debug(a("n\r\tH=wJ\tH*@\u000e\t\u0006,]\u0018\u0003T"), var10);
            throw new AgentXCommunicationsException(a("J\u0018\u001eI;\u000f\u0003\u0002\u0006,A\t\u0003B A\rLK,\\\u0019\rA,\u0015J") + var10.getMessage());
         }

         this.p.debug(a("|/\"b\u0000a-Ld\u0010{/?\u001ci%") + ByteFormatter.toString(var11) + "\n");

         try {
            synchronized(this.l) {
               this.l.put(new Integer(var2.getPacketId()), var2);
               this.e.write(var11);
               this.e.flush();
            }
         } catch (IOException var9) {
            this.p.debug(a("f%LC1L\u000f\u001cR @\u0004LI'\u000f\u0019\tH-"), var9);
            throw new AgentXCommunicationsException(a("f%LC;]\u0005\u001e\u0006&AJ\u001fC'KP") + var9);
         }

         if (var7 != 0) {
            SnmpException.b = !SnmpException.b;
         }

         return var2;
      }
   }

   private synchronized void a(AgentXPDU var1) {
      int var4 = q;
      this.p.debug(a("}//c\u0000y/(\u0006\u0019k?V\u0006C") + var1);
      if (var1.getType() == 18) {
         label22: {
            Integer var2 = new Integer(var1.getPacketId());
            PendingRequest var3 = (PendingRequest)this.l.get(var2);
            if (var3 != null) {
               var3.a(var1);
               this.l.remove(var2);
               if (var4 == 0) {
                  break label22;
               }
            }

            System.out.println(a("z\u0004\u0007H&X\u0004LV(L\u0001\tRif.V\u0006") + var2);
         }

         if (var4 == 0) {
            return;
         }
      }

      if (this.j != null) {
         this.j.handleRequest(new PendingIndication(var1), var1);
         if (var4 == 0) {
            return;
         }
      }

      this.p.debug(a("a\u0005Lj \\\u001e\tH,]J\u001eC.F\u0019\u0018C;J\u000eL@&]PL") + var1);
   }

   private synchronized int a() {
      return this.n++;
   }

   public static void setDefaultRequestTimeoutMillis(long var0) {
      o = var0;
   }

   public static long getDefaultRequestTimeoutMillis() {
      return o;
   }

   public void shutdown() {
      this.i = false;

      try {
         this.c.close();
      } catch (Exception var3) {
      }

      try {
         this.h.interrupt();
      } catch (Exception var2) {
      }

   }

   public int getMaxPayloadLength() {
      return this.b;
   }

   public void setMaxPayloadLength(int var1) {
      this.b = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 47;
               break;
            case 1:
               var10003 = 106;
               break;
            case 2:
               var10003 = 108;
               break;
            case 3:
               var10003 = 38;
               break;
            default:
               var10003 = 73;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class PDUFactory {
      private PDUFactory() {
      }

      public AgentXPDU newInstance(int var1) throws CoderException {
         Object var2 = null;
         switch (var1) {
            case 1:
               var2 = new OpenPDU();
               break;
            case 2:
               var2 = new ClosePDU();
               break;
            case 3:
               var2 = new RegisterPDU();
               break;
            case 4:
               var2 = new UnregisterPDU();
               break;
            case 5:
               var2 = new GetPDU();
               break;
            case 6:
               var2 = new GetNextPDU();
               break;
            case 7:
               var2 = new GetBulkPDU();
               break;
            case 8:
               var2 = new TestSetPDU();
               break;
            case 9:
               var2 = new CommitSetPDU();
               break;
            case 10:
               var2 = new UndoSetPDU();
               break;
            case 11:
               var2 = new CleanupSetPDU();
               break;
            case 12:
               var2 = new NotifyPDU();
               break;
            case 13:
               var2 = new PingPDU();
               break;
            case 14:
               var2 = new IndexAllocatePDU();
               break;
            case 15:
               var2 = new IndexDeallocatePDU();
               break;
            case 16:
               var2 = new AddAgentCapsPDU();
               break;
            case 17:
               var2 = new RemoveAgentCapsPDU();
               break;
            case 18:
               var2 = new ResponsePDU();
               break;
            default:
               throw new CoderException(a("\u0015\u000e&\u001f~\u0015\u0004p.V)@$\u0007b\u0019Zp") + var1);
         }

         return (AgentXPDU)var2;
      }

      // $FF: synthetic method
      PDUFactory(Object var2) {
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
                  var10003 = 124;
                  break;
               case 1:
                  var10003 = 96;
                  break;
               case 2:
                  var10003 = 80;
                  break;
               case 3:
                  var10003 = 126;
                  break;
               default:
                  var10003 = 18;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class Processor implements Runnable {
      private Processor() {
      }

      public void run() {
         int var7 = AgentXConnection.q;
         AgentXConnection.this.a = 1;
         if (AgentXConnection.this.k != null) {
            AgentXConnection.this.k.connectionUp(AgentXConnection.this);
         }

         while(true) {
            try {
               if (AgentXConnection.this.i) {
                  byte[] var1 = new byte[20];
                  AgentXConnection.this.d.readFully(var1);
                  String var2 = ByteFormatter.toString(var1);
                  AgentXConnection.this.p.debug(a("\u0014wRSa\u0010wU6`\u0003sUSz|\u0012\u001b") + var2 + "\n");
                  AgentXPDU var3 = null;

                  ResponsePDU var5;
                  label86: {
                     try {
                        var3 = AgentXConnection.this.f.newInstance(var1[1]);
                     } catch (CoderException var11) {
                        AgentXConnection.this.p.error(a("$Su6i!W\u007fbpf_te['Ut:\b$Su6E#AbwO#\u0012eoX#"), var11);
                        var5 = new ResponsePDU();
                        var5.setError(266);
                        var5.setSessionId(0);
                        var5.setPacketId(0);
                        var5.setTransactionId(0);
                        AgentXConnection.this.a(var5);
                        if (var7 == 0) {
                           continue;
                        }
                        break label86;
                     }

                     if (var7 != 0 || var7 != 0 || var7 != 0 || var7 != 0 || var7 != 0 || var7 != 0) {
                        break;
                     }
                  }

                  int var4 = 0;

                  ResponsePDU var6;
                  try {
                     var4 = var3.decodeHeader(var1);
                  } catch (CoderException var10) {
                     AgentXConnection.this.p.error(a("$Su6i!W\u007fbpf_te['Ut:\b.WprM4\u0012awZ5W1sZ4]c"), var10);
                     var6 = new ResponsePDU();
                     var6.setError(266);
                     var6.setSessionId(var3.getSessionId());
                     var6.setPacketId(var3.getPacketId());
                     var6.setTransactionId(var3.getTransactionId());
                     AgentXConnection.this.a(var6);
                     if (var7 == 0) {
                        continue;
                     }
                  }

                  AgentXConnection.this.p.debug(a("'Utx\\kJ<fL3\u0012awQ*]pr\b\nW\u007f,\b") + var4);
                  if (AgentXConnection.this.b > 0 && var4 > AgentXConnection.this.b) {
                     AgentXConnection.this.p.error(a("6ShzG'V1sP%Wtr[f_pn\b6ShzG'V1zM(Ue~\bn") + var4 + a("f\f1") + AgentXConnection.this.b + a("o\u001e1uD)AxxOfQ~xF#Qe\u007fG(\u001c"));
                     var5 = new ResponsePDU();
                     var5.setError(268);
                     var5.setSessionId(var3.getSessionId());
                     var5.setPacketId(var3.getPacketId());
                     var5.setTransactionId(var3.getTransactionId());
                     AgentXConnection.this.a(var5);
                     AgentXConnection.this.p.debug(a("5Zdb\\/\\v6L)E\u007f6K)\\\u007fsK2[~x"));
                     AgentXConnection.this.shutdown();
                     if (var7 == 0) {
                        continue;
                     }
                  }

                  if (var4 > 0) {
                     try {
                        byte[] var14 = new byte[var4];
                        AgentXConnection.this.d.readFully(var14);
                        AgentXConnection.this.p.debug(a("\u0014wRSa\u0010wU6x\u0007k]Yi\u0002\b1\u001c") + ByteFormatter.toString(var14) + "\n");
                        var3.decodePayload(var14);
                     } catch (CoderException var8) {
                        AgentXConnection.this.p.error(a("$Su6i!W\u007fbpf_te['Ut:\b6ShzG'V1fI4At6M4@~d"), var8);
                        var6 = new ResponsePDU();
                        var6.setError(266);
                        var6.setSessionId(var3.getSessionId());
                        var6.setPacketId(var3.getPacketId());
                        var6.setTransactionId(var3.getTransactionId());
                        AgentXConnection.this.a(var6);
                        continue;
                     } catch (OutOfMemoryError var9) {
                        AgentXConnection.this.p.error(a(")Ge6G \u0012|sE)@h6A(\u0012rdM'FxxOfbUC\u0004fAtxL/\\v6M4@~d\b'\\u6K*]b\u007fF!\u0012ryF(WrbA)\\"), var9);
                        var6 = new ResponsePDU();
                        var6.setError(268);
                        var6.setSessionId(var3.getSessionId());
                        var6.setPacketId(var3.getPacketId());
                        var6.setTransactionId(var3.getTransactionId());
                        AgentXConnection.this.a(var6);
                        AgentXConnection.this.p.debug(a("5Zdb\\/\\v6L)E\u007f6K)\\\u007fsK2[~x"));
                        AgentXConnection.this.shutdown();
                        if (var7 == 0) {
                           continue;
                        }
                     }
                  }

                  AgentXConnection.this.a(var3);
                  if (var7 == 0) {
                     continue;
                  }
               }
            } catch (IOException var12) {
               AgentXConnection.this.p.debug(a("/]1sP%WabA)\\"), var12);
            } catch (Exception var13) {
               AgentXConnection.this.p.debug(a("#JrsX2[~x"), var13);
            }

            AgentXConnection.this.a = 0;
            break;
         }

         if (AgentXConnection.this.k != null) {
            AgentXConnection.this.k.connectionDown(AgentXConnection.this);
         }

      }

      // $FF: synthetic method
      Processor(Object var2) {
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
                  var10003 = 70;
                  break;
               case 1:
                  var10003 = 50;
                  break;
               case 2:
                  var10003 = 17;
                  break;
               case 3:
                  var10003 = 22;
                  break;
               default:
                  var10003 = 40;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public interface StatusListener {
      void connectionUp(AgentXConnection var1);

      void connectionDown(AgentXConnection var1);
   }

   public interface RequestListener {
      void handleRequest(PendingIndication var1, AgentXPDU var2);
   }

   public class PendingIndication {
      private AgentXPDU a = null;
      private int b = -1;
      private int c = -1;

      PendingIndication(AgentXPDU var2) {
         this.a = var2;
         this.b = var2.getPacketId();
         this.c = var2.getTransactionId();
      }

      public int getPacketId() {
         return this.b;
      }

      public int getTransactionId() {
         return this.c;
      }

      public AgentXPDU getRequest() {
         return this.a;
      }

      public void sendProcessingError() {
         this.sendError(268);
      }

      public void sendRequestDenied() {
         this.sendError(267);
      }

      public void sendError(int var1) {
         ResponsePDU var2 = new ResponsePDU();
         var2.setError(var1);
         var2.setSessionId(this.a.getSessionId());
         this.sendResponse(var2);
      }

      public void sendResponse(ResponsePDU var1) {
         AgentXConnection.this.a(this, var1);
      }

      public AgentXConnection getConnection() {
         return AgentXConnection.this;
      }
   }

   public class PendingRequest {
      private AgentXPDU a = null;
      private AgentXPDU b = null;
      private int c = -1;

      PendingRequest(AgentXPDU var2) {
         this.a = var2;
      }

      public int getPacketId() {
         return this.c;
      }

      void a(int var1) {
         this.c = var1;
      }

      public AgentXPDU getRequest() {
         return this.a;
      }

      public synchronized AgentXPDU getResponse() throws AgentXTimeoutException {
         return this.getResponse(AgentXConnection.this.getRequestTimeoutMillis());
      }

      public synchronized AgentXPDU getResponse(long var1) throws AgentXTimeoutException {
         try {
            if (this.b == null) {
               this.wait(var1);
               if (this.b == null) {
                  throw new AgentXTimeoutException();
               }
            }

            return this.b;
         } catch (InterruptedException var4) {
            throw new AgentXTimeoutException(a("-\u0018DvZ6\u0003@gM VUkK!\u0006DzG*"));
         }
      }

      synchronized void a(AgentXPDU var1) {
         this.b = var1;
         this.notifyAll();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 68;
                  break;
               case 1:
                  var10003 = 118;
                  break;
               case 2:
                  var10003 = 48;
                  break;
               case 3:
                  var10003 = 19;
                  break;
               default:
                  var10003 = 40;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
