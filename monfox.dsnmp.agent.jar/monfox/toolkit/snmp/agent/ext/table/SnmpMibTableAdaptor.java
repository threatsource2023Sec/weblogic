package monfox.toolkit.snmp.agent.ext.table;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.SnmpRowStatus;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;

public class SnmpMibTableAdaptor extends SnmpMibTable {
   private Map c;
   private boolean g;
   private boolean a;
   private boolean b;
   private static Logger d = Logger.getInstance(a("\u001d8'\u0001H"), a("\u0018,,\u0002Lt.1\u00185\r*+\u0000]"), a("\n\u0005\u0004<U0\t=-z5\u000e((y)\u001f\u0006>"));
   public static boolean e;

   public SnmpMibTableAdaptor(SnmpMetadata var1, String var2) throws SnmpMibException, SnmpValueException {
      boolean var3 = e;
      super(var1, var2);
      this.c = new Hashtable();
      this.g = false;
      this.a = true;
      this.b = true;
      this.setDefaultFactory(new AdaptorMibNodeFactory());
      if (d.isDebugEnabled()) {
         d.debug(a(":\u0019\f-l0\u0005\u000elK7\u0006\u0019\u0001q;?\b.t<*\r-h-\u0004\u001bv8") + this.getOid());
      }

      if (SnmpException.b) {
         e = !var3;
      }

   }

   public SnmpMibTableAdaptor(SnmpOid var1) throws SnmpMibException {
      super(var1);
      this.c = new Hashtable();
      this.g = false;
      this.a = true;
      this.b = true;
      this.setDefaultFactory(new AdaptorMibNodeFactory());
      if (d.isDebugEnabled()) {
         d.debug(a(":\u0019\f-l0\u0005\u000elK7\u0006\u0019\u0001q;?\b.t<*\r-h-\u0004\u001bv8") + this.getOid());
      }

   }

   public void isLazyLoadingEnabled(boolean var1) {
      if (d.isDebugEnabled()) {
         d.debug(a("*\u000e\u001d8q7\fI%k\u0015\n\u00135T6\n\r%v>.\u0007-z5\u000e\rv8") + var1);
      }

      this.g = var1;
      if (this.getNumOfRows() == 0) {
         d.debug(a("yKDa8<\u0006\u00198ay\u001f\b.t<QI?}-\u001f\u0000\"\u007fy\u0002\u001a\r{-\u0002\u001f)%?\n\u0005?}"));
         this.a = false;
      }

   }

   public boolean isLazyLoadingEnabled() {
      return this.g;
   }

   public void isValidationEnabled(boolean var1) {
      this.b = var1;
   }

   public boolean isValidationEnabled() {
      return this.b;
   }

   public boolean isAvailableForContextName(SnmpMibTableRow var1, String var2) {
      Object var3 = var1.getUserObject();
      if (var3 != null && var3 instanceof Row) {
         Row var4 = (Row)var3;
         return var4.isAvailableForContextName(var2);
      } else {
         return true;
      }
   }

   public Iterator getAdaptorRows() {
      Vector var1 = new Vector();
      var1.addAll(this.c.values());
      return var1.iterator();
   }

   public void addRow(Row var1) throws SnmpMibException, SnmpValueException {
      boolean var5 = e;
      if (d.isDebugEnabled()) {
         d.debug(a("8\u000f\r\u001ew.KA") + this.getOid() + a("pK"));
      }

      if (var1 == null) {
         throw new SnmpMibException(a("7\u001e\u0005 8+\u0004\u001elh8\u0018\u001a)|"));
      } else {
         SnmpOid var2 = this.a(var1, this.b);
         var1.a(var2);
         if (d.isDebugEnabled()) {
            d.debug(a("yKDa8+\u0004\u001elq7\u000f\f4\"y") + var2);
         }

         synchronized(this.c) {
            this.c.put(var2, var1);
         }

         if (this.g) {
            d.debug(a("yKDa85\n\u0013585\u0004\b(q7\fI)v8\t\u0005)|"));
            if (this.a) {
               d.debug(a("yKDa88\b\u001d%n<K\u001d-z5\u000e"));
               this.a(var1);
               if (!var5) {
                  return;
               }

               SnmpException.b = !SnmpException.b;
            }

            d.debug(a("yKDa80\u0005\b/l0\u001d\fll8\t\u0005)"));
            if (!var5) {
               return;
            }
         }

         d.debug(a("yKDa85\n\u0013585\u0004\b(q7\fI\u0002W\rK\f\"y;\u0007\f("));
         this.a(var1);
      }
   }

