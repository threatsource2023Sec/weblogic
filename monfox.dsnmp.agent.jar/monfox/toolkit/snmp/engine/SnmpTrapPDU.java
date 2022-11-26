package monfox.toolkit.snmp.engine;

import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpOid;

public class SnmpTrapPDU extends SnmpPDU {
   static final long serialVersionUID = 1591665230982958382L;
   private SnmpOid a;
   private SnmpIpAddress b;
   private int c;
   private int d;
   private long e;
   private static String[] g = new String[]{a("\u0013y\n<s\u0004w\u0014,"), a("\u0007w\u00145s\u0004w\u0014,"), a("\u001c\u007f\b3d\u001fa\b"), a("\u001c\u007f\b3u\u0000"), a("\u0011c\u00120E\u001eb\u000f;A\u0004\u007f\t6f\u0011\u007f\n-R\u0015"), a("\u0015q\u0016\u0016E\u0019q\u000e:O\u0002Z\t+S"), a("\u0015x\u0012=R\u0000d\u000f+E#f\u0003;I\u0016\u007f\u0005")};
   private static final String h = "$Id: SnmpTrapPDU.java,v 1.3 2002/04/30 13:58:38 samin Exp $";

   public SnmpTrapPDU() {
      this.setType(164);
   }

   public SnmpOid getEnterprise() {
      return this.a;
   }

   public SnmpIpAddress getAgentAddr() {
      return this.b;
   }

   public int getGenericTrap() {
      return this.c;
   }

   public int getSpecificTrap() {
      return this.d;
   }

   public long getTimestamp() {
      return this.e;
   }

   public void setEnterprise(SnmpOid var1) {
      this.a = var1;
   }

   public void setAgentAddr(SnmpIpAddress var1) {
      this.b = var1;
   }

   public void setGenericTrap(int var1) {
      this.c = var1;
   }

   public void setSpecificTrap(int var1) {
      this.d = var1;
   }

   public void setTimestamp(int var1) {
      this.e = (long)var1;
   }

   protected void specificToString(StringBuffer var1) {
      var1.append(a("zs\b,E\u0002f\u00141S\u0015+")).append(this.a);
      var1.append(a("zw\u0001=N\u0004W\u0002<RM")).append(this.b);
      var1.append(a("zq\u00036E\u0002\u007f\u0005\fR\u0011f[")).append(this.getGenericTrapString());
      var1.append(a("ze\u0016=C\u0019p\u000f;t\u0002w\u0016e")).append(this.d);
      var1.append(a("zb\u000f5E#b\u00075PM")).append(this.e).append("\n");
   }

   public String getGenericTrapString() {
      return this.c >= 0 && this.c <= 6 ? g[this.c] : String.valueOf(this.c);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 112;
               break;
            case 1:
               var10003 = 22;
               break;
            case 2:
               var10003 = 102;
               break;
            case 3:
               var10003 = 88;
               break;
            default:
               var10003 = 32;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
