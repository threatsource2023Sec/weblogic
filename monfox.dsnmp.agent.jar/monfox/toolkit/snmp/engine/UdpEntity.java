package monfox.toolkit.snmp.engine;

import java.net.InetAddress;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

public class UdpEntity extends TransportEntity {
   private int a = -1;
   private InetAddress b = null;
   private int c;
   private static final String d = "$Id: UdpEntity.java,v 1.5 2006/08/01 19:54:10 sking Exp $";

   public UdpEntity() {
      this.c = Snmp.DEFAULT_MAX_SIZE;
   }

   public void initialize(InetAddress var1, int var2) {
      this.b = var1;
      this.a = var2;
   }

   public int getTransportType() {
      return 1;
   }

   public SnmpOid getTransportDomain() {
      return Snmp.snmpUDPDomain;
   }

   public InetAddress getAddress() {
      return this.b;
   }

   public int getPort() {
      return this.a;
   }

   public int getMaxSize() {
      return this.c;
   }

   public void setMaxSize(int var1) {
      this.c = var1;
   }

   public String toString() {
      StringBuffer var1;
      label11: {
         var1 = new StringBuffer();
         var1.append(b(">IN\u001fE\u001fDJ#\u0011"));
         if (this.b != null) {
            var1.append(this.b.getHostAddress());
            if (!SnmpPDU.i) {
               break label11;
            }
         }

         var1.append(b("WCQwJ\u000fIL?X\u0018\u0013"));
      }

      var1.append(":");
      var1.append(this.a);
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
               var10003 = 107;
               break;
            case 1:
               var10003 = 45;
               break;
            case 2:
               var10003 = 62;
               break;
            case 3:
               var10003 = 90;
               break;
            default:
               var10003 = 43;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
