package monfox.toolkit.snmp.agent;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.util.Lock;

public abstract class SnmpMibNode {
   public static final int LEAF = 1;
   public static final int RANGE = 2;
   public static final int RANGE_END = 22;
   public static final int SUB_TREE = 3;
   protected SnmpOid _oid;
   private Lock a;
   protected SnmpMib _mib;
   public static boolean b;

   public SnmpMibNode() {
      this.a = null;
   }

   public SnmpMibNode(SnmpMetadata var1, String var2) throws SnmpValueException {
      this(new SnmpOid(var1, var2));
   }

   public SnmpMibNode(String var1) throws SnmpValueException {
      this(new SnmpOid(var1));
   }

   public SnmpMibNode(SnmpOid var1) {
      this.a = null;
      this._oid = var1;
   }

   public SnmpOid getOid() {
      return this._oid;
   }

   public SnmpOidInfo getOidInfo() {
      return this._oid.getOidInfo();
   }

   public abstract SnmpOid getMaxOid();

   public SnmpMib getMib() {
      return this._mib;
   }

   protected void setMib(SnmpMib var1) {
      this._mib = var1;
   }

   public int getNodeType() {
      SnmpOid var1 = this.getMaxOid();
      if (var1 == null) {
         return 3;
      } else {
         return this.getOid().equals(var1) ? 1 : 2;
      }
   }

   public abstract void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3);

   public abstract void getNextRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3);

   public abstract int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3);

   public abstract boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3);

   public abstract boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3);

   public abstract void cleanupSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3);

   public abstract boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpAccessPolicy var3);

   public boolean isAvailableForContextName(String var1) {
      return true;
   }

   public void prepareForAccess() {
   }

   public Lock getLock() {
      synchronized(this) {
         if (this.a == null) {
            this.a = new Lock();
         }
      }

      return this.a;
   }

   public String toString() {
      boolean var2 = b;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("!\u0012\u0012E~"));
      var1.append('{');
      var1.append(a(" \u0014\u0012\u001d"));
      var1.append(this.getOid());
      var1.append(',');
      var1.append(a("\"\u001c\u000eo*+@"));
      var1.append(this.getMaxOid());
      var1.append(',');
      var1.append(a("!\u0012\u0012E\u00176\r\u0013\u001d"));
      switch (this.getNodeType()) {
         case 1:
            var1.append(a("\u000387f"));
            if (!var2) {
               break;
            }
         case 2:
            var1.append(a("\u001d<8g\u0006"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a("\u001c(4\u007f\u0017\u001d83"));
            if (!var2) {
               break;
            }
         default:
            var1.append(a("pBI"));
      }

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
               var10003 = 79;
               break;
            case 1:
               var10003 = 125;
               break;
            case 2:
               var10003 = 118;
               break;
            case 3:
               var10003 = 32;
               break;
            default:
               var10003 = 67;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
