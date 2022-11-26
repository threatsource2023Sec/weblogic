package monfox.toolkit.snmp.agent.notify;

import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.tc.StorageType;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class SnmpNotifyFilterTable extends SnmpMibTable {
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int a = 5;
   public static final int INCLUDED = 1;
   public static final int EXCLUDED = 2;

   public SnmpNotifyFilterTable(SnmpMetadata var1) throws SnmpMibException, SnmpValueException {
      super(new SnmpOid(var1, a("\u000e\rxw\u0002\u0012\u0017|a5;\nys)\u000f7te \u0018")));
      this.isSettableWhenActive(true);
      this.setInitialValue(2, new SnmpString());
      this.setInitialValue(3, new SnmpInt(1));
      this.setFactory(4, StorageType.getFactory(3));
   }

   public SnmpMibTableRow add(String var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      return this.add(var1, var2, (byte[])(new byte[0]), 1);
   }

   public SnmpMibTableRow add(String var1, SnmpOid var2, byte[] var3, int var4) throws SnmpMibException, SnmpValueException {
      SnmpString var5 = new SnmpString(var1);
      SnmpMibTableRow var6 = this.initRow(new SnmpValue[]{var5, var2});
      var6.getLeaf(2).setValue((SnmpValue)(new SnmpString(var3)));
      var6.getLeaf(3).setValue((SnmpValue)(new SnmpInt(var4)));
      this.addRow(var6);
      var6.getRowStatusLeaf().setActive();
      return var6;
   }

   public SnmpMibTableRow add(String var1, SnmpOid var2, String var3, int var4) throws SnmpMibException, SnmpValueException {
      byte[] var5 = monfox.toolkit.snmp.agent.notify.b.a(var3);
      return this.add(var1, var2, var5, var4);
   }

   public void remove(String var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      SnmpString var3 = new SnmpString(var1);
      SnmpMibTableRow var4 = this.removeRow(new SnmpValue[]{var3, var2});
      if (var4 != null) {
         var4.destroy();
      }

   }

   public SnmpMibTableRow get(String var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      SnmpString var3 = new SnmpString(var1);
      return this.getRow(new SnmpValue[]{var3, var2});
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 125;
               break;
            case 1:
               var10003 = 99;
               break;
            case 2:
               var10003 = 21;
               break;
            case 3:
               var10003 = 7;
               break;
            default:
               var10003 = 76;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