   private void a(Row var1) throws SnmpMibException, SnmpValueException {
      boolean var8 = e;
      if (d.isDebugEnabled()) {
         d.debug(a("+\n\u001e\r|=9\u0006;8q") + this.getOid() + a("pQI") + var1.getIndexOid());
      }

      SnmpOid var2 = var1.getIndexOid();
      SnmpMibTableRow var3 = this.initRow(var2, false);
      Iterator var4 = var3.getLeaves();

      while(true) {
         if (var4.hasNext()) {
            Object var5 = var4.next();
            if (var8) {
               break;
            }

            label42: {
               if (var5 instanceof AdaptorMibNode) {
                  AdaptorMibNode var6 = (AdaptorMibNode)var5;
                  if (var6 != null) {
                     var6.setRow(var1);
                  }

                  if (!var8) {
                     break label42;
                  }
               }

               if (var5 instanceof SnmpRowStatus) {
                  d.debug(a("+\u0004\u001eak-\n\u001d9ky\u0005\u0006(}cK") + var5);
                  SnmpRowStatus var9 = (SnmpRowStatus)var5;
                  SnmpInt var7 = new SnmpInt(var1.getRowStatus());
                  var9.setValue(var7);
                  if (!var8) {
                     break label42;
                  }
               }

               d.debug(a("7\u0004\u0007ay=\n\u00198w+K\u0007#|<QI") + var5);
            }

            if (!var8) {
               continue;
            }
         }

         var3.setUserObject(var1);
         this.addRow(var3);
         var1.a(var3);
         d.debug(a("yFDly=\n\u00198w+K\u001b#oy\n\r(}="));
         break;
      }

   }

   public void removeRow(Row var1) throws SnmpMibException, SnmpValueException {
      if (var1 == null) {
         throw new SnmpMibException(a("7\u001e\u0005 8+\u0004\u001elh8\u0018\u001a)|"));
      } else {
         synchronized(this.c) {
            this.c.remove(var1.getIndexOid());
         }

         label25: {
            if (this.g) {
               if (!this.a) {
                  break label25;
               }

               this.b(var1);
               if (!e) {
                  break label25;
               }
            }

            this.b(var1);
         }

         var1.a((SnmpMibTableRow)null);
      }
   }

   private void b(Row var1) throws SnmpMibException, SnmpValueException {
      if (var1 == null) {
         throw new SnmpMibException(a("7\u001e\u0005 8+\u0004\u001elh8\u0018\u001a)|"));
      } else if (var1.getRow() == null) {
         throw new SnmpMibException(a("+\u0004\u001elv6\u001fI/m+\u0019\f\"l5\u0012I-l-\n\n$}=K\u001d#8-\n\u000b }"));
      } else if (var1.getRow().getTable() != this) {
         throw new SnmpMibException(a("+\u0004\u001elv6\u001fI%vy\u001f\u0001%ky\u001f\b.t<QIdo+\u0004\u0007+8-\n\u000b }p"));
      } else {
         this.removeRow(var1.getIndexOid());
      }
   }

   public Row getAdaptorRowByInstance(SnmpOid var1) throws SnmpValueException {
      SnmpOid var2 = this.indexFromInstance(var1);
      return this.getAdaptorRow(var2);
   }

