package monfox.toolkit.snmp.agent.modules;

import java.beans.IntrospectionException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAccessControlModel;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibModule;
import monfox.toolkit.snmp.agent.V2cacm;
import monfox.toolkit.snmp.agent.beans.StandardBeanAdaptor;
import monfox.toolkit.snmp.agent.beans.StandardTableBeanAdaptor;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpModuleIdentityInfo;

public class SnmpV2Mib implements SnmpMibModule {
   private SnmpAgent a;
   private SnmpMib b;
   private SnmpEngine c;
   private StandardTableBeanAdaptor d;
   private StandardBeanAdaptor e;
   private String f;
   private SnmpOid g;
   private int h;
   private String i;
   private String j;
   private String k;
   private int l;
   private int m;
   private Vector n;
   private V2cacm o;
   private int p;
   // $FF: synthetic field
   static Class q;

   public SnmpV2Mib(SnmpAgent var1) throws SnmpException {
      int var4 = SnmpFrameworkMib.g;
      super();
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = null;
      this.e = null;
      this.f = c("9\u0014\t|=\u0004\u0018[Q(\u0003\u0012FV");
      this.h = 0;
      this.l = 0;
      this.a = var1;
      this.b = var1.getMib();
      this.c = var1.getEngine();
      SnmpAccessControlModel var2 = this.a.getAccessControlModel();
      if (var2 instanceof V2cacm) {
         this.o = (V2cacm)var2;
      }

      this.loadFromProperties(System.getProperties());

      try {
         this.e = new StandardBeanAdaptor(this.b, this);
         this.e.attach();
         this.d = new StandardTableBeanAdaptor(this.b, q == null ? (q = b(c("\u001a\u0014G^7\u000fU]W7\u001b\u0010@Lv\u0004\u0015DHv\u0016\u001cLV,Y\u0016F\\-\u001b\u001eZ\u0016\u000b\u0019\u0016Ynj:\u0012K\u001c\u000b\u000e\bfj\u001d\u0019\u000f[A"))) : q, c("\u0004\u0002Zw\n#\u001aKT="));
         this.d.attach();
      } catch (IntrospectionException var5) {
         throw new SnmpException(var5.getMessage());
      }

      this.updateSysORTable();
      if (var4 != 0) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public void sendColdStart() throws SnmpException {
      this.a.getNotifier().sendNotification(c("\u0014\u0014E\\\u000b\u0003\u001a[L"));
   }

   public void sendWarmStart() throws SnmpException {
      this.a.getNotifier().sendNotification(c("\u0000\u001a[U\u000b\u0003\u001a[L"));
   }

   public void sendAuthenticationFailure() throws SnmpException {
      this.a.getNotifier().sendNotification(c("\u0016\u000e]P=\u0019\u000f@[9\u0003\u0012FV\u001e\u0016\u0012EM*\u0012"));
   }

   public String getModuleName() {
      return c("$5dh.EVdq\u001a");
   }

   public void loadFromProperties(Properties var1) throws SnmpException {
      int var4 = SnmpFrameworkMib.g;
      Properties var2 = System.getProperties();
      this.f = var1.getProperty(c("\u0004\u0002Z|=\u0004\u0018["), var2.getProperty(c("\u0018\b\u0007V9\u001a\u001e")) + " " + var2.getProperty(c("\u0018\b\u0007N=\u0005\b@W6")) + " " + var2.getProperty(c("\u0018\b\u0007Y*\u0014\u0013")));
      this.g = new SnmpOid(this.b.getMetadata(), var1.getProperty(c("\u0004\u0002Zw:\u001d\u001eJL\u00113"), c("FU\u001a\u0016nYJ\u0007\fvFU\u001a\u0000i@U\u0018\u0016iGK\u0019")));
      this.i = var1.getProperty(c("\u0004\u0002Z{7\u0019\u000fH[,"), c("9\u0014\t{7\u0019\u000fH[,"));

      try {
         this.j = InetAddress.getLocalHost().getHostName();
      } catch (UnknownHostException var5) {
         this.j = c("\"\u0015BV7\u0000\u0015");
      }

      this.j = var1.getProperty(c("\u0004\u0002Zv9\u001a\u001e"), this.j);
      this.k = var1.getProperty(c("\u0004\u0002Zt7\u0014\u001a]Q7\u0019"), c("\"\u0015MQ+\u0014\u0017FK=\u0013[eW;\u0016\u000f@W6"));
      this.l = Integer.parseInt(var1.getProperty(c("\u0004\u0002Zk=\u0005\r@[=\u0004"), c("AO")));
      if (SnmpException.b) {
         ++var4;
         SnmpFrameworkMib.g = var4;
      }

   }

   public void updateSysORTable() throws SnmpMibException, SnmpValueException {
      int var6 = SnmpFrameworkMib.g;
      this.d.removeAll();
      SnmpMetadata var1 = this.b.getMetadata();
      int var2 = 1;
      Enumeration var3 = var1.getModuleIdentities();

      while(true) {
         if (var3.hasMoreElements()) {
            SnmpModuleIdentityInfo var4 = (SnmpModuleIdentityInfo)var3.nextElement();
            SysOREntry var5 = new SysOREntry();
            var5.setSysORIndex(var2);
            var5.setSysORID(var4.getOid());
            var5.setSysORDescr(a(var4.getDescription()));
            var5.setSysORUpTime(this.getSysUpTime());
            ++var2;
            this.d.addRow(var5);
            if (var6 != 0) {
               break;
            }

            if (var6 == 0) {
               continue;
            }
         }

         this.m = this.getSysUpTime();
         break;
      }

   }

   private static String a(String var0) {
      int var5 = SnmpFrameworkMib.g;
      if (var0 == null) {
         return "";
      } else {
         StringBuffer var1 = new StringBuffer();
         char var2 = ' ';
         int var3 = 0;

         String var10000;
         while(true) {
            if (var3 < var0.length()) {
               var10000 = var0;
               if (var5 != 0) {
                  break;
               }

               char var4 = var0.charAt(var3);
               if (var4 == ' ' && var2 != ' ' || Character.isLetterOrDigit(var4) || var4 == '.') {
                  var1.append(var4);
               }

               var2 = var4;
               ++var3;
               if (var5 == 0) {
                  continue;
               }
            }

            var10000 = var1.toString();
            break;
         }

         return var10000;
      }
   }

   public String getSysDescr() {
      return this.f;
   }

   public void setSysDescr(String var1) {
      this.f = var1;
   }

   public SnmpOid getSysObjectID() {
      return this.g;
   }

   public void setSysObjectID(SnmpOid var1) {
      this.g = var1;
   }

   public int getSysUpTime() {
      return this.c.getSysUpTime();
   }

   public String getSysContact() {
      return this.i;
   }

   public void setSysContact(String var1) {
      this.i = var1;
   }

   public String getSysName() {
      return this.j;
   }

   public void setSysName(String var1) {
      this.j = var1;
   }

   public String getSysLocation() {
      return this.k;
   }

   public void setSysLocation(String var1) {
      this.k = var1;
   }

   public int getSysServices() {
      return this.l;
   }

   public void setSysServices(int var1) {
      this.l = var1;
   }

   public int getSysORLastChange() {
      return this.m;
   }

   public void setSysORLastChange(int var1) {
      this.m = var1;
   }

   public Vector getSysORTable() {
      return this.n;
   }

   public int getSnmpInPkts() {
      return this.c.getSnmpInPkts();
   }

   public int getSnmpInBadVersions() {
      return this.c.getSnmpInBadVersions();
   }

   public int getSnmpInBadCommunityNames() {
      return this.o != null ? this.o.getSnmpInBadCommunityNames() : 0;
   }

   public int getSnmpInBadCommunityUses() {
      return this.o != null ? this.o.getSnmpInBadCommunityUses() : 0;
   }

   public int getSnmpInASNParseErrs() {
      return this.c.getSnmpInASNParseErrs();
   }

   public int getSnmpEnableAuthenTraps() {
      return this.p;
   }

   public void setSnmpEnableAuthenTraps(int var1) {
      this.p = var1;
   }

   public int getSnmpSilentDrops() {
      return this.c.getSnmpSilentDrops();
   }

   public int getSnmpProxyDrops() {
      return this.c.getSnmpProxyDrops();
   }

   // $FF: synthetic method
   static Class b(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String c(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 119;
               break;
            case 1:
               var10003 = 123;
               break;
            case 2:
               var10003 = 41;
               break;
            case 3:
               var10003 = 56;
               break;
            default:
               var10003 = 88;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class SysOREntry {
      private int a;
      private SnmpOid b;
      private String c;
      private int d;

      public int getSysORIndex() {
         return this.a;
      }

      public void setSysORIndex(int var1) {
         this.a = var1;
      }

      public SnmpOid getSysORID() {
         return this.b;
      }

      public void setSysORID(SnmpOid var1) {
         this.b = var1;
      }

      public String getSysORDescr() {
         return this.c;
      }

      public void setSysORDescr(String var1) {
         this.c = var1;
      }

      public int getSysORUpTime() {
         return this.d;
      }

      public void setSysORUpTime(int var1) {
         this.d = var1;
      }
   }
}
