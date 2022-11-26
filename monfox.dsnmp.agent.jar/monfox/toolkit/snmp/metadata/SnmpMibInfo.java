package monfox.toolkit.snmp.metadata;

public abstract class SnmpMibInfo extends SnmpOidInfo {
   static final long serialVersionUID = 4584425539211228749L;
   private boolean _removed = false;
   private String _description = null;
   private String _status = a("twG,\u0006yv");

   public void setDescription(String var1) {
      this._description = var1;
   }

   public String getDescription() {
      return this._description;
   }

   public void setStatus(String var1) {
      this._status = var1;
   }

   public String getStatus() {
      return this._status;
   }

   boolean a() {
      return this._removed;
   }

   void b() {
      this._removed = true;
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
               var10003 = 23;
               break;
            case 1:
               var10003 = 2;
               break;
            case 2:
               var10003 = 53;
               break;
            case 3:
               var10003 = 94;
               break;
            default:
               var10003 = 99;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
