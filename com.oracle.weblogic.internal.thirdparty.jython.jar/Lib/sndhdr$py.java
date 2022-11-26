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
@Filename("sndhdr.py")
public class sndhdr$py extends PyFunctionTable implements PyRunnable {
   static sndhdr$py self;
   static final PyCode f$0;
   static final PyCode what$1;
   static final PyCode whathdr$2;
   static final PyCode test_aifc$3;
   static final PyCode test_au$4;
   static final PyCode test_hcom$5;
   static final PyCode test_voc$6;
   static final PyCode test_wav$7;
   static final PyCode test_8svx$8;
   static final PyCode test_sndt$9;
   static final PyCode test_sndr$10;
   static final PyCode get_long_be$11;
   static final PyCode get_long_le$12;
   static final PyCode get_short_be$13;
   static final PyCode get_short_le$14;
   static final PyCode test$15;
   static final PyCode testall$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Routines to help recognizing sound files.\n\nFunction whathdr() recognizes various types of sound file headers.\nIt understands almost all headers that SOX can decode.\n\nThe return tuple contains the following items, in this order:\n- file type (as SOX understands it)\n- sampling rate (0 if unknown or hard to decode)\n- number of channels (0 if unknown or hard to decode)\n- number of frames in the file (-1 if unknown or hard to decode)\n- number of bits/sample, or 'U' for U-LAW, or 'A' for A-LAW\n\nIf the file doesn't have a recognizable type, it returns None.\nIf the file can't be opened, IOError is raised.\n\nTo compute the total time, divide the number of frames by the\nsampling rate (a frame contains a sample for each channel).\n\nFunction what() calls whathdr().  (It used to also use some\nheuristics for raw data, but this doesn't work very well.)\n\nFinally, the function test() is a simple main program that calls\nwhat() for all files mentioned on the argument list.  For directory\narguments it calls what() for all files in that directory.  Default\nargument is \".\" (testing all files in the current directory).  The\noption -r tells it to recurse down directories found inside\nexplicitly given directories.\n"));
      var1.setline(28);
      PyString.fromInterned("Routines to help recognizing sound files.\n\nFunction whathdr() recognizes various types of sound file headers.\nIt understands almost all headers that SOX can decode.\n\nThe return tuple contains the following items, in this order:\n- file type (as SOX understands it)\n- sampling rate (0 if unknown or hard to decode)\n- number of channels (0 if unknown or hard to decode)\n- number of frames in the file (-1 if unknown or hard to decode)\n- number of bits/sample, or 'U' for U-LAW, or 'A' for A-LAW\n\nIf the file doesn't have a recognizable type, it returns None.\nIf the file can't be opened, IOError is raised.\n\nTo compute the total time, divide the number of frames by the\nsampling rate (a frame contains a sample for each channel).\n\nFunction what() calls whathdr().  (It used to also use some\nheuristics for raw data, but this doesn't work very well.)\n\nFinally, the function test() is a simple main program that calls\nwhat() for all files mentioned on the argument list.  For directory\narguments it calls what() for all files in that directory.  Default\nargument is \".\" (testing all files in the current directory).  The\noption -r tells it to recurse down directories found inside\nexplicitly given directories.\n");
      var1.setline(33);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("what"), PyString.fromInterned("whathdr")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(35);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, what$1, PyString.fromInterned("Guess the type of a sound file"));
      var1.setlocal("what", var5);
      var3 = null;
      var1.setline(41);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, whathdr$2, PyString.fromInterned("Recognize sound headers"));
      var1.setlocal("whathdr", var5);
      var3 = null;
      var1.setline(56);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("tests", var3);
      var3 = null;
      var1.setline(58);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_aifc$3, (PyObject)null);
      var1.setlocal("test_aifc", var5);
      var3 = null;
      var1.setline(76);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_aifc"));
      var1.setline(79);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_au$4, (PyObject)null);
      var1.setlocal("test_au", var5);
      var3 = null;
      var1.setline(105);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_au"));
      var1.setline(108);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_hcom$5, (PyObject)null);
      var1.setlocal("test_hcom", var5);
      var3 = null;
      var1.setline(114);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_hcom"));
      var1.setline(117);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_voc$6, (PyObject)null);
      var1.setlocal("test_voc", var5);
      var3 = null;
      var1.setline(127);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_voc"));
      var1.setline(130);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_wav$7, (PyObject)null);
      var1.setlocal("test_wav", var5);
      var3 = null;
      var1.setline(140);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_wav"));
      var1.setline(143);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_8svx$8, (PyObject)null);
      var1.setlocal("test_8svx", var5);
      var3 = null;
      var1.setline(149);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_8svx"));
      var1.setline(152);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_sndt$9, (PyObject)null);
      var1.setlocal("test_sndt", var5);
      var3 = null;
      var1.setline(158);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_sndt"));
      var1.setline(161);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_sndr$10, (PyObject)null);
      var1.setlocal("test_sndr", var5);
      var3 = null;
      var1.setline(167);
      var1.getname("tests").__getattr__("append").__call__(var2, var1.getname("test_sndr"));
      var1.setline(174);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_long_be$11, (PyObject)null);
      var1.setlocal("get_long_be", var5);
      var3 = null;
      var1.setline(177);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_long_le$12, (PyObject)null);
      var1.setlocal("get_long_le", var5);
      var3 = null;
      var1.setline(180);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_short_be$13, (PyObject)null);
      var1.setlocal("get_short_be", var5);
      var3 = null;
      var1.setline(183);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_short_le$14, (PyObject)null);
      var1.setlocal("get_short_le", var5);
      var3 = null;
      var1.setline(191);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test$15, (PyObject)null);
      var1.setlocal("test", var5);
      var3 = null;
      var1.setline(206);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testall$16, (PyObject)null);
      var1.setlocal("testall", var5);
      var3 = null;
      var1.setline(227);
      PyObject var6 = var1.getname("__name__");
      PyObject var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(228);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject what$1(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString.fromInterned("Guess the type of a sound file");
      var1.setline(37);
      PyObject var3 = var1.getglobal("whathdr").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject whathdr$2(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyString.fromInterned("Recognize sound headers");
      var1.setline(43);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(512));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getglobal("tests").__iter__();

      PyObject var5;
      do {
         var1.setline(45);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(49);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(46);
         var5 = var1.getlocal(3).__call__(var2, var1.getlocal(2), var1.getlocal(1));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(47);
      } while(!var1.getlocal(4).__nonzero__());

      var1.setline(48);
      var5 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject test_aifc$3(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = imp.importOne("aifc", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("FORM"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(61);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(62);
         PyObject var4 = var1.getlocal(0).__getslice__(Py.newInteger(8), Py.newInteger(12), (PyObject)null);
         var10000 = var4._eq(PyString.fromInterned("AIFC"));
         var4 = null;
         PyString var6;
         if (var10000.__nonzero__()) {
            var1.setline(63);
            var6 = PyString.fromInterned("aifc");
            var1.setlocal(3, var6);
            var4 = null;
         } else {
            var1.setline(64);
            var4 = var1.getlocal(0).__getslice__(Py.newInteger(8), Py.newInteger(12), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("AIFF"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(67);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(65);
            var6 = PyString.fromInterned("aiff");
            var1.setlocal(3, var6);
            var4 = null;
         }

         var1.setline(68);
         var1.getlocal(1).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));

         try {
            var1.setline(70);
            var4 = var1.getlocal(2).__getattr__("openfp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("r"));
            var1.setlocal(4, var4);
            var4 = null;
         } catch (Throwable var5) {
            PyException var8 = Py.setException(var5, var1);
            if (var8.match(new PyTuple(new PyObject[]{var1.getglobal("EOFError"), var1.getlocal(2).__getattr__("Error")}))) {
               var1.setline(72);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            throw var8;
         }

         var1.setline(73);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4).__getattr__("getframerate").__call__(var2), var1.getlocal(4).__getattr__("getnchannels").__call__(var2), var1.getlocal(4).__getattr__("getnframes").__call__(var2), Py.newInteger(8)._mul(var1.getlocal(4).__getattr__("getsampwidth").__call__(var2))});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject test_au$4(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned(".snd"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(81);
         var3 = var1.getglobal("get_long_be");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(82);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("\u0000ds."), PyString.fromInterned("dns.")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(85);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(83);
         var3 = var1.getglobal("get_long_le");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(86);
      PyString var4 = PyString.fromInterned("au");
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(87);
      PyObject var5 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(4), Py.newInteger(8), (PyObject)null));
      var1.setlocal(3, var5);
      var4 = null;
      var1.setline(88);
      var5 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(8), Py.newInteger(12), (PyObject)null));
      var1.setlocal(4, var5);
      var4 = null;
      var1.setline(89);
      var5 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(12), Py.newInteger(16), (PyObject)null));
      var1.setlocal(5, var5);
      var4 = null;
      var1.setline(90);
      var5 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(16), Py.newInteger(20), (PyObject)null));
      var1.setlocal(6, var5);
      var4 = null;
      var1.setline(91);
      var5 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(20), Py.newInteger(24), (PyObject)null));
      var1.setlocal(7, var5);
      var4 = null;
      var1.setline(92);
      PyInteger var7 = Py.newInteger(1);
      var1.setlocal(8, var7);
      var4 = null;
      var1.setline(93);
      var5 = var1.getlocal(5);
      var10000 = var5._eq(Py.newInteger(1));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(94);
         var4 = PyString.fromInterned("U");
         var1.setlocal(9, var4);
         var4 = null;
      } else {
         var1.setline(95);
         var5 = var1.getlocal(5);
         var10000 = var5._eq(Py.newInteger(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(96);
            var7 = Py.newInteger(8);
            var1.setlocal(9, var7);
            var4 = null;
         } else {
            var1.setline(97);
            var5 = var1.getlocal(5);
            var10000 = var5._eq(Py.newInteger(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(98);
               var7 = Py.newInteger(16);
               var1.setlocal(9, var7);
               var4 = null;
               var1.setline(99);
               var7 = Py.newInteger(2);
               var1.setlocal(8, var7);
               var4 = null;
            } else {
               var1.setline(101);
               var4 = PyString.fromInterned("?");
               var1.setlocal(9, var4);
               var4 = null;
            }
         }
      }

      var1.setline(102);
      var5 = var1.getlocal(8)._mul(var1.getlocal(7));
      var1.setlocal(10, var5);
      var4 = null;
      var1.setline(103);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(6), var1.getlocal(7), var1.getlocal(4)._floordiv(var1.getlocal(10)), var1.getlocal(9)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject test_hcom$5(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyObject var3 = var1.getlocal(0).__getslice__(Py.newInteger(65), Py.newInteger(69), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("FSSD"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(128), Py.newInteger(132), (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned("HCOM"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(110);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(111);
         PyObject var4 = var1.getglobal("get_long_be").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(128)._add(Py.newInteger(16)), Py.newInteger(128)._add(Py.newInteger(20)), (PyObject)null));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(112);
         PyTuple var5 = new PyTuple(new PyObject[]{PyString.fromInterned("hcom"), Py.newInteger(22050)._floordiv(var1.getlocal(2)), Py.newInteger(1), Py.newInteger(-1), Py.newInteger(8)});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject test_voc$6(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(20), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("Creative Voice File\u001a"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(119);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(120);
         PyObject var4 = var1.getglobal("get_short_le").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(20), Py.newInteger(22), (PyObject)null));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(121);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal(3, var7);
         var4 = null;
         var1.setline(122);
         var7 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(2);
         PyInteger var8 = var7;
         var4 = var10001;
         PyObject var5;
         if ((var5 = var8._le(var10001)).__nonzero__()) {
            var5 = var4._lt(Py.newInteger(500));
         }

         var10000 = var5;
         var4 = null;
         if (var5.__nonzero__()) {
            var4 = var1.getlocal(0).__getitem__(var1.getlocal(2));
            var10000 = var4._eq(PyString.fromInterned("\u0001"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(123);
            var4 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(2)._add(Py.newInteger(4))));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(124);
            var4 = var1.getglobal("int").__call__(var2, Py.newFloat(1000000.0)._div(Py.newInteger(256)._sub(var1.getlocal(4))));
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(125);
         PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("voc"), var1.getlocal(3), Py.newInteger(1), Py.newInteger(-1), Py.newInteger(8)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject test_wav$7(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("RIFF"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(8), Py.newInteger(12), (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned("WAVE"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(12), Py.newInteger(16), (PyObject)null);
            var10000 = var3._ne(PyString.fromInterned("fmt "));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(133);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(134);
         PyObject var4 = var1.getglobal("get_short_le").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(20), Py.newInteger(22), (PyObject)null));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(135);
         var4 = var1.getglobal("get_short_le").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(22), Py.newInteger(24), (PyObject)null));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(136);
         var4 = var1.getglobal("get_long_le").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(24), Py.newInteger(28), (PyObject)null));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(137);
         var4 = var1.getglobal("get_short_le").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(34), Py.newInteger(36), (PyObject)null));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(138);
         PyTuple var5 = new PyTuple(new PyObject[]{PyString.fromInterned("wav"), var1.getlocal(4), var1.getlocal(3), Py.newInteger(-1), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject test_8svx$8(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("FORM"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(8), Py.newInteger(12), (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned("8SVX"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(145);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(147);
         PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned("8svx"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0), Py.newInteger(8)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject test_sndt$9(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("SOUND"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(154);
         var3 = var1.getglobal("get_long_le").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(8), Py.newInteger(12), (PyObject)null));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(155);
         var3 = var1.getglobal("get_short_le").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(20), Py.newInteger(22), (PyObject)null));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(156);
         PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned("sndt"), var1.getlocal(3), Py.newInteger(1), var1.getlocal(2), Py.newInteger(8)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_sndr$10(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("\u0000\u0000"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(163);
         var3 = var1.getglobal("get_short_le").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(2), Py.newInteger(4), (PyObject)null));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(164);
         PyInteger var5 = Py.newInteger(4000);
         PyObject var10001 = var1.getlocal(2);
         PyInteger var7 = var5;
         var3 = var10001;
         PyObject var4;
         if ((var4 = var7._le(var10001)).__nonzero__()) {
            var4 = var3._le(Py.newInteger(25000));
         }

         var3 = null;
         if (var4.__nonzero__()) {
            var1.setline(165);
            PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("sndr"), var1.getlocal(2), Py.newInteger(1), Py.newInteger(-1), Py.newInteger(8)});
            var1.f_lasti = -1;
            return var6;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_long_be$11(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)))._lshift(Py.newInteger(24))._or(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)))._lshift(Py.newInteger(16)))._or(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(2)))._lshift(Py.newInteger(8)))._or(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(3))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_long_le$12(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(3)))._lshift(Py.newInteger(24))._or(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(2)))._lshift(Py.newInteger(16)))._or(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)))._lshift(Py.newInteger(8)))._or(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_short_be$13(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)))._lshift(Py.newInteger(8))._or(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_short_le$14(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)))._lshift(Py.newInteger(8))._or(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$15(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(193);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(194);
      PyObject var10000 = var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("argv").__getitem__(Py.newInteger(1));
         var10000 = var3._eq(PyString.fromInterned("-r"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(195);
         var1.getlocal(0).__getattr__("argv").__delslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
         var1.setline(196);
         var5 = Py.newInteger(1);
         var1.setlocal(1, var5);
         var3 = null;
      }

      try {
         var1.setline(198);
         if (var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
            var1.setline(199);
            var1.getglobal("testall").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
         } else {
            var1.setline(201);
            var1.getglobal("testall").__call__((ThreadState)var2, new PyList(new PyObject[]{PyString.fromInterned(".")}), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
         }
      } catch (Throwable var4) {
         PyException var6 = Py.setException(var4, var1);
         if (!var6.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var6;
         }

         var1.setline(203);
         var1.getlocal(0).__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n[Interrupted]\n"));
         var1.setline(204);
         var1.getlocal(0).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testall$16(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(208);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(209);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         while(true) {
            var1.setline(209);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(5, var4);
            var1.setline(210);
            PyException var5;
            if (var1.getlocal(4).__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               var1.setline(211);
               Py.printComma(var1.getlocal(5)._add(PyString.fromInterned("/:")));
               var1.setline(212);
               PyObject var10000 = var1.getlocal(1);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(2);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(213);
                  Py.println(PyString.fromInterned("recursing down:"));
                  var1.setline(214);
                  PyObject var7 = imp.importOne("glob", var1, -1);
                  var1.setlocal(6, var7);
                  var5 = null;
                  var1.setline(215);
                  var7 = var1.getlocal(6).__getattr__("glob").__call__(var2, var1.getlocal(4).__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("*")));
                  var1.setlocal(7, var7);
                  var5 = null;
                  var1.setline(216);
                  var1.getglobal("testall").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
               } else {
                  var1.setline(218);
                  Py.println(PyString.fromInterned("*** directory (use -r) ***"));
               }
            } else {
               var1.setline(220);
               Py.printComma(var1.getlocal(5)._add(PyString.fromInterned(":")));
               var1.setline(221);
               var1.getlocal(3).__getattr__("stdout").__getattr__("flush").__call__(var2);

               try {
                  var1.setline(223);
                  Py.println(var1.getglobal("what").__call__(var2, var1.getlocal(5)));
               } catch (Throwable var6) {
                  var5 = Py.setException(var6, var1);
                  if (!var5.match(var1.getglobal("IOError"))) {
                     throw var5;
                  }

                  var1.setline(225);
                  Py.println(PyString.fromInterned("*** not found ***"));
               }
            }
         }
      }
   }

   public sndhdr$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"filename", "res"};
      what$1 = Py.newCode(1, var2, var1, "what", 35, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "f", "h", "tf", "res"};
      whathdr$2 = Py.newCode(1, var2, var1, "whathdr", 41, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f", "aifc", "fmt", "a"};
      test_aifc$3 = Py.newCode(2, var2, var1, "test_aifc", 58, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f", "type", "hdr_size", "data_size", "encoding", "rate", "nchannels", "sample_size", "sample_bits", "frame_size"};
      test_au$4 = Py.newCode(2, var2, var1, "test_au", 79, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f", "divisor"};
      test_hcom$5 = Py.newCode(2, var2, var1, "test_hcom", 108, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f", "sbseek", "rate", "ratecode"};
      test_voc$6 = Py.newCode(2, var2, var1, "test_voc", 117, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f", "style", "nchannels", "rate", "sample_bits"};
      test_wav$7 = Py.newCode(2, var2, var1, "test_wav", 130, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f"};
      test_8svx$8 = Py.newCode(2, var2, var1, "test_8svx", 143, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f", "nsamples", "rate"};
      test_sndt$9 = Py.newCode(2, var2, var1, "test_sndt", 152, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "f", "rate"};
      test_sndr$10 = Py.newCode(2, var2, var1, "test_sndr", 161, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      get_long_be$11 = Py.newCode(1, var2, var1, "get_long_be", 174, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      get_long_le$12 = Py.newCode(1, var2, var1, "get_long_le", 177, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      get_short_be$13 = Py.newCode(1, var2, var1, "get_short_be", 180, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      get_short_le$14 = Py.newCode(1, var2, var1, "get_short_le", 183, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys", "recursive"};
      test$15 = Py.newCode(0, var2, var1, "test", 191, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"list", "recursive", "toplevel", "sys", "os", "filename", "glob", "names"};
      testall$16 = Py.newCode(3, var2, var1, "testall", 206, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sndhdr$py("sndhdr$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sndhdr$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.what$1(var2, var3);
         case 2:
            return this.whathdr$2(var2, var3);
         case 3:
            return this.test_aifc$3(var2, var3);
         case 4:
            return this.test_au$4(var2, var3);
         case 5:
            return this.test_hcom$5(var2, var3);
         case 6:
            return this.test_voc$6(var2, var3);
         case 7:
            return this.test_wav$7(var2, var3);
         case 8:
            return this.test_8svx$8(var2, var3);
         case 9:
            return this.test_sndt$9(var2, var3);
         case 10:
            return this.test_sndr$10(var2, var3);
         case 11:
            return this.get_long_be$11(var2, var3);
         case 12:
            return this.get_long_le$12(var2, var3);
         case 13:
            return this.get_short_be$13(var2, var3);
         case 14:
            return this.get_short_le$14(var2, var3);
         case 15:
            return this.test$15(var2, var3);
         case 16:
            return this.testall$16(var2, var3);
         default:
            return null;
      }
   }
}
