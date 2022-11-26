package monfox.toolkit.snmp.engine;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.util.ByteFormatter;
import monfox.toolkit.snmp.util.ParamUtil;
import monfox.toolkit.snmp.util.SimpleQueue;

public class TcpTransportProvider extends TransportProvider {
   public static int DEFAULT_MAX_CONNECTIONS = 100;
   public static long DEFAULT_MAX_IDLE_TIME = 300000L;
   public static int DEFAULT_BACKLOG = 10;
   private static final int[] a = new int[]{1, 256, 65536, 16777216};
   private Cleaner b = null;
   private int c;
   private long d;
   private int e;
   private ServerMonitor f;
   private ServerSocket g;
   private Hashtable h;
   private SimpleQueue i;
   private int j;
   private InetAddress k;
   private static Logger l = null;
   private boolean m;
   private static final String n = "$Id: TcpTransportProvider.java,v 1.6 2013/09/06 22:17:00 sking Exp $";

   public TcpTransportProvider() {
      this.c = DEFAULT_MAX_CONNECTIONS;
      this.d = DEFAULT_MAX_IDLE_TIME;
      this.e = DEFAULT_BACKLOG;
      this.f = null;
      this.g = null;
      this.h = new Hashtable();
      this.i = new SimpleQueue(b("i,+aRh*.\t"));
      this.j = -1;
      this.k = null;
      this.m = true;
      l = Logger.getInstance(b("i\f\u000b\u0018q\\\u0001\b<lO\u001b+>lK\u0006\u001f)q"));
   }

   public void initialize(TransportProvider.Params var1) throws SnmpTransportException {
      if (var1.getTransportType() != this.getTransportType()) {
         throw new SnmpTransportException(b("M\u000e\t-nNO\u0016%pP\u000e\u000f/k\u0011O\u001e4sX\f\u000f)g\u001d;8\u001c#M\u000e\t-nN"));
      } else {
         Params var2 = (Params)var1;
         this.c = var2.getMaxConnections();
         this.d = var2.getMaxIdleTimeMillis();
         this.e = var2.getBacklog();
         super.initialize(var2);
      }
   }

   public int getTransportType() {
      return 2;
   }

   public SnmpOid getTransportDomain() {
      return Snmp.snmpTCPDomain;
   }

   public void initialize(InetAddress var1, int var2) throws SnmpTransportException {
      if (l.isDebugEnabled()) {
         String var3 = null;
         if (this.k != null) {
            var3 = this.k.getHostAddress();
         }

         l.debug(b("T\u0001\u00128j\\\u0003\u00126f\u0007OS") + var3 + ":" + this.j + ")");
      }

      this.k = var1;
      this.j = var2;

      try {
         label30: {
            if (this.k != null && this.j > 0) {
               this.g = new ServerSocket(this.j, this.e, this.k);
               if (!SnmpPDU.i) {
                  break label30;
               }
            }

            if (this.j > 0) {
               this.g = new ServerSocket(this.j, this.e);
            }
         }
      } catch (IOException var4) {
         l.error(b("^\u000e\u0015\"lIO\u0018>f\\\u001b\u001elPX\u001d\r)qn\u0000\u0018'fIO\u001d#q\u0007O") + this.k + ":" + this.j, var4);
         throw new SnmpTransportException(b("^\u000e\u0015\"lIO\u0018>f\\\u001b\u001elPX\u001d\r)qn\u0000\u0018'fIO\u001d#q\u0007O") + this.k + ":" + this.j + "(" + var4 + ")");
      }

      if (this.g != null) {
         this.f = new ServerMonitor();
         this.f.setDaemon(true);
         this.f.start();
      }

      this.b = new Cleaner();
      this.b.setDaemon(true);
      this.b.start();
   }

   private static InetAddress b() throws UnknownHostException {
      try {
         return InetAddress.getByName(b("Q\u0000\u0018-oU\u0000\b8"));
      } catch (Exception var1) {
         return InetAddress.getLocalHost();
      }
   }

   public InetAddress getAddress() {
      return this.k;
   }

