package monfox.toolkit.snmp.ext;

import java.io.Serializable;

public class SnmpError implements Serializable {
   public static final int errNone = 0;
   public static final int errTimeout = 1;
   public static final int errResponse = 2;
   public static final int errException = 3;
   public static final int errCommunication = 4;
   private int a;
   private String b;
   private Object c;
   private static String[] d = new String[]{a("\u0003</\u0001U\b+"), a("\u0003</\u001bS\u000b+2:N"), a("\u0003</\u001d_\u0015>2!I\u0003"), a("\u0003</\nB\u0005+-;S\t "), a("\u0003</\fU\u000b#(!S\u0005/)&U\b")};
   private static final String e = "$Id: SnmpError.java,v 1.3 2001/06/20 21:44:55 sking Exp $";

   public SnmpError(int var1, String var2) {
      this(var1, var2, (Object)null);
   }

   public SnmpError(int var1, String var2, Object var3) {
      this.a = -1;
      this.b = null;
      this.c = null;
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public int getCode() {
      return this.a;
   }

   public String getMessage() {
      return this.b;
   }

   public Object getInfo() {
      return this.c;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a(this.a)).append(":");
      var1.append(this.b);
      return var1.toString();
   }

   private static String a(int var0) {
      return var0 >= 0 && var0 <= 4 ? d[var0] : String.valueOf(var0);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 102;
               break;
            case 1:
               var10003 = 78;
               break;
            case 2:
               var10003 = 93;
               break;
            case 3:
               var10003 = 79;
               break;
            default:
               var10003 = 58;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
