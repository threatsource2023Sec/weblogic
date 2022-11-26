package monfox.toolkit.snmp.metadata;

import java.io.Serializable;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.util.OidTree;
import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpOidInfo implements Serializable {
   static final long serialVersionUID = 5031776669661814470L;
   private SnmpMetadata _metadata = null;
   private SnmpModule _module = null;
   private SnmpOid _oid = null;
   OidTree.Node node = null;
   private static final String _ident = "$Id: SnmpOidInfo.java,v 1.15 2007/06/21 00:42:40 sking Exp $";
   public static boolean a;

   public String getName() {
      return this.node == null ? a("&ic\u0015") : this.node.name;
   }

   public int getLevel() {
      return this.node == null ? -1 : this.node.level;
   }

   public SnmpOid getOid() {
      if (this._oid == null && this.node != null) {
         long[] var1 = this.node.getLongArray();
         this._oid = new SnmpOid(var1);
         this._oid.setMetadata(this._metadata);
      }

      return this._oid;
   }

   public SnmpOidInfo getParent() {
      if (this.node != null && this.node.parent != null) {
         if (this.node.parent.info == null) {
            this.node.parent.info = new SnmpOidInfo();
         }

         return (SnmpOidInfo)this.node.parent.info;
      } else {
         return null;
      }
   }

   public SnmpOidInfo[] getChildren() {
      boolean var5 = a;
      if (this.node == null) {
         return new SnmpOidInfo[0];
      } else {
         Vector var1 = new Vector();
         OidTree.Node var2 = this.node.childlist;

         while(var2 != null) {
            if (var2.info == null) {
               SnmpOidInfo var3 = new SnmpOidInfo();
               var2.info = var3;
            }

            var1.addElement(var2.info);
            var2 = var2.next;
            if (var5) {
               break;
            }
         }

         SnmpOidInfo[] var6 = new SnmpOidInfo[var1.size()];
         int var4 = 0;

         SnmpOidInfo[] var10000;
         while(true) {
            if (var4 < var1.size()) {
               var10000 = var6;
               if (var5) {
                  break;
               }

               var6[var4] = (SnmpOidInfo)var1.elementAt(var4);
               ++var4;
               if (!var5) {
                  continue;
               }
            }

            var10000 = var6;
            break;
         }

         return var10000;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      var1.append(a("'ukDU&}b\u001c\u0013"));
      var1.append(this.getName());
      var1.append('}');
   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this.getName()).append((Object)a("\u0007UKY\u0013h"));
      var1.append((Object)this.getOid().toNumericString());
      var1.append((Object)"\n");
   }

   static void a(StringBuffer var0, SnmpOidInfo[] var1) {
      boolean var3 = a;
      var0.append('{');
      int var2 = 0;

      while(true) {
         if (var2 < var1.length) {
            if (var3) {
               break;
            }

            if (var2 > 0) {
               var0.append(',');
            }

            var0.append(var1[var2].getName());
            ++var2;
            if (!var3) {
               continue;
            }
         }

         var0.append('}');
         break;
      }

   }

   static void a(TextBuffer var0, SnmpOidInfo[] var1) {
      int var2 = 0;

      while(var2 < var1.length) {
         var0.append((Object)var1[var2].getName());
         var0.append('\n');
         ++var2;
         if (a) {
            break;
         }
      }

   }

   public void setMetadata(SnmpMetadata var1) {
      this._metadata = var1;
      if (this._oid != null) {
         this._oid.setMetadata(var1);
      }

   }

   public SnmpMetadata getMetadata() {
      return this._metadata;
   }

   public SnmpModule getModule() {
      return this._module;
   }

   public void setModule(SnmpModule var1) {
      this._module = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 72;
               break;
            case 1:
               var10003 = 28;
               break;
            case 2:
               var10003 = 15;
               break;
            case 3:
               var10003 = 121;
               break;
            default:
               var10003 = 46;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
