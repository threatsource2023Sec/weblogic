package monfox.toolkit.snmp.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpException;

public class CronTime implements Serializable {
   private int[] a = null;
   private int[] b = null;
   private int[] c = null;
   private int[] d = null;
   private int[] e = null;
   private static final String f = "$Id: CronTime.java,v 1.7 2003/02/25 22:17:14 sking Exp $";

   public CronTime(String var1) throws SchedulerException {
      this.setTime(var1);
   }

   public void setTime(String var1) throws SchedulerException {
      int var6 = WorkItem.d;
      String[] var2 = new String[]{"*", "*", "*", "*", ""};
      StringTokenizer var3 = new StringTokenizer(var1, b("]<"), true);
      int var4 = 0;

      int var10000;
      while(true) {
         if (var3.hasMoreTokens()) {
            var10000 = var4;
            if (var6 != 0) {
               break;
            }

            if (var4 < var2.length) {
               label47: {
                  String var5 = var3.nextToken().trim();
                  if (var5.equals(":")) {
                     var2[var4] = "";
                     if (var6 == 0) {
                        break label47;
                     }

                     SnmpException.b = !SnmpException.b;
                  }

                  var2[var4] = var5;
                  if (var3.hasMoreTokens()) {
                     var3.nextToken();
                  }
               }

               ++var4;
               if (var6 == 0) {
                  continue;
               }
            }
         }

         var10000 = var2[0].equals("");
         break;
      }

      if (var10000 != 0) {
         var2[0] = "*";
      }

      if (var2[1].equals("")) {
         var2[1] = "*";
      }

      if (var2[4].equals("") && var2[2].equals("")) {
         var2[2] = "*";
      }

      if (var2[3].equals("")) {
         var2[3] = "*";
      }

      this.a = a(var2[0], 0, 59);
      this.b = a(var2[1], 0, 23);
      this.c = a(var2[2], 1, 31);
      this.d = a(var2[3], 1, 12);
      this.e = a(var2[4]);
   }

   public Calendar nextTime(Calendar var1) throws SchedulerException {
      Calendar var2 = this.nextDayOfMonth(var1);
      Calendar var3 = this.nextDayOfWeek(var1);
      if (var2 == null && var3 == null) {
         throw new SchedulerException(b(")shj3\u000bi-\u001c\u001c\u0002d<\u001c\u0006\u000eq-"));
      } else if (var2 == null) {
         return var3;
      } else if (var3 == null) {
         return var2;
      } else {
         return var3.before(var2) ? var3 : var2;
      }
   }

   public Calendar nextDayOfWeek(Calendar var1) {
      return this.a((Calendar)var1, 12);
   }

   private Calendar a(Calendar var1, int var2) {
      if (var1 == null) {
         return null;
      } else {
         Calendar var3 = (Calendar)var1.clone();
         if (var2 == -1) {
            return var3;
         } else {
            if (var2 == 12) {
               var3.add(12, 1);
               var2 = 11;
            }

            int var4 = var3.get(1);
            int var5 = var3.get(2) + 1;
            int var6 = var3.get(5);
            int var7 = var3.get(11);
            int var8 = var3.get(12);
            boolean var10 = false;
            Calendar var11 = Calendar.getInstance();
            var11.set(1, var4);
            var11.set(2, var5 - 1);
            var11.set(5, var6);
            int var12 = a(this.d, var5);
            var11.set(2, var12 - 1);
            if (var12 < var5) {
               var11.add(1, 1);
               boolean var19 = true;
               var8 = 0;
               var7 = 0;
               var10 = true;
               var2 = -1;
               var11.set(2, 0);
               var11.set(5, 1);
               var11.set(11, 0);
               var11.set(12, 0);
            }

            int var14;
            int var15;
            label51: {
               int var13 = var11.get(7);
               var14 = a(this.e, var13);
               var15 = 0;
               if (var14 > var13) {
                  var15 = var14 - var13;
                  if (WorkItem.d == 0) {
                     break label51;
                  }
               }

               if (var14 < var13) {
                  var15 = 7 + var14 - var13;
               }
            }

            if (var15 > 0) {
               var11.add(5, var15);
               var7 = 0;
               var8 = 0;
               var10 = true;
               var2 = 1;
               var11.set(11, 0);
               var11.set(12, 0);
            }

            int var16 = a(this.b, var7);
            var11.set(11, var16);
            if (var16 < var7) {
               var11.add(5, 1);
               var8 = 0;
               var10 = true;
               var2 = 7;
               var11.set(11, 0);
               var11.set(12, 0);
            }

            int var17 = a(this.a, var8);
            var11.set(12, var17);
            if (var17 < var8) {
               var11.add(11, 1);
               var10 = true;
               var2 = 5;
               var11.set(12, 0);
            }

            var11.set(13, 0);
            Calendar var18 = null;
            if (var4 != -1 && var12 != -1 && var14 != -1 && var16 != -1 && var17 != -1) {
               var18 = var11;
            }

            if (var10) {
               var18 = this.a(var18, var2);
            }

            return var18;
         }
      }
   }

