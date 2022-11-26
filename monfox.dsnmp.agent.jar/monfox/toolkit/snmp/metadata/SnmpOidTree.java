package monfox.toolkit.snmp.metadata;

import java.io.Serializable;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.util.OidTree;

public class SnmpOidTree implements Serializable {
   static final long serialVersionUID = -6580357022537048980L;
   OidTree _oidTree = null;

   public SnmpOidTree() {
      this._oidTree = new OidTree();
      this.a();
   }

   public SnmpOidTree(int var1, int var2) {
      this._oidTree = new OidTree(var1, var2);
      this.a();
   }

   private void a() {
      try {
         this.add(new SnmpOid("0"), a("!W\u001d\u0001k"), new SnmpOidInfo());
         this.add(new SnmpOid("1"), a("+G\u001b"), new SnmpOidInfo());
         this.add(new SnmpOid("2"), a("([\u001d\u001bko]\u0007\u001a2!W\u001d\u0001k"), new SnmpOidInfo());
      } catch (SnmpValueException var2) {
      }

   }

   public SnmpOidInfo resolveBaseOid(String var1) throws SnmpValueException {
      OidTree.Node var2 = this._oidTree.getBase(var1);
      if (var2 == null) {
         throw new SnmpValueException(a("\f[T&j!\\T:v&\u000e") + var1);
      } else {
         return this.a(var2);
      }
   }

   public SnmpOidInfo resolveBaseOid(SnmpOid var1) throws SnmpValueException {
      OidTree.Node var2 = this._oidTree.getBase(var1.toLongArray(false), var1.getLength());
      if (var2 == null) {
         throw new SnmpValueException(a("\f[T&j!\\T:v&\u000e") + var1.toNumericString());
      } else {
         return this.a(var2);
      }
   }

   public SnmpOidInfo resolveOid(String var1) throws SnmpValueException {
      OidTree.Node var2 = this._oidTree.get(var1);
      if (var2 == null) {
         throw new SnmpValueException(a("\f[T&j!\\T:v&\u000e") + var1);
      } else {
         return this.a(var2);
      }
   }

   public SnmpOidInfo resolveOid(SnmpOid var1) throws SnmpValueException {
      OidTree.Node var2 = this._oidTree.get(var1.toLongArray(false), var1.getLength());
      if (var2 == null) {
         throw new SnmpValueException(a("\f[T&j!\\T:v&\u000e") + var1.toNumericString());
      } else {
         return this.a(var2);
      }
   }

   public SnmpOidInfo resolveName(String var1) throws SnmpValueException {
      OidTree.Node var2 = this._oidTree.findByName(var1);
      if (var2 == null) {
         throw new SnmpValueException(a("\f[T&j!\\T:v&\u000e") + var1);
      } else {
         return this.a(var2);
      }
   }

   private SnmpOidInfo a(OidTree.Node var1) throws SnmpValueException {
      if (var1.info == null) {
         if (var1.name == null) {
            throw new SnmpValueException(var1.toString());
         }

         SnmpOidInfo var2 = new SnmpOidInfo();
         var2.node = var1;
         var1.info = var2;
         if (SnmpOidInfo.a) {
            throw new SnmpValueException(var1.toString());
         }
      }

      return (SnmpOidInfo)var1.info;
   }

   public void add(SnmpOid var1, String var2, SnmpOidInfo var3) {
      OidTree.Node var4 = this._oidTree.add(var1.toLongArray(false), var1.getLength(), var2, var3);
      if (var3 != null) {
         var3.node = var4;
      }

   }

   public void add(String var1, String var2, SnmpOidInfo var3) {
      OidTree.Node var4 = this._oidTree.add((String)var1, var2, var3);
      if (var3 != null) {
         var3.node = var4;
      }

   }

   public void add(String var1, SnmpOid var2, String var3, SnmpOidInfo var4) {
      OidTree.Node var5 = this._oidTree.add(var2.toLongArray(false), var2.getLength(), var1, var3, var4);
      if (var4 != null) {
         var4.node = var5;
      }

   }

   public void add(String var1, String var2, String var3, SnmpOidInfo var4) {
      OidTree.Node var5 = this._oidTree.add(var1, var2, var3, var4);
      if (var4 != null) {
         var4.node = var5;
      }

   }

   public void remove(SnmpOidInfo var1) {
      if (var1 != null) {
         SnmpOid var2 = var1.getOid();
         OidTree.Node var3 = this._oidTree.get(var2.toLongArray());
         if (var3 != null) {
            this._oidTree.removeByName(var1.getName());
            var3.info = null;
            var3.name = null;
            var3.remove(false);
         }

      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      a var2 = new a(var1);
      this._oidTree.getRoot().walk(var2);
      return var1.toString();
   }

   public void optimize(int var1) {
      this._oidTree.optimize(var1);
   }

   public void walk(Walker var1) {
      this.a(this._oidTree.getRoot(), var1);
   }

   public void walk(SnmpOid var1, Walker var2) throws SnmpValueException {
      OidTree.Node var3 = this._oidTree.get(var1.toLongArray(false), var1.getLength());
      if (var3 == null) {
         throw new SnmpValueException(a("\f[T&j!\\T:v&\u000e") + var1.toNumericString());
      } else {
         this.a(var3, var2);
      }
   }

   private void a(OidTree.Node var1, OidTree.Walker var2) {
      var1.walk(var2);
   }

   public SnmpOid getNextObjectOid(SnmpOid var1, boolean var2) {
      boolean var6 = SnmpOidInfo.a;
      OidTree.Node var3 = this._oidTree.getNext(var1.toLongArray(false), var1.getLength());

      while(var3 != null) {
         try {
            label55: {
               SnmpOidInfo var4 = this.a(var3);
               if (var4 instanceof SnmpTableInfo) {
                  return var4.getOid();
               }

               if (var4 instanceof SnmpObjectInfo) {
                  SnmpObjectInfo var5 = (SnmpObjectInfo)var4;
                  if (!var5.isColumnar()) {
                     return var4.getOid();
                  }

                  if (!var2) {
                     return var4.getOid();
                  }

                  if (!var6) {
                     break label55;
                  }
               }

               if (var4 instanceof SnmpTableEntryInfo && !var2) {
                  return var4.getOid();
               }
            }
         } catch (SnmpValueException var7) {
         }

         var3 = var3.nextNode();
         if (var6) {
            break;
         }
      }

      return null;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 66;
               break;
            case 1:
               var10003 = 52;
               break;
            case 2:
               var10003 = 116;
               break;
            case 3:
               var10003 = 117;
               break;
            default:
               var10003 = 31;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public abstract static class Walker implements OidTree.Walker {
      public abstract void processOidInfo(OidTree.Node var1, SnmpOidInfo var2);

      public abstract void processEmpty(OidTree.Node var1, String var2);

      public boolean process(OidTree.Node var1) {
         if (var1.level <= 0) {
            return true;
         } else {
            if (var1.info == null) {
               this.processEmpty(var1, var1.name);
               if (!SnmpOidInfo.a) {
                  return true;
               }
            }

            this.processOidInfo(var1, (SnmpOidInfo)var1.info);
            return true;
         }
      }
   }
}
