package monfox.toolkit.snmp.agent;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTypeInfo;

public class SnmpMibLeaf extends SnmpMibNode {
   protected SnmpOid _classOid;
   protected SnmpValue _value;
   protected SnmpValue _savedValue;
   protected SnmpObjectInfo _objectInfo;
   protected int _access;
   protected int _type;
   protected SnmpTypeInfo _typeInfo;
   protected SnmpMibTable _table;
   protected SnmpMibTableRow _row;
   private Logger a;

   public SnmpMibLeaf(SnmpMetadata var1, String var2) throws SnmpValueException, SnmpMibException {
      this(new SnmpOid(var1, var2));
   }

   public SnmpMibLeaf(String var1) throws SnmpValueException, SnmpMibException {
      this(new SnmpOid(var1));
   }

   public SnmpMibLeaf(SnmpOid var1) throws SnmpMibException {
      this._access = -1;
      this._type = -1;
      this._typeInfo = null;
      this._table = null;
      this._row = null;
      this.a = null;
      this.a = Logger.getInstance(a("wD8\u0005\u0012MH\u0019\u0010>B"));
      SnmpOidInfo var2 = var1.getOidInfo();
      SnmpObjectInfo var3 = null;
      SnmpOid var4;
      if (var2 != null && var2 instanceof SnmpObjectInfo) {
         var3 = (SnmpObjectInfo)var2;
         var4 = var1;
         var1 = var2.getOid();
         var1.setMetadata(var4.getMetadata());
      }

      var4 = (SnmpOid)var1.clone();
      var4.setMetadata(var1.getMetadata());
      var4.append(0L);
      this._oid = var4;
      this._classOid = var1;
      this._objectInfo = var3;
   }

   public SnmpMibLeaf(SnmpMetadata var1, String var2, String var3) throws SnmpMibException, SnmpValueException {
      this(new SnmpOid(var1, var2), new SnmpOid(var1, var3));
   }

   public SnmpMibLeaf(String var1, String var2) throws SnmpMibException, SnmpValueException {
      this(new SnmpOid(var1), new SnmpOid(var2));
   }

   public SnmpMibLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException {
      this._access = -1;
      this._type = -1;
      this._typeInfo = null;
      this._table = null;
      this._row = null;
      this.a = null;
      this.a = Logger.getInstance(a("wD8\u0005\u0012MH\u0019\u0010>B"));
      SnmpOidInfo var3 = var1.getOidInfo();
      SnmpObjectInfo var4 = null;
      if (var3 != null) {
         this.a.debug(a("\t\u0007u\u001c1BEoU") + var3);
         if (var3 instanceof SnmpObjectInfo) {
            var4 = (SnmpObjectInfo)var3;
            SnmpOid var5 = var1;
            var1 = var3.getOid();
            var1.setMetadata(var5.getMetadata());
         }
      }

      a(var1, var2);
      this._oid = var2;
      this._classOid = var1;
      this._objectInfo = var4;
   }

   public SnmpValue getValue() {
      return this._value;
   }

   public SnmpValue getSavedValue() {
      return this._savedValue;
   }

   public boolean hasValue() {
      return this.getValue() != null;
   }

   public void setValue(String var1) throws SnmpValueException {
      SnmpValue var2;
      label25: {
         boolean var3 = SnmpMibNode.b;
         if (this.getTypeInfo() != null) {
            var2 = SnmpValue.getInstance(this.getTypeInfo(), var1, this.getObjectInfo());
            if (!var3) {
               break label25;
            }
         }

         if (this.getObjectInfo() != null) {
            var2 = SnmpValue.getInstance(this.getObjectInfo(), var1);
            if (!var3) {
               break label25;
            }
         }

         if (this.getType() == -1) {
            throw new SnmpValueException(a("JEu\u0001&TOu\u0011:BC;\u0010;\u0004L:\u0007\u007fHO4\u0013e\u0004") + this.getOid());
         }

         var2 = SnmpValue.getInstance(this.getType(), var1);
         if (var3) {
            throw new SnmpValueException(a("JEu\u0001&TOu\u0011:BC;\u0010;\u0004L:\u0007\u007fHO4\u0013e\u0004") + this.getOid());
         }
      }

      this.setValue(var2);
   }