   public Calendar nextDayOfMonth(Calendar var1) {
      return this.b((Calendar)var1, 12);
   }

   private Calendar b(Calendar var1, int var2) {
      if (var1 == null) {
         return null;
      } else {
         Calendar var3 = (Calendar)var1.clone();
         if (var2 == -1) {
            return var3;
         } else {
            if (var2 == 12) {
               var3.add(12, 1);
               var2 = 11;
            }

            int var4 = var3.get(1);
            int var5 = var3.get(2) + 1;
            int var6 = var3.get(5);
            int var7 = var3.get(7);
            int var8 = var3.get(11);
            int var9 = var3.get(12);
            boolean var11 = false;
            Calendar var12 = Calendar.getInstance();
            var12.set(1, var4);
            int var13 = a(this.d, var5);
            var12.set(2, var13 - 1);
            boolean var18;
            if (var13 < var5) {
               var12.add(1, 1);
               var6 = 1;
               var18 = true;
               var9 = 0;
               var8 = 0;
               var11 = true;
               var2 = -1;
               var12.set(2, 0);
               var12.set(5, 1);
               var12.set(11, 0);
               var12.set(12, 0);
            }

            int var14 = a(this.c, var6);
            var12.set(5, var14);
            if (var14 < var6) {
               var12.add(2, 1);
               var18 = true;
               var8 = 0;
               var9 = 0;
               var11 = true;
               var2 = 1;
               var12.set(5, 1);
               var12.set(11, 0);
               var12.set(12, 0);
            }

            int var15 = a(this.b, var8);
            var12.set(11, var15);
            if (var15 < var8) {
               var12.add(5, 1);
               var9 = 0;
               var11 = true;
               var2 = 2;
               var12.set(11, 0);
               var12.set(12, 0);
            }

            int var16 = a(this.a, var9);
            var12.set(12, var16);
            if (var16 < var9) {
               var12.add(11, 1);
               var11 = true;
               var2 = 5;
               var12.set(12, 0);
            }

            var12.set(13, 0);
            Calendar var17 = null;
            if (var4 != -1 && var13 != -1 && var14 != -1 && var15 != -1 && var16 != -1) {
               var17 = var12;
            }

            if (var11) {
               var17 = this.b(var17, var2);
            }

            return var17;
         }
      }
   }

