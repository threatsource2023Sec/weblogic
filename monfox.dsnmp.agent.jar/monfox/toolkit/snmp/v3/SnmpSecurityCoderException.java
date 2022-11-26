package monfox.toolkit.snmp.v3;

import monfox.toolkit.snmp.engine.SnmpCoderException;

public class SnmpSecurityCoderException extends SnmpCoderException {
   private int a = 0;
   private int b = 0;
   private boolean c = true;
   private int d = 0;
   private byte[] e = null;
   private static final String[] f = new String[]{a("~\u001d\f\u0001\th\u001a\u0019\u0018\to"), a("~\u001d\f\u0004\u001c{\u001c\r\u0005\to\f\f\u0014\u000f~\u0001\u0016\u0005\u0015t\u001e\u0010\u0015\tg"), a("~\u001d\f\u0004\u001c{\u001c\r\u0005\to\f\f\u0014\u000f~\u0001\u0016\u0005\u0015t\u001f\u001a\u0007\tg"), a("~\u001d\u0014\u001f\u0003|\u001d\u0000\u0014\u0002l\u001a\u0011\u0014\u0013b\u0017"), a("~\u001d\u0014\u001f\u0003|\u001d\u0000\u0004\u001fn\u0001\u0000\u001f\rf\u0016"), a("e\u001c\u000b\u000e\u0005e\f\u000b\u0018\u0001n\f\b\u0018\u0002o\u001c\b"), a("j\u0006\u000b\u0019\u0013m\u0012\u0016\u001d\u0019y\u0016"), a("o\u0016\u001c\u0003\u0015{\u0007\u0016\u001e\u0002t\u0016\r\u0003\u0003y"), a("n\u001d\u001c\u0003\u0015{\u0007\u0016\u001e\u0002t\u0016\r\u0003\u0003y")};
   public static int g;

   public SnmpSecurityCoderException(int var1, String var2, boolean var3, int var4, byte[] var5) {
      super(var2);
      this.a = var1;
      this.c = var3;
      this.d = var4;
      this.e = var5;
   }

   public SnmpSecurityCoderException(int var1, String var2, boolean var3) {
      super(var2);
      this.a = var1;
      this.c = var3;
   }

   public SnmpSecurityCoderException(int var1, boolean var2) {
      this.a = var1;
      this.c = var2;
   }

   public SnmpSecurityCoderException(int var1, boolean var2, int var3, byte[] var4) {
      this.a = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
   }

   public SnmpSecurityCoderException(int var1, String var2) {
      super(var2);
      this.a = var1;
   }

   public SnmpSecurityCoderException(int var1) {
      this.a = var1;
   }

   public byte[] getUserName() {
      return this.e;
   }

   public int getSecurityLevel() {
      return this.d;
   }

   public boolean isReportable() {
      return this.c;
   }

   public int getSpecificError() {
      return this.a;
   }

   public String getSpecificErrorString() {
      return a(this.a);
   }

   public void setMsgId(int var1) {
      this.b = var1;
   }

   public int getMsgId() {
      return this.b;
   }

   public String getMessage() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.getSpecificErrorString());
      var1.append(a("\u0003>,6\u0005On")).append(this.getMsgId()).append(")");
      if (super.getMessage() != null) {
         var1.append(a("\u0011s")).append(super.getMessage());
      }

      return var1.toString();
   }

   private static String a(int var0) {
      return var0 >= 0 && var0 < f.length ? f[var0] : a("\u0014l`");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 43;
               break;
            case 1:
               var10003 = 83;
               break;
            case 2:
               var10003 = 95;
               break;
            case 3:
               var10003 = 81;
               break;
            default:
               var10003 = 76;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
