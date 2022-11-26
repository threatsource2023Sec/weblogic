package monfox.toolkit.snmp.metadata;

import java.io.Serializable;
import java.util.Hashtable;
import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpModule implements Serializable {
   static final long serialVersionUID = 6237796037342090327L;
   private String _name = null;
   SnmpModuleIdentityInfo _moduleIdentity = null;
   SnmpTypeInfo[] _types = new SnmpTypeInfo[0];
   Hashtable _typeMap = new Hashtable(89);
   SnmpOidInfo[] _elements = new SnmpOidInfo[0];
   Hashtable _elementMap = new Hashtable(89);
   String[] _requiredModules = new String[0];

   public SnmpModule(String var1) {
      this._name = var1;
   }

   public String getName() {
      return this._name;
   }

   public SnmpOidInfo[] getOidInfo() {
      return this._elements;
   }

   public SnmpTypeInfo[] getTypes() {
      return this._types;
   }

   public SnmpOidInfo getOidInfo(String var1) {
      return (SnmpOidInfo)this._elementMap.get(var1);
   }

   public SnmpTypeInfo getTypeInfo(String var1) {
      return (SnmpTypeInfo)this._typeMap.get(var1);
   }

   public String[] getRequiredModules() {
      return this._requiredModules;
   }

   public SnmpModuleIdentityInfo getIdentity() {
      return this._moduleIdentity;
   }

   public String toString() {
      TextBuffer var1 = new TextBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(TextBuffer var1) {
      boolean var3 = SnmpOidInfo.a;
      var1.append((Object)a("5A!W\u0000=4E")).append((Object)this._name);
      var1.append((Object)a("rL E\u00056"));
      var1.pushIndent();
      if (this._moduleIdentity != null) {
         this._moduleIdentity.toString(var1);
      }

      int var2 = 0;

      while(true) {
         if (var2 < this._types.length) {
            this._types[var2].toString(var1);
            ++var2;
            if (!var3 || !var3) {
               continue;
            }
            break;
         }

         var2 = 0;
         break;
      }

      while(true) {
         if (var2 < this._elements.length) {
            this._elements[var2].toString(var1);
            ++var2;
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         var1.popIndent();
         var1.append((Object)a("=@!\"aU.(M\b-B 8l")).append((Object)this._name).append((Object)a("X#H\b"));
         break;
      }

   }

   void a(String var1, SnmpOidInfo var2) {
      boolean var6 = SnmpOidInfo.a;
      var2.setModule((SnmpModule)null);
      if (var2 instanceof SnmpModuleIdentityInfo) {
         this._moduleIdentity = null;
         if (!var6) {
            return;
         }
      }

      this._elementMap.remove(var1);
      int var3 = -1;
      int var4 = this._elements.length;
      int var5 = 0;

      SnmpOidInfo[] var10000;
      while(true) {
         if (var5 < var4) {
            label39: {
               var10000 = this._elements;
               if (var6) {
                  break;
               }

               if (var10000[var5] == var2) {
                  var3 = var5;
                  if (!var6) {
                     break label39;
                  }
               }

               ++var5;
               if (!var6) {
                  continue;
               }
            }
         }

         if (var3 < 0) {
            return;
         }

         var10000 = new SnmpOidInfo[var4 - 1];
         break;
      }

      SnmpOidInfo[] var7 = var10000;
      System.arraycopy(this._elements, 0, var7, 0, var3);
      System.arraycopy(this._elements, var3 + 1, var7, var3, var4 - var3 - 1);
      this._elements = var7;
   }

   void b(String var1, SnmpOidInfo var2) {
      var2.setModule(this);
      if (var2 instanceof SnmpModuleIdentityInfo) {
         this._moduleIdentity = (SnmpModuleIdentityInfo)var2;
         if (!SnmpOidInfo.a) {
            return;
         }
      }

      this._elementMap.put(var1, var2);
      int var3 = this._elements.length;
      SnmpOidInfo[] var4 = new SnmpOidInfo[var3 + 1];
      System.arraycopy(this._elements, 0, var4, 0, var3);
      var4[var3] = var2;
      this._elements = var4;
   }

   void a(String var1, SnmpTypeInfo var2) {
      var2.a(this);
      this._typeMap.put(var1, var2);
      int var3 = this._types.length;
      SnmpTypeInfo[] var4 = new SnmpTypeInfo[var3 + 1];
      System.arraycopy(this._types, 0, var4, 0, var3);
      var4[var3] = var2;
      this._types = var4;
   }

   public void addRequiredModule(String var1) {
      int var2 = this._requiredModules.length;
      String[] var3 = new String[var2 + 1];
      System.arraycopy(this._requiredModules, 0, var3, 0, var2);
      var3[var2] = var1;
      this._requiredModules = var3;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 120;
               break;
            case 1:
               var10003 = 14;
               break;
            case 2:
               var10003 = 101;
               break;
            case 3:
               var10003 = 2;
               break;
            default:
               var10003 = 76;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
