package monfox.toolkit.snmp.agent.proxy;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.tc.StorageType;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class SnmpProxySubtreeTable extends SnmpMibTable {
   private static final int LEAF = 10;
   private static final int RANGE = 1;
   private static final int RANGE_END = 2;
   private static final int SUB_TREE = 3;
   private static final int a = 4;
   private static final int b = 5;
   private static final int c = 6;
   private SnmpProxyForwarder d;
   private SnmpAgent e;
   private SnmpMetadata f;
   private a g;
   private Logger l = Logger.getInstance(a("\\(\u0018\u0003z"), a("Y<\u0013\u0000~5+\u0004\u0001rA"), a("K\u0015;>zj\u0014.7~y\u0019:+"));

   SnmpProxySubtreeTable(SnmpAgent var1, SnmpMetadata var2, SnmpProxyForwarder var3) throws SnmpMibException, SnmpValueException {
      super(new SnmpOid(var2, a("|\b8#ZK\u000e4:X}\u001e\u0006<E`\u0002\u0002/Ht\u001e")));
      this.isSettableWhenActive(false);
      this.e = var1;
      this.d = var3;
      this.f = var2;
      this.g = new a(this.e, this, var3);
      this.e.getResponder().addPreProcessor(this.g);
      this.setFactory(5, StorageType.getFactory(3));
   }

   public Row add(String var1, String var2, String var3) throws SnmpMibException, SnmpValueException {
      return this.add(var1, (SnmpOid)(new SnmpOid(this.f, var2)), (String)null, var3);
   }

   public Row add(String var1, String var2, String var3, String var4) throws SnmpMibException, SnmpValueException {
      return this.add(var1, new SnmpOid(this.f, var2), var3, var4);
   }

   public Row add(String var1, SnmpOid var2, String var3) throws SnmpMibException, SnmpValueException {
      return this.add(var1, (SnmpOid)var2, (String)null, var3);
   }

   public Row add(String var1, SnmpOid var2, String var3, String var4) throws SnmpMibException, SnmpValueException {
      if (var1 == null) {
         throw new NullPointerException(a("u\u0012%=Cv\u001cv Ku\u001e"));
      } else {
         SnmpMibTableRow var5 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         if (var3 != null) {
            var5.getLeaf(2).setValue((SnmpValue)(new SnmpString(var3)));
         }

         var5.getLeaf(3).setValue((SnmpValue)var2);
         var5.getLeaf(4).setValue((SnmpValue)(new SnmpString(var4)));
         this.addRow(var5);
         var5.getRowStatusLeaf().setActive();
         return this.b(var5);
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

   public Row getProxyRowByName(String var1) throws SnmpMibException, SnmpValueException {
      return this.b(this.get(var1));
   }

   protected void fireRowAddedEvent(SnmpMibTableRow var1) {
      super.fireRowAddedEvent(var1);
      if (this.e.getRequestExecManager() == null && this.e.getNumThreads() == 0) {
         this.e.setNumThreads(10);
      }

      Row var2 = this.b(var1);

      try {
         b var3 = new b(var2.getSubtreeOid(), var2.getTargetOut(), this.e.getSession(), false, this.e, this.d, var2.getName());
         String var4 = var2.getContextName();
         SnmpMib var5 = null;
         if (var4 != null) {
            var5 = this.e.getContextMib(var4);
         }

         if (var5 == null) {
            var5 = this.e.getMib();
         }

         var5.add(var3);
      } catch (Exception var6) {
         this.l.error(a("}\t$!X8\u001a2*Cv\u001cv\u001d_z\u000f$+OV\u00142+\u00108") + var2.getSubtreeOid(), var6);
      }

   }

   private Row b(SnmpMibTableRow var1) {
      if (var1 == null) {
         return null;
      } else if (var1.getUserObject() != null && var1.getUserObject() instanceof Row) {
         return (Row)var1.getUserObject();
      } else {
         Row var2 = new Row(var1);
         if (var1.getUserObject() == null) {
            var1.setUserObject(var1);
         }

         return var2;
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 24;
               break;
            case 1:
               var10003 = 123;
               break;
            case 2:
               var10003 = 86;
               break;
            case 3:
               var10003 = 78;
               break;
            default:
               var10003 = 42;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public class Row {
      SnmpMibTableRow a;

      Row(SnmpMibTableRow var2) {
         this.a = var2;
      }

      public String getName() {
         try {
            return this.a.getLeaf(1).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public String getContextName() {
         try {
            return this.a.getLeaf(2).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public SnmpOid getSubtreeOid() {
         try {
            return (SnmpOid)this.a.getLeaf(3).getValue();
         } catch (Exception var2) {
            return null;
         }
      }

      public String getTargetOut() {
         try {
            return this.a.getLeaf(4).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public String toString() {
         return this.a.toString();
      }
   }
}
