package monfox.toolkit.snmp.v3;

import monfox.toolkit.snmp.engine.SnmpMessageParameters;
import monfox.toolkit.snmp.util.ByteFormatter;

public class V3SnmpMessageParameters implements SnmpMessageParameters {
   private int a = 3;
   private int b = 4096;
   private byte c = 4;
   private byte[] d = new byte[]{4};
   private byte[] e = new byte[0];
   private byte[] f = new byte[0];
   private static final String g = "$Id: V3SnmpMessageParameters.java,v 1.7 2003/02/26 16:48:23 sking Exp $";

   public V3SnmpMessageParameters() {
   }

   public V3SnmpMessageParameters(int var1) {
      this.a = var1;
   }

   public int getVersion() {
      return this.a;
   }

   public void setVersion(int var1) {
      this.a = var1;
   }

   public boolean isAuth() {
      return (this.c & 1) != 0;
   }

   public boolean isPriv() {
      return (this.c & 2) != 0;
   }

   public boolean isReportable() {
      return (this.c & 4) != 0;
   }

   public byte getFlags() {
      return this.c;
   }

   public byte[] getFlagBuf() {
      return this.d;
   }

   public void setFlags(byte var1) {
      this.c = var1;
      this.d = new byte[]{var1};
   }

   public int getMaxSize() {
      return this.b;
   }

   public void setMaxSize(int var1) {
      this.b = var1;
   }

   public byte[] getContextEngineID() {
      return this.e;
   }

   public byte[] getContextName() {
      return this.f;
   }

   public void setContextEngineID(byte[] var1) {
      this.e = var1;
   }

   public void setContextName(byte[] var1) {
      this.f = var1;
   }

   public void setContextEngineID(String var1) {
      this.e = var1.getBytes();
   }

   public void setContextName(String var1) {
      this.f = var1.getBytes();
   }

   public String toString() {
      int var2 = SnmpSecurityCoderException.g;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("A<\u0004t=Vwmf*R2")).append(this.b);
      var1.append(',');
      switch (this.c & 3) {
         case 0:
            var1.append(a("Dj]C5AjR2>XNK{8y`n}9A"));
            if (var2 == 0) {
               break;
            }
         case 1:
            var1.append(a("Dj]C5AjR21B{VA?g}Wy"));
            if (var2 == 0) {
               break;
            }
         case 3:
            var1.append(a("Dj]C5AjR21B{V_\"^y"));
            if (var2 == 0) {
               break;
            }
         case 2:
         default:
            var1.append(a("Dj]C5AjR2o\b0\u0016") + this.c + ")");
      }

      var1.append(a("\u001blQa$RwJJ>PfPj\u0019s2")).append(ByteFormatter.toHexString(this.e));
      var1.append(a("\u001blQa$RwJA1Zj\u0003")).append(new String(this.f));
      var1.append('}');
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
               var10003 = 55;
               break;
            case 1:
               var10003 = 15;
               break;
            case 2:
               var10003 = 62;
               break;
            case 3:
               var10003 = 15;
               break;
            default:
               var10003 = 80;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
