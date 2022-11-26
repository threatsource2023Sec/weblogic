package monfox.toolkit.snmp.agent;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.metadata.SnmpTypeInfo;

public class SnmpMibTable extends SnmpMibNode {
   private InitializeListener a;
   private SnmpTableInfo b;
   private Map c;
   private int d;
   private int e;
   private int f;
   private boolean g;
   private boolean[] h;
   private SnmpMibLeafFactory i;
   private SnmpMibLeafFactory[] j;
   private SnmpValue[] k;
   private Logger l;
   private SnmpMibTableListener m;

   public SnmpMibTable(SnmpMetadata var1, String var2) throws SnmpMibException, SnmpValueException {
      this(new SnmpOid(var1, var2));
   }

   public SnmpMibTable(SnmpOid var1) throws SnmpMibException {
      boolean var7 = SnmpMibNode.b;
      super(var1);
      this.a = null;
      this.d = 0;
      this.e = 0;
      this.f = 0;
      this.g = true;
      SnmpOidInfo var2 = this.getOidInfo();
      if (var2 == null) {
         throw new SnmpMibException(a("Ix\rbq\ny\u0017z5L~\u0016j5Gr\foqKc\u0019.sEeXztH{\u001d.") + this.getOid());
      } else if (!(var2 instanceof SnmpTableInfo)) {
         throw new SnmpMibException(a("Eu\u0012kv^7\fweO7\u0011}5Dx\f.aKu\u0014k5") + this.getOid());
      } else {
         this.b = (SnmpTableInfo)var2;
         SnmpObjectInfo[] var3 = this.b.getColumns();
         this.l = Logger.getInstance(this.getOid().toString());
         this.d = var3.length;
         int var4 = 0;
         int var5 = 0;

         int var10000;
         int var10001;
         while(true) {
            if (var5 < var3.length) {
               int var6 = this.columnFromInstance(var3[var5].getOid());
               var10000 = var6;
               var10001 = var4;
               if (var7) {
                  break;
               }

               var4 = var6 > var4 ? var6 : var4;
               ++var5;
               if (!var7) {
                  continue;
               }
            }

            this.e = var4;
            var10000 = this.e;
            var10001 = this.d;
            break;
         }

         if (var10000 != var10001) {
            this.l.debug(a("\u0007:X@Z~RB.x_d\f.wO7\u0015gfY~\u0016i5Ix\u0014{xDdXg{\n0") + var2.getOid() + a("\r7U#"));
         }

         var5 = 0;

         while(true) {
            if (var5 < var3.length) {
               label60: {
                  SnmpTypeInfo var8 = var3[var5].getTypeInfo();
                  if (var7) {
                     break;
                  }

                  if (var8 != null && a("xx\u000f]aKc\r}").equals(var8.getName())) {
                     this.f = this.columnFromInstance(var3[var5].getOid());
                     this.setFactory(this.f, new SnmpRowStatusFactory());
                     if (!var7) {
                        break label60;
                     }
                  }

                  ++var5;
                  if (!var7) {
                     continue;
                  }
               }
            }

            this.c = new TreeMap(new IndexComparator());
            break;
         }

      }
   }

   public void setInitialValuesFromDefVals() {
      this.l.debug(a("Yr\fG{Cc\u0011oy|v\u0014{pYQ\naxnr\u001eXtFd"));
      SnmpObjectInfo[] var1 = this.b.getColumns();
      int var2 = 0;

      while(var2 < var1.length) {
         if (var1[var2].getDefVal() != null) {
            try {
               SnmpValue var3 = SnmpValue.getInstance(var1[var2], var1[var2].getDefVal());
               if (this.l.isDebugEnabled()) {
                  this.l.debug(a("Yr\fz|DpXg{Cc\u0011oy\na\u0019b`O-X") + var1[var2].getOid() + a("\n*X") + var3);
               }

               this.setInitialValue(this.columnFromInstance(var1[var2].getOid()), var3);
            } catch (SnmpValueException var4) {
               this.l.error(a("hV<.QOq.oy"), var4);
            }
         }

         ++var2;
         if (SnmpMibNode.b) {
            break;
         }
      }

   }

