package monfox.toolkit.snmp.engine;

import java.net.InetAddress;
import java.net.Socket;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

public class TcpEntity extends TransportEntity {
   private int a;
   private int b;
   private InetAddress c;
   private Socket d;
   private static final String e = "$Id: TcpEntity.java,v 1.3 2006/08/01 19:54:10 sking Exp $";

   public TcpEntity() {
      this.a = Snmp.DEFAULT_MAX_SIZE;
      this.b = -1;
      this.c = null;
      this.d = null;
   }

   public void initialize(InetAddress var1, int var2) {
      this.c = var1;
      this.b = var2;
   }

   public int getTransportType() {
      return 2;
   }

   public SnmpOid getTransportDomain() {
      return Snmp.snmpTCPDomain;
   }

   public InetAddress getAddress() {
      return this.c;
   }

   public int getPort() {
      return this.b;
   }

   public int getMaxSize() {
      return this.a;
   }

   public void setMaxSize(int var1) {
      this.a = var1;
   }

   public String toString() {
      StringBuffer var1;
      label11: {
         var1 = new StringBuffer();
         var1.append(b("\u00015d-\u0018!?`\u0011L"));
         if (this.c != null) {
            var1.append(this.c.getHostAddress());
            if (!SnmpPDU.i) {
               break label11;
            }
         }

         var1.append(b("i8{E\u001712f\r\u0005&h"));
      }

      var1.append(":");
      var1.append(this.b);
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
               var10003 = 85;
               break;
            case 1:
               var10003 = 86;
               break;
            case 2:
               var10003 = 20;
               break;
            case 3:
               var10003 = 104;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
