package monfox.toolkit.snmp.agent;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;

public class SnmpMibTableRow {
   private SnmpMibTable a;
   private SnmpMibLeaf[] b;
   private SnmpOid c;
   private SnmpOid d;
   private Object e = null;

   protected SnmpMibTableRow(SnmpMibTable var1, SnmpOid var2, SnmpMibLeaf[] var3) {
      this.a = var1;
      this.d = var2;
      this.b = var3;
      int var4 = 0;

      while(var4 < var3.length) {
         if (this.b[var4] != null) {
            this.b[var4].setRow(this);
         }

         ++var4;
         if (SnmpMibNode.b) {
            break;
         }
      }

      SnmpOid var5 = var1.getOid();
      this.c = (SnmpOid)var5.clone();
      this.c.append(1L);
      this.c.setMetadata(var5.getMetadata());
   }

   public SnmpOid getClassOid() {
      return this.c;
   }

   public SnmpOid getIndex() {
      return this.d;
   }

   public SnmpValue[] getIndexValues() throws SnmpValueException {
      return this.a.a(this.d);
   }

   public SnmpMibTable getTable() {
      return this.a;
   }

   public boolean hasRowStatus() {
      return this.a.hasRowStatus();
   }

   public int getRowStatusColumn() {
      return this.a.getRowStatusColumn();
   }

   public SnmpRowStatus getRowStatusLeaf() {
      return this.hasRowStatus() ? (SnmpRowStatus)this.getLeaf(this.getRowStatusColumn()) : null;
   }

   public int getRowStatus() {
      return this.hasRowStatus() ? this.getRowStatusLeaf().intValue() : 0;
   }

   public boolean isActive() {
      if (!this.hasRowStatus()) {
         return false;
      } else {
         return this.getRowStatus() == 1;
      }
   }

   public int size() {
      return this.a.getNumOfColumns();
   }

   public Object getUserObject() {
      return this.e;
   }

   public void setUserObject(Object var1) {
      this.e = var1;
   }

   public boolean hasUserObject() {
      return this.e != null;
   }

   public boolean isInTable() {
      return this.a.hasRow(this.d);
   }

   /** @deprecated */
   public void setLeaf(int var1, SnmpMibLeaf var2) {
      this.b[var1 - 1] = var2;
   }

   /** @deprecated */
   public SnmpMibLeaf setLeafValue(int var1, SnmpValue var2) throws SnmpMibException, SnmpValueException {
      SnmpMibLeaf var3 = this.getLeaf(var1);
      if (var3 == null) {
         throw new SnmpMibException(a("\u0019G\u000fx\u000b\u0016N\u000fr\u0001\u0002FK4\u000f\u0003\bL{\u0002\u0002EA4") + var1);
      } else {
         var3.setValue(var2);
         return var3;
      }
   }

   public SnmpMibLeaf setLeafValue(int var1, String var2) throws SnmpMibException, SnmpValueException {
      SnmpMibLeaf var3 = this.getLeaf(var1);
      if (var3 == null) {
         throw new SnmpMibException(a("\u0019G\u000fx\u000b\u0016N\u000fr\u0001\u0002FK4\u000f\u0003\bL{\u0002\u0002EA4") + var1);
      } else {
         var3.setValue(var2);
         return var3;
      }
   }

   public void setLeafValues(String[] var1) throws SnmpMibException, SnmpValueException {
      boolean var5 = SnmpMibNode.b;
      int var2 = 1;
      int var3 = 0;

      while(var3 < var1.length) {
         label51: {
            SnmpMibLeaf var4 = this.getLeaf(var2);
            if (var4 != null) {
               if (var1[var3] == null) {
                  break label51;
               }

               this.setLeafValue(var2, var1[var3]);
               if (!var5) {
                  break label51;
               }
            }

            if (var1[var3] != null) {
               SnmpMibLeaf var10000;
               while(true) {
                  if (var2 <= this.a.getMaxColumnNum()) {
                     var4 = this.getLeaf(var2);
                     var10000 = var4;
                     if (var5) {
                        break;
                     }

                     if (var4 == null) {
                        ++var2;
                        if (!var5) {
                           continue;
                        }
                     }
                  }

                  var10000 = var4;
                  break;
               }

               if (var10000 == null) {
                  throw new SnmpMibException(a("\u0001ICa\u000bXK@x\u001b\u001aF\u000fy\u0007\u0004EN`\r\u001f"));
               }

               this.setLeafValue(var2, var1[var3]);
            }
         }

         ++var2;
         ++var3;
         if (var5) {
            break;
         }
      }

   }

   public SnmpMibLeaf setLeafValue(String var1, String var2) throws SnmpMibException, SnmpValueException {
      SnmpMibLeaf var3 = this.getLeaf(var1);
      if (var3 == null) {
         throw new SnmpMibException(a("\u0019G\u000fx\u000b\u0016N\u000fr\u0001\u0002FK4\u000f\u0003\bL{\u0002\u0002EA4") + var1);
      } else {
         var3.setValue(var2);
         return var3;
      }
   }

