package monfox.toolkit.snmp.mgr;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;

public class SnmpExplorer {
   private int a;
   private boolean b;
   private boolean c;
   private boolean d;
   private g e;
   private SnmpExplorerListener f;
   private SnmpSession g;
   private long h;
   private Hashtable i;
   private Vector j;
   private h k;
   private String l;
   private static Logger m = null;

   public SnmpExplorer(SnmpSession var1, SnmpExplorerListener var2) {
      this(var1, var2, 5000L);
   }

   public SnmpExplorer(SnmpSession var1, SnmpExplorerListener var2, long var3) {
      this();
      this.g = var1;
      this.f = var2;
      this.h = var3;
   }

   private SnmpExplorer() {
      this.a = 3;
      this.b = false;
      this.c = false;
      this.d = false;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = 3000L;
      this.i = new Hashtable();
      this.j = new Vector();
      this.k = null;
      this.l = null;
      if (m == null) {
         m = Logger.getInstance(a("-.A:\u001c\u00060@%+\u001b2"));
      }

      this.k = new h(this);
   }

   public synchronized void performExplore(SnmpParameters var1, SnmpVarBindList var2, String var3, String var4, int var5, long var6) throws IOException, SnmpTimeoutException, SnmpException {
      InetAddress var8 = InetAddress.getByName(var3);
      InetAddress var9 = InetAddress.getByName(var4);
      this.performExplore(var1, var2, var8, var9, var5, var6);
   }

   public synchronized void performExplore(SnmpParameters var1, SnmpVarBindList var2, InetAddress var3, InetAddress var4, int var5, long var6) throws IOException, SnmpTimeoutException, SnmpException {
      this.startExplore(var1, var2, var3, var4, var5);

      try {
         if (!this.b) {
            label34: {
               if (var6 <= 0L) {
                  this.wait();
                  if (!SnmpSession.B) {
                     break label34;
                  }
               }

               this.wait(var6);
            }
         }
      } catch (InterruptedException var9) {
         this.a();
         throw new SnmpTimeoutException(a(";8\\&6\f%\f\u001e0\u0013%C?-V\tB><\f2Y:-\u001b$\u0005"));
      }

      if (this.c) {
         throw new SnmpException(a(";8\\&6\f%\f\t8\u0010#I&5\u001b$"));
      } else if (!this.b) {
         this.a();
         throw new SnmpTimeoutException(a(";8\\&6\f%\f\u001e0\u0013%C?-"));
      }
   }

   public Thread startExplore(SnmpParameters var1, SnmpVarBindList var2, String var3, String var4, int var5) throws IOException, SnmpException {
      InetAddress var6 = InetAddress.getByName(var3);
      InetAddress var7 = InetAddress.getByName(var4);
      return this.startExplore(var1, var2, var6, var7, var5);
   }

   public Thread startExplore(SnmpParameters var1, SnmpVarBindList var2, InetAddress var3, InetAddress var4, int var5) throws IOException, SnmpException {
      if (this.e != null) {
         throw new SnmpException(a(";8\\&6\f%\f\u00037^\u0010^%>\f%_9"));
      } else {
         byte[] var6 = var3.getAddress();
         byte[] var7 = var4.getAddress();
         if ((var7[0] & 255) == 255 && (var7[1] & 255) == 255 && (var7[2] & 255) == 255 && (var7[3] & 255) == 255) {
            throw new SnmpException(a("7.Z+5\u0017$\f\u0004<\n\rM92D`") + var4.toString());
         } else {
            this.b = false;
            this.e = new g(this, var1, var2, var6, var7, var5);
            Thread var8 = new Thread(this.e);
            var8.start();
            return var8;
         }
      }
   }

   public boolean isExploring() {
      return this.e != null;
   }

   public String getExploringAddress() {
      return this.l;
   }

   public Thread startExplore(SnmpParameters var1, SnmpVarBindList var2, String var3, String var4, String var5, int var6) throws IOException, SnmpException {
      InetAddress var7 = InetAddress.getByName(var3);
      InetAddress var8 = InetAddress.getByName(var4);
      InetAddress var9 = InetAddress.getByName(var5);
      return this.startExplore(var1, var2, var7, var8, var9, var6);
   }

   public synchronized void performExplore(SnmpParameters var1, SnmpVarBindList var2, String var3, String var4, String var5, int var6, long var7) throws IOException, SnmpTimeoutException, SnmpException {
      InetAddress var9 = InetAddress.getByName(var3);
      InetAddress var10 = InetAddress.getByName(var4);
      InetAddress var11 = InetAddress.getByName(var5);
      this.performExplore(var1, var2, var9, var10, var11, var6, var7);
   }

