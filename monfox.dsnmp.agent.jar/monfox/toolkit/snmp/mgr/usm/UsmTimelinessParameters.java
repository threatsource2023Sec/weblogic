package monfox.toolkit.snmp.mgr.usm;

import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;

public class UsmTimelinessParameters {
   private boolean a = false;
   private SnmpEngineID b;
   private int c;
   private int d;
   private int e;
   private int f;
   private V3SnmpMessageParameters g;
   private byte[] h;

   UsmTimelinessParameters(boolean var1, SnmpEngineID var2, int var3, int var4, int var5, int var6, V3SnmpMessageParameters var7, byte[] var8) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
      this.h = var8;
   }

   public SnmpEngineID getEngineID() {
      return this.b;
   }

   public int getEngineBoots() {
      return this.c;
   }

   public int getEngineTime() {
      return this.d;
   }

   public int getMsgAuthoritativeEngineBoots() {
      return this.e;
   }

   public int getMsgAuthoritativeEngineTime() {
      return this.f;
   }

   public V3SnmpMessageParameters getHeaderParams() {
      return this.g;
   }

   public byte[] getUsername() {
      return this.h;
   }
}
