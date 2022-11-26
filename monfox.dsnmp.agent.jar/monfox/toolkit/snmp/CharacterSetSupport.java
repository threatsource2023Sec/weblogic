package monfox.toolkit.snmp;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTypeInfo;

public class CharacterSetSupport {
   private String a = null;
   private Map b = null;
   private Map c = null;

   CharacterSetSupport() {
   }

   private void a(String var1) throws UnsupportedEncodingException {
      new String(new byte[0], var1);
   }

   public void setDefaultCharSet(String var1) throws UnsupportedEncodingException {
      this.a(var1);
      this.a = var1;
   }

   public String getDefaultCharSet() {
      return this.a;
   }

   public void setCharSetForOid(String var1, String var2) throws UnsupportedEncodingException, SnmpValueException {
      this.a(var2);
      this.setCharSetForOid(new SnmpOid(var1), var2);
   }

   public void setCharSetForOid(SnmpOid var1, String var2) throws UnsupportedEncodingException {
      this.a(var2);
      if (this.c == null) {
         this.c = new Hashtable();
      }

      if (var2 == null) {
         this.c.remove(var1);
         if (!SnmpValue.b) {
            return;
         }
      }

      this.c.put(var1, var2);
   }

   public void setCharSetForTC(String var1, String var2) throws UnsupportedEncodingException {
      this.a(var2);
      if (this.b == null) {
         this.b = new Hashtable();
      }

      if (var2 == null) {
         this.b.remove(var1);
         if (!SnmpValue.b) {
            return;
         }
      }

      this.b.put(var1, var2);
   }

   public String getCharSetForVariable(SnmpOid var1) {
      boolean var7 = SnmpValue.b;
      if (this.a == null && this.b == null && this.c == null) {
         return null;
      } else {
         String var2 = null;
         if (var1 != null) {
            if (var2 == null && this.b != null) {
               SnmpOidInfo var3 = var1.getOidInfo();
               if (var3 != null && var3 instanceof SnmpObjectInfo) {
                  SnmpObjectInfo var4 = (SnmpObjectInfo)var3;
                  SnmpTypeInfo var5 = var4.getTypeInfo();
                  if (var5 != null) {
                     String var6 = var5.getName();
                     if (var6 != null) {
                        var2 = (String)this.b.get(var6);
                     }

                     if (var2 == null) {
                        var6 = var5.getDefinedType();
                        if (var6 != null) {
                           var2 = (String)this.b.get(var6);
                        }
                     }
                  }
               }
            }

            if (var2 == null && this.c != null) {
               Iterator var8 = this.c.entrySet().iterator();

               while(var8.hasNext()) {
                  Map.Entry var9 = (Map.Entry)var8.next();
                  SnmpOid var10 = (SnmpOid)var9.getKey();
                  if (var7) {
                     return var2;
                  }

                  if (var10.contains(var1)) {
                     var2 = (String)var9.getValue();
                     if (!var7) {
                        break;
                     }
                  }

                  if (var7) {
                     break;
                  }
               }
            }
         }

         if (var2 == null) {
            var2 = this.a;
         }

         return var2;
      }
   }
}
