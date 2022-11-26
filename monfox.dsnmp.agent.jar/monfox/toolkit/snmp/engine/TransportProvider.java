package monfox.toolkit.snmp.engine;

import java.net.InetAddress;
import java.util.Hashtable;
import java.util.Properties;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

public abstract class TransportProvider {
   private Properties a = null;
   private boolean b = false;
   private PushListener c = null;
   private static Hashtable d = new Hashtable();
   private static Hashtable e = new Hashtable();
   private static final String f = "$Id: TransportProvider.java,v 1.6 2013/09/06 22:17:00 sking Exp $";
   // $FF: synthetic field
   static Class g;
   // $FF: synthetic field
   static Class h;

   public abstract int getTransportType();

   public SnmpOid getTransportDomain() {
      return Snmp.monfoxUndefinedDomain;
   }

   protected void initialize(Params var1) throws SnmpTransportException {
      this.initialize(var1.getLocalAddress(), var1.getLocalPort());
      this.b = var1.isPushProvider();
   }

   public boolean isPushProvider() {
      return this.b;
   }

   public abstract void initialize(InetAddress var1, int var2) throws SnmpTransportException;

   public void setParameter(String var1, Object var2) {
   }

   public abstract void shutdown() throws SnmpTransportException;

   public abstract Object send(Object var1, TransportEntity var2) throws SnmpTransportException;

   public abstract TransportEntity receive(SnmpBuffer var1, boolean var2) throws SnmpTransportException;

   public abstract boolean isActive();

   void a(PushListener var1) {
      this.c = var1;
   }

   PushListener a() {
      return this.c;
   }

   public void pushMessage(TransportEntity var1, SnmpBuffer var2) {
      if (this.c != null) {
         this.c.pushMessage(var1, var2);
      }

   }

   public Properties getUserProperties() {
      if (this.a == null) {
         this.a = new Properties();
      }

      return this.a;
   }

   public static void addTransportProvider(int var0, Class var1) {
      d.put(new Integer(var0), var1);
   }

   public static void addTransportProvider(int var0, SnmpOid var1, Class var2) {
      d.put(new Integer(var0), var2);
      e.put(var1, var2);
   }

   public static TransportProvider newInstance(int var0, InetAddress var1, int var2, boolean var3) throws SnmpTransportException {
      TransportProvider var4 = newInstance(var0, var1, var2);
      if (var3) {
         BufferedTransportProvider var5 = new BufferedTransportProvider(var4);
         var5.initialize(var1, var2);
         return var5;
      } else {
         return var4;
      }
   }

   public static TransportProvider newInstance(SnmpOid var0, InetAddress var1, int var2, boolean var3) throws SnmpTransportException {
      TransportProvider var4 = newInstance(var0, var1, var2);
      if (var3) {
         BufferedTransportProvider var5 = new BufferedTransportProvider(var4);
         var5.initialize(var1, var2);
         return var5;
      } else {
         return var4;
      }
   }

   public static TransportProvider newInstance(int var0, InetAddress var1, int var2) throws SnmpTransportException {
      Class var3 = (Class)d.get(new Integer(var0));
      if (var3 == null) {
         throw new SnmpTransportException(b("Q7gR\u001b|0gU\u001c~64q\u0001m,}!") + var0 + ".");
      } else {
         try {
            TransportProvider var4 = (TransportProvider)var3.newInstance();
            var4.initialize(var1, var2);
            return var4;
         } catch (ClassCastException var5) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var5 + ")");
         } catch (IllegalAccessException var6) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var6 + ")");
         } catch (InstantiationException var7) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var7 + ")");
         }
      }
   }

   public static TransportProvider newInstance(SnmpOid var0, InetAddress var1, int var2) throws SnmpTransportException {
      Class var3 = (Class)e.get(var0);
      if (var3 == null) {
         throw new SnmpTransportException(b("Q7gR\u001b|0gU\u001c~64q\u0001m,}!") + var0 + ".");
      } else {
         try {
            TransportProvider var4 = (TransportProvider)var3.newInstance();
            var4.initialize(var1, var2);
            return var4;
         } catch (ClassCastException var5) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var5 + ")");
         } catch (IllegalAccessException var6) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var6 + ")");
         } catch (InstantiationException var7) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var7 + ")");
         }
      }
   }

   public static TransportProvider newInstance(Params var0) throws SnmpTransportException {
      return newInstance(var0, false);
   }

   public static TransportProvider newInstance(Params var0, boolean var1) throws SnmpTransportException {
      Class var2 = (Class)d.get(new Integer(var0.getTransportType()));
      if (var2 == null) {
         var2 = (Class)e.get(var0.getTransportDomain());
      }

      if (var2 == null) {
         throw new SnmpTransportException(b("Q7gR\u001b|0gU\u001c~64q\u0001m,}!") + var0.getTransportType() + "/" + var0.getTransportDomain() + ".");
      } else {
         TransportProvider var3 = null;

         try {
            var3 = (TransportProvider)var2.newInstance();
            var3.initialize(var0);
         } catch (ClassCastException var5) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var5 + ")");
         } catch (IllegalAccessException var6) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var6 + ")");
         } catch (InstantiationException var7) {
            throw new SnmpTransportException(b("V61`\u0002v<gU\u001c~64q\u0001m,gQ\u001cp..e\u000bmx\u0004m\u000fl+}!") + var0 + b("1xo") + var7 + ")");
         }

         if (var1) {
            BufferedTransportProvider var4 = new BufferedTransportProvider(var3);
            var4.initialize(var0);
            return var4;
         } else {
            return var3;
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
      addTransportProvider(1, Snmp.snmpUDPDomain, g == null ? (g = a(b("r7)g\u0001gv3n\u0001s3.u@l6*q@z6 h\u0000zv\u0012e\u001eK*&o\u001do75u>m71h\nz*"))) : g);
      addTransportProvider(2, Snmp.snmpTCPDomain, h == null ? (h = a(b("r7)g\u0001gv3n\u0001s3.u@l6*q@z6 h\u0000zv\u0013b\u001eK*&o\u001do75u>m71h\nz*"))) : h);
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 31;
               break;
            case 1:
               var10003 = 88;
               break;
            case 2:
               var10003 = 71;
               break;
            case 3:
               var10003 = 1;
               break;
            default:
               var10003 = 110;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public abstract static class Params {
      private InetAddress a = null;
      private int b = -1;
      private boolean c = false;

      public Params() {
      }

      public Params(InetAddress var1, int var2) {
         this.a = var1;
         this.b = var2;
      }

      public InetAddress getLocalAddress() {
         return this.a;
      }

      public int getLocalPort() {
         return this.b;
      }

      public void setLocalAddress(InetAddress var1) {
         this.a = var1;
      }

      public void setLocalPort(int var1) {
         this.b = var1;
      }

      public abstract int getTransportType();

      public SnmpOid getTransportDomain() {
         return Snmp.monfoxUndefinedDomain;
      }

      public void isPushProvider(boolean var1) {
         this.c = var1;
      }

      public boolean isPushProvider() {
         return this.c;
      }
   }

   interface PushListener {
      void pushMessage(TransportEntity var1, SnmpBuffer var2);
   }
}
