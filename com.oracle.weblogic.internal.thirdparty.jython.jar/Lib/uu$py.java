import java.util.Arrays;
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
@Filename("uu.py")
public class uu$py extends PyFunctionTable implements PyRunnable {
   static uu$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode encode$2;
   static final PyCode decode$3;
   static final PyCode test$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Implementation of the UUencode and UUdecode functions.\n\nencode(in_file, out_file [,name, mode])\ndecode(in_file [, out_file, mode])\n"));
      var1.setline(31);
      PyString.fromInterned("Implementation of the UUencode and UUdecode functions.\n\nencode(in_file, out_file [,name, mode])\ndecode(in_file [, out_file, mode])\n");
      var1.setline(33);
      PyObject var3 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var3);
      var3 = null;
      var1.setline(34);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(35);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(37);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("Error"), PyString.fromInterned("encode"), PyString.fromInterned("decode")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(39);
      PyObject[] var6 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("Error", var6, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(42);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, encode$2, PyString.fromInterned("Uuencode file"));
      var1.setlocal("encode", var7);
      var3 = null;
      var1.setline(90);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0)};
      var7 = new PyFunction(var1.f_globals, var6, decode$3, PyString.fromInterned("Decode uuencoded file"));
      var1.setlocal("decode", var7);
      var3 = null;
      var1.setline(158);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, test$4, PyString.fromInterned("uuencode/uudecode main program"));
      var1.setlocal("test", var7);
      var3 = null;
      var1.setline(195);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(196);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(40);
      return var1.getf_locals();
   }

   public PyObject encode$2(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Uuencode file");
      var1.setline(47);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      PyObject var5;
      try {
         var1.setline(49);
         var4 = var1.getlocal(0);
         PyObject var10000 = var4._eq(PyString.fromInterned("-"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(50);
            var4 = var1.getglobal("sys").__getattr__("stdin");
            var1.setlocal(0, var4);
            var4 = null;
         } else {
            var1.setline(51);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__nonzero__()) {
               var1.setline(52);
               var4 = var1.getlocal(2);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(53);
                  var4 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
                  var1.setlocal(2, var4);
                  var4 = null;
               }

               var1.setline(54);
               var4 = var1.getlocal(3);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  try {
                     var1.setline(56);
                     var4 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getattr__("st_mode");
                     var1.setlocal(3, var4);
                     var4 = null;
                  } catch (Throwable var6) {
                     PyException var8 = Py.setException(var6, var1);
                     if (!var8.match(var1.getglobal("AttributeError"))) {
                        throw var8;
                     }

                     var1.setline(58);
                  }
               }

               var1.setline(59);
               var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
               var1.setlocal(0, var4);
               var4 = null;
               var1.setline(60);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0));
            }
         }

         var1.setline(64);
         var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("-"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(65);
            var4 = var1.getglobal("sys").__getattr__("stdout");
            var1.setlocal(1, var4);
            var4 = null;
         } else {
            var1.setline(66);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
               var1.setline(67);
               var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(68);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(1));
            }
         }

         var1.setline(72);
         var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(73);
            PyString var9 = PyString.fromInterned("-");
            var1.setlocal(2, var9);
            var4 = null;
         }

         var1.setline(74);
         var4 = var1.getlocal(3);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(75);
            PyInteger var10 = Py.newInteger(438);
            var1.setlocal(3, var10);
            var4 = null;
         }

         var1.setline(79);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("begin %o %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)._and(Py.newInteger(511)), var1.getlocal(2)})));
         var1.setline(80);
         var4 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(45));
         var1.setlocal(5, var4);
         var4 = null;

         while(true) {
            var1.setline(81);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            var10000 = var4._gt(Py.newInteger(0));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(84);
               var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" \nend\n"));
               break;
            }

            var1.setline(82);
            var1.getlocal(1).__getattr__("write").__call__(var2, var1.getglobal("binascii").__getattr__("b2a_uu").__call__(var2, var1.getlocal(5)));
            var1.setline(83);
            var4 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(45));
            var1.setlocal(5, var4);
            var4 = null;
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(86);
         var4 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(86);
            var5 = var4.__iternext__();
            if (var5 == null) {
               throw (Throwable)var7;
            }

            var1.setlocal(6, var5);
            var1.setline(87);
            var1.getlocal(6).__getattr__("close").__call__(var2);
         }
      }

      var1.setline(86);
      var4 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(86);
         var5 = var4.__iternext__();
         if (var5 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var5);
         var1.setline(87);
         var1.getlocal(6).__getattr__("close").__call__(var2);
      }
   }

   public PyObject decode$3(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString.fromInterned("Decode uuencoded file");
      var1.setline(95);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(96);
      PyObject var10 = var1.getlocal(0);
      PyObject var10000 = var10._eq(PyString.fromInterned("-"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(97);
         var10 = var1.getglobal("sys").__getattr__("stdin");
         var1.setlocal(0, var10);
         var3 = null;
      } else {
         var1.setline(98);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(99);
            var10 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var10);
            var3 = null;
            var1.setline(100);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0));
         }
      }

      var3 = null;

      PyObject var4;
      PyObject var5;
      try {
         PyException var11;
         while(true) {
            var1.setline(105);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(106);
            var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(107);
            if (var1.getlocal(5).__not__().__nonzero__()) {
               var1.setline(108);
               throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No valid begin line found in input file")));
            }

            var1.setline(109);
            if (!var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("begin")).__not__().__nonzero__()) {
               var1.setline(111);
               var4 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)Py.newInteger(2));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(112);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
               var10000 = var4._eq(Py.newInteger(3));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(6).__getitem__(Py.newInteger(0));
                  var10000 = var4._eq(PyString.fromInterned("begin"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  try {
                     var1.setline(114);
                     var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getitem__(Py.newInteger(1)), (PyObject)Py.newInteger(8));
                     break;
                  } catch (Throwable var6) {
                     var11 = Py.setException(var6, var1);
                     if (!var11.match(var1.getglobal("ValueError"))) {
                        throw var11;
                     }

                     var1.setline(117);
                  }
               }
            }
         }

         var1.setline(118);
         var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(119);
            var4 = var1.getlocal(6).__getitem__(Py.newInteger(2)).__getattr__("rstrip").__call__(var2);
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(120);
            if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(121);
               throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("Cannot overwrite existing file: %s")._mod(var1.getlocal(1))));
            }
         }

         var1.setline(122);
         var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(123);
            var4 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getitem__(Py.newInteger(1)), (PyObject)Py.newInteger(8));
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(127);
         var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("-"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(128);
            var4 = var1.getglobal("sys").__getattr__("stdout");
            var1.setlocal(1, var4);
            var4 = null;
         } else {
            var1.setline(129);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
               var1.setline(130);
               var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
               var1.setlocal(7, var4);
               var4 = null;

               try {
                  var1.setline(132);
                  var1.getglobal("os").__getattr__("path").__getattr__("chmod").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               } catch (Throwable var7) {
                  var11 = Py.setException(var7, var1);
                  if (!var11.match(var1.getglobal("AttributeError"))) {
                     throw var11;
                  }

                  var1.setline(134);
               }

               var1.setline(135);
               var4 = var1.getlocal(7);
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(136);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(1));
            }
         }

         var1.setline(140);
         var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(8, var4);
         var4 = null;

         while(true) {
            var1.setline(141);
            var10000 = var1.getlocal(8);
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(8).__getattr__("strip").__call__(var2);
               var10000 = var4._ne(PyString.fromInterned("end"));
               var4 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(152);
               if (var1.getlocal(8).__not__().__nonzero__()) {
                  var1.setline(153);
                  throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Truncated input file")));
               }
               break;
            }

            try {
               var1.setline(143);
               var4 = var1.getglobal("binascii").__getattr__("a2b_uu").__call__(var2, var1.getlocal(8));
               var1.setlocal(9, var4);
               var4 = null;
            } catch (Throwable var8) {
               var11 = Py.setException(var8, var1);
               if (!var11.match(var1.getglobal("binascii").__getattr__("Error"))) {
                  throw var11;
               }

               var5 = var11.value;
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(146);
               var5 = var1.getglobal("ord").__call__(var2, var1.getlocal(8).__getitem__(Py.newInteger(0)))._sub(Py.newInteger(32))._and(Py.newInteger(63))._mul(Py.newInteger(4))._add(Py.newInteger(5))._floordiv(Py.newInteger(3));
               var1.setlocal(11, var5);
               var5 = null;
               var1.setline(147);
               var5 = var1.getglobal("binascii").__getattr__("a2b_uu").__call__(var2, var1.getlocal(8).__getslice__((PyObject)null, var1.getlocal(11), (PyObject)null));
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(148);
               if (var1.getlocal(3).__not__().__nonzero__()) {
                  var1.setline(149);
                  var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("Warning: %s\n")._mod(var1.getlocal(10)));
               }
            }

            var1.setline(150);
            var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(9));
            var1.setline(151);
            var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
            var1.setlocal(8, var4);
            var4 = null;
         }
      } catch (Throwable var9) {
         Py.addTraceback(var9, var1);
         var1.setline(155);
         var4 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(155);
            var5 = var4.__iternext__();
            if (var5 == null) {
               throw (Throwable)var9;
            }

            var1.setlocal(12, var5);
            var1.setline(156);
            var1.getlocal(12).__getattr__("close").__call__(var2);
         }
      }

      var1.setline(155);
      var4 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(155);
         var5 = var4.__iternext__();
         if (var5 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(12, var5);
         var1.setline(156);
         var1.getlocal(12).__getattr__("close").__call__(var2);
      }
   }

   public PyObject test$4(PyFrame var1, ThreadState var2) {
      var1.setline(159);
      PyString.fromInterned("uuencode/uudecode main program");
      var1.setline(161);
      PyObject var3 = imp.importOne("optparse", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(162);
      PyObject var10000 = var1.getlocal(0).__getattr__("OptionParser");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("usage: %prog [-d] [-t] [input [output]]")};
      String[] var4 = new String[]{"usage"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(163);
      var10000 = var1.getlocal(1).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-d"), PyString.fromInterned("--decode"), PyString.fromInterned("decode"), PyString.fromInterned("Decode (instead of encode)?"), var1.getglobal("False"), PyString.fromInterned("store_true")};
      var4 = new String[]{"dest", "help", "default", "action"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(164);
      var10000 = var1.getlocal(1).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-t"), PyString.fromInterned("--text"), PyString.fromInterned("text"), PyString.fromInterned("data is text, encoded format unix-compatible text?"), var1.getglobal("False"), PyString.fromInterned("store_true")};
      var4 = new String[]{"dest", "help", "default", "action"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(166);
      var3 = var1.getlocal(1).__getattr__("parse_args").__call__(var2);
      PyObject[] var7 = Py.unpackSequence(var3, 2);
      PyObject var5 = var7[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(167);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var10000 = var3._gt(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(168);
         var1.getlocal(1).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("incorrect number of arguments"));
         var1.setline(169);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.setline(171);
      var3 = var1.getglobal("sys").__getattr__("stdin");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(174);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(175);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(176);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(1));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(178);
      if (var1.getlocal(2).__getattr__("decode").__nonzero__()) {
         var1.setline(179);
         if (var1.getlocal(2).__getattr__("text").__nonzero__()) {
            var1.setline(180);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("basestring")).__nonzero__()) {
               var1.setline(181);
               var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("w"));
               var1.setlocal(5, var3);
               var3 = null;
            } else {
               var1.setline(183);
               Py.printComma(var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
               Py.println(PyString.fromInterned(": cannot do -t to stdout"));
               var1.setline(184);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }
         }

         var1.setline(185);
         var1.getglobal("decode").__call__(var2, var1.getlocal(4), var1.getlocal(5));
      } else {
         var1.setline(187);
         if (var1.getlocal(2).__getattr__("text").__nonzero__()) {
            var1.setline(188);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("basestring")).__nonzero__()) {
               var1.setline(189);
               var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("r"));
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(191);
               Py.printComma(var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
               Py.println(PyString.fromInterned(": cannot do -t from stdin"));
               var1.setline(192);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }
         }

         var1.setline(193);
         var1.getglobal("encode").__call__(var2, var1.getlocal(4), var1.getlocal(5));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public uu$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 39, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"in_file", "out_file", "name", "mode", "opened_files", "data", "f"};
      encode$2 = Py.newCode(4, var2, var1, "encode", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"in_file", "out_file", "mode", "quiet", "opened_files", "hdr", "hdrfields", "fp", "s", "data", "v", "nbytes", "f"};
      decode$3 = Py.newCode(4, var2, var1, "decode", 90, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"optparse", "parser", "options", "args", "input", "output"};
      test$4 = Py.newCode(0, var2, var1, "test", 158, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new uu$py("uu$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(uu$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.encode$2(var2, var3);
         case 3:
            return this.decode$3(var2, var3);
         case 4:
            return this.test$4(var2, var3);
         default:
            return null;
      }
   }
}
