package monfox.toolkit.snmp.mgr;

import java.io.IOException;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;

class g implements Runnable {
   private SnmpParameters a = null;
   private SnmpVarBindList b = null;
   private byte[] c = null;
   private byte[] d = null;
   private byte[] e = null;
   private byte[] f = null;
   private int g = 161;
   private SnmpExplorer h = null;

   g(SnmpExplorer var1, SnmpParameters var2, SnmpVarBindList var3, byte[] var4, byte[] var5, byte[] var6, int var7) throws IOException, SnmpException {
      this.h = var1;
      this.a = var2;
      this.b = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
   }

   g(SnmpExplorer var1, SnmpParameters var2, SnmpVarBindList var3, byte[] var4, byte[] var5, int var6) throws IOException, SnmpException {
      this.h = var1;
      this.a = var2;
      this.b = var3;
      this.c = var4;
      this.f = var5;
      this.g = var6;
   }

   public void run() {
      if (this.e == null) {
         this.h.a(this.a, this.b, this.c, this.f, this.g);
         if (!SnmpSession.B) {
            return;
         }
      }

      this.h.a(this.a, this.b, this.d, this.e, this.f, this.g);
   }
}