   public Row getAdaptorRow(SnmpOid var1) throws SnmpValueException {
      synchronized(this.c) {
         return (Row)this.c.get(var1);
      }
   }

   public SnmpMibTableRow removeRow(SnmpOid var1) {
      synchronized(this.c) {
         Row var3 = (Row)this.c.get(var1);
         if (var3 == null) {
            return null;
         } else {
            SnmpMibTableRow var4 = var3.getRow();
            this.c.remove(var1);
            var3.a((SnmpMibTableRow)null);
            if (this.g) {
               if (!this.a) {
                  return var4;
               }

               this.b(var1);
               if (!e) {
                  return var4;
               }
            }

            this.b(var1);
            return var4;
         }
      }
   }

   private SnmpMibTableRow b(SnmpOid var1) {
      SnmpMibTableRow var2 = super.removeRow(var1);
      if (var2 != null) {
         Object var3 = var2.getUserObject();
         var2.setUserObject((Object)null);
      }

      return var2;
   }

   private SnmpOid a(Row var1, boolean var2) throws SnmpMibException, SnmpValueException {
      SnmpOid var3 = new SnmpOid();
      SnmpObjectInfo[] var4 = this.getTableInfo().getIndexes();
      if (var4 == null) {
         throw new SnmpMibException(a("7\u0004I8y;\u0007\fl|<\r"));
      } else {
         int var5 = 0;

         while(true) {
            if (var5 < var4.length) {
               boolean var6 = this.getTableInfo().isImplied() && var5 == var4.length - 1;
               SnmpValue var7 = var1.getValue(var4[var5].getOid());
               if (var7 == null) {
                  throw new SnmpValueException(a("7\u0004I:y5\u001e\fl~6\u0019S") + var4[var5].getOid());
               }

               if (var2 && !SnmpValue.validate(var4[var5].getType(), var7)) {
                  throw new SnmpValueException(a("0\u0005\u001f-t0\u000fI:y5\u001e\fl~6\u0019I8a)\u000eI") + SnmpValue.typeToString(var4[var5].getType()) + a("cK") + var7);
               }

               var7.appendIndexOid(var3, var6);
               ++var5;
               if (!e) {
                  continue;
               }
            }

            return var3;
         }
      }
   }

   public void clearAllNodes() {
      synchronized(this.getMib()) {
         this.a = false;
         this.g = true;
         this.removeAllRows();
      }
   }

