package monfox.toolkit.snmp.util;

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpException;

public class Commandline {
   public String[] params;
   Hashtable a;
   public boolean debug;
   private static final String b = "$Id: Commandline.java,v 1.2 2001/01/15 21:33:26 sking Exp $";

   public Commandline(String[] var1, String var2, String var3, String[] var4, String[] var5) throws h {
      this.debug = false;
      this.a(var1, var2, var3, var4, var5);
   }

   public Commandline(String[] var1, String var2, String var3) throws h {
      this.debug = false;
      String[] var4 = new String[0];
      this.a(var1, var2, var3, var4, var4);
   }

   public Commandline(String[] var1, String var2, String var3, String var4, String[] var5, String[] var6, String[] var7) throws h, i {
      int var13 = WorkItem.d;
      super();
      this.debug = false;
      boolean var8 = false;
      String var9 = d(")M\u0014\u001bR\nCG+T\tI\u0006\u0006_\bM\t\r\u001b+T\u0013\u0001T\n\f\u0014A\u0001D");
      String[] var10 = new String[var6.length + var7.length];
      int var12 = 0;
      int var11 = 0;

      while(true) {
         if (var11 < var6.length) {
            var10[var12] = var6[var11];
            ++var11;
            ++var12;
            if (var13 != 0) {
               break;
            }

            if (var13 == 0) {
               continue;
            }
         }

         var11 = 0;
         break;
      }

      while(true) {
         if (var11 < var7.length) {
            var10[var12] = var7[var11];
            ++var11;
            ++var12;
            if (var13 != 0) {
               break;
            }

            if (var13 == 0) {
               continue;
            }
         }

         this.a(var1, var2, var3 + var4, var5, var10);
         break;
      }

      var11 = 0;

      byte var10000;
      while(true) {
         if (var11 < var4.length()) {
            var10000 = this.getOption(new Character(var4.charAt(var11))).equals("");
            if (var13 != 0) {
               break;
            }

            if (var10000 != 0) {
               var9 = var9 + " " + (new Character(var4.charAt(var11))).toString();
               var8 = true;
            }

            ++var11;
            if (var13 == 0) {
               continue;
            }
         }

         var10000 = 0;
         break;
      }

      var11 = var10000;

      boolean var14;
      while(true) {
         if (var11 < var7.length) {
            var14 = this.getOption(var7[var11]).equals("");
            if (var13 != 0) {
               break;
            }

            if (var14) {
               var9 = var9 + " " + var7[var11];
               var8 = true;
            }

            ++var11;
            if (var13 == 0) {
               continue;
            }
         }

         var14 = var8;
         break;
      }

      if (var14) {
         throw new i(var9);
      }
   }

