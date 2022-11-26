package monfox.toolkit.snmp.agent.vacm;

import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.tc.StorageType;

public class VacmSecurityToGroupTable extends SnmpMibTable {
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int a = 5;

   public VacmSecurityToGroupTable() throws SnmpMibException, SnmpValueException {
      super(Vacm.vacmSecurityToGroupTable);
      this.isSettableWhenActive(true);
      this.setFactory(4, StorageType.getFactory(3));
   }

   public SnmpMibTableRow add(int var1, String var2, String var3) throws SnmpMibException, SnmpValueException {
      SnmpInt var4 = new SnmpInt(var1);
      SnmpString var5 = new SnmpString(var2);
      SnmpMibTableRow var6 = this.initRow(new SnmpValue[]{var4, var5});
      var6.getLeaf(3).setValue((SnmpValue)(new SnmpString(var3)));
      this.addRow(var6);
      var6.getRowStatusLeaf().setActive();
      return var6;
   }

   public void remove(int var1, String var2) throws SnmpMibException, SnmpValueException {
      SnmpInt var3 = new SnmpInt(var1);
      SnmpString var4 = new SnmpString(var2);
      SnmpMibTableRow var5 = this.removeRow(new SnmpValue[]{var3, var4});
      if (var5 != null) {
         var5.destroy();
      }

   }

   public SnmpMibTableRow get(int var1, String var2) throws SnmpMibException, SnmpValueException {
      SnmpInt var3 = new SnmpInt(var1);
      SnmpString var4 = new SnmpString(var2);
      return this.getRow(new SnmpValue[]{var3, var4});
   }

   public String getGroupName(int var1, String var2) {
      SnmpMibTableRow var3;
      try {
         SnmpInt var4 = new SnmpInt(var1);
         SnmpString var5 = new SnmpString(var2);
         var3 = this.getRow(new SnmpValue[]{var4, var5});
      } catch (SnmpMibException var6) {
         return null;
      } catch (SnmpValueException var7) {
         return null;
      }

      if (var3 == null) {
         return null;
      } else {
         return !var3.getRowStatusLeaf().isActive() ? null : var3.getLeaf(3).getValue().getString();
      }
   }
}