   public int getLocalPort() {
      return this.j;
   }

   public boolean isActive() {
      return this.m;
   }

   public void shutdown() throws SnmpTransportException {
      if (this.m) {
         l.debug(b("N\u0007\u000e8gR\u0018\u0015v#") + this.toString());
         this.m = false;
         ServerMonitor var1 = this.f;
         if (var1 != null) {
            var1.shutdown();
         }

         Cleaner var2 = this.b;
         if (var2 != null) {
            var2.shutdown();
         }

         Enumeration var3 = ((Hashtable)((Hashtable)this.h.clone())).elements();

         while(var3.hasMoreElements()) {
            try {
               TcpContext var4 = (TcpContext)var3.nextElement();
               var4.shutdown();
            } catch (Exception var5) {
               if (SnmpPDU.i) {
                  break;
               }
            }
         }

      }
   }

   public Object send(Object var1, TransportEntity var2) throws SnmpTransportException {
      if (!this.m) {
         throw new SnmpTransportException(b("M\u001d\u0014:jY\n\tlmR\u001b[-`I\u0006\r)"));
      } else if (!(var2 instanceof TcpEntity)) {
         throw new SnmpTransportException(b("J\u001d\u0014\"d\u001d\u001b\t-mN\u001f\u0014>w\u001d\u001f\t#uT\u000b\u001e>#I\u0016\u000b)9") + var2);
      } else {
         TcpEntity var3 = (TcpEntity)var2;
         TcpContext var4 = this.a(var3);
         if (var4 == null) {
            l.comms(b("^\u000e\u0015\"lIO\u0018#mS\n\u00188#I\u0000[>fP\u0000\u000f)#X\u0001\u000f%wDU[") + var3);
            return var1;
         } else {
            if (var1 instanceof SnmpBuffer) {
               SnmpBuffer var5 = (SnmpBuffer)var1;

               try {
                  if (l.isCommsEnabled()) {
                     l.comms(b("n*5\bJs([\u000eZi*(\u0017") + var2 + b("`U[d\t") + ByteFormatter.toString(var5.data, var5.offset, var5.length) + b("7F"));
                  }

                  var4.accessed();
                  OutputStream var6 = var4.getOutputStream();
                  var6.write(var5.data, var5.offset, var5.length);
                  if (l.isCommsEnabled()) {
                     l.comms(b("n*5\u0018"));
                  }
               } catch (IOException var7) {
                  var4.shutdown();
                  l.error(b("^\u000e\u0015\"lIO\b)mYO+\bV"), var7);
               }

               if (!SnmpPDU.i) {
                  return var1;
               }
            }

            throw new SnmpTransportException(b("H\u0001\u0010\"lJ\u0001[(bI\u000e[/o\\\u001c\bv") + var1.getClass().getName());
         }
      }
   }

   private synchronized TcpContext a(TcpEntity var1) {
      TcpContext var2 = (TcpContext)this.h.get(var1);
      if (var2 == null) {
         try {
            var2 = new TcpContext(var1, (Socket)null);
            this.h.put(var1, var2);
            var2.setDaemon(true);
            var2.start();
         } catch (Exception var4) {
            l.error(b("^\u000e\u0015\"lIO\u0018>f\\\u001b\u001el`R\u0001\u000f){IO\u001d#q\u001d\n\u00158jI\u0016Al") + var1, var4);
            return null;
         }
      }

      return var2;
   }

   public TransportEntity receive(SnmpBuffer var1, boolean var2) throws SnmpTransportException {
      try {
         if (!this.m) {
            throw new SnmpTransportException(b("M\u001d\u0014:jY\n\tlmR\u001b[-`I\u0006\r)"));
         } else {
            BufferContext var3 = (BufferContext)this.i.popFront();
            if (var1.data.length < var3.buffer.length) {
               throw new SnmpTransportException(b("O\n\u0018)jK\n\u001flaH\t\u001d)q\u001d\u001b\u0014loR\u0001\u001c"));
            } else {
               System.arraycopy(var3.buffer.data, 0, var1.data, 0, var3.buffer.length);
               var1.length = var3.buffer.length;
               if (l.isCommsEnabled()) {
                  String var4 = var3.entity.getAddress() != null ? var3.entity.getAddress().getHostAddress() : b("\rAKb3\u0013_");
                  l.comms(b("o*8\tJk*?lAd;>\u001f9\u001d4\u001d>lPU") + var4 + ":" + var3.entity.getPort() + b("\u0015e") + ByteFormatter.toString(var1.data, 0, var1.length) + b("7F"));
               }

               return var2 ? var3.entity : null;
            }
         }
      } catch (InterruptedException var5) {
         throw new SnmpTransportException(var5.toString());
      }
   }