   void a(String[] var1, String var2, String var3, String[] var4, String[] var5) throws h {
      int var17 = WorkItem.d;
      boolean var8 = false;
      String var9 = new String(d("-J\u0011\tW\r@G+T\tI\u0006\u0006_\bM\t\r\u001b\u0005V\u0000\u001dV\u0001J\u0013@HM\u001eG"));
      Hashtable var10 = new Hashtable();
      boolean var13 = true;
      Vector var14 = new Vector(1, 1);
      this.a = new Hashtable(1, 1.0F);
      int var6 = 0;

      int var10000;
      while(true) {
         if (var6 < var4.length) {
            var10000 = this.debug;
            if (var17 != 0) {
               break;
            }

            if (var10000 != 0) {
               System.out.println(d("\u0000A\u0005\u001d\\^\u0004+\u0007T\u000fM\t\u000f\u001b\u0002K\u0015HZ\u0016CGJ") + var4[var6] + "\"");
            }

            var10.put(var4[var6], Boolean.FALSE);
            ++var6;
            if (var17 == 0) {
               continue;
            }
         }

         var10000 = 0;
         break;
      }

      var6 = var10000;

      while(true) {
         if (var6 < var5.length) {
            var10000 = this.debug;
            if (var17 != 0) {
               break;
            }

            if (var10000 != 0) {
               System.out.println(d("\u0000A\u0005\u001d\\^\u0004+\u0007T\u000fM\t\u000f\u001b\u0002K\u0015HZ\u0016CGJ") + var5[var6] + "\"");
            }

            var10.put(var5[var6], Boolean.TRUE);
            ++var6;
            if (var17 == 0) {
               continue;
            }
         }

         var10000 = 0;
         break;
      }

      var6 = var10000;

      while(true) {
         if (var6 < var2.length()) {
            var10000 = this.debug;
            if (var17 != 0) {
               break;
            }

            if (var10000 != 0) {
               System.out.println(d("\u0000A\u0005\u001d\\^\u0004+\u0007T\u000fM\t\u000f\u001b\u0002K\u0015HZ\u0016CG") + (new Character(var2.charAt(var6))).toString());
            }

            var10.put((new Character(var2.charAt(var6))).toString(), Boolean.FALSE);
            ++var6;
            if (var17 == 0) {
               continue;
            }
         }

         var10000 = 0;
         break;
      }

      var6 = var10000;

      while(true) {
         if (var6 < var3.length()) {
            var10000 = this.debug;
            if (var17 != 0) {
               break;
            }

            if (var10000 != 0) {
               System.out.println(d("\u0000A\u0005\u001d\\^\u0004+\u0007T\u000fM\t\u000f\u001b\u0002K\u0015HZ\u0016CG") + (new Character(var3.charAt(var6))).toString());
            }

            var10.put((new Character(var3.charAt(var6))).toString(), Boolean.TRUE);
            ++var6;
            if (var17 == 0) {
               continue;
            }
         }

         var10000 = 0;
         break;
      }

      var6 = var10000;

      boolean var18;
      while(true) {
         if (var6 < var1.length) {
            String var11 = var1[var6];
            var18 = this.debug;
            if (var17 != 0) {
               break;
            }

            if (var18) {
               System.out.println(d("\u0000A\u0005\u001d\\^\u0004&\u001a\\D\u0006") + var11 + d("F\u001eG"));
            }

            label143: {
               if (var13) {
                  if (var11.equalsIgnoreCase(d("I\t"))) {
                     var13 = false;
                     if (var17 == 0) {
                        break label143;
                     }
                  }

                  int var7;
                  String var12;
                  if (var11.length() > 2 && var11.charAt(0) == '-' && var10.containsKey(var11.substring(1))) {
                     var7 = 0;
                     var11 = var11.substring(1);
                     if (this.debug) {
                        System.out.println(d("\u0000A\u0005\u001d\\^\u0004GHW\u000bK\f\u0001U\u0003\u0004\u0001\u0007IDM\t\u001c^\u0016J\u0006\u0004\u001b\u0002H\u0006\u000f\u001bF") + var11 + "\"");
                     }

                     label137: {
                        if (var10.containsKey(var11)) {
                           if (var10.get(var11) == Boolean.FALSE) {
                              this.a(var11);
                              if (var17 == 0) {
                                 break label137;
                              }
                           }

                           ++var7;
                           this.a(var11, var12 = this.a(var1, var6, var7));
                           if (!var12.equals("")) {
                              break label137;
                           }

                           --var7;
                           if (var17 == 0) {
                              break label137;
                           }
                        }

                        var9 = var9 + " " + var11;
                        var8 = true;
                     }

                     var6 += var7;
                     if (var17 == 0) {
                        break label143;
                     }
                  }

                  if (this.b(var11)) {
                     var7 = 0;
                     int var15 = 1;

                     while(true) {
                        if (var15 < var11.length()) {
                           String var16 = (new Character(var11.charAt(var15))).toString();
                           var10000 = var10.containsKey(var16);
                           if (var17 != 0) {
                              break;
                           }

                           label113: {
                              if (var10000 != 0) {
                                 if (var10.get(var16) == Boolean.FALSE) {
                                    this.a(var16);
                                    if (var17 == 0) {
                                       break label113;
                                    }
                                 }

                                 ++var7;
                                 this.a(var16, var12 = this.a(var1, var6, var7));
                                 if (!var12.equals("")) {
                                    break label113;
                                 }

                                 --var7;
                                 if (var17 == 0) {
                                    break label113;
                                 }
                              }

                              var9 = var9 + " " + var16;
                              var8 = true;
                           }

                           ++var15;
                           if (var17 == 0) {
                              continue;
                           }
                        }

                        var10000 = var6 + var7;
                        break;
                     }

                     var6 = var10000;
                     if (var17 == 0) {
                        break label143;
                     }
                  }

                  var14.addElement(var11);
                  if (!this.debug) {
                     break label143;
                  }

                  System.out.println(d("\u0000A\u0005\u001d\\^\u0004GHS\u0005WG\u0018Z\u0016E\nH") + var11);
                  if (var17 == 0) {
                     break label143;
                  }
               }

               var14.addElement(var11);
               if (this.debug) {
                  System.out.println(d("\u0000A\u0005\u001d\\^\u0004GHS\u0005WG\u0018Z\u0016E\nH") + var11);
               }
            }

            ++var6;
            if (var17 == 0) {
               continue;
            }
         }

         this.params = new String[var14.size()];
         var14.copyInto(this.params);
         var18 = var8;
         break;
      }

      if (var18) {
         throw new h(var9);
      } else {
         if (SnmpException.b) {
            ++var17;
            WorkItem.d = var17;
         }

      }
   }