   public int getNumOfColumns() {
      return this.d;
   }

   public int getMaxColumnNum() {
      return this.e;
   }

   public int getNumOfRows() {
      return this.c.size();
   }

   public SnmpOid getMaxOid() {
      return null;
   }

   public SnmpTableInfo getTableInfo() {
      return this.b;
   }

   public SnmpObjectInfo[] getIndexInfo() {
      return this.b.getIndexes();
   }

   public SnmpObjectInfo[] getColumnInfo() {
      return this.b.getColumns();
   }

   public boolean hasRowStatus() {
      return this.f != 0;
   }

   public int getRowStatusColumn() {
      return this.f;
   }

   public void isSettableWhenActive(boolean var1) {
      this.g = var1;
   }

   public void isSettableWhenActive(int var1, boolean var2) {
      if (this.h == null) {
         this.h = new boolean[this.e];
      }

      this.h[var1 - 1] = var2;
   }

   public boolean isSettableWhenActive(int var1) {
      return this.h != null ? this.h[var1 - 1] : this.g;
   }

   public SnmpMibTableRow getRowByInstance(String var1) throws SnmpValueException {
      SnmpOid var2 = this.indexFromInstance(new SnmpOid(var1));
      return (SnmpMibTableRow)this.c.get(var2);
   }

   public SnmpMibTableRow getRowByInstance(SnmpOid var1) throws SnmpValueException {
      SnmpOid var2 = this.indexFromInstance(var1);
      return (SnmpMibTableRow)this.c.get(var2);
   }

   public SnmpMibTableRow getRow(String var1) throws SnmpValueException {
      SnmpOid var2 = new SnmpOid(var1);
      return (SnmpMibTableRow)this.c.get(var2);
   }

   public SnmpMibTableRow getRow(SnmpOid var1) {
      return (SnmpMibTableRow)this.c.get(var1);
   }

   public SnmpMibTableRow getRow(String[] var1) throws SnmpValueException, SnmpMibException {
      SnmpOid var2 = this.b(var1);
      return (SnmpMibTableRow)this.c.get(var2);
   }

   public SnmpMibTableRow getRow(SnmpValue[] var1) throws SnmpValueException, SnmpMibException {
      SnmpOid var2 = this.a(var1, true);
      return (SnmpMibTableRow)this.c.get(var2);
   }

   public Iterator getRows() {
      return this.c.values().iterator();
   }

   public Set getRowSet() {
      HashSet var1 = new HashSet();
      var1.addAll(this.c.values());
      return var1;
   }

   public boolean hasRow(SnmpOid var1) {
      return this.c.containsKey(var1);
   }

   public SnmpMibTableRow addRow(String var1) throws SnmpMibException, SnmpValueException {
      SnmpOid var2 = new SnmpOid(var1);
      SnmpMibTableRow var3 = this.initRow(var2);
      this.addRow(var3);
      return var3;
   }

   public SnmpMibTableRow addRow(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var2 = this.initRow(var1);
      this.addRow(var2);
      return var2;
   }

   public SnmpMibTableRow addRow(String[] var1) throws SnmpMibException, SnmpValueException {
      SnmpValue[] var2 = this.a(var1);
      SnmpOid var3 = this.a(var2, false);
      SnmpMibTableRow var4 = this.a(var3, var2);
      this.addRow(var4);
      return var4;
   }

   public SnmpMibTableRow addRow(SnmpValue[] var1) throws SnmpMibException, SnmpValueException {
      SnmpOid var2 = this.a(var1, true);
      SnmpMibTableRow var3 = this.a(var2, var1);
      this.addRow(var3);
      return var3;
   }