   public SnmpMibLeaf setLeafValue(String var1, SnmpValue var2) throws SnmpMibException, SnmpValueException {
      SnmpMibLeaf var3 = this.getLeaf(var1);
      if (var3 == null) {
         throw new SnmpMibException(a("\u0019G\u000fx\u000b\u0016N\u000fr\u0001\u0002FK4\u000f\u0003\bL{\u0002\u0002EA4") + var1);
      } else {
         var3.setValue(var2);
         return var3;
      }
   }

   public SnmpMibLeaf setLeafValue(SnmpOid var1, String var2) throws SnmpMibException, SnmpValueException {
      SnmpMibLeaf var3 = this.getLeaf(var1);
      if (var3 == null) {
         throw new SnmpMibException(a("\u0019G\u000fx\u000b\u0016N\u000fr\u0001\u0002FK4\u000f\u0003\bL{\u0002\u0002EA4") + var1);
      } else {
         var3.setValue(var2);
         return var3;
      }
   }

   public SnmpMibLeaf setLeafValue(SnmpOid var1, SnmpValue var2) throws SnmpMibException, SnmpValueException {
      SnmpMibLeaf var3 = this.getLeaf(var1);
      if (var3 == null) {
         throw new SnmpMibException(a("\u0019G\u000fx\u000b\u0016N\u000fr\u0001\u0002FK4\u000f\u0003\bL{\u0002\u0002EA4") + var1);
      } else {
         var3.setValue(var2);
         return var3;
      }
   }

   public SnmpMibLeaf getLeaf(int var1) {
      return var1 <= this.b.length && var1 >= 1 ? this.b[var1 - 1] : null;
   }

   public SnmpMibLeaf getLeaf(String var1) throws SnmpValueException {
      SnmpOid var2 = new SnmpOid(this.c.getMetadata(), var1);
      int var3 = this.a.columnFromInstance(var2);
      return this.getLeaf(var3);
   }

   public SnmpMibLeaf getLeaf(SnmpOid var1) {
      int var2 = this.a.columnFromInstance(var1);
      return this.getLeaf(var2);
   }

   public Iterator getLeaves() {
      if (this.a.getNumOfColumns() == this.a.getMaxColumnNum()) {
         return Arrays.asList(this.b).iterator();
      } else {
         LinkedList var1 = new LinkedList();
         int var2 = 0;

         while(var2 < this.b.length) {
            if (this.b[var2] != null) {
               var1.add(this.b[var2]);
            }

            ++var2;
            if (SnmpMibNode.b) {
               break;
            }
         }

         return var1.iterator();
      }
   }

   public void destroy() {
      if (this.hasRowStatus()) {
         SnmpRowStatus var1 = this.getRowStatusLeaf();

         try {
            var1.setValue(6);
         } catch (SnmpValueException var3) {
         }
      }

      if (this.isInTable()) {
         this.a.removeRow(this.d);
      }

      int var4 = 0;

      while(var4 < this.b.length) {
         if (this.b[var4] != null) {
            this.b[var4].setRow((SnmpMibTableRow)null);
         }

         ++var4;
         if (SnmpMibNode.b) {
            break;
         }
      }

   }

   public boolean isAvailableForContextName(String var1) {
      return this.a.isAvailableForContextName(this, var1);
   }

   public String toString() {
      boolean var3 = SnmpMibNode.b;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u0005GX)"));
      var1.append('{');
      var1.append(a("\u0014DNg\u001d8AK)"));
      var1.append(this.getClassOid());
      var1.append(',');
      var1.append(a("\u001eFKq\u0016J"));
      var1.append(this.getIndex());
      var1.append(',');
      var1.append(a("\u001bMNb\u000b\u0004\u0015"));
      var1.append('{');
      int var2 = 0;

      while(true) {
         if (var2 < this.b.length) {
            if (var3) {
               break;
            }

            if (this.b[var2] != null) {
               label37: {
                  if (var2 > 0) {
                     var1.append(',');
                  }

                  if (this.b[var2] == null) {
                     var1.append(a("\u0019]Cx"));
                     if (!var3) {
                        break label37;
                     }
                  }

                  var1.append(this.b[var2].getClassOid());
                  var1.append('=');
                  var1.append(this.b[var2].getValue());
               }
            }

            ++var2;
            if (!var3) {
               continue;
            }
         }

         var1.append('}');
         var1.append('}');
         break;
      }

      return var1.toString();
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
               var10003 = 40;
               break;
            case 2:
               var10003 = 47;
               break;
            case 3:
               var10003 = 20;
               break;
            default:
               var10003 = 110;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
