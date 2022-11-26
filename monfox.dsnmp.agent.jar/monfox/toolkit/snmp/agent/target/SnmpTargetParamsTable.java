package monfox.toolkit.snmp.agent.target;

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
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class SnmpTargetParamsTable extends SnmpMibTable {
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int a = 5;
   private static final int b = 6;
   private static final int c = 7;

   public SnmpTargetParamsTable(SnmpMetadata var1) throws SnmpMibException, SnmpValueException {
      super(new SnmpOid(var1, a("\u0004e_{N\u0016yUnn'j@jw\u0004_Siv\u0012")));
      this.isSettableWhenActive(false);
      this.setFactory(6, StorageType.getFactory(3));
   }

   public SnmpMibTableRow addV1(String var1, String var2) throws SnmpMibException, SnmpValueException {
      return this.add(var1, 0, 1, var2, 0);
   }

   public SnmpMibTableRow addV2(String var1, String var2) throws SnmpMibException, SnmpValueException {
      return this.add(var1, 1, 2, var2, 0);
   }

   public SnmpMibTableRow addV3(String var1, String var2, int var3) throws SnmpMibException, SnmpValueException {
      return this.add(var1, 3, 3, var2, var3);
   }

   public SnmpMibTableRow add(String var1, int var2, int var3, String var4, int var5) throws SnmpMibException, SnmpValueException {
      int var9 = SnmpTarget.f;
      int var6;
      switch (var5 & 3) {
         case 0:
            var6 = 1;
            if (var9 == 0) {
               break;
            }
         case 1:
            var6 = 2;
            if (var9 == 0) {
               break;
            }
         case 3:
            var6 = 3;
            if (var9 == 0) {
               break;
            }
         case 2:
         default:
            var6 = var5;
      }

      int var7;
      switch (var2) {
         case 0:
            var7 = 1;
            if (var9 == 0) {
               break;
            }
         case 1:
            var7 = 2;
            if (var9 == 0) {
               break;
            }
         case 3:
            var7 = 3;
            if (var9 == 0) {
               break;
            }
         case 2:
         default:
            var7 = var2;
      }

      if (var1 == null) {
         throw new NullPointerException();
      } else {
         SnmpMibTableRow var8 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         var8.getLeaf(2).setValue((SnmpValue)(new SnmpInt(var7)));
         var8.getLeaf(3).setValue((SnmpValue)(new SnmpInt(var3)));
         var8.getLeaf(4).setValue((SnmpValue)(new SnmpString(var4)));
         var8.getLeaf(5).setValue((SnmpValue)(new SnmpInt(var6)));
         this.addRow(var8);
         var8.getRowStatusLeaf().setActive();
         return var8;
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

   public Row getRowByName(String var1) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var2 = this.get(var1);
      return var2 == null ? null : new Row(var2);
   }

   public SnmpMessageProfile getMessageProfile(String var1) {
      int var8 = SnmpTarget.f;

      SnmpMibTableRow var2;
      try {
         var2 = this.get(var1);
      } catch (SnmpException var9) {
         return null;
      }

      if (var2 == null) {
         return null;
      } else if (!var2.isActive()) {
         return null;
      } else {
         int var3 = var2.getLeaf(2).getValue().intValue();
         String var4 = var2.getLeaf(4).getValue().getString();
         int var5 = var2.getLeaf(5).getValue().intValue();
         SnmpMessageProfile var6;
         switch (var3) {
            case 1:
               var6 = new SnmpMessageProfile(0, 1, 0, var4);
               if (var8 == 0) {
                  break;
               }
            case 2:
               var6 = new SnmpMessageProfile(1, 2, 0, var4);
               if (var8 == 0) {
                  break;
               }
            case 3:
               byte var7;
               label55: {
                  if (var5 == 1) {
                     var7 = 0;
                     if (var8 == 0) {
                        break label55;
                     }
                  }

                  if (var5 == 2) {
                     var7 = 1;
                     if (var8 == 0) {
                        break label55;
                     }
                  }

                  if (var5 != 3) {
                     return null;
                  }

                  var7 = 3;
                  if (var8 != 0) {
                     return null;
                  }
               }

               var6 = new SnmpMessageProfile(3, 3, var7, var4);
               if (var8 == 0) {
                  break;
               }
            default:
               var6 = null;
         }

         return var6;
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
               var10003 = 119;
               break;
            case 1:
               var10003 = 11;
               break;
            case 2:
               var10003 = 50;
               break;
            case 3:
               var10003 = 11;
               break;
            default:
               var10003 = 26;
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

      public int getMPModel() {
         try {
            return this.a.getLeaf(2).getValue().intValue();
         } catch (Exception var2) {
            return -1;
         }
      }

      public int getSecurityModel() {
         try {
            return this.a.getLeaf(3).getValue().intValue();
         } catch (Exception var2) {
            return -1;
         }
      }

      public int getSecurityLevel() {
         try {
            return this.a.getLeaf(5).getValue().intValue();
         } catch (Exception var2) {
            return -1;
         }
      }

      public String getSecurityName() {
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
