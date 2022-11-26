package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.util.ByteFormatter;

public class USMSecurityParameters implements SnmpSecurityParameters {
   private byte[] a = new byte[0];
   private int b = 0;
   private int c = 0;
   private int d = 0;
   private byte[] e = null;
   private byte[] f = null;
   private byte[] g = null;
   private static final String h = "$Id: USMSecurityParameters.java,v 1.8 2006/09/14 19:21:59 sking Exp $";

   public int getSecurityModel() {
      return 3;
   }

   public int getSecurityLevel() {
      return this.d;
   }

   public void setSecurityLevel(int var1) {
      this.d = var1;
   }

   public byte[] getSecurityName() {
      return this.e;
   }

   public byte[] getAuthoritativeEngineID() {
      return this.a;
   }

   public int getAuthoritativeEngineBoots() {
      return this.b;
   }

   public int getAuthoritativeEngineTime() {
      return this.c;
   }

   public byte[] getUserName() {
      return this.e;
   }

   public byte[] getAuthenticationParameters() {
      return this.f;
   }

   public byte[] getPrivacyParameters() {
      return this.g;
   }

   public void setUserName(byte[] var1) {
      this.e = var1;
   }

   public void setAuthoritativeEngineID(byte[] var1) {
      this.a = var1;
   }

   public void setAuthoritativeEngineBoots(int var1) {
      this.b = var1;
   }

   public void setAuthoritativeEngineTime(int var1) {
      this.c = var1;
   }

   public void setAuthenticationParameters(byte[] var1) {
      this.f = var1;
   }

   public void setPrivacyParameters(byte[] var1) {
      this.g = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("tx7|T`~..jol3(JHog")).append(ByteFormatter.toHexString(this.a));
      var1.append(a("-j/2GDe=/AdI5)[r6")).append(this.b);
      var1.append(a("-j/2GDe=/Ad_3+J<")).append(this.c);
      var1.append(a("-~)#]Oj7#\u0012")).append(new String(this.e));
      var1.append(a("-j/2GQj('Br6")).append(ByteFormatter.toHexString(this.f));
      var1.append(a("-{(/YQj('Br6")).append(ByteFormatter.toHexString(this.g));
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
               var10003 = 1;
               break;
            case 1:
               var10003 = 11;
               break;
            case 2:
               var10003 = 90;
               break;
            case 3:
               var10003 = 70;
               break;
            default:
               var10003 = 47;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
