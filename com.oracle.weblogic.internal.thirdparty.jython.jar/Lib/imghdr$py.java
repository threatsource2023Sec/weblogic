import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("imghdr.py")
public class imghdr$py extends PyFunctionTable implements PyRunnable {
   static imghdr$py self;
   static final PyCode f$0;
   static final PyCode what$1;
   static final PyCode test_jpeg$2;
   static final PyCode test_exif$3;
   static final PyCode test_png$4;
   static final PyCode test_gif$5;
   static final PyCode test_tiff$6;
   static final PyCode test_rgb$7;
   static final PyCode test_pbm$8;
   static final PyCode test_pgm$9;
   static final PyCode test_ppm$10;
   static final PyCode test_rast$11;
   static final PyCode test_xbm$12;
   static final PyCode test_bmp$13;
   static final PyCode test$14;
   static final PyCode testall$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Recognize image file formats based on their first few bytes."));
      var1.setline(1);
      PyString.fromInterned("Recognize image file formats based on their first few bytes.");
      var1.setline(3);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("what")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, what$1, (PyObject)null);
      var1.setlocal("what", var5);
      var3 = null;
      var1.setline(35);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("tests", var3);
      var3 = null;
      var1.setline(37);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_jpeg$2, PyString.fromInterned("JPEG data in JFIF format"));
      var1.setlocal("test_jpeg", var5);
      var3 = null;
      var1.setline(42);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_jpeg"));
      var1.setline(44);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_exif$3, PyString.fromInterned("JPEG data in Exif format"));
      var1.setlocal("test_exif", var5);
      var3 = null;
      var1.setline(49);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_exif"));
      var1.setline(51);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_png$4, (PyObject)null);
      var1.setlocal("test_png", var5);
      var3 = null;
      var1.setline(55);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_png"));
      var1.setline(57);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_gif$5, PyString.fromInterned("GIF ('87 and '89 variants)"));
      var1.setlocal("test_gif", var5);
      var3 = null;
      var1.setline(62);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_gif"));
      var1.setline(64);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_tiff$6, PyString.fromInterned("TIFF (can be in Motorola or Intel byte order)"));
      var1.setlocal("test_tiff", var5);
      var3 = null;
      var1.setline(69);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_tiff"));
      var1.setline(71);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_rgb$7, PyString.fromInterned("SGI image library"));
      var1.setlocal("test_rgb", var5);
      var3 = null;
      var1.setline(76);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_rgb"));
      var1.setline(78);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_pbm$8, PyString.fromInterned("PBM (portable bitmap)"));
      var1.setlocal("test_pbm", var5);
      var3 = null;
      var1.setline(84);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_pbm"));
      var1.setline(86);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_pgm$9, PyString.fromInterned("PGM (portable graymap)"));
      var1.setlocal("test_pgm", var5);
      var3 = null;
      var1.setline(92);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_pgm"));
      var1.setline(94);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_ppm$10, PyString.fromInterned("PPM (portable pixmap)"));
      var1.setlocal("test_ppm", var5);
      var3 = null;
      var1.setline(100);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_ppm"));
      var1.setline(102);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_rast$11, PyString.fromInterned("Sun raster file"));
      var1.setlocal("test_rast", var5);
      var3 = null;
      var1.setline(107);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_rast"));
      var1.setline(109);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_xbm$12, PyString.fromInterned("X bitmap (X10 or X11)"));
      var1.setlocal("test_xbm", var5);
      var3 = null;
      var1.setline(115);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_xbm"));
      var1.setline(117);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_bmp$13, (PyObject)null);
      var1.setlocal("test_bmp", var5);
      var3 = null;
      var1.setline(121);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_bmp"));
      var1.setline(127);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test$14, (PyObject)null);
      var1.setlocal("test", var5);
      var3 = null;
      var1.setline(142);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testall$15, (PyObject)null);
      var1.setlocal("testall", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject what$1(PyFrame var1, ThreadState var2) {
      var1.setline(10);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(11);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(12);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(13);
            var3 = var1.getlocal(2).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(32));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(15);
            var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(16);
            var3 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(32));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(17);
            var1.getlocal(0).__getattr__("seek").__call__(var2, var1.getlocal(3));
            var1.setline(18);
            var3 = var1.getglobal("None");
            var1.setlocal(2, var3);
            var3 = null;
         }
      } else {
         var1.setline(20);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var3 = null;

      Throwable var12;
      label63: {
         PyObject var4;
         boolean var10001;
         try {
            var1.setline(22);
            var4 = var1.getglobal("tests").__iter__();
         } catch (Throwable var8) {
            var12 = var8;
            var10001 = false;
            break label63;
         }

         PyObject var6;
         while(true) {
            PyObject var5;
            try {
               var1.setline(22);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  break;
               }
            } catch (Throwable var10) {
               var12 = var10;
               var10001 = false;
               break label63;
            }

            try {
               var1.setlocal(4, var5);
               var1.setline(23);
               var6 = var1.getlocal(4).__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(24);
               if (!var1.getlocal(5).__nonzero__()) {
                  continue;
               }

               var1.setline(25);
               var6 = var1.getlocal(5);
            } catch (Throwable var9) {
               var12 = var9;
               var10001 = false;
               break label63;
            }

            var1.setline(27);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(27);
               var1.getlocal(2).__getattr__("close").__call__(var2);
            }

            try {
               var1.f_lasti = -1;
               return var6;
            } catch (Throwable var7) {
               var12 = var7;
               var10001 = false;
               break label63;
            }
         }

         var1.setline(27);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(27);
            var1.getlocal(2).__getattr__("close").__call__(var2);
         }

         var1.setline(28);
         var6 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var6;
      }

      Throwable var11 = var12;
      Py.addTraceback(var11, var1);
      var1.setline(27);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(27);
         var1.getlocal(2).__getattr__("close").__call__(var2);
      }

      throw (Throwable)var11;
   }

   public PyObject test_jpeg$2(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyString.fromInterned("JPEG data in JFIF format");
      var1.setline(39);
      PyObject var3 = var1.getlocal(0).__getslice__(Py.newInteger(6), Py.newInteger(10), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("JFIF"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(40);
         PyString var4 = PyString.fromInterned("jpeg");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_exif$3(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("JPEG data in Exif format");
      var1.setline(46);
      PyObject var3 = var1.getlocal(0).__getslice__(Py.newInteger(6), Py.newInteger(10), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("Exif"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(47);
         PyString var4 = PyString.fromInterned("jpeg");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_png$4(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(8), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("\u0089PNG\r\n\u001a\n"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(53);
         PyString var4 = PyString.fromInterned("png");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_gif$5(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyString.fromInterned("GIF ('87 and '89 variants)");
      var1.setline(59);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("GIF87a"), PyString.fromInterned("GIF89a")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(60);
         PyString var4 = PyString.fromInterned("gif");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_tiff$6(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyString.fromInterned("TIFF (can be in Motorola or Intel byte order)");
      var1.setline(66);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("MM"), PyString.fromInterned("II")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         PyString var4 = PyString.fromInterned("tiff");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_rgb$7(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("SGI image library");
      var1.setline(73);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("\u0001Ú"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(74);
         PyString var4 = PyString.fromInterned("rgb");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_pbm$8(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyString.fromInterned("PBM (portable bitmap)");
      var1.setline(80);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._ge(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("P"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
            var10000 = var3._in(PyString.fromInterned("14"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
               var10000 = var3._in(PyString.fromInterned(" \t\n\r"));
               var3 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(82);
         PyString var4 = PyString.fromInterned("pbm");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_pgm$9(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyString.fromInterned("PGM (portable graymap)");
      var1.setline(88);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._ge(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("P"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
            var10000 = var3._in(PyString.fromInterned("25"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
               var10000 = var3._in(PyString.fromInterned(" \t\n\r"));
               var3 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(90);
         PyString var4 = PyString.fromInterned("pgm");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_ppm$10(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyString.fromInterned("PPM (portable pixmap)");
      var1.setline(96);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._ge(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("P"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
            var10000 = var3._in(PyString.fromInterned("36"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
               var10000 = var3._in(PyString.fromInterned(" \t\n\r"));
               var3 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(98);
         PyString var4 = PyString.fromInterned("ppm");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_rast$11(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyString.fromInterned("Sun raster file");
      var1.setline(104);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("Y¦j\u0095"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(105);
         PyString var4 = PyString.fromInterned("rast");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_xbm$12(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("X bitmap (X10 or X11)");
      var1.setline(111);
      PyString var3 = PyString.fromInterned("#define ");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(112);
      PyObject var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(2)), (PyObject)null);
      PyObject var10000 = var4._eq(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(113);
         var3 = PyString.fromInterned("xbm");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_bmp$13(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("BM"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(119);
         PyString var4 = PyString.fromInterned("bmp");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test$14(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(129);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(130);
      PyObject var10000 = var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("argv").__getitem__(Py.newInteger(1));
         var10000 = var3._eq(PyString.fromInterned("-r"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(131);
         var1.getlocal(0).__getattr__("argv").__delslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
         var1.setline(132);
         var5 = Py.newInteger(1);
         var1.setlocal(1, var5);
         var3 = null;
      }

      try {
         var1.setline(134);
         if (var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
            var1.setline(135);
            var1.getglobal("testall").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
         } else {
            var1.setline(137);
            var1.getglobal("testall").__call__((ThreadState)var2, new PyList(new PyObject[]{PyString.fromInterned(".")}), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
         }
      } catch (Throwable var4) {
         PyException var6 = Py.setException(var4, var1);
         if (!var6.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var6;
         }

         var1.setline(139);
         var1.getlocal(0).__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n[Interrupted]\n"));
         var1.setline(140);
         var1.getlocal(0).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testall$15(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(144);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(145);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         while(true) {
            var1.setline(145);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(5, var4);
            var1.setline(146);
            PyException var5;
            if (var1.getlocal(4).__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               var1.setline(147);
               Py.printComma(var1.getlocal(5)._add(PyString.fromInterned("/:")));
               var1.setline(148);
               PyObject var10000 = var1.getlocal(1);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(2);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(149);
                  Py.println(PyString.fromInterned("recursing down:"));
                  var1.setline(150);
                  PyObject var7 = imp.importOne("glob", var1, -1);
                  var1.setlocal(6, var7);
                  var5 = null;
                  var1.setline(151);
                  var7 = var1.getlocal(6).__getattr__("glob").__call__(var2, var1.getlocal(4).__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("*")));
                  var1.setlocal(7, var7);
                  var5 = null;
                  var1.setline(152);
                  var1.getglobal("testall").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
               } else {
                  var1.setline(154);
                  Py.println(PyString.fromInterned("*** directory (use -r) ***"));
               }
            } else {
               var1.setline(156);
               Py.printComma(var1.getlocal(5)._add(PyString.fromInterned(":")));
               var1.setline(157);
               var1.getlocal(3).__getattr__("stdout").__getattr__("flush").__call__(var2);

               try {
                  var1.setline(159);
                  Py.println(var1.getglobal("what").__call__(var2, var1.getlocal(5)));
               } catch (Throwable var6) {
                  var5 = Py.setException(var6, var1);
                  if (!var5.match(var1.getglobal("IOError"))) {
                     throw var5;
                  }

                  var1.setline(161);
                  Py.println(PyString.fromInterned("*** not found ***"));
               }
            }
         }
      }
   }

   public imghdr$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"file", "h", "f", "location", "tf", "res"};
      what$1 = Py.newCode(2, var2, var1, "what", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_jpeg$2 = Py.newCode(2, var2, var1, "test_jpeg", 37, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_exif$3 = Py.newCode(2, var2, var1, "test_exif", 44, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_png$4 = Py.newCode(2, var2, var1, "test_png", 51, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_gif$5 = Py.newCode(2, var2, var1, "test_gif", 57, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_tiff$6 = Py.newCode(2, var2, var1, "test_tiff", 64, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_rgb$7 = Py.newCode(2, var2, var1, "test_rgb", 71, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_pbm$8 = Py.newCode(2, var2, var1, "test_pbm", 78, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_pgm$9 = Py.newCode(2, var2, var1, "test_pgm", 86, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_ppm$10 = Py.newCode(2, var2, var1, "test_ppm", 94, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_rast$11 = Py.newCode(2, var2, var1, "test_rast", 102, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f", "s"};
      test_xbm$12 = Py.newCode(2, var2, var1, "test_xbm", 109, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_bmp$13 = Py.newCode(2, var2, var1, "test_bmp", 117, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys", "recursive"};
      test$14 = Py.newCode(0, var2, var1, "test", 127, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"list", "recursive", "toplevel", "sys", "os", "filename", "glob", "names"};
      testall$15 = Py.newCode(3, var2, var1, "testall", 142, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new imghdr$py("imghdr$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(imghdr$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.what$1(var2, var3);
         case 2:
            return this.test_jpeg$2(var2, var3);
         case 3:
            return this.test_exif$3(var2, var3);
         case 4:
            return this.test_png$4(var2, var3);
         case 5:
            return this.test_gif$5(var2, var3);
         case 6:
            return this.test_tiff$6(var2, var3);
         case 7:
            return this.test_rgb$7(var2, var3);
         case 8:
            return this.test_pbm$8(var2, var3);
         case 9:
            return this.test_pgm$9(var2, var3);
         case 10:
            return this.test_ppm$10(var2, var3);
         case 11:
            return this.test_rast$11(var2, var3);
         case 12:
            return this.test_xbm$12(var2, var3);
         case 13:
            return this.test_bmp$13(var2, var3);
         case 14:
            return this.test$14(var2, var3);
         case 15:
            return this.testall$15(var2, var3);
         default:
            return null;
      }
   }
}