   public synchronized void performExplore(SnmpParameters var1, SnmpVarBindList var2, InetAddress var3, InetAddress var4, InetAddress var5, int var6, long var7) throws IOException, SnmpTimeoutException, SnmpException {
      this.startExplore(var1, var2, var3, var4, var5, var6);

      try {
         if (!this.b) {
            label34: {
               if (var7 <= 0L) {
                  this.wait();
                  if (!SnmpSession.B) {
                     break label34;
                  }
               }

               this.wait(var7);
            }
         }
      } catch (InterruptedException var10) {
         this.a();
         throw new SnmpTimeoutException(a(";8\\&6\f%\f\u001e0\u0013%C?-V\tB><\f2Y:-\u001b$\u0005"));
      }

      if (this.c) {
         throw new SnmpException(a(";8\\&6\f%\f\t8\u0010#I&5\u001b$"));
      } else if (!this.b) {
         this.a();
         throw new SnmpTimeoutException(a(";8\\&6\f%\f\u001e0\u0013%C?-"));
      }
   }

   public Thread startExplore(SnmpParameters var1, SnmpVarBindList var2, InetAddress var3, InetAddress var4, InetAddress var5, int var6) throws IOException, SnmpException {
      if (this.e != null) {
         throw new SnmpException(a(";8\\&6\f%\f\u00037^\u0010^%>\f%_9"));
      } else {
         byte[] var7 = var3.getAddress();
         byte[] var8 = var4.getAddress();
         byte[] var9 = var5.getAddress();
         if ((var9[0] & 255) == 255 && (var9[1] & 255) == 255 && (var9[2] & 255) == 255 && (var9[3] & 255) == 255) {
            throw new SnmpException(a("7.Z+5\u0017$\f\u0004<\n\rM92D`") + var5.toString());
         } else {
            this.b = false;
            this.e = new g(this, var1, var2, var7, var8, var9, var6);
            Thread var10 = new Thread(this.e);
            var10.start();
            return var10;
         }
      }
   }

   public synchronized void cancel() {
      this.a();
      if (this.f != null) {
         this.f.handleCancelled(this);
      }

      this.notify();
   }

   void a() {
      this.c = true;
      this.e = null;
      Vector var1 = null;
      synchronized(this.j) {
         var1 = (Vector)this.j.clone();
         this.j.clear();
      }

      Enumeration var2 = var1.elements();

      while(var2.hasMoreElements()) {
         SnmpPendingRequest var3 = (SnmpPendingRequest)var2.nextElement();
         var3.cancel();
         if (SnmpSession.B) {
            break;
         }
      }

   }

   void a(SnmpParameters var1, SnmpVarBindList var2, byte[] var3, byte[] var4, int var5) {
      boolean var16 = SnmpSession.B;
      if (m.isDebugEnabled()) {
         m.debug(a("\r4M8-,![\u000f!\u000e,C8<D`") + this.j);
      }

      this.b = false;
      this.c = false;
      int var6 = a(var3);
      int var7 = a(var4);
      int var8 = 31;

      int var10000;
      while(true) {
         if (var8 >= 0) {
            var10000 = var7 >> var8 & 1;
            if (var16) {
               break;
            }

            if (var10000 != 0) {
               --var8;
               if (!var16) {
                  continue;
               }
            }
         }

         var10000 = (1 << var8 + 1) - 1;
         break;
      }

      int var9 = var10000;
      int var10 = ~var7 & -1;
      boolean var11 = false;
      this.d = true;
      int var12 = 1;

      SnmpExplorer var18;
      while(true) {
         if (var12 <= var9) {
            var18 = this;
            if (var16) {
               break;
            }

            if (!this.c) {
               if ((var12 & var7) == 0 && (var12 & 255) != 255) {
                  label73: {
                     int var13 = var12 + var6;
                     String var14 = a(var13);
                     if ((var13 & var10) == 0) {
                        m.debug(a("7'B%+\u0017.Kj\u0017\u001b4[%+\u0015`m.=\fz\f") + var14);
                        if (!var16) {
                           break label73;
                        }
                     }

                     if ((var13 & var10) == var10) {
                        m.debug(a("7'B%+\u0017.Kj\u001b\f/M.:\u001f3Xj\u0018\u001a$^py") + var14);
                        if (!var16) {
                           break label73;
                        }
                     }

                     try {
                        if (this.a > 0) {
                           Thread.sleep((long)this.a);
                        }

                        this.a(var14, var1, var2, var5);
                     } catch (Exception var17) {
                        m.warn(a("\f![\u000f!\u000e,C8<"), var17);
                     }
                  }
               }

               ++var12;
               if (!var16) {
                  continue;
               }
            }
         }

         this.d = false;
         this.b((SnmpPendingRequest)null);
         var18 = this;
         break;
      }

      var18.e = null;
   }

