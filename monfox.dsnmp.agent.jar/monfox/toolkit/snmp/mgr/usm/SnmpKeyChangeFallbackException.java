package monfox.toolkit.snmp.mgr.usm;

public class SnmpKeyChangeFallbackException extends Exception {
   static final long serialVersionUID = 2571716671191174064L;
   private int a;
   private byte[] b;
   private byte[] c;
   private static final String d = "$Id: SnmpKeyChangeFallbackException.java,v 1.1 2004/12/20 22:45:20 sking Exp $";

   SnmpKeyChangeFallbackException(String var1, int var2, byte[] var3, byte[] var4) {
      super(var1);
      this.a = var2;
      this.b = var3;
      this.c = var4;
   }

   public boolean isAuthKey() {
      return this.a == 1;
   }

   public boolean isPrivKey() {
      return !this.isAuthKey();
   }

   public byte[] getOriginalKey() {
      return this.b;
   }

   public byte[] getNewKey() {
      return this.c;
   }
}