   String a(String[] var1, int var2, int var3) {
      int var5 = WorkItem.d;
      int var4 = var2 + 1;

      while(true) {
         if (var4 <= var2 + var3) {
            if (var4 >= var1.length || this.b(var1[var4])) {
               if (this.debug) {
                  System.out.print(d("\u0000A\u0005\u001d\\^\u0004GH^\u0017G\u0006\u0018R\nCG\u0018Z\u0016E\n\u000eT\u0016\u0004\u0002\tI\b]"));
                  if (var4 >= var1.length) {
                     System.out.println(d("DG\u0012\u0012\u001b\u0005V\u0000\u0004R\u0017PG\u0001HDP\b\u0007\u001b\u0017L\b\u001aOJ"));
                     if (var5 == 0) {
                        return new String("");
                     }
                  }

                  System.out.println(d("DG\u0012\u0012\u001b\u0013AG\u0000R\u0010\u0004\u0006HU\u0001SG\u0007K\u0010\u0004\u000b\u0001H\u0010\n"));
               }

               return new String("");
            }

            ++var4;
            if (var5 == 0) {
               continue;
            }
         }

         return var1[var2 + var3];
      }
   }

   synchronized void a(String var1) {
      if (this.debug) {
         System.out.println(d("\u0000A\u0005\u001d\\^\u0004GHS\u0005WG\u000eW\u0005CG") + var1);
      }

      this.a.put(var1, new String(""));
   }

   synchronized void a(String var1, String var2) {
      if (this.debug) {
         System.out.println(d("\u0000A\u0005\u001d\\^\u0004GHS\u0005WG\u0007K\u0010M\b\u0006\u001b") + var1 + d("DS\u000e\u001cSDR\u0006\u0004N\u0001\u0004") + var2);
      }

      this.a.put(var1, var2);
   }

   boolean b(String var1) {
      if (this.debug) {
         System.out.println(d("\u0000A\u0005\u001d\\^\u0004GHX\u0005H\u000b\u0001U\u0003\u0004\u000e\u001bT\u0014P\u000b\u0001H\u0010\f") + var1 + ")");
      }

      return var1.length() > 1 && var1.charAt(0) == '-';
   }

   boolean c(String var1) {
      return var1.length() > 2 && var1.charAt(0) == '-' && var1.charAt(1) == '-';
   }

   public boolean hasOption(Character var1) {
      return this.hasOption(var1.toString());
   }

   public boolean hasOption(String var1) {
      return this.a.containsKey(var1);
   }

   public String getOption(Character var1) {
      return this.getOption(var1.toString());
   }

   public String getOption(String var1) {
      return (String)this.a.get(var1);
   }

