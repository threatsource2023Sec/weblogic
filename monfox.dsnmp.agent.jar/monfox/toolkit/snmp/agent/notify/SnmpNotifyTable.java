package monfox.toolkit.snmp.agent.notify;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import monfox.toolkit.snmp.SnmpException;
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

public class SnmpNotifyTable extends SnmpMibTable {
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int a = 5;
   public static final int TRAP = 1;
   public static final int INFORM = 2;

   public SnmpNotifyTable(SnmpMetadata var1) throws SnmpMibException, SnmpValueException {
      super(new SnmpOid(var1, a("K6+d\u0001W,/r6l9$x*")));
      this.isSettableWhenActive(true);
      this.setInitialValue(2, new SnmpString());
      this.setInitialValue(3, new SnmpInt(1));
      this.setFactory(4, StorageType.getFactory(3));
   }

   public SnmpMibTableRow add(String var1, String var2) throws SnmpMibException, SnmpValueException {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         SnmpMibTableRow var3 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         var3.getLeaf(2).setValue((SnmpValue)(new SnmpString(var2)));
         var3.getLeaf(3).setValue((SnmpValue)(new SnmpInt(1)));
         this.addRow(var3);
         var3.getRowStatusLeaf().setActive();
         return var3;
      }
   }

   public SnmpMibTableRow addTrapGroup(String var1, String var2) throws SnmpMibException, SnmpValueException {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         SnmpMibTableRow var3 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         var3.getLeaf(2).setValue((SnmpValue)(new SnmpString(var2)));
         var3.getLeaf(3).setValue((SnmpValue)(new SnmpInt(1)));
         this.addRow(var3);
         var3.getRowStatusLeaf().setActive();
         return var3;
      }
   }

   public SnmpMibTableRow addInformGroup(String var1, String var2) throws SnmpMibException, SnmpValueException {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         SnmpMibTableRow var3 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         var3.getLeaf(2).setValue((SnmpValue)(new SnmpString(var2)));
         var3.getLeaf(3).setValue((SnmpValue)(new SnmpInt(2)));
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

   public String getTag(String var1) {
      try {
         SnmpMibTableRow var2 = this.get(var1);
         return var2 == null ? null : var2.getLeaf(2).getValue().getString();
      } catch (SnmpException var3) {
         return null;
      }
   }

   public int getType(String var1) {
      try {
         SnmpMibTableRow var2 = this.get(var1);
         return var2 == null ? 1 : var2.getLeaf(3).getValue().intValue();
      } catch (SnmpException var3) {
         return 1;
      }
   }

   public Set getGroupNames() {
      return this.a(-1);
   }

   public Set getTrapGroupNames() {
      return this.a(1);
   }

   public Set getinformGroupNames() {
      return this.a(2);
   }

   private Set a(int var1) {
      Set var2 = this.getRowSet();
      Iterator var3 = var2.iterator();
      HashSet var4 = new HashSet();

      while(var3.hasNext()) {
         SnmpMibTableRow var5 = (SnmpMibTableRow)var3.next();

         try {
            int var6 = var5.getLeaf(3).getValue().intValue();
            if (var1 < 0 || var1 == var6) {
               String var7 = var5.getLeaf(1).getValue().getString();
               var4.add(var7);
            }
         } catch (Exception var8) {
         }

         if (SnmpNotifier.v) {
            break;
         }
      }

      return var4;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 56;
               break;
            case 1:
               var10003 = 88;
               break;
            case 2:
               var10003 = 70;
               break;
            case 3:
               var10003 = 20;
               break;
            default:
               var10003 = 79;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