   private static int[] a(String var0) throws SchedulerException {
      int var5 = WorkItem.d;
      if (var0.startsWith("*")) {
         return null;
      } else {
         StringTokenizer var1 = new StringTokenizer(var0, ",");
         int var2 = 0;
         int[] var3 = new int[var1.countTokens()];

         while(true) {
            if (var1.hasMoreTokens()) {
               String var4 = var1.nextToken().trim();
               if (var5 != 0) {
                  break;
               }

               label106: {
                  if (var4.equalsIgnoreCase(b("*S\u0006"))) {
                     var3[var2] = 2;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equalsIgnoreCase(b("3I\r"))) {
                     var3[var2] = 3;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equalsIgnoreCase(b("0Y\f"))) {
                     var3[var2] = 4;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equalsIgnoreCase(b("3T\u001d"))) {
                     var3[var2] = 5;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equalsIgnoreCase(b("!N\u0001"))) {
                     var3[var2] = 6;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equalsIgnoreCase(b("4]\u001c"))) {
                     var3[var2] = 7;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equalsIgnoreCase(b("4I\u0006"))) {
                     var3[var2] = 1;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equals("0")) {
                     var3[var2] = 1;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equals("1")) {
                     var3[var2] = 2;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equals("2")) {
                     var3[var2] = 3;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equals("3")) {
                     var3[var2] = 4;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equals("4")) {
                     var3[var2] = 5;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equals("5")) {
                     var3[var2] = 6;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (var4.equals("6")) {
                     var3[var2] = 7;
                     if (var5 == 0) {
                        break label106;
                     }
                  }

                  if (!var4.equals("7")) {
                     throw new SchedulerException(b(".r>]>\u000exhx3\u001e1\u0007Z\u007f0y-Wr@") + var4 + "'");
                  }

                  var3[var2] = 1;
                  if (var5 != 0) {
                     throw new SchedulerException(b(".r>]>\u000exhx3\u001e1\u0007Z\u007f0y-Wr@") + var4 + "'");
                  }
               }

               ++var2;
               if (var5 == 0) {
                  continue;
               }
            }

            a(var3);
            break;
         }

         return var3;
      }
   }

   private static int[] a(String var0, int var1, int var2) throws SchedulerException {
      int var13 = WorkItem.d;
      if (var0.startsWith("*")) {
         return null;
      } else if (var0.startsWith("-")) {
         return new int[0];
      } else {
         StringTokenizer var3 = new StringTokenizer(var0, ",");
         Vector var4 = new Vector();

         int var6;
         int var10000;
         label128: {
            while(var3.hasMoreTokens()) {
               String var5 = var3.nextToken();

               try {
                  label133: {
                     var6 = var5.indexOf(45);
                     int var7 = var5.indexOf(43);
                     var10000 = var6;
                     if (var13 != 0) {
                        break label128;
                     }

                     String var8;
                     int var10;
                     if (var6 > -1) {
                        var8 = var5.substring(0, var6);
                        String var9 = var5.substring(var6 + 1);
                        var10 = Integer.parseInt(var8);
                        int var11 = Integer.parseInt(var9);
                        if (var10 < var1) {
                           throw new SchedulerException(b("5}&[7GO<] \u0013 ") + var1 + "[" + var5 + "]");
                        }

                        if (var11 > var2) {
                           throw new SchedulerException(b("5}&[7GY&Xn") + var2 + "[" + var5 + "]");
                        }

                        label135: {
                           int var12;
                           if (var10 > var11) {
                              var12 = 0;

                              do {
                                 if (var12 > var11) {
                                    var12 = var10;
                                    break;
                                 }

                                 var4.addElement(new Integer(var12));
                                 ++var12;
                              } while(var13 == 0 || var13 == 0);

                              while(var12 <= var2) {
                                 var4.addElement(new Integer(var12));
                                 ++var12;
                                 if (var13 != 0) {
                                    break label135;
                                 }

                                 if (var13 != 0) {
                                    break;
                                 }
                              }

                              if (var13 == 0) {
                                 break label135;
                              }
                           }

                           var12 = var10;

                           while(var12 <= var11) {
                              var4.addElement(new Integer(var12));
                              ++var12;
                              if (var13 != 0 && var13 != 0) {
                                 break label133;
                              }
                           }
                        }

                        if (var13 == 0) {
                           break label133;
                        }
                     }

                     if (var7 > -1) {
                        var8 = var5.substring(var7 + 1);
                        int var17 = Integer.parseInt(var8);
                        var10 = 0;

                        while(var10 <= var2) {
                           var4.addElement(new Integer(var10));
                           var10 += var17;
                           if (var13 != 0) {
                              break label133;
                           }

                           if (var13 != 0) {
                              break;
                           }
                        }

                        if (var13 == 0) {
                           break label133;
                        }
                     }

                     var4.addElement(Integer.valueOf(var5));
                  }
               } catch (NumberFormatException var14) {
                  throw new SchedulerException(b(".r>]>\u000exhr'\n~-Nr!s:Q3\u0013&h") + var5);
               }

               if (var13 != 0) {
                  break;
               }
            }

            var10000 = var4.size();
         }

         int[] var15 = new int[var10000];
         var6 = 0;

         while(true) {
            if (var6 < var15.length) {
               Integer var16 = (Integer)var4.elementAt(var6);
               var15[var6] = var16;
               ++var6;
               if (var13 != 0) {
                  break;
               }

               if (var13 == 0) {
                  continue;
               }
            }

            a(var15);
            break;
         }

         return var15;
      }
   }

   private static void a(int[] var0) {
      int var4 = WorkItem.d;
      int var1 = 0;

      do {
         int var10000 = var1;
         int var10001 = var0.length - 1;

         label29:
         while(true) {
            if (var10000 >= var10001) {
               return;
            }

            int var2 = var1 + 1;

            while(true) {
               if (var2 >= var0.length) {
                  break label29;
               }

               var10000 = var0[var1];
               var10001 = var0[var2];
               if (var4 != 0) {
                  break;
               }

               if (var10000 > var10001) {
                  int var3 = var0[var1];
                  var0[var1] = var0[var2];
                  var0[var2] = var3;
               }

               ++var2;
               if (var4 != 0) {
                  break label29;
               }
            }
         }

         ++var1;
      } while(var4 == 0);

   }

   public boolean matches(Calendar var1) {
      int var2 = var1.get(12);
      if (!b(this.a, var2)) {
         return false;
      } else {
         int var3 = var1.get(11);
         if (!b(this.b, var3)) {
            return false;
         } else {
            int var4 = var1.get(5);
            int var5 = var1.get(7);
            int var6 = var1.get(2) + 1;
            return b(this.e, var5) || b(this.c, var4) && b(this.d, var6);
         }
      }
   }

   private static int a(int[] var0, int var1) {
      int var3 = WorkItem.d;
      if (var0 == null) {
         return var1;
      } else if (var0.length == 0) {
         return -1;
      } else {
         int var2 = 0;

         int var10000;
         while(true) {
            if (var2 < var0.length) {
               var10000 = var0[var2];
               if (var3 != 0) {
                  break;
               }

               if (var10000 >= var1) {
                  return var0[var2];
               }

               ++var2;
               if (var3 == 0) {
                  continue;
               }
            }

            var10000 = var0[0];
            break;
         }

         return var10000;
      }
   }

   private static boolean b(int[] var0, int var1) {
      int var3 = WorkItem.d;
      if (var0 == null) {
         return true;
      } else {
         int var2 = 0;

         int var10000;
         while(true) {
            if (var2 < var0.length) {
               var10000 = var0[var2];
               if (var3 != 0) {
                  break;
               }

               if (var10000 == var1) {
                  return true;
               }

               ++var2;
               if (var3 == 0) {
                  continue;
               }
            }

            var10000 = 0;
            break;
         }

         return (boolean)var10000;
      }
   }

   private StringBuffer a(StringBuffer var1, int[] var2) {
      StringBuffer var10000;
      label37: {
         int var4 = WorkItem.d;
         if (var2 == null) {
            var1.append("*");
            if (var4 == 0) {
               break label37;
            }
         }

         if (var2.length == 0) {
            var1.append("-");
            if (var4 == 0) {
               break label37;
            }
         }

         int var3 = 0;

         while(var3 < var2.length) {
            var10000 = var1.append(var2[var3]);
            if (var4 != 0) {
               return var10000;
            }

            if (var3 < var2.length - 1) {
               var1.append(",");
            }

            ++var3;
            if (var4 != 0) {
               break;
            }
         }
      }

      var10000 = var1;
      return var10000;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.a(var1, this.a).append(":");
      this.a(var1, this.b).append(":");
      this.a(var1, this.c).append(":");
      this.a(var1, this.d).append(":");
      if (this.e != null) {
         int var2 = 0;

         while(var2 < this.e.length) {
            String var3 = null;
            switch (this.e[var2]) {
               case 1:
                  var3 = b("4i&");
                  break;
               case 2:
                  var3 = b("*s&");
                  break;
               case 3:
                  var3 = b("3i-");
                  break;
               case 4:
                  var3 = b("0y,");
                  break;
               case 5:
                  var3 = b("3t=");
                  break;
               case 6:
                  var3 = b("!n!");
                  break;
               case 7:
                  var3 = b("4}<");
                  break;
               default:
                  var3 = null;
            }

            if (var3 != null) {
               var1.append(var3);
               if (var2 < this.e.length - 1) {
                  var1.append(",");
               }
            }

            ++var2;
            if (WorkItem.d != 0) {
               break;
            }
         }
      }

      return var1.toString();
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof CronTime)) {
         return false;
      } else {
         CronTime var2 = (CronTime)var1;
         return this.a(var2.a, this.a) && this.a(var2.b, this.b) && this.a(var2.c, this.c) && this.a(var2.d, this.d) && this.a(var2.e, this.e);
      }
   }

   private boolean a(int[] var1, int[] var2) {
      int var4 = WorkItem.d;
      if (var1 == null && var2 == null) {
         return true;
      } else if (var1 != null && var2 != null) {
         if (var1.length != var2.length) {
            return false;
         } else {
            int var3 = 0;

            int var10000;
            while(true) {
               if (var3 < var1.length) {
                  var10000 = var1[var3];
                  if (var4 != 0) {
                     break;
                  }

                  if (var10000 != var2[var3]) {
                     return false;
                  }

                  ++var3;
                  if (var4 == 0) {
                     continue;
                  }
               }

               var10000 = 1;
               break;
            }

            return (boolean)var10000;
         }
      } else {
         return false;
      }
   }

   public int[] getMinuteList() {
      return this.a;
   }

   public int[] getHourList() {
      return this.b;
   }

   public int[] getDayList() {
      return this.c;
   }

   public int[] getMonthList() {
      return this.d;
   }

   public int[] getDayOfWeekList() {
      return this.e;
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 103;
               break;
            case 1:
               var10003 = 28;
               break;
            case 2:
               var10003 = 72;
               break;
            case 3:
               var10003 = 60;
               break;
            default:
               var10003 = 82;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
