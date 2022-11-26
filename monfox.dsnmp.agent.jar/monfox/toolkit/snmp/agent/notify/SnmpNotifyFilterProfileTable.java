package monfox.toolkit.snmp.agent.notify;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.tc.StorageType;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class SnmpNotifyFilterProfileTable extends SnmpMibTable {
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;

   public SnmpNotifyFilterProfileTable(SnmpMetadata var1) throws SnmpMibException, SnmpValueException {
      super(new SnmpOid(var1, a(" h\u0004&-<r\u00000\u001a\u0015o\u0005\"\u0006!V\u001b9\u0005:j\f\u0002\u00021j\f")));
      this.isSettableWhenActive(true);
      this.setFactory(2, StorageType.getFactory(3));
   }

   public SnmpMibTableRow add(String var1, String var2) throws SnmpMibException, SnmpValueException {
      if (var1 == null) {
         throw new NullPointerException();
      } else if (var2 == null) {
         throw new NullPointerException();
      } else {
         SnmpMibTableRow var3 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         var3.getLeaf(1).setValue((SnmpValue)(new SnmpString(var2)));
         this.addRow(var3);
         var3.getRowStatusLeaf().setActive();
         return var3;
      }
   }

   public void remove(String var1) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var2 = this.removeRow(new SnmpValue[]{new SnmpString(var1)});
      if (var2 != null) {
         var2.destroy();
      }

   }

   public SnmpMibTableRow get(String var1) throws SnmpMibException, SnmpValueException {
      return this.getRow(new SnmpValue[]{new SnmpString(var1)});
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 83;
               break;
            case 1:
               var10003 = 6;
               break;
            case 2:
               var10003 = 105;
               break;
            case 3:
               var10003 = 86;
               break;
            default:
               var10003 = 99;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
