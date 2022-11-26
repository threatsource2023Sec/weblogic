package monfox.toolkit.snmp.mgr;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;

public class SnmpPeerView implements Serializable {
   public static final int READ = 1;
   public static final int WRITE = 2;
   public static final int INFORM = 4;
   public static final int ANY = 7;
   private List a;
   private List b;
   private SnmpParameters c;
   private int d;

   public SnmpPeerView(SnmpParameters var1) {
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = -1;
      this.a = null;
      this.b = null;
      this.c = var1;
   }

   public SnmpPeerView(SnmpOid var1, SnmpParameters var2) {
      this(var2);
      this.a = new Vector();
      this.a.add(var1);
   }

   public SnmpPeerView(SnmpOid var1, int var2, String var3) {
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = -1;
      this.c = new SnmpParameters(var3, var3, var3);
      this.c.setVersion(var2);
      this.a = new Vector();
      this.a.add(var1);
   }

   public SnmpPeerView(SnmpOid var1, int var2, String var3, String var4, String var5) {
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = -1;
      this.c = new SnmpParameters(var3, var4, var5);
      this.c.setVersion(var2);
      this.a = new Vector();
      this.a.add(var1);
   }

   public List getIncludes() {
      return this.a;
   }

   public List getExcludes() {
      return this.b;
   }

   public SnmpParameters getParameters() {
      return this.c;
   }

   public void includeSubtree(SnmpOid var1) {
      if (this.a == null) {
         this.a = new Vector();
      }

      this.a.add(var1);
   }

   public void excludeSubtree(SnmpOid var1) {
      if (this.b == null) {
         this.b = new Vector();
      }

      this.b.add(var1);
   }

   public void includeAll() {
      this.a = null;
      this.b = null;
   }

   public void excludeAll() {
      this.a = new Vector();
      this.b = null;
   }

   public boolean isInView(SnmpVarBindList var1) {
      return this.isInView((SnmpVarBindList)var1, 7);
   }

   public boolean isInView(SnmpVarBindList var1, int var2) {
      boolean var5 = SnmpSession.B;
      if (!this.isViewType(var2)) {
         return false;
      } else {
         Enumeration var3 = var1.getVarBinds();

         boolean var10000;
         while(true) {
            if (var3.hasMoreElements()) {
               SnmpVarBind var4 = (SnmpVarBind)var3.nextElement();
               var10000 = this.isInView(var4.getOid(), var2);
               if (var5) {
                  break;
               }

               if (!var10000) {
                  return false;
               }

               if (!var5) {
                  continue;
               }
            }

            var10000 = true;
            break;
         }

         return var10000;
      }
   }

   public boolean isInView(SnmpOid var1) {
      return this.isInView((SnmpOid)var1, 7);
   }

   public boolean isInView(SnmpOid var1, int var2) {
      boolean var6 = SnmpSession.B;
      if (!this.isViewType(var2)) {
         return false;
      } else {
         boolean var10000;
         boolean var3;
         ListIterator var4;
         SnmpOid var5;
         label57: {
            var3 = true;
            if (this.a != null) {
               var3 = false;
               var4 = this.a.listIterator();

               while(var4.hasNext()) {
                  var5 = (SnmpOid)var4.next();
                  var10000 = var5.contains(var1);
                  if (var6) {
                     return var10000;
                  }

                  if (var10000) {
                     var3 = true;
                     if (!var6) {
                        break;
                     }
                  }

                  if (var6) {
                     break;
                  }
               }

               if (!var6) {
                  break label57;
               }
            }

            var3 = true;
         }

         if (this.b != null && var3) {
            var4 = this.b.listIterator();

            while(var4.hasNext()) {
               var5 = (SnmpOid)var4.next();
               var10000 = var5.contains(var1);
               if (var6) {
                  return var10000;
               }

               if (var10000) {
                  var3 = false;
                  if (!var6) {
                     break;
                  }
               }

               if (var6) {
                  break;
               }
            }
         }

         var10000 = var3;
         return var10000;
      }
   }

   public boolean isViewType(int var1) {
      if (var1 == 7) {
         return true;
      } else {
         if (this.d == -1) {
            this.d = 0;
            SnmpParameters var2 = this.getParameters();
            Object var3 = null;
            if (var2.getReadProfile() != null && var2.getReadProfile().getSecurityName() != null) {
               this.d |= 1;
            }

            if (var2.getWriteProfile() != null && var2.getWriteProfile().getSecurityName() != null) {
               this.d |= 2;
            }

            if (var2.getInformProfile() != null && var2.getInformProfile().getSecurityName() != null) {
               this.d |= 4;
            }
         }

         return (this.d & var1) == var1;
      }
   }
}