   public void addRow(SnmpMibTableRow var1) throws SnmpMibException {
      boolean var8 = SnmpMibNode.b;
      if (var1.getTable() != this) {
         throw new SnmpMibException(a("Xx\u000f.qEr\u000b.{EcXlpFx\u0016i5^xXz}CdXztH{\u001d"));
      } else if (this.getMib() == null) {
         throw new SnmpMibException(a("~v\u001abp\n~\u000b.{EcXoa^v\u001bfpN7\fa5KyX]{Gg5gw\u00047(bpKd\u001d.eOe\u001eagG7\u0019`5yy\u0015~XCuVoqN?V ;\u00037\fa5Kc\fovB7\ffp\nc\u0019lyO7\fa5K75GW"));
      } else {
         synchronized(this) {
            label46: {
               SnmpOid var3 = var1.getIndex();
               if (this.c.containsKey(var3)) {
                  throw new SnmpMibException(a("Xx\u000f.|Ds\u001dv5K{\nktNnXg{\nb\u000bk9\n") + var3);
               }

               SnmpMib var4 = this.getMib();
               Iterator var5 = var1.getLeaves();

               while(var5.hasNext()) {
                  SnmpMibLeaf var6 = (SnmpMibLeaf)var5.next();
                  var4.add(var6);
                  if (var8) {
                     break label46;
                  }

                  if (var8) {
                     break;
                  }
               }

               this.c.put(var3, var1);
            }
         }

         this.fireRowAddedEvent(var1);
         if (this.l.isInfoEnabled()) {
            this.l.info(a("ks\u001ckq\n") + var1);
         }

      }
   }

   public SnmpMibTableRow initRow(String var1) throws SnmpMibException, SnmpValueException {
      SnmpOid var2 = new SnmpOid(var1);
      return this.initRow(var2);
   }

   public SnmpMibTableRow initRow(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      SnmpValue[] var2 = this.a(var1);
      return this.a(var1, var2);
   }

   public SnmpMibTableRow initRow(SnmpOid var1, boolean var2) throws SnmpMibException, SnmpValueException {
      return this.a((SnmpOid)var1, (SnmpValue[])null);
   }

   public SnmpMibTableRow initRow(String[] var1) throws SnmpMibException, SnmpValueException {
      SnmpValue[] var2 = this.a(var1);
      SnmpOid var3 = this.a(var2, false);
      return this.a(var3, var2);
   }

   public SnmpMibTableRow initRow(SnmpValue[] var1) throws SnmpMibException, SnmpValueException {
      SnmpOid var2 = this.a(var1, true);
      return this.a(var2, var1);
   }

   private SnmpMibTableRow a(SnmpOid var1, SnmpValue[] var2) throws SnmpMibException, SnmpValueException {
      return this.a(var1, var2, true);
   }