   void a(SnmpParameters var1, SnmpVarBindList var2, byte[] var3, byte[] var4, byte[] var5, int var6) {
      boolean var30 = SnmpSession.B;
      if (m.isDebugEnabled()) {
         m.debug(a("\r4M8-,![\u000f!\u000e,C8<D`") + this.j);
      }

      this.b = false;
      this.c = false;
      byte[] var7 = new byte[]{(byte)(var5[0] ^ 255), (byte)(var5[1] ^ 255), (byte)(var5[2] ^ 255), (byte)(var5[3] ^ 255)};
      int var8 = var3[0] & 255;
      int var9 = var4[0] & 255;
      this.d = true;
      int var10 = var8;

      SnmpExplorer var33;
      label208:
      while(true) {
         int var10000 = var10;

         label205:
         while(var10000 <= var9) {
            var33 = this;
            if (var30) {
               break label208;
            }

            if (this.c) {
               break;
            }

            boolean var11 = var10 == (var3[0] & 255);
            boolean var12 = var10 == (var4[0] & 255);
            int var13 = var11 ? var3[1] & 255 : 0;
            int var14 = var12 ? var4[1] & 255 : 255;
            int var15 = var13;

            label202:
            do {
               var10000 = var15;

               label199:
               while(true) {
                  if (var10000 > var14) {
                     break label202;
                  }

                  var10000 = this.c;
                  if (var30) {
                     continue label205;
                  }

                  if (var10000 != 0) {
                     break label202;
                  }

                  boolean var16 = var11 && var15 == (var3[1] & 255);
                  boolean var17 = var12 && var15 == (var4[1] & 255);
                  int var18 = var16 ? var3[2] & 255 : 0;
                  int var19 = var17 ? var4[2] & 255 : 255;
                  int var20 = var18;

                  while(true) {
                     var10000 = var20;

                     label194:
                     while(true) {
                        if (var10000 > var19) {
                           break label199;
                        }

                        var10000 = this.c;
                        if (var30) {
                           continue label199;
                        }

                        if (var10000 != 0) {
                           break label199;
                        }

                        boolean var21 = var16 && var20 == (var3[2] & 255);
                        boolean var22 = var17 && var20 == (var4[2] & 255);
                        int var23 = var21 ? var3[3] & 255 : 0;
                        int var24 = var22 ? var4[3] & 255 : 255;
                        int var25 = var23;

                        while(true) {
                           label218: {
                              if (var25 > var24) {
                                 break label194;
                              }

                              var10000 = this.c;
                              if (var30) {
                                 break;
                              }

                              if (var10000 != 0) {
                                 break label194;
                              }

                              byte[] var26 = new byte[]{(byte)var10, (byte)var15, (byte)var20, (byte)var25};
                              byte[] var27 = new byte[]{(byte)(var26[0] & var7[0] & 255), (byte)(var26[1] & var7[1] & 255), (byte)(var26[2] & var7[2] & 255), (byte)(var26[3] & var7[3] & 255)};
                              String var28 = "" + (var10 & 255) + "." + (var15 & 255) + "." + (var20 & 255) + "." + (var25 & 255);
                              if (var27[0] == 0 && var27[1] == 0 && var27[2] == 0 && var27[3] == 0) {
                                 m.debug(a("7'B%+\u0017.Kj\u0017\u001b4[%+\u0015`m.=\fz\f") + var28);
                                 if (!var30) {
                                    break label218;
                                 }
                              }

                              if (var27[0] == var7[0] && var27[1] == var7[1] && var27[2] == var7[2] && var27[3] == var7[3]) {
                                 m.debug(a("7'B%+\u0017.Kj\u001b\f/M.:\u001f3Xj\u0018\u001a$^j") + var28);
                                 if (!var30) {
                                    break label218;
                                 }
                              }

                              Exception var34;
                              label186: {
                                 boolean var10001;
                                 label185: {
                                    try {
                                       if (this.a > 0) {
                                          Thread.sleep((long)this.a);
                                       }

                                       if (!this.c) {
                                          break label185;
                                       }
                                    } catch (Exception var32) {
                                       var34 = var32;
                                       var10001 = false;
                                       break label186;
                                    }

                                    if (!var30) {
                                       break label194;
                                    }
                                 }

                                 try {
                                    this.a(var28, var1, var2, var6);
                                    break label218;
                                 } catch (Exception var31) {
                                    var34 = var31;
                                    var10001 = false;
                                 }
                              }

                              Exception var29 = var34;
                              m.warn(a("\f![\u000f!\u000e,C8<"), var29);
                           }

                           ++var25;
                           if (var30) {
                              break label194;
                           }
                        }
                     }

                     ++var20;
                     if (var30) {
                        break label199;
                     }
                  }
               }

               ++var15;
            } while(!var30);

            ++var10;
            if (!var30) {
               continue label208;
            }
            break;
         }

         this.d = false;
         this.b((SnmpPendingRequest)null);
         var33 = this;
         break;
      }

      var33.e = null;
   }

