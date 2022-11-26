package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpAgentCapabilitiesInfo extends SnmpMibInfo {
   static final long serialVersionUID = -7199150535983852419L;
   private String _productRelease = null;
   private String _reference = null;
   private SnmpSupportedModule[] _supportedModules = null;
   private static final String _ident = "$Id: SnmpAgentCapabilitiesInfo.java,v 1.1 2008/06/03 15:10:23 sking Exp $";

   public SnmpAgentCapabilitiesInfo(SnmpSupportedModule[] var1) {
      this._supportedModules = var1;
   }

   public void setProductRelease(String var1) {
      this._productRelease = var1;
   }

   public String getProductRelease() {
      return this._productRelease;
   }

   public void setReference(String var1) {
      this._reference = var1;
   }

   public String getReference() {
      return this._reference;
   }

   public SnmpSupportedModule[] getSupports() {
      return this._supportedModules;
   }

   public void toString(StringBuffer var1) {
      boolean var3 = SnmpOidInfo.a;
      var1.append(a("X\u001bD\u007fjz\u001dQp|P\u0010Hew\\\u000f\u001cjpX\u0011D,"));
      var1.append(this.getName());
      var1.append(a("\u0015\fS~zL\u001fUC{U\u0019@b{\u0004")).append(this.getProductRelease());
      var1.append(a("\u0015\u0018Db}K\u0015QewV\u0012\u001c")).append(this.getDescription());
      var1.append(a("\u0015\u000eDw{K\u0019Or{\u0004")).append(this.getReference());
      var1.append(a("\u0015\u000fUpjL\u000f\u001c")).append(this.getStatus());
      var1.append(a("\u0015\u000fTanV\u000eUtzt\u0013Edr\\\u000f\u001cj"));
      int var2 = 0;

      while(true) {
         if (var2 < this._supportedModules.length) {
            var1.append("").append(this._supportedModules[var2]);
            ++var2;
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         var1.append("}");
         var1.append('}');
         break;
      }

   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this.getName()).append((Object)a("\u0019=fTPmQbPNx>h]Wm5dB>\u0004\\"));
      var1.append((Object)this.getOid().toNumericString());
      var1.pushIndent();
      var1.append((Object)a("3,s^Zl?u<L|0dPM|\\\u001c1")).append((Object)this.getProductRelease());
      var1.append((Object)a("3/uPJl/\u00011>\u0019\\\u00011>\u0019\\\u001c1")).append((Object)this.getStatus());
      var1.append((Object)a("38dB]k5qEWv2\u00011>\u0019\\\u001c1")).append((Object)this.getDescription());
      var1.append((Object)a("3.dW[k9oR[\u0019\\\u00011>\u0019\\\u001c1")).append((Object)this.getReference());
      var1.pushIndent();
      var1.popIndent();
      var1.popIndent();
   }

   void c() {
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 57;
               break;
            case 1:
               var10003 = 124;
               break;
            case 2:
               var10003 = 33;
               break;
            case 3:
               var10003 = 17;
               break;
            default:
               var10003 = 30;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
