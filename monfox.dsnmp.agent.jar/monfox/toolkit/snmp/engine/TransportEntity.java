package monfox.toolkit.snmp.engine;

import java.net.InetAddress;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

public abstract class TransportEntity {
   private TransportProvider a = null;
   private static Hashtable b = new Hashtable();
   private static Hashtable c = new Hashtable();
   private static final String d = "$Id: TransportEntity.java,v 1.4 2014/07/28 13:44:10 sking Exp $";
   // $FF: synthetic field
   static Class e;
   // $FF: synthetic field
   static Class f;

   public abstract int getTransportType();

   public SnmpOid getTransportDomain() {
      return Snmp.monfoxUndefinedDomain;
   }

   public abstract void initialize(InetAddress var1, int var2);

   public abstract InetAddress getAddress();

   public abstract int getPort();

   public abstract int getMaxSize();

   public abstract void setMaxSize(int var1);

   public TransportProvider getProvider() {
      return this.a;
   }

   public void setProvider(TransportProvider var1) {
      this.a = var1;
   }

   public static void addTransportType(int var0, Class var1) {
      b.put(new Integer(var0), var1);
   }

   public static void addTransportType(int var0, SnmpOid var1, Class var2) {
      b.put(new Integer(var0), var2);
      c.put(var1, var2);
   }

   public static TransportEntity newInstance(int var0, InetAddress var1, int var2) throws NoSuchElementException {
      Class var3 = (Class)b.get(new Integer(var0));
      if (var3 == null) {
         throw new NoSuchElementException(b("\u00072;7&*5;0!(3h\u0014<;)!D") + var0 + ".");
      } else {
         try {
            TransportEntity var4 = (TransportEntity)var3.newInstance();
            var4.initialize(var1, var2);
            return var4;
         } catch (ClassCastException var5) {
            throw new NoSuchElementException(b("\u00003m\u0005? 9;0!(3h\u0014<;);!==4o\u001ds\n1z\u0017 s}") + var0 + b("g}3") + var5 + ")");
         } catch (IllegalAccessException var6) {
            throw new NoSuchElementException(b("\u00003m\u0005? 9;0!(3h\u0014<;);!==4o\u001ds\n1z\u0017 s}") + var0 + b("g}3") + var6 + ")");
         } catch (InstantiationException var7) {
            throw new NoSuchElementException(b("\u00003m\u0005? 9;0!(3h\u0014<;);!==4o\u001ds\n1z\u0017 s}") + var0 + b("g}3") + var7 + ")");
         }
      }
   }

   public static TransportEntity newInstance(SnmpOid var0, InetAddress var1, int var2) throws NoSuchElementException {
      Class var3 = (Class)c.get(var0);
      if (var3 == null) {
         throw new NoSuchElementException(b("\u00072;7&*5;0!(3h\u0014<;)!D") + var0 + ".");
      } else {
         try {
            TransportEntity var4 = (TransportEntity)var3.newInstance();
            var4.initialize(var1, var2);
            return var4;
         } catch (ClassCastException var5) {
            throw new NoSuchElementException(b("\u00003m\u0005? 9;0!(3h\u0014<;);!==4o\u001ds\n1z\u0017 s}") + var0 + b("g}3") + var5 + ")");
         } catch (IllegalAccessException var6) {
            throw new NoSuchElementException(b("\u00003m\u0005? 9;0!(3h\u0014<;);!==4o\u001ds\n1z\u0017 s}") + var0 + b("g}3") + var6 + ")");
         } catch (InstantiationException var7) {
            throw new NoSuchElementException(b("\u00003m\u0005? 9;0!(3h\u0014<;);!==4o\u001ds\n1z\u0017 s}") + var0 + b("g}3") + var7 + ")");
         }
      }
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   static {
      addTransportType(1, Snmp.snmpUDPDomain, e == null ? (e = a(b("$2u\u0002<1so\u000b<%6r\u0010}:3v\u0014},3|\r=,sN\u0000#\f3o\r'0"))) : e);
      addTransportType(1, Snmp.transportDomainUdpIpv4, e == null ? (e = a(b("$2u\u0002<1so\u000b<%6r\u0010}:3v\u0014},3|\r=,sN\u0000#\f3o\r'0"))) : e);
      addTransportType(1, Snmp.transportDomainUdpIpv6, e == null ? (e = a(b("$2u\u0002<1so\u000b<%6r\u0010}:3v\u0014},3|\r=,sN\u0000#\f3o\r'0"))) : e);
      addTransportType(2, Snmp.snmpTCPDomain, f == null ? (f = a(b("$2u\u0002<1so\u000b<%6r\u0010}:3v\u0014},3|\r=,sO\u0007#\f3o\r'0"))) : f);
      addTransportType(2, Snmp.transportDomainTcpIpv4, f == null ? (f = a(b("$2u\u0002<1so\u000b<%6r\u0010}:3v\u0014},3|\r=,sO\u0007#\f3o\r'0"))) : f);
      addTransportType(2, Snmp.transportDomainTcpIpv6, f == null ? (f = a(b("$2u\u0002<1so\u000b<%6r\u0010}:3v\u0014},3|\r=,sO\u0007#\f3o\r'0"))) : f);
   }

   private static String b(String var0) {
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
               var10003 = 93;
               break;
            case 2:
               var10003 = 27;
               break;
            case 3:
               var10003 = 100;
               break;
            default:
               var10003 = 83;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
