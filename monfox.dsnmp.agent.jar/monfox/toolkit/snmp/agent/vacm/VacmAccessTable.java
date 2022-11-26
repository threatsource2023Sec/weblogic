package monfox.toolkit.snmp.agent.vacm;

import java.util.Iterator;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.tc.StorageType;

public class VacmAccessTable extends SnmpMibTable {
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int a = 5;
   private static final int b = 6;
   private static final int c = 7;
   private static final int d = 8;
   private static final int e = 9;
   private Logger l = Logger.getInstance(a("8(@\u0012n\r*F\f\\:(A\u0013J"));
   public static final int EXACT = 1;
   public static final int PREFIX = 2;

   public VacmAccessTable() throws SnmpMibException, SnmpValueException {
      super(Vacm.vacmAccessTable);
      this.isSettableWhenActive(true);
      this.setInitialValue(4, new SnmpInt(1));
      this.setInitialValue(5, new SnmpString());
      this.setInitialValue(6, new SnmpString());
      this.setInitialValue(7, new SnmpString());
      this.setFactory(8, StorageType.getFactory(3));
   }

   public void add(String var1, String var2, int var3, int var4, String var5, String var6, String var7) throws SnmpMibException, SnmpValueException {
      this.add(var1, var2, var3, var4, 1, var5, var6, var7);
   }

