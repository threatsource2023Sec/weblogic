package monfox.toolkit.snmp.agent.vacm;

import java.util.Iterator;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.tc.StorageType;
import monfox.toolkit.snmp.engine.SnmpEngineID;

public class SnmpCommunityTable extends SnmpMibTable {
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int a = 5;
   private static final int b = 6;
   private static final int c = 7;
   private static final int d = 8;

   public SnmpCommunityTable(SnmpEngineID var1) throws SnmpMibException, SnmpValueException {
      super(Vacm.snmpCommunityTable);
      this.isSettableWhenActive(true);
      this.setInitialValue(4, new SnmpString(var1.getValue()));
      this.setInitialValue(5, new SnmpString());
      this.setInitialValue(6, new SnmpString());
      this.setFactory(7, StorageType.getFactory(3));
   }

   public void add(String var1, String var2, String var3, String var4) throws SnmpMibException, SnmpValueException {
      this.add(var1, var2, var3, var4, "");
   }

   public void add(String var1, String var2, String var3) throws SnmpMibException, SnmpValueException {
      this.add(var1, var2, var3, "", "");
   }

   public void add(String var1, String var2, String var3, String var4, String var5) throws SnmpMibException, SnmpValueException {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         SnmpMibTableRow var6 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         var6.getLeaf(2).setValue((SnmpValue)(new SnmpString(var2)));
         var6.getLeaf(3).setValue((SnmpValue)(new SnmpString(var3)));
         var6.getLeaf(5).setValue((SnmpValue)(new SnmpString(var4)));
         var6.getLeaf(6).setValue((SnmpValue)(new SnmpString(var5)));
         this.addRow(var6);
         var6.getRowStatusLeaf().setActive();
      }
   }

   public void remove(String var1) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var2 = this.removeRow(new SnmpValue[]{new SnmpString(var1)});
      if (var2 != null) {
         var2.destroy();
      }

   }

   public String getSecurityName(String var1) {
      Iterator var2 = this.getRows();

      while(var2.hasNext()) {
         SnmpMibTableRow var3 = (SnmpMibTableRow)var2.next();
         if (var3.getRowStatusLeaf().isActive()) {
            String var4 = var3.getLeaf(2).getValue().getString();
            if (var4.equals(var1)) {
               return var3.getLeaf(3).getValue().getString();
            }

            if (Vacm.j) {
               break;
            }
         }
      }

      return null;
   }

   public String getContextName(String var1) {
      Iterator var2 = this.getRows();

      while(var2.hasNext()) {
         SnmpMibTableRow var3 = (SnmpMibTableRow)var2.next();
         if (var3.getRowStatusLeaf().isActive()) {
            String var4 = var3.getLeaf(2).getValue().getString();
            if (var4.equals(var1)) {
               return var3.getLeaf(5).getValue().getString();
            }

            if (Vacm.j) {
               break;
            }
         }
      }

      return null;
   }

   public SnmpEngineID getContextEngineID(String var1) {
      Iterator var2 = this.getRows();

      while(var2.hasNext()) {
         SnmpMibTableRow var3 = (SnmpMibTableRow)var2.next();
         if (var3.getRowStatusLeaf().isActive()) {
            String var4 = var3.getLeaf(2).getValue().getString();
            if (var4.equals(var1)) {
               SnmpString var5 = (SnmpString)var3.getLeaf(4).getValue();
               if (var5 == null) {
                  return null;
               }

               SnmpEngineID var6 = new SnmpEngineID(var5.toByteArray());
               return var6;
            }

            if (Vacm.j) {
               break;
            }
         }
      }

      return null;
   }
}