   public void setValue(String var1, boolean var2) throws SnmpValueException {
      SnmpValue var3;
      label25: {
         boolean var4 = SnmpMibNode.b;
         if (this.getTypeInfo() != null) {
            var3 = SnmpValue.getInstance(this.getTypeInfo(), var1, this.getObjectInfo(), var2);
            if (!var4) {
               break label25;
            }
         }

         if (this.getObjectInfo() != null) {
            var3 = SnmpValue.getInstance(this.getObjectInfo(), var1, var2);
            if (!var4) {
               break label25;
            }
         }

         if (this.getType() == -1) {
            throw new SnmpValueException(a("JEu\u0001&TOu\u0011:BC;\u0010;\u0004L:\u0007\u007fHO4\u0013e\u0004") + this.getOid());
         }

         var3 = SnmpValue.getInstance(this.getType(), var1);
         if (var4) {
            throw new SnmpValueException(a("JEu\u0001&TOu\u0011:BC;\u0010;\u0004L:\u0007\u007fHO4\u0013e\u0004") + this.getOid());
         }
      }

      this.setValue(var3);
   }

   public void setValue(SnmpValue var1) throws SnmpValueException {
      synchronized(this) {
         this._savedValue = this._value;
         this._value = var1;
      }
   }

   public boolean unsetValue() {
      synchronized(this) {
         if (this._savedValue != null) {
            this._value = this._savedValue;
         }

         this._savedValue = null;
         return true;
      }
   }

   public void cleanupValue() {
      this._savedValue = null;
   }

   public void clearValue() {
      synchronized(this) {
         this._value = null;
         this._savedValue = null;
      }
   }

   public int valueOk(SnmpValue var1) {
      return 0;
   }

   public SnmpOid getInstanceOid() {
      return this.getOid();
   }

   public SnmpOid getClassOid() {
      return this._classOid;
   }

   public SnmpOid getMaxOid() {
      return this.getOid();
   }

   public int getNodeType() {
      return 1;
   }

   public SnmpObjectInfo getObjectInfo() {
      return this._objectInfo;
   }

   public int getAccess() {
      if (this._access != -1) {
         return this._access;
      } else {
         return this.getObjectInfo() != null ? this.getObjectInfo().getAccess() : 16;
      }
   }

   public void setAccess(int var1) {
      this._access = var1;
   }

   public int getType() {
      if (this._type != -1) {
         return this._type;
      } else if (this.getTypeInfo() != null) {
         return this.getTypeInfo().getType();
      } else {
         return this.getObjectInfo() != null ? this.getObjectInfo().getType() : -1;
      }
   }

   public void setType(int var1) {
      this._type = var1;
   }

   public SnmpTypeInfo getTypeInfo() {
      if (this._typeInfo != null) {
         return this._typeInfo;
      } else {
         return this.getObjectInfo() != null ? this.getObjectInfo().getTypeInfo() : null;
      }
   }

   public void setTypeInfo(SnmpTypeInfo var1) {
      this._typeInfo = var1;
   }

   public SnmpMibTable getTable() {
      return this._row == null ? null : this._row.getTable();
   }

   public SnmpMibTableRow getRow() {
      return this._row;
   }

   public boolean isInTable() {
      return this.getTable() != null;
   }

   public boolean isInRow() {
      return this.getRow() != null;
   }

   protected void setRow(SnmpMibTableRow var1) {
      this._row = var1;
   }

