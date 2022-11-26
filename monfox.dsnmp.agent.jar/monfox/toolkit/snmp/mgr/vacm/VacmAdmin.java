package monfox.toolkit.snmp.mgr.vacm;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpSession;

public class VacmAdmin {
   public static final SecModel MODEL_ANY = new SecModel(0);
   public static final SecModel MODEL_V1 = new SecModel(1);
   public static final SecModel MODEL_V2C = new SecModel(2);
   public static final SecModel MODEL_USM = new SecModel(3);
   public static final SecLevel LEVEL_NO_AUTH_NO_PRIV = new SecLevel(1);
   public static final SecLevel LEVEL_AUTH_NO_PRIV = new SecLevel(2);
   public static final SecLevel LEVEL_AUTH_PRIV = new SecLevel(3);
   public static final CtxMatch MATCH_EXACT = new CtxMatch(1);
   public static final CtxMatch MATCH_PREFIX = new CtxMatch(2);
   public static final ViewType SUBTREE_INCLUDED = new ViewType(1);
   public static final ViewType SUBTREE_EXCLUDED = new ViewType(2);
   private boolean a = true;
   private SnmpSession b = null;
   private SnmpMetadata c = null;
   private Logger d = null;
   public static boolean e;

   public VacmAdmin(SnmpSession var1) throws SnmpException {
      this.b = var1;
      this.d = Logger.getInstance(a("\u001a;59\u0002(7?:"));
      this.c = this.a(this.b.getMetadata());
      if (this.c == null) {
         this.c = this.a(SnmpFramework.getMetadata());
      }

      if (this.c == null) {
         try {
            this.c = SnmpFramework.loadMibs(a("\u001f\u0014\u001b\u0004n\u001a\u0013\u0013\u0003n\u000e\u001b\u0005\u0011\u0007a\u001b\u0015\u0019n\u0001\u0013\u0014"));
         } catch (Exception var3) {
            throw new SnmpException(a("/;8:,8z:;\"(z\u0000\u0015\u0000\u0001z\u001b\u001d\u0001"));
         }
      }

   }

   private SnmpMetadata a(SnmpMetadata var1) {
      if (var1 == null) {
         return null;
      } else {
         try {
            if (var1.getModule(a("\u001f\u0014\u001b\u0004n\u001a\u0013\u0013\u0003n\u000e\u001b\u0005\u0011\u0007a\u001b\u0015\u0019n\u0001\u0013\u0014")) != null) {
               return var1;
            }
         } catch (SnmpException var3) {
         }

         return null;
      }
   }