   public int getIntOption(String var1, int var2) {
      int var5 = WorkItem.d;
      StringTokenizer var3 = new StringTokenizer(var1, d("^\bG"), false);

      int var10000;
      while(true) {
         if (var3.hasMoreTokens()) {
            String var4 = var3.nextToken();
            var10000 = this.hasOption(var4);
            if (var5 != 0) {
               break;
            }

            if (var10000 != 0) {
               return Integer.parseInt(this.getOption(var4));
            }

            if (var5 == 0) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000;
   }

   public boolean hasFlag(String var1) {
      int var4 = WorkItem.d;
      StringTokenizer var2 = new StringTokenizer(var1, d("^\bG"), false);

      boolean var10000;
      while(true) {
         if (var2.hasMoreTokens()) {
            String var3 = var2.nextToken();
            var10000 = this.hasOption(var3);
            if (var4 != 0) {
               break;
            }

            if (var10000) {
               return true;
            }

            if (var4 == 0) {
               continue;
            }
         }

         var10000 = false;
         break;
      }

      return var10000;
   }

   public String getOption(String var1, String var2) {
      int var5 = WorkItem.d;
      StringTokenizer var3 = new StringTokenizer(var1, d("^\bG"), false);

      String var10000;
      while(true) {
         if (var3.hasMoreTokens()) {
            var10000 = var3.nextToken();
            if (var5 != 0) {
               break;
            }

            String var4 = var10000;
            if (this.hasOption(var4)) {
               return this.getOption(var4);
            }

            if (var5 == 0) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000;
   }

   public static void test(String[] var0, String var1, String var2, String[] var3, String[] var4) {
      int var8 = WorkItem.d;
      System.out.println(d("2E\u000b\u0001_DW\u000f\u0007I\u0010\u0004\b\u0018O\rK\t\u001b\u001b\u0005V\u0002R\u001b") + var2 + d("D\f\u0010\u0001O\f\u0004\u0006\u001a\\\u0017\rG\tU\u0000\u0004") + var1 + d("D\f\u0010\u0001O\fK\u0012\u001c\u001b\u0005V\u0000\u001b\u0012J"));
      System.out.println(d("2E\u000b\u0001_DH\b\u0006\\DK\u0017\u001cR\u000bJ\u0014HZ\u0016A]H"));
      int var6 = 0;

      while(true) {
         if (var6 < var4.length) {
            System.out.println(d("D\u0004") + var4[var6]);
            ++var6;
            if (var8 != 0) {
               break;
            }

            if (var8 == 0) {
               continue;
            }
         }

         System.out.println(d("2E\u000b\u0001_DH\b\u0006\\DB\u000b\t\\\u0017\u0004\u0006\u001a^^\u0004"));
         break;
      }

      var6 = 0;

      while(true) {
         if (var6 < var3.length) {
            System.out.println(d("D\u0004") + var3[var6]);
            ++var6;
            if (var8 != 0) {
               break;
            }

            if (var8 == 0) {
               continue;
            }
         }

         System.out.print(d("6Q\t\u0006R\nCG\u001fR\u0010LG\u0007K\u0010M\b\u0006H^\u0004"));
         break;
      }

      var6 = 0;

      while(true) {
         if (var6 < var0.length) {
            System.out.print(var0[var6] + " ");
            ++var6;
            if (var8 != 0) {
               break;
            }

            if (var8 == 0) {
               continue;
            }
         }

         System.out.println(d("D\nIF"));
         break;
      }

      try {
         label129: {
            Commandline var10 = new Commandline(var0, var1, var2, var3, var4);
            var2 = var2 + var1;
            int var7 = 0;

            String var5;
            int var10000;
            label96: {
               while(var7 < var2.length()) {
                  var5 = var2.substring(var7, var7 + 1);
                  var10000 = var10.hasOption(var5);
                  if (var8 != 0) {
                     break label96;
                  }

                  label92: {
                     if (var10000 != 0) {
                        System.out.print(d("J\nIH]\u000bQ\t\f\u001b\u000bT\u0013\u0001T\n\u0004") + var5 + d("DS\u000e\u001cSDR\u0006\u0004N\u0001\u0004") + var10.getOption(var5) + "\n");
                        if (var8 == 0) {
                           break label92;
                        }
                     }

                     System.out.print(d("J\nIH_\r@\tOODB\u000e\u0006_DK\u0017\u001cR\u000bJG") + var5 + "\n");
                  }

                  ++var7;
                  if (var8 != 0) {
                     break;
                  }
               }

               var10000 = 0;
            }

            var7 = var10000;

            int var10001;
            while(true) {
               if (var7 < var4.length + var3.length) {
                  label132: {
                     var10000 = var7;
                     var10001 = var4.length;
                     if (var8 != 0) {
                        break;
                     }

                     label74: {
                        if (var7 < var10001) {
                           var5 = var4[var7];
                           if (var8 == 0) {
                              break label74;
                           }
                        }

                        var5 = var3[var7 - var4.length];
                     }

                     if (var10.hasOption(var5)) {
                        System.out.print(d("J\nIH]\u000bQ\t\f\u001b\u000bT\u0013\u0001T\n\u0004") + var5 + d("DS\u000e\u001cSDR\u0006\u0004N\u0001\u0004") + var10.getOption(var5) + "\n");
                        if (var8 == 0) {
                           ++var7;
                           if (var8 == 0) {
                              continue;
                           }
                           break label132;
                        }
                     }

                     System.out.print(d("J\nIH_\r@\tOODB\u000e\u0006_DK\u0017\u001cR\u000bJG") + var5 + "\n");
                     ++var7;
                     if (var8 == 0) {
                        continue;
                     }
                  }
               }

               System.out.print(d("J\nIH~\u001cP\u0015\t\u001b4E\u0015\tV\u0001P\u0002\u001aH^\u0004"));
               var7 = 0;
               var10000 = var7;
               var10001 = var10.params.length;
               break;
            }

            while(var10000 < var10001) {
               System.out.print(var10.params[var7] + " ");
               ++var7;
               if (var8 != 0) {
                  break label129;
               }

               if (var8 != 0) {
                  break;
               }

               var10000 = var7;
               var10001 = var10.params.length;
            }

            System.out.print("\n");
         }
      } catch (h var9) {
         System.out.println(d("'E\u0012\u000fS\u0010\u0004\u0006\u0006\u001b-J\u0011\tW\r@G\u000bT\tI\u0006\u0006_DH\u000e\u0006^DE\u0015\u000fN\tA\t\u001c\u0001n") + var9.toString() + "\n");
      }

      System.out.println(d(" K\t\r\u0015"));
   }

   private static String d(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 100;
               break;
            case 1:
               var10003 = 36;
               break;
            case 2:
               var10003 = 103;
               break;
            case 3:
               var10003 = 104;
               break;
            default:
               var10003 = 59;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
