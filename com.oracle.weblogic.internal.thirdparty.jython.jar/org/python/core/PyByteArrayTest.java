package org.python.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.python.util.PythonInterpreter;

public class PyByteArrayTest extends BaseBytesTest {
   public PyByteArrayTest(String name) {
      super(name);
   }

   public static int[] patternInts(int na, int nd, int nb, String c) {
      int[] r = new int[na + nd + nb];
      int p = 0;

      int i;
      for(i = 0; i < na; ++i) {
         r[p++] = c.charAt(0);
      }

      for(i = 0; i < nd; ++i) {
         r[p++] = c.charAt(1);
      }

      for(i = 0; i < nb; ++i) {
         r[p++] = c.charAt(2);
      }

      return r;
   }

   public static int[] adbInts(int na, int nd, int nb) {
      return patternInts(na, nd, nb, "aDb");
   }

   public static int[] aebInts(int na, int ne, int nb) {
      return patternInts(na, ne, nb, "aEb");
   }

   public static int[][] randomSliceProblem(Random random, int na, int nd, int nb, int ne) {
      int[] adb = new int[na + nd + nb];
      int[] aeb = new int[na + ne + nb];
      int[] e = new int[ne];
      int[][] ret = new int[][]{adb, aeb, e};
      int p = 0;
      int q = 0;

      int i;
      int b;
      for(i = 0; i < na; ++i) {
         b = random.nextInt(256);
         adb[p++] = b;
         aeb[q++] = b;
      }

      for(i = 0; i < nd; ++i) {
         b = random.nextInt(256);
         adb[p++] = b;
      }

      for(i = 0; i < ne; ++i) {
         b = random.nextInt(256);
         e[p++] = b;
         aeb[q++] = b;
      }

      for(i = 0; i < nb; ++i) {
         b = random.nextInt(256);
         adb[p++] = b;
         aeb[q++] = b;
      }

      return ret;
   }

   public static void checkSlice(int na, int nd, int nb, int ne, int[] x, int[] y, BaseBytes result) {
      assertEquals("size", na + ne + nb, result.size());
      checkInts(x, 0, result, 0, na);
      checkInts(y, 0, result, na, ne);
      checkInts(x, na + nd, result, na + ne, nb);
   }

   public static void checkSlice(int start, int step, int n, int[] x, int[] y, BaseBytes u) {
      assertEquals("size", x.length, u.size());
      int px;
      int py;
      int i;
      int j;
      if (step > 0) {
         px = 0;

         for(py = 0; px < start; ++px) {
            assertEquals("before slice", x[px], u.intAt(px));
         }

         if (n > 0) {
            assertEquals("first affected", y[py++], u.intAt(px++));
         }

         for(i = 1; i < n; ++i) {
            for(j = 1; j < step; ++px) {
               assertEquals("in gap", x[px], u.intAt(px));
               ++j;
            }

            assertEquals("next affected", y[py++], u.intAt(px++));
         }

         while(px < x.length) {
            assertEquals("after slice", x[px], u.intAt(px));
            ++px;
         }
      } else {
         step = -step;
         px = x.length - 1;

         for(py = 0; px > start; --px) {
            assertEquals("after slice", x[px], u.intAt(px));
         }

         if (n > 0) {
            assertEquals("first affected", y[py++], u.intAt(px--));
         }

         for(i = 1; i < n; ++i) {
            for(j = 1; j < step; --px) {
               assertEquals("in gap", x[px], u.intAt(px));
               ++j;
            }

            assertEquals("next affected", y[py++], u.intAt(px--));
         }

         while(px >= 0) {
            assertEquals("before slice", x[px], u.intAt(px));
            --px;
         }
      }

   }

   public static void checkDelSlice(int start, int step, int n, int[] x, BaseBytes u) {
      assertEquals("size", x.length - n, u.size());
      int px;
      int pu;
      int i;
      int j;
      if (step > 0) {
         px = 0;

         for(pu = 0; px < start; ++px) {
            assertEquals("before slice", x[px], u.intAt(pu++));
         }

         ++px;

         for(i = 1; i < n; ++i) {
            for(j = 1; j < step; ++px) {
               assertEquals("in gap", x[px], u.intAt(pu++));
               ++j;
            }

            ++px;
         }

         while(px < x.length) {
            assertEquals("after slice", x[px], u.intAt(pu++));
            ++px;
         }
      } else {
         step = -step;
         px = x.length - 1;

         for(pu = u.size - 1; px > start; --px) {
            assertEquals("after slice", x[px], u.intAt(pu--));
         }

         --px;

         for(i = 1; i < n; ++i) {
            for(j = 1; j < step; --px) {
               assertEquals("in gap", x[px], u.intAt(pu--));
               ++j;
            }

            --px;
         }

         while(px >= 0) {
            assertEquals("before slice", x[px], u.intAt(pu--));
            --px;
         }
      }

   }

   protected void setUp() throws Exception {
      super.setUp();
   }

