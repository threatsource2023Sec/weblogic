package monfox.toolkit.snmp.agent.ext.acm;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.agent.SnmpAccessControlModel;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageParameters;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;

public class AppAcm extends SnmpAccessControlModel {
   public static final int VERSION_1 = 0;
   public static final int VERSION_2 = 1;
   public static final int VERSION_3 = 3;
   public static final int LEVEL_noAuthNoPriv = 0;
   public static final int LEVEL_authNoPriv = 1;
   public static final int LEVEL_authPriv = 3;
   private String a;
   private int[] b;
   private AppAccessPolicy c;
   private AccessController d;
   public static boolean e;

   public AppAcm(AccessController var1) {
      this((int[])null, var1);
   }

   public AppAcm(int[] var1, AccessController var2) {
      boolean var3 = e;
      super();
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = null;
      this.a = a("\u0002\n&gm.");
      this.b = var1;
      this.d = var2;
      this.c = new AppAccessPolicy();
      if (var3) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public String getModelName() {
      return this.a;
   }

   public void setAccessController(AccessController var1) {
      this.d = var1;
   }

   public AccessController getAccessController() {
      return this.d;
   }

   public boolean supportsVersion(int var1) {
      boolean var3 = e;
      if (this.b == null) {
         return true;
      } else {
         int var2 = 0;

         int var10000;
         while(true) {
            if (var2 < this.b.length) {
               var10000 = this.b[var2];
               if (var3) {
                  break;
               }

               if (var10000 == var1) {
                  return true;
               }

               ++var2;
               if (!var3) {
                  continue;
               }
            }

            var10000 = 0;
            break;
         }

         return (boolean)var10000;
      }
   }

   public SnmpAccessPolicy getAccessPolicy(SnmpPendingIndication var1) {
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
               var10003 = 67;
               break;
            case 1:
               var10003 = 122;
               break;
            case 2:
               var10003 = 86;
               break;
            case 3:
               var10003 = 38;
               break;
            default:
               var10003 = 14;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class AppAccessPolicy extends SnmpAccessPolicy {
      AppAccessPolicy() {
         boolean var2 = AppAcm.e;
         super("", true);
         if (SnmpException.b) {
            AppAcm.e = !var2;
         }

      }

      public boolean checkAccess(SnmpPendingIndication var1) {
         if (AppAcm.this.d == null) {
            return true;
         } else {
            SnmpMessage var2 = var1.getRequest();
            SnmpSecurityParameters var3 = var2.getSecurityParameters();
            SnmpMessageParameters var4 = var2.getMessageParameters();
            String var5 = new String(var3.getSecurityName());
            int var6 = 0;
            if (var4 instanceof V3SnmpMessageParameters) {
               V3SnmpMessageParameters var7 = (V3SnmpMessageParameters)var4;
               var6 = var7.getFlags() & 3;
            }

            boolean var10 = false;
            SnmpPDU var8 = var2.getData();
            if (var8 != null && var8.getType() == 163) {
               var10 = true;
            }

            String var9 = null;
            if (var2.getContext() != null) {
               var9 = var2.getContext().getContextName();
            }

            return AppAcm.this.d.checkAccess(var1.getAgent(), var5, var2.getVersion(), var6, var10, var9);
         }
      }

      public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpOid var3) {
         if (AppAcm.this.d == null) {
            return true;
         } else {
            SnmpMessage var4 = var1.getRequest();
            SnmpSecurityParameters var5 = var4.getSecurityParameters();
            SnmpMessageParameters var6 = var4.getMessageParameters();
            String var7 = new String(var5.getSecurityName());
            int var8 = 0;
            if (var6 instanceof V3SnmpMessageParameters) {
               V3SnmpMessageParameters var9 = (V3SnmpMessageParameters)var6;
               var8 = var9.getFlags() & 3;
            }

            boolean var15 = false;
            SnmpPDU var10 = var4.getData();
            if (var10 != null && var10.getType() == 163) {
               var15 = true;
            }

            SnmpOidInfo var11 = var3.getOidInfo();
            SnmpOid var12 = var3;
            String var13 = null;
            if (var11 != null) {
               var12 = var11.getOid();
               var13 = var11.getName();
            }

            String var14 = null;
            if (var4.getContext() != null) {
               var14 = var4.getContext().getContextName();
            }

            return AppAcm.this.d.checkAccess(var1.getAgent(), var7, var4.getVersion(), var8, var15, var14, var12, var13, var3);
         }
      }
   }

   public interface AccessController {
      boolean checkAccess(SnmpAgent var1, String var2, int var3, int var4, boolean var5, String var6);

      boolean checkAccess(SnmpAgent var1, String var2, int var3, int var4, boolean var5, String var6, SnmpOid var7, String var8, SnmpOid var9);
   }
}
