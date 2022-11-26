package monfox.toolkit.snmp.ext;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;

public class SnmpRow extends SnmpObjectSet {
   private SnmpObjectInfo[] a = null;
   private int[] c = null;
   private boolean b = true;
   private static final String p = "$Id: SnmpRow.java,v 1.6 2007/03/30 18:41:40 sking Exp $";

   SnmpRow(SnmpMetadata var1, SnmpObjectInfo[] var2, int[] var3, SnmpOid var4) {
      super(var1);
      this.a = var2;
      this.c = var3;
      super.setRawIndex(var4);
   }

   public boolean isEnabled() {
      return this.b;
   }

   public void isEnabled(boolean var1) {
      this.b = var1;
   }

   protected void updateInfoSet(SnmpObjectInfo[] var1, int[] var2) {
      this.a = var1;
      this.c = var2;
   }

   protected int[] varMask() {
      return this.c;
   }

   protected SnmpObjectInfo[] infoSet() {
      return this.a;
   }
}
