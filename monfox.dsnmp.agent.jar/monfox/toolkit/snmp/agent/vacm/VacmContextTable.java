package monfox.toolkit.snmp.agent.vacm;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;

public class VacmContextTable extends SnmpMibTable {
   private static final int LEAF = 1;

   public VacmContextTable() throws SnmpMibException, SnmpValueException {
      super(Vacm.vacmContextTable);
   }

   public SnmpMibTableRow add(String var1) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var2 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
      this.addRow(var2);
      return var2;
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

   public boolean hasContextName(String var1) {
      try {
         return this.get(var1) != null;
      } catch (SnmpException var3) {
         return false;
      }
   }
}
