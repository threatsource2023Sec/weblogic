package monfox.toolkit.snmp.agent.vacm;

import java.util.Iterator;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.tc.StorageType;

public class VacmViewTreeFamilyTable extends SnmpMibTable {
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int a = 5;
   private static final int b = 6;
   public static final int INCLUDE = 1;
   public static final int EXCLUDE = 2;

   public VacmViewTreeFamilyTable() throws SnmpMibException, SnmpValueException {
      super(Vacm.vacmViewTreeFamilyTable);
      this.isSettableWhenActive(true);
      this.setInitialValue(3, new SnmpString());
      this.setInitialValue(4, new SnmpInt(1));
      this.setFactory(5, StorageType.getFactory(3));
   }

   public SnmpMibTableRow add(String var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      return this.add(var1, var2, new byte[0], 1);
   }

   public SnmpMibTableRow add(String var1, SnmpOid var2, byte[] var3, int var4) throws SnmpMibException, SnmpValueException {
      SnmpString var5 = new SnmpString(var1);
      SnmpMibTableRow var6 = this.initRow(new SnmpValue[]{var5, var2});
      var6.getLeaf(3).setValue((SnmpValue)(new SnmpString(var3)));
      var6.getLeaf(4).setValue((SnmpValue)(new SnmpInt(var4)));
      this.addRow(var6);
      var6.getRowStatusLeaf().setActive();
      return var6;
   }

   public void remove(String var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      SnmpString var3 = new SnmpString(var1);
      SnmpMibTableRow var4 = this.removeRow(new SnmpValue[]{var3, var2});
      if (var4 != null) {
         var4.destroy();
      }

   }

   public Vector getView(String var1) {
      Vector var2 = new Vector();
      if (var1 != null) {
         Iterator var3 = this.getRows();

         while(var3.hasNext()) {
            SnmpMibTableRow var4 = (SnmpMibTableRow)var3.next();
            String var5 = var4.getLeaf(1).getValue().getString();
            if (var1.equals(var5)) {
               var2.add(var4);
            }

            if (Vacm.j) {
               break;
            }
         }
      }

      return var2;
   }
}
