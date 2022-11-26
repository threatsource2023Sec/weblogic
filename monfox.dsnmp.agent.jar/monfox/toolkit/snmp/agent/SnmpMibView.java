package monfox.toolkit.snmp.agent;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpOid;

public class SnmpMibView {
   private Vector a;
   private Vector b;
   private Vector c;
   private Vector d;

   public boolean inView(SnmpOid var1) {
      boolean var6 = SnmpMibNode.b;
      if (this.a == null & this.b == null & this.c == null & this.d == null) {
         return true;
      } else {
         boolean var10000;
         Iterator var2;
         SnmpOid var3;
         if (this.c != null) {
            var2 = this.c.iterator();

            while(var2.hasNext()) {
               var3 = (SnmpOid)var2.next();
               var10000 = var3.equals(var1);
               if (var6) {
                  return var10000;
               }

               if (var10000) {
                  return false;
               }

               if (var6) {
                  break;
               }
            }
         }

         if (this.a != null) {
            var2 = this.a.iterator();

            while(var2.hasNext()) {
               var3 = (SnmpOid)var2.next();
               var10000 = var3.equals(var1);
               if (var6) {
                  return var10000;
               }

               if (var10000) {
                  return true;
               }

               if (var6) {
                  break;
               }
            }
         }

         if (this.b != null) {
            var2 = this.b.iterator();

            while(true) {
               if (var2.hasNext()) {
                  var3 = (SnmpOid)var2.next();
                  var10000 = var3.contains(var1);
                  if (var6) {
                     break;
                  }

                  if (var10000) {
                     if (this.d != null) {
                        Iterator var4 = this.d.iterator();

                        while(var4.hasNext()) {
                           SnmpOid var5 = (SnmpOid)var4.next();
                           var10000 = var5.contains(var1);
                           if (var6) {
                              return var10000;
                           }

                           if (var10000 && var5.getLength() >= var3.getLength()) {
                              return false;
                           }

                           if (var6) {
                              break;
                           }
                        }
                     }

                     var10000 = true;
                     return var10000;
                  }

                  if (!var6) {
                     continue;
                  }
               }

               var10000 = false;
               break;
            }

            return var10000;
         } else if (this.d != null) {
            var2 = this.d.iterator();

            while(true) {
               if (var2.hasNext()) {
                  var3 = (SnmpOid)var2.next();
                  var10000 = var3.contains(var1);
                  if (var6) {
                     break;
                  }

                  if (var10000) {
                     return false;
                  }

                  if (!var6) {
                     continue;
                  }
               }

               var10000 = true;
               break;
            }

            return var10000;
         } else {
            var10000 = false;
            return var10000;
         }
      }
   }

   public void exclude(SnmpOid var1) {
      if (var1 != null) {
         if (this.c == null) {
            this.c = new Vector();
         }

         this.c.add(var1);
      }
   }

   public void excludeSubTree(SnmpOid var1) {
      if (var1 != null) {
         if (this.d == null) {
            this.d = new Vector();
         }

         this.d.add(var1);
         Collections.sort(this.d, new Comparator() {
            public int compare(Object var1, Object var2) {
               return ((SnmpOid)var1).compareTo((SnmpOid)var2);
            }

            public boolean equals(Object var1) {
               return false;
            }
         });
         Collections.reverse(this.d);
      }
   }

   public void include(SnmpOid var1) {
      if (var1 != null) {
         if (this.a == null) {
            this.a = new Vector();
         }

         this.a.add(var1);
      }
   }

   public void includeSubTree(SnmpOid var1) {
      if (var1 != null) {
         if (this.b == null) {
            this.b = new Vector();
         }

         this.b.add(var1);
         Collections.sort(this.b, new Comparator() {
            public int compare(Object var1, Object var2) {
               return ((SnmpOid)var1).compareTo((SnmpOid)var2);
            }

            public boolean equals(Object var1) {
               return false;
            }
         });
         Collections.reverse(this.b);
      }
   }

   public void includeAll() {
      this.a = null;
      this.b = null;
      this.c = null;
      this.d = null;
   }

   public void excludeAll() {
      this.a = new Vector();
      this.b = new Vector();
      this.c = null;
      this.d = null;
   }

   public boolean includesAll() {
      return this.a == null & this.b == null;
   }

   public boolean excludesAll() {
      if (this.a != null && this.a.size() == 0) {
         return this.b != null && this.b.size() == 0;
      } else {
         return false;
      }
   }

   public String toString() {
      boolean var5 = SnmpMibNode.b;
      StringBuffer var1 = new StringBuffer();
      if (this.includesAll()) {
         var1.append(a("9{~%$4pn60<y"));
         if (!var5) {
            return var1.toString();
         }
      }

      if (this.excludesAll()) {
         var1.append(a("5m~%$4pn60<y"));
         if (!var5) {
            return var1.toString();
         }
      }

      int var10000;
      int var2;
      Iterator var3;
      SnmpOid var4;
      label102: {
         var1.append(a("P|s*=%qx:Q\u000b"));
         var2 = 0;
         if (this.a != null) {
            var3 = this.a.iterator();

            while(var3.hasNext()) {
               var10000 = var2;
               if (var5) {
                  break label102;
               }

               if (var2 > 0) {
                  var1.append(',');
               }

               var4 = (SnmpOid)var3.next();
               var1.append(var4);
               ++var2;
               if (var5) {
                  break;
               }
            }
         }

         var10000 = var2;
      }

      if (var10000 > 0) {
         var1.append(',');
      }

      label88: {
         var2 = 0;
         if (this.b != null) {
            var3 = this.b.iterator();

            while(var3.hasNext()) {
               var10000 = var2;
               if (var5) {
                  break label88;
               }

               if (var2 > 0) {
                  var1.append(',');
               }

               var4 = (SnmpOid)var3.next();
               var1.append(var4);
               var1.append(a("^\u001f"));
               ++var2;
               if (var5) {
                  break;
               }
            }
         }

         var1.append('}');
         var1.append(a("Ppe*=%qx:Q\u000b"));
         var10000 = 0;
      }

      label75: {
         var2 = var10000;
         if (this.c != null) {
            var3 = this.c.iterator();

            while(var3.hasNext()) {
               var10000 = var2;
               if (var5) {
                  break label75;
               }

               if (var2 > 0) {
                  var1.append(',');
               }

               var4 = (SnmpOid)var3.next();
               var1.append(var4);
               ++var2;
               if (var5) {
                  break;
               }
            }
         }

         var10000 = var2;
      }

      if (var10000 > 0) {
         var1.append(',');
      }

      var2 = 0;
      if (this.d != null) {
         var3 = this.d.iterator();

         while(var3.hasNext()) {
            if (var5) {
               return var1.toString();
            }

            if (var2 > 0) {
               var1.append(',');
            }

            var4 = (SnmpOid)var3.next();
            var1.append(var4);
            var1.append(a("^\u001f"));
            ++var2;
            if (var5) {
               break;
            }
         }
      }

      var1.append('}');
      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 112;
               break;
            case 1:
               var10003 = 53;
               break;
            case 2:
               var10003 = 61;
               break;
            case 3:
               var10003 = 105;
               break;
            default:
               var10003 = 113;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