   private SnmpMibTableRow a(SnmpOid var1, SnmpValue[] var2, boolean var3) throws SnmpMibException, SnmpValueException {
      boolean var9 = SnmpMibNode.b;
      if (this.c.containsKey(var1)) {
         throw new SnmpMibException(a("Xx\u000f.|Ds\u001dv5K{\nktNnXg{\nb\u000bk/\n") + var1);
      } else if (var2 != null && !this.validateIndex(var1, var2)) {
         throw new SnmpMibException(a("Cy\u000eoyCsX|z]7\u0011`qOoB.") + var1);
      } else {
         SnmpMibLeaf[] var4 = new SnmpMibLeaf[this.e];
         int var5 = 0;

         label71:
         while(true) {
            if (var5 < this.e) {
               SnmpMibLeaf var6 = this.a(var5 + 1, var1);
               if (var9) {
                  break;
               }

               label66: {
                  if (var6 == null) {
                     this.l.debug(a("\u0007:X{{Ku\u0014k5^xXmgOv\fk5L~\u001dbq\u00067\u001bay_z\u00163") + (var5 + 1));
                     if (!var9) {
                        break label66;
                     }
                  }

                  var4[var5] = var6;
               }

               ++var5;
               if (!var9) {
                  continue;
               }
            }

            if (var2 != null) {
               SnmpObjectInfo[] var10 = this.b.getIndexes();
               int var12 = 0;

               int var10000;
               while(true) {
                  if (var12 < var10.length) {
                     SnmpOid var7 = var10[var12].getOid();
                     var10000 = this.getOid().contains(var7);
                     if (var9) {
                        break;
                     }

                     if (var10000 != 0 || var9) {
                        int var8 = this.columnFromInstance(var10[var12].getOid());
                        this.a(var4[var8 - 1], var2[var12]);
                     }

                     ++var12;
                     if (!var9) {
                        continue;
                     }
                  }

                  if (this.k == null) {
                     break label71;
                  }

                  var10000 = var12;
                  break;
               }

               while(var10000 < var4.length) {
                  this.a(var4[var12], this.k[var12]);
                  ++var12;
                  if (var9) {
                     break label71;
                  }

                  var10000 = var12;
               }
            }
            break;
         }

         SnmpMibTableRow var11 = new SnmpMibTableRow(this, var1, var4);
         this.fireRowInitEvent(var11);
         return var11;
      }
   }

   private void a(SnmpMibLeaf var1, SnmpValue var2) throws SnmpValueException {
      if (var2 != null) {
         if (var1 != null) {
            int var3;
            label30: {
               var3 = 0;
               if (var1.getTypeInfo() != null) {
                  var3 = SnmpValue.validate(var1.getTypeInfo(), var2);
                  this.l.debug(a("q&%|pYb\u0014z(") + Snmp.errorStatusToString(var3));
                  if (!SnmpMibNode.b) {
                     break label30;
                  }
               }

               if (var1.getType() != -1) {
                  if (!SnmpValue.validate(var1.getType(), var2)) {
                     var3 = 7;
                  }

                  this.l.debug(a("q%%|pYb\u0014z(") + Snmp.errorStatusToString(var3));
               }
            }

            if (var3 == 0) {
               this.l.debug(a("q$VoHXr\u000b{y^*") + Snmp.errorStatusToString(var3));
               var3 = var1.valueOk(var2);
               this.l.debug(a("q$VlHXr\u000b{y^*") + Snmp.errorStatusToString(var3));
            }

            if (var3 != 0) {
               throw new SnmpValueException(a("Oe\nag\nd\u001dzaCy\u001f.cK{\rk5Q{\u001dos\u0017") + var1.getOid() + a("\u0006a\u0019b`O*") + var2 + a("\u0006r\n|zX*") + Snmp.errorStatusToString(var3) + "}");
            } else {
               var1.setValue(var2);
            }
         }
      }
   }

   private SnmpMibLeaf a(int var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      SnmpOid var3 = this.getOid();
      SnmpOid var4 = (SnmpOid)var3.clone();
      var4.setMetadata(var3.getMetadata());
      var4.append(1L);
      var4.append((long)var1);
      SnmpOid var5 = (SnmpOid)var4.clone();
      var5.setMetadata(var3.getMetadata());
      var5.append(var2);
      SnmpMibLeafFactory var6 = null;
      if (this.j != null) {
         var6 = this.j[var1 - 1];
      }

      if (var6 == null) {
         var6 = this.i;
      }

      if (var4.getOidInfo() != null && var4.getOidInfo() instanceof SnmpObjectInfo) {
         return var6 != null ? var6.getInstance(this, var4, var5) : new SnmpMibLeaf(var4, var5);
      } else {
         return null;
      }
   }

   public SnmpMibTableRow removeRowByInstance(String var1) throws SnmpValueException {
      SnmpOid var2 = this.indexFromInstance(new SnmpOid(var1));
      return this.removeRow(var2);
   }

