package monfox.toolkit.snmp.engine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.util.ByteFormatter;
import monfox.toolkit.snmp.util.ParamUtil;

public class UdpTransportProvider extends TransportProvider {
   private Params a = null;
   private boolean b = true;
   private int c = -1;
   private InetAddress d = null;
   private static Logger e = null;
   private boolean f = true;
   private DatagramSocket g = null;
   private static final String h = "$Id: UdpTransportProvider.java,v 1.19 2014/07/28 13:44:10 sking Exp $";

   public UdpTransportProvider() {
      if (e == null) {
         e = Logger.getInstance(b("=Z\u0002\u000fQ\tP\u0001+L\u001aJ\")L\u001eW\u0016>Q"));
      }

      if ((SnmpFramework.getCompatFlags() & 1) != 0) {
         this.useDatagramOffset(false);
      }

   }

   public int getTransportType() {
      return 1;
   }

   public SnmpOid getTransportDomain() {
      return Snmp.snmpUDPDomain;
   }

   public void initialize(TransportProvider.Params var1) throws SnmpTransportException {
      if (var1.getTransportType() != this.getTransportType()) {
         throw new SnmpTransportException(b("\u0018_\u0000:N\u001b\u001e\u001f2P\u0005_\u00068KD\u001e\u0017#S\r]\u0006>GHk6\u000b\u0003\u0018_\u0000:N\u001b"));
      } else {
         Params var2 = (Params)var1;
         this.a = (Params)var1;
         this.b = var2.useDatagramOffset();
         super.initialize(var2);
      }
   }

   public void initialize(InetAddress var1, int var2) throws SnmpTransportException {
      boolean var4 = SnmpPDU.i;
      if (this.g == null) {
         try {
            label67: {
               if (var2 > 0 && var1 != null) {
                  this.g = new DatagramSocket(var2, var1);
                  this.d = var1;
                  this.c = var2;
                  if (!var4) {
                     break label67;
                  }
               }

               if (var2 > 0) {
                  this.g = new DatagramSocket(var2);
                  this.d = this.g.getLocalAddress();
                  this.c = var2;
                  if (!var4) {
                     break label67;
                  }
               }

               if (var1 != null) {
                  this.g = new DatagramSocket(0, var1);
                  this.d = var1;
                  this.c = var2;
                  if (!var4) {
                     break label67;
                  }
               }

               this.g = new DatagramSocket();
               this.d = this.g.getLocalAddress();
               this.c = this.g.getLocalPort();
            }

            if (this.d == null) {
               this.d = b();
            }

            if (this.a != null) {
               if (this.a.getUDPSendBufferSize() > 0) {
                  this.g.setSendBufferSize(this.a.getUDPSendBufferSize());
               }

               if (this.a.getUDPReceiveBufferSize() > 0) {
                  this.g.setReceiveBufferSize(this.a.getUDPReceiveBufferSize());
               }
            }

            if (e.isDebugEnabled()) {
               e.debug(b("\u001b[\u001c?\u000e\nK\u0014=F\u001a\u0013\u00012Y\r\u0004R") + this.g.getSendBufferSize());
               e.debug(b("\u001a[\u0011-\u000e\nK\u0014=F\u001a\u0013\u00012Y\r\u0004R") + this.g.getReceiveBufferSize());
            }
         } catch (UnknownHostException var5) {
            throw new SnmpTransportException(var5.getMessage());
         } catch (SocketException var6) {
            throw new SnmpTransportException(b("+_\u001c5L\u001c\u001e\u00102M\f\u001e\u00064\u0003\u0018Q\u0000/\u0003O") + var2 + b("O\u0010Rs") + var6 + ")");
         }

         if (e.isCommsEnabled()) {
            String var3 = null;
            if (this.d != null) {
               var3 = this.d.getHostAddress();
            }

            e.comms(b("+L\u0017:W\r\u001e'?S<L\u00135P\u0018Q\u0000/s\u001aQ\u00042G\rLZ") + var3 + ":" + this.c + ")");
         }

      }
   }

   public void setParameter(String var1, Object var2) {
      if (!ParamUtil.setParameter(e, this.g, var1, var2)) {
         ParamUtil.setParameter(e, this, var1, var2);
      }

   }

   private static InetAddress b() throws UnknownHostException {
      try {
         return InetAddress.getByName(b("\u0004Q\u0011:O\u0000Q\u0001/"));
      } catch (Exception var1) {
         return InetAddress.getLocalHost();
      }
   }

   public InetAddress getAddress() {
      return this.d;
   }

   public int getLocalPort() {
      return this.c;
   }

   public DatagramSocket getSocket() {
      return this.g;
   }

   public boolean isActive() {
      return this.f;
   }

   public void shutdown() throws SnmpTransportException {
      e.comms(b("\u001bV\u0007/G\u0007I\u001ca\u0003") + this + b("R\u001eZ(L\u000bU\u0017/\u0019H") + this.g + ")");
      this.f = false;
      this.g.close();
   }

