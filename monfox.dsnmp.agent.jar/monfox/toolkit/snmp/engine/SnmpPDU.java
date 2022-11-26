package monfox.toolkit.snmp.engine;

import java.io.Serializable;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpVarBindList;

public abstract class SnmpPDU implements Serializable {
   static final long serialVersionUID = 3853070754054831731L;
   public static final int getRequest = 160;
   public static final int getNextRequest = 161;
   public static final int getResponse = 162;
   public static final int setRequest = 163;
   public static final int v1Trap = 164;
   public static final int getBulkRequest = 165;
   public static final int informRequest = 166;
   public static final int v2Trap = 167;
   public static final int report = 168;
   private int a = 0;
   private byte[] b = new byte[0];
   private int c = 0;
   private int d = 0;
   private long e = 0L;
   private SnmpVarBindList f;
   private static String[] g = new String[]{a("\br\u00047l\u001eb\u0015\u0016}"), a("\br\u0004+l\u0017c\"\u0000x\u001ar\u0003\u0011"), a("\br\u00047l\u001cg\u001f\u000bz\n"), a("\u001cr\u00047l\u001eb\u0015\u0016}"), a("\u001be\u0011\u0015_^"), a("\br\u0004'|\u0003|\"\u0000x\u001ar\u0003\u0011"), a("\u0006y\u0016\n{\u0002E\u0015\u0014|\nd\u0004"), a("\u001be\u0011\u0015_]"), a("\u001dr\u0000\n{\u001b")};
   private static final String h = "$Id: SnmpPDU.java,v 1.5 2002/04/30 13:58:38 samin Exp $";
   public static boolean i;

   public SnmpPDU() {
   }

   public SnmpPDU(int var1, int var2, byte[] var3, int var4) {
      this.c = var1;
      this.a = var2;
      this.b = var3;
      this.d = var4;
   }

   public int getVersion() {
      return this.a;
   }

   public void setVersion(int var1) {
      this.a = var1;
   }

   public byte[] getCommunity() {
      return this.b;
   }

   public void setCommunity(byte[] var1) {
      this.b = var1;
   }

   public int getType() {
      return this.c;
   }

   public void setType(int var1) {
      this.c = var1;
   }

   public int getRequestId() {
      return this.d;
   }

   public void setRequestId(int var1) {
      this.d = var1;
   }

   public void setVarBindList(SnmpVarBindList var1) {
      this.f = var1;
   }

   public SnmpVarBindList getVarBindList() {
      return this.f;
   }

   public static String getTypeString(int var0) {
      return typeToString(var0);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("e7PE\u007f\ne\u0003\ff\u0001*")).append(Snmp.versionToString(this.a));
      var1.append(a("e7PEj\u0000z\u001d\u0010g\u0006c\tX")).append(new String(this.b));
      var1.append(a("e7PEy\u000bb$\u001cy\n*")).append(typeToString(this.c));
      var1.append(a("e7PE{\nf\u0005\u0000z\u001b^\u0014X")).append(this.d);
      this.specificToString(var1);
      if (this.f != null) {
         var1.append(a("e7PE\u007f\u000ee2\fg\u000b[\u0019\u0016}R7"));
         this.f.toString(var1);
         var1.append("\n");
      }

      return var1.toString();
   }

   public void setTimestamp(long var1) {
      this.e = var1;
   }

   public long getTimestamp() {
      return this.e;
   }

   protected void specificToString(StringBuffer var1) {
   }

   /** @deprecated */
   public static String TypeToString(int var0) {
      return typeToString(var0);
   }

   public static String typeToString(int var0) {
      int var1 = var0 - 160;
      return var1 < g.length && var1 >= 0 ? g[var1] : a("\u0006y\u0006\u0004e\u0006s !\\;n\u0000\u0000");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 111;
               break;
            case 1:
               var10003 = 23;
               break;
            case 2:
               var10003 = 112;
               break;
            case 3:
               var10003 = 101;
               break;
            default:
               var10003 = 9;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
