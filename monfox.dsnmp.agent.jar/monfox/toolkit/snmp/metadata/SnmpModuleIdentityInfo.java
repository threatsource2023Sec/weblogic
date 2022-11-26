package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpModuleIdentityInfo extends SnmpOidInfo {
   static final long serialVersionUID = -1862339013365087150L;
   private String _lastUpdated = null;
   private String _contactInfo = null;
   private String _organization = null;
   private String _description = null;
   private static final String _ident = "$Id: SnmpModuleIdentityInfo.java,v 1.3 2001/10/29 15:44:29 sking Exp $";

   public SnmpModuleIdentityInfo(String var1, String var2, String var3, String var4) {
      this._lastUpdated = var1;
      this._organization = var2;
      this._contactInfo = var3;
      this._description = var4;
   }

   public String getOrganization() {
      return this._organization;
   }

   public String getDescription() {
      return this._description;
   }

   public String getLastUpdated() {
      return this._lastUpdated;
   }

   public String getContactInfo() {
      return this._contactInfo;
   }

   public void toString(StringBuffer var1) {
      var1.append(a("lHE\r\u0018dnE\u001d\u001auNU\u0001IzI@\u0015\u0011<"));
      var1.append(this.getName());
      var1.append(a("-CD\u000b\u0017sNQ\f\u001dnI\u001c"));
      var1.append(this.getDescription());
      var1.append(a("-HS\u001f\u0015oN[\u0019\u0000hHOE"));
      var1.append(this.getOrganization());
      var1.append('}');
   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this.getName()).append((Object)a("!jn<!Mb\f10Diu1 X\u0007\u001cX"));
      var1.append((Object)this.getOid().toNumericString());
      var1.pushIndent();
      var1.append((Object)a("nUF\u0019\u001ah]@\f\u001dnI\u0001E")).append((Object)this.getOrganization()).append((Object)"\n");
      var1.append((Object)a("eBR\u001b\u0006hWU\u0011\u001bo\u0007\u0001E")).append((Object)this.getDescription()).append((Object)"\n");
      var1.popIndent();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 1;
               break;
            case 1:
               var10003 = 39;
               break;
            case 2:
               var10003 = 33;
               break;
            case 3:
               var10003 = 120;
               break;
            default:
               var10003 = 116;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