   private void a(String var1, SnmpParameters var2, SnmpVarBindList var3, int var4) throws IOException, SnmpException {
      SnmpPeer var5 = new SnmpPeer(var1, var4);
      var5.setParameters(var2);
      var5.setTimeout(this.h);
      var5.setMaxRetries(2);
      this.l = var1;
      if (m.isDebugEnabled()) {
         m.debug(a(";8\\&6\f)B-c^") + var5);
      }

      synchronized(this.j) {
         if (!this.c) {
            SnmpPendingRequest var7 = this.g.startGet(var5, this.k, var3);
            this.j.addElement(var7);
            if (m.isDebugEnabled()) {
               m.debug(a("?$H/=^0^py") + var7);
            }
         }

      }
   }

   private static int a(byte[] var0) {
      boolean var4 = SnmpSession.B;
      int var1 = 0;
      boolean var2 = false;
      int var3 = 0;

      int var10000;
      while(true) {
         if (var3 <= 3) {
            int var5 = var0[var3] & 255;
            var10000 = var1 | var5 << 8 * (3 - var3);
            if (var4) {
               break;
            }

            var1 = var10000;
            ++var3;
            if (!var4) {
               continue;
            }
         }

         var10000 = var1;
         break;
      }

      return var10000;
   }

   private static String a(int var0) {
      int var1 = var0 & 255;
      int var2 = var0 >> 8 & 255;
      int var3 = var0 >> 16 & 255;
      int var4 = var0 >> 24 & 255;
      StringBuffer var5 = new StringBuffer();
      var5.append(var4).append('.');
      var5.append(var3).append('.');
      var5.append(var2).append('.');
      var5.append(var1);
      return var5.toString();
   }

   void a(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (m.isDebugEnabled()) {
         m.debug(a("\u0016!B.5\u001b\u0012I9)\u0011._/c^") + var1);
      }

      if (this.f != null && !this.c) {
         this.f.handleDiscovered(this, var1.getPeer(), var1.getParameters(), var2, var3, var4);
      }

      this.b(var1);
   }

   void b(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (m.isDebugEnabled()) {
         m.debug(a("\u0016!B.5\u001b\u0012I:6\f4\u0016j") + var1);
      }

      if (this.f != null && !this.c) {
         this.f.handleDiscovered(this, var1.getPeer(), var1.getParameters(), var2, var3, var4);
      }

      this.b(var1);
   }

   void a(SnmpPendingRequest var1) {
      if (m.isDebugEnabled()) {
         m.debug(a("\u0016!B.5\u001b\u0014E'<\u00115Xpy") + var1);
      }

      this.b(var1);
   }

   void a(SnmpPendingRequest var1, Exception var2) {
      if (m.isDebugEnabled()) {
         m.debug(a("\u0016!B.5\u001b\u0005T)<\u000e4E%7D`") + var1, var2);
      }

      var1.cancel();
      this.b(var1);
   }

   private synchronized void b(SnmpPendingRequest var1) {
      if (m.isDebugEnabled()) {
         m.debug(a("\f%A%/\u001b\u0010~py") + var1);
      }

      boolean var2 = false;
      synchronized(this.j) {
         if (var1 != null) {
            this.j.removeElement(var1);
            if (m.isDebugEnabled()) {
               m.debug(a("Sm\f8<\u0013/Z/=D`") + var1);
            }
         }

         if (this.j.size() == 0 && !this.d && !this.c) {
            if (m.isDebugEnabled()) {
               m.debug(a("Sm\f)6\u00130@/-\u001b$\u0016j") + var1);
            }

            var2 = true;
         }
      }

      if (var2) {
         if (this.f != null) {
            this.f.handleCompleted(this);
         }

         this.b = true;
         this.notify();
      }

   }

   public static void main(String[] var0) {
      f var1 = new f();
      var1.run(var0);
   }

   public void setDelayPeriod(int var1) {
      this.a = var1;
   }

   public int getDelayPeriod() {
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
               var10003 = 126;
               break;
            case 1:
               var10003 = 64;
               break;
            case 2:
               var10003 = 44;
               break;
            case 3:
               var10003 = 74;
               break;
            default:
               var10003 = 89;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