   public void setParameter(String var1, Object var2) {
      if (!ParamUtil.setParameter(l, this.g, var1, var2)) {
         ParamUtil.setParameter(l, this, var1, var2);
      }

   }

   public String toString() {
      StringBuffer var1;
      label11: {
         var1 = new StringBuffer();
         var1.append(b("\u0015;8\u001c9"));
         if (this.k != null) {
            var1.append(this.k.getHostAddress()).append(":");
            var1.append(this.j);
            if (!SnmpPDU.i) {
               break label11;
            }
         }

         var1.append(":");
      }

      var1.append(")");
      return var1.toString();
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 61;
               break;
            case 1:
               var10003 = 111;
               break;
            case 2:
               var10003 = 123;
               break;
            case 3:
               var10003 = 76;
               break;
            default:
               var10003 = 3;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class Cleaner extends Thread {
      private boolean a;

      private Cleaner() {
         this.a = true;
      }

      public void run() {
         boolean var7 = SnmpPDU.i;

         try {
            label53:
            while(true) {
               int var10000 = this.a;

               label50:
               while(true) {
                  if (var10000 == 0 || !TcpTransportProvider.this.isActive()) {
                     return;
                  }

                  if (TcpTransportProvider.this.d <= 0L) {
                     Thread.sleep(10000L);
                     if (!var7) {
                        continue label53;
                     }
                  }

                  Thread.sleep(TcpTransportProvider.this.d / 4L + 1000L);
                  Enumeration var1 = ((Hashtable)((Hashtable)TcpTransportProvider.this.h.clone())).elements();
                  long var2 = System.currentTimeMillis();
                  long var4 = var2 - TcpTransportProvider.this.d;

                  while(true) {
                     if (!var1.hasMoreElements()) {
                        break label50;
                     }

                     try {
                        TcpContext var6 = (TcpContext)var1.nextElement();
                        long var10;
                        var10000 = (var10 = var6.getLastAccessTime() - var4) == 0L ? 0 : (var10 < 0L ? -1 : 1);
                        if (var7 || var7) {
                           break;
                        }

                        if (var10000 < 0) {
                           if (TcpTransportProvider.l.isCommsEnabled()) {
                              TcpTransportProvider.l.comms("" + var6.getEntity() + a("6\u007f`%<rzv)ue~fq!\u007fxt%1ya}+"));
                           }

                           var6.shutdown();
                        }
                     } catch (Exception var8) {
                        if (var7) {
                           break label50;
                        }
                     }
                  }
               }

               if (var7) {
                  break;
               }
            }
         } catch (Exception var9) {
         }

      }

      public void shutdown() {
         this.a = false;
         this.interrupt();
      }

      // $FF: synthetic method
      Cleaner(Object var2) {
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
                  var10003 = 22;
                  break;
               case 1:
                  var10003 = 22;
                  break;
               case 2:
                  var10003 = 19;
                  break;
               case 3:
                  var10003 = 5;
                  break;
               default:
                  var10003 = 85;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class TcpContext extends Thread {
      boolean a = true;
      TcpEntity b = null;
      Socket c = null;
      DataInputStream d = null;
      OutputStream e = null;
      long f = System.currentTimeMillis();

      TcpContext(TcpEntity var2, Socket var3) throws SnmpTransportException {
         this.b = var2;
         if (var3 == null) {
            try {
               if (var2.getAddress() != null && var2.getPort() > 0) {
                  this.c = new Socket(var2.getAddress(), var2.getPort());
                  if (!SnmpPDU.i) {
                     this.d = new DataInputStream(this.c.getInputStream());
                     this.e = this.c.getOutputStream();
                     return;
                  }
               }

               TcpTransportProvider.l.error(a("\u000bD\u0016G7\u000b_\u0011F<HM\nF?HY\u001dD=\u001cNXL<\u001cB\fPr\u0004D\u000b]~H\\\u0011E>HE\u0017]r\u001aNUL!\u001cJ\u001aE;\u001bCX\u0013") + var2);
               throw new SnmpTransportException(a("\u000bD\u0016G7\u000b_\u0011F<HM\nF?HY\u001dD=\u001cNXL<\u001cB\fPr\u0004D\u000b]~H\\\u0011E>HE\u0017]r\u001aNUL!\u001cJ\u001aE;\u001bCX\u0013") + var2);
            } catch (Exception var6) {
               TcpTransportProvider.l.error(a("\u000bJ\u0016G=\u001c\u000b\u001dZ&\tI\u0014@!\u0000\u000b,j\u0002HH\u0017G&\rS\f\t4\u0007YXL<\u001cB\fPh") + var2, var6);
               throw new SnmpTransportException(a("\u000bJ\u0016G=\u001c\u000b\u001dZ&\tI\u0014@!\u0000\u000b,j\u0002HH\u0017G&\rS\f\t4\u0007YXL<\u001cB\fPh") + var2 + "(" + var6 + ")");
            }
         } else {
            try {
               this.c = var3;
               this.d = new DataInputStream(this.c.getInputStream());
               this.e = this.c.getOutputStream();
            } catch (Exception var5) {
               TcpTransportProvider.l.error(a("\u000bJ\u0016G=\u001c\u000b\u001dZ&\tI\u0014@!\u0000\u000b,j\u0002HH\u0017G&\rS\f\t4\u0007YXL<\u001cB\fPz\u001bD\u001bB7\u001c\u0002B") + var2, var5);
               throw new SnmpTransportException(a("\u000bJ\u0016G=\u001c\u000b\u001dZ&\tI\u0014@!\u0000\u000b,j\u0002HH\u0017G&\rS\f\t4\u0007YXL<\u001cB\fPz\u001bD\u001bB7\u001c\u0002B") + var2 + "(" + var5 + ")");
            }
         }

      }

      public DataInputStream getInputStream() {
         return this.d;
      }

      public OutputStream getOutputStream() {
         return this.e;
      }

      public Socket getSocket() {
         return this.c;
      }

      public TcpEntity getEntity() {
         return this.b;
      }

      public void run() {
         boolean var8 = SnmpPDU.i;

         Exception var16;
         label74: {
            TcpContext var10000;
            boolean var10001;
            while(true) {
               try {
                  if (TcpTransportProvider.this.isActive()) {
                     var10000 = this;
                     if (var8) {
                        break;
                     }

                     if (this.a) {
                        label84: {
                           this.accessed();
                           int var1 = this.d.readUnsignedByte();
                           if (var1 != 48) {
                              TcpTransportProvider.l.error(a(";e5yr\u0005N\u000bZ3\u000fNXO \tF\u0011G5HN\n[=\u001a\u0005Xk3\f\u000b\fH5R\u000bHQ") + Integer.toHexString(var1) + a("\rS\bL1\u001cN\u001c\tb\u0010\u0018H"));
                              if (!var8) {
                                 break label84;
                              }
                           }

                           int var2 = this.d.readUnsignedByte();
                           int var3 = var2;
                           byte[] var4 = null;
                           int var6;
                           if ((var2 & 128) == 128) {
                              var4 = new byte[var2 & 127];
                              this.d.readFully(var4);
                              int var5 = var2;

                              try {
                                 var6 = var5 & 127;
                                 var3 = 0;
                                 int var7 = 0;

                                 while(var7 < var6) {
                                    var3 += (var4[var7] & 255) * TcpTransportProvider.a[var6 - var7 - 1];
                                    ++var7;
                                    if (var8 || var8) {
                                       break;
                                    }
                                 }
                              } catch (Exception var11) {
                                 throw new IOException(a("\u000bJ\u0016G=\u001c\u000b\u001bF<\u001eN\n]r\u0004N\u0016N&\u0000"));
                              }
                           }

                           byte[] var14;
                           label47: {
                              if (var4 == null) {
                                 var14 = new byte[2 + var3];
                                 var14[0] = (byte)var1;
                                 var14[1] = (byte)var2;
                                 var6 = 2;
                                 if (!var8) {
                                    break label47;
                                 }
                              }

                              var14 = new byte[2 + var4.length + var3];
                              var14[0] = (byte)var1;
                              var14[1] = (byte)var2;
                              System.arraycopy(var4, 0, var14, 2, var4.length);
                              var6 = 2 + var4.length;
                           }

                           this.d.readFully(var14, var6, var3);
                           SnmpBuffer var15 = new SnmpBuffer(var14, 0, var14.length);
                           TcpTransportProvider.this.i.pushBack(TcpTransportProvider.this.new BufferContext(var15, this.b));
                           if (!var8) {
                              continue;
                           }
                        }
                     }
                  }
               } catch (Throwable var12) {
                  TcpTransportProvider.l.warn(a("<h(\t \rH\u001d@$\rYXL*\u000bN\b];\u0007E"), var12);
               }

               this.a = false;
               TcpTransportProvider.this.h.remove(this.b);

               try {
                  TcpTransportProvider.l.debug(a("\u000bG\u0017Z;\u0006LXZ=\u000b@\u001d]hH") + this.c);
                  var10000 = this;
                  break;
               } catch (Exception var10) {
                  var16 = var10;
                  var10001 = false;
                  break label74;
               }
            }

            try {
               var10000.c.close();
               return;
            } catch (Exception var9) {
               var16 = var9;
               var10001 = false;
            }
         }

         Exception var13 = var16;
         TcpTransportProvider.l.debug(a("\rY\nF HH\u0014F!\u0001E\u001f\t!\u0007H\u0013L&"), var13);
      }

      public void accessed() {
         this.f = System.currentTimeMillis();
      }

      public long getLastAccessTime() {
         return this.f;
      }

      public void shutdown() {
         TcpTransportProvider.this.h.remove(this);
         this.a = false;
         this.interrupt();

         try {
            this.c.close();
         } catch (Exception var2) {
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
                  var10003 = 104;
                  break;
               case 1:
                  var10003 = 43;
                  break;
               case 2:
                  var10003 = 120;
                  break;
               case 3:
                  var10003 = 41;
                  break;
               default:
                  var10003 = 82;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class BufferContext {
      public SnmpBuffer buffer;
      public TcpEntity entity;

      public BufferContext(SnmpBuffer var2, TcpEntity var3) {
         this.buffer = var2;
         this.entity = var3;
      }
   }

   private class ServerMonitor extends Thread {
      ServerMonitor() {
      }

      public void run() {
         try {
            if (TcpTransportProvider.l.isDebugEnabled()) {
               String var1 = null;
               if (TcpTransportProvider.this.k != null) {
                  var1 = TcpTransportProvider.this.k.getHostAddress();
               }

               TcpTransportProvider.l.debug(a("P\u0002\u000bL5q*\u0016T9w\b\u000b\u0014\"v\tC\u001ax") + var1 + ":" + TcpTransportProvider.this.j + ")");
            }

            while(TcpTransportProvider.this.g != null && TcpTransportProvider.this.isActive()) {
               Socket var7 = TcpTransportProvider.this.g.accept();
               if (TcpTransportProvider.this.h.size() >= TcpTransportProvider.this.c) {
                  if (TcpTransportProvider.l.isDebugEnabled()) {
                     TcpTransportProvider.l.debug(a("n\u0006\u0001\u001a3l\t\u0017_3w\u000e\u0016T##\u0015\u001c[3k\u0002\u001d\u0016p`\u000b\u0016I9m\u0000YY?m\t\u001cY$j\b\u0017\u0000p") + var7);
                  }

                  try {
                     var7.close();
                  } catch (Exception var5) {
                  }
               }

               try {
                  if (TcpTransportProvider.l.isDebugEnabled()) {
                     TcpTransportProvider.l.debug(a("b\u0004\u001a_ w\u0002\u001d\u0000p") + var7);
                  }

                  TcpEntity var2 = new TcpEntity();
                  var2.initialize(var7.getInetAddress(), var7.getPort());
                  var2.setProvider(TcpTransportProvider.this);
                  TcpContext var3 = TcpTransportProvider.this.new TcpContext(var2, var7);
                  TcpTransportProvider.this.h.put(var2, var3);
                  var3.setDaemon(true);
                  var3.start();
               } catch (Exception var4) {
                  TcpTransportProvider.l.error(a("f\u0015\u000bU\"#\u000e\u0017\u001a3q\u0002\u0018N9m\u0000Y_>w\u000e\rCpe\b\u000b\u001a9m\u0004\u0016W9m\u0000YI?`\f\u001cNj") + var7, var4);
               }

               if (SnmpPDU.i) {
                  break;
               }
            }
         } catch (Throwable var6) {
            TcpTransportProvider.l.error(a("p\u0002\u000bL5qG\nU3h\u0002\r\u001a5{\u0004\u001cJ$j\b\u0017"), var6);
            this.shutdown();
         }

      }

      public void shutdown() {
         this.interrupt();
         ServerSocket var1 = TcpTransportProvider.this.g;
         if (var1 != null) {
            try {
               var1.close();
            } catch (Exception var3) {
            }
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
                  var10003 = 3;
                  break;
               case 1:
                  var10003 = 103;
                  break;
               case 2:
                  var10003 = 121;
                  break;
               case 3:
                  var10003 = 58;
                  break;
               default:
                  var10003 = 80;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class Params extends TransportProvider.Params {
      private int b;
      private long a;
      private int c;

      public Params() {
         this.b = TcpTransportProvider.DEFAULT_MAX_CONNECTIONS;
         this.a = TcpTransportProvider.DEFAULT_MAX_IDLE_TIME;
         this.c = TcpTransportProvider.DEFAULT_BACKLOG;
      }

      public Params(String var1, int var2, int var3, long var4, int var6) throws UnknownHostException {
         super(var1 == null ? null : InetAddress.getByName(var1), var2);
         this.b = TcpTransportProvider.DEFAULT_MAX_CONNECTIONS;
         this.a = TcpTransportProvider.DEFAULT_MAX_IDLE_TIME;
         this.c = TcpTransportProvider.DEFAULT_BACKLOG;
         this.b = var3;
         this.a = var4;
         this.c = var6;
      }

      public Params(String var1, int var2) throws UnknownHostException {
         super(var1 == null ? null : InetAddress.getByName(var1), var2);
         this.b = TcpTransportProvider.DEFAULT_MAX_CONNECTIONS;
         this.a = TcpTransportProvider.DEFAULT_MAX_IDLE_TIME;
         this.c = TcpTransportProvider.DEFAULT_BACKLOG;
      }

      public Params(InetAddress var1, int var2) {
         super(var1, var2);
         this.b = TcpTransportProvider.DEFAULT_MAX_CONNECTIONS;
         this.a = TcpTransportProvider.DEFAULT_MAX_IDLE_TIME;
         this.c = TcpTransportProvider.DEFAULT_BACKLOG;
      }

      public void setMaxConnections(int var1) {
         this.b = var1;
      }

      public int getMaxConnections() {
         return this.b;
      }

      public void setMaxIdleTimeMillis(long var1) {
         this.a = var1;
      }

      public long getMaxIdleTimeMillis() {
         return this.a;
      }

      public void setBacklog(int var1) {
         this.c = var1;
      }

      public int getBacklog() {
         return this.c;
      }

      public int getTransportType() {
         return 2;
      }

      public SnmpOid getTransportDomain() {
         return Snmp.snmpTCPDomain;
      }
   }
}
