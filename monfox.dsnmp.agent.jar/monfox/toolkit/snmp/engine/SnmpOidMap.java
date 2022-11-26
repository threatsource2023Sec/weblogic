package monfox.toolkit.snmp.engine;

import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;

public class SnmpOidMap {
   private static Logger a = Logger.getInstance(a("3\u001e\u0015Kq"), a("6\n\u001eHu"), a("$#6vn\u001e)\u0016gQ"));
   private List b = new Vector();
   private String c = null;

   public SnmpOidMap(String var1) {
      this.c = var1;
   }

   public String getMapName() {
      return this.c;
   }

   public void addMapping(String var1, String var2, boolean var3) throws SnmpValueException {
      this.addMapping(new SnmpOid(var1), new SnmpOid(var2), var3);
   }

   public void addMapping(SnmpOid var1, SnmpOid var2, boolean var3) {
      if (a.isDebugEnabled()) {
         a.debug(a("We") + this.c + a("^w{&@\u0013)\u0016gQ\u0007$5a\u001bW") + var1 + a("Wpf8\u0001") + var2 + a("Mm>~@\u00149f") + var3);
      }

      OidEntry var4 = new OidEntry(var1, var2, var3);
      this.b.add(var4);
   }

   public SnmpVarBindList translate(SnmpVarBindList var1, boolean var2) {
      boolean var8 = SnmpPDU.i;
      SnmpVarBindList var3 = null;
      int var4 = 0;

      SnmpVarBindList var10000;
      while(true) {
         if (var4 < var1.size()) {
            var10000 = var1;
            if (var8) {
               break;
            }

            SnmpVarBind var5 = var1.get(var4);
            SnmpOid var6 = var5.getOid();
            SnmpOid var7 = this.translate(var6, var2);
            if (var7 != null) {
               if (var3 == null) {
                  var3 = new SnmpVarBindList(var1, true, true);
                  var1 = var3;
               }

               var5 = var3.get(var4);
               var5.setOid(var7);
            }

            ++var4;
            if (!var8) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public SnmpOid translate(SnmpOid var1, boolean var2) {
      ListIterator var3 = this.b.listIterator();

      while(var3.hasNext()) {
         OidEntry var4 = (OidEntry)var3.next();
         SnmpOid var5 = var4.translate(var1, var2);
         if (var5 != null) {
            return var5;
         }

         if (SnmpPDU.i) {
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
               var10003 = 119;
               break;
            case 1:
               var10003 = 77;
               break;
            case 2:
               var10003 = 91;
               break;
            case 3:
               var10003 = 6;
               break;
            default:
               var10003 = 33;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class OidEntry {
      private SnmpOid a;
      private SnmpOid b;
      private boolean c = false;

      OidEntry(SnmpOid var2, SnmpOid var3, boolean var4) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
      }

      public SnmpOid translate(SnmpOid var1, boolean var2) {
         SnmpOid var3 = null;
         SnmpOid var4 = null;
         if (var2) {
            var3 = this.a;
            var4 = this.b;
         } else {
            var4 = this.a;
            var3 = this.b;
         }

         SnmpOid var5;
         if (!this.c) {
            if (var3.contains(var1)) {
               var5 = (SnmpOid)var4.clone();

               try {
                  if (var1.getLength() > var3.getLength()) {
                     var5.append(var1.suboid(var3.getLength()));
                  }

                  if (SnmpOidMap.a.isDebugEnabled()) {
                     SnmpOidMap.a.debug(a("VP") + SnmpOidMap.this.c + a("_B\u0005T\u000b\u0017\u0016VL\u0018\u0002\u0011KGY\u0019\u0011A\u001aY") + var1 + a("VE\u0018\u001eY") + var5);
                  }

                  return var5;
               } catch (Exception var7) {
                  SnmpOidMap.a.debug(a("\u0013\u0000FE\t\u0002\u0011JNY\u001f\u0016\u0005T\u000b\u0017\u0016VL\u0018\u0002\u0011KGY91a") + var1, var7);
                  return null;
               }
            }
         } else if (this.c && var3.equals(var1)) {
            var5 = (SnmpOid)var4.clone();
            if (SnmpOidMap.a.isDebugEnabled()) {
               SnmpOidMap.a.debug(a("VP") + SnmpOidMap.this.c + a("_#@X\u0018\u0015\fx\u001aYV\fWA\u0017\u0005\u0014DT\u0010\u0018\u001f\u0005O\u0010\u0012B\u0005") + var1 + a("VE\u0018\u001eY") + var5);
            }

            return var5;
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
                  var10003 = 118;
                  break;
               case 1:
                  var10003 = 120;
                  break;
               case 2:
                  var10003 = 37;
                  break;
               case 3:
                  var10003 = 32;
                  break;
               default:
                  var10003 = 121;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public static class DefaultManager implements Manager {
      private List a = new Vector();
      private Map b = new Hashtable();
      private SnmpOidMap c;

      public void setDefaultOidMap(SnmpOidMap var1) {
         this.c = var1;
      }

      public void add(SnmpOidMap var1) {
         this.a.add(var1);
         this.b.put(var1.getMapName(), var1);
      }

      public void processIncoming(SnmpMessage var1) {
         SnmpOidMap.a.debug(a("\u007f\u0015-Ry|\u0014\u000b_\u007f`\n+_{5") + var1.getMsgID());
         SnmpOidMap var2 = null;
         ListIterator var3 = this.a.listIterator();

         SnmpOidMap var4;
         while(var3.hasNext()) {
            var4 = (SnmpOidMap)var3.next();
            SnmpOidMap.a.debug(a("l\u000f'Rwf\t%\u0011hj\n2]}{\u0002b\\}{\u0004*\u000b") + var4.getMapName());
            if (this.isTemplateMatch(var4, var1)) {
               SnmpOidMap.a.debug(a("/Gb\u0011hj\n2]}{\u0002b\\}{\u0004*\u0011=.]b") + var4.getMapName());
               var2 = var4;
               var1.setOidMap(var4);
               break;
            }
         }

         if (var2 == null) {
            var2 = this.c;
            SnmpOidMap.a.debug(a("/Gb\u0011i|\u000e,V<k\u0002$Pic\u0013b\\}\u007f]b") + var2);
         }

         if (var1.getData() != null && var1.getData().getVarBindList() != null) {
            if (var2 != null) {
               SnmpOidMap.a.debug(a("/Gb\u0011h}\u0006,Bpn\u0013+_{/\u0011#]ij\u0014bWs}G/Pl5G") + var2.getMapName());
               var1.setOidMap(var2);
               SnmpVarBindList var6 = var2.translate(var1.getData().getVarBindList(), true);
               if (var6 != null) {
                  var1.getData().setVarBindList(var6);
               }

            } else {
               if (var1.getOidMap() == null) {
                  var3 = this.a.listIterator();

                  while(var3.hasNext()) {
                     var4 = (SnmpOidMap)var3.next();
                     SnmpOidMap.a.debug(a("/Gb\u0011h}\u0006,Bpn\u0013'\u000b<l\u000f'Rwf\t%\u0011SF#1\u0011uaG/Pl5G") + var4.getMapName());
                     SnmpVarBindList var5 = var4.translate(var1.getData().getVarBindList(), true);
                     if (var5 != null) {
                        SnmpOidMap.a.debug(a("|\u00026\u0011jn\u0015\u0000Xrk++Bh5G") + var5);
                        var1.getData().setVarBindList(var5);
                        var1.setOidMap(var4);
                     }

                     if (SnmpPDU.i) {
                        break;
                     }
                  }
               }

            }
         } else {
            SnmpOidMap.a.debug(a("/Gb\u0011r`G4Ppz\u00021\u0011h`G6C}a\u0014.PhjKbCy{\u00120_ua\u0000"));
         }
      }

      public boolean isTemplateMatch(SnmpOidMap var1, SnmpMessage var2) {
         return false;
      }

      public void processOutgoing(SnmpMessage var1) {
         SnmpOidMap.a.debug(a("\u007f\u0015-Ry|\u0014\rDhh\b+_{5G") + var1.getMsgID());
         SnmpOidMap var2 = var1.getOidMap();
         if (var1.getData() != null && var1.getData().getVarBindList() != null) {
            if (var2 != null) {
               SnmpVarBindList var3 = var2.translate(var1.getData().getVarBindList(), false);
               if (var3 != null) {
                  var1.getData().setVarBindList(var3);
               }
            }

         } else {
            SnmpOidMap.a.debug(a("a\bb\\y|\u0014#Vy/\u0003#E}/\u00170Toj\t6"));
         }
      }

      public SnmpOidMap getOidMap(String var1) {
         return var1 == null ? null : (SnmpOidMap)this.b.get(var1);
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 15;
                  break;
               case 1:
                  var10003 = 103;
                  break;
               case 2:
                  var10003 = 66;
                  break;
               case 3:
                  var10003 = 49;
                  break;
               default:
                  var10003 = 28;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public interface Manager {
      void processIncoming(SnmpMessage var1);

      void processOutgoing(SnmpMessage var1);

      SnmpOidMap getOidMap(String var1);
   }
}
