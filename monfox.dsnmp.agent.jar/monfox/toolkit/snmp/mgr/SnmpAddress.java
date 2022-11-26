package monfox.toolkit.snmp.mgr;

import java.io.Serializable;
import java.net.InetAddress;

public class SnmpAddress implements Serializable {
   private InetAddress a = null;
   private int b = 161;
   private static final String c = "$Id: SnmpAddress.java,v 1.8 2003/01/17 23:57:03 sking Exp $";

   public SnmpAddress(InetAddress var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   public InetAddress getAddress() {
      return this.a;
   }

   public int getPort() {
      return this.b;
   }

   public String toString() {
      StringBuffer var1;
      label11: {
         var1 = new StringBuffer();
         if (this.a != null) {
            var1.append(this.a.getHostAddress());
            if (!SnmpSession.B) {
               break label11;
            }
         }

         var1.append(a("}Q\u0002\u00143%[\u001f\\!2\u0001"));
      }

      var1.append(":");
      var1.append(this.b);
      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 65;
               break;
            case 1:
               var10003 = 63;
               break;
            case 2:
               var10003 = 109;
               break;
            case 3:
               var10003 = 57;
               break;
            default:
               var10003 = 82;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