   public void test__getslice__2() {
      int verbose = 0;
      String ver = "L'un a la pourpre de nos âmes";
      int L = ver.length();
      int[] aRef = toInts(ver);
      BaseBytes a = this.getInstance(aRef);
      List bList = new ArrayList(L);
      this.interp = new PythonInterpreter();
      int[] posStart = new int[]{0, 8, 16, L - 5, L - 1};
      int[] var8 = posStart;
      int var9 = posStart.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         int start = var8[var10];
         PyObject pyStart = new PyInteger(start);
         Object pyStart_L;
         if (start > 0) {
            pyStart_L = new PyInteger(start - L);
         } else {
            pyStart_L = Py.None;
         }

         for(int stop = start; stop <= L; ++stop) {
            bList.clear();

            for(int i = start; i < stop; ++i) {
               if (verbose >= 5) {
                  System.out.printf("    (%d,%d) i=%d\n", start, stop, i);
               }

               bList.add(new PyInteger(aRef[i]));
            }

            Object pyStop_L;
            if (stop < L) {
               pyStop_L = new PyInteger(stop - L);
            } else {
               pyStop_L = Py.None;
            }

            PyObject pyStop = new PyInteger(stop);
            this.doTest__getslice__2(a, pyStart, pyStop, bList, verbose + 2);
            this.doTest__getslice__2(a, (PyObject)pyStart_L, pyStop, bList, verbose);
            this.doTest__getslice__2(a, pyStart, (PyObject)pyStop_L, bList, verbose);
            this.doTest__getslice__2(a, (PyObject)pyStart_L, (PyObject)pyStop_L, bList, verbose);
         }
      }

   }

   private void doTest__getslice__2(BaseBytes a, PyObject pyStart, PyObject pyStop, List bList, int verbose) {
      if (verbose >= 4) {
         System.out.printf("    __getslice__(%s,%s)\n", pyStart, pyStop);
      }

      PyObject b = a.__getslice__(pyStart, pyStop);
      if (verbose >= 3) {
         System.out.println(this.toString((BaseBytes)b));
      }

      checkInts(bList, b);
   }

   public void test__getslice__3() {
      int verbose = 0;
      String ver = "Quand je brûle et que tu t'enflammes ;";
      int L = ver.length();
      int[] aRef = toInts(ver);
      BaseBytes a = this.getInstance(aRef);
      List bList = new ArrayList(L);
      this.interp = new PythonInterpreter();
      int[] posStart = new int[]{0, 9, 22, L - 11, L - 1};

      int var12;
      int start;
      int stop;
      Object pyStart_L;
      PyInteger pyStart;
      for(int step = 1; step < 4; ++step) {
         PyInteger pyStep = new PyInteger(step);
         int[] var10 = posStart;
         int var11 = posStart.length;

         for(var12 = 0; var12 < var11; ++var12) {
            start = var10[var12];
            PyObject pyStart = new PyInteger(start);
            Object pyStart_L;
            if (start > 0) {
               pyStart_L = new PyInteger(start - L);
            } else {
               pyStart_L = Py.None;
            }

            for(int stop = start; stop <= L; ++stop) {
               bList.clear();

               for(stop = start; stop < stop; stop += step) {
                  if (verbose >= 5) {
                     System.out.printf("    (%d,%d,%d) i=%d\n", start, stop, step, stop);
                  }

                  bList.add(new PyInteger(aRef[stop]));
               }

               if (stop < L) {
                  pyStart_L = new PyInteger(stop - L);
               } else {
                  pyStart_L = Py.None;
               }

               pyStart = new PyInteger(stop);
               this.doTest__getslice__3(a, pyStart, pyStart, pyStep, bList, verbose + 2);
               this.doTest__getslice__3(a, (PyObject)pyStart_L, pyStart, pyStep, bList, verbose);
               this.doTest__getslice__3(a, pyStart, (PyObject)pyStart_L, pyStep, bList, verbose);
               this.doTest__getslice__3(a, (PyObject)pyStart_L, (PyObject)pyStart_L, pyStep, bList, verbose);
            }
         }
      }

      int[] negStart = new int[]{0, 5, 14, L - 10, L - 1};

      for(int step = -1; step > -4; --step) {
         PyInteger pyStep = new PyInteger(step);
         int[] var24 = negStart;
         var12 = negStart.length;

         for(start = 0; start < var12; ++start) {
            int start = var24[start];
            pyStart = new PyInteger(start);
            if (start < L - 1) {
               pyStart_L = new PyInteger(start - L);
            } else {
               pyStart_L = Py.None;
            }

            for(stop = start; stop >= -1; --stop) {
               bList.clear();

               for(int i = start; i > stop; i += step) {
                  if (verbose >= 5) {
                     System.out.printf("    (%d,%d,%d) i=%d\n", start, stop, step, i);
                  }

                  bList.add(new PyInteger(aRef[i]));
               }

               Object pyStop_L;
               if (stop >= 0) {
                  pyStop_L = new PyInteger(stop - L);
               } else {
                  stop = -(L + 1);
                  pyStop_L = Py.None;
               }

               PyObject pyStop = new PyInteger(stop);
               this.doTest__getslice__3(a, pyStart, pyStop, pyStep, bList, verbose + 2);
               this.doTest__getslice__3(a, (PyObject)pyStart_L, pyStop, pyStep, bList, verbose);
               this.doTest__getslice__3(a, pyStart, (PyObject)pyStop_L, pyStep, bList, verbose);
               this.doTest__getslice__3(a, (PyObject)pyStart_L, (PyObject)pyStop_L, pyStep, bList, verbose);
            }
         }
      }

   }

   private void doTest__getslice__3(BaseBytes a, PyObject pyStart, PyObject pyStop, PyObject pyStep, List bList, int verbose) {
      if (verbose >= 4) {
         System.out.printf("    __getslice__(%s,%s,%s)\n", pyStart, pyStop, pyStep);
      }

      PyObject b = a.__getslice__(pyStart, pyStop, pyStep);
      if (verbose >= 3) {
         System.out.println(this.toString((BaseBytes)b));
      }

      checkInts(bList, b);
   }

   public void testPyset() {
      int verbose = 0;
      this.interp = new PythonInterpreter();
      int[] aRef = randomInts(this.random, 25);
      BaseBytes a = this.getInstance(aRef);

      int ai;
      for(int i = 0; i < 25; ++i) {
         int b = aRef[i] ^ 85;
         PyInteger pyb = new PyInteger(b);
         a.__setitem__(i, pyb);
         ai = a.pyget(i).asInt();
         if (verbose >= 3) {
            System.out.printf("    __setitem__(%2d,%3d) : a[%2d]=%3d\n", i, b, i, ai);
         }

         assertEquals(b, ai);
      }

      int[] badValue = new int[]{256, Integer.MAX_VALUE, -1, -2, -100, -65536, Integer.MIN_VALUE};
      int[] var14 = badValue;
      int var16 = badValue.length;

      int i;
      for(ai = 0; ai < var16; ++ai) {
         i = var14[ai];
         PyInteger b = new PyInteger(i);

         try {
            a.__setitem__(0, b);
            fail("Exception not thrown for __setitem__(0, " + b + ")");
         } catch (PyException var12) {
            assertEquals(Py.ValueError, var12.type);
            if (verbose >= 2) {
               System.out.printf("    Exception: %s\n", var12);
            }
         }
      }

      PyInteger x = new PyInteger(10);
      int[] var17 = new int[]{-26, -125, 25, 26};
      ai = var17.length;

      for(i = 0; i < ai; ++i) {
         int i = var17[i];

         try {
            a.__setitem__(i, x);
            fail("Exception not thrown for __setitem__(" + i + ", x)");
         } catch (PyException var11) {
            assertEquals(Py.IndexError, var11.type);
            if (verbose >= 2) {
               System.out.printf("    Exception: %s\n", var11);
            }
         }
      }

   }

   public void testSetslice2() {
      int verbose = 0;
      int[] naList = new int[]{2, 5, 0};
      int[] ndList = new int[]{5, 20, 0};
      int[] nbList = new int[]{4, 7, 0};
      int[] neList = new int[]{4, 5, 6, 20, 0};
      int[] var6 = neList;
      int var7 = neList.length;

      int[] xInts;
      int na;
      int[] var16;
      int var17;
      int var18;
      int nb;
      int ne;
      for(int var8 = 0; var8 < var7; ++var8) {
         int ne = var6[var8];
         xInts = new int[ne];
         Arrays.fill(xInts, 69);
         PyByteArray e = new PyByteArray(xInts);
         int[] var12 = ndList;
         int var13 = ndList.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            na = var12[var14];
            var16 = naList;
            var17 = naList.length;

            for(var18 = 0; var18 < var17; ++var18) {
               nb = var16[var18];
               int[] var20 = nbList;
               ne = nbList.length;

               for(int var22 = 0; var22 < ne; ++var22) {
                  int nb = var20[var22];
                  int[] aRef = adbInts(nb, na, nb);
                  int[] bRef = aebInts(nb, ne, nb);
                  PyByteArray b = this.getInstance(aRef);
                  byte[] oldStorage = b.storage;
                  if (verbose >= 2) {
                     System.out.printf("setslice(%d,%d,%d,e[len=%d])\n", nb, nb + na, 1, ne);
                     if (verbose >= 3) {
                        System.out.println(this.toString(b));
                     }
                  }

                  b.setslice(nb, nb + na, 1, (PyObject)e);
                  if (verbose >= 2) {
                     boolean avAlloc = b.storage != oldStorage && bRef.length <= oldStorage.length;
                     if (b.storage.length * 2 < oldStorage.length) {
                        avAlloc = false;
                     }

                     System.out.println(this.toString(b) + (avAlloc ? " avoidable new" : ""));
                  }

                  checkInts(bRef, b);
               }
            }
         }
      }

      int AMAX = true;
      int BMAX = true;
      int DMAX = true;
      int EMAX = true;
      xInts = randomInts(this.random, 39, 117, 122);
      int[] yInts = randomInts(this.random, 25, 65, 72);
      PyByteArray x = this.getInstance(xInts);
      PyByteArray y = this.getInstance(yInts);
      int[] nbList2 = new int[]{0, 1, 7};

      for(na = 0; na <= 7; ++na) {
         var16 = nbList2;
         var17 = nbList2.length;

         for(var18 = 0; var18 < var17; ++var18) {
            nb = var16[var18];

            for(int nd = 0; nd < 25; ++nd) {
               for(ne = 0; ne < 25; ++ne) {
                  PyByteArray u = x.getslice(0, na + nd + nb, 1);
                  PyByteArray e = y.getslice(0, ne, 1);
                  if (verbose >= 2) {
                     System.out.printf("setslice(start=%d, stop=%d, step=%d, e[len=%d])\n", na, na + nd, 1, ne);
                     if (verbose >= 3) {
                        System.out.println("u = " + this.toString(u));
                        System.out.println("e = " + this.toString(e));
                     }
                  }

                  u.setslice(na, na + nd, 1, (PyObject)e);
                  if (verbose >= 1) {
                     System.out.println("u'= " + this.toString(u));
                  }

                  checkSlice(na, nd, nb, ne, xInts, yInts, u);
               }
            }
         }
      }

   }

   public void test__setslice__2() {
      int verbose = 0;
      String ver = "Cet autre affecte tes langueurs";
      int L = ver.length();
      int[] uRef = toInts(ver);
      this.interp = new PythonInterpreter();
      int[] eRef = randomInts(this.random, 14, 86, 90);
      BaseBytes eFull = new BaseBytesTest.MyBytes(eRef);
      int[] posStart = new int[]{0, 4, 10, 18, L - 9};
      int[] posStop = new int[]{0, 3, 9, 17, L - 10, L};
      int[] var9 = posStart;
      int var10 = posStart.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         int start = var9[var11];
         PyObject pyStart = new PyInteger(start);
         Object pyStart_L;
         if (start > 0) {
            pyStart_L = new PyInteger(start - L);
         } else {
            pyStart_L = Py.None;
         }

         int[] var15 = posStop;
         int var16 = posStop.length;

         for(int var17 = 0; var17 < var16; ++var17) {
            int stop = var15[var17];
            if (stop >= start) {
               Object pyStop_L;
               if (stop < L) {
                  pyStop_L = new PyInteger(stop - L);
               } else {
                  pyStop_L = Py.None;
               }

               PyObject pyStop = new PyInteger(stop);

               for(int n = 0; n <= eRef.length; ++n) {
                  this.doTest__setslice__2(uRef, pyStart, pyStop, eFull, n, eRef, start, stop, verbose + 2);
                  this.doTest__setslice__2(uRef, (PyObject)pyStart_L, pyStop, eFull, n, eRef, start, stop, verbose);
                  this.doTest__setslice__2(uRef, pyStart, (PyObject)pyStop_L, eFull, n, eRef, start, stop, verbose);
                  this.doTest__setslice__2(uRef, (PyObject)pyStart_L, (PyObject)pyStop_L, eFull, n, eRef, start, stop, verbose);
               }
            }
         }
      }

   }

   private void doTest__setslice__2(int[] uRef, PyObject pyStart, PyObject pyStop, BaseBytes eFull, int n, int[] eRef, int start, int stop, int verbose) {
      PyByteArray u = this.getInstance(uRef);
      BaseBytes e = eFull.getslice(0, n, 1);
      if (verbose >= 4) {
         System.out.printf("    __setslice__(%s,%s,e[0:%d])\n", pyStart, pyStop, n);
         System.out.println("u = " + this.toString(u));
         System.out.println("e = " + this.toString(e));
      }

      u.__setslice__(pyStart, pyStop, e);
      if (verbose >= 3) {
         System.out.println("u'= " + this.toString(u));
      }

      int nd = stop - start;
      int nb = uRef.length - stop;
      checkSlice(start, nd, nb, n, uRef, eRef, u);
   }

   public void testSetslice3() {
      int verbose = 0;
      this.interp = new PythonInterpreter();
      int[] eRef = randomInts(this.random, 25, 65, 72);
      BaseBytes eFull = new BaseBytesTest.MyBytes(eRef);
      int[] uRef = randomInts(this.random, 25, 109, 115);
      int[] posStep = new int[]{2, 3, 5, 8, 25, 100};

      int start;
      int var10;
      int step;
      int step;
      int n;
      int n;
      int last;
      for(int start = 0; start < uRef.length; ++start) {
         start = uRef.length - start;
         int[] var8 = posStep;
         int var9 = posStep.length;

         for(var10 = 0; var10 < var9; ++var10) {
            step = var8[var10];
            step = (start + step - 1) / step;

            for(n = 1; n <= step; ++n) {
               n = start + step * (n - 1) + 1;

               for(last = n + 1; last < n + step; ++last) {
                  PyByteArray u = this.getInstance(uRef);
                  BaseBytes e = eFull.getslice(0, n, 1);
                  if (verbose >= 2) {
                     System.out.printf("setslice(start=%d, stop=%d, step=%d, e[len=%d])\n", start, last, step, n);
                     if (verbose >= 3) {
                        System.out.println("u = " + this.toString(u));
                        System.out.println("e = " + this.toString(e));
                     }
                  }

                  u.setslice(start, last, step, (PyObject)e);
                  if (verbose >= 1) {
                     System.out.println("u'= " + this.toString(u));
                  }

                  checkSlice(start, step, n, uRef, eRef, u);
               }
            }
         }
      }

      int[] negStep = new int[]{-1, -2, -5, -8, -25, -100};

      for(start = uRef.length - 1; start >= 0; --start) {
         int len = start + 1;
         int[] var21 = negStep;
         var10 = negStep.length;

         for(step = 0; step < var10; ++step) {
            step = var21[step];
            n = (len + -step - 1) / -step;

            for(n = 1; n <= n; ++n) {
               last = start + step * (n - 1) - 1;

               for(int stop = last; stop > last - -step && stop >= 0; --stop) {
                  PyByteArray u = this.getInstance(uRef);
                  BaseBytes e = eFull.getslice(0, n, 1);
                  if (verbose >= 2) {
                     System.out.printf("setslice(start=%d, stop=%d, step=%d, e[len=%d])\n", start, stop, step, n);
                     if (verbose >= 3) {
                        System.out.println("u = " + this.toString(u));
                        System.out.println("e = " + this.toString(e));
                     }
                  }

                  u.setslice(start, stop, step, (PyObject)e);
                  if (verbose >= 1) {
                     System.out.println("u'= " + this.toString(u));
                  }

                  checkSlice(start, step, n, uRef, eRef, u);
               }
            }
         }
      }

   }

   public void test__setslice__3() {
      int verbose = 0;
      this.interp = new PythonInterpreter();
      int[] eRef = randomInts(this.random, 25, 65, 72);
      BaseBytes eFull = new BaseBytesTest.MyBytes(eRef);
      int[] uRef = randomInts(this.random, 25, 109, 115);
      int[] posStep = new int[]{2, 3, 5, 8, 25, 100};

      int var11;
      int step;
      int n;
      int n;
      int last;
      for(int start = 0; start < uRef.length; ++start) {
         PyInteger pyStart = new PyInteger(start);
         int len = uRef.length - start;
         int[] var9 = posStep;
         int var10 = posStep.length;

         for(var11 = 0; var11 < var10; ++var11) {
            step = var9[var11];
            PyInteger pyStep = new PyInteger(step);
            int nmax = (len + step - 1) / step;

            for(n = 1; n <= nmax; ++n) {
               n = start + step * (n - 1) + 1;

               for(last = n + 1; last < n + step; ++last) {
                  PyInteger pyStop = new PyInteger(last);
                  PyByteArray u = this.getInstance(uRef);
                  BaseBytes e = eFull.getslice(0, n, 1);
                  if (verbose >= 2) {
                     System.out.printf("setslice(%d,%d,%d, e[len=%d])\n", start, last, step, n);
                     if (verbose >= 3) {
                        System.out.println("u = " + this.toString(u));
                        System.out.println("e = " + this.toString(e));
                     }
                  }

                  u.__setslice__(pyStart, pyStop, pyStep, e);
                  if (verbose >= 1) {
                     System.out.println("u'= " + this.toString(u));
                  }

                  checkSlice(start, step, n, uRef, eRef, u);
               }
            }
         }
      }

      int[] negStep = new int[]{-1, -2, -5, -8, -25, -100};

      for(int start = uRef.length - 1; start >= 0; --start) {
         PyInteger pyStart = new PyInteger(start);
         int len = start + 1;
         int[] var26 = negStep;
         var11 = negStep.length;

         for(step = 0; step < var11; ++step) {
            int step = var26[step];
            PyInteger pyStep = new PyInteger(step);
            n = (len + -step - 1) / -step;

            for(n = 1; n <= n; ++n) {
               last = start + step * (n - 1) - 1;

               for(int stop = last; stop > last - -step && stop >= 0; --stop) {
                  PyInteger pyStop = new PyInteger(stop);
                  PyByteArray u = this.getInstance(uRef);
                  BaseBytes e = eFull.getslice(0, n, 1);
                  if (verbose >= 2) {
                     System.out.printf("setslice(%d,%d,%d, e[len=%d])\n", start, stop, step, n);
                     if (verbose >= 3) {
                        System.out.println("u = " + this.toString(u));
                        System.out.println("e = " + this.toString(e));
                     }
                  }

                  u.__setslice__(pyStart, pyStop, pyStep, e);
                  if (verbose >= 1) {
                     System.out.println("u'= " + this.toString(u));
                  }

                  checkSlice(start, step, n, uRef, eRef, u);
               }
            }
         }
      }

   }

   public void testSetsliceTime() {
      int verbose = 1;
      this.timeSetslice(50, 100, 7, 14, verbose);
      this.timeSetslice(50, 100, 25, 25, verbose);
      this.timeSetslice(500, 20, 2000, 400, verbose);
   }

   private void timeSetslice(int trials, int repeats, int N, int M, int verbose) {
      int[] startList = new int[11];

      for(int i = 0; i < startList.length; ++i) {
         startList[i] = N * i / (startList.length - 1);
      }

      int[] changeList = new int[11];

      for(int i = 0; i < changeList.length; ++i) {
         changeList[i] = M * i / (changeList.length - 1);
      }

      long[][] elapsed = new long[startList.length][changeList.length];

      for(int row = 0; row < startList.length; ++row) {
         for(int col = 0; col < changeList.length; ++col) {
            elapsed[row][col] = Long.MAX_VALUE;
         }
      }

      int[] xRef = randomInts(this.random, N, 117, 122);
      PyByteArray x = this.getInstance(xRef);
      int[] yRef = randomInts(this.random, M, 65, 72);
      PyByteArray y = this.getInstance(yRef);
      PyByteArray[] u = new PyByteArray[repeats];

      int row;
      int col;
      for(row = 0; row < trials; ++row) {
         for(col = 0; col < startList.length; ++col) {
            int row = (col + 5 * row) % startList.length;
            int na = startList[row];
            int nd = 0;

            for(int icol = 0; icol < changeList.length; ++icol) {
               int col = (icol + row) % changeList.length;
               int ne = changeList[col];
               int stop = na + nd;
               PyByteArray e = y.getslice(0, ne, 1);
               if (row == 0) {
                  this.doTimeSetslice(u, na, stop, e, x, verbose);
                  checkSlice(na, nd, N - (na + nd), ne, xRef, yRef, u[0]);
               }

               long t = this.doTimeSetslice(u, na, stop, e, x, -1);
               if (t < elapsed[row][col]) {
                  elapsed[row][col] = t;
               }
            }
         }
      }

      if (verbose >= 1) {
         System.out.print("     N  ,     na  ");

         for(row = 0; row < changeList.length; ++row) {
            System.out.printf(", ne=%7d", changeList[row]);
         }

         System.out.println(", elements inserted: time in microseconds.");

         for(row = 0; row < startList.length; ++row) {
            System.out.printf("%8d, %8d", N, startList[row]);

            for(col = 0; col < changeList.length; ++col) {
               double usPerCall = 0.001 * (double)elapsed[row][col] / (double)repeats;
               System.out.printf(", %10.3f", usPerCall);
            }

            System.out.println();
         }
      }

   }

   private long doTimeSetslice(PyByteArray[] u, int start, int stop, BaseBytes e, BaseBytes x, int verbose) {
      int repeats = 1;
      if (verbose < 0) {
         repeats = u.length;
      }

      for(int i = 0; i < repeats; ++i) {
         u[i] = new PyByteArray(x);
      }

      PyByteArray v = u[0];
      byte[] oldStorage = v.storage;
      if (verbose >= 3) {
         System.out.printf("setslice(%d,%d,%d,e[%d])\n", start, stop, 1, e.size());
         System.out.println("u = " + this.toString(v));
         System.out.println("e = " + this.toString(e));
      }

      long beginTime = System.nanoTime();

      for(int i = 0; i < repeats; ++i) {
         u[i].setslice(start, stop, 1, (PyObject)e);
      }

      long t = System.nanoTime() - beginTime;
      if (verbose >= 2) {
         boolean avAlloc = v.storage != oldStorage;
         if (v.size * 2 <= oldStorage.length) {
            avAlloc = false;
         }

         if (v.size > oldStorage.length) {
            avAlloc = false;
         }

         System.out.println("u'= " + this.toString(v) + (avAlloc ? " new" : ""));
      }

      return t;
   }

   public void testDelslice2() {
      int verbose = 0;
      int[] naList = new int[]{2, 5, 0};
      int[] ndList = new int[]{5, 20, 0};
      int[] nbList = new int[]{4, 7, 0};
      int[] var5 = ndList;
      int var6 = ndList.length;

      int na;
      int var14;
      int nb;
      int nd;
      for(int var7 = 0; var7 < var6; ++var7) {
         int nd = var5[var7];
         int[] var9 = naList;
         int var10 = naList.length;

         for(na = 0; na < var10; ++na) {
            int na = var9[na];
            int[] var13 = nbList;
            var14 = nbList.length;

            for(nb = 0; nb < var14; ++nb) {
               nd = var13[nb];
               int[] aRef = adbInts(na, nd, nd);
               int[] bRef = aebInts(na, 0, nd);
               PyByteArray b = this.getInstance(aRef);
               byte[] oldStorage = b.storage;
               if (verbose >= 2) {
                  System.out.printf("delslice(%d,%d,%d,%d)\n", na, na + nd, 1, nd);
                  if (verbose >= 3) {
                     System.out.println(this.toString(b));
                  }
               }

               b.delslice(na, na + nd, 1, nd);
               if (verbose >= 2) {
                  boolean avAlloc = b.storage != oldStorage;
                  if (bRef.length * 2 <= oldStorage.length) {
                     avAlloc = false;
                  }

                  System.out.println(this.toString(b) + (avAlloc ? " avoidable new" : ""));
               }

               checkInts(bRef, b);
            }
         }
      }

      int AMAX = true;
      int BMAX = true;
      int DMAX = true;
      int[] xInts = randomInts(this.random, 39, 117, 122);
      PyByteArray x = this.getInstance(xInts);
      int[] nbList2 = new int[]{0, 1, 7};

      for(na = 0; na <= 7; ++na) {
         int[] var28 = nbList2;
         int var29 = nbList2.length;

         for(var14 = 0; var14 < var29; ++var14) {
            nb = var28[var14];

            for(nd = 0; nd < 25; ++nd) {
               PyByteArray u = x.getslice(0, na + nd + nb, 1);
               if (verbose >= 2) {
                  System.out.printf("delslice(start=%d, stop=%d, step=%d, n=%d)\n", na, na + nd, 1, nd);
                  if (verbose >= 3) {
                     System.out.println("u = " + this.toString(u));
                  }
               }

               u.delslice(na, na + nd, 1, nd);
               if (verbose >= 1) {
                  System.out.println("u'= " + this.toString(u));
               }

               checkSlice(na, nd, nb, 0, xInts, (int[])null, u);
            }
         }
      }

   }

   public void test__delslice__2() {
      int verbose = 0;
      String ver = "Et tes pâleurs, alors que lasse,";
      int L = ver.length();
      int[] uRef = toInts(ver);
      this.interp = new PythonInterpreter();
      int[] posStart = new int[]{0, 3, 7, 16, L - 1};
      int[] posStop = new int[]{0, 3, 7, 16, L - 6, L};
      int[] var7 = posStart;
      int var8 = posStart.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         int start = var7[var9];
         PyObject pyStart = new PyInteger(start);
         Object pyStart_L;
         if (start > 0) {
            pyStart_L = new PyInteger(start - L);
         } else {
            pyStart_L = Py.None;
         }

         int[] var13 = posStop;
         int var14 = posStop.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            int stop = var13[var15];
            if (stop >= start) {
               Object pyStop_L;
               if (stop < L) {
                  pyStop_L = new PyInteger(stop - L);
               } else {
                  pyStop_L = Py.None;
               }

               PyObject pyStop = new PyInteger(stop);
               this.doTest__delslice__2(uRef, pyStart, pyStop, start, stop, verbose + 2);
               this.doTest__delslice__2(uRef, (PyObject)pyStart_L, pyStop, start, stop, verbose);
               this.doTest__delslice__2(uRef, pyStart, (PyObject)pyStop_L, start, stop, verbose);
               this.doTest__delslice__2(uRef, (PyObject)pyStart_L, (PyObject)pyStop_L, start, stop, verbose);
            }
         }
      }

   }

   private void doTest__delslice__2(int[] uRef, PyObject pyStart, PyObject pyStop, int start, int stop, int verbose) {
      PyByteArray u = this.getInstance(uRef);
      if (verbose >= 4) {
         System.out.printf("    __delslice__(%s,%s,1)\n", pyStart, pyStop);
         System.out.println("u = " + this.toString(u));
      }

      u.__delslice__(pyStart, pyStop);
      if (verbose >= 3) {
         System.out.println("u'= " + this.toString(u));
      }

      int nd = stop - start;
      int nb = uRef.length - stop;
      checkSlice(start, nd, nb, 0, uRef, (int[])null, u);
   }

   public void test__delslice__3() {
      int verbose = 0;
      this.interp = new PythonInterpreter();
      int[] uRef = randomInts(this.random, 25, 109, 115);
      int[] posStep = new int[]{2, 3, 5, 8, 25, 100};

      int var9;
      int step;
      int n;
      int n;
      int last;
      for(int start = 0; start < uRef.length; ++start) {
         PyInteger pyStart = new PyInteger(start);
         int len = uRef.length - start;
         int[] var7 = posStep;
         int var8 = posStep.length;

         for(var9 = 0; var9 < var8; ++var9) {
            step = var7[var9];
            PyInteger pyStep = new PyInteger(step);
            int nmax = (len + step - 1) / step;

            for(n = 1; n <= nmax; ++n) {
               n = start + step * (n - 1) + 1;

               for(last = n + 1; last < n + step; ++last) {
                  PyInteger pyStop = new PyInteger(last);
                  PyByteArray u = this.getInstance(uRef);
                  if (verbose >= 2) {
                     System.out.printf("__delslice__(%d,%d,%d) (%d deletions)\n", start, last, step, n);
                     if (verbose >= 3) {
                        System.out.println("u = " + this.toString(u));
                     }
                  }

                  u.__delslice__(pyStart, pyStop, pyStep);
                  if (verbose >= 1) {
                     System.out.println("u'= " + this.toString(u));
                  }

                  checkDelSlice(start, step, n, uRef, u);
               }
            }
         }
      }

      int[] negStep = new int[]{-1, -2, -5, -8, -25, -100};

      for(int start = uRef.length - 1; start >= 0; --start) {
         PyInteger pyStart = new PyInteger(start);
         int len = start + 1;
         int[] var23 = negStep;
         var9 = negStep.length;

         for(step = 0; step < var9; ++step) {
            int step = var23[step];
            PyInteger pyStep = new PyInteger(step);
            n = (len + -step - 1) / -step;

            for(n = 1; n <= n; ++n) {
               last = start + step * (n - 1) - 1;

               for(int stop = last; stop > last - -step && stop >= 0; --stop) {
                  PyInteger pyStop = new PyInteger(stop);
                  PyByteArray u = this.getInstance(uRef);
                  if (verbose >= 2) {
                     System.out.printf("__delslice__(%d,%d,%d) (%d deletions)\n", start, stop, step, n);
                     if (verbose >= 3) {
                        System.out.println("u = " + this.toString(u));
                     }
                  }

                  u.__delslice__(pyStart, pyStop, pyStep);
                  if (verbose >= 1) {
                     System.out.println("u'= " + this.toString(u));
                  }

                  checkDelSlice(start, step, n, uRef, u);
               }
            }
         }
      }

   }

   public void testDelsliceTime3() {
      int verbose = 1;
      this.timeDelslice3(50, 100, 7, verbose);
      this.timeDelslice3(50, 100, 25, verbose);
      this.timeDelslice3(20, 4, 2000, verbose);
   }

   private void timeDelslice3(int trials, int repeats, int N, int verbose) {
      int[] startList = new int[10];

      for(int i = 0; i < startList.length; ++i) {
         startList[i] = N * i / startList.length;
      }

      int[] posStep = new int[]{1, 2, 3, 5, 10};
      int[] stepList = new int[2 * posStep.length];

      for(int i = 0; i < posStep.length; ++i) {
         stepList[posStep.length - i - 1] = posStep[i];
         stepList[posStep.length + i] = -posStep[i];
      }

      long[][] elapsed = new long[startList.length][stepList.length];

      for(int row = 0; row < startList.length; ++row) {
         for(int col = 0; col < stepList.length; ++col) {
            elapsed[row][col] = Long.MAX_VALUE;
         }
      }

      int[] xRef = randomInts(this.random, N, 117, 122);
      PyByteArray x = this.getInstance(xRef);
      PyByteArray[] u = new PyByteArray[repeats];

      int row;
      int col;
      for(row = 0; row < trials; ++row) {
         for(col = 0; col < startList.length; ++col) {
            int row = (col + 5 * row) % startList.length;
            int start = startList[row];

            for(int icol = 0; icol < stepList.length; ++icol) {
               int col = (icol + row) % stepList.length;
               int step = stepList[col];
               int n;
               if (step > 0) {
                  n = (xRef.length - start + step - 1) / step;
               } else {
                  n = (start + -step) / -step;
               }

               PyInteger pyStart = new PyInteger(start);
               PyObject pyStop = Py.None;
               PyInteger pyStep = new PyInteger(step);
               if (row == 0) {
                  this.doTimeDelslice3(u, pyStart, pyStop, pyStep, x, verbose);
                  checkDelSlice(start, step, n, xRef, u[0]);
               }

               long t = this.doTimeDelslice3(u, pyStart, pyStop, pyStep, x, -1);
               if (t < elapsed[row][col]) {
                  elapsed[row][col] = t;
               }
            }
         }
      }

      System.out.print("     N  ,    start");

      for(row = 0; row < stepList.length; ++row) {
         System.out.printf(", step=%5d", stepList[row]);
      }

      System.out.println(", deletion time in microseconds.");

      for(row = 0; row < startList.length; ++row) {
         System.out.printf("%8d, %8d", N, startList[row]);

         for(col = 0; col < stepList.length; ++col) {
            double usPerCall = 0.001 * (double)elapsed[row][col] / (double)repeats;
            System.out.printf(", %10.3f", usPerCall);
         }

         System.out.println();
      }

   }

   private long doTimeDelslice3(PyByteArray[] u, PyObject pyStart, PyObject pyStop, PyObject pyStep, BaseBytes x, int verbose) {
      int repeats = 1;
      if (verbose < 0) {
         repeats = u.length;
      }

      for(int i = 0; i < repeats; ++i) {
         u[i] = new PyByteArray(x);
      }

      PyByteArray v = u[0];
      byte[] oldStorage = v.storage;
      if (verbose >= 3) {
         System.out.printf("__delslice__(%s,%s,%s)\n", pyStart, pyStop, pyStep);
         System.out.println("u = " + this.toString(v));
      }

      long beginTime = System.nanoTime();

      for(int i = 0; i < repeats; ++i) {
         u[i].__delslice__(pyStart, pyStop, pyStep);
      }

      long t = System.nanoTime() - beginTime;
      if (verbose >= 2) {
         boolean avAlloc = v.storage != oldStorage;
         if (v.size * 2 <= oldStorage.length) {
            avAlloc = false;
         }

         if (v.size > oldStorage.length) {
            avAlloc = false;
         }

         System.out.println("u'= " + this.toString(v) + (avAlloc ? " new" : ""));
      }

      return t;
   }

   public PyByteArray getInstance(PyType type) {
      return new PyByteArray(type);
   }

   public PyByteArray getInstance() {
      return new PyByteArray();
   }

   public PyByteArray getInstance(int size) {
      return new PyByteArray(size);
   }

   public PyByteArray getInstance(int[] value) {
      return new PyByteArray(value);
   }

   public PyByteArray getInstance(BaseBytes source) {
      return new PyByteArray(source);
   }

   public PyByteArray getInstance(Iterable source) {
      return new PyByteArray(source);
   }

   public PyByteArray getInstance(PyString arg, PyObject encoding, PyObject errors) {
      return new PyByteArray(arg, encoding, errors);
   }

   public PyByteArray getInstance(PyString arg, String encoding, String errors) {
      return new PyByteArray(arg, encoding, errors);
   }

   public PyByteArray getInstance(PyObject arg) throws PyException {
      return new PyByteArray(arg);
   }
}