   public void prepareForAccess() {
      boolean var8 = e;
      if (d.isDebugEnabled()) {
         d.debug(a(")\u0019\f<y+\u000e/#j\u0018\b\n)k*QI") + this.getOid());
      }

      synchronized(this.getMib()) {
         if (this.g && !this.a) {
            synchronized(this.c) {
               if (d.isDebugEnabled()) {
                  d.debug(a("yKIa5yKI%v8\b\u001d%n<KDly=\u000f\u0000\"\u007fy\n\u0005 8+\u0004\u001e?"));
               }

               Iterator var3 = this.c.values().iterator();

               while(var3.hasNext()) {
                  Row var4 = (Row)var3.next();

                  label44: {
                     try {
                        this.a(var4);
                     } catch (Exception var9) {
                        d.error(a("<\u0019\u001b#jy\u0002\u0007lt8\u0011\u0010ly=\u000f\u0000\"\u007fy\u0004\u000flj6\u001cSl") + var4, var9);
                        break label44;
                     }

                     if (var8) {
                        break;
                     }
                  }

                  if (var8) {
                     break;
                  }
               }
            }

            d.debug(a("yKIa5yKI-{-\u0002\u001f-l0\u0005\u000ell8\t\u0005)8tK\u001d-z5\u000eI\"w.K\b/l0\u001d\f"));
            this.a = true;
         }

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
               var10003 = 89;
               break;
            case 1:
               var10003 = 107;
               break;
            case 2:
               var10003 = 105;
               break;
            case 3:
               var10003 = 76;
               break;
            default:
               var10003 = 24;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class AdaptorMibNode extends SnmpMibLeaf {
      private Row a;

      public AdaptorMibNode(SnmpMibTable var2, SnmpOid var3, SnmpOid var4) throws SnmpMibException, SnmpValueException {
         super(var3, var4);
         this._table = var2;
         if (SnmpMibTableAdaptor.d.isDebugEnabled()) {
            SnmpMibTableAdaptor.d.debug(a("\\+\u0014,qV7\u0016mD[8\u00019jM\u0014\u0018/KP=\u0014\u0016") + var2.getOid() + a("bcQ") + var3 + "/" + var4);
         }

      }

      public SnmpValue getValue() {
         if (this.a != null) {
            try {
               return this.a.getValue(this.getClassOid());
            } catch (Exception var2) {
               SnmpMibTableAdaptor.d.error(a("\u001ft\\m`M+\u001e?%V7Q*`K-\u0018#b\u001f:\u001e!pR7Q;dS,\u0014mcP+Qj") + this.getClassOid() + a("\u0018S"), var2);
               return null;
            }
         } else {
            return null;
         }
      }

      public void setValue(SnmpValue var1) throws SnmpValueException {
         if (this.a != null) {
            synchronized(this) {
               this._savedValue = this.getValue();
               this.a.setValue(this.getClassOid(), var1);
            }

            if (!SnmpMibTableAdaptor.e) {
               return;
            }
         }

         throw new SnmpValueException(a("Q6Q;dS,\u0014"));
      }

      public boolean unsetValue() {
         synchronized(this) {
            if (this._savedValue != null) {
               try {
                  this.setValue(this._savedValue);
               } catch (Exception var4) {
                  SnmpMibTableAdaptor.d.error(a("\\8\u001f#jKy\u0004#(L<\u0005ms^5\u0004(%Y6\u0003w") + this.getClassOid(), var4);
               }
            }

            this._savedValue = null;
            return true;
         }
      }

      public void setRow(Row var1) {
         this.a = var1;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 63;
                  break;
               case 1:
                  var10003 = 89;
                  break;
               case 2:
                  var10003 = 113;
                  break;
               case 3:
                  var10003 = 77;
                  break;
               default:
                  var10003 = 5;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class AdaptorMibNodeFactory implements SnmpMibLeafFactory {
      private AdaptorMibNodeFactory() {
      }

      public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpMibException, SnmpValueException {
         return SnmpMibTableAdaptor.this.new AdaptorMibNode(var1, var2, var3);
      }

      // $FF: synthetic method
      AdaptorMibNodeFactory(Object var2) {
         this();
      }
   }

   public abstract static class Row {
      private SnmpMibTableRow a = null;
      private SnmpOid b = null;

      public Row() {
         if (SnmpMibTableAdaptor.d.isDebugEnabled()) {
            SnmpMibTableAdaptor.d.debug(a("2zC5\u001cfp_7]@vFxT"));
         }

      }

      public int getRowStatus() {
         return 1;
      }

      public boolean isAvailableForContextName(String var1) {
         return true;
      }

      public abstract SnmpValue getValue(SnmpOid var1) throws SnmpValueException;

      public abstract void setValue(SnmpOid var1, SnmpValue var2) throws SnmpValueException;

      public SnmpMibTableRow getRow() {
         return this.a;
      }

      void a(SnmpMibTableRow var1) {
         this.a = var1;
      }

      public SnmpOid getIndexOid() {
         return this.b;
      }

      void a(SnmpOid var1) {
         this.b = var1;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 18;
                  break;
               case 1:
                  var10003 = 25;
                  break;
               case 2:
                  var10003 = 49;
                  break;
               case 3:
                  var10003 = 80;
                  break;
               default:
                  var10003 = 125;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
