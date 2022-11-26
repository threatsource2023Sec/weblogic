package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.engine.SnmpEngine;

public class USMAuthoritativeEngineInfo extends USMEngineInfo {
   private SnmpEngine a;

   public USMAuthoritativeEngineInfo(SnmpEngine var1) {
      super(var1.getEngineID());
      this.a = var1;
   }

   public int getEngineBoots() {
      return this.a.getEngineBoots();
   }

   public int getEngineTime() {
      return this.a.getEngineTime();
   }
}