   public Object send(Object var1, TransportEntity var2) throws SnmpTransportException {
      if (!(var2 instanceof UdpEntity)) {
         throw new SnmpTransportException(b("\rP\u00062W\u0011\u0011\u0002)L\u001eW\u0016>QHS\u001b(N\tJ\u00113\rH") + var2 + b("HW\u0001{M\u0007JR:\u0003=Z\u0002\u001eM\u001cW\u0006\""));
      } else {
         UdpEntity var3 = (UdpEntity)var2;
         DatagramPacket var4 = null;
         if (var1 instanceof SnmpBuffer) {
            SnmpBuffer var5 = (SnmpBuffer)var1;
            if (var5.offset == 0) {
               var4 = new DatagramPacket(var5.data, var5.length, var3.getAddress(), var3.getPort());
            } else if (this.b) {
               var4 = new DatagramPacket(var5.data, var5.offset, var5.length, var3.getAddress(), var3.getPort());
            } else {
               byte[] var6 = new byte[var5.length];
               System.arraycopy(var5.data, var5.offset, var6, 0, var5.length);
               var4 = new DatagramPacket(var6, var6.length, var3.getAddress(), var3.getPort());
            }
         } else {
            if (!(var1 instanceof DatagramPacket)) {
               throw new SnmpTransportException(b("=P\u00195L\u001fPR?B\u001c_R8O\tM\u0001a") + var1.getClass().getName());
            }

            var4 = (DatagramPacket)var1;
         }

         try {
            if (e.isCommsEnabled()) {
               e.comms(b(";{<\u001fj&yR\u0019z<{!\u0000") + var2 + b("5\u0004Rs)") + ByteFormatter.toString(var4.getData(), var4.getOffset(), var4.getLength()) + b("b\u0017"));
            }

            this.g.send(var4);
            return var4;
         } catch (IOException var7) {
            throw new SnmpTransportException(var7.toString());
         }
      }
   }

   public TransportEntity receive(SnmpBuffer var1, boolean var2) throws SnmpTransportException {
      try {
         if (!this.f) {
            throw new SnmpTransportException(b("\u0018L\u001d-J\f[\u0000{M\u0007JR:@\u001cW\u0004>"));
         } else {
            DatagramPacket var3 = new DatagramPacket(var1.data, var1.data.length);
            this.g.receive(var3);
            if (e.isCommsEnabled()) {
               String var4 = this.d != null ? this.d.getHostAddress() : b("X\u0010Bu\u0013F\u000e");
               e.comms(b(":{1\u001ej>{6{a1j7\b\u0019HM\u001d.Q\u000b[)") + var3.getAddress() + b("R\u001e") + var3.getPort() + b("5\u001e_{O\u0001M\u0006>M\rL)") + var4 + ":" + this.c + b("@4") + ByteFormatter.toString(var3.getData(), var3.getOffset(), var3.getLength()) + b("b\u0017"));
            }

            var1.length = var3.getLength();
            if (var2) {
               InetAddress var8 = var3.getAddress();
               int var5 = var3.getPort();
               UdpEntity var6 = new UdpEntity();
               var6.initialize(var8, var5);
               var6.setProvider(this);
               return var6;
            } else {
               return null;
            }
         }
      } catch (IOException var7) {
         throw new SnmpTransportException(var7.toString());
      }
   }

   public String toString() {
      StringBuffer var1;
      label11: {
         var1 = new StringBuffer();
         var1.append(b("@k6\u000b\u0019"));
         if (this.g != null) {
            var1.append(this.g.getLocalAddress()).append(":");
            var1.append(this.g.getLocalPort());
            if (!SnmpPDU.i) {
               break label11;
            }
         }

         var1.append(":");
      }

      var1.append(")");
      return var1.toString();
   }

   public void useDatagramOffset(boolean var1) {
      this.b = var1;
   }

   private static String b(String var0) {
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
               var10003 = 62;
               break;
            case 2:
               var10003 = 114;
               break;
            case 3:
               var10003 = 91;
               break;
            default:
               var10003 = 35;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class BufferedProvider extends BufferedTransportProvider {
      public BufferedProvider() {
         super(new UdpTransportProvider());
      }
   }

   public static class Params extends TransportProvider.Params {
      private boolean c = true;
      private int b = -1;
      private int a = -1;

      public Params() {
      }

      public Params(int var1) throws UnknownHostException {
         super((InetAddress)null, var1);
      }

      public Params(String var1, int var2) throws UnknownHostException {
         super(InetAddress.getByName(var1), var2);
      }

      public Params(InetAddress var1, int var2) {
         super(var1, var2);
      }

      public void useDatagramOffset(boolean var1) {
         this.c = var1;
      }

      public boolean useDatagramOffset() {
         return this.c;
      }

      public int getTransportType() {
         return 1;
      }

      public SnmpOid getTransportDomain() {
         return Snmp.snmpUDPDomain;
      }

      public int getUDPSendBufferSize() {
         return this.b;
      }

      public void setUDPSendBufferSize(int var1) {
         this.b = var1;
      }

      public int getUDPReceiveBufferSize() {
         return this.a;
      }

      public void setUDPReceiveBufferSize(int var1) {
         this.a = var1;
      }
   }
}