   public void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("CO!':U_0\u0006+\fZ<Y") + var2 + "," + var3 + ")");
      }

      try {
         SnmpValue var4 = this.getValue();
         if (this.a.isDebugEnabled()) {
            this.a.debug(a("\t\u0007u\u0003>H_0O\u007f") + var4);
         }

         if (var4 == null) {
            var3.setToNoSuchObject();
            if (!SnmpMibNode.b) {
               return;
            }
         }

         var3.setValue(var4);
      } catch (SnmpRuntimeErrorException var5) {
         if (this.a.isDebugEnabled()) {
            this.a.debug(a("CO!O\u007fV_;\u00016IOu\u0010-VE'U:\\I0\u0005+ME;U>GI0\u0006,MD2Ux") + var3.getOid() + "'", var5);
         }

         var1.setError(var5.getErrorStatus(), var2);
      }

   }

   public void getNextRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      var3.setOid(this.getInstanceOid());

      try {
         SnmpValue var4 = this.getValue();
         if (var4 == null) {
            var3.setToNoSuchObject();
            if (!SnmpMibNode.b) {
               return;
            }
         }

         var3.setValue(var4);
      } catch (SnmpRuntimeErrorException var5) {
         if (this.a.isDebugEnabled()) {
            this.a.debug(a("CO!;:\\^oU-QD!\u001c2A\n0\u0007-KXu\u0010'GO%\u00016KDu\u0014<GO&\u00066JMuR") + var3.getOid() + "'", var5);
         }

         var1.setError(var5.getErrorStatus(), var2);
      }

   }

   public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      try {
         SnmpValue var4 = var3.getValue();
         if (this.getTypeInfo() != null) {
            int var5 = SnmpValue.validate(this.getTypeInfo(), var4);
            if (var5 != 0) {
               return var5;
            }

            if (!SnmpMibNode.b) {
               return this.valueOk(var4);
            }
         }

         if (this.getType() != -1) {
            boolean var7 = SnmpValue.validate(this.getType(), var4);
            if (!var7) {
               return 7;
            }
         }

         return this.valueOk(var4);
      } catch (SnmpRuntimeErrorException var6) {
         if (this.a.isDebugEnabled()) {
            this.a.debug(a("CO!;:\\^oU-QD!\u001c2A\n0\u0007-KXu\u0010'GO%\u00016KDu\u0014<GO&\u00066JMuR") + var3.getOid() + "'", var6);
         }

         return var6.getErrorStatus();
      }
   }

   public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      try {
         this.setValue(var3.getValue());

         try {
            if (this._table != null && this._row != null) {
               this._table.columnUpdated(this._row, this);
            }
         } catch (Exception var5) {
            this.a.debug(a("AX'\u001a-\u0004C;U\fJG%86F~4\u00173A\u00046\u001a3QG; /@K!\u0010;\u0004B4\u001b;HO'"), var5);
         }

         return true;
      } catch (SnmpValueException var6) {
         return false;
      } catch (SnmpRuntimeErrorException var7) {
         if (this.a.isDebugEnabled()) {
            this.a.debug(a("CO!;:\\^oU-QD!\u001c2A\n0\u0007-KXu\u0010'GO%\u00016KDu\u0014<GO&\u00066JMuR") + var3.getOid() + "'", var7);
         }

         var1.setError(var7.getErrorStatus(), var2);
         return false;
      }
   }

   public boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return this.unsetValue();
   }

   public void cleanupSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      this.cleanupValue();
   }

   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpAccessPolicy var3) {
      int var4 = this.getAccess();
      boolean var5 = var1.getRequestType() == 163;
      switch (var4) {
         case 0:
            return false;
         case 1:
            return !var5;
         case 2:
            return var5;
         case 3:
            return true;
         case 4:
         case 6:
         case 7:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            return true;
         case 5:
            return true;
         case 8:
            return false;
         case 16:
            return true;
      }
   }

   public boolean isAvailableForContextName(String var1) {
      SnmpMibTableRow var2 = this._row;
      return var2 != null ? this._row.isAvailableForContextName(var1) : true;
   }

   private static void a(SnmpMibLeaf var0) throws SnmpMibException, SnmpValueException {
      SnmpOid var1 = var0.getClassOid();
      SnmpOid var2 = var0.getInstanceOid();
      if (!a(var1, var2)) {
         throw new SnmpMibException(a("MD#\u00143MNu\u001c1W^4\u001b<A\u0010u\u00163EY&H") + var1 + a("\b\n<\u001b,PK;\u0016:\u0019") + var2);
      } else {
         SnmpValue var3 = var0.getValue();
         if (var3 != null) {
            int var4 = var0.getType();
            if (!SnmpValue.validate(var4, var3)) {
               throw new SnmpMibException(a("MD#\u00143MNu\u0003>H_0U9KXu\u0001&TOu") + SnmpValue.typeToString(var4) + a("\u001e\n") + var3);
            }
         }
      }
   }

   private static boolean a(SnmpOid var0, SnmpOid var1) {
      if (var0 != null && var1 != null) {
         return var0.contains(var1) && !var0.equals(var1);
      } else {
         return false;
      }
   }

   public String toString() {
      boolean var2 = SnmpMibNode.b;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("HO4\u0013b"));
      var1.append('{');
      var1.append(a("KC1H"));
      var1.append(this.getOid());
      var1.append(',');
      var1.append(a("GF4\u0006,kC1H"));
      var1.append(this.getClassOid());
      var1.append(',');
      var1.append(a("RK9\u0000:\u0019"));
      var1.append(this.getValue());
      var1.append(',');
      var1.append(a("WK#\u0010;rK9\u0000:\u0019"));
      var1.append(this.getSavedValue());
      var1.append(',');
      var1.append(a("EI6\u0010,W\u0017"));
      var1.append(SnmpObjectInfo.accessToString(this.getAccess()));
      var1.append(',');
      var1.append(a("PS%\u0010b"));
      var1.append(SnmpValue.typeToShortString(this.getType()));
      var1.append(',');
      var1.append(a("MY\u001c\u001b\u000bEH9\u0010b"));
      var1.append(this.isInTable());
      var1.append(',');
      var1.append(a("MY\u001c\u001b\rK]h"));
      var1.append(this.isInRow());
      var1.append('}');
      String var10000 = var1.toString();
      if (var2) {
         SnmpException.b = !SnmpException.b;
      }

      return var10000;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 36;
               break;
            case 1:
               var10003 = 42;
               break;
            case 2:
               var10003 = 85;
               break;
            case 3:
               var10003 = 117;
               break;
            default:
               var10003 = 95;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