   public void add(String var1, String var2, int var3, int var4, int var5, String var6, String var7, String var8) throws SnmpMibException, SnmpValueException {
      boolean var15 = Vacm.j;
      int var9 = a(var4);
      SnmpString var10 = new SnmpString(var1);
      SnmpString var11 = new SnmpString(var2);
      SnmpInt var12 = new SnmpInt(var3);
      SnmpInt var13 = new SnmpInt(var9);
      SnmpMibTableRow var14 = this.initRow(new SnmpValue[]{var10, var11, var12, var13});
      var14.getLeaf(4).setValue((SnmpValue)(new SnmpInt(var5)));
      var14.getLeaf(5).setValue((SnmpValue)(new SnmpString(var6)));
      var14.getLeaf(6).setValue((SnmpValue)(new SnmpString(var7)));
      var14.getLeaf(7).setValue((SnmpValue)(new SnmpString(var8)));
      this.addRow(var14);
      var14.getRowStatusLeaf().setActive();
      if (var15) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public void remove(String var1, String var2, int var3, int var4) throws SnmpMibException, SnmpValueException {
      SnmpString var5 = new SnmpString(var1);
      SnmpString var6 = new SnmpString(var2);
      SnmpInt var7 = new SnmpInt(var3);
      int var8 = a(var4);
      SnmpInt var9 = new SnmpInt(var8);
      SnmpMibTableRow var10 = this.removeRow(new SnmpValue[]{var5, var6, var7, var9});
      if (var10 != null) {
         var10.destroy();
      }

   }

   public SnmpMibTableRow get(String var1, String var2, int var3, int var4) throws SnmpMibException, SnmpValueException {
      SnmpString var5 = new SnmpString(var1);
      SnmpString var6 = new SnmpString(var2);
      SnmpInt var7 = new SnmpInt(var3);
      int var8 = a(var4);
      SnmpInt var9 = new SnmpInt(var8);
      return this.getRow(new SnmpValue[]{var5, var6, var7, var9});
   }

   public String getViewName(String var1, String var2, int var3, int var4, int var5) {
      boolean var9 = Vacm.j;
      Vector var6 = new Vector();
      Iterator var7 = this.getRows();

      int var10000;
      while(true) {
         if (var7.hasNext()) {
            SnmpMibTableRow var8 = (SnmpMibTableRow)var7.next();
            var10000 = var8.getRowStatusLeaf().isActive();
            if (var9 || var9 || var9 || var9 || var9) {
               break;
            }

            if (var10000 == 0 && !var9 || !this.a(var8, var1) && !var9 || !this.b(var8, var2) && !var9 || !this.a(var8, var4) && !var9) {
               continue;
            }

            var6.add(var8);
            if (!var9) {
               continue;
            }
         }

         var10000 = var6.size();
         break;
      }

      if (var10000 == 0) {
         return null;
      } else {
         if (var6.size() == 1) {
            this.b((SnmpMibTableRow)var6.elementAt(0), var5);
         }

         return this.a(var6, var2, var3, var4, var5);
      }
   }

   private boolean a(SnmpMibTableRow var1, String var2) {
      SnmpString var3 = new SnmpString();

      try {
         var3.fromIndexOid(var1.getIndex(), 0);
      } catch (SnmpValueException var5) {
         return false;
      }

      String var4 = var3.getString();
      return var4.equals(var2);
   }

   private boolean b(SnmpMibTableRow var1, String var2) {
      String var3 = var1.getLeaf(1).getValue().getString();
      int var4 = var1.getLeaf(4).getValue().intValue();
      if (var3 != null && var2 != null) {
         if (var4 == 1) {
            return var3.equals(var2);
         } else if (var3.length() > var2.length()) {
            return false;
         } else if (var3.length() == var2.length()) {
            return var3.equals(var2);
         } else {
            String var5 = var2.substring(0, var3.length());
            return var3.equals(var5);
         }
      } else {
         return false;
      }
   }

   private boolean a(SnmpMibTableRow var1, int var2) {
      int var3 = var1.getLeaf(3).getValue().intValue();
      int var4 = a(var2);
      if (this.l.isDebugEnabled()) {
         this.l.debug(a("\r!F\u001cD=,@\n]\u0007=Z3J\u0018,OW") + var1 + "," + var2 + a("G\u0012") + var3 + a("Rt") + var4 + "=" + (var3 <= var4) + "]");
      }

      return var3 <= var4;
   }

   private String b(SnmpMibTableRow var1, int var2) {
      switch (var2) {
         case 160:
         case 161:
         case 165:
            return var1.getLeaf(5).getValue().getString();
         case 162:
         default:
            return null;
         case 163:
            return var1.getLeaf(6).getValue().getString();
         case 164:
         case 166:
         case 167:
            return var1.getLeaf(7).getValue().getString();
      }
   }

   private String a(Vector var1, String var2, int var3, int var4, int var5) {
      boolean var13 = Vacm.j;
      boolean var6 = false;
      Iterator var7 = var1.iterator();

      SnmpMibTableRow var8;
      SnmpMibTableRow var9;
      Iterator var14;
      int var10000;
      int var10001;
      label146:
      while(true) {
         if (var7.hasNext()) {
            label151: {
               var8 = (SnmpMibTableRow)var7.next();
               var10000 = var8.getLeaf(2).getValue().intValue();
               var10001 = var3;
               if (var13) {
                  break;
               }

               if (var10000 == var3) {
                  var6 = true;
                  if (!var13) {
                     break label151;
                  }
               }

               if (!var13) {
                  continue;
               }
            }
         }

         if (var6) {
            var14 = var1.iterator();

            while(var14.hasNext()) {
               var9 = (SnmpMibTableRow)var14.next();
               var10000 = var9.getLeaf(2).getValue().intValue();
               var10001 = var3;
               if (var13) {
                  break label146;
               }

               if (var10000 != var3) {
                  var14.remove();
               }

               if (var13) {
                  break;
               }
            }
         }

         var10000 = var1.size();
         var10001 = 1;
         break;
      }

      if (var10000 == var10001) {
         this.b((SnmpMibTableRow)var1.elementAt(0), var5);
      }

      var6 = false;
      var7 = var1.iterator();

      boolean var17;
      while(true) {
         if (var7.hasNext()) {
            label153: {
               var8 = (SnmpMibTableRow)var7.next();
               var17 = var8.getLeaf(1).getValue().getString().equals(var2);
               if (var13) {
                  break;
               }

               if (var17) {
                  var6 = true;
                  if (!var13) {
                     break label153;
                  }
               }

               if (!var13) {
                  continue;
               }
            }
         }

         var17 = var6;
         break;
      }

      label109: {
         if (var17) {
            var14 = var1.iterator();

            while(var14.hasNext()) {
               var9 = (SnmpMibTableRow)var14.next();
               var10000 = var9.getLeaf(1).getValue().getString().equals(var2);
               if (var13) {
                  break label109;
               }

               if (var10000 == 0) {
                  var14.remove();
               }

               if (var13) {
                  break;
               }
            }
         }

         var10000 = var1.size();
      }

      if (var10000 == 1) {
         this.b((SnmpMibTableRow)var1.elementAt(0), var5);
      }

      int var15 = 0;
      var7 = var1.iterator();

      label95:
      while(true) {
         String var10;
         if (var7.hasNext()) {
            var9 = (SnmpMibTableRow)var7.next();
            var10 = var9.getLeaf(1).getValue().getString();
            var10000 = var10.length();
            var10001 = var15;
            if (var13) {
               break;
            }

            if (var10000 > var15) {
               var15 = var10.length();
            }

            if (!var13) {
               continue;
            }
         }

         var7 = var1.iterator();

         while(var7.hasNext()) {
            var9 = (SnmpMibTableRow)var7.next();
            var10 = var9.getLeaf(1).getValue().getString();
            var10000 = var10.length();
            var10001 = var15;
            if (var13) {
               break label95;
            }

            if (var10000 < var15) {
               var7.remove();
            }

            if (var13) {
               break;
            }
         }

         var10000 = var1.size();
         var10001 = 1;
         break;
      }

      if (var10000 == var10001) {
         this.b((SnmpMibTableRow)var1.elementAt(0), var5);
      }

      var7 = var1.iterator();
      var9 = (SnmpMibTableRow)var7.next();

      while(var7.hasNext()) {
         SnmpMibTableRow var16 = (SnmpMibTableRow)var7.next();
         int var11 = var9.getLeaf(3).getValue().intValue();
         int var12 = var16.getLeaf(3).getValue().intValue();
         if (var12 > var11) {
            var9 = var16;
         }

         if (var13) {
            break;
         }
      }

      return this.b(var9, var5);
   }

   private static int a(int var0) {
      boolean var2 = Vacm.j;
      boolean var1 = true;
      int var3;
      switch (var0 & 3) {
         case 0:
            var3 = 1;
            if (!var2) {
               break;
            }
         case 1:
            var3 = 2;
            if (!var2) {
               break;
            }
         case 3:
            var3 = 3;
            if (!var2) {
               break;
            }
         case 2:
         default:
            var3 = var0;
      }

      return var3;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 110;
               break;
            case 1:
               var10003 = 73;
               break;
            case 2:
               var10003 = 35;
               break;
            case 3:
               var10003 = 127;
               break;
            default:
               var10003 = 47;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