   public SnmpMibTableRow removeRowByInstance(SnmpOid var1) throws SnmpValueException {
      SnmpOid var2 = this.indexFromInstance(new SnmpOid(var1));
      return this.removeRow(var2);
   }

   public SnmpMibTableRow removeRow(String var1) throws SnmpValueException {
      SnmpOid var2 = new SnmpOid(var1);
      return this.removeRow(var2);
   }

   public SnmpMibTableRow removeRow(SnmpOid var1) {
      synchronized(this) {
         SnmpMibTableRow var3 = (SnmpMibTableRow)this.c.remove(var1);
         if (var3 == null) {
            return null;
         } else {
            this.a(var3);
            return var3;
         }
      }
   }

   public SnmpMibTableRow removeRow(SnmpMibTableRow var1) {
      return this.removeRow(var1.getIndex());
   }

   private void a(SnmpMibTableRow var1) {
      boolean var5 = SnmpMibNode.b;
      SnmpMib var2 = this.getMib();
      Iterator var3 = var1.getLeaves();

      while(true) {
         if (var3.hasNext()) {
            SnmpMibLeaf var4 = (SnmpMibLeaf)var3.next();
            var2.remove((SnmpMibNode)var4);
            if (var5) {
               break;
            }

            if (!var5) {
               continue;
            }
         }

         this.fireRowRemovedEvent(var1);
         break;
      }

      if (this.l.isInfoEnabled()) {
         this.l.info(a("xr\u0015acOsX") + var1);
      }

   }

   public void removeAllRows() {
      Iterator var1 = this.c.values().iterator();

      while(var1.hasNext()) {
         SnmpMibTableRow var2 = (SnmpMibTableRow)var1.next();
         synchronized(this) {
            var1.remove();
            this.a(var2);
            var2.destroy();
         }

         if (SnmpMibNode.b) {
            break;
         }
      }

   }

   public SnmpMibTableRow removeRow(String[] var1) throws SnmpMibException, SnmpValueException {
      SnmpOid var2 = this.b(var1);
      return this.removeRow(var2);
   }

   public SnmpMibTableRow removeRow(SnmpValue[] var1) throws SnmpMibException, SnmpValueException {
      SnmpOid var2 = this.a(var1, true);
      return this.removeRow(var2);
   }

   public void setDefaultFactory(SnmpMibLeafFactory var1) {
      this.i = var1;
   }

   public void setFactory(String var1, SnmpMibLeafFactory var2) throws SnmpMibException, SnmpValueException {
      SnmpOid var3 = new SnmpOid(this.getOid().getMetadata(), var1);
      this.setFactory(var3, var2);
   }

   public void setFactory(SnmpOid var1, SnmpMibLeafFactory var2) throws SnmpMibException {
      int var3 = this.columnFromInstance(var1);
      if (var3 < 1) {
         throw new SnmpMibException(var1 + a("\n~\u000b.{EcXo5Ix\u0014{xD7\u0017h5") + this.getOid());
      } else {
         this.setFactory(var3, var2);
      }
   }

   public void setFactory(int var1, SnmpMibLeafFactory var2) {
      if (this.j == null) {
         this.j = new SnmpMibLeafFactory[this.e];
      }

      this.j[var1 - 1] = var2;
   }

   public void setInitialValue(SnmpOid var1, SnmpValue var2) {
      int var3 = this.columnFromInstance(var1);
      if (this.k == null) {
         this.k = new SnmpValue[this.e];
      }

      this.k[var3 - 1] = var2;
   }

   public void setInitialValue(int var1, SnmpValue var2) {
      if (this.k == null) {
         this.k = new SnmpValue[this.e];
      }

      this.k[var1 - 1] = var2;
   }