   public void createAccess(SnmpPeer var1, String var2, String var3, SecModel var4, SecLevel var5, CtxMatch var6, String var7, String var8, String var9) throws SnmpException {
      boolean var11 = e;
      SnmpVarBindList var10 = new SnmpVarBindList(this.c);
      var10.add(a(":;59\u0002/93'0\u001f.7 6?"), a("/(357)\u001b80\u0004#"));
      var10.add(a(":;59\u0002/93'0\u000f58 &4.\u001b57/2"), (long)var6.intValue());
      var10.add(a(":;59\u0002/93'0\u001e?70\u0015%?!\u001a\"!?"), var7 == null ? "" : var7);
      var10.add(a(":;59\u0002/93'0\u001b(? &\u001a33#\r-73"), var8 == null ? "" : var8);
      var10.add(a(":;59\u0002/93'0\u00025\"=%5\f?14\u0002;;1"), var9 == null ? "" : var9);
      var10.addInstance(a(":;59\u0002/93'0\u0018;48&"), new SnmpValue[]{new SnmpString(var2), new SnmpString(var3), new SnmpInt(var4.intValue()), new SnmpInt(var5.intValue())});
      this.b.performSet(var1, var10);
      if (var11) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public void deleteAccess(SnmpPeer var1, String var2, String var3, SecModel var4, SecLevel var5) throws SnmpException {
      SnmpVarBindList var6 = new SnmpVarBindList(this.c);
      var6.add(a(":;59\u0002/93'0\u001f.7 6?"), a("(?% 1##"));
      var6.addInstance(a(":;59\u0002/93'0\u0018;48&"), new SnmpValue[]{new SnmpString(var2), new SnmpString(var3), new SnmpInt(var4.intValue()), new SnmpInt(var5.intValue())});
      this.b.performSet(var1, var6);
   }

   public void createSec2Group(SnmpPeer var1, SecModel var2, String var3, String var4) throws SnmpException {
      SnmpVarBindList var5 = new SnmpVarBindList(this.c);
      var5.add(a(":;59\u0010)9#&*8#\u0002;\u0004>5#$\u00108;\"!0"), a("/(357)\u001b80\u0004#"));
      var5.add(a(":;59\u0004>5#$\r-73"), var4);
      var5.addInstance(a(":;59\u0010)9#&*8#\u0002;\u0004>5#$\u0017-8:1"), new SnmpValue[]{new SnmpInt(var2.intValue()), new SnmpString(var3)});
      this.b.performSet(var1, var5);
   }

   public void deleteSec2Group(SnmpPeer var1, SecModel var2, String var3) throws SnmpException {
      SnmpVarBindList var4 = new SnmpVarBindList(this.c);
      var4.add(a(":;59\u0010)9#&*8#\u0002;\u0004>5#$\u00108;\"!0"), a("(?% 1##"));
      var4.addInstance(a(":;59\u0010)9#&*8#\u0002;\u0004>5#$\u0017-8:1"), new SnmpValue[]{new SnmpInt(var2.intValue()), new SnmpString(var3)});
      this.b.performSet(var1, var4);
   }

   public void createView(SnmpPeer var1, String var2, String var3, String var4, ViewType var5) throws SnmpException {
      SnmpOid var6 = null;

      try {
         var6 = new SnmpOid(this.c, var3);
      } catch (Exception var8) {
         throw new SnmpException(a("%4 5/%>v'6..$1&l5?0yl") + var3);
      }

      this.createView(var1, var2, var6, var4, var5);
   }

   public void createView(SnmpPeer var1, String var2, SnmpOid var3, String var4, ViewType var5) throws SnmpException {
      boolean var11 = e;
      int var6 = 0;
      byte var7 = 10;

      while(true) {
         long var8 = -1L;

         try {
            SnmpVarBindList var10;
            SnmpVarBindList var10000;
            String var10001;
            long var10002;
            label47: {
               var10 = new SnmpVarBindList(this.c);
               var8 = this.a(var1);
               if (var8 > -1L) {
                  var10000 = var10;
                  var10001 = a(":;59\u0015%?!\u00073%4\u001a; 'tf");
                  var10002 = var8;
                  if (var11) {
                     break label47;
                  }

                  var10.add(var10001, var8);
               }

               var10.add(a(":;59\u0015%?!\u00001)?\u00105.%6/\u00077-.#'"), a("/(357)\u001b80\u0004#"));
               var10.add(a(":;59\u0015%?!\u00001)?\u00105.%6/\u0019\"?1"), var4);
               var10000 = var10;
               var10001 = a(":;59\u0015%?!\u00001)?\u00105.%6/\u0000:<?");
               var10002 = (long)var5.intValue();
            }

            var10000.add(var10001, var10002);
            var10.addInstance(a(":;59\u0015%?!\u00001)?\u00105.%6/\u0000\".63"), new SnmpValue[]{new SnmpString(var2), var3});
            this.b.performSet(var1, var10);
            break;
         } catch (SnmpErrorException var12) {
            if (this.d.isDebugEnabled()) {
               this.d.debug(a("awv1;/?& *#4lt") + var12);
            }

            if (var8 < 0L || var12.getErrorStatus() != 12 || var12.getErrorIndex() != 1) {
               throw var12;
            }

            this.d.debug(a(":;59\u0015%?!\u00073%4\u001a; 'z05* ?2tk-.\"1.<.l") + var6 + ")");
            if (var6 >= var7) {
               throw var12;
            }

            ++var6;
         } catch (SnmpException var13) {
            if (this.d.isDebugEnabled()) {
               this.d.debug(a("awv1;/?& *#4lt") + var13);
            }

            throw var13;
         }
      }

      if (SnmpException.b) {
         e = !var11;
      }

   }

   public void deleteView(SnmpPeer var1, String var2, String var3) throws SnmpException {
      SnmpOid var4 = null;

      try {
         var4 = new SnmpOid(this.c, var3);
      } catch (Exception var6) {
         throw new SnmpException(a("%4 5/%>v'6..$1&l5?0yl") + var3);
      }

      this.deleteView(var1, var2, var4);
   }

   public void deleteView(SnmpPeer var1, String var2, SnmpOid var3) throws SnmpException {
      int var4 = 0;
      byte var5 = 10;

      while(true) {
         long var6 = -1L;

         try {
            SnmpVarBindList var10000;
            String var10001;
            SnmpVarBindList var8;
            label38: {
               var8 = new SnmpVarBindList(this.c);
               var6 = this.a(var1);
               if (var6 > -1L) {
                  var10000 = var8;
                  var10001 = a(":;59\u0015%?!\u00073%4\u001a; 'tf");
                  if (e) {
                     break label38;
                  }

                  var8.add(var10001, var6);
               }

               var8.add(a(":;59\u0015%?!\u00001)?\u00105.%6/\u00077-.#'"), a("(?% 1##"));
               var10000 = var8;
               var10001 = a(":;59\u0015%?!\u00001)?\u00105.%6/\u0000\".63");
            }

            var10000.addInstance(var10001, new SnmpValue[]{new SnmpString(var2), var3});
            this.b.performSet(var1, var8);
            return;
         } catch (SnmpErrorException var9) {
            if (this.d.isDebugEnabled()) {
               this.d.debug(a("awv1;/?& *#4lt") + var9);
            }

            if (var6 < 0L || var9.getErrorStatus() != 12 || var9.getErrorIndex() != 1) {
               throw var9;
            }

            this.d.debug(a(":;59\u0015%?!\u00073%4\u001a; 'z05* ?2tk-.\"1.<.l") + var4 + ")");
            if (var4 >= var5) {
               throw var9;
            }

            ++var4;
         } catch (SnmpException var10) {
            if (this.d.isDebugEnabled()) {
               this.d.debug(a("awv1;/?& *#4lt") + var10);
            }

            throw var10;
         }
      }
   }

   private long a(SnmpPeer var1) {
      if (!this.a) {
         return -1L;
      } else {
         long var2 = -1L;

         try {
            SnmpVarBindList var4 = new SnmpVarBindList(this.c);
            var4.add(a(":;59\u0015%?!\u00073%41\u0018,/1xd"));
            SnmpVarBindList var5 = this.b.performGet(var1, var4);
            return var5.get(0).getValue().longValue();
         } catch (SnmpException var6) {
            return -1L;
         }
      }
   }

   public void setUseSpinLock(boolean var1) {
      this.a = var1;
   }

   public boolean getUseSpinLock() {
      return this.a;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 76;
               break;
            case 1:
               var10003 = 90;
               break;
            case 2:
               var10003 = 86;
               break;
            case 3:
               var10003 = 84;
               break;
            default:
               var10003 = 67;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class ViewType extends a {
      ViewType(int var1) {
         super(var1);
      }
   }

   public static class CtxMatch extends a {
      CtxMatch(int var1) {
         super(var1);
      }
   }

   public static class SecLevel extends a {
      SecLevel(int var1) {
         super(var1);
      }
   }

   public static class SecModel extends a {
      SecModel(int var1) {
         super(var1);
      }
   }
}
