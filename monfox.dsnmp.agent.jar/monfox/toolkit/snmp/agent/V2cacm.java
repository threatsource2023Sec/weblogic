package monfox.toolkit.snmp.agent;

import java.util.HashMap;

public class V2cacm extends SnmpAccessControlModel {
   private SnmpAgent a;
   private HashMap b;
   private SnmpAccessPolicy c;
   private int d = 0;
   private int e = 0;

   public V2cacm(SnmpAgent var1) {
      this.a = var1;
   }

   public String getModelName() {
      return a("[+2K=nz4\u0018\u000f-Z>\u0005\b\u007fv=K1b}4\u0007\\%Oc\b=NTx");
   }

   public boolean supportsVersion(int var1) {
      return var1 == 0 || var1 == 1;
   }

   public SnmpAccessPolicy getAccessPolicy(SnmpPendingIndication var1) {
      String var2 = new String(var1.getRequest().getData().getCommunity());
      SnmpAccessPolicy var3 = this.getAccessPolicy(var2);
      if (var3 == null && var2 != null && var1.getCommunity() != null && !var2.equals(var1.getCommunity())) {
         var3 = this.getAccessPolicy(var1.getCommunity());
      }

      if (var3 == null) {
         ++this.d;
         return null;
      } else if (var3.getAccessMode()) {
         return var3;
      } else if (var1.getRequestType() == 163) {
         ++this.e;
         return null;
      } else {
         return var3;
      }
   }

   public int getSnmpInBadCommunityNames() {
      return this.d;
   }

   public int getSnmpInBadCommunityUses() {
      return this.e;
   }

   public SnmpAccessPolicy addAccessPolicy(String var1, boolean var2) {
      return this.addAccessPolicy(var1, var2, (SnmpMibView)null);
   }

   public SnmpAccessPolicy addAccessPolicy(String var1, boolean var2, SnmpMibView var3) {
      if (var3 == null) {
         var3 = new SnmpMibView();
      }

      SnmpAccessPolicy var4 = new SnmpAccessPolicy(var1, var2, var3);
      this.addAccessPolicy(var4);
      return var4;
   }

   public void addAccessPolicy(SnmpAccessPolicy var1) {
      if (this.b == null) {
         this.b = new HashMap();
      }

      this.b.put(var1.getCommunity(), var1);
   }

   public SnmpAccessPolicy getAccessPolicy(String var1) {
      if (this.b == null) {
         return this.c;
      } else {
         SnmpAccessPolicy var2 = (SnmpAccessPolicy)this.b.get(var1);
         return var2 == null ? this.c : var2;
      }
   }

   public SnmpAccessPolicy removeAccessPolicy(String var1) {
      return this.b == null ? null : (SnmpAccessPolicy)this.b.remove(var1);
   }

   public void setDefaultAccessPolicy(SnmpAccessPolicy var1) {
      this.c = var1;
   }

   public SnmpAccessPolicy getDefaultAccessPolicy() {
      return this.c;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 13;
               break;
            case 1:
               var10003 = 25;
               break;
            case 2:
               var10003 = 81;
               break;
            case 3:
               var10003 = 107;
               break;
            default:
               var10003 = 124;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