   public void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
   }

   public void getNextRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
   }

   public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return 0;
   }

   public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return false;
   }

   public boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return false;
   }

   public void cleanupSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
   }

   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpAccessPolicy var3) {
      return false;
   }

   public void addListener(SnmpMibTableListener var1) {
      this.m = monfox.toolkit.snmp.agent.f.add(this.m, var1);
   }

   public void removeListener(SnmpMibTableListener var1) {
      this.m = monfox.toolkit.snmp.agent.f.remove(this.m, var1);
   }

   protected void fireRowInitEvent(SnmpMibTableRow var1) {
      if (this.m != null) {
         this.m.rowInit(var1);
      }

   }

   protected void fireRowAddedEvent(SnmpMibTableRow var1) {
      if (this.m != null) {
         this.m.rowAdded(var1);
      }

   }

   protected void fireRowActivatedEvent(SnmpMibTableRow var1) {
      if (this.m != null) {
         this.m.rowActivated(var1);
      }

   }

   protected void fireRowDeactivatedEvent(SnmpMibTableRow var1) {
      if (this.m != null) {
         this.m.rowDeactivated(var1);
      }

   }

   protected void fireRowRemovedEvent(SnmpMibTableRow var1) {
      if (this.m != null) {
         this.m.rowRemoved(var1);
      }

   }

   protected void columnUpdated(SnmpMibTableRow var1, SnmpMibLeaf var2) {
   }

   private SnmpValue[] a(String[] var1) throws SnmpMibException, SnmpValueException {
      boolean var6 = SnmpMibNode.b;
      SnmpObjectInfo[] var2 = this.b.getIndexes();
      if (var1.length != var2.length) {
         if (var1.length > var2.length) {
            throw new SnmpMibException(a("^x\u0017.xKy\u0001.cK{\rkf\ng\nacCs\u001dj5\u0002r\u0000~pIc\u001dj5") + var2.length + ")");
         } else {
            throw new SnmpMibException(a("G~\u000b}|DpXg{Nr\u0000.cK{\rk=Y>X&pRg\u001dmaOsX") + var2.length + ")");
         }
      } else {
         new SnmpOid();
         SnmpValue[] var4 = new SnmpValue[var2.length];
         int var5 = 0;

         SnmpValue[] var10000;
         while(true) {
            if (var5 < var2.length) {
               var10000 = var4;
               if (var6) {
                  break;
               }

               var4[var5] = SnmpValue.getInstance(var2[var5], var1[var5]);
               ++var5;
               if (!var6) {
                  continue;
               }
            }

            var10000 = var4;
            break;
         }

         return var10000;
      }
   }

   private SnmpOid a(SnmpValue[] var1, boolean var2) throws SnmpMibException, SnmpValueException {
      boolean var8 = SnmpMibNode.b;
      SnmpOid var3 = new SnmpOid();
      SnmpObjectInfo[] var4;
      int var5;
      boolean var6;
      boolean var7;
      if (var2) {
         var4 = this.b.getIndexes();
         if (var1.length != var4.length) {
            throw new SnmpMibException(a("G~\u000b}|DpXg{Nr\u0000.cK{\rk=Y>V.SEb\u0016j5") + var1.length + a("\u00067\nkd_~\nk5") + var4.length);
         }

         var5 = 0;

         while(var5 < var1.length) {
            if (var8) {
               return var3;
            }

            if (!SnmpValue.validate(var4[var5].getType(), var1[var5])) {
               throw new SnmpValueException(a("Cy\u000eoyCsXxtFb\u001d.sEeXzlZrX") + SnmpValue.typeToString(var4[var5].getType()) + a("\u00107") + var1[var5]);
            }

            var6 = false;

            try {
               var6 = SnmpFramework.isFixedStringEncodingEnabled() && var4[var5].getType() == 4 && var4[var5].getTypeInfo().isFixedSize();
               if (this.l.isDebugEnabled() && var6) {
                  this.l.debug(a("CdUh|Rr\u001cU") + var4[var5].getOid() + a("w-X") + var6);
               }
            } catch (Exception var10) {
            }

            var7 = var4 != null && var4.length > var5 && (this.b.isImplied() && var5 == var1.length - 1 || var6);
            var1[var5].appendIndexOid(var3, var7);
            ++var5;
            if (var8) {
               break;
            }
         }

         if (!var8) {
            return var3;
         }
      }

      var4 = this.b.getIndexes();
      var5 = 0;

      while(var5 < var1.length) {
         var6 = false;

         try {
            var6 = SnmpFramework.isFixedStringEncodingEnabled() && var4[var5].getType() == 4 && var4[var5].getTypeInfo().isFixedSize();
            if (this.l.isDebugEnabled() && var6) {
               this.l.debug(a("CdUh|Rr\u001cU") + var4[var5].getOid() + a("w-X") + var6);
            }
         } catch (Exception var9) {
         }

         var7 = var4 != null && var4.length > var5 && (this.b.isImplied() && var5 == var1.length - 1 || var6);
         var1[var5].appendIndexOid(var3, var7);
         ++var5;
         if (var8) {
            break;
         }
      }

      return var3;
   }

   private SnmpOid b(String[] var1) throws SnmpMibException, SnmpValueException {
      SnmpObjectInfo[] var2 = this.b.getIndexes();
      if (var1.length != var2.length) {
         throw new SnmpMibException(a("G~\u000b}|DpXg{Nr\u0000.cK{\rk=Y>"));
      } else {
         SnmpOid var3 = new SnmpOid();
         SnmpValue[] var4 = new SnmpValue[var2.length];
         int var5 = 0;

         while(var5 < var2.length) {
            var4[var5] = SnmpValue.getInstance(var2[var5].getType(), var1[var5]);
            boolean var6 = false;

            try {
               var6 = SnmpFramework.isFixedStringEncodingEnabled() && var2[var5].getType() == 4 && var2[var5].getTypeInfo().isFixedSize();
               if (this.l.isDebugEnabled() && var6) {
                  this.l.debug(a("CdUh|Rr\u001cU") + var2[var5].getOid() + a("w-X") + var6);
               }
            } catch (Exception var8) {
            }

            var4[var5].appendIndexOid(var3, this.b.isImplied() && var5 == var2.length - 1 || var6);
            ++var5;
            if (SnmpMibNode.b) {
               break;
            }
         }

         return var3;
      }
   }

   SnmpValue[] a(SnmpOid var1) throws SnmpValueException {
      boolean var9 = SnmpMibNode.b;
      if (this.l.isDebugEnabled()) {
         this.l.debug(a("\\v\u0014{pYQ\naxcy\u001ckm\u00107") + var1);
      }

      SnmpObjectInfo[] var2 = this.b.getIndexes();
      SnmpValue[] var3 = new SnmpValue[var2.length];
      int var4 = 0;
      int var5 = 0;

      SnmpValue[] var10000;
      while(true) {
         if (var5 < var2.length) {
            int var6 = var2[var5].getType();
            var10000 = var3;
            if (var9) {
               break;
            }

            var3[var5] = SnmpValue.getInstance(var6, (String)null);
            if (this.l.isDebugEnabled()) {
               this.l.debug(a("\n7X.5") + var2[var5].getOid() + a("\u0002c\u0001~p\u00107") + var6 + a("\u00037E.") + var3[var5]);
            }

            int var7 = -1;

            try {
               if (SnmpFramework.isFixedStringEncodingEnabled() && (var2[var5].getType() == 4 || var2[var5].getType() == 6) && var2[var5].getTypeInfo().isFixedSize()) {
                  var7 = var2[var5].getTypeInfo().getFixedSize();
               }

               if (this.l.isDebugEnabled() && var7 > 0) {
                  this.l.debug(a("CdUh|Rr\u001c#fCm\u001dU") + var2[var5].getOid() + a("w-X") + var7);
               }
            } catch (Exception var10) {
            }

            var4 = var3[var5].fromIndexOid(var1, var4, this.b.isImplied() && var5 == var3.length - 1, var7);
            ++var5;
            if (!var9) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public boolean readyForService(SnmpPendingIndication var1, SnmpMibTableRow var2, SnmpVarBind[] var3) {
      return true;
   }

   public boolean validateIndex(SnmpOid var1, SnmpValue[] var2) {
      return true;
   }

   public boolean validateInstance(SnmpOid var1) {
      SnmpOid var2 = this.getOid();
      if (var1.getLength() <= var2.getLength() + 2) {
         return false;
      } else if (!var2.contains(var1)) {
         return false;
      } else {
         int var3 = this.columnFromInstance(var1);
         return var3 <= this.e && var3 >= 1;
      }
   }

   public SnmpOid indexFromInstance(SnmpOid var1) {
      int var2 = this.getOid().getLength();

      SnmpOid var3;
      try {
         var3 = var1.suboid(var2 + 2);
      } catch (Exception var5) {
         return null;
      }

      var3.setMetadata((SnmpMetadata)null);
      return var3;
   }

   public int columnFromInstance(SnmpOid var1) {
      int var2 = this.getOid().getLength();
      boolean var3 = false;

      try {
         int var6 = (int)var1.get(var2 + 1);
         return var6;
      } catch (Exception var5) {
         return 0;
      }
   }

   public boolean isAvailableForContextName(SnmpMibTableRow var1, String var2) {
      return true;
   }

   public void prepareForAccess() {
      try {
         if (this.getNumOfRows() == 0) {
            InitializeListener var1 = this.a;
            if (var1 != null) {
               var1.initialize(this);
            }
         }
      } catch (Exception var2) {
         this.l.error(a("Oe\nag\n~\u0016.\\D~\fgtF~\u0002kYCd\fk{Oe"), var2);
      }

   }

   public void setInitializeListener(InitializeListener var1) {
      if (this.getMib() != null) {
         this.getMib().isPrepareForAccessSupported(true);
      }

      this.a = var1;
   }

   protected void setMib(SnmpMib var1) {
      super.setMib(var1);
      if (this.a != null) {
         var1.isPrepareForAccessSupported(true);
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("^v\u001abp\u0017"));
      var1.append('{');
      var1.append(a("I{\u0019}fe~\u001c3"));
      var1.append(this.getOid());
      var1.append(',');
      var1.append(a("Ix\u0014{xDT\u0017{{^*"));
      var1.append(this.getNumOfColumns());
      var1.append(',');
      var1.append(a("Gv\u0000MzFb\u0015`[_zE"));
      var1.append(this.getMaxColumnNum());
      var1.append(',');
      var1.append(a("Xx\u000fMz_y\f3"));
      var1.append(this.getNumOfRows());
      var1.append(',');
      var1.append(a("Yc\u0019z`YT\u0017b`GyE"));
      var1.append(this.getRowStatusColumn());
      var1.append('}');
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
               var10003 = 42;
               break;
            case 1:
               var10003 = 23;
               break;
            case 2:
               var10003 = 120;
               break;
            case 3:
               var10003 = 14;
               break;
            default:
               var10003 = 21;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class IndexComparator implements Comparator {
      public int compare(Object var1, Object var2) {
         SnmpOid var3 = (SnmpOid)var1;
         SnmpOid var4 = (SnmpOid)var2;
         if (var3 == null && var4 == null) {
            return 0;
         } else if (var3 == null) {
            return -1;
         } else {
            return var4 == null ? 1 : var3.compareTo(var4);
         }
      }

      public boolean equals(Object var1) {
         if (var1 == this) {
            return true;
         } else {
            return var1 instanceof IndexComparator;
         }
      }
   }

   public interface InitializeListener {
      void initialize(SnmpMibTable var1);
   }
}
